package pl.bazylicyran.funcanalyzer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import pl.bazylicyran.funcanalyzer.graphics.FunctionAnalyzerUI;

/**
 * App that parses, analyzes and draws a mathematical functions.
 * 
 * @author Bazyli Cyran
 */
public class FunctionAnalyzer extends JFrame {

	/** Application name used e.g. as window title */
	public final static String appName = "Function Analyzer";

	/** Dimensions of application window. */
	public final static Dimension appDimension = new Dimension(1000, 600);

	/**
	 * Entry point for program.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame window = new JFrame(appName);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setLayout(new BorderLayout());
				window.setContentPane(new FunctionAnalyzerUI());
				window.pack();
				window.setLocationRelativeTo(null);
				window.setVisible(true);
			}
		});
	}

}
