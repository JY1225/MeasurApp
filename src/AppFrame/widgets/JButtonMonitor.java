package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import AppBase.SensorImageDraw.DetectorImageFrame;
import AppBase.SensorImageDraw.RadarImageFrame;
import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class JButtonMonitor extends JButton{
	private String DEBUG_TAG="JButtonMonitor";

	int _sensorID;

	public JButtonMonitor(JPanel panel, 
		int gridX, int gridY,
		final int sensorID)
	{
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		
		setText("打开监控");
		
		//=======================================================================
		GridBagConstraints gbc_buttonMonitor = new GridBagConstraints();
		gbc_buttonMonitor.insets = new Insets(0, 0, 5, 5);
		gbc_buttonMonitor.gridx = gridX;
		gbc_buttonMonitor.gridy = gridY;
		panel.add(this,gbc_buttonMonitor);
		
		//=======================================================================
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(_sensorID >= 0)
				{
					RadarImageFrame.showFrame(sensorID);
				}
				else
				{					
					DetectorImageFrame.showFrame();
				}
			}
		});
		
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
				if(_sensorID >=0 && sensorID != _sensorID)
				{
					return;
				}
			}

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    	
	        	String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

	        	if(_sensorID >=0 && nvram.equals(LMSConstValue.nvramRadarMonitor)) 
		        {
		    		if(LMSConstValue.bRadarMonitor[sensorID] == false)
		    			setText("打开监控");	
		    		else
		    			setText("关闭监控");	
		        }
		        else if(_sensorID == -1 && nvram.equals(LMSConstValue.nvramDetectorMonitor)) 
		        {
		    		if(LMSConstValue.bDetectorMonitor == false)
		    			setText("打开监控");	
		    		else
		    			setText("关闭监控");	
		        }
	        }
		}
	}
}
