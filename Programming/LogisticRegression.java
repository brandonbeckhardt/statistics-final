import java.io.*;
import java.util.Arrays;
import java.lang.*;

public class LogisticRegression{ 
	public static int numOfInputVariables = 0;
    public static int numOfDataVectors = 0;
    public static int[][] dataFromFile;


    // X0 is always 1.  Xm+1 is Y
    public static int[][] createInstanceVectors(int[][] data, int m){
        // +2 because we want the Instance[0] = 1, and Instance[M+1] equal to Y
        int[][] instances = new int[numOfDataVectors][numOfInputVariables + 2];

        for(int i=0; i < numOfDataVectors; i++){
            instances[i][0] = 1;
            for(int k = 1; k <= m+1; k++){   
                instances[i][k] = data[i][k-1];
            }
        }
        return instances;
    }

	public static void main(String[] args) { 
		if (args.length!= 0) formatDataFromFile(args[0]);
        System.out.println(Arrays.deepToString(dataFromFile));

        double n = .00001;
        int m = numOfInputVariables;
        //Array of arrays containing instances.  X0 is always 1.  Xm+1 is Y
        int instances[][] = createInstanceVectors(dataFromFile,m);
		double[] beta = new double[m+1];
		for(int i = 0; i < m; i++) beta[i] = 0;
		int epochs = 100000;
   	    // for each pass over dataset
		for(int e = 0; e < epochs; e++){
            double[] gradient = new double[m+1];
			int[] z = calculateZ(beta,instances,m);
   		   //calculate batch gradient vector for each gradient
			for(int kthInput=0; kthInput <= m; kthInput++){
				gradient[kthInput] = 0;
   				//iterate through all training instances in data
				for(int ithTVar = 0; ithTVar < numOfDataVectors; ithTVar++){
					int xInstance = instances[ithTVar][kthInput];
					int yInstance = instances[ithTVar][m+1]; 
					double eToZ = Math.exp(-z[ithTVar]);
					gradient[kthInput] += xInstance*(yInstance-(1/(1+eToZ)));
				}
			}       
        //update all bk
            for(int k = 0; k <= m; k++) beta[k] += n * gradient[k];
		}
    System.out.println("Here");
        System.out.println(Arrays.toString(beta));
	}


    public static int[] calculateZ(double[] beta,int[][] instances,int m){
     int[] z = new int[numOfDataVectors];
     for(int i = 0; i < numOfDataVectors; i++){
        int[] instance = instances[i];
        for(int j = 0; j <= m; j++)
            z[i] += beta[j] * instance[j];
     }
     return z;
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
