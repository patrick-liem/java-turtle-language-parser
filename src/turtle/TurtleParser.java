/**
 * This class takes a program written in the Turtle Graphics language and ensures it is syntactically correct.
 * It then returns an ArrayList of the commands that the program contains. If the program is not valid, then
 * the TurtleParser class will print out a reason that it is invalid.
 * 
 * @author Patrick Liem, Wenkai Zhao, Matthew Murch, Lei Liu
 * 
 */

package turtle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TurtleParser {
	
	private Scanner codeScanner;
	private String currentWord;
	
	public TurtleParser(String file) {
		try {
			codeScanner = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file");
			e.printStackTrace();
		}
	}
	
	public GrammarNode checkProgram() {
		GrammarNode root = new GrammarNode("program", null);
		
		GrammarNode blockNode = checkBlock();
		
		if (blockNode == null) {
			System.out.println("program block was not formatted correctly");
			return null;
		}
		
		root.children.add(blockNode);
		
		
		currentWord = codeScanner.next();
		
		if (!currentWord.equals("programEnd")) {
			System.out.println("no programEnd statement");
			return null;
		}
		
		root.children.add(new GrammarNode("programEnd", "programEnd"));
		
		return root;
	}
	
	private GrammarNode checkBlock() {
		
		GrammarNode blockNode = new GrammarNode("block", null);
		
		currentWord = codeScanner.next();
		if (!currentWord.equals("begin")) {
			System.out.println("missing begin statement");
			return null;
		}
		
		blockNode.children.add(new GrammarNode("begin", "begin"));
		
		GrammarNode statementListNode = checkStatementList();
		
		if (statementListNode == null) {
			System.out.println("bad statement list");
			return null;
		}
		
		blockNode.children.add(statementListNode);
		
		if (!currentWord.equals("end")) {
			System.out.println("missing end statement");
			return null;
		}
		
		blockNode.children.add(new GrammarNode("end", "end"));
		
		return blockNode;
		
	}
	
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
	
	private GrammarNode checkStatement() {
		GrammarNode statementNode = new GrammarNode("statement", null);
		
		currentWord = codeScanner.next();
		
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
	
	private GrammarNode checkLoop() {
		GrammarNode loopNode = new GrammarNode("loop", null);
		
		//currentWord = codeScanner.next();
		
		if (!currentWord.equals("loop")) {
			return null;
		}
		
		loopNode.children.add(new GrammarNode("loop", "loop"));
		
		GrammarNode countNode = checkCount();
		if (countNode == null) {
			System.out.println("invalid count");
			return null;
		}
		loopNode.children.add(countNode);
		
		GrammarNode blockNode = checkBlock();
		if (blockNode == null) {
			System.out.println("invalid loop block");
			return null;
		}
		loopNode.children.add(blockNode);
		
		return loopNode;
		
	}
	
	private GrammarNode checkCommand() {
		
		GrammarNode commandNode = new GrammarNode("command", null);
		
		//currentWord = codeScanner.next();
		
		//System.out.println("Checking command: " + currentWord);
		
		if (currentWord.equals("end")) {
			return null;
		}
		
		if (!currentWord.equals("forward")) {
			if (!currentWord.equals("turn")) {
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
				System.out.println("invalid angle");
				return null;
			}
			
			commandNode.children.add(angleNode);
			
			// Do turn stuff
			return commandNode;
		}
		
		commandNode.children.add(new GrammarNode("forward", "forward"));
		
		GrammarNode distanceNode = checkDistance();
		
		if (distanceNode == null) {
			System.out.println("invalid distance");
			return null;
		}
		
		commandNode.children.add(distanceNode);
		
		return commandNode;
	}
	
	private GrammarNode checkAssignment() {
		GrammarNode assignmentNode = new GrammarNode("assignment", null);
		
		GrammarNode variableNode = checkVariable();
		if (variableNode == null) {
			return null;
		}
		
		assignmentNode.children.add(variableNode);
		
		
		currentWord = codeScanner.next();
		if (!currentWord.equals("=")) {
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
	
	private GrammarNode checkVariable() {
		if (currentWord.matches("[a-zA-Z]+[a-zA-Z0-9]*")) {
			return new GrammarNode("string", currentWord);
		}
		return null;
	}
	
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
	
	private GrammarNode checkNumber() {
		
		currentWord = codeScanner.next();
		
		//System.out.println("Check number: " + currentWord);
		
		if (!currentWord.matches("[0-9]+")) {
			return null;
		}
		return new GrammarNode("NUMBER", currentWord);
	}
}
