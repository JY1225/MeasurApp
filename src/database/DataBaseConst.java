package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import AppBase.ImplementPC.LogImplementPC;
import AppFrame.contourDetection.ContourDetectionDataBaseConst;
import AppFrame.debugerManager.SettingFrameDebug.GeneratingDialog;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class DataBaseConst {	
	private final static String DEBUG_TAG="DataBaseConst";

	public final static String JDBC_DRIVER_NAME_MYSQL = "com.mysql.jdbc.Driver";
	public final static String JDBC_DRIVER_NAME_SQL_SERVER2000 = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	public final static String JDBC_DRIVER_NAME_SQL_SERVER2005 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public final static String JDBC_URL_HEADER_MYSQL = "jdbc:mysql://";
	public final static String JDBC_URL_HEADER_SQL_SERVER2000 = "jdbc:microsoft:sqlserver://";
	public final static String JDBC_URL_HEADER_SQL_SERVER2005 = "jdbc:sqlserver://";

	public static Connection local_store_access_conn; 
	public static Connection customerProtocol_conn; 

	public static void databaseNotConnect()
	{
		LMSLog.errorDialog("数据库错误","未连接数据库");
	}
	
	JDialog dialog;
	public class GeneratingDialog extends Thread
	{
		public GeneratingDialog(String str)
		{
			JOptionPane op = new JOptionPane("本对话框将在30秒后关闭",JOptionPane.INFORMATION_MESSAGE);
	        
			dialog = op.createDialog("数据库表新插入列中:"+str);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setAlwaysOnTop(true);
			dialog.setModal(false);
			dialog.setVisible(true);
			dialog.show();
		}
		
		public void run()
		{
		}
	}
	
	public static List getDataBaseTableColumnNames(String table)
	{
		List<String> columnHeads = new ArrayList<String>(); 
		String query = "select column_name from information_schema.columns where table_name = '" + table + "'";; 
		
		if(DataBaseConst.local_store_access_conn != null)
		{
			try { 
				LMSLog.d(DEBUG_TAG,"getColumnNames="+query);
				
				//执行SQL语句
				Statement statement = DataBaseConst.local_store_access_conn.createStatement(); 
				ResultSet rs = statement.executeQuery( query ); 
				ResultSetMetaData rsmd = rs.getMetaData() ; 
								
				rs.next();
				do {
					for ( int i = 1; i <= rsmd.getColumnCount(); ++i )
					{
						LMSLog.d(DEBUG_TAG, rs.getString( i ));
						columnHeads.add(rs.getString( i ));
					}
				} while ( rs.next() ); 

			}catch (SQLException e) { 
				LMSLog.exception(e);
			}
		}
		
		return columnHeads;
	}
	
	public static List getCreateTableSQLColumnNames(String createTableSQL)
	{
		List<String[]> columnHeads = new ArrayList<String[]>(); 
		LMSLog.d(DEBUG_TAG+"createTableSQL 0=", createTableSQL);

		int startIndex = 0,endIndex = 0;	
		
		startIndex = createTableSQL.indexOf('(',0);
		endIndex = createTableSQL.indexOf(")DEFAULT",0);
		createTableSQL = createTableSQL.substring(startIndex+1,endIndex);
		LMSLog.d(DEBUG_TAG+"createTableSQL 1=", createTableSQL);
		
		startIndex = 0;
		endIndex = 0;
		while(startIndex >= 0)
		{
			String value[] = new String[2];

			startIndex = endIndex;
			
			startIndex = createTableSQL.indexOf(',',startIndex);
			if(startIndex < 0)
				break;
			endIndex = createTableSQL.indexOf(' ',startIndex);
			value[0] = createTableSQL.substring(startIndex+1 ,endIndex);
			
			int nextIndex = createTableSQL.indexOf(',',startIndex+1);
			if(nextIndex > 0)
			{
				value[1] = createTableSQL.substring(startIndex+1 ,nextIndex);
			}
			else
			{
				value[1] = createTableSQL.substring(startIndex+1);				
			}
			
//			LMSLog.d(DEBUG_TAG, "value[0]="+value[0]);
//			LMSLog.d(DEBUG_TAG, "value[1]="+value[1]);

			columnHeads.add(value);			
		}
		
		return columnHeads;
	}
}
