package lmsTransfer.lmsTransferRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.HashMap;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import lmsTransfer.LMSTransferRunnable;
import lmsTransfer.lmsTransferType.LMSTransferTypeXMLProtocol;
import lmsTransfer.lmsTransferSocket.LMSTransferSocketServer;

public class LMSTransferXMLProtocolSocketServer extends LMSTransferRunnable {
	private final static String DEBUG_TAG="LMSTransferXMLProtocolSocketServer";
	
	public ServerSocket server = null;
	public LMSTransferSocketServerForXMLProtocol lmsTransferSocketServerForXMLProtocol;
    LMSTransferTypeXMLProtocolForSocketServer lmsTransferTypeXMLProtocolForSocketServer;

	@Override
	public void runnabbleRun() {
		// TODO Auto-generated method stub
		 try {
			 lmsTransferSocketServerForXMLProtocol.runnabbleRun();
		} catch (BindException e) {
			LMSLog.exceptionDialog("检测仪异常:FRSL端口打开失败,请检查是否打开了多个程序",e);
			
	        System.exit(0);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(e);
		} 
	}

	public LMSTransferXMLProtocolSocketServer(String ip)
	{
		lmsTransferSocketServerForXMLProtocol = new LMSTransferSocketServerForXMLProtocol(ip, LMSConstValue.LMS_AP_XML_PROTOCOL_PORT);
		lmsTransferTypeXMLProtocolForSocketServer = new LMSTransferTypeXMLProtocolForSocketServer();
		
		server = lmsTransferSocketServerForXMLProtocol.server;

        //==============================================================
		EventListener eventListener = new EventListener();
        LMSEventManager.addListener(eventListener);
	}
	
	public class LMSTransferSocketServerForXMLProtocol extends LMSTransferSocketServer
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
			eventExtraSocketReceive.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG,"port:"+LMSConstValue.LMS_AP_XML_PROTOCOL_PORT+"\r\n"+str);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_RECEIVE_MSG_INTENT,eventExtraSocketReceive);    					

			//======================================================================
		    try {
		    	lmsTransferTypeXMLProtocolForSocketServer.physic_receive(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LMSLog.exceptionDialog("检测仪异常", e);
			}// window
		}
		
		public LMSTransferSocketServerForXMLProtocol(String ip,int port)
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
	
	class LMSTransferTypeXMLProtocolForSocketServer extends LMSTransferTypeXMLProtocol
    {
		@Override
		public void RESULT_PHYSIC_SEND(String str) {
			LMSLog.d(DEBUG_TAG, "RESULT_PHYSIC_SEND="+str);
			
			// TODO Auto-generated method stub
			lmsTransferSocketServerForXMLProtocol.socketSend(str);			
		}    			
    }	
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			
			lmsTransferTypeXMLProtocolForSocketServer.eventToCommand(eventType,eventExtra);
		}
	}
}
