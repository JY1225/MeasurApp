package AppFrame.widgets;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JTextField;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class JTextFieldSensorPort extends JTextField{
	private String DEBUG_TAG="JTextFieldSensorPort";

	private int _sensorID;
	
	public JTextFieldSensorPort(int sensorID)
	{
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//=======================================================
		resetEditable();
		
		//=======================================================
		addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = getText().toString();
   				
				CarDetectSetting.setSensorPort(_sensorID,str);					
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_MINUS||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
			
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
				}  
			}  
		});
	}
	
	void resetEditable()
	{		
    	if(LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.UNKNOW)
    		||LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.LMS1XX)
    		||LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.LMS511)
    		||LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.ZM10)
    		||LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.PS_16I)
    		||LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.HK_DS)
    		||LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.DH_IPC)
    		)
    	{
    		setEditable(false);
    	}
    	else 
    	{
    		if(LMSConstValue.isValidIP(LMSConstValue.SENSOR_IP[_sensorID]))
    		{
    			setEditable(true);		        		
    		}
    		else
    		{
    			setEditable(false);		        		
    		}
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

	        	if(nvram.equals(LMSConstValue.nvramSensorIP)||nvram.equals(LMSConstValue.nvramSensorType)) 
		        {	    			
	        		resetEditable();
		        }
	        }
	    }
	}
}
