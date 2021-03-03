package NetworkCamera.SDK_Hikvision;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import NetworkCamera.NetworkCameraConst;

public class MainCapturePics {
	public static void main(String[] args) {
		if(LMSConstValue.sensorType[LMSConstValue.FRONT_CAMERA_ID].key.equals(LMSConstValue.SensorType.HK_DS))
    	{
    		 try {
    			 Camera_Hikvision.frontCameraBackgroundInit();        	
    		 }
    		 catch (UnsatisfiedLinkError e)
    		 {
    			 LMSLog.exceptionDialog(null,e); 
    		 }
    		 catch(NoClassDefFoundError e) 
    		 {
    			 LMSLog.exceptionDialog("摄像头异常", e);
    		 }
    	}
    	if(LMSConstValue.sensorType[LMSConstValue.BACK_CAMERA_ID].key.equals(LMSConstValue.SensorType.HK_DS))
    	{
    		 try {
    			 Camera_Hikvision.backCameraBackgroundInit();        	
    		 }
    		 catch (UnsatisfiedLinkError e)
    		 {
    			 LMSLog.exceptionDialog(null,e); 
    		 }
    		 catch(NoClassDefFoundError e) 
    		 {
    			 LMSLog.exceptionDialog("摄像头异常", e);
    		 }
    	}
    	
		Camera_Hikvision.capturePics(NetworkCameraConst.lUserIDCarIn, NetworkCameraConst.sImageSavePath, NetworkCameraConst.sFileNameCarIn);
		Camera_Hikvision.capturePics(NetworkCameraConst.lUserIDCarIn, NetworkCameraConst.sImageSavePath, NetworkCameraConst.sFileNameCarOut);
	}
}
