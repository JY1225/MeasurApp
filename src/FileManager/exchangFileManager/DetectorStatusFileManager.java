package FileManager.exchangFileManager;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import CarDetect.CarDetectSetting;
import FileManager.FileManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class DetectorStatusFileManager {
	private String DEBUG_TAG="DetectorStatusFileManager";

	File pFile;
	int sensorID;
	
	public DetectorStatusFileManager(int _sensorID) 
	{
		sensorID = _sensorID;
		
		pFile = FileManager.checkFile("cartype","status"+sensorID+".txt");
		
		//==================================================================
		try {
			FileWriter fileWriter = new FileWriter(pFile);
			
    		fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(sensorID, e);
		}

		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int _sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				_sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
				
				if(_sensorID != sensorID)
				{
					return;
				}
			}
			
	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    	
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

				try {
					if(nvram.equals(LMSConstValue.nvramSensorPortConnected)
						||nvram.equals(LMSConstValue.nvramPortHasData)
						||nvram.equals(LMSConstValue.nvramPortHasValidData)
					)
					{
						FileWriter fileWriter = new FileWriter(pFile,false);

						String str = "";
						if(nvram.equals(LMSConstValue.nvramSensorPortConnected)) 
				        {	   							
							if(LMSConstValue.bSensorPortConnected[sensorID] == true)
							{
								str = "1";
							}
							else
							{
								str = "0";
							}							
				        }  
						else if(nvram.equals(LMSConstValue.nvramPortHasData))
						{
							if(LMSConstValue.bPortHasData[sensorID] == true)
							{
								str = "1";
							}
							else
							{
								str = "0";
							}	
						}
						else if(nvram.equals(LMSConstValue.nvramPortHasValidData))
						{
							if(LMSConstValue.bPortHasValidData[sensorID] == true)
							{
								str = "1";
							}
							else
							{
								str = "0";
							}	
						}
						
						fileWriter.write(str);

			    		fileWriter.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LMSLog.exception(sensorID, e);
				}

	        }
		}
	}
}
