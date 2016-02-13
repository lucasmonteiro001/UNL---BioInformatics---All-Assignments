public class DNA {
	private String information;
	private String sequence;
	private String ID;
	
	public DNA (String information, String sequence, String ID) {
		this.setInformation(information);
		this.setSequence(sequence);
		this.setID(ID);
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
}
