package ThreeD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import layer.algorithmLayer.Contour;
import layer.algorithmLayer.ContourMaxPoint;
import layer.algorithmLayer.ThreeDPoint;
import layer.algorithmLayer.ZPlane;
import lmsBase.LMSProductInfo;
import AppBase.appBase.CarTypeAdapter;
import CarAppAlgorithm.CarAlgorithmAxle;
import CarAppAlgorithm.CarAlgorithmGuaChe;
import CarAppAlgorithm.CarAlgorithmHeight;
import CarAppAlgorithm.CarAlgorithmLanBan;
import CarAppAlgorithm.CarAlgorithmLength;
import CarAppAlgorithm.CarAlgorithmWidth;
import CarAppAlgorithm.CarAlgorithmXiao;
import Comparator.ComparatorThreeDPointHeight;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import algorithmLayer.ContourFilterAlgorithm;

public class ThreeDCarAlgorithm {
	private final static String DEBUG_TAG="ThreeDCarAlgorithm";

	void width_filter_WH1_L1(Contour contour,ArrayList<ZPlane> zPlaneFilterResultList[])
	{
		int zStart = 0;
		int zEnd = 0;
		if(LMSConstValue.bNvramRulerCalibration.isValid == true
			&&LMSConstValue.bNvramRulerCalibration.bValue == true)
		{
			zStart = -5000;
			zEnd = contour.carLength;
		}
		else
		{
			zStart = 2500;
			zEnd = contour.carLength;
		}
		
		ContourMaxPoint widthMaxPoint[];
		widthMaxPoint = new CarAlgorithmWidth().widthFilter(contour, zStart, zEnd);	
		contour.carWidth = Math.abs(widthMaxPoint[contour.maxPointSensorID].maxValue-widthMaxPoint[contour.minPointSensorID].maxValue);	
		contour.widthMaxPoint[contour.maxPointSensorID] = widthMaxPoint[contour.maxPointSensorID];
		contour.widthMaxPoint[contour.minPointSensorID] = widthMaxPoint[contour.minPointSensorID];
	}
	
