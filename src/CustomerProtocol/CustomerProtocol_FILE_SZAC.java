package CustomerProtocol;

import java.util.ArrayList;

import SensorBase.LMSLog;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;

public class CustomerProtocol_FILE_SZAC extends CustomerProtocol_FILE{
	String DEBUG_TAG="CustomerProtocol_FILE_SZAC";

	public CustomerProtocol_FILE_SZAC()
	{
		CustomerProtocol_FILE.readFilePath = "SZAC";
		CustomerProtocol_FILE.readFileName = "TestCmd.flag";
		
		CustomerProtocol_FILE.writeFilePath = "SZAC";
		CustomerProtocol_FILE.writeFileName = "TestResult.flag";
	}

	@Override
	public boolean updateDetectionResult(
			ContourDetectionTabPanelMain detectPanel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void parseDetectionList(ArrayList<String> lineList) {		
		DetectionVehicle detectionVehicle = new DetectionVehicle();
		
		for(int i=0;i<lineList.size();i++)
		{
			String str = lineList.get(i);
//			LMSLog.d(DEBUG_TAG, str);

			int index = str.indexOf("=");
			if(str.contains("³µÅÆºÅÂë"))
			{				
				detectionVehicle.sVehicleNum = str.substring(index+1);					
				
				LMSLog.d(DEBUG_TAG, "³µÅÆºÅÂë="+detectionVehicle.sVehicleNum);

			}
		}
		
		DetectionVehicle.detectionVehicleList.add(detectionVehicle);

	}

	@Override
	public void parseDetectionList(String xmlStr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beginDetection(ContourDetectionTabPanelMain detectPanel,boolean bValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void carInSignal(ContourDetectionTabPanelMain detectPanel,
			boolean bValue) {
		// TODO Auto-generated method stub
		
	}
}
