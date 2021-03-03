package AppBase.appBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsTransfer.lmsTransferRunnable.LMSTransferFSRLProtocolSocketServer;
import lmsTransfer.lmsTransferRunnable.LMSTransferResultSocketServer;
import lmsTransfer.lmsTransferRunnable.LMSTransferUserCmdSocketServer;
import lmsTransfer.lmsTransferRunnable.LMSTransferXMLProtocolSocketServer;
import CarDetect.CarDetectSetting;
import CustomerProtocol.CustomerProtocol;
import FileManager.FileManager;
import FileManager.exchangFileManager.DetectorStatusFileManager;
import FileManager.exchangFileManager.ExchangeFileManagerRunnable;

public class AppInit {
	public static void init()
	{
		LMSConstValue.genPasswordKey();

		//===========================================================================
		String iniFileName = "CarDetect_ini";
		FileManager.removeFolderFileByDate(Calendar.MONTH, -6, iniFileName); //6月
		
		//保存配置文件
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");//设置日期格式
		String time = df.format(new Date()); 
		String path = iniFileName;
 		FileManager.fileCopyWithRealTime(time, path, ".//", AppConst.MainDetectProperty);

		//====================================================================================
		//底层初始化----雷达
		CarDetectSetting.carDetectSetting.catDetectInit();
		
		//====================================================================================	
		new Thread(new LMSTransferResultSocketServer(LMSConstValue.LOCAL_IP).thread).start();   
		new Thread(new LMSTransferUserCmdSocketServer(LMSConstValue.LOCAL_IP).thread).start();   
		new Thread(new LMSTransferXMLProtocolSocketServer(LMSConstValue.LOCAL_IP).thread).start();   

		String strIP[] = LMSConstValue.getAllLocalHostIP();		
		for(int i=0;i<strIP.length;i++)
		{
			if(!strIP[i].equals(LMSConstValue.LOCAL_IP))
			{
				new Thread(new LMSTransferResultSocketServer(strIP[i]).thread).start();  
				new Thread(new LMSTransferUserCmdSocketServer(strIP[i]).thread).start();   
				new Thread(new LMSTransferXMLProtocolSocketServer(strIP[i]).thread).start();   
			}
		}
		
		LMSTransferFSRLProtocolSocketServer.lmsTransferFSRLProtocolSocketServer = new LMSTransferFSRLProtocolSocketServer();;
		new Thread(LMSTransferFSRLProtocolSocketServer.lmsTransferFSRLProtocolSocketServer.thread).start();   
		
		//====================================================================================		
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
		{			
			new DetectorStatusFileManager(i);
		}
		new DetectorStatusFileManager(LMSConstValue.LIGHT_CURTAIN_ID_START);
		
		//====================================================================================		
		CustomerProtocol.init();

		new Thread(new ExchangeFileManagerRunnable(2000).thread()).start(); 
		
		new CarTypeAdapter().init();
		
		//====================================================================================		
 		SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.versionSdf);
		Date dateNow = new Date();
		String strNowTime = sdf.format(dateNow);
		long lCurrentTime = LMSConstValue.stringToLongTime(strNowTime,LMSConstValue.versionSdf);      			
		long lVersionTime = LMSConstValue.stringToLongTime(LMSConstValue.softwareVersionTime,LMSConstValue.versionSdf);      			

		int I_TIME_OFFSET = 60;
		Calendar cl = Calendar.getInstance();
		cl.setTime(dateNow);
		cl.add(Calendar.DAY_OF_YEAR,-I_TIME_OFFSET);	
		Date date = cl.getTime();
		String strTime = sdf.format(date);
		long lTimeOffset = LMSConstValue.stringToLongTime(strTime,LMSConstValue.versionSdf);      			

		cl.setTime(dateNow);
		cl.add(Calendar.DAY_OF_YEAR,-(I_TIME_OFFSET-10));	
		date = cl.getTime();
		String strTimeWarning = sdf.format(date);
		long lTimeOffsetWarning = LMSConstValue.stringToLongTime(strTimeWarning,LMSConstValue.versionSdf);      			

		//----------------------------------------------------------------
		if(lTimeOffset>lVersionTime)
		{
			JOptionPane.showMessageDialog(null, "软件版本过期!!!请联系供应商:"+LMSConstValue.softwareVersion, "提示", JOptionPane.OK_OPTION);

			System.exit(0);
		}
		else if(lTimeOffsetWarning>lVersionTime)
		{	        
			JOptionPane.showMessageDialog(null, "软件版本即将失效,请尽快联系供应商:"+LMSConstValue.softwareVersion, "提示", JOptionPane.OK_OPTION);
		}
	}
}
