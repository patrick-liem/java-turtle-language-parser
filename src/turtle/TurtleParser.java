/**
 * This class takes a program written in the Turtle Graphics language and ensures it is syntactically correct.
 * It then returns an ArrayList of the commands that the program contains. If the program is not valid, then
 * the TurtleParser class will print out a reason that it is invalid.
 * 
 * @author Patrick Liem
 * 
 */

package turtle;


public class TurtleParser {

	private boolean error = false;

	private TurtleLexer codeScanner;
	private GrammarNode currentWord;

	/**
	 * The constructor creates a new TurtleParser object for a given file that contains a turtle program
	 * @param file The file that contains the turtle program
	 */
	public TurtleParser(String file) {
			codeScanner = new TurtleLexer(file);
	}

	/**
	 * Checks the turtle program for syntax errors and returns a node that is the root of the
	 * abstract syntax tree of the program. If the program contains syntax errors, it returns null instead.
	 * @return The root of an abstract syntax tree representing the turtle program. If the program contains syntax errors, it returns null instead.
	 */
	public GrammarNode checkProgram() {
		GrammarNode root = new GrammarNode("program", null);

		GrammarNode blockNode = checkBlock();

		if (blockNode == null) {
			return null;
		}

		root.children.add(blockNode);

		if (codeScanner.hasNext()) {
			currentWord = codeScanner.next();
			
			if (!currentWord.data.equals("programEnd")) {
				if (!error) {
					error = true;
					System.out.println("Syntax Error: Missing programEnd statement.");
				}
				return null;
			}
			
		} else {
			error = true;
			System.out.println("Syntax Error: Missing programEnd statement.");
			return null;
		}

		root.children.add(new GrammarNode("programEnd", "programEnd"));

		return root;
	}

	/**
	 * Helper method that returns a node that represents a block nonterminal.
	 * @return A node that represents a block nonterminal
	 */
	private GrammarNode checkBlock() {

		GrammarNode blockNode = new GrammarNode("block", null);

		currentWord = codeScanner.next();
		if (!currentWord.data.equals("begin")) {
			if (!error) {
				error = true;
				System.out.println("Syntax Error: Missing begin statement for program block.");
			}
			return null;
		}

		blockNode.children.add(new GrammarNode("begin", "begin"));

		GrammarNode statementListNode = checkStatementList();

		if (statementListNode == null) {
			return null;
		}

		blockNode.children.add(statementListNode);

		if (!currentWord.data.equals("end")) {
			if (!error) {
				error = true;
				System.out.println("Syntax Error: Missing end statement for program block.");
			}
			return null;
		}

		blockNode.children.add(new GrammarNode("end", "end"));

		return blockNode;

	}

	/**
	 * Helper method that returns a node that represents a statementList nonterminal.
	 * @return A node that represents a statementList nonterminal
	 */
	private GrammarNode checkStatementList() {

		GrammarNode statementListNode = new GrammarNode("statementList" , null);

		GrammarNode nextStatement = checkStatement();
		if (nextStatement == null) {
			return null;
		}
		while(nextStatement != null) {
			statementListNode.children.add(nextStatement);
			nextStatement = checkStatement();
		}
		return statementListNode;

	}

	/**
	 * Helper method that returns a node that represents a statement nonterminal.
	 * @return A node that represents a statement nonterminal
	 */
	private GrammarNode checkStatement() {
		GrammarNode statementNode = new GrammarNode("statement", null);
		
		if (codeScanner.hasNext())
			currentWord = codeScanner.next();
		else
			return null;

		GrammarNode loopNode = checkLoop();
		if (loopNode == null) {
			GrammarNode commandNode = checkCommand();
			if (commandNode == null) {
				return null;
			}

			statementNode.children.add(commandNode);

			return statementNode;
		}

		statementNode.children.add(loopNode);

		return statementNode;
	}

	/**
	 * Helper method that returns a node that represents a loop nonterminal.
	 * @return A node that represents a loop nonterminal
	 */
	private GrammarNode checkLoop() {
		GrammarNode loopNode = new GrammarNode("loop", null);

		if (!currentWord.data.equals("loop")) {
			return null;
		}

		loopNode.children.add(new GrammarNode("loop", "loop"));

		GrammarNode countNode = checkCount();
		if (countNode == null) {
			return null;
		}
		loopNode.children.add(countNode);

		GrammarNode blockNode = checkBlock();
		if (blockNode == null) {
			return null;
		}
		loopNode.children.add(blockNode);

		return loopNode;

	}

