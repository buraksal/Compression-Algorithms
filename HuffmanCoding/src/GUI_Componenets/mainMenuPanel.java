package GUI_Componenets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class mainMenuPanel extends JPanel{
	
	JFrame currentFrame;
	
	JLabel title;
	
	JButton lzwButton;
	JButton huffmanButton;
	
	final static int HEIGHT = 540;
	final static int WIDTH = 960;
	final int BUTTON_WIDTH = 300;
	final int BUTTON_HEIGHT = 75;
	final int DISTANCE_BETWEEN_BUTTONS = 125;
	final int FONT_SIZE = 35;
	
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
		
		title = new JLabel("Compression Algorithms");
		title.setFont(new Font("Delta Ray", Font.PLAIN, FONT_SIZE));
		title.setBounds(WIDTH / 2 - BUTTON_WIDTH / 2, 0, 600, 200);
		this.add(title);
		
		lzwButton = new JButton("LZW Compression");
		lzwButton.setBounds((WIDTH-BUTTON_WIDTH) / 2 ,(HEIGHT-BUTTON_HEIGHT) / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
		lzwButton.addActionListener(buttonListener);
		this.add(lzwButton);
		
		huffmanButton = new JButton("Huffman Compression");
		huffmanButton.setBounds((WIDTH-BUTTON_WIDTH) / 2, (HEIGHT-BUTTON_HEIGHT) / 2 + DISTANCE_BETWEEN_BUTTONS, BUTTON_WIDTH, BUTTON_HEIGHT);
		huffmanButton.addActionListener(buttonListener);
		this.add(huffmanButton);
				
		repaint();
	}
	
	class mainMenuButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Component component = (Component) e.getSource();
			currentFrame = (JFrame) SwingUtilities.getRoot(component);
			if (e.getSource() == lzwButton){
				System.out.println("LZW Buton");
				new lzwPanel(currentFrame);
				return;
			} else if(e.getSource() == huffmanButton);{
				System.out.println("HuffmanButton");
				new huffmanPanel(currentFrame);
			}
				
		}

		
	}
	
}
