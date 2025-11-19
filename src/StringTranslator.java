import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StringTranslator {


    //input La stringa da tradurre.
    //set1  La stringa che definisce l'alfabeto di origine (es. "abc", "a-z").
    //set2  La stringa che definisce l'alfabeto di destinazione (es. "xyz", "A-Z").

    public static String translate(String input, String set1, String set2) {
        if (input == null || input.isEmpty() || set1 == null || set2 == null || set1.isEmpty() || set2.isEmpty()) {
            return input; // Nessuna traduzione possibile
        }

        // FASE 1: SETUP - Costruisci la mappa di traduzione
        Map<Character, Character> translationMap = buildTranslationMap(set1, set2);

        if (translationMap.isEmpty()) {
            return input; // Nessuna traduzione valida trovata
        }

        // FASE 2: ELABORAZIONE - Traduci la stringa
        StringBuilder result = new StringBuilder(input.length());

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            // Cerca il carattere nella mappa.
            // Se non c'è (getOrDefault), usa il carattere originale 'c'.
            char translatedChar = translationMap.getOrDefault(c, c);

            result.append(translatedChar);
        }

        return result.toString();
    }
    private static Map<Character, Character> buildTranslationMap(String set1, String set2) {
        // Espandi i range (es. "a-z" -> "abc...z")
        String expandedSet1 = expandSet(set1);
        String expandedSet2 = expandSet(set2);

        Map<Character, Character> map = new HashMap<>();

        if (expandedSet1.isEmpty() || expandedSet2.isEmpty()) {
            return map; // Mappa vuota
        }

        // Comportamento di 'tr': se set2 è più corto, ripeti il suo ultimo carattere
        char lastCharSet2 = expandedSet2.charAt(expandedSet2.length() - 1);

        for (int i = 0; i < expandedSet1.length(); i++) {
            char c1 = expandedSet1.charAt(i);

            char c2;
            if (i < expandedSet2.length()) {
                c2 = expandedSet2.charAt(i);
            } else {
                c2 = lastCharSet2;
            }

            // Aggiungi solo se non già mappato (evita sovrascritture inutili)
            map.putIfAbsent(c1, c2);
        }

        return map;
    }

    private static String expandSet(String set) {
        StringBuilder expanded = new StringBuilder();

        for (int i = 0; i < set.length(); i++) {
            char c = set.charAt(i);

            // Cerca un pattern di range valido: "char1-char2"
            // (controlla che ci siano almeno 3 caratteri da i in poi)
            if (i + 2 < set.length() && set.charAt(i + 1) == '-') {
                char start = c;
                char end = set.charAt(i + 2);

                if (start <= end) {
                    // È un range valido (es. "a-z", "0-9")
                    for (char j = start; j <= end; j++) {
                        expanded.append(j);
                    }
                    // Abbiamo processato 3 caratteri ('a', '-', 'z'), 
                    // quindi saltiamo i prossimi 2
                    i += 2;
                } else {
                    // Range non valido (es. "z-a"), tratta 'c' come un letterale
                    expanded.append(c);
                }
            } else {
                // Non è un range, solo un carattere letterale
                expanded.append(c);
            }
        }
        return expanded.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String text;
        String set1;
        String set2;
        System.out.println("inserisci testo da sostituire");
        text = scanner.nextLine();
        System.out.println("inserisci set di partenza ");
        set1 = scanner.nextLine();
        System.out.println("inserisci set di arrivo (sostituzione delle lettere)");
        set2 = scanner.nextLine();
        String translation = StringTranslator.translate(text,set1,set2 );
        System.out.println("Originale: " + text);
        System.out.println("Traduzione: " + translation);

        /*
        // Esempio 1: Sostituzione semplice
        String testo1 = "La mia casa è bella.";
        String tradotto1 = StringTranslator.translate(testo1, "aeiou", "AEIOU");
        System.out.println("Originale: " + testo1);
        System.out.println("Tradotto 1: " + tradotto1);
        System.out.println("---");

        // Esempio 2: Range (ROT13 semplice)
        String testo2 = "Ciao Mondo";
        String tradotto2 = StringTranslator.translate(testo2,
                "a-zA-Z",
                "n-za-mN-ZA-M");
        System.out.println("Originale: " + testo2);
        System.out.println("Tradotto 2: " + tradotto2);
        System.out.println("---");

        // Esempio 3: Range (minuscolo -> maiuscolo)
        String testo3 = "Hello World 123!";
        String tradotto3 = StringTranslator.translate(testo3, "a-z", "A-Z");
        System.out.println("Originale: " + testo3);
        System.out.println("Tradotto 3: " + tradotto3);
        System.out.println("---");

        // Esempio 4: Comportamento 'tr' (set2 più corto)
        String testo4 = "abcde";
        String tradotto4 = StringTranslator.translate(testo4, "abcde", "xy");
        System.out.println("Originale: " + testo4);
        System.out.println("Tradotto 4: " + tradotto4);
        System.out.println("---");

         */
    }
}