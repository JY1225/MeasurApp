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
import lmsTransfer.lmsTransferSocket.LMSTransferSocketServer;
import lmsTransfer.lmsTransferType.LMSTransferTypeUserCmd;

public class LMSTransferUserCmdSocketServer extends LMSTransferRunnable{
	private final static String DEBUG_TAG="LMSTransferUserCmdSocketServer";
	
	public ServerSocket server = null;
	public LMSTransferSocketServerForUserCmd lmsTransferSocketServerForUserCmd;
    LMSTransferTypeUserCmdForSocketServer lmsTransferTypeUserCmdForSocketServer;

	@Override
	public void runnabbleRun() {
		// TODO Auto-generated method stub
		 try {
			lmsTransferSocketServerForUserCmd.runnabbleRun();
		} catch (BindException e) {
			LMSLog.exceptionDialog("检测仪异常:结果监控端口打开失败,请检查是否打开了多个程序",e);
			
	        System.exit(0);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(e);
		} 
	}

	public LMSTransferUserCmdSocketServer(String ip)
	{
		lmsTransferSocketServerForUserCmd = new LMSTransferSocketServerForUserCmd(ip, LMSConstValue.LMS_AP_USER_PORT);
		lmsTransferTypeUserCmdForSocketServer = new LMSTransferTypeUserCmdForSocketServer();
		
		server = lmsTransferSocketServerForUserCmd.server;

        //==============================================================
		EventListener eventListener = new EventListener();
        LMSEventManager.addListener(eventListener);
	}
	
	public class LMSTransferSocketServerForUserCmd extends LMSTransferSocketServer
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
			eventExtraSocketReceive.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG,"port:"+LMSConstValue.LMS_AP_USER_PORT+"\r\n"+str);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_RECEIVE_MSG_INTENT,eventExtraSocketReceive);    					
			
			//=====================================================================
			lmsTransferTypeUserCmdForSocketServer.physic_receive_server(str);
		}
		
		public LMSTransferSocketServerForUserCmd(String ip,int port)
		{
			super(ip,port);
		}

		@Override
		public boolean BUFFER_READ(BufferedReader bufferedReader) {
			// TODO Auto-generated method stub
			int count;
			char[] buffer = new char[1024*100];  ;
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
	
	class LMSTransferTypeUserCmdForSocketServer extends LMSTransferTypeUserCmd
    {
		@Override
		public void PHYSIC_SEND(String str) {
			LMSLog.d(DEBUG_TAG, "PHYSIC_SEND="+str);
			
			// TODO Auto-generated method stub
			lmsTransferSocketServerForUserCmd.socketSend(str);			
		}    			
    }
		
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			lmsTransferTypeUserCmdForSocketServer.eventToCommand(eventType,eventExtra);
		}
	}
}
