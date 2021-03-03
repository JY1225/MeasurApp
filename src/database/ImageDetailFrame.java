package database;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ImageDetailFrame extends JFrame{
	private static int IMAGE_DETAIL_FRAME_NUM = 5;
	private static ImageDetailFrame imageDetailFrame[] = new ImageDetailFrame[IMAGE_DETAIL_FRAME_NUM];
	
	private JLabel imageLabel;
	
	private ImageDetailFrame() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);

		//===============================================================
		imageLabel = new JLabel();
		GridBagConstraints gbc_imageLabel = new GridBagConstraints();
		gbc_imageLabel.fill = GridBagConstraints.BOTH;
		gbc_imageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_imageLabel.gridx = 0;
		gbc_imageLabel.gridy = 0;
		getContentPane().add(imageLabel,gbc_imageLabel);		
	}

	private void setImage(String fileName)
	{
		setTitle("Í¼Æ¬:"+fileName);

		//===============================================================
		ImageIcon iconImage = new ImageIcon(fileName);
		
		//===============================================================
		float labelWidth = 992;
		float labelHeight = 578;
		float iconWidth = iconImage.getIconWidth();
		float iconHeight = iconImage.getIconHeight();
		int width,height;
		    
	    if(labelWidth/labelHeight < iconWidth/iconHeight)
	    {
	    	width = (int) labelWidth;
	    	height = (int) (width*iconHeight/iconWidth);
	    }
	    else
	    {
	    	height = (int) labelHeight;
	    	width = (int) (height*iconWidth/iconHeight);
	    }
	    
	    iconImage.setImage(iconImage.getImage().getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING)); 
		imageLabel.setIcon(iconImage); 

		setBounds(100, 10, width, height);

		//===============================================================
		setVisible(true);
		show();
	}
	
	public static void showImage(String fileName)
	{
		boolean bShow = false;
		for(int i = 0;i<IMAGE_DETAIL_FRAME_NUM;i++)
		{
			if(imageDetailFrame[i] == null)
			{
				imageDetailFrame[i] = new ImageDetailFrame();
			}
			
			if(!imageDetailFrame[i].isVisible())
			{
				imageDetailFrame[i].setImage(fileName);
				
				bShow = true;
				break;
			}
		}
		if(bShow == false)
		{
			JOptionPane.showMessageDialog(null, "´ò¿ªÍ¼Æ¬Ô¤ÀÀÌ«¶à,ÇëÏÈ¹Ø±Õ", "´ò¿ªÍ¼Æ¬Ô¤ÀÀÌ«¶à,ÇëÏÈ¹Ø±Õ", JOptionPane.WARNING_MESSAGE); 
		}
	}
}
