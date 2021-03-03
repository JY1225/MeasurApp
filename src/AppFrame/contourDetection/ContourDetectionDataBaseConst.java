package AppFrame.contourDetection;

import java.sql.Connection;

import database.DataBaseConst;

public class ContourDetectionDataBaseConst extends DataBaseConst{
	protected final static String DATA_BASE_NAME = "contour";
	
//	protected final static String TABLE_NAME = "car_20170203_00";
	protected final static String TABLE_NAME = "car_20170329_01";
	
	protected final static String TABLE_COLUMN_ID = "ID";
	protected final static String TABLE_COLUMN_TIME = "time";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_SERIAL_NUM = "serialNum"; //��ˮ��
	protected final static String CAR_TABLE_COLUMN_VEHICLE_NUM = "vehicleNum";	//���ƺ���
	protected final static String CAR_TABLE_COLUMN_VEHICLE_NUM_TYPE = "vehicleNumType";	//��������
	protected final static String CAR_TABLE_COLUMN_VEHICLE_TYPE = "vehicleType";	//��������
	protected final static String CAR_TABLE_COLUMN_VEHICLE_BRAND = "vehicleBrand";	//Ʒ���ͺ�
	protected final static String CAR_TABLE_COLUMN_VEHICLE_ID = "vehicleID";	//����ʶ�����
	protected final static String CAR_TABLE_COLUMN_VEHICLE_MOTOR_ID = "vehicleMotorID";	//����������
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_DECISION_STANDARD = "decisionStandard";	//�ж���׼
	protected final static String CAR_TABLE_COLUMN_DETECT_VAR = "detectVar";	//��������
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_WIDTH = "width";
	protected final static String CAR_TABLE_COLUMN_HEIGHT = "height";
	protected final static String CAR_TABLE_COLUMN_LENGTH = "length";
	protected final static String CAR_TABLE_COLUMN_LANBAN_HEIGHT = "lanban_height";
	protected final static String CAR_TABLE_COLUMN_XZJ = "xzj";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_Z_NUM = "zNum";
	protected final static String CAR_TABLE_COLUMN_Z0 = "z0";
	protected final static String CAR_TABLE_COLUMN_Z1 = "z1";
	protected final static String CAR_TABLE_COLUMN_Z2 = "z2";
	protected final static String CAR_TABLE_COLUMN_Z3 = "z3";
	protected final static String CAR_TABLE_COLUMN_Z4 = "z4";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_WIDTH_ORIGINAL = "width_original";
	protected final static String CAR_TABLE_COLUMN_HEIGHT_ORIGINAL = "height_original";
	protected final static String CAR_TABLE_COLUMN_LENGTH_ORIGINAL = "length_original";
	protected final static String CAR_TABLE_COLUMN_LANBAN_HEIGHT_ORIGINAL = "lanban_height_original";
	protected final static String CAR_TABLE_COLUMN_XZJ_ORIGINAL = "xzj_original";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_Z_NUM_ORIGINAL = "zNum_original";
	protected final static String CAR_TABLE_COLUMN_Z0_ORIGINAL = "z0_original";
	protected final static String CAR_TABLE_COLUMN_Z1_ORIGINAL = "z1_original";
	protected final static String CAR_TABLE_COLUMN_Z2_ORIGINAL = "z2_original";
	protected final static String CAR_TABLE_COLUMN_Z3_ORIGINAL = "z3_original";
	protected final static String CAR_TABLE_COLUMN_Z4_ORIGINAL = "z4_original";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_ZJ_ORIGINAL = "zj_original";
	protected final static String CAR_TABLE_COLUMN_ZJ = "zj";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_DETECT_NUM_OF_TIME = "detect_num_of_time";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_OPERATOR_NAME = "operator_name";
	protected final static String CAR_TABLE_COLUMN_OPERATOR_ID = "operator_id";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_YINCHEYUAN_NAME = "yingcheyuan_name";
	protected final static String CAR_TABLE_COLUMN_YINCHEYUAN_ID = "yingcheyuan_id";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_LENGTH_PRDADJUST = "length_preadjust";
	protected final static String CAR_TABLE_COLUMN_WIDTH_PRDADJUST = "width_preadjust";
	protected final static String CAR_TABLE_COLUMN_HEIGHT_PRDADJUST = "height_preadjust";
	protected final static String CAR_TABLE_COLUMN_LANBAN_PRDADJUST = "lanban_preadjust";
	protected final static String CAR_TABLE_COLUMN_ZJ_PRDADJUST = "zj_preadjust";
	protected final static String CAR_TABLE_COLUMN_HUMAN_ADJUST_COMMENT_PARAMETER = "human_adjust_comment_parameter";
	protected final static String CAR_TABLE_COLUMN_HUMAN_ADJUST_COMMENT_VALUE = "human_adjust_comment_value";
	//-------------------------------------------------------------------
	protected final static String CAR_TABLE_COLUMN_LEFT_SIDE_VIEW = "left_side_view";
	protected final static String CAR_TABLE_COLUMN_RIGHT_SIDE_VIEW = "right_side_view";
	protected final static String CAR_TABLE_COLUMN_DOWN_VIEW = "down_view";
	protected final static String CAR_TABLE_COLUMN_FRONT_VIEW = "front_view";
	protected final static String CAR_TABLE_COLUMN_REAR_VIEW = "rear_view";
	protected final static String CAR_TABLE_COLUMN_CAR_IN = "car_in";
	protected final static String CAR_TABLE_COLUMN_CAR_OUT = "car_out";

