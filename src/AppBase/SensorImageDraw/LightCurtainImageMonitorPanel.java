package AppBase.SensorImageDraw;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import SensorBase.LMSLog;

import layer.drawLayer.LightCurtainMonitorDrawRunnable;

public class LightCurtainImageMonitorPanel extends JPanel{
	String DEBUG_TAG = "LightCurtainImageMonitorPanel";

	LightCurtainMonitorDrawRunnablePC lightCurtainMonitorDrawRunnablePC;
	
	LightCurtainImageMonitorPanel()
	{
		//==============================================================================
		lightCurtainMonitorDrawRunnablePC = new LightCurtainMonitorDrawRunnablePC();
		new Thread(lightCurtainMonitorDrawRunnablePC.thread()).start();   
	}
	
	LightCurtainDrawView lightCurtainDrawView = new LightCurtainDrawView();
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g); //����

//    	Graphics2D g2 = (Graphics2D) g;		
    	
    	lightCurtainDrawView.initGraphics2D((Graphics2D)g);

    	lightCurtainDrawView.lightCurtainDrawPoint(lightCurtainMonitorDrawRunnablePC.dataLayerLightCurtainParseLine.bLightCurtainLightStatus);
    }

    class LightCurtainMonitorDrawRunnablePC extends LightCurtainMonitorDrawRunnable
	{
		public LightCurtainMonitorDrawRunnablePC() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void lightCurtainMonitorDrawRunnableParse() {
//          	LMSLog.d(DEBUG_TAG,"lightCurtainMonitorDrawRunnableParse");

			// TODO Auto-generated method stub
			repaint();			
		}
	}
}
