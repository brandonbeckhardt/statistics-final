import java.io.*;
import java.util.Arrays;
import java.util.*;
import java.lang.*;


public class NaiveBayes {

    public static int numOfInputVariables = 0;
    public static int numOfDataVectors = 0;
    public static int[][] trainingFile;
    public static double trainingClass1 = 0;
    public static double trainingClass0 = 0;

    public static int[][] testFile;
    public static int numOfInputVariablesTest = 0;
    public static int numOfDataVectorsTest = 0;
    public static double testClass1 = 0;
    public static double testClass0 = 0;

    public static double probOfY0;
    public static double probOfY1;

    public static void main(String [] args) {
        if (args.length == 2) {
            
            
            Boolean isLaplace = false;
            HashMap<Integer, double[][]> MLEValues = createEstimator(args[0],isLaplace);
            Boolean train = false;
            testFile = formatDataFromFile(args[1],train);
            countYClassesTest(testFile);
            /*
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("This is training data:");
            System.out.println(Arrays.deepToString(trainingFile));
            System.out.println("Variales: "+numOfInputVariables+" Vectors: "+numOfDataVectors);
            System.out.println("Y=0: "+trainingClass0+" Y=1: "+trainingClass1);
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("This is test data:");
            System.out.println(Arrays.deepToString(testFile));
            System.out.println("Variales: "+numOfInputVariablesTest+" Vectors: "+numOfDataVectorsTest);
            System.out.println("Y=0: "+testClass0+" Y=1: "+testClass1);
            */
            classify(MLEValues);
            

            //HashMap<Integer, double[][]> lapraceValues = createMLE(args[0]);


            //create laplace estimates
            trainingClass1 = 0;
            trainingClass0 = 0;
            testClass1 = 0;
            testClass0 = 0;
            isLaplace = true;
            HashMap<Integer, double[][]> laplaceValues = createEstimator(args[0],isLaplace);
            train = false;
            testFile = formatDataFromFile(args[1],train);
            countYClassesTest(testFile);
            classify(laplaceValues);

            /*
            for (int i = 1;i <= numOfInputVariables ;i++ ) {
                System.out.println(Arrays.deepToString(MLEValues.get(i)));
            }
            */


            

            /*
            HashMap<Integer, int[][]> inputVariableestimateTables = new HashMap<Integer, int[][]>();
            int[][] test = new int[5][5];
            for (int i = 0; i < 5; i++){
                for (int j = 0;j < 5; j++ ) {
                    test[i][j] = 0;        
                }

            }
            int[][] test2 = new int[5][5];
            for (int i = 0; i < 5; i++){
                for (int j = 0;j < 5; j++ ) {
                    test2[i][j] = 2;        
                }

            }
            inputVariableestimateTables.put(1,test);
            inputVariableestimateTables.put(2,test2);
            int[][] get = inputVariableestimateTables.get(1);
            int[][] get2 = inputVariableestimateTables.get(2);
            System.out.println(Arrays.deepToString(get));
            System.out.println(Arrays.deepToString(get2));
            */

        } 
    }

    private static void classify(HashMap<Integer, double[][]> esitmatorValues){
                
        int numberClassifiedAs1 = 0;
        int numberClassifiedAs0 = 0;

        double pXGivenYIs0 = 0;
        double pXGivenYIs1 = 0;
        int trueValOfY = 0;
        for (int i = 0; i < numOfDataVectorsTest;i++){// 2 because it is the classes Y can be 0 or 1
            for (int j = 0;j <=numOfInputVariablesTest-1 ;j++ ) {
                int valOfXVar = testFile[i][j];
                trueValOfY = testFile[i][numOfInputVariablesTest];
                double[][] xMLE = esitmatorValues.get(j+1);
                pXGivenYIs1 += Math.log10(xMLE[1][valOfXVar]);
                pXGivenYIs0 += Math.log10(xMLE[0][valOfXVar]);
                //System.out.println("Assum 0: "+ pXGivenYIs0 + "Value we take log of:" + xMLE[0][valOfXVar]);
            }
            pXGivenYIs0 += Math.log10(probOfY0);
            //System.out.println("Prob Y=1: " + pXGivenYIs1 + "Prob Y=0: " + pXGivenYIs0);
            pXGivenYIs1 += Math.log10(probOfY1);
            //System.out.println("Prob Y=1: " + pXGivenYIs1 + "Prob Y=0: " + pXGivenYIs0);
            if (pXGivenYIs0 > pXGivenYIs1 && trueValOfY == 1){
                System.out.println(Arrays.toString(testFile[i]));
                System.out.println("Prob Y=1: " + pXGivenYIs1 + "Prob Y=0: " + pXGivenYIs0);
            }
            if(pXGivenYIs0 > pXGivenYIs1 && trueValOfY ==0){
                //System.out.println("Y is 0! Yay!");
                numberClassifiedAs0++;
            } else if (pXGivenYIs1 > pXGivenYIs0 && trueValOfY ==1) {
                //System.out.println("Y is 1! Yay!");
                //System.out.println(numberClassifiedAs1);
                //System.out.println("true Y==== "+ trueValOfY);
                //System.out.println(Arrays.toString(testFile[i]));
                numberClassifiedAs1++;
            }
            pXGivenYIs0 = 0;
            pXGivenYIs1 = 0;
        }
        System.out.println("Class 0: tested "+(int)testClass0+", correctly classified "+numberClassifiedAs0);
        System.out.println("Class 1: tested "+(int)testClass1+", correctly classified "+numberClassifiedAs1);
        System.out.println("Overall: tested "+numOfDataVectorsTest+" correctly classified "+(numberClassifiedAs0+numberClassifiedAs1));
        double accuracy = (((double)numberClassifiedAs0+(double)numberClassifiedAs1)/(double)numOfDataVectorsTest);
        System.out.println("Accuracy = " + accuracy);
        //add value of prob y at end here
    }

