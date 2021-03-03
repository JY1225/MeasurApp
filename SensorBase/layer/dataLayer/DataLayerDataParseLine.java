package layer.dataLayer;

import java.util.LinkedList;

public class DataLayerDataParseLine {
	public int iPlaneNum; 

	public boolean bTimeValid;

	public int iScanCounter; 
	protected long timeOfTrans; 

	public String sTimeOfReceived;
	protected long lTimeOfReceived;
	public long lTimeOfReceived_fitting;

	public boolean dataLayerDataParseLineAdd(
		LinkedList<DataLayerDataParseLine> timeList,
		DataLayerDataParseLine dataLayerDataParseLineIn
	)
	{
		DataLayerDataParseLine dataLayerDataParseLine = new DataLayerDataParseLine();
		dataLayerDataParseLine.iPlaneNum = dataLayerDataParseLineIn.iPlaneNum;
		dataLayerDataParseLine.iScanCounter = dataLayerDataParseLineIn.iScanCounter;
		dataLayerDataParseLine.timeOfTrans = dataLayerDataParseLineIn.timeOfTrans;
		dataLayerDataParseLine.sTimeOfReceived = dataLayerDataParseLineIn.sTimeOfReceived;
		dataLayerDataParseLine.lTimeOfReceived = dataLayerDataParseLineIn.lTimeOfReceived;
		
		return timeList.add(dataLayerDataParseLine);
	}
}
