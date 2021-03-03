package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import AppBase.appBase.CarTypeAdapter;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.enumType;

public class JLabelRadioButtonGroup {
	private String DEBUG_TAG="JLabelRadioButtonGroup";

	int _sensorID;
	int _index;

	String _labelStr;
	
	boolean _bSave;
	String _nvramStr;
	enumType _enumValue;
	
	JRadioButton radioButton[] = new JRadioButton[10];
	public ButtonGroup buttonGroup;
	int _gridX;

	public JLabelRadioButtonGroup(
		JPanel panel,
		int gridX, int gridY,
		int gridWidth, int gridHeight,
		String labelStr,
		final int sensorID,
		boolean bSave,
		String nvram, enumType enumValue
	)
	{		
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		
		_bSave = bSave;
		_nvramStr = nvram;
		_enumValue = enumValue;
		_gridX = gridX;
		
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
		
		//========================================================================
		if(labelStr != null)
		{
			JLabel label = new JLabel(labelStr);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.fill = GridBagConstraints.BOTH;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = gridX;
			gbc_label.gridy = gridY;
			panel.add(label, gbc_label);
			gridX++;
			gridY++;
		}

		buttonGroup = new ButtonGroup();// 创建单选按钮组
		ArrayList<String> enumString = _enumValue.getValueArray();
		int width = 0;
		for(int i=0;i<enumString.size();i++)
		{
			radioButton[i] = new JRadioButton(enumString.get(i));
			GridBagConstraints gbc_radioButton = new GridBagConstraints();
			gbc_radioButton.insets = new Insets(0, 0, 5, 5);
			gbc_radioButton.gridx = gridX;
			gbc_radioButton.gridy = gridY;
			panel.add(radioButton[i],gbc_radioButton);
			radioButton[i].addItemListener(new ItemListener() {
		        public void itemStateChanged(ItemEvent e) { 
		        	JRadioButton jrb=(JRadioButton)e.getSource();
		        	String str = jrb.getText();
		        	
					if(!str.equals(_enumValue.getValueFromKey(_enumValue.key)))
					{									    		
						_enumValue.key = _enumValue.getKeyFromValue(str);
						
			        	if(bAutoSet == false)
			        	{
							HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
							eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, _nvramStr);
							eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, _bSave);
							eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _enumValue.key);
							eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
							LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
						}	
			        	
						bAutoSet = false;
		        	}						
		         }           
			});
			buttonGroup.add(radioButton[i]);
			
			if(_enumValue.key.equals(_enumValue.getKeyFromValue(enumString.get(i))))
	    		buttonGroup.setSelected(radioButton[i].getModel(), true);

			//======================================================================================
			gridX++;
			width++;
			if(width >= gridWidth)
			{
				gridX = _gridX;
				width = 0;
				gridY++;
			}
		}
	}
	
	boolean bAutoSet = false;
	public void autoSetRadioButton(String str,enumType value)
	{
		ArrayList<String> enumString = value.getValueArray();
		for(int i=0;i<enumString.size();i++)
		{
			if(str.equals(value.getKeyFromValue(enumString.get(i))))
			{
	    		if(buttonGroup.getSelection() != radioButton[i].getModel())
	    		{
		    		bAutoSet = true;

		    		buttonGroup.setSelected(radioButton[i].getModel(), true);
	    		}

	    		break;
			}
		}
	}

	public void setEnabled(boolean bValue) {
        for (int i = 0; i < buttonGroup.getButtonCount(); i++) {
        	radioButton[i].setEnabled(bValue);
        }
    }

	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
				if(sensorID != _sensorID)
				{
					return;
				}
			}

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    	
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

	        }
		}
	}
}
