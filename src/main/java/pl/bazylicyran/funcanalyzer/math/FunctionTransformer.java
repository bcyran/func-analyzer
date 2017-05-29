package pl.bazylicyran.funcanalyzer.math;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Applies transformations to given function.
 * 
 * @author Bazyli Cyran
 */
public class FunctionTransformer {

	/** Function to transform. */
	private String function;

	/** List of transformations to apply */
	private Set<String> transformations = new HashSet<>();

	/**
	 * Creates FunctionTransformer.
	 */
	public FunctionTransformer() {

	}

	/**
	 * Creates FunctionTransformer and sets function to given value.
	 * 
	 * @param function Function to transform.
	 */
	public FunctionTransformer(String function) {
		setFunction(function);
	}

	/**
	 * Sets given function as function to transform.
	 * 
	 * @param function Function to transform.
	 */
	public void setFunction(String function) {
		this.function = function;
		normalize();
	}

	/**
	 * Return function in current form.
	 * 
	 * @return Function.
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * Applies symmetry about X axis.
	 * 
	 * @return Function with applied X symmetry.
	 */
	public String symmetryX() {
		function = "-(" + function + ")";
		return function;
	}

	/**
	 * Applies symmetry about Y axis.
	 * 
	 * @return Function with applied Y symmetry.
	 */
	public String symmetryY() {
		function = function.replace("x", "(-x)");
		return function;
	}

	/**
	 * Applies absolute value on x.
	 * 
	 * @return Function with applied absolute value on x.
	 */
	public String absX() {
		function = function.replace("x", "abs(x)");
		return function;
	}

	/**
	 * Applies absolute value on y.
	 * 
	 * @return Function with applied absolute value on y.
	 */
	public String absY() {
		function = "abs(" + function + ")";
		return function;
	}

	/**
	 * Sets flag indicating whether or not given transformation will be applied.
	 * 
	 * @param transformation Transformation name.
	 * @param value True if should be applied.
	 */
	public void setTransform(String transformation, boolean value) {
		if (value == true) {
			transformations.add(transformation);
		} else {
			transformations.remove(transformation);
		}
	}

	/**
	 * Applies transformations defined by setTransform method.
	 * 
	 * @return Function with applied transformations.
	 */
	public String transform() {
		for (String tranformation : transformations) {
			try {
				Method t = this.getClass().getMethod(tranformation);
				t.invoke(this);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return function;
	}

	/**
	 * Normalizes function.
	 * 
	 * Currently only adds multiplication symbol between number and variable.
	 * For example: 2x => 2*x
	 */
	private void normalize() {
		String tmp = "";
		char current;
		char last = ' ';
		for (int i = 0; i < function.length(); i++) {
			current = function.charAt(i);

			if (i > 0 && Character.isLetter(current) && Character.isDigit(last)) {
				tmp += "*";
			}

			tmp += current;

			last = current;
		}

		function = tmp;
	}

}
