package SensorBase;

import java.awt.Image;

import javax.swing.ImageIcon;

public class LMSFunction {
	public ImageIcon getScaledIconImage(String fileName,int labelWidth,int labelHeight)
	{
	    ImageIcon iconImage = new ImageIcon(fileName);
	    
	    float iconWidth = iconImage.getIconWidth();
	    float iconHeight = iconImage.getIconHeight();
	    int width,height;
	    int width1,height1;
	    int width2,height2;

	    width1 = labelWidth;
	    height1 = (int) (width1*iconHeight/iconWidth);
	    
	    height2 =  labelHeight;
    	width2 = (int) (height2*iconWidth/iconHeight);

	    if(width1 <= labelWidth && height1 <= labelHeight)
	    {
	    	width = width1;
	    	height = height1;
	    }
	    else
	    {
	    	width = width2;
	    	height = height2;
	    }
	    	
	    iconImage.setImage(iconImage.getImage().getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING)); 

	    return iconImage;
	}
	
}
