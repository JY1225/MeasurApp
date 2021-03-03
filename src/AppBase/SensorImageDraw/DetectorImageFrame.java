package AppBase.SensorImageDraw;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class DetectorImageFrame extends JFrame{
	String DEBUG_TAG="DetectorImageFrame";

	EventListener eventListener;

	JPanel splitPanelLightCurtain;
	GridBagConstraints gbc_splitPanelLightCurtain;
	
	static DetectorImageFrame detectorImageFrame;
	
	public DetectorImageFrame()
	{		
		//==============================================================================
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==============================================================================		
		setBounds(200, 100, 900, 640);

		//==============================================================================
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{829};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0,1.0};
		getContentPane().setLayout(gridBagLayout);

		//========================================================
		setTitle("IMI-BES ¼ì²âÒÇ¼à¿Ø");
				
		if(LMSConstValue.iRadarSensorNum == 1)
		{	
			JSplitPane splitPanel = new RadarImagePanel(0);
			GridBagConstraints gbc_splitPanel = new GridBagConstraints();
			gbc_splitPanel.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel.fill = GridBagConstraints.BOTH;
			gbc_splitPanel.gridx = 0;
			gbc_splitPanel.gridy = 0;
			add(splitPanel, gbc_splitPanel);
		}
		else if(LMSConstValue.iRadarSensorNum == 2)
		{	
			JSplitPane splitPanel0 = new RadarImagePanel(0);
			GridBagConstraints gbc_splitPanel0 = new GridBagConstraints();
			gbc_splitPanel0.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel0.fill = GridBagConstraints.BOTH;
			gbc_splitPanel0.gridx = 0;
			gbc_splitPanel0.gridy = 0;
			add(splitPanel0, gbc_splitPanel0);

			JSplitPane splitPanel1 = new RadarImagePanel(1);
			GridBagConstraints gbc_splitPanel1 = new GridBagConstraints();
			gbc_splitPanel1.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel1.fill = GridBagConstraints.BOTH;
			gbc_splitPanel1.gridx = 0;
			gbc_splitPanel1.gridy = 1;
			add(splitPanel1, gbc_splitPanel1);
		}
		else if(LMSConstValue.iRadarSensorNum == 3)
		{	
			JSplitPane splitPanel0 = new RadarImagePanel(0);
			GridBagConstraints gbc_splitPanel0 = new GridBagConstraints();
			gbc_splitPanel0.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel0.fill = GridBagConstraints.BOTH;
			gbc_splitPanel0.gridwidth = 1;
			gbc_splitPanel0.gridx = 0;
			gbc_splitPanel0.gridy = 0;
			add(splitPanel0, gbc_splitPanel0);

			JSplitPane splitPanel1 = new RadarImagePanel(1);
			GridBagConstraints gbc_splitPanel1 = new GridBagConstraints();
			gbc_splitPanel1.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel1.fill = GridBagConstraints.BOTH;
			gbc_splitPanel1.gridwidth = 1;
			gbc_splitPanel1.gridx = 1;
			gbc_splitPanel1.gridy = 0;
			add(splitPanel1, gbc_splitPanel1);

			JSplitPane splitPanel2 = new RadarImagePanel(2);
			GridBagConstraints gbc_splitPanel2 = new GridBagConstraints();
			gbc_splitPanel2.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel2.fill = GridBagConstraints.BOTH;
			gbc_splitPanel2.gridwidth = 2;
			gbc_splitPanel2.gridx = 0;
			gbc_splitPanel2.gridy = 1;
			add(splitPanel2, gbc_splitPanel2);
			
			splitPanelLightCurtain = new LightCurtainImagePanel();
			gbc_splitPanelLightCurtain = new GridBagConstraints();
			gbc_splitPanelLightCurtain.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanelLightCurtain.fill = GridBagConstraints.BOTH;
			gbc_splitPanelLightCurtain.gridwidth = 1;
			gbc_splitPanelLightCurtain.gridheight = 2;
			gbc_splitPanelLightCurtain.gridx = 2;
			gbc_splitPanelLightCurtain.gridy = 0;
			LMSLog.d(DEBUG_TAG,"------------------------"+LMSConstValue.LIGHT_CURTAIN_ID_START+" DD="+LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key);
			if(LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
				||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_2)
				||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_840)
				||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_1600)
				||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.ZM10)
				||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.PS_16I))
			{
				add(splitPanelLightCurtain, gbc_splitPanelLightCurtain);
			}
		}
		else if(LMSConstValue.iRadarSensorNum == 4)
		{	
			JSplitPane splitPanel0 = new RadarImagePanel(0);
			GridBagConstraints gbc_splitPanel0 = new GridBagConstraints();
			gbc_splitPanel0.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel0.fill = GridBagConstraints.BOTH;
			gbc_splitPanel0.gridwidth = 1;
			gbc_splitPanel0.gridx = 0;
			gbc_splitPanel0.gridy = 0;
			add(splitPanel0, gbc_splitPanel0);

			JSplitPane splitPanel1 = new RadarImagePanel(1);
			GridBagConstraints gbc_splitPanel1 = new GridBagConstraints();
			gbc_splitPanel1.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel1.fill = GridBagConstraints.BOTH;
			gbc_splitPanel1.gridwidth = 1;
			gbc_splitPanel1.gridx = 1;
			gbc_splitPanel1.gridy = 0;
			add(splitPanel1, gbc_splitPanel1);

			JSplitPane splitPanel2 = new RadarImagePanel(2);
			GridBagConstraints gbc_splitPanel2 = new GridBagConstraints();
			gbc_splitPanel2.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel2.fill = GridBagConstraints.BOTH;
			gbc_splitPanel2.gridwidth = 1;
			gbc_splitPanel2.gridx = 0;
			gbc_splitPanel2.gridy = 1;
			add(splitPanel2, gbc_splitPanel2);
			
			JSplitPane splitPanel3 = new RadarImagePanel(3);
			GridBagConstraints gbc_splitPanel3 = new GridBagConstraints();
			gbc_splitPanel3.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanel3.fill = GridBagConstraints.BOTH;
			gbc_splitPanel3.gridwidth = 1;
			gbc_splitPanel3.gridx = 1;
			gbc_splitPanel3.gridy = 1;
			add(splitPanel3, gbc_splitPanel3);
		}
			
		//=============================================================
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);  
				
				LMSConstValue.bDetectorMonitor = false; 
				
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramDetectorMonitor);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bDetectorMonitor);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
			}
		});
		
		//===================================================================
		setExtendedState(JFrame.NORMAL);	
	}
	
	public static void showFrame()
	{
		if(detectorImageFrame == null)
		{
			detectorImageFrame = new DetectorImageFrame();
		}
		
		if(!detectorImageFrame.isVisible())
		{
			LMSConstValue.bDetectorMonitor = true;
			detectorImageFrame.setVisible(true);			
		}
		else
		{
			LMSConstValue.bDetectorMonitor = false;
			detectorImageFrame.setVisible(false);						
		}
		
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramDetectorMonitor);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bDetectorMonitor);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
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

				if(nvram.equals(LMSConstValue.nvramSensorType)&&sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START ) 
				{
					if(LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
						||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_2)
						||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_840)
						||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_1600)
						||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.ZM10)
						||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.PS_16I))
					{
						add(splitPanelLightCurtain, gbc_splitPanelLightCurtain);
					}
					else
					{
						remove(splitPanelLightCurtain);
					}
					revalidate();
				}
				else if(nvram.equals(LMSConstValue.nvramDetectorMonitor)) 
				{
					setVisible(LMSConstValue.bDetectorMonitor);        
				}
	        }
		}
	}
}
