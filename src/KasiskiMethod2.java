import java.util.*;

public class KasiskiMethod2 {

    String text;
    int minLength;
    int maxLength;

    public KasiskiMethod2(String t, int min, int max){
        this.text = t;
        this.minLength = min;
        this.maxLength = max;
    }

    public void findRepeatedPatterns() {
        Map<String, List<Integer>> patternPositions = new HashMap<>();
        text = text.toLowerCase(); // Case-insensitive

        for (int len = minLength; len <= maxLength; len++) {
            for (int i = 0; i <= text.length() - len; i++) {
                String sub = text.substring(i, i + len);
                patternPositions.computeIfAbsent(sub, k -> new ArrayList<>()).add(i);
            }
        }

        // Filtra solo i pattern che si ripetono
        List<String> repeatedPatterns = patternPositions.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .sorted((s1, s2) -> Integer.compare(s2.length(), s1.length())) // Ordina per lunghezza decrescente
                .toList();

        List<String> finalPatterns = new ArrayList<>();

        for (String pattern : repeatedPatterns) {
            boolean isSubpattern = false;
            for (String accepted : finalPatterns) {
                if (accepted.contains(pattern)) {
                    isSubpattern = true;
                    break;
                }
            }
            if (!isSubpattern) {
                finalPatterns.add(pattern);
            }
        }

        // Stampa i pattern finali con le loro posizioni e tutte le distanze
        for (String pattern : finalPatterns) {
            List<Integer> positions = patternPositions.get(pattern);
            List<Integer> allDistances = new ArrayList<>();

            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    allDistances.add(positions.get(j) - positions.get(i));
                }
            }

            System.out.println("Pattern: \"" + pattern +
                    " - Posizioni: " + positions +
                    " - Distanze: " + allDistances);

        }
    }
}
