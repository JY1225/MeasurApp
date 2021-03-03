package AppBase.appBase;

import java.util.HashMap;

import CarDetect.CarDetectSetting;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;

public class AppConst {
	private final static String DEBUG_TAG="AppConst";

//	public static String PCProperty = "C:/Program Files/SICK/LMS.properties";
	public static String UserConfigProperty = "UserConfig.ini";
	public static String MainDetectProperty = "CarDetect.ini";
	public static String DebugerManagerProperty = "DebugerManager.ini";
	public static String ResultMonitorProperty = "ResultMonitor.ini";
	
	public static String companyName = "";
	
    public static boolean bShowVehicleNum = false;

    public static int MAX_OPERATOR = 20;
}
