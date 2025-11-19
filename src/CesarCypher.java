public class CesarCypher {

    int k;
    String s;

    CesarCypher(int k, String s) {
        this.k = k;
        this.s = s;
    }

    String Permutation(int k, String s) {
        StringBuilder newS = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char shifted = (char) ((c - base + k) % 26 + base);
                newS.append(shifted);
            } else {
                newS.append(c);
            }
        }

        return newS.toString();
    }

    String DecypherCesar(int k, String s) {
        return Permutation(26 - (k % 26), s);
    }
}