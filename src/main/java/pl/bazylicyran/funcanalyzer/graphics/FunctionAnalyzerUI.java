package pl.bazylicyran.funcanalyzer.graphics;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pl.bazylicyran.funcanalyzer.FunctionAnalyzer;

/**
 * User interface for FunctionAnalyzer.
 * 
 * @author Bazyli Cyran
 */
public class FunctionAnalyzerUI extends JPanel {

	/** Left panel containing controls. */
	private final JPanel leftPane = new JPanel();

	/** Right panel containing canvas area. */
	private final JPanel rightPane = new JPanel();

	/** Total width of UI. */
	private final int paneWidth = FunctionAnalyzer.appDimension.width;

	/** Total height of UI. */
	private final int paneHeight = FunctionAnalyzer.appDimension.height;

	/** Width of the left pane. */
	private final int leftPaneWidth = paneWidth / 4;

	/** Width of the right pane. */
	private final int rightPaneWidth = paneWidth - leftPaneWidth;

	/** Border width. */
	private final int borderWidth = 10;

	/** Coordinate system to draw on. */
	private final CoordinateSystem coordinateSystem = new CoordinateSystem(rightPaneWidth - borderWidth,
			paneHeight - 2 * borderWidth);

	/**
	 * Calls method initializing UI.
	 */
	public FunctionAnalyzerUI() {
		initUI();
	}

	/**
	 * Initializes left pane.
	 */
	private void initLeftPane() {
		leftPane.setPreferredSize(new Dimension(leftPaneWidth, paneHeight));
		leftPane.setLayout(new GridBagLayout());
		leftPane.setBorder(BorderFactory.createEmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 0);
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;

		int anchor = GridBagConstraints.NORTHWEST;
		int fill = GridBagConstraints.HORIZONTAL;
		Insets insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 0);

		// Function section label
		JLabel expressionLabel = new JLabel("Functions");
		addElement(leftPane, expressionLabel, 0, 0, 4, 1, 0, 0, anchor, fill, insets);

