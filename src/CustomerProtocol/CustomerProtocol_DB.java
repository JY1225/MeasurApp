package CustomerProtocol;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DataBaseConst;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public abstract class CustomerProtocol_DB extends CustomerProtocol{
	private final static String DEBUG_TAG="CustomerProtocol_DB";
	
	public abstract void parseDetectionList(int row, ResultSet rs, ResultSetMetaData rsmd);	

	protected void getResultSet(ResultSet rs, String tableName) 
	{ 
		DetectionVehicle.detectionVehicleList.clear();

		//==========================================================
		//��λ�����һ����¼							
		try { 
			boolean moreRecords = rs.next(); 
			
			//���û�м�¼������ʾһ����Ϣ
			if ( !moreRecords ) { 
				if(DetectionVehicle.bAutoRefresh == false)
				{
					LMSLog.errorDialog("�޼�¼", "���޼�¼"); 
				}
				
				return;
			}

			//��ȡ�ֶε�����
			ResultSetMetaData rsmd = rs.getMetaData(); 	
			
			//��ȡ��¼��
			int i = 0;
			do {
				parseDetectionList( ++i, rs, rsmd ); 
			} while ( rs.next() ); 			
		}catch (SQLException e) { 
			LMSLog.exception(e);
		}		
	}
	
	public void queryDetectionListDB(String querySQL)
	{
		String table = LMSConstValue.sNvramCustomerProtocol_database_table.sValue;
		
		LMSLog.d(DEBUG_TAG, "queryDetectionList!!!"+DataBaseConst.customerProtocol_conn);

		if(DataBaseConst.customerProtocol_conn != null)
		{
			querySQL += (" FROM "+table);
			
			LMSLog.d(DEBUG_TAG,"querySQL="+querySQL);

			try { 				
				//ִ��SQL���
				Statement statement = DataBaseConst.customerProtocol_conn.createStatement(); 
				ResultSet resultSet = statement.executeQuery( querySQL ); 
				
				//�ڱ������ʾ��ѯ���
				getResultSet(
					resultSet,
					table); 
			}catch (SQLException e) {
				LMSLog.exception(e);
				LMSLog.errorDialog("���ݿ��쳣", querySQL+"\r\n"+e);
			}
		}
		else
		{
			LMSLog.errorDialog("���ݿ��쳣", "customerProtocol_conn null!");
		}
	}
	
	//���������������:XML��ʽ,����
	public void parseDetectionList_WS(String xmlStr){};	
}
