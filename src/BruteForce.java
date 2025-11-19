import java.util.Scanner;

public class BruteForce {
//try
    String code;

    public BruteForce(String cyphertext){
        this.code = cyphertext;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("inserisci testo cifrato:");

        String encryptedText =scanner.nextLine() ;
        BruteForce obj = new BruteForce(encryptedText);

        // Brute-force per tutti gli shift da 1 a 25
        for (int shift = 1; shift <= 25; shift++) {
            String decrypted = decrypt(encryptedText, shift);
            System.out.println("Shift " + shift + ": " + decrypted);

            // Euristicamente riconoscibile come testo in chiaro
            if (isReadable(decrypted)) {
                System.out.println("✅ Possibile match trovato con shift = " + shift + ": " + decrypted);
            }
        }
    }

    // Decifra usando il cifrario di Cesare con uno shift specifico
    public static String decrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int originalAlphabetPos = character - base;
                int newPos = (originalAlphabetPos - shift + 26) % 26;
                result.append((char) (base + newPos));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    // Euristiche per determinare se il testo appare "leggibile"
    public static boolean isReadable(String text) {
        // Per semplificare, vediamo se contiene parole comuni inglesi
        return text.toLowerCase().matches(".*\\b(hello|world|the|is|this|you|we|they|are|were|was|been|have|has|had|an|at|on|of|in|to|be|it|as|by|do|go|no|ok|up|so|my|me|not|can|get|see|say|may|did|new|now|man|men|boy|girl|day|way|try|put|run|big|old|one|two|yes)\\b.*");
    }
}