import java.util.*;

public class FrequencyAnalyses {

    String text;

    public FrequencyAnalyses(String t) {
        this.text = t;
    }

    void freqAnalyses() {
        Map<Character, Long> frequencyMap = new HashMap<>();
        // Conta le occorrenze di ogni carattere
        for (char c : text.toLowerCase().toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0L) + 1);
        }
        long totalChars = text.toLowerCase().length();
        // Ordina la mappa per valore (frequenza) in ordine decrescente
        List<Map.Entry<Character, Long>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()));
        // Stampa i risultati
        for (Map.Entry<Character, Long> entry : sortedEntries) {
            double perc = (entry.getValue() * 100.0) / totalChars;
            System.out.printf("%c : %.4f%%\n", entry.getKey(), perc);
        }
    }
}

