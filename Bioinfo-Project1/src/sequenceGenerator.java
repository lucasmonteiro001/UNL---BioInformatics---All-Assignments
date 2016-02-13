import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeSet;

public class sequenceGenerator {

	static String aa = "ACGT";
	static String possibleResults = "ACGT-";
	static ArrayList<Character> primarySequence = new ArrayList<Character>();
	static int SEQUENCE_SIZE = 20;

	static Map<Character, Integer> seqs = new HashMap<Character, Integer>();

	static float[][] probMatrix = {
			{ (float) 0.75, (float) 0.05, (float) 0.06, (float) 0.04,
					(float) 0.1 },
			{ (float) 0.08, (float) 0.68, (float) 0.02, (float) 0.04,
					(float) 0.18 },
			{ (float) 0.04, (float) 0.03, (float) 0.74, (float) 0.07,
					(float) 0.12 },
			{ (float) 0.03, (float) 0.08, (float) 0.01, (float) 0.7,
					(float) 0.18 },
			{ (float) 0.3, (float) 0.15, (float) 0.1, (float) 0.05, (float) 0.4 } };

	private static void buildFirstSequence() {
		seqs.put('A', 0);
		seqs.put('C', 1);
		seqs.put('G', 2);
		seqs.put('T', 3);
		seqs.put('-', 4);
		
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
		printProbMatrix();
		
		buildFirstSequence();
		
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
	    
	    for(int i = 0; i < 5; i++) {
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
			if(i == 0) System.out.print("A");
			else if(i == 1) System.out.print("C");
			if(i == 2) System.out.print("G");
			if(i == 3) System.out.print("T");
			if(i == 4) System.out.print("-");
			
			for(int j = 0; j< possibleResults.length(); j++) {
				System.out.print("\t" + probMatrix[i][j]);
			}
			System.out.println("\n");
		}
	}

}
