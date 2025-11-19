public class RailFenceCypher {

    int rows;
    String plainText;
    int columns;

    RailFenceCypher(int r, int c, String s){
        this.rows = r;
        this.columns = c;
        this.plainText = s;
    }

    void matrixInsertion(int r, int c, String s){
        if(s.length() > r*c){
            System.out.println("matrice piccola per ospitare tale testo");
            System.exit(2);
        }
        char[][] matrix = new char[r][c];
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                matrix[i][j] = ' ';
            }
        }
        int index = 0;
        int k = 0;
        boolean direction = false;
        for (int i = 0; i < c; i++) {
            if (index == 0 || index == r - 1){
                direction = !direction;
            }
            matrix[index][i] = s.charAt(k);
            k++;
            index += direction ? 1 : -1;
        }

        // Stampa della matrice per verifica
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] != ' '){
                    result.append(matrix[i][j]);
                }
            }
        }
        System.out.println(result);
    }

    void matrixExtraction(int r, int c,String s){
        char[][] matrix = new char[r][c];
        int index = 0;
        int k = 0;
        boolean direction = false;
        for (int i = 0; i < c; i++) {
            if (index == 0 || index == r - 1){
                direction = !direction;
            }
            matrix[index][i] = ' ';
            index += direction ? 1 : -1;
        }
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (matrix[i][j] == ' ' && k < c){
                    matrix[i][j] = s.charAt(k);
                    k++;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        direction = false;
        index = 0;
        for(int i = 0; i < c; i++){
            if(index == 0 || index == r - 1){
                direction = !direction;
            }
            result.append(matrix[index][i]);
            index += direction ? 1 : -1;
        }
        System.out.println(result);
    }
}
