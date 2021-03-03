package AppFrame.debugerManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import AppBase.appBase.AppConst;
import AppFrame.widgets.AuthWidget;
import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JButtonMonitor;
import AppFrame.widgets.JLabelComboBox;
import AppFrame.widgets.JLabelSpinner;
import AppFrame.widgets.JLabelSystemStatus;
import AppFrame.widgets.JLableSensorParameter;
import AppFrame.widgets.JSettingLabelTextField;
import AppFrame.widgets.SensorIPWidget;
import CarDetect.CarDetectSetting;

import SensorBase.LMSConstValue;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;
import SensorBase.LMSConstValue.enumBoardType;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventManager;

public class SettingFrameBasicTab extends SettingFrame{
	private final static String DEBUG_TAG="SettingFrameBasicTab";

	int ONE_LINE_LIGHT_PULSE_NUM = 3;
	public static EventListener eventListener;
	
	public SettingFrameBasicTab()
	{
        //=============================================================
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

        //=============================================================
		int sensorID = 0;
		for(sensorID=0;sensorID<LMSConstValue.RADAR_SENSOR_NUM;sensorID++)
		{	
			sensorPanel[sensorID] = new JPanel();
			sensorSettingPanelRight[sensorID] = new JPanel();
			
			for(int i=0;i<LMSConstValue.ANTI_LEVEL;i++)
			{
				labelAntiLevel[sensorID][i] = new JLabel();
				editTextAntiLevel[sensorID][i] = new JTextField();
			}
						
			textLeftWindow[sensorID] = new JLabel("左窗口("+LMSConstValue.iLeftWindow[sensorID]+"mm):");
			editTextLeftWindow[sensorID] = new JTextField();
			
			textRightWindow[sensorID] = new JLabel("右窗口("+LMSConstValue.iRightWindow[sensorID]+"mm):");
			editTextRightWindow[sensorID] = new JTextField();
	
			textHeightWindow[sensorID] = new JLabel("高窗口("+LMSConstValue.iHeightWindow[sensorID]+"mm):");
			editTextHeightWindow[sensorID] = new JTextField();

			textFilterStartAngle[sensorID] = new JLabel("有效区域起始角("+((float)LMSConstValue.iFilterStartAngle[sensorID]/10)+"度):");
			editTextFilterStartAngle[sensorID] = new JTextField();	
			textFilterEndAngle[sensorID] = new JLabel("有效区域终止角("+((float)LMSConstValue.iFilterEndAngle[sensorID]/10)+"度):");
			editTextFilterEndAngle[sensorID] = new JTextField();

			textGroundStartAngle[sensorID] = new JLabel("地面基准起始角("+((float)LMSConstValue.iGroundStartAngle[sensorID]/10)+"度):");
			editTextGroundStartAngle[sensorID] = new JTextField();	
			textGroundEndAngle[sensorID] = new JLabel("地面基准终止角("+((float)LMSConstValue.iGroundEndAngle[sensorID]/10)+"度):");
			editTextGroundEndAngle[sensorID] = new JTextField();

			lableAngleLROffset[sensorID] = new JLabel();
			textFieldAngleLROffset[sensorID] = new JTextField();
			lableAngleFBOffset[sensorID] = new JLabel();
			textFieldAngleFBOffset[sensorID] = new JTextField();
			lableAngleRotateOffset[sensorID] = new JLabel();
			textFieldAngleRotateOffset[sensorID] = new JTextField();
									
			splitPanelLMS[sensorID] = new JSplitPane();
			gbc_splitPanelLMS[sensorID] = new GridBagConstraints();
		}
				
		//------------------------------------------------------------		
		labelSetAsWHPosition = new JLabel();

		buttonDataReplay = new JButton();
		buttonBoardType = new JButton();
		
		//------------------------------------------------------------
		labelCarRoadWidth  = new JLabel[LMSConstValue.MAX_CAR_ROAD_NUM];
		editTextCarRoadWidth = new CarRoadWidthTextField[LMSConstValue.MAX_CAR_ROAD_NUM]; 
	}
	
	public JPanel createTab() {
		panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{156, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);

		//===============================================================================
		int splitPanelY = 0;
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
		{	
			splitPanelLMS[i] = new JSplitPane();
			gbc_splitPanelLMS[i] = new GridBagConstraints();
			gbc_splitPanelLMS[i].insets = new Insets(0, 0, 5, 0);
			gbc_splitPanelLMS[i].fill = GridBagConstraints.BOTH;
			gbc_splitPanelLMS[i].gridx = 0;
			gbc_splitPanelLMS[i].gridy = splitPanelY;
			panel.add(splitPanelLMS[i], gbc_splitPanelLMS[i]);
			
			createLMSSplitPanel(splitPanelLMS[i],i);
			splitPanelY++;
		}
		
		//==========================================================
		detectorPanel = new JPanel();
		//MACHINE SETTING
		splitPanelMachine = new JSplitPane();
		GridBagConstraints gbc_splitPanelMachine = new GridBagConstraints();
		gbc_splitPanelMachine.insets = new Insets(0, 0, 5, 0);
		gbc_splitPanelMachine.fill = GridBagConstraints.BOTH;
		gbc_splitPanelMachine.gridx = 0;
		gbc_splitPanelMachine.gridy = splitPanelY;
		panel.add(splitPanelMachine, gbc_splitPanelMachine);
		createMachineSplitPanel();
		
		splitPanelY++;
			
		if(LMSConstValue.defaultDetectType != enumDetectType.ANTI_COLLITION)
		{
			//MACHINE SETTING
			splitPanelCompensate = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			GridBagConstraints gbc_splitPanelCompensate = new GridBagConstraints();
			gbc_splitPanelCompensate.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanelCompensate.fill = GridBagConstraints.BOTH;
			gbc_splitPanelCompensate.gridx = 0;
			gbc_splitPanelCompensate.gridy = splitPanelY;
			panel.add(splitPanelCompensate, gbc_splitPanelCompensate);
			createCompensateSplitPanel();
			
			splitPanelY++;
		}
					
		//=====================================================================
		if(LMSConstValue.defaultDetectType != enumDetectType.UNKNOW_DETECT_TYPE)
		{
			//CAR STATE
			splitPanelCarState = new JSplitPane();
			gbc_splitPanelCarState = new GridBagConstraints();
			gbc_splitPanelCarState.insets = new Insets(0, 0, 5, 0);
			gbc_splitPanelCarState.fill = GridBagConstraints.BOTH;
			gbc_splitPanelCarState.gridx = 0;
			gbc_splitPanelCarState.gridy = splitPanelY;
			
			createCarStateSplitPanel();
	
			splitPanelY++;
		}
		
		//======================================================================
		if(LMSConstValue.isMyMachine())	
		{
			settingFrameMyMachine();
		}
		
		return panel;
    }
	
