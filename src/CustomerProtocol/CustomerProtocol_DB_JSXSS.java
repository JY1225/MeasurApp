package CustomerProtocol;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import database.DataBaseConst;

public class CustomerProtocol_DB_JSXSS extends CustomerProtocol_DB{
	private final static String DEBUG_TAG="CustomerProtocol_DB_JSXSS";

	
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		boolean bUploadResult = true;
		
		String Wkcpp = null;
		if(detectPanel.bSingleDecisionLength)
		{
			Wkcpp = "1";//"1：合格";
		}
		else
		{
			Wkcpp = "2";//"2：不合格";			
		}
		
		String Wkkpp = null;
		if(detectPanel.bSingleDecisionWidth)
		{
			Wkkpp = "1";//"1：合格";
		}
		else
		{
			Wkkpp = "2";//"2：不合格";			
		}

		String Wkgpp = null;
		if(detectPanel.bSingleDecisionHeight)
		{
			Wkgpp = "1";//"1：合格";
		}
		else
		{
			Wkgpp = "2";//"2：不合格";			
		}
		
		String updateSQL = 
			"UPDATE "+LMSConstValue.sNvramCustomerProtocol_database_table.sValue+" SET"
			+" wkcjg="+detectPanel.iNvramDetectLength.iValue
			+",Wkkjg="+detectPanel.iNvramDetectWidth.iValue
			+",Wkgjg="+detectPanel.iNvramDetectHeight.iValue
			+",Wkcpp='"+Wkcpp+"'"			//--外廓判定:未检填0，合格填1，不合格填2      
			+",Wkkpp='"+Wkkpp+"'"			//--栏板高度判定:未检填0，合格填1，不合格填2  
			+",Wkgpp='"+Wkgpp+"'"			//--轴距判定:未检填0，合格填1，不合格填2  
			+" WHERE JYLSH='"+detectPanel.serialNumLabelTextField.getTextFieldText()+"'";
		
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
			+"ID, JYLSH, HPHM, HPZL, CLLX,clsbdh,Jylx, " 
			+"cwkc, cwkk, cwkg";
/*
 				+"ID,联网流水号,牌照号码,号牌种类,车辆类型,VIN号,
				+标准车长,标准车宽,标准车高"
 */
	
		//---------------------------------------------------------
		queryDetectionListDB(querySQL);
	}
	
	@Override
	public void parseDetectionList(int row, ResultSet rs, ResultSetMetaData rsmd ){
		int i = 0;
		i++;

		try { 				
			DetectionVehicle detectionVehicle = new DetectionVehicle();
			detectionVehicle.index = row;
			
			String sID = rs.getString(i++);
			if(sID != null)
				detectionVehicle.ID = Integer.valueOf(sID);
			else
				detectionVehicle.ID = -1;
							
			detectionVehicle.sSerialNum = rs.getString(i++);//流水号
			detectionVehicle.sVehicleNum = rs.getString(i++);//号牌号码
			detectionVehicle.sVehicleNumType = rs.getString(i++);//号牌种类
			detectionVehicle.sVehicleType = rs.getString(i++);//车辆类型
			//detectionVehicle.sVehicleBrand = rs.getString(i++);//中文品牌
			detectionVehicle.sVehicleID = rs.getString(i++);//车辆识别代号
			//detectionVehicle.sMotorID = rs.getString(i++);//发动机号码
			String sNewOrOld = rs.getString(i++);
			if(sNewOrOld != null && sNewOrOld.equals("00"))	// --- 检验类别, 00 --- 注册车 其他 --- 在用车 
				detectionVehicle.sNewOrOld = "注册车";
			else
				detectionVehicle.sNewOrOld = "在用车";				
			//detectionVehicle.sOperatorName = rs.getString(i++);//操作员姓名
			
			detectionVehicle.sOriginalCarLength = rs.getString(i++);//原车长
			detectionVehicle.sOriginalCarWidth = rs.getString(i++);//原车宽
			detectionVehicle.sOriginalCarHeight = rs.getString(i++);//原车高
			//detectionVehicle.sOriginalLanBanHeight = rs.getString(i++);//原车栏板高
			//detectionVehicle.sOriginalZJ = rs.getString(i++);//原车轴距
		
			DetectionVehicle.detectionVehicleList.add(detectionVehicle);
		}catch (SQLException e) { 
			LMSLog.exceptionDialog("数据库异常", e);
		}catch (Exception e) {
			LMSLog.exceptionDialog("数据库异常", e);
		}
	}

	@Override
	public void beginDetection(ContourDetectionTabPanelMain detectPanel,boolean bValue) {
		
		
	}

	@Override
	public void carInSignal(ContourDetectionTabPanelMain detectPanel,
			boolean bValue) {
		
		
	}
}
