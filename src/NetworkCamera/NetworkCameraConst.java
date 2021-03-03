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
	
//	NET_DVR_NETWORK_FAIL_CONNECT 7 �����豸ʧ�ܡ��豸�����߻�����ԭ����������ӳ�ʱ�ȡ� 
	public static void showHikError(String cameraIP,HCNetSDK hcNetSDK)
	{
		int errorID = hcNetSDK.NET_DVR_GetLastError();
		String errorTitle = "";
		String errorStr = "";
		
		if(cameraIP != null)
		{
			if(cameraIP.equals(LMSConstValue.SENSOR_IP[LMSConstValue.FRONT_CAMERA_ID]))
				errorTitle += "��������ͷ����   ";
			else if(cameraIP.equals(LMSConstValue.SENSOR_IP[LMSConstValue.BACK_CAMERA_ID]))
				errorTitle += "��������ͷ����   ";
		}
		
		errorStr += "������:"+errorID+";";
		if(errorID == 1)
		{			
			errorStr += "�û����������ע��ʱ������û��������������";
		}
		else if(errorID == 7)
		{			
			errorStr += "�����豸ʧ�ܡ��豸�����߻�����ԭ����������ӳ�ʱ�ȡ� ";
		}
		
		LMSLog.errorDialog(errorTitle, errorStr);
	}
}
