package AppFrame.widgets;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import SensorBase.LMSConstValue;
import SensorBase.enumType;

public class JLabelComboBox{	
	private String DEBUG_TAG="JLabelComboBox";

	int _sensorID;
	int _index;

	String _labelStr;
	
	String _nvram;
	enumType _enumValue;
	
	public JComboBox comboBox;
	
	public JLabelComboBox(
		JPanel panel,
		int gridX,int gridY,
		int comboBoxWidth,
		String labelStr,
		final int sensorID,
		String nvram, enumType enumValue
	)
	{    	
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		_nvram = nvram;
		_enumValue = enumValue;
		_labelStr = labelStr;
		
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==============================================================================
		if(labelStr != null)
		{
			JLabel label = new JLabel(labelStr);
	    	GridBagConstraints gbc_label = new GridBagConstraints();
	    	gbc_label.fill = GridBagConstraints.BOTH;
	    	gbc_label.insets = new Insets(0, 0, 0, 5);
	    	gbc_label.gridx = gridX;
	    	gbc_label.gridy = gridY;
	    	panel.add(label, gbc_label);
	    	gridX++;
		}
    	
    	comboBox = new JComboBox();
    	GridBagConstraints gbc_comboBox = new GridBagConstraints();
    	gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
    	gbc_comboBox.insets = new Insets(0, 0, 0, 5);
    	gbc_comboBox.gridx = gridX;
    	gbc_comboBox.gridy = gridY;
    	gbc_comboBox.gridwidth = comboBoxWidth;
    	panel.add(comboBox, gbc_comboBox);
    	gridX++;
    	
    	//=================================================================================================
    	int selectedItemIndex = 0;
    	ArrayList<String> enumString = _enumValue.getValueArray();
    	for(int i=0;i<enumString.size();i++)
    	{
    		String str= enumString.get(i);
    		comboBox.addItem(str);
			if(_enumValue.key.equals(_enumValue.getKeyFromValue(str)))
			{
				selectedItemIndex = i;
			}
    	}
    	comboBox.setSelectedIndex(selectedItemIndex);
    	
    	//=================================================================================================
    	comboBox.addItemListener(new ItemListener() {  
            @Override  
            public void itemStateChanged(ItemEvent e) {  
                // TODO Auto-generated method stub  
                String str = e.getItem().toString();  
                int stateChange = e.getStateChange();
                
                if (stateChange == ItemEvent.SELECTED) {  
    				if(!str.equals(_enumValue.getValueFromKey(_enumValue.key)))
    				{
    					_enumValue.key = _enumValue.getKeyFromValue(str);

    					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
    					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, _nvram);
    					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _enumValue.key);
    					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
    					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
    				}  					
                }else if (stateChange == ItemEvent.DESELECTED) {  

                }else {  

                }  
            }  
        });  
	}
	
	public void autoSetSelectedItem(String strItem)
	{
		int selectedItemIndex = 0;
    	ArrayList<String> enumString = _enumValue.getValueArray();
    	for(int i=0;i<enumString.size();i++)
    	{
    		String str= enumString.get(i);
//    		comboBox.addItem(str);
			if(strItem.equals(_enumValue.getKeyFromValue(str)))
			{
				selectedItemIndex = i;
			}
    	}
    	comboBox.setSelectedIndex(selectedItemIndex);		
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

				if(nvram.equals(_nvram))
				{
					autoSetSelectedItem(_enumValue.key);		    	
				}
	        }
		}
	}
}
