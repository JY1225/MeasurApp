package AppFrame.widgets;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;

public class SensorIPWidget {
	private String DEBUG_TAG="JTextFieldSensorPort";

	int _sensorID;
	
	JLabelSpinner spinnerSensorType;
	JLabel textViewSensorIP;
	JTextField textFieldSensorIP;
	JLabelSensorPort labelSensorPort;
	JTextFieldSensorPort textFieldSensorPort;

	public SensorIPWidget(int sensorID,JPanel sensorPanel,int gridX,int gridY)
	{
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		
		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==================================================================
		new JLabelComboBox(
			sensorPanel,
			gridX,gridY,
			1,
			null,
			_sensorID,
			LMSConstValue.nvramSensorType,LMSConstValue.sensorType[_sensorID]
		);
		gridX++;
		
		//===========================================================================
		textViewSensorIP = new JLabel();		
		resetIPLabel(sensorID);
		
		if(LMSConstValue.bSensorPortConnected[sensorID] == true)
			textViewSensorIP.setBackground(Color.GREEN);			
		else
			textViewSensorIP.setBackground(Color.RED);	
		textViewSensorIP.setOpaque(true); 
		GridBagConstraints gbc_textViewSensorIP = new GridBagConstraints();
		gbc_textViewSensorIP.gridwidth = 2;
		gbc_textViewSensorIP.fill = GridBagConstraints.VERTICAL;
		gbc_textViewSensorIP.anchor = GridBagConstraints.WEST;
		gbc_textViewSensorIP.insets = new Insets(0, 0, 5, 5);
		gbc_textViewSensorIP.gridx = gridX;
		gbc_textViewSensorIP.gridy = gridY;
		sensorPanel.add(textViewSensorIP, gbc_textViewSensorIP);
   		gridX+=2;
   		
   		textFieldSensorIP = new JTextField();
		GridBagConstraints gbc_textFieldLMSIP = new GridBagConstraints();
		gbc_textFieldLMSIP.fill = GridBagConstraints.BOTH;
		gbc_textFieldLMSIP.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLMSIP.gridx = gridX;
		gbc_textFieldLMSIP.gridy = gridY;
		sensorPanel.add(textFieldSensorIP, gbc_textFieldLMSIP);
		textFieldSensorIP.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = textFieldSensorIP.getText().toString();
   				
				CarDetectSetting.setSensorIP(_sensorID,str);				
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		gridX++;
		
		labelSensorPort = new JLabelSensorPort(_sensorID);
		GridBagConstraints gbc_portLabel = new GridBagConstraints();
		gbc_portLabel.fill = GridBagConstraints.VERTICAL;
		gbc_portLabel.anchor = GridBagConstraints.WEST;
		gbc_textViewSensorIP.insets = new Insets(0, 0, 5, 5);
		gbc_portLabel.gridx = gridX;
		gbc_portLabel.gridy = gridY;
		sensorPanel.add(labelSensorPort, gbc_portLabel);
   		gridX++;
   			   		
   		textFieldSensorPort = new JTextFieldSensorPort(_sensorID);
		GridBagConstraints gbc_textFieldRadarPort = new GridBagConstraints();
		gbc_textFieldRadarPort.fill = GridBagConstraints.BOTH;
		gbc_textFieldRadarPort.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldRadarPort.gridx = gridX;
		gbc_textFieldRadarPort.gridy = gridY;
		sensorPanel.add(textFieldSensorPort, gbc_textFieldRadarPort);
		gridX++;		
	}
	
	void resetIPLabel(int sensorID)
	{
		if(sensorID<LMSConstValue.RADAR_SENSOR_NUM)
			textViewSensorIP.setText("雷达IP("+LMSConstValue.SENSOR_IP[sensorID].toUpperCase()+")");
		else if(sensorID == LMSConstValue.FRONT_CAMERA_ID)
			textViewSensorIP.setText("前摄像头地址("+LMSConstValue.SENSOR_IP[sensorID].toUpperCase()+")");		        				
		else if(sensorID == (LMSConstValue.BACK_CAMERA_ID))
			textViewSensorIP.setText("后摄像头地址("+LMSConstValue.SENSOR_IP[sensorID].toUpperCase()+")");		        				
		else 
			textViewSensorIP.setText("通信地址("+LMSConstValue.SENSOR_IP[sensorID].toUpperCase()+")");		        				
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

	        	if(nvram.equals(LMSConstValue.nvramSensorIP)) 
		        {	    
	        		resetIPLabel(sensorID);
		        }  
		        else if(nvram.equals(LMSConstValue.nvramSensorPort)) 
		        {	    			
		        	labelSensorPort.resetText();	        		
		        }  
		        else if(nvram.equals(LMSConstValue.nvramSensorPortConnected)) 
		        {	    			
		        	if(textViewSensorIP != null)
		        	{
						if(LMSConstValue.bSensorPortConnected[sensorID] == true)
							textViewSensorIP.setBackground(Color.GREEN);			
						else
							textViewSensorIP.setBackground(Color.RED);			    		
		        	}
		        }  
		        else if(nvram.equals(LMSConstValue.nvramSensorType)) 
		        {
		        	labelSensorPort.resetText();	        		
		        }  
		    }
		}	
	}
}
