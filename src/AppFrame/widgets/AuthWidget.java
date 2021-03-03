package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import SensorBase.LMSConstValue;

public class AuthWidget {
    JLabel labelAuthString;
    JPasswordField textFieldAuth;
    
    public AuthWidget(
    	JPanel panel,
    	int gridX,int gridY,
    	String labelStr)
    {
        labelAuthString = new JLabel(labelStr);
    	GridBagConstraints gbc_labelAuthString = new GridBagConstraints();
    	gbc_labelAuthString.fill = GridBagConstraints.BOTH;
    	gbc_labelAuthString.insets = new Insets(0, 0, 0, 5);
    	gbc_labelAuthString.gridx = gridX;
    	gbc_labelAuthString.gridy = gridY;
    	panel.add(labelAuthString, gbc_labelAuthString);
    	gridX++;
    	
	    textFieldAuth = new JPasswordField();
	    GridBagConstraints gbc_textFieldAuth = new GridBagConstraints();
	    gbc_textFieldAuth.insets = new Insets(0, 0, 0, 5);
	    gbc_textFieldAuth.fill = GridBagConstraints.BOTH;
	    gbc_textFieldAuth.gridx = gridX;
	    gbc_textFieldAuth.gridy = gridY;
	    panel.add(textFieldAuth,gbc_textFieldAuth);
		textFieldAuth.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = textFieldAuth.getText().toString();

				if(!str.equals(LMSConstValue.sAuth))
				{
					LMSConstValue.sAuth = str;
						
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramAuth);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.sAuth);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    														
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
    }
}
