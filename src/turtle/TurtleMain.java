/**
 * This class runs a program written in the Turtle Graphics language. It uses the TurtleParser class
 * to ensure that the code is valid, and to get the commands it has to run. It then converts the commands
 * to Java code and executes them. The TurtleMain class will not create the TurtleGUI if the program 
 * is not written with valid syntax.
 * 
 * @author Patrick Liem, Wenkai Zhao, Matthew Murch, Lei Liu
 * 
 */

package turtle;

import java.util.ArrayList;
import java.util.HashMap;

public class TurtleMain {

	private static ArrayList<String> commands;
	private static DrawableTurtle turtle;
	
	private static HashMap<String, Integer> variables = new HashMap<String, Integer>();
	
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
	 * Converts the turtle code into real Java code and executes it.
	 * Precondition: The turtle code must not contain syntax errors.
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
				int distance;
				try {
					distance = Integer.parseInt(commands.get(0));
				} catch (NumberFormatException e) {
					distance = variables.get(commands.get(0));
				}
				commands.remove(0);
				turtle.forward(distance);
				break;
			case "turn":
				int angle;
				try {
					angle = Integer.parseInt(commands.get(0));
				} catch (NumberFormatException e) {
					angle = variables.get(commands.get(0));
				}
				commands.remove(0);
				turtle.turn(angle);
				break;
			case "loop":
				int max;
				try {
					max = Integer.parseInt(commands.get(0));
				} catch (NumberFormatException e) {
					max = variables.get(commands.get(0));
				}
				commands.remove(0);
				ArrayList<String> chunk = getBlock();
				for (int i = 0; i < max; i++) {
					doCommands(copy(chunk));
				}
			case "end":
				break;
			case "programEnd":
				break;
			default:
				String variable = currentCommand;
				//remove equals sign
				commands.remove(0);
				int value = Integer.parseInt(commands.get(0));
				commands.remove(0);
				variables.put(variable, value);
				
			}
		}
	}
	
	/**
	 * Helper method that gets a block of code and puts it into an ArrayList.
	 * @return An ArrayList containing the block of code
	 */
	private static ArrayList<String> getBlock() {
		ArrayList<String> chunk = new ArrayList<String>();
		
		while (!commands.get(0).equals("end")) {
			chunk.add(commands.get(0));
			commands.remove(0);
		}
		
		return chunk;
	}
	
	
	/**
	 * Helper method that creates a deep copy of an ArrayList<String>
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
	

