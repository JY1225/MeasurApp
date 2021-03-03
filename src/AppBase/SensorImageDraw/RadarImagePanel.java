package AppBase.SensorImageDraw;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class RadarImagePanel extends JSplitPane{
	private String DEBUG_TAG = "RadarImagePanel";

	int _sensorID;
	
	RadarImageMonitorPanel radarImageMonitorPanel[] = new RadarImageMonitorPanel[LMSConstValue.RADAR_SENSOR_NUM];;
	JButton buttonBoolAngleDisplay[] = new JButton[LMSConstValue.RADAR_SENSOR_NUM];
	JTextField editTextAngleDisplay[] = new JTextField[LMSConstValue.RADAR_SENSOR_NUM];
			
	public RadarImagePanel(final int sensorID) 
	{		
		_sensorID = sensorID;
		
		DEBUG_TAG += _sensorID;
		
		//==============================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==============================================================================
		if(sensorID<LMSConstValue.RADAR_SENSOR_NUM)
		{
			buttonBoolAngleDisplay[sensorID] = new JButton();
			editTextAngleDisplay[sensorID] = new JTextField();			
		}
		
		//==============================================================================
		int gridX,gridY;
		JPanel panel;

		//============================================================
		JLabel textViewLMS;
		if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
			textViewLMS = new JLabel("<html>长\n 雷\n 达\n"+"<html>");
		else
			textViewLMS = new JLabel("<html>宽\n 高\n 雷\n 达\n "+(sensorID+1)+"<html>");

		setLeftComponent(textViewLMS);

		//============================================================					
		panel = new JPanel();
		GridBagLayout gbc_panel = new GridBagLayout();
		gbc_panel.columnWidths = new int[]{776, 20, 20, 0};
		gbc_panel.rowHeights = new int[]{22, 22, 22, 22, 22, 22, 22, 22, 22};
		gbc_panel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbc_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbc_panel);

		setRightComponent(panel);

		//=========================================================
		gridY = 0;
		
		JButton buttonYEnlarge = new JButton("Y+");
		GridBagConstraints gbc_buttonYEnlarge = new GridBagConstraints();
		gbc_buttonYEnlarge.anchor = GridBagConstraints.NORTH;
		gbc_buttonYEnlarge.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonYEnlarge.insets = new Insets(0, 0, 5, 0);
		gbc_buttonYEnlarge.gridwidth = 1;
		gbc_buttonYEnlarge.gridx = 1;
		gbc_buttonYEnlarge.gridy = gridY;
		panel.add(buttonYEnlarge, gbc_buttonYEnlarge);		
		buttonYEnlarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDetectSetting.enlargeFrontEdgeWindowValue(sensorID,true);
			}
		});
		
		JButton buttonYReduce = new JButton("Y-");
		GridBagConstraints gbc_buttonYReduce = new GridBagConstraints();
		gbc_buttonYReduce.anchor = GridBagConstraints.NORTH;
		gbc_buttonYReduce.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonYReduce.insets = new Insets(0, 0, 5, 0);
		gbc_buttonYReduce.gridwidth = 1;
		gbc_buttonYReduce.gridx = 2;
		gbc_buttonYReduce.gridy = gridY;
		panel.add(buttonYReduce, gbc_buttonYReduce);
		buttonYReduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDetectSetting.enlargeFrontEdgeWindowValue(sensorID,false);
			}
		});	
		gridY++;

		JButton buttonXEnlarge = new JButton("X+");
		GridBagConstraints gbc_buttonXEnlarge = new GridBagConstraints();
		gbc_buttonXEnlarge.anchor = GridBagConstraints.NORTH;
		gbc_buttonXEnlarge.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonXEnlarge.insets = new Insets(0, 0, 5, 0);
		gbc_buttonXEnlarge.gridwidth = 1;
		gbc_buttonXEnlarge.gridx = 1;
		gbc_buttonXEnlarge.gridy = gridY;
		panel.add(buttonXEnlarge, gbc_buttonXEnlarge);
		buttonXEnlarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDetectSetting.enlargeLREdgeWindowValue(sensorID,true);
			}
		});

		JButton buttonXReduce = new JButton("X-");
		GridBagConstraints gbc_buttonXReduce = new GridBagConstraints();
		gbc_buttonXReduce.anchor = GridBagConstraints.NORTH;
		gbc_buttonXReduce.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonXReduce.insets = new Insets(0, 0, 5, 0);
		gbc_buttonXReduce.gridwidth = 1;
		gbc_buttonXReduce.gridx = 2;
		gbc_buttonXReduce.gridy = gridY;
		panel.add(buttonXReduce, gbc_buttonXReduce);
		buttonXReduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDetectSetting.enlargeLREdgeWindowValue(sensorID,false);
			}
		});
		gridY++;
		
		JButton buttonXMoveLeft = new JButton("左移");
		GridBagConstraints gbc_buttonXMoveLeft = new GridBagConstraints();
		gbc_buttonXMoveLeft.anchor = GridBagConstraints.NORTH;
		gbc_buttonXMoveLeft.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonXMoveLeft.insets = new Insets(0, 0, 5, 0);
		gbc_buttonXMoveLeft.gridwidth = 1;
		gbc_buttonXMoveLeft.gridx = 1;
		gbc_buttonXMoveLeft.gridy = gridY;
		panel.add(buttonXMoveLeft, gbc_buttonXMoveLeft);
		buttonXMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDetectSetting.setXMoveLeftValue(sensorID);
			}
		});
		
		JButton buttonXMoveRight = new JButton("右移");
		GridBagConstraints gbc_buttonXMoveRight = new GridBagConstraints();
		gbc_buttonXMoveRight.anchor = GridBagConstraints.NORTH;
		gbc_buttonXMoveRight.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonXMoveRight.insets = new Insets(0, 0, 5, 0);
		gbc_buttonXMoveRight.gridwidth = 1;
		gbc_buttonXMoveRight.gridx = 2;
		gbc_buttonXMoveRight.gridy = gridY;
		panel.add(buttonXMoveRight, gbc_buttonXMoveRight);
		buttonXMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDetectSetting.setXMoveRightValue(sensorID);
			}
		});
		gridY++;

		JButton buttonYMoveUp = new JButton("上移");
		GridBagConstraints gbc_buttonYMoveUp = new GridBagConstraints();
		gbc_buttonYMoveUp.anchor = GridBagConstraints.NORTH;
		gbc_buttonYMoveUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonYMoveUp.insets = new Insets(0, 0, 5, 0);
		gbc_buttonYMoveUp.gridwidth = 1;
		gbc_buttonYMoveUp.gridx = 1;
		gbc_buttonYMoveUp.gridy = gridY;
		panel.add(buttonYMoveUp, gbc_buttonYMoveUp);
		buttonYMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(LMSConstValue.bUpDownTurn[sensorID] == false)
				{
					CarDetectSetting.setYMoveUpValue(sensorID);					
				}
				else
				{
					CarDetectSetting.setYMoveDownValue(sensorID);
				}
			}
		});
		
		JButton buttonYMoveDown = new JButton("下移");
		GridBagConstraints gbc_buttonYMoveDown = new GridBagConstraints();
		gbc_buttonYMoveDown.anchor = GridBagConstraints.NORTH;
		gbc_buttonYMoveDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonYMoveDown.insets = new Insets(0, 0, 5, 0);
		gbc_buttonYMoveDown.gridwidth = 1;
		gbc_buttonYMoveDown.gridx = 2;
		gbc_buttonYMoveDown.gridy = gridY;
		panel.add(buttonYMoveDown, gbc_buttonYMoveDown);
		buttonYMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(LMSConstValue.bUpDownTurn[sensorID] == false)
				{
					CarDetectSetting.setYMoveDownValue(sensorID);
				}
				else
				{
					CarDetectSetting.setYMoveUpValue(sensorID);					
				}
			}
		});
		gridY++;

		JButton buttonLRTurn = new JButton("视窗镜像");
		GridBagConstraints gbc_buttonLRTurn = new GridBagConstraints();
		gbc_buttonLRTurn.anchor = GridBagConstraints.NORTH;
		gbc_buttonLRTurn.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonLRTurn.insets = new Insets(0, 0, 5, 0);
		gbc_buttonLRTurn.gridwidth = 2;
		gbc_buttonLRTurn.gridx = 1;
		gbc_buttonLRTurn.gridy = gridY;
		panel.add(buttonLRTurn, gbc_buttonLRTurn);
		buttonLRTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LMSLog.d(DEBUG_TAG,"buttonLRTurn"+sensorID);

				CarDetectSetting.changeLRTurn(sensorID);
			}
		});
		gridY++;

		//*
		JButton buttonUpDownTurn = new JButton("视窗翻转");
		GridBagConstraints gbc_buttonUpDownTurn = new GridBagConstraints();
		gbc_buttonUpDownTurn.anchor = GridBagConstraints.NORTH;
		gbc_buttonUpDownTurn.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonUpDownTurn.insets = new Insets(0, 0, 5, 0);
		gbc_buttonUpDownTurn.gridwidth = 2;
		gbc_buttonUpDownTurn.gridx = 1;
		gbc_buttonUpDownTurn.gridy = gridY;
		panel.add(buttonUpDownTurn, gbc_buttonUpDownTurn);
		buttonUpDownTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LMSLog.d(DEBUG_TAG,"buttonUpDownTurn"+sensorID);

				CarDetectSetting.changeUpDownTurn(sensorID);
			}
		});
		gridY++;
		//*/
		
		/*
		JButton buttonRotate = new JButton("视窗旋转");
		GridBagConstraints gbc_buttonRotate = new GridBagConstraints();
		gbc_buttonRotate.anchor = GridBagConstraints.NORTH;
		gbc_buttonRotate.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonRotate.insets = new Insets(0, 0, 5, 0);
		gbc_buttonRotate.gridwidth = 2;
		gbc_buttonRotate.gridx = 1;
		gbc_buttonRotate.gridy = gridY;
		panel.add(buttonRotate, gbc_buttonRotate);
		buttonRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LMSLog.d(DEBUG_TAG,"buttonRotate"+sensorID);

				CarDetectSetting.changeButtonRotate(sensorID);
			}
		});
		gridY++;
		*/
		
		if(LMSConstValue.bAngleDisplay[sensorID] == true)
		{
			buttonBoolAngleDisplay[sensorID] = new JButton("角度显示("+(float)LMSConstValue.iAngleDisplay[sensorID]/10+")");
		}
		else
		{
			buttonBoolAngleDisplay[sensorID] = new JButton("角度隐藏("+(float)LMSConstValue.iAngleDisplay[sensorID]/10+")");			
		}
		GridBagConstraints gbc_buttonBoolAngleDisplay = new GridBagConstraints();
		gbc_buttonBoolAngleDisplay.anchor = GridBagConstraints.NORTH;
		gbc_buttonBoolAngleDisplay.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonBoolAngleDisplay.insets = new Insets(0, 0, 5, 0);
		gbc_buttonBoolAngleDisplay.gridwidth = 2;
		gbc_buttonBoolAngleDisplay.gridx = 1;
		gbc_buttonBoolAngleDisplay.gridy = gridY;
		panel.add(buttonBoolAngleDisplay[sensorID], gbc_buttonBoolAngleDisplay);
		buttonBoolAngleDisplay[sensorID].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LMSLog.d(DEBUG_TAG,"button90Display"+sensorID);

				CarDetectSetting.changeButtonBoolAngleDisplaye(sensorID);
			}
		});

		gridY++;

		JTextField editTextAngleDisplayTmp = new JTextField();
		GridBagConstraints gbc_editTextAngleDisplay = new GridBagConstraints();
		gbc_editTextAngleDisplay.fill = GridBagConstraints.BOTH;
		gbc_editTextAngleDisplay.insets = new Insets(0, 0, 5, 5);
		gbc_editTextAngleDisplay.gridwidth = 2;
		gbc_editTextAngleDisplay.gridx = 1;
		gbc_editTextAngleDisplay.gridy = gridY;
		panel.add(editTextAngleDisplayTmp, gbc_editTextAngleDisplay);
		editTextAngleDisplay[sensorID] = editTextAngleDisplayTmp;
		editTextAngleDisplay[sensorID].addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = editTextAngleDisplay[sensorID].getText().toString();

				CarDetectSetting.setEditTextAngleDisplayValue(str,sensorID);
			}
	
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
						
			}			
		});
		editTextAngleDisplay[sensorID].addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_MINUS || keyChar == KeyEvent.VK_PERIOD ||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){  
			
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
				}  
			}  
		});
		gridY++;
		
		//============================================================					
		radarImageMonitorPanel[sensorID] = new RadarImageMonitorPanel(sensorID);
		GridBagLayout gbl_aPane = new GridBagLayout();
		gbl_aPane.columnWidths = new int[]{0};
		gbl_aPane.rowHeights = new int[]{0};
		gbl_aPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_aPane.rowWeights = new double[]{Double.MIN_VALUE};
		radarImageMonitorPanel[sensorID].setLayout(gbl_aPane);

		GridBagConstraints gbc_aPane = new GridBagConstraints();
		gbc_aPane.insets = new Insets(0, 0, 5, 5);
		gbc_aPane.gridheight = gridY+1;
		gbc_aPane.fill = GridBagConstraints.BOTH;
		gbc_aPane.gridx = 0;
		gbc_aPane.gridy = 0;
		panel.add(radarImageMonitorPanel[sensorID], gbc_aPane);		
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
				
				if(_sensorID == sensorID)
				{
					if(nvram.equals(LMSConstValue.nvramLREdgeWindow)
						||nvram.equals(LMSConstValue.nvramFrontEdgeWindow)
						||nvram.equals(LMSConstValue.nvramLRturn)
						||nvram.equals(LMSConstValue.nvramUpDownTurn)
	//					||nvram.equals(LMSConstValue.nvramRotateTurn)
						||nvram.equals(LMSConstValue.nvramAngleDisplay))
					{
						radarImageMonitorPanel[_sensorID].repaint();					
					}
					
					if(nvram.equals(LMSConstValue.nvramBoolAngleDisplay)
						||nvram.equals(LMSConstValue.nvramAngleDisplay)) 
			        {				
						if(buttonBoolAngleDisplay[sensorID] != null)
						{
				        	if(LMSConstValue.bAngleDisplay[sensorID] == true)
				        		buttonBoolAngleDisplay[sensorID].setText("角度显示("+(float)LMSConstValue.iAngleDisplay[sensorID]/10+")");	        		
				        	else
				        		buttonBoolAngleDisplay[sensorID].setText("角度隐藏("+(float)LMSConstValue.iAngleDisplay[sensorID]/10+")");	        		
						}
			        }
				}
	        }
		}
	}	
}
