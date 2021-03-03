package AppFrame.debugerManager;

import http.WebService.XMLParse;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import AppFrame.widgets.JButtonSendFSRLCmd;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class SettingFrameFsrlProtocol {
	private final static String DEBUG_TAG="SettingFrameFsrlProtocol";

	JPanel panel;
	private static EventListener eventListener;
	
	JButton setSystemStatusButton;
	JTextArea receiveMsgTextArea;
	JTextField sendMsgTextField;	
	JTextField longSensorPortTextField;
	JTextField leftSensorPortTextField;
	JTextField rightSensorPortTextField;
	JTextField carTypeTextField;

	JButtonSendFSRLCmd jButtonGetSensorBase[] = new JButtonSendFSRLCmd[LMSConstValue.RADAR_SENSOR_NUM];
	JButtonSendFSRLCmd jButtonGetSensorReal[] = new JButtonSendFSRLCmd[LMSConstValue.RADAR_SENSOR_NUM];

	JButtonSendFSRLCmd jButtonGetStaticLength = new JButtonSendFSRLCmd("静态获取两龙门间距",LMSConstValue.strFSRLGetLength);
	JButtonSendFSRLCmd jButtonGetStaticWidth = new JButtonSendFSRLCmd("静态获取宽",LMSConstValue.strFSRLGetWidth);
	
	
	public JPanel createTab() {
        //=============================================================
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

        //=============================================================
		panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100,100,100,100,100,100};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
		
		JLabel sendMsgLabel = new JLabel("send:");
		GridBagConstraints gbc_sendMsgLabel = new GridBagConstraints();
		gbc_sendMsgLabel.fill = GridBagConstraints.BOTH;
		gbc_sendMsgLabel.anchor = GridBagConstraints.WEST;
		gbc_sendMsgLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sendMsgLabel.gridwidth = 1;
		gbc_sendMsgLabel.gridx = gridX;
		gbc_sendMsgLabel.gridy = gridY;
		panel.add(sendMsgLabel, gbc_sendMsgLabel);
		gridX++;
		
		sendMsgTextField = new JTextField();
		GridBagConstraints gbc_sendMsgTextField = new GridBagConstraints();
		gbc_sendMsgTextField.fill = GridBagConstraints.BOTH;
		gbc_sendMsgTextField.anchor = GridBagConstraints.WEST;
		gbc_sendMsgTextField.insets = new Insets(0, 0, 5, 5);
		gbc_sendMsgTextField.gridwidth = 5;
		gbc_sendMsgTextField.gridx = gridX;
		gbc_sendMsgTextField.gridy = gridY;
		panel.add(sendMsgTextField, gbc_sendMsgTextField);

		gridY++;
		gridX = 0;
		
		//==============================================================
		JLabel receiveMsgLabel = new JLabel("receive:");
		GridBagConstraints gbc_receiveMsgLabel = new GridBagConstraints();
		gbc_receiveMsgLabel.fill = GridBagConstraints.BOTH;
		gbc_receiveMsgLabel.anchor = GridBagConstraints.WEST;
		gbc_receiveMsgLabel.insets = new Insets(0, 0, 5, 5);
		gbc_receiveMsgLabel.gridwidth = 1;
		gbc_receiveMsgLabel.gridx = gridX;
		gbc_receiveMsgLabel.gridy = gridY;
		panel.add(receiveMsgLabel, gbc_receiveMsgLabel);
		gridX++;
		
		receiveMsgTextArea = new JTextArea();
		GridBagConstraints gbc_receiveMsgTextArea = new GridBagConstraints();
		gbc_receiveMsgTextArea.fill = GridBagConstraints.BOTH;
		gbc_receiveMsgTextArea.anchor = GridBagConstraints.WEST;
		gbc_receiveMsgTextArea.insets = new Insets(0, 0, 5, 5);
		gbc_receiveMsgTextArea.gridwidth = 5;
		gbc_receiveMsgTextArea.gridx = gridX;
		gbc_receiveMsgTextArea.gridy = gridY;
		receiveMsgTextArea.setLineWrap(true);        //激活自动换行功能 
		receiveMsgTextArea.setWrapStyleWord(true);            // 激活断行不断字功能
		panel.add(receiveMsgTextArea, gbc_receiveMsgTextArea);
		
		JScrollPane scrollPane = new JScrollPane(receiveMsgTextArea);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = gridX;
		gbc_scrollPane.gridy = gridY;
		gbc_scrollPane.gridwidth = gbc_receiveMsgTextArea.gridwidth;
		panel.add(scrollPane, gbc_scrollPane);
		
		//==============================================================
		gridY++;
		gridX = 0;
		
		setSystemStatusButton = new JButton();
		if(LMSConstValue.bNormalMode == false)
			setSystemStatusButton.setText("待命状态0");
		else 
			setSystemStatusButton.setText("测量状态1");
		GridBagConstraints gbc_setSystemStatusButton = new GridBagConstraints();
		gbc_setSystemStatusButton.fill = GridBagConstraints.BOTH;
		gbc_setSystemStatusButton.anchor = GridBagConstraints.WEST;
		gbc_setSystemStatusButton.insets = new Insets(0, 0, 5, 5);
		gbc_setSystemStatusButton.gridx = gridX;
		gbc_setSystemStatusButton.gridy = gridY;
		panel.add(setSystemStatusButton, gbc_setSystemStatusButton);
		setSystemStatusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				String str;
				if(LMSConstValue.bNormalMode == true)
				{
					LMSConstValue.bNormalMode = false;
					str = "<AskCommand><SetSystemMode>0</SetSystemMode></AskCommand>";
					setSystemStatusButton.setText("待命状态0");
				}
				else
				{
					LMSConstValue.bNormalMode = true;
					str = "<AskCommand><SetSystemMode>1</SetSystemMode></AskCommand>";
					setSystemStatusButton.setText("测量状态1");
				}
								
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, str);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    					
			}
		});
		gridX++;
		
		JButton readResultButton = new JButton("读取结果数据");
		GridBagConstraints gbc_readResultButton = new GridBagConstraints();
		gbc_readResultButton.fill = GridBagConstraints.BOTH;
		gbc_readResultButton.anchor = GridBagConstraints.WEST;
		gbc_readResultButton.insets = new Insets(0, 0, 5, 5);
		gbc_readResultButton.gridx = gridX;
		gbc_readResultButton.gridy = gridY;
		panel.add(readResultButton, gbc_readResultButton);
		readResultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = "<AskCommand>ReadTestResult</AskCommand>";
								
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, str);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    					
			}
		});
		gridX++;
		
		JButton readRealTimeSpeedButton = new JButton("读取实时速度");
		GridBagConstraints gbc_readRealTimeSpeedButton = new GridBagConstraints();
		gbc_readRealTimeSpeedButton.fill = GridBagConstraints.BOTH;
		gbc_readRealTimeSpeedButton.anchor = GridBagConstraints.WEST;
		gbc_readRealTimeSpeedButton.insets = new Insets(0, 0, 5, 5);
		gbc_readRealTimeSpeedButton.gridwidth = 1;
		gbc_readRealTimeSpeedButton.gridx = gridX;
		gbc_readRealTimeSpeedButton.gridy = gridY;
		panel.add(readRealTimeSpeedButton, gbc_readRealTimeSpeedButton);
		readRealTimeSpeedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = "<AskCommand>ReadSpeed</AskCommand>";
								
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, str);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
			}
		});
		gridX++;
		
		JButton readLengthDistanceButton = new JButton("读取距离");
		GridBagConstraints gbc_readLengthDistanceButton = new GridBagConstraints();
		gbc_readLengthDistanceButton.fill = GridBagConstraints.BOTH;
		gbc_readLengthDistanceButton.anchor = GridBagConstraints.WEST;
		gbc_readLengthDistanceButton.insets = new Insets(0, 0, 5, 5);
		gbc_readLengthDistanceButton.gridwidth = 1;
		gbc_readLengthDistanceButton.gridx = gridX;
		gbc_readLengthDistanceButton.gridy = gridY;
		panel.add(readLengthDistanceButton, gbc_readLengthDistanceButton);
		readLengthDistanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = "<AskCommand>ReadLengthDistance</AskCommand>";
								
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, str);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
			}
		});
		
		//==========================================================================================
		gridY++;
		gridX = 0;
		
		JLabel longSensorPortLabel = new JLabel("长传感器串口号");
		GridBagConstraints gbc_longSensorPortLabel = new GridBagConstraints();
		gbc_longSensorPortLabel.fill = GridBagConstraints.BOTH;
		gbc_longSensorPortLabel.anchor = GridBagConstraints.WEST;
		gbc_longSensorPortLabel.insets = new Insets(0, 0, 5, 5);
		gbc_longSensorPortLabel.gridwidth = 1;
		gbc_longSensorPortLabel.gridx = gridX;
		gbc_longSensorPortLabel.gridy = gridY;
		panel.add(longSensorPortLabel, gbc_longSensorPortLabel);
		gridX++;
		
		longSensorPortTextField = new JTextField();
		GridBagConstraints gbc_longSensorPortTextField = new GridBagConstraints();
		gbc_longSensorPortTextField.fill = GridBagConstraints.BOTH;
		gbc_longSensorPortTextField.anchor = GridBagConstraints.WEST;
		gbc_longSensorPortTextField.insets = new Insets(0, 0, 5, 5);
		gbc_longSensorPortTextField.gridwidth = 1;
		gbc_longSensorPortTextField.gridx = gridX;
		gbc_longSensorPortTextField.gridy = gridY;
		panel.add(longSensorPortTextField, gbc_longSensorPortTextField);
		longSensorPortTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = longSensorPortTextField.getText();
				if(!str.equals("")){					
					String cmdStr = "<AskCommand><SetSensorPort>0</SetSensorPort><Comport>"+str+"</Comport></AskCommand>";
									
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, cmdStr);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		gridX++;
		
		JLabel leftSensorPortLabel = new JLabel("左传感器串口号");
		GridBagConstraints gbc_leftSensorPortLabel = new GridBagConstraints();
		gbc_leftSensorPortLabel.fill = GridBagConstraints.BOTH;
		gbc_leftSensorPortLabel.anchor = GridBagConstraints.WEST;
		gbc_leftSensorPortLabel.insets = new Insets(0, 0, 5, 5);
		gbc_leftSensorPortLabel.gridwidth = 1;
		gbc_leftSensorPortLabel.gridx = gridX;
		gbc_leftSensorPortLabel.gridy = gridY;
		panel.add(leftSensorPortLabel, gbc_leftSensorPortLabel);
		gridX++;
		
		leftSensorPortTextField = new JTextField();
		GridBagConstraints gbc_leftSensorPortTextField = new GridBagConstraints();
		gbc_leftSensorPortTextField.fill = GridBagConstraints.BOTH;
		gbc_leftSensorPortTextField.anchor = GridBagConstraints.WEST;
		gbc_leftSensorPortTextField.insets = new Insets(0, 0, 5, 5);
		gbc_leftSensorPortTextField.gridwidth = 1;
		gbc_leftSensorPortTextField.gridx = gridX;
		gbc_leftSensorPortTextField.gridy = gridY;
		panel.add(leftSensorPortTextField, gbc_leftSensorPortTextField);
		leftSensorPortTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = leftSensorPortTextField.getText();

				if(!str.equals("")){					
					String cmdStr = "<AskCommand><SetSensorPort>1</SetSensorPort><Comport>"+str+"</Comport></AskCommand>";
									
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, cmdStr);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		gridX++;
		
		JLabel rightSensorPortLabel = new JLabel("右传感器串口号");
		GridBagConstraints gbc_rightSensorPortLabel = new GridBagConstraints();
		gbc_rightSensorPortLabel.fill = GridBagConstraints.BOTH;
		gbc_rightSensorPortLabel.anchor = GridBagConstraints.WEST;
		gbc_rightSensorPortLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rightSensorPortLabel.gridwidth = 1;
		gbc_rightSensorPortLabel.gridx = gridX;
		gbc_rightSensorPortLabel.gridy = gridY;
		panel.add(rightSensorPortLabel, gbc_rightSensorPortLabel);
		gridX++;
		
		rightSensorPortTextField = new JTextField();
		GridBagConstraints gbc_rightSensorPortTextField = new GridBagConstraints();
		gbc_rightSensorPortTextField.fill = GridBagConstraints.BOTH;
		gbc_rightSensorPortTextField.anchor = GridBagConstraints.WEST;
		gbc_rightSensorPortTextField.insets = new Insets(0, 0, 5, 5);
		gbc_rightSensorPortTextField.gridwidth = 1;
		gbc_rightSensorPortTextField.gridx = gridX;
		gbc_rightSensorPortTextField.gridy = gridY;
		panel.add(rightSensorPortTextField, gbc_rightSensorPortTextField);
		rightSensorPortTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = rightSensorPortTextField.getText();

				if(!str.equals("")){					
					String cmdStr = "<AskCommand><SetSensorPort>2</SetSensorPort><Comport>"+str+"</Comport></AskCommand>";
									
					HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
					eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, cmdStr);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		gridX++;
		
		//==========================================================================================
		gridY++;
		gridX = 0;
		
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
		{
			String strButton = null;
			if(i == 0)
				strButton = "设1传感器基点";
			else if(i == 1)
				strButton = "设2传感器基点";
			else if(i == 2)
				strButton = "设长传感器基点";

			String strCmd = "<AskCommand><SetBasePoint>"+i+"</SetBasePoint></AskCommand>";

			jButtonGetSensorBase[i] = new JButtonSendFSRLCmd(strButton,strCmd);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.fill = GridBagConstraints.BOTH;
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridwidth = 2;
			gbc_button.gridx = gridX;
			gbc_button.gridy = gridY;
			panel.add(jButtonGetSensorBase[i], gbc_button);

			gridX+=2;
		}
		
		//==========================================================================================
		gridY++;
		gridX = 0;
		
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
		{
			String strButton = null;
			if(i == 0)
				strButton = "取1传感器实时值";
			else if(i == 1)
				strButton = "取2传感器实时值";
			else if(i == 2)
				strButton = "取长传感器实时值";

			String strCmd = "<AskCommand><ReadRealValue>"+i+"</ReadRealValue></AskCommand>";

			jButtonGetSensorReal[i] = new JButtonSendFSRLCmd(strButton,strCmd);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.fill = GridBagConstraints.BOTH;
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridwidth = 2;
			gbc_button.gridx = gridX;
			gbc_button.gridy = gridY;
			panel.add(jButtonGetSensorReal[i], gbc_button);

			gridX+=2;
		}

		//=================================================================
		gridY++;
		gridX = 0;
		
		GridBagConstraints gbc_getLongButton = new GridBagConstraints();
		gbc_getLongButton.fill = GridBagConstraints.BOTH;
		gbc_getLongButton.insets = new Insets(0, 0, 5, 5);
		gbc_getLongButton.gridwidth = 2;
		gbc_getLongButton.gridx = gridX;
		gbc_getLongButton.gridy = gridY;
		panel.add(jButtonGetStaticLength, gbc_getLongButton);
		gridX+=2;
		
		GridBagConstraints gbc_getWidthButton = new GridBagConstraints();
		gbc_getWidthButton.fill = GridBagConstraints.BOTH;
		gbc_getWidthButton.insets = new Insets(0, 0, 5, 5);
		gbc_getWidthButton.gridwidth = 2;
		gbc_getWidthButton.gridx = gridX;
		gbc_getWidthButton.gridy = gridY;
		panel.add(jButtonGetStaticWidth, gbc_getWidthButton);
		gridX+=2;

		/*
		getHeightButton = new JButton("获取高");
		GridBagConstraints gbc_getHeightButton = new GridBagConstraints();
		gbc_getHeightButton.fill = GridBagConstraints.BOTH;
		gbc_getHeightButton.insets = new Insets(0, 0, 5, 5);
		gbc_getHeightButton.gridwidth = 2;
		gbc_getHeightButton.gridx = gridX;
		gbc_getHeightButton.gridy = gridY;
		panel.add(getHeightButton, gbc_getHeightButton);
		getHeightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = "<AskCommand><ReadOneDataValue>"+1+"</ReadOneDataValue></AskCommand>";
								
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, str);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
			}
		});
		gridX+=2;
		*/
		
		//=================================================================
		gridY++;
		gridX = 0;
		
		JLabel carTypeLabel = new JLabel("车型");
		GridBagConstraints gbc_carTypeLabel = new GridBagConstraints();
		gbc_carTypeLabel.fill = GridBagConstraints.BOTH;
		gbc_carTypeLabel.anchor = GridBagConstraints.WEST;
		gbc_carTypeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_carTypeLabel.gridwidth = 1;
		gbc_carTypeLabel.gridx = gridX;
		gbc_carTypeLabel.gridy = gridY;
		panel.add(carTypeLabel, gbc_carTypeLabel);
		gridX++;
		
		carTypeTextField = new JTextField();
		GridBagConstraints gbc_carTypeTextField = new GridBagConstraints();
		gbc_carTypeTextField.fill = GridBagConstraints.BOTH;
		gbc_carTypeTextField.anchor = GridBagConstraints.WEST;
		gbc_carTypeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_carTypeTextField.gridwidth = 1;
		gbc_carTypeTextField.gridx = gridX;
		gbc_carTypeTextField.gridy = gridY;
		panel.add(carTypeTextField, gbc_carTypeTextField);
		carTypeTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = "<AskCommand><SetVehicleType>"+carTypeTextField.getText()+"</SetVehicleType></AskCommand>";
								
				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SOCKET_MSG, str);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SOCKET_SEND_MSG_INTENT,eventExtra);    						
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		gridX++;
		
		return panel;
	}
	
	class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

