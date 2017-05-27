package pl.bazylicyran.funcanalyzer.math;

import java.util.List;

import pl.bazylicyran.funcanalyzer.parsing.ExpressionParser;
import pl.bazylicyran.funcanalyzer.parsing.ExpressionTokenizer;

/**
 * Represents mathematical expression built using numbers, operators and
 * elementary functions.
 * 
 * Tokenizes string and then parses it.
 * 
 * @author Bazyli Cyran
 */
public class MathExpression {

	/** Expression given by user. */
	private String expression;

	/** Parser instance */
	protected ExpressionParser parser;

	/** Tokens list created from expressions. */
	protected List<String> tokens;

	/** Final value of expression. */
	protected Double value;

	/** Whether or not expression was evaluated. */
	protected boolean evaluated = false;

	/**
	 * Initializes expression variable, calls tokenization method.
	 * 
	 * @param expression The expression to tokenize and parse.
	 */
	public MathExpression(String expression) {
		this.expression = expression;

		tokenize();

		parser = new ExpressionParser(tokens);
	}

	/**
	 * Returns value of expression. If needed parses expression to get the
	 * value.
	 * 
	 * @return The value of expression.
	 */
	public Double getValue() {
		if (evaluated == false) {
			eval();
		}

		return value.doubleValue();
	}

	/**
	 * Returns tokens list created from expression.
	 * 
	 * @return The tokens list.
	 */
	public List<String> getTokens() {
		return tokens;
	}

	/**
	 * Returns initial expression.
	 * 
	 * @return Expression.
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Tokenizes expression using MathTokenizer.
	 */
	private void tokenize() {
		ExpressionTokenizer tokenizer = new ExpressionTokenizer(expression);
		tokens = tokenizer.getTokens();
	}

	/**
	 * Evaluates expression using MathParser.
	 */
	protected void eval() {
		parser.parse();

		value = parser.getValue();

		evaluated = true;
	}

}
