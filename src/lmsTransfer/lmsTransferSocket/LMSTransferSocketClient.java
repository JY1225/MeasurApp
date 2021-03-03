package lmsTransfer.lmsTransferSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsTransfer.LMSTransferRunnable;

public abstract class LMSTransferSocketClient {
	private final static String DEBUG_TAG="LMSTransferSocketClient";

	private Socket socket = null;
    private int port;
    private boolean bLineRead;

	public abstract void socketConnected();
	public abstract void PHYSIC_RECEIVE(String str);
	
	BufferedReader bufferedReader = null;  
    private InputStreamReader inputStreamReader;  
    protected OutputStream outputStream;  
    protected InputStream inputStream;  
	
	public LMSTransferSocketClient(int port,boolean bLineRead)
	{
		this.port = port;
		this.bLineRead = bLineRead;
	}

	public Runnable thread()
    {
		return new Runnable(){
			@Override
    		public void run() {       
				LMSLog.d(DEBUG_TAG,"socketRunnable thread run:"+Thread.currentThread().getId()+" port="+port);

		        //==============================================================
		        try  
		        {         
			        if(connectSocket())
			        {
			        	LMSLog.d(DEBUG_TAG,"socket connected:"+port);
			        	socketConnected();

		    			/*
		        		 * buffer开大点，避免线程有时阻塞，导致数据读取不及时，底层拥塞，通信中断
		        		 */
		        		 
			        	if(bLineRead == true)
			        	{
			        		String buffer = null;
				    		while ((buffer = bufferedReader.readLine()) != null)  
				            {  	
				    			PHYSIC_RECEIVE(buffer);
				            }
			        	}
			        	else
			        	{
			        		char[] buffer = new char[200*1024];  
			        		int count = 0;
			        		while ((count = inputStreamReader.read(buffer))>=0)    						    		
				            {  	
			        			if(count == 0)
			        			{
			        				LMSLog.d(DEBUG_TAG,"why count== 0");
			        			}
			        			else
			        			{
			        				PHYSIC_RECEIVE(new String(buffer));
			        			}
			        		}
			        	}
			        	
			    		LMSLog.d(DEBUG_TAG,"read finish");
			    		
			        	LMSLog.w(DEBUG_TAG,"sendSocketErrorMsg 1");

						sendSocketErrorMsg(port);
			        }
			        else
			        {
			        	LMSLog.d(DEBUG_TAG,"socket eeeee");
			        }
		        }  
		        catch (SocketException e)  
		        {
		    		LMSLog.exception(e);

					sendSocketErrorMsg(port);
		        }
		        catch (Exception e)  
		        {  
		    		LMSLog.exception(e);

					sendSocketErrorMsg(port);
		        }  
		        finally
		        {
		        	disconnectSocket();
		        }
		                                        
		        LMSLog.d(DEBUG_TAG,"socketRunnable thread end:"+Thread.currentThread().getId());
			}
		};
    }
    	
	public void socketSend(String str)
	{
//		LMSLog.d(DEBUG_TAG,"socketSend:"+str);
		
		try {
			if(outputStream != null)
			{
				outputStream.write((str).getBytes("utf-8"));
			}
		} catch (IOException e) {
    		LMSLog.exception(e);
		}	
	}
	
	private boolean connectSocket()
	{
		try {
			LMSLog.d(DEBUG_TAG,"connectSocket IP="+LMSConstValue.socketIP+" port="+port);

			socket = new Socket(LMSConstValue.socketIP,port);        	
			
			outputStream = socket.getOutputStream();  
			inputStream = socket.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"),200*1024);
			inputStreamReader = new InputStreamReader(inputStream);

			sendSocketOkMsg(port);
		} catch (SocketException e) {
			sendSocketErrorMsg(port);
			
    		LMSLog.exception(e);

			return false;
		} catch (IOException e) {
			sendSocketErrorMsg(port);

    		LMSLog.exception(e);

			return false;
		}	

		return true;
	}

	public void disconnectSocket()
	{
		LMSLog.d(DEBUG_TAG,"disconnectSocket");
		try {
    		if(socket != null&&!socket.isClosed())
    		{	
        		socket.shutdownInput();	//正常跳出read 阻塞
        		socket.shutdownOutput();	
        		socket.close();
    		}        		
        	if(outputStream != null)
        	{
        		outputStream.close();
        		outputStream = null;
        	}
        	if(bufferedReader != null)
        	{
        		bufferedReader.close();
        		bufferedReader = null;
        	}
		} catch (IOException e) {
    		LMSLog.exception(e);
		}			
	}	
	
	public void sendSocketOkMsg(int port) {
		LMSLog.d(DEBUG_TAG,"sendSocketOkMsg:"+port);

		HashMap eventExtra = new HashMap();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_CONNECT_STATE, LMSConstValue.enumDeviceStateType.TRANSFER_SOCKET_OK.ordinal());
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_CONNECT_PORT, port);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_CONNECT_STATE_INTENT,eventExtra);	    	
	}

	public void sendSocketErrorMsg(int port) {
		LMSLog.d(DEBUG_TAG,"sendSocketErrorMsg:"+port);

		HashMap eventExtra = new HashMap();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_CONNECT_STATE, LMSConstValue.enumDeviceStateType.TRANSFER_SOCKET_ERROR.ordinal());
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_CONNECT_PORT, port);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_CONNECT_STATE_INTENT,eventExtra);	    	
	}

}
