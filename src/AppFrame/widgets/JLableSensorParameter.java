package AppFrame.widgets;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JLabel;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class JLableSensorParameter extends JLabel{
	private String DEBUG_TAG="JLableSensorParameter";

	private int _sensorID;
	
	public JLableSensorParameter(int sensorID)
	{
		super("检测头参数:");

		_sensorID = sensorID;
		
		DEBUG_TAG += _sensorID;		
				
		//==================================================================
		String str= CarDetectSetting.getSensorParameter(sensorID);
		_setText(str);

		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
		
	}

	void _setText(String str) 
	{    	
    	if(str.contains("无数据")
    		||str.contains("串口打开失败")
    		||str.contains("串口被占用")
    		||str.contains("串口初始化中")
    		||str.contains("不支持串口模式")
    		||str.contains("数据无效")
    		||str.contains("地址无效")
    	)
    	{
    		setForeground(Color.RED);
    	}
    	else
    	{
    		setForeground(Color.BLACK);	  
    	}
    	setText("检测头参数:"+str);	
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
				if(sensorID != _sensorID)
				{
					return;
				}
			}

	        if (eventType != null)
	        {
	        	if(eventType.equals(LMSConstValue.LMS_PARAMETER_STRING_INTENT)) 	        
		        {	    
		    		String str=event.getEventStringExtra(LMSConstValue.INTENT_EXTRA_LMS_PARAMETER);
		    		
		        	_setText(str);
		        }  
	        	else if(eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))
				{    		
					String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
					String value = String.valueOf(eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE)); 
		
					if(nvram.equals(LMSConstValue.nvramPortHasData))
					{
						String str = "";
						if(LMSConstValue.bPortHasData[sensorID] == false)
						{
							str = "端口无数据";
			    	   		if(LMSConstValue.getSensorType(_sensorID).equals(LMSConstValue.SensorType.RADAR_B))
			    	   		{
			    	   			str += ",是否打开了安装指示灯,或检查水晶头?";
			    	   		}
						}
						else if(sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START)
						{
							str = "轴距正常检测中";
						}
						else
						{
							str = CarDetectSetting.getSensorParameter(sensorID);
						}
						_setText(str);
					}
					else if(nvram.equals(LMSConstValue.nvramPortHasValidData))
					{
						String str = "";
						if(LMSConstValue.bPortHasValidData[sensorID] == false)
						{
							str = "端口数据无效,协议解析错误";
						}
						else if(sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START)
						{
							str = "轴距正常检测中";
						}
						else
						{
							str = CarDetectSetting.getSensorParameter(sensorID);
						}
						_setText(str);
					}
				}
	        }
		}
	}
}
