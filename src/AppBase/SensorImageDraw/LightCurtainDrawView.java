package AppBase.SensorImageDraw;

import AppBase.ImplementPC.SensorDrawViewImplementPC;
import SensorBase.LMSConstValue;

public class LightCurtainDrawView extends SensorDrawViewImplementPC{
	String DEBUG_TAG = "LightCurtainDrawView";
	
	public void lightCurtainDrawPoint(boolean bLightCurtainLightStatus[])
	{
		int loopNum = 0;
		if(LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.XZY_2))
			loopNum = 2;
		else
			loopNum = LMSConstValue.iLPNum;
		
		for(int j=0;j<loopNum;j++)
		{
			if(bLightCurtainLightStatus[j] == true)
				paintSetColorRED();					
			else
				paintSetColorGREEN();
			drawOval(1, j*6, 4, 4);
		}

	}
}
