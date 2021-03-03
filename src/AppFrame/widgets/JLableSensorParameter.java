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
		super("���ͷ����:");

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
    	if(str.contains("������")
    		||str.contains("���ڴ�ʧ��")
    		||str.contains("���ڱ�ռ��")
    		||str.contains("���ڳ�ʼ����")
    		||str.contains("��֧�ִ���ģʽ")
    		||str.contains("������Ч")
    		||str.contains("��ַ��Ч")
    	)
    	{
    		setForeground(Color.RED);
    	}
    	else
    	{
    		setForeground(Color.BLACK);	  
    	}
    	setText("���ͷ����:"+str);	
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
							str = "�˿�������";
			    	   		if(LMSConstValue.getSensorType(_sensorID).equals(LMSConstValue.SensorType.RADAR_B))
			    	   		{
			    	   			str += ",�Ƿ���˰�װָʾ��,����ˮ��ͷ?";
			    	   		}
						}
						else if(sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START)
						{
							str = "������������";
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
							str = "�˿�������Ч,Э���������";
						}
						else if(sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START)
						{
							str = "������������";
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
