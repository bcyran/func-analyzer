package pl.bazylicyran.funcanalyzer.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Discretizes function in given interval and with given resolution.
 * 
 * @author Bazyli Cyran
 */
public class FunctionDiscretizer {

	/** String with function to discretize. */
	private String input;

	private FunctionExpression function;

	/** Start of interval in which function will be discretized. */
	private double intervalStart;

	/** End of interval in which function will be discretized. */
	private double intervalEnd;

	/** Discretization resolution. */
	private double resolution;

	/** Discretized function. */
	private List<CSPoint> points = new ArrayList<>();

	/** Whether or not function was discretized. */
	private boolean discretized = false;

	/**
	 * Creates empty FunctionDiscretizer.
	 */
	public FunctionDiscretizer() {

	}

	/**
	 * Creates FunctionDiscretizer with given values.
	 * 
	 * @param function Function to discretize.
	 * @param intervalStart Start of discretization interval.
	 * @param IntervalEnd End of discretization interval.
	 * @param resolution Discretization resolution.
	 */
	public FunctionDiscretizer(String function, double intervalStart, double intervalEnd, double resolution) {
		this.input = function;
		this.intervalStart = intervalStart;
		this.intervalEnd = intervalEnd;
		this.resolution = resolution;
	}

	/**
	 * Sets given string as current function to discretize.
	 * 
	 * @param function Function to discretize.
	 */
	public void setFunction(String function) {
		input = function;
		discretized = false;
	}

	/**
	 * Sets discretizer interval to given values.
	 * 
	 * @param intervalStart Start of interval.
	 * @param intervalEnd End of interval.
	 */
	public void setInterval(double intervalStart, double intervalEnd) {
		this.intervalStart = intervalStart;
		this.intervalEnd = intervalEnd;
	}

	/**
	 * Sets discretizer resolution to given value.
	 * 
	 * @param resolution Resolution to set.
	 */
	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	/**
	 * Returns list of discretized points.
	 * 
	 * @return Discretized points.
	 */
	public List<CSPoint> getPoints() {
		
		if (discretized == false) {
			discretize();
		}

		return points;
	}

	/**
	 * Discretizes current function.
	 */
	private void discretize() {
		points.clear();
		
		function = new FunctionExpression(input);
		Double value;

		for (double x = intervalStart; x <= intervalEnd; x += resolution) {
			function.setVar("x", x);
			
			if (function.inDomain()) {
				value = function.getValue();
			} else {
				value = null;
			}
			
			CSPoint point = new CSPoint(x, value);
			points.add(point);
		}

		discretized = true;
	}

}
