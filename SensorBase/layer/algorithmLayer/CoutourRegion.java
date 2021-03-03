package layer.algorithmLayer;

import java.util.ArrayList;

import SensorBase.LMSConstValue;

public class CoutourRegion {
	public int region;
	public ArrayList<Integer> index = new ArrayList<Integer>();  
	
	public Integer getSize()
	{
		return index.size();
	}

}
