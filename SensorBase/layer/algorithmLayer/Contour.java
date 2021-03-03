package layer.algorithmLayer;

import java.util.ArrayList;
import java.util.List;

import algorithmLayer.ContourFilterAlgorithm;
import layer.algorithmLayer.SingleBubble.SINGLE_BUBBLE_CAR_CREATE_BREAK;
import SensorBase.LMSConstValue;

public class Contour {
  	public static boolean bCarIn;

	public int carWidth;
	public int carHeight;
	public int carLength;
	
	public int carWidth_RearViewMirror;
	public int carHeight_RearViewMirror;
	public int carLength_RearViewMirror;
	
	public int carWidth_FrontMirror;
	public int carHeight_FrontMirror;
	public int carLength_FrontMirror;

	public int carTime;
	public String carOutTime;
	public int carNum;
	public int carSpeed;
  	public ContourMaxPoint heightMaxPoint;
  	public ContourMaxPoint widthMaxPoint[] = new ContourMaxPoint[2];
  	public int minPointSensorID = 1;
  	public int maxPointSensorID = 0;

	//体积测量相关
	public int realVolumn;
	public int boxVolumn;
	public float angle;
	public int rotateCenterX;
	public int rotateCenterY;
	public int rotatedWdith;
	public int rotatedLength;

	//====================================
	public int WEIQI_SENSOR_ID;
	
	//====================================
	public String carTypeString;

	//====================================
	public int GUA_SENSOR_ID;
	public boolean bGuaChe = false;

	public int carGuaCheIndex;
	public float carGuaCheZ;
	
	public int carGuaCheWidthMiddle;
	
	public float carGuaCheWithCheLanZ;

	public int carGuaCheLength;
	public int carGuaCheWidth;
	public int carGuaCheHeight;
	public int carGuaCheHeightLeanOriginal; //原倾斜高
	public double dGuacheLeanAngle;
	public double dGuacheLeanA;
	public double dGuacheLeanB;
	
	public float zDownPoint;
	public int yDownPoint;
	
	//--------------------------------------------------------------------
  	public ContourMaxPoint heightMaxPointGuaChe;
  	public ContourMaxPoint widthMaxPointGuaChe[] = new ContourMaxPoint[2];
 
	public int iCarGuaCheZNum;
	public int iCarGuaCheZDistance[] = new int[LMSConstValue.MAX_Z_NUM-1];
	public int iCarGuaCheZMiddle[] = new int[LMSConstValue.MAX_Z_NUM];
	public int iCarGuaCheZJ;

	//-------------------------------------------------------------------------------
	public boolean bQianYing = false;
	public int QIANYING_SENSOR_ID;
	public int QIANYING_HEIGHT_SENSOR_ID;
	public float carQianYing_TuQiZEnd; //牵引车,车头凸起的那部分的位置,计算高度,宽度用的

	public float carQianYingZStart;
	public float carQianYingZEnd;
	public int carQianYingLength;
	public int carQianYingWidth;
	public int carQianYingHeight;
  	public ContourMaxPoint heightMaxPointQianYing;
 	public ContourMaxPoint widthMaxPointQianYing[] = new ContourMaxPoint[2];

	public int iCarQianYingZNum;
	public int iCarQianYingZDistance[] = new int[LMSConstValue.MAX_Z_NUM-1];
	public int iCarQianYingZMiddle[] = new int[LMSConstValue.MAX_Z_NUM];
	public int iCarQianYingZJ;

	//-------------------------------------------------------------------------------
	public int iXiao_length_CurveFitting;
	public int carXZJ;

	//================================================================================
	public int LANBAN_SENSOR_ID;
	public boolean bLanBanChe = false;
	
	public int carLanBanHeight;

	public double dLanbanPanelLeanAngle;
	public double dLanbanPanelLeanA;
	public double dLanbanPanelLeanB;
	public double dLanbanPanelValidXBegin;
	public double dLanbanPanelValidXEnd;
	public int iLanbanPanelValidYBegin;
	public int iLanbanPanelValidYEnd;

	public double dLanbanCheLeanAngle;
	public double dLanbanCheLeanA;
	public double dLanbanCheLeanB;
	public double dLanbanCheValidXBegin;
	public double dLanbanCheValidXEnd;
	public int iLanbanCheValidYBegin;
	public int iLanbanCheValidYEnd;

	//====================================
	public int iLightCurtainRadarDaistance0;

	public int iCarZNum;
	public int iCarZDistance[] = new int[LMSConstValue.MAX_Z_NUM-1];
	public int iCarZStart[] = new int[LMSConstValue.MAX_Z_NUM];
	public int iCarZEnd[] = new int[LMSConstValue.MAX_Z_NUM];
	public int iCarZMiddle[] = new int[LMSConstValue.MAX_Z_NUM];
	public int iCarZJ;

  	public float zStart[] = new float[LMSConstValue.LMS_WH_SENSOR_NUM];

  	public float zLightCurtainStart;
	
	public float zEarEnd[] = new float[LMSConstValue.LMS_WH_SENSOR_NUM];

//	public SINGLE_BUBBLE_CAR_IN_OUT carInOut;
	//出车时刻(单边计算长的时候)
	public SINGLE_BUBBLE_CAR_CREATE_BREAK carCreateBreak;
			
	public SingleBubble whBubble[] = new SingleBubble[LMSConstValue.LMS_WH_SENSOR_NUM];
	
	public List<LightCurtainCarInLine> lightCurtainCarInLineList = new ArrayList<LightCurtainCarInLine>();

	public Contour()
	{
		for(int i=0;i<LMSConstValue.LMS_WH_SENSOR_NUM;i++)
		{
			whBubble[i] = new SingleBubble();			
		}
		
		widthMaxPoint[0] = new ContourMaxPoint(0, null);
		widthMaxPoint[1] = new ContourMaxPoint(0, null);
	}
	
	public ContourFilter contourFilter = new ContourFilter();
}
