package Core;

import java.awt.Image;

public interface DataOperator {

    public String[] getRecordFromFile(String pth);
    public Image getImageFromFile(String pth, int width, int height);
    public String[] getRecord(String k);
    public Image getImage(String k);
    public void addImage(String k, Image image);
    public void addRecordData(String k, String[] recordData);
   
	
	
}
