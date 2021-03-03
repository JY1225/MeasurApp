package lmsTransfer.lmsTransferSerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import SensorBase.LMSLog;

import lmsTransfer.LMSTransferRunnable;
import lmsTransfer.lmsTransferType.LMSTransferTypeResult;

public abstract class LMSTransferSerialPortServer extends LMSTransferRunnable{
	final static String DEBUG_TAG="LMSTransferSerialPortServer";

	InputStream inputStream;  
	OutputStream outputStream;  

	public abstract void PHYSIC_RECEIVE(String str);

	public LMSTransferSerialPortServer(InputStream input,OutputStream output)
	{
		this.inputStream = input;
		this.outputStream = output;
	}
	
	public void serialPortSend(String str)
	{
		try {
			if(outputStream != null)
				outputStream.write(str.getBytes());
		} catch (IOException e) {
    		LMSLog.exception(e);
		}		
	}
	
	public void runnabbleRun(){
		  
        LMSLog.d(DEBUG_TAG,"thread run:"+Thread.currentThread().getId());

        byte[] buffer = new byte[200];  
        int size;  

        LMSLog.d(DEBUG_TAG,"interrupt 000");

        if (outputStream == null) 
        	return;  
        
        try {
        	String content = null;
    		  
        	while((size = inputStream.read(buffer)) >= 0)
        	{
        		if(size == 0)
        		{
        			LMSLog.d(DEBUG_TAG,"why count== 0");
        		}
        		else
        		{
					if(buffer[0] == '\02')
					{
						content = new String(buffer,0,size);  
					}
					else
					{
						content += new String(buffer,0,size);    
						LMSLog.d(DEBUG_TAG,"make one frame");				    								    				
					}
					
					LMSLog.d(DEBUG_TAG,"serail="+content);				    								    				
					
					if(buffer[size-1] != '\03')
					LMSLog.d(DEBUG_TAG,"not one frame");				    								    				
					else
						PHYSIC_RECEIVE(content);
        		}
			  }
			  LMSLog.d(DEBUG_TAG,"interrupt 111");
		} catch (IOException e) {
    		LMSLog.exception(e);
		}  
        
         LMSLog.d(DEBUG_TAG,"thread end:"+Thread.currentThread().getId());
	}
}
