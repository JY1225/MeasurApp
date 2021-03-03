package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppBase.appBase.CarTypeAdapter;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.NvramType;
import SensorBase.enumType;

public class JCheckBoxNvram extends JCheckBox{
	private String DEBUG_TAG="JCheckBoxNvram";

	int _sensorID;
	int _index;

	String _labelStr;
	
	boolean _bSave;
	NvramType _nvram;
	enumType _enumValue;
	
	ButtonGroup buttonGroup;

	boolean bAutoSet = false;

	public JCheckBoxNvram(
		JPanel panel,
		int gridX, int gridY,int width,
		String labelStr, NvramType nvram,
		final int sensorID,
		boolean bSave
	)
	{		
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		_labelStr = labelStr;
		_nvram = nvram;
		_bSave = bSave;
		
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
		
		//========================================================================
		setText(_labelStr);
		
		GridBagConstraints gbc_jCheckBox = new GridBagConstraints();
		gbc_jCheckBox.fill = GridBagConstraints.BOTH;
		gbc_jCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_jCheckBox.gridwidth = width;
		gbc_jCheckBox.gridx = gridX;
		gbc_jCheckBox.gridy = gridY;
		panel.add(this,gbc_jCheckBox);
		
		if(_nvram.bValue)
			setSelected(true);
		
		addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) { 
	        	JCheckBox jCheckBox=(JCheckBox)e.getSource();
	        	
	        	if (jCheckBox.isSelected()) {
	        		_nvram.bValue = true;
                } else {
                	_nvram.bValue = false;
                }

	        	LMSLog.d(DEBUG_TAG,"AAAAAAAAAA0="+bAutoSet);

	        	if(bAutoSet == false)
	        	{
		        	LMSLog.d(DEBUG_TAG,"AAAAAAAAAA1="+_nvram.bValue);
				
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, _nvram.nvramStr);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _nvram.bValue);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, _bSave);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	        	}
	        	
        		bAutoSet = false;
	         }           
		});
	}
	
	public void autoSelect(boolean bSelect)
	{
		if(
			(bSelect&&!isSelected())
			||(!bSelect&&isSelected())
		)
		{
			bAutoSet = true;
			
			setSelected(bSelect);
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
