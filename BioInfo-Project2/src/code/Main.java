package code;

import java.util.Map;

import javax.xml.ws.handler.MessageContext.Scope;

public class Main {

	// font: http://www.ncbi.nlm.nih.gov/Class/FieldGuide/BLOSUM62.txt
	static int[][] BLOSUM62 = {
			{ 4, -1, -2, -2, 0, -1, -1, 0, -2, -1, -1, -1, -1, -2, -1, 1, 0,
					-3, -2, 0, -2, -1, 0, -4 },
			{ -1, 5, 0, -2, -3, 1, 0, -2, 0, -3, -2, 2, -1, -3, -2, -1, -1, -3,
					-2, -3, -1, 0, -1, -4 },
			{ -2, 0, 6, 1, -3, 0, 0, 0, 1, -3, -3, 0, -2, -3, -2, 1, 0, -4, -2,
					-3, 3, 0, -1, -4 },
			{ -2, -2, 1, 6, -3, 0, 2, -1, -1, -3, -4, -1, -3, -3, -1, 0, -1,
					-4, -3, -3, 4, 1, -1, -4 },
			{ 0, -3, -3, -3, 9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1,
					-2, -2, -1, -3, -3, -2, -4 },
			{ -1, 1, 0, 0, -3, 5, 2, -2, 0, -3, -2, 1, 0, -3, -1, 0, -1, -2,
					-1, -2, 0, 3, -1, -4 },
			{ -1, 0, 0, 2, -4, 2, 5, -2, 0, -3, -3, 1, -2, -3, -1, 0, -1, -3,
					-2, -2, 1, 4, -1, -4 },
			{ 0, -2, 0, -1, -3, -2, -2, 6, -2, -4, -4, -2, -3, -3, -2, 0, -2,
					-2, -3, -3, -1, -2, -1, -4 },
			{ -2, 0, 1, -1, -3, 0, 0, -2, 8, -3, -3, -1, -2, -1, -2, -1, -2,
					-2, 2, -3, 0, 0, -1, -4 },
			{ -1, -3, -3, -3, -1, -3, -3, -4, -3, 4, 2, -3, 1, 0, -3, -2, -1,
					-3, -1, 3, -3, -3, -1, -4 },
			{ -1, -2, -3, -4, -1, -2, -3, -4, -3, 2, 4, -2, 2, 0, -3, -2, -1,
					-2, -1, 1, -4, -3, -1, -4 },
			{ -1, 2, 0, -1, -3, 1, 1, -2, -1, -3, -2, 5, -1, -3, -1, 0, -1, -3,
					-2, -2, 0, 1, -1, -4 },
			{ -1, -1, -2, -3, -1, 0, -2, -3, -2, 1, 2, -1, 5, 0, -2, -1, -1,
					-1, -1, 1, -3, -1, -1, -4 },
			{ -2, -3, -3, -3, -2, -3, -3, -3, -1, 0, 0, -3, 0, 6, -4, -2, -2,
					1, 3, -1, -3, -3, -1, -4 },
			{ -1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4, 7, -1,
					-1, -4, -3, -2, -2, -1, -2, -4 },
			{ 1, -1, 1, 0, -1, 0, 0, 0, -1, -2, -2, 0, -1, -2, -1, 4, 1, -3,
					-2, -2, 0, 0, 0, -4 },
			{ 0, -1, 0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1, 1, 5,
					-2, -2, 0, -1, -1, 0, -4 },
			{ -3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1, 1, -4, -3,
					-2, 11, 2, -3, -4, -3, -2, -4 },
			{ -2, -2, -2, -3, -2, -1, -2, -3, 2, -1, -1, -2, -1, 3, -3, -2, -2,
					2, 7, -1, -3, -2, -1, -4 },
			{ 0, -3, -3, -3, -1, -2, -2, -3, -3, 3, 1, -2, 1, -1, -2, -2, 0,
					-3, -1, 4, -3, -2, -1, -4 },
			{ -2, -1, 3, 4, -3, 0, 1, -1, 0, -3, -4, 0, -3, -3, -2, 0, -1, -4,
					-3, -3, 4, 1, -1, -4 },
			{ -1, 0, 0, 1, -3, 3, 4, -2, 0, -3, -3, 1, -1, -3, -1, 0, -1, -3,
					-2, -2, 1, 4, -1, -4 },
			{ 0, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, 0, 0,
					-2, -1, -1, -1, -1, -1, -4 },
			{ -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4,
					-4, -4, -4, -4, -4, -4, -4, 1, } };

