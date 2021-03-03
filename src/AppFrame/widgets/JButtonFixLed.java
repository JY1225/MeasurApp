package AppFrame.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class JButtonFixLed extends JButton{
	private String DEBUG_TAG="JButtonFixLed";

	private int _sensorID;
	
	public JButtonFixLed(int sensorID)
	{
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==================================================================
		setText("安装指示灯(已关闭)");
		resetEnabled();
		
		//==================================================================
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDetectSetting.resetFixLed(_sensorID);
			}
		});
	}
	
	public void resetEnabled()
	{		
    	if(LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.RADAR_B)
    		||LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.RADAR_FS))
    	{
    		setEnabled(true);
    	}
    	else 
    	{
    		setEnabled(false);
    	}
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

	        	if(nvram.equals(LMSConstValue.nvramFixLed)) 
		        {
		    		if(LMSConstValue.bFixLed[sensorID] == false)
		    		{
		    			setText("安装指示灯(已关闭)");	
		    		}
		    		else
		    		{
		    			setText("安装指示灯(已打开)");	
		    		}
		        }
	        	else if(nvram.equals(LMSConstValue.nvramSensorType)) 
		        {
	        		resetEnabled();
		        }
	        }
	    }
	}
}
