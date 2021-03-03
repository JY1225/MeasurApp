package App;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lmsEvent.LMSEventManager;

import org.apache.log4j.PropertyConfigurator;

import AppBase.ImplementPC.LogImplementPC;
import AppBase.ImplementPC.PlatformImplementPC;
import AppBase.appBase.AppConst;
import AppFrame.contourDetection.ContourDetectionFrame;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;

public class ContourDetectionKTS extends AppStartBase{
	private final static String DEBUG_TAG="CoutourDetection";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("CarDetectLog4j.properties");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
	        LMSLog.setCallFunc(new LogImplementPC());
	        LMSPlatform.setCallFunc(new PlatformImplementPC());
	        
	      	LMSConstValue.lmsEventManager = new LMSEventManager();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
    		LMSLog.exception(e);
		}

		try {
		    File directory = new File("");//设定为当前文件夹 
			LMSLog.d(DEBUG_TAG,"java.library.path 0 ="+System.getProperty("java.library.path"));        
			System.setProperty("java.library.path",directory.getAbsolutePath()+"\\lib");
			LMSLog.d(DEBUG_TAG,"java.library.path 1 ="+System.getProperty("java.library.path"));       
//			System.loadLibrary("jogl");
		}
        catch(UnsatisfiedLinkError e)
        {
        	LMSLog.exceptionDialog(null,e); 
        	
	        System.exit(0);   
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					appStartCheck();

					new ContourDetectionKTS();
				}
				catch(OutOfMemoryError e)
		        {
		        	LMSLog.outOfMemoryDialog(e); 
		        }
				catch(Exception e)
		        {
		        	LMSLog.exceptionDialog(null,e); 
		        	
			        System.exit(0);   
		        }
			}
		});
	}

	/**
	 * Create the application.
	 */

	private ContourDetectionKTS() {
		AppConst.companyName = "KTS";
		
		try {
			LMSConstValue.defaultDetectType = LMSConstValue.enumDetectType.WH2_L1_DIF;

			new ContourDetectionFrame(LMSConstValue.AppType.CAR_DETECT);
		}
		catch(LinkageError e)
        {
        	LMSLog.exceptionDialog(null,e); 
        	
	        System.exit(0);   
        }
		catch(Exception e)
        {
        	LMSLog.exceptionDialog(null,e); 
        	
	        System.exit(0);   
        }
	}
}