	// http://www.ncbi.nlm.nih.gov/IEB/ToolBox/C_DOC/lxr/source/data/BLOSUM50
	static int[][] BLOSUM50 = {
			{ 5, -2, -1, -2, -1, -1, -1, 0, -2, -1, -2, -1, -1, -3, -1, 1, 0,
					-3, -2, 0, -2, -2, -1, -1, -5 },
			{ -2, 7, -1, -2, -4, 1, 0, -3, 0, -4, -3, 3, -2, -3, -3, -1, -1,
					-3, -1, -3, -1, -3, 0, -1, -5 },
			{ -1, -1, 7, 2, -2, 0, 0, 0, 1, -3, -4, 0, -2, -4, -2, 1, 0, -4,
					-2, -3, 5, -4, 0, -1, -5 },
			{ -2, -2, 2, 8, -4, 0, 2, -1, -1, -4, -4, -1, -4, -5, -1, 0, -1,
					-5, -3, -4, 6, -4, 1, -1, -5 },
			{ -1, -4, -2, -4, 13, -3, -3, -3, -3, -2, -2, -3, -2, -2, -4, -1,
					-1, -5, -3, -1, -3, -2, -3, -1, -5 },
			{ -1, 1, 0, 0, -3, 7, 2, -2, 1, -3, -2, 2, 0, -4, -1, 0, -1, -1,
					-1, -3, 0, -3, 4, -1, -5 },
			{ -1, 0, 0, 2, -3, 2, 6, -3, 0, -4, -3, 1, -2, -3, -1, -1, -1, -3,
					-2, -3, 1, -3, 5, -1, -5 },
			{ 0, -3, 0, -1, -3, -2, -3, 8, -2, -4, -4, -2, -3, -4, -2, 0, -2,
					-3, -3, -4, -1, -4, -2, -1, -5 },
			{ -2, 0, 1, -1, -3, 1, 0, -2, 10, -4, -3, 0, -1, -1, -2, -1, -2,
					-3, 2, -4, 0, -3, 0, -1, -5 },
			{ -1, -4, -3, -4, -2, -3, -4, -4, -4, 5, 2, -3, 2, 0, -3, -3, -1,
					-3, -1, 4, -4, 4, -3, -1, -5 },
			{ -2, -3, -4, -4, -2, -2, -3, -4, -3, 2, 5, -3, 3, 1, -4, -3, -1,
					-2, -1, 1, -4, 4, -3, -1, -5 },
			{ -1, 3, 0, -1, -3, 2, 1, -2, 0, -3, -3, 6, -2, -4, -1, 0, -1, -3,
					-2, -3, 0, -3, 1, -1, -5 },
			{ -1, -2, -2, -4, -2, 0, -2, -3, -1, 2, 3, -2, 7, 0, -3, -2, -1,
					-1, 0, 1, -3, 2, -1, -1, -5 },
			{ -3, -3, -4, -5, -2, -4, -3, -4, -1, 0, 1, -4, 0, 8, -4, -3, -2,
					1, 4, -1, -4, 1, -4, -1, -5 },
			{ -1, -3, -2, -1, -4, -1, -1, -2, -2, -3, -4, -1, -3, -4, 10, -1,
					-1, -4, -3, -3, -2, -3, -1, -1, -5 },
			{ 1, -1, 1, 0, -1, 0, -1, 0, -1, -3, -3, 0, -2, -3, -1, 5, 2, -4,
					-2, -2, 0, -3, 0, -1, -5 },
			{ 0, -1, 0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1, 2, 5,
					-3, -2, 0, 0, -1, -1, -1, -5 },
			{ -3, -3, -4, -5, -5, -1, -3, -3, -3, -3, -2, -3, -1, 1, -4, -4,
					-3, 15, 2, -3, -5, -2, -2, -1, -5 },
			{ -2, -1, -2, -3, -3, -1, -2, -3, 2, -1, -1, -2, 0, 4, -3, -2, -2,
					2, 8, -1, -3, -1, -2, -1, -5 },
			{ 0, -3, -3, -4, -1, -3, -3, -4, -4, 4, 1, -3, 1, -1, -3, -2, 0,
					-3, -1, 5, -3, 2, -3, -1, -5 },
			{ -2, -1, 5, 6, -3, 0, 1, -1, 0, -4, -4, 0, -3, -4, -2, 0, 0, -5,
					-3, -3, 6, -4, 1, -1, -5 },
			{ -2, -3, -4, -4, -2, -3, -3, -4, -3, 4, 4, -3, 2, 1, -3, -3, -1,
					-2, -1, 2, -4, 4, -3, -1, -5 },
			{ -1, 0, 0, 1, -3, 4, 5, -2, 0, -3, -3, 1, -1, -4, -1, 0, -1, -2,
					-2, -3, 1, -3, 5, -1, -5 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
					-1, -1, -1, -1, -1, -1, -1, -1, -5 },
			{ -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5,
					-5, -5, -5, -5, -5, -5, -5, -5, 1 } };

