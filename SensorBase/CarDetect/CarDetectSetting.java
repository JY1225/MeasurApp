package CarDetect;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import layer.algorithmLayer.Contour;
import layer.algorithmLayer.ParseLMSAckCommand;
import layer.algorithmLayer.SingleBubble;
import layer.dataLayer.DataLayerDataParseRunnable;
import layer.dataLayer.LMSTelegram;
import layer.physicLayer.PhysicLayerModbus;
import layer.physicLayer.PhysicLayerPacket;
import layer.physicLayer.PhysicLayerSerialPort;
import layer.physicLayer.PhysicLayerSimulate;
import layer.physicLayer.PhysicLayerSocket;
import AppBase.appBase.AppConst;
import AppBase.appBase.CarTypeAdapter;
import CarAppAlgorithm.LightCurtainAlgorithm;
import CarAppAlgorithm.WidthHeightDetectRunnable;
import CustomerProtocol.DetectionVehicle;
import SensorBase.LMSConstValue;
import SensorBase.LMSConstValue.EnumBackgroundColor;
import SensorBase.LMSConstValue.SensorType;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import SensorBase.LMSToken;
import SensorBase.NvramType;
import SensorBase.LMSConstValue.enumBoardType;
import SensorBase.LMSConstValue.enumDeviceStateType;
import lmsBase.LMSProductInfo;
import lmsBase.Md5;
import lmsBase.Nvram;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
/**
 * 
 * lidar通讯
 *
 */
public class CarDetectSetting {
	private final static String DEBUG_TAG="CarDetectSetting";

	public static CarDetectSetting carDetectSetting = new CarDetectSetting();
	public static String getSensorParameter(int sensorID)
    {
		String text = "";

//		LMSLog.d(DEBUG_TAG,"getSensorMessage sensorID:"+sensorID);

		if(sensorID < LMSConstValue.RADAR_SENSOR_NUM)
		{
			if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID)
			{
				text = "宽高检测头";
			}
			else if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
			{
				text = "长检测头";
			}
		}
		else 
		{
			text = "传感器";
		}
		
		if(LMSTelegram.serialNumber[sensorID] != null)
		{
			if(
				LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.RADAR_B)
				||LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.RADAR_FS)
			)
			{
				text += (" SN:"+LMSTelegram.serialNumber[sensorID]);
			}
			else
			{
				text += (" SN:"+Integer.valueOf(LMSTelegram.serialNumber[sensorID],16).toString());
			}
			
			text+=(" F:"+ParseLMSAckCommand.scanFreq[sensorID]
				+" S:"+ParseLMSAckCommand.startAngle[sensorID]
				+" N:"+ParseLMSAckCommand.measureData16bit_amount[sensorID]
				+" R:"+ParseLMSAckCommand.angleResolution[sensorID]
				+" B:"+LMSConstValue.yBaseValue[sensorID]
				+" BS:"+(int)LMSConstValue.indexToAngle(sensorID,LMSConstValue.baseBeginIndex[sensorID])
				+" BE:"+(int)LMSConstValue.indexToAngle(sensorID,LMSConstValue.baseEndIndex[sensorID])
				+" 丢帧数:"+ParseLMSAckCommand.telegramCounterLost[sensorID]
				);
		}
		else
		{
			text += (" SN:NULL");
		}

		return text;
    }
	
	public static void initSensorNumAndType()
	{
 		if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WIDTH_HEIGHT_1_DETECT)
		{
			LMSConstValue.LMS_L_SENSOR_NUM = 0;
			LMSConstValue.LMS_WH_SENSOR_NUM = 1;
			
			LMSConstValue.radarFunctionType[0] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
		}
		else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH_1_HIGH_SPEED
			||LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.LM1)
		{
			LMSConstValue.LMS_L_SENSOR_NUM = 0;
			LMSConstValue.LMS_WH_SENSOR_NUM = 1;
			
			LMSConstValue.radarFunctionType[0] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
		}
		else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH2)
		{
			LMSConstValue.LMS_L_SENSOR_NUM = 0;
			LMSConstValue.LMS_WH_SENSOR_NUM = 2;
			
			LMSConstValue.radarFunctionType[0] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
			LMSConstValue.radarFunctionType[1] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
		}
		else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH1_L1_SAME)
		{
			LMSConstValue.LMS_L_SENSOR_NUM = 1;
			LMSConstValue.LMS_WH_SENSOR_NUM = 1;
			
			LMSConstValue.radarFunctionType[0] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
			LMSConstValue.radarFunctionType[1] = LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID;
		}
		else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH2_L1_SAME
			||LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH2_L1_DIF)
		{
			LMSConstValue.LMS_L_SENSOR_NUM = 1;
			LMSConstValue.LMS_WH_SENSOR_NUM = 2;
			
			LMSConstValue.radarFunctionType[0] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
			LMSConstValue.radarFunctionType[1] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
			LMSConstValue.radarFunctionType[2] = LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID;
		}
		else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH2_L2_SAME)
		{
			LMSConstValue.LMS_L_SENSOR_NUM = 2;
			LMSConstValue.LMS_WH_SENSOR_NUM = 2;
			
			LMSConstValue.radarFunctionType[0] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
			LMSConstValue.radarFunctionType[1] = LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID;
			LMSConstValue.radarFunctionType[2] = LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID;
			LMSConstValue.radarFunctionType[3] = LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID;
		}
		else
		{
			LMSConstValue.LMS_L_SENSOR_NUM = 0;
			LMSConstValue.LMS_WH_SENSOR_NUM = 1;
		}
 		LMSConstValue.iRadarSensorNum = LMSConstValue.LMS_WH_SENSOR_NUM+LMSConstValue.LMS_L_SENSOR_NUM;

 		//先默认含光栅
		LMSConstValue.iLedSensorNum = 1;
		LMSConstValue.iCameraSensorNum = 2;
		LMSConstValue.iLightCurtainSensorNum = LMSConstValue.LIGHT_CURTAIN_SENSOR_NUM;
		LMSConstValue.iSystemSensorNum = 
			LMSConstValue.iRadarSensorNum 
			+LMSConstValue.iLightCurtainSensorNum
			+LMSConstValue.iCameraSensorNum
			+LMSConstValue.iLedSensorNum;
	}

	public static int widthHeightDetectSetProductInfo()
	{		
		String boardType = LMSPlatform.getStringPorperty("nvram_boardType", String.valueOf(enumBoardType.SERVER_BOARD));
		LMSConstValue.boardType	= LMSConstValue.getEnumBoardTypeFromString(boardType);	
		
		LMSLog.d(DEBUG_TAG,"defaultDetectType:"+LMSConstValue.defaultDetectType);

		initSensorNumAndType();

		LMSConstValue.LMS_AP_RESULT_PORT = LMSPlatform.getIntPorperty(LMSConstValue.nvramResultPort,3333);
		LMSConstValue.LMS_AP_USER_PORT = LMSPlatform.getIntPorperty(LMSConstValue.nvramUserCmdPort,3456);
		LMSConstValue.LMS_AP_FSRL_PROTOCOL_PORT = LMSPlatform.getIntPorperty(LMSConstValue.nvramFSRLProtocolPort,3010);
		LMSConstValue.LMS_AP_XML_PROTOCOL_PORT = LMSPlatform.getIntPorperty(LMSConstValue.nvramXMLProtocolPort,3355);
			
		if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME	
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME)
		{
			LMSConstValue.iCarRoadNum = 1;
		}
		else
		{
			LMSConstValue.iCarRoadNum = LMSPlatform.getIntPorperty(LMSConstValue.nvramCarRoadNum, 1);
		}
		LMSConstValue.bCarRoadOutputTurn = LMSPlatform.getBooleanPorperty("nvram_bCarRoadOutputTurn",true);
		for(int i=0;i<LMSConstValue.MAX_CAR_ROAD_NUM;i++)	
		{
			LMSConstValue.iCarRoadWidth[i] = LMSPlatform.getIntPorperty("nvram_carRoadWidth"+i, 0);
		}
		
		for(int i=0;i<LMSConstValue.RADAR_SENSOR_NUM;i++)
		{
			LMSConstValue.iLeftWindow[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramLeftWindow+i, 1000);	
			LMSConstValue.iRightWindow[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramRightWindow+i, 1000);
			LMSConstValue.iHeightWindow[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramHeightWindow+i, 1000);
			
			LMSConstValue.iGroundStartAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramGroundStartAngle+i, 0);	
			LMSConstValue.iGroundEndAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramGroundEndAngle+i, 180);

			if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
			{
				LMSConstValue.iFilterStartAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramFilterStartAngle+i, 0);	
				LMSConstValue.iFilterEndAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramFilterEndAngle+i, 1800);				
			}
			else
			{
				if(LMSConstValue.radarFunctionType[i] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
				{
					LMSConstValue.iFilterStartAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramFilterStartAngle+i, 10);	
					LMSConstValue.iFilterEndAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramFilterEndAngle+i, 1790);				
				}
				else if(LMSConstValue.radarFunctionType[i] == LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID)
				{
					LMSConstValue.iFilterStartAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramFilterStartAngle+i, 30);	
					LMSConstValue.iFilterEndAngle[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramFilterEndAngle+i, 1770);				
				}
			}
			resetFilterPoint(i);

			for(int antiLevel=0;antiLevel<LMSConstValue.ANTI_LEVEL;antiLevel++)
			{
				if(antiLevel == 0) //默认一级防撞级别为1000mm
					LMSConstValue.iAntiLevel[i][antiLevel] = LMSPlatform.getIntPorperty("nvram_antiLevel"+i+antiLevel, 1000);
				else
					LMSConstValue.iAntiLevel[i][antiLevel] = LMSPlatform.getIntPorperty("nvram_antiLevel"+i+antiLevel, 0);
			}
			
			LMSConstValue.fAngleLROffset[i] = LMSPlatform.getFloatPorperty(LMSConstValue.nvramAngleLROffset+i, 0);
			LMSConstValue.fAngleFBOffset[i] = LMSPlatform.getFloatPorperty(LMSConstValue.nvramAngleFBOffset+i, 0);
			LMSConstValue.fAngleRotateOffset[i] = LMSPlatform.getFloatPorperty(LMSConstValue.nvramAngleRotateOffset+i, 0);
		
			LMSConstValue.yBaseValue[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramYBaseValue+i, 1000);

			LMSConstValue.baseBeginIndex[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramBaseBeginIndex+i, 1000);
			LMSConstValue.baseEndIndex[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramBaseEndIndex+i, 1000);

			LMSConstValue.iFrontEdgeWindow[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramFrontEdgeWindow+i, 8000);
			LMSConstValue.iLREdgeWindow[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramLREdgeWindow+i, 500);
			LMSConstValue.iXMove[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramXMove+i, 0);
			if(LMSConstValue.iXMove[i] > LMSConstValue.MOVE_STEP)
				LMSConstValue.iXMove[i] = LMSConstValue.MOVE_STEP;
			else if(LMSConstValue.iXMove[i] < -LMSConstValue.MOVE_STEP)
				LMSConstValue.iXMove[i] = -LMSConstValue.MOVE_STEP;
			LMSConstValue.iYMove[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramYMove+i, 0);
			if(LMSConstValue.iYMove[i] > LMSConstValue.MOVE_STEP)
				LMSConstValue.iYMove[i] = LMSConstValue.MOVE_STEP;
			else if(LMSConstValue.iYMove[i] < -LMSConstValue.MOVE_STEP)
				LMSConstValue.iYMove[i] = -LMSConstValue.MOVE_STEP;
			
			LMSConstValue.bBaseValid[i] = LMSPlatform.getBooleanPorperty("nvram_bBaseValid"+i, LMSConstValue.bBaseValid[i]);
			
			if(LMSConstValue.fixMethod[i] == null)
				LMSConstValue.fixMethod[i] = LMSConstValue.innerObj.new EnumFixMethod();
			LMSConstValue.fixMethod[i].key = LMSPlatform.getStringPorperty(LMSConstValue.nvramFixMethod+i, LMSConstValue.EnumFixMethod.UP_FIX);	
			if(LMSConstValue.fixMethod[i].getValueFromKey(LMSConstValue.fixMethod[i].key) == null)
			{
				LMSConstValue.fixMethod[i].key = LMSConstValue.EnumFixMethod.UP_FIX;
			}
			
			LMSConstValue.bLRTurn[i] = LMSPlatform.getBooleanPorperty(LMSConstValue.nvramLRturn+i, false);	
			LMSConstValue.bUpDownTurn[i] = LMSPlatform.getBooleanPorperty(LMSConstValue.nvramUpDownTurn+i, false);
