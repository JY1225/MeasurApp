package AppBase.SensorImageDraw;

import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;

public class RadarImageFrame extends JFrame{
	private String DEBUG_TAG = "RadarImageFrame";

	int _sensorID;
		
	EventListener eventListener;
		
	static RadarImageFrame radarImageFrame[] = new RadarImageFrame[LMSConstValue.RADAR_SENSOR_NUM];

	public RadarImageFrame(final int sensorID) 
	{
		_sensorID = sensorID;
		
		DEBUG_TAG += _sensorID;
		
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

		//==============================================================================
		//单独的每个头
		if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
			setTitle("长雷达");
		else
			setTitle("宽高雷达"+(sensorID+1));

		JSplitPane splitPanel = new RadarImagePanel(sensorID);
		GridBagConstraints gbc_splitPanel = new GridBagConstraints();
		gbc_splitPanel.insets = new Insets(0, 0, 5, 0);
		gbc_splitPanel.fill = GridBagConstraints.BOTH;
		gbc_splitPanel.gridx = 0;
		gbc_splitPanel.gridy = 0;
		getContentPane().add(splitPanel, gbc_splitPanel);
			
		//=============================================================
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);  
				
				LMSLog.d(DEBUG_TAG,"imageMonitorFrame["+sensorID+"] close!");
				LMSConstValue.bRadarMonitor[sensorID] = false; 
				
	    		LMSEventManager.removeListener(eventListener);

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramRadarMonitor);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bRadarMonitor[LMSConstValue.iRadarSensorNum]);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
  					
			}
		});
		
		//===================================================================
		setExtendedState(JFrame.NORMAL);	
 	}

	public static void showFrame(int sensorID)
	{
		if(radarImageFrame[sensorID] == null)
		{
			radarImageFrame[sensorID] = new RadarImageFrame(sensorID);
		}
		
		if(!radarImageFrame[sensorID].isVisible())
		{
			LMSConstValue.bRadarMonitor[sensorID] = true;
			radarImageFrame[sensorID].setVisible(true);			
		}
		else
		{
			LMSConstValue.bRadarMonitor[sensorID] = false;
			radarImageFrame[sensorID].setVisible(false);						
		}
		
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramRadarMonitor);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bRadarMonitor[LMSConstValue.iRadarSensorNum]);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
		}
	}
}
