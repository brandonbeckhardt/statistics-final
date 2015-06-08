import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.*;

public class LogisticRegression{ 
	public static int numOfInputVariables = 0;
    public static int numOfDataVectors = 0;
    public static int[][] dataFromFile;
    public static HashMap<Integer, Integer> actualClassifications;
    public static int[] actualValues;


    // X0 is always 1.  Xm+1 is Y
    public static int[][] createInstanceVectors(int[][] data, int m, Boolean test){
        // +2 because we want the Instance[0] = 1, and Instance[M+1] equal to Y
        int[][] instances = new int[numOfDataVectors][m + 2];
        int[] values = new int[numOfDataVectors];
        HashMap<Integer, Integer> classifications = new HashMap<Integer, Integer>();

        for(int i=0; i < numOfDataVectors; i++){
            instances[i][0] = 1;
            for(int k = 1; k <= m+1; k++){   
                instances[i][k] = data[i][k-1];

                if(test && k == m+1) {
                    values[i] = instances[i][k];
                    if(classifications.containsKey(instances[i][k])){
                        int count = classifications.get(instances[i][k]);
                        classifications.put(instances[i][k], count+1);
                    } else {
                        classifications.put(instances[i][k], 1);
                    }
                }
            }
        }
        if(test) {
            actualValues = values;
            actualClassifications = classifications;
        }
        return instances;
    }

    public static double[] calculateZ(double[] beta,int[][] instances,int m){
     double[] z = new double[numOfDataVectors];
     for(int i = 0; i < numOfDataVectors; i++){
        int[] instance = instances[i];
        for(int j = 0; j <= m; j++)
            z[i] += beta[j] * instance[j];
     }
     return z;
    }

    public static double[] train(String file){
        if (file != null) formatDataFromFile(file);
        double n = 0.0001;
        int m = numOfInputVariables;
        //Array of arrays containing instances.  X0 is always 1.  Xm+1 is Y
        int instances[][] = createInstanceVectors(dataFromFile,m, false);
        //Array of betas. B0 is alpha.  All initialized to 0
        double[] beta = new double[m+1];

        int epochs = 10000;
        // for each pass over dataset
        for(int e = 0; e < epochs; e++){
            double[] gradient = new double[m+1];

            double[] zs = calculateZ(beta,instances,m);
           //calculate batch gradient vector for each gradient
            for(int kthInput=0; kthInput <= m; kthInput++){
                //iterate through all training instances in data
                for(int instance = 0; instance < numOfDataVectors; instance++){
                    int xInstance = instances[instance][kthInput];
                    int yInstance = instances[instance][m+1]; 
                    double eToZ = Math.exp(-zs[instance]);
                    gradient[kthInput] += xInstance*(yInstance-(1/(1+eToZ)));
                }
            }       
        //update all bk
            for(int k = 0; k <= m; k++) beta[k] += n * gradient[k];
        }
        return beta;
    }

    public static HashMap<Integer, Integer> test(String file, double[] beta){
        if (file != null) formatDataFromFile(file);
        int newm = numOfInputVariables;
        HashMap<Integer, Integer> properlyTested = new HashMap<Integer, Integer> ();
        int instances[][] = createInstanceVectors(dataFromFile,newm, true);
        double[] zs = calculateZ(beta, instances, newm);
        for(int i =0; i < numOfDataVectors; i++){
            double eToZ = Math.exp(-zs[i]);
             // double probabilityOf0 = eToZ/(1+eToZ);
            double probabilityOf1 = 1/(1+eToZ);
            int classification = 0;
            if (probabilityOf1 > .5 ){
                 classification = 1;
            }
            if(classification == actualValues[i]){
                if(properlyTested.containsKey(classification)){
                    int count = properlyTested.get(classification);
                    properlyTested.put(classification, count+1);
                } else{
                    properlyTested.put(classification,1);
                }
            }
        }
        return properlyTested;
    }


	public static void main(String[] args) { 
		double[] beta = train(args[0]);
        // System.out.println(beta);
        HashMap<Integer, Integer> properlyTested = test(args[1], beta);

        System.out.println("Class 0: tested " + actualClassifications.get(0) + ", correctly classified "+ properlyTested.get(0));
        System.out.println("Class 1: tested " + actualClassifications.get(1) + ", correctly classified "+ properlyTested.get(1));
        int totalTested = actualClassifications.get(0) + actualClassifications.get(1);
        int totalProperlyTested = properlyTested.get(0) + properlyTested.get(1);
        System.out.println("Overall: tested " + totalTested + ", correctly classified " + totalProperlyTested);
        double accuracy = (double)totalProperlyTested/(double)totalTested;
        System.out.println("Accuracy = " + accuracy);
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
                    // System.out.println(numOfInputVariables);
                }
                if (i == 1){
                    numOfDataVectors = Integer.parseInt(line);
                    // System.out.println(numOfDataVectors);
                }
            }
            dataFromFile = new int[numOfDataVectors][numOfInputVariables+1]; //+1 because we want room for Y var
            int numOfDigits = 0;
            for (int a = 0; a < numOfDataVectors; a++){
                line = bufferedReader.readLine();
                // System.out.println(line);
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
            // System.out.println(Arrays.deepToString(dataFromFile));
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