//			LMSLog.d(DEBUG_TAG, "eventType="+eventType);

	        if (eventType.equals(LMSConstValue.SOCKET_RECEIVE_MSG_INTENT)||eventType.equals(LMSConstValue.SOCKET_SEND_MSG_INTENT))
			{				
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SOCKET_MSG))
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SOCKET_MSG);
					LMSLog.d(DEBUG_TAG,"receive MSG:"+str);
					receiveMsgTextArea.setText(str);
					
					if(str.contains("<SetBasePoint>")||str.contains("<RealValue>"))
					{
						if(str.contains("<Sensor>"))
						{
							String result;
							
							result = new XMLParse().getXMLStrValue(str,"Sensor");							
							int sensorID = Integer.valueOf(result);
												
							result = new XMLParse().getXMLStrValue(str,"Virtical");							
							int Virtical = Integer.valueOf(result);
							
							result = new XMLParse().getXMLStrValue(str,"Horizontal");							
							int Horizontal = Integer.valueOf(result);
	
							//=================================================================================
							if(str.contains("<SetBasePoint>"))
							{
								String strButton = "";
								if(sensorID == 0)
									strButton = "设1传感器基点";
								else if(sensorID == 1)
									strButton = "设2传感器基点";
								else if(sensorID == 2)
									strButton = "设长传感器基点";
								jButtonGetSensorBase[sensorID].setText(strButton+"(垂直值:"+Horizontal+";水平值:"+Virtical+")");
							}
							else
							{
								String strButton = "";
								if(sensorID == 0)
									strButton = "取1传感器实时值";
								else if(sensorID == 1)
									strButton = "取2传感器实时值";
								else if(sensorID == 2)
									strButton = "取长传感器实时值";
								jButtonGetSensorReal[sensorID].setText(strButton+"(垂直值:"+Horizontal+";水平值:"+Virtical+")");							
							}
						}
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
			else if (eventType.equals(LMSConstValue.SOCKET_SEND_MSG_INTENT))
			{				
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SOCKET_MSG))
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SOCKET_MSG);
					LMSLog.d(DEBUG_TAG,"send MSG:"+str);
					
					sendMsgTextField.setText(str);
				}
			}
		}
	}
}
