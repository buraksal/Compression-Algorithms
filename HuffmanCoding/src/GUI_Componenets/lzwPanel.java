package GUI_Componenets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Compressions.LZWCompression;

public class lzwPanel extends JPanel{

	ArrayList<String> picExtentions = new ArrayList<>(Arrays.asList("png", "jpg", "bmp", "gif", "jpeg", "mp4"));

	JFrame currentFrame;

	JLabel lzwTitle;
	JLabel bgImage;
	JLabel footer;

	JButton uploadFile;
	JButton downloadFile;
	JButton backButton;

	String extention;

	List<Integer> compressed;

	byte[] bytes;

	final static int HEIGHT = 540;
	final static int WIDTH = 960;
	final int BUTTON_WIDTH = 300;
	final int BUTTON_HEIGHT = 75;
	final int DISTANCE_BETWEEN_BUTTONS = 125;
	final int FONT_SIZE = 45;
	final int SIZE_ADJUSTMENT_RATE = 5;
	final int FONT_SIZE_ADJUSTMENT = 5;
	final int BUTTON_FONT_SIZE = 15;
	final int FOOTER_LOCX = 800;

	public lzwPanel(JFrame currentFrame) {
		currentFrame.setContentPane(this);
		initialize(currentFrame);
	}

	private void initialize(JFrame currentFrame) {
		this.removeAll();
		this.setBounds(0,0,WIDTH, HEIGHT);
		this.setLayout(null);

		//Adding main title
		lzwTitle = new JLabel ("lzw Compression");
		lzwTitle.setFont(new Font("Delta Ray", Font.PLAIN, FONT_SIZE));
		lzwTitle.setForeground(Color.RED);
		lzwTitle.setBounds(DISTANCE_BETWEEN_BUTTONS/ SIZE_ADJUSTMENT_RATE, DISTANCE_BETWEEN_BUTTONS/ SIZE_ADJUSTMENT_RATE - 50, 
				FONT_SIZE * (FONT_SIZE_ADJUSTMENT * SIZE_ADJUSTMENT_RATE), FONT_SIZE* FONT_SIZE_ADJUSTMENT);		
		this.add(lzwTitle);

		//Adding footer to panel
		footer = new JLabel("by Burak SAL");
		footer.setFont(new Font("Delta Ray", Font.PLAIN, BUTTON_FONT_SIZE));
		footer.setForeground(Color.CYAN);
		footer.setBounds(FOOTER_LOCX, (HEIGHT-DISTANCE_BETWEEN_BUTTONS) , BUTTON_WIDTH / 2, BUTTON_HEIGHT);
		this.add(footer);

		//Adding file chooser button to panel for compression
		uploadFile = new JButton("Select a File to Compress!");
		uploadFile.setBounds((lzwTitle.getX() + BUTTON_WIDTH) / 2, lzwTitle.getY() - DISTANCE_BETWEEN_BUTTONS + (BUTTON_HEIGHT*SIZE_ADJUSTMENT_RATE)
				, BUTTON_WIDTH, BUTTON_HEIGHT);
		uploadFile.setFont(new Font("Delta Ray", Font.PLAIN, BUTTON_FONT_SIZE));
		uploadFile.setForeground(Color.PINK);
		uploadFile.addActionListener(new UploadButtonListener());
		uploadFile.setOpaque(false);
		uploadFile.setContentAreaFilled(false);
		uploadFile.setBorderPainted(false);
		this.add(uploadFile);

		//Adding file chooser button to panel for decompression
		downloadFile = new JButton("Select a File to Decompress!");
		downloadFile.setBounds((lzwTitle.getX() + BUTTON_WIDTH) / 2, lzwTitle.getY()  + (BUTTON_HEIGHT*SIZE_ADJUSTMENT_RATE)
				, BUTTON_WIDTH, BUTTON_HEIGHT);
		downloadFile.setFont(new Font("Delta Ray", Font.PLAIN, BUTTON_FONT_SIZE));
		downloadFile.setForeground(Color.PINK);
		downloadFile.addActionListener(new DownloadButtonListener());
		downloadFile.setOpaque(false);
		downloadFile.setContentAreaFilled(false);
		downloadFile.setBorderPainted(false);
		this.add(downloadFile);

		//Adding back button to return to main screeen
		backButton = new JButton(" < Back");
		backButton.setBounds(WIDTH - 150, 50, 100, 50);
		backButton.setForeground(Color.PINK);
		backButton.addActionListener(new backButtonListener());
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		this.add(backButton);

		//Adding background image to the panel
		bgImage = new JLabel(new ImageIcon("resources\\CompressedScrew.jpg"));
		bgImage.setBounds(0,0, WIDTH,HEIGHT);
		this.add(bgImage);

		repaint();
	}

