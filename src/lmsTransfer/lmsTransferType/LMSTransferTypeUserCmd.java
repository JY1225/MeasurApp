package lmsTransfer.lmsTransferType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsBase.LMSProductInfo;

public abstract class LMSTransferTypeUserCmd extends LMSTransferType{
	private final static String DEBUG_TAG="LMSTransferTypeUserCmd";

	public abstract void PHYSIC_SEND(String str);

	@Override
	public void eventToCommand(String event,HashMap eventExtra) 
	{
		int sensorID = 0;

		if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 

		if(event.equals(LMSConstValue.SETTING_TRANSFER_INTENT))
		{			
			String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM);

//			PHYSIC_SEND(LMSConstValue.SETTING_TRANSFER_COMMAND+'|'+nvram+'|'+sensorID+'|'+eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE));
		}
	}
		

	public static String resultParse(String content,String searchProtocol)
	{
		return null;
	}
	
	public void physic_receive_server(String str)
	{
		LMSLog.d(DEBUG_TAG,"physic_receive str="+str);
		
		String result = null;
		if(str.startsWith("\02"))
		{
			if(str.endsWith("\03"))
				result = str.substring(1,str.length()-1);
			else if(str.endsWith("\03\n"))
				result = str.substring(1,str.length()-2);
		}
		else
		{
			result = str;
		}

		String outStr = LMSTrasferCarTypeParse.carTypeParse(result);
		
		if(result.contains("GET_CAR_TYPE"))
		{
			PHYSIC_SEND(outStr);
		}
	}	
}
