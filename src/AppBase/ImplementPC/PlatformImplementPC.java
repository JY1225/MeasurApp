package AppBase.ImplementPC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import AppBase.appBase.AppConst;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatformInterface;
import SensorBase.LMSToken;

public class PlatformImplementPC implements LMSPlatformInterface{
	private final static String DEBUG_TAG="PlatformImplementPC";

	public PlatformImplementPC()
	{
		LMSConstValue.setMyMachine();
 		
		//===========================================================================
		File file;
 		file = new File("image");  //�ж��ļ����Ƿ����,����������򴴽��ļ���  
 		if (!file.exists()) 
 		{   
 			file.mkdir();
 		}
 		
 		file = new File("image//tmp");  //�ж��ļ����Ƿ����,����������򴴽��ļ���  
 		if (!file.exists()) 
 		{   
 			file.mkdir();
 		}
 		else
 		{
 			deleteDir(file);
 			file.mkdir();
 		} 		 		
		file = new File("image//tempPics");
		if (!file.exists())
		{
			file.mkdir();
		}
	}
	 /**     
	  * * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�    
	  * * @param dir ��Ҫɾ�����ļ�Ŀ¼     
	  * * @return boolean Returns "true" if all deletions were successful.     
	  * *                 If a deletion fails, the method stops attempting to     
	  * *                 delete and returns "false".     
	  * */    
	private static boolean deleteDir(File dir) {        
		if (dir.isDirectory()) {            
			String[] children = dir.list();			
			//�ݹ�ɾ��Ŀ¼�е���Ŀ¼��            
			for (int i=0; i<children.length; i++) {                
				boolean success = deleteDir(new File(dir, children[i]));                
				if (!success) {                    
					return false;                
				}            
			}        
		}        
		
		// Ŀ¼��ʱΪ�գ�����ɾ��        
		return dir.delete();    
	}

	public void PLATFORM_INIT()
	{

	}

	@Override
	public int HEX_STRING_TO_INT(String str) {
		try {
			BigInteger b = new BigInteger(str,16);
			return b.intValue();		}
		catch (NumberFormatException e) {
			LMSLog.exception(-1,e);
		}

		return 0;
	}

	@Override
	public long HEX_STRING_TO_LONG(String str) {
		return Long.valueOf(str,16);
	}
	
	@Override
	public void LOG_EVERY_POINT(int i, int carX, int carY, int carR, int thisX,
			int thisY, int thisR) {
	     LMSLog.d(DEBUG_TAG, "i="+i+" carX="+carX+" carY="+carY+" carR="+carR+" thisX="+thisX+" thisY="+thisY+" thisR="+thisR);

		// TODO Auto-generated method stub
		
	}

	static LMSToken propertyToken = new LMSToken();
	@Override
	public void SAVE_PROPERTY(String propertyType,String key,String value)
	{
		synchronized(propertyToken) 
		{
			File pFile = null;
			String fileName = null;
			
			LMSLog.d(DEBUG_TAG, "SAVE_PROPERTY key="+key+" value="+value);
	
			if(propertyType.equals(LMSConstValue.RESULT_MONITOR_PROPERTY))
			{
				fileName = AppConst.ResultMonitorProperty;
			}
			else if(propertyType.equals(LMSConstValue.MAIN_PROPERTY))
			{
				fileName = AppConst.MainDetectProperty;
			}
			else if(propertyType.equals(LMSConstValue.USER_PROPERTY))
			{
				fileName = AppConst.UserConfigProperty;
			}
			pFile = new File(fileName);    // properties�ļ�����e���£�windows��
	
			FileInputStream pInStream=null;
			
			try {
				pInStream = new FileInputStream(pFile);
			} catch (FileNotFoundException e) {
	    		LMSLog.exception(e);
			}
			
			Properties props = new Properties();
	        try {
	        	props.load(pInStream );       //Properties ���������ɣ������ļ��е�����
	        } catch (IOException e) {
	    		LMSLog.exception(e);
	        }
	      
	        props.setProperty(key, value);
	        try {
				props.store(new FileOutputStream(fileName), null);
			} catch (FileNotFoundException e) {
	    		LMSLog.exception(e);
			} catch (IOException e) {
	    		LMSLog.exception(e);
			}
	        
	        if(pInStream != null)
	        {
				try {
					pInStream.close();
				} catch (IOException e) {
		    		LMSLog.exception(e);
				}
	        }
		}
	}
	
