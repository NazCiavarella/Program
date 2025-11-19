public class ColumnarTransposition {

    int columns;
    int rows;
    String text;
    int k = 0;

    ColumnarTransposition(int r, int c, String s){
        this.columns = c;
        this.rows = r;
        this.text = s;
    }

    void matrixInsertion(String s,int r, int c){
        if(s.length() > r*c){
            System.out.println("matrice piccola per ospitare tale testo");
            System.exit(2);
        }
        char[][] matrix = new char[r][c];
        // In questo modo posso gestire anche matrici con diverso numero di righe e colonne
        // Inserisco le lettere per riga
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if (k<s.length()){
                    matrix[i][j] = s.charAt(k);
                    k++;
                } else{
                    matrix[i][j] = ' ';
                }
            }
        }
        // In questo modo stampo la matrice per colonne
        for(int i = 0; i < matrix[0].length; i++){
            for(int j = 0; j < matrix.length; j++){
                System.out.print(matrix[j][i]);
            }
        }
        System.out.println();
    }

    void matrixExtraction(String s,int r, int c){
        char[][] matrix = new char[c][r];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if (k<s.length()){
                    matrix[i][j] = s.charAt(k);
                    k++;
                } else{
                    matrix[i][j] = ' ';
                }
            }
        }
        for(int i = 0; i < matrix[0].length; i++){
            for(int j = 0; j < matrix.length; j++){
                System.out.print(matrix[j][i]);
            }
        }
        System.out.println();
    }

}
