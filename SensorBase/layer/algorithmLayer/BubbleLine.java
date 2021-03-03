package layer.algorithmLayer;

import SensorBase.LMSConstValue;

public class BubbleLine extends AlogrithmLine{      	    	
	public int length_CurveFitting;

	public boolean bBlackLine;
	
	public int height;
	
	public int xBegin;
	public int xEnd;
	public int yBegin;
	public int yEnd;
			
	public int beginIndex;
	public int endIndex;
	
	public int[] x = new int[LMSConstValue.LMS_POINT_NUM];
	public int[] y = new int[LMSConstValue.LMS_POINT_NUM];
	
	public boolean bAxle;	
	public int iQianYingNum;
}