	synchronized private String getProperty(String propertyType,String key,String defaultValue)
	{
		synchronized(propertyToken) 
		{
			String strValue;
			File pFile = null;
			
			if(propertyType.equals(LMSConstValue.RESULT_MONITOR_PROPERTY))
			{
				pFile = new File(AppConst.ResultMonitorProperty);    // properties�ļ�����e���£�windows��				
				
				try {		       
					if(!pFile.exists())
					     LMSLog.d(DEBUG_TAG, AppConst.ResultMonitorProperty+" not exist!");
					
					if(pFile.getParent()!=null && !new File(pFile.getParent()).exists()){
					    new File(pFile.getParent()).mkdirs();
					}
					pFile.createNewFile();
				} catch (Exception e) {
		       		LMSLog.exceptionDialog(null, e);
				}
			}
			else if(propertyType.equals(LMSConstValue.MAIN_PROPERTY))
			{
				pFile = new File(AppConst.MainDetectProperty);    // properties�ļ�����e���£�windows��			

				if(!pFile.exists())
				{
				     LMSLog.d(DEBUG_TAG, AppConst.MainDetectProperty+" not exist!");
				}
			}
			else if(propertyType.equals(LMSConstValue.USER_PROPERTY))
			{
				pFile = new File(AppConst.UserConfigProperty);    // properties�ļ�����e���£�windows��			
								
				try {		       
					if(!pFile.exists())
					     LMSLog.d(DEBUG_TAG, AppConst.UserConfigProperty+" not exist!");
					
					if(pFile.getParent()!=null && !new File(pFile.getParent()).exists()){
					    new File(pFile.getParent()).mkdirs();
					}
					pFile.createNewFile();
				} catch (Exception e) {
		       		LMSLog.exceptionDialog(null, e);
				}
			}
			
	    	FileInputStream pInStream=null;
	        
	        try {
	        	pInStream = new FileInputStream(pFile);
	        } catch (Exception e) {
	       		LMSLog.exceptionDialog(null, e);
	
	        	return defaultValue;
	        }
	
	        Properties props = new Properties();
	        try {
	        	props.load(pInStream );       //Properties ���������ɣ������ļ��е�����
	        	pInStream.close();
	        } catch (IOException e) {
	    		LMSLog.exception(e);
	
	        	return defaultValue;
	        }
	 
	        strValue = props.getProperty(key);
	
//	        LMSLog.d(DEBUG_TAG, "key="+key);
//	        LMSLog.d(DEBUG_TAG, "value="+strValue);
	 
	        if(pInStream != null)
	        {
		        try {
					pInStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        if(strValue == null)
	           	return defaultValue;
	        else
	        	return strValue;        
		}
	}
	
	@Override
	public int GET_INT_PROPERTY(String propertyType, String key,int defaultValue, int minValue, int maxValue) 
	{
		int value = defaultValue;

		String strValue = getProperty(propertyType,key,null);
        
        if(strValue != null)
        {
        	try{
        		value = Integer.valueOf(strValue);
        		if(value < minValue && minValue != LMSConstValue.INVALID_X)
        		{
        			value = minValue;
        		}
        		if(value > maxValue && maxValue != LMSConstValue.INVALID_X)
        		{
        			value = maxValue;
        		}
        	}
    		catch (NumberFormatException e) {
    			LMSLog.exception(-1,e);
    		}
        }

    	return value;     
	}

	@Override
	public int GET_INT_PROPERTY(String propertyType,String key,int defaultValue)
	{
		return GET_INT_PROPERTY(propertyType, key, defaultValue, LMSConstValue.INVALID_X, LMSConstValue.INVALID_X);
	}
	
	@Override
	public float GET_FLOAT_PROPERTY(String propertyType, String key, float defaultValue, float minValue, float maxValue)
	{
		float value = defaultValue;

		String strValue = getProperty(propertyType,key,null);
        
        if(strValue != null)
        {
        	try{
        		value = Float.valueOf(strValue);
        		if(value < minValue && minValue != LMSConstValue.INVALID_X)
        		{
        			value = minValue;
        		}
        		if(value > maxValue && maxValue != LMSConstValue.INVALID_X)
        		{
        			value = maxValue;
        		}
        	}
    		catch (NumberFormatException e) {
    			LMSLog.exception(-1,e);
    		}	
        }
        
    	return value;     
	}
	
	@Override
	public float GET_FLOAT_PROPERTY(String propertyType,String key,float defaultValue)
	{
		return GET_FLOAT_PROPERTY(propertyType, key, defaultValue, LMSConstValue.INVALID_X, LMSConstValue.INVALID_X);
	}
	
	@Override
	public boolean GET_BOOLEAN_PROPERTY(String propertyType,String key,boolean defaultValue)
	{
		boolean value;

		String strValue = getProperty(propertyType,key,null);
		
        if(strValue == null)
           	return defaultValue;
        else
        	return Boolean.valueOf(strValue);
   	}

	@Override
	public String GET_STRING_PROPERTY(String propertyType,String key, String defaultValue) {
		String strValue = getProperty(propertyType,key,null);
		 
       if(strValue == null)
           	return defaultValue;
        else
        	return strValue;        
	}
}
