public class TreeNode {
	private TreeNode Left;
	private TreeNode Right;
	private double value;
	private String nodeName;
	
	public TreeNode(TreeNode left, TreeNode right, double value, String nodeName) {
		super();
		Left = left;
		Right = right;
		this.value = value;
		this.nodeName = nodeName;
	}
	
	public TreeNode getLeft() {
		return Left;
	}
	public TreeNode getRight() {
		return Right;
	}
	public double getValue() {
		return value;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setLeft(TreeNode left) {
		Left = left;
	}
	public void setRight(TreeNode right) {
		Right = right;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	@Override
	public String toString() {
		return this.getNodeName() + ":" + this.getValue();
	}
	
	
}
