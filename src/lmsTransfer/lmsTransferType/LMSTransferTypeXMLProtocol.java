package lmsTransfer.lmsTransferType;

import http.WebService.XMLParse;

import java.util.HashMap;

import layer.dataLayer.RadarCalibration;
import CarAppAlgorithm.LongLineAlgorithm;
import CarDetect.CarDetectSetting;
import CustomerProtocol.CustomerProtocol_SocketServer_XML;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public abstract class LMSTransferTypeXMLProtocol extends LMSTransferType{
	private final static String DEBUG_TAG="LMSTransferTypeXMLProtocol";
	
	public abstract void RESULT_PHYSIC_SEND(String str);		

	public static String resultParse(String content,String searchProtocol)
	{
		return null;
	}
    
	public void physic_receive(String str)
	{
		new CustomerProtocol_SocketServer_XML().parseSingleCarInformation(str);
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
