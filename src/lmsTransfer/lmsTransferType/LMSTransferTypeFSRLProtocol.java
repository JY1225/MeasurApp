package lmsTransfer.lmsTransferType;

import http.WebService.XMLParse;

import java.util.HashMap;

import layer.dataLayer.RadarCalibration;
import CarAppAlgorithm.LongLineAlgorithm;
import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public abstract class LMSTransferTypeFSRLProtocol extends LMSTransferType{
	
	private final static String DEBUG_TAG="LMSTransferTypeFSRLProtocol";
	
	public abstract void RESULT_PHYSIC_SEND(String str);		

	public static String resultParse(String content,String searchProtocol)
	{
		return null;
	}
    
	public void physic_receive(String str)
	{
		if(str.contains("<AskCommand>")&&str.contains("</AskCommand>"))
		{			
			LMSLog.d(DEBUG_TAG,"AskCommand-----------------");
			
			if(str.equals("<AskCommand>ReadTestResult</AskCommand>"))
			{
				RESULT_PHYSIC_SEND(
					"<TestResult>"
						+"<Long>"+LMSConstValue.last_length+"</Long>"
						+"<Width>"+LMSConstValue.last_width+"</Width>"
						+"<Height>"+LMSConstValue.last_height+"</Height>"
						+"<Guache_Long>"+LMSConstValue.last_guache_length+"</Guache_Long>"
						+"<Guache_Width>"+LMSConstValue.last_guache_width+"</Guache_Width>"
						+"<Guache_Height>"+LMSConstValue.last_guache_height+"</Guache_Height>"
						+"<TestTime>"+LMSConstValue.last_carOutTime+"</TestTime>"
						+"<TestInterval>"+LMSConstValue.last_carTime+"</TestInterval>"
					+"</TestResult>");
				
				return;
			}
			else if(str.equals("<AskCommand>ReadLengthDistance</AskCommand>"))
			{
				RESULT_PHYSIC_SEND("<ReadLengthDistance>"+LongLineAlgorithm.fsrl_length_moving+"</ReadLengthDistance>");
			}
			else if(str.equals("<AskCommand>ReadSpeed</AskCommand>"))
			{
				RESULT_PHYSIC_SEND("<Speed>"+LongLineAlgorithm.fsrl_speed+"</Speed>");
			}
			else if(str.equals("<AskCommand>ReadSystemStatus</AskCommand>"))
			{
				int fsrl_system_status;
				if(LMSConstValue.bNormalMode == true)
					fsrl_system_status = 1;
				else
					fsrl_system_status = 0;
				RESULT_PHYSIC_SEND("<SystemStatus>"+fsrl_system_status+"</SystemStatus>");
			}
			else if(str.equals("<AskCommand><SetSystemMode>0</SetSystemMode></AskCommand>"))
			{
				LMSConstValue.bNormalMode = false;
				
				RESULT_PHYSIC_SEND("<SystemMode>0</SystemMode>");
			}
			else if(str.equals("<AskCommand><SetSystemMode>1</SetSystemMode></AskCommand>"))
			{
				LMSConstValue.bNormalMode = true;
				
				RESULT_PHYSIC_SEND("<SystemMode>1</SystemMode>");
			}
			else if(str.contains("<SetSensorPort>"))
			{
				int beginIndex,endIndex;
				
				beginIndex = str.indexOf("<SetSensorPort>")+"<SetSensorPort>".length();
				endIndex = str.indexOf("</SetSensorPort>");
				int sensorID = Integer.valueOf(str.substring(beginIndex,endIndex));
				
				beginIndex = str.indexOf("<Comport>")+"<Comport>".length();
				endIndex = str.indexOf("</Comport>");
				LMSConstValue.SENSOR_IP[sensorID] = "COM"+str.substring(beginIndex,endIndex);
	
				//=================================================================================
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramSensorIP);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.SENSOR_IP[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	
				//=================================================================================
				RESULT_PHYSIC_SEND("<SetSensorPort>"+sensorID+"</SetSensorPort>");
			}
			else if(str.contains("<SetVehicleType>"))
			{
				String result = new XMLParse().getXMLStrValue(str,"SetVehicleType");

				CarDetectSetting.CarTypeChangeTrig(result);
	
				//=================================================================================
				RESULT_PHYSIC_SEND("<SetSensorPort>ok</SetSensorPort>");
			}
		}
		
		RadarCalibration.biaodingParse(str);
	}	
	
	@Override
	public void eventToCommand(String event,HashMap eventExtra) 
	{			
		if (event.equals(LMSConstValue.SOCKET_SEND_MSG_INTENT))
		{
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SOCKET_MSG))
			{
				String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SOCKET_MSG);
				
				RESULT_PHYSIC_SEND(str);
			}
		}
	}
}
