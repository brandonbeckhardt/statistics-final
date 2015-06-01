public class LogisticRegression{ 
	public static int calculateZ(beta, x, m){
		z = 0;
		for(int j = 0; j <= j; i++)
			z += beta[j] * x[j];
		return z;
	}


	public static void main(String[] args) { 
		int m = 1000;
		int[m] beta;
		for(int i = 0; i < m; i++) beta[i] = 0;

		int epochs = 100000;
   	// for each pass over dataset
		for(int e = 0; e < epochs; i++){
			int[m] gradient;
			int z = calculateZ();
   		//calculate batch gradient vector for each gradient
			for(int kthInput=0; kthInput <= m; kthInput++){
				gradient[k] = 0;
				int numInstances = 100;
   					//iterate through all training instances in data
				int[] xInput = x[kthInput];
				for(int ithTVar = 0; ithTVar < numInstances; ithTVar++){
					int xInstance = xInput[ithTVar];
					int yInstance = y[ithTVar];

					int eToZ = Math.exp(-z);
					gradient[kthInput] += xInstance*(y - (1/(1+eToZ)));
				}
			}
		}
		int n = .00001;
   		//update all bk
		for(int k = 0; k <= m; k++) beta[k] += n * gradient[k];
	}
	}
}
// System.out.println("Hello, World");

//logistic function f(z) = 1/1+e^-z

// Training
// make an array for b
// each index of b = 0


// for(100000 - this is epocs) - number of passes over data
// 	make an array for k
// each index of k = 0
// for(k = 0, k <= m)
// 		//
// 	end
// end
