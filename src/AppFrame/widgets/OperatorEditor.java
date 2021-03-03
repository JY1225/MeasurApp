package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SensorBase.LMSConstValue;
import SensorBase.LMSPlatform;

public class OperatorEditor {
	public Operator operator = new Operator();
	
	private JTextField userNameTextField;
	private JTextField userIDTextField;
	
    //=========================================================================	
	public OperatorEditor(int i,
		JPanel panel,
		int gridX,int gridY)
	{
		operator.index = i;
		
		operator.sOperatorName = LMSPlatform.getStringPorperty(LMSConstValue.USER_PROPERTY,Operator.nvramOperatorName+i, "");	
		operator.sOperatorID = LMSPlatform.getStringPorperty(LMSConstValue.USER_PROPERTY,Operator.nvramOperatorID+i, "");	
		
	    //=========================================================================	
		JLabel userSerialLabel = new JLabel("²Ù×÷Ô±"+(i+1)); 
		GridBagConstraints gbc_userSerialLabel = new GridBagConstraints();
		gbc_userSerialLabel.fill = GridBagConstraints.BOTH;
		gbc_userSerialLabel.gridwidth = 1;
		gbc_userSerialLabel.insets = new Insets(0, 0, 5, 5);
		gbc_userSerialLabel.gridx = gridX;
		gbc_userSerialLabel.gridy = gridY;
		panel.add(userSerialLabel,gbc_userSerialLabel);		
		gridX++;
		
		userNameTextField = new JTextField(operator.sOperatorName); 
		GridBagConstraints gbc_userNameTextField = new GridBagConstraints();
		gbc_userNameTextField.fill = GridBagConstraints.BOTH;
		gbc_userNameTextField.gridwidth = 1;
		gbc_userNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userNameTextField.gridx = gridX;
		gbc_userNameTextField.gridy = gridY;
		panel.add(userNameTextField,gbc_userNameTextField);	
		userNameTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = userNameTextField.getText().toString();
				
				if(!str.equals(operator.sOperatorName))
				{
					operator.sOperatorName = str;
					
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, Operator.nvramOperatorName);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, operator.sOperatorName);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_PROPERTY, LMSConstValue.USER_PROPERTY);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, operator.index);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra); 
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		gridX++;
		
		userIDTextField = new JTextField(operator.sOperatorID); 
		GridBagConstraints gbc_userIDTextField = new GridBagConstraints();
		gbc_userIDTextField.fill = GridBagConstraints.BOTH;
		gbc_userIDTextField.gridwidth = 1;
		gbc_userIDTextField.insets = new Insets(0, 0, 5, 5);
		gbc_userIDTextField.gridx = gridX;
		gbc_userIDTextField.gridy = gridY;
		panel.add(userIDTextField,gbc_userIDTextField);	
		userIDTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = userIDTextField.getText().toString();
				
				if(!str.equals(operator.sOperatorID))
				{
					operator.sOperatorID = str;
					
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, Operator.nvramOperatorID);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, operator.sOperatorID);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_PROPERTY, LMSConstValue.USER_PROPERTY);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, operator.index);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra); 
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		gridX++;
	}

}
