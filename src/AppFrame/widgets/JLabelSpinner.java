package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.enumType;

public class JLabelSpinner {
	private String DEBUG_TAG="JLabelSpinner";

	int _sensorID;
	int _index;

	String _labelStr;
	
	String _nvram;
	enumType _enumValue;
	
	JSpinner spinner;

	public JLabelSpinner(
		JPanel panel,
		int gridX,int gridY,
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
		}
		
		SpinnerModel spinnerModel = new SpinnerListModel(_enumValue.getValueArray());
		spinner = new JSpinner(spinnerModel);
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.BOTH;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = gridX;
		gbc_spinner.gridy = gridY;
		try{
			spinner.getModel().setValue(_enumValue.getValueFromKey(_enumValue.key));
		}
		catch(IllegalArgumentException e)
		{
			ArrayList<String> enumString = _enumValue.getValueArray();
			
			String strException = "invalid spinner:"+_enumValue.key;
			strException +="(";
			
			for(int i=0;i<enumString.size();i++)
			{
				strException += enumString.get(i)+";";
			}
			strException +=")";

			LMSLog.exceptionDialog(strException,e);
		}
		panel.add(spinner, gbc_spinner);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				JSpinner spinner = (JSpinner) (arg0.getSource());
				String str = spinner.getValue().toString();

				if(!str.equals(_enumValue.getValueFromKey(_enumValue.key)))
				{
					_enumValue.key = _enumValue.getKeyFromValue(str);

					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, _nvram);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _enumValue.key);
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
				}
			}
		});
		gridX++;
	}
	
	public void setEnabled(boolean bEditable)
	{
		 spinner.setEnabled(bEditable);
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
					spinner.getModel().setValue(_enumValue.getValueFromKey(_enumValue.key));
				}
	        }
		}
	}
}
