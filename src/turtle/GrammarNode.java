/**
 * This class is a special node that is used for building the Abstract Syntax Tree of a Turtle Graphics program
 */

package turtle;

import java.util.ArrayList;

public class GrammarNode {
	public String type;
	public String data;
	public ArrayList<GrammarNode> children;
	
	public GrammarNode(String type, String data) {
		this.type = type;
		this.data = data;
		children = new ArrayList<GrammarNode>();
	}
	
	/**
	 * Recursive method that performs a preorder traversal of the tree (assuming the current node is the root)
	 * and returns each leaf it encounters.
	 * @return An ArrayList of the leaves of the tree.
	 */
	public ArrayList<String> leafPreorderTraversal() {
		ArrayList<String> leafValues = new ArrayList<String>();
		
		if (children.isEmpty()) {
			leafValues.add(data);
		}
		
		for (int i = 0; i < children.size(); i++) {
			children.get(i).leafPreorderTraversal(leafValues);
		}
		
		return leafValues;
	}
	
	/**
	 * Recursive helper method
	 * @param currentLeaves The leaves found so far
	 */
	private void leafPreorderTraversal(ArrayList<String> currentLeaves) {
		
		if (children.isEmpty()) {
			currentLeaves.add(data);
		}
		
		for (int i = 0; i < children.size(); i++) {
			children.get(i).leafPreorderTraversal(currentLeaves);
		}
		
	}
}