    //gets the data from the input file and calculates the MLE values
    private static HashMap<Integer, double[][]> createEstimator(String nameOfFile, Boolean isLaplace){
        Boolean train = true;
        trainingFile = formatDataFromFile(nameOfFile,train);
        countYClassesTrain(trainingFile);

        probOfY1 = trainingClass1/numOfDataVectors;
        probOfY0 = trainingClass0/numOfDataVectors;

        System.out.println("Y counts:");
        System.out.println(trainingClass1);
        System.out.println(trainingClass0);

        HashMap<Integer, double[][]> inputVariableEstimatorTables = createTableOfEstimates(isLaplace);
        return inputVariableEstimatorTables;


    }

       private static void countYClassesTest(int[][] data){
         for (int i = 0; i < numOfDataVectorsTest;i++){// 2 because it is the classes Y can be 0 or 1
            if (data[i][numOfInputVariablesTest] == 0){
                testClass0++;
            } else {
                testClass1++;
            }



            /*
            for (int j = 0;j <numOfInputVariablesTest ;j++ ) {
                if (j == numOfInputVariables-1){
                    if(data[i][j] == 0){
                        testClass0++;
                        System.out.println("we added!");
                    } else if (data[i][j] == 1){
                        testClass1++;
                    }
                }
            }
            */
        }
        //System.out.println("num if y = 0: " + testClass0);
    }

    private static void countYClassesTrain(int[][] data){
         for (int i = 0; i < numOfDataVectors;i++){// 2 because it is the classes Y can be 0 or 1
            if (data[i][numOfInputVariables] == 0){
                //System.out.println(data[i][numOfInputVariables]);
                trainingClass0++;
            } else {
                trainingClass1++;
            }
            /*
            for (int j = 0;j <numOfInputVariables ;j++ ) {
                if (j == numOfInputVariables-1){
                    if(data[i][j] == 0){
                        trainingClass0++;
                    } else if (data[i][j] == 1){
                        trainingClass1++;
                    }
                }
            }
            */


        }
        System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
        System.out.println(trainingClass1);
    }


    private static HashMap<Integer, double[][]> createTableOfEstimates(Boolean isLaplace){
        HashMap<Integer, double[][]> hashOfInputVarCounts = createTableOfCounts();
        if (isLaplace) {
            System.out.println("Begin Laplace");

            

            HashMap<Integer, double[][]> laplaceCounts = add1CountToAll(hashOfInputVarCounts);

            //print
            for (int i = 1;i <=numOfInputVariables ;i++ ) {
                double[][] tableOfLaplace = hashOfInputVarCounts.get(i);
                System.out.println(Arrays.deepToString(tableOfLaplace));
            }

            for (int i = 1;i <=numOfInputVariables ;i++ ) {
                double[][] tableOfLaplace = calculateEstimates(laplaceCounts.get(i),isLaplace);
                laplaceCounts.put(i,tableOfLaplace);
            }

            //print
            for (int i = 1;i <=numOfInputVariables ;i++ ) {
                double[][] tableOfLaplace = laplaceCounts.get(i);
                System.out.println(Arrays.deepToString(tableOfLaplace));
            }

            return laplaceCounts; 
        } else {
            
            for (int i = 1;i <=numOfInputVariables ;i++ ) {
                double[][] tableOfMLE = hashOfInputVarCounts.get(i);
                System.out.println(i);
                System.out.println(Arrays.deepToString(tableOfMLE));
            }

            for (int i = 1;i <=numOfInputVariables ;i++ ) {
                double[][] tableOfMLE = calculateEstimates(hashOfInputVarCounts.get(i),isLaplace);
                hashOfInputVarCounts.put(i,tableOfMLE);
            }


            for (int i = 1;i <=numOfInputVariables ;i++ ) {
                double[][] tableOfMLE = hashOfInputVarCounts.get(i);
                System.out.println(i);
                System.out.println(Arrays.deepToString(tableOfMLE));
            }

            return hashOfInputVarCounts;
        } 
        
    }

    private static HashMap<Integer, double[][]> add1CountToAll(HashMap<Integer, double[][]> variablesWithCounts){
        for (int i = 1;i <=numOfInputVariables ;i++ ) {
            double[][] countTable = variablesWithCounts.get(i);
            //System.out.println(Arrays.deepToString(countTable));//before add 1
            for (int k = 0; k < 2;k++){// 2 because it is the classes Y can be 0 or 1
                for (int j = 0;j <2 ;j++ ) {
                    countTable[k][j]++;
                }
            }
            //System.out.println("Added 1"+Arrays.deepToString(countTable));//after add 1
        }
        return variablesWithCounts;
    }

