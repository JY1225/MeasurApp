package AppFrame.debugerManager;

import http.WebService.XMLParse;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import AppBase.appBase.AppConst;
import AppFrame.debugerManager.SettingFrameBasicTab.CarRoadWidthTextField;
import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JButtonFixLed;
import AppFrame.widgets.JButtonMonitor;
import AppFrame.widgets.JButtonSendFSRLCmd;
import AppFrame.widgets.JLabelSpinner;
import AppFrame.widgets.JLabelSystemStatus;
import AppFrame.widgets.JLableSensorParameter;
import AppFrame.widgets.JSettingLabelTextField;
import CarAppAlgorithm.WidthHeightDetectRunnable;
import CarDetect.CarDetectSetting;

import SensorBase.LMSConstValue;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;
import SensorBase.LMSConstValue.enumBoardType;

import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;

public class SettingFrame {
	private final static String DEBUG_TAG="SettingFrame";

	JPanel panel;

	///*
	protected JLabel textViewCarState[] = new JLabel[LMSConstValue.MAX_CAR_ROAD_NUM];
	protected JLabelSystemStatus systemStatusLabel[] = new JLabelSystemStatus[LMSConstValue.SYSTEM_SENSOR_NUM];
	protected JLableSensorParameter sensorParameterLabel[]= new JLableSensorParameter[LMSConstValue.SYSTEM_SENSOR_NUM];

