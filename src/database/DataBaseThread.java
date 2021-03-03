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
		
		//���������������������ݿ�
		try {
			//=========================================================================================
			// ֮����Ҫʹ������������䣬����ΪҪʹ��MySQL����������������Ҫ�������������� 
			// ����ͨ��Class.forName�������ؽ�ȥ��Ҳ����ͨ����ʼ������������������������ʽ������ 
			// or: 
			// com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver(); 
			// or�� 
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

			LMSLog.d(DEBUG_TAG,"�ɹ��������ݿ���������:"+jdbcDriverName); 

		}catch ( ClassNotFoundException e ) { //����������������쳣
			LMSLog.exception(e);
			LMSLog.errorDialog("����","װ�� JDBC/ODBC ��������ʧ��:"+jdbcDriverName); 
			System.exit( 1 ); // terminate program 
		}
		
		return jdbcUrlHeader;
	}
	
	public void start() { 		
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//===============================================================
		myThread.start();                                //���߳̿�ʼ                 		
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
				//�Զ�����,��ʾ�޷��������ݿ������;���ɹ����Ϻ�,�Զ��رո���ʾ��
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
				//���ӳɹ�,�Զ���ѯ
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
			String str = "�޷��������ݿ�:"+jdbcUrlHeader+"\r\n" 
				+"dataBaseUrl="+dataBaseUrl+" account="+account+" password="+password
				+"\r\n"+LogImplementPC.getStackTrace(e);
					
			if(dialogConnectError == null)
				(new GeneratingConnectErrorDialog(str)).start();				

			LMSLog.exception(e);
		} catch (Exception e) {
			LMSLog.exception(e);
			LMSLog.errorDialog("���ݿ��쳣", "dataBaseUrl="+dataBaseUrl+" account="+account+" password="+password); 		
		}
		
		LMSLog.d(DEBUG_TAG,"���ݿ����ӳɹ�:"+jdbcUrlHeader
				+"dataBaseUrl="+dataBaseUrl+" account="+account+" password="+password);

		String DATA_BASE_ACCESS_URL = null;
		try {
			if(create_conn != null)
			{
				dbmd = create_conn.getMetaData();  

				LMSLog.d(DEBUG_TAG,"���ݿ���:"+dataBaseName);

				rs = dbmd.getCatalogs();//ȡ���ڴ����ݿ���ʹ�õ������,��mysql��˵���˾��ǿ��õ����ݿ����ƣ�ֻ��һ�� 
				boolean bHasDataBase = false;
				while(rs.next()){          
					LMSLog.d(DEBUG_TAG,"rs.getString(1)="+rs.getString(1));		
					
					if(rs.getString(1).toUpperCase().equals(dataBaseName.toUpperCase()))
					{
						bHasDataBase = true;
					}
		        }  
				
				//����DATABASE
				if(bHasDataBase == false)
				{
					Statement statement = create_conn.createStatement();      
					sql = "CREATE DATABASE "+dataBaseName;   
					
					statement.executeUpdate(sql);
				}
				create_conn.close();
							 
				//���ݿⴴ�����
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
	
				LMSLog.d(DEBUG_TAG,"����:"+tableName);

				rs = dbmd.getTables(dataBaseName, null, null, new String[]{"TABLE"});
				boolean bHasTable = false;
				while(rs.next()){  
					LMSLog.d(DEBUG_TAG,"rs.getString(3)="+rs.getString(3));		

					if(rs.getString(3).equals(tableName))
					{
						bHasTable = true;
					}
		        }
				
				//����TABLE
				if(bHasTable == false && createTableSQL != null)
				{
					Statement stmt = conncet_conn.createStatement(); 
					
					String createSql = "create table "+tableName+createTableSQL;
					LMSLog.d(DEBUG_TAG,"createSql="+createSql);
					stmt.executeUpdate(createSql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ� 
				}
			}
		} catch (SQLException e) {
			LMSLog.exception(e);
			LMSLog.errorDialog(
				"�޷��������ݿ�", 
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

			dialogConnectError = op.createDialog("�޷��������ݿ�");
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
