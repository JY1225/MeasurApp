package http.WebService;

import http.Jason.JdHttpJason;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class WebService {
	private final static String DEBUG_TAG="WebService";

	public static ArrayBlockingQueue webServiceQueue = new ArrayBlockingQueue(10); 
	
	public WebService()
	{
       //=============================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	}
	
	public Runnable thread()
    {
		return new Runnable(){
    		@Override
    		public void run() {                  
                LMSLog.d(DEBUG_TAG,"thread run:"+Thread.currentThread().getId());

                //==============================================================
                try{
	    			while(true)
	    			{	
	    				WebServiceLine webServiceLine = (WebServiceLine) webServiceQueue.take();
	    				
		        		HttpPost.setXmlParameter(webServiceLine.xmlStr);
		        		
		        		new HttpPost().post(webServiceLine.url);				
	    			}
                } catch (Exception e) {
    	       		LMSLog.exceptionDialog(null, e);
    			}
       	    }    
		};
    }
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
			}

	        if(eventType != null && (eventType.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT))) 
	        {	    	
				int carState = 0;

				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
					carState = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE); 

	        }
	    }
	}
}
