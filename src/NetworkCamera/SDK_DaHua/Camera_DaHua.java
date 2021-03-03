package NetworkCamera.SDK_DaHua;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import NetworkCamera.NetworkCameraConst;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class Camera_DaHua
{

	private static String DEBUG_TAG = "Camera_DaHua";
	static NetSDKLib NetSdk = NetSDKLib.COMMON_INSTANCE;
	static NetSDKLib ConfigSdk = NetSDKLib.CONFIG_INSTANCE;

	private NetSDKLib.NET_DEVICEINFO m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO();
	private SDKEnvironment sdkEnv;
	private NetSDKLib.SNAP_PARAMS stParam = new NetSDKLib.SNAP_PARAMS();
	private String picsPath;

	public static Camera_DaHua frontCamera = new Camera_DaHua();
	public static Camera_DaHua backCamera = new Camera_DaHua();

	public long initAndRegist(String cameraIP, int sensorID)
	{
		//初始化SDK
		sdkEnv = new SDKEnvironment();
		boolean bInit = sdkEnv.init();

		if (bInit)
		{
			LMSLog.d(DEBUG_TAG, "大华摄像头SDK初始化成功");
		}
		else
		{
			LMSLog.d(DEBUG_TAG, "大华摄像头SDK初始化失败");
		}

		// 登录设备
		NativeLong nLUserID = new NativeLong(0);
		int nError[] =
		{ 0 };
		nLUserID = NetSdk.CLIENT_LoginEx(cameraIP, (short) 37777, LMSConstValue.sNvramCameraUserName[sensorID].sValue, LMSConstValue.sNvramCameraPassword[sensorID].sValue, 0, null, m_stDeviceInfo, nError);
		long userID = nLUserID.longValue();
		if (userID == 0)
		{
			System.err.printf("Login Device[%s] Port[%d]Failed. Last Error[%x]\n", cameraIP, new Integer("37777"), NetSdk.CLIENT_GetLastError());
			LMSConstValue.sendPortConnected(sensorID, false);
			LMSLog.d(DEBUG_TAG + sensorID, "注册失败");
		}
		else
		{
			System.out.println("Login Success [ " + cameraIP + " ]");
			LMSConstValue.sendPortConnected(sensorID, true);
			LMSLog.d(DEBUG_TAG + sensorID, "注册成功");
		}

		return userID;
	}

	//抓图
	public boolean capturePics(long userID, String folderPath, String picsName)
	{
		picsPath = folderPath + picsName + ".jpg";
		NativeLong nLUserID = new NativeLong(userID);
		
		SnapRev OnSnapRevMessage = new SnapRev();
		//设置抓图回调函数
		NetSdk.CLIENT_SetSnapRevCallBack(OnSnapRevMessage, nLUserID);
		
		
		boolean bCapture = NetSdk.CLIENT_SnapPictureEx(nLUserID, stParam, stParam.Reserved);
		if (bCapture)
		{
			LMSLog.d(DEBUG_TAG, "抓图请求成功");
			/**
			try
			{
				Thread.sleep(1500);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			*/
		}
		else
		{
			LMSLog.d(DEBUG_TAG, "抓图请求失败");
		}

		return bCapture;
	}

	//NetSDK 库初始化
	private class SDKEnvironment
	{
		private boolean bInit = false;
		private DisConnect disConnect = new DisConnect(); // 设备断线通知回调
		private HaveReConnect haveReConnect = new HaveReConnect(); // 网络连接恢复

		// 初始化
		public boolean init()
		{
			// SDK 库初始化, 并设置断线回调
			bInit = NetSdk.CLIENT_Init(disConnect, new NativeLong(0));
			if (!bInit)
			{
				System.err.println("Initialize SDK failed");
				return false;
			}

			// 获取版本, 可选操作
			//System.out.printf("NetSDK Version [%d]\n", NetSdk.CLIENT_GetSDKVersion());

			// 设置断线重连回调接口，设置过断线重连成功回调函数后，当设备出现断线情况，SDK内部会自动进行重连操作
			// 此操作为可选操作，但建议用户进行设置
			NetSdk.CLIENT_SetAutoReconnect(haveReConnect, new NativeLong(0));

			// 设置登录超时时间和尝试次数 , 此操作为可选操作	   
			int waitTime = 3000; // 登录请求响应超时时间设置为 5s
			int tryTimes = 3; // 登录时尝试建立链接3次
			NetSdk.CLIENT_SetConnectTime(waitTime, tryTimes);

			// 设置更多网络参数，NET_PARAM的nWaittime，nConnectTryNum成员与CLIENT_SetConnectTime 
			// 接口设置的登录设备超时时间和尝试次数意义相同
			// 此操作为可选操作
			NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
			netParam.nConnectTime = 2000; // 登录时尝试建立链接的超时时间
			NetSdk.CLIENT_SetNetworkParam(netParam);
			return true;
		}

		// 清除环境
		public void cleanup()
		{
			if (bInit)
			{
				NetSdk.CLIENT_Cleanup();
			}
		}

		// 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
		private class DisConnect implements NetSDKLib.fDisConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("Device[%s] Port[%d] Disconnect!\n", pchDVRIP, nDVRPort);
				System.out.println("断线了");
			}
		}

		// 网络连接恢复，设备重连成功回调
		// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
		private class HaveReConnect implements NetSDKLib.fHaveReConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
				System.out.println("断线后已经重连");
			}
		}
	}

	//抓图回调函数
	private class SnapRev implements NetSDKLib.fSnapRev
	{
		public void invoke(NativeLong lLoginID, Pointer pBuf, int RevLen, int EncodeType, NativeLong CmdSerial, NativeLong dwUser)
		{
			NetSDKTools.savePicture(pBuf, RevLen, picsPath);
		}
	}

	public void frontCameraBackgroundInit()
	{
		NetworkCameraConst.lUserIDCarIn = frontCamera.initAndRegist(LMSConstValue.SENSOR_IP[LMSConstValue.FRONT_CAMERA_ID], LMSConstValue.FRONT_CAMERA_ID);
	}

	public void backCameraBackgroundInit()
	{
		NetworkCameraConst.lUserIDCarOut = backCamera.initAndRegist(LMSConstValue.SENSOR_IP[LMSConstValue.BACK_CAMERA_ID], LMSConstValue.BACK_CAMERA_ID);
	}

}
