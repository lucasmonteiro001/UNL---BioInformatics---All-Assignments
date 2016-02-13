package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SequenceUtility {
	
	private static int AA_LENGTH = 20;
	
	public static <E> void printSequence(StringBuilder sq) {
		for (int i = 0; i < sq.length(); i++)
			System.out.print(sq.charAt(i));
		System.out.println();
	}
	
	public static <E> void printSequence(ArrayList<E> sq) {
		Iterator<E> i = sq.iterator();
		while (i.hasNext()) {
			Object element = i.next();
			System.out.print(element);
		}
		System.out.println();
	}
	
	public static Map<Character, Integer> createAlphabetSet() {
		Map<Character, Integer> s = new HashMap<Character, Integer>();
		
		s.put('A', 0);
		s.put('R', 1);
		s.put('N', 2);
		s.put('D', 3);
		s.put('C', 4);
		s.put('Q', 5);
		s.put('E', 6);
		s.put('G', 7);
		s.put('H', 8);
		s.put('I', 9);
		s.put('L', 10);
		s.put('K', 11);
		s.put('M', 12);
		s.put('F', 13);
		s.put('P', 14);
		s.put('S', 15);
		s.put('T', 16);
		s.put('W', 17);
		s.put('Y', 18);
		s.put('V', 19);
		s.put('-', 20);
		
		return s;
		
	}

	public static void printMatrix(int[][] m, Map<Character, Integer> seqs) {
		
		for(int i = 0; i <= AA_LENGTH; i++) 
			System.out.printf("\t%c", (char)MapUtility.getKeyByValue(seqs, i));
		System.out.println();
		
		for(int i = 0; i <= AA_LENGTH; i++) {
			
			System.out.print((char)MapUtility.getKeyByValue(seqs, i));
			
			for(int j = 0; j<= AA_LENGTH; j++) {
				if(i != j)
					System.out.printf("\t%d", m[i][j]);
				else
					System.out.printf("\t[%d]", m[i][j]);
			}
			System.out.println("\n");
		}
	}

	public static void getScore(StringBuilder new_s1, StringBuilder new_s2, int[][] bLOSUM50, Map<Character, Integer> seqs) {
		int total = 0;
		
		for(int i = 0; i < new_s1.length(); i++) {
			total += bLOSUM50[seqs.get(new_s1.charAt(i))][seqs.get(new_s2.charAt(i))];
		}
		
		System.out.println("Total score: " + total);
		
	}
}
