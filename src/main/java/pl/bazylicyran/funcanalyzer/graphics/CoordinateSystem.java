package pl.bazylicyran.funcanalyzer.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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

	/** Consecutive colors when drawing many functions. */
	private final List<Color> graphColors = Arrays.asList(Color.BLUE, Color.MAGENTA, Color.ORANGE, Color.RED,
			Color.CYAN, Color.GREEN, Color.GRAY, Color.PINK);

	/** Point to show in the middle of drawing area. */
	private final CSPoint center = new CSPoint(0.0, 0.0);

	/** Function discretizer. */
	private final FunctionDiscretizer disc = new FunctionDiscretizer();

	/** Width of distance between two neighboring points. */
	private int unitLength = 50;

	/** Function to draw. */
	private List<String> functions = new ArrayList<>();

	/** Image to draw everything on. */
	private BufferedImage canvas;

	/** Current graph color. */
	private Color currentColor = graphColors.get(0);

	/**
	 * Initializes coordinate system and creates canvas.
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
	 * Draws all current functions.
	 */
	public void drawFunctions() {
		clearDrawingArea();

		if (functions.isEmpty()) {
			return;
		}

		for (int i = 0; i < functions.size(); i++) {
			setColor(graphColors.get(i % graphColors.size()));
			drawFunction(functions.get(i));
		}
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
		this.functions.add(function);
		drawFunctions();
	}

	/**
	 * Removes all saved functions.
	 */
	public void clearFunctions() {
		functions.clear();
	}

	/**
	 * Removes last add function.
	 */
	public void clearLastFunction() {
		functions.remove(functions.size() - 1);
	}

	/**
	 * Checks if given functions is already drawn.
	 * 
	 * @return True if function is already drawn.
	 */
	public boolean inFunctions(String function) {
		return functions.contains(function);
	}

	/**
	 * Zooms system in.
	 */
	public void zoomPlus() {
		if (unitLength > width / 2) {
			return;
		}

		unitLength *= 2;

		drawFunctions();
	}

	/**
	 * Zooms system out.
	 */
	public void zoomMinus() {
		if (unitLength / 2 <= 1) {
			return;
		}

		unitLength /= 2;

		drawFunctions();
	}

	/**
	 * Moves center point by given offset in X and Y axes.
	 * 
	 * @param dx Offset to move center by in X axis.
	 * @param dy Offset to move center by in Y axis.
	 */
	public void moveCenter(int dx, int dy) {
		double delta = (double) Math.ceil(50 / (double) unitLength);
		center.move(-dx * delta, -dy * delta);

		drawFunctions();
	}

	/**
	 * Sets center point and zoom to default values.
	 */
	public void resetMovement() {
		center.set(0.0, 0.0);
		unitLength = 50;

		drawFunctions();
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

		// Length of line indicating point on axis
		int scaleLength = 10;

		// How frequent points should be marked on axis
		int scaleValueInterval = 50 / unitLength;

		// First visible point from the left and from the bottom
		double visibleLeft = (double) Math.round(pixToX(0)) + center.getX();
		double visibleBottom = (double) Math.round(pixToY(0)) + center.getY();

		// X axis scale
		CSPoint leftmost = new CSPoint(visibleLeft, 0.0);
		int scaleYpix1 = yToPix(0) + scaleLength / 2;
		int scaleYpix2 = yToPix(0) - scaleLength / 2;
		int scaleXpix;

		while (xToPix(leftmost.getX()) < width) {
			scaleXpix = xToPix(leftmost.getX());

			if (leftmost.getX() != 0 && ((leftmost.getX() % scaleValueInterval) == 0 || scaleValueInterval == 0)) {
				g.drawLine(scaleXpix, scaleYpix1, scaleXpix, scaleYpix2);
				g.drawString(String.valueOf((int) (double) leftmost.getX()), scaleXpix - 3, scaleYpix1 + 17);
			}

			leftmost.move(1.0, 0.0);
		}

		// Y axis scale
		CSPoint upmost = new CSPoint(0.0, visibleBottom);
		int scaleXpix1 = xToPix(0) + scaleLength / 2;
		int scaleXpix2 = xToPix(0) - scaleLength / 2;
		int scaleYpix;

		while (yToPix(upmost.getY()) > 0) {
			scaleYpix = yToPix(upmost.getY());

			if (upmost.getY() != 0 && ((upmost.getY() % scaleValueInterval) == 0 || scaleValueInterval == 0)) {
				g.drawLine(scaleXpix1, scaleYpix, scaleXpix2, scaleYpix);
				g.drawString(String.valueOf((int) (double) upmost.getY()), scaleXpix1 + 7, scaleYpix + 5);
			}

			upmost.move(0.0, 1.0);
		}

		g.dispose();
		repaint();
	}

	/**
	 * Draws given function.
	 * 
	 * @param function Function to draw.
	 */
	private void drawFunction(String function) {
		Graphics g = canvas.getGraphics();

		g.setColor(currentColor);

		double visibleLeft = (double) Math.floor(pixToX(0)) + center.getX();
		double visibleRight = (double) Math.ceil(pixToX(width)) + center.getX();

		disc.setFunction(function);
		disc.setInterval(visibleLeft, visibleRight);
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
	 * Sets given color as current graph color.
	 * 
	 * @param color Color to set.
	 */
	private void setColor(Color color) {
		currentColor = color;
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

	/**
	 * Calculates X coordinate from pixel value.
	 * 
	 * @param pix Pixels.
	 * @return X Coordinate.
	 */
	private double pixToX(int pix) {
		double half = width / 2;
		return half <= pix ? (double) (pix - half) / unitLength : (double) -(half - pix) / unitLength;
	}

	/**
	 * Calculates Y coordinate from pixel value.
	 * 
	 * @param pix Pixels.
	 * @return Y Coordinate.
	 */
	private double pixToY(int pix) {
		double half = height / 2;
		return half < pix ? (double) (pix - half) / unitLength : (double) -(half - pix) / unitLength;
	}

}
