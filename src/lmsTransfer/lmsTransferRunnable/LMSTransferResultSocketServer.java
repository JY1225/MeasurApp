package lmsTransfer.lmsTransferRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import lmsTransfer.LMSTransferRunnable;
import lmsTransfer.lmsTransferSocket.LMSTransferSocketServer;
import lmsTransfer.lmsTransferType.LMSTransferTypeResult;

public class LMSTransferResultSocketServer extends LMSTransferRunnable{
	private final static String DEBUG_TAG="LMSTransferResultSocketServer";
	
	public ServerSocket server = null;
	public LMSTransferSocketServerForResult lmsTransferSocketServerForResult;
    LMSTransferTypeResultForSocketServer lmsTransferTypeResultForSocketServer;

	@Override
	public void runnabbleRun() {
		// TODO Auto-generated method stub
		 try {
			lmsTransferSocketServerForResult.runnabbleRun();
		} catch (BindException e) {
			LMSLog.exceptionDialog("检测仪异常:结果监控端口打开失败,请检查是否打开了多个程序",e);
			
	        System.exit(0);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(e);
		} 
	}

	public LMSTransferResultSocketServer(String ip)
	{
		lmsTransferSocketServerForResult = new LMSTransferSocketServerForResult(ip,LMSConstValue.LMS_AP_RESULT_PORT);
		lmsTransferTypeResultForSocketServer = new LMSTransferTypeResultForSocketServer(ip);
		
		server = lmsTransferSocketServerForResult.server;

        //==============================================================
		EventListener eventListener = new EventListener();
        LMSEventManager.addListener(eventListener);
	}
	
	public class LMSTransferSocketServerForResult extends LMSTransferSocketServer
	{
		@Override
    	public void serverSocketSetTimeOut(){}

		@Override
		public void clientConnected() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void PHYSIC_RECEIVE(String str) {
			LMSLog.d(DEBUG_TAG,"PHYSIC_RECEIVE="+str);

			HashMap<String, Comparable> eventExtraSocketReceive = new HashMap<String, Comparable>();
			eventExtraSocketReceive.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG,"port:"+LMSConstValue.LMS_AP_RESULT_PORT+"\r\n"+str);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_RECEIVE_MSG_INTENT,eventExtraSocketReceive);    					
		
			//=================================================================
			lmsTransferTypeResultForSocketServer.physic_receive_server(str);
		}
		
		public LMSTransferSocketServerForResult(String ip,int port)
		{
			super(ip,port);
		}

		@Override
		public boolean BUFFER_READ(BufferedReader bufferedReader) {
			// TODO Auto-generated method stub
			int count;
			char[] buffer = new char[1024];  ;
    		try {
				if((count = bufferedReader.read(buffer)) >= 0)
				{
					if(count == 0)
        			{
        				LMSLog.d(DEBUG_TAG,"why count== 0");
        			}
        			else
        			{
        				readString = new String(buffer,0,count);  
        			}
					
					return true;
				}
				else
					return false;
			} catch (IOException e) {
	    		LMSLog.exception(e);
			}
			return false;
		}
	}
	
	class LMSTransferTypeResultForSocketServer extends LMSTransferTypeResult
    {
		public LMSTransferTypeResultForSocketServer(String ip) {
			super(ip);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void PHYSIC_SEND(String str) {
			// TODO Auto-generated method stub
			lmsTransferSocketServerForResult.socketSend(str);			
		}    	  	
    }
		
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			lmsTransferTypeResultForSocketServer.eventToCommand(eventType,eventExtra);
		}
	}
}