	JLabel createJLable(GridBagConstraints gbc,int gridX,int gridY,int gridWidth,JPanel panel,String str)
	{
		gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = gridWidth;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		
		JLabel comp = new JLabel(str);
		panel.add(comp, gbc);
		
		return comp;
	}

	JTextField createJTextField(GridBagConstraints gbc,int gridX,int gridY,int gridWidth,JPanel panel)
	{
		gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = gridWidth;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = gridX;
		gbc.gridy = gridY;
 
		JTextField comp = new JTextField();
		panel.add(comp, gbc);
		
		return comp;
	}
	
	void createSensorSettingSplitPanel(JSplitPane splitPanel,final int sensorID)
	{
		//--------------------------------------------------		
		GridBagLayout gbc_panel_left = new GridBagLayout();
		gbc_panel_left.columnWidths = new int[]{100, 60, 100, 60, 150, 100, 150, 60};
		gbc_panel_left.rowHeights = new int[]{29, 29, 15, 0, 0, 0};
		gbc_panel_left.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,  1.0,Double.MIN_VALUE};
		gbc_panel_left.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		sensorPanel[sensorID].setLayout(gbc_panel_left);
		splitPanel.setLeftComponent(sensorPanel[sensorID]);

		createSensorSettingSplitPanelLeft(sensorPanel[sensorID],sensorID);

		//--------------------------------------------------
		sensorSettingPanelRight[sensorID] = new JPanel();
		
		GridBagLayout gbc_panel_right = new GridBagLayout();
		gbc_panel_right.columnWidths = new int[]{100,40};
		gbc_panel_right.rowHeights = new int[]{29, 29, 15, 0, 0, 0};
		gbc_panel_right.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbc_panel_right.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		sensorSettingPanelRight[sensorID].setLayout(gbc_panel_right);
		splitPanel.setRightComponent(sensorSettingPanelRight[sensorID]);

