package CustomerProtocol;

import java.util.ArrayList;

public class DetectionVehicle {
	public static ArrayList<DetectionVehicle> detectionVehicleList = new ArrayList<DetectionVehicle>();
	public static boolean bAutoRefresh = false;
	public static boolean bAutoUpdate = false;

	public static boolean bRefreshAsyc = false;

	public static boolean bProtocolSocketServer = false;
	public static boolean bProtocolWS = false;
	public static boolean bProtocolDataBase = false;
	public static boolean bProtocolFile = false;
	public static boolean bProtocolDll = false;
	
	public static boolean bFileStrXML = false;
	public static boolean bFileStrLine = false;

	public int index;

	public int ID;

	public String sDetectNumOfTime;	//����ʱ��
	//----------------------------------------------------------
	public String sSerialNum;	//��ˮ��
	public String sVehicleNum;	//���ƺ���
	public String sVehicleNumType;	//��������
	public String sVehicleType;		//��������
	public String sVehicleBrand;	//Ʒ���ͺ�
	public String sVehicleID;		//����ʶ�����
	public String sMotorID;			//����������
	public String sNewOrOld;		//���ó�/ע�ᳵ

	public String sOperatorID;		//����ԱID
	public String sOperatorName;	//����Ա����
	
	//------------------------------------------------
	public String sOriginalCarLength;	//ԭ����
	public String sOriginalCarWidth;	//ԭ����
	public String sOriginalCarHeight;	//ԭ����

	public String sOriginalLanBanHeight;	//ԭ�������
	public String sOriginalZJ;				//ԭ�����

	//------------------------------------------------
	public String sCarTypeString;
	public static boolean bPauseDetect;	//��ͣ���
	public boolean bLanbanDetect;	//�������
	public boolean bFilterCheLan;
	public boolean bFilterEndGas;
	public String sLengthFilter;
	public String sWidthFilter;
	public String sHeightFilter;
	
	public boolean bDetectStatus;	//���״̬
}
