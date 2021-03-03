package AppFrame.contourDetection;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import SensorBase.LMSLog;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;

public class ContourDetectionTabPanelMainHNLG extends ContourDetectionTabPanelMain{
	private final static String DEBUG_TAG="ContourDetectionTabPanelMainHNLG";

	int PANEL_WIDTH;
	int PANEL_HEIGHT;

	int BG_IMAGE_WIDTH;
	int BG_IMAGE_HEIGHT;

	final int WIDTH_OFFSET = 10;
	final int VALUE_WIDTH = 335-2*WIDTH_OFFSET;
	final int VALUE_HEIGHT = 64;
	final int VALUE_X = 242+WIDTH_OFFSET;
	
	final int LENGTH_Y = 74;
	final int WIDTH_Y = 143;
	final int HEIGHT_Y = 213;
	final int ZHOUJU_Y = 282;
	
	public ContourDetectionTabPanelMainHNLG(JFrame frame,ContourDetectionDataBaseConst.MainPanelType type,ContourDetectionDataBaseConst.MainPanelCarType _carType)
	{
		super(frame,type,_carType);
	}
	
	public JPanel createPanelTab() {	
		BackgroundPanel panel = new BackgroundPanel();
		
		panel.setBounds(150, 50, 1100, 700);
//		/*
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1100};
		gridBagLayout.rowHeights = new int[]{700};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
//*/
	//				/*
		
		//==========================================================
		detectLengthLabel = new JLabel();
		detectLengthLabel.setFont(new Font("宋体",Font.BOLD, 70));
		panel.add(detectLengthLabel);
		
		detectWidthLabel = new JLabel();
		detectWidthLabel.setFont(new Font("宋体",Font.BOLD, 70));
		panel.add(detectWidthLabel);
		
		detectHeightLabel = new JLabel();
		detectHeightLabel.setFont(new Font("宋体",Font.BOLD, 70));
		panel.add(detectHeightLabel);
		
		detectZLabel[0] = new JLabel();
		detectZLabel[0].setFont(new Font("宋体",Font.BOLD, 70));
		panel.add(detectZLabel[0]);	 
//*/
	    LMSLog.d(DEBUG_TAG,"createPanelTab()");

		return panel;
	}
	
	public class BackgroundPanel extends JPanel {  	      	  	  
	    // 固定背景图片，允许这个JPanel可以在图片上添加其他组件  
	    protected void paintComponent(Graphics g) { 
			PANEL_WIDTH = getWidth();
			PANEL_HEIGHT = getHeight();

			//===================================================================
			ImageIcon bgImage = new ImageIcon(getClass().getResource("/HNLG.jpg"));  

		    BG_IMAGE_WIDTH = bgImage.getIconWidth();
		    BG_IMAGE_HEIGHT = bgImage.getIconHeight();	    
		    
		    LMSLog.d(DEBUG_TAG,"PANEL_WIDTH="+PANEL_WIDTH+" PANEL_HEIGHT="+PANEL_HEIGHT);
		    
	        g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this); 	 

	        //================================================================
	        int x = VALUE_X*PANEL_WIDTH/BG_IMAGE_WIDTH;
	        int width = VALUE_WIDTH*PANEL_WIDTH/BG_IMAGE_WIDTH;
	        int height = VALUE_HEIGHT*PANEL_HEIGHT/BG_IMAGE_HEIGHT;
	        
	        
			//setLayout(null);

	        ///*
	        detectLengthLabel.setBounds(
				x, 
				LENGTH_Y*PANEL_HEIGHT/BG_IMAGE_HEIGHT, 
				width, 
				height
			);

	        detectWidthLabel.setBounds(
				x, 
				WIDTH_Y*PANEL_HEIGHT/BG_IMAGE_HEIGHT, 
				width, 
				height
			);

	        detectHeightLabel.setBounds(
				x, 
				HEIGHT_Y*PANEL_HEIGHT/BG_IMAGE_HEIGHT, 
				width, 
				height
			);

			detectZLabel[0].setBounds(
				x, 
				ZHOUJU_Y*PANEL_HEIGHT/BG_IMAGE_HEIGHT, 
				width, 
				height
			);
			//*/
	    }  
	}  
	 
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
		
		}
	}

	@Override
	public JSplitPane createTab() {
		// TODO Auto-generated method stub
		return null;
	}
}
