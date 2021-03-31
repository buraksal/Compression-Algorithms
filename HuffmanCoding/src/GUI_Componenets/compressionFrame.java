package GUI_Componenets;

import java.awt.Color;

import javax.swing.JFrame;

public class compressionFrame extends JFrame{

	final static int HEIGHT = 540;
	final static int WIDTH = 960;

	public static void main(String[] args) {
		JFrame compressionFrame = new JFrame("Compression Algorithms");

		// setting the size of the frame
		compressionFrame.setBounds(0,0, WIDTH, HEIGHT);
		// Setting the layout of the frame
		compressionFrame.setLayout(null);
		compressionFrame.getContentPane().setBackground(Color.DARK_GRAY);
		// adding default close operation to the frame and making it visible
		compressionFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		compressionFrame.setResizable(false);
		compressionFrame.setVisible( true );

		new mainMenuPanel(compressionFrame);

	}
}
