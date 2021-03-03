package CustomerProtocol;

import java.util.HashMap;

import SensorBase.LMSConstValue;
import http.WebService.XMLParse;

public class CustomerProtocol_XML_MINE {
	public static boolean XML_parse(String resultSingleCar,DetectionVehicle detectionVehicle)
	{
		//=================================================================================
	//	detectionVehicle.bDetectStatus = new XMLParse().getXMLStrValue(resultSingleCar,"DetectStatus");	
	
		detectionVehicle.sDetectNumOfTime = new XMLParse().getXMLStrValue(resultSingleCar,"DetectNumOfTime");	
		//---------------------------------------------------------------------------
		detectionVehicle.sSerialNum = new XMLParse().getXMLStrValue(resultSingleCar,"SerialNum");	
		detectionVehicle.sVehicleNum = new XMLParse().getXMLStrValue(resultSingleCar,"VehicleNum");	
		detectionVehicle.sVehicleNumType = new XMLParse().getXMLStrValue(resultSingleCar,"VehicleNumType");	
		detectionVehicle.sVehicleType = new XMLParse().getXMLStrValue(resultSingleCar,"VehicleType");	
		detectionVehicle.sVehicleBrand = new XMLParse().getXMLStrValue(resultSingleCar,"VehicleBrand");	
		detectionVehicle.sVehicleID = new XMLParse().getXMLStrValue(resultSingleCar,"VehicleID");	
		detectionVehicle.sMotorID = new XMLParse().getXMLStrValue(resultSingleCar,"MotorID");	
		detectionVehicle.sNewOrOld = new XMLParse().getXMLStrValue(resultSingleCar,"NewOrOld");
	//	detectionVehicle.sMotorID = new XMLParse().getXMLStrValue(resultSingleCar,"车主");	
		detectionVehicle.sOperatorName = new XMLParse().getXMLStrValue(resultSingleCar,"OperatorName");	
		detectionVehicle.sOperatorID = new XMLParse().getXMLStrValue(resultSingleCar,"OperatorID");	
	
		//-----------------------------------------------------------------------------------------------
		detectionVehicle.sOriginalCarLength = new XMLParse().getXMLStrValue(resultSingleCar,"OriginalCarLength");					
		detectionVehicle.sOriginalCarWidth = new XMLParse().getXMLStrValue(resultSingleCar,"OriginalCarWidth");	
		detectionVehicle.sOriginalCarHeight = new XMLParse().getXMLStrValue(resultSingleCar,"OriginalCarHeight");	
		detectionVehicle.sOriginalLanBanHeight = new XMLParse().getXMLStrValue(resultSingleCar,"OriginalLanBanHeight");	
		detectionVehicle.sOriginalZJ = new XMLParse().getXMLStrValue(resultSingleCar,"OriginalZJ");	

		//-----------------------------------------------------------------------------------------------
		String strLedMessage = new XMLParse().getXMLStrValue(resultSingleCar,"LEDMessage");
		if(strLedMessage != null)
		{
			if(!LMSConstValue.getSensorType(LMSConstValue.LED_ID_START).equals(LMSConstValue.SensorType.UNKNOW)
				&&LMSConstValue.bNvramLedLocalSetting.bValue == false)
			{					
				HashMap<String, Comparable> eventExtraLED = new HashMap<String, Comparable>();
				eventExtraLED.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, strLedMessage);
				eventExtraLED.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtraLED);
			}
		}
		
		//-----------------------------------------------------------------------------------------------
		detectionVehicle.sCarTypeString = new XMLParse().getXMLStrValue(resultSingleCar,"DetectCarType");
		/*
		//XML旧协议
		detectionVehicle.bLanbanDetect = false;
		detectionVehicle.bFilterCheLan = false;
		for(int i=1; i<=10; i++)
		{
			String str = new XMLParse().getXMLStrValue(resultSingleCar,"DetectParam"+i);
			if(str != null)
			{
				if(str.equals("DetectLanBan"))
				{
					detectionVehicle.bLanbanDetect = true;							
				}
				else if(str.equals("DetectFilter_GuaChe_CheLan"))
				{
					detectionVehicle.bFilterCheLan = true;
				}
				else if(str.equals("DetectFilter_EndGas"))
				{
					detectionVehicle.bFilterEndGas = true;
				}
			}
		}	
		*/
		String str;
		
		detectionVehicle.bPauseDetect = false;
		str = new XMLParse().getXMLStrValue(resultSingleCar,"PauseDetect");
		if(str != null && str.equals("TRUE"))
		{
			detectionVehicle.bPauseDetect = true;							
		}
		//-------------------------------------------------------------------
		detectionVehicle.bLanbanDetect = false;
		str = new XMLParse().getXMLStrValue(resultSingleCar,"DetectLanBan");
		if(str != null && str.equals("TRUE"))
		{
			detectionVehicle.bLanbanDetect = true;							
		}
		//-------------------------------------------------------------------
		detectionVehicle.bFilterCheLan = false;
		str = new XMLParse().getXMLStrValue(resultSingleCar,"DetectFilter_GuaChe_CheLan");
		if(str != null && str.equals("TRUE"))
		{
			detectionVehicle.bFilterCheLan = true;							
		}
		//-------------------------------------------------------------------
		detectionVehicle.bFilterEndGas = false;
		str = new XMLParse().getXMLStrValue(resultSingleCar,"DetectFilter_EndGas");
		if(str != null && str.equals("TRUE"))
		{
			detectionVehicle.bFilterEndGas = true;							
		}
		//-------------------------------------------------------------------
		detectionVehicle.sLengthFilter = new XMLParse().getXMLStrValue(resultSingleCar,"LengthFilterType");;
		detectionVehicle.sWidthFilter = new XMLParse().getXMLStrValue(resultSingleCar,"WidthFilterType");;
		detectionVehicle.sHeightFilter = new XMLParse().getXMLStrValue(resultSingleCar,"HeightFilterType");;
		//-----------------------------------------------------------------------------------------------
		if(detectionVehicle.sCarTypeString != null && !detectionVehicle.sCarTypeString.equals(""))
			return true;
		else 
			return false;
	}
}
