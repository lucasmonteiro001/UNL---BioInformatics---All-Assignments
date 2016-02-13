package src_code;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SmithWatermanMatrix {

	private static final Integer GAP_PENALTY = 8;
	private static final Integer THRESHOLD = 20;
	private Integer[][] SWmatrix;
	private Character[][] traceback;
	private Sequence s1;
	private Sequence s2;
	private ArrayList<StringBuilder> paths;
	private ScoringMatrix sm;
	private int numberOfHits = 0;
	private int numberOfMatchSequencesFound = 0;
	public int max_i, max_j, global_max = 0;
	private String aligned_seq1, aligned_seq2;
	
	public String getAligned_seq1() {
		return aligned_seq1;
	}

	public String getAligned_seq2() {
		return aligned_seq2;
	}

	public int getGlobal_max() {
		return global_max;
	}

	public int getNumberOfMatchSequencesFound() {
		return numberOfMatchSequencesFound;
	}

	private void setNumberOfMatchSequencesFound(int numberOfMatchSequencesFound) {
		this.numberOfMatchSequencesFound = numberOfMatchSequencesFound;
	}

	public int getNumberOfHits() {
		return numberOfHits;
	}

	private void setNumberOfHits(int numberOfHits) {
		this.numberOfHits = numberOfHits;
	}

	public SmithWatermanMatrix(Sequence s1, Sequence s2, ScoringMatrix sm) {
		setS1(s1);
		setS2(s2);
		setSWmatrix(sm, s1, s2);
		this.sm = sm;
		setPaths();
	}

	/*
	 * Discover the traceback path of the best alignment
	 */
	public void setPaths(int idx_i, int idx_j) {
		ArrayList<StringBuilder> paths = new ArrayList<StringBuilder>();

		// If the position being analyzed came from a valid place
		if (traceback[idx_i][idx_j] != '*') {

			int aux_i = idx_i;
			int aux_j = idx_j;

			char c = traceback[aux_i][aux_j];

			// String that will store the path found
			StringBuilder sb = new StringBuilder();

			/* Loop through the possible path from that position */
			while (c != '*') {
				sb.append(aux_j + "," + aux_i + "-");
				if (c == 'D') {
					aux_i--;
					aux_j--;
					c = traceback[aux_i][aux_j];
				} else if (c == 'L') {
					aux_i--;
					c = traceback[aux_i][aux_j];
				} else if (c == 'U') {
					aux_j--;
					c = traceback[aux_i][aux_j];
				} else {
					break;
				}
			}

			paths.add(sb);

			// After finding the path, split it into tokens that
			// will represent the position
			String[] tokens = sb.toString().split("-");

			StringBuilder s1 = new StringBuilder();
			StringBuilder s2 = new StringBuilder();

			String[] pos = tokens[0].split(",");

			// Add the first element to the strings
			s1.insert(0, getS1().getSeq().charAt(Integer.parseInt(pos[1]) - 1));
			s2.insert(0, getS2().getSeq().charAt(Integer.parseInt(pos[0]) - 1));

			int token_size = 0;
			for (@SuppressWarnings("unused")
			String token : tokens)
				token_size++;
			
			// Do the process of discovering where the max value came from 
			// in order to build the best alignment
			for (int count = 1; count < token_size; count++) {
				pos = tokens[count].split(",");

				int j = Integer.parseInt(pos[0]);
				int i = Integer.parseInt(pos[1]);

				if (traceback[i][j] == 'D') {
					s1.insert(0, getS1().getSeq().charAt(i - 1));
					s2.insert(0, getS2().getSeq().charAt(j - 1));
				} else if (traceback[i][j] == 'L') {
					s1.insert(0, getS1().getSeq().charAt(i - 1));
					s2.insert(0, "-");
				} else { // if it's equal to U
					s1.insert(0, getS1().getSeq().charAt(i - 1));
					s2.insert(0, "-");
				}
			}
			aligned_seq1 = s1.toString();
			aligned_seq2 = s2.toString();
			
		}
	}

	private void setPaths() {
		ArrayList<StringBuilder> paths = new ArrayList<StringBuilder>();

		for (int j = s2.getSeq().length(); j >= 0; j--) {
			for (int i = s1.getSeq().length(); i >= 0; i--) {

				// If the position being analyzed came from a valid place
				if (traceback[i][j] != '*') {

					int aux_i = i;
					int aux_j = j;

					char c = traceback[aux_i][aux_j];

					// String that will store the path found
					StringBuilder sb = new StringBuilder();

					/* Loop through the possible path from that position */
					while (c != '*') {
						sb.append(aux_j + "," + aux_i + "-");
						if (c == 'D') {
							aux_i--;
							aux_j--;
							c = traceback[aux_i][aux_j];
						} else if (c == 'L') {
							aux_i--;
							c = traceback[aux_i][aux_j];
						} else if (c == 'U') {
							aux_j--;
							c = traceback[aux_i][aux_j];
						} else {
							break;
						}
					}
					paths.add(sb);
				}
			}
		}

		// array that marks all the positions that do not need to enter in the
		// final search
		int[] doNotSearchIn = new int[paths.size()];
		for (int i = 0; i < doNotSearchIn.length; i++) {
			doNotSearchIn[i] = 0;
		}

		// Discover all unique paths
		// FILTER
		for (int i = 0; i < paths.size(); i++) {
			for (int j = 0; j < paths.size(); j++) {
				if (i != j && doNotSearchIn[i] != 1 && doNotSearchIn[j] != 1) {
					// if the string in j is a substring of i, j won't enter in
					// a unique path
					if (paths.get(i).toString()
							.contains(paths.get(j).toString())) {
						doNotSearchIn[j] = 1;
					}
				}
			}
		}

		ArrayList<StringBuilder> allUniquePaths = new ArrayList<StringBuilder>();

		for (int i = 0; i < paths.size(); i++) {
			if (doNotSearchIn[i] == 0)
				allUniquePaths.add(paths.get(i));
		}

		this.paths = allUniquePaths;

	}

	public ArrayList<StringBuilder> getPaths() {
		return paths;

	}

	public Sequence getS1() {
		return s1;
	}

	public void setS1(Sequence s1) {
		this.s1 = s1;
	}

	public Sequence getS2() {
		return s2;
	}

	public void setS2(Sequence s2) {
		this.s2 = s2;
	}

	public Integer[][] getSWmatrix() {
		return SWmatrix;
	}

	public void setSWmatrix(ScoringMatrix sm, Sequence s1, Sequence s2) {

		int s1length = s1.getSeq().length() + 1;
		int s2length = s2.getSeq().length() + 1;

		// SmithWaterman matrix
		SWmatrix = new Integer[s1length][s2length];

		// Matrix that is going to store the path that was used to generate the
		// SWmatrix
		traceback = new Character[s1length][s2length];

		// Fill out all the positions of the matrix traceback with *
		for (int i = 0; i < s1length; i++)
			for (int j = 0; j < s2length; j++)
				traceback[i][j] = '*';

		// Fill the first row and the first column with 0
		for (int i = 0; i < s1length; i++)
			SWmatrix[i][0] = 0;
		for (int j = 0; j < s2length; j++)
			SWmatrix[0][j] = 0;

		int match, delete, insert, zero;

		// Calculate the exact value in each cell using dynamic programming
		for (int i = 1; i < s1length; i++) {
			for (int j = 1; j < s2length; j++) {

				char el1 = s1.getSeq().charAt(i - 1);
				char el2 = s2.getSeq().charAt(j - 1);

				zero = 0;
				match = SWmatrix[i - 1][j - 1]
						+ sm.getScoreValueFromPair(el1, el2);
				delete = SWmatrix[i - 1][j] - GAP_PENALTY;
				insert = SWmatrix[i][j - 1] - GAP_PENALTY;

				int max = getMax(match, insert, delete, zero);

				// Record the path:
				// D means that it came from the diagonal, L -> left, U -> up
				if (max == match)
					traceback[i][j] = 'D';
				else if (max == delete)
					traceback[i][j] = 'L';
				else if (max == insert)
					traceback[i][j] = 'U';

				SWmatrix[i][j] = max;
				if (global_max < max) {
					global_max = max;
					max_i = i;
					max_j = j;
				}
			}
		}
	}

	public Character[][] getTracebackMatrix() {
		return traceback;
	}

	public void printTracebackPath() {
		for (int j = 0; j < s2.getSeq().length() + 1; j++) {
			for (int i = 0; i < s1.getSeq().length() + 1; i++) {
				System.out.print("\t" + traceback[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int getMax(int match, int insert, int delete, int zero) {
		// System.out.print("match: " + match + " insert: " + insert +
		// " delete: " + delete);
		int max = Math.max(Math.max(match, insert), Math.max(delete, zero));
		// System.out.println("\tMax: " + max);
		return max;
	}

	@Override
	public String toString() {
		StringBuilder sbResult = new StringBuilder();

		int s1length = s1.getSeq().length() + 1;
		int s2length = s2.getSeq().length() + 1;

		for (int i = 0; i < s1length; i++) {
			if (i > 0)
				System.out.print("\t" + s1.getSeq().charAt(i - 1));
			else
				System.out.print("\t");
		}
		System.out.println();

		for (int j = 0; j < s2length; j++) {
			if (j > 0)
				System.out.print(s2.getSeq().charAt(j - 1));
			for (int i = 0; i < s1length; i++) {
				System.out.print("\t" + SWmatrix[i][j]);
			}
			System.out.println();
		}

		return sbResult.toString();
	}

	private void getScore(int idx, PrintWriter writer) {
		String s = getPaths().get(idx).toString();

		String[] tokens = s.split("-");

		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();

		int flag = 1;
		int val_i = 0, val_j = 0;

		for (String token : tokens) {
			String[] pos = token.split(",");

			int j = Integer.parseInt(pos[0]);
			int i = Integer.parseInt(pos[1]);

			if (flag == 1) {
				val_i = i;
				val_j = j;
				flag = 0;
			}

			if (traceback[i][j] == 'D') {
				s1.insert(0, getS1().getSeq().charAt(i - 1));
				s2.insert(0, getS2().getSeq().charAt(j - 1));
			} else if (traceback[i][j] == 'U') {
				s1.insert(0, "-");
				s2.insert(0, getS2().getSeq().charAt(j - 1));
			} else { // if it's equal to L
				s1.insert(0, getS1().getSeq().charAt(i - 1));
				s2.insert(0, "-");
			}

		}

		// If found I sequence greater than the threshold
		if (SWmatrix[val_i][val_j] >= THRESHOLD) {

			// This sequence increment the total number of sequences found
			setNumberOfMatchSequencesFound(getNumberOfMatchSequencesFound() + 1);

			// Hits per sequence
			int hits = 0;

			StringBuilder matchSequence = new StringBuilder();

			// Build the match query
			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) == s2.charAt(i)) {
					matchSequence.append(s1.charAt(i));
					setNumberOfHits(getNumberOfHits() + 1);
					hits++;
				} else if (sm.getScoreValueFromPair(s1.charAt(i), s2.charAt(i)) > 0) {
					setNumberOfHits(getNumberOfHits() + 1);
					hits++;
					matchSequence.append('+');
				} else {
					matchSequence.append(' ');
				}
			}

			writer.println(s1);
			writer.println(matchSequence);
			writer.println(s2);
			writer.println("Hits = "
					+ hits
					+ "/"
					+ matchSequence.length()
					+ " ("
					+ String.format("%.2f",
							((double) hits / matchSequence.length()) * 100)
					+ "%)");
			writer.println("Score = " + SWmatrix[val_i][val_j] + "\n");

		}

	}

	public void printAllMatches(PrintWriter writer) {
		for (int i = 0; i < getPaths().size(); i++)
			getScore(i, writer);
	}

}