	/**
	 * Helper method that returns a node that represents a command nonterminal.
	 * @return A node that represents a command nonterminal
	 */
	private GrammarNode checkCommand() {

		GrammarNode commandNode = new GrammarNode("command", null);


		if (currentWord.data.equals("end")) {
			return null;
		}

		if (!currentWord.data.equals("forward")) {
			if (!currentWord.data.equals("turn")) {
				GrammarNode assignment = checkAssignment();

				if (assignment == null) {
					return null;
				}

				commandNode.children.add(assignment);
				return commandNode;

			}

			commandNode.children.add(new GrammarNode("turn", "turn"));

			GrammarNode angleNode = checkAngle();

			if (angleNode == null) {
				return null;
			}

			commandNode.children.add(angleNode);

			return commandNode;
		}

		commandNode.children.add(new GrammarNode("forward", "forward"));

		GrammarNode distanceNode = checkDistance();

		if (distanceNode == null) {
			return null;
		}

		commandNode.children.add(distanceNode);

		return commandNode;
	}

	/**
	 * Helper method that returns a node that represents an assignment nonterminal.
	 * @return A node that represents an assignment nonterminal
	 */
	private GrammarNode checkAssignment() {
		GrammarNode assignmentNode = new GrammarNode("assignment", null);

		GrammarNode variableNode = checkVariable();
		if (variableNode == null) {
			return null;
		}

		assignmentNode.children.add(variableNode);

		if (codeScanner.hasNext()) {
			currentWord = codeScanner.next();
		} else {
			if (!error) {
				error = true;
				System.out.println("Syntax Error: Missing end statement for program block.");
			}
			return null;
		}
			
		if (!currentWord.data.equals("=")) {
			if (!error) {
				error = true;
				System.out.println("Syntax Error: Expected \"=\", but found \"" + currentWord.data + "\"");
			}
			return null;
		}

		assignmentNode.children.add(new GrammarNode("=", "="));

		GrammarNode numberNode = checkNumber();

		if (numberNode == null) {
			return null;
		}
		assignmentNode.children.add(numberNode);

		return assignmentNode;

	}

	/**
	 * Helper method that returns a node that represents a variable nonterminal.
	 * @return A node that represents a variable nonterminal
	 */
	private GrammarNode checkVariable() {
		if (currentWord.data.matches("[a-zA-Z]+[a-zA-Z0-9]*")) {
			return new GrammarNode("string", currentWord.data);
		}
		if (!error) {
			error = true;
			System.out.println("Syntax Error: Invalid variable name \"" + currentWord.data + "\"");
		}
		return null;
	}

	/**
	 * Helper method that returns a node that represents a distance nonterminal.
	 * @return A node that represents a distance nonterminal
	 */
	private GrammarNode checkDistance() {
		GrammarNode numberNode = checkNumber();
		if (numberNode == null) {
			GrammarNode variableNode = checkVariable();
			if (variableNode == null) {
				return null;
			}
			return variableNode;
		}
		return numberNode;
	}

	/**
	 * Helper method that returns a node that represents a angle nonterminal.
	 * @return A node that represents a angle nonterminal
	 */
	private GrammarNode checkAngle() {
		GrammarNode numberNode = checkNumber();
		if (numberNode == null) {
			GrammarNode variableNode = checkVariable();
			if (variableNode == null) {
				return null;
			}
			return variableNode;
		}
		return numberNode;
	}

	/**
	 * Helper method that returns a node that represents a count nonterminal.
	 * @return A node that represents a count nonterminal
	 */
	private GrammarNode checkCount() {
		GrammarNode numberNode = checkNumber();
		if (numberNode == null) {
			GrammarNode variableNode = checkVariable();
			if (variableNode == null) {
				return null;
			}
			return variableNode;
		}
		return numberNode;
	}

	/**
	 * Helper method that returns a node that represents a number nonterminal.
	 * @return A node that represents a number nonterminal
	 */
	private GrammarNode checkNumber() {
		
		if (codeScanner.hasNext())
			currentWord = codeScanner.next();
		else
			return null;

		if (!currentWord.data.matches("[0-9]+")) {
			if (!currentWord.data.matches("[a-zA-Z]+[a-zA-Z0-9]*")) {
				if (!error) {
					error = true;
					System.out.println("Syntax Error: Invalid number \"" + currentWord.data + "\"");
				}
			}
			return null;
		}
		return new GrammarNode("NUMBER", currentWord.data);
	}
}
