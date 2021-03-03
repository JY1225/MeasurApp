package CustomerProtocol;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import AppBase.appBase.CarTypeAdapter;
import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import FileManager.exchangFileManager.ExchangeFileManagerRunnable;
import SensorBase.LMSLog;

public class CustomerProtocol_FILE_XML extends CustomerProtocol_FILE{
	String DEBUG_TAG="CustomerProtocol_FILE_XML";
	
	public CustomerProtocol_FILE_XML()
	{
		CustomerProtocol_FILE.readFilePath = "cartype";
		CustomerProtocol_FILE.readFileName = "cartype.txt";
	}
	
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void parseDetectionList(String xmlStr) 
	{
		try {
    		if(xmlStr != null && !xmlStr.equals(""))
    		{	
    			if(!CarTypeAdapter.oldCarTypeAdapter(xmlStr))
    			{
					DetectionVehicle detectionVehicle = new DetectionVehicle();
					
					CustomerProtocol_XML_MINE.XML_parse(xmlStr,detectionVehicle);
					
					DetectionVehicle.detectionVehicleList.add(detectionVehicle);
    			}
    		}
    	} catch (Exception e) {
       		LMSLog.exceptionDialog(null, e);
		}	
	}

	@Override
	public void parseDetectionList(ArrayList<String> lineList) {
		// TODO Auto-generated method stub
		
	}

	public static String carTypeStr;
	public static void updateCarType(String carTypeStr)
	{
		LMSLog.d("rrrrrrrrrrrrrr","!!!!!!!!!!!!!!!!!!!!!!!"+carTypeStr);
		new Thread(carTypeStr).start();
	}
	
	public Runnable Thread(String _str)
    {
		carTypeStr = _str;
		
		return new Runnable(){    		
       		@Override
    		public void run() {
       			FileWriter fileWriter;
       			boolean bWriteFinish = false;

       			while(bWriteFinish == false)
       			{	            
    				try {
        				fileWriter = new FileWriter(ExchangeFileManagerRunnable.pWriteFile);
        				
						fileWriter.write(carTypeStr);
	    				bWriteFinish = true;

    				} catch (IOException e) {
    					LMSLog.exception(e);
    				}
    				
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
    					LMSLog.exception(e);
					}
    				
					LMSLog.d(DEBUG_TAG,"write carType="+carTypeStr);
	       		}       			
			};
	    };
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
