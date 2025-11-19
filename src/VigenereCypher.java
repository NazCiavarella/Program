import java.util.ArrayList;

public class VigenereCypher {

    String key;
    String plainText;

    VigenereCypher(String k, String s){
        this.key = k;
        this.plainText = s;
    }

    void cripter(String k, String s){
        // creo un arraylist per poter contenere il messaggio da cifrare diviso in parole lunghe quanto la chiave ci cifratura
        int index = 0;
        ArrayList<String> subString = new ArrayList<>();
        while (index < s.length()){
            if (index + k.length() < s.length()){
                subString.add(s.substring(index, index + k.length()));
                index += k.length();
            } else{
                subString.add(s.substring(index));
                break;
            }
        }
        /*
        //prova di stampa
        System.out.println(subString);
         */
        // creo un array per poter inserire le lettere che compongono la chiave per poi richiamare il cifrario di Cesare
        char[] keyWord = new char[k.length()];
        for (int i = 0; i < keyWord.length; i++){
            keyWord[i] = k.charAt(i);
        }
        /*
        // prova di stampa
        for (int i = 0; i < keyWord.length; i++){
            System.out.println(keyWord[i]);
        }

         */
        for (int i = 0; i < subString.size(); i++){
            for (int j = 0; j < k.length(); j++ ){
                // effettuo un controllo nel caso in cui l'ultima sezione sia più piccola della chiave e mi fermo prima
                if(j > subString.get(i).length()-1){
                    break;
                }
                char base = Character.isUpperCase(keyWord[j]) ? 'A' : 'a';     //mi serve per calcolare le classi di resto
                int key = keyWord[j] - base;
                String sNew = subString.get(i);
                CesarCypher carattereCriptato = new CesarCypher(key, sNew);
                char carattereStampato = carattereCriptato.Permutation(key, sNew).charAt(j);
                System.out.print(carattereStampato);
            }
        }

    }

    void decripter(String k, String s){
        // creo un arraylist per poter contenere il messaggio da cifrare diviso in parole lunghe quanto la chiave ci cifratura
        int index = 0;
        ArrayList<String> subString = new ArrayList<>();
        while (index < s.length()){
            if (index + k.length() < s.length()){
                subString.add(s.substring(index, index + k.length()));
                index += k.length();
            } else{
                subString.add(s.substring(index));
                break;
            }
        }
        /*
        //prova di stampa
        System.out.println(subString);
         */
        // creo un array per poter inserire le lettere che compongono la chiave per poi richiamare il cifrario di Cesare
        char[] keyWord = new char[k.length()];
        for (int i = 0; i < keyWord.length; i++){
            keyWord[i] = k.charAt(i);
        }
        /*
        // prova di stampa
        for (int i = 0; i < keyWord.length; i++){
            System.out.println(keyWord[i]);
        }

         */
        for (int i = 0; i < subString.size(); i++){
            for (int j = 0; j < k.length(); j++ ){
                // effettuo un controllo nel caso in cui l'ultima sezione sia più piccola della chiave e mi fermo prima
                if(j > subString.get(i).length()-1){
                    break;
                }
                char base = Character.isUpperCase(keyWord[j]) ? 'A' : 'a';     //mi serve per calcolare le classi di resto
                int key = keyWord[j] - base;
                String sNew = subString.get(i);
                CesarCypher carattereCriptato = new CesarCypher(key, sNew);
                char carattereStampato = carattereCriptato.DecypherCesar(key, sNew).charAt(j);
                System.out.print(carattereStampato);
            }
        }

    }

}