    private static double[][] calculateEstimates(double[][] tableOfCounts, Boolean isLaplace){
        double[][] estimateTable = tableOfCounts;
        for (int i = 0; i < 2;i++){// 2 because it is the classes Y can be 0 or 1
            for (int j = 0;j <2 ;j++ ) {
                if (isLaplace) {
                    probOfY0 = (trainingClass0+2)/(numOfDataVectors+4);
                    probOfY1 = (trainingClass1+2)/(numOfDataVectors+4);
                    estimateTable[i][j] /= (numOfDataVectors+4); //4 is the number of cells
                    if (i==0) {
                         estimateTable[i][j] /= probOfY0;
                    } else {
                         estimateTable[i][j] /= probOfY1;
                    }

                } else {
                    estimateTable[i][j] /= numOfDataVectors;
                    if (i == 0){
                        estimateTable[i][j] /= probOfY0;
                    } else {
                        estimateTable[i][j] /= probOfY1;
                    }
                    
                }
            }
        }
        return estimateTable;
    }

    private static HashMap<Integer, double[][]> createTableOfCounts(){
        HashMap<Integer, double[][]> inputVarCounts = new HashMap<Integer, double[][]>();
        //create hashmap with all values of variable table set to zero
        for (int i = 0; i < numOfInputVariables; i++){
            double[][] countsForInputVar = new double[2][2]; //the 2 is for the two ways Y can be classified 1 or 0
            inputVarCounts.put(i+1,countsForInputVar);//+1 to align with variables X1,X2,X3
        }

        //get count for each input variable from data 2x2 array (trainingFile)
        for (int i = 0; i < numOfDataVectors; i++){
             for (int j = 0;j < numOfInputVariables; j++ ) {
                 int valueOfY = trainingFile[i][numOfInputVariables];
                 int valueOfInputVar = trainingFile[i][j];

                 //get corresponding table for variable X
                 double[][] tableOfInputVar = inputVarCounts.get(j+1);

                 //because the values of X and Y are either 0 or 1, their values also correspond
                 //to the correct cell that the counts are contained in.  
                 tableOfInputVar[valueOfY][valueOfInputVar]++;      
             }
         }
        return inputVarCounts;
    }

    //Method takes in the name of the data file and parses the texts into data that is stored as a
    //2X2 array
    private static int[][] formatDataFromFile(String nameOfFile, Boolean train){
        // The name of the file to open.
        String fileName = nameOfFile;

        // This will reference one line at a time
        String line = null;
        int[][] data = new int[0][0];
        /*Code below is taken from: https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html 
            It is taken from the section "Reading Ordinary Text Files in Java"
        */
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        /*end of cited code*/



        if(train){
            //stores values for:
            //numOfInputVariables = first number from first line parsed
            //numOfDataVectors = second number from second line parsed
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

            //create new 2x2 array where the outer array contains number of data points and inner arrays contain data for the input 
            //variables
            data = new int[numOfDataVectors][numOfInputVariables+1]; //+1 because we want room for Y var

            int numOfDigits = 0;
            for (int a = 0; a < numOfDataVectors; a++){
                line = bufferedReader.readLine();
                //System.out.println(line);
                for (int i = 0;i <line.length();i++) {
                    if (Character.isDigit(line.charAt(i))){
                        data[a][numOfDigits] = Character.getNumericValue(line.charAt(i));
                        numOfDigits++;
                    }
                    if (i == line.length()-1){
                        numOfDigits = 0;
                    }
                }

            }    
            //System.out.println(Arrays.deepToString(trainingFile));
        } else {
            for (int i = 0; i <2;i++){//for loops twice to get the first two values in file
                line = bufferedReader.readLine();
                if (i == 0){
                    numOfInputVariablesTest = Integer.parseInt(line);
                    System.out.println(numOfInputVariablesTest);
                }
                if (i == 1){
                    numOfDataVectorsTest = Integer.parseInt(line);
                    System.out.println(numOfDataVectorsTest);
                }
            }

            //create new 2x2 array where the outer array contains number of data points and inner arrays contain data for the input 
            //variables
            data = new int[numOfDataVectorsTest][numOfInputVariablesTest+1]; //+1 because we want room for Y var

            int numOfDigits = 0;
            for (int a = 0; a < numOfDataVectorsTest; a++){
                line = bufferedReader.readLine();
                //System.out.println(line);
                for (int i = 0;i <line.length();i++) {
                    if (Character.isDigit(line.charAt(i))){
                        data[a][numOfDigits] = Character.getNumericValue(line.charAt(i));
                        numOfDigits++;
                    }
                    if (i == line.length()-1){
                        numOfDigits = 0;
                    }
                }

            } 
        }



        /*Code below is taken from: https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html 
            It is taken from the section "Reading Ordinary Text Files in Java"
        */

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
        }
        /*Cited Code ends here*/
    return data;
    }


}