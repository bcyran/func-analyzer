package pl.bazylicyran.funcanalyzer.parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements recursive descent parser to evaluate mathematical expression given
 * in form of a tokens list.
 * 
 * Sources:
 * http://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form
 * http://blog.erezsh.com/how-to-write-a-calculator-in-70-python-lines-by-writing-a-recursive-descent-parser/
 * https://github.com/uklimaschewski/EvalEx
 * 
 * @author Bazyli Cyran
 */
public class ExpressionParser {

	/** Expression in form of a tokens list. */
	private List<String> tokens;

	/** Map of operators and their implementations. */
	private Map<String, ExpOperator> operators = new HashMap<>();

	/** Map of functions and their implementations. */
	private Map<String, ExpFunction> functions = new HashMap<>();

	/** List of variables. */
	private Map<String, Double> variables = new HashMap<>();

	/** Value of expression. */
	private double value;

	/** Whether current variable is in domain or not. */
	private boolean inDomain = true;

	/** Current position of parser. */
	private int pos = -1;

	/** Current token. */
	private String current;

	/** Last token. */
	private String last;

	/**
	 * Initializes tokens list, basic operators, functions and variables.
	 * 
	 * @param tokens The list of tokens.
	 */
	public ExpressionParser(List<String> tokens) {
		this.tokens = tokens;

		// Operators
		addOperator(new ExpOperator("+", Arrays.asList("expression", "factor")) {
			@Override
			public double eval(double left, double right) {
				return left + right;
			}
		});
		addOperator(new ExpOperator("-", Arrays.asList("expression", "factor")) {
			@Override
			public double eval(double left, double right) {
				return left - right;
			}
		});
		addOperator(new ExpOperator("*", Arrays.asList("term")) {
			@Override
			public double eval(double left, double right) {
				return left * right;
			}
		});
		addOperator(new ExpOperator("/", Arrays.asList("term")) {
			@Override
			public double eval(double left, double right) {

				if (right == 0) {
					inDomain = false;
					return 0;
				}

				return left / right;
			}
		});
		addOperator(new ExpOperator("^", Arrays.asList("term")) {
			@Override
			public double eval(double left, double right) {
				return Math.pow(left, right);
			}
		});

		// Functions
		addFunction(new ExpFunction("number", 1) {
			@Override
			public double eval(List<Double> args) {
				return args.get(0);
			}
		});
		addFunction(new ExpFunction("sqrt", 1) {
			@Override
			public double eval(List<Double> args) {

				if (args.get(0) < 0) {
					inDomain = false;
					return 0;
				}

				return Math.sqrt(args.get(0));
			}
		});
		addFunction(new ExpFunction("sin", 1) {
			@Override
			public double eval(List<Double> args) {
				return Math.sin(args.get(0));
			}
		});
		addFunction(new ExpFunction("cos", 1) {
			@Override
			public double eval(List<Double> args) {
				return Math.cos(args.get(0));
			}
		});
		addFunction(new ExpFunction("tan", 1) {
			@Override
			public double eval(List<Double> args) {
				return Math.tan(args.get(0));
			}
		});
		addFunction(new ExpFunction("ln", 1) {
			@Override
			public double eval(List<Double> args) {

				if (args.get(0) < 0) {
					inDomain = false;
					return 0;
				}

				return Math.log(args.get(0));
			}
		});
		addFunction(new ExpFunction("log", 2) {
			@Override
			public double eval(List<Double> args) {

				if (args.get(0) <= 1 || args.get(1) < 0) {
					inDomain = false;
					return 0;
				}

				return Math.log(args.get(1)) / Math.log(args.get(0));
			}
		});
		addFunction(new ExpFunction("abs", 1) {
			@Override
			public double eval(List<Double> args) {
				return Math.abs(args.get(0));
			}
		});

		// Constants stored as variables
		addVariable("pi", Math.PI);
		addVariable("e", Math.E);
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
		value = parseExp();
	}

	/**
	 * Returns whether or not current variable is in domain.
	 * 
	 * @return True if variable is in domain.
	 */
	public boolean inDomain() {
		return inDomain;
	}

	/**
	 * Returns value of parsed expression.
	 * 
	 * @return The value of expression.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Add operator to the parser operators list
	 * 
	 * @param operator Operator.
	 */
	public void addOperator(ExpOperator operator) {
		operators.put(operator.getOperator(), operator);
	}

	/**
	 * Add function to the parser functions list
	 * 
	 * @param function Function.
	 */
	public void addFunction(ExpFunction function) {
		functions.put(function.getFunction(), function);
	}

	/**
	 * Add variable to the parser variables list
	 * 
	 * @param name Variable name.
	 * @param value Variable value.
	 */
	public void addVariable(String name, double value) {
		inDomain = true;
		variables.put(name, value);
	}

	/**
	 * Increments position counter and puts next token in <code>current</code>
	 * variable. If there is no more tokens sets <code>current</code> as null.
	 */
	private void nextToken() {
		pos++;

		last = current;

		if (pos <= tokens.size() - 1) {
			current = tokens.get(pos);
		} else {
			pos = -1;
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
	private double parseExp() {
		double result = parseTerm();
		ExpOperator operator = operators.get(current);

		while (operator != null && operator.levelAllowed("expression")) {
			nextToken();
			result = operator.eval(result, parseTerm());
			operator = operators.get(current);
		}

		return result;
	}

	/**
	 * Parses term (multiplication, division, exponentiation).
	 * 
	 * term = factor | term * factor | term / factor | factor ^ factor
	 * 
	 * @return Term value.
	 */
	private double parseTerm() {
		double result = parseFactor();
		ExpOperator operator = operators.get(current);

		while (operator != null && operator.levelAllowed("term")) {
			nextToken();
			result = operator.eval(result, parseTerm());
			operator = operators.get(current);
		}

		return result;
	}

	/**
	 * Parses factor (numbers, negative numbers, parentheses, functions).
	 * 
	 * factor = + factor | - factor | ( expression ) | number | function factor
	 * 
	 * @return Factor value.
	 */
	private double parseFactor() {
		double result = 0;
		ExpOperator operator = operators.get(current);
		ExpFunction function = functions.get(current);
		Double variable = variables.get(current);
		
		if (current == null) {
			throw new ExpressionException("Unexpected token.", current, last);
		}

		// positive or negative term
		if (operator != null && operator.levelAllowed("factor")) {
			nextToken();
			result = operator.eval(0.0, parseTerm());

			// parentheses
		} else if (tokenIs("(")) {
			result = parseExp();
			if (!tokenIs(")")) {
				throw new ExpressionException("Expected closing parethesis.", current, last);
			}

			// number
		} else if (isNumeric(current)) {
			result = Double.parseDouble(current);

			if (functions.containsKey("number")) {
				result = functions.get("number").eval(Arrays.asList(result));
			}

			nextToken();

			// functions
		} else if (function != null) {
			List<Double> args = new ArrayList<>();
			nextToken();

			for (int i = 0; i < function.getArgNum(); i++) {
				nextToken();
				args.add(parseExp());
			}
			result = function.eval(args);
			nextToken();

			// variables
		} else if (variable != null) {
			result = variable;
			nextToken();

			// unknown
		} else {
			throw new ExpressionException("Unknown token.", current);
		}

		return result;
	}

}