//			LMSConstValue.bUpDownTurn[i] = false;

//			LMSConstValue.bRotateTurn[i] = LMSPlatform.getBooleanPorperty(LMSConstValue.nvramRotateTurn+i, false);	
			LMSConstValue.bAngleDisplay[i] = LMSPlatform.getBooleanPorperty(LMSConstValue.nvramBoolAngleDisplay+i, false);				
			LMSConstValue.iAngleDisplay[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramAngleDisplay+i, 0);
		}

		LMSProductInfo.enumForComboBoxCustomerCode.key = LMSPlatform.getStringPorperty(LMSProductInfo.nvramCustomerCode, LMSProductInfo.UNKNOW[0]);	

		LMSConstValue.enumBackgroundColor.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramBackgroundColor, LMSConstValue.EnumBackgroundColor.BLACK_BACKGROUND_WHITH_FONT);	
		LMSConstValue.enumImageFormat.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramImageFormat, LMSConstValue.EnumImageFormat.PNG);	
		LMSConstValue.enumThreeDImageSize.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramThreeDImageSize, LMSConstValue.EnumThreeDImageSize.ThreeDImageSIZE10);	
		LMSConstValue.enumCarInDirection.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramCarInDirection,LMSConstValue.EnumCarInDirection.CAR_IN_WH_FIRST);
		LMSConstValue.enumLengthFilterType.key = LMSConstValue.LENGTH_FILTER_NONE;	
		LMSConstValue.enumWidthFilterType.key = LMSConstValue.WIDTH_FILTER_NONE;	
		LMSConstValue.enumHeightFilterType.key = LMSConstValue.HEIGHT_FILTER_NONE;	
		CarTypeAdapter.carEnumType.key = CarTypeAdapter.CAR_TYPE_NORMAL;	
		CarTypeAdapter.sNvramCarTypeString.sValue = CarTypeAdapter.CAR_TYPE_NORMAL;			
		LMSConstValue.protocolDatabaseEnumType.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramProtocolDataBaseType, LMSConstValue.protocolDatabaseEnumType.DATABASE_TYPE_SQL_SERVER_2000);	
		LMSConstValue.localStoreDatabaseEnumType.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramLocalStoreDataBaseType, LMSConstValue.localStoreDatabaseEnumType.DATABASE_TYPE_MYSQL);	
		LMSConstValue.enumOutputType.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramOutputType, LMSConstValue.enumOutputType.OUTPUT_FULL);	
		LMSConstValue.enumRecordType.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramRecordType, LMSConstValue.enumRecordType.RECORD_TYPE_CAR_IN_RECORD);	
		LMSConstValue.bNvramGuaCheLean.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramGuaCheLean.nvramStr, true);	

		LMSConstValue.bDoubleEdgeDetect = LMSPlatform.getBooleanPorperty(LMSConstValue.nvramDoubleEdgeDetect, false);
		
		if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF)
		{
			LMSConstValue.bNvramCreateThreeDImage.bValue = true;			
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME	
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME	
		)
		{
			LMSConstValue.bNvramCreateThreeDImage.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramCreateThreeDImage.nvramStr, true);
		}
		else
		{
			LMSConstValue.bNvramCreateThreeDImage.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramCreateThreeDImage.nvramStr, false);
		}

		LMSConstValue.bNvramCarTypeLocalSetting.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramCarTypeLocalSetting.nvramStr, false);
		LMSConstValue.bNvramLanBanSingleDecision.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramLanBanSingleDecision.nvramStr, false);
		LMSConstValue.bNvramZJSingleDecision.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramZJSingleDecision.nvramStr, false);
		LMSConstValue.bNvramZJ_GuaChe_XIAO.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramZJ_GuaChe_XIAO.nvramStr, false);

		LMSConstValue.bNvramThreeDScreenSnap.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDScreenSnap.nvramStr, true);
		LMSConstValue.bNvramThreeDFrame.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDFrame.nvramStr, false);

		LMSConstValue.bNvramThreeDImageQianYing.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDImageQianYing.nvramStr, false);
		LMSConstValue.bNvramThreeDImageWithSize.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDImageWithSize.nvramStr, true);
		LMSConstValue.bNvramThreeDImageWithSizeFrame.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDImageWithSizeFrame.nvramStr, true);
		LMSConstValue.bNvramCarInOutSignal.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramCarInOutSignal.nvramStr, false);
		LMSConstValue.bNvramThreeDCarRodeMiddle.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDCarRodeMiddle.nvramStr, true);
		LMSConstValue.bNvramProtocolWithCarOutTime.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramProtocolWithCarOutTime.nvramStr, true);
		LMSConstValue.bNvramProtocolCarOutTimeWithStr.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramProtocolCarOutTimeWithStr.nvramStr, false);
//		LMSConstValue.bNvramThreeDDisplayWidthMax.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDDisplayWidthMax.nvramStr, true);
		LMSConstValue.bNvramThreeDDisplayPointMax.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramThreeDDisplayPointMax.nvramStr, true);
		LMSConstValue.bNvramBackingCarOutput.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramBackingCarOutput.nvramStr, false);
				
		LMSConstValue.sNvramLocalStoreDataBaseIP.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramLocalStoreDataBaseIP.nvramStr, "127.0.0.1");
		LMSConstValue.sNvramLocalStoreDataBasePORT.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramLocalStoreDataBasePORT.nvramStr, "3306");
		LMSConstValue.sNvramLocalStoreDataBaseName.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramLocalStoreDataBaseName.nvramStr, LMSConstValue.DATA_BASE_DEFAULT_USER);
		LMSConstValue.sNvramLocalStoreDataBasePassword.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramLocalStoreDataBasePassword.nvramStr, LMSConstValue.DATA_BASE_DEFAULT_PASSWORD);
		if(AppConst.companyName.equals(LMSConstValue.CUSTOMER_GDHS))
		{
			LMSConstValue.sNvramCustomer.sValue = LMSConstValue.CUSTOMER_GDHS;			
		}
		else
		{
			LMSConstValue.sNvramCustomer.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCustomer.nvramStr, "");
		}
		LMSConstValue.sNvramUserMsg.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramUserMsg.nvramStr, "可于用户设置界面设置本信息");

