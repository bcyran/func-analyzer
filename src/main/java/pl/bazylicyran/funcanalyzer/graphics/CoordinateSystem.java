package pl.bazylicyran.funcanalyzer.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import pl.bazylicyran.funcanalyzer.math.CSPoint;
import pl.bazylicyran.funcanalyzer.math.FunctionDiscretizer;

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
	private final CSPoint center = new CSPoint(0.0, 0.0);

	/** Function discretizer. */
	private final FunctionDiscretizer disc = new FunctionDiscretizer();

	/** Width of distance between two neighboring points. */
	private int unitLength = 50;

	/** Function to draw. */
	private String function;

	/** Image to draw everything on. */
	private BufferedImage canvas;

	/**
	 * Initializes coordinate system.
	 */
	public CoordinateSystem(int width, int height) {
		this.width = width;
		this.height = height;

		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		initDrawingArea();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(canvas, 0, 0, null);
	}

	/**
	 * Clears all previously drawn functions and draws axes.
	 */
	public void clearDrawingArea() {
		Graphics g = canvas.getGraphics();
		g.clearRect(0, 0, width, height);
		g.setColor(backgroundColor);
		g.drawRect(0, 0, width, height);
		g.fillRect(0, 0, width, height);
		drawAxes();
	}

	/**
	 * Adds function to coordinate system.
	 * 
	 * @param function Function to draw.
	 */
	public void addFunction(String function) {
		this.function = function;
		drawFunction();
	}

	/**
	 * Returns current function.
	 * 
	 * @return Current function.
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * Zooms system in.
	 */
	public void zoomPlus() {
		unitLength /= 2;
	}

	/**
	 * Zooms system out.
	 */
	public void zoomMinus() {
		unitLength += 2;
	}

	/**
	 * Moves center point by given offset in X and Y axes.
	 * 
	 * @param dx Offset to move center by in X axis.
	 * @param dy Offset to move center by in Y axis.
	 */
	public void moveCenter(int dx, int dy) {
		center.move((double) -dx, (double) -dy);
	}

	/**
	 * Initializes drawing area.
	 */
	private void initDrawingArea() {
		setPreferredSize(new Dimension(width, height));
		clearDrawingArea();
	}

	/**
	 * Draws X and Y axes.
	 * 
	 * @param g Graphics object.
	 */
	private void drawAxes() {
		Graphics g = canvas.getGraphics();

		g.setColor(axesColor);
		Font font = g.getFont().deriveFont(15.0f);
		g.setFont(font);

		// X axis
		g.drawLine(0, yToPix(0), width, yToPix(0));

		// Y axis
		g.drawLine(xToPix(0), 0, xToPix(0), height);

		int scaleLength = unitLength / 5;
		int scaleValueInterval = 50 / unitLength;

		// X axis scale
		CSPoint leftmost = new CSPoint((double) -(width / 2 / unitLength), 0.0);
		int scaleYpix1 = yToPix(0) + scaleLength / 2;
		int scaleYpix2 = yToPix(0) - scaleLength / 2;
		int scaleXpix;

		while (xToPix(leftmost.getX()) < width) {
			scaleXpix = xToPix(leftmost.getX());
			g.drawLine(scaleXpix, scaleYpix1, scaleXpix, scaleYpix2);

			if (leftmost.getX() != 0 && ((leftmost.getX() % scaleValueInterval) == 0 || scaleValueInterval == 0)) {
				g.drawString(String.valueOf((int) (double) leftmost.getX()), scaleXpix - 3, scaleYpix1 + 17);
			}

			leftmost.move(1.0, 0.0);
		}

		// Y axis scale
		CSPoint upmost = new CSPoint(0.0, (double) -(height / 2 / unitLength));
		int scaleXpix1 = xToPix(0) + scaleLength / 2;
		int scaleXpix2 = xToPix(0) - scaleLength / 2;
		int scaleYpix;

		while (yToPix(upmost.getY()) > 0) {
			scaleYpix = yToPix(upmost.getY());
			g.drawLine(scaleXpix1, scaleYpix, scaleXpix2, scaleYpix);

			if (upmost.getY() != 0 && ((upmost.getY() % scaleValueInterval) == 0 || scaleValueInterval == 0)) {
				g.drawString(String.valueOf((int) (double) upmost.getY()), scaleXpix1 + 7, scaleYpix + 5);
			}

			upmost.move(0.0, 1.0);
		}

		g.dispose();
		repaint();
	}

	/**
	 * Draws current function.
	 * 
	 * @param g Graphics object.
	 */
	private void drawFunction() {
		Graphics g = canvas.getGraphics();

		g.setColor(graphColor);

		disc.setFunction(function);
		disc.setInterval((double) -width / 2 / unitLength, (double) width / 2 / unitLength);
		disc.setResolution((double) 1 / (unitLength));
		List<CSPoint> points = disc.getPoints();

		CSPoint point = points.get(0);
		int lastX = xToPix(point.getX());
		int lastY = point.getY() != null ? yToPix(point.getY()) : 0;
		int currentX;
		int currentY;
		boolean lastNull;
		boolean currentNull;

		for (int i = 1; i < points.size(); i++) {
			lastNull = point.getY() != null ? true : false;
			point = points.get(i);
			currentNull = point.getY() != null ? true : false;
			currentX = xToPix(point.getX());
			currentY = point.getY() != null ? yToPix(point.getY()) : 0;

			if (lastNull && currentNull && ((lastY >= 0 && lastY <= height) || (currentY >= 0 && currentY <= height))) {
				g.drawLine(lastX, lastY, currentX, currentY);
			}

			lastX = currentX;
			lastY = currentY;
		}

		g.dispose();
		repaint();
	}

	/**
	 * Calculates pixel value of given X coordinate.
	 * 
	 * @param x X coordinate.
	 * @return Pixels.
	 */
	private int xToPix(double x) {
		return (width / 2) + (int) ((double) unitLength * (x - center.getX()));
	}

	/**
	 * Calculates pixel value of given Y coordinate.
	 * 
	 * @param y Y coordinate.
	 * @return Pixels.
	 */
	private int yToPix(double y) {
		return (height / 2) - (int) ((double) unitLength * (y - center.getY()));
	}

}
