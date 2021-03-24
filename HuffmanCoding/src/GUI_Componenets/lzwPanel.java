package GUI_Componenets;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Compressions.HuffmanTree;
import Compressions.LZWCompression;

public class lzwPanel extends JPanel{
	
	JFrame currentFrame;
	
	JLabel lzwTitle;
	
	JButton uploadFile;
	JButton downloadFile;
	JButton backButton;

	List<Integer> compressed;
	
	final static int HEIGHT = 540;
	final static int WIDTH = 960;
	final int BUTTON_WIDTH = 300;
	final int BUTTON_HEIGHT = 75;
	final int DISTANCE_BETWEEN_BUTTONS = 125;
	final int FONT_SIZE = 45;
	final int SIZE_ADJUSTMENT_RATE = 5;
	final int FONT_SIZE_ADJUSTMENT = 5;
	
	public lzwPanel(JFrame currentFrame) {
		currentFrame.setContentPane(this);
		initialize(currentFrame);
	}

	private void initialize(JFrame currentFrame) {
		this.removeAll();
		this.setBounds(0,0,WIDTH, HEIGHT);
		this.setLayout(null);
		
		lzwTitle = new JLabel ("lzw Compression");
		lzwTitle.setFont(new Font("Delta Ray", Font.PLAIN, FONT_SIZE));
		lzwTitle.setBounds(DISTANCE_BETWEEN_BUTTONS/ SIZE_ADJUSTMENT_RATE, DISTANCE_BETWEEN_BUTTONS/ SIZE_ADJUSTMENT_RATE - 50, 
				FONT_SIZE * (FONT_SIZE_ADJUSTMENT * SIZE_ADJUSTMENT_RATE), FONT_SIZE* FONT_SIZE_ADJUSTMENT);		
		this.add(lzwTitle);
		
		uploadFile = new JButton("Select a File to Compress!");
		uploadFile.setBounds((lzwTitle.getX() + BUTTON_WIDTH) / 2, lzwTitle.getY() - DISTANCE_BETWEEN_BUTTONS + (BUTTON_HEIGHT*SIZE_ADJUSTMENT_RATE)
				, BUTTON_WIDTH, BUTTON_HEIGHT);
		uploadFile.addActionListener(new UploadButtonListener());
		this.add(uploadFile);
		
		downloadFile = new JButton("Select a File to Decompress!");
		downloadFile.setBounds((lzwTitle.getX() + BUTTON_WIDTH) / 2, lzwTitle.getY()  + (BUTTON_HEIGHT*SIZE_ADJUSTMENT_RATE)
				, BUTTON_WIDTH, BUTTON_HEIGHT);
		downloadFile.addActionListener(new DownloadButtonListener());
		this.add(downloadFile);
		
		backButton = new JButton(" < Back");
		backButton.setBounds(WIDTH - 150, 50, 100, 50);
		backButton.addActionListener(new backButtonListener());
		this.add(backButton);
		
		repaint();
	}
	
	class UploadButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
            Component component = (Component) e.getSource();
            currentFrame = (JFrame) SwingUtilities.getRoot(component);
            int option = fileChooser.showOpenDialog(currentFrame);
            if(option == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               try {
				compressFile(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
               uploadFile.setText("Folder Selected: " + file.getName());
            }else{
            	uploadFile.setText("Open command canceled");
            }
            
            
            repaint();
         }

		private void compressFile(File file) throws IOException {
			LZWCompression lzw = new LZWCompression();
			Scanner reader = new Scanner(file);
			reader.useDelimiter("\\Z");
			String str = setFileName(file);
			FileWriter writer = new FileWriter(str, false);
			writer.append("");
			writer = new FileWriter(str, true);
			
			String data;
			data = reader.next();
			compressed = lzw.compress(data);
			writeToFile(compressed, file, writer);
			/*while(reader.hasNextLine()) {
				data = reader.nextLine();
				compressed = lzw.compress(data);
				writeToFile(compressed, file, writer);
			}*/
		}

		private void writeToFile(List<Integer> compressed, File file, FileWriter writer) {
			String str = "lzwCompression_"+file.getName();
			
			try {
		    	  writer.write(compressed.toString());
		    	  writer.close();
			} catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			}
		}
		
		
		private String setFileName(File file) {
			String str2 = file.getPath();
			int loc = str2.lastIndexOf('\\');
			str2=str2.substring(0,loc+1);
			String str = str2+"lzwCompression_"+file.getName();
			return str;
		}
	}
	
	class DownloadButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
            Component component = (Component) e.getSource();
            currentFrame = (JFrame) SwingUtilities.getRoot(component);
            int option = fileChooser.showOpenDialog(currentFrame);
            if(option == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               try {
            	   decompressFile(file);
               } catch (IOException e1) {
            	   e1.printStackTrace();
               }
            	downloadFile.setText("Folder Selected: " + file.getName());
            }else{
            	downloadFile.setText("Open command canceled");
            }

            repaint();
         }

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

		private void writeToFile(File file, String data, FileWriter writer) {
			String str = "lzwCompression_"+file.getName();
			
			try {
		    	  writer.write(data.toString());
		    	  writer.close();
			} catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			}
		}
		
		private String setFileName(File file) {
			String str1 = file.getName();
			int loc1 = str1.indexOf('_');
			str1 = str1.substring(loc1+1);
			String str2 = file.getPath();
			int loc2 = str2.lastIndexOf('\\');
			str2=str2.substring(0,loc2+1);
			String str = str2+"lzwDecompression_"+str1;
			return str;
		}
	}
	class backButtonListener implements ActionListener {
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
