package FileManager.exchangFileManager;

import java.io.File;

import CustomerProtocol.CustomerProtocol;
import CustomerProtocol.CustomerProtocol_FILE;
import CustomerProtocol.DetectionVehicle;
import FileManager.FileManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class ExchangeFileManagerRunnable {
	String DEBUG_TAG="ExchangeFileManagerRunnable";

	public static File pReadFile;
	public static File pWriteFile;

	int sleepTime;
	public ExchangeFileManagerRunnable(int _sleepTime)
	{				
		pReadFile = FileManager.checkFile(CustomerProtocol_FILE.readFilePath, CustomerProtocol_FILE.readFileName);
		pWriteFile = FileManager.checkFile(CustomerProtocol_FILE.writeFilePath, CustomerProtocol_FILE.writeFileName);
		
		//------------------------------------------------------------
		sleepTime = _sleepTime;
	}
	
	public Runnable thread()
    {
		return new Runnable(){    		
       		@Override
    		public void run() {
       			try
       			{
       				while(DetectionVehicle.bAutoRefresh && DetectionVehicle.bProtocolFile)
	       			{	                
//       					if(LMSConstValue.bNvramCarTypeLocalSetting.bValue == false)
//       					{
		                	if(CustomerProtocol.customerProtocol != null)
		    				{
		                		CustomerProtocol.customerProtocol.queryDetectionList(null);
		    					
		    					if(DetectionVehicle.detectionVehicleList.size() > 0)
		    					{				    						
		    						CustomerProtocol.notifyDetectionList();	
		    					}	
//		    				}
       					}
	            		
	       				Thread.sleep(sleepTime);
	       			}       			
				} catch (Exception e) {
		    		LMSLog.exceptionDialog(null,e);
				}
			};
	    };
    }
}
