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
			Wkcpp = "1";//"1���ϸ�";
		}
		else
		{
			Wkcpp = "2";//"2�����ϸ�";			
		}
		
		String Wkkpp = null;
		if(detectPanel.bSingleDecisionWidth)
		{
			Wkkpp = "1";//"1���ϸ�";
		}
		else
		{
			Wkkpp = "2";//"2�����ϸ�";			
		}

		String Wkgpp = null;
		if(detectPanel.bSingleDecisionHeight)
		{
			Wkgpp = "1";//"1���ϸ�";
		}
		else
		{
			Wkgpp = "2";//"2�����ϸ�";			
		}
		
		String updateSQL = 
			"UPDATE "+LMSConstValue.sNvramCustomerProtocol_database_table.sValue+" SET"
			+" wkcjg="+detectPanel.iNvramDetectLength.iValue
			+",Wkkjg="+detectPanel.iNvramDetectWidth.iValue
			+",Wkgjg="+detectPanel.iNvramDetectHeight.iValue
			+",Wkcpp='"+Wkcpp+"'"			//--�����ж�:δ����0���ϸ���1�����ϸ���2      
			+",Wkkpp='"+Wkkpp+"'"			//--����߶��ж�:δ����0���ϸ���1�����ϸ���2  
			+",Wkgpp='"+Wkgpp+"'"			//--����ж�:δ����0���ϸ���1�����ϸ���2  
			+" WHERE JYLSH='"+detectPanel.serialNumLabelTextField.getTextFieldText()+"'";
		
		LMSLog.d(DEBUG_TAG,"updateSQL="+updateSQL);

		try { 				
			//ִ��SQL���
			Statement statement = DataBaseConst.customerProtocol_conn.createStatement(); 
			statement.executeUpdate( updateSQL ); 
		}catch (SQLException e) { 
			bUploadResult = false;
			
			LMSLog.exceptionDialog("���ݿ��쳣", e);
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
 				+"ID,������ˮ��,���պ���,��������,��������,VIN��,
				+��׼����,��׼����,��׼����"
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
							
			detectionVehicle.sSerialNum = rs.getString(i++);//��ˮ��
			detectionVehicle.sVehicleNum = rs.getString(i++);//���ƺ���
			detectionVehicle.sVehicleNumType = rs.getString(i++);//��������
			detectionVehicle.sVehicleType = rs.getString(i++);//��������
			//detectionVehicle.sVehicleBrand = rs.getString(i++);//����Ʒ��
			detectionVehicle.sVehicleID = rs.getString(i++);//����ʶ�����
			//detectionVehicle.sMotorID = rs.getString(i++);//����������
			String sNewOrOld = rs.getString(i++);
			if(sNewOrOld != null && sNewOrOld.equals("00"))	// --- �������, 00 --- ע�ᳵ ���� --- ���ó� 
				detectionVehicle.sNewOrOld = "ע�ᳵ";
			else
				detectionVehicle.sNewOrOld = "���ó�";				
			//detectionVehicle.sOperatorName = rs.getString(i++);//����Ա����
			
			detectionVehicle.sOriginalCarLength = rs.getString(i++);//ԭ����
			detectionVehicle.sOriginalCarWidth = rs.getString(i++);//ԭ����
			detectionVehicle.sOriginalCarHeight = rs.getString(i++);//ԭ����
			//detectionVehicle.sOriginalLanBanHeight = rs.getString(i++);//ԭ�������
			//detectionVehicle.sOriginalZJ = rs.getString(i++);//ԭ�����
		
			DetectionVehicle.detectionVehicleList.add(detectionVehicle);
		}catch (SQLException e) { 
			LMSLog.exceptionDialog("���ݿ��쳣", e);
		}catch (Exception e) {
			LMSLog.exceptionDialog("���ݿ��쳣", e);
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
