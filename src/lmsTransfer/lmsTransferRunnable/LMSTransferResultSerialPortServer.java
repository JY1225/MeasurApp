package lmsTransfer.lmsTransferRunnable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import lmsTransfer.LMSTransferRunnable;
import lmsTransfer.lmsTransferSerialPort.LMSTransferSerialPortServer;
import lmsTransfer.lmsTransferType.LMSTransferTypeResult;

public class LMSTransferResultSerialPortServer extends LMSTransferRunnable{
	private final static String DEBUG_TAG="LMSTransferResultSerialPortServer";

	LMSTransferSerialPortServerForResult lmsTransferSerialPortServerForResult;
	LMSTransferTypeResultForSerialPortServer lmsTransferTypeResultForSerialPortServer;

	public LMSTransferResultSerialPortServer(InputStream input,OutputStream output)
	{		
		lmsTransferSerialPortServerForResult = new LMSTransferSerialPortServerForResult(input,output);
		lmsTransferTypeResultForSerialPortServer = new LMSTransferTypeResultForSerialPortServer("");

        //==============================================================
		EventListener eventListener = new EventListener();
        LMSEventManager.addListener(eventListener);
	}
	
	@Override
	public void runnabbleRun() {
		// TODO Auto-generated method stub
		 lmsTransferSerialPortServerForResult.runnabbleRun();			
	}
	
	class LMSTransferSerialPortServerForResult extends LMSTransferSerialPortServer
	{
		public LMSTransferSerialPortServerForResult(InputStream input,OutputStream output) {
			super(input, output);
		}

		@Override
		public void PHYSIC_RECEIVE(String str) {
			HashMap<String, Comparable> eventExtraSocketReceive = new HashMap<String, Comparable>();
			eventExtraSocketReceive.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, str);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_RECEIVE_MSG_INTENT,eventExtraSocketReceive);    					
		
			//=================================================================
			lmsTransferTypeResultForSerialPortServer.physic_receive_server(str);			
		}		
	}

	class LMSTransferTypeResultForSerialPortServer extends LMSTransferTypeResult
    {
		public LMSTransferTypeResultForSerialPortServer(String ip) {
			super(ip);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void PHYSIC_SEND(String str) {
			// TODO Auto-generated method stub
			lmsTransferSerialPortServerForResult.serialPortSend(str);			

		}    	
    }
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			lmsTransferTypeResultForSerialPortServer.eventToCommand(eventType,eventExtra);
		}
	}
}
