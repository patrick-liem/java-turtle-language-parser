/**
 * This class is a special node that is used for building the Abstract Syntax Tree of a Turtle Graphics program
 * @author Patrick Liem, Wenkai Zhao, Matthew Murch, Lei Liu
 */

package turtle;

import java.util.ArrayList;

public class GrammarNode {
	public String type;
	public String data;
	public ArrayList<GrammarNode> children;
	
	/**
	 * Constructs a new GrammarNode object, with the specified type and data. Type represents
	 * the terminal or nonterminal that the node contains. Data is the actual value of the thing the
	 * node contains, or null if the node represents a nonterminal.
	 * @param type The kind of nonterminal or terminal the node contains
	 * @param data The actual value of the terminal the node contains
	 */
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