//		LMSConstValue.sNvramPrintFootStringLine1.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramPrintFootStringLine1.nvramStr, "页脚");
		LMSConstValue.sNvramPrintFootStringLine2.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramPrintFootStringLine2.nvramStr, "说明:GB21861-2014/机动车安全技术检验项目和方法");
		LMSConstValue.sNvramPrintFootStringLine3.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramPrintFootStringLine3.nvramStr, "");
		LMSConstValue.bNvramPrintOperatorID.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramPrintOperatorID.nvramStr, true);

		for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
		{
			LMSConstValue.sNvramMainImageTitle[i] = new NvramType("nvram_MainImageTitle"+(i+1),NvramType.Type.STRING_TYPE);
			LMSConstValue.sNvramMainImage[i] = new NvramType("nvram_MainImage"+(i+1),NvramType.Type.STRING_TYPE);
			
			String defaultImageName = null;
			String defaultImageTitle = null;
			if(i == 0)
			{
				defaultImageName = "down";
				defaultImageTitle = "俯视图";
			}
			else if(i == 1)
			{
				defaultImageName = "left";
				defaultImageTitle = "左视图";
			}
			else if(i == 2)
			{
				defaultImageName = "right";
				defaultImageTitle = "右视图";
			}
			else if(i == 3)
			{
				defaultImageName = "front";
				defaultImageTitle = "前视图";
			}
			else if(i == 4)
			{
				defaultImageName = "rear";
				defaultImageTitle = "后视图";
			}
			LMSConstValue.sNvramMainImage[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramMainImage[i].nvramStr, defaultImageName);
			LMSConstValue.sNvramMainImageTitle[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramMainImageTitle[i].nvramStr, defaultImageTitle);
		}
		for(int i=0;i<LMSConstValue.PRINT_IMAGE_NUM;i++)
		{
			LMSConstValue.sNvramPrintImageTitle[i] = new NvramType("nvram_PrintImageTitle"+(i+1),NvramType.Type.STRING_TYPE);
			LMSConstValue.sNvramPrintImage[i] = new NvramType("nvram_PrintImage"+(i+1),NvramType.Type.STRING_TYPE);
			
			String defaultImageName = null;
			String defaultImageTitle = null;
			if(i == 0)
			{
				defaultImageName = "down";
				defaultImageTitle = "俯视图";
			}
			else if(i == 1)
			{
				defaultImageName = "left";
				defaultImageTitle = "侧视图";
			}
			else if(i == 2)
			{
				defaultImageName = "car_in";
				defaultImageTitle = "车头图片";
			}
			else if(i == 3)
			{
				defaultImageName = "car_out";
				defaultImageTitle = "车尾图片";
			}
			LMSConstValue.sNvramPrintImage[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramPrintImage[i].nvramStr, defaultImageName);
			LMSConstValue.sNvramPrintImageTitle[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramPrintImageTitle[i].nvramStr, defaultImageTitle);
		}
		LMSConstValue.sNvramWebServiceUrl.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramWebServiceUrl.nvramStr, "http://127.0.0.1:8459/test.asmx/");
		LMSConstValue.bNvramOnlyShowNotDetectdCar.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramOnlyShowNotDetectdCar.nvramStr, false);
		LMSConstValue.bNvramWebServiceDebugDialog.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramWebServiceDebugDialog.nvramStr, false);
		LMSConstValue.iNvramUserMsgFontSize.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramUserMsgFontSize.nvramStr, 20 ,LMSConstValue.UserMsgFontSize_MIN, LMSConstValue.UserMsgFontSize_MAX);

		LMSConstValue.sNvramCustomerProtocol_database_ip.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCustomerProtocol_database_ip.nvramStr, "127.0.0.1");
		LMSConstValue.sNvramCustomerProtocol_database_port.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCustomerProtocol_database_port.nvramStr, "1433");
		LMSConstValue.sNvramCustomerProtocol_database_name.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCustomerProtocol_database_name.nvramStr, "HF_SRF");
		LMSConstValue.sNvramCustomerProtocol_database_table.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCustomerProtocol_database_table.nvramStr, "WK_table");
		LMSConstValue.sNvramCustomerProtocol_database_user.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCustomerProtocol_database_user.nvramStr, "sa");
		LMSConstValue.sNvramCustomerProtocol_database_password.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCustomerProtocol_database_password.nvramStr, "123456");
		
		LMSConstValue.bNvramRulerCalibration.bValue = LMSPlatform.getBooleanPorperty(LMSConstValue.bNvramRulerCalibration.nvramStr, false);
		
		if(LMSConstValue.defaultDetectType == enumDetectType.WH2
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME
		)
		{
			LMSConstValue.iNvramLongWH0istance.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramLongWH0istance.nvramStr, 0);
		}

		//======================================================================================
		LMSConstValue.sNvramSettingPassword.sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramSettingPassword.nvramStr, "");
		/*
		if(LMSConstValue.sNvramSettingPassword.sValue.equals(""))
		{			
			LMSConstValue.iNvramLRDistance.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramLRDistance.nvramStr, 4000);

			LMSConstValue.iNvramLWDistance.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramLWDistance.nvramStr, 8000);
			LMSConstValue.iNvramLWDistance2.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramLWDistance2.nvramStr, 0);
			LMSConstValue.iNvramLightCurtianRadarDistance0.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramLightCurtianRadarDistance0.nvramStr, 100);

			LMSConstValue.iNvramWidthOutputCompensate.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramWidthOutputCompensate.nvramStr, 0);
			LMSConstValue.iNvramHeightOutputCompensate.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramHeightOutputCompensate.nvramStr, 0);
			LMSConstValue.iNvramLengthOutputCompensate.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramLengthOutputCompensate.nvramStr, 0);
		}
		else
		*/
		{
			String strValue;

			//----------------------------------------------------------------------------------------
			strValue = LMSPlatform.getStringPorperty(LMSConstValue.iNvramLRDistance.nvramStr, "0");
			if(!strValue.equals(""))
			{
				try{
					LMSConstValue.iNvramLRDistance.iValue = Integer.valueOf(strValue);
//	        		LMSPlatform.savePorperty(null,LMSConstValue.iNvramLRDistance.nvramStr,Md5.convertMD5(strValue));
				}
	    		catch (NumberFormatException e) {
	    			if(!strValue.equals("null")) //兼容以前弄出来的null的bug
	    				LMSConstValue.iNvramLRDistance.iValue = Integer.valueOf(Md5.convertMD5(strValue));
	    			else
	    				LMSConstValue.iNvramLRDistance.iValue = 0;
	    		}
			}
			
			//----------------------------------------------------------------------------------------
			strValue = LMSPlatform.getStringPorperty(LMSConstValue.iNvramLWDistance.nvramStr, "0");
			if(!strValue.equals(""))
			{
				try{
					LMSConstValue.iNvramLWDistance.iValue = Integer.valueOf(strValue);
//	        		LMSPlatform.savePorperty(null,LMSConstValue.iNvramLWDistance.nvramStr,Md5.convertMD5(strValue));
				}
	    		catch (NumberFormatException e) {
	    			if(!strValue.equals("null")) //兼容以前弄出来的null的bug
	    				LMSConstValue.iNvramLWDistance.iValue = Integer.valueOf(Md5.convertMD5(strValue));
	    			else
	    				LMSConstValue.iNvramLWDistance.iValue = 0;
	    		}
			}

			//----------------------------------------------------------------------------------------
			strValue = LMSPlatform.getStringPorperty(LMSConstValue.iNvramLWDistance2.nvramStr, "0");
			if(!strValue.equals(""))
			{
				try{
					LMSConstValue.iNvramLWDistance2.iValue = Integer.valueOf(strValue);
//	        		LMSPlatform.savePorperty(null,LMSConstValue.iNvramLWDistance2.nvramStr,Md5.convertMD5(strValue));
				}
	    		catch (NumberFormatException e) {
	    			if(!strValue.equals("null")) //兼容以前弄出来的null的bug
	    				LMSConstValue.iNvramLWDistance2.iValue = Integer.valueOf(Md5.convertMD5(strValue));
	    			else
	    				LMSConstValue.iNvramLWDistance2.iValue = 0;
	    		}
			}
			
			//----------------------------------------------------------------------------------------
			strValue = LMSPlatform.getStringPorperty(LMSConstValue.iNvramLightCurtianLongDistance.nvramStr, "0");
			if(!strValue.equals(""))
			{
				try{
					LMSConstValue.iNvramLightCurtianLongDistance.iValue = Integer.valueOf(strValue);
//	        		LMSPlatform.savePorperty(null,LMSConstValue.iNvramLightCurtianRadarDistance0.nvramStr,Md5.convertMD5(strValue));
				}
	    		catch (NumberFormatException e) {
	    			if(!strValue.equals("null")) //兼容以前弄出来的null的bug
	    				LMSConstValue.iNvramLightCurtianLongDistance.iValue = Integer.valueOf(Md5.convertMD5(strValue));
	    			else
	    				LMSConstValue.iNvramLightCurtianLongDistance.iValue = 0;
	    		}
			}

			//----------------------------------------------------------------------------------------
			strValue = LMSPlatform.getStringPorperty(LMSConstValue.iNvramWidthOutputCompensate.nvramStr, "0");
			if(!strValue.equals(""))
			{
				try{
					LMSConstValue.iNvramWidthOutputCompensate.iValue = Integer.valueOf(strValue);
//	        		LMSPlatform.savePorperty(null,LMSConstValue.iNvramWidthOutputCompensate.nvramStr,Md5.convertMD5(strValue));
				}
	    		catch (NumberFormatException e) {
	    			if(!strValue.equals("null")) //兼容以前弄出来的null的bug
	    				LMSConstValue.iNvramWidthOutputCompensate.iValue = Integer.valueOf(Md5.convertMD5(strValue));
	    			else
	    				LMSConstValue.iNvramWidthOutputCompensate.iValue = 0;
	    		}
			}
			
			//----------------------------------------------------------------------------------------
			strValue = LMSPlatform.getStringPorperty(LMSConstValue.iNvramLengthOutputCompensate.nvramStr, "0");
			if(!strValue.equals(""))
			{
				try{
					LMSConstValue.iNvramLengthOutputCompensate.iValue = Integer.valueOf(strValue);
//	        		LMSPlatform.savePorperty(null,LMSConstValue.iNvramLengthOutputCompensate.nvramStr,Md5.convertMD5(strValue));
				}
	    		catch (NumberFormatException e) {
	    			if(!strValue.equals("null")) //兼容以前弄出来的null的bug
	    				LMSConstValue.iNvramLengthOutputCompensate.iValue = Integer.valueOf(Md5.convertMD5(strValue));
	    			else
	    				LMSConstValue.iNvramLengthOutputCompensate.iValue = 0;
	    		}
			}
			
			//----------------------------------------------------------------------------------------
			strValue = LMSPlatform.getStringPorperty(LMSConstValue.iNvramHeightOutputCompensate.nvramStr, "0");
			if(!strValue.equals(""))
			{
				try{
					LMSConstValue.iNvramHeightOutputCompensate.iValue = Integer.valueOf(strValue);
//	        		LMSPlatform.savePorperty(null,LMSConstValue.iNvramHeightOutputCompensate.nvramStr,Md5.convertMD5(strValue));
				}
	    		catch (NumberFormatException e) {
	    			if(!strValue.equals("null")) //兼容以前弄出来的null的bug
	    				LMSConstValue.iNvramHeightOutputCompensate.iValue = Integer.valueOf(Md5.convertMD5(strValue));
	    			else
	    				LMSConstValue.iNvramHeightOutputCompensate.iValue = 0;
	    		}
			}
		}

		LMSConstValue.enumWHPositonType.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramEnumWHPositonType, LMSConstValue.EnumWHPositonType.WH_POSITION_AT_LONG_LEFT);
		LMSConstValue.enumWHPositionSetType.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramEnumWHPositionSetType, LMSConstValue.EnumWHPositionSetType.AUTO_SET);
