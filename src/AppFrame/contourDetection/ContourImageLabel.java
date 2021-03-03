package AppFrame.contourDetection;

import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import database.ImageDetailFrame;

public class ContourImageLabel{
	String _labelStr;
	String _fileName;
	
	JLabel imageLabel;
	JLabel titleLabel;
	public ImageIcon iconImage;
	
	public ContourImageLabel(
		JPanel panel,
		int gridX,int gridY,
		String labelStr,
		String fileName,int imageGridWidth,int imageGridHeight)
	{
		_labelStr = labelStr;
		_fileName = fileName;

		//==================================================================
		if(_labelStr != null)
		{
			titleLabel = new JLabel(_labelStr);
			GridBagConstraints gbc_titleLabel = new GridBagConstraints();
			gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
			gbc_titleLabel.fill = GridBagConstraints.BOTH;
			gbc_titleLabel.gridx = gridX;
			gbc_titleLabel.gridy = gridY;
			panel.add(titleLabel, gbc_titleLabel);		
			gridX++;
		}
		
		imageLabel = new JLabel();
		GridBagConstraints gbc_imageLabel = new GridBagConstraints();
		gbc_imageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_imageLabel.fill = GridBagConstraints.BOTH;
		gbc_imageLabel.gridwidth = imageGridWidth;
		gbc_imageLabel.gridheight = imageGridHeight;
		gbc_imageLabel.gridx = gridX;
		gbc_imageLabel.gridy = gridY;
		panel.add(imageLabel, gbc_imageLabel);
		imageLabel.addMouseListener(new LabelMouseListener());	
		if(fileName != null)
		{
			imageLabel.setText("待显示图片文件名:"+fileName);
		}
	}

	public class LabelMouseListener implements MouseListener{
		public void mouseEntered(MouseEvent e) {
			/**鼠标移到组件上方法时事件处理方法.**/
		}

		public void mouseExited(MouseEvent e) {
		/**鼠标移开组件时事件处理方法.**/}

		public void mousePressed(MouseEvent e) {
		/**鼠标在组件上按下(但没弹起)时事件处理方法.**/}

		public void mouseReleased(MouseEvent e) {
		/**鼠标在组件上弹起事件处理方法.**/}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(_fileName != null)
			{
				ImageDetailFrame.showImage(_fileName);
			}
		}
	}
	
	public void setLabel(String str)
	{
		titleLabel.setText(str);
	}
	
	public void setScaledIconImage(String fileName)
	{
		if(fileName != null)
		{
			_fileName = fileName;
		}
		
		//==================================================
	    iconImage = new ImageIcon(_fileName);
	    
	    float labelWidth = imageLabel.getWidth();
	    float labelHeight = imageLabel.getHeight();
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
	    	
	    if(width != 0&&height != 0)
	    {
		    iconImage.setImage(iconImage.getImage().getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING)); 
		    imageLabel.setText("");
		    imageLabel.setIcon(iconImage); 
		    
		    imageLabel.repaint();
	    }
	}
}
