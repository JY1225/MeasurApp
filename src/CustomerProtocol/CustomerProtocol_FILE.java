package CustomerProtocol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import FileManager.exchangFileManager.ExchangeFileManagerRunnable;
import SensorBase.LMSLog;
import SensorBase.LMSToken;

public abstract class CustomerProtocol_FILE extends CustomerProtocol{
	String DEBUG_TAG="CustomerProtocol_FILE";

	//解析待检队列数据:XML格式
	public abstract void parseDetectionList(String xmlStr);	
	//解析待检队列数据:行格式
	public abstract void parseDetectionList(ArrayList<String> lineList);	

	public ArrayList<String> lineList = new ArrayList<String>();  

	public static String readFilePath;
	public static String readFileName;
	public static String writeFilePath;
	public static String writeFileName;
	
   	LMSToken tokenFile = new LMSToken();
	
   	//查询待检队列
	@Override
	public void queryDetectionList(ContourDetectionTabPanelMain detectPanel) {
		if(ExchangeFileManagerRunnable.pReadFile != null)
		{
			synchronized (tokenFile) {
	        	FileReader fileReader;
				try {
					fileReader = new FileReader(ExchangeFileManagerRunnable.pReadFile);
		        	BufferedReader bufferedReader = new BufferedReader(fileReader);
	
		        	lineList.clear();
					CustomerProtocol.xmlStr = "";

					String lineStr;
					while((lineStr = bufferedReader.readLine()) != null)
		    		{
						DetectionVehicle.detectionVehicleList.clear();

		    			if(DetectionVehicle.bFileStrLine)
		    			{
		    				lineList.add(lineStr);
		    			}
		    			else if(DetectionVehicle.bFileStrXML);
		    			{
		    				CustomerProtocol.xmlStr += lineStr;
		    			}
		    		}
					bufferedReader.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
										
				if(DetectionVehicle.bFileStrLine)
				{
					parseDetectionList(lineList);
				}
				else if(DetectionVehicle.bFileStrXML);
				{
					parseDetectionList(CustomerProtocol.xmlStr);
				}
	    	}
		}
	}
	
	//解析待检队列数据:XML格式,无用
	public void parseDetectionList_WS(String xmlStr){};	
}
