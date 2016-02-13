import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tree {

	private static ArrayList<TreeNode> listOfNodes = new ArrayList<TreeNode> ();
	private static TreeNode head = null;

	public static TreeNode getHead() {
		return head;
	}

	public static void setHead(TreeNode head) {
		Tree.head = head;
	}

	public static void getTree(FileWriter writer) throws IOException {
		writer.write("(" + head.getNodeName());
		printTree(head, writer);
		writer.write( ");");
	}

	private static void printTree(TreeNode node, FileWriter writer) throws IOException {
		if (node == null){
			return;
		}
		
		if (node.getLeft() != null && node.getRight() != null) {
			writer.write("(");

			if (node.getLeft() != null) {
				writer.write(node.getLeft().toString());
				printTree(node.getLeft(), writer);
			}

			if (node.getRight() != null) {
				
				if (node.getRight() != null) {
					writer.write(",");
				}
				
				writer.write(node.getRight().toString());
				printTree(node.getRight(), writer);
			}
			writer.write(")");
		}
	}

	public static TreeNode searchNode (String key) {
		for (TreeNode tn : listOfNodes) {
			if (tn.getNodeName().compareTo(key) == 0) {
				return tn;
			}
		}
		return null;
	}
	public static ArrayList<TreeNode> getListOfNodes() {
		return listOfNodes;
	}

	public static void setListOfNodes(ArrayList<TreeNode> listOfNodes) {
		Tree.listOfNodes = listOfNodes;
	}
	
}
