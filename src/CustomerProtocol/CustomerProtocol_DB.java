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
		//定位到达第一条记录							
		try { 
			boolean moreRecords = rs.next(); 
			
			//如果没有记录，则提示一条消息
			if ( !moreRecords ) { 
				if(DetectionVehicle.bAutoRefresh == false)
				{
					LMSLog.errorDialog("无记录", "查无记录"); 
				}
				
				return;
			}

			//获取字段的名称
			ResultSetMetaData rsmd = rs.getMetaData(); 	
			
			//获取记录集
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
				//执行SQL语句
				Statement statement = DataBaseConst.customerProtocol_conn.createStatement(); 
				ResultSet resultSet = statement.executeQuery( querySQL ); 
				
				//在表格中显示查询结果
				getResultSet(
					resultSet,
					table); 
			}catch (SQLException e) {
				LMSLog.exception(e);
				LMSLog.errorDialog("数据库异常", querySQL+"\r\n"+e);
			}
		}
		else
		{
			LMSLog.errorDialog("数据库异常", "customerProtocol_conn null!");
		}
	}
	
	//解析待检队列数据:XML格式,无用
	public void parseDetectionList_WS(String xmlStr){};	
}
