import java.util.ArrayList;
import java.util.List;

public class FriedmanMethod {

    private final double IC_RANDOM = 1.0 / 26.0;

    // Calcola l'indice di coincidenza di un testo cifrato.
    public double calcolaIndiceDiCoincidenza(String testo) {
        int[] frequenze = new int[26];
        int totaleLettere = 0;

        for (char c : testo.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                frequenze[c - 'A']++;
                totaleLettere++;
            }
        }

        double ic = 0.0;
        for (int f : frequenze) {
            ic += f * (f - 1);
        }

        if (totaleLettere <= 1) return 0.0;

        ic /= (double) (totaleLettere * (totaleLettere - 1));
        return ic;
    }

    // Stima la lunghezza della chiave usando la formula di Friedman.
    public int stimaLunghezzaChiave(String testo, double icLanguage) {
        double ic = calcolaIndiceDiCoincidenza(testo);
        double k = (icLanguage - IC_RANDOM) / (ic - IC_RANDOM);
        return (int) Math.round(k);
    }

    // Suddivide il testo in sottosequenze secondo la lunghezza della chiave
    public List<String> suddividiTesto(String testo, int lunghezzaChiave) {
        List<String> sottosequenze = new ArrayList<>();
        for (int i = 0; i < lunghezzaChiave; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < testo.length(); j += lunghezzaChiave) {
                char c = testo.charAt(j);
                if (Character.isLetter(c)) {
                    sb.append(c);
                }
            }
            sottosequenze.add(sb.toString());
        }
        return sottosequenze;
    }

    // Applica Friedman a k-1, k, k+1
    public void analizzaConFriedman(String testo, int kKasiski, double icLanguage) {
        for (int k : new int[]{kKasiski - 1, kKasiski, kKasiski + 1}) {
            if (k <= 0) continue; // evita valori non validi

            List<String> sottosequenze = suddividiTesto(testo, k);
            System.out.println("Analisi per k = " + k);
            for (int i = 0; i < sottosequenze.size(); i++) {
                String seq = sottosequenze.get(i);
                double ic = calcolaIndiceDiCoincidenza(seq);
                System.out.println("  Sottosequenza " + (i + 1) + ": IC = " + ic + " " + seq);
            }
            System.out.println();
        }
    }
}