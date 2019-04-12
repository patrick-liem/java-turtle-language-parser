package turtle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Step1Parser {
	
	private Scanner codeScanner;
	private String currentWord;
	
	public Step1Parser(String file) {
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
		
		GrammarNode commandNode = checkCommand();
		if (commandNode == null) {
			return null;
		}
		
		statementNode.children.add(commandNode);
		
		return statementNode;
	}
	
	private GrammarNode checkCommand() {
		
		GrammarNode commandNode = new GrammarNode("command", null);
		
		currentWord = codeScanner.next();
		
		//System.out.println("Checking command: " + currentWord);
		
		if (currentWord.equals("end")) {
			return null;
		}
		
		if (!currentWord.equals("forward")) {
			if (!currentWord.equals("turn")) {
				System.out.println("invalid command");
				return null;
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
	
	private GrammarNode checkDistance() {
		return checkNumber();
	}
	
	private GrammarNode checkAngle() {
		return checkNumber();
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
