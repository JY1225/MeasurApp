package NetworkCamera;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import NetworkCamera.SDK_Hikvision.HCNetSDK;

public class NetworkCameraConst {
	public static final String sImageSavePath = "image//";
	public static final String sImageTempSavePath = "image//tempPics/";
	public static final String sFileNameCarIn = "car_in";
	public static final String sFileNameCarOut = "car_out";
	
	public static long lUserIDCarIn;
	public static long lUserIDCarOut;
	
//	NET_DVR_NETWORK_FAIL_CONNECT 7 连接设备失败。设备不在线或网络原因引起的连接超时等。 
	public static void showHikError(String cameraIP,HCNetSDK hcNetSDK)
	{
		int errorID = hcNetSDK.NET_DVR_GetLastError();
		String errorTitle = "";
		String errorStr = "";
		
		if(cameraIP != null)
		{
			if(cameraIP.equals(LMSConstValue.SENSOR_IP[LMSConstValue.FRONT_CAMERA_ID]))
				errorTitle += "进车摄像头错误   ";
			else if(cameraIP.equals(LMSConstValue.SENSOR_IP[LMSConstValue.BACK_CAMERA_ID]))
				errorTitle += "出车摄像头错误   ";
		}
		
		errorStr += "错误码:"+errorID+";";
		if(errorID == 1)
		{			
			errorStr += "用户名密码错误。注册时输入的用户名或者密码错误。";
		}
		else if(errorID == 7)
		{			
			errorStr += "连接设备失败。设备不在线或网络原因引起的连接超时等。 ";
		}
		
		LMSLog.errorDialog(errorTitle, errorStr);
	}
}