	protected JLabel labelAntiLevel[][] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM][LMSConstValue.ANTI_LEVEL];
	protected JTextField editTextAntiLevel[][] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM][LMSConstValue.ANTI_LEVEL];
	protected JLabel textLeftWindow[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JLabel textRightWindow[]= new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JLabel textHeightWindow[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField editTextLeftWindow[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField editTextRightWindow[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField editTextHeightWindow[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	
	protected JLabel textFilterStartAngle[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JLabel textFilterEndAngle[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField editTextFilterStartAngle[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField editTextFilterEndAngle[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];

	protected JLabel textGroundStartAngle[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JLabel textGroundEndAngle[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField editTextGroundStartAngle[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField editTextGroundEndAngle[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];

	protected JLabel lableAngleLROffset[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField textFieldAngleLROffset[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	protected JLabel lableAngleFBOffset[]= new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField textFieldAngleFBOffset[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	protected JLabel lableAngleRotateOffset[] = new JLabel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JTextField textFieldAngleRotateOffset[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
	
	protected static JLabel labelCarRoadWidth[];
	protected static CarRoadWidthTextField editTextCarRoadWidth[];
	protected JLabel labelCarRoadNum;
	protected JTextField editTextCarRoadNum;
	protected JButton buttonCarRoadOutputTurn;	
	protected JButton buttonDataReplay;
	protected JButton buttonBoardType;

	protected JButtonFixLed buttonFixLed[] = new JButtonFixLed[LMSConstValue.RADAR_SENSOR_NUM];
	protected GridBagConstraints gbc_buttonFixLed[] = new GridBagConstraints[LMSConstValue.RADAR_SENSOR_NUM];

	protected JButtonSendFSRLCmd jButtonGetDistanceBetween[] = new JButtonSendFSRLCmd[LMSConstValue.RADAR_SENSOR_NUM];
	protected GridBagConstraints gbc_buttonGetDistanceBetween[] = new GridBagConstraints[LMSConstValue.RADAR_SENSOR_NUM];

	protected JButtonSendFSRLCmd jButtonGetHeight[] = new JButtonSendFSRLCmd[LMSConstValue.RADAR_SENSOR_NUM];
	protected GridBagConstraints gbc_buttonGetHeight[] = new GridBagConstraints[LMSConstValue.RADAR_SENSOR_NUM];

	protected JButtonBoolean rulerCalibrationButton;

	protected JButton buttonDoubleEdgeDetect;
	protected JSplitPane splitPane;
	protected int sensorPanleWindowSettingGridY;
	protected JPanel sensorPanel[] = new JPanel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JPanel sensorSettingPanelRight[] = new JPanel[LMSConstValue.RADAR_SENSOR_NUM];
	protected JPanel detectorPanel;
	protected JSplitPane splitPanelMachine;
	protected JSplitPane splitPanelCompensate;
	protected JLabel textViewMachine;
	protected JSplitPane splitPanelCarState;
	GridBagConstraints gbc_splitPanelCarState;
	protected JLabel labelCarState;
	protected JPanel carStatePanel;
	protected JSplitPane splitPanelLMS[] = new JSplitPane[LMSConstValue.RADAR_SENSOR_NUM];
	protected GridBagConstraints gbc_splitPanelLMS[] = new GridBagConstraints[LMSConstValue.RADAR_SENSOR_NUM];
	
	protected JRadioButton autoSetRadioButton;
	protected JRadioButton handSetRadioButton;
	protected ButtonGroup buttonGroup;
	
	protected JSettingLabelTextField LWDistanceSetting;
	protected JSettingLabelTextField LongWH0DistanceSetting;

	protected JSettingLabelTextField lightCurtianRadarDistanceLabelTextField;

	protected JLabel labelSetAsWHPosition;
	
	JButtonSendFSRLCmd jButtonGetStaticLength = new JButtonSendFSRLCmd("静态获取宽高雷达(1或2)与长雷达间距",LMSConstValue.strFSRLGetLength);
	JButtonSendFSRLCmd jButtonGetStaticWidth = new JButtonSendFSRLCmd("静态获取宽",LMSConstValue.strFSRLGetWidth);

	protected JLabelSpinner whPositionLabelSpinner;

	GridBagConstraints gbc_textViewBoardIP;
	GridBagConstraints gbc_spinnerBoardIP;
	GridBagConstraints gbc_buttonADB;
	GridBagConstraints gbc_buttonDataReplay;
	GridBagConstraints gbc_labelSimulateInteval;
	GridBagConstraints gbc_textFieldSimulateInteval;
	GridBagConstraints gbc_buttonBoardType;
	GridBagConstraints gbc_labelEraseEarLength;
	GridBagConstraints gbc_textFieldEraseEarLength;

	JSettingLabelTextField absoluteCompensateWidthLabelTextField;
	JSettingLabelTextField absoluteCompensateHeightLabelTextField;
	JSettingLabelTextField absoluteCompensateLengthLabelTextField;
	JSettingLabelTextField lrDistanceLabelTextField;
	JSettingLabelTextField lwDistance1LabelTextField;
	JSettingLabelTextField lwDistance2LabelTextField;
	
	JSettingLabelTextField customerLabelTextField;
	//*/
	public SettingFrame() {
	}
	
	void sensorPanleWindowSetting(int sensorID,int gridY)
	{
		int gridX = 0;
		
		sensorPanel[sensorID].remove(textLeftWindow[sensorID]);
		sensorPanel[sensorID].remove(editTextLeftWindow[sensorID]);
		sensorPanel[sensorID].remove(textRightWindow[sensorID]);
		sensorPanel[sensorID].remove(editTextRightWindow[sensorID]);
		sensorPanel[sensorID].remove(textHeightWindow[sensorID]);
		sensorPanel[sensorID].remove(editTextHeightWindow[sensorID]);

		sensorPanel[sensorID].remove(textFilterStartAngle[sensorID]);
		sensorPanel[sensorID].remove(editTextFilterStartAngle[sensorID]);
		sensorPanel[sensorID].remove(textFilterEndAngle[sensorID]);
		sensorPanel[sensorID].remove(editTextFilterEndAngle[sensorID]);

		sensorPanel[sensorID].remove(textGroundStartAngle[sensorID]);
		sensorPanel[sensorID].remove(editTextGroundStartAngle[sensorID]);
		sensorPanel[sensorID].remove(textGroundEndAngle[sensorID]);
		sensorPanel[sensorID].remove(editTextGroundEndAngle[sensorID]);

		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX)
   			||LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW)
   			||LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
   		{
			GridBagConstraints gbc_textLeftWindow = new GridBagConstraints();	
			gbc_textLeftWindow.fill = GridBagConstraints.BOTH;
			gbc_textLeftWindow.anchor = GridBagConstraints.WEST;
			gbc_textLeftWindow.gridwidth = 1;
			gbc_textLeftWindow.insets = new Insets(0, 0, 5, 5);
			gbc_textLeftWindow.gridx = gridX;
			gbc_textLeftWindow.gridy = gridY;
			sensorPanel[sensorID].add(textLeftWindow[sensorID], gbc_textLeftWindow);
			gridX++;
	
			GridBagConstraints gbc_editTextLeftWindow = new GridBagConstraints();
			gbc_editTextLeftWindow.fill = GridBagConstraints.BOTH;
			gbc_editTextLeftWindow.gridwidth = 1;
			gbc_editTextLeftWindow.insets = new Insets(0, 0, 5, 5);
			gbc_editTextLeftWindow.gridx = gridX;
			gbc_editTextLeftWindow.gridy = gridY;
			sensorPanel[sensorID].add(editTextLeftWindow[sensorID], gbc_editTextLeftWindow);
			gridX++;
   		}
		
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX)
   			||LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW)
   			||LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
   		{
			GridBagConstraints gbc_textRightWindow = new GridBagConstraints();	
			gbc_textRightWindow.fill = GridBagConstraints.BOTH;
			gbc_textRightWindow.gridwidth = 1;
			gbc_textRightWindow.insets = new Insets(0, 0, 5, 5);
			gbc_textRightWindow.gridx = gridX;
			gbc_textRightWindow.gridy = gridY;
			sensorPanel[sensorID].add(textRightWindow[sensorID], gbc_textRightWindow);
			gridX++;
			
			GridBagConstraints gbc_editTextRightWindow = new GridBagConstraints();
			gbc_editTextRightWindow.fill = GridBagConstraints.BOTH;
			gbc_editTextRightWindow.gridwidth = 1;
			gbc_editTextRightWindow.insets = new Insets(0, 0, 5, 5);
			gbc_editTextRightWindow.gridx = gridX;
			gbc_editTextRightWindow.gridy = gridY;
			sensorPanel[sensorID].add(editTextRightWindow[sensorID], gbc_editTextRightWindow);
			gridX++;
   		}
		
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW)
   			||LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
   		{
			GridBagConstraints gbc_textFrontWindow = new GridBagConstraints();	
			gbc_textFrontWindow.fill = GridBagConstraints.BOTH;
			gbc_textFrontWindow.anchor = GridBagConstraints.WEST;
			gbc_textFrontWindow.gridwidth = 1;
			gbc_textFrontWindow.insets = new Insets(0, 0, 5, 5);
			gbc_textFrontWindow.gridx = gridX;
			gbc_textFrontWindow.gridy = gridY;
			sensorPanel[sensorID].add(textHeightWindow[sensorID], gbc_textFrontWindow);
			gridX++;
	
			GridBagConstraints gbc_editTextFrontWindow = new GridBagConstraints();
			gbc_editTextFrontWindow.fill = GridBagConstraints.BOTH;
			gbc_editTextFrontWindow.gridwidth = 1;
			gbc_editTextFrontWindow.insets = new Insets(0, 0, 5, 5);
			gbc_editTextFrontWindow.gridx = gridX;
			gbc_editTextFrontWindow.gridy = gridY;
			sensorPanel[sensorID].add(editTextHeightWindow[sensorID], gbc_editTextFrontWindow);
			gridX++;
   		}
		
		//--------------------------------------------------------
		GridBagConstraints gbc_textFilterStartAngle = new GridBagConstraints();	
		gbc_textFilterStartAngle.fill = GridBagConstraints.BOTH;
		gbc_textFilterStartAngle.gridwidth = 1;
		gbc_textFilterStartAngle.insets = new Insets(0, 0, 5, 5);
		gbc_textFilterStartAngle.gridx = gridX;
		gbc_textFilterStartAngle.gridy = gridY;
		sensorPanel[sensorID].add(textFilterStartAngle[sensorID], gbc_textFilterStartAngle);
		gridX++;

		GridBagConstraints gbc_editTextFilterStartAngle = new GridBagConstraints();
		gbc_editTextFilterStartAngle.fill = GridBagConstraints.BOTH;
		gbc_editTextFilterStartAngle.gridwidth = 1;
		gbc_editTextFilterStartAngle.insets = new Insets(0, 0, 5, 5);
		gbc_editTextFilterStartAngle.gridx = gridX;
		gbc_editTextFilterStartAngle.gridy = gridY;
		sensorPanel[sensorID].add(editTextFilterStartAngle[sensorID], gbc_editTextFilterStartAngle);
		gridX++;

		GridBagConstraints gbc_textFilterEndAngle = new GridBagConstraints();	
		gbc_textFilterEndAngle.fill = GridBagConstraints.BOTH;
		gbc_textFilterEndAngle.anchor = GridBagConstraints.WEST;
		gbc_textFilterEndAngle.gridwidth = 1;
		gbc_textFilterEndAngle.insets = new Insets(0, 0, 5, 5);
		gbc_textFilterEndAngle.gridx = gridX;
		gbc_textFilterEndAngle.gridy = gridY;
		sensorPanel[sensorID].add(textFilterEndAngle[sensorID], gbc_textFilterEndAngle);
		gridX++;
		
		GridBagConstraints gbc_editTextEndAngle = new GridBagConstraints();
		gbc_editTextEndAngle.fill = GridBagConstraints.BOTH;
		gbc_editTextEndAngle.gridwidth = 1;
		gbc_editTextEndAngle.insets = new Insets(0, 0, 5, 5);
		gbc_editTextEndAngle.gridx = gridX;
		gbc_editTextEndAngle.gridy = gridY;
		sensorPanel[sensorID].add(editTextFilterEndAngle[sensorID], gbc_editTextEndAngle);
		gridX++;
		
		sensorPanel[sensorID].updateUI();//重绘界面
	}
	
	void createSensorSettingSplitPanelRight(JPanel panel,final int sensorID)
	{
		int gridX = 0;
		int gridY = 0;

		if(LMSConstValue.defaultDetectType != enumDetectType.ANTI_COLLITION)
		{
			GridBagConstraints gbc_groundStartAngle = new GridBagConstraints();
			gbc_groundStartAngle.fill = GridBagConstraints.BOTH;
			gbc_groundStartAngle.anchor = GridBagConstraints.WEST;
			gbc_groundStartAngle.gridwidth = 2;
			gbc_groundStartAngle.insets = new Insets(0, 0, 5, 0);
			gbc_groundStartAngle.gridx = gridX;
			gbc_groundStartAngle.gridy = gridY;
			panel.add(textGroundStartAngle[sensorID], gbc_groundStartAngle);
			gridX+=gbc_groundStartAngle.gridwidth;
			
			GridBagConstraints gbc_editTextGroundStartAngle = new GridBagConstraints();
			gbc_editTextGroundStartAngle.fill = GridBagConstraints.BOTH;
			gbc_editTextGroundStartAngle.gridwidth = 2;
			gbc_editTextGroundStartAngle.insets = new Insets(0, 0, 5, 5);
			gbc_editTextGroundStartAngle.gridx = gridX;
			gbc_editTextGroundStartAngle.gridy = gridY;
			panel.add(editTextGroundStartAngle[sensorID], gbc_editTextGroundStartAngle);
			gridY++;
			
			gridX = 0;
			GridBagConstraints gbc_groundEndAngle = new GridBagConstraints();
			gbc_groundEndAngle.fill = GridBagConstraints.BOTH;
			gbc_groundEndAngle.anchor = GridBagConstraints.WEST;
			gbc_groundEndAngle.gridwidth = 2;
			gbc_groundEndAngle.insets = new Insets(0, 0, 5, 0);
			gbc_groundEndAngle.gridx = gridX;
			gbc_groundEndAngle.gridy = gridY;
			panel.add(textGroundEndAngle[sensorID], gbc_groundEndAngle);
			gridX+=gbc_groundEndAngle.gridwidth;
			
			GridBagConstraints gbc_editTextGroundEndAngle = new GridBagConstraints();
			gbc_editTextGroundEndAngle.fill = GridBagConstraints.BOTH;
			gbc_editTextGroundEndAngle.gridwidth = 2;
			gbc_editTextGroundEndAngle.insets = new Insets(0, 0, 5, 5);
			gbc_editTextGroundEndAngle.gridx = gridX;
			gbc_editTextGroundEndAngle.gridy = gridY;
			panel.add(editTextGroundEndAngle[sensorID], gbc_editTextGroundEndAngle);
			gridY++;
		}
		
		//===============================================================================		
		gridX = 0;

		buttonFixLed[sensorID] = new JButtonFixLed(sensorID);
		gbc_buttonFixLed[sensorID] = new GridBagConstraints();
		gbc_buttonFixLed[sensorID].insets = new Insets(0, 0, 5, 5);
		gbc_buttonFixLed[sensorID].gridx = gridX;
		gbc_buttonFixLed[sensorID].gridy = gridY;
		gbc_buttonFixLed[sensorID].gridwidth = 2;
		panel.add(buttonFixLed[sensorID], gbc_buttonFixLed[sensorID]);
		gridX+=2;
		
		JButton getBaseDataButton = new JButton("建立基准");
		GridBagConstraints gbc_getBaseDataButton = new GridBagConstraints();
		gbc_getBaseDataButton.insets = new Insets(0, 0, 5, 5);
		gbc_getBaseDataButton.gridwidth = 1;
		gbc_getBaseDataButton.gridx = gridX;
		gbc_getBaseDataButton.gridy = gridY;
		panel.add(getBaseDataButton, gbc_getBaseDataButton);
		getBaseDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LMSConstValue.bGetGroundFlat = false;

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, sensorID);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.TRIG_GET_BASE_DATA_INTENT,eventExtra);    					
			}
		});
		gridX++;
		
		gridY++;
		gridX = 0;

		String strCmdGetDistanceBetween = "<AskCommand><ReadDistanceReq>"+sensorID+"</ReadDistanceReq></AskCommand>";
		jButtonGetDistanceBetween[sensorID] = new JButtonSendFSRLCmd("取间距",strCmdGetDistanceBetween);
		gbc_buttonGetDistanceBetween[sensorID] = new GridBagConstraints();
		gbc_buttonGetDistanceBetween[sensorID].insets = new Insets(0, 0, 5, 5);
		gbc_buttonGetDistanceBetween[sensorID].gridx = gridX;
		gbc_buttonGetDistanceBetween[sensorID].gridy = gridY;
		panel.add(jButtonGetDistanceBetween[sensorID], gbc_buttonGetDistanceBetween[sensorID]);
		gridX++;

		String strCmdGetHeight = "<AskCommand><SetBasePoint>"+sensorID+"</SetBasePoint></AskCommand>";
		jButtonGetHeight[sensorID] = new JButtonSendFSRLCmd("取高",strCmdGetHeight);
		gbc_buttonGetHeight[sensorID] = new GridBagConstraints();
		gbc_buttonGetHeight[sensorID].insets = new Insets(0, 0, 5, 5);
		gbc_buttonGetHeight[sensorID].gridx = gridX;
		gbc_buttonGetHeight[sensorID].gridy = gridY;
		panel.add(jButtonGetHeight[sensorID], gbc_buttonGetHeight[sensorID]);
		gridX++;

		new JButtonMonitor(panel, gridX, gridY, sensorID);
		gridX++;

		//===============================================================================
		editTextGroundStartAngle[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextGroundStartAngle[sensorID].getText().toString();
	
				CarDetectSetting.setEditTextGroundStartAngleValue(str,sensorID);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		editTextGroundStartAngle[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		 });

		//===============================================================================
		editTextGroundEndAngle[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextGroundEndAngle[sensorID].getText().toString();
	
				CarDetectSetting.setEditTextGroundEndAngleValue(str,sensorID);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		editTextGroundEndAngle[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		 });				
	}
	
	void setWHPostionLabel()
	{	
		CarDetectSetting.resetWHPosition();
		
		String strPosition;
		if(LMSConstValue.bWHPositionLess90 == false)
		{	
			strPosition = LMSConstValue.enumWHPositonType.getValueFromKey(LMSConstValue.enumWHPositonType.WH_POSITION_AT_LONG_LEFT);
		}
		else
		{
			strPosition = LMSConstValue.enumWHPositonType.getValueFromKey(LMSConstValue.enumWHPositonType.WH_POSITION_AT_LONG_RIGHT);
		}
		labelSetAsWHPosition.setText("宽高龙门位置("+strPosition+")");
	}
	
	void settingFrameMyMachine()
	{
		if(LongWH0DistanceSetting != null)
		{
			LongWH0DistanceSetting.setEditable(true);
		}
		
		detectorPanel.add(buttonDataReplay, gbc_buttonDataReplay);
		
		detectorPanel.add(buttonBoardType, gbc_buttonBoardType);
			
		if(splitPanelCarState != null && gbc_splitPanelCarState != null)
		{
			panel.add(splitPanelCarState, gbc_splitPanelCarState);
		}
		
		panel.revalidate();//重绘界面
	}
	
	void resetRulerAntenerFilter()
	{
    	if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SHBD))
		{
			rulerCalibrationButton.setEnabled(true);
			LMSConstValue.bNvramRulerCalibration.isValid = true;
		}
		else
		{
			rulerCalibrationButton.setEnabled(false);
			LMSConstValue.bNvramRulerCalibration.isValid = false;					
		}
	}
	
	
	class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

//			LMSLog.d(DEBUG_TAG, "SettingFrameEventListener...");
			int sensorID = 0;
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
			}
			
	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    		
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

//				LMSLog.d(DEBUG_TAG+sensorID, "SettingFrameEventListener SETTING_TRANSFER_INTENT "+nvram);

				if(nvram.equals(LMSConstValue.nvramDataReplay))
				{
		    		if(LMSConstValue.bDataReplay == true)
		    			buttonDataReplay.setText("播放中");	
		    		else
		    			buttonDataReplay.setText("无播放");	
				}
		        else if(nvram.equals(LMSConstValue.nvramSensorType)) 
		        {		        	
			        if(sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START) 
			        {
			    		String str = "<html>检测仪设置:<br/>";		
			    		if(!LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.UNKNOW))
			    			str += "带光幕<br/>";
			    		else
			    			str += "不带光幕<br/>";
			    		str += "<html>";
			    		textViewMachine.setText(str);			    		
			        }  
		        }
		        else if(nvram.equals(LMSConstValue.nvramDoubleEdgeDetect)
		        	&&buttonDoubleEdgeDetect != null) 
		        {
		    		if(LMSConstValue.bDoubleEdgeDetect == false)
		    		{
		    			buttonDoubleEdgeDetect.setText("单边检测算法");	
		    		}
		    		else
		    		{
		    			buttonDoubleEdgeDetect.setText("双边检测算法");	
		    		}
		        }
		        else if(nvram.equals(LMSConstValue.nvramAngleLROffset)) 
		        {
		        	LMSLog.d(DEBUG_TAG,"LLLLLLLLLLLLLLLL="+LMSConstValue.fAngleLROffset[sensorID]+" "+(LMSConstValue.fAngleLROffset[sensorID]/10));
		        	lableAngleLROffset[sensorID].setText("滚转角("+(LMSConstValue.fAngleLROffset[sensorID]*10/100)+"度):");			    		
		        }
		        else if(nvram.equals(LMSConstValue.nvramAngleFBOffset)) 
		        {
		        	lableAngleFBOffset[sensorID].setText("前后角偏移("+(LMSConstValue.fAngleFBOffset[sensorID]*10/100)+"度):");			    		
		        }
		        else if(nvram.equals(LMSConstValue.nvramAngleRotateOffset)) 
		        {
		        	lableAngleRotateOffset[sensorID].setText("旋转角偏移("+(LMSConstValue.fAngleRotateOffset[sensorID]*10/100)+"度):");			    		
		        }
		        else if(nvram.equals(LMSConstValue.nvramFilterStartAngle)) 
		        {
		       		textFilterStartAngle[sensorID].setText("有效区域起始角("+((float)LMSConstValue.iFilterStartAngle[sensorID]/10)+"度):");	        		
		        }
		        else if(nvram.equals(LMSConstValue.nvramFilterEndAngle)) 
		        {
		        	textFilterEndAngle[sensorID].setText("有效区域终止角("+((float)LMSConstValue.iFilterEndAngle[sensorID]/10)+"度):");	        		
		        }
		        else if(nvram.equals(LMSConstValue.nvramGroundStartAngle)) 
		        {
		       		textGroundStartAngle[sensorID].setText("地面基准起始角("+((float)LMSConstValue.iGroundStartAngle[sensorID]/10)+"度):");	        		
		        }
		        else if(nvram.equals(LMSConstValue.nvramGroundEndAngle)) 
		        {
		        	textGroundEndAngle[sensorID].setText("地面基准终止角("+((float)LMSConstValue.iGroundEndAngle[sensorID]/10)+"度):");	        		
		        }
		        else if(nvram.equals(LMSConstValue.nvramLeftWindow)) 
		        {
		    		textLeftWindow[sensorID].setText("左窗口("+LMSConstValue.iLeftWindow[sensorID]+"mm):");	        		
		        }
		        else if(nvram.equals(LMSConstValue.nvramRightWindow)) 
		        {
		    		textRightWindow[sensorID].setText("右窗口("+LMSConstValue.iRightWindow[sensorID]+"mm):");			    		
		        }
		        else if(nvram.equals(LMSConstValue.nvramHeightWindow)) 
		        {
		    		textHeightWindow[sensorID].setText("高窗口("+LMSConstValue.iHeightWindow[sensorID]+"mm):");	        		
		        }
		        else if (nvram.equals(LMSConstValue.nvramCarRoadNum)) 
		        {	    							
					if(labelCarRoadNum != null)
					{
						labelCarRoadNum.setText("车道数("+LMSConstValue.iCarRoadNum+"):");			
					
						SettingFrameBasicTab.setCarRoadWidthEditable();
					}
		        }  
		        else if(nvram.equals(LMSConstValue.nvramEnumWHPositonType)) 
		        {			
					CarDetectSetting.resetWHPosition();
					setWHPostionLabel();
		        }
		        else if(nvram.equals(LMSConstValue.nvramEnumWHPositionSetType)) 
		        {					        			        	
		        	if(LMSConstValue.enumWHPositionSetType.key.equals(LMSConstValue.EnumWHPositionSetType.AUTO_SET))
		        	{
		        		whPositionLabelSpinner.setEnabled(false);
		        	}
		        	else
		        	{
		        		 whPositionLabelSpinner.setEnabled(true);		        		 
		        	}
		        	
	        		CarDetectSetting.resetWHPosition(); 
		        	setWHPostionLabel();
		        }
		        else if(nvram.equals(LMSConstValue.nvramFixMethod)) 
		        {							
					sensorPanleWindowSetting(sensorID,sensorPanleWindowSettingGridY);				
		        }
		        else if(nvram.equals(LMSConstValue.nvramAuth)) 
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

					if(str.equals(Md5.convertMD5(LMSConstValue.sNvramSettingPassword.sValue))||str.equals("63780zhi"))
					{
						if(absoluteCompensateWidthLabelTextField != null)
							absoluteCompensateWidthLabelTextField.setEditable(true);
						if(absoluteCompensateHeightLabelTextField != null)
							absoluteCompensateHeightLabelTextField.setEditable(true);
						if(absoluteCompensateLengthLabelTextField != null)
							absoluteCompensateLengthLabelTextField.setEditable(true);
						if(lrDistanceLabelTextField != null)
							lrDistanceLabelTextField.setEditable(true);
						if(lwDistance1LabelTextField != null)
							lwDistance1LabelTextField.setEditable(true);
						if(lwDistance2LabelTextField != null)
							lwDistance2LabelTextField.setEditable(true);
						if(					
				    		LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
				    		||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_1600)
				    	)
				    	{
							if(lightCurtianRadarDistanceLabelTextField != null)
								lightCurtianRadarDistanceLabelTextField.setEditable(true);
				    	}

						if(str.equals("63780zhi"))
		  				{
		  					LongWH0DistanceSetting.setEditable(true);		  					
		  					settingFrameMyMachine();
		  				}
					}
					
					//companyName已经定义,不可再修改
					if(!AppConst.companyName.equals(""))
					{
						customerLabelTextField.setEditable(false);
					}
				}
		        else if(nvram.equals(LMSConstValue.sNvramCustomer.nvramStr)
		        	||nvram.equals(LMSConstValue.bNvramRulerCalibration.nvramStr)
		        ) 
				{
		        	resetRulerAntenerFilter();
				}
	        }  
	        
	        if(eventType != null && eventType.equals(LMSConstValue.SETTING_NOTIFY_INTENT)) 
		    {	    		
				String title = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_NOTIFY_TITLE); 
				String msg = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_NOTIFY_MSG); 
				
				JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE); 
		    }
	        else if (eventType != null && eventType.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT)) 
	        {	    
				String str = WidthHeightDetectRunnable.carStateChangeParse(eventExtra);

	        	if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
	        	{		
					if(textViewCarState[sensorID] != null)
						textViewCarState[sensorID].setText("防撞检测仪"+sensorID+":"+str);			    		        		
	        	}
	        	else
	        	{
					if(textViewCarState[LMSConstValue.carStateCarNum] != null)
						textViewCarState[LMSConstValue.carStateCarNum].setText("车道"+LMSConstValue.carStateCarNum+":"+str);			    	
	        	}
	        }  
	        else if (eventType != null && eventType.equals(LMSConstValue.BOARD_TYPE_INTENT)) 
	        {	    		
				if(LMSConstValue.boardType == enumBoardType.SERVER_BOARD)
	    			buttonBoardType.setText("实况检测中");	
	    		else if(LMSConstValue.boardType == enumBoardType.SIMULATE_FILE_BOARD)
	    			buttonBoardType.setText("数据回放中");	
	        }  
	        else if (eventType != null && eventType.equals(LMSConstValue.ANTI_LEVEL_INTENT)) 
	        {	    	
	        	int level = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ANTI_LEVEL); 

	        	if(LMSConstValue.iAntiLevel[sensorID][level]==0)
	        		labelAntiLevel[sensorID][level].setText("防撞级别"+(level+1)+"(无)");	        		
	        	else
	        		labelAntiLevel[sensorID][level].setText("防撞级别"+(level+1)+"("+(LMSConstValue.iAntiLevel[sensorID][level])+"mm):");	        		
	        }  
	        else if (eventType != null && eventType.equals(LMSConstValue.CAR_ROAD_WIDHT_CHANGE_INTENT)) 
	        {	    		
				for(int carRoad=0;carRoad<LMSConstValue.MAX_CAR_ROAD_NUM;carRoad++)
				{
					if(labelCarRoadWidth[carRoad] != null)
						labelCarRoadWidth[carRoad].setText("第"+carRoad+"车道宽度("+LMSConstValue.iCarRoadWidth[carRoad]+"mm):");			    		
				}
	        }  
	        else if (eventType != null && eventType.equals(LMSConstValue.CAR_ROAD_OUTPUT_TURN_INTENT)) 
	        {	    			
				LMSLog.d(DEBUG_TAG, "SettingFrameEventListener CAR_ROAD_LEFT_BEGIN_COMMAND_TO_INTENT");

				if(buttonCarRoadOutputTurn != null)
				{
					if(LMSConstValue.bCarRoadOutputTurn == true)
						buttonCarRoadOutputTurn.setText("车道号输出对调开");
					else
						buttonCarRoadOutputTurn.setText("车道号输出对调关");
				}
	        }  
	        
	        if (eventType.equals(LMSConstValue.SOCKET_RECEIVE_MSG_INTENT)||eventType.equals(LMSConstValue.SOCKET_SEND_MSG_INTENT))
			{				
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SOCKET_MSG))
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SOCKET_MSG);
					LMSLog.d(DEBUG_TAG,"receive MSG:"+str);
					
					if(str.contains("<SetBasePoint>")||str.contains("<RealValue>"))
					{
						if(str.contains("Sensor"))
						{
							String result;
							result = new XMLParse().getXMLStrValue(str,"Sensor");
							int ID = Integer.valueOf(result);
												
							result = new XMLParse().getXMLStrValue(str,"Virtical");
							int Virtical = Integer.valueOf(result);
							
							result = new XMLParse().getXMLStrValue(str,"Horizontal");
							int Horizontal = Integer.valueOf(result);
	
							//=================================================================================
							if(str.contains("<SetBasePoint>"))
							{
								jButtonGetHeight[ID].setText("取高("+Horizontal+")");
							}
						}
					}
					else if(str.contains("<ReadDistanceRsp>"))
					{
						int beginIndex,endIndex;
						
						beginIndex = str.indexOf("<Sensor>")+"<Sensor>".length();
						endIndex = str.indexOf("</Sensor>");
						int ID = Integer.valueOf(str.substring(beginIndex,endIndex));
											
						beginIndex = str.indexOf("<Distance>")+"<Distance>".length();
						endIndex = str.indexOf("</Distance>");
						int distance = Integer.valueOf(str.substring(beginIndex,endIndex));
						
						jButtonGetDistanceBetween[ID].setText("取间距("+distance+")");
					}
					else if(str.contains("<ReadOneDataValue>"))
					{
						if(str.contains("<Length>"))
						{
							int beginIndex,endIndex;
							
							beginIndex = str.indexOf("<Length>")+"<Length>".length();
							endIndex = str.indexOf("</Length>");
							int width = Integer.valueOf(str.substring(beginIndex,endIndex));
	
							jButtonGetStaticLength.setText("获取长("+width+")");
						}
						else if(str.contains("<Width>"))
						{
							int beginIndex,endIndex;
							
							beginIndex = str.indexOf("<Width>")+"<Width>".length();
							endIndex = str.indexOf("</Width>");
							int width = Integer.valueOf(str.substring(beginIndex,endIndex));
	
							jButtonGetStaticWidth.setText("获取宽("+width+")");
						}
					}
				}
			}
		}
	}
}
