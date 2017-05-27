package pl.bazylicyran.funcanalyzer.graphics;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		// Expression field label
		JLabel expressionLabel = new JLabel("Wzór funkcji:");
		c.gridy = 0;
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
		c.weighty = 1;
		leftPane.add(expressionButton, c);
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
	 * Passes given function to CoordinateSystem and draws it.
	 * 
	 * @param function Function to draw.
	 */
	private void drawFunction(String function) {
		coordinateSystem.addFunction(function);
	}

}
