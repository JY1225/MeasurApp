package layer.dataLayer;

import java.util.concurrent.BlockingQueue;

import SensorBase.LMSConstValue;

public class DataLayerDataParseLineLightCurtain extends DataLayerDataParseLine{
	public boolean bLightCurtainLightStatus[] = new boolean[LMSConstValue.LIGHT_CURTAIN_LIGHT_NUM];

	public void dataLayerLightCurtainParseLineQueuePut(
			BlockingQueue queue,
			int iScanCounter,long timeOfTrans,
			String timeOfReceived,long lTimeOfReceived,
			boolean bLightCurtainLightStatus[])
	{
		DataLayerDataParseLineLightCurtain dataLayerLightCurtainParseLine = new DataLayerDataParseLineLightCurtain();
		dataLayerLightCurtainParseLine.iScanCounter = iScanCounter;
		dataLayerLightCurtainParseLine.timeOfTrans = timeOfTrans;
		dataLayerLightCurtainParseLine.sTimeOfReceived = timeOfReceived;
		dataLayerLightCurtainParseLine.lTimeOfReceived_fitting = lTimeOfReceived;
		
		for(int i=0;i<LMSConstValue.iLPNum;i++)
		{
			dataLayerLightCurtainParseLine.bLightCurtainLightStatus[i] = bLightCurtainLightStatus[i];
		}
		
		try {
			queue.put(dataLayerLightCurtainParseLine);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
