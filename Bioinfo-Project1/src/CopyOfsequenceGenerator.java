import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeSet;

public class CopyOfsequenceGenerator {

	static String aa = "ARNDCQEGHILKMFPSTWYV";
	static String possibleResults = "ARNDCQEGHILKMFPSTWYV-";
	static ArrayList<Character> primarySequence = new ArrayList<Character>();
	static int SEQUENCE_SIZE = 20;

	static Map<Character, Integer> seqs = new HashMap<Character, Integer>();

	static float[][] probMatrix = {
		{(float)0.082278481,(float)0.037974684,(float)0.056962025,(float)0.056962025,(float)0.03164557,(float)0.050632911,(float)0.056962025,(float)0.075949367,(float)0.037974684,(float)0.050632911,(float)0.037974684,(float)0.044303797,(float)0.044303797,(float)0.025316456,(float)0.069620253,(float)0.069620253,(float)0.069620253,(float)0.012658228,(float)0.025316456,(float)0.056962025,(float)0.006329114},
		{(float)0.066666667,(float)0.188888889,(float)0.044444444,(float)0.033333333,(float)0.022222222,(float)0.055555556,(float)0.033333333,(float)0.022222222,(float)0.066666667,(float)0.033333333,(float)0.022222222,(float)0.1,(float)0.044444444,(float)0.011111111,(float)0.044444444,(float)0.044444444,(float)0.033333333,(float)0.077777778,(float)0.022222222,(float)0.022222222,(float)0.011111111},
		{(float)0.104651163,(float)0.046511628,(float)0.069767442,(float)0.081395349,(float)0.023255814,(float)0.058139535,(float)0.069767442,(float)0.046511628,(float)0.069767442,(float)0.034883721,(float)0.023255814,(float)0.058139535,(float)0.034883721,(float)0.023255814,(float)0.046511628,(float)0.058139535,(float)0.046511628,(float)0.023255814,(float)0.034883721,(float)0.034883721,(float)0.011627907},
		{(float)0.095744681,(float)0.031914894,(float)0.074468085,(float)0.117021277,(float)0.010638298,(float)0.074468085,(float)0.106382979,(float)0.053191489,(float)0.063829787,(float)0.031914894,(float)0.021276596,(float)0.053191489,(float)0.031914894,(float)0.010638298,(float)0.042553191,(float)0.053191489,(float)0.053191489,(float)0.010638298,(float)0.021276596,(float)0.031914894,(float)0.010638298},
		{(float)0.056179775,(float)0.02247191,(float)0.02247191,(float)0.011235955,(float)0.584269663,(float)0.011235955,(float)0.011235955,(float)0.02247191,(float)0.02247191,(float)0.02247191,(float)0.011235955,(float)0.011235955,(float)0.011235955,(float)0.011235955,(float)0.02247191,(float)0.033707865,(float)0.02247191,(float)0.011235955,(float)0.04494382,(float)0.02247191,(float)0.011235955},
		{(float)0.095238095,(float)0.05952381,(float)0.05952381,(float)0.083333333,(float)0.011904762,(float)0.119047619,(float)0.083333333,(float)0.035714286,(float)0.083333333,(float)0.023809524,(float)0.035714286,(float)0.05952381,(float)0.035714286,(float)0.011904762,(float)0.047619048,(float)0.035714286,(float)0.035714286,(float)0.011904762,(float)0.023809524,(float)0.035714286,(float)0.011904762},
		{(float)0.095744681,(float)0.031914894,(float)0.063829787,(float)0.106382979,(float)0.010638298,(float)0.074468085,(float)0.127659574,(float)0.053191489,(float)0.063829787,(float)0.031914894,(float)0.021276596,(float)0.053191489,(float)0.031914894,(float)0.010638298,(float)0.042553191,(float)0.053191489,(float)0.053191489,(float)0.010638298,(float)0.021276596,(float)0.031914894,(float)0.010638298},
		{(float)0.093023256,(float)0.015503876,(float)0.031007752,(float)0.03875969,(float)0.015503876,(float)0.023255814,(float)0.03875969,(float)0.209302326,(float)0.03875969,(float)0.03875969,(float)0.031007752,(float)0.046511628,(float)0.03875969,(float)0.023255814,(float)0.062015504,(float)0.085271318,(float)0.069767442,(float)0.015503876,(float)0.023255814,(float)0.054263566,(float)0.007751938},
		{(float)0.069767442,(float)0.069767442,(float)0.069767442,(float)0.069767442,(float)0.023255814,(float)0.081395349,(float)0.069767442,(float)0.058139535,(float)0.174418605,(float)0.023255814,(float)0.023255814,(float)0.034883721,(float)0.023255814,(float)0.023255814,(float)0.034883721,(float)0.034883721,(float)0.023255814,(float)0.023255814,(float)0.034883721,(float)0.023255814,(float)0.011627907},
		{(float)0.096385542,(float)0.036144578,(float)0.036144578,(float)0.036144578,(float)0.024096386,(float)0.024096386,(float)0.036144578,(float)0.060240964,(float)0.024096386,(float)0.120481928,(float)0.072289157,(float)0.024096386,(float)0.072289157,(float)0.060240964,(float)0.024096386,(float)0.036144578,(float)0.048192771,(float)0.012048193,(float)0.036144578,(float)0.108433735,(float)0.012048193},
		{(float)0.041958042,(float)0.013986014,(float)0.013986014,(float)0.013986014,(float)0.006993007,(float)0.020979021,(float)0.013986014,(float)0.027972028,(float)0.013986014,(float)0.041958042,(float)0.237762238,(float)0.027972028,(float)0.13986014,(float)0.090909091,(float)0.034965035,(float)0.027972028,(float)0.041958042,(float)0.041958042,(float)0.048951049,(float)0.090909091,(float)0.006993007},
		{(float)0.057377049,(float)0.073770492,(float)0.040983607,(float)0.040983607,(float)0.008196721,(float)0.040983607,(float)0.040983607,(float)0.049180328,(float)0.024590164,(float)0.016393443,(float)0.032786885,(float)0.196721311,(float)0.073770492,(float)0.016393443,(float)0.049180328,(float)0.06557377,(float)0.06557377,(float)0.032786885,(float)0.024590164,(float)0.040983607,(float)0.008196721},
		{(float)0.085365854,(float)0.048780488,(float)0.036585366,(float)0.036585366,(float)0.012195122,(float)0.036585366,(float)0.036585366,(float)0.06097561,(float)0.024390244,(float)0.073170732,(float)0.243902439,(float)0.109756098,(float)0.073170732,(float)0.024390244,(float)0.012195122,(float)0.012195122,(float)0.012195122,(float)0.012195122,(float)0.012195122,(float)0.024390244,(float)0.012195122},
		{(float)0.038834951,(float)0.009708738,(float)0.019417476,(float)0.009708738,(float)0.009708738,(float)0.009708738,(float)0.009708738,(float)0.029126214,(float)0.019417476,(float)0.048543689,(float)0.126213592,(float)0.019417476,(float)0.019417476,(float)0.310679612,(float)0.009708738,(float)0.019417476,(float)0.019417476,(float)0.038834951,(float)0.194174757,(float)0.029126214,(float)0.009708738},
		{(float)0.112244898,(float)0.040816327,(float)0.040816327,(float)0.040816327,(float)0.020408163,(float)0.040816327,(float)0.040816327,(float)0.081632653,(float)0.030612245,(float)0.020408163,(float)0.051020408,(float)0.06122449,(float)0.010204082,(float)0.010204082,(float)0.204081633,(float)0.06122449,(float)0.051020408,(float)0.010204082,(float)0.020408163,(float)0.040816327,(float)0.010204082},
		{(float)0.101851852,(float)0.037037037,(float)0.046296296,(float)0.046296296,(float)0.027777778,(float)0.027777778,(float)0.046296296,(float)0.101851852,(float)0.027777778,(float)0.027777778,(float)0.037037037,(float)0.074074074,(float)0.009259259,(float)0.018518519,(float)0.055555556,(float)0.092592593,(float)0.083333333,(float)0.037037037,(float)0.037037037,(float)0.055555556,(float)0.009259259},
		{(float)0.107843137,(float)0.029411765,(float)0.039215686,(float)0.049019608,(float)0.019607843,(float)0.029411765,(float)0.049019608,(float)0.088235294,(float)0.019607843,(float)0.039215686,(float)0.058823529,(float)0.078431373,(float)0.009803922,(float)0.019607843,(float)0.049019608,(float)0.088235294,(float)0.107843137,(float)0.019607843,(float)0.029411765,(float)0.058823529,(float)0.009803922},
		{(float)0.02020202,(float)0.070707071,(float)0.02020202,(float)0.01010101,(float)0.01010101,(float)0.01010101,(float)0.01010101,(float)0.02020202,(float)0.02020202,(float)0.01010101,(float)0.060606061,(float)0.04040404,(float)0.01010101,(float)0.04040404,(float)0.01010101,(float)0.04040404,(float)0.02020202,(float)0.555555556,(float)0.01010101,(float)0,(float)0.01010101},
		{(float)0.038834951,(float)0.019417476,(float)0.029126214,(float)0.019417476,(float)0.038834951,(float)0.019417476,(float)0.019417476,(float)0.029126214,(float)0.029126214,(float)0.029126214,(float)0.067961165,(float)0.029126214,(float)0.009708738,(float)0.194174757,(float)0.019417476,(float)0.038834951,(float)0.029126214,(float)0.009708738,(float)0.300970874,(float)0.019417476,(float)0.009708738},
		{(float)0.088235294,(float)0.019607843,(float)0.029411765,(float)0.029411765,(float)0.019607843,(float)0.029411765,(float)0.029411765,(float)0.068627451,(float)0.019607843,(float)0.088235294,(float)0.12745098,(float)0.049019608,(float)0.019607843,(float)0.029411765,(float)0.039215686,(float)0.058823529,(float)0.058823529,(float)0,(float)0.019607843,(float)0.166666667,(float)0.009803922},
		{(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0.05,(float)0} };
	private static void buildFirstSequence() {
		
		seqs.put('A', 0);
		seqs.put('R', 1);
		seqs.put('N', 2);
		seqs.put('D', 3);
		seqs.put('C', 4);
		seqs.put('Q', 5);
		seqs.put('E', 6);
		seqs.put('G', 7);
		seqs.put('H', 8);
		seqs.put('I', 9);
		seqs.put('L', 10);
		seqs.put('K', 11);
		seqs.put('M', 12);
		seqs.put('F', 13);
		seqs.put('P', 14);
		seqs.put('S', 15);
		seqs.put('T', 16);
		seqs.put('W', 17);
		seqs.put('Y', 18);
		seqs.put('V', 19);
		seqs.put('-', 20);
		
		
		for (int i = 0; i < SEQUENCE_SIZE; i++) {
			int idx = new Random().nextInt(aa.length());
			primarySequence.add(aa.charAt(idx));
		}
	}

	private static <E> void printSequence(ArrayList<E> sq) {
		Iterator<E> i = sq.iterator();
		while (i.hasNext()) {
			Object element = i.next();
			System.out.print(element);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		buildFirstSequence();
		printProbMatrix();
		
		ArrayList<Character> son_ps1 = createNewSequence(primarySequence);
		ArrayList<Character> son_son_ps1_1 = createNewSequence(son_ps1);
		ArrayList<Character> son_son_ps1_2 = createNewSequence(son_ps1);
		
		ArrayList<Character> son_ps2 = createNewSequence(primarySequence);
		
		System.out.print("Primary sequence: \t\t");
		printSequence(primarySequence);
		
		System.out.print("Son of primary sequence PS1: \t");
		printSequence(son_ps1);
		System.out.print("Son of PS1_1: \t\t\t");
		printSequence(son_son_ps1_1);
		System.out.print("Son of PS1_2: \t\t\t");
		printSequence(son_son_ps1_2);
		
		System.out.print("Son of primary sequence PS2: \t");
		printSequence(son_ps2);
	}
	
	private static ArrayList<Character> createNewSequence(
			ArrayList<Character> seq) {
		ArrayList<Character> newSeq = new ArrayList<Character>();
		
		for(int i = 0; i < SEQUENCE_SIZE; i++) {
			char aa = seq.get(i);
			int aa_pos = seqs.get(aa);
			
			int c_pos = getRandomElementPos(probMatrix, aa_pos);
			char c = (char)getKeyByValue(seqs, c_pos);
			
			newSeq.add(c);
		}
		return newSeq;
	}

	private static int getRandomElementPos(float[][] elements, int idx)
	{

	    float random = new Random().nextFloat();
	    
	    for(int i = 0; i < elements[0].length; i++) {
	    	if(random < elements[idx][i]) {
	    		return i;
	    	}
	    	random -= elements[idx][i];
	    }

	    throw new IllegalStateException("Should never get here");
	}
	
	private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (value.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	private static void printProbMatrix() {
		System.out.print("\n \tA\tC\tG\tT\t-\n");
		for(int i = 0; i < possibleResults.length(); i++) {
			char c = (char)getKeyByValue(seqs, i);
			System.out.print(c);
			
			for(int j = 0; j< possibleResults.length(); j++) {
				System.out.print("\t" + String.format("%.4f", probMatrix[i][j]));
			}
			System.out.println("\n");
		}
	}

}
