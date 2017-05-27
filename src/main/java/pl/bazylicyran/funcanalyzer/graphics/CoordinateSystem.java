package pl.bazylicyran.funcanalyzer.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

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
	private CSPoint center = new CSPoint(0, 0);

	/** Width of distance between two neighboring points. */
	private int unitLength = 50;

	/**
	 * Initializes coordinate system.
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

	/**
	 * Initializes drawing area.
	 */
	private void initDrawingArea() {
		setPreferredSize(new Dimension(width, height));
		setBackground(backgroundColor);
	}

	/**
	 * Draw X and Y axes.
	 */
	private void drawAxes(Graphics g) {
		g.setColor(axesColor);
		Font font = g.getFont().deriveFont(15.0f);
		g.setFont(font);

		// X axis
		g.drawLine(0, yToPix(0), width, yToPix(0));

		// Y axis
		g.drawLine(xToPix(0), 0, xToPix(0), height);

		int scaleLength = unitLength / 5;

		// X axis scale
		CSPoint leftmost = new CSPoint(-(width / 2 / unitLength), 0);
		int scaleYpix1 = yToPix(0) + scaleLength / 2;
		int scaleYpix2 = yToPix(0) - scaleLength / 2;
		int scaleXpix;

		while (xToPix((int) leftmost.getX()) < width) {
			scaleXpix = xToPix((int) leftmost.getX());
			g.drawLine(scaleXpix, scaleYpix1, scaleXpix, scaleYpix2);

			if (leftmost.getX() != 0) {
				g.drawString(String.valueOf((int) leftmost.getX()), scaleXpix - 3, scaleYpix1 + 15);
			}

			leftmost.move(1, 0);
		}

		// Y axis scale
		CSPoint upmost = new CSPoint(0, -(height / 2 / unitLength));
		int scaleXpix1 = xToPix(0) + scaleLength / 2;
		int scaleXpix2 = xToPix(0) - scaleLength / 2;
		int scaleYpix;

		while (yToPix((int) upmost.getY()) < height) {
			scaleYpix = yToPix((int) upmost.getY());
			g.drawLine(scaleXpix1, scaleYpix, scaleXpix2, scaleYpix);

			if (upmost.getY() != 0) {
				g.drawString(String.valueOf((int) upmost.getY()), scaleXpix1 + 10, scaleYpix + 5);
			}

			upmost.move(0, 1);
		}

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
