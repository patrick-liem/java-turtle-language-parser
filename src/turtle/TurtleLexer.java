/**
 * This class takes a program written in the Turtle Graphics language and converts its statements into tokens.
 * These tokens are represented by GrammarNodes, which contain the token type and data.
 * 
 * @author Patrick Liem, Wenkai Zhao, Matthew Murch, Lei Liu
 * 
 */

package turtle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TurtleLexer {
	
	private Scanner codeScanner;
	
	/**
	 * Creates a new TurtleLexer object that reads the contents of the given file
	 * @param file The Turtle Graphics program to read
	 */
	public TurtleLexer (String file) {
		try {
			codeScanner = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file");
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the next word of the Turtle Graphics program, converts it into a token, and returns it.
	 * @return A GrammarNode that represents the next token in the program
	 */
	public GrammarNode next() {
		String currentWord = codeScanner.next();
		switch(currentWord) {
		case "programEnd":
			return new GrammarNode("TERMINAL", "programEnd");
		case "begin":
			return new GrammarNode("TERMINAL", "begin");
		case "end":
			return new GrammarNode("TERMINAL", "end");
		case "loop":
			return new GrammarNode("TERMINAL", "loop");
		case "forward":
			return new GrammarNode("COMMAND", "forward");
		case "turn":
			return new GrammarNode("COMMAND", "turn");
		case "=":
			return new GrammarNode("TERMINAL", "=");
		default:
			if (currentWord.matches("[0-9]+")) {
				return new GrammarNode("NUMBER", currentWord);
			} else if (currentWord.matches("[a-zA-Z]+[a-zA-Z0-9]*")) {
				return new GrammarNode("VARIABLE", currentWord);
			} else {
				return new GrammarNode("INVALID_VARIABLE", currentWord);
			}
			
		}
	}
	
	/**
	 * Checks whether the program has any more statements to tokenize
	 * @return True if the program still has more content, false otherwise
	 */
	public boolean hasNext() {
		return codeScanner.hasNext();
	}
}
