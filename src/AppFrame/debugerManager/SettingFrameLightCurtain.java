package AppFrame.debugerManager;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import AppFrame.contourDetection.ContourImageLabel;
import AppFrame.widgets.JLabelSystemStatus;
import AppFrame.widgets.JLableSensorParameter;
import AppFrame.widgets.JSettingLabelTextField;
import AppFrame.widgets.SensorIPWidget;
import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class SettingFrameLightCurtain {
	private final static String DEBUG_TAG="SettingFrameLightCurtain";

	JLabelSystemStatus systemStatusLabel;
	JLabel sensorParameterLabel;
	JLabel lightStatusLabel[] = new JLabel[LMSConstValue.LIGHT_CURTAIN_LIGHT_NUM];
			
	JSplitPane mainTab;
	JPanel pointValidSettingPanel;
	
	private static EventListener eventListener;
	ContourImageLabel imageLabel;
	JSettingLabelTextField lightCurtianRadarDistanceLabelTextField;
	
	public JSplitPane createTab() {
        //=============================================================
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
		
		//======================================================
		mainTab = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	
		//=====================================================================
		mainTab.setLeftComponent(createSettingPanel());
	
		mainTab.setRightComponent(createPointValidSettingPanel());
    	
		return mainTab;
	}
	
	private JPanel createSettingPanel()
	{
		JPanel panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 160, 0, 120, 120, 80, 580};
		gridBagLayout.rowHeights = new int[]{86, 86, 86, 86};
		gridBagLayout.columnWeights = new double[]{0, 0, 0, 0, 0, 0, 0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
	
		systemStatusLabel = new JLabelSystemStatus(LMSConstValue.LIGHT_CURTAIN_ID_START);			
		GridBagConstraints gbc_systemStatusLabel = new GridBagConstraints();
		gbc_systemStatusLabel.fill = GridBagConstraints.BOTH;
		gbc_systemStatusLabel.anchor = GridBagConstraints.WEST;
		gbc_systemStatusLabel.gridwidth = 6;
		gbc_systemStatusLabel.insets = new Insets(0, 0, 5, 0);
		gbc_systemStatusLabel.gridx = gridX;
		gbc_systemStatusLabel.gridy = gridY;
		panel.add(systemStatusLabel, gbc_systemStatusLabel);
		gridX+=6;
				
		//===================================================================
		imageLabel = new ContourImageLabel(panel,gridX,gridY,null,
			"image//left."+LMSConstValue.enumImageFormat.key,1,4);
		gridX+=1;

		gridY++;
		//===================================================================
		gridX = 0;
		sensorParameterLabel = new JLableSensorParameter(LMSConstValue.LIGHT_CURTAIN_ID_START);
		GridBagConstraints gbc_sensorParameterLabel = new GridBagConstraints();
		gbc_sensorParameterLabel.fill = GridBagConstraints.BOTH;
		gbc_sensorParameterLabel.anchor = GridBagConstraints.WEST;
		gbc_sensorParameterLabel.gridwidth = 6;
		gbc_sensorParameterLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sensorParameterLabel.gridx = gridX;
		gbc_sensorParameterLabel.gridy = gridY;
		panel.add(sensorParameterLabel, gbc_sensorParameterLabel);
		gridX+=6;

		gridY++;
		//===================================================================
		gridX = 0;
		SensorIPWidget sensorIPWidget = new SensorIPWidget(LMSConstValue.LIGHT_CURTAIN_ID_START,panel,gridX,gridY);

		gridX+=6;

		gridY++;
		//===================================================================
		gridX = 0;
		lightCurtianRadarDistanceLabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 2, 2,
			"雷达1与光栅间距", "mm", true,
			false,
			true,
			LMSConstValue.iNvramLightCurtianLongDistance, -1, -1
		);
		lightCurtianRadarDistanceLabelTextField.setEditable(false);
		lightCurtianRadarDistanceLabelTextField.setPositiveKeyNumOnly();
		gridX+=2;	
		return panel;
	}
	
	private JPanel createPointValidSettingPanel()
	{
		JPanel panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{
			1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
		
		JLabel lightCurtainValidLabel = new JLabel("光点有效与否设置");
		GridBagConstraints gbc_lightCurtainValidLabel = new GridBagConstraints();
		gbc_lightCurtainValidLabel.gridwidth = 12;
		gbc_lightCurtainValidLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lightCurtainValidLabel.anchor = GridBagConstraints.WEST;
		gbc_lightCurtainValidLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lightCurtainValidLabel.gridx = gridX;
		gbc_lightCurtainValidLabel.gridy = gridY;
		panel.add(lightCurtainValidLabel, gbc_lightCurtainValidLabel);
		gridY++;
		
		int count = 0;
		int LINE_NUM = 4;
		for(int i=0;i<LINE_NUM;i++)
		{
			for(int j=0;j<LMSConstValue.LIGHT_CURTAIN_LIGHT_NUM/LINE_NUM;j++)
			{
				lightStatusLabel[count] = new JLabel(""+count);		
				lightStatusLabel[count].setOpaque(true); 
				lightStatusLabel[count].setBackground(Color.GREEN);
				GridBagConstraints gbc_lightStatusLabel = new GridBagConstraints();
				//gbc_lightStatusLabel.fill = GridBagConstraints.BOTH;
				gbc_lightStatusLabel.anchor = GridBagConstraints.CENTER;
				gbc_lightStatusLabel.gridwidth = 1;
				gbc_lightStatusLabel.insets = new Insets(0, 0, 5, 0);
				gbc_lightStatusLabel.gridx = gridX;
				gbc_lightStatusLabel.gridy = gridY;
				panel.add(lightStatusLabel[count], gbc_lightStatusLabel);
			
				count++;
				
				gridX++;
			}
			gridY++;
			gridX = 0;
		}
		
		return panel;
	}
	
	class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
			}
			
	        if(eventType != null)
	        {		
		        if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
		        {
					if((Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.CAR_RESULT.ordinal()
						||(Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.CAR_REGENATE_RESULT.ordinal()
						||(Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.LIGHTCURTAIN_REGENATE.ordinal()
					)
					{
						imageLabel.setScaledIconImage(null);
					}
		        }
	        }
		}
	}
}
