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

	public String sDetectNumOfTime;	//测量时间
	//----------------------------------------------------------
	public String sSerialNum;	//流水号
	public String sVehicleNum;	//号牌号码
	public String sVehicleNumType;	//号牌种类
	public String sVehicleType;		//车辆类型
	public String sVehicleBrand;	//品牌型号
	public String sVehicleID;		//车辆识别代码
	public String sMotorID;			//发动机号码
	public String sNewOrOld;		//在用车/注册车

	public String sOperatorID;		//操作员ID
	public String sOperatorName;	//操作员姓名
	
	//------------------------------------------------
	public String sOriginalCarLength;	//原车长
	public String sOriginalCarWidth;	//原车宽
	public String sOriginalCarHeight;	//原车高

	public String sOriginalLanBanHeight;	//原车栏板高
	public String sOriginalZJ;				//原车轴距

	//------------------------------------------------
	public String sCarTypeString;
	public static boolean bPauseDetect;	//暂停检测
	public boolean bLanbanDetect;	//栏板测量
	public boolean bFilterCheLan;
	public boolean bFilterEndGas;
	public String sLengthFilter;
	public String sWidthFilter;
	public String sHeightFilter;
	
	public boolean bDetectStatus;	//检测状态
}
