package layer.algorithmLayer;

import SensorBase.LMSConstValue;


public class ParseLMSAckCommand extends Object{
	public static int beamDiameterAtFrontScreen[] = new int[LMSConstValue.RADAR_SENSOR_NUM];	//前镜面处的光斑直径
	public static float beamDiameterArgument[] = new float[LMSConstValue.RADAR_SENSOR_NUM];
	
	public static int discardTail[] = new int[LMSConstValue.RADAR_SENSOR_NUM];

	public static int commandType[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String resultStr[] = new String[LMSConstValue.RADAR_SENSOR_NUM];	//parseLMSAckCommandType放结果用的

	public static int measureData16bit_amount[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	
	public static int telegramCounterLast[] = new int[LMSConstValue.SYSTEM_SENSOR_NUM]; 
	public static int telegramCounterLost[] = new int[LMSConstValue.SYSTEM_SENSOR_NUM]; 

	public static int scanFreq[] = new int[LMSConstValue.SYSTEM_SENSOR_NUM]; //频率应除以100(帧的频率)
	public static double angleResolution[] = new double[LMSConstValue.RADAR_SENSOR_NUM]; //角分辨率应除以10000
	public static int measureFreq[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; //测量频率应乘以100（两点间间距，点的频率）
	
	public static int degree90Index[] = new int[LMSConstValue.RADAR_SENSOR_NUM];

	public static float startAngle[] = new float[LMSConstValue.RADAR_SENSOR_NUM]; //角度应除以10000
	public static float stopAngle[] = new float[LMSConstValue.RADAR_SENSOR_NUM];  //角度应除以10000
	
	public static int amountOf16bitChannels[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	
	public static boolean permentSend[] = new boolean[LMSConstValue.RADAR_SENSOR_NUM];
	/*
	 * 0: undefine
	 * 7: measure中，可以输出或者没有输出
	 */
	public static int statusCode[] = new int[LMSConstValue.RADAR_SENSOR_NUM];	
}
