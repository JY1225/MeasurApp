package lmsTransfer.lmsTransferRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.HashMap;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import lmsTransfer.LMSTransferRunnable;
import lmsTransfer.lmsTransferSocket.LMSTransferSocketServer;
import lmsTransfer.lmsTransferType.LMSTransferTypeFSRLProtocol;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class LMSTransferFSRLProtocolSocketServer extends LMSTransferRunnable{
	private final static String DEBUG_TAG="LMSTransferFSRLProtocolSocketServer";
	
	public static LMSTransferFSRLProtocolSocketServer lmsTransferFSRLProtocolSocketServer;

	public ServerSocket server = null;
	public LMSTransferSocketServerForFSRLProtocol lmsTransferSocketServerForFSRLProtocol;
    LMSTransferTypeFSRLProtocolForSocketServer lmsTransferTypeFSRLProtocolForSocketServer;

	@Override
	public void runnabbleRun() {
		// TODO Auto-generated method stub
		 try {
			 lmsTransferSocketServerForFSRLProtocol.runnabbleRun();
		} catch (BindException e) {
			LMSLog.exceptionDialog("检测仪异常:FRSL端口打开失败,请检查是否打开了多个程序",e);
			
	        System.exit(0);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(e);
		} 
	}

	public LMSTransferFSRLProtocolSocketServer()
	{
		lmsTransferSocketServerForFSRLProtocol = new LMSTransferSocketServerForFSRLProtocol(LMSConstValue.LOCAL_IP,LMSConstValue.LMS_AP_FSRL_PROTOCOL_PORT);
		lmsTransferTypeFSRLProtocolForSocketServer = new LMSTransferTypeFSRLProtocolForSocketServer();
		
		server = lmsTransferSocketServerForFSRLProtocol.server;

        //==============================================================
		EventListener eventListener = new EventListener();
        LMSEventManager.addListener(eventListener);
	}
	
	public class LMSTransferSocketServerForFSRLProtocol extends LMSTransferSocketServer
	{
		@Override
    	public void serverSocketSetTimeOut(){}

		@Override
		public void clientConnected() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void PHYSIC_RECEIVE(String str) {
			HashMap<String, Comparable> eventExtraSocketReceive = new HashMap<String, Comparable>();
			eventExtraSocketReceive.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG,"port:"+LMSConstValue.LMS_AP_FSRL_PROTOCOL_PORT+"\r\n"+str);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_RECEIVE_MSG_INTENT,eventExtraSocketReceive);    					
		
			//=================================================================			
			lmsTransferTypeFSRLProtocolForSocketServer.physic_receive(str);
		}
		
		public LMSTransferSocketServerForFSRLProtocol(String ip,int port)
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
	
	class LMSTransferTypeFSRLProtocolForSocketServer extends LMSTransferTypeFSRLProtocol
    {
		@Override
		public void RESULT_PHYSIC_SEND(String str) {
			LMSLog.d(DEBUG_TAG, "RESULT_PHYSIC_SEND="+str);
			
			// TODO Auto-generated method stub
			lmsTransferSocketServerForFSRLProtocol.socketSend(str);			
		}    			
    }	
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			
			lmsTransferTypeFSRLProtocolForSocketServer.eventToCommand(eventType,eventExtra);
		}
	}
}
