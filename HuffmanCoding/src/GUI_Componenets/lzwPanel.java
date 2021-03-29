package GUI_Componenets;

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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Compressions.LZWCompression;

public class lzwPanel extends JPanel{
	
	ArrayList<String> picExtentions = new ArrayList<>(Arrays.asList("png", "jpg", "bmp", "gif", "jpeg"));
	
	JFrame currentFrame;
	
	JLabel lzwTitle;
	
	JButton uploadFile;
	JButton downloadFile;
	JButton backButton;
	
	String extention;
	String abdul;
	
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
               setExtention(file);
               try {
            	   if(extention.equals("txt")) {
            		   System.out.println("here-1");
            		   compressFile(file);
            	   }            		   
            	   else {
            		   System.out.println("here-1.5");
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
		
		private void compressOtherExtention(File file) throws IOException {
			if(picExtentions.contains(extention)) {
				byte[] data2 = new byte[(int) file.length()];
			    FileInputStream in = new FileInputStream(file);
			    in.read(data2);
			    in.close();
				for(int i= 0; i< data2.length;i++){
				        System.out.print("original = " + data2[i]);
				}
				bytes = data2;
				String str = imageToText(file);
				LZWCompression lzw = new LZWCompression();
				String fileName = setPathExtention(file);
				FileWriter writer = new FileWriter(fileName, true);
				writer.append("");
				//writer = new FileWriter(str, true);
				compressed = lzw.compress(str);
				writeToFile(compressed, file, writer);
			}
			
		}

		private String imageToText(File file) throws IOException {
			String str = "";
			BufferedImage image = ImageIO.read(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			if(extention.equals("png"))
				ImageIO.write(image, "png", bos);
			if(extention.equals("jpg"))
				ImageIO.write(image, "jpg", bos);
			if(extention.equals("gif"))
				ImageIO.write(image, "gif", bos);
			//bytes = bos.toByteArray();
		    //str = new String(bytes);
			str = Arrays.toString(bytes);
			abdul = str;
		    //System.out.println(str);
			return str;
		}
		
		private String getFileName(File file) {
			String name = file.getName();
			int loc = name.lastIndexOf('.');
			name = name.substring(0, loc);
			return name;
		}

		private void setExtention(File file) {
			String fileExtention = file.getName();
			int loc = fileExtention.lastIndexOf('.');
			fileExtention = fileExtention.substring(loc + 1);
			extention = fileExtention;
		}

		private String setPathExtention(File file) {
			String str = getFileName(file);
			String path = file.getPath();
			int loc = path.lastIndexOf('\\');
			path=path.substring(0,loc+1);
			String str2 = path+"lzwCompression_" + str + ".txt";
			return str2;
		}

		private void compressFile(File file) throws IOException {
			LZWCompression lzw = new LZWCompression();
			Scanner reader = new Scanner(file);
			reader.useDelimiter("\\Z");
			String str = setPathExtention(file);
			FileWriter writer = new FileWriter(str, true);
			writer.append("");
			//writer = new FileWriter(str, true);
			System.out.println("here0");
			String data;
			data = reader.next();
			compressed = lzw.compress(data);
			writeToFile(compressed, file, writer);
		}

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
		    	byteToImage(image, currentPath);
		    }
		    
		}

		private void byteToImage(BufferedImage image, String currentPath) throws IOException {
			ImageIO.write(image, extention,  new File(currentPath) );
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
			try {
				writer.write(data.toString());
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
