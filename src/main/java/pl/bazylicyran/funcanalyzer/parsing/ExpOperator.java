package pl.bazylicyran.funcanalyzer.parsing;

import java.util.List;

/**
 * Represents mathematical expression operator. Calculates its arithmetical
 * value based on left and right argument.
 * 
 * E.g. left "+" right = left + right
 * 
 * @author Bazyli Cyran
 */
public abstract class ExpOperator {

	/** Operator (its "name") */
	private String operator;

	/** Allowed levels on which operator can be executed */
	private List<String> levels;

	/**
	 * Initializes operator name
	 */
	public ExpOperator(String operator, List<String> levels) {
		this.operator = operator;
		this.levels = levels;
	}

	/**
	 * Returns operators "name".
	 * 
	 * @return Operator.
	 */
	public String getOperator() {
		return this.operator;
	}

	/**
	 * Checks if operator is allowed on given level
	 * 
	 * @return True if allowed.
	 */
	public boolean levelAllowed(String level) {
		return levels.contains(level);
	}

	/**
	 * Evaluates expression with operator basing on its arguments (left and
	 * right).
	 * 
	 * @param left Left argument.
	 * @param right Right argument.
	 * @return Value of expression with operator.
	 */
	public abstract double eval(double left, double right);

}
