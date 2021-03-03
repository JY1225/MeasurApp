package layer.dataLayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import SensorBase.LMSConstValue;

public class DataLayerDataParseLineRadar extends DataLayerDataParseLine{
	
    public int distanceX[] = new int[LMSConstValue.LMS_POINT_NUM]; 
    public int distanceY[] = new int[LMSConstValue.LMS_POINT_NUM]; 
    public int distanceR[] = new int[LMSConstValue.LMS_POINT_NUM]; 

	public void dataLayerDataParseLineRadarQueuePut(
			BlockingQueue queue,
			int planeNum,
			int iScanCounter,long timeOfTrans,String timeOfReceived,long lTimeOfReceived_fitting,
			int[] distanceX,int[] distanceY,int[] distanceR)
	{
		DataLayerDataParseLineRadar dataLayerDataParseLineRadar = new DataLayerDataParseLineRadar();
		
		dataLayerDataParseLineRadar.iPlaneNum = iPlaneNum;
		dataLayerDataParseLineRadar.iScanCounter = iScanCounter;
		dataLayerDataParseLineRadar.timeOfTrans = timeOfTrans;
		dataLayerDataParseLineRadar.sTimeOfReceived = timeOfReceived;
		dataLayerDataParseLineRadar.lTimeOfReceived_fitting = lTimeOfReceived_fitting;
		
		for(int i=0;i<LMSConstValue.LMS_POINT_NUM;i++)
		{
			dataLayerDataParseLineRadar.distanceX[i] = distanceX[i];
			dataLayerDataParseLineRadar.distanceY[i] = distanceY[i];
			dataLayerDataParseLineRadar.distanceR[i] = distanceR[i];
		}
		
//			System.arraycopy(distanceX,0,lmsDataParseLine.distanceX,0,lmsDataParseLine.distanceX.length);
//			System.arraycopy(distanceY,0,lmsDataParseLine.distanceY,0,lmsDataParseLine.distanceX.length);
//			System.arraycopy(distanceR,0,lmsDataParseLine.distanceR,0,lmsDataParseLine.distanceX.length);
		
		try {
			queue.put(dataLayerDataParseLineRadar);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
