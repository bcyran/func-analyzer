package pl.bazylicyran.funcanalyzer.graphics;

/**
 * Represents point on Cartesian coordinate system.
 * 
 * @author Bazyli Cyran
 */
public class CSPoint {

	/** X coordinate of a point. */
	private float x;

	/** Y coordinate of a point. */
	private float y;

	/**
	 * Initializes point coordinates.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public CSPoint(float x, float y) {
		this.set(x, y);
	}

	/**
	 * Sets given values as point coordinates.
	 * 
	 * @param x Value to set as X coordinate.
	 * @param y Value to set as Y coordinate.
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Move point by given offset on both axes.
	 * 
	 * @param dx Offset to move point on X axis.
	 * @param dy Offset to move point on Y axis.
	 */
	public void move(float dx, float dy) {
		x += dx;
		y += dy;
	}

	/**
	 * Returns X coordinate of the point.
	 * 
	 * @return X coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns Y coordinate of the point.
	 * 
	 * @return Y coordinate.
	 */
	public float getY() {
		return y;
	}

}
