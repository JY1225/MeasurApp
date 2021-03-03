package CustomerProtocol;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import database.DataBaseConst;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

//������:�Ϸ�˼��,�Ϸ�����
//վ��:���ն�Զ(�Ϸ�˼��);¦����Դ(�Ϸ�����)
public class CustomerProtocol_DB_HF_SRF extends CustomerProtocol_DB{
	private final static String DEBUG_TAG="CustomerProtocol_HF_SRF";

	String CREATE_DATABASE_SQL = 
		"("
		+"id [INT] IDENTITY (1, 1) NOT NULL ,"
		+"[������ˮ��] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��¼ʱ��] [char] (14) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[���պ���] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[����] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[������ɫ] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[�����ͺ�] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[ȼ�����] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[�������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[VIN��] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[���εǼ�����] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[פ����] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[�������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[������] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[�ؿ���] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[����Ա] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��¼Ա] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[������] [int] NULL ,"
		+"[��ע] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[��׼����] [int] NULL ,"
		+"[��׼����] [int] NULL ,"
		+"[��׼����] [int] NULL ,"
		+"[ʵ�⳵��] [int] NULL ,"
		+"[ʵ�⳵��] [int] NULL ,"
		+"[ʵ�⳵��] [int] NULL ,"
		+"[�������] [int] NULL ,"
		+"[�������] [int] NULL ,"
		+"[�������] [int] NULL ,"
		+"[�����ж�] [varchar] (10) COLLATE Chinese_PRC_CI_AS NULL ,"
		+"[�����������] [bit] NULL ,"
		+"[����] [int] NULL ,"
		+"[����] [int] NULL ,"
		+"[����] [int] NULL ,"
		+"[����������] [int] NULL ,"
		+"[���״̬] [int] NULL )";
	
	//=============================================================================
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		boolean bUploadResult = true;
		
		String decisionStr_HF_SRF = null;
		if(detectPanel.bDecision)
		{
			decisionStr_HF_SRF = "��";//"�ϣ��ϸ�";
		}
		else
		{
			decisionStr_HF_SRF = "��";//"�������ϸ�";			
		}
		String updateSQL = 
			"UPDATE "+LMSConstValue.sNvramCustomerProtocol_database_table.sValue+" SET"
			+" ���״̬="+8
			+" ,ʵ�⳵��="+detectPanel.iNvramDetectLength.iValue
			+",ʵ�⳵��="+detectPanel.iNvramDetectWidth.iValue
			+",ʵ�⳵��="+detectPanel.iNvramDetectHeight.iValue
			+",�������="+detectPanel.absoluteCarLength
			+",�������="+detectPanel.absoluteCarWidth
			+",�������="+detectPanel.absoluteCarHeight
			+",�����ж�='"+decisionStr_HF_SRF+"'"
			+" WHERE ������ˮ��='"+detectPanel.serialNumLabelTextField.getTextFieldText()+"'";
		
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
			+"�����������,ID,������ˮ��,���պ���,��������,��������,�����ͺ�,VIN��,��������,����Ա,��׼����,��׼����,��׼����,���״̬";

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
				if(iDetectStatus == 0) //0 --- δ���,�����Ѿ����
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
			LMSLog.exceptionDialog("���ݿ��쳣", e);
		}catch (Exception e) {
			LMSLog.exceptionDialog("���ݿ��쳣", e);
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