//		LMSConstValue.enumThreeDMouseType.key = LMSPlatform.getStringPorperty(LMSConstValue.nvramEnumThreeDMouseType, LMSConstValue.EnumThreeDMouseType.MOUSE_AUTO_ROTATE);
		LMSConstValue.enumThreeDMouseType.key = LMSConstValue.EnumThreeDMouseType.MOUSE_AUTO_ROTATE;
		LMSConstValue.enumThreeDMaxPointType.key = LMSConstValue.EnumThreeDMaxPointType.WHOLE_CAR_MAX_POINT;
		
		LMSConstValue.sNvramStationID.sValue = LMSPlatform.getStringPorperty(LMSConstValue.USER_PROPERTY,LMSConstValue.sNvramStationID.nvramStr, "");
		
		if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME
		)
		{
			CarDetectSetting.resetWHPosition();
		}
		
		LMSConstValue.bNvramThreeDDisplayRadar0.bValue = true;
		LMSConstValue.bNvramThreeDDisplayRadar1.bValue = true;
		LMSConstValue.bNvramThreeDDisplayLightCurtain.bValue = true;
		LMSConstValue.bNvramThreeDDisplayFilterIn.bValue = true;
		LMSConstValue.bNvramThreeDDisplayFilterOut1.bValue = false;
		LMSConstValue.bNvramThreeDDisplayFilterOut2.bValue = false;
		LMSConstValue.iNvramXRangeMin.iValue = -4000;
		LMSConstValue.iNvramXRangeMax.iValue = 4000;
		LMSConstValue.iNvramYRangeMin.iValue = -5000;
		LMSConstValue.iNvramYRangeMax.iValue = 5000;
		LMSConstValue.iNvramZRangeMin.iValue = -40000;
		LMSConstValue.iNvramZRangeMax.iValue = 40000;
		
		for(int i=0;i<LMSConstValue.SYSTEM_SENSOR_NUM;i++)
		{
			LMSConstValue.SENSOR_IP[i] = LMSPlatform.getStringPorperty(LMSConstValue.nvramSensorIP+i,"192.168.0."+(i+1));
			LMSConstValue.SENSOR_PORT[i] = LMSPlatform.getIntPorperty(LMSConstValue.nvramSensorPort+i,2111);
			LMSConstValue.bPortHasData[i] = false;
			LMSConstValue.bPortHasValidData[i] = false;
			
			if(i<LMSConstValue.RADAR_SENSOR_NUM)
			{
				if(LMSConstValue.sensorType[i] == null)
				{
					LMSConstValue.sensorType[i] = LMSConstValue.innerObj.new SensorTypeForRadarSetting();
				}
			}
			else if(i>=LMSConstValue.LIGHT_CURTAIN_ID_START&&i<LMSConstValue.LIGHT_CURTAIN_ID_START+LMSConstValue.LIGHT_CURTAIN_SENSOR_NUM)
			{
				if(LMSConstValue.sensorType[i] == null)
				{
					LMSConstValue.sensorType[i] = LMSConstValue.innerObj.new SensorTypeForLightCurtainSetting();
				}
			}
			else if(i>=LMSConstValue.CAMERA_ID_START&&i<LMSConstValue.CAMERA_ID_START+LMSConstValue.CAMERA_SENSOR_NUM)
			{
				if(LMSConstValue.sensorType[i] == null)
				{
					LMSConstValue.sensorType[i] = LMSConstValue.innerObj.new SensorTypeForCamera();
				}
			}
			else if(i>=LMSConstValue.LED_ID_START&&i<LMSConstValue.LED_ID_START+LMSConstValue.LED_SENSOR_NUM)
			{
				if(LMSConstValue.sensorType[i] == null)
				{
					LMSConstValue.sensorType[i] = LMSConstValue.innerObj.new SensorTypeForLED();
				}
			}
			LMSConstValue.sensorType[i].key = LMSPlatform.getStringPorperty(LMSConstValue.nvramSensorType+i, LMSConstValue.SensorType.UNKNOW);			

			//camera初始化
			if(i>=LMSConstValue.CAMERA_ID_START&&i<LMSConstValue.CAMERA_ID_START+LMSConstValue.CAMERA_SENSOR_NUM)
			{
				LMSConstValue.iNvramCameraCaptureDelaySecond[i] = new NvramType("nvram_cameraCaptureDelaySecond",NvramType.Type.INTEGER_TYPE);
				LMSConstValue.iNvramCameraCaptureDelaySecond[i].iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramCameraCaptureDelaySecond[i].nvramStr+i, 0);
				
				if(LMSConstValue.sensorType[i].key.equals(LMSConstValue.SensorType.HK_DS))
				{
					LMSConstValue.sNvramCameraUserName[i] = new NvramType("nvram_cameraUserName",NvramType.Type.STRING_TYPE);
					LMSConstValue.sNvramCameraUserName[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCameraUserName[i].nvramStr+i, "admin");
					
					LMSConstValue.sNvramCameraPassword[i] = new NvramType("nvram_cameraPassword",NvramType.Type.STRING_TYPE);
					LMSConstValue.sNvramCameraPassword[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCameraPassword[i].nvramStr+i, "qwer1234");
				}
				else if(LMSConstValue.sensorType[i].key.equals(LMSConstValue.SensorType.DH_IPC))
				{
					LMSConstValue.sNvramCameraUserName[i] = new NvramType("nvram_cameraUserName",NvramType.Type.STRING_TYPE);
					LMSConstValue.sNvramCameraUserName[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCameraUserName[i].nvramStr+i, "admin");
					
					LMSConstValue.sNvramCameraPassword[i] = new NvramType("nvram_cameraPassword",NvramType.Type.STRING_TYPE);
					LMSConstValue.sNvramCameraPassword[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCameraPassword[i].nvramStr+i, "admin");
				}
				else
				{
					LMSConstValue.sNvramCameraUserName[i] = new NvramType("nvram_cameraUserName",NvramType.Type.STRING_TYPE);
					LMSConstValue.sNvramCameraUserName[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCameraUserName[i].nvramStr+i, "none");
					
					LMSConstValue.sNvramCameraPassword[i] = new NvramType("nvram_cameraPassword",NvramType.Type.STRING_TYPE);
					LMSConstValue.sNvramCameraPassword[i].sValue = LMSPlatform.getStringPorperty(LMSConstValue.sNvramCameraPassword[i].nvramStr+i, "none");					
				}
			}
			
			LMSConstValue.setLPNum(i);			
		}
			
		LMSConstValue.iNvramThreeDImageFontSize.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramThreeDImageFontSize.nvramStr, 16);

		LMSConstValue.iNvramValidThingWidthMin.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramValidThingWidthMin.nvramStr, 1000);
		LMSConstValue.iNvramValidThingWidthMax.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramValidThingWidthMax.nvramStr, 4500);
		LMSConstValue.iNvramValidThingHeightMin.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramValidThingHeightMin.nvramStr, 1000);
		LMSConstValue.iNvramValidThingHeightMax.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramValidThingHeightMax.nvramStr, 5500);
		LMSConstValue.iNvramValidThingLengthMin.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramValidThingLengthMin.nvramStr, 1000);
		LMSConstValue.iNvramValidThingLengthMax.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramValidThingLengthMax.nvramStr, 30000);

		LMSConstValue.iConVeyerSpeed = LMSPlatform.getIntPorperty(LMSConstValue.nvramConVeyerSpeed,0);
		LMSConstValue.iMinPacketWidth = LMSPlatform.getIntPorperty(LMSConstValue.nvramMinPacketWidth,0);
		LMSConstValue.iNoReadNoVolumnMachingTimeInteval = LMSPlatform.getIntPorperty(LMSConstValue.nvramNoReadNoVolumnMachingTimeInteval,5000);

		LMSConstValue.iNvramReplayLine.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramReplayLine.nvramStr, 0);
		LMSConstValue.iNvramSimulateInteval.iValue = LMSPlatform.getIntPorperty(LMSConstValue.iNvramSimulateInteval.nvramStr, 20);
		if(LMSConstValue.iNvramSimulateInteval.iValue < 1)
			LMSConstValue.iNvramSimulateInteval.iValue = 1;
		
		if(LMSConstValue.defaultDetectType == enumDetectType.WH2
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME
		)
		{
			for(int i=0;i<LMSConstValue.RADAR_SENSOR_NUM;i++)
			{
				if(LMSConstValue.radarFunctionType[i] == LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID)
					LMSConstValue.VALID_THING_HEIGHT[i] = LMSConstValue.VALID_THING_HEIGHT_WH;
				else
					LMSConstValue.VALID_THING_HEIGHT[i] = LMSConstValue.VALID_THING_HEIGHT_LONG;
			}
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WIDTH_HEIGHT_1_DETECT
			||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED
			||LMSConstValue.defaultDetectType == enumDetectType.LM1
		)
		{
			for(int i=0;i<LMSConstValue.RADAR_SENSOR_NUM;i++)
			{
				if(LMSConstValue.radarFunctionType[i] == LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID)
					LMSConstValue.VALID_THING_HEIGHT[i] = LMSConstValue.VALID_THING_HEIGHT_WH;
				else
					LMSConstValue.VALID_THING_HEIGHT[i] = LMSConstValue.VALID_THING_HEIGHT_LONG;
			}			
		}

		//----------------------------------------------------------------------------
		return LMSConstValue.defaultDetectType.ordinal();
	}

	//==============================================================================================
	public static BlockingQueue<PhysicLayerPacket> packetQueue[] = new ArrayBlockingQueue[LMSConstValue.SYSTEM_SENSOR_NUM];  

   	public static LMSToken tokenSensorID[] = new LMSToken[LMSConstValue.SYSTEM_SENSOR_NUM];

	public PhysicLayerSocket physicLayerSocket[] = new PhysicLayerSocket[LMSConstValue.SYSTEM_SENSOR_NUM];
	public PhysicLayerSerialPort physicLayerSerialPort[] = new PhysicLayerSerialPort[LMSConstValue.SYSTEM_SENSOR_NUM];  
	public PhysicLayerModbus physicLayerModbus[] = new PhysicLayerModbus[LMSConstValue.SYSTEM_SENSOR_NUM];
	public PhysicLayerSimulate physicLayerSimulate[] = new PhysicLayerSimulate[LMSConstValue.SYSTEM_SENSOR_NUM];

	public DataLayerDataParseRunnable dataLayerDataParseRunnable[] = new DataLayerDataParseRunnable[LMSConstValue.RADAR_SENSOR_NUM];
	public DataLayerDataParseRunnable dataLayerLightCurtainRunnable;
	public WidthHeightDetectRunnable widthHeightDetectRunnable[] = new WidthHeightDetectRunnable[LMSConstValue.RADAR_SENSOR_NUM];
	public LightCurtainAlgorithm lightCurtainAlgorithm = new LightCurtainAlgorithm();
	public ArrayBlockingQueue<Contour> contourList = new ArrayBlockingQueue<Contour>(5);
	
	public void catDetectInit()
	{
		File fileResult = new File("result");  //判断文件夹是否存在,如果不存在则创建文件夹  
 		if (!fileResult.exists()) 
 		{   
 			fileResult.mkdir();
 		}
 		 				
 		//=====================================================================
 		LMSProductInfo.generateProductInfoOuter();

 		//=====================================================================
		for(int i=0;i<LMSConstValue.MAX_CAR_ROAD_NUM;i++)
		{
        	LMSConstValue.carState[i] = LMSConstValue.enumCarState.NOT_CAR_DETECT;			
		}
				
		//===========================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		Nvram nvram = new Nvram();
		LMSEventManager.addListener(nvram.eventListener);
	
		for(int i=0;i<LMSConstValue.SYSTEM_SENSOR_NUM;i++)
		{
			tokenSensorID[i] = new LMSToken();
			packetQueue[i] = new ArrayBlockingQueue(LMSConstValue.LMS_PACKET_NUM_MAX_SOCKET);  
			
	       	LMSProductInfo.bSensorInit[i] = false;
		}
		
		//=======================================================================
		//雷达
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
		{			
        	resetSystemStateString("NULL",i);
        	
        	LMSProductInfo.bValidSerialNumber[i] = false;
 			//模拟线程	
			if(LMSConstValue.boardType ==enumBoardType.SIMULATE_FILE_BOARD)
			{
				physicLayerSimulate[i] = new PhysicLayerSimulate(i);
				Thread thread = new Thread(physicLayerSimulate[i].thread(i));
				thread.setPriority(Thread.MAX_PRIORITY);
				thread.start();
			}
			else
			{	//lidar通讯获取数据
				physicLayerSocket[i] = new PhysicLayerSocket(i);
				Thread threadSocket = new Thread(physicLayerSocket[i].thread());
				threadSocket.setPriority(Thread.MAX_PRIORITY);
				threadSocket.start();
				//串口通讯
				physicLayerSerialPort[i] = new PhysicLayerSerialPort(i);  
				Thread threadSerialPort = new Thread(physicLayerSerialPort[i].thread());
				threadSerialPort.setPriority(Thread.MAX_PRIORITY);
				threadSerialPort.start();					
			}	
			//数据解析
			dataLayerDataParseRunnable[i] = new DataLayerDataParseRunnable(i);
			new Thread(dataLayerDataParseRunnable[i].thread()).start(); 
			//算法层
			widthHeightDetectRunnable[i] = new WidthHeightDetectRunnable(i);
			new Thread(widthHeightDetectRunnable[i].thread(i)).start();   
		}	
		
		//=============================================================================
		//光幕
		if(LMSConstValue.boardType ==enumBoardType.SIMULATE_FILE_BOARD)
		{
			physicLayerSimulate[LMSConstValue.LIGHT_CURTAIN_ID_START] = new PhysicLayerSimulate(LMSConstValue.LIGHT_CURTAIN_ID_START);
			Thread thread = new Thread(physicLayerSimulate[LMSConstValue.LIGHT_CURTAIN_ID_START].thread(LMSConstValue.LIGHT_CURTAIN_ID_START));
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.start();
		}
		else
		{
			physicLayerSocket[LMSConstValue.LIGHT_CURTAIN_ID_START] = new PhysicLayerSocket(LMSConstValue.LIGHT_CURTAIN_ID_START);
			Thread threadSocket = new Thread(physicLayerSocket[LMSConstValue.LIGHT_CURTAIN_ID_START].thread());
			threadSocket.setPriority(Thread.MAX_PRIORITY);
			threadSocket.start();
			
			physicLayerSerialPort[LMSConstValue.LIGHT_CURTAIN_ID_START] = new PhysicLayerSerialPort(LMSConstValue.LIGHT_CURTAIN_ID_START);  
			Thread threadSerialPort = new Thread(physicLayerSerialPort[LMSConstValue.LIGHT_CURTAIN_ID_START].thread());
			threadSerialPort.setPriority(Thread.MAX_PRIORITY);
			threadSerialPort.start();		
			
			physicLayerModbus[LMSConstValue.LIGHT_CURTAIN_ID_START] = new PhysicLayerModbus(LMSConstValue.LIGHT_CURTAIN_ID_START);  
			Thread threadModbus = new Thread(physicLayerModbus[LMSConstValue.LIGHT_CURTAIN_ID_START].thread());
			threadModbus.setPriority(Thread.MAX_PRIORITY);
			threadModbus.start();					
		}
		dataLayerLightCurtainRunnable = new DataLayerDataParseRunnable(LMSConstValue.LIGHT_CURTAIN_ID_START);
		new Thread(dataLayerLightCurtainRunnable.thread()).start(); 
		new Thread(lightCurtainAlgorithm.thread()).start();   
		
		for(int i=LMSConstValue.LED_ID_START;i<LMSConstValue.LED_ID_START+LMSConstValue.iLedSensorNum;i++)
		{
			physicLayerSocket[i] = new PhysicLayerSocket(i);
			Thread threadSocket = new Thread(physicLayerSocket[i].thread());
			threadSocket.start();
			
			physicLayerSerialPort[i] = new PhysicLayerSerialPort(i);  
			Thread threadSerialPort = new Thread(physicLayerSerialPort[i].thread());
			threadSerialPort.start();			
		}		
	}
	
	static public void resetBaseValue(int sensorID)
    {
		if(LMSConstValue.defaultDetectType != enumDetectType.ANTI_COLLITION)
		{
			LMSLog.d(DEBUG_TAG,"resetBaseValue sensorID="+sensorID);
	
	    	LMSConstValue.bBaseValid[sensorID] = false;
	    	LMSConstValue.bInitial[sensorID] = true;
	    	
	  		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SERVER_SEND_BASE_DATA_INTENT,eventExtra);	    	
		}
    }
	 		
	static public void setEditTextLeftWindowValue(String str,int sensorID)
	{						
		if(!str.equals("")){
			int value = Integer.valueOf(str,10);
			
			if(value != LMSConstValue.iLeftWindow[sensorID])
			{	
				LMSConstValue.iLeftWindow[sensorID] = value;
				if(LMSConstValue.iLeftWindow[sensorID]>50000)
				{
					LMSConstValue.iLeftWindow[sensorID] = 50000;	
				}
				
				if(LMSConstValue.iLeftWindow[sensorID]<-LMSConstValue.iRightWindow[sensorID])
				{
					LMSConstValue.iLeftWindow[sensorID] = -LMSConstValue.iRightWindow[sensorID];
				}
	    							
				resetCarRoadWidthValue();

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramLeftWindow);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iLeftWindow[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    				
			}
		}		
	}
	
	static public void setEditTextRightWindowValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			int value = Integer.valueOf(str,10);
			
			if(value != LMSConstValue.iRightWindow[sensorID])
			{	
				LMSConstValue.iRightWindow[sensorID] = value;
				if(LMSConstValue.iRightWindow[sensorID]>50000)
				{
					LMSConstValue.iRightWindow[sensorID] = 50000;	
				}
	    				
				if(LMSConstValue.iRightWindow[sensorID]<-LMSConstValue.iLeftWindow[sensorID])
				{
					LMSConstValue.iRightWindow[sensorID] = -LMSConstValue.iLeftWindow[sensorID];
				}
				
				resetCarRoadWidthValue();

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramRightWindow);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iRightWindow[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}		
    }
	
	static public void setEditTextHeightWindowValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			int value = Integer.valueOf(str,10);
			
			if(value != LMSConstValue.iHeightWindow[sensorID])
			{	
				LMSConstValue.iHeightWindow[sensorID] = value;
				if(LMSConstValue.iHeightWindow[sensorID]>50000)
				{
					LMSConstValue.iHeightWindow[sensorID] = 50000;	
				}
	    				
				if(LMSConstValue.iHeightWindow[sensorID]<0)
				{
					LMSConstValue.iHeightWindow[sensorID] = 0;
				}
				
				resetCarRoadWidthValue();

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramHeightWindow);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iHeightWindow[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}		
    }
	
	static public void resetFilterPoint(int sensorID)
	{
		if(ParseLMSAckCommand.angleResolution[sensorID] == 0)
		{
			LMSConstValue.filterStartPoint[sensorID] = 0;
			LMSConstValue.filterEndPoint[sensorID] = 0;
		}
		else
		{
			LMSConstValue.filterStartPoint[sensorID] = LMSConstValue.angleToIndex(sensorID,LMSConstValue.iFilterStartAngle[sensorID]); 
			LMSConstValue.filterEndPoint[sensorID] = LMSConstValue.angleToIndex(sensorID,LMSConstValue.iFilterEndAngle[sensorID]);  
			
			if(LMSConstValue.filterStartPoint[sensorID] < LMSConstValue.physicStartPoint[sensorID])
				LMSConstValue.filterStartPoint[sensorID] = LMSConstValue.physicStartPoint[sensorID];
			else if(LMSConstValue.filterStartPoint[sensorID] > LMSConstValue.physicEndPoint[sensorID])
				LMSConstValue.filterStartPoint[sensorID] = LMSConstValue.physicEndPoint[sensorID];

			if(LMSConstValue.filterEndPoint[sensorID] > LMSConstValue.physicEndPoint[sensorID])
				LMSConstValue.filterEndPoint[sensorID] = LMSConstValue.physicEndPoint[sensorID];
			else if(LMSConstValue.filterEndPoint[sensorID] < LMSConstValue.physicStartPoint[sensorID])
				LMSConstValue.filterEndPoint[sensorID] = LMSConstValue.physicStartPoint[sensorID];
			
			LMSLog.d(DEBUG_TAG+sensorID, "angleResolution 1="+ParseLMSAckCommand.angleResolution[sensorID]);
			LMSLog.d(DEBUG_TAG+sensorID, "iFilterStartAngle 1="+LMSConstValue.iFilterStartAngle[sensorID]+" iEndAngle="+LMSConstValue.iFilterEndAngle[sensorID]);
			LMSLog.d(DEBUG_TAG+sensorID, "filterStartPoint 1="+LMSConstValue.filterStartPoint[sensorID]+" filterEndPoint="+LMSConstValue.filterEndPoint[sensorID]);
			LMSLog.d(DEBUG_TAG+sensorID, "physicStartPoint 1="+LMSConstValue.physicStartPoint[sensorID]+" physicEndPoint="+LMSConstValue.physicEndPoint[sensorID]);
			LMSLog.d(DEBUG_TAG+sensorID, "startAngle 1="+ParseLMSAckCommand.startAngle[sensorID]+" stopAngle="+ParseLMSAckCommand.stopAngle[sensorID]);
		}
	}
	
	static public void setEditTextFilterStartAngleValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);
			int value = (int) (valueFloat*10);
			
			if(value != LMSConstValue.iFilterStartAngle[sensorID])
			{	
				LMSConstValue.iFilterStartAngle[sensorID] = value;

				if(LMSConstValue.iFilterStartAngle[sensorID]>2700)
				{
					LMSConstValue.iFilterStartAngle[sensorID] = 2700;	
				}

				if(LMSConstValue.iFilterStartAngle[sensorID]>LMSConstValue.iFilterEndAngle[sensorID])
				{
					LMSConstValue.iFilterStartAngle[sensorID] = LMSConstValue.iFilterEndAngle[sensorID];	
				}
				
				LMSLog.d(DEBUG_TAG,"============================2"+LMSConstValue.iFilterStartAngle[sensorID]);

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramFilterStartAngle);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iFilterStartAngle[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}		
    }
	
	static public void setEditTextFilterEndAngleValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);
			int value = (int) (valueFloat*10);

			if(value != LMSConstValue.iFilterEndAngle[sensorID])
			{	
				LMSConstValue.iFilterEndAngle[sensorID] = value;

				//限定有效检测区域不能在第四象限,算法上不兼容
				if(LMSConstValue.iFilterEndAngle[sensorID]>2700)
				{
					LMSConstValue.iFilterEndAngle[sensorID] = 2700;	
				}
				
				if(LMSConstValue.iFilterEndAngle[sensorID]<LMSConstValue.iFilterStartAngle[sensorID])
				{
					LMSConstValue.iFilterEndAngle[sensorID] = LMSConstValue.iFilterStartAngle[sensorID];	
				}
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramFilterEndAngle);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iFilterEndAngle[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}		
    }
	
	static public void setEditTextGroundStartAngleValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);
			int value = (int) (valueFloat*10);
			
			if(value != LMSConstValue.iGroundStartAngle[sensorID])
			{	
				LMSConstValue.iGroundStartAngle[sensorID] = value;

				if(LMSConstValue.iGroundStartAngle[sensorID]>2700)
				{
					LMSConstValue.iGroundStartAngle[sensorID] = 2700;	
				}

				if(LMSConstValue.iGroundStartAngle[sensorID]>LMSConstValue.iGroundEndAngle[sensorID])
				{
					LMSConstValue.iGroundEndAngle[sensorID] = LMSConstValue.iGroundStartAngle[sensorID];	

					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramGroundEndAngle);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iGroundEndAngle[sensorID]);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
				}
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramGroundStartAngle);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iGroundStartAngle[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}		
    }
	
	static public void setEditTextGroundEndAngleValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);
			int value = (int) (valueFloat*10);

			if(value != LMSConstValue.iGroundEndAngle[sensorID])
			{	
				LMSConstValue.iGroundEndAngle[sensorID] = value;

				//限定有效检测区域不能在第四象限,算法上不兼容
				if(LMSConstValue.iGroundEndAngle[sensorID]>2700)
				{
					LMSConstValue.iGroundEndAngle[sensorID] = 2700;	
				}
				
				if(LMSConstValue.iGroundEndAngle[sensorID]<LMSConstValue.iGroundStartAngle[sensorID])
				{
					LMSConstValue.iGroundStartAngle[sensorID] = LMSConstValue.iGroundEndAngle[sensorID];	
					
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramGroundStartAngle);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iGroundStartAngle[sensorID]);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
				}
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramGroundEndAngle);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iGroundEndAngle[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}		
    }
	
	static public void setEditTextAntiLevelValue(int sensorID,int level,String str)
    {				
		if(!str.equals("")){
			int value = Integer.valueOf(str,10);
			
			if(value != LMSConstValue.iAntiLevel[sensorID][level])
			{		
				if(value>60000)
					value = 60000;
				if(value!=0)
				{
					if(level<LMSConstValue.ANTI_LEVEL-1)
					{
						for(int levelLoop=level+1;levelLoop<LMSConstValue.ANTI_LEVEL;levelLoop++)
						{
							if(LMSConstValue.iAntiLevel[sensorID][levelLoop]!=0&&value>LMSConstValue.iAntiLevel[sensorID][levelLoop])
							{
								value=LMSConstValue.iAntiLevel[sensorID][levelLoop];
								break;
							}
						}
					}
					if(level>0)
					{
						for(int levelLoop=level-1;levelLoop>=0;levelLoop--)
						{
							if(LMSConstValue.iAntiLevel[sensorID][levelLoop]!=0&&value<LMSConstValue.iAntiLevel[sensorID][levelLoop])
							{
								value=LMSConstValue.iAntiLevel[sensorID][levelLoop];
								break;
							}
						}
					}
				}
				LMSConstValue.iAntiLevel[sensorID][level] = value;

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_ANTI_LEVEL, level);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.ANTI_LEVEL_INTENT,eventExtra);			
			}
		}
    }
	
	static public void setTextFieldAngleLROffsetValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);			
			float value = (float)((int)(valueFloat*100))/10;
			
			if(value != LMSConstValue.fAngleLROffset[sensorID])
			{	
				LMSConstValue.fAngleLROffset[sensorID] = value;
				if(LMSConstValue.fAngleLROffset[sensorID]>LMSConstValue.MAX_ANGLE_OFFSET)
				{
					LMSConstValue.fAngleLROffset[sensorID] = LMSConstValue.MAX_ANGLE_OFFSET;	
				}
				else if(LMSConstValue.fAngleLROffset[sensorID]<-LMSConstValue.MAX_ANGLE_OFFSET)
				{
					LMSConstValue.fAngleLROffset[sensorID] = -LMSConstValue.MAX_ANGLE_OFFSET;	
				}
	    						
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramAngleLROffset);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.fAngleLROffset[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}
    }
	
	static public void setTextFieldAngleFBOffsetValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);
			float value = (float)((int)(valueFloat*100))/10;
			
			if(value != LMSConstValue.fAngleFBOffset[sensorID])
			{	
				LMSConstValue.fAngleFBOffset[sensorID] = value;
				if(LMSConstValue.fAngleFBOffset[sensorID]>LMSConstValue.MAX_ANGLE_OFFSET)
				{
					LMSConstValue.fAngleFBOffset[sensorID] = LMSConstValue.MAX_ANGLE_OFFSET;	
				}
				else if(LMSConstValue.fAngleFBOffset[sensorID]<-LMSConstValue.MAX_ANGLE_OFFSET)
				{
					LMSConstValue.fAngleFBOffset[sensorID] = -LMSConstValue.MAX_ANGLE_OFFSET;	
				}
	    						
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramAngleFBOffset);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.fAngleFBOffset[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}
    }
	
	static public void setTextFieldAngleRotateOffsetValue(String str,int sensorID)
    {		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);
			float value = (float)((int)(valueFloat*100))/10;
			
			if(value != LMSConstValue.fAngleRotateOffset[sensorID])
			{	
				LMSConstValue.fAngleRotateOffset[sensorID] = value;
				if(LMSConstValue.fAngleRotateOffset[sensorID]>LMSConstValue.MAX_ANGLE_OFFSET)
				{
					LMSConstValue.fAngleRotateOffset[sensorID] = LMSConstValue.MAX_ANGLE_OFFSET;	
				}
				else if(LMSConstValue.fAngleRotateOffset[sensorID]<-LMSConstValue.MAX_ANGLE_OFFSET)
				{
					LMSConstValue.fAngleRotateOffset[sensorID] = -LMSConstValue.MAX_ANGLE_OFFSET;	
				}
	    						
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramAngleRotateOffset);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.fAngleRotateOffset[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}
    }

	static void resetCarRoadWidthValue()
	{
		int carRoadWidthSum = 0;
		for(int i = 0;i<LMSConstValue.MAX_CAR_ROAD_NUM;i++)
		{
			if(i == LMSConstValue.iCarRoadNum-1)
			{
				LMSConstValue.iCarRoadWidth[i] = LMSConstValue.iLeftWindow[0] + LMSConstValue.iRightWindow[0] - carRoadWidthSum;
			}
			else if(i < LMSConstValue.iCarRoadNum-1)
			{
				if(LMSConstValue.iCarRoadWidth[i]+carRoadWidthSum>LMSConstValue.iLeftWindow[0] + LMSConstValue.iRightWindow[0])
					LMSConstValue.iCarRoadWidth[i] = LMSConstValue.iLeftWindow[0] + LMSConstValue.iRightWindow[0] - carRoadWidthSum;
			}
			else
				LMSConstValue.iCarRoadWidth[i] = 0;
			
			LMSLog.d(DEBUG_TAG,"iCarRoadWidth["+i+"]:"+LMSConstValue.iCarRoadWidth[i]);

			carRoadWidthSum += LMSConstValue.iCarRoadWidth[i];
		}
		
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_ROAD_WIDHT_CHANGE_INTENT);
	}
	
	static public void setEditTextCarRoadWidthValue(int carRoad,String str)
    {				
		if(!str.equals("")){
			int value = Integer.valueOf(str,10);
			
			if(value != LMSConstValue.iCarRoadWidth[carRoad])
			{				
				LMSConstValue.iCarRoadWidth[carRoad] = value;

				resetCarRoadWidthValue();
			}
		}
    }
	
	static public void setCarRoadNum(String str)
    {		
		if(!str.equals("")){
			int value = Integer.valueOf(str,10);
			
			if(value<1)
				value = 1;
			else if(value>LMSConstValue.MAX_CAR_ROAD_NUM)
				value = LMSConstValue.MAX_CAR_ROAD_NUM;
			
			if(value != LMSConstValue.iCarRoadNum)
			{
				LMSConstValue.iCarRoadNum = value;
					
				resetCarRoadWidthValue();
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramCarRoadNum);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iCarRoadNum );
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
			}
		}
    }
	
	static public void enlargeFrontEdgeWindowValue(int sensorID,boolean enlarge)
    {
		int step = 0;
		if(LMSConstValue.iFrontEdgeWindow[sensorID] >= 1000)
			step = 500;
		if(LMSConstValue.iFrontEdgeWindow[sensorID] >= -1500)
			step = 100;
		else
			step = 50;
		
		if(enlarge)
		{				
			LMSConstValue.iFrontEdgeWindow[sensorID]-=step;
		}
		else
		{
			LMSConstValue.iFrontEdgeWindow[sensorID]+=step;
		}
		
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramFrontEdgeWindow);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iFrontEdgeWindow[sensorID]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
    }
	
	static public void enlargeLREdgeWindowValue(int sensorID,boolean enlarge)
    {
		int step = 0;
		if(LMSConstValue.iLREdgeWindow[sensorID] >= 1000)
			step = 500;
		if(LMSConstValue.iLREdgeWindow[sensorID] >= -1500)
			step = 100;
		else
			step = 50;
		
		if(enlarge)
		{				
			LMSConstValue.iLREdgeWindow[sensorID]-=step;
		}
		else
		{
			LMSConstValue.iLREdgeWindow[sensorID]+=step;
		}
		
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramLREdgeWindow);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iLREdgeWindow[sensorID]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
    }
	
	static public void setXMoveLeftValue(int sensorID)
    {
		if(LMSConstValue.iXMove[sensorID] > -LMSConstValue.MOVE_STEP)
		{
			LMSConstValue.iXMove[sensorID]--;
		
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramXMove);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iXMove[sensorID]);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
		}
    }
	
	static public void setXMoveRightValue(int sensorID)
    {
		if(LMSConstValue.iXMove[sensorID] < LMSConstValue.MOVE_STEP)
		{
			LMSConstValue.iXMove[sensorID]++;
		
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramXMove);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iXMove[sensorID]);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
		}
    }
	
	static public void setYMoveDownValue(int sensorID)
    {
		if(LMSConstValue.iYMove[sensorID] > -LMSConstValue.MOVE_STEP)
		{
			LMSConstValue.iYMove[sensorID]--;
		
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramYMove);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iYMove[sensorID]);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
		}
    }
	
	static public void setYMoveUpValue(int sensorID)
    {
		if(LMSConstValue.iYMove[sensorID] < LMSConstValue.MOVE_STEP)
		{
			LMSConstValue.iYMove[sensorID]++;
		
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramYMove);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iYMove[sensorID]);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
		}
    }
	
	static public void changeLRTurn(int sensorID)
	{
		LMSConstValue.bLRTurn[sensorID] = !LMSConstValue.bLRTurn[sensorID];

		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramLRturn);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bLRTurn[sensorID]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	}
	
	static public void changeUpDownTurn(int sensorID)
	{
		LMSConstValue.bUpDownTurn[sensorID] = !LMSConstValue.bUpDownTurn[sensorID];

		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramUpDownTurn);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bUpDownTurn[sensorID]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	}
	
	/*
	static public void changeButtonRotate(int sensorID)
	{
		LMSConstValue.bRotateTurn[sensorID] = !LMSConstValue.bRotateTurn[sensorID];

		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramRotateTurn);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bRotateTurn[sensorID]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	}
	*/
	
	static public void changeButtonBoolAngleDisplaye(int sensorID)
	{
		LMSConstValue.bAngleDisplay[sensorID] = !LMSConstValue.bAngleDisplay[sensorID];

		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramBoolAngleDisplay);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bAngleDisplay[sensorID]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	}
	
	static public void setEditTextAngleDisplayValue(String str,int sensorID)
	{		
		if(!str.equals("")){
			float valueFloat = Float.valueOf(str);
			int value = (int) (valueFloat*10);

			if(value != LMSConstValue.iAngleDisplay[sensorID])
			{	
				LMSConstValue.iAngleDisplay[sensorID] = value;

				/*
				if(LMSConstValue.iAngleDisplay[sensorID]>1800)
				{
					LMSConstValue.iAngleDisplay[sensorID] = 1800;	
				}

				if(LMSConstValue.iAngleDisplay[sensorID]<0)
				{
					LMSConstValue.iAngleDisplay[sensorID] = 0;	
				}
				*/
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramAngleDisplay);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iAngleDisplay[sensorID]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}		
    }
	
	static public void setConveyerSpeed(String str)
    {		
		if(!str.equals("")){
			int value = Integer.valueOf(str);
			
			if(value != LMSConstValue.iConVeyerSpeed)
			{	
				LMSConstValue.iConVeyerSpeed = value;
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramConVeyerSpeed);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iConVeyerSpeed);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}
    }
	
	static public void setMinPacketWidth(String str)
    {		
		if(!str.equals("")){
			int value = Integer.valueOf(str);
			
			if(value != LMSConstValue.iMinPacketWidth)
			{	
				LMSConstValue.iMinPacketWidth = value;
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramMinPacketWidth);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iMinPacketWidth);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}
    }

	static public void setNoReadNoVolumnMachingTime(String str)
    {		
		if(!str.equals("")){
			int value = Integer.valueOf(str);
			
			if(value != LMSConstValue.iNoReadNoVolumnMachingTimeInteval)
			{	
				LMSConstValue.iNoReadNoVolumnMachingTimeInteval = value;
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramNoReadNoVolumnMachingTimeInteval);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.iNoReadNoVolumnMachingTimeInteval);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	   		}
		}
    }

	static public void resetFixLed(int sensorID)
    {		
		LMSConstValue.bFixLed[sensorID] = !LMSConstValue.bFixLed[sensorID];
		
		LMSLog.d(DEBUG_TAG+sensorID,"bFixLed="+LMSConstValue.bFixLed[sensorID]);

		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramFixLed);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bFixLed[sensorID]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
    }
	
	static public void resetWHPosition()
	{
		if(LMSConstValue.enumWHPositionSetType.key.equals(LMSConstValue.EnumWHPositionSetType.AUTO_SET))
		{
			int sensorID = LMSConstValue.getLongSensorID();

			if(LMSConstValue.iLeftWindow[sensorID] > LMSConstValue.iRightWindow[sensorID])
			{
				LMSConstValue.bWHPositionLess90 = false;
			}
			else
			{
				LMSConstValue.bWHPositionLess90 = true;				
			}
		}	
		else
		{
			if(LMSConstValue.enumWHPositonType.key.equals(LMSConstValue.EnumWHPositonType.WH_POSITION_AT_LONG_RIGHT))
			{	
				LMSConstValue.bWHPositionLess90 = true;
			}
			else
			{	
				LMSConstValue.bWHPositionLess90 = false;
			}				
		}
	}
	
	static public void setSpinnerSensorType(int sensorID,String str)
	{
		LMSConstValue.sensorType[sensorID].key = LMSConstValue.sensorType[sensorID].getKeyFromValue(str);

		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramSensorType);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.sensorType[sensorID].key);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	}
	
	static public void CarTypeChangeTrig(String sValue)
	{
		if(!CarTypeAdapter.sNvramCarTypeString.sValue.equals(sValue))
		{
			CarTypeAdapter.sNvramCarTypeString.sValue = sValue;
			
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, CarTypeAdapter.sNvramCarTypeString.nvramStr);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, CarTypeAdapter.sNvramCarTypeString.sValue);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
			
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
		}
	}
	
	static public void setSensorIP(int sensorID,String str)
    {
		if(!str.equals("")&&!(LMSConstValue.SENSOR_IP[sensorID].equals(str))){			
			LMSConstValue.SENSOR_IP[sensorID] = str;
			
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramSensorIP);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.SENSOR_IP[sensorID]);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
		}
    }
	
	static public void setSensorPort(int sensorID,String str)
    {
		if(!str.equals("")&&(LMSConstValue.SENSOR_PORT[sensorID] != Integer.valueOf(str))){		
			LMSLog.d(DEBUG_TAG,"str="+str+" SENSOR_PORT---------------:"+LMSConstValue.SENSOR_PORT[sensorID]);
			
			LMSConstValue.SENSOR_PORT[sensorID] = Integer.valueOf(str);
			
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramSensorPort);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.SENSOR_PORT[sensorID]);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
		}
    }
			
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {

			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
						
			int sensorID = 0;
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
			
			if (eventType.equals(LMSConstValue.TRIG_GET_BASE_DATA_INTENT))
			{
				if(LMSConstValue.bGetGroundFlat == false)
				{
					resetSystemStateString("正在取基准值，请稍候",sensorID);  
				}
			}
			else if (eventType.equals(LMSConstValue.DEVICE_STATE_INTENT))
			{
				LMSConstValue.enumDeviceStateType deviceState = null;
				
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_DEVICE_STATE_TYPE))
					deviceState = (enumDeviceStateType)eventExtra.get(LMSConstValue.INTENT_EXTRA_DEVICE_STATE_TYPE);
							
				setDeviceState(deviceState,sensorID);			
			}
			else if (eventType.equals(LMSConstValue.SERVER_SEND_BASE_DATA_INTENT))
			{
				if(LMSConstValue.bBaseValid[sensorID] == true)
				{
					resetSystemStateString("基准值设置成功",sensorID);  				
				}
				else
				{
				    if(LMSConstValue.bInitial[sensorID] == false)
				    {
				    	resetSystemStateString("基准值设置失败",sensorID);
				    }
				    else
				    {
				    	resetSystemStateString("请重取基准值",sensorID);
				    }
				}	
							
	        	String strParameter = CarDetectSetting.getSensorParameter(sensorID);
		   		HashMap<String, Comparable> eventExtraSend = new HashMap<String, Comparable>();
		   		eventExtraSend.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		   		eventExtraSend.put(LMSConstValue.INTENT_EXTRA_LMS_PARAMETER, strParameter);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.LMS_PARAMETER_STRING_INTENT,eventExtraSend);			
			}
		}
	}
	
	public static String serverSystemStateString;
    //错误标识，避免多次显示
	private static boolean bStateError[] = new boolean[LMSConstValue.SYSTEM_SENSOR_NUM];
	private static boolean bInvalidSensor[] = new boolean[LMSConstValue.SYSTEM_SENSOR_NUM];
	private static boolean bWifiOpenError;
	private static boolean bWifiConnectError;
	private static boolean bTransferError;
	
    public static void setDeviceState(LMSConstValue.enumDeviceStateType deviceState,int sensorID)
    {	    	
		LMSLog.d(DEBUG_TAG,"setDeviceState deviceState="+deviceState+" sensorID="+sensorID);
		
		if(deviceState == LMSConstValue.enumDeviceStateType.INVALID_SENSOR)
		{
			bInvalidSensor[sensorID] = true;
		}
		
		resetSystemStateString("NULL",sensorID);
    }
    
    //逐个扫描各种设备的状态，并显示
    public static void resetSystemStateString(String inputStr,int sensorID)
    {
    	String systemStateStringPrefix;
    	    	
    	if(LMSConstValue.boardType ==enumBoardType.SIMULATE_FILE_BOARD)
		{
			systemStateStringPrefix = "数据回放:";
		}
		else
		{
			systemStateStringPrefix = "系统状态:";
		}
    	
		bStateError[sensorID] = false;

//		LMSLog.d(DEBUG_TAG,"systemstate 000:"+inputStr);
		if(bInvalidSensor[sensorID] == true)
		{
     		bStateError[sensorID] = true;

     		LMSConstValue.SystemStateString[sensorID] = "未授权设备，请联系供应商.SN:"+LMSTelegram.serialNumber[sensorID];

    		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
    		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
    		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SERVER_SYSTEM_STATE_STRING_INTENT,eventExtra);			

			return;
		}

