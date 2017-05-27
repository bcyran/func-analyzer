package pl.bazylicyran.funcanalyzer.math;

/**
 * Represents mathematical expression with possibility of using variables.
 * 
 * @author Bazyli Cyran
 */
public class FunctionExpression extends MathExpression {

	/** Whether vars changed since last eval or not */
	private boolean varsChanged = false;

	/** Whether current variable is in domain or not. */
	private boolean inDomain = true;

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

	/**
	 * Returns whether or not current variable is in domain.
	 * 
	 * @return True if variable is in domain.
	 */
	public boolean inDomain() {
		if (evaluated == false || varsChanged == true) {
			eval();
			varsChanged = false;
		}
		
		return inDomain;
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
		inDomain = parser.inDomain();

		if (inDomain) {
			value = parser.getValue();
		} else {
			value = null;
		}

		evaluated = true;
	}

}
