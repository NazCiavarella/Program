
import java.io.*;

 public class SystemDataCollector {
     public static void main(String[] args) {
         String[] commands = {
                 "getmac /v /fo list",
                 "wmic computersystem get Manufacturer, Model, Name, Domain, UserName, TotalPhysicalMemory, SystemType /format:list",
                 "wmic bios get SerialNumber",
                 "wmic MEMORYCHIP get BankLabel, Capacity, DeviceLocator, MemoryType, TypeDetail, Speed /format:list",
                 "wmic os get Caption, Version, OSArchitecture /format:list",
                 "echo -------------------------------------------------------------------------------------------------"
         };

         String fileName = "raccoltaDati.txt";

         // Usa la directory corrente dell'applicazione
         String outputPath = System.getProperty("user.dir") + File.separator + fileName;

         // Alternativamente, puoi impostare un percorso personalizzato
         // String outputPath = "C:\\cartella_personale\\" + fileName;

         try {
             File file = new File(outputPath);

             // Crea il file
             if (file.createNewFile()) {
                 System.out.println("File creato: " + file.getAbsolutePath());
             } else {
                 System.out.println("Il file esiste già: " + file.getAbsolutePath());
             }

             // Scrive nel file
             try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                 writer.write("Contenuto di esempio nel file di testo.");
                 System.out.println("Dati scritti nel file con successo.");
             }
         } catch (IOException e) {
             System.out.println("Si è verificato un errore: " + e.getMessage());
         }
         //da modificare in modo da trovare un path assoluto

         try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath, true))) {
             for (String command : commands) {
                 writer.println(command + " :");
                 executeCommand(command, writer);
                 writer.println();
             }
             System.out.println("Raccolta dati completata e salvata in " + outputPath);
         } catch (IOException e) {
             System.err.println("Errore durante la scrittura dei dati: " + e.getMessage());
         }
     }

     private static void executeCommand(String command, PrintWriter writer) {
         try {
             Process process = new ProcessBuilder("cmd.exe", "/c", command).start();
             try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                 String line;
                 while ((line = reader.readLine()) != null) {
                     writer.println(line);
                 }
             }
         } catch (IOException e) {
             writer.println("Errore durante l'esecuzione del comando: " + e.getMessage());
         }
     }
 }
