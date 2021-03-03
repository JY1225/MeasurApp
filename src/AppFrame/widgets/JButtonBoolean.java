package AppFrame.widgets;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import AppBase.appBase.CarTypeAdapter;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.NvramType;

public class JButtonBoolean extends JButton{
	private String DEBUG_TAG="JButtonBoolean";
	
	int _sensorID;

	String _labelStrTrue;
	String _labelStrFalse;
	
	Color _backGroundColorTrue;
	Color _backGroundColorFalse;
	
	NvramType _nvram;
	GridBagConstraints gbc;
	
	public JButtonBoolean(
		JPanel panel,
		int gridX,int gridY,int width,
		String labelStrTrue, 
		String labelStrFalse, 
		NvramType nvram,
		final int sensorID)
	{
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		_nvram = nvram;
		_labelStrTrue = labelStrTrue;
		_labelStrFalse = labelStrFalse;
		
		//===========================================================
		setLabelText();

		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==================================================================
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		gbc.gridwidth = width;
		panel.add(this, gbc);

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LMSLog.d(DEBUG_TAG, "bNvramPauseDetect="+CarTypeAdapter.bNvramPauseDetect.bValue);

	        	JButton jButton = (JButton)e.getSource();
	        	_nvram.bValue = !_nvram.bValue;
				
				//===========================================================
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, _nvram.nvramStr);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _nvram.bValue);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
			}
		});
	}

	public GridBagConstraints getGBC()
	{
		return gbc;
	}
	
	public void resetTextBackGroundColor(Color colorTrue,Color colorFalse)
	{
		_backGroundColorTrue = colorTrue;
		_backGroundColorFalse = colorFalse;
		setLabelText();
	}
	
	public void setLabelText()
	{
		if(_nvram.bValue == true)
		{
			setText(_labelStrTrue);		
			if(_backGroundColorTrue != null)
			{
				setBackground(_backGroundColorTrue);
			}
		}
		else
		{
			setText(_labelStrFalse);			    								
			if(_backGroundColorFalse != null)
			{
				setBackground(_backGroundColorFalse);
			}
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
				if(_sensorID >=0 && sensorID != _sensorID)
				{
					return;
				}
			}

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    	
				String nvramStr = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
				
				if(nvramStr.equals(_nvram.nvramStr))
				{
					setLabelText();
				}
	        }
		}
	}
}
