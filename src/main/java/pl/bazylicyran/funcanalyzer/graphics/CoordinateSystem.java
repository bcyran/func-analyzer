package pl.bazylicyran.funcanalyzer.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * Draws Cartesian coordinate system.
 * 
 * @author Bazyli Cyran
 */
public class CoordinateSystem extends JPanel {

	/** System width. */
	private final int width;

	/** System height. */
	private final int height;

	/** Background color. */
	private final Color backgroundColor = Color.WHITE;

	/** Color of axes and labels. */
	private final Color axesColor = Color.BLACK;

	/** Color of function graph. */
	private final Color graphColor = new Color(131, 126, 191);

	/** Point to show in the middle of drawing area. */
	private Point center = new Point(0, 0);

	/** Width of distance between two neighboring points. */
	private int unitLength = 50;

	/**
	 * Initialize coordinate system.
	 */
	public CoordinateSystem(int width, int height) {
		this.width = width;
		this.height = height;

		initDrawingArea();

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawAxes(g);
	}

	private void initDrawingArea() {
		setPreferredSize(new Dimension(width, height));
		setBackground(backgroundColor);
	}

	/**
	 * Draw X and Y axes.
	 */
	private void drawAxes(Graphics g) {
		g.setColor(axesColor);

		// X axis
		g.drawLine(0, yToPix(0), width, yToPix(0));

		// Y axis
		g.drawLine(xToPix(0), 0, xToPix(0), height);
	}

	/**
	 * Calculates pixel value of given X coordinate.
	 * 
	 * @param x X coordinate.
	 * @return Pixels.
	 */
	private int xToPix(int x) {
		return (width / 2) + unitLength * (x - (int) center.getX());
	}

	/**
	 * Calculates pixel value of given Y coordinate.
	 * 
	 * @param y Y coordinate.
	 * @return Pixels.
	 */
	private int yToPix(int y) {
		return (height / 2) + unitLength * (y - (int) center.getY());
	}

}
