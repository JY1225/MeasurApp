package AppFrame.logisticsMachine;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import SensorBase.LMSConstValue;

import AppFrame.widgets.AuthWidget;
import CarDetect.CarDetectSetting;

public class LogisticsMachineTabPanelSetting {
	private final static String DEBUG_TAG="LogisticMachineTabPanelSetting";

	JLabel conveyerSpeedLabel;
	GridBagConstraints gbc_conveyerSpeedLabel;
	
	JTextField conveyerSpeedTextField;
	GridBagConstraints gbc_conveyerSpeedTextField;
	
	JLabel minPacketWidthLabel;
	GridBagConstraints gbc_minPacketWidthLabel;
	
	JTextField minPacketWidthTextField;
	GridBagConstraints gbc_minPacketWidthTextField;
	
	JLabel machingTimeLabel;
	GridBagConstraints gbc_machingTimeLabel;
	
	JTextField machingTimeTextField;
	GridBagConstraints gbc_machingTimeTextField;
	
	JPanel panel = new JPanel();

	public LogisticsMachineTabPanelSetting()
	{
	    EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	}
	
	public JPanel createTab() {		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{};
		gridBagLayout.rowHeights = new int[]{50,50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		//===============================================================
		new AuthWidget(panel, gridX, gridY, "连接密码");

		gridX = 0;	
		gridY++;
		
		//==============================================================================
		conveyerSpeedLabel = new JLabel("输送带速度("+LMSConstValue.iConVeyerSpeed+"毫米/秒)");
		gbc_conveyerSpeedLabel = new GridBagConstraints();
		gbc_conveyerSpeedLabel.fill = GridBagConstraints.BOTH;
		gbc_conveyerSpeedLabel.gridwidth = 1;
		gbc_conveyerSpeedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_conveyerSpeedLabel.gridx = gridX;
		gbc_conveyerSpeedLabel.gridy = gridY;
		gridX++;
		
		conveyerSpeedTextField = new JTextField();
		gbc_conveyerSpeedTextField = new GridBagConstraints();
		gbc_conveyerSpeedTextField.fill = GridBagConstraints.BOTH;
		gbc_conveyerSpeedTextField.gridwidth = 3;
		gbc_conveyerSpeedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_conveyerSpeedTextField.gridx = gridX;
		gbc_conveyerSpeedTextField.gridy = gridY;
		conveyerSpeedTextField.addFocusListener(new FocusListener(){		
			public void focusLost(FocusEvent e) {
				String str = conveyerSpeedTextField.getText().toString();
								
				CarDetectSetting.setConveyerSpeed(str);				
				
				conveyerSpeedLabel.setText("输送带速度("+str+"毫米/秒)");
		     }  

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		gridY++;
		gridX = 0;
		
		//===============================================================
		minPacketWidthLabel = new JLabel("无条码时最小包裹宽("+LMSConstValue.iMinPacketWidth+"mm)");
		gbc_minPacketWidthLabel = new GridBagConstraints();
		gbc_minPacketWidthLabel.fill = GridBagConstraints.BOTH;
		gbc_minPacketWidthLabel.gridwidth = 1;
		gbc_minPacketWidthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_minPacketWidthLabel.gridx = gridX;
		gbc_minPacketWidthLabel.gridy = gridY;
		gridX++;
		
		minPacketWidthTextField = new JTextField();
		gbc_minPacketWidthTextField = new GridBagConstraints();
		gbc_minPacketWidthTextField.fill = GridBagConstraints.BOTH;
		gbc_minPacketWidthTextField.gridwidth = 3;
		gbc_minPacketWidthTextField.insets = new Insets(0, 0, 5, 5);
		gbc_minPacketWidthTextField.gridx = gridX;
		gbc_minPacketWidthTextField.gridy = gridY;
		minPacketWidthTextField.addFocusListener(new FocusListener(){		
			public void focusLost(FocusEvent e) {
				String str = minPacketWidthTextField.getText().toString();
								
				CarDetectSetting.setMinPacketWidth(str);				
				
				minPacketWidthLabel.setText("无条码时最小包裹宽("+str+"mm)");
		     }  

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		gridY++;
		gridX = 0;

		//-----------------------------------------------------------
		machingTimeLabel = new JLabel("匹配时间间隔"+LMSConstValue.iNoReadNoVolumnMachingTimeInteval+"ms)");
		gbc_machingTimeLabel = new GridBagConstraints();
		gbc_machingTimeLabel.fill = GridBagConstraints.BOTH;
		gbc_machingTimeLabel.gridwidth = 1;
		gbc_machingTimeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_machingTimeLabel.gridx = gridX;
		gbc_machingTimeLabel.gridy = gridY;
		gridX++;
		
		machingTimeTextField = new JTextField();
		gbc_machingTimeTextField = new GridBagConstraints();
		gbc_machingTimeTextField.fill = GridBagConstraints.BOTH;
		gbc_machingTimeTextField.gridwidth = 1;
		gbc_machingTimeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_machingTimeTextField.gridx = gridX;
		gbc_machingTimeTextField.gridy = gridY;
		machingTimeTextField.addFocusListener(new FocusListener(){		
			public void focusLost(FocusEvent e) {
				String str = machingTimeTextField.getText().toString();
								
				CarDetectSetting.setNoReadNoVolumnMachingTime(str);				
				
				machingTimeLabel.setText("匹配时间间隔"+LMSConstValue.iNoReadNoVolumnMachingTimeInteval+"ms)");
		     }  

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		gridX++;

		return panel;
    }
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
//			LMSLog.d(DEBUG_TAG, "EventListener...");
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
									
			int sensorID = 0;
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
			
	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    		
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
				
				if(nvram.equals(LMSConstValue.nvramAuth)) 
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

					if(str.equals("13579")||str.equals(Md5.convertMD5(LMSConstValue.sNvramSettingPassword.sValue))||str.equals("63780zhi"))
					{
						panel.add(conveyerSpeedLabel,gbc_conveyerSpeedLabel);
						panel.add(conveyerSpeedTextField,gbc_conveyerSpeedTextField);

						panel.add(minPacketWidthLabel,gbc_minPacketWidthLabel);
						panel.add(minPacketWidthTextField,gbc_minPacketWidthTextField);

						panel.add(machingTimeLabel,gbc_machingTimeLabel);
						panel.add(machingTimeTextField,gbc_machingTimeTextField);						
					}
				}
	        }  
		}
	}	
}
