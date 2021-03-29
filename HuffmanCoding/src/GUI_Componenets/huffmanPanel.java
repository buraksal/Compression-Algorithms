package GUI_Componenets;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Compressions.HuffmanTree;
import Compressions.HuffmanTree.HuffmanTreeNode;

public class huffmanPanel extends JPanel{
	
	ArrayList<String> picExtentions = new ArrayList<>(Arrays.asList("png", "jpg", "bmp", "gif", "jpeg"));
	
	JFrame currentFrame;
	
	JLabel huffmanTitle;
	JLabel compressionLabel;
	JLabel decompressionLabel;
	
	JButton uploadFile;
	JButton downloadFile;
	JButton backButton;
	
	HuffmanTreeNode root;
	
	String extention;
	
	byte[] bytes;

	final static int HEIGHT = 540;
	final static int WIDTH = 960;
	final int BUTTON_WIDTH = 300;
	final int BUTTON_HEIGHT = 75;
	final int LABEL_WIDTH = 75;
	final int LABEL_HEIGHT = 75;
	final int DISTANCE_BETWEEN_BUTTONS = 125;
	final int FONT_SIZE = 45;
	final int SIZE_ADJUSTMENT_RATE = 5;
	final int FONT_SIZE_ADJUSTMENT = 5;
	final int COMPLABEL_LOCX = 650;
	final int COMPLABEL_LOCY = 225;
	
	public huffmanPanel(JFrame currentFrame) {
		currentFrame.setContentPane(this);
		initialize(currentFrame);
	}

	private void initialize(JFrame currentFrame) {
		this.removeAll();
		this.setBounds(0,0,WIDTH, HEIGHT);
		this.setLayout(null);
		
		huffmanTitle = new JLabel ("huffman Compression");
		huffmanTitle.setFont(new Font("Delta Ray", Font.PLAIN, FONT_SIZE));
		huffmanTitle.setBounds(DISTANCE_BETWEEN_BUTTONS/ SIZE_ADJUSTMENT_RATE, DISTANCE_BETWEEN_BUTTONS/ SIZE_ADJUSTMENT_RATE - 50, 
				FONT_SIZE * (FONT_SIZE_ADJUSTMENT * SIZE_ADJUSTMENT_RATE), FONT_SIZE* FONT_SIZE_ADJUSTMENT);		
		this.add(huffmanTitle);
		
		uploadFile = new JButton("Select a File to Compress!");
		uploadFile.setBounds((huffmanTitle.getX() + BUTTON_WIDTH) / 2, huffmanTitle.getY() - DISTANCE_BETWEEN_BUTTONS + (BUTTON_HEIGHT*SIZE_ADJUSTMENT_RATE)
				, BUTTON_WIDTH, BUTTON_HEIGHT);
		uploadFile.addActionListener(new UploadButtonListener());
		this.add(uploadFile);
		
		compressionLabel = new JLabel("Compressing");
		compressionLabel.setFont(new Font("Delta Ray", Font.PLAIN, FONT_SIZE / FONT_SIZE_ADJUSTMENT * 3));
		compressionLabel.setBounds(COMPLABEL_LOCX, COMPLABEL_LOCY, LABEL_WIDTH * 3, LABEL_HEIGHT);
		
		downloadFile = new JButton("Select a File to Decompress!");
		downloadFile.setBounds((huffmanTitle.getX() + BUTTON_WIDTH) / 2, huffmanTitle.getY()  + (BUTTON_HEIGHT*SIZE_ADJUSTMENT_RATE)
				, BUTTON_WIDTH, BUTTON_HEIGHT);
		downloadFile.addActionListener(new DownloadButtonListener());
		this.add(downloadFile);
		
		decompressionLabel = new JLabel("Decompressing");
		decompressionLabel.setFont(new Font("Delta Ray", Font.PLAIN, FONT_SIZE / FONT_SIZE_ADJUSTMENT * 3));
		decompressionLabel.setBounds(COMPLABEL_LOCX, downloadFile.getY(), LABEL_WIDTH * 3, LABEL_HEIGHT);
		//this.add(decompressionLabel);
		
		backButton = new JButton(" < Back");
		backButton.setBounds(WIDTH - (BUTTON_WIDTH / 2), DISTANCE_BETWEEN_BUTTONS - BUTTON_HEIGHT, 100, 50);
		backButton.addActionListener(new backButtonListener());
		this.add(backButton);
		
		repaint();
	}
	
	class UploadButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
            Component component = (Component) e.getSource();
            currentFrame = (JFrame) SwingUtilities.getRoot(component);
            
