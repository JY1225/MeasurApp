package AppFrame.logisticsMachine;

import database.DataBaseConst;
import AppFrame.contourDetection.ContourDetectionDataBaseConst;

public class LogisticsMachineDataBaseConst extends DataBaseConst{
	public final static String DATA_BASE_NAME = "LogisticsMachine";
	protected final static String TABLE_NAME = "vms_ops_tbl";

	protected final static String TABLE_COLUMN_ID = "ID";
	protected final static String TABLE_COLUMN_TIME = "time";
	protected final static String TABLE_COLUMN_BAR_CODE = "barCode";
	protected final static String TABLE_COLUMN_WIDTH = "width";
	protected final static String TABLE_COLUMN_HEIGHT = "height";
	protected final static String TABLE_COLUMN_LENGTH = "length";
	protected final static String TABLE_COLUMN_BOX_VOLUMN = "boxVolumn";
	protected final static String TABLE_COLUMN_REAL_VOLUMN = "realVolumn";
	protected final static String TABLE_COLUMN_ROTATE_ANGLE = "rotateAngle";

	public final static String TABLE_DISPLAY_COLUMN = 
		TABLE_COLUMN_ID+","
		+TABLE_COLUMN_TIME+","
		+TABLE_COLUMN_BAR_CODE+","
		+TABLE_COLUMN_LENGTH+","
		+TABLE_COLUMN_WIDTH+","
		+TABLE_COLUMN_HEIGHT+","
		+TABLE_COLUMN_BOX_VOLUMN+","
		+TABLE_COLUMN_REAL_VOLUMN+","
		+TABLE_COLUMN_ROTATE_ANGLE;

	static String createTableSQL = 
		"create table "+TABLE_NAME+"(" +
		"ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
		TABLE_COLUMN_TIME+" datetime default NULL COMMENT '时间'," +
		TABLE_COLUMN_BAR_CODE+" varchar(20) COMMENT '条码'," +
		TABLE_COLUMN_LENGTH+" float COMMENT '长(cm)'," +
		TABLE_COLUMN_WIDTH+" float COMMENT '宽(cm)'," +
		TABLE_COLUMN_HEIGHT+" float COMMENT '高(cm)'," +
		TABLE_COLUMN_BOX_VOLUMN+" int COMMENT '最小盒子体积(cm3)'," +
		TABLE_COLUMN_REAL_VOLUMN+" int COMMENT '真实体积(cm3)'," +
		TABLE_COLUMN_ROTATE_ANGLE+" float COMMENT '旋转角度'" +
		")DEFAULT CHARSET=utf8"; 

	public enum MainPanelType{
		DETECT_RESULT,
		SEARCH_SINGLE_RESULT,
	}
}
