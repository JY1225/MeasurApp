package lmsEvent;

import java.util.EventObject;
import java.util.HashMap;

import SensorBase.LMSLog;


public class LMSEvent extends EventObject{
	private final static String DEBUG_TAG="LMSTransferEvent";

	private static final long serialVersionUID = 1L;
	
	private String eventType = "";
	private HashMap eventExtra = new HashMap();

	public LMSEvent(Object source,String eventType) {
		super(source);
		this.eventType = eventType;
	}

	public String getEventType() {
        return this.eventType;
    }
	
	public void putEventExtra(HashMap eventExtra) {
		this.eventExtra = eventExtra;
    }
	
	public HashMap getEventExtra() {
		return eventExtra;
    }

	public int getEventIntExtra(String extraType)
	{
		return Integer.valueOf(eventExtra.get(extraType).toString());
	}

	public boolean getEventBooleanExtra(String extraType)
	{
		return Boolean.valueOf(eventExtra.get(extraType).toString());
	}

	public String getEventStringExtra(String extraType)
	{
		return (String) eventExtra.get(extraType);
	}
}
