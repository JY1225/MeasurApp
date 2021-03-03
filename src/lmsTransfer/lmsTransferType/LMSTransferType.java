package lmsTransfer.lmsTransferType;

import java.util.HashMap;

public abstract class LMSTransferType {
	public abstract void eventToCommand(String event,HashMap eventExtra);
}
