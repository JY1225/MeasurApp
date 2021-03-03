package App;

import java.awt.EventQueue;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.naming.LinkException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import lmsEvent.LMSEventManager;

import org.apache.log4j.PropertyConfigurator;

import AppBase.ImplementPC.LogImplementPC;
import AppBase.ImplementPC.PlatformImplementPC;
import AppFrame.contourDetection.ContourDetectionFrame;

public class CoutourDetection extends AppStartBase{
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

		if(args.length > 0)
		{
//			LMSLog.warningDialog("para",args[0]);
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
					
					new CoutourDetection();
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

	private CoutourDetection() {
		try {
			LMSConstValue.defaultDetectType = LMSConstValue.enumDetectType.WH2_L1_DIF;
			
			new ContourDetectionFrame(LMSConstValue.AppType.CONTOUR_DETECTION);			
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
