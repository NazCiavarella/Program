import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class SubstitutionCipherSolverInteractive {

    // Dizionario esteso
    private static final Set<String> DICTIONARY = new HashSet<>(Arrays.asList(
            // Parole ad alta frequenza
            "ESSERE", "AVERE", "POTERE", "VOLERE", "DOVERE", "FARE", "ANDARE", "DIRE", "VEDERE", "DARE",
            "STARE", "VENIRE", "GUARDARE", "METTERE", "LASCIARE", "SENTIRE", "TROVARE", "PARLARE", "PASSARE",
            "TENERE", "TORNARE", "CAPIRE", "PRENDERE", "MANGIARE", "IL", "LA", "L", "I", "GLI", "LE", "UN",
            "UNA", "UNO", "DI", "A", "DA", "IN", "CON", "SU", "PER", "TRA", "FRA", "IO", "TU", "LUI", "LEI",
            "NOI", "VOI", "LORO", "MI", "TI", "CI", "VI", "SI", "CHE", "CHI", "QUALE", "QUANTO", "QUESTO",
            "QUELLO", "E", "O", "MA", "PERCHÉ", "SE", "NON", "ANCHE", "PIÙ", "MOLTO", "POCO", "COSÌ", "COME",
            "QUANDO", "DOVE", "QUI", "LÌ", "ORA", "POI", "GIÀ", "SEMPRE", "MAI", "SUBITO", "DUNQUE", "ALLORA",
            "COSA", "ANNO", "UOMO", "GIORNO", "VOLTA", "CASA", "PARTE", "VITA", "TEMPO", "DONNA", "MANO",
            "OCCHIO", "ORA", "SIGNORE", "PAESE", "MODO", "MONDO", "PAROLA", "PADRE", "PUNTO", "LAVORO",
            "STATO", "CASO", "CITTÀ", "GUERRA", "STRADA", "FIGLIO", "NOTTE", "AMICO", "FATTO", "GENTE",
            "AMORE", "STORIA", "FORZA", "TESTA",
            // Parole di media/bassa frequenza
            "SOGGETTO", "SENSO", "INTERESSE", "RAGAZZO", "RISPOSTA", "TESTO", "PRINCIPIO", "AZIONE",
            "PRODOTTO", "GIUDIZIO", "CONTRO", "RISCHIO", "SISTEMA", "CAMPO", "DIRETTORE", "ELEMENTO",
            "FIGURA", "LIBRERIA", "OSPEDALE", "VANTAGGIO", "SVILUPPO", "TECNICA", "SOLUZIONE", "NATURA",
            "RICERCA", "SUCCESSO", "UNIVERSO", "VERITÀ", "ESPERTO", "CHIESA", "ALBERO", "SERVIZIO",
            "LIBERTÀ", "PIETRA", "CARNE", "SOGNO", "FIORE", "INIZIO", "DESTINO", "DIETRO", "ACCORDO",
            "PASSATO", "FUTURO", "POESIA", "REALE", "COMPITO", "MESE", "SETTIMANA",
            "ALTRO", "NUOVO", "PRIMO", "CERTO", "GRANDE", "PICCOLO", "BUONO", "BELLO", "VECCHIO",
            "STESSO", "TROPPO", "TANTO", "ASSOLUTO", "CATTIVO", "FINALE", "INSIEME", "QUASI", "FORSE",
            "ALCUNO", "NESSUNO", "CIASCUNO", "SECONDO", "PRONTO", "ULTIMO", "LONTANO", "VICINO",
            "APPENA", "ENTRO", "PURE", "ECC", "SOLO", "ANZI", "AFFATTO", "DAVVERO", "INFINE",
            "SPESSO", "TALVOLTA", "OVUNQUE", "PERÒ", "INFATTI", "USCIRE", "COSTRUIRE", "ACCETTARE",
            "AVVENIRE", "CREARE", "CAMBIARE", "ATTENDERE", "SPERARE", "CENARE", "COMPRARE", "ENTRARE",
            "IMPARARE", "SPARARE", "REAGIRE", "PREFERIRE", "REGNARE", "SCAVARE", "DIPINGERE", "PROVARE"
    ));

    // Mappatura: Carattere Cifrato (K) -> Carattere in Chiaro (P)
    private static Map<Character, Character> keyMap = new HashMap<>();

    // Testo cifrato di esempio (una frase semplice). Verranno mantenute solo le lettere.
    private static String CIPHERTEXT_RAW;
    private static String CIPHERTEXT_NORMALIZED; // Solo lettere maiuscole
    private static String CIPHERTEXT_STRUCTURE;  // Testo originale con i separatori

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci testo cifrato:");
        CIPHERTEXT_RAW = scanner.nextLine();
        // Normalizza il testo cifrato mantenendo la struttura per la visualizzazione
        CIPHERTEXT_NORMALIZED = CIPHERTEXT_RAW.toUpperCase().replaceAll("[^A-Z]", "");
        CIPHERTEXT_STRUCTURE = CIPHERTEXT_RAW.toUpperCase();

        interactiveDecryptionLoop();
    }
    private static String getWordPattern(String word) {
        Map<Character, Integer> charToId = new HashMap<>();
        StringBuilder pattern = new StringBuilder();
        int nextId = 0;
        for (char c : word.toCharArray()) {
            if (!charToId.containsKey(c)) {
                charToId.put(c, nextId++);
            }
            if (pattern.length() > 0) {
                pattern.append("-");
            }
            pattern.append(charToId.get(c));
        }
        return pattern.toString();
    }

    //Trova le parole del dizionario che corrispondono allo schema della parola cifrata.
    private static List<String> findPossiblePlainwords(String encryptedWord) {
        if (encryptedWord == null || encryptedWord.isEmpty()) {
            return List.of();
        }
        encryptedWord = encryptedWord.toUpperCase();
        int length = encryptedWord.length();
        String encryptedPattern = getWordPattern(encryptedWord);

        return DICTIONARY.stream()
                .filter(dictWord -> dictWord.length() == length)
                .filter(dictWord -> getWordPattern(dictWord).equals(encryptedPattern))
                .collect(Collectors.toList());
    }

    //Ciclo Interattivo

    private static void interactiveDecryptionLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayState();

            System.out.println("\n> Comandi disponibili:");
            System.out.println("  1. 'suggest <parola_cifrata>' per vedere le possibili parole in chiaro.");
            System.out.println("     Esempio: suggest VTAI");
            System.out.println("  2. 'guess <parola_cifrata> <parola_in_chiaro>' per applicare una sostituzione.");
            System.out.println("     Esempio: guess VTAI ANNA");
            System.out.println("  3. 'quit' per uscire.");
            System.out.print("Input: ");
            String input = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(input)) {
                break;
            }

            try {
                processInput(input);
            } catch (Exception e) {
                System.out.println("--- ERRORE: Input non valido. Riprova. (" + e.getMessage() + ") ---");
            }
        }
        scanner.close();
    }

    private static void processInput(String input) {
        String[] parts = input.split(" ");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "suggest":
                if (parts.length < 2) throw new IllegalArgumentException("Manca la parola cifrata.");
                String cipherWord = parts[1].toUpperCase();
                List<String> suggestions = findPossiblePlainwords(cipherWord);
                System.out.println("\n--- SUGGERIMENTI PER '" + cipherWord + "' ---");
                if (suggestions.isEmpty()) {
                    System.out.println("Nessuna corrispondenza trovata nel dizionario.");
                } else {
                    System.out.println(suggestions);
                }
                break;
            case "guess":
                if (parts.length < 3) throw new IllegalArgumentException("Manca la parola cifrata o la parola in chiaro.");
                String cipher = parts[1].toUpperCase();
                String plain = parts[2].toUpperCase();
                applyGuess(cipher, plain);
                break;
            default:
                throw new IllegalArgumentException("Comando sconosciuto.");
        }
    }

    private static void applyGuess(String cipherWord, String plainWord) {
        if (cipherWord.length() != plainWord.length()) {
            throw new IllegalArgumentException("Le lunghezze delle parole devono corrispondere.");
        }

        // Manteniamo il controllo sullo schema di ripetizione, è cruciale per la logica.
        if (!getWordPattern(cipherWord).equals(getWordPattern(plainWord))) {
            throw new IllegalArgumentException("Le parole non corrispondono nello schema di ripetizione (es. doppie).");
        }

        // Mappa inversa temporanea per rilevare i conflitti
        Map<Character, Character> inverseKeyMap = new HashMap<>();
        keyMap.forEach((k, v) -> inverseKeyMap.put(v, k));

        for (int i = 0; i < cipherWord.length(); i++) {
            char cipherChar = cipherWord.charAt(i);
            char plainChar = plainWord.charAt(i);

            // 1. Controllo di Sovrascrittura (Cifrato -> Chiaro)
            if (keyMap.containsKey(cipherChar) && keyMap.get(cipherChar) != plainChar) {
                System.out.printf("AVVISO (Sovrascrittura): '%c' era mappato su '%c'. Nuova mappa: '%c' -> '%c'.\n",
                        cipherChar, keyMap.get(cipherChar), cipherChar, plainChar);
            }

            // 2. Controllo Conflitto Inverso (Biunivocità) - SOLO AVVISO
            // Cerca se il carattere in chiaro è già mappato da un *altro* carattere cifrato
            if (inverseKeyMap.containsKey(plainChar) && inverseKeyMap.get(plainChar) != cipherChar) {
                char existingCipher = inverseKeyMap.get(plainChar);
                System.out.printf("⚠️ AVVISO GRAVE (Conflitto Biunivoco): Il chiaro '%c' è già mappato da '%c'. La nuova ipotesi '%c' -> '%c' ROMPE la coerenza. Il mapping precedente NON è stato rimosso.\n",
                        plainChar, existingCipher, cipherChar, plainChar);
                // Non eseguiamo alcuna rimozione qui. Entrambi i mapping (il vecchio e il nuovo) rimangono attivi.
                // Ciò significa che il testo decifrato visualizzerà il risultato dell'ultimo mapping inserito per la lettera cifrata.
            }

            // Applicazione della Nuova Mappatura (Sovrascrittura diretta)
            // Se 'X' era 'C', e ora lo mappiamo a 'T', il mapping 'X' -> 'C' viene semplicemente perso.
            keyMap.put(cipherChar, plainChar);
        }

        System.out.println("Mappatura applicata con successo. Tutte le nuove ipotesi sono state mantenute. Controlla gli AVVISI GRAVI.");
    }
    private static void displayState() {
        System.out.println("\n--- STATO ATTUALE DELLA DECIFRAZIONE ---");

        // 1. Chiave Parziale (Mappatura)
        System.out.println("\n>>> CHIAVE PARZIALE (Cifrato -> Chiaro)");
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabet.length(); i++) {
            char cipherChar = alphabet.charAt(i);
            System.out.printf("%c -> %c | ", cipherChar, keyMap.getOrDefault(cipherChar, '_'));
            if ((i + 1) % 6 == 0) System.out.println();
        }
        System.out.println("\n");

        // 2. Testo Decifrato Parziale
        System.out.println(">>> TESTO CIPHERTEXT (con separatori e punteggiatura originali)");

        StringBuilder decryptedText = new StringBuilder();
        for (char c : CIPHERTEXT_STRUCTURE.toCharArray()) {
            if (Character.isLetter(c)) {
                decryptedText.append(keyMap.getOrDefault(c, '_'));
            } else {
                decryptedText.append(c);
            }
        }

        System.out.println(CIPHERTEXT_STRUCTURE);
        System.out.println(decryptedText);
    }
}