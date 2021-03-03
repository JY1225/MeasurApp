package SensorBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import layer.algorithmLayer.ParseLMSAckCommand;
import lmsBase.LMSProductInfo;
import lmsBase.Md5;
import lmsEvent.LMSEventManager;

public class LMSConstValue {
	private final static String DEBUG_TAG="LMSConstValue";

	public final static int INVALID_X = 500000;

	public static int[] I_LR_POINT_COUNT  = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static int[] I_CENTER_POINT_COUNT  = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static int[] MIN_CONTINUOUS_BLOCK_POINT_NUM = new int[LMSConstValue.RADAR_SENSOR_NUM];

	public static int VALID_THING_HEIGHT_WH = 150; //不能太小，否则一些干扰点将引起误差
	public static int VALID_THING_HEIGHT_LONG = 150; //不能太小，否则一些干扰点将引起误差
	public final static int VALID_THING_HEIGHT[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; //不能太小，否则一些干扰点将引起误差

	//======================================================================
	public static LMSConstValue innerObj = new LMSConstValue();

	public enum enumDetectType{
		UNKNOW_DETECT_TYPE,
		WIDTH_HEIGHT_1_DETECT,	//低速单头检测
		WH1_L1_SAME, //单头宽高，单头长(同点安装，十字交叉,使用补偿算法)
		WH1_L1_DIF, //单头宽高，单头长(异点安装，十字交叉,使用夹击算法)
		WH_1_HIGH_SPEED,	//高速单头检测
		WH2, //宽高双头
		WH2_L1_SAME,	 //宽高双头，长单头(同点安装)
		WH2_L1_DIF,	 //宽高双头，长单头(异点安装)
		WH2_L2_SAME,	 
		LM1,	
		LM_VMS_OPS,	 
		ANTI_COLLITION, //反撞
		
		//Ben
		WH1_L1_TIC,    //高速车辆宽高测量系统， 宽高雷达1，长雷达1
		VMS,           //体积测量系统
    } 	
	public static enumDetectType getEnumDetectTypeFromOridal(int ordinal)
	{
		for(enumDetectType e : enumDetectType.values()) { 
			if(e.ordinal() == ordinal)
				return e;
		}
		
		return enumDetectType.WIDTH_HEIGHT_1_DETECT;
	}
	public static enumDetectType getEnumDetectTypeFromString(String str)
	{
		for(enumDetectType e : enumDetectType.values()) { 
			if(e.toString().equals(str))
				return e;
		}
		
		return enumDetectType.WIDTH_HEIGHT_1_DETECT;
	}
	public static enumDetectType defaultDetectType;
	
	public enum AppType{
		RESULT_MONITOR,
		CAR_DETECT,
		CONTOUR_DETECTION,
	}
	public static AppType appType;
		
	public static final String MAIN_PROPERTY = "MAIN_PROPERTY";
	public static final String USER_PROPERTY = "USER_PROPERTY";
	public static final String RESULT_MONITOR_PROPERTY = "RESULT_MONITOR_PROPERTY";

	public enum CarMoveState{
		NO_CAR,
		CAR_STOP,
		CAR_MOVING,
		CAR_STOP_TO_MOVE,
		CAR_MOVE_TO_STOP,
	}
		
	public static boolean bMyMachine = false;
	public static void setMyMachine()
	{
		bMyMachine = false;

		List<String> macList = LMSConstValue.getWindowsMACAddress();    
		if(macList != null)
		{
			for(int i=0;i<macList.size();i++)
			{
				String mac = macList.get(i);
				
				System.out.println("mac="+mac);   

				if(mac.equals("3C-97-3F-5D-EE-99")
					||mac.equals("3C-97-0E-ED-EE-99")
					||mac.equals("12-24-50-D3-2F-40")
					||mac.equals("50-7B-9D-46-C8-3B")
					||mac.equals("54-EE-75-38-99-26")
					||mac.equals("74-29-AF-0B-2F-D5")
					||mac.equals("16-8E-8F-7F-40-D7")
					||mac.equals("56-8E-8F-46-40-D7")
					||mac.equals("F0-76-1C-C6-BB-55")
					||mac.equals("A4-DB-30-FE-A4-31")
					||mac.equals("3C-97-0E-C9-0B-70")
					||mac.equals("00-26-82-98-B9-FD")
					||mac.equals("5C-FF-35-06-3E-0B")
					||mac.equals("EC-9A-74-55-1E-5F")
					||mac.equals("20-1A-06-36-65-F8")
					||mac.equals("8C-89-A5-C5-2D-52")
					||mac.equals("8C-89-A5-C5-2D-52")
					||mac.equals("50-7B-9D-FB-53-00")//嘉华
				)
				{
					bMyMachine = true;

					break;
				}
			}
		}
	}
	
	public static boolean isMyMachine()
	{		
		return bMyMachine;
	}
	
	public static boolean bAuthOk = false;
	public static int INVALID_POINT_SIDE_FILTER_NUM = 1;

	public final static String SDF = "yyyy-MM-dd HH:mm:ss,SSS";
	
	public static boolean bInnerSetting = false;

	public static String appLable;	//包名
	public static String softwareVersionTime = "20170421";	//大版本（供用户看）
	public static String softwareVersionNum = "3.25";	//大版本（供用户看）
	public static String softwareVersion = "v"+softwareVersionNum+"("+softwareVersionTime+")";	//大版本（供用户看）
	public static String versionSdf = "yyyyMMdd";  
	public static int versionCode;	//小版本（真正升级判断用）

	//=======================================================
	public static int LIGHT_CURTAIN_TIME_STAMP_START = 2;
	public static int LIGHT_CURTAIN_TIME_STAMP_LENGTH = 4;
	public static int LIGHT_CURTAIN_DATA_START = 6;
	public static int LIGHT_CURTAIN_DATA_LENGTH = 12;

	//=======================================================
 	public static final int BEA_SYNC_LENGTH = 4;
	public static final int BEA_SIZE_LENGTH = 2;
	public static final int BEA_CMD_LENGTH = 2;
	public static final int BEA_ID_LENGTH = 4;
	public static final int BEA_FRAME_COUNTER_LENGTH = 2;
	public static final int BEA_PLANE_NUM_LENGTH = 1;
	public static final int BEA_MDI_LENGTH = 548;
	public static final int BEA_CHK_LENGTH = 2;
	public static final int BEA_CMD_START = 
		BEA_SYNC_LENGTH
		+BEA_SIZE_LENGTH;
	public static final int BEA_921_MSG_LENGTH = 
		BEA_CMD_LENGTH
		+BEA_ID_LENGTH
		+BEA_FRAME_COUNTER_LENGTH
		+BEA_PLANE_NUM_LENGTH
		+BEA_MDI_LENGTH;		
	public static final int BEA_ID_START = 
		BEA_SYNC_LENGTH
		+BEA_SIZE_LENGTH
		+BEA_CMD_LENGTH;
	public static final int BEA_FRAME_COUNTER_START = 
		BEA_ID_START
		+BEA_ID_LENGTH;
	public static final int BEA_PN_MDI_START = 
		BEA_FRAME_COUNTER_START
		+BEA_FRAME_COUNTER_LENGTH;
	public static final int BEA_MDI_DATA_START = 
		BEA_PN_MDI_START
		+BEA_PLANE_NUM_LENGTH;
	public static final int BEA_921_CHK_START = 
		BEA_MDI_DATA_START
		+BEA_MDI_LENGTH;
	public static final int BEA_921_PACKET_LENGTH = 
		BEA_SYNC_LENGTH
		+BEA_SIZE_LENGTH
		+BEA_CMD_LENGTH
		+BEA_ID_LENGTH
		+BEA_FRAME_COUNTER_LENGTH
		+BEA_PLANE_NUM_LENGTH+BEA_MDI_LENGTH
		+BEA_CHK_LENGTH;
	public static final int BEA_920_PACKET_LENGTH = 
		BEA_SYNC_LENGTH
		+BEA_SIZE_LENGTH
		+BEA_CMD_LENGTH
		+BEA_ID_LENGTH
		+BEA_FRAME_COUNTER_LENGTH
		+(BEA_PLANE_NUM_LENGTH+BEA_MDI_LENGTH)*4
		+BEA_CHK_LENGTH;
	/*
fc fd fe ff --- BEA_SYNC_LENGTH = 4;
9c 08 --- BEA_SIZE_LENGTH = 2;
5b c3 --- BEA_CMD_LENGTH = 2;
9e e4 29 00 --- BEA_ID_LENGTH = 4;
17 13 --- BEA_FRAME_COUNTER_LENGTH = 2;

00 --- BEA_PLANE_NUM_LENGTH = 1;
40 04 53 04 53 04 53 04 53 04 54 04 5c 04 5c 04 5c 04 66 04 67 04 66 04 66 04 6f 04 
66 04 6f 04 6f 04 79 04 79 04 79 04 82 04 79 04 82 04 82 04 82 04 83 04 8b 04 8b 04 
95 04 95 04 95 04 95 04 96 04 9e 04 9f 04 9f 04 a7 04 a8 04 b1 04 b2 04 b2 04 ba 04 
bb 04 c4 04 c4 04 c5 04 cd 04 ce 04 ce 04 d6 04 e1 04 d7 04 e9 04 e9 04 fc 04 f3 04 
fc 04 fc 04 05 05 05 05 0f 05 10 05 18 05 21 05 2b 05 2b 05 2c 05 34 05 3e 05 35 05 
51 05 48 05 51 05 5b 05 63 05 63 05 6e 05 6e 05 6e 05 7f 05 7f 05 8a 05 93 05 a5 05 
a6 05 b8 05 b9 05 c2 05 cb 05 d5 05 d5 05 de 05 f1 05 04 06 fa 05 0d 06 17 06 20 06 
20 06 29 06 33 06 46 06 4f 06 58 06 6b 06 74 06 7e 06 91 06 91 06 ad 06 ad 06 c0 06 
d2 06 dc 06 e5 06 ee 06 01 07 15 07 1e 07 31 07 43 07 44 07 57 07 6a 07 73 07 8f 07 
98 07 ab 07 c0 07 cc 07 d9 07 dc 07 de 07 dd 07 dd 07 d3 07 dc 07 dc 07 db 07 dc 07 
db 07 d1 07 d1 07 d1 07 d1 07 d1 07 d0 07 d1 07 d1 07 d0 07 db 07 d0 07 d1 07 d0 07 
d1 07 db 07 d1 07 db 07 da 07 da 07 e3 07 e3 07 da 07 e4 07 e3 07 e3 07 e4 07 e4 07 
e3 07 ee 07 ed 07 ee 07 ed 07 ed 07 ee 07 f6 07 f7 07 00 08 f6 07 f7 07 00 08 ff 07 
0a 08 0a 08 09 08 0a 08 13 08 13 08 13 08 1d 08 1d 08 26 08 26 08 26 08 2f 08 2f 08 
2e 08 38 08 42 08 41 08 4a 08 4b 08 4b 08 54 08 5d 08 5d 08 5d 08 67 08 68 08 70 08 
70 08 7a 08 79 08 83 08 96 08 96 08 9f 08 9f 08 a8 08 a8 08 b2 08 bb 08 ba 08 c4 08 
cd 08 ce 08 d6 08 e1 08 e1 08 e9 08 f2 08 fd 08 06 09 06 09 10 09 21 09 21 09 2b 09 
34 09 34 09 3e 09 48 09 50 09 5b 09 63 09 6b 09 76 09 7f 09 89 09 9b 09 a4 09 a5 09 
b9 09 c1 09 ca 09 dd 09 e6 09 f0 09 fa 09 0c 0a 15 0a 28 0a 32 0a 3b 0a 4e 0a 57 0a 
6a 0a 72 0a 85 0a 98 0a a1 0a b4 0a bd 0a da 0a 
01 --- BEA_PLANE_NUM_LENGTH = 1;
e4 04 f6 04 f7 04 f5 04 f5 04 f5 04 f5 04 fe 04 fe 04 fe 04 ff 04 fe 04 07 05 07 05 
08 05 11 05 11 05 11 05 1a 05 1a 05 1a 05 23 05 23 05 2d 05 2d 05 2d 05 2d 05 2d 05 
2d 05 36 05 40 05 36 05 49 05 49 05 52 05 5b 05 51 05 5c 05 5c 05 5c 05 64 05 65 05 
6f 05 6f 05 78 05 78 05 81 05 81 05 93 05 8b 05 94 05 9d 05 9e 05 a7 05 af 05 af 05 
b9 05 c2 05 cb 05 cb 05 d5 05 de 05 df 05 e8 05 f1 05 fb 05 05 06 0e 06 17 06 20 06 
21 06 21 06 34 06 3d 06 47 06 50 06 59 06 63 06 63 06 6e 06 76 06 7f 06 88 06 9c 06 
9b 06 a4 06 b8 06 b8 06 cb 06 d5 06 dd 06 e7 06 f0 06 03 07 0d 07 1e 07 29 07 3d 07 
45 07 4f 07 59 07 62 07 74 07 7d 07 90 07 9a 07 ad 07 c1 07 d6 07 e2 07 f0 07 f3 07 
f8 07 eb 07 f4 07 e8 07 e8 07 df 07 df 07 e8 07 df 07 d5 07 d5 07 d4 07 d5 07 d4 07 
d5 07 d4 07 d5 07 d4 07 cc 07 cc 07 cc 07 d4 07 cb 07 cb 07 c2 07 cc 07 cc 07 cb 07 
c3 07 c3 07 c2 07 c2 07 c2 07 cb 07 cb 07 c2 07 ca 07 cb 07 ca 07 c1 07 c2 07 ca 07 
ca 07 ca 07 cb 07 ca 07 cb 07 ca 07 d4 07 cc 07 d3 07 d4 07 d3 07 d4 07 d4 07 d3 07 
de 07 de 07 d3 07 dd 07 de 07 e6 07 e6 07 e5 07 dd 07 e7 07 e6 07 f0 07 fa 07 f9 07 
f9 07 f9 07 f9 07 f9 07 02 08 0c 08 0c 08 0c 08 0c 08 14 08 15 08 14 08 1f 08 1f 08 
28 08 28 08 28 08 31 08 30 08 3b 08 43 08 44 08 4d 08 4c 08 56 08 56 08 5f 08 5e 08 
68 08 73 08 72 08 71 08 7b 08 85 08 84 08 8e 08 98 08 98 08 a2 08 aa 08 ab 08 ab 08 
bc 08 bc 08 c6 08 cf 08 d9 08 d9 08 e2 08 eb 08 f5 08 ff 08 ff 08 11 09 11 09 1a 09 
23 09 2d 09 36 09 36 09 41 09 4a 09 51 09 66 09 65 09 6e 09 82 09 82 09 94 09 94 09 
a7 09 b0 09 c3 09 cc 09 d6 09 df 09 f1 09 05 0a 04 0a 17 0a 20 0a 33 0a 3c 0a 45 0a 
58 0a 6b 0a 6a 0a 86 0a 99 0a ac 0a b4 0a c7 0a 
02 --- BEA_PLANE_NUM_LENGTH = 1; 
ff 03 07 04 08 04 08 04 12 04 12 04 12 04 08 04 12 04 12 04 12 04 1b 04 1b 04 1b 04 
1b 04 1b 04 25 04 25 04 25 04 2e 04 2e 04 2e 04 2e 04 37 04 37 04 37 04 37 04 36 04 
41 04 41 04 41 04 4a 04 41 04 53 04 53 04 53 04 53 04 53 04 5d 04 5d 04 66 04 66 04 
66 04 70 04 70 04 79 04 70 04 79 04 82 04 82 04 82 04 82 04 8c 04 8c 04 95 04 95 04 
9f 04 9f 04 a8 04 a8 04 b1 04 bb 04 bb 04 c4 04 c4 04 cd 04 cd 04 d7 04 d7 04 e0 04 
e0 04 f3 04 f3 04 f3 04 fc 04 06 05 06 05 0f 05 0f 05 19 05 2b 05 2b 05 2b 05 35 05 
48 05 48 05 51 05 5a 05 5a 05 5a 05 64 05 6d 05 6d 05 76 05 80 05 93 05 9c 05 9c 05 
a5 05 b0 05 b8 05 cb 05 cb 05 de 05 df 05 e8 05 f1 05 03 06 0d 06 17 06 20 06 33 06 
3d 06 3d 06 4f 06 62 06 6a 06 7e 06 88 06 92 06 a5 06 b7 06 c0 06 ca 06 d4 06 e6 06 
f9 06 0b 07 1f 07 28 07 3b 07 44 07 57 07 60 07 73 07 87 07 a3 07 b7 07 c4 07 d0 07 
de 07 df 07 de 07 df 07 de 07 dd 07 dd 07 dd 07 dd 07 dd 07 dc 07 dd 07 dd 07 d4 07 
dd 07 dd 07 dd 07 dc 07 e6 07 dd 07 e6 07 e6 07 e5 07 e6 07 e6 07 e6 07 e5 07 f0 07 
f0 07 f8 07 f9 07 f8 07 f8 07 f9 07 02 08 f9 07 03 08 02 08 02 08 0b 08 0c 08 0b 08 
14 08 15 08 13 08 1e 08 14 08 1e 08 1d 08 28 08 28 08 31 08 27 08 31 08 3a 08 39 08 
42 08 4d 08 4c 08 4d 08 56 08 56 08 5f 08 5e 08 5e 08 69 08 72 08 72 08 7b 08 7c 08 
85 08 84 08 8e 08 8d 08 97 08 9f 08 ab 08 b3 08 b3 08 bc 08 bc 08 c6 08 c5 08 cf 08 
d8 08 d8 08 e2 08 eb 08 f4 08 f5 08 fd 08 07 09 11 09 11 09 1a 09 23 09 35 09 35 09 
40 09 40 09 48 09 52 09 5b 09 64 09 6f 09 77 09 89 09 89 09 9c 09 a6 09 af 09 b9 09 
c2 09 cc 09 d5 09 e8 09 f9 09 f9 09 0d 0a 16 0a 29 0a 32 0a 3b 0a 46 0a 61 0a 6a 0a 
73 0a 86 0a 99 0a a2 0a b5 0a bf 0a d0 0a e3 0a 
03 --- BEA_PLANE_NUM_LENGTH = 1;
83 04 96 04 96 04 9f 04 96 04 9f 04 9f 04 96 04 a8 04 9f 04 a8 04 a9 04 a8 04 a8 04 
b2 04 b2 04 bb 04 bb 04 bb 04 bb 04 c5 04 ce 04 c5 04 ce 04 ce 04 ce 04 d7 04 d7 04 
d7 04 e1 04 e1 04 ea 04 ea 04 ea 04 ea 04 f4 04 fd 04 fd 04 fd 04 fd 04 06 05 06 05 
10 05 19 05 19 05 22 05 22 05 22 05 2c 05 2c 05 2c 05 35 05 35 05 48 05 48 05 51 05 
51 05 5b 05 64 05 5b 05 64 05 64 05 6e 05 77 05 80 05 80 05 8a 05 8a 05 9d 05 9d 05 
a6 05 a6 05 af 05 b9 05 cb 05 cb 05 d5 05 de 05 e8 05 f1 05 fa 05 fa 05 04 06 17 06 
20 06 18 06 2a 06 34 06 3c 06 3d 06 50 06 58 06 63 06 6c 06 7f 06 7f 06 92 06 9b 06 
ae 06 b7 06 c1 06 ca 06 d4 06 e7 06 f0 06 02 07 15 07 1e 07 29 07 3b 07 45 07 4e 07 
57 07 6b 07 7d 07 8f 07 99 07 b7 07 c2 07 d8 07 e4 07 df 07 df 07 df 07 e0 07 d4 07 
d4 07 de 07 d4 07 d4 07 dd 07 d4 07 d4 07 d4 07 d3 07 d3 07 d3 07 ca 07 d2 07 d2 07 
d3 07 ca 07 ca 07 ca 07 d3 07 d2 07 ca 07 ca 07 ca 07 cb 07 d3 07 d2 07 ca 07 ca 07 
d3 07 d2 07 d2 07 d3 07 d3 07 d2 07 d2 07 d2 07 d2 07 dd 07 dd 07 dd 07 dd 07 dc 07 
dd 07 dc 07 dd 07 e6 07 e5 07 ef 07 e6 07 ef 07 ef 07 f8 07 f8 07 f8 07 f8 07 f9 07 
f8 07 01 08 0c 08 02 08 0c 08 0b 08 14 08 13 08 14 08 1e 08 1e 08 1e 08 27 08 26 08 
27 08 2f 08 2f 08 39 08 39 08 42 08 4b 08 4b 08 55 08 55 08 56 08 68 08 68 08 68 08 
71 08 72 08 7b 08 7a 08 85 08 8d 08 97 08 97 08 a0 08 a1 08 a9 08 a9 08 bd 08 bc 08 
c5 08 c5 08 cf 08 d8 08 d8 08 e2 08 eb 08 f4 08 fd 08 07 09 10 09 10 09 1a 09 24 09 
2c 09 35 09 40 09 3f 09 48 09 51 09 5c 09 64 09 6d 09 77 09 8a 09 8a 09 9c 09 a6 09 
b9 09 b9 09 c1 09 d5 09 de 09 e7 09 fb 09 04 0a 0d 0a 20 0a 29 0a 3c 0a 4e 0a 58 0a 
61 0a 74 0a 87 0a 8e 0a a2 0a ac 0a b5 0a d1 0a 
5a 72 --- BEA_CHK_LENGTH = 2;
	 */
	
	//=======================================================
	 	public static final int BEA_FS_SYNC_LENGTH = 11;
		public static final int BEA_FS_CMD_LENGTH = 2;
		public static final int BEA_FS_ID_LENGTH = 4;
		public static final int BEA_FS_FRAME_COUNTER_LENGTH = 2;
		public static final int BEA_FS_TEMP_LENGTH = 2;
		public static final int BEA_FS_FACET_LENGTH = 1;
		public static final int BEA_FS_MDI_LENGTH = 336;
		public static final int BEA_FS_CHK_LENGTH = 2;
		
		public static final int BEA_FS_CMD_START = BEA_FS_SYNC_LENGTH;
		public static final int BEA_FS_MSG_LENGTH = 
			BEA_FS_CMD_LENGTH
			+BEA_FS_ID_LENGTH
			+BEA_FS_FRAME_COUNTER_LENGTH
			+BEA_FS_TEMP_LENGTH
			+BEA_FS_FACET_LENGTH;		
		public static final int BEA_FS_ID_START = 
			BEA_FS_SYNC_LENGTH
			+BEA_FS_CMD_LENGTH;
		public static final int BEA_FS_FRAME_COUNTER_START = 
			BEA_FS_FRAME_COUNTER_LENGTH
			+BEA_FS_ID_START
			+BEA_FS_ID_LENGTH;
		public static final int BEA_FS_MDI_DATA_START = 
			BEA_FS_FRAME_COUNTER_START
			+BEA_FS_TEMP_LENGTH
			+BEA_FS_FACET_LENGTH;
		public static final int BEA_FS_CHK_START = 
			BEA_FS_MDI_DATA_START
			+BEA_FS_MDI_LENGTH;
		public static final int BEA_FS_PACKET_LENGTH = 
			BEA_FS_CHK_START
			+BEA_FS_CHK_LENGTH;
		
/*
 * 
BE A0 12 34 02 68 01 02 00 00 00 --- SYNC

5B C3 --- CMD
44 8F 33 00 --- SERIAL NUM
B4 03 --- SCAN COUNTER
81 01 --- TEMP
05 --- FACET

55 06 29 06 11 06 F4 05 DF 05 B9 05 A2 05 8F 05 78 05 5E 05 43 05 2D 05 15 05 FF 04 ED 04 D7 04 C6 04 BB 04 A5 04 9C 04 
8B 04 82 04 6E 04 5E 04 53 04 45 04 3D 04 35 04 25 04 12 04 14 04 0C 04 FF 03 F1 03 E9 03 DA 03 C9 03 D1 03 B7 03 BE 03 
AE 03 A5 03 9A 03 95 03 8B 03 8D 03 80 03 81 03 7F 03 7C 03 73 03 62 03 60 03 60 03 54 03 4B 03 53 03 43 03 4F 03 3A 03 
40 03 34 03 2D 03 30 03 2E 03 2E 03 22 03 26 03 1A 03 1E 03 10 03 0A 03 F2 02 DE 01 F5 02 C3 01 FE 02 FE 02 02 03 04 03 
03 03 FA 02 FD 02 F3 02 F4 02 F0 02 F6 02 F1 02 F1 02 F3 02 EF 02 EE 02 F0 02 EF 02 E2 02 E8 02 EF 02 F3 02 F0 02 E7 02 
F2 02 EC 02 EA 02 E5 02 F2 02 EB 02 EE 02 EF 02 E5 02 EF 02 F2 02 F2 02 F2 02 F0 02 F2 02 EF 02 F2 02 F1 02 E8 02 ED 02 
F8 02 F2 02 E7 02 F5 02 FE 02 ED 02 FC 02 02 03 0A 03 05 03 08 03 02 03 0C 03 16 03 1D 03 15 03 1B 03 18 03 1E 03 28 03 
17 03 21 03 21 03 28 03 27 03 3E 02 AD 01 77 01 66 01 53 01 3E 01 53 01 87 01 65 01 49 01 33 01 2A 01 22 01 1C 01 11 01 
0B 01 08 01 03 01 06 01 FB 00 F5 00 F8 00 F5 00 --- 336(168*2)

52 45 --- CHK
 */

	public static final String[] boardIPSpinnerString=
	{
		"192.168.0.100",
		"192.168.0.110",
		"192.168.0.120",
	}; 
	
	public static final String[] nearSensorSpinnerString=
	{
		"LMS0",
		"LMS1",
	}; 

	public class EnumFixMethod extends enumType{		
		public static final String UP_FIX = "UP_FIX";
		public static final String GROUND_FIX_LEFT_WINDOW = "GROUND_FIX_LEFT_WINDOW";
		public static final String GROUND_FIX_RIGHT_WINDOW = "GROUND_FIX_RIGHT_WINDOW";

		public EnumFixMethod() {
			map.put(UP_FIX,"      悬挂安装");  
			map.put(GROUND_FIX_LEFT_WINDOW,"地面安装左窗有效");  
			map.put(GROUND_FIX_RIGHT_WINDOW,"地面安装右窗侧有效");  
		}	
	}
	public static EnumFixMethod fixMethod[] = new EnumFixMethod[LMSConstValue.RADAR_SENSOR_NUM]; 
	public static String nvramFixMethod = "nvram_fixMethod";
	
	public class EnumBackgroundColor extends enumType{		
		public static final String BLACK_BACKGROUND_WHITH_FONT = "BLACK_BACKGROUND_WHITH_FONT";
		public static final String WHITE_BACKGROUND_BLACK_FONT = "WHITE_BACKGROUND_BLACK_FONT";

		public EnumBackgroundColor() {
			map.put(BLACK_BACKGROUND_WHITH_FONT,"黑底白点");  
			map.put(WHITE_BACKGROUND_BLACK_FONT,"白底黑点");  
		}	
	}
	public static EnumBackgroundColor enumBackgroundColor = innerObj.new EnumBackgroundColor();  
	public static String nvramBackgroundColor = "nvram_backgroundColor";

	public class EnumImageFormat extends enumType{		
		public static final String PNG = "PNG";
		public static final String JPG = "JPG";

		public EnumImageFormat() {
			map.put(PNG,"png");  
			map.put(JPG,"jpg");  
		}	
	}
	public static EnumImageFormat enumImageFormat = innerObj.new EnumImageFormat();  
	public static String nvramImageFormat = "nvram_imageFormat";

	public class EnumThreeDImageSize extends enumType{		
		public static final String ThreeDImageSIZE10 = "ThreeDImageSIZE10";
		public static final String ThreeDImageSIZE9 = "ThreeDImageSIZE9";
		public static final String ThreeDImageSIZE8 = "ThreeDImageSIZE8";
		public static final String ThreeDImageSIZE7 = "ThreeDImageSIZE7";
		public static final String ThreeDImageSIZE6 = "ThreeDImageSIZE6";
		public static final String ThreeDImageSIZE5 = "ThreeDImageSIZE5";
		public static final String ThreeDImageSIZE4 = "ThreeDImageSIZE4";
		public static final String ThreeDImageSIZE3 = "ThreeDImageSIZE3";
		public static final String ThreeDImageSIZE2 = "ThreeDImageSIZE2";
		public static final String ThreeDImageSIZE1 = "ThreeDImageSIZE1";

		public EnumThreeDImageSize() {
			map.put(ThreeDImageSIZE10,"1/1");  
			map.put(ThreeDImageSIZE9,"9/10");  
			map.put(ThreeDImageSIZE8,"8/10");  
			map.put(ThreeDImageSIZE7,"7/10");  
			map.put(ThreeDImageSIZE6,"6/10");  
			map.put(ThreeDImageSIZE5,"5/10");  
			map.put(ThreeDImageSIZE4,"4/10");  
			map.put(ThreeDImageSIZE3,"3/10");  
			map.put(ThreeDImageSIZE2,"2/10");  
			map.put(ThreeDImageSIZE1,"1/10");  
		}	
	}
	public static EnumThreeDImageSize enumThreeDImageSize = innerObj.new EnumThreeDImageSize(); 
	public static String nvramThreeDImageSize = "nvram_threeDImageSize";
	
	public class EnumCarInDirection extends enumType{		
		public static final String CAR_IN_WH_FIRST = "CAR_IN_WH_FIRST";
		public static final String CAR_IN_LONG_FIRST = "CAR_IN_LONG_FIRST";

		public EnumCarInDirection() {
			map.put(CAR_IN_WH_FIRST,"进车时,车头先穿越宽高龙门");  
			map.put(CAR_IN_LONG_FIRST,"进车时,车头先穿越长龙门");  
		}	
	}
	public static EnumCarInDirection enumCarInDirection = innerObj.new EnumCarInDirection();  
	public static String nvramCarInDirection = "nvram_carInDirection";
		
	public static final String LENGTH_FILTER_NONE = "LENGTH_FILTER_NONE";
	public static final String LENGTH_FILTER_BODY = "LENGTH_FILTER_BODY";
	public class EnumLengthFilterType extends enumType{		
		public EnumLengthFilterType() {
			map.put(LENGTH_FILTER_NONE,"无剔除");  
			map.put(LENGTH_FILTER_BODY,"只测车体");  
//			map.put(WIDTH_FILTER_BIG,"超大物体滤除");  
		}	
	}
	public static EnumLengthFilterType enumLengthFilterType = innerObj.new EnumLengthFilterType(); 
	public static String nvramEnumLengthFilterType = "nvram_EnumLengthFilterType";

	public static final String WIDTH_FILTER_NONE = "WIDTH_FILTER_NONE";
	public static final String WIDTH_FILTER_LEVEL1 = "WIDTH_FILTER_LEVEL1";
	public static final String WIDTH_FILTER_LEVEL2 = "WIDTH_FILTER_LEVEL2";
	public static final String WIDTH_FILTER_BODY = "WIDTH_FILTER_BODY";
	public static final String WIDTH_FILTER_BIG = "WIDTH_FILTER_BIG";
	public class EnumWidthFilterType extends enumType{		
		public EnumWidthFilterType() {
			map.put(WIDTH_FILTER_NONE,"无剔除");  
			map.put(WIDTH_FILTER_LEVEL1,"一级剔除");  
			map.put(WIDTH_FILTER_LEVEL2,"二级剔除");  
			map.put(WIDTH_FILTER_BODY,"只测车体");  
//			map.put(WIDTH_FILTER_BIG,"超大物体滤除");  
		}	
	}
	public static EnumWidthFilterType enumWidthFilterType = innerObj.new EnumWidthFilterType(); 
	public static String nvramEnumWidthFilterType = "nvram_EnumWidthFilterType";

	public static final String HEIGHT_FILTER_NONE = "HEIGHT_FILTER_NONE";
	public static final String HEIGHT_FILTER_LEVEL1 = "HEIGHT_FILTER_LEVEL1";
	public static final String HEIGHT_FILTER_LEVEL2 = "HEIGHT_FILTER_LEVEL2";
	public static final String HEIGHT_FILTER_BODY = "HEIGHT_FILTER_BODY";
	public class EnumHeightFilterType extends enumType{		
		public EnumHeightFilterType() {
			map.put(HEIGHT_FILTER_NONE,"无剔除");  
			map.put(HEIGHT_FILTER_LEVEL1,"一级剔除");  
			map.put(HEIGHT_FILTER_LEVEL2,"二级剔除");  
			map.put(HEIGHT_FILTER_BODY,"只测车体");  
		}	
	}
	public static EnumHeightFilterType enumHeightFilterType = innerObj.new EnumHeightFilterType(); 
	public static String nvramEnumHeightFilterType = "nvram_EnumHeightFilterType";

	public static NvramType bNvramCarWideMaxBoolean = new NvramType("nvram_bNvramCarWideMaxBoolean",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramCarTypeLocalSetting = new NvramType("nvram_bNvramCarTypeLocalSetting",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bNvramLanBanSingleDecision = new NvramType("nvram_bNvramLanBanDecision",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramZJSingleDecision = new NvramType("nvram_bNvramZJDecision",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramZJ_GuaChe_XIAO = new NvramType("nvram_bNvramZJ_GuaChe_XIAO",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType sNvramLocalStoreDataBaseIP = new NvramType("nvram_sNvramLocalStoreDataBaseIP",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramLocalStoreDataBasePORT = new NvramType("nvram_sNvramLocalStoreDataBasePORT",NvramType.Type.STRING_TYPE);
	public final static String DATA_BASE_DEFAULT_USER = "root";
	public static NvramType sNvramLocalStoreDataBaseName = new NvramType("nvram_sNvramLocalStoreDataBaseName",NvramType.Type.STRING_TYPE);
	public final static String DATA_BASE_DEFAULT_PASSWORD = "123456";
	public static NvramType sNvramLocalStoreDataBasePassword = new NvramType("nvram_sNvramLocalStoreDataBasePassword",NvramType.Type.STRING_TYPE);

	public static NvramType sNvramCustomerProtocol_database_ip = new NvramType("nvram_CustomerProtocol_database_ip",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCustomerProtocol_database_port = new NvramType("nvram_CustomerProtocol_database_port",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCustomerProtocol_database_name = new NvramType("nvram_CustomerProtocol_database_name",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCustomerProtocol_database_table = new NvramType("nvram_CustomerProtocol_database_table",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCustomerProtocol_database_user = new NvramType("nvram_CustomerProtocol_database_user",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramCustomerProtocol_database_password = new NvramType("nvram_CustomerProtocol_database_password",NvramType.Type.STRING_TYPE);

	public class DataBaseType extends enumType{		
		public static final String DATABASE_TYPE_NONE = "DATABASE_TYPE_NONE";
		public static final String DATABASE_TYPE_MYSQL = "DATABASE_TYPE_MYSQL";
		public static final String DATABASE_TYPE_SQL_SERVER_2000 = "SQL_SERVER_2000";
		public static final String DATABASE_TYPE_SQL_SERVER_2005 = "SQL_SERVER_2005";

		public DataBaseType() {
			map.put(DATABASE_TYPE_NONE,"不带数据库");  
			map.put(DATABASE_TYPE_MYSQL,"MYSQL");  
			map.put(DATABASE_TYPE_SQL_SERVER_2000,"SQL_SERVER_2000");  
			map.put(DATABASE_TYPE_SQL_SERVER_2005,"SQL_SERVER_2005");  
		}	
	}
	public static DataBaseType protocolDatabaseEnumType = innerObj.new DataBaseType(); 
	public static String nvramProtocolDataBaseType = "nvram_ProtocolDatabaseType";
	public static DataBaseType localStoreDatabaseEnumType = innerObj.new DataBaseType(); 
	public static String nvramLocalStoreDataBaseType = "nvram_LocalSearchDatabaseType";
	
	public class EnumOutputType extends enumType{		
		public static final String OUTPUT_WH = "OUTPUT_WH";
		public static final String OUTPUT_WHL = "OUTPUT_WHL";
		public static final String OUTPUT_FULL = "OUTPUT_FULL";

		public EnumOutputType() {
			map.put(OUTPUT_WH,"宽高输出");  
			map.put(OUTPUT_WHL,"长宽高输出");  
			map.put(OUTPUT_FULL,"所有参数输出");  
		}	
	}
	public static EnumOutputType enumOutputType = innerObj.new EnumOutputType(); 
	public static String nvramOutputType = "nvram_outputEnumType";

	public static NvramType bNvramGuaCheLean = new NvramType("nvram_bNvramGuaCheLean",NvramType.Type.BOOLEAN_TYPE);

	public class EnumWHPositonType extends enumType{		
		public static final String WH_POSITION_AT_LONG_LEFT = "WH_POSITION_AT_LONG_LEFT";
		public static final String WH_POSITION_AT_LONG_RIGHT = "WH_POSITION_AT_LONG_RIGHT";

		public EnumWHPositonType() {
			map.put(WH_POSITION_AT_LONG_LEFT,"位于长龙门左窗口");  
			map.put(WH_POSITION_AT_LONG_RIGHT,"位于长龙门右窗口");  
		}	
	}
	public static EnumWHPositonType enumWHPositonType = innerObj.new EnumWHPositonType(); 
	public static String nvramEnumWHPositonType = "nvram_enumWhPositon";
	
	public class SensorType extends enumType{		
		public static final String UNKNOW = "UNKNOW";
		public static final String LMS1XX = "LMS1XX";
		public static final String RADAR_B = "RADAR_B";
		public static final String LMS511 = "LMS511";
		public static final String TIM551 = "TIM551";
		public static final String LMS400 = "LMS400";
		public static final String VMD500 = "VMD500";
		public static final String VMD500_F = "VMD500_F";
		public static final String RADAR_FS = "RADAR_FS";
		public static final String RADAR_F = "RADAR_F";
		public static final String VMS530 = "VMS530";
		public static final String LIGHT_CURTAIN = "LIGHT_CURTAIN";
		public static final String XZY_2 = "XZY_2";
		public static final String XZY_840 = "XZY_840";
		public static final String XZY_1600 = "XZY_1600";
		public static final String ZM10 = "ZM10";
		public static final String PS_16I = "PS_16I";
		public static final String HK_DS = "HK_DS";
		public static final String DH_IPC = "DH_IPC";
		public static final String HS_LED = "HS_LED";

		public SensorType() {

		}	
	}
	public class SensorTypeForRadarSetting extends SensorType{	
		public SensorTypeForRadarSetting() {
			super();

			map.clear();  

			map.put(UNKNOW,        "     未知类型");  
			map.put(LMS1XX,        "      LMS1XX");  
			map.put(LMS511,        "      LMS511");  
			map.put(RADAR_B,       "     RADAR_B");  
			map.put(LMS400,        "      LMS400");  
			map.put(VMD500,        "      VMD500");  
			map.put(VMD500_F,      "    VMD500_F");  
			map.put(RADAR_FS,      "    RADAR_FS");  
		}	
	}
	public class SensorTypeForLightCurtainSetting extends SensorType{	
		public SensorTypeForLightCurtainSetting() {
			super();
			 
			map.clear();  
			
			map.put(UNKNOW,        "    不带光幕");  
			map.put(LIGHT_CURTAIN, "2米光幕");  
			map.put(XZY_2, 	   	   "两点光幕");  
			map.put(XZY_840, 	   "840mm光幕");  
			map.put(XZY_1600, 	   "1600mm光幕");  
			map.put(ZM10, 		   "ZM10");  
			map.put(PS_16I,        "PS_16I");  
		}	
	}
	public class SensorTypeForCamera extends SensorType{	
		public SensorTypeForCamera() {
			super();
			 
			map.clear();  
			
			map.put(UNKNOW,        "不带摄像头");  
			map.put(HK_DS,		   "HK_DS");  
			map.put(DH_IPC,		   "DH_IPC");
		}	
	}
	public class SensorTypeForLED extends SensorType{	
		public SensorTypeForLED() {
			super();
			 
			map.clear();  
			
			map.put(UNKNOW,        "不带LED");  
			map.put(HS_LED,		   "HS_LED");  
		}	
	}
	public class SensorTypeForVMS530 extends SensorType{	
		public SensorTypeForVMS530() {
			super();
			 
			map.clear();  
			
			map.put(VMS530,        "    VMS530");  
		}	
	}
	public static SensorType sensorType[] =  new SensorType[LMSConstValue.SYSTEM_SENSOR_NUM]; 
	public static String nvramSensorType = "nvram_SensorType";

	public class EnumWHPositionSetType extends enumType{		
		public static final String AUTO_SET = "AUTO_SET";
		public static final String HAND_SET = "HAND_SET";

		public EnumWHPositionSetType() {
			map.put(AUTO_SET,"自动设置");  
			map.put(HAND_SET,"手动设置");  
		}	
	}
	public static EnumWHPositionSetType enumWHPositionSetType =  innerObj.new EnumWHPositionSetType(); 
	public static String nvramEnumWHPositionSetType = "nvram_enumWHPositionSetType";
	
	public static NvramType sNvramStationID = new NvramType("nvram_StationID",NvramType.Type.STRING_TYPE);

	public class EnumThreeDMouseType extends enumType{		
		public static final String MOUSE_AUTO_ROTATE = "MOUSE_AUTO_ROTATE";
		public static final String MOUSE_HAND_ROTATE = "MOUSE_HAND_ROTATE";

		public EnumThreeDMouseType() {
			map.put(MOUSE_AUTO_ROTATE,"自动旋转");  
			map.put(MOUSE_HAND_ROTATE,"手动旋转");  
		}	
	}
	public static EnumThreeDMouseType enumThreeDMouseType =  innerObj.new EnumThreeDMouseType(); 
	public static String nvramEnumThreeDMouseType = "nvram_enumThreeDMouseType";
	
	public class EnumThreeDMaxPointType extends enumType{		
		public static final String NO_CAR_MAX_POINT = "NO_CAR_MAX_POINT";
		public static final String WHOLE_CAR_MAX_POINT = "WHOLE_CAR_MAX_POINT";
		public static final String GUA_CHE_MAX_POINT = "GUA_CHE_MAX_POINT";
		public static final String QIAN_YING_MAX_POINT = "QIAN_YING_MAX_POINT";

		public EnumThreeDMaxPointType() {
			map.put(NO_CAR_MAX_POINT,"不显示最值");  
			map.put(WHOLE_CAR_MAX_POINT,"整车最值(宽高)");  
			map.put(GUA_CHE_MAX_POINT,"挂车最值(宽高)");  
			map.put(QIAN_YING_MAX_POINT,"牵引车最值(宽高)");  
		}	
	}
	public static EnumThreeDMaxPointType enumThreeDMaxPointType =  innerObj.new EnumThreeDMaxPointType(); 
	public static String nvramEnumThreeDMaxPointType = "nvram_enumThreeDMaxPointType";
	
	public static NvramType bNvramThreeDDisplayRadar0 = new NvramType("nvram_bThreeDDisplayRadar0",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDDisplayRadar1 = new NvramType("nvram_bThreeDDisplayRadar1",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDDisplayLightCurtain = new NvramType("nvram_bNvramThreeDDisplayLightCurtain",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDDisplayFilterIn = new NvramType("nvram_bThreeDDisplayFilterIn",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDDisplayFilterOut1 = new NvramType("nvram_bThreeDDisplayFilterOut1",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDDisplayFilterOut2 = new NvramType("nvram_bThreeDDisplayFilterOut2",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType iNvramXRangeMin = new NvramType("nvram_xRangeMin",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramXRangeMax = new NvramType("nvram_xRangeMax",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramYRangeMin = new NvramType("nvram_yRangeMin",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramYRangeMax = new NvramType("nvram_yRangeMax",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramZRangeMin = new NvramType("nvram_zRangeMin",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramZRangeMax = new NvramType("nvram_zRangeMax",NvramType.Type.INTEGER_TYPE);
	public static NvramType bNvramThreeDDisplayMiddle = new NvramType("nvram_bThreeDDisplayMiddle",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bAuthFrameDisplay = new NvramType("nvram_bbAuthFrameDisplay",NvramType.Type.BOOLEAN_TYPE);

	public static boolean bWHPositionLess90;

	public class EnumRecordType extends enumType{		
		public static final String RECORD_TYPE_NO_RECORD = "RECORD_TYPE_NO_RECORD";
		public static final String RECORD_TYPE_CAR_IN_RECORD = "RECORD_TYPE_CAR_IN_RECORD";
		public static final String RECORD_TYPE_CAR_WHOLE_RECORD = "RECORD_TYPE_CAR_WHOLE_RECORD";

		public EnumRecordType() {
			map.put(RECORD_TYPE_NO_RECORD,"无录像");  
			map.put(RECORD_TYPE_CAR_IN_RECORD,"过车录像");  
			map.put(RECORD_TYPE_CAR_WHOLE_RECORD,"全录像");  
		}	
	}
	public static EnumRecordType enumRecordType = innerObj.new EnumRecordType(); 
	public static String nvramRecordType = "nvram_recordType";

	public static int getLeftWindow(int sensorID)
	{
		int window = LMSConstValue.INVALID_X;
		
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
		{
			window = LMSConstValue.iLeftWindow[sensorID];
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			window = LMSConstValue.INVALID_X;
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			window = LMSConstValue.iLeftWindow[sensorID];
		}
		
		return window;
	}
	
	public static int getRightWindow(int sensorID)
	{
		int window = LMSConstValue.INVALID_X;

		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
		{
			window = LMSConstValue.iRightWindow[sensorID];
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			window = LMSConstValue.iRightWindow[sensorID];
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			window = LMSConstValue.INVALID_X;
		}
		
		return window;
	}

	public static int getHeightWindow(int sensorID)
	{
		int window = LMSConstValue.INVALID_X;

		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
		{
			window = LMSConstValue.INVALID_X;
		}
		else 
		{
			window = LMSConstValue.iHeightWindow[sensorID];
		}
		
		return window;
	}
	
	public static int getHeight(int sensorID,int y)
	{
		if(LMSConstValue.yBaseValue[sensorID]>0)
			return LMSConstValue.yBaseValue[sensorID]-y;
		else
			return y-LMSConstValue.yBaseValue[sensorID];			
	}
	
	public static int angleToIndex(int sensorID,float angle)
	{
		return (int) (((angle-LMSConstValue.fAngleLROffset[sensorID])/ParseLMSAckCommand.angleResolution[sensorID])/10);
	}

	public static double indexToAngle(int sensorID,int index)
	{
		return (index*ParseLMSAckCommand.angleResolution[sensorID]+LMSConstValue.fAngleLROffset[sensorID]/10);
	}

	//=================================================================================
	public static final String SETTING_TRANSFER_INTENT = "lmsApp.intent.action.SETTING_TRANSFER_INTENT"; 
	public static final String SETTING_TRANSFER_COMMAND = "SETTING_TRANSFER_COMMAND";	
	public static final String INTENT_EXTRA_SETTING_NVRAM = "SETTING_NVRAM";
	public static final String INTENT_EXTRA_SETTING_VALUE = "SETTING_VALUE";
	public static final String INTENT_EXTRA_SETTING_SAVE = "SETTING_SAVE";
	public static final String INTENT_EXTRA_SETTING_PROPERTY = "SETTING_PROPERTY";

	public static final String INTENT_EXTRA_SOCKET_MSG = "INTENT_EXTRA_SOCKET_MSG";
	public static final String SOCKET_SEND_MSG_INTENT = "SOCKET_SEND_MSG_INTENT"; 
	public static final String SOCKET_RECEIVE_MSG_INTENT = "SOCKET_RECEIVE_MSG_INTENT"; 

	public static boolean bDataReplay = false;
	public static String nvramDataReplay = "nvram_bDataRecord";

	public static String sAuth = "";
	public static String nvramAuth = "nvram_sAuth";

	public static boolean bRadarMonitor[] = new boolean[LMSConstValue.RADAR_SENSOR_NUM]; 
	public static String nvramRadarMonitor = "nvram_bRadarMonitor";

	public static boolean bDetectorMonitor; 
	public static String nvramDetectorMonitor = "nvram_bDetectorMonitor";

	public static boolean bFixLed[] = new boolean[LMSConstValue.SYSTEM_SENSOR_NUM]; 
	public static String nvramFixLed = "nvram_bFixLed";

	public static String sLedMessage; 
	public static String nvramLedMessage = "nvram_sLedMessage";

	public static NvramType sNvramLedDefaultMessage = new NvramType("nvram_sLedDefaultMessage",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramLedCurrentMessage = new NvramType("nvram_sLedCurrentMessage",NvramType.Type.STRING_TYPE);
	public static NvramType bNvramLedLocalSetting = new NvramType("nvram_bNvramLedLocalSetting",NvramType.Type.BOOLEAN_TYPE);

	public static boolean bDoubleEdgeDetect = false;
	public static String nvramDoubleEdgeDetect = "nvram_bDoubleEdgeDetect";

	public static NvramType bNvramCreateThreeDImage = new NvramType("nvram_bCreateThreeDImage",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType iNvramThreeDImageFontSize = new NvramType("nvram_threeDImageFontSize",NvramType.Type.INTEGER_TYPE);

	public static NvramType bNvramThreeDScreenSnap = new NvramType("nvram_bThreeDScreenSnap",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDFrame = new NvramType("nvram_bHideThreeDFrame",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bNvramResultResend = new NvramType("nvram_bNvramResultResend",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bNvramThreeDImageQianYing = new NvramType("nvram_bThreeDImageQianYing",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDImageWithSize = new NvramType("nvram_bThreeDImageWithSize",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDImageWithSizeFrame = new NvramType("nvram_bThreeDImageWithSizeFrame",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bNvramThreeDCarRodeMiddle = new NvramType("nvram_bThreeDCarRodeMiddle",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bNvramCarInOutSignal = new NvramType("nvram_bCarInOutSignal",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bNvramProtocolWithCarOutTime = new NvramType("nvram_bProtocolWithCarOutTime",NvramType.Type.BOOLEAN_TYPE);
			
	public static NvramType bNvramProtocolCarOutTimeWithStr = new NvramType("nvram_bProtocolCarOutTimeWithStr",NvramType.Type.BOOLEAN_TYPE);

//	public static NvramType bNvramThreeDDisplayWidthMax = new NvramType("nvram_bNvramThreeDDisplayWidthMax",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramThreeDDisplayPointMax = new NvramType("nvram_bNvramThreeDDisplayPointMax",NvramType.Type.BOOLEAN_TYPE);

	public static NvramType bNvramBackingCarOutput = new NvramType("nvram_bBackingCarOutput",NvramType.Type.BOOLEAN_TYPE);

	public static boolean bDynamicDetect = true;
	public static String nvramDynamicDetect = "nvram_bDynamicDetect";

	public static String CUSTOMER_GDHS = "GDHS"; //广东泓胜
	public static String CUSTOMER_SHBD = "SHBD"; //上海标定
	public static String CUSTOMER_SDGC = "SDGC"; //山东高程
	public static String CUSTOMER_HNLG = "HNLG";//华工邦元
	public static String CUSTOMER_HGJN = "HGJN";//华工济南
	public static String CUSTOMER_HF_SRF = "HF_SRF"; //合肥思瑞福,合肥正升
	public static String CUSTOMER_JSYY = "JSYY"; //江苏越洋
	public static String CUSTOMER_SZAC = "SZAC"; //深圳安车
	public static String CUSTOMER_FSNH = "FSNH"; //佛山南华	
	public static String CUSTOMER_JSHX = "JSHX"; //江苏华西	
	public static String CUSTOMER_GXCZJ = "GXCZJ"; //广西车之家
	public static String CUSTOMER_JSXSS = "JSXSS"; //江苏新士尚
	public static NvramType sNvramCustomer = new NvramType("nvram_Customer",NvramType.Type.STRING_TYPE);

	public static String strPauseDetect = "暂停检测中";
	public static String strDetecting = "正在检测中";
	
	public static NvramType sNvramUserMsg = new NvramType("nvram_UserMsg",NvramType.Type.STRING_TYPE);

//	public static NvramType sNvramPrintFootStringLine1 = new NvramType("nvram_PrintFootStringLine1",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramPrintFootStringLine2 = new NvramType("nvram_PrintFootStringLine2",NvramType.Type.STRING_TYPE);
	public static NvramType sNvramPrintFootStringLine3 = new NvramType("nvram_PrintFootStringLine3",NvramType.Type.STRING_TYPE);
	public static NvramType bNvramPrintOperatorID = new NvramType("nvram_PrintPrintOperatorID",NvramType.Type.BOOLEAN_TYPE);

	public static int MAIN_IMAGE_NUM = 5;
	public static NvramType sNvramMainImage[] = new NvramType[MAIN_IMAGE_NUM];
	public static NvramType sNvramMainImageTitle[] = new NvramType[MAIN_IMAGE_NUM];
	
	public static int PRINT_IMAGE_NUM = 4;
	public static NvramType sNvramPrintImage[] = new NvramType[PRINT_IMAGE_NUM];
	public static NvramType sNvramPrintImageTitle[] = new NvramType[PRINT_IMAGE_NUM];

	public static NvramType sNvramWebServiceUrl = new NvramType("nvram_WebServiceUrl",NvramType.Type.STRING_TYPE);
	public static NvramType bNvramWebServiceDebugDialog = new NvramType("nvram_bWebServiceDebugDialog",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramOnlyShowNotDetectdCar = new NvramType("nvram_bOnlyShowNotDetectdCar",NvramType.Type.BOOLEAN_TYPE);

	public static int UserMsgFontSize_MIN = 10;
	public static int UserMsgFontSize_MAX = 40;
	public static NvramType iNvramUserMsgFontSize = new NvramType("nvram_UserMsgFontSize",NvramType.Type.INTEGER_TYPE);

	public static NvramType iNvramValidThingWidthMin = new NvramType("nvram_validThingWidthMin",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramValidThingWidthMax = new NvramType("nvram_validThingWidthMax",NvramType.Type.INTEGER_TYPE);

	public static NvramType iNvramValidThingHeightMin = new NvramType("nvram_validThingHeightMin",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramValidThingHeightMax = new NvramType("nvram_validThingHeightMax",NvramType.Type.INTEGER_TYPE);

	public static NvramType iNvramValidThingLengthMin = new NvramType("nvram_validThingLengthMin",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramValidThingLengthMax = new NvramType("nvram_validThingLengthMax",NvramType.Type.INTEGER_TYPE);

	public static NvramType iNvramReplayLine = new NvramType("nvram_replayLine",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramSimulateInteval = new NvramType("nvram_simulateInteval",NvramType.Type.INTEGER_TYPE);

	public static int iConVeyerSpeed;
	public static String nvramConVeyerSpeed = "nvram_conVeyerSpeed";

	public static int iMinPacketWidth;
	public static String nvramMinPacketWidth = "nvram_minPacketWidth";

	public static int iNoReadNoVolumnMachingTimeInteval;
	public static String nvramNoReadNoVolumnMachingTimeInteval = "nvram_noReadNoVolumnMachingTimeInteval";

	//长,宽,高绝对补偿
	public static NvramType iNvramWidthOutputCompensate = new NvramType("nvram_widthOutputCompensate",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramHeightOutputCompensate = new NvramType("nvram_heightOutputCompensate",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramLengthOutputCompensate = new NvramType("nvram_lengthOutputCompensate",NvramType.Type.INTEGER_TYPE);

	public static NvramType bNvramRulerCalibration = new NvramType("nvram_bNvramRulerCalibration",NvramType.Type.BOOLEAN_TYPE);

	//左右角偏移
	public static float[] fAngleLROffset = new float[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramAngleLROffset = "nvram_angleLROffset";
	
	//前后角偏移
	public static float[] fAngleFBOffset = new float[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramAngleFBOffset = "nvram_angleFBOffset";

	//旋转角偏移
	public static float[] fAngleRotateOffset = new float[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramAngleRotateOffset = "nvram_angleRotateOffset";

	public static int iFilterStartAngle[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramFilterStartAngle = "nvram_filterStartAngle";

	public static int iFilterEndAngle[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramFilterEndAngle = "nvram_filterEndAngle";

	public static int iGroundStartAngle[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramGroundStartAngle = "nvram_groundStartAngle";

	public static int iGroundEndAngle[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramGroundEndAngle = "nvram_groundEndAngle";

	//窗口参数
	public static int iLeftWindow[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramLeftWindow = "nvram_leftwindow";

	public static int iRightWindow[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramRightWindow = "nvram_rightwindow";

	public static int iHeightWindow[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramHeightWindow = "nvram_heightwindow";

	public static NvramType iNvramLRDistance = new NvramType("nvram_lrdistance",NvramType.Type.INTEGER_TYPE);

	public static NvramType iNvramLongWH0istance = new NvramType("nvram_longWH0istance",NvramType.Type.INTEGER_TYPE);

	public static NvramType iNvramLWDistance = new NvramType("nvram_lwdistance",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramLWDistance2 = new NvramType("nvram_lwdistance2",NvramType.Type.INTEGER_TYPE);
	public static NvramType iNvramLightCurtianLongDistance = new NvramType("nvram_LightCurtianLongDistance",NvramType.Type.INTEGER_TYPE);

    public static final int MAX_CAR_ROAD_NUM = 3; //最多车道数  
	public static String nvramCarRoadNum = "nvram_carRoadNum";
	public static int iCarRoadNum; //车道数  
	
	public static final int LIGHT_CURTAIN_LIGHT_NUM = 96;
	public static final int ZM10_PULSE_NUM = 16;
	public static int yLPStart = 20;
	public static int yLPStep = 20;
	public static int iLPNum = 0;
	public static int yXiaoStart = 900;
	public static int yXiaoEnd = 1450;
	public static void setLPNum(int sensorID)
	{
		if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LIGHT_CURTAIN))
		{
			LMSConstValue.iLPNum = 96;
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.XZY_2))
		{
			LMSConstValue.iLPNum = 8; //虽然是2点光栅，但是其协议里跑了8点
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.XZY_840))
		{
			LMSConstValue.iLPNum = 14;
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.XZY_1600))
		{
			LMSConstValue.iLPNum = 80;
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.ZM10)
			||LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.PS_16I))
		{
			LMSConstValue.iLPNum = 16;
		}	
	}
	
	//==============================================================================
	public static final int LED_SENSOR_NUM = 10;
	public static int iLedSensorNum;  
	public static final int LED_ID_START = LMSConstValue.CAMERA_ID_START+LMSConstValue.CAMERA_SENSOR_NUM;

	public static final int CAMERA_SENSOR_NUM = 10;
	public static int iCameraSensorNum;  
	public static final int CAMERA_ID_START = LMSConstValue.LIGHT_CURTAIN_ID_START+LMSConstValue.LIGHT_CURTAIN_SENSOR_NUM;
	public static final int FRONT_CAMERA_ID = CAMERA_ID_START;
	public static final int BACK_CAMERA_ID = CAMERA_ID_START+1;
	
	public static final int LIGHT_CURTAIN_SENSOR_NUM = 10;
	public static int iLightCurtainSensorNum;  
	public static final int LIGHT_CURTAIN_ID_START = LMSConstValue.RADAR_SENSOR_NUM;

	//开始架构没设计好,10以上的参数已经给其他用了(配置文件);后面雷达如不够用,需另建一组
	public static final int RADAR_SENSOR_NUM = 10;  
	public static int iRadarSensorNum;  
	public static final int SYSTEM_SENSOR_NUM = RADAR_SENSOR_NUM+LIGHT_CURTAIN_SENSOR_NUM+CAMERA_SENSOR_NUM+LED_SENSOR_NUM;  
	public static int iSystemSensorNum;  

	//==============================================================================
    public static String[] SENSOR_IP = new String[LMSConstValue.SYSTEM_SENSOR_NUM]; 
	public static String nvramSensorIP = "LMS_IP";

	//----------------------------------------------------------------------------------
    public static int[] SENSOR_PORT = new int[LMSConstValue.SYSTEM_SENSOR_NUM]; 
	public static String nvramSensorPort = "nvram_sensorPort";

	public static boolean bPortHasData[] = new boolean[LMSConstValue.SYSTEM_SENSOR_NUM];
	public static String nvramPortHasData = "nvram_bPortHasData";
	
	public static boolean bPortHasValidData[] = new boolean[LMSConstValue.SYSTEM_SENSOR_NUM];
	public static String nvramPortHasValidData = "nvram_bPortHasValidData";

	public static boolean bSensorPortConnected[] = new boolean[LMSConstValue.SYSTEM_SENSOR_NUM] ;
	public static String nvramSensorPortConnected = "nvram_bSensorPortConnected";

	//----------------------------------------------------------------------------------
	public static NvramType iNvramCameraCaptureDelaySecond[] = new NvramType[LMSConstValue.SYSTEM_SENSOR_NUM];
	public static NvramType sNvramCameraUserName[] = new NvramType[LMSConstValue.SYSTEM_SENSOR_NUM];
	public static NvramType sNvramCameraPassword[] = new NvramType[LMSConstValue.SYSTEM_SENSOR_NUM];

	public static boolean bLRTurn[] = new boolean[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramLRturn = "nvram_lrturn";

	public static boolean bUpDownTurn[] = new boolean[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramUpDownTurn = "nvram_upDownTurn";

	/*
	public static boolean bRotateTurn[] = new boolean[LMSConstValue.LMS_SENSOR_NUM];
	public static String nvramRotateTurn = "nvram_RotateTurn";
	*/
	
	public static boolean bAngleDisplay[] = new boolean[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramBoolAngleDisplay = "nvram_boolAngleDisplay";

	public static int iAngleDisplay[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramAngleDisplay = "nvram_angleDisplay";

	public static int iFrontEdgeWindow[] = new int[LMSConstValue.RADAR_SENSOR_NUM];;
	public static String nvramFrontEdgeWindow = "nvram_frontEdgeWindow";

	public static int iLREdgeWindow[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramLREdgeWindow = "nvram_lrEdgeWindow";
	
	public static final int MOVE_STEP = 8;
	public static int iXMove[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramXMove = "nvram_xMove";

	public static int iYMove[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String nvramYMove = "nvram_yMove";
	
	public static int yBaseValue[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; 
	public static String nvramYBaseValue = "nvram_yBaseValue";

	public static int baseBeginIndex[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; 
	public static String nvramBaseBeginIndex = "nvram_baseBeginIndex";

	public static int baseEndIndex[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; 
	public static String nvramBaseEndIndex = "nvram_baseEndIndex";

	//=================================================================================
	public static int longWH0Distance;

	//=================================================================================
	public static final String SETTING_NOTIFY_INTENT = "lmsApp.intent.action.SETTING_NOTIFY_INTENT"; 
	public static final String INTENT_EXTRA_NOTIFY_TITLE = "NOTIFY_TITLE";
	public static final String INTENT_EXTRA_NOTIFY_MSG = "NOTIFY_MSG";

	//=================================================================================
	public final static int SERVICE_OK = 500;

//	public static final String LMS_DATA_INTENT = "lmsApp.intent.action.LMS_DATA_INTENT"; 
	public static final String DATA_MSG_INTENT = "lmsApp.intent.action.DATA_MSG_INTENT"; 
	public static final String CONFIG_MSG_INTENT = "lmsApp.intent.action.CONFIG_MSG_INTENT"; 

	//=================================================================================
	public static final String SOCKET_CONNECT_STATE_INTENT = "lmsApp.intent.action.SOCKET_CONNECT_STATE_INTENT"; 
	public static final String INTENT_EXTRA_SOCKET_CONNECT_STATE = "socket_connect_state"; 
	public static final String INTENT_EXTRA_SOCKET_CONNECT_PORT = "socket_connect_port"; 

	public static final String BOARD_TYPE_INTENT = "lmsApp.intent.action.BOARD_TYPE_INTENT"; 

	public static final String STATE_SOCKET_CLIENT_DISCONNECT_INTENT = "lmsApp.intent.action.STATE_SOCKET_CLIENT_DISCONNECT_INTENT"; 

	//检测头参数
	public static final String LMS_PARAMETER_STRING_INTENT = "lmsApp.intent.action.LMS_PARAMETER_STRING_INTENT"; 
	public static final String INTENT_EXTRA_LMS_PARAMETER = "lms_parameter"; 

	public static final String HTTP_STATE_STRING_INTENT = "HTTP_STATE_STRING_INTENT"; 
	public static final String INTENT_EXTRA_HTTP_STATE = "http_state"; 

	public static int ANTI_LEVEL = 4;  
	public static final String ANTI_LEVEL_INTENT = "lmsApp.intent.action.ANTI_LEVEL_INTENT"; 
	public static int iAntiLevel[][] = new int[LMSConstValue.RADAR_SENSOR_NUM][ANTI_LEVEL]; 

	public static final String CAR_ROAD_WIDHT_CHANGE_INTENT = "lmsApp.intent.action.CAR_ROAD_WIDHT_CHANGE_INTENT"; 
	public static int iCarRoadWidth[] = new int[MAX_CAR_ROAD_NUM];

	public static final String CAR_ROAD_OUTPUT_TURN_INTENT = "lmsApp.intent.action.CAR_ROAD_OUTPUT_TURN_INTENT"; 
	public static boolean bCarRoadOutputTurn;
	 
	//触发取基准值
	public static final String TRIG_GET_BASE_DATA_INTENT = "TRIG_GET_BASE_DATA_INTENT"; 

	//发送基准值参数
	public static final String SERVER_SEND_BASE_DATA_INTENT = "SERVER_SEND_BASE_DATA_INTENT"; 

	//版本信息
//	public static final String GET_SERVER_VERNO_INTENT = "lmsApp.intent.action.GET_SERVER_VERNO_INTENT"; 
	public static final String GET_SERVER_VERNO_COMMAND = "GET_SERVER_VERNO_COMMAND"; 
	public static final String GET_SERVER_VERNO_COMMAND_TO_INTENT = "lmsApp.intent.action.GET_SERVER_VERNO_COMMAND_TO_INTENT"; 

	
    //Ben 显示图片
	public static final String SHOW_PICS = "showPics";
	//车辆检测结果
	public static final String strSimulateTime = "2015-11-05 14:58:27,000";
	
	public static final int MAX_Z_NUM = 11;
	public static final String CAR_STATE_CHANGE_INTENT = "CAR_STATE_CHANGE_INTENT"; 
	public static final String CAR_STATE_CHANGE_COMMAND_TO_INTENT = "CAR_STATE_CHANGE_COMMAND"; 
	public static final String INTENT_EXTRA_CAR_STATE = "carState"; 
	public static final String INTENT_EXTRA_CAR_NUM = "carNum"; 
	public static final String INTENT_EXTRA_CAR_WIDTH = "carWidth"; 
	public static final String INTENT_EXTRA_CAR_HEIGHT = "carHeight"; 
	public static final String INTENT_EXTRA_CAR_LENGTH = "carLength"; 
	public static final String INTENT_EXTRA_CAR_WIDTH_WITH_REAR_VIEW_MIRROR = "carWidthWithRearViewMirror"; 
	public static final String INTENT_EXTRA_CAR_HEIGHT_WITH_REAR_VIEW_MIRROR = "carHeightRearViewMirror"; 
	public static final String INTENT_EXTRA_CAR_LENGTH_WITH_REAR_VIEW_MIRROR = "carLengthRearViewMirror"; 
	public static final String INTENT_EXTRA_CAR_WIDTH_WITH_FRONT_MIRROR = "carWidthWithFrontMirror"; 
	public static final String INTENT_EXTRA_CAR_HEIGHT_WITH_FRONT_MIRROR = "carHeightWithFrontMirror"; 
	public static final String INTENT_EXTRA_CAR_LENGTH_WITH_FRONT_MIRROR = "carLengthWithFrontMirror"; 
	public static final String INTENT_EXTRA_CAR_LB_HEIGHT = "carLBHeight"; 
	public static final String INTENT_EXTRA_CAR_Z_NUM = "carZNum"; 
	public static final String INTENT_EXTRA_CAR_ZD = "carZD"; 
	public static final String INTENT_EXTRA_CAR_ZJ = "carZJ"; 
	public static final String INTENT_EXTRA_CAR_TIME = "carTime"; 
	//-----------------------------
	public static final String INTENT_EXTRA_CAR_G_LENGTH = "carGLength"; 
	public static final String INTENT_EXTRA_CAR_G_WIDTH = "carGWidth"; 
	public static final String INTENT_EXTRA_CAR_G_HEIGHT = "carGHeight"; 
	public static final String INTENT_EXTRA_CAR_G_HEIGHT_LEAN_ORI = "carGHeightLeanOri"; 
	public static final String INTENT_EXTRA_CAR_G_Z_NUM = "carGZNum"; 
	public static final String INTENT_EXTRA_CAR_G_ZD = "carGZD"; 
	public static final String INTENT_EXTRA_CAR_G_ZJ = "carGZJ"; 
	//-----------------------------
	public static final String INTENT_EXTRA_CAR_Q_LENGTH = "carQLength"; 
	public static final String INTENT_EXTRA_CAR_Q_WIDTH = "carQWidth"; 
	public static final String INTENT_EXTRA_CAR_Q_HEIGHT = "carQHeight"; 
	public static final String INTENT_EXTRA_CAR_Q_Z_NUM = "carQZNum"; 
	public static final String INTENT_EXTRA_CAR_Q_ZD = "carQZD"; 
	public static final String INTENT_EXTRA_CAR_Q_ZJ = "carQZJ"; 
	//-----------------------------
	public static final String INTENT_EXTRA_CAR_XZJ = "carXZJ"; 
	//-----------------------------
	public static final String INTENT_EXTRA_CAR_IN_TIME = "carInTime"; 
	public static final String INTENT_EXTRA_CAR_IN_TIME_L = "carInTimeL"; 
	public static final String INTENT_EXTRA_CAR_OUT_TIME = "carOutTime"; 
	public static final String INTENT_EXTRA_CAR_OUT_TIME_L = "carOutTimeL"; 
	public static final String INTENT_EXTRA_ANTI_LEVEL = "antiLevel"; 
	public static final String INTENT_EXTRA_ERROR_CODE = "errorCode"; 
	public static final String INTENT_EXTRA_BAR_CODE = "barCode";
	
	public static final String INTENT_EXTRA_BOX_VOLUMN = "boxVolumn"; 
	public static final String INTENT_EXTRA_REAL_VOLUMN = "realVolumn"; 
	public static final String INTENT_EXTRA_ROTATE_ANGLE = "rotateAngle"; 
	public static final String INTENT_EXTRA_ROTATE_CENTER_X = "rotateCenterX"; 
	public static final String INTENT_EXTRA_ROTATE_CENTER_Y = "rotateCenterY"; 
	public static final String INTENT_EXTRA_ROTATE_WIDTH = "rotateWidth"; 
	public static final String INTENT_EXTRA_ROTATE_LENGTH = "rotateLength"; 

	//==============================================================================
	public static final String CAR_OUT_LOG_INTENT = "CAR_STATE_CHANGE_INTENT"; 
	public static final String INTENT_EXTRA_CAR_OUT_LOG_STR = "INTENT_EXTRA_CAR_OUT_LOG_STR"; 

	public static boolean bNormalMode = true;

	public static int last_width = 0;
	public static int last_height = 0;
	public static int last_length = 0;
	public static int last_guache_width = 0;
	public static int last_guache_height = 0;
	public static int last_guache_length = 0;
	public static int last_carTime = 0;
	public static String last_carOutTime = null;
	
	//=====================================================================
	public static final String INTENT_EXTRA_COMMAND_TYPE = "commandType"; 
	public static final String INTENT_EXTRA_SENSOR_ID = "SensorID"; 

	//==================================================================
	public final static int ADB_PORT = 5555;
	public static int LMS_AP_RESULT_PORT;
	public static String nvramResultPort = "nvram_resultPort";
	public static int LMS_AP_USER_PORT;
	public static String nvramUserCmdPort = "nvram_userCmdPort";
	public static int LMS_AP_FSRL_PROTOCOL_PORT;
	public static String nvramFSRLProtocolPort = "nvram_FSRLProtocolPort";
	public static int LMS_AP_XML_PROTOCOL_PORT;
	public static String nvramXMLProtocolPort = "nvram_XMLProtocolPort";
	public final static int LMS_AP_UPDATE_PORT = 2000;

	//==================================================================
	//调试参数
	public static String LmsParameterString;

	//==================================================================	


	//基准值
	public static final int LMS_POINT_NUM = 721; //274*4  
//	public static int LMS_PLANE_NUM = 4; //274*4  
	
    public static int LMS_WH_SENSOR_NUM = 2;  
    public static int LMS_L_SENSOR_NUM = 2;  
	public enum enumRadarFunctionType{
		WH_SENSOR_ID,
		LONG_SENSOR_ID,
	}
	public static enumRadarFunctionType radarFunctionType[] = new enumRadarFunctionType[LMSConstValue.RADAR_SENSOR_NUM]; 
	public static int getLongSensorID()
	{
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
		{
			if(LMSConstValue.radarFunctionType[i] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
				return i;
		}
		
		return -1;
	}
	
	public static boolean bGetGroundFlat = false;
	public static boolean[] bBaseValid = new boolean[LMSConstValue.RADAR_SENSOR_NUM];
	public static boolean[] bInitial = new boolean[LMSConstValue.RADAR_SENSOR_NUM];

    public static final String LOCAL_IP = "127.0.0.1";
    public static String socketIP = "127.0.0.1";
    public static void setSocketIP(String IP)
    {
    	LMSConstValue.socketIP = IP;
    }
    
    public static String getLocalHostName() {  
        String hostName;  
        try {  
            InetAddress addr = InetAddress.getLocalHost();  
            hostName = addr.getHostName();  
        } catch (Exception ex) {  
            hostName = "";  
        }  
        return hostName;  
    }  
  
	public static String[] getAllLocalHostIP() {  
	    String[] ret = null;  
	    try {  
	        String hostName = getLocalHostName();  
	        if (hostName.length() > 0) {  
	            InetAddress[] addrs = InetAddress.getAllByName(hostName);  
	            if (addrs.length > 0) {  
	                ret = new String[addrs.length];  
	                for (int i = 0; i < addrs.length; i++) {  
	                    ret[i] = addrs[i].getHostAddress();  
	                    LMSLog.d(DEBUG_TAG,"IP ADDRESS="+ret[i]);
	                }  
	            }  
	        }  
        } catch (Exception ex) {  
            ret = null;  
        }  
        return ret;  
    }  

    //车辆状态
	public static enumCarState[] carState = new enumCarState[MAX_CAR_ROAD_NUM];
	public enum enumCarState{
		NOT_CAR_DETECT,
		INVALID_THING,
		INVALID_THING_TO_NO_CAR,
		NO_CAR,
		CAR_COMMING, //刚进车
		CAR_IN,	//车辆已经进入
		CAR_OUTING, //刚出车
		CAR_RESULT, //结果数据
		CAR_REGENATE_RESULT, //重新生成结果数据
		LIGHTCURTAIN_REGENATE, 
		ANTI_LEVEL_CHANGE,
		VOLUMN_RESULT,
    } 	

	//系统状态
	public enum enumDeviceStateType{
		SERIAL_PORT_OK,
		TRANSFER_SOCKET_OK,
		TRANSFER_SOCKET_ERROR,
		INVALID_SENSOR,
    } 	
	public static final String DEVICE_STATE_INTENT = "lmsApp.intent.action.DEVICE_STATE_INTENT"; 
	public static final String INTENT_EXTRA_DEVICE_STATE_TYPE = "deviceStateType"; 

	public static final String SERVER_SYSTEM_STATE_STRING_INTENT = "lmsApp.intent.action.SERVER_SYSTEM_STATE_STRING_INTENT"; 
	public static final String INTENT_EXTRA_SYSTEM_STATE_STRING = "systemStateString"; 
	public static String SystemStateString[] = new String[LMSConstValue.SYSTEM_SENSOR_NUM]; 

	public static long lLastTimeOfReceived_fitting[] = new long[LMSConstValue.SYSTEM_SENSOR_NUM];
	public static String sLastTimeOfReceived[] = new String[LMSConstValue.SYSTEM_SENSOR_NUM];

	public static int carStateCarNum;

	//================================================================================
	//算法
    public static int MAX_CONTINUOUS_BLOCK = 400;  

	public static int physicStartPoint[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; //角度应除以10000
	public static int physicEndPoint[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; //角度应除以10000
	public static int filterStartPoint[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; //角度应除以10000
	public static int filterEndPoint[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; //角度应除以10000

	//=====================================================================
	public final static int LMS_WORKFLOW_SOPAS_ERROR_ACK = 1100;
	
	public final static int LMS_WORKFLOW_POLLING_ONE_ACK = 1102;
	public final static int LMS_WORKFLOW_PERMANT_SEND_ACK = 1103;
	public final static int LMS_WORKFLOW_ACTUAL_OUTPUT_RANGE_ACK = 1104;
	public final static int LMS_WORKFLOW_FREQ_AND_ANGULAR_RESOLUTION_ACK = 1105;
	public final static int LMS_WORKFLOW_LOGIN = 1106;
	public final static int LMS_WORKFLOW_DATA_SENT = 1107;
	public final static int LMS_WORKFLOW_SET_OUTPUT_ANGLE_ACK = 1108;
	public final static int LMS_WORKFLOW_TIMESTAMP_ACK = 1109;

	//=====================================================================
	public enum enumBoardType{
		SERVER_BOARD,
		CUSTOM_BOARD,
		SIMULATE_FILE_BOARD,
    } 	
//	public static enumBoardType boardType = enumBoardType.SIMULATE_SERVER_BOARD;
	public static enumBoardType boardType = enumBoardType.SERVER_BOARD;
	public static enumBoardType getEnumBoardTypeFromOridal(int ordinal)
	{
		for(enumBoardType e : enumBoardType.values()) { 
			if(e.ordinal() == ordinal)
				return e;
		}
		
		return enumBoardType.SERVER_BOARD;
	}
	public static enumBoardType getEnumBoardTypeFromString(String str)
	{
		for(enumBoardType e : enumBoardType.values()) { 
			if(e.toString().equals(str))
				return e;
		}
		
		return enumBoardType.SERVER_BOARD;
	}

    public static boolean bTransfer;

	public static final int HISTORY_DATA_NUM = 10;	
	public static final String FILE_NAME = "LMS_DATA";
	public static File fileDir;

	//==================================================================	
	public static int LMS_PACKET_NUM_MAX = 1000;
	public static int LMS_PACKET_NUM_MAX_SOCKET = 5000;

	//=====================================================================
	public final static int JUMP_POINT_Y = 100;
	public final static int GET_BASE_HORIZITION_SPACE = 150;

	//=====================================================================
	public static int iDTBC_W = 0;
	public static int iDTBC_H = 0;
	public static int iDTBC_L = 0;
	public static int iDTBC_GW = 0;
	public static int iDTBC_GH = 0;
	public static int iDTBC_GL = 0;
	public static int iDTBC_QW = 0;
	public static int iDTBC_QH = 0;
	public static int iDTBC_QL = 0;

	public static String sDTBC_Set_W = "DTBC_SET_W:";
	public static String sDTBC_Set_H = "DTBC_SET_H:";
	public static String sDTBC_Set_L = "DTBC_SET_L:";
	public static String sDTBC_Set_GW = "DTBC_SET_GW:";
	public static String sDTBC_Set_GH = "DTBC_SET_GH:";
	public static String sDTBC_Set_GL = "DTBC_SET_GL:";
	public static String sDTBC_Set_QW = "DTBC_SET_QW:";
	public static String sDTBC_Set_QH = "DTBC_SET_QH:";
	public static String sDTBC_Set_QL = "DTBC_SET_QL:";
	
	public static int iGetDTBC_Adjust(int iWs,int iWa)
	{ 
		/*
		//iWs --- 补偿值(或者标称值)
		//iWa --- 测量值
		//iWx --- 输出
		int iWx = 0;
		if(Math.abs(iWs)<200) //合理的补偿范围
		{
			iWx = iWa+iWs;
		}
		else if(iWs>1500&&iWa>=0.97*iWs&&iWa<=1.03*iWs) //合理的标称值
		{
			iWx = (int) (0.3*(iWa-iWs)+iWs);
		}
		else //标称值也不合理,使用测量值
		{
			iWx = iWa;
		}
		
		return iWx;
		/*/
		return iWa;
	}
	//=====================================================================
	public static LMSEventManager lmsEventManager;
	
	//模拟测试过,721个点的情况下,44118行就OutOfMemoryError
	//3D的情况下，10000就会溢出
	public static final int MAX_BUBBLE_LINE = 5000;
	public static final int MAX_LIGHT_CURTAIN_LINE = 10000;
	
    public static long recordFileLineNum[] = new long[LMSConstValue.RADAR_SENSOR_NUM];

	//===================================================
	public static int PS_MODULE_NUM = 1;  
	
	public static int[] COS_STEP = new int[LMSConstValue.RADAR_SENSOR_NUM];	
	public final static int MAX_ANGLE_OFFSET =1000;// = new int[LMSConstValue.LMS_SENSOR_NUM];
				
	public static double getCos(int sensorID,int index)
	{
		return -Math.cos((index*ParseLMSAckCommand.angleResolution[sensorID]*10)*Math.PI/1800);
	}
	
	public static double getSin(int sensorID,int index)
	{
		return Math.sin((index*ParseLMSAckCommand.angleResolution[sensorID]*10)*Math.PI/1800);
	}
	
	public static double getTan(int sensorID,int index)
	{
		return Math.tan((index*ParseLMSAckCommand.angleResolution[sensorID]*10)*Math.PI/1800);
	}

	public static int rFBOffsetAdjust(int sensorID,int angle,int r)
	{
		/*		
		double X,Y,X1,Y1;
		int r_resize;
		boolean bLog = false;
		
		if(sensorID ==2)
			bLog = true;
		
		if(bLog == true)
			LMSLog.d(DEBUG_TAG+sensorID,"rrrrrrrrrr angle="+angle+" before="+r);

		//先取得原来的Z
		X = r*Math.cos(angle*ParseLMSAckCommand.angleResolution[sensorID]*Math.PI/180);
		Y = r*Math.sin(angle*ParseLMSAckCommand.angleResolution[sensorID]*Math.PI/180);

		X1=X*Math.cos(iAngleFBOffset[sensorID]*ParseLMSAckCommand.angleResolution[sensorID]*Math.PI/180)+Y*Math.sin(iAngleFBOffset[sensorID]*ParseLMSAckCommand.angleResolution[sensorID]*Math.PI/180);
		Y1=-X*Math.sin(iAngleFBOffset[sensorID]*ParseLMSAckCommand.angleResolution[sensorID]*Math.PI/180)+Y*Math.cos(iAngleFBOffset[sensorID]*ParseLMSAckCommand.angleResolution[sensorID]*Math.PI/180);

		r_resize = (int) Math.sqrt(X1*X1+Y1*Y1);

		if(bLog == true)
			LMSLog.d(DEBUG_TAG+sensorID,"rrrrrrrrrr after="+r_resize);

		return r_resize;
		//*/
		return r;
	}

	public static double getCosLR(int sensorID,int index)
	{
		return -Math.cos((index*ParseLMSAckCommand.angleResolution[sensorID]*10+fAngleLROffset[sensorID])*Math.PI/1800);
	}
	
	public static double getSinLR(int sensorID,int index)
	{
		return Math.sin((index*ParseLMSAckCommand.angleResolution[sensorID]*10+fAngleLROffset[sensorID])*Math.PI/1800);
	}

	public static double getTanLR(int sensorID,int index)
	{
		return Math.tan((index*ParseLMSAckCommand.angleResolution[sensorID]*10+fAngleLROffset[sensorID])*Math.PI/1800);
	}

	 public static List<String> getWindowsMACAddress() { 
		 List<String> macList = new ArrayList<String>();    
		 
		 LMSLog.d(DEBUG_TAG,"======="+System.getProperties().getProperty("os.name"));   
		 if(System.getProperties().getProperty("os.name").contains("Windows"))
		 {
			 BufferedReader bufferedReader = null;       
			 Process process = null;       
			 try {       
			     process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息      
			     bufferedReader = new BufferedReader(new InputStreamReader(process       
			         .getInputStream()));       
				 String line = null;       
				 int index = -1;    
				 
			     while ((line = bufferedReader.readLine()) != null) {   
			    	 if (line.toLowerCase().indexOf("physical address") >= 0||line.toLowerCase().indexOf("物理地址") >= 0) { // 寻找标示字符串[physical address]    
			    		 index = line.indexOf(":");// 寻找":"的位置      
			    		 LMSLog.d(DEBUG_TAG,"line="+line);   

			    		 if (index>=0) {       
			    			 macList.add(line.substring(index + 1).trim());//  取出mac地址并去除2边空格      
			    		 }       
			         }  
			     }
			 } catch (IOException e) {       
				 LMSLog.exception(e);
			 } finally {       
			     try {       
			         if (bufferedReader != null) {       
			             bufferedReader.close();       
			         }       
			     } catch (IOException e) {       
			         LMSLog.exception(e);	         
			      }       
			       bufferedReader = null;       
			       process = null;       
			 }        
		 }
		   
		 return macList;  
	 }      
	 
	 public static String getSensorType(int sensorID)
	 {
		 if(LMSConstValue.sensorType[sensorID] == null)
		 {			
			 return SensorType.UNKNOW;
		 }
		 else
		 {
			 return LMSConstValue.sensorType[sensorID].key;
		 }
	 }
	 
	 //==================================================================	
	 public static void sendPortHasData(int sensorID,boolean bHasData)
	 {
		 if(LMSConstValue.boardType != enumBoardType.SIMULATE_FILE_BOARD)
		 {
			 HashMap<String, Comparable> eventExtraInit = new HashMap<String, Comparable>();
	
			 LMSConstValue.bPortHasData[sensorID] = bHasData;
		 
			 eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramPortHasData);
			 eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bPortHasData[sensorID]);
			 eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
			 eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			 LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtraInit);    					
		 }
	 }
	 
	 public static void sendPortHasValidData(int sensorID,boolean bHasValidData)
	 {
		HashMap<String, Comparable> eventExtraInit = new HashMap<String, Comparable>();
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);

		LMSConstValue.bPortHasValidData[sensorID] = bHasValidData;
	 
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramPortHasValidData);
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bPortHasValidData[sensorID]);
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);

 		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtraInit);    					
	 }
	 
	 public static void sendPortConnected(int sensorID,boolean connected)
	 {
		HashMap<String, Comparable> eventExtraInit = new HashMap<String, Comparable>();
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);

		LMSConstValue.bSensorPortConnected[sensorID] = connected;
	 
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramSensorPortConnected);
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bSensorPortConnected[sensorID]);
		eventExtraInit.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);

 		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtraInit);    					
	 }
	 
	 //==================================================================	
	 public static boolean isValidIP(String ipAddress)   
	 {   
		 if(ipAddress != null)
		 {
			 String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";    

			 Pattern pattern = Pattern.compile(ip);    
			 Matcher matcher = pattern.matcher(ipAddress);    
   
			 return matcher.matches();    
		 }
		 else
		 {
			 return false;
		 }
	 } 

	 public static boolean isValidCOM(String address)   
	 {   
		 if(address != null && address.length() >= 3)
		 {
			 if(address.substring(0,3).toUpperCase().equals("COM"))
				 return true;
			 else
				 return false;
		 }
		 else
		 {
			 return false;
		 }
	 } 


	//==================================================================	
	public static int JD_HTTP_JASON_PACKET_MAX = 3000;

	
	//==================================================================	
	public static String strFSRLGetLength = "<AskCommand><ReadOneDataValue>Length</ReadOneDataValue></AskCommand>";
	public static String strFSRLGetWidth = "<AskCommand><ReadOneDataValue>Width</ReadOneDataValue></AskCommand>";

	public static boolean bProtocolOld_ZM10 = false;
	public static String nvramProtocolOld_ZM10 = "nvram_bProtocolOld_ZM10";

	public static boolean bProtocolOld_radarB = false;
	public static String nvramProtocolOld_radarB = "nvram_bProtocolOld_radarB";

	public static String getSensorName(int sensorID)
	{
		String str = null;
		
		if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID)
		{
			str = "宽高雷达"+(sensorID+1);
		}
		else if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
		{
			str = "长雷达";
		}
		
		return str;
	}
    
    public static String lTimeToString(long lTime)
    {
 		SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.SDF);
		String str = sdf.format(new Date(lTime)); 
		
		return str;
    }
    
    public static long stringToLongTime(String sTime,String SDF)
    {
		SimpleDateFormat sdf = new SimpleDateFormat(SDF);  

		long lTimeOfReceived = 0;
		try {
			Date d = sdf.parse(sTime);
			lTimeOfReceived = d.getTime();	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(-1,e);
		}
		
		return lTimeOfReceived;
    }
    
    public static String getCurrentStrTime()
    {
		String strTime;

    	Date date = null;
 		SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.SDF);
		Date dateNow = new Date();
		
		strTime = sdf.format(dateNow);
		
		return strTime;
    }
    
    public static long getCurrentLTime()
    {
		String strTime;
		long lTime;

    	Date date = null;
 		SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.SDF);
		Date dateNow = new Date();
		
		strTime = sdf.format(dateNow);
		lTime = LMSConstValue.stringToLongTime(strTime, LMSConstValue.SDF);      			
		
		return lTime;
    }
    
    public static int iPasswordKey = 0;
    public static String sPasswordTmpAll;
    public static String sPasswordTmpAllMD5Str;
	public static NvramType bNvramOriginalPasswordView = new NvramType("nvram_bNvramOriginalPasswordView",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramNewPasswordView = new NvramType("nvram_bNvramNewPasswordView",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType sNvramSettingPassword = new NvramType("nvram_SettingPassword",NvramType.Type.STRING_TYPE);
    public static int iGenTmpPasswordAll(int _iPasswordKey)
	{
		return 3*((_iPasswordKey/3)+10567383);
	}	


    public static void genPasswordKey()
	{
		int OFFSET;
		
		java.util.Random random=new java.util.Random();// 定义随机类

		OFFSET = 99999999;
		iPasswordKey = random.nextInt(OFFSET);
		//-----------------------------------------------------------
		int iPasswordTmpAll = iGenTmpPasswordAll(iPasswordKey);
		sPasswordTmpAll = String.valueOf(iPasswordTmpAll);
		sPasswordTmpAllMD5Str = Md5.convertMD5(sPasswordTmpAll);  	
	}
}

