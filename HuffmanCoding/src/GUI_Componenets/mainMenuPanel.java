package GUI_Componenets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class mainMenuPanel extends JPanel{

	JFrame currentFrame;

	JLabel title;
	JLabel bgImage;
	JLabel footer;

	JButton lzwButton;
	JButton huffmanButton;


	final static int HEIGHT = 540;
	final static int WIDTH = 960;
	final int BUTTON_WIDTH = 300;
	final int BUTTON_HEIGHT = 75;
	final int DISTANCE_BETWEEN_BUTTONS = 125;
	final int FONT_SIZE = 35;
	final int BUTTON_FONT_SIZE = 15;
	final int FOOTER_LOCX = 800;

	mainMenuButtonListener buttonListener;

	public mainMenuPanel(JFrame currentFrame) {
		currentFrame.setContentPane(this);
		initialize(currentFrame);

	}

	//Default Constructor
	public mainMenuPanel() {

	}


	private void initialize(JFrame currentFrame) {
		this.setLayout(null);
		buttonListener = new mainMenuButtonListener();
		currentFrame.getContentPane().setBounds(0,0,WIDTH,HEIGHT);
		currentFrame.getContentPane().setBackground(Color.WHITE);

		//Adding panel title
		title = new JLabel("Compression Algorithms");
		title.setFont(new Font("Delta Ray", Font.PLAIN, FONT_SIZE));
		title.setForeground(Color.RED);
		title.setBounds(WIDTH / 2 - BUTTON_WIDTH / 2 - BUTTON_HEIGHT, 0, DISTANCE_BETWEEN_BUTTONS*4, DISTANCE_BETWEEN_BUTTONS + BUTTON_HEIGHT);
		this.add(title);

		//Adding panel footer
		footer = new JLabel("by Burak SAL");
		footer.setFont(new Font("Delta Ray", Font.PLAIN, BUTTON_FONT_SIZE));
		footer.setForeground(Color.CYAN);
		footer.setBounds(FOOTER_LOCX, (HEIGHT-DISTANCE_BETWEEN_BUTTONS) , BUTTON_WIDTH / 2, BUTTON_HEIGHT);
		this.add(footer);

		//Adding button for LZW Compression
		lzwButton = new JButton("LZW Compression");
		lzwButton.setBounds((WIDTH-BUTTON_WIDTH) / 2 ,(HEIGHT-BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
		lzwButton.setForeground(Color.PINK);
		lzwButton.setFont(new Font("Delta Ray", Font.PLAIN, BUTTON_FONT_SIZE));
		lzwButton.addActionListener(buttonListener);
		lzwButton.setOpaque(false);
		lzwButton.setContentAreaFilled(false);
		lzwButton.setBorderPainted(false);
		this.add(lzwButton);

		//Adding button for Huffman Compression
		huffmanButton = new JButton("Huffman Compression");
		huffmanButton.setBounds((WIDTH-BUTTON_WIDTH) / 2, (HEIGHT-BUTTON_HEIGHT) / 2 + DISTANCE_BETWEEN_BUTTONS, BUTTON_WIDTH, BUTTON_HEIGHT);
		huffmanButton.setForeground(Color.PINK);
		huffmanButton.setFont(new Font("Delta Ray", Font.PLAIN, BUTTON_FONT_SIZE));
		huffmanButton.addActionListener(buttonListener);
		huffmanButton.setOpaque(false);
		huffmanButton.setContentAreaFilled(false);
		huffmanButton.setBorderPainted(false);
		this.add(huffmanButton);

		//Adding Background Image
		bgImage = new JLabel(new ImageIcon("resources\\UnCompressedScrew.jpg"));
		bgImage.setBounds(0,0, WIDTH,HEIGHT);
		this.add(bgImage);



		repaint();
	}

	class mainMenuButtonListener implements ActionListener {
		//Navigates to the desired panel upon button click
		@Override
		public void actionPerformed(ActionEvent e) {
			Component component = (Component) e.getSource();
			currentFrame = (JFrame) SwingUtilities.getRoot(component);
			if (e.getSource() == lzwButton){
				new lzwPanel(currentFrame);
				return;
			} else if(e.getSource() == huffmanButton);{
				new huffmanPanel(currentFrame);
			}	
		}
	}

}
