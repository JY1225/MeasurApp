package AppFrame.widgets;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JLabel;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSConstValue.enumDetectType;

public class JLabelSystemStatus extends JLabel{
	private String DEBUG_TAG="JLabelSystemStatus";

	private int _sensorID;

	public JLabelSystemStatus(int sensorID)
	{
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
				
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==================================================================
		setText(_sensorID);
	}
	
	public void setText(int sensorID) 
	{
		if(LMSConstValue.SystemStateString[sensorID] != null)
		{
	    	if(LMSConstValue.SystemStateString[sensorID].contains("未授权设备，请联系供应商"))
	    	{
	    		setForeground(Color.RED);
	    		setText(LMSConstValue.SystemStateString[sensorID]);			    		
	    	}
	    	else
	    	{
				if(LMSConstValue.defaultDetectType == enumDetectType.UNKNOW_DETECT_TYPE)
		    	{
		    		setForeground(Color.RED);	        		
		    		setText("未知检测类型,请先连接传感器,连接成功后自己决定类型;成功连接后,需退出检测程序,重新进入方有效");			    		
		    	}
		    	else
		    	{
		        	if(LMSConstValue.SystemStateString[sensorID].contains("检测类型错误,请重启系统")
			        	||LMSConstValue.SystemStateString[sensorID].contains("正在取基准值")
			        	||LMSConstValue.SystemStateString[sensorID].contains("基准值设置失败")
			        	||LMSConstValue.SystemStateString[sensorID].contains("请重取基准值")
			        	||LMSConstValue.SystemStateString[sensorID].contains("地面不平，基准值建立失败")
			        	||LMSConstValue.SystemStateString[sensorID].contains(LMSConstValue.strPauseDetect)
		        	)
		        	{
		        		setForeground(Color.RED);
		        	}
		        	else
		        	{
		        		setForeground(Color.BLACK);	        		
		        	}
		        	
		    		setText(LMSConstValue.SystemStateString[sensorID]);			    		
		    	}
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

			if (eventType != null && 
		        (eventType.equals(LMSConstValue.SERVER_SYSTEM_STATE_STRING_INTENT))) 
	        {	   
//				LMSLog.d(DEBUG_TAG, "SERVER_SYSTEM_STATE_STRING_INTENT["+sensorID+"]="+LMSConstValue.SystemStateString[sensorID]);

				if(sensorID<LMSConstValue.RADAR_SENSOR_NUM)
				{
					setText(sensorID);			    		
				}
	        }  
	        
	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    	
	        	String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

	        	if(nvram.equals(LMSConstValue.nvramFixLed)) 
		        {
	        		
		        }
	        }
        }
	}
}
