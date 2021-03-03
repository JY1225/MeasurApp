package CustomerProtocol;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import database.DataBaseConst;

public class CustomerProtocol_DB_JSYY extends CustomerProtocol_DB{
	private final static String DEBUG_TAG="CustomerProtocol_DB_JSYY";

	/*
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
		*/
	
	//=============================================================================
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		boolean bUploadResult = true;
		
		String decisionStrWK = null;
		if(detectPanel.bDecisionWHL)
		{
			decisionStrWK = "1";//"1���ϸ�";
		}
		else
		{
			decisionStrWK = "2";//"2�����ϸ�";			
		}
		
		String decisionStrLB = null;
		if(detectPanel.bSingleDecisionLanban)
		{
			decisionStrLB = "1";//"1���ϸ�";
		}
		else
		{
			decisionStrLB = "2";//"2�����ϸ�";			
		}

		String decisionStrZJ = null;
		if(detectPanel.bSingleDecisionZJ)
		{
			decisionStrZJ = "1";//"1���ϸ�";
		}
		else
		{
			decisionStrZJ = "2";//"2�����ϸ�";			
		}
		
		String updateSQL = 
			"UPDATE "+LMSConstValue.sNvramCustomerProtocol_database_table.sValue+" SET"
			+" jczt="+8 //0�Ǵ��죬8���Ѿ����
			+",sccwkc="+detectPanel.iNvramDetectLength.iValue
			+",sccwkk="+detectPanel.iNvramDetectWidth.iValue
			+",sccwkg="+detectPanel.iNvramDetectHeight.iValue
			+",sclbgd="+detectPanel.iNvramDetectLanbanHeight.iValue
			+",sczj="+detectPanel.iNvramDetectZJ.iValue
			+",wkcwc="+detectPanel.absoluteCarLength
			+",wkkwc="+detectPanel.absoluteCarWidth
			+",wkgwc="+detectPanel.absoluteCarHeight
			+",lbgdwc="+detectPanel.absoluteCarLanbanHeight
			+",zjwc="+detectPanel.absoluteCarZJ
			+",wkpd='"+decisionStrWK+"'"			//--�����ж�:δ����0���ϸ���1�����ϸ���2      
			+",lbgdpd='"+decisionStrLB+"'"			//--����߶��ж�:δ����0���ϸ���1�����ϸ���2  
			+",zjpd='"+decisionStrZJ+"'"			//--����ж�:δ����0���ϸ���1�����ϸ���2  
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
			+"ID,     JYLSH,   HPHM,    HPZL,   CLLX, clpp1,clsbdh,     fdjh, jylb, ycy, " 
			+"bzcwkc, bzcwkk,  bzcwkg,bzlbgd,bzzj";
//			+"DetectCarType,"
//			+"DetectParam1,DetectParam2,DetectParam3,DetectParam4,DetectParam5,"
//			+"DetectParam6,DetectParam7,DetectParam8,DetectParam9,DetectParam10";
/*
 				+"ID,������ˮ��,���պ���,��������,��������,�����ͺ�,VIN��,��������,�������,����Ա,
				+��׼����,��׼����,��׼����,��׼�����,��׼���"
 */
	
		//---------------------------------------------------------
		queryDetectionListDB(querySQL);
	}
	
	@Override
	@SuppressWarnings({"rawtypes","unchecked"})
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
							
			detectionVehicle.sSerialNum = rs.getString(i++);
			detectionVehicle.sVehicleNum = rs.getString(i++);
			detectionVehicle.sVehicleNumType = rs.getString(i++);
			detectionVehicle.sVehicleType = rs.getString(i++);
			detectionVehicle.sVehicleBrand = rs.getString(i++);
			detectionVehicle.sVehicleID = rs.getString(i++);
			detectionVehicle.sMotorID = rs.getString(i++);
			String sNewOrOld = rs.getString(i++);
			if(sNewOrOld != null && sNewOrOld.equals("00"))	// --- �������, 00 --- ע�ᳵ ���� --- ���ó� 
				detectionVehicle.sNewOrOld = "ע�ᳵ";
			else
				detectionVehicle.sNewOrOld = "���ó�";				
			detectionVehicle.sOperatorName = rs.getString(i++);
			
			detectionVehicle.sOriginalCarLength = rs.getString(i++);
			detectionVehicle.sOriginalCarWidth = rs.getString(i++);
			detectionVehicle.sOriginalCarHeight = rs.getString(i++);
			detectionVehicle.sOriginalLanBanHeight = rs.getString(i++);
			detectionVehicle.sOriginalZJ = rs.getString(i++);
			/*
			//---------------------------------------------------------------
			detectionVehicle.sCarTypeString = rs.getString(i++);
			for(int j=1; j<=10; j++)
			{
				String str = rs.getString(i++);
				if(str != null)
				{
					if(str.equals("DetectLanBan"))
					{
						detectionVehicle.bLanbanDetect = true;							
					}
					else if(str.equals("DetectFilter_GuaChe_CheLan"))
					{
						detectionVehicle.bFilterCheLan = true;
					}
					else if(str.equals("DetectFilter_EndGas"))
					{
						detectionVehicle.bFilterEndGas = true;
					}
				}
			}		
			*/
			DetectionVehicle.detectionVehicleList.add(detectionVehicle);
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
