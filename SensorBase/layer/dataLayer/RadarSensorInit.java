package layer.dataLayer;

import java.util.HashMap;

import layer.algorithmLayer.ParseLMSAckCommand;
import layer.dataLayer.LMSTelegram.enumLmsDataField;
import layer.physicLayer.LMSWorkflow;
import layer.physicLayer.PhysicLayer;
import lmsBase.LMSProductInfo;
import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSConstValue.enumDetectType;

public class RadarSensorInit {
	private String DEBUG_TAG="RadarSensorInit";

	public void init(int sensorID)
	{
		LMSProductInfo.bSensorInit[sensorID] = true;									

		float START_ANGLE = 0;
		float ANGLE_RESOLUTION = 0;
		int SCAN_FREQ = 0;
		int MEASURE_AMOUNT = 0;

		if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.RADAR_B))
		{
			ParseLMSAckCommand.angleResolution[sensorID] = 0.35164835164835164835164835164835;
			ParseLMSAckCommand.scanFreq[sensorID] = 60;
			ParseLMSAckCommand.measureData16bit_amount[sensorID] = LMSConstValue.BEA_MDI_LENGTH/2;
			ParseLMSAckCommand.startAngle[sensorID] = (180-96)/2;
			ParseLMSAckCommand.stopAngle[sensorID] = 180-(180-96)/2;

			ParseLMSAckCommand.degree90Index[sensorID] = (int) ((90-(float)LMSConstValue.fAngleLROffset[sensorID]/10)/ParseLMSAckCommand.angleResolution[sensorID]);
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.RADAR_FS))
		{
			ParseLMSAckCommand.angleResolution[sensorID] = 0.6547;
			ParseLMSAckCommand.scanFreq[sensorID] = 20;
			ParseLMSAckCommand.measureData16bit_amount[sensorID] = LMSConstValue.BEA_FS_MDI_LENGTH/2;
			ParseLMSAckCommand.startAngle[sensorID] = (180-110)/2;
			ParseLMSAckCommand.stopAngle[sensorID] = 180-(180-110)/2;

			ParseLMSAckCommand.degree90Index[sensorID] = (int) ((90-(float)LMSConstValue.fAngleLROffset[sensorID]/10)/ParseLMSAckCommand.angleResolution[sensorID]);
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LMS400))
		{
			ParseLMSAckCommand.angleResolution[sensorID] = 0.1;
			ParseLMSAckCommand.scanFreq[sensorID] = 270;
			ParseLMSAckCommand.measureData16bit_amount[sensorID] = 700;
			ParseLMSAckCommand.startAngle[sensorID] = 0;
			ParseLMSAckCommand.stopAngle[sensorID] = 70;

//			ParseLMSAckCommand.degree90Index[sensorID] = (int) ((90-(float)LMSConstValue.fAngleLROffset[sensorID]/10)/ParseLMSAckCommand.angleResolution[sensorID]);
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.VMD500))
		{
			ParseLMSAckCommand.angleResolution[sensorID] = 0.125;
			ParseLMSAckCommand.scanFreq[sensorID] = 50;
			ParseLMSAckCommand.measureData16bit_amount[sensorID] = 560;
			ParseLMSAckCommand.startAngle[sensorID] = 0;
			ParseLMSAckCommand.stopAngle[sensorID] = 70;

//			ParseLMSAckCommand.degree90Index[sensorID] = (int) ((90-(float)LMSConstValue.fAngleLROffset[sensorID]/10)/ParseLMSAckCommand.angleResolution[sensorID]);
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.VMD500_F))
		{
			ParseLMSAckCommand.angleResolution[sensorID] = 0.125;
			ParseLMSAckCommand.scanFreq[sensorID] = 330;
			ParseLMSAckCommand.measureData16bit_amount[sensorID] = 560;
			ParseLMSAckCommand.startAngle[sensorID] = 0;
			ParseLMSAckCommand.stopAngle[sensorID] = 70;

//			ParseLMSAckCommand.degree90Index[sensorID] = (int) ((90-(float)LMSConstValue.fAngleLROffset[sensorID]/10)/ParseLMSAckCommand.angleResolution[sensorID]);
		}
		else
		{
			ParseLMSAckCommand.stopAngle[sensorID] = 180;					

			LMSTelegram.parseLMSDataFrame(sensorID,ParseLMSAckCommand.resultStr[sensorID],enumLmsDataField.LMS_DATA_FIELD_16bitChannels);	

			if(LMSConstValue.boardType == LMSConstValue.enumBoardType.SERVER_BOARD)
			{
				if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WIDTH_HEIGHT_1_DETECT)
				{									
					if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LMS1XX))
					{
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.25;
						SCAN_FREQ = 25;
						MEASURE_AMOUNT = 721;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{	
							LMSLog.d(DEBUG_TAG, "startAngle["+sensorID+"]="+ParseLMSAckCommand.startAngle[sensorID]);
							LMSLog.d(DEBUG_TAG, "count["+sensorID+"]="+ParseLMSAckCommand.measureData16bit_amount[sensorID]);
							LMSLog.d(DEBUG_TAG, "angleResolution=["+sensorID+"]="+ParseLMSAckCommand.angleResolution[sensorID]);
							LMSLog.d(DEBUG_TAG, "scanFreq["+sensorID+"]="+ParseLMSAckCommand.scanFreq[sensorID]);
	
							LMSWorkflow.lmsWorkflowSet_25HZ_25ANGLE_0_180_OUTPUT(sensorID);
										
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
					}
					else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.TIM551))
					{
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 1;
						SCAN_FREQ = 15;
						MEASURE_AMOUNT = 181;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{
							LMSLog.d(DEBUG_TAG, "startAngle["+sensorID+"]="+ParseLMSAckCommand.startAngle[sensorID]);
							LMSLog.d(DEBUG_TAG, "count["+sensorID+"]="+ParseLMSAckCommand.measureData16bit_amount[sensorID]);
							LMSLog.d(DEBUG_TAG, "angleResolution=["+sensorID+"]="+ParseLMSAckCommand.angleResolution[sensorID]);
							LMSLog.d(DEBUG_TAG, "scanFreq["+sensorID+"]="+ParseLMSAckCommand.scanFreq[sensorID]);
	
							LMSWorkflow.lmsWorkflowSet_15HZ_100ANGLE_0_180_OUTPUT(sensorID);
							    	
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
					}
				}
				else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.LM1)
				{
					ParseLMSAckCommand.angleResolution[sensorID] = 0.1f;
					ParseLMSAckCommand.scanFreq[sensorID] = 270;
					ParseLMSAckCommand.measureData16bit_amount[sensorID] = 700;
					ParseLMSAckCommand.startAngle[sensorID] = 55;
					ParseLMSAckCommand.stopAngle[sensorID] = (180-55)/2;

					ParseLMSAckCommand.degree90Index[sensorID] = (int) ((90-(float)LMSConstValue.fAngleLROffset[sensorID]/10)/ParseLMSAckCommand.angleResolution[sensorID]);
				}
				else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH_1_HIGH_SPEED)
				{
					if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LMS511))
					{
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.5;
						SCAN_FREQ = 75;
						MEASURE_AMOUNT = 361;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{				
							LMSWorkflow.lmsWorkflowSet_75HZ_50ANGLE_0_180_OUTPUT(sensorID);
	
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
					}
					else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LMS1XX))
					{
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.5;
						SCAN_FREQ = 50;
						MEASURE_AMOUNT = 361;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{				
							LMSWorkflow.lmsWorkflowSet_50HZ_50ANGLE_0_180_OUTPUT(sensorID);
	
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
					}
				}
				else if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH2)
				{
					if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID)
					{
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.25;
						SCAN_FREQ = 25;
						MEASURE_AMOUNT = 721;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{				
							LMSLog.d(DEBUG_TAG, "startAngle["+sensorID+"]="+ParseLMSAckCommand.startAngle[sensorID]);
							LMSLog.d(DEBUG_TAG, "count["+sensorID+"]="+ParseLMSAckCommand.measureData16bit_amount[sensorID]);
							LMSLog.d(DEBUG_TAG, "angleResolution=["+sensorID+"]="+ParseLMSAckCommand.angleResolution[sensorID]);
							LMSLog.d(DEBUG_TAG, "scanFreq["+sensorID+"]="+ParseLMSAckCommand.scanFreq[sensorID]);

							LMSWorkflow.lmsWorkflowSet_25HZ_25ANGLE_5_175_OUTPUT(sensorID);
//									LMSWorkflow.lmsWorkflowSet_25HZ_25ANGLE_10_170_OUTPUT(sensorID);
							
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}

					}
					else if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
					{
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.25;
						SCAN_FREQ = 25;
						MEASURE_AMOUNT = 721;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{	
							LMSWorkflow.lmsWorkflowSet_25HZ_25ANGLE_0_180_OUTPUT(sensorID);
	
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
					}
				}
				else if(
					LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH1_L1_SAME
					||LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH2_L1_SAME
				)
				{

					START_ANGLE = 0;
					ANGLE_RESOLUTION = (float) 0.25;
					SCAN_FREQ = 25;
					MEASURE_AMOUNT = 721;
					
					if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
						||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
						||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
						||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
					{	
						LMSWorkflow.lmsWorkflowSet_25HZ_25ANGLE_0_180_OUTPUT(sensorID);

						CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
					}
				}
				else if(
					LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
				{
					if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.WH_SENSOR_ID)
					{
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.5;
						SCAN_FREQ = 50;
						MEASURE_AMOUNT = 361;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{	
							LMSWorkflow.lmsWorkflowSet_50HZ_50ANGLE_0_180_OUTPUT(sensorID);
	
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
					}
					else if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
					{
						//*
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.25;
						SCAN_FREQ = 25;
						MEASURE_AMOUNT = 721;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{	
							LMSWorkflow.lmsWorkflowSet_25HZ_25ANGLE_0_180_OUTPUT(sensorID);
	
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
						//*/
						/*
						START_ANGLE = 0;
						ANGLE_RESOLUTION = (float) 0.5;
						SCAN_FREQ = 50;
						MEASURE_AMOUNT = 361;
						
						if(ParseLMSAckCommand.startAngle[sensorID] != START_ANGLE
							||ParseLMSAckCommand.measureData16bit_amount[sensorID] != MEASURE_AMOUNT
							||ParseLMSAckCommand.angleResolution[sensorID] != ANGLE_RESOLUTION
							||ParseLMSAckCommand.scanFreq[sensorID] != SCAN_FREQ)
						{	
							LMSWorkflow.lmsWorkflowSet_50HZ_50ANGLE_0_180_OUTPUT(sensorID);
	
							CarDetectSetting.resetSystemStateString("检测头初始化参数中,请稍候",sensorID);  
						}
						*/
					}
				}
			}
			
			if(ParseLMSAckCommand.startAngle[sensorID]<0)
			{
				LMSLog.errorDialog(null,"不支持负角度");
				System.exit(0);
			}
		}
		
		LMSConstValue.physicStartPoint[sensorID] = (int) (ParseLMSAckCommand.startAngle[sensorID]/ParseLMSAckCommand.angleResolution[sensorID]);			
		LMSConstValue.physicEndPoint[sensorID] = (int) (ParseLMSAckCommand.stopAngle[sensorID]/ParseLMSAckCommand.angleResolution[sensorID]);			

		CarDetectSetting.resetFilterPoint(sensorID);
		PhysicLayer.CAROUT_RECORD_COUNT[sensorID] = ParseLMSAckCommand.scanFreq[sensorID]*10;
		
		if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LMS511))
		{
			ParseLMSAckCommand.beamDiameterAtFrontScreen[sensorID] = 13;
			ParseLMSAckCommand.beamDiameterArgument[sensorID] = (float) 0.009;
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LMS1XX))
		{
			ParseLMSAckCommand.beamDiameterAtFrontScreen[sensorID] = 8;
			ParseLMSAckCommand.beamDiameterArgument[sensorID] = (float) 0.015;
		}
		else if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.TIM551))
		{
			ParseLMSAckCommand.beamDiameterAtFrontScreen[sensorID] = 6;
			ParseLMSAckCommand.beamDiameterArgument[sensorID] = (float) 0.014;
		}

		if(ParseLMSAckCommand.scanFreq[sensorID] == 50)
		{
			ParseLMSAckCommand.discardTail[sensorID] = 20;
		}
		else if(ParseLMSAckCommand.scanFreq[sensorID] == 25)
		{
			ParseLMSAckCommand.discardTail[sensorID] = 10;
		}
		else if(ParseLMSAckCommand.scanFreq[sensorID] == 15)
		{
			ParseLMSAckCommand.discardTail[sensorID] = 6;
		}
		
		ParseLMSAckCommand.degree90Index[sensorID] = (int) ((90-(float)LMSConstValue.fAngleLROffset[sensorID]/10)/ParseLMSAckCommand.angleResolution[sensorID]);
	
		//干扰光圈其实不会太大，一般最多5个点（真的跟角分辨率有关吗）
		LMSConstValue.I_LR_POINT_COUNT[sensorID] = (int) (1+1/ParseLMSAckCommand.angleResolution[sensorID]);
		LMSConstValue.I_CENTER_POINT_COUNT[sensorID] = (int) (2/ParseLMSAckCommand.angleResolution[sensorID]);
		LMSConstValue.MIN_CONTINUOUS_BLOCK_POINT_NUM[sensorID] = (int)(3/ParseLMSAckCommand.angleResolution[sensorID]);
		
    	String msg = CarDetectSetting.getSensorParameter(sensorID);
   		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
   		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
   		eventExtra.put(LMSConstValue.INTENT_EXTRA_LMS_PARAMETER, msg);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.LMS_PARAMETER_STRING_INTENT,eventExtra);			
	}
}