	static int GAP_PENALTY = 8;

	static int SEQ_LENGTH = 20;
	static int M_SIZE = SEQ_LENGTH + 1;

	static StringBuilder new_s1 = new StringBuilder();
	static StringBuilder new_s2 = new StringBuilder();

	public static void main(String[] args) {

		Map<Character, Integer> seqs = SequenceUtility.createAlphabetSet();
		
		char c1 = 'W';
		char c2 = 'W';
		
		System.out.println(BLOSUM50[seqs.get(c1)][seqs.get(c2)]);

		// SequenceUtility.printMatrix(BLOSUM62, seqs);

		String root = "ENQDAAEYPKYAYGWGLSID";
		String s1 = "EASETNPFAAGKTVWSMFYP";
		 String s2 = "ESQGDAKNLTYCYCWFGNAI";
		 String s1_1 = "SYSDYKCDPMQKAYWLNLYE";
		 String s1_2 = "QASTVLAID-SQRGWGGGQS";

		int[][] optAlign = makeOptimalAlign(s1, s1_2, BLOSUM50, seqs);

		for (int i = 0; i < M_SIZE; i++) {
			for (int j = 0; j < M_SIZE; j++) {
				System.out.print(optAlign[i][j] + "\t");
			}
			System.out.println();
		}

		buildPath(optAlign, seqs, root, s1, BLOSUM50);

		new_s1 = new_s1.reverse();
		new_s2 = new_s2.reverse();

		SequenceUtility.printSequence(new_s1);
		SequenceUtility.printSequence(new_s2);

		SequenceUtility.getScore(new_s1, new_s2, BLOSUM50, seqs);

	}

	private static void buildPath(int[][] optAlign,
			Map<Character, Integer> seqs, String s1, String s2,
			int[][] scoreMatrix) {
		int match, delete, insert;

		int i = SEQ_LENGTH;
		int j = SEQ_LENGTH;
		int aux_i = s1.length() - 1;
		int aux_j = s2.length() - 1;

		while (i >= 1 || j >= 1) {

			char s1_char = s1.charAt(aux_i);
			char s2_char = s2.charAt(aux_j);

			// System.out.print("("+i+") " + " (" + j+")\t");

			if (i == 0) {
				j--;
				new_s1.append('-');
				new_s2.append(s2_char);
			} else if (j == 0) {
				i--;
				new_s1.append(s1_char);
				new_s2.append('-');
			} else {
				match = optAlign[i - 1][j - 1];
				delete = optAlign[i - 1][j];
				insert = optAlign[i][j - 1];

				int max = getMax(match, insert, delete);

				if (max == match) {
					i--;
					j--;
					aux_i--;
					aux_j--;
					new_s1.append(s1_char);
					new_s2.append(s2_char);
				} else if (max == delete) {
					i--;
					aux_i--;
					new_s1.append(s1_char);
					new_s2.append('-');
				} else {
					j--;
					aux_j--;
					new_s1.append('-');
					new_s2.append(s2_char);
				}
			}
		}

		// System.out.println("Total: " + total);

	}

	private static int[][] makeOptimalAlign(String s1, String s2,
			int[][] scoringMatrix, Map<Character, Integer> seqs) {

		int[][] m = new int[M_SIZE][M_SIZE];

		// Fill the first row and the first column with 0
		for (int i = 0; i < M_SIZE; i++)
			m[i][0] = -GAP_PENALTY * i;
		for (int j = 0; j < M_SIZE; j++)
			m[0][j] = -GAP_PENALTY * j;

		int match, delete, insert;

		for (int i = 1; i < M_SIZE; i++) {
			for (int j = 1; j < M_SIZE; j++) {

				char s1_char = s1.charAt(i - 1);
				char s2_char = s2.charAt(i - 1);

				int s1_key = seqs.get(s1_char);
				int s2_key = seqs.get(s2_char);

				match = m[i - 1][j - 1] + scoringMatrix[s1_key][s2_key];
				delete = m[i - 1][j] - GAP_PENALTY;
				insert = m[i][j - 1] - GAP_PENALTY;

				int max = getMax(match, insert, delete);

				m[i][j] = max;

			}
		}

		return m;

	}

	private static int getMax(int match, int insert, int delete) {
		// System.out.print("match: " + match + " insert: " + insert +
		// " delete: " + delete);
		int max = Math.max(Math.max(match, insert), delete);
		// System.out.println("\tMax: " + max);
		return max;
	}
}
