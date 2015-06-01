import java.io.*;

public class NaiveBayes {
    public static void main(String [] args) {

        // The name of the file to open.
        String trainFile = args[0];

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(trainFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }    

            // Always close files.
            bufferedReader.close();            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                trainFile + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + trainFile + "'");                   
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
}