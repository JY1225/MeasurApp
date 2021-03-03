package AppBase.ImplementPC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import CarAppAlgorithm.LightCurtainAlgorithm;
import CarAppAlgorithm.LongLineAlgorithm;
import CarAppAlgorithm.WidthHeightDetectRunnable;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSLogInterface;

public class LogImplementPC implements LMSLogInterface {
	private static Logger little = LogManager.getLogger("little");
	private static Logger logger = LogManager.getLogger("log");
	private static Logger exception = LogManager.getLogger("exception");
	private static Logger lightCurtainLogger_0 = LogManager.getLogger("lightCurtain_0");
	private static Logger dataLogger_0 = LogManager.getLogger("dataLog_0");
	private static Logger dataLogger_1 = LogManager.getLogger("dataLog_1");
	private static Logger dataLogger_2 = LogManager.getLogger("dataLog_2");
	private static Logger car_out = LogManager.getLogger("car_out");
	private static Logger out_error = LogManager.getLogger("out_error");

	public void LMS_DEBUG_D(String debugTag,String DebugString)
	{
		logger.debug(debugTag+":"+DebugString);
	}

   	public void LMS_DEBUG_I(String debugTag,String DebugString)
	{
		logger.debug(debugTag+":"+DebugString);
	}

  	public void LMS_DEBUG_W(String debugTag,String DebugString)
	{
		logger.debug(debugTag+":"+DebugString);
	}
  	
   	public void LMS_DEBUG_E(String debugTag,String DebugString)
	{
		logger.error(debugTag+":"+DebugString); 
	}
 	
 	public void LMS_LITTLE(String debugTag,String DebugString)
	{
 		little.error(debugTag+":"+DebugString); 
	}
 	
	public static String getStackTrace(Throwable aThrowable) {
	    final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	}

 	public void LMS_EXCEPTION(int sensorID,Throwable t)
	{
 		logger.debug("EXCEPTION:"+sensorID);
 		t.printStackTrace();
 		exception.error("sensor:"+sensorID); 
 		exception.error(getStackTrace(t)); 
	}

 	public void LMS_EXCEPTION(Throwable t)
	{
 		t.printStackTrace();
 		exception.error(getStackTrace(t)); 
	}
 	
 	public void LMS_OUT_OF_MEMORY_DIALOG(Throwable t)
 	{
 		String strOutOfMemory = "";
 		
 		if(LightCurtainAlgorithm.lightCurtainCarInLineList != null)
 			strOutOfMemory += "光栅:"+LightCurtainAlgorithm.lightCurtainCarInLineList.size()+"\r\n";
 		if(LongLineAlgorithm.longLineList != null)
 			strOutOfMemory += "长:"+LongLineAlgorithm.longLineList.size()+"\r\n"; 	
		if(WidthHeightDetectRunnable.bubbleListArray[0].bubbleList != null)
		{
 			strOutOfMemory += "宽高1,listSize:"+WidthHeightDetectRunnable.bubbleListArray[0].bubbleList.size()+"\r\n";	
 			
 			if(WidthHeightDetectRunnable.bubbleListArray[0].bubbleList.size() > 0)
 				strOutOfMemory += " 最大:"+WidthHeightDetectRunnable.bubbleListArray[0].bubbleList.get(WidthHeightDetectRunnable.bubbleListArray[0].biggestBubble).bubble.size()+"\r\n";	
		}
		if(WidthHeightDetectRunnable.bubbleListArray[1].bubbleList != null)
		{
 			strOutOfMemory += "宽高2,listSize:"+WidthHeightDetectRunnable.bubbleListArray[1].bubbleList.size()+"\r\n";			

 			if(WidthHeightDetectRunnable.bubbleListArray[1].bubbleList.size() > 0)
 				strOutOfMemory += " 最大:"+WidthHeightDetectRunnable.bubbleListArray[1].bubbleList.get(WidthHeightDetectRunnable.bubbleListArray[1].biggestBubble).bubble.size()+"\r\n";	
		}
		
		t.printStackTrace();
		strOutOfMemory += getStackTrace(t)+"\r\n";
 		exception.error(strOutOfMemory); 

		JOptionPane.showMessageDialog(null, strOutOfMemory, "内存溢出:"+LMSConstValue.softwareVersion, JOptionPane.ERROR_MESSAGE); 
		
        System.exit(0);   
 	}

	public void LMS_EXCEPTION_DIALOG(String title,Throwable t)
	{
		t.printStackTrace();
 		exception.error(getStackTrace(t)); 
 		if(title == null)
 			title = "检测仪异常:"+LMSConstValue.softwareVersion;
 			
		JOptionPane.showMessageDialog(null, getStackTrace(t), title, JOptionPane.ERROR_MESSAGE); 
	}

	public void LMS_EXCEPTION_DIALOG(String title, Throwable t, String headStr) {
		t.printStackTrace();
 		exception.error(headStr+"\r\n"+getStackTrace(t)); 
 		if(title == null)
 			title = "检测仪异常:"+LMSConstValue.softwareVersion;
 			
		JOptionPane.showMessageDialog(null, headStr+"\r\n"+getStackTrace(t), title, JOptionPane.ERROR_MESSAGE); 
	}

	public void LMS_ERROR_DIALOG(String title,String msg)
	{
 		if(title == null)
 			title = "检测仪异常:"+LMSConstValue.softwareVersion;
 			
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE); 
	}
	
	public void LMS_WARNING_DIALOG(String title,String msg)
	{
 		if(title == null)
 			title = "检测仪异常:"+LMSConstValue.softwareVersion;
 			
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE); 
	}
	
	public void LMS_CAR_OUT(String str)
	{
		car_out.debug(str);
	}
	public void LMS_DATA_LOG(int sensorID,String logString)
	{
		if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
			||LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.XZY_2)
			||LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.XZY_840)	
			||LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.XZY_1600)	
			||LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.ZM10)
			||LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.PS_16I))
		{
			lightCurtainLogger_0.debug(logString);
		}
		else
		{
			if(sensorID == 0)
				dataLogger_0.debug(logString);
			else if(sensorID == 1)
				dataLogger_1.debug(logString);
			else if(sensorID == 2)
				dataLogger_2.debug(logString);
		}
	}

	public void LMS_DEBUG_OUT_ERROR(String DebugString)
	{
		out_error.debug(DebugString);
	}
}
