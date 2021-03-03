package database;

import http.Jason.JdHttpJason;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppBase.ImplementPC.LogImplementPC;
import AppFrame.contourDetection.ContourDetectionDataBaseConst;
import CustomerProtocol.CustomerProtocol;
import CustomerProtocol.CustomerProtocol_DB_HF_SRF;
import CustomerProtocol.CustomerProtocol_DB_JSYY;
import CustomerProtocol.CustomerProtocol_WS_SDGC;
import CustomerProtocol.DetectionVehicle;
import FileManager.FileManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class DataBaseThread implements Runnable{
	private final static String DEBUG_TAG="DataBaseThread";
	
	Thread myThread = new Thread(this); 

	String DATA_BASE_NAME;
	String TABLE_NAME;
	String createTableSQL;
	
	String ip;
	String port;
	String userName;
	String password;
	
	public enum DataBaseUserTypeEnum{
		LOCAL_DATABASE,
		PROTOCOL_DATABASE,
	}
	DataBaseUserTypeEnum sDataBaseUserType;
	public static ArrayBlockingQueue dataBaseQueue = new ArrayBlockingQueue(10); 
	
	public DataBaseThread(
		DataBaseUserTypeEnum _sDataBaseUserType,
		String _DATA_BASE_NAME, String _TABLE_NAME,	String _createTableSQL,
		String _ip,String _port,
		String _userName,String _password
	)
	{
		sDataBaseUserType = _sDataBaseUserType;

		LMSLog.d(DEBUG_TAG, "DataBaseThread sDataBaseUserType="+sDataBaseUserType);
	
		DATA_BASE_NAME = _DATA_BASE_NAME;
		TABLE_NAME = _TABLE_NAME;
		createTableSQL = _createTableSQL;
		
		ip = _ip;
		port = _port;
		userName = _userName;
		password = _password;		
	}
	
	public String initDataBaseDriver()
	{
		String jdbcUrlHeader = null;
		String jdbcDriverName = DataBaseConst.JDBC_DRIVER_NAME_MYSQL;
		String sDataBase = null;
		if(sDataBaseUserType == DataBaseUserTypeEnum.LOCAL_DATABASE)
		{
			sDataBase = LMSConstValue.localStoreDatabaseEnumType.key;
		}
		else
		{
			sDataBase = LMSConstValue.protocolDatabaseEnumType.key;			
		}
		
		//加载驱动程序以连接数据库
		try {
			//=========================================================================================
			// 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来， 
			// 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以 
			// or: 
			// com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver(); 
			// or： 
			// new com.mysql.jdbc.Driver(); 
			if(sDataBase.equals(LMSConstValue.DataBaseType.DATABASE_TYPE_MYSQL))
			{
				jdbcDriverName = DataBaseConst.JDBC_DRIVER_NAME_MYSQL;
				jdbcUrlHeader = DataBaseConst.JDBC_URL_HEADER_MYSQL;
			}
			else if(sDataBase.equals(LMSConstValue.DataBaseType.DATABASE_TYPE_SQL_SERVER_2000))
			{
				jdbcDriverName = DataBaseConst.JDBC_DRIVER_NAME_SQL_SERVER2000;
				jdbcUrlHeader = DataBaseConst.JDBC_URL_HEADER_SQL_SERVER2000;
			}
			else if(sDataBase.equals(LMSConstValue.DataBaseType.DATABASE_TYPE_SQL_SERVER_2005))
			{
				jdbcDriverName = DataBaseConst.JDBC_DRIVER_NAME_SQL_SERVER2005;
				jdbcUrlHeader = DataBaseConst.JDBC_URL_HEADER_SQL_SERVER2005;
			}
			Class.forName(jdbcDriverName);

			LMSLog.d(DEBUG_TAG,"成功加载数据库驱动程序:"+jdbcDriverName); 

		}catch ( ClassNotFoundException e ) { //捕获加载驱动程序异常
			LMSLog.exception(e);
			LMSLog.errorDialog("错误","装载 JDBC/ODBC 驱动程序失败:"+jdbcDriverName); 
			System.exit( 1 ); // terminate program 
		}
		
		return jdbcUrlHeader;
	}
	
	public void start() { 		
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//===============================================================
		myThread.start();                                //主线程开始                 		
	} 
	
	@Override
	public void run() {                  
        LMSLog.d(DEBUG_TAG,"thread run:"+Thread.currentThread().getId());

        //==============================================================
		String jdbcUrlHeader = initDataBaseDriver();

        try{
			if(jdbcUrlHeader != null)
			{
				Connection connection = null;
				//自动开机,提示无法连接数据库的问题;当成功连上后,自动关闭该提示框
				while(connection == null)
				{					
					connection = create_database(
						jdbcUrlHeader, 
						DATA_BASE_NAME, TABLE_NAME,	createTableSQL,
						ip, port,
						userName,password
					);

					Thread.sleep(2000);
					
					LMSLog.d(DEBUG_TAG, "sDataBaseUserType="+sDataBaseUserType+" connection="+connection);
				}
				
				if(sDataBaseUserType.equals(DataBaseUserTypeEnum.LOCAL_DATABASE))
				{
					DataBaseConst.local_store_access_conn = connection;
					
					LMSLog.d(DEBUG_TAG, "local_store_access_conn="+DataBaseConst.local_store_access_conn);
				}
				else
				{
					DataBaseConst.customerProtocol_conn = connection;

					LMSLog.d(DEBUG_TAG, "customerProtocol_conn="+DataBaseConst.customerProtocol_conn);
				}
								
				if(dialogConnectError != null)
					dialogConnectError.dispose();
				
				//============================================================
				//连接成功,自动查询
				if(sDataBaseUserType.equals(DataBaseUserTypeEnum.PROTOCOL_DATABASE))
				{
					while(DetectionVehicle.bAutoRefresh && DetectionVehicle.bProtocolDataBase)
	       			{
						if(CustomerProtocol.customerProtocol != null)
						{
							CustomerProtocol.customerProtocol.queryDetectionList(null);
							
							if(DetectionVehicle.detectionVehicleList.size() > 0)
							{			
								CustomerProtocol.notifyDetectionList();	
							}	
						}
	       				
	       				Thread.sleep(2000);
	       			}    
				}
			}
        } catch (Exception e) {
       		LMSLog.exceptionDialog(null, e);
		}
    }    
		
	public Connection create_database(
		String jdbcUrlHeader,
		String dataBaseName,String tableName,String createTableSQL,
		String ip,String port,
		String account,String password
	)
	{
		Connection create_conn = null; 
		Connection conncet_conn = null;
		String sql; 
		String dataBaseUrl = jdbcUrlHeader
			+ip
			+":"
			+port;

		//=========================================================================================
		ResultSet rs;
		DatabaseMetaData dbmd;
		try {			
			create_conn = (Connection) DriverManager.getConnection(dataBaseUrl, account, password);
		} catch (SQLException e) {
			String str = "无法连接数据库:"+jdbcUrlHeader+"\r\n" 
				+"dataBaseUrl="+dataBaseUrl+" account="+account+" password="+password
				+"\r\n"+LogImplementPC.getStackTrace(e);
					
			if(dialogConnectError == null)
				(new GeneratingConnectErrorDialog(str)).start();				

			LMSLog.exception(e);
		} catch (Exception e) {
			LMSLog.exception(e);
			LMSLog.errorDialog("数据库异常", "dataBaseUrl="+dataBaseUrl+" account="+account+" password="+password); 		
		}
		
		LMSLog.d(DEBUG_TAG,"数据库连接成功:"+jdbcUrlHeader
				+"dataBaseUrl="+dataBaseUrl+" account="+account+" password="+password);

		String DATA_BASE_ACCESS_URL = null;
		try {
			if(create_conn != null)
			{
				dbmd = create_conn.getMetaData();  

				LMSLog.d(DEBUG_TAG,"数据库名:"+dataBaseName);

				rs = dbmd.getCatalogs();//取可在此数据库中使用的类别名,在mysql中说白了就是可用的数据库名称，只有一列 
				boolean bHasDataBase = false;
				while(rs.next()){          
					LMSLog.d(DEBUG_TAG,"rs.getString(1)="+rs.getString(1));		
					
					if(rs.getString(1).toUpperCase().equals(dataBaseName.toUpperCase()))
					{
						bHasDataBase = true;
					}
		        }  
				
				//创建DATABASE
				if(bHasDataBase == false)
				{
					Statement statement = create_conn.createStatement();      
					sql = "CREATE DATABASE "+dataBaseName;   
					
					statement.executeUpdate(sql);
				}
				create_conn.close();
							 
				//数据库创建完毕
				if(jdbcUrlHeader.equals(DataBaseConst.JDBC_URL_HEADER_MYSQL))
				{
					DATA_BASE_ACCESS_URL = jdbcUrlHeader+ip+":"+port+"/"+dataBaseName+"?&useUnicode=true&characterEncoding=UTF8"; 
				}
				else if(jdbcUrlHeader.equals(DataBaseConst.JDBC_URL_HEADER_SQL_SERVER2000)
					||jdbcUrlHeader.equals(DataBaseConst.JDBC_URL_HEADER_SQL_SERVER2005))
				{
					DATA_BASE_ACCESS_URL = jdbcUrlHeader+ip+":"+port+";DatabaseName="+dataBaseName;				
				}	
				
				conncet_conn = DriverManager.getConnection(DATA_BASE_ACCESS_URL, account, password);
				dbmd = conncet_conn.getMetaData();  
	
				LMSLog.d(DEBUG_TAG,"表名:"+tableName);

				rs = dbmd.getTables(dataBaseName, null, null, new String[]{"TABLE"});
				boolean bHasTable = false;
				while(rs.next()){  
					LMSLog.d(DEBUG_TAG,"rs.getString(3)="+rs.getString(3));		

					if(rs.getString(3).equals(tableName))
					{
						bHasTable = true;
					}
		        }
				
				//创建TABLE
				if(bHasTable == false && createTableSQL != null)
				{
					Statement stmt = conncet_conn.createStatement(); 
					
					String createSql = "create table "+tableName+createTableSQL;
					LMSLog.d(DEBUG_TAG,"createSql="+createSql);
					stmt.executeUpdate(createSql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功 
				}
			}
		} catch (SQLException e) {
			LMSLog.exception(e);
			LMSLog.errorDialog(
				"无法连接数据库", 
					"DATA_BASE_ACCESS_URL="+DATA_BASE_ACCESS_URL
					+" account="+account
					+" password="+password
					+"\n\r"+e); 			
		} 
		
		return conncet_conn;
	}

	
	JDialog dialogConnectError;
	public class GeneratingConnectErrorDialog extends Thread
	{
		public GeneratingConnectErrorDialog(String str)
		{
			JOptionPane op = new JOptionPane(str,JOptionPane.INFORMATION_MESSAGE);

			dialogConnectError = op.createDialog("无法连接数据库");
			dialogConnectError.setSize(1000, 500);
			dialogConnectError.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialogConnectError.setAlwaysOnTop(true);
			dialogConnectError.setModal(false);
			dialogConnectError.setVisible(true);
			dialogConnectError.show();
		}
		
		public void run()
		{
		}
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
			}

	        if(eventType != null && (eventType.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT))) 
	        {	    	
				int carState = 0;

				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
					carState = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE); 

	        }
	    }
	}
}
