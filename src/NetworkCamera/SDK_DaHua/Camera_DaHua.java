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
		//��ʼ��SDK
		sdkEnv = new SDKEnvironment();
		boolean bInit = sdkEnv.init();

		if (bInit)
		{
			LMSLog.d(DEBUG_TAG, "������ͷSDK��ʼ���ɹ�");
		}
		else
		{
			LMSLog.d(DEBUG_TAG, "������ͷSDK��ʼ��ʧ��");
		}

		// ��¼�豸
		NativeLong nLUserID = new NativeLong(0);
		int nError[] =
		{ 0 };
		nLUserID = NetSdk.CLIENT_LoginEx(cameraIP, (short) 37777, LMSConstValue.sNvramCameraUserName[sensorID].sValue, LMSConstValue.sNvramCameraPassword[sensorID].sValue, 0, null, m_stDeviceInfo, nError);
		long userID = nLUserID.longValue();
		if (userID == 0)
		{
			System.err.printf("Login Device[%s] Port[%d]Failed. Last Error[%x]\n", cameraIP, new Integer("37777"), NetSdk.CLIENT_GetLastError());
			LMSConstValue.sendPortConnected(sensorID, false);
			LMSLog.d(DEBUG_TAG + sensorID, "ע��ʧ��");
		}
		else
		{
			System.out.println("Login Success [ " + cameraIP + " ]");
			LMSConstValue.sendPortConnected(sensorID, true);
			LMSLog.d(DEBUG_TAG + sensorID, "ע��ɹ�");
		}

		return userID;
	}

	//ץͼ
	public boolean capturePics(long userID, String folderPath, String picsName)
	{
		picsPath = folderPath + picsName + ".jpg";
		NativeLong nLUserID = new NativeLong(userID);
		
		SnapRev OnSnapRevMessage = new SnapRev();
		//����ץͼ�ص�����
		NetSdk.CLIENT_SetSnapRevCallBack(OnSnapRevMessage, nLUserID);
		
		
		boolean bCapture = NetSdk.CLIENT_SnapPictureEx(nLUserID, stParam, stParam.Reserved);
		if (bCapture)
		{
			LMSLog.d(DEBUG_TAG, "ץͼ����ɹ�");
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
			LMSLog.d(DEBUG_TAG, "ץͼ����ʧ��");
		}

		return bCapture;
	}

	//NetSDK ���ʼ��
	private class SDKEnvironment
	{
		private boolean bInit = false;
		private DisConnect disConnect = new DisConnect(); // �豸����֪ͨ�ص�
		private HaveReConnect haveReConnect = new HaveReConnect(); // �������ӻָ�

		// ��ʼ��
		public boolean init()
		{
			// SDK ���ʼ��, �����ö��߻ص�
			bInit = NetSdk.CLIENT_Init(disConnect, new NativeLong(0));
			if (!bInit)
			{
				System.err.println("Initialize SDK failed");
				return false;
			}

			// ��ȡ�汾, ��ѡ����
			//System.out.printf("NetSDK Version [%d]\n", NetSdk.CLIENT_GetSDKVersion());

			// ���ö��������ص��ӿڣ����ù����������ɹ��ص������󣬵��豸���ֶ��������SDK�ڲ����Զ�������������
			// �˲���Ϊ��ѡ�������������û���������
			NetSdk.CLIENT_SetAutoReconnect(haveReConnect, new NativeLong(0));

			// ���õ�¼��ʱʱ��ͳ��Դ��� , �˲���Ϊ��ѡ����	   
			int waitTime = 3000; // ��¼������Ӧ��ʱʱ������Ϊ 5s
			int tryTimes = 3; // ��¼ʱ���Խ�������3��
			NetSdk.CLIENT_SetConnectTime(waitTime, tryTimes);

			// ���ø������������NET_PARAM��nWaittime��nConnectTryNum��Ա��CLIENT_SetConnectTime 
			// �ӿ����õĵ�¼�豸��ʱʱ��ͳ��Դ���������ͬ
			// �˲���Ϊ��ѡ����
			NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
			netParam.nConnectTime = 2000; // ��¼ʱ���Խ������ӵĳ�ʱʱ��
			NetSdk.CLIENT_SetNetworkParam(netParam);
			return true;
		}

		// �������
		public void cleanup()
		{
			if (bInit)
			{
				NetSdk.CLIENT_Cleanup();
			}
		}

		// �豸���߻ص�: ͨ�� CLIENT_Init ���øûص����������豸���ֶ���ʱ��SDK����øú���
		private class DisConnect implements NetSDKLib.fDisConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("Device[%s] Port[%d] Disconnect!\n", pchDVRIP, nDVRPort);
				System.out.println("������");
			}
		}

		// �������ӻָ����豸�����ɹ��ص�
		// ͨ�� CLIENT_SetAutoReconnect ���øûص����������Ѷ��ߵ��豸�����ɹ�ʱ��SDK����øú���
		private class HaveReConnect implements NetSDKLib.fHaveReConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
				System.out.println("���ߺ��Ѿ�����");
			}
		}
	}

	//ץͼ�ص�����
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
