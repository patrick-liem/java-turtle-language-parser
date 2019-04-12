package turtle;

import java.util.ArrayList;

public class TurtleMain {

	private static ArrayList<String> commands;
	private static DrawableTurtle turtle;
	
	/**
	 * Main method.
	 * The program should be run with 1 parameter, which is the program to be read.
	 */
	public static void main(String args[]) {

		TurtleParser parser = new TurtleParser(args[0]);

		GrammarNode root = parser.checkProgram();
		
		
		// If program is valid
		if (root != null) {
			
			turtle = new DrawableTurtle();
			
			commands = root.leafPreorderTraversal();
			System.out.println(commands);
			
			doCommands(commands);
			
			turtle.draw();
		}

	}
	
	/**
	 * Gets a block of code and puts it into an ArrayList
	 * @return An ArrayList containing the block of code
	 */
	private static ArrayList<String> getBlock() {
		ArrayList<String> chunk = new ArrayList<String>();
		
		while (!commands.get(0).equals("end")) {
			chunk.add(commands.get(0));
			commands.remove(0);
		}
		System.out.println(chunk);
		
		return chunk;
	}
	
	/**
	 * Converts the turtle code into real Java code and executes it
	 * @param commands The turtle code commands to execute
	 */
	private static void doCommands(ArrayList<String> commands) {
		while (!commands.isEmpty()) {
			String currentCommand = commands.get(0);
			commands.remove(0);
			
			switch(currentCommand) {
			case "begin":
				break;
			case "forward":
				int distance = Integer.parseInt(commands.get(0));
				commands.remove(0);
				turtle.forward(distance);
				break;
			case "turn":
				int angle = Integer.parseInt(commands.get(0));
				commands.remove(0);
				turtle.turn(angle);
				break;
			case "loop":
				int max = Integer.parseInt(commands.get(0));
				commands.remove(0);
				ArrayList<String> chunk = getBlock();
				for (int i = 0; i < max; i++) {
					doCommands(copy(chunk));
				}
			case "end":
				break;
			case "programEnd":
				break;
				
			}
		}
	}
	
	
	/**
	 * Creates a deep copy of an ArrayList<String>
	 * 
	 * @param original Array to be copied
	 * @return The copy of the original array
	 */
	private static ArrayList<String> copy(ArrayList<String> original) {
		ArrayList<String> copy = new ArrayList<String>();
		for (String s: original) {
			copy.add(s);
		}
		return copy;
	}
}
	

