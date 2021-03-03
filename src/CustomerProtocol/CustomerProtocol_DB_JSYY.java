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
		*/
	
	//=============================================================================
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		boolean bUploadResult = true;
		
		String decisionStrWK = null;
		if(detectPanel.bDecisionWHL)
		{
			decisionStrWK = "1";//"1：合格";
		}
		else
		{
			decisionStrWK = "2";//"2：不合格";			
		}
		
		String decisionStrLB = null;
		if(detectPanel.bSingleDecisionLanban)
		{
			decisionStrLB = "1";//"1：合格";
		}
		else
		{
			decisionStrLB = "2";//"2：不合格";			
		}

		String decisionStrZJ = null;
		if(detectPanel.bSingleDecisionZJ)
		{
			decisionStrZJ = "1";//"1：合格";
		}
		else
		{
			decisionStrZJ = "2";//"2：不合格";			
		}
		
		String updateSQL = 
			"UPDATE "+LMSConstValue.sNvramCustomerProtocol_database_table.sValue+" SET"
			+" jczt="+8 //0是待检，8是已经检测
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
			+",wkpd='"+decisionStrWK+"'"			//--外廓判定:未检填0，合格填1，不合格填2      
			+",lbgdpd='"+decisionStrLB+"'"			//--栏板高度判定:未检填0，合格填1，不合格填2  
			+",zjpd='"+decisionStrZJ+"'"			//--轴距判定:未检填0，合格填1，不合格填2  
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
			+"ID,     JYLSH,   HPHM,    HPZL,   CLLX, clpp1,clsbdh,     fdjh, jylb, ycy, " 
			+"bzcwkc, bzcwkk,  bzcwkg,bzlbgd,bzzj";
//			+"DetectCarType,"
//			+"DetectParam1,DetectParam2,DetectParam3,DetectParam4,DetectParam5,"
//			+"DetectParam6,DetectParam7,DetectParam8,DetectParam9,DetectParam10";
/*
 				+"ID,联网流水号,牌照号码,号牌种类,车辆类型,厂牌型号,VIN号,发动机号,检验类别,引车员,
				+标准车长,标准车宽,标准车高,标准栏板高,标准轴距"
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
			if(sNewOrOld != null && sNewOrOld.equals("00"))	// --- 检验类别, 00 --- 注册车 其他 --- 在用车 
				detectionVehicle.sNewOrOld = "注册车";
			else
				detectionVehicle.sNewOrOld = "在用车";				
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