//		LMSLog.d(DEBUG_TAG,"systemstate 111["+sensorID+"]="+LMSConstValue.SystemStateString[sensorID]);

		if(CarTypeAdapter.bNvramPauseDetect.bValue == true)
		{
			LMSConstValue.SystemStateString[sensorID] = systemStateStringPrefix+LMSConstValue.strPauseDetect;      				    				    													
		}
		else
		{
			//inputStr为强制显示的字符串
			if(!inputStr.equals("NULL"))
			{
				LMSConstValue.SystemStateString[sensorID] = systemStateStringPrefix+inputStr;      				    				    													
			}
			else
			{
				if(bStateError[sensorID] == false)
				{
					if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
					{
						LMSConstValue.SystemStateString[sensorID] = systemStateStringPrefix+LMSConstValue.strDetecting;      				    				    							
					}
					else
					{
						LMSConstValue.SystemStateString[sensorID] = systemStateStringPrefix+CarTypeAdapter.carEnumType.getValueFromKey(CarTypeAdapter.carEnumType.key)+"检测中";      				    				    					
	
						if(LMSConstValue.bBaseValid[sensorID] == false)
						{							
							LMSConstValue.SystemStateString[sensorID] = systemStateStringPrefix+"地面不平，基准值建立失败";  					
						}
					}
				}
			}
		}
		
//    	LMSLog.d(DEBUG_TAG,"systemStateString11["+sensorID+"]="+LMSConstValue.SystemStateString[sensorID]);	    				
    	
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SERVER_SYSTEM_STATE_STRING_INTENT,eventExtra);			
    }
}