		createSensorSettingSplitPanelRight(sensorSettingPanelRight[sensorID],sensorID);
	}
	
	void createSensorSettingSplitPanelLeft(JPanel panel,final int sensorID)
	{
		int gridX = 0;
		int gridY = 0;

		//=====================================================================
		systemStatusLabel[sensorID] = new JLabelSystemStatus(sensorID);
		GridBagConstraints gbc_systemStatusLabel = new GridBagConstraints();
		gbc_systemStatusLabel.fill = GridBagConstraints.BOTH;
		gbc_systemStatusLabel.anchor = GridBagConstraints.WEST;
		gbc_systemStatusLabel.gridwidth = 7;
		gbc_systemStatusLabel.insets = new Insets(0, 0, 5, 0);
		gbc_systemStatusLabel.gridx = gridX;
		gbc_systemStatusLabel.gridy = gridY;
		sensorPanel[sensorID].add(systemStatusLabel[sensorID], gbc_systemStatusLabel);
		gridX+=7;

		gridY++;
		
		//=====================================================================
		gridX = 0;
		sensorParameterLabel[sensorID] = new JLableSensorParameter(sensorID);
		GridBagConstraints gbc_sensorParameterLabel = new GridBagConstraints();
		gbc_sensorParameterLabel.fill = GridBagConstraints.BOTH;
		gbc_sensorParameterLabel.anchor = GridBagConstraints.WEST;
		gbc_sensorParameterLabel.gridwidth = 7;
		gbc_sensorParameterLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sensorParameterLabel.gridx = gridX;
		gbc_sensorParameterLabel.gridy = gridY;
		sensorPanel[sensorID].add(sensorParameterLabel[sensorID], gbc_sensorParameterLabel);
		gridX+=7;

		//========================================================================
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			null,
			sensorID,
			LMSConstValue.nvramFixMethod,LMSConstValue.fixMethod[sensorID]
		);
		gridX++;
		
		gridY++;
		
		//=====================================================================
		sensorPanleWindowSettingGridY = gridY;
		sensorPanleWindowSetting(sensorID,sensorPanleWindowSettingGridY);				
		gridY++;
		
		if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			//=====================================================================
			gridX = 0;
					
			for(int i=0;i<LMSConstValue.ANTI_LEVEL;i++)
			{
				JLabel labelAntiLevelTmp = new JLabel("防撞级别"+i+":");
				GridBagConstraints gbc_labelAntiLevel = new GridBagConstraints();	
				gbc_labelAntiLevel.fill = GridBagConstraints.BOTH;
				gbc_labelAntiLevel.anchor = GridBagConstraints.WEST;
				gbc_labelAntiLevel.gridwidth = 1;
				gbc_labelAntiLevel.insets = new Insets(0, 0, 5, 5);
				gbc_labelAntiLevel.gridx = gridX;
				gbc_labelAntiLevel.gridy = gridY;
				sensorPanel[sensorID].add(labelAntiLevelTmp, gbc_labelAntiLevel);
				labelAntiLevel[sensorID][i] = labelAntiLevelTmp;
				gridX++;
	
				AntiLevelTextField editTextAntiLevelTmp = new AntiLevelTextField(sensorID,i);
				GridBagConstraints gbc_editTextAntiLevel = new GridBagConstraints();
				gbc_editTextAntiLevel.fill = GridBagConstraints.BOTH;
				gbc_editTextAntiLevel.gridwidth = 1;
				gbc_editTextAntiLevel.insets = new Insets(0, 0, 5, 5);
				gbc_editTextAntiLevel.gridx = gridX;
				gbc_editTextAntiLevel.gridy = gridY;
				sensorPanel[sensorID].add(editTextAntiLevelTmp, gbc_editTextAntiLevel);
				editTextAntiLevel[sensorID][i] = editTextAntiLevelTmp;
				gridX++;
				
			}
		}
		else
		{
			//===============================================================		
			gridX = 0;
			JLabel labelAngleLROffsetTmp = new JLabel("滚转角("+(LMSConstValue.fAngleLROffset[sensorID]*10/100)+"度):");
			GridBagConstraints gbc_labelAngleLROffset = new GridBagConstraints();
			gbc_labelAngleLROffset.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelAngleLROffset.insets = new Insets(0, 0, 5, 5);
			gbc_labelAngleLROffset.gridx = gridX;
			gbc_labelAngleLROffset.gridy = gridY;
			sensorPanel[sensorID].add(labelAngleLROffsetTmp, gbc_labelAngleLROffset);
			lableAngleLROffset[sensorID] = labelAngleLROffsetTmp;
			gridX++;
			
			JTextField textFieldAngleLROffsetTmp = new JTextField();
			GridBagConstraints gbc_textFieldAngleLROffset = new GridBagConstraints();
			gbc_textFieldAngleLROffset.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldAngleLROffset.fill = GridBagConstraints.BOTH;
			gbc_textFieldAngleLROffset.gridx = gridX;
			gbc_textFieldAngleLROffset.gridy = gridY;
			sensorPanel[sensorID].add(textFieldAngleLROffsetTmp, gbc_textFieldAngleLROffset);
			textFieldAngleLROffset[sensorID] = textFieldAngleLROffsetTmp;
			gridX++;
			
			textFieldAngleLROffset[sensorID].addFocusListener(new FocusListener(){			
				public void focusLost(FocusEvent e) {
					String str = textFieldAngleLROffset[sensorID].getText().toString();
		
					CarDetectSetting.setTextFieldAngleLROffsetValue(str,sensorID);
				}
	
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}			
			});
			textFieldAngleLROffset[sensorID].addKeyListener(new KeyAdapter(){  
				public void keyTyped(KeyEvent e) {  
					int keyChar = e.getKeyChar();                 
				
					if(keyChar == KeyEvent.VK_MINUS || keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
	///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
						
					}else{  
						e.consume(); //关键，屏蔽掉非法输入   
						
			       }  
			     }  
			 });
			
			/*
			JLabel labelAngleFBOffsetTmp = new JLabel("前后角偏移");
			GridBagConstraints gbc_labelAngleFBOffset = new GridBagConstraints();
			gbc_labelAngleFBOffset.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelAngleFBOffset.insets = new Insets(0, 0, 5, 5);
			gbc_labelAngleFBOffset.gridx = gridX;
			gbc_labelAngleFBOffset.gridy = gridY;
			sensorPanel[sensorID].add(labelAngleFBOffsetTmp, gbc_labelAngleFBOffset);
			lableAngleFBOffset[sensorID] = labelAngleFBOffsetTmp;
			gridX++;
			
			JTextField textFieldAngleFBOffsetTmp = new JTextField();
			GridBagConstraints gbc_textFieldAngleFBOffset = new GridBagConstraints();
			gbc_textFieldAngleFBOffset.fill = GridBagConstraints.BOTH;
			gbc_textFieldAngleFBOffset.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldAngleFBOffset.gridx = gridX;
			gbc_textFieldAngleFBOffset.gridy = gridY;
			sensorPanel[sensorID].add(textFieldAngleFBOffsetTmp, gbc_textFieldAngleFBOffset);
			textFieldAngleFBOffset[sensorID] = textFieldAngleFBOffsetTmp;
			gridX++;
			
			textFieldAngleFBOffset[sensorID].addFocusListener(new FocusListener(){			
				public void focusLost(FocusEvent e) {
					String str = textFieldAngleFBOffset[sensorID].getText().toString();
		
					CarDetectSetting.setTextFieldAngleFBOffsetValue(str,sensorID);
				}
	
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}			
			});
			textFieldAngleFBOffset[sensorID].addKeyListener(new KeyAdapter(){  
				public void keyTyped(KeyEvent e) {  
					int keyChar = e.getKeyChar();                 
				
					if(keyChar == KeyEvent.VK_MINUS || keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
	///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
						
					}else{  
						e.consume(); //关键，屏蔽掉非法输入   
						
			       }  
			     }  
			 });
			
			JLabel labelAngleRotateOffsetTmp = new JLabel("旋转角偏移");
			GridBagConstraints gbc_labelAngleRotateOffset = new GridBagConstraints();
			gbc_labelAngleRotateOffset.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelAngleRotateOffset.insets = new Insets(0, 0, 5, 5);
			gbc_labelAngleRotateOffset.gridx = gridX;
			gbc_labelAngleRotateOffset.gridy = gridY;
			sensorPanel[sensorID].add(labelAngleRotateOffsetTmp, gbc_labelAngleRotateOffset);
			lableAngleRotateOffset[sensorID] = labelAngleRotateOffsetTmp;
			gridX++;
			
			JTextField textFieldAngleRotateOffsetTmp = new JTextField();
			GridBagConstraints gbc_textFieldAngleRotateOffset = new GridBagConstraints();
			gbc_textFieldAngleRotateOffset.insets = new Insets(0, 0, 5, 0);
			gbc_textFieldAngleRotateOffset.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldAngleRotateOffset.gridx = gridX;
			gbc_textFieldAngleRotateOffset.gridy = gridY;
			sensorPanel[sensorID].add(textFieldAngleRotateOffsetTmp, gbc_textFieldAngleRotateOffset);
			textFieldAngleRotateOffset[sensorID] = textFieldAngleRotateOffsetTmp;
			gridX++;
			
			textFieldAngleRotateOffset[sensorID].addFocusListener(new FocusListener(){			
				public void focusLost(FocusEvent e) {
					String str = textFieldAngleRotateOffset[sensorID].getText().toString();
		
					CarDetectSetting.setTextFieldAngleRotateOffsetValue(str,sensorID);
				}
	
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}			
			});		
			textFieldAngleRotateOffset[sensorID].addKeyListener(new KeyAdapter(){  
				public void keyTyped(KeyEvent e) {  
					int keyChar = e.getKeyChar();                 
				
					if(keyChar == KeyEvent.VK_MINUS || keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
	///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
						
					}else{  
						e.consume(); //关键，屏蔽掉非法输入   
						
			       }  
			     }  
			 });
			*/

			SensorIPWidget sensorIPWidget = new SensorIPWidget(sensorID,sensorPanel[sensorID],gridX,gridY);
		}

		gridY++;
		
		//=======================================================================
		editTextLeftWindow[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextLeftWindow[sensorID].getText().toString();
			
				CarDetectSetting.setEditTextLeftWindowValue(str,sensorID);
				
				//=========================================================================================
        		CarDetectSetting.resetWHPosition(); 
				setWHPostionLabel();
			}
	
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
						
			}			
		});
		editTextLeftWindow[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_MINUS||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
			
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
				}  
			}  
		});
		
		editTextRightWindow[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextRightWindow[sensorID].getText().toString();
	
				CarDetectSetting.setEditTextRightWindowValue(str,sensorID);
				
				//=========================================================================================
        		CarDetectSetting.resetWHPosition(); 
				setWHPostionLabel();
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		editTextRightWindow[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_MINUS||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		 });  
		
		editTextHeightWindow[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextHeightWindow[sensorID].getText().toString();
			
				CarDetectSetting.setEditTextHeightWindowValue(str,sensorID);
			}
	
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
						
			}			
		});
		editTextHeightWindow[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_MINUS||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
			
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
				}  
			}  
		});  
		
		//=========================================================================
		editTextFilterStartAngle[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextFilterStartAngle[sensorID].getText().toString();
	
				CarDetectSetting.setEditTextFilterStartAngleValue(str,sensorID);
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		editTextFilterStartAngle[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		 });

		editTextFilterEndAngle[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextFilterEndAngle[sensorID].getText().toString();
			
				CarDetectSetting.setEditTextFilterEndAngleValue(str,sensorID);
			}
	
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
						
			}			
		});
		editTextFilterEndAngle[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
			
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
				}  
			}  
		});  
	}
	
	void createLMSSplitPanel(JSplitPane splitPanel,final int sensorID)
	{		
		JLabel textViewLMS; 
		if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
			textViewLMS = new JLabel("<html>长<br/>雷<br/>达<html>");
		else
			textViewLMS = new JLabel("<html>宽<br/>高<br/>雷<br/>达<br/>"+(sensorID+1)+"<html>");
			
		splitPanel.setLeftComponent(textViewLMS);

		JSplitPane sensorSettingSplitPanel = new JSplitPane();
		splitPanel.setRightComponent(sensorSettingSplitPanel);			
		
		//--------------------------------------------------
		createSensorSettingSplitPanel(sensorSettingSplitPanel,sensorID);
	}
	
	void createMachineSplitPanel()
	{
		int gridX = 0;
		int gridY = 0;
		
		splitPanelMachine.setRightComponent(detectorPanel);
		
		textViewMachine = new JLabel();
		String str = "<html>检测仪设置:<br/>";		
		if(!LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.UNKNOW))
			str += "带光幕<br/>";
		else
			str += "不带光幕<br/>";
		str += "<html>";
		textViewMachine.setText(str);
		
		splitPanelMachine.setLeftComponent(textViewMachine);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		if(LMSConstValue.defaultDetectType == enumDetectType.UNKNOW_DETECT_TYPE)
		{
			gbl_panel_1.columnWidths = new int[]{100, 50, 100, 50, 100, 50,100, 50, 0, 0};
			gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
			gbl_panel_1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			detectorPanel.setLayout(gbl_panel_1);
		}
		else
		{
			gbl_panel_1.columnWidths = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
			gbl_panel_1.rowHeights = new int[]{30, 30, 30, 30};
			gbl_panel_1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{1.0, 1.0, 1.0,1.0, 1.0, Double.MIN_VALUE};
			detectorPanel.setLayout(gbl_panel_1);

			if(LMSConstValue.defaultDetectType != enumDetectType.ANTI_COLLITION)
			{
	
				//=========================================================================================
				if(LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED
					||LMSConstValue.defaultDetectType == enumDetectType.LM1)
				{	
					for(int i = 0;i<LMSConstValue.MAX_CAR_ROAD_NUM;i++)
					{
						
						labelCarRoadWidth[i] = new JLabel("第"+i+"车道宽度("+LMSConstValue.iCarRoadWidth[i]+"mm):");
						GridBagConstraints gbc_textViewCarRoadWidthTmp = new GridBagConstraints();
						gbc_textViewCarRoadWidthTmp.anchor = GridBagConstraints.WEST;
						gbc_textViewCarRoadWidthTmp.insets = new Insets(0, 0, 5, 5);
						gbc_textViewCarRoadWidthTmp.gridx = i*2;
						gbc_textViewCarRoadWidthTmp.gridy = gridY;
						detectorPanel.add(labelCarRoadWidth[i], gbc_textViewCarRoadWidthTmp);
						
						CarRoadWidthTextField editTextCarRoadWidthTmp = new CarRoadWidthTextField(i);
						GridBagConstraints gbc_editTextCarRoadWidthTmp = new GridBagConstraints();
						gbc_editTextCarRoadWidthTmp.fill = GridBagConstraints.HORIZONTAL;
						gbc_editTextCarRoadWidthTmp.insets = new Insets(0, 0, 5, 5);
						gbc_editTextCarRoadWidthTmp.gridx = i*2+1;
						gbc_editTextCarRoadWidthTmp.gridy = gridY;
						detectorPanel.add(editTextCarRoadWidthTmp, gbc_editTextCarRoadWidthTmp);
						editTextCarRoadWidth[i] = editTextCarRoadWidthTmp;
						
						setCarRoadWidthEditable();
					}
					gridY++;
					
					//=====================================================================
					gridX = 0;
					labelCarRoadNum = new JLabel("车道数("+LMSConstValue.iCarRoadNum+"):");
					GridBagConstraints gbc_labelCarRoadNum = new GridBagConstraints();
					gbc_labelCarRoadNum.anchor = GridBagConstraints.EAST;
					gbc_labelCarRoadNum.insets = new Insets(0, 0, 5, 5);
					gbc_labelCarRoadNum.gridx = gridX;
					gbc_labelCarRoadNum.gridy = gridY;
					detectorPanel.add(labelCarRoadNum, gbc_labelCarRoadNum);
					gridX++;
					
					editTextCarRoadNum = new JTextField();
					GridBagConstraints gbc_editTextCarRoadNum = new GridBagConstraints();
					gbc_editTextCarRoadNum.fill = GridBagConstraints.HORIZONTAL;
					gbc_editTextCarRoadNum.insets = new Insets(0, 0, 5, 5);
					gbc_editTextCarRoadNum.gridx = gridX;
					gbc_editTextCarRoadNum.gridy = gridY;
					detectorPanel.add(editTextCarRoadNum, gbc_editTextCarRoadNum);
					editTextCarRoadNum.addFocusListener(new FocusListener(){			
						public void focusLost(FocusEvent e) {
							String str = editTextCarRoadNum.getText().toString();
				
							CarDetectSetting.setCarRoadNum(str);
						}
			
						@Override
						public void focusGained(FocusEvent arg0) {
							// TODO Auto-generated method stub
							
						}			
					});
					gridX++;	
					
					buttonCarRoadOutputTurn = new JButton("车道从左开始");
					GridBagConstraints gbc_buttonCarRoadLeftBegin = new GridBagConstraints();
					gbc_buttonCarRoadLeftBegin.insets = new Insets(0, 0, 5, 5);
					gbc_buttonCarRoadLeftBegin.gridx = gridX;
					gbc_buttonCarRoadLeftBegin.gridy = gridY;
					detectorPanel.add(buttonCarRoadOutputTurn, gbc_buttonCarRoadLeftBegin);
					buttonCarRoadOutputTurn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							LMSLog.d(DEBUG_TAG,"buttonCarRoadLeftBegin"+LMSConstValue.bCarRoadOutputTurn);
		
							LMSConstValue.bCarRoadOutputTurn = !LMSConstValue.bCarRoadOutputTurn;
								
							LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_ROAD_OUTPUT_TURN_INTENT);    					
						}
					});
			
					gridY++;
				}
				
				if(LMSConstValue.defaultDetectType == enumDetectType.WH2
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME
				)
				{
					lrDistanceLabelTextField = new JSettingLabelTextField(
						detectorPanel, 
						gridX, gridY, 1, 1,
						"左右间距", "mm", true,
						false,
						true,
						LMSConstValue.iNvramLRDistance, -1, -1
					);
					lrDistanceLabelTextField.setEditable(false);
					lrDistanceLabelTextField.setMD5(true);
					lrDistanceLabelTextField.setPositiveKeyNumOnly();
					gridX+=2;
				}
				
				if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
				{
					lwDistance1LabelTextField = new JSettingLabelTextField(
						detectorPanel, 
						gridX, gridY, 1, 1,
						"宽高雷达1与长距离", "mm", true,
						false,
						true,
						LMSConstValue.iNvramLWDistance, -1, -1
					);
					lwDistance1LabelTextField.setEditable(false);
					lwDistance1LabelTextField.setMD5(true);
					lwDistance1LabelTextField.setPositiveKeyNumOnly();
					gridX+=2;	
					
					if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
						||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
					{
						lwDistance2LabelTextField = new JSettingLabelTextField(
							detectorPanel, 
							gridX, gridY, 1, 1,
							"宽高雷达2与长距离", "mm", true,
							false,
							true,
							LMSConstValue.iNvramLWDistance2, -1, -1
						);
						lwDistance2LabelTextField.setEditable(false);
						lwDistance2LabelTextField.setPositiveKeyNumOnly();
						gridX+=2;	
					}
					
					lightCurtianRadarDistanceLabelTextField = new JSettingLabelTextField(
						detectorPanel, 
						gridX, gridY, 1, 1,
						"光栅与长距离", "mm", true,
						false,
						true,
						LMSConstValue.iNvramLightCurtianLongDistance, -1, -1
					);
					lightCurtianRadarDistanceLabelTextField.setEditable(false);
					lightCurtianRadarDistanceLabelTextField.setPositiveKeyNumOnly();
					gridX+=2;	
				}
				
				new JButtonMonitor(detectorPanel, gridX, gridY, -1);
				gridX++;
	
				gridY++;
				gridX = 0;
				//=====================================================================
				if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
					||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
				{
					//------------------------------------------------------
					GridBagConstraints gbc_labelSetAsWHPosition = new GridBagConstraints();
					labelSetAsWHPosition.setText("宽高头位置");
					gbc_labelSetAsWHPosition.insets = new Insets(0, 0, 5, 5);
					gbc_labelSetAsWHPosition.gridwidth = 2;
					gbc_labelSetAsWHPosition.gridx = gridX;
					gbc_labelSetAsWHPosition.gridy = gridY;
					detectorPanel.add(labelSetAsWHPosition, gbc_labelSetAsWHPosition);
					gridX+=2;

					autoSetRadioButton = new JRadioButton("自动设置");
					GridBagConstraints gbc_autoSetRadioButton = new GridBagConstraints();
					gbc_autoSetRadioButton.insets = new Insets(0, 0, 5, 5);
					gbc_autoSetRadioButton.gridx = gridX;
					gbc_autoSetRadioButton.gridy = gridY;
					detectorPanel.add(autoSetRadioButton,gbc_autoSetRadioButton);
					autoSetRadioButton.addItemListener(new ItemListener() {
				         public void itemStateChanged(ItemEvent e) { 
				        	 JRadioButton jrb=(JRadioButton)e.getSource();

				        	 if(jrb.isSelected()){		
					        	 LMSConstValue.enumWHPositionSetType.key = LMSConstValue.EnumWHPositionSetType.AUTO_SET;
				        	 				        	
				        		 HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				        		 eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramEnumWHPositionSetType);
				        		 eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.enumWHPositionSetType.key);
				        		 eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				        		 LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);				        		 
				        	 }
				         }           
					});
					gridX++;
					
			        handSetRadioButton = new JRadioButton("手动设置");
					GridBagConstraints gbc_handSetRadioButton = new GridBagConstraints();
					gbc_handSetRadioButton.insets = new Insets(0, 0, 5, 5);
					gbc_handSetRadioButton.gridx = gridX;
					gbc_handSetRadioButton.gridy = gridY;
			        detectorPanel.add(handSetRadioButton,gbc_handSetRadioButton);
			        handSetRadioButton.addItemListener(new ItemListener() {
				         public void itemStateChanged(ItemEvent e) { 
				        	 JRadioButton jrb=(JRadioButton)e.getSource();

				        	 if(jrb.isSelected()){
					        	 LMSConstValue.enumWHPositionSetType.key = LMSConstValue.EnumWHPositionSetType.HAND_SET;
 	 				        	
				        		 HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				        		 eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramEnumWHPositionSetType);
				        		 eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.enumWHPositionSetType.key);
				        		 eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				        		 LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);				        		 
				        	 }
				         }           
					});
					gridX++;

			        buttonGroup = new ButtonGroup();// 创建单选按钮组
			        buttonGroup.add(autoSetRadioButton);// 将radioButton1增加到单选按钮组中
			        buttonGroup.add(handSetRadioButton);// 将radioButton2增加到单选按钮组中

			        whPositionLabelSpinner = new JLabelSpinner(
			        	detectorPanel,
						gridX,gridY,
						null,
						-1,
						LMSConstValue.nvramEnumWHPositonType,LMSConstValue.enumWHPositonType
					);
					gridX++;
					
					//=======================================================================
					//初始化
					if(LMSConstValue.enumWHPositionSetType.key.equals(LMSConstValue.EnumWHPositionSetType.AUTO_SET))
		        		buttonGroup.setSelected(autoSetRadioButton.getModel(), true);
		        	else
		        		buttonGroup.setSelected(handSetRadioButton.getModel(), true);	
		        	setWHPostionLabel();
		        	
					//=======================================================================
		        	LongWH0DistanceSetting = new JSettingLabelTextField(
						detectorPanel, 
						gridX, gridY, 1, 1,
						"中线偏移量", "mm", true,
						false,
						true,
						LMSConstValue.iNvramLongWH0istance, -1, -1
					);
					LongWH0DistanceSetting.setEditable(false);
					gridX+=2;	
				}
				
				//=========================================================================
				gridX = 0;
				gridY++;
				
				JButton getGroundFlatButton = new JButton("判断地面是否平整");
				GridBagConstraints gbc_getGroundFlatButton = new GridBagConstraints();
				gbc_getGroundFlatButton.insets = new Insets(0, 0, 5, 5);
				gbc_getGroundFlatButton.gridwidth = 2;
				gbc_getGroundFlatButton.gridx = gridX;
				gbc_getGroundFlatButton.gridy = gridY;
				detectorPanel.add(getGroundFlatButton, gbc_getGroundFlatButton);
				getGroundFlatButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						LMSConstValue.bGetGroundFlat = true;

						for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
						{
							HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
							eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, i);
							LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.TRIG_GET_BASE_DATA_INTENT,eventExtra);    					
						}
					}
				});
				gridX+=2;
		
				GridBagConstraints gbc_getWidthButton = new GridBagConstraints();
				gbc_getWidthButton.fill = GridBagConstraints.BOTH;
				gbc_getWidthButton.insets = new Insets(0, 0, 5, 5);
				gbc_getWidthButton.gridwidth = 2;
				gbc_getWidthButton.gridx = gridX;
				gbc_getWidthButton.gridy = gridY;
				detectorPanel.add(jButtonGetStaticWidth, gbc_getWidthButton);
				gridX+=2;

				GridBagConstraints gbc_getLongButton = new GridBagConstraints();
				gbc_getLongButton.fill = GridBagConstraints.BOTH;
				gbc_getLongButton.insets = new Insets(0, 0, 5, 5);
				gbc_getLongButton.gridwidth = 2;
				gbc_getLongButton.gridx = gridX;
				gbc_getLongButton.gridy = gridY;
				detectorPanel.add(jButtonGetStaticLength, gbc_getLongButton);
				gridX+=2;
								
				//=====================================================================
				new AuthWidget(detectorPanel, gridX, gridY, "调试码");
								
				//=======================================================================
				gridX = 0;
				gridY++;

				customerLabelTextField = new JSettingLabelTextField(
					detectorPanel, 
					gridX, gridY, 1, 1,
					"客户编码", "", true,
					false,
					true,
					LMSConstValue.sNvramCustomer, -1, -1
				);
		        if(!AppConst.companyName.equals(""))
		        {
		        	customerLabelTextField.setEditable(false);
		        }
				gridX+=2;
		    	
				//----------------------------------------------------------
				buttonDoubleEdgeDetect = new JButton();
	    		if(LMSConstValue.bDoubleEdgeDetect == false)
	    		{
	    			buttonDoubleEdgeDetect.setText("单边检测算法");	
	    		}
	    		else
	    		{
	    			buttonDoubleEdgeDetect.setText("双边检测算法");	
	    		}
				GridBagConstraints gbc_buttonDoubleEdgeDetect = new GridBagConstraints();
				gbc_buttonDoubleEdgeDetect.insets = new Insets(0, 0, 5, 5);
				gbc_buttonDoubleEdgeDetect.gridx = gridX;
				gbc_buttonDoubleEdgeDetect.gridy = gridY;
				detectorPanel.add(buttonDoubleEdgeDetect, gbc_buttonDoubleEdgeDetect);
				buttonDoubleEdgeDetect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
		
						LMSConstValue.bDoubleEdgeDetect = !LMSConstValue.bDoubleEdgeDetect;
						
						HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramDoubleEdgeDetect);
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bDoubleEdgeDetect);
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
						LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
					}
				});
				gridX++;
				
				rulerCalibrationButton = new JButtonBoolean(
					detectorPanel,
					gridX,gridY,1,
					"尺子校准(打开)",
					"尺子校准(关闭)",
					LMSConstValue.bNvramRulerCalibration,
					-1
				);
				gridX++;
				
				resetRulerAntenerFilter();
			}
		}
			
		gridY++;
	
		{
			gridX = 0;
			
			if(LMSConstValue.boardType == enumBoardType.SIMULATE_FILE_BOARD)
			{				
				new JSettingLabelTextField(
					detectorPanel, 
					gridX, gridY, 1, 1,
					"模拟间隔", "ms", true,
					false,
					true,
					LMSConstValue.iNvramSimulateInteval, -1, -1
				).setRange(true, 1, 1000);
				gridX+=2;
				
				new JSettingLabelTextField(
					detectorPanel, 
					gridX, gridY, 1, 1,
					"起始行", "", true,
					false,
					true,
					LMSConstValue.iNvramReplayLine, -1, -1
				);
				gridX+=2;
			}
			
    		if(LMSConstValue.bDataReplay == true)
    			buttonDataReplay.setText("播放中");	
    		else
    			buttonDataReplay.setText("无播放");	
			gbc_buttonDataReplay = new GridBagConstraints();
			gbc_buttonDataReplay.insets = new Insets(0, 0, 5, 5);
			gbc_buttonDataReplay.gridx = gridX;
			gbc_buttonDataReplay.gridy = gridY;
			gridX++;
						
			if(LMSConstValue.boardType == enumBoardType.SIMULATE_FILE_BOARD)
				buttonBoardType.setText("数据回放中");
			else
				buttonBoardType.setText("实况检测中");				
			gbc_buttonBoardType = new GridBagConstraints();
			gbc_buttonBoardType.insets = new Insets(0, 0, 5, 5);
			gbc_buttonBoardType.gridx = gridX;
			gbc_buttonBoardType.gridy = gridY;
			gridX++;

			gridY++;
		}
			
		//=======================================================================		
		buttonDataReplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LMSLog.d(DEBUG_TAG,"buttonDataReplay");

				LMSConstValue.bDataReplay = !LMSConstValue.bDataReplay;
					
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.nvramDataReplay);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.bDataReplay);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT,eventExtra);    					
			}
		});

		buttonBoardType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LMSLog.d(DEBUG_TAG,"buttonBoardType");

				if(LMSConstValue.boardType == enumBoardType.SERVER_BOARD)
					LMSConstValue.boardType = enumBoardType.SIMULATE_FILE_BOARD;
				else if(LMSConstValue.boardType == enumBoardType.SIMULATE_FILE_BOARD)
					LMSConstValue.boardType = enumBoardType.SERVER_BOARD;
				
				LMSEvent event = new LMSEvent(this, LMSConstValue.BOARD_TYPE_INTENT);
				LMSEventManager.notifyListeners(event);
			}
		});
	}
	
	JSplitPane createAbsoluteCompensateSplitPanel()
	{
		JSplitPane splitPanelAbsoluteCompensate = new JSplitPane();
		
		int gridX = 0;
		int gridY = 0;
		
		JLabel textViewCompensate = new JLabel("绝对补偿");
		splitPanelAbsoluteCompensate.setLeftComponent(textViewCompensate);

		JPanel compensatePanel = new JPanel();
		splitPanelAbsoluteCompensate.setRightComponent(compensatePanel);
		
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{120, 100, 120, 100, 120, 100};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		compensatePanel.setLayout(gbl_panel_1);
		
		//======================================================================
		gridX = 0;

		absoluteCompensateWidthLabelTextField = new JSettingLabelTextField(
			compensatePanel, 
			gridX, gridY, 1, 1,
			"动态宽绝对补偿", "mm", true,
			false,
			true,
			LMSConstValue.iNvramWidthOutputCompensate, -1, -1
		);
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_GDHS))
		{
			absoluteCompensateWidthLabelTextField.setRange(true, -150, 150);
		}
		absoluteCompensateWidthLabelTextField.setMD5(true);
		absoluteCompensateWidthLabelTextField.setEditable(false);
		gridX+=2;
		
		absoluteCompensateHeightLabelTextField = new JSettingLabelTextField(
			compensatePanel, 
			gridX, gridY, 1, 1,
			"动态高绝对补偿", "mm", true,
			false,
			true,
			LMSConstValue.iNvramHeightOutputCompensate, -1, -1
		);
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_GDHS))
		{
			absoluteCompensateHeightLabelTextField.setRange(true, -150, 150);
		}
		absoluteCompensateHeightLabelTextField.setMD5(true);
		absoluteCompensateHeightLabelTextField.setEditable(false);
		gridX+=2;
		
		absoluteCompensateLengthLabelTextField = new JSettingLabelTextField(
			compensatePanel, 
			gridX, gridY, 1, 1,
			"动态长绝对补偿", "mm", true,
			false,
			true,
			LMSConstValue.iNvramLengthOutputCompensate, -1, -1
		);
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_GDHS))
		{
			absoluteCompensateLengthLabelTextField.setRange(true, -150, 150);
		}
		absoluteCompensateLengthLabelTextField.setMD5(true);
		absoluteCompensateLengthLabelTextField.setEditable(false);
		gridX+=2;
				
		return splitPanelAbsoluteCompensate;
	}
		
	void createCompensateSplitPanel()
	{
		//===========================================================================		
		splitPanelCompensate.setLeftComponent(createAbsoluteCompensateSplitPanel());
	}
	
	void createCarStateSplitPanel()
	{
		//-------------------------------------------------------------------
		if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			labelCarState = new JLabel("防撞信息:");			
		}
		else
		{
			labelCarState = new JLabel("车道信息:");
		}
		splitPanelCarState.setLeftComponent(labelCarState);
		
		//-------------------------------------------------------------------		
		carStatePanel = new JPanel();
		splitPanelCarState.setRightComponent(carStatePanel);
		GridBagLayout gbl_carStatePanel = new GridBagLayout();
		gbl_carStatePanel.columnWidths = new int[]{0};
		gbl_carStatePanel.rowHeights = new int[] {0, 0, 0};
		gbl_carStatePanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_carStatePanel.rowWeights = new double[]{Double.MIN_VALUE, 0.0, 0.0};
		carStatePanel.setLayout(gbl_carStatePanel);
		
		int i = 0;
		int gridY = 0;
		for(i=0;i<LMSConstValue.MAX_CAR_ROAD_NUM;i++)
		{
			textViewCarState[i] = new JLabel();
			if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
			{
				textViewCarState[i].setText("防撞检测仪"+i+":");				
			}
			else
			{
				textViewCarState[i].setText("车道 "+i+":");				
			}
			GridBagConstraints gbc_textViewCarState = new GridBagConstraints();
			gbc_textViewCarState.fill = GridBagConstraints.BOTH;
			gbc_textViewCarState.anchor = GridBagConstraints.WEST;
			gbc_textViewCarState.gridwidth = 4;
			gbc_textViewCarState.insets = new Insets(0, 0, 5, 5);
			gbc_textViewCarState.gridx = 0;
			gbc_textViewCarState.gridy = gridY;
			carStatePanel.add(textViewCarState[i], gbc_textViewCarState);
			
			gridY++;
		}
	}
	
	public static void setCarRoadWidthEditable()
	{
		for(int i = 0;i<LMSConstValue.MAX_CAR_ROAD_NUM;i++)
		{
			if(editTextCarRoadWidth[i] != null)
			{
				if(i<LMSConstValue.iCarRoadNum-1)
					editTextCarRoadWidth[i].setEditable(true);
				else
					editTextCarRoadWidth[i].setEditable(false);
			}
		}
	}
		
	class CarRoadWidthTextField extends JTextField implements FocusListener 
	{
		private int ID;
		public CarRoadWidthTextField(final int ID) {
			 this.ID = ID;
			 super.addFocusListener(this);
		}
		
		@Override
		public void focusLost(FocusEvent e) {
			LMSLog.d(DEBUG_TAG,"CarRoad["+ID+"]:"+this.getText());
			CarDetectSetting.setEditTextCarRoadWidthValue(ID,this.getText());
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void keyTyped(KeyEvent e) {  
			int keyChar = e.getKeyChar();                 
		
			if(keyChar == KeyEvent.VK_MINUS||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
				
			}else{  
				e.consume(); //关键，屏蔽掉非法输入   
				
	       }  
	     }  
	}
	
	class AntiLevelTextField extends JTextField implements FocusListener 
	{
		private int LEVEL;
		private int SENSOR_ID;
		public AntiLevelTextField(final int sensorID,final int LEVEL) {
			 this.LEVEL = LEVEL;
			 this.SENSOR_ID = sensorID;
			 super.addFocusListener(this);
		}
		
		@Override
		public void focusLost(FocusEvent e) {
			CarDetectSetting.setEditTextAntiLevelValue(SENSOR_ID,LEVEL,this.getText());
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void keyTyped(KeyEvent e) {  
			int keyChar = e.getKeyChar();                 
		
			if(keyChar == KeyEvent.VK_MINUS||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
///				LMSConstValue.iRightWindow = editTextRightWindow.getText().toString();
				
			}else{  
				e.consume(); //关键，屏蔽掉非法输入   
				
	       }  
	     }  
	}
}
