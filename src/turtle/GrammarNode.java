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
	
	public void leafPreorderTraversal(ArrayList<String> currentLeaves) {
		
		if (children.isEmpty()) {
			currentLeaves.add(data);
		}
		
		for (int i = 0; i < children.size(); i++) {
			children.get(i).leafPreorderTraversal(currentLeaves);
		}
		
	}
}