	void width_filter_WH2_L1(Contour contour,ArrayList<ZPlane> zPlaneFilterResultList[])
	{
		ContourMaxPoint widthMaxPoint[];
		int zStart = 0;

		//==========================================================================
		widthMaxPoint = new CarAlgorithmWidth().widthFilter(contour, -5000, contour.carLength);
		if(widthMaxPoint[contour.maxPointSensorID] != null && widthMaxPoint[contour.minPointSensorID] != null)
		{
			contour.carWidth_RearViewMirror = Math.abs(widthMaxPoint[contour.maxPointSensorID].maxValue-widthMaxPoint[contour.minPointSensorID].maxValue);	
		}
		
		//==========================================================================
		if(LMSConstValue.bNvramRulerCalibration.isValid == true
			&&LMSConstValue.bNvramRulerCalibration.bValue == true)
		{
			zStart = -5000;
		}
		else
		{
			if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_DIAOBI_CHE))
			{
				zStart = 5000;	
			}
			else if(contour.bGuaChe == true)
			{
				zStart = 1000;
			}
			else
			{
				zStart = 2500;
			}
		}
		//==========================================================================
		widthMaxPoint = new CarAlgorithmWidth().widthFilter(contour, zStart, contour.carLength);
		if(widthMaxPoint[contour.maxPointSensorID] != null && widthMaxPoint[contour.minPointSensorID] != null)
		{
			contour.carWidth = Math.abs(widthMaxPoint[contour.maxPointSensorID].maxValue-widthMaxPoint[contour.minPointSensorID].maxValue);	
			contour.widthMaxPoint[contour.maxPointSensorID] = widthMaxPoint[contour.maxPointSensorID];
			contour.widthMaxPoint[contour.minPointSensorID] = widthMaxPoint[contour.minPointSensorID];
		}
		
		if(contour.bGuaChe == true)
		{
			widthMaxPoint = new CarAlgorithmWidth().widthFilter(contour, zStart, (int) contour.carGuaCheZ);	
			if(widthMaxPoint[contour.maxPointSensorID] != null && widthMaxPoint[contour.minPointSensorID] != null)
			{
				contour.carQianYingWidth = Math.abs(widthMaxPoint[contour.maxPointSensorID].maxValue-widthMaxPoint[contour.minPointSensorID].maxValue);	
				contour.widthMaxPointQianYing[contour.maxPointSensorID] = widthMaxPoint[contour.maxPointSensorID];
				contour.widthMaxPointQianYing[contour.minPointSensorID] = widthMaxPoint[contour.minPointSensorID];
			}
			
			widthMaxPoint = new CarAlgorithmWidth().widthFilter(contour, (int) contour.carGuaCheZ, contour.carLength);	
			if(widthMaxPoint[contour.maxPointSensorID] != null && widthMaxPoint[contour.minPointSensorID] != null)
			{
				contour.carGuaCheWidth = Math.abs(widthMaxPoint[contour.maxPointSensorID].maxValue-widthMaxPoint[contour.minPointSensorID].maxValue);	
				contour.widthMaxPointGuaChe[contour.maxPointSensorID] = widthMaxPoint[contour.maxPointSensorID];
				contour.widthMaxPointGuaChe[contour.minPointSensorID] = widthMaxPoint[contour.minPointSensorID];
			}
		}
	}
	
	public void threeDCarAlgorithm(Contour contour)
	{
		LMSLog.d(DEBUG_TAG,"contour.lightCurtainCarInLineList 1="+contour.lightCurtainCarInLineList.size());

		//先生成高队列
		contour.carHeight = 0;
		for(int sensorID=0;sensorID<LMSConstValue.LMS_WH_SENSOR_NUM;sensorID++)
		{
			ArrayList<ThreeDPoint> heightListAll =  new ArrayList<ThreeDPoint>();
			new CarAlgorithmHeight().generateHeightList(contour, heightListAll, -LMSConstValue.INVALID_X, LMSConstValue.INVALID_X);
			
			//按高排列后进行高滤波
			ComparatorThreeDPointHeight comparator=new ComparatorThreeDPointHeight();
			Collections.sort(heightListAll, comparator);
			contour.heightMaxPoint = new CarAlgorithmHeight().heightFilter(false, -1, contour, heightListAll);
			int carHeight= (int)contour.heightMaxPoint.maxValue;
			
	//				if(sensorID == 0)
			//兼容地面安装：默认用sensor 0作为高判断;如果是地面安装的情况,则不作为判断
			if(contour.carHeight==0 && carHeight!=0)
			{
				contour.carHeight = carHeight;
				
				LMSLog.d(DEBUG_TAG, "heightList contour.carHeight="+contour.carHeight);
			}
		}
			
		//=====================================================================
		new CarAlgorithmLength().carLengthAfterfilter(contour);
		
		//=====================================================================
		new CarAlgorithmGuaChe().guaCheAlgo(contour);
		new CarAlgorithmGuaChe().qianYingAlgoWithGround(contour,contour.whBubble);
		new CarAlgorithmGuaChe().qianYingAlgoWithLightCurtain(contour);
		contour.carGuaCheLength = new CarAlgorithmGuaChe().determineCarGuacheLength(contour);

		//=====================================================================
		if(LMSConstValue.isMyMachine())
		{
			new ContourFilterAlgorithm().volumnAlgo(contour);
		}
		
		//=================================================================
		//先清空数据
		contour.iCarZNum = 0;
		contour.iCarGuaCheZNum = 0;
		contour.iCarQianYingZNum = 0;
		//轴距算法
		new CarAlgorithmAxle().getCarAxle(contour,contour.whBubble);
		
		if(LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
			||LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.XZY_1600)
		)
		{
			new CarAlgorithmXiao().getIXiao(contour);
			new CarAlgorithmAxle().getQianYingCarAxle(contour);
		}				
		new CarAlgorithmAxle().getGuaCheCarAxle(contour);
		
		if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.WH1_L1_SAME)
		{
			width_filter_WH1_L1(contour,contour.contourFilter.zPlaneFilterResultList);
		}
		else
		{
			width_filter_WH2_L1(contour,contour.contourFilter.zPlaneFilterResultList);						

			new CarAlgorithmGuaChe().qianYingHeight(contour);

			new CarAlgorithmLanBan().lanBanHeightAlgo(contour);				
		}
		
		//--------------------------------------------------------------
		//挂车斜率算法
		if(LMSConstValue.bNvramGuaCheLean.bValue == true)
		{
			new CarAlgorithmGuaChe().fittingCarGuaCheHeight(contour);			
		}
		else
		{
			contour.carGuaCheHeight = contour.carGuaCheHeightLeanOriginal;
		}
	}
	
	void threeDCarOutCompensate(Contour contour)
	{
		contour.carWidth+=LMSConstValue.iNvramWidthOutputCompensate.iValue;
		contour.carWidth=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_W, contour.carWidth);
		contour.carHeight+=LMSConstValue.iNvramHeightOutputCompensate.iValue;
		contour.carHeight=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_H, contour.carHeight);
		contour.carLength+=LMSConstValue.iNvramLengthOutputCompensate.iValue;	
		contour.carLength=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_L, contour.carLength);
		
		if(contour.carWidth_RearViewMirror != 0)
		{
			contour.carWidth_RearViewMirror+=LMSConstValue.iNvramWidthOutputCompensate.iValue;
			contour.carWidth_RearViewMirror=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_W, contour.carWidth_RearViewMirror);
		}
		if(contour.carHeight_RearViewMirror != 0)
		{
			contour.carHeight_RearViewMirror+=LMSConstValue.iNvramHeightOutputCompensate.iValue;
			contour.carHeight_RearViewMirror=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_H, contour.carHeight_RearViewMirror);
		}
		if(contour.carLength_RearViewMirror != 0)
		{
			contour.carLength_RearViewMirror+=LMSConstValue.iNvramLengthOutputCompensate.iValue;	
			contour.carLength_RearViewMirror=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_L, contour.carLength_RearViewMirror);
		}
		
		if(contour.carWidth_FrontMirror != 0)
		{
			contour.carWidth_FrontMirror+=LMSConstValue.iNvramWidthOutputCompensate.iValue;
			contour.carWidth_FrontMirror=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_W, contour.carWidth_FrontMirror);
		}
		if(contour.carHeight_FrontMirror != 0)
		{
			contour.carHeight_FrontMirror+=LMSConstValue.iNvramHeightOutputCompensate.iValue;
			contour.carHeight_FrontMirror=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_H, contour.carHeight_FrontMirror);
		}
		if(contour.carLength_FrontMirror != 0)
		{
			contour.carLength_FrontMirror+=LMSConstValue.iNvramLengthOutputCompensate.iValue;	
			contour.carLength_FrontMirror=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_L, contour.carLength_FrontMirror);
		}
		
		if(contour.carGuaCheLength != 0)
		{
			contour.carGuaCheLength+=LMSConstValue.iNvramLengthOutputCompensate.iValue;											
			contour.carGuaCheLength=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_GL, contour.carGuaCheLength);
			contour.carGuaCheWidth+=LMSConstValue.iNvramWidthOutputCompensate.iValue;
			contour.carGuaCheWidth=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_GW, contour.carGuaCheWidth);
			contour.carGuaCheHeight+=LMSConstValue.iNvramHeightOutputCompensate.iValue;
			contour.carGuaCheHeight=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_GH, contour.carGuaCheHeight);
			contour.carGuaCheHeightLeanOriginal+=LMSConstValue.iNvramHeightOutputCompensate.iValue;
			contour.carGuaCheHeightLeanOriginal=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_GH, contour.carGuaCheHeightLeanOriginal);
		}
		
		if(contour.bQianYing == true)
		{
			contour.carQianYingWidth+=LMSConstValue.iNvramWidthOutputCompensate.iValue;
			contour.carQianYingWidth=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_QW, contour.carQianYingWidth);
			contour.carQianYingHeight+=LMSConstValue.iNvramHeightOutputCompensate.iValue;											
			contour.carQianYingHeight=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_QH, contour.carQianYingHeight);
			//牵引车,光栅使用的是差分算法,无需做长补偿
			if(
				!(LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
					||LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.XZY_1600)
				)
			)
			{
				contour.carQianYingLength+=LMSConstValue.iNvramLengthOutputCompensate.iValue;	
			}
			contour.carQianYingLength=LMSConstValue.iGetDTBC_Adjust(LMSConstValue.iDTBC_QL, contour.carQianYingLength);
		}
		else
		{
			contour.carQianYingWidth = 0;
			contour.carQianYingHeight = 0;										
			contour.carQianYingLength = 0;
		}
		

		if(LMSConstValue.bAuthOk == false
			&&!LMSConstValue.isMyMachine()
			&&LMSProductInfo.bTest == true)
		{
			LMSLog.d(DEBUG_TAG,"IN TEST");

			contour.carWidth = 0;
			contour.carHeight = 0;
			contour.carLength = 0;
		}
	}
	
	void threeDFinishSendCar(Contour contour,boolean bRegenateResult)
	{
		String logStr = "threeD finish send:carWidth="+contour.carWidth
				+" carHeight="+contour.carHeight
				+" carLength="+contour.carLength 
				+" carGuacheLength="+contour.carGuaCheLength
				+" carGuacheWidth="+contour.carGuaCheWidth
				+" carGuacheHeight="+contour.carGuaCheHeightLeanOriginal
				+" carQianYingLength="+contour.carQianYingLength
				+" carLanBanHeight="+contour.carLanBanHeight
				+" carNum="+contour.carNum
				+" carTime="+contour.carTime
				+" carOutTime="+contour.carOutTime
				+" carSpeed="+contour.carSpeed
				+" carXZJ="+contour.carXZJ
				+" carZNum="+contour.iCarZNum;
      	if(contour.iCarZNum>1)
      	{
      		for(int i=0;i<contour.iCarZNum-1;i++)
      		{
      			logStr += (" carZDistance["+i+"]="+contour.iCarZDistance[i]);
      		}
      	}
      	logStr += " carZJ="+contour.iCarZJ;
		LMSLog.d(DEBUG_TAG, logStr);
		
		HashMap<String, Comparable> eventExtraResult = new HashMap<String, Comparable>();
		if(bRegenateResult == true)
		{
			eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.CAR_REGENATE_RESULT.ordinal());
		}
		else
		{
			eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.CAR_RESULT.ordinal());			
		}
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_NUM,contour.carNum);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_WIDTH,contour.carWidth);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT,contour.carHeight);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_LENGTH,contour.carLength);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_REAR_VIEW_MIRROR,contour.carWidth_RearViewMirror);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_REAR_VIEW_MIRROR,contour.carHeight_RearViewMirror);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_REAR_VIEW_MIRROR,contour.carLength_RearViewMirror);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_FRONT_MIRROR,contour.carWidth_FrontMirror);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_FRONT_MIRROR,contour.carHeight_FrontMirror);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_FRONT_MIRROR,contour.carLength_FrontMirror);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_LB_HEIGHT,contour.carLanBanHeight);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_TIME,contour.carTime);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_OUT_TIME,contour.carOutTime);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_XZJ,contour.carXZJ);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Z_NUM,contour.iCarZNum);
		if(contour.iCarZNum>1)
		{
			for(int i=0;i<contour.iCarZNum-1;i++)
			{
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_ZD+i,contour.iCarZDistance[i]);
			}
		}
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_ZJ,contour.iCarZJ);
		//--------------------------------------------------------------------
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN,contour.realVolumn);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN,contour.boxVolumn);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_ROTATE_ANGLE,contour.angle);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_ROTATE_CENTER_X,contour.rotateCenterX);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_ROTATE_CENTER_Y,contour.rotateCenterY);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH,contour.rotatedWdith);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH,contour.rotatedLength);
		/*
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.VOLUMN_RESULT.ordinal());
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_VOLUMN,1000);
		*/
		//----------------------------------------------------------
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH,contour.carGuaCheLength);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_WIDTH,contour.carGuaCheWidth);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT,contour.carGuaCheHeight);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT_LEAN_ORI,contour.carGuaCheHeightLeanOriginal);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_Z_NUM,contour.iCarGuaCheZNum);
		if(contour.iCarGuaCheZNum>1)
		{
			for(int i=0;i<contour.iCarGuaCheZNum-1;i++)
			{
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_ZD+i,contour.iCarGuaCheZDistance[i]);
			}
		}
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_ZJ,contour.iCarGuaCheZJ);
		//----------------------------------------------------------
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH,contour.carQianYingLength);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_WIDTH,contour.carQianYingWidth);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_HEIGHT,contour.carQianYingHeight);
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_Z_NUM,contour.iCarQianYingZNum);
		if(contour.iCarQianYingZNum>1)
		{
			for(int i=0;i<contour.iCarQianYingZNum-1;i++)
			{
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_ZD+i,contour.iCarQianYingZDistance[i]);
			}
		}
		eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_ZJ,contour.iCarQianYingZJ);
		//----------------------------------------------------------
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_STATE_CHANGE_INTENT,eventExtraResult);			
	}
}
