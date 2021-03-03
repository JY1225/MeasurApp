package SensorBase;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;


public class enumType {
	private final static String DEBUG_TAG="enumType";
	
	public String key;
	String value;

	protected LinkedHashMap<String , String> map = new LinkedHashMap<String , String>();    

	public String getValueFromKey(String inKey)
	{
		Iterator it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String _key = (String)it.next();  
			if(_key.equals(inKey))
				return map.get(_key);
		}  
		
		return null;
	}

	public String getKeyFromValue(String inValue)
	{
		Iterator it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String _key = (String)it.next();  
			String _value = map.get(_key);
			
			if(_value.equals(inValue))
				return _key;
		}  
		
		return null;
	}

	public ArrayList<String> getValueArray()
	{
		ArrayList<String> array=new ArrayList<String>();
		
		Iterator it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String key = (String)it.next();  
			array.add(map.get(key));
		}  
		 
		return array;
	}

	public ArrayList<String> getKeyArray()
	{
		ArrayList<String> array=new ArrayList<String>();
		
		Iterator it = map.keySet().iterator();  
		while(it.hasNext()) {  
			String key = (String)it.next();  
			array.add(key);
		}  
		 
		return array;
	}
	
	public LinkedHashMap<String , String> getMap()
	{		 
		return map;
	}
}
