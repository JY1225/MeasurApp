package SensorBase;

public interface LMSPlatformInterface {
	public void PLATFORM_INIT();

    public int HEX_STRING_TO_INT(String str); 
    public long HEX_STRING_TO_LONG(String str); 
	public void LOG_EVERY_POINT(int i,int carX,int carY,int carR,int thisX,int thisY,int thisR); 

	public void SAVE_PROPERTY(String propertyType,String key,String value); 
	public String GET_STRING_PROPERTY(String propertyType,String key,String defaultValue); 
	public int GET_INT_PROPERTY(String propertyType,String key,int defaultValue); 
	public int GET_INT_PROPERTY(String propertyType,String key,int defaultValue,int minValue, int maxValue); 
	public float GET_FLOAT_PROPERTY(String propertyType, String key, float defaultValue); 
	public float GET_FLOAT_PROPERTY(String propertyType, String key, float defaultValue, float minValue, float maxValue); 
	public boolean GET_BOOLEAN_PROPERTY(String propertyType,String key,boolean defaultValue); 
}
