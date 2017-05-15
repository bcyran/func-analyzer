package pl.bazylicyran.funcanalyzer.parsing;

import java.util.List;

/**
 * Represents mathematical expression function. Calculates its arithmetical
 * value based on arguments.
 * 
 * E.g. sin(x) = Math.sin(x)
 * 
 * @author Bazyli Cyran
 */
public abstract class ExpFunction {

	/** Function name. */
	private String function;

	/** Number of arguments function needs */
	private int argNum;

	/**
	 * Initializes operator name
	 */
	public ExpFunction(String name, int argNum) {
		this.function = name;
		this.argNum = argNum;
	}

	/**
	 * Returns function name.
	 * 
	 * @return Function name.
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * Returns number of arguments expected by function.
	 * 
	 * @return Number of arguments.
	 */
	public int getArgNum() {
		return argNum;
	}

	/**
	 * Evaluates function basing on its arguments.
	 * 
	 * @param args Ordered list of arguments.
	 * @return Value of function.
	 */
	public abstract double eval(List<Double> args);

}
