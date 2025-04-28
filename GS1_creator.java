import java.util.Scanner;

public class GS1_creator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        int sum = 0;
        int cifraDiControllo = 0;

        System.out.println("Insert indicator (the first number of GTIN-14) from 1 to 8: ");
        int indicator = scanner.nextInt();
        scanner.nextLine();
        if (indicator < 1 || indicator > 8) {
            System.out.println("Invalid indicator");
            System.exit(0);
        }


        System.out.println("Insert code EAN with 13 digits: ");
        String code = scanner.nextLine();
        int [] GS1 = new int[14];

        GS1[0] = indicator;
        for (int i = 1; i < 13; i++) {
            GS1[i] = code.charAt(i-1) - '0';
        }

        for (int i = 0; i < 13; i++) {
            System.out.print(GS1[i]);
        }

        System.out.println();


       for (int i = 1; i < 13; i++) {
           if (i % 2 != 0) {
               sum = sum + GS1[i];
           } else {
               sum = sum + GS1[i] * 3;
           }
       }

       System.out.println("Sum: " + sum);

        int i = 0;
        while(sum > i){
                cifraDiControllo = i + 10 - sum;
                i+=10;
            if(sum == i){
                cifraDiControllo = 0;
            }
        }

        GS1[GS1.length-1] = cifraDiControllo;

        System.out.println("cifraDiControllo: " + cifraDiControllo);

        System.out.println();

        System.out.print("GTIN-14: ");
        System.out.print(GS1_creator.printArray(GS1));
        System.out.println();

    }

    public static String printArray(int[] array) {
        for (int i = 0; i < 14; i++) {
            System.out.print(array[i]);
        }
        return " ";
    }
}
