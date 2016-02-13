import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Sequence {

	// Read all the sequences in the database
	public static ArrayList<DNA> ReadAllSequences(String filename) {
		int counter = 0;
		ArrayList<DNA> dna = new ArrayList<DNA> ();
		
		try {
			Scanner scanner = new Scanner (new FileReader(filename));
			String aux = scanner.nextLine();

			while (scanner.hasNext()) {
				String information = new String();
				String sequence = new String();

				if (aux.contains(">") == true ) {
					while (aux.contains("|")){
						aux = aux.substring(aux.indexOf("|") + 1, aux.length() - 1);
					}
					String [] split = aux.split(" ");
					
					information = split[0];
				}
				aux = scanner.nextLine();
				while (aux.contains(">") != true ) {
					sequence += aux;
					if (scanner.hasNext()) {
						aux = scanner.nextLine();
					} else {
						break;
					}
				}		
				dna.add(new DNA(information, sequence, String.valueOf(counter++)));
			}
			
			// Closing File
			if (scanner != null) {
				scanner.close();
			}
			

			// CHANGE HERE IF YOU WANT FEW SEQUENCES

			// for (int i = dna.size() - 1; i > 2; i--) {
			// 	dna.remove(i);
			// }
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		return dna;
	}
}
// This line make it work