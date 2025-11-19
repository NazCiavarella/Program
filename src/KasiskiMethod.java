import java.util.*;
import java.util.stream.Collectors;

public class KasiskiMethod {

    private String text;
    private int minLength;
    private int maxLength;

    public KasiskiMethod(String text, int minLength, int maxLength) {
        this.text = text.toUpperCase();
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public void generateTable() {
        Map<String, List<Integer>> patternPositions = new HashMap<>();

        // Trova tutti i pattern e le loro posizioni
        for (int len = minLength; len <= maxLength; len++) {
            for (int i = 0; i <= text.length() - len; i++) {
                String sub = text.substring(i, i + len);
                patternPositions.computeIfAbsent(sub, k -> new ArrayList<>()).add(i);
            }
        }

        // Filtra solo i pattern ripetuti
        Map<String, List<Integer>> repeatedPatterns = patternPositions.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Ordina i pattern per lunghezza decrescente
        List<Map.Entry<String, List<Integer>>> sortedPatterns = repeatedPatterns.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getKey().length(), e1.getKey().length()))
                .collect(Collectors.toList());

        // Calcola tutte le distanze e raccoglie i divisori
        Set<Integer> divisorsSet = new TreeSet<>();
        for (Map.Entry<String, List<Integer>> entry : sortedPatterns) {
            List<Integer> positions = entry.getValue();
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int dist = positions.get(j) - positions.get(i);
                    for (int d = 2; d < dist; d++) {
                        if (dist % d == 0) divisorsSet.add(d);
                    }
                }
            }
        }

        List<Integer> keyLengths = new ArrayList<>(divisorsSet);

        // Header
        System.out.print(String.format("%-10s %-6s %-6s %-8s", "Pattern", "Pos1", "Pos2", "Dist"));
        for (int k : keyLengths) {
            System.out.print(String.format("%-4d", k));
        }
        System.out.println();

        // Conteggio occorrenze
        Map<Integer, Integer> occurrences = new HashMap<>();
        for (int k : keyLengths) occurrences.put(k, 0);

        // Genera righe ordinate
        for (Map.Entry<String, List<Integer>> entry : sortedPatterns) {
            String pattern = entry.getKey();
            List<Integer> positions = entry.getValue();

            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int pos1 = positions.get(i);
                    int pos2 = positions.get(j);
                    int dist = pos2 - pos1;

                    System.out.print(String.format("%-10s %-6d %-6d %-8d", pattern, pos1, pos2, dist));

                    for (int k : keyLengths) {
                        if (dist % k == 0) {
                            System.out.print("X   ");
                            occurrences.put(k, occurrences.get(k) + 1);
                        } else {
                            System.out.print("    ");
                        }
                    }
                    System.out.println();
                }
            }
        }

        // Riga finale
        System.out.print(String.format("%-10s %-6s %-6s %-8s", "", "", "", "Occ"));
        for (int k : keyLengths) {
            System.out.print(String.format("%-4d", occurrences.get(k)));
        }
        System.out.println();
    }
}