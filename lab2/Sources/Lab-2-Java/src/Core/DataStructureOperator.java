package Core;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.WeakHashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class DataStructureOperator implements DataOperator {

	
	WeakHashMap<String, String[]> recordData = new WeakHashMap<>();
	WeakHashMap<String, Image> images = new WeakHashMap<>();
	
	
	
	
	@Override
	public String[] getRecordFromFile(String pth) {
	       
	        String A = pth + "\\" + "record.txt";
	        String[] tok = null;

	        try {
	            BufferedReader br = new BufferedReader(new FileReader(A));
	            String fileRead = br.readLine();
	            while (fileRead != null) {
	                
	                String[] tokenize = fileRead.split(",");
	                tok=tokenize;

	               

	                fileRead = br.readLine();
	            }

	            br.close();
	        } catch (FileNotFoundException fnfe) {
	            System.out.println("file not found");
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }


	        return tok;


	}

	@Override
	public Image getImageFromFile(String pth, int width, int height) {
		Image img = null;
		String A = pth + "\\" + "image.png";
	    try {
			img = ImageIO.read(new File(A)).getScaledInstance(width, height, Image.SCALE_DEFAULT);;
		} catch (Exception e1) {
			
		}
		return img;
	}

	@Override
	public String[] getRecord(String k) {
		if(recordData.containsKey(k)) {
			
			return recordData.get(k);
		}else {
			
			return null;
		}
	}

	
	
	@Override
	public Image getImage(String k) {
		if(images.containsKey(k)) {
			return images.get(k);
		}else {
			return null;
		}
	}

	@Override
	public void addImage(String k, Image image) {
		images.put(k, image);
	}

	@Override
	public void addRecordData(String k, String[] fileData) {
		recordData.put(k, fileData);
	}
	
	
	






}
