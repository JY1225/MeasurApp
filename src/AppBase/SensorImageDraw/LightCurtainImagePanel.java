package AppBase.SensorImageDraw;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JPanel;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import SensorBase.LMSConstValue;

public class LightCurtainImagePanel extends JPanel{
	private String DEBUG_TAG="LightCurtainImagePanel";

	EventListener eventListener;
	LightCurtainImageMonitorPanel lightCurtainImageMonitorPanel;
	
	public LightCurtainImagePanel() 
	{		
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==============================================================================
		int gridX,gridY = 0;

		GridBagLayout gbc_panel = new GridBagLayout();
		gbc_panel.columnWidths = new int[]{12};
		gbc_panel.rowHeights = new int[]{};
		gbc_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbc_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gbc_panel);

		//============================================================					
		lightCurtainImageMonitorPanel = new LightCurtainImageMonitorPanel();
		GridBagLayout gbl_aPane = new GridBagLayout();
		gbl_aPane.columnWidths = new int[]{0};
		gbl_aPane.rowHeights = new int[]{0};
		gbl_aPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_aPane.rowWeights = new double[]{Double.MIN_VALUE};
		lightCurtainImageMonitorPanel.setLayout(gbl_aPane);

		GridBagConstraints gbc_aPane = new GridBagConstraints();
		gbc_aPane.insets = new Insets(0, 0, 5, 5);
		gbc_aPane.gridheight = gridY+1;
		gbc_aPane.fill = GridBagConstraints.BOTH;
		gbc_aPane.gridx = 0;
		gbc_aPane.gridy = 0;
		add(lightCurtainImageMonitorPanel, gbc_aPane);		
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 

			if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
			{
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

				if(nvram.equals(LMSConstValue.nvramSensorType)&&sensorID==LMSConstValue.LIGHT_CURTAIN_ID_START) 
				{			 
					lightCurtainImageMonitorPanel.repaint();
				}
	        }
		}
	}	
}
