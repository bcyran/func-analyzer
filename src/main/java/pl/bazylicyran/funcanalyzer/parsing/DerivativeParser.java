package pl.bazylicyran.funcanalyzer.parsing;

import java.util.List;
import java.util.Map;

/**
 * Implements recursive descent parser to determine given function derivative.
 * 
 * @author Bazyli Cyran
 */
public class DerivativeParser {

	/** Expression in form of a tokens list. */
	private List<String> tokens;

	/** Derivative string */
	private String derivative = "";

	private String variable = "x";

	/** Current position of parser. */
	private int pos = -1;

	/** Current token. */
	private String current;

	/**
	 * Initializes tokens list.
	 * 
	 * @param tokens The list of tokens.
	 */
	public DerivativeParser(List<String> tokens) {
		this.tokens = tokens;
	}

	/**
	 * Checks if given string contains only letters.
	 * 
	 * @param str The string to check.
	 * @return True if string contains only letter
	 */
	private static boolean isAlpha(String str) {
		return str.matches("[a-zA-Z]+");
	}

	/**
	 * Checks if given string contains only number.
	 * 
	 * @param str The string to check.
	 * @return True if string contains number.
	 */
	private static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * Starts parsing.
	 */
	public void parse() {
		nextToken();
		derivative += parseExp();
	}

	/**
	 * Returns value of parsed expression.
	 * 
	 * @return The value of expression.
	 */
	public String getDerivative() {
		return derivative;
	}

	/**
	 * Pass map containing variables names and values to parser.
	 * 
	 * @param variables Map with variables.
	 */
	public void setVar(String name) {
		this.variable = name;
	}

	/**
	 * Increments position counter and puts next token in `current` variable. If
	 * there is no more tokens sets `current` as null.
	 */
	private void nextToken() {
		pos++;

		if (pos <= tokens.size() - 1) {
			current = tokens.get(pos);
		} else {
			current = null;
		}
	}

	/**
	 * Compares given value with current token. Returns whether it is same or
	 * different.
	 * 
	 * @param value The value to compare.
	 * @return True if given value is same as current token.
	 */
	private boolean tokenIs(String value) {
		if (current != null && current.equals(value)) {
			nextToken();
			return true;
		}

		return false;
	}

	/**
	 * Parses expression (addition and subtraction).
	 * 
	 * exp = term | exp + term | exp - term
	 * 
	 * @return Expression value.
	 */
	private String parseExp() {
		String result = parseTerm();

		while (true) {
			// addition
			if (tokenIs("+")) {
				result += "+" + parseTerm();

				// subtraction
			} else if (tokenIs("-")) {
				result += "-" + parseTerm();

				// term
			} else {
				return result;
			}
		}
	}

	/**
	 * Parses term (multiplication, division, exponentiation).
	 * 
	 * term = factor | term * factor | term / factor | factor ^ factor
	 * 
	 * @return Term value.
	 */
	private String parseTerm() {
		String result = "";
		String first = current;
		String firstDer = parseFactor();

		while (true) {
			// multiplication
			if (tokenIs("*")) {
				String second = current;
				String secondDer = parseTerm();
				result += firstDer + "*" + second + "+" + first + "*" + secondDer;

				// division
			} else if (tokenIs("/")) {
				String second = current;
				String secondDer = parseTerm();
				result += (firstDer + "*" + second + "+" + first + "*" + secondDer) +
						"/" + Double.toString(Math.pow(Double.parseDouble(second), 2));

				// exponentiation
			} else if (tokenIs("^")) {
				result = Math.pow(result, parseFactor());

				// factor
			} else {
				result = firstDer;
				return result;
			}
		}
	}

	/**
	 * Parses factor (numbers, negative numbers, parentheses, functions).
	 * 
	 * factor = + factor | - factor | ( expression ) | number | function factor
	 * 
	 * @return Factor value.
	 */
	private String parseFactor() {
		// positive term
		if (tokenIs("+")) {
			return parseTerm();

			// negative term
		} else if (tokenIs("-")) {
			return -parseTerm();
		}

		String result = 0;

		// parentheses
		if (tokenIs("(")) {
			result = parseExp();
			if (!tokenIs(")")) {
				throw new ExpressionException("Expected closing parethesis.");
			}

			// number
		} else if (isNumeric(current)) {
			result = Double.parseDouble(current);
			nextToken();

			// letters
		} else if (isAlpha(current)) {

			// functions
			if (tokenIs("sqrt")) {
				result = Math.sqrt(parseFactor());
			} else if (tokenIs("sin")) {
				result = Math.sin(parseFactor());
			} else if (tokenIs("cos")) {
				result = Math.cos(parseFactor());
			} else if (tokenIs("tan") || tokenIs("tg")) {
				result = Math.tan(parseFactor());
			} else if (tokenIs("ln")) {
				result = Math.log(parseFactor());
			} else if (tokenIs("log")) {
				nextToken();
				double base = parseFactor();
				nextToken();
				result = Math.log(parseFactor()) / Math.log(base);
				nextToken();

				// constants
			} else if (tokenIs("pi")) {
				result = Math.PI;
			} else if (tokenIs("e")) {
				result = Math.E;

				// variables
			} else if (vars != null && vars.containsKey(current)) {
				result = vars.get(current);
				nextToken();
			} else {
				throw new ExpressionException("Unknown function.", current);
			}
		} else {
			throw new ExpressionException("Unexpected character.", current);
		}

		return result;
	}

}
