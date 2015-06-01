import java.io.*;
import java.util.Arrays;
import java.lang.*;


public class NaiveBayes {

    public static int numOfInputVariables = 0;
    public static int numOfDataVectors = 0;
    public static int[][] dataFromFile;

    public static void main(String [] args) {
       // System.out.println(Arrays.deepToString(dataFromFile));

        if (args.length!= 0) {
            createMLE(args[0]);
        }
        


    }

    private static void createMLE(String nameOfFile){
        formatDataFromFile(nameOfFile);
    }

    private static void formatDataFromFile(String nameOfFile){
        // The name of the file to open.
        String fileName = nameOfFile;

        // This will reference one line at a time
        String line = null;

        /*Code below is taken from: https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html */
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            for (int i = 0; i <2;i++){//for loops twice to get the first two values in file
                line = bufferedReader.readLine();
                if (i == 0){
                    numOfInputVariables = Integer.parseInt(line);
                    System.out.println(numOfInputVariables);
                }
                if (i == 1){
                    numOfDataVectors = Integer.parseInt(line);
                    System.out.println(numOfDataVectors);
                }
            }

            dataFromFile = new int[numOfDataVectors][numOfInputVariables+1]; //+1 because we want room for Y var

            int numOfDigits = 0;
            for (int a = 0; a < numOfDataVectors; a++){
                line = bufferedReader.readLine();
                System.out.println(line);
                for (int i = 0;i <line.length();i++) {
                    if (Character.isDigit(line.charAt(i))){
                        dataFromFile[a][numOfDigits] = Character.getNumericValue(line.charAt(i));
                        numOfDigits++;
                    }
                    if (i == line.length()-1){
                        numOfDigits = 0;
                    }
                }

            }    
            System.out.println(Arrays.deepToString(dataFromFile));
            // Always close files.
            bufferedReader.close();            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                   
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        /*Cited Code ends here*/

    }


}