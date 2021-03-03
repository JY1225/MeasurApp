package NetworkCamera.SDK_Hikvision;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import NetworkCamera.NetworkCameraConst;

public class Camera_Hikvision {
	private static String DEBUG_TAG="Camera_Hikvision";

	private static HCNetSDK hCNetSDK = (HCNetSDK) Native.loadLibrary("HCNetSDK",HCNetSDK.class);
	private static HCNetSDK.NET_DVR_JPEGPARA lpJpegPara;
	
	public static long initAndRegist(String cameraIP,int sensorID) {
		NativeLong lUserID;
		HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;
		boolean init = hCNetSDK.NET_DVR_Init();
		hCNetSDK.NET_DVR_SetConnectTime(2000, 1);
		hCNetSDK.NET_DVR_SetReconnect(10000, true);
		if (init) {
			LMSLog.d(DEBUG_TAG,"海康威视摄像头SDK初始化成功");
		} 
		else {
			NetworkCameraConst.showHikError(null,hCNetSDK);
		}
		m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
	
		lUserID = hCNetSDK.NET_DVR_Login_V30(cameraIP, (short) 8000, LMSConstValue.sNvramCameraUserName[sensorID].sValue, LMSConstValue.sNvramCameraPassword[sensorID].sValue, m_strDeviceInfo);
		
		long userID = lUserID.longValue();
		LMSLog.d(DEBUG_TAG,"userID:" + userID);
		if (userID >= 0)
		{
			LMSConstValue.sendPortConnected(sensorID, true);
			
			LMSLog.d(DEBUG_TAG+sensorID,"注册成功");
		} 
		else if (userID < 0)
		{
			LMSConstValue.sendPortConnected(sensorID, false);

			NetworkCameraConst.showHikError(cameraIP,hCNetSDK);
			
			LMSLog.d(DEBUG_TAG+sensorID,"注册失败");
		}
		return userID;
	}

	public static boolean capturePics(long userID,String folderPath,String picsName)
	{
		if(userID >= 0)
		{
			NativeLong lUserID = new NativeLong(userID);
			String path = folderPath + picsName + ".jpg";
			lpJpegPara = new HCNetSDK.NET_DVR_JPEGPARA();
			boolean CaptureState = hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, new NativeLong(1), lpJpegPara, path);
			if (CaptureState) {
				LMSLog.d(DEBUG_TAG,"抓图成功");
			} else {
				LMSLog.d(DEBUG_TAG,"抓图失败");
			}
			return CaptureState;
		}
		else
		{
			return false;
		}
	}
	
	public static void frontCameraBackgroundInit()
	{
		NetworkCameraConst.lUserIDCarIn = Camera_Hikvision.initAndRegist(LMSConstValue.SENSOR_IP[LMSConstValue.FRONT_CAMERA_ID],LMSConstValue.FRONT_CAMERA_ID);
	}	

	public static void backCameraBackgroundInit()
	{
		NetworkCameraConst.lUserIDCarOut = Camera_Hikvision.initAndRegist(LMSConstValue.SENSOR_IP[LMSConstValue.BACK_CAMERA_ID],LMSConstValue.BACK_CAMERA_ID);
	}	
}