	class UploadButtonListener implements ActionListener {
		//Gets the file and does the compression
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			Component component = (Component) e.getSource();
			currentFrame = (JFrame) SwingUtilities.getRoot(component);
			int option = fileChooser.showOpenDialog(currentFrame);
			if(option == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				setExtention(file);
				try {
					if(extention.equals("txt")) {
						compressFile(file);
					}            		   
					else {
						compressOtherExtention(file);
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				uploadFile.setText("Folder Selected: " + file.getName());
			}else{
				uploadFile.setText("Open command canceled");
			}

			repaint();
		}

		//Compresses the files with other extentions than txt
		private void compressOtherExtention(File file) throws IOException {
			if(picExtentions.contains(extention)) {
				byte[] data2 = new byte[(int) file.length()];
				FileInputStream in = new FileInputStream(file);
				in.read(data2);
				in.close();
				bytes = data2;
				String str = imageToText(file);
				LZWCompression lzw = new LZWCompression();
				String fileName = setPathExtention(file);
				FileWriter writer = new FileWriter(fileName, true);
				System.out.println("Here");
				writer.append("");
				//writer = new FileWriter(str, true);
				compressed = lzw.compress(str);
				writeToFile(compressed, file, writer);
				System.out.println("Done!");
			}

		}
		
		//Returns the byte array as string
		private String imageToText(File file) throws IOException {
			String str = "";
			str = Arrays.toString(bytes);
			return str;
		}

		//Gets the file name
		private String getFileName(File file) {
			String name = file.getName();
			int loc = name.lastIndexOf('.');
			name = name.substring(0, loc);
			return name;
		}
		
		//Sets the extention as global to be reached later
		private void setExtention(File file) {
			String fileExtention = file.getName();
			int loc = fileExtention.lastIndexOf('.');
			fileExtention = fileExtention.substring(loc + 1);
			extention = fileExtention;
		}

		//Sets the file to be created's name
		private String setPathExtention(File file) {
			String str = getFileName(file);
			String path = file.getPath();
			int loc = path.lastIndexOf('\\');
			path=path.substring(0,loc+1);
			String str2 = path+"lzwCompression_" + str + ".txt";
			return str2;
		}
		
		//Compresses txt files
		private void compressFile(File file) throws IOException {
			LZWCompression lzw = new LZWCompression();
			Scanner reader = new Scanner(file);
			reader.useDelimiter("\\Z");
			String str = setPathExtention(file);
			FileWriter writer = new FileWriter(str, true);
			writer.append("");
			String data;
			data = reader.next();
			compressed = lzw.compress(data);
			writeToFile(compressed, file, writer);
		}

		//Writes output to txt file
		private void writeToFile(List<Integer> compressed, File file, FileWriter writer) {
			try {
				writer.write(compressed.toString()); 
				writer.close();
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}

	class DownloadButtonListener implements ActionListener{
		//Gets the file and does the decompression
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			Component component = (Component) e.getSource();
			currentFrame = (JFrame) SwingUtilities.getRoot(component);
			int option = fileChooser.showOpenDialog(currentFrame);
			if(option == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				try {
					if(!extention.equals("txt")) {
						decompressOtherExtentions(file);
						//changeFileExtention(file);
					} else {
						decompressFile(file);
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				downloadFile.setText("Folder Selected: " + file.getName());
			}else{
				downloadFile.setText("Open command canceled");
			}

			repaint();
		}

		//Does the decompression for files with other extentions than txt
		private void decompressOtherExtentions(File file) throws IOException {
			LZWCompression lzw = new LZWCompression();
			String decompressed = lzw.decompress(compressed);
			String[] input = decompressed.replaceAll("[\\[\\]]", "").split(", ");
			byte[] output = new byte[decompressed.length()];

			for (int i = 0; i < input.length; i++) {
				output[i] = Byte.parseByte(input[i]);
			}

			if(picExtentions.contains(extention)) {
				ByteArrayInputStream bis = new ByteArrayInputStream(output);
				BufferedImage image = ImageIO.read(bis);
				String currentPath = setFileName(file);
				ImageIO.write(image, extention,  new File(currentPath) );
			}

		}

		//Does the decompression for files with txt extention
		private void decompressFile(File file) throws IOException {
			LZWCompression lzw = new LZWCompression();
			Scanner reader = new Scanner(file);
			reader.useDelimiter("\\Z");
			String str = setFileName(file);
			FileWriter writer = new FileWriter(str, false);
			writer.append("");
			writer = new FileWriter(str, true);		
			String data;
			data = reader.next();
			String decompressed = lzw.decompress(compressed);
			writeToFile(file, decompressed, writer);
		}

		//Writes the decompressed version to a file
		private void writeToFile(File file, String data, FileWriter writer) {
			try {
				writer.write(data.toString());
				writer.close();
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

		//Sets the name of the decompressed file
		private String setFileName(File file) {
			String fileName = file.getAbsolutePath();
			int loc = fileName.lastIndexOf('.');
			fileName = fileName.substring(0, loc);
			fileName += ("."+ extention);
			fileName = fileName.replace("Compression", "Decompression");
			return fileName;
		}

	}
	class backButtonListener implements ActionListener {
		//Navigates to the main menu
		@Override
		public void actionPerformed(ActionEvent e) {

			Component component = (Component) e.getSource();
			currentFrame = (JFrame) SwingUtilities.getRoot(component);
			if (e.getSource() == backButton){
				new mainMenuPanel(currentFrame);
			}
		}

	}

}
