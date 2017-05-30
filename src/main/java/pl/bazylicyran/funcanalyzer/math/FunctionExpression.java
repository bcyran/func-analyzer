package pl.bazylicyran.funcanalyzer.math;

/**
 * Represents mathematical expression with possibility of using variables.
 * 
 * @author Bazyli Cyran
 */
public class FunctionExpression extends MathExpression {

	/** Whether vars changed since last eval or not */
	private boolean varsChanged = false;

	/**
	 * Calls MathExpression constructor
	 * 
	 * @param expression
	 */
	public FunctionExpression(String expression) {
		super(expression);
	}

	/**
	 * Adds new variable (its name and value).
	 * 
	 * @param name Variable name.
	 * @param value Variable value.
	 */
	public void setVar(String name, double value) {
		parser.addVariable(name, value);
		varsChanged = true;
	}

	@Override
	public Double getValue() {
		if (evaluated == false || varsChanged == true) {
			eval();
			varsChanged = false;
		}

		return value;
	}

	@Override
	protected void eval() {
		parser.parse();

		value = parser.getValue();

		evaluated = true;
	}

}