            int option = fileChooser.showOpenDialog(currentFrame);
            if(option == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               currentFrame.getContentPane().add(compressionLabel);
               setExtention(file);
               try {
            	   if(extention.equals("txt"))
            		   compressFile(file);
            	   else
            		   compressOtherExtentions(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
               uploadFile.setText("Folder Selected: " + file.getName());
            }else{
            	uploadFile.setText("Open command canceled");
            }
            compressionLabel.setText("All Done!");
            repaint();
         }

		private void compressOtherExtentions(File file) throws IOException {
			
			if(picExtentions.contains(extention)) {
				byte[] data2 = new byte[(int) file.length()];
			    FileInputStream in = new FileInputStream(file);
			    in.read(data2);
			    in.close();
				bytes = data2;
				String str = imageToText(file);
				str = str.replace("[", "");
				str = str.replace("]", "");
				String fileName = setFileName(file);
				FileWriter writer = new FileWriter(fileName, false);
				writer.append("");
				writer = new FileWriter(fileName, true);
				writeToFile(file, str, writer);
			}
		}

		private void compressFile(File file) throws IOException {
			Scanner reader = new Scanner(file);
			String data;
			reader.useDelimiter("\\Z");
			String fileName = setFileName(file);
			FileWriter writer = new FileWriter(fileName, false);
			writer.append("");
			writer = new FileWriter(fileName, true);
			data = reader.next();
			writeToFile(file, data, writer);

		}

		private void writeToFile(File file, String data, FileWriter writer) {
			root = HuffmanTree.createTree(data);
			HuffmanTree.createCode(root, "");
			String encodedStr = HuffmanTree.encode(root, data);
			try {
				writer.write(encodedStr+"\n");
	        	writer.close();
		    } catch (IOException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }
			System.out.println("Done!");
		}

		private String setFileName(File file) {
			String str1 = file.getPath();
			int loc = str1.lastIndexOf('\\');
			str1 = str1.substring(0,loc+1);
			String str2 = file.getName();
			int loc2 = str2.lastIndexOf('.');
			str2 = str2.substring(0,loc2+1);
			String str = str1 + "huffmanCompression_" + str2 + "txt";
			return str;
		}
		
		private void setExtention(File file) {
			String fileExtention = file.getName();
			int loc = fileExtention.lastIndexOf('.');
			fileExtention = fileExtention.substring(loc + 1);
			extention = fileExtention;
		}
		
		private String imageToText(File file) throws IOException {
			String str = "";
			str = Arrays.toString(bytes);
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
               currentFrame.getContentPane().add(decompressionLabel);
               try {
            	   if(!extention.equals("txt")) {
            		   decompressOtherExtentions(file);
            	   } else {
            		   decompress(file);
            	   }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
               downloadFile.setText("Folder Selected: " + file.getName());
            }else{
            	downloadFile.setText("Open command canceled");
            }
            decompressionLabel.setText("All Done!");
            repaint();
         }

		private void decompressOtherExtentions(File file) throws IOException {
			Scanner reader = new Scanner(file);
			String data;
			reader.useDelimiter("\\Z");
			String str = setFileName(file);
			FileWriter writer = new FileWriter(str, true);
			data = reader.next();
			String decodedStr = HuffmanTree.decode(root, data);
			System.out.println("We are here!");
			String[] input = decodedStr.replaceAll("[\\[\\]]", "").split(", ");
			System.out.println("Boom Now We are here!");
			byte[] output = new byte[decodedStr.length()];
			
			for (int i = 0; i < input.length; i++) {
		        output[i] = Byte.parseByte(input[i]);
		        if(i % 5000 == 0)
		        System.out.println("Total length is: " + input.length + " we are at: " + i);
		    }

		    if(picExtentions.contains(extention)) {
		    	ByteArrayInputStream bis = new ByteArrayInputStream(output);
			    BufferedImage image = ImageIO.read(bis);
			    String currentPath = setFileName(file);
			    ImageIO.write(image, extention,  new File(currentPath) );
			    System.out.println("Done!");
		    }
			
		}

		private void decompress(File file) throws IOException {
			Scanner reader = new Scanner(file);
			String data;
			reader.useDelimiter("\\Z");
			String str = setFileName(file);
			FileWriter writer = new FileWriter(str, true);
			data = reader.next();
			writeToFile(file, data, writer);
		}

		private void writeToFile(File file, String data, FileWriter writer) {
			String decodedStr = HuffmanTree.decode(root, data);
			try {
				writer.write(decodedStr+ "\n");
	        	writer.close();
		    } catch (IOException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }
		}

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