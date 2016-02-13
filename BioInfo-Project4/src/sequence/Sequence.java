package sequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sequence {
	
	private String name;
	private int id;
	private String description;
	private String seq;
	
	public Sequence(String name, int id, String description, String seq) {
		setName(name);
		setId(id);
		setDescription(description);
		setSeq(seq);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getSeq() {
		return seq;
	}

	public static Sequence readSequenceFromFile(String pathToFile) {
		Sequence s = null;
		
		// open the file
		Scanner sc = null;
    	try {
			sc = new Scanner(new File(pathToFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	while(sc.hasNext()) {
    		String line = sc.nextLine();
    		String tokens[] = line.split("\\|");
    		
    		String name = tokens[0].substring(1);
    		int id 	= Integer.parseInt(tokens[1]);
    		String description = tokens[2];
    		
    		String seq = sc.nextLine();
    		
    		s = new Sequence(name, id, description, seq);
    	}
    	
    	sc.close();
    	
    	if(s != null) {
    		return s;
    	} else {
    		System.err.println("Wrong input");
    		System.exit(1);
    	}
		
    	return s;
	}
	
	@Override
	public String toString() {
		return ">" + getName() + "|" + getId() + "|" + getDescription() + "\n" +
				getSeq();
	}

	public static ArrayList<Sequence> readMultipleSequencesFromFile(
			String databasePath) {
		
		ArrayList<Sequence> seqs =new ArrayList<Sequence>();
		Sequence s = null;
		
		// open the file
				Scanner sc = null;
		    	try {
					sc = new Scanner(new File(databasePath));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		    	
		    	String line = null;
		    	
		    	if(sc.hasNext())
		    		line = sc.nextLine();
		    	
		    	while(sc.hasNext()) {
		    		String tokens[] = line.split("\\|");
		    		
		    		String name = tokens[0].substring(1);
		    		int id 	= Integer.parseInt(tokens[1]);
		    		String description = tokens[2] + "|" + tokens[3] + "|" + tokens[4];
		    		
		    		// get the next line
		    		String seq = sc.nextLine();
		    		
		    		// get the next char to check if it's a new sequence or not
		    		String nextLine = null;
		    		
		    		if(sc.hasNext())
		    			nextLine = sc.nextLine();
		    		
		    		while(nextLine.charAt(0) != '>') {
		    			seq += nextLine;
		    			if(sc.hasNext())
		    				nextLine = sc.nextLine();
		    			else 
		    				break;
		    		}
		    		
		    		s = new Sequence(name, id, description, seq);
		    		
		    		line = nextLine;
		    		
		    		// add the found sequence to the list of sequences
		    		seqs.add(s);
		    	}
		    	
		    	sc.close();
		    	
		    	if(s != null) {
		    		return seqs;
		    	} else {
		    		System.err.println("Wrong input");
		    		System.exit(1);
		    	}
		
		
		return seqs;
	}
	
	static public void printSequences(ArrayList<Sequence> seqs) {
		for(Sequence s : seqs)
			System.out.println(s);
	}

}
