import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	private static ArrayList<DNA> sequences;

	public static void main(String[] args) throws IOException {
		// Read all the Database
		// and compute time elapsed
		long start = System.nanoTime();
		
		sequences = Sequence.ReadAllSequences("data/treedata.txt");
		
		long end = (System.nanoTime() - start);
		System.out.println("# of Sequences :" + sequences.size() + "\n"
				+ "Time reading files: " + (double) end/1000000000 + " s" );
		
		HashMap<String, Double> jukesCantor = new HashMap<String, Double> ();
		
		// Generate the first distance matrix and compute time elapsed
		start = System.nanoTime();		
		
		for (int i = 0 ; i < sequences.size() - 1; i++) {
			for (int j = i + 1; j < sequences.size(); j++) {
				double aux =  (double) Operations.getJukesCantor(sequences.get(i), sequences.get(j));
				jukesCantor.put(String.valueOf(i) + "/" + String.valueOf(j), aux);
			}
		}
		
		end = (System.nanoTime() - start);
		System.out.println("Time using jukesCantor: " + (double) end/1000000000 + " s" );
		
		// Generate the tree and compute time elapsed
		start = System.nanoTime();
		
		Operations.generatePhylogeneticTree(jukesCantor, sequences);
		
		end = (System.nanoTime() - start);
		System.out.println("Time generating the tree: " + (double) end/1000000000 + " s" );
		
		File sequenceFile = new File("output/phylogenetic.txt");
		FileWriter writer = new FileWriter(sequenceFile);
		System.out.println("Newick Format at: output/phylogenetic.txt");
		
		Tree.getTree(writer);
		
		writer.close();
		System.out.println("Finished");
	}
}
// This line make it work