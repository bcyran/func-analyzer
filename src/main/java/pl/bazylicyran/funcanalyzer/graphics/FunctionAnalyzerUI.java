package pl.bazylicyran.funcanalyzer.graphics;

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

		// Function section label
		JLabel expressionLabel = new JLabel("Rysowanie funkcji");
		c.gridy = 0;
		c.gridwidth = 4;
		leftPane.add(expressionLabel, c);

		// Expression text field
		JTextField expressionField = new JTextField();
		expressionField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawFunction(expressionField.getText());
			}
		});
		c.gridy = 1;
		leftPane.add(expressionField, c);

		// Draw button
		JButton expressionButton = new JButton("Rysuj");
		expressionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawFunction(expressionField.getText());
			}
		});
		c.gridy = 2;
		leftPane.add(expressionButton, c);

		// Add button
		JButton addButton = new JButton("Dodaj");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFunction(expressionField.getText());
			}
		});
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		leftPane.add(addButton, c);

		// Clear button
		JButton clearButton = new JButton("Wyczyœæ");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFunction();
			}
		});
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 2;

		c.insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		leftPane.add(clearButton, c);

		// CoordinateSystem section field label
		JLabel systemLabel = new JLabel("Uk³ad wspó³rzêdnych");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 4;
		c.insets = new Insets(borderWidth, 0, borderWidth / 2, 0);
		leftPane.add(systemLabel, c);

		// Zoom+ button
		JButton zoomPlusButton = new JButton("Zoom+");
		zoomPlusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomPlus();
			}
		});
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		leftPane.add(zoomPlusButton, c);

		// Zoom- button
		JButton zoomMinusButton = new JButton("Zoom-");
		zoomMinusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoomMinus();
			}
		});
		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 2;
		c.insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		leftPane.add(zoomMinusButton, c);
		
		// Move left button
		JButton moveLeftButton = new JButton("<");
		moveLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveLeft();
			}
		});
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.insets = new Insets(borderWidth / 2, 0, borderWidth / 2, 5);
		leftPane.add(moveLeftButton, c);
		
		// Move down button
		JButton moveDownButton = new JButton("v");
		moveDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveDown();
			}
		});
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.weighty = 1;
		c.insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 5);
		leftPane.add(moveDownButton, c);
		
		// Move up button
		JButton moveUpButton = new JButton("^");
		moveUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveUp();
			}
		});
		c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 1;
		c.weighty = 1;
		c.insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 5);
		leftPane.add(moveUpButton, c);
		
		// Move right button
		JButton moveRightButton = new JButton(">");
		moveRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveRight();
			}
		});
		c.gridx = 3;
		c.gridy = 6;
		c.gridwidth = 1;
		c.weighty = 1;
		c.insets = new Insets(borderWidth / 2, 5, borderWidth / 2, 0);
		leftPane.add(moveRightButton, c);
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

}
