package AppFrame.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;

import SensorBase.LMSConstValue;

public class JButtonSendFSRLCmd extends JButton{
	public JButtonSendFSRLCmd(String buttonName,final String cmdStr)
	{
		super(buttonName);
				
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, cmdStr);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
			}
		});
	}
}
