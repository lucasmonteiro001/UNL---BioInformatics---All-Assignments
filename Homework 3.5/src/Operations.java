import java.util.ArrayList;
import java.util.HashMap;

public class Operations {
	
	public static int counterNode = 1;

	/**
	 * Return the value after Jukes Cantor equations
	 * @param dna
	 * @param dna2
	 * @return
	 */
	public static Double getJukesCantor(DNA dna, DNA dna2) {
		String str1 = dna.getSequence();
		String str2 = dna2.getSequence();
		
		int i = 0;
		int counter = 0;
		int size = Math.max(str1.length(), str2.length());
		double prob = 0.0;
		
		if (str1.length() == str2.length()) {
			for(i = 0 ; i < str1.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) counter++;
			}
		} else if (str1.length() > str2.length()) {
			for(i = 0 ; i < str2.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) counter++;
			}
			
			for (int j = i; j < str1.length(); j++) {
				if (str1.charAt(j) != '-') continue;
				counter++;
			}
		} else if (str1.length() < str2.length()) {
			for(i = 0 ; i < str1.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) counter++;
			}
			
			for (int j = i; j < str2.length(); j++) {
				if (str2.charAt(j) != '-') continue;
				counter++;
			}
		} else {
			System.out.println("Unable to solve it");
			System.exit(1);
		}
		prob = (double)counter/size;
		double aux = (double) -19/20;
		double aux2 = (double) 20/19;
		double jukes = aux * Math.log10(1 - (aux2 * prob));
		
		if (Double.isNaN(jukes)) return 10.0;
		
		return jukes;
	}

	/**
	 * Generate the phylogenetic tree recursively
	 * @param jukesCantor
	 * @param sequences
	 */
	public static void generatePhylogeneticTree(HashMap<String, Double> jukesCantor, ArrayList<DNA> sequences) {
		TreeNode leaf1 = null;
		TreeNode leaf2 = null;
		TreeNode tn = null;
		
		// Stop condition of recursion
		if (jukesCantor == null || sequences.size() <= 2){
			double value = jukesCantor.get(sequences.get(0).getID()+"/"+sequences.get(1).getID());
			
			TreeNode first = new TreeNode(null, null, -1,"U" + String.valueOf(counterNode++));
			
			tn = Tree.searchNode(sequences.get(0).getID());
			if (tn == null) {
				String n = findName(sequences, sequences.get(0).getID());
				if (n == null) {
					n = sequences.get(0).getID();
				}
				leaf1 = new TreeNode(null, null, value, n);
			} else {
				leaf1 = tn;
				if (tn.getValue() == -1) tn.setValue(value);
			}
			
			tn = Tree.searchNode(sequences.get(1).getID());
			if (tn == null) {
				String n = findName(sequences, sequences.get(1).getID());
				if (n == null) {
					n = sequences.get(0).getID();
				}
				leaf2 = new TreeNode(null, null, value, n);
			} else {
				leaf2 = tn;
				if (tn.getValue() == -1) tn.setValue(value);
			}
			
			// Set Left and right and add to the array List
			first.setLeft(leaf1);
			first.setRight(leaf2);
			Tree.getListOfNodes().add(first);
			
			// Set this as the root of the tree
			Tree.setHead(first);
			
			// Go back to main function
			return;
		}
		
		HashMap<String, Double> newJukesCantor = new HashMap<String, Double> ();
		HashMap<Integer, Double> S = new HashMap<Integer, Double> ();
		
		double size = sequences.size() - 2;
		double min = Double.MAX_VALUE;

		double S1 = 0, S2 = 0, D = 0;
		String N1 = new String("");
		String N2 = new String("");
		
		// Generate S
		for (int i = 0; i < sequences.size() - 1; i++) {
			double sum = 0.0;
			DNA seq1 = sequences.get(i);
			
			for (int j = 0; j < sequences.size() - 1; j++) {
				DNA seq2 = sequences.get(j);
				
				if (i != j){
					if (i > j)	sum += jukesCantor.get(seq2.getID() + "/" + seq1.getID());
					else 		sum += jukesCantor.get(seq1.getID() + "/" + seq2.getID()) ;
				}
			}
			S.put(i, sum/size);
		}

		// Generate all possible combinations and find the min one
		for (int i = 0; i < sequences.size() - 1; i++) {
			DNA seq1 = sequences.get(i);
			
			for (int j = 0 ; j < sequences.size() - 1; j++) {
				if (i != j){
					DNA seq2 = sequences.get(j);
					
					double Dij = 0.0;
					double Si = S.get(i);
					double Sj = S.get(j);

					if (i > j)	Dij = jukesCantor.get(seq2.getID() + "/" + seq1.getID());
					else		Dij = jukesCantor.get(seq1.getID() + "/" + seq2.getID()) ;
					
					// Change the current min path
					if ((Dij - Si - Sj) < min){
						min = (Dij - Si - Sj);
						D = Dij;
						N1 = seq1.getID();
						N2 = seq2.getID();
						S1 = Si;
						S2 = Sj;
					}
				}
			}
		}
		
		// Generate the node U
		double Su1 = (D/2) + (S1 - S2)/2;
		double Su2 = (D/2) + (S2 - S1)/2;
		
		TreeNode first = new TreeNode(null, null, -1,"U" + String.valueOf(counterNode++));
		
		String name1 = findName(sequences , N1);
		String name2 = findName(sequences , N2);
		
		if (name1 == null) {
			name1 = N1;
		}
		
		if (name2 == null) {
			name2 = N2;
		}
		
		// See if node already exists
		tn = Tree.searchNode(name1);

		if (tn == null) {
			leaf1 = new TreeNode(null, null, Su1, name1);
		} else {
			leaf1 = tn;
			if (tn.getValue() == -1) tn.setValue(Su1);
		}
		
		tn = Tree.searchNode(name2);
		if (tn == null) {
			leaf2 = new TreeNode(null, null, Su2, name2);
		} else {
			leaf2 = tn;
			if (tn.getValue() == -1) tn.setValue(Su2);
		}
		
		// Remove both sequences from the array list
		removeSequence(sequences, N1);
		removeSequence(sequences, N2);
		
		// Set Left and right and add to the array List
		first.setLeft(leaf1);
		first.setRight(leaf2);
		
		Tree.getListOfNodes().add(first);
		
		// Compute the new distance with this new element
		for (DNA d : sequences) {
			double Sxi = 0.0;
			double Syj = 0.0;

			if (jukesCantor.containsKey(N1 + "/" + d.getID())) {
				Sxi = jukesCantor.get(N1 + "/" + d.getID());
			} else {
				Sxi = jukesCantor.get(d.getID()+"/"+N1);
			}
			
			if (jukesCantor.containsKey(N2 + "/" + d.getID())) {
				Syj = jukesCantor.get(N2 + "/" + d.getID());
			} else {
				Syj = jukesCantor.get(d.getID() + "/" + N2);
			}
			
			double newValue = (D - Sxi - Syj)/2;
			jukesCantor.put(d.getID() + "/" + first.getNodeName(), newValue);
		}
		
		// Add this new node to the arrayList
		sequences.add(new DNA("-", "-", first.getNodeName()));		
		
		// Set Left and right and add to the array List
		first.setLeft(leaf1);
		first.setRight(leaf2);
		Tree.getListOfNodes().add(first);

		// Generate new Matrix of Distances with the Joined member
		for (int i = 0 ; i < sequences.size() - 1; i++) {
			DNA seq1 = sequences.get(i);

			for (int j = i + 1; j < sequences.size(); j++) {
				DNA seq2 = sequences.get(j);

				newJukesCantor.put(seq1.getID() + "/" + seq2.getID(), 
										jukesCantor.get(seq1.getID() + "/" + seq2.getID()) );
			}
		}
		
		// Update the variables
		jukesCantor = newJukesCantor;
		S = null;
		
		// Call it recursive
		generatePhylogeneticTree(jukesCantor, sequences);
	}

	private static String findName(ArrayList<DNA> sequences, String key) {
		for (DNA dna : sequences) {
			if (dna.getID().compareTo(key) == 0) {
				if (dna.getInformation().compareTo("-") == 0) return null;
				return dna.getInformation();
			}
		}
		return null;
	}

	/**
	 * remove the desired sequences
	 * @param sequences
	 * @param key
	 */
	public static void removeSequence(ArrayList<DNA> sequences, String key) {
		for (int i = 0; i < sequences.size(); i++) {
			if (sequences.get(i).getID().compareTo(key) == 0) {
				sequences.remove(i);
				return;
			}
		}
	}
}
// This line make it work