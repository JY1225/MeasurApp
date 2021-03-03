package layer.algorithmLayer;

import SensorBase.LMSConstValue;


public class ParseLMSAckCommand extends Object{
	public static int beamDiameterAtFrontScreen[] = new int[LMSConstValue.RADAR_SENSOR_NUM];	//ǰ���洦�Ĺ��ֱ��
	public static float beamDiameterArgument[] = new float[LMSConstValue.RADAR_SENSOR_NUM];
	
	public static int discardTail[] = new int[LMSConstValue.RADAR_SENSOR_NUM];

	public static int commandType[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	public static String resultStr[] = new String[LMSConstValue.RADAR_SENSOR_NUM];	//parseLMSAckCommandType�Ž���õ�

	public static int measureData16bit_amount[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	
	public static int telegramCounterLast[] = new int[LMSConstValue.SYSTEM_SENSOR_NUM]; 
	public static int telegramCounterLost[] = new int[LMSConstValue.SYSTEM_SENSOR_NUM]; 

	public static int scanFreq[] = new int[LMSConstValue.SYSTEM_SENSOR_NUM]; //Ƶ��Ӧ����100(֡��Ƶ��)
	public static double angleResolution[] = new double[LMSConstValue.RADAR_SENSOR_NUM]; //�Ƿֱ���Ӧ����10000
	public static int measureFreq[] = new int[LMSConstValue.RADAR_SENSOR_NUM]; //����Ƶ��Ӧ����100��������࣬���Ƶ�ʣ�
	
	public static int degree90Index[] = new int[LMSConstValue.RADAR_SENSOR_NUM];

	public static float startAngle[] = new float[LMSConstValue.RADAR_SENSOR_NUM]; //�Ƕ�Ӧ����10000
	public static float stopAngle[] = new float[LMSConstValue.RADAR_SENSOR_NUM];  //�Ƕ�Ӧ����10000
	
	public static int amountOf16bitChannels[] = new int[LMSConstValue.RADAR_SENSOR_NUM];
	
	public static boolean permentSend[] = new boolean[LMSConstValue.RADAR_SENSOR_NUM];
	/*
	 * 0: undefine
	 * 7: measure�У������������û�����
	 */
	public static int statusCode[] = new int[LMSConstValue.RADAR_SENSOR_NUM];	
}
