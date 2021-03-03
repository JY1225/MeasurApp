package lmsEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import SensorBase.LMSLog;
import SensorBase.LMSToken;

public class LMSEventManager {
	private final static String DEBUG_TAG="LMSTransferEventManager";

	private static Collection listeners;
	private static LMSToken tokenEventManager = new LMSToken();

    /**
     * 添加事件
     */
    public static void addListener(LMSEventListener listener) {
//		LMSLog.d(DEBUG_TAG,"addListener:"+listener);

		synchronized(tokenEventManager)
    	{
			if (listeners == null) {
	            listeners = new HashSet();
	        }
	        listeners.add(listener);
    	}
    }

    /**
     * 移除事件
     */
    public static void removeListener(LMSEventListener listener) {
//    	LMSLog.d(DEBUG_TAG,"removeListener:"+listener);

		synchronized(tokenEventManager)
    	{
	        if (listeners == null)
	            return;
	    	listeners.remove(listener);
    	}
    }
        
    /**
     * 通知所有的Listener
     */
    public static void notifyListeners(LMSEvent event) {
        try  
        {
    		synchronized(tokenEventManager)
        	{
		    	if(listeners != null)
		    	{	
		    		Collection listenersTmp = new HashSet();;
			        Iterator iter = listeners.iterator();
			        while (iter.hasNext())
			        {
			        	listenersTmp.add(iter.next());
			        }
			        
			        Iterator iterTmp = listenersTmp.iterator();
			        while (iterTmp.hasNext()) 
			        {		    		
			        	LMSEventListener listener = (LMSEventListener) iterTmp.next();	
			            listener.lmsTransferEvent(event);
			        }
		    	}
		    	else
		    	{
		    		LMSLog.d(DEBUG_TAG,"event:"+event.getEventType()+" no listener");
		    	}
        	}
        }
        catch (Exception e)  
        {  
    		LMSLog.exceptionDialog(null, e);
        }  
    }
    
	public void sendEvent(String eventType) {
		LMSEvent event = new LMSEvent(this, eventType);
		LMSEventManager.notifyListeners(event);				
	}

	public void sendEvent(String eventType,HashMap eventExtra) {
		LMSEvent event = new LMSEvent(this, eventType);
		event.putEventExtra(eventExtra);
		LMSEventManager.notifyListeners(event);				
	}
}
