import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int key;
        String vigenereKey;
        int columns;
        int rows;
        double ic;   //indice di coincidenza per metodo Friedman
        String plainText;
        String cypherText;

        Scanner scanner = new Scanner(System.in);



        /*

        // Metodo Friedman
        System.out.println("Inserisci testo:");
        cypherText = scanner.nextLine();
        System.out.println("Inserisci l'indice di coincidenza della lingua:");
        ic = scanner.nextDouble();
        scanner.nextLine();

        FriedmanMethod obj = new FriedmanMethod();
        obj.analizzaConFriedman(cypherText,18,ic);

         */






        /*
        // Metodo Kasiski
        System.out.println("Inserisci il testo:");
        plainText = scanner.nextLine();

        KasiskiMethod obj2 = new KasiskiMethod(plainText,2,plainText.length());
        obj2.generateTable();

         */



/*

        // Analisi delle frequenze
        System.out.println("Inserisci il testo:");
        plainText = scanner.nextLine();

        FrequencyAnalyses analyses = new FrequencyAnalyses(plainText);
        analyses.freqAnalyses();


*/





/*

        // Cifrario di Vigenere
        System.out.println("Inserisci il testo:");
        plainText = scanner.nextLine();
        System.out.println("inserisci la chiave");
        vigenereKey = scanner.nextLine();

        VigenereCypher obj1 = new VigenereCypher(vigenereKey,plainText);
        obj1.decripter(vigenereKey,plainText);

 */



/*
        VigenereCypher obj = new VigenereCypher(vigenereKey, plainText);
        obj.cripter(vigenereKey, plainText);

 */





/*
        // Cifrario Rail Fence


        System.out.println("inserisci testo:");
        plainText = scanner.nextLine();
        System.out.println("inserisci righe:");
        rows = scanner.nextInt();
        scanner.nextLine();
        columns = plainText.length();



        RailFenceCypher obj2 = new RailFenceCypher(rows, columns, plainText);
        obj2.matrixInsertion(rows, columns, plainText);


        System.out.println("inserisci testo:");
        cypherText = scanner.nextLine();
        System.out.println(cypherText.length());
        System.out.println("inserisci righe:");
        rows = scanner.nextInt();
        scanner.nextLine();
        columns = cypherText.length();

        RailFenceCypher obj3 = new RailFenceCypher(rows, columns, cypherText);
        obj3.matrixExtraction(rows, columns, cypherText);

*/


        /*
        //Cifrario a trsposizione colonnare

        System.out.println("inserisci testo:");
        plainText = scanner.nextLine();
        System.out.println("inserisci righe:");
        rows = scanner.nextInt();
        System.out.println("Inserisci colonne:");
        columns = scanner.nextInt();
        scanner.nextLine();


        ColumnarTransposition obj = new ColumnarTransposition(columns, rows, plainText);
        obj.matrixInsertion(plainText,rows,columns);



        System.out.println("inserisci testo:");
        cypherText = scanner.nextLine();
        System.out.println("inserisci righe:");
        rows = scanner.nextInt();
        System.out.println("Inserisci colonne:");
        columns = scanner.nextInt();
        scanner.nextLine();

        ColumnarTransposition obj1 = new ColumnarTransposition(columns, rows, cypherText);
        obj1.matrixExtraction(cypherText,rows,columns);

         */


        /*
        //Cifrario di Cesare applicazione e funzionamento

        System.out.println("inserisci Messaggio in chiaro da Criptare:");
        plainText = scanner.nextLine();
        System.out.println("inserisci Chiave per la criptazione:");
        key = scanner.nextInt();
        scanner.nextLine();

        CesarCypher Text = new CesarCypher(key, plainText);
        System.out.println(Text.Permutation(key, plainText));

         */


/*
        System.out.println("inserisci Messaggio cifrato da Decriptare:");
        cypherText = scanner.nextLine();
        System.out.println("inserisci Chiave per la decriptazione:");
        key = scanner.nextInt();
        scanner.nextLine();

        CesarCypher plainText1 = new CesarCypher(key, cypherText);
        System.out.println(plainText1.DecypherCesar(key, cypherText));

 */
    }


}
