package layer.algorithmLayer;

import SensorBase.LMSConstValue;

public class LightCurtainCarInLine extends AlogrithmLine{ 
	public boolean bLightCurtainLightStatus[] = new boolean[LMSConstValue.LIGHT_CURTAIN_LIGHT_NUM];
	public int[] y = new int[LMSConstValue.LIGHT_CURTAIN_LIGHT_NUM];

	public boolean bAxle;	   
	
	public int length_CurveFitting;
}
