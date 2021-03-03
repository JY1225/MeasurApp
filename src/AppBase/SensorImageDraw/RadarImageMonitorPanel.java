package AppBase.SensorImageDraw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;


import layer.algorithmLayer.ParseLMSAckCommand;
import layer.drawLayer.RadarMonitorDrawRunnable;

import SensorBase.LMSConstValue;

public class RadarImageMonitorPanel extends JPanel{
	private String DEBUG_TAG="RadarImageMonitorPanel";
	
	int _sensorID;

	RadarMonitorDrawRunnablePC radarMonitorDrawRunnablePC;
	
	public RadarImageMonitorPanel(int sensorID)
	{
		_sensorID = sensorID;
	
		DEBUG_TAG+=_sensorID;
		
		//==============================================================================
		radarMonitorDrawRunnablePC = new RadarMonitorDrawRunnablePC(sensorID);
		new Thread(radarMonitorDrawRunnablePC.thread()).start();   
	}

	RadarDrawView radarDrawView = new RadarDrawView();
    public void paintComponent(Graphics g)
    {
    	int width,height;

    	super.paintComponent(g); //«Â∆¡
    	    	
    	width = getWidth();
    	height = getHeight();

		if(LMSConstValue.bLRTurn[_sensorID] == true)
		{
			AffineTransform transform = new AffineTransform();
			transform.scale(-1, 1);
			transform.translate(-2 * width / 2,
				0 * height / 2);		

			((Graphics2D)g).transform(transform);	
		}

		((Graphics2D)g).setClip(0, 0, width, height);
		
		int leftWindow = 0,rightWindow = 0,heightWindow = 0;
		if(LMSConstValue.fixMethod[_sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
		{
			leftWindow = LMSConstValue.iLeftWindow[_sensorID];
			rightWindow = LMSConstValue.iRightWindow[_sensorID];
			heightWindow = 0;
		}
		else if(LMSConstValue.fixMethod[_sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			leftWindow = 0;
			rightWindow = LMSConstValue.iRightWindow[_sensorID];
			heightWindow = LMSConstValue.iHeightWindow[_sensorID];
		}
		else if(LMSConstValue.fixMethod[_sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			leftWindow = LMSConstValue.iLeftWindow[_sensorID];
			rightWindow = 0;
			heightWindow = LMSConstValue.iHeightWindow[_sensorID];
		}
		
		int iWhPosition = 0;
		if(LMSConstValue.bWHPositionLess90 == false)
		{
			iWhPosition =  LMSConstValue.iNvramLWDistance.iValue;
		}
		else
		{
			iWhPosition =  -LMSConstValue.iNvramLWDistance.iValue;
		}
		
		radarDrawView.initGraphics2D((Graphics2D)g);
		radarDrawView.lmsHWMeasureDataMonitor(
        	_sensorID,
        	width, height,
        	leftWindow,rightWindow,heightWindow,
        	iWhPosition,
        	LMSConstValue.iFilterStartAngle[_sensorID],LMSConstValue.iFilterEndAngle[_sensorID],
        	LMSConstValue.iGroundStartAngle[_sensorID],LMSConstValue.iGroundEndAngle[_sensorID],
        	LMSConstValue.iFrontEdgeWindow[_sensorID],height*LMSConstValue.iYMove[_sensorID]/LMSConstValue.MOVE_STEP,
        	LMSConstValue.iLREdgeWindow[_sensorID],width*LMSConstValue.iXMove[_sensorID]/LMSConstValue.MOVE_STEP,
        	ParseLMSAckCommand.measureData16bit_amount[_sensorID],
        	radarMonitorDrawRunnablePC.dataParseLineDrawImage.distanceX,radarMonitorDrawRunnablePC.dataParseLineDrawImage.distanceY);
    }
    
	class RadarMonitorDrawRunnablePC extends RadarMonitorDrawRunnable
	{
		public RadarMonitorDrawRunnablePC(int sensorID) {
			super(sensorID);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void radarMonitorDrawRunnableParse() {
			repaint();
		}
	}
}
