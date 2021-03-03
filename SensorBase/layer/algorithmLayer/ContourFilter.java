package layer.algorithmLayer;

import java.util.ArrayList;

import SensorBase.LMSConstValue;

public class ContourFilter {
	public boolean bigView = false;

	public final int FRIEND_DISTANCE_LMS = 100;
	public final int FRIEND_DISTANCE_SQUARE_LMS = 20000;
	public final int FRIEND_DISTANCE_Z_LMS= 100;
	public final int FRIEND_FILTER_POINT_LMS = 3;

	//�ӱ궨�����״����࣬���ϲ����ݵĻ�������˳�
	/*
	final int FRIEND_DISTANCE_BIG = 200;
	final int FRIEND_DISTANCE_SQUARE_BIG = 40000;
	final int FRIEND_DISTANCE_Z_BIG = 150;
	final int FRIEND_FILTER_POINT_BIG = -1;//<0,���ϲ�����
	*/
	public final int FRIEND_FILTER_HEAD_LENGTH = 300;
	public final int FRIEND_FILTER_POINT_HEAD = 5;
	public final int FRIEND_FILTER_END_LENGTH = 300;
	public final int FRIEND_FILTER_POINT_END = 5;

	public final int FRIEND_DISTANCE_BIG = 100;
	public final int FRIEND_DISTANCE_SQUARE_BIG = 20000;
	public final int FRIEND_DISTANCE_Z_BIG = 60;
	public final int FRIEND_FILTER_POINT_BIG = 30;

	public final int FRIEND_DISTANCE_LITTLE = 50;
	public final int FRIEND_DISTANCE_SQUARE_LITTLE = 5000;
	public final int FRIEND_DISTANCE_Z_LITTLE = 60;
	public final int FRIEND_FILTER_POINT_LITTLE = 5;

	public final static int VALID_HEIGHT_THRESHOLD = 5;
	public static int VALID_HEIGHT_POINT = 0;

	public ArrayList<ThreeDPoint> threeDPointListLightCurtain = new ArrayList();  
	
	public ArrayList<ThreeDPoint> threeDPointListSide[] = new ArrayList[LMSConstValue.LMS_WH_SENSOR_NUM];  
	public int threeDPointNumAll; //����������static������ͬ�������Ӱ��

	public ArrayList<CoutourRegion> regionListSide[] = new ArrayList[LMSConstValue.LMS_WH_SENSOR_NUM]; 
	
	public ArrayList<ZPlane> zPlaneList[] =  new ArrayList[LMSConstValue.LMS_WH_SENSOR_NUM];  
	public ArrayList<ZPlane> zPlaneFilterResultList[] =  new ArrayList[LMSConstValue.LMS_WH_SENSOR_NUM];  
	public int iZStart,iZEnd;
	public int iZStart_FrontMirror;

	public static boolean bCoutourFilter = false;
	
	public ContourFilter()
	{
      	for(int sensorID=0;sensorID<LMSConstValue.LMS_WH_SENSOR_NUM;sensorID++)
      	{
      		threeDPointListSide[sensorID] = new ArrayList<ThreeDPoint>();

      		regionListSide[sensorID] = new ArrayList<CoutourRegion>();
      		      		
      		zPlaneList[sensorID] = new ArrayList<ZPlane>();
      		zPlaneFilterResultList[sensorID] = new ArrayList<ZPlane>();
      	}
	}
}
