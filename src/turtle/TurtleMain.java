package turtle;

import java.util.ArrayList;

public class TurtleMain {

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 */
	public static void main(String args[]) {

		switch(args[1]) {
		case "1":
			parseGrammar1(args[0]);
			break;
		case "2":
			parseGrammar2(args[0]);
			break;
		case "3":
			parseGrammar3(args[0]);
			break;
		default:
			System.out.println("Invalid command line arguments. "
					+ "Run the program like so: ./program <inputProgramName> <grammarNumber> "
					+ "inputProgramName should be a valid program. grammarNumber can be 1, 2, or 3.");	
		}

	}
	
	public static void parseGrammar1(String file) {
		Step1Parser parser1 = new Step1Parser("testProgramStep1.txt");

		GrammarNode root = parser1.checkProgram();
		
		
		// If program is valid
		if (root != null) {
			
			DrawableTurtle turtle = new DrawableTurtle();
			
			ArrayList<String> commands = root.leafPreorderTraversal();
			
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
				case "end":
					break;
				case "programEnd":
					break;
					
				}
			}

			
			turtle.draw();
		}
	}
	
	public static void parseGrammar2(String file) {
		
	}
	
	public static void parseGrammar3(String file) {
		
	}
}
