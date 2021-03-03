package CustomerProtocol;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import database.DataBaseConst;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

//联网商:合肥思瑞福,合肥正升
//站点:安徽定远(合肥思瑞福);娄底涟源(合肥正升)
public class CustomerProtocol_DB_HF_SRF extends CustomerProtocol_DB{
	private final static String DEBUG_TAG="CustomerProtocol_HF_SRF";

	String CREATE_DATABASE_SQL = 
		"("
		+"id [INT] IDENTITY (1, 1) NOT NULL ,"
		+"[联网流水号] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[登录时间] [char] (14) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[牌照号码] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[车主] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[牌照颜色] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[号牌种类] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[车辆类型] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[厂牌型号] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[燃料类别] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[检验类别] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[发动机号] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[VIN号] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[车辆轴数] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[出厂年月] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[检验日期] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[初次登记日期] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[驻车轴] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[车属类别] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[载质量] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[载客量] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[引车员] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[登录员] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[总质量] [int] NULL ,"
		+"[备注] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[标准车长] [int] NULL ,"
		+"[标准车宽] [int] NULL ,"
		+"[标准车高] [int] NULL ,"
		+"[实测车长] [int] NULL ,"
		+"[实测车宽] [int] NULL ,"
		+"[实测车高] [int] NULL ,"
		+"[车长误差] [int] NULL ,"
		+"[车宽误差] [int] NULL ,"
		+"[车高误差] [int] NULL ,"
		+"[外廓判定] [varchar] (10) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[外形轮廓检测] [bit] NULL ,"
		+"[车长] [int] NULL ,"
		+"[车宽] [int] NULL ,"
		+"[车高] [int] NULL ,"
		+"[外廓检测次数] [int] NULL ,"
		+"[检测状态] [int] NULL )";
	
	//=============================================================================
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		boolean bUploadResult = true;
		
		String decisionStr_HF_SRF = null;
		if(detectPanel.bDecision)
		{
			decisionStr_HF_SRF = "Ｏ";//"Ｏ：合格";
		}
		else
		{
			decisionStr_HF_SRF = "×";//"×：不合格";			
		}
		String updateSQL = 
			"UPDATE "+LMSConstValue.sNvramCustomerProtocol_database_table.sValue+" SET"
			+" 检测状态="+8
			+" ,实测车长="+detectPanel.iNvramDetectLength.iValue
			+",实测车宽="+detectPanel.iNvramDetectWidth.iValue
			+",实测车高="+detectPanel.iNvramDetectHeight.iValue
			+",车长误差="+detectPanel.absoluteCarLength
			+",车宽误差="+detectPanel.absoluteCarWidth
			+",车高误差="+detectPanel.absoluteCarHeight
			+",外廓判定='"+decisionStr_HF_SRF+"'"
			+" WHERE 联网流水号='"+detectPanel.serialNumLabelTextField.getTextFieldText()+"'";
		
		LMSLog.d(DEBUG_TAG,"updateSQL="+updateSQL);

		try { 				
			//执行SQL语句
			Statement statement = DataBaseConst.customerProtocol_conn.createStatement(); 
			statement.executeUpdate( updateSQL ); 
		}catch (SQLException e) { 
			bUploadResult = false;
			
			LMSLog.exceptionDialog("数据库异常", e);
		}
		
		return bUploadResult;
	}
	
	@Override
	public void queryDetectionList(ContourDetectionTabPanelMain detectPanel)
	{		
		String querySQL =
			"SELECT "
			+"外形轮廓检测,ID,联网流水号,牌照号码,号牌种类,车辆类型,厂牌型号,VIN号,发动机号,引车员,标准车长,标准车宽,标准车高,检测状态";

		queryDetectionListDB(querySQL);
	}
	
	@Override
	@SuppressWarnings({"rawtypes","unchecked"})
	public void parseDetectionList(int row, ResultSet rs, ResultSetMetaData rsmd ){
		//-----------------------------------------------
		int i = 1;
		i++;

		try { 				
			DetectionVehicle detectionVehicle = new DetectionVehicle();
			detectionVehicle.index = row;
			
			String sID = rs.getString(i++);
			if(sID != null)
				detectionVehicle.ID = Integer.valueOf(sID);
			else
				detectionVehicle.ID = -1;
				
			detectionVehicle.sSerialNum = rs.getString(i++);
			detectionVehicle.sVehicleNum = rs.getString(i++);
			detectionVehicle.sVehicleNumType = rs.getString(i++);
			detectionVehicle.sVehicleType = rs.getString(i++);
			detectionVehicle.sVehicleBrand = rs.getString(i++);
			detectionVehicle.sVehicleID = rs.getString(i++);
			detectionVehicle.sMotorID = rs.getString(i++);
			detectionVehicle.sOperatorName = rs.getString(i++);
			
			detectionVehicle.sOriginalCarLength = rs.getString(i++);
			detectionVehicle.sOriginalCarWidth = rs.getString(i++);
			detectionVehicle.sOriginalCarHeight = rs.getString(i++);
				
			String sDetectStatus = rs.getString(i++);
			if(sDetectStatus != null)
			{
				int iDetectStatus = Integer.valueOf(sDetectStatus);
				if(iDetectStatus == 0) //0 --- 未检测,其他已经检测
					detectionVehicle.bDetectStatus = false;
				else
					detectionVehicle.bDetectStatus = true;
			}
			else
			{
				detectionVehicle.bDetectStatus = false;				
			}
			
			if(detectionVehicle.bDetectStatus == false||LMSConstValue.bNvramOnlyShowNotDetectdCar.bValue == false)
			{
				DetectionVehicle.detectionVehicleList.add(detectionVehicle);
			}
		}catch (SQLException e) { 
			LMSLog.exceptionDialog("数据库异常", e);
		}catch (Exception e) {
			LMSLog.exceptionDialog("数据库异常", e);
		}
	}

	@Override
	public void beginDetection(ContourDetectionTabPanelMain detectPanel,boolean bValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void carInSignal(ContourDetectionTabPanelMain detectPanel,
			boolean bValue) {
		// TODO Auto-generated method stub
		
	}
}
