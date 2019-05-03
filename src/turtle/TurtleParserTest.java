/**
 * This class is used to test the TurtleParser and TurtleLexer classes. It demonstrates that the TurtleParser class
 * will print the correct errors if the syntax of the turtle program is incorrect. It also shows that TurtleLexer's
 * methods works correctly.
 * 
 * @author Patrick Liem
 */
package turtle;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TurtleParserTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	
	/*
	 * Sets up a stream that allows us to check if the correct output is being printed
	 */
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	/*
	 * Closes stream that reads standard output
	 */
	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	}
	
	@Test
	public void testProgramEnd() {
		TurtleParser tp = new TurtleParser("testcases/programEnd.txt");
		tp.checkProgram();
		assertEquals("Syntax Error: Missing programEnd statement.", outContent.toString().trim());
	}
	
	@Test
	public void testBegin() {
		TurtleParser tp = new TurtleParser("testcases/begin.txt");
		tp.checkProgram();
		assertEquals("Syntax Error: Missing begin statement for program block.", outContent.toString().trim());
	}
	
	@Test
	public void testEnd() {
		TurtleParser tp = new TurtleParser("testcases/end.txt");
		tp.checkProgram();
		assertEquals("Syntax Error: Missing end statement for program block.", outContent.toString().trim());
	}
	
	@Test
	public void testEquals() {
		TurtleParser tp = new TurtleParser("testcases/equals.txt");
		tp.checkProgram();
		assertEquals("Syntax Error: Expected \"=\", but found \"5\"", outContent.toString().trim());
	}
	
	@Test
	public void testVariable() {
		TurtleParser tp = new TurtleParser("testcases/variable.txt");
		tp.checkProgram();
		assertEquals("Syntax Error: Invalid variable name \"5times\"", outContent.toString().trim());
	}
	
	@Test
	public void testNumber() {
		TurtleParser tp = new TurtleParser("testcases/number.txt");
		tp.checkProgram();
		assertEquals("Syntax Error: Invalid number \"90.5\"", outContent.toString().trim());
	}
	
	@Test
	public void testLexer() {
		TurtleLexer tl = new TurtleLexer("testcases/lexertest.txt");
		assertEquals(true, tl.hasNext());
		assertEquals(new GrammarNode("TERMINAL", "begin"), tl.next());
		assertEquals(false, tl.hasNext());
	}
}
