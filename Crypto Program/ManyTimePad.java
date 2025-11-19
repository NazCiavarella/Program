import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManyTimePad {

    private static final float SCORE_THRESHOLD = 0.7f;

    public static void main(String[] args) {
        List<String> hexCiphertexts = loadHexCiphertexts();
        List<byte[]> ciphertexts = new ArrayList<>();
        for (String hex : hexCiphertexts) {
            ciphertexts.add(hexToBytes(hex));
        }

        int keyLength = findMaxKeyLength(ciphertexts);
        byte[] key = new byte[keyLength];
        boolean[] keyByteFound = new boolean[keyLength];

        statisticalAttack(ciphertexts, key, keyByteFound);
        interactiveDecryptionLoop(ciphertexts, key, keyByteFound);
    }

    private static void statisticalAttack(List<byte[]> ciphertexts, byte[] key, boolean[] keyByteFound) {
        System.out.println("Esecuzione dell'attacco statistico per trovare gli spazi...");
        int keyLength = key.length;

        for (int b = 0; b < keyLength; b++) {
            List<Byte> columnBytes = new ArrayList<>();
            for (byte[] ct : ciphertexts) {
                if (b < ct.length) {
                    columnBytes.add(ct[b]);
                }
            }
            if (columnBytes.size() < 2) continue;

            byte bestKeyByte = 0;
            int maxScore = 0;

            for (byte c1 : columnBytes) {
                byte potentialKey = (byte) (c1 ^ ' ');
                int currentScore = 0;
                for (byte c2 : columnBytes) {
                    byte decryptedByte = (byte) (c2 ^ potentialKey);
                    if (isAsciiLetter(decryptedByte)) {
                        currentScore++;
                    }
                }
                if (currentScore > maxScore) {
                    maxScore = currentScore;
                    bestKeyByte = potentialKey;
                }
            }

            if (maxScore > SCORE_THRESHOLD * columnBytes.size()) {
                key[b] = bestKeyByte;
                keyByteFound[b] = true;
            }
        }
        System.out.println("Attacco statistico completato.");
    }

    private static void interactiveDecryptionLoop(List<byte[]> ciphertexts, byte[] key, boolean[] keyByteFound) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayState(ciphertexts, key, keyByteFound);

            System.out.println("\n> Inserisci un'ipotesi nel formato: <indice_testo> <posizione> <testo_in_chiaro>");
            System.out.println("  Esempio: 0 3 chitarra");
            System.out.println("> Oppure digita 'quit' per uscire.");
            System.out.print("Input: ");
            String input = scanner.nextLine();

            if ("quit".equalsIgnoreCase(input)) {
                break;
            }

            try {
                String[] parts = input.split(" ", 3);
                if (parts.length < 3) throw new IllegalArgumentException("Formato input non valido.");

                int ciphertextIndex = Integer.parseInt(parts[0]);
                int position = Integer.parseInt(parts[1]);
                String guessedText = parts[2];

                applyGuess(ciphertexts.get(ciphertextIndex), guessedText, position, key, keyByteFound);
            } catch (Exception e) {
                System.out.println("--- ERRORE: Input non valido. Riprova. (" + e.getMessage() + ") ---");
            }
        }
        scanner.close();
    }

    private static void applyGuess(byte[] targetCiphertext, String guessedText, int position, byte[] key, boolean[] keyByteFound) {
        byte[] guessedBytes = guessedText.getBytes();
        for (int i = 0; i < guessedBytes.length; i++) {
            int keyPos = position + i;
            if (keyPos < key.length && keyPos < targetCiphertext.length) {
                key[keyPos] = (byte) (targetCiphertext[keyPos] ^ guessedBytes[i]);
                keyByteFound[keyPos] = true;
            }
        }
    }

    private static void displayState(List<byte[]> ciphertexts, byte[] key, boolean[] keyByteFound) {
        System.out.println("\n--- STATO ATTUALE DELLA DECIFRAZIONE ---");

        System.out.println("\n>>> CHIAVE PARZIALE");
        for (int i = 0; i < key.length; i++) {
            if ((i % 32) == 0 && i > 0) System.out.println();
            if (keyByteFound[i]) {
                System.out.printf("%02x ", key[i]);
            } else {
                System.out.print("__ ");
            }
        }
        System.out.println("\n");

        int ctr = 0;
        for (byte[] ct : ciphertexts) {
            // Stampa il testo decifrato
            System.out.printf(">>> TESTO %d: ", ctr++);
            for (int i = 0; i < ct.length; i++) {
                if (i < key.length && keyByteFound[i]) {
                    char decryptedChar = (char) (ct[i] ^ key[i]);
                    System.out.print(Character.isISOControl(decryptedChar) || decryptedChar > 255 ? '.' : decryptedChar);
                } else {
                    System.out.print("_");
                }
            }
            System.out.println(); // Nuova riga dopo il testo

            final String RULER_INDENT = "             "; // Spazi per allinearsi con l'inizio del testo

            // 1. Righello delle unità (01234567890123...)
            StringBuilder onesRuler = new StringBuilder(RULER_INDENT);
            for (int i = 0; i < ct.length; i++) {
                onesRuler.append(i % 10);
            }
            System.out.println(onesRuler);

            // 2. Righello delle decine (0    10   20   ...)
            StringBuilder tensRuler = new StringBuilder(RULER_INDENT);
            for (int i = 0; i < ct.length; i++) {
                if (i % 10 == 0) {
                    String label = String.valueOf(i);
                    tensRuler.append(label);
                    // Aggiunge spazi per spingere la prossima etichetta alla posizione corretta
                    for (int j = 0; j < 10 - label.length(); j++) {
                        tensRuler.append(" ");
                    }
                }
            }
            System.out.println(tensRuler);

            System.out.println(); // Riga vuota per separare i testi
        }
    }

    // --- Metodi di Utilità ---
    private static List<String> loadHexCiphertexts() {
        return List.of(
                "053c77f018698944c7bcb608b9692f88ffafb668c462d84270fe0d0a9c4f3ef929b7773a2dfa117f36916c915f0917e3d94805e9a60e8c73a8ec17f114fd54f8fdb14bc1bbdf17f88fab839ee44ba78e90",
                "0b286bfd16619253c4bab622ac663780ece6ba2fcb6496116cfe4a0191523ef17ba47a3a20f15f6632df669d0a0b56e0db5310f4a40a843da4ad1aea18ea11a4afa757c6b09a0ce2ca",
                "0a326ab31d2f9c54debae40abc6e34c9feaff738d568960c79f34108d44b2bf634e5727f37e04373779a23c8114515f1d44016f4ad0cc520a4f90dea55ee5db8bce242c6ba9a11e2dd83",
                "0b286bfd16619253c4bab622ac663780ece6ba2bc863960b3ceb58069d062bea2fac656e2db81171389123c90d0a06e0db0125f0a6118c30aaad0af01caf5cb5b3ab42caa78b0b",
                "0a326ab31d6ddd42cbbbec04b66e77c9eea5f468c660d91079b30d0a9b486af137e5756f2be6543232df60d2114506f9799844f9ac0d8b36ebfe1ce805fd54f4b0a74ac0f48c17f9dd87",
                "0b286bfd16619253c4bab622ac663780ece6ba2ad262d80575f05f079b0607f929ac773664f75e7c77986fd45f0a15f3d24844edaa068b3aebe910a518ee5dbdb3a14bc1bd9e",
                "0b286bfd16619253c4bab62fb16877c9e1a5ba3bc664960174fa0d0a9d0639f735aa367b2af7597777966c",
                "053c77f018698944c7bcb608b9692f88ffafb668c462d84270fe0d0a9c4f3ef929b7773a2dfa117f36916c915f0917e3d94805e9a60e8c73a8ec17f114fd54f4a8ac458fb79e0cecc08c92dff84ba78e9009b02d676659",
                "053c77f018698944c7bcb608b9692f88ffafb668d768c401745c84499a436aeb34ab793a22fd546038d323ce100b19b0cf4f44d4b702893aaae316a955fa5ff494b645c3bd9e0cf98f94928de7"
        );
    }
    private static byte[] hexToBytes(String s) {
        int len = s.length();
        if (len % 2 != 0) throw new IllegalArgumentException("La stringa esadecimale deve avere una lunghezza pari.");
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    private static int findMaxKeyLength(List<byte[]> ciphertexts) {
        return ciphertexts.stream().mapToInt(ct -> ct.length).max().orElse(0);
    }
    private static boolean isAsciiLetter(byte b) {
        return (b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z');
    }
}