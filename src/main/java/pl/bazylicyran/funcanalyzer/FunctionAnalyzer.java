package pl.bazylicyran.funcanalyzer;

import pl.bazylicyran.funcanalyzer.math.FunctionExpression;

/**
 * App that parses, analyzes and draws a mathematical functions.
 * 
 * @author Bazyli Cyran
 */
public class FunctionAnalyzer {

	/**
	 * Entry point for program.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		FunctionExpression exp = new FunctionExpression("2 ^ x");

		System.out.println("Expression: " + exp.getExpression());
		
		exp.setVar("x", 3);
		System.out.println("Result: " + exp.getValue());
	}

}