		// Expression text field
		JTextField expressionField = new JTextField();
		expressionField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawFunction(expressionField.getText());
			}
		});
		addElement(leftPane, expressionField, 0, 1, 4, 1, 0, 0, anchor, fill, insets);

		// Draw button
		JButton expressionButton = new JButton("Draw");
		expressionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawFunction(expressionField.getText());
			}
		});
		addElement(leftPane, expressionButton, 0, 2, 4, 1, 0, 0, anchor, fill, insets);

		// Add button
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFunction(expressionField.getText());
			}
		});
		addElement(leftPane, addButton, 0, 3, 2, 1, 0, 0, anchor, fill, insets);

		// Clear button
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFunction();
			}
		});
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		addElement(leftPane, clearButton, 2, 3, 2, 1, 0, 0, anchor, fill, insets);

		// CoordinateSystem section field label
		JLabel systemLabel = new JLabel("Coordinate system");
		insets = new Insets(borderWidth, 0, borderWidth / 2, 0);
		addElement(leftPane, systemLabel, 0, 4, 4, 1, 0, 0, anchor, fill, insets);

		// Zoom- button
		JButton zoomMinusButton = new JButton("Zoom -");
		zoomMinusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomMinus();
			}
		});
		insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		addElement(leftPane, zoomMinusButton, 0, 5, 2, 1, 1, 0, anchor, fill, insets);

		// Zoom+ button
		JButton zoomPlusButton = new JButton("Zoom +");
		zoomPlusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomPlus();
			}
		});
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		addElement(leftPane, zoomPlusButton, 2, 5, 2, 1, 1, 0, anchor, fill, insets);

		// Move left button
		JButton moveLeftButton = new JButton("<");
		moveLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveLeft();
			}
		});
		insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		addElement(leftPane, moveLeftButton, 0, 6, 1, 1, 1, 0, anchor, fill, insets);

		// Move down button
		JButton moveDownButton = new JButton("v");
		moveDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveDown();
			}
		});
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 5);
		addElement(leftPane, moveDownButton, 1, 6, 1, 1, 1, 0, anchor, fill, insets);

		// Move up button
		JButton moveUpButton = new JButton("^");
		moveUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveUp();
			}
		});
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 5);
		addElement(leftPane, moveUpButton, 2, 6, 1, 1, 1, 0, anchor, fill, insets);

		// Move right button
		JButton moveRightButton = new JButton(">");
		moveRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveRight();
			}
		});
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		addElement(leftPane, moveRightButton, 3, 6, 1, 1, 1, 0, anchor, fill, insets);

		// Reset button
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMovement();
			}
		});
		insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 0);
		addElement(leftPane, resetButton, 0, 7, 4, 1, 0, 1, anchor, fill, insets);
	}

	/**
	 * Initializes right pane.
	 */
	private void initRightPane() {
		rightPane.setPreferredSize(new Dimension(rightPaneWidth, paneHeight));
		rightPane.setLayout(new GridBagLayout());
		rightPane.setBorder(BorderFactory.createEmptyBorder(borderWidth, 0, borderWidth, borderWidth));

		// Put coordinate system in right pane
		rightPane.add(coordinateSystem);

		// Zoom in and out on mouse scroll
		rightPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0) {
					zoomPlus();
				} else {
					zoomMinus();
				}
			}
		});
	}

	/**
	 * Initializes UI.
	 */
	private void initUI() {
		setPreferredSize(new Dimension(paneWidth, paneHeight));
		setLayout(new GridBagLayout());

		initLeftPane();
		initRightPane();

		add(leftPane);
		add(rightPane);
	}

	/**
	 * Shortcut for adding elements using GridBacContraints.
	 * 
	 * @param c Container.
	 * @param e Element to add.
	 */
	private void addElement(Container c, Component e, int gx, int gy, int gw, int gh, double wx, double wy, int anc,
			int fill, Insets ins) {
		GridBagConstraints gbc = new GridBagConstraints(gx, gy, gw, gh, wx, wy, anc, fill, ins, 0, 0);
		c.add(e, gbc);
	}

	/**
	 * Clears CoordinateSystem and draws new function.
	 * 
	 * @param function Function to draw.
	 */
	private void drawFunction(String function) {
		if (!function.isEmpty() && !coordinateSystem.inFunctions(function)) {
			coordinateSystem.clearDrawingArea();
			coordinateSystem.clearFunctions();
			coordinateSystem.addFunction(function);
		}
	}

	/*
	 * Draws new function on CoordinateSystem without clearing.
	 */
	private void addFunction(String function) {
		if (!function.isEmpty() && !coordinateSystem.inFunctions(function)) {
			coordinateSystem.addFunction(function);
		}
	}

	/**
	 * Clears CoordinateSystem.
	 */
	private void clearFunction() {
		coordinateSystem.clearDrawingArea();
		coordinateSystem.clearFunctions();
	}

	/**
	 * Zoom in coordinate system.
	 */
	private void zoomPlus() {
		coordinateSystem.zoomPlus();
	}

	/**
	 * Zoom out coordinate system.
	 */
	private void zoomMinus() {
		coordinateSystem.zoomMinus();
	}

	/**
	 * Move coordinate system left.
	 */
	private void moveLeft() {
		coordinateSystem.moveCenter(-1, 0);
	}

	/**
	 * Move coordinate system down.
	 */
	private void moveDown() {
		coordinateSystem.moveCenter(0, -1);
	}

	/**
	 * Move coordinate system up.
	 */
	private void moveUp() {
		coordinateSystem.moveCenter(0, 1);
	}

	/**
	 * Move coordinate system right.
	 */
	private void moveRight() {
		coordinateSystem.moveCenter(1, 0);
	}

	/**
	 * Set center point and zoom to default.
	 */
	private void resetMovement() {
		coordinateSystem.resetMovement();
	}

}
