package pl.bazylicyran.funcanalyzer.graphics;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pl.bazylicyran.funcanalyzer.FunctionAnalyzer;
import pl.bazylicyran.funcanalyzer.math.FunctionTransformer;
import pl.bazylicyran.funcanalyzer.parsing.ExpressionException;

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

	/** Function transformer object. */
	private final FunctionTransformer trans = new FunctionTransformer();

	/** Current function. */
	private String function;

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

		int anchor = GridBagConstraints.NORTHWEST;
		int fill = GridBagConstraints.HORIZONTAL;
		Insets insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 0);

		// Function section label
		JLabel expressionLabel = new JLabel("Functions");
		addElement(leftPane, expressionLabel, 0, 0, 4, 1, 0, 0, anchor, fill, insets);

		// Expression text field
		JTextField expressionField = new JTextField();
		addElement(leftPane, expressionField, 0, 1, 4, 1, 0, 0, anchor, fill, insets);
		expressionField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawFunction(expressionField.getText());
			}
		});

		// Draw button
		JButton expressionButton = new JButton("Draw");
		addElement(leftPane, expressionButton, 0, 2, 4, 1, 0, 0, anchor, fill, insets);
		expressionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawFunction(expressionField.getText());
			}
		});

		// Add button
		JButton addButton = new JButton("Add");
		insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		addElement(leftPane, addButton, 0, 3, 2, 1, 0, 0, anchor, fill, insets);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFunction(expressionField.getText());
			}
		});

		// Clear button
		JButton clearButton = new JButton("Clear");
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		addElement(leftPane, clearButton, 2, 3, 2, 1, 0, 0, anchor, fill, insets);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFunction();
			}
		});

		// CoordinateSystem section field label
		JLabel systemLabel = new JLabel("Coordinate system");
		insets = new Insets(borderWidth, 0, borderWidth / 2, 0);
		addElement(leftPane, systemLabel, 0, 4, 4, 1, 0, 0, anchor, fill, insets);

		// Zoom- button
		JButton zoomMinusButton = new JButton("Zoom -");
		insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		addElement(leftPane, zoomMinusButton, 0, 5, 2, 1, 1, 0, anchor, fill, insets);
		zoomMinusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomMinus();
			}
		});

		// Zoom+ button
		JButton zoomPlusButton = new JButton("Zoom +");
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		addElement(leftPane, zoomPlusButton, 2, 5, 2, 1, 1, 0, anchor, fill, insets);
		zoomPlusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomPlus();
			}
		});

		// Move left button
		JButton moveLeftButton = new JButton("<");
		insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		addElement(leftPane, moveLeftButton, 0, 6, 1, 1, 1, 0, anchor, fill, insets);
		moveLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveLeft();
			}
		});

		// Move down button
		JButton moveDownButton = new JButton("v");
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 5);
		addElement(leftPane, moveDownButton, 1, 6, 1, 1, 1, 0, anchor, fill, insets);
		moveDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveDown();
			}
		});

		// Move up button
		JButton moveUpButton = new JButton("^");
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 5);
		addElement(leftPane, moveUpButton, 2, 6, 1, 1, 1, 0, anchor, fill, insets);
		moveUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveUp();
			}
		});

		// Move right button
		JButton moveRightButton = new JButton(">");
		insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		addElement(leftPane, moveRightButton, 3, 6, 1, 1, 1, 0, anchor, fill, insets);
		moveRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveRight();
			}
		});

		// Reset button
		JButton resetButton = new JButton("Reset");
		insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 0);
		addElement(leftPane, resetButton, 0, 7, 4, 1, 0, 0, anchor, fill, insets);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMovement();
			}
		});

		// Function transformations label
		JLabel transLabel = new JLabel("Function transformations");
		insets = new Insets(borderWidth, 0, borderWidth / 2, 0);
		addElement(leftPane, transLabel, 0, 8, 4, 1, 0, 0, anchor, fill, insets);

		// Function transformations checkboxes
		insets = new Insets(borderWidth / 4, 0, 0, 0);
		JCheckBox symmetryXcheck = new JCheckBox("X axis symmetry");
		addElement(leftPane, symmetryXcheck, 0, 9, 4, 1, 0, 0, anchor, fill, insets);
		JCheckBox symmetryYcheck = new JCheckBox("Y axis symmetry");
		addElement(leftPane, symmetryYcheck, 0, 10, 4, 1, 0, 0, anchor, fill, insets);
		JCheckBox absXcheck = new JCheckBox("Absolute value of x");
		addElement(leftPane, absXcheck, 0, 11, 4, 1, 0, 0, anchor, fill, insets);
		JCheckBox absYcheck = new JCheckBox("Absolute value of y");
		addElement(leftPane, absYcheck, 0, 12, 4, 1, 0, 1, anchor, fill, insets);

		ItemListener checkboxListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				trans.setTransform("symmetryX", symmetryXcheck.isSelected());
				trans.setTransform("symmetryY", symmetryYcheck.isSelected());
				trans.setTransform("absX", absXcheck.isSelected());
				trans.setTransform("absY", absYcheck.isSelected());

				coordinateSystem.clearLastFunction();
				addFunction(function);
			}
		};

		symmetryXcheck.addItemListener(checkboxListener);
		symmetryYcheck.addItemListener(checkboxListener);
		absXcheck.addItemListener(checkboxListener);
		absYcheck.addItemListener(checkboxListener);
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
		if (function != null && !function.isEmpty() && !coordinateSystem.inFunctions(function)) {
			coordinateSystem.clearDrawingArea();
			coordinateSystem.clearFunctions();
			addFunction(function);
		}
	}

	/*
	 * Draws new function on CoordinateSystem without clearing.
	 */
	private void addFunction(String function) {
		if (function != null && !function.isEmpty() && !coordinateSystem.inFunctions(transform(function))) {
			try {
				coordinateSystem.addFunction(transform(function));
			} catch (ExpressionException e) {
				expressionError(e);
				return;
			}

			this.function = function;
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

	/**
	 * Apply set transformations to function.
	 * 
	 * @param function Function to transform.
	 * @return Transformed function.
	 */
	private String transform(String function) {
		trans.setFunction(function);
		return trans.transform();
	}

	/**
	 * Show error message.
	 * 
	 * @param e Exception.
	 */
	private void expressionError(ExpressionException e) {

		String message = e.getMessage();

		if (e.getToken() != null) {
			message += "\nCurrent token: " + e.getToken();
		}

		if (e.getLastToken() != null) {
			message += "\nLast token: " + e.getLastToken();
		}

		JOptionPane.showMessageDialog(this, message, "Expression error", JOptionPane.ERROR_MESSAGE);

		coordinateSystem.clearLastFunction();
		coordinateSystem.drawFunctions();
	}

}
