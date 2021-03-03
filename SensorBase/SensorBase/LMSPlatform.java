package SensorBase;

public class LMSPlatform {
	private static LMSPlatformInterface lmsPlatformInterface;

	public static void platformInit()
	{
		lmsPlatformInterface.PLATFORM_INIT();
	}
	
	public static void setCallFunc(LMSPlatformInterface inInterface) {
		lmsPlatformInterface = inInterface;
	}

	public static int hexStringToInt(String str) {
		return lmsPlatformInterface.HEX_STRING_TO_INT(str);
	}
	
	public static long hexStringToLong(String str) {
		return lmsPlatformInterface.HEX_STRING_TO_LONG(str);
	}
	
    public static void logEveryPoint(int i,int carX,int carY,int carR,int thisX,int thisY,int thisR)
    {
    	lmsPlatformInterface.LOG_EVERY_POINT(i,carX,carY,carR,thisX,thisY,thisR);
    }

    //===================================================================================	
	public static void savePorperty(String propertyType,String key,String value)
	{
		if(propertyType == null)
		{
			if(LMSConstValue.appType == LMSConstValue.AppType.RESULT_MONITOR)
			{
		    	lmsPlatformInterface.SAVE_PROPERTY(LMSConstValue.RESULT_MONITOR_PROPERTY,key,value);
			}
			else
			{
		    	lmsPlatformInterface.SAVE_PROPERTY(LMSConstValue.MAIN_PROPERTY,key,value);
			}
		}
		else
		{
			lmsPlatformInterface.SAVE_PROPERTY(propertyType,key,value);
		}
	}
	
    //===================================================================================
	public static String getStringPorperty(String key,String defaultValue)
	{
		if(LMSConstValue.appType == LMSConstValue.AppType.RESULT_MONITOR)
		{
		   	return lmsPlatformInterface.GET_STRING_PROPERTY(LMSConstValue.RESULT_MONITOR_PROPERTY,key,defaultValue);
		}
		else
		{
		   	return lmsPlatformInterface.GET_STRING_PROPERTY(LMSConstValue.MAIN_PROPERTY,key,defaultValue);
		}
	}

	public static String getStringPorperty(String propertyType,String key,String defaultValue)
	{
	   	return lmsPlatformInterface.GET_STRING_PROPERTY(propertyType,key,defaultValue);
	}
	
    //===================================================================================
	public static int getIntPorperty(String key,int defaultValue)
	{
	   	if(LMSConstValue.appType == LMSConstValue.AppType.RESULT_MONITOR)
		{
		   	return lmsPlatformInterface.GET_INT_PROPERTY(LMSConstValue.RESULT_MONITOR_PROPERTY,key,defaultValue);
		}
		else
		{
		   	return lmsPlatformInterface.GET_INT_PROPERTY(LMSConstValue.MAIN_PROPERTY,key,defaultValue);
		}
	}
	
	public static int getIntPorperty(String key,int defaultValue, int minValue, int maxValue)
	{
		if(LMSConstValue.appType == LMSConstValue.AppType.RESULT_MONITOR)
		{
		   	return lmsPlatformInterface.GET_INT_PROPERTY(LMSConstValue.RESULT_MONITOR_PROPERTY, key, defaultValue, minValue, maxValue);
		}
		else
		{
		   	return lmsPlatformInterface.GET_INT_PROPERTY(LMSConstValue.MAIN_PROPERTY, key, defaultValue, minValue, maxValue);
		}
	}

    //===================================================================================
	public static float getFloatPorperty(String key,float defaultValue)
	{
		if(LMSConstValue.appType == LMSConstValue.AppType.RESULT_MONITOR)
		{
		   	return lmsPlatformInterface.GET_FLOAT_PROPERTY(LMSConstValue.RESULT_MONITOR_PROPERTY,key,defaultValue);
		}
		else
		{
		   	return lmsPlatformInterface.GET_FLOAT_PROPERTY(LMSConstValue.MAIN_PROPERTY,key,defaultValue);
		}		
	}

	public static float getFloatPorperty(String propertyType,String key,float defaultValue)
	{
	   	return lmsPlatformInterface.GET_FLOAT_PROPERTY(propertyType,key,defaultValue);
	}

    //===================================================================================
	public static boolean getBooleanPorperty(String key,boolean defaultValue)
	{
		if(LMSConstValue.appType == LMSConstValue.AppType.RESULT_MONITOR)
		{
		   	return lmsPlatformInterface.GET_BOOLEAN_PROPERTY(LMSConstValue.RESULT_MONITOR_PROPERTY,key,defaultValue);
		}
		else 
		{
		   	return lmsPlatformInterface.GET_BOOLEAN_PROPERTY(LMSConstValue.MAIN_PROPERTY,key,defaultValue);
		}			
	}
	
	public static boolean getBooleanPorperty(String propertyType,String key,boolean defaultValue)
	{
	   	return lmsPlatformInterface.GET_BOOLEAN_PROPERTY(propertyType,key,defaultValue);
	}	
}
