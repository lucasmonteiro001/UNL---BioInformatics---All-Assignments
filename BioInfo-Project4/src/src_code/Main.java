package src_code;

import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

	private static final String PATH_TO_S1 = "data/Seq1_test.txt";
	private static final String DATABASE_PATH = "data/database.txt";
	private static final String DESTINATION = "data/output.txt";

	public static void main(String[] args) {

		/*
		 * Read the 2 sequences from the 2 files in order to be compared to the
		 * database stored in database.txt
		 */
		Sequence s1 = Sequence.readSequenceFromFile(PATH_TO_S1);

		Sequence seqToTest = s1;

		ArrayList<Sequence> seqs_from_db = Sequence
				.readMultipleSequencesFromFile(DATABASE_PATH);

		// Sequence.printSequences(seqs_from_db);

		// Initialize the scoring matrix that will be used to compute the score
		// between pairs
		ScoringMatrix sm = new ScoringMatrix(ScoringMatrix.BLOSUM50);

		// File that will contain all the match results from Seq1 and the
		// database
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(DESTINATION, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		long total_matrix = 0;
		long total_traceback = 0;

//		System.out.println("Please wait!\nRunning...");
		// Perform all matches between Seq1 and the database
		for (int i = 0; i < seqs_from_db.size(); i++) {
			
			long startTime = System.currentTimeMillis();
			SmithWatermanMatrix swm = new SmithWatermanMatrix(seqToTest,
					seqs_from_db.get(i), sm);
			long duration = System.currentTimeMillis() - startTime;
			total_matrix += duration;
			
//			System.out.println(swm);
//			swm.printTracebackPath();
//			System.out.println(swm.getGlobal_max());
			startTime = System.currentTimeMillis();
			swm.setPaths(swm.max_i,swm.max_j);
			duration = System.currentTimeMillis() - startTime;
			total_traceback += duration;
//			System.out.println(swm.getAligned_seq1());
//			System.out.println(swm.getAligned_seq2());
			System.out.println(swm.getAligned_seq1());
			System.out.println(swm.getAligned_seq2());
			
		}
//		System.out.println("Matrix creation: " + total_matrix/1000.0);
//		System.out.println("Traceback: " + total_traceback/1000.0);
//		System.out.println("Ration: " + total_matrix/total_traceback);
//		
//		System.out.println("Done");
//		Toolkit.getDefaultToolkit().beep();
	}
}
