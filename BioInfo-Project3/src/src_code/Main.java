package src_code;

import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

	private static final String PATH_TO_S1 = "data/Seq1.txt";
	private static final String PATH_TO_S2 = "data/Seq2.txt";
	private static final String DATABASE_PATH = "data/database.txt";
	private static final int WORDS_ANALYSED = 50;
	private static final String DESTINATION = "data/output.txt";

	public static void main(String[] args) {

		/*
		 * Read the 2 sequences from the 2 files in order to be compared to the
		 * database stored in database.txt
		 */
		Sequence s1 = Sequence.readSequenceFromFile(PATH_TO_S1);
		Sequence s2 = Sequence.readSequenceFromFile(PATH_TO_S2);

		Sequence seqToTest = s2;

		ArrayList<Sequence> seqs_from_db = Sequence
				.readMultipleSequencesFromFile(DATABASE_PATH);

		// Sequence.printSequences(seqs_from_db);

		// Initialize the scoring matrix that will be used to compute the score
		// between pairs
		ScoringMatrix sm = new ScoringMatrix(ScoringMatrix.PAM70);

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

		System.out.println("Please wait!\nRunning...");
		// Perform all matches between Seq1 and the database
		for (int i = 0; i < seqs_from_db.size(); i++) {

			// Number of letters in the sequence
			int numberOfLetters = seqs_from_db.get(i).getSeq().length();

			if (numberOfLetters <= WORDS_ANALYSED) {
				
				long startTime = System.currentTimeMillis();

				SmithWatermanMatrix swm = new SmithWatermanMatrix(seqToTest,
						seqs_from_db.get(i), sm);

				writer.println("###Analysing Seq1:\t\t\t\t>"
						+ seqToTest.getName() + "|" + seqToTest.getId() + "|"
						+ seqToTest.getDescription());
				writer.println("###With Seq from database:\t\t>"
						+ seqs_from_db.get(i).getName() + "|"
						+ seqs_from_db.get(i).getId() + "|"
						+ seqs_from_db.get(i).getDescription() + "\n");

				swm.printAllMatches(writer);

				long duration = System.currentTimeMillis() - startTime;

				writer.println("###Number of match sequences: "
						+ swm.getNumberOfMatchSequencesFound());
				writer.println("###Number of hits in all sequences: "
						+ swm.getNumberOfHits());
				writer.println("###Time in seconds: " + (duration / 1000.0)
						+ "\n\n");
			}
		}

		writer.close();
		System.out
				.println("The program executed successfuly!\n"
						+ "Check the result at the Seq1_MATCH_Database.txt and Seq2_MATCH_Database.txt files "
						+ "inside the <data> folder");

		Toolkit.getDefaultToolkit().beep();
	}
}
