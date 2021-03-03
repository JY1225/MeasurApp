package lmsTransfer;

import java.io.IOException;

public abstract class LMSTransferRunnable {
	private final static String DEBUG_TAG="LMSTransferRunnable";

	public abstract void runnabbleRun() throws IOException;
	
	public Runnable thread = new Runnable()
    {
		@Override
		public void run() {    
			try {
				runnabbleRun();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    };
}
