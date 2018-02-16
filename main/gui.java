/**
 * Calculator GUI from Top-Down Parsing Algorithm
 * Sawyer Clever
 * 11/12/17
 **/

package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.apple.eawt.Application;

public class gui {
	
	static calc parser = null;

	private JFrame frame;
	private JTextField calcTextField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public gui() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		
		/*
		 * 'Application Icon'
		 * Tries to set an application icon for Windows and Mac operating systems.
		 */
		try {
			//Windows OS
		    frame.setIconImage(ImageIO.read(new File("resources/icon.png")));
		    //Mac OS
		    Application.getApplication().setDockIconImage(new ImageIcon("resources/icon.png").getImage());
		} catch (IOException exc) {
		    exc.printStackTrace();
		}
		
		/*
		 * 'Interface Properties'
		 * Sets the application's title, fixed-dimensions, close operation and position
		 */
		frame.setTitle("Calculator Integrating a Top-Down Parser");
		frame.setSize(450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		
		/*
		 * 'Input Field Label'
		 * Sets the label's value and it's dimensions and adds it to the interface
		 */	
		JLabel enterLabel = new JLabel("Enter a numeric calculation to parse:");
		enterLabel.setBounds(30, 18, 239, 16);
		frame.getContentPane().add(enterLabel);
		
		/*
		 * 'Input Text Field'
		 * 
		 */
		calcTextField = new JTextField();
		calcTextField.setText("(1+2)*3");
		calcTextField.setBounds(300, 13, 96, 26);
		frame.getContentPane().add(calcTextField);
		calcTextField.setColumns(10);
		
		/*
		 * 'Calculate Button'
		 * 
		 */
		JButton calculateButton = new JButton("Calculate");
		calculateButton.setBounds(300, 39, 96, 26);
		frame.getContentPane().add(calculateButton);
		frame.getRootPane().setDefaultButton(calculateButton);

		//horizontal separator
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 70, 438, 12);
		frame.getContentPane().add(separator);
		
		//output field label
		JLabel outputLabel = new JLabel("Output:");
		outputLabel.setBounds(30, 94, 61, 16);
		frame.getContentPane().add(outputLabel);
		
		//output field
		JTextPane outputTextPane = new JTextPane();
		outputTextPane.setBounds(30, 122, 388, 123);
		frame.getContentPane().add(outputTextPane);
		outputTextPane.setEditable(false);
	
		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {	
				if(calcTextField.getText().isEmpty())
					outputTextPane.setText("Please enter a value in the input section.");
				else {
					String input = calcTextField.getText();
					InputStream is = new ByteArrayInputStream(input.getBytes());
					//Printing last character from input
					System.out.println(input.substring(input.length() - 1));
					if(parser == null)
						parser = new calc(is);
					else
						calc.ReInit(is);
		            try {
						Double output = calc.parse();
						outputTextPane.setText("= "+ output + "\nParse Successful.");
					} catch (ParseException e) {
						outputTextPane.setText("Errora: " + e.getMessage());
					} catch (Exception e) {
						outputTextPane.setText("Errorb: " + e.getMessage());
					} catch (Error e)  {
						outputTextPane.setText("Error: Entered character not supported.");
					}
				}
			}
		});
	}
}
