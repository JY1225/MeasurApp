package lmsEvent;

import java.util.EventListener;


public interface LMSEventListener extends EventListener {
	public void lmsTransferEvent(LMSEvent event);


}
