package AppFrame.logisticsMachine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

import layer.algorithmLayer.ThreeDPoint;
import SensorBase.LMSConstValue;
public class LogisticsMachineConveyorDrawPanel extends JPanel{
	private final static String DEBUG_TAG="LogisticsMachineConveyorDrawPanel";

	public Graphics2D g2;
	
	final float CONVEYOR_LENGTH = 8000;
	final float CONVEYOR_WIDTH = 800;	
	int conveyorOffset = 0;
	public final static int CONVEYOR_TIMER  = 100; 
	
	public LogisticsMachineConveyorDrawPanel()
	{
		JPanel panel = new JPanel();
		
		//==========================================================
		int gridX = 0,gridY = 0;
		
		JPanel drawPanel = new JPanel();
		GridBagConstraints gbc_drawPanel = new GridBagConstraints();
		gbc_drawPanel.fill = GridBagConstraints.BOTH;
		gbc_drawPanel.gridwidth = 1;
		gbc_drawPanel.insets = new Insets(0, 0, 5, 5);
		gbc_drawPanel.gridx = gridX;
		gbc_drawPanel.gridy = gridY;
		panel.add(drawPanel,gbc_drawPanel);
		
		conveyorDrawStart();	
	}
	
    private static Timer timer = new Timer();
    private TimerTask timerTask;
	public class TimeOutTask extends TimerTask{ 
    	TimeOutTask()
        {

        }
        
        // TimerTask 是个抽象类,实现的是Runable类   
        @Override  
        public void run() {
			repaint();
        }
    };
    
    void conveyorDrawStart()
    {
    	if(timer != null)
    	{	
	    	if(timerTask != null)
	    	{	
		    	timerTask.cancel();
	    	}
	    	timerTask = new TimeOutTask();
            timer.schedule(timerTask, LogisticsMachineConveyorDrawPanel.CONVEYOR_TIMER,LogisticsMachineConveyorDrawPanel.CONVEYOR_TIMER);   	    	
    	}
    }
    
	public static ArrayList<LogisticsMachineConveyerObj> conveyerObjList = new ArrayList<LogisticsMachineConveyerObj>(); 

	int DRAW_WIDTH;
	int DRAW_LENGTH;
	int realLength2Draw(float x)
	{
		return (int) (DRAW_LENGTH*x/CONVEYOR_LENGTH);
	}

	int realWidth2Draw(float y)
	{
		return (int) (DRAW_WIDTH*y/CONVEYOR_WIDTH);
	}

	private long lLastTime;
    public void paintComponent(Graphics g)
    {
    	int height;
    	
    	super.paintComponent(g); //清屏
    	
		g2 = (Graphics2D)g;
		
		//===============================================
		DRAW_LENGTH = getWidth();
    	height = getHeight();
    	
		g2.setColor(Color.GRAY);
		DRAW_WIDTH = (int) ((CONVEYOR_WIDTH*DRAW_LENGTH)/CONVEYOR_LENGTH);
		int drawWidthStart = 0;
		if(height>DRAW_WIDTH)
		{
			drawWidthStart = (height-DRAW_WIDTH)/2;
		}
		else
		{
			drawWidthStart = 0;
		}
//    	LMSLog.d(DEBUG_TAG,"paintComponent w="+width+" h="+height+" drawHeight="+drawHeight+" drawHeightStart="+drawHeightStart);

        Date date = new Date();//获得系统时间.               
		long lCurrentTime = date.getTime();	

		//画输送带
		g2.fillRect(0, drawWidthStart, DRAW_LENGTH, DRAW_WIDTH);
		
		//画输送带坐标
		g2.setColor(Color.YELLOW);			
		if(lLastTime == 0)
		{
			conveyorOffset = 0;
		}
		else
		{
			conveyorOffset += (LMSConstValue.iConVeyerSpeed*(lCurrentTime - lLastTime)/1000);
		}
		
		if(conveyorOffset > 1000)
		{
			conveyorOffset -= 1000;
		}
		for(int i=0;i<CONVEYOR_LENGTH/1000;i++)
		{
			int offset = (i*1000+conveyorOffset);
			int drawOffset = realLength2Draw(offset);
			g2.drawLine(drawOffset, drawWidthStart, drawOffset, drawWidthStart+DRAW_WIDTH);
		}
		
		//画物体
		g2.setColor(Color.RED);			

        for (Iterator it = conveyerObjList.iterator(); it.hasNext();) 
        { 
        	LogisticsMachineConveyerObj conveyerObj = (LogisticsMachineConveyerObj) it.next();
        	
        	float CONVEYOR_VISUAL_TIME  = CONVEYOR_LENGTH*1000/LMSConstValue.iConVeyerSpeed; 
        	
     		if(lCurrentTime - conveyerObj.lReceiveTime > CONVEYOR_VISUAL_TIME)
			{
     			it.remove();
			}
     		else
     		{
     			float objOffset = (lCurrentTime - conveyerObj.lReceiveTime)*LMSConstValue.iConVeyerSpeed/1000;
     			
    			int drawLengthOffset = realLength2Draw(objOffset);
    			int drawLength = realLength2Draw(conveyerObj.length);
    			
    			int drawWidthOffset = drawWidthStart+DRAW_WIDTH/2 //居中传送带
   					+ realWidth2Draw(conveyerObj.rotateCenterX); //加偏移
    			int drawWidth = realWidth2Draw(conveyerObj.width);

//    			LMSLog.d(DEBUG_TAG, "drawObjWidthStart============="+drawObjWidthStart+" X="+conveyerObj.rotateCenterX);
    			
    			AffineTransform transform = new AffineTransform();
    			transform.rotate(Math.toRadians(conveyerObj.rotateAngle),drawLengthOffset,drawWidthOffset); //旋转
    			Rectangle2D rectangle2D = new Rectangle2D.Double(drawLengthOffset-drawLength, drawWidthOffset, drawLength, drawWidth);
    			g2.fill(transform.createTransformedShape(rectangle2D));
     		}
    	} 
        
        lLastTime = lCurrentTime;

//		g2.drawString("0°", 15, 12);
    }
}