	protected final static String TABLE_DISPLAY_COLUMN = 
		TABLE_COLUMN_ID+","
		+TABLE_COLUMN_TIME+","
		+CAR_TABLE_COLUMN_VEHICLE_NUM+","
		+CAR_TABLE_COLUMN_WIDTH+","
		+CAR_TABLE_COLUMN_HEIGHT+","
		+CAR_TABLE_COLUMN_LENGTH;
	
	static String createTableSQL = 
		"(" +
		"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
		TABLE_COLUMN_TIME+" datetime default NULL COMMENT 'ʱ��'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_SERIAL_NUM+" varchar(50) COMMENT '��ˮ��'," +
		CAR_TABLE_COLUMN_VEHICLE_NUM+" varchar(50) COMMENT '���ƺ���'," +
		CAR_TABLE_COLUMN_VEHICLE_NUM_TYPE+" varchar(50) COMMENT '��������'," +
		CAR_TABLE_COLUMN_VEHICLE_TYPE+" varchar(50) COMMENT '��������'," +
		CAR_TABLE_COLUMN_VEHICLE_BRAND+" varchar(50) COMMENT 'Ʒ���ͺ�'," +
		CAR_TABLE_COLUMN_VEHICLE_ID+" varchar(50) COMMENT '����ʶ�����'," +
		CAR_TABLE_COLUMN_VEHICLE_MOTOR_ID+" varchar(50) COMMENT '����������'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_DECISION_STANDARD+" smallint COMMENT '�ж���׼'," +
		CAR_TABLE_COLUMN_DETECT_VAR+" varchar(50) COMMENT '��������'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_WIDTH+" mediumint COMMENT '��'," +
		CAR_TABLE_COLUMN_HEIGHT+" mediumint COMMENT '��'," +
		CAR_TABLE_COLUMN_LENGTH+" mediumint COMMENT '��'," +
		CAR_TABLE_COLUMN_LANBAN_HEIGHT+" mediumint COMMENT '�����'," +
		CAR_TABLE_COLUMN_XZJ+" mediumint COMMENT '�N���'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_Z_NUM+" mediumint COMMENT '����'," +
		CAR_TABLE_COLUMN_Z0+" mediumint COMMENT '���1'," +
		CAR_TABLE_COLUMN_Z1+" mediumint COMMENT '���2'," +
		CAR_TABLE_COLUMN_Z2+" mediumint COMMENT '���3'," +
		CAR_TABLE_COLUMN_Z3+" mediumint COMMENT '���4'," +
		CAR_TABLE_COLUMN_Z4+" mediumint COMMENT '���5'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_WIDTH_ORIGINAL+" mediumint COMMENT 'ԭ����'," +
		CAR_TABLE_COLUMN_HEIGHT_ORIGINAL+" mediumint COMMENT 'ԭ����'," +
		CAR_TABLE_COLUMN_LENGTH_ORIGINAL+" mediumint COMMENT 'ԭ����'," +
		CAR_TABLE_COLUMN_LANBAN_HEIGHT_ORIGINAL+" mediumint COMMENT 'ԭ�������'," +
		CAR_TABLE_COLUMN_XZJ_ORIGINAL+" mediumint COMMENT 'ԭ���N���'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_Z_NUM_ORIGINAL+" mediumint COMMENT 'ԭ������'," +
		CAR_TABLE_COLUMN_Z0_ORIGINAL+" mediumint COMMENT 'ԭ�����1'," +
		CAR_TABLE_COLUMN_Z1_ORIGINAL+" mediumint COMMENT 'ԭ�����2'," +
		CAR_TABLE_COLUMN_Z2_ORIGINAL+" mediumint COMMENT 'ԭ�����3'," +
		CAR_TABLE_COLUMN_Z3_ORIGINAL+" mediumint COMMENT 'ԭ�����4'," +
		CAR_TABLE_COLUMN_Z4_ORIGINAL+" mediumint COMMENT 'ԭ�����5'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_ZJ_ORIGINAL+" mediumint COMMENT 'ԭ�����'," +
		CAR_TABLE_COLUMN_ZJ+" mediumint COMMENT '���'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_DETECT_NUM_OF_TIME+" mediumint COMMENT '������'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_OPERATOR_NAME+" varchar(20) COMMENT '����Ա����'," +
		CAR_TABLE_COLUMN_OPERATOR_ID+" varchar(50) COMMENT '����ԱID'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_YINCHEYUAN_NAME+" varchar(20) COMMENT '����Ա����'," +
		CAR_TABLE_COLUMN_YINCHEYUAN_ID+" varchar(50) COMMENT '����ԱID'," +
		//-------------------------------------------------------------------
//		CAR_TABLE_COLUMN_LENGTH_PRDADJUST+" mediumint COMMENT '�˹�����ǰ����'," +
//		CAR_TABLE_COLUMN_WIDTH_PRDADJUST+" mediumint COMMENT '�˹�����ǰ����'," +
//		CAR_TABLE_COLUMN_HEIGHT_PRDADJUST+" mediumint COMMENT '�˹�����ǰ����'," +
//		CAR_TABLE_COLUMN_LANBAN_PRDADJUST+" mediumint COMMENT '�˹�����ǰ����'," +
//		CAR_TABLE_COLUMN_ZJ_PRDADJUST+" mediumint COMMENT '�˹�����ǰ����'," +
//		CAR_TABLE_COLUMN_HUMAN_ADJUST_COMMENT_PARAMETER+" varchar(100) COMMENT '�˹�����ǰ����'," +
//		CAR_TABLE_COLUMN_HUMAN_ADJUST_COMMENT_VALUE+" varchar(500) COMMENT '�˹�������ע'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_LEFT_SIDE_VIEW+" MEDIUMBLOB COMMENT '�����ͼ'," +
		CAR_TABLE_COLUMN_RIGHT_SIDE_VIEW+" MEDIUMBLOB COMMENT '�Ҳ���ͼ'," +
		CAR_TABLE_COLUMN_DOWN_VIEW+" MEDIUMBLOB COMMENT '����ͼ'," +
		CAR_TABLE_COLUMN_FRONT_VIEW+" MEDIUMBLOB COMMENT 'ǰ��ͼ'," +
		CAR_TABLE_COLUMN_REAR_VIEW+" MEDIUMBLOB COMMENT '����ͼ'," +
		CAR_TABLE_COLUMN_CAR_IN+" MEDIUMBLOB COMMENT '��ͷͼ'," +
		CAR_TABLE_COLUMN_CAR_OUT+" MEDIUMBLOB COMMENT '��βͼ'" +
		")DEFAULT CHARSET=utf8"; 
//SQL SERVER
	/*
	 *static String createTableSQL = 
		"(" +
		"ID INT NOT NULL PRIMARY KEY identity(1,1)," +
		TABLE_COLUMN_TIME+" datetime default NULL,"+// COMMENT 'ʱ��'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_SERIAL_NUM+" varchar(30),"+// COMMENT '��ˮ��'," +
		CAR_TABLE_COLUMN_VEHICLE_NUM+" varchar(20),"+// COMMENT '���ƺ���'," +
		CAR_TABLE_COLUMN_VEHICLE_NUM_TYPE+" varchar(20),"+// COMMENT '��������'," +
		CAR_TABLE_COLUMN_VEHICLE_TYPE+" varchar(20),"+// COMMENT '��������'," +
		CAR_TABLE_COLUMN_VEHICLE_BRAND+" varchar(20),"+// COMMENT 'Ʒ���ͺ�'," +
		CAR_TABLE_COLUMN_VEHICLE_ID+" varchar(20),"+// COMMENT '����ʶ�����'," +
		CAR_TABLE_COLUMN_VEHICLE_MOTOR_ID+" varchar(20),"+// COMMENT '����������'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_DECISION_STANDARD+" smallint,"+// COMMENT '�ж���׼'," +
		CAR_TABLE_COLUMN_DETECT_VAR+" varchar(20),"+// COMMENT '��������'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_WIDTH+" int,"+// COMMENT '��'," +
		CAR_TABLE_COLUMN_HEIGHT+" int,"+// COMMENT '��'," +
		CAR_TABLE_COLUMN_LENGTH+" int,"+// COMMENT '��'," +
		CAR_TABLE_COLUMN_LANBAN_HEIGHT+" int,"+// COMMENT '�����'," +
		CAR_TABLE_COLUMN_XZJ+" int,"+// COMMENT '�N���'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_Z_NUM+" int,"+// COMMENT '����'," +
		CAR_TABLE_COLUMN_Z0+" int,"+// COMMENT '���1'," +
		CAR_TABLE_COLUMN_Z1+" int,"+// COMMENT '���2'," +
		CAR_TABLE_COLUMN_Z2+" int,"+// COMMENT '���3'," +
		CAR_TABLE_COLUMN_Z3+" int,"+// COMMENT '���4'," +
		CAR_TABLE_COLUMN_Z4+" int,"+// COMMENT '���5'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_WIDTH_ORIGINAL+" int,"+// COMMENT 'ԭ����'," +
		CAR_TABLE_COLUMN_HEIGHT_ORIGINAL+" int,"+// COMMENT 'ԭ����'," +
		CAR_TABLE_COLUMN_LENGTH_ORIGINAL+" int,"+// COMMENT 'ԭ����'," +
		CAR_TABLE_COLUMN_LANBAN_HEIGHT_ORIGINAL+" int,"+// COMMENT 'ԭ�������'," +
		CAR_TABLE_COLUMN_XZJ_ORIGINAL+" int,"+// COMMENT 'ԭ���N���'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_Z_NUM_ORIGINAL+" int,"+// COMMENT 'ԭ������'," +
		CAR_TABLE_COLUMN_Z0_ORIGINAL+" int,"+// COMMENT 'ԭ�����1'," +
		CAR_TABLE_COLUMN_Z1_ORIGINAL+" int,"+// COMMENT 'ԭ�����2'," +
		CAR_TABLE_COLUMN_Z2_ORIGINAL+" int,"+// COMMENT 'ԭ�����3'," +
		CAR_TABLE_COLUMN_Z3_ORIGINAL+" int,"+// COMMENT 'ԭ�����4'," +
		CAR_TABLE_COLUMN_Z4_ORIGINAL+" int,"+// COMMENT 'ԭ�����5'," +
		//-------------------------------------------------------------------
		CAR_TABLE_COLUMN_LEFT_SIDE_VIEW+" MEDIUMBLOB,"+// COMMENT '�����ͼ'," +
		CAR_TABLE_COLUMN_RIGHT_SIDE_VIEW+" MEDIUMBLOB,"+// COMMENT '�Ҳ���ͼ'," +
		CAR_TABLE_COLUMN_DOWN_VIEW+" MEDIUMBLOB,"+// COMMENT '����ͼ'," +
		CAR_TABLE_COLUMN_FRONT_VIEW+" MEDIUMBLOB,"+// COMMENT 'ǰ��ͼ'," +
		CAR_TABLE_COLUMN_REAR_VIEW+" MEDIUMBLOB,"+// COMMENT '����ͼ'," +
		CAR_TABLE_COLUMN_CAR_IN+" MEDIUMBLOB,"+// COMMENT '��ͷͼ'," +
		CAR_TABLE_COLUMN_CAR_OUT+" MEDIUMBLOB"+// COMMENT '��βͼ'" +
		")CHARSET=utf8"; 

	 */
	public enum MainPanelType{
		DETECT_RESULT,
		SEARCH_SINGLE_RESULT,
	}

	public enum MainPanelCarType{
		GUACHE,
		QIAN_YING_CHE,
	}
}
