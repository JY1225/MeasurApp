package SensorBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import SensorBase.LMSConstValue.enumDetectType;

public class LMSLog {
	private static LMSLogInterface lmsLogInterface;
	
	public LMSLog(){}
	
	public static void setCallFunc(LMSLogInterface debugInterface) {
		lmsLogInterface = debugInterface;
	}
	
	public static void d(String debugTag,String debugString) {
		if(LMSConstValue.isMyMachine())
		{
			lmsLogInterface.LMS_DEBUG_D(debugTag, debugString);
		}
	}

	public static void i(String debugTag,String debugString) {
		lmsLogInterface.LMS_DEBUG_I(debugTag, debugString);
	}
	
	public static void w(String debugTag,String debugString) {
		lmsLogInterface.LMS_DEBUG_W(debugTag, debugString);
	}
	
	public static void little(String debugTag,String debugString) {
		lmsLogInterface.LMS_LITTLE(debugTag, debugString);
	}

	public static void exception(int sensorID,Throwable t) {
		lmsLogInterface.LMS_EXCEPTION(sensorID,t);
	}

	public static void exception(Throwable t) {
		lmsLogInterface.LMS_EXCEPTION(t);
	}

	public static void exceptionDialog(String title,Throwable t) {
		lmsLogInterface.LMS_EXCEPTION_DIALOG(title,t);
	}

	public static void outOfMemoryDialog(Throwable t) {
		lmsLogInterface.LMS_OUT_OF_MEMORY_DIALOG(t);
	}

	public static void exceptionDialog(String title,Throwable t,String headStr) {
		lmsLogInterface.LMS_EXCEPTION_DIALOG(title, t, headStr);
	}
	
	public static void errorDialog(String title,String msg) {
		lmsLogInterface.LMS_ERROR_DIALOG(title,msg);
	}
	
	public static void warningDialog(String title,String msg) {
		lmsLogInterface.LMS_WARNING_DIALOG(title,msg);
	}

	public static void data(int sensorID,String debugString) {
		lmsLogInterface.LMS_DATA_LOG(sensorID,debugString);
	}

	public static void carOut(String str) {
		lmsLogInterface.LMS_CAR_OUT(str);
	}
	
	public static void outError(String debugString) {
		lmsLogInterface.LMS_DEBUG_OUT_ERROR(debugString);
	}	
}
