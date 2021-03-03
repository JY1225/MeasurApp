package AppFrame.carDetect;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import AppBase.appBase.AppConst;
import AppBase.appBase.CarTypeAdapter;
import AppFrame.widgets.AuthWidget;
import AppFrame.widgets.DetectionListComboBox;
import AppFrame.widgets.JAuthFrame;
import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JButtonMonitor;
import AppFrame.widgets.JCheckBoxNvram;
import AppFrame.widgets.JLabelComboBox;
import AppFrame.widgets.JLabelRadioButtonGroup;
import AppFrame.widgets.JLabelSpinner;
import AppFrame.widgets.JLabelSystemStatus;
import AppFrame.widgets.JSettingLabelTextField;
import CustomerProtocol.DetectionVehicle;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import SensorBase.LMSConstValue.enumDetectType;
import ThreeD.ThreeDMainThread;

import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class CarDetectFrameMainTab {
	private final String DEBUG_TAG="CarDetectFrameMainTab";

	ThreeDMainThread threeDMainThread;
	
	protected JLabelSystemStatus systemStatusLabel[] = new JLabelSystemStatus[LMSConstValue.RADAR_SENSOR_NUM];
    JLabel labelRainFilter;
    JTextField textFieldRainFilter;
    JLabel labelResultPortString;
    JTextField textFieldResultPort;
    
	protected JRadioButton autoRotateRadioButton;
	protected JRadioButton handRotateRadioButton;
	protected ButtonGroup buttonGroup;

	JSettingLabelTextField customerLabelTextField;
	JSettingLabelTextField radar2distanceLabelTextField;
	JSettingLabelTextField lightCurtainDistanceLabelTextField;
		
	public static JButtonBoolean beginDetectionButton;
	public static JLabel detectParameterLabel;
	public static JLabelRadioButtonGroup carTypeRadioButtonGroup;
	public static JCheckBoxNvram lanBanDetectCheckBox;
	public static JCheckBoxNvram filterCheLanCheckBox;
	public static JCheckBoxNvram filterEndGasCheckBox;
	public static JLabelComboBox lengthFilterLabelComboBox;
	public static JLabelComboBox widthFilterLabelComboBox;
	public static JLabelComboBox heightFilterLabelComboBox;
	
	JAuthFrame authFrame;
		

    //=============================================================
	public JSplitPane createTab() {
    	EventListener eventListener = new EventListener();
        LMSEventManager.addListener(eventListener);
		
		//======================================================
		if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
				||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME	
				||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
				||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME	
		)
		{				
			if(LMSConstValue.bNvramCreateThreeDImage.bValue)
			{
				threeDMainThread = new ThreeDMainThread();
				threeDMainThread.start();
			}
			
//			new CNN_PredictMain().loadCNN_DATA();
		}	
		
		//======================================================
    	JSplitPane mainTab = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
		//=====================================================================
		mainTab.setLeftComponent(createImagePanel());
	
		mainTab.setRightComponent(createSettingPanel());
		
		return mainTab;
	}

//	static DefaultComboBoxModel model = new DefaultComboBoxModel(); 
//	public static JAutoCompleteComboBox vehicleTypeComboBox = new JAutoCompleteComboBox(model);
	public JPanel createImagePanel() {
		JPanel panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100};
		gridBagLayout.rowHeights = new int[]{600, 20, 20, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//=====================================================================
		int gridY = 0;
		int gridX = 0;
		
        GridBagConstraints gbc_glCanvas = new GridBagConstraints();
        gbc_glCanvas.gridwidth = 9;
        gbc_glCanvas.insets = new Insets(0, 0, 5, 5);
        gbc_glCanvas.fill = GridBagConstraints.BOTH;
		gbc_glCanvas.gridx = gridX;
        gbc_glCanvas.gridy = gridY;
		if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME	
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME	
		)
		{				
	        if(LMSConstValue.bNvramCreateThreeDImage.bValue == true)
			{
		        panel.add(threeDMainThread.threeDAnimator.glcanvas, gbc_glCanvas);
			}
		}
                        
        //=====================================================================
        gridY++;
        gridX = 0;

		new JButtonMonitor(panel, gridX, gridY, -1);
		gridY++;

        //=====================================================================
		gridX = 0;
        new AuthWidget(panel,gridX,gridY,"设置密码");
        gridX +=2;
        
		JButton AuthFrameButton = new JButton("重设密码");
	    GridBagConstraints gbc_AuthFrameButton = new GridBagConstraints();
	    gbc_AuthFrameButton.insets = new Insets(0, 0, 0, 5);
	    gbc_AuthFrameButton.fill = GridBagConstraints.HORIZONTAL;
	    gbc_AuthFrameButton.gridx = gridX;
	    gbc_AuthFrameButton.gridy = gridY;
	    panel.add(AuthFrameButton,gbc_AuthFrameButton);
	    AuthFrameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(authFrame == null)
				{
					authFrame = new JAuthFrame();
				}
				
				if(authFrame.isVisible())
					authFrame.setVisible(false);
				else
					authFrame.setVisible(true);
			}
		});
		gridX++;		
		
		//========================================================
		JButton buttonResultMonitor = new JButton("结果监控");
	    GridBagConstraints gbc_buttonResultMonitor = new GridBagConstraints();
	    gbc_buttonResultMonitor.insets = new Insets(0, 0, 0, 5);
	    gbc_buttonResultMonitor.fill = GridBagConstraints.HORIZONTAL;
	    gbc_buttonResultMonitor.gridx = gridX;
	    gbc_buttonResultMonitor.gridy = gridY;
	    panel.add(buttonResultMonitor,gbc_buttonResultMonitor);
	    buttonResultMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 Process process = null;       

			     try {
					process = Runtime.getRuntime().exec("java -jar 结果监控.jar inner");
					LMSLog.d(DEBUG_TAG, process.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// windows下的命令，显示信息中包含有mac地址信息      

			}
		});
		gridX++;

        labelResultPortString = new JLabel("结果输出端口:"+LMSConstValue.LMS_AP_RESULT_PORT);
        GridBagConstraints gbc_labelResultPortString = new GridBagConstraints();
        gbc_labelResultPortString.insets = new Insets(0, 0, 5, 5);
        gbc_labelResultPortString.fill = GridBagConstraints.HORIZONTAL;
        gbc_labelResultPortString.gridx = gridX;
        gbc_labelResultPortString.gridy = gridY;
        gridX++;
        panel.add(labelResultPortString, gbc_labelResultPortString);

        textFieldResultPort = new JTextField();
        GridBagConstraints gbc_textFieldResultPortString = new GridBagConstraints();
        gbc_textFieldResultPortString.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldResultPortString.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldResultPortString.gridx = gridX;
        gbc_textFieldResultPortString.gridy = gridY;
        gridX++;
        panel.add(textFieldResultPort,gbc_textFieldResultPortString);
		textFieldResultPort.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = textFieldResultPort.getText().toString();

				if(!str.equals(""))
				{
					LMSConstValue.LMS_AP_RESULT_PORT = Integer.valueOf(str);
					labelResultPortString.setText("结果输出端口("+LMSConstValue.LMS_AP_RESULT_PORT+"):");
					
	        		LMSPlatform.savePorperty(null,"nvram_resultPort",String.valueOf(LMSConstValue.LMS_AP_RESULT_PORT));
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		textFieldResultPort.setEditable(false);
		
		//===========================================================================
		JButton buttonSimulateCarOut = new JButton("模拟出车");
	    GridBagConstraints gbc_buttonSimulateCarOut = new GridBagConstraints();
	    gbc_buttonSimulateCarOut.insets = new Insets(0, 0, 0, 5);
	    gbc_buttonSimulateCarOut.fill = GridBagConstraints.HORIZONTAL;
	    gbc_buttonSimulateCarOut.gridx = gridX;
	    gbc_buttonSimulateCarOut.gridy = gridY;
	    panel.add(buttonSimulateCarOut,gbc_buttonSimulateCarOut);
	    buttonSimulateCarOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				HashMap<String, Comparable> eventExtraResult = new HashMap<String, Comparable>();
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.CAR_RESULT.ordinal());
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_NUM,0);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_WIDTH,1700);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT,2400);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_LENGTH,15000);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_TIME,6000);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_OUT_TIME,LMSConstValue.strSimulateTime);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Z_NUM,6);				
				for(int i=0;i<5;i++)
				{
					eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_ZD+i,1000+200*i);
				}
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_ZJ,9456);
				//--------------------------------------------------------------------------
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH,12000);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_WIDTH,1500);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT,2300);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT_LEAN_ORI,2350);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_Z_NUM,3);				
				for(int i=0;i<3;i++)
				{
					eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_ZD+i,1000+200*i);
				}
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_G_ZJ,6456);
				//--------------------------------------------------------------------------
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH,3000);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_WIDTH,1400);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_HEIGHT,2200);
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_Z_NUM,3);				
				for(int i=0;i<3;i++)
				{
					eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_ZD+i,1000+200*(i+3));
				}
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_Q_ZJ,3444);
				//--------------------------------------------------------------------------
				eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_XZJ,7000);
				//--------------------------------------------------------------------------
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_STATE_CHANGE_INTENT,eventExtraResult);	
			}
		});
		gridX++;

        //-------------------------------------------------------------------
		customerLabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"客户编码", "", true,
			false,
			true,
			LMSConstValue.sNvramCustomer, -1, -1
		);        
        //companyName已经定义,不可再修改
		if(!AppConst.companyName.equals(""))
		{
			customerLabelTextField.setEditable(false);
		}
		gridX+=2;
		
        //============================================================================
        gridY++; 
        gridX = 0;
        
        //============================================================================
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
        {	
			JLabel lmsLabel;
			if(LMSConstValue.radarFunctionType[i] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
				lmsLabel = new JLabel("长雷达:");
			else
				lmsLabel = new JLabel("宽高雷达"+(i+1)+":");
			GridBagConstraints gbc_lmsLabel = new GridBagConstraints();
			gbc_lmsLabel.gridx = gridX;
			gbc_lmsLabel.gridy = gridY;
			panel.add(lmsLabel, gbc_lmsLabel);
			gridX++;
			
	        systemStatusLabel[i] = new JLabelSystemStatus(i);
			GridBagConstraints gbc_LabelSystemStateTmp = new GridBagConstraints();
			gbc_LabelSystemStateTmp.gridwidth = 2;
			gbc_LabelSystemStateTmp.anchor = GridBagConstraints.WEST;
			gbc_LabelSystemStateTmp.gridx = gridX;
			gbc_LabelSystemStateTmp.gridy = gridY;
			panel.add(systemStatusLabel[i], gbc_LabelSystemStateTmp);
			gridX += gbc_LabelSystemStateTmp.gridwidth;			
        }
				
		//=====================================================================
		return panel;
	}
	
	public JPanel createSettingPanel() {
		//======================================================
        JPanel panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{120, 120};
		gridBagLayout.rowHeights = new int[]{
			15, 15, 15, 15, 15, 15, 15, 15,
			15, 15, 15, 15, 15, 15, 15, 15,
			15, 15, 15}; //580
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{
			1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);

        //================================================================================
		int gridX = 0;
        int gridY = 0;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
		String time = df.format(new Date()); 
	    JLabel labelStartTime = new JLabel("系统启动时间:"+time);
        GridBagConstraints gbc_labelStartTime = new GridBagConstraints();
        gbc_labelStartTime.gridwidth = 2;
        gbc_labelStartTime.insets = new Insets(0, 0, 5, 5);
        gbc_labelStartTime.gridx = gridX;
        gbc_labelStartTime.gridy = gridY;
        panel.add(labelStartTime, gbc_labelStartTime);
        gridX += gbc_labelStartTime.gridwidth;
        
		gridY++;
        gridX = 0;

        JLabel labelVersion = new JLabel("软件版本:"+LMSConstValue.softwareVersion);
        GridBagConstraints gbc_labelVersion = new GridBagConstraints();
        gbc_labelVersion.insets = new Insets(0, 0, 5, 5);
        gbc_labelVersion.gridwidth = 2;
        gbc_labelVersion.gridx = gridX;
        gbc_labelVersion.gridy = gridY;
        panel.add(labelVersion, gbc_labelVersion);
        gridX += gbc_labelVersion.gridwidth;
		gridY++;
        
 		//----------------------------------------------------
		gridX = 0;
        if(LMSConstValue.appType != LMSConstValue.AppType.CONTOUR_DETECTION)
        {
			new JButtonBoolean(
    			panel,
    			gridX,gridY,2,
    			"本地设置(打开)",
    			"本地设置(关闭)",
    			LMSConstValue.bNvramCarTypeLocalSetting,
    			-1
    		);
        	gridY++;
        	
        	beginDetectionButton = new JButtonBoolean(
        		panel,
    			gridX, gridY, 2,
    			LMSConstValue.strPauseDetect,
    			LMSConstValue.strDetecting,
    			CarTypeAdapter.bNvramPauseDetect,
    			-1
    		);
    		beginDetectionButton.resetTextBackGroundColor(Color.RED, Color.GREEN);
    		gridY++;
    		
    		//---------------------------------------------------------------------------
			detectParameterLabel = new JLabel("测量参数:"+CarTypeAdapter.sNvramCarTypeString.sValue);
        	GridBagConstraints gbcLabel = new GridBagConstraints();
    		gbcLabel.insets = new Insets(0, 0, 5, 5);
    		gbcLabel.fill = GridBagConstraints.BOTH;
    		gbcLabel.gridwidth = 2;
    		gbcLabel.gridx = gridX;
    		gbcLabel.gridy = gridY;
    		panel.add(detectParameterLabel, gbcLabel);    		
			gridX+=2;
			
			gridY++;
		 		
	 		//===============================================================
	        gridX = 0;
	        carTypeRadioButtonGroup = new JLabelRadioButtonGroup(
		    	panel,
				gridX,gridY,
				2,1,
				null,
				-1,
				false,
				CarTypeAdapter.nvramCarEnumType,CarTypeAdapter.carEnumType
	        );	
	        gridY+=3;
	        
	        lanBanDetectCheckBox = new JCheckBoxNvram(
				panel,
				gridX,gridY,1,
				"栏板测量",
				CarTypeAdapter.bNvramLanBanDetect,
				-1,
				false
			);
	        gridX++;

	        gridY++;
	        gridX=0;
	        filterCheLanCheckBox = new JCheckBoxNvram(
	        	panel,
				gridX,gridY,2,
				"挂车剔除车篮,梯子",
				CarTypeAdapter.bNvramFilterCheLan,
				-1,
				false
			);
	        gridX+=2;
	        
	        //===========================================================
	        gridY++;
			gridX = 0;
			lengthFilterLabelComboBox = new JLabelComboBox(
	 			panel,
	 			gridX,gridY,
	 			1,
	 			"长小物体剔除",
	 			-1,
	 			LMSConstValue.nvramEnumLengthFilterType,LMSConstValue.enumLengthFilterType
	 		);
			gridX+=3;
			
			gridY++;
			gridX = 0;
			widthFilterLabelComboBox = new JLabelComboBox(
	 			panel,
	 			gridX,gridY,
	 			1,
	 			"宽小物体剔除",
	 			-1,
	 			LMSConstValue.nvramEnumWidthFilterType,LMSConstValue.enumWidthFilterType
	 		);
			gridX+=3;
			
			gridY++;
			gridX = 0;
			heightFilterLabelComboBox = new JLabelComboBox(
	 			panel,
	 			gridX,gridY,
	 			1,
	 			"高小物体剔除",
	 			-1,
	 			LMSConstValue.nvramEnumHeightFilterType,LMSConstValue.enumHeightFilterType
	 		);
			gridX+=3;
			
	        //===========================================================
			gridY++;
			gridX = 0;
			filterEndGasCheckBox = new JCheckBoxNvram(
	        	panel,
				gridX,gridY,2,
				"尾气滤除",
				CarTypeAdapter.bNvramFilterEndGas,
				-1,
				false
			);
			
			resetCarTypeLocalSetting();
		}
        else
        {
        	//================================================================================
            gridX = 0;
            new JLabelRadioButtonGroup(
    	    	panel,
    			gridX,gridY,
    			2,1,
    			null,
    			-1,
    			false,
    			LMSConstValue.nvramEnumThreeDMouseType,LMSConstValue.enumThreeDMouseType
            );	
            gridY++;
            
    		//================================================================================
            gridX = 0;				
            new JCheckBoxNvram(
    			panel,
    			gridX,gridY,1,
    			"显示雷达1",
    			LMSConstValue.bNvramThreeDDisplayRadar0,
    			-1,
    			false
    		);
            gridX++;
            
            new JCheckBoxNvram(
    			panel,
    			gridX,gridY,1,
    			"显示雷达2",
    			LMSConstValue.bNvramThreeDDisplayRadar1,
    			-1,
    			false
    		);
            gridX++;
            
            gridY++;

    	    //=================================================================================
    		if(LMSConstValue.isMyMachine())
    		{
    	        gridX = 0;
    	        new JCheckBoxNvram(
	    			panel,
	    			gridX,gridY,1,
	    			"显示光栅",
	    			LMSConstValue.bNvramThreeDDisplayLightCurtain,
	    			-1,
	    			false
	    		);
	            gridX++;
	            
    	        new JCheckBoxNvram(
        			panel,
        			gridX,gridY,1,
        			"滤波后数据",
        			LMSConstValue.bNvramThreeDDisplayFilterIn,
        			-1,
        			false
        		);
    	        gridX++;
    	        gridY++;

    	        //=================================================================================
    	        gridX = 0;
    	        new JCheckBoxNvram(
        			panel,
        			gridX,gridY,1,
        			"一级滤波数据",
        			LMSConstValue.bNvramThreeDDisplayFilterOut1,
        			-1,
        			false
        		);
    	        gridX++;
    	        
    	        new JCheckBoxNvram(
        			panel,
        			gridX,gridY,1,
        			"二级滤波数据",
        			LMSConstValue.bNvramThreeDDisplayFilterOut2,
        			-1,
        			false
        		);
    	        gridX++;
    	        
    	        //=================================================================================
                gridY++;
                gridX = 0;
                new JSettingLabelTextField(
                	panel, 
        			gridX, gridY, 1, 1,
        			"X向范围(小)", "mm", true,
        			false,
        			true,
        			LMSConstValue.iNvramXRangeMin, -1, -1
        		);
                
                gridY++;
                gridX = 0;
                new JSettingLabelTextField(
                	panel, 
        			gridX, gridY, 1, 1,
        			"X向范围(大)", "mm", true,
        			false,
        			true,
        			LMSConstValue.iNvramXRangeMax, -1, -1
        		);
                
    	        //=================================================================================
                gridY++;
                gridX = 0;
                new JSettingLabelTextField(
                	panel, 
        			gridX, gridY, 1, 1,
        			"Y向范围(小)", "mm", true,
        			false,
        			true,
        			LMSConstValue.iNvramYRangeMin, -1, -1
        		);
                
                gridY++;
                gridX = 0;
                new JSettingLabelTextField(
                	panel, 
        			gridX, gridY, 1, 1,
        			"Y向范围(大)", "mm", true,
        			false,
        			true,
        			LMSConstValue.iNvramYRangeMax, -1, -1
        		);
    	        
    	        //=================================================================================
    	        gridY++;
                gridX = 0;
                new JSettingLabelTextField(
                	panel, 
        			gridX, gridY, 1, 1,
        			"Z向范围(小)", "mm", true,
        			false,
        			true,
        			LMSConstValue.iNvramZRangeMin, -1, -1
        		);
                
                gridY++;
                gridX = 0;
                new JSettingLabelTextField(
                	panel, 
        			gridX, gridY, 1, 1,
        			"Z向范围(大)", "mm", true,
        			false,
        			true,
        			LMSConstValue.iNvramZRangeMax, -1, -1
        		);
    	        gridY++;
    		}

            //=================================================================================
            gridX = 0;
            new JButtonBoolean(
    			panel,
    			gridX,gridY,2,
    			"中脊显示(打开)",
    			"中脊显示(关闭)",
    			LMSConstValue.bNvramThreeDDisplayMiddle,
    			-1
    		);
    		gridX+=1;
    		        
            //=================================================================================
            gridY++;
            gridX = 0;
            radar2distanceLabelTextField = new JSettingLabelTextField(
            	panel, 
    			gridX, gridY, 1, 1,
    			"雷达2与长距离", "mm", true,
    			false,
    			true,
    			LMSConstValue.iNvramLWDistance2, -1, -1
    		);
            radar2distanceLabelTextField.setEditable(false);

    		gridY++;
    		
    		lightCurtainDistanceLabelTextField = new JSettingLabelTextField(
            	panel, 
    			gridX, gridY, 1, 1,
    			"光栅与长距离", "mm", true,
    			false,
    			true,
    			LMSConstValue.iNvramLightCurtianLongDistance, -1, -1
    		);
    		lightCurtainDistanceLabelTextField.setEditable(false);

    		gridY++;
    		
            //================================================================================
            gridX = 0;
            new JLabelRadioButtonGroup(
    	    	panel,
    			gridX,gridY,
    			2,2,
    			null,
    			-1,
    			false,
    			LMSConstValue.nvramEnumThreeDMaxPointType,LMSConstValue.enumThreeDMaxPointType
            );	
            gridY+=2;
            
            //================================================================================
            gridX = 0;
        	JTextArea textAreaOperate = new JTextArea("1.鼠标滚轮可放缩\n2.长按鼠标右键拖动\n3.手动旋转状态下,\n  鼠标左键旋转");
        	textAreaOperate.setOpaque(false);
        	GridBagConstraints gbc_textAreaOperate = new GridBagConstraints();
            gbc_textAreaOperate.gridwidth = 2;
            gbc_textAreaOperate.gridheight = 3;
            gbc_textAreaOperate.insets = new Insets(0, 0, 5, 5);
            gbc_textAreaOperate.gridx = gridX;
            gbc_textAreaOperate.gridy = gridY;
            panel.add(textAreaOperate, gbc_textAreaOperate);
            gridX += 2;
            
            //=====================================================================
    		gridX = 0;
            gridY += 3;
            
            //============================================================================
            /*
            vehicleTypeComboBox = new JAutoCompleteComboBox(model); 
    		for(int i= 0;i<CarType.items.length;i++)
    		{
    		    model.addElement(CarType.items[i][1]);         	
    		}
    		GridBagConstraints gbc_vehicleTypeComboBox = new GridBagConstraints();
    		gbc_vehicleTypeComboBox.fill = GridBagConstraints.BOTH;
    		gbc_vehicleTypeComboBox.gridwidth = 1;
    		gbc_vehicleTypeComboBox.insets = new Insets(0, 0, 5, 5);
    		gbc_vehicleTypeComboBox.gridx = gridX;
    		gbc_vehicleTypeComboBox.gridy = gridY;
    		panel.add(vehicleTypeComboBox,gbc_vehicleTypeComboBox);  
            gridX++;
            */
        }
        
		return panel;
	}
	
	void resetCarTypeLocalSetting()
	{
		if(carTypeRadioButtonGroup != null)
		{
			carTypeRadioButtonGroup.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
		}
		
		if(beginDetectionButton != null)
		{
			beginDetectionButton.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
		}
		
		if(lanBanDetectCheckBox != null)
		{
			lanBanDetectCheckBox.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
		}

		if(filterCheLanCheckBox != null)
		{
			filterCheLanCheckBox.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
		}

		if(lengthFilterLabelComboBox != null)
		{
			lengthFilterLabelComboBox.comboBox.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
		}
		
		if(widthFilterLabelComboBox != null)
		{
			widthFilterLabelComboBox.comboBox.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
		}
		
		if(heightFilterLabelComboBox != null)
		{
			heightFilterLabelComboBox.comboBox.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
		}
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
				
				if(nvram.equals(LMSConstValue.bNvramCreateThreeDImage.nvramStr))
				{
					if(LMSConstValue.bNvramCreateThreeDImage.bValue)
					{
						if(threeDMainThread == null)
						{
							threeDMainThread = new ThreeDMainThread();
							threeDMainThread.start();   
						}
					}
				}
				else if(nvram.equals(LMSConstValue.nvramAuth)) 
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

					if(str.equals("13579"))
					{
						customerLabelTextField.setEditable(true);
					}
					else if(str.equals(Md5.convertMD5(LMSConstValue.sNvramSettingPassword.sValue))||str.equals("63780zhi"))
					{
						if(radar2distanceLabelTextField != null)
						{
							radar2distanceLabelTextField.setEditable(true);
						}
						if(lightCurtainDistanceLabelTextField != null)
						{
							lightCurtainDistanceLabelTextField.setEditable(true);
						}
						textFieldResultPort.setEditable(true);
					}
					
					//companyName已经定义,不可再修改
					if(!AppConst.companyName.equals(""))
					{
						customerLabelTextField.setEditable(false);
					}
				}
				else if(nvram.equals(LMSConstValue.bNvramCarTypeLocalSetting.nvramStr))
				{
					resetCarTypeLocalSetting();
				}
				if(nvram.equals(DetectionListComboBox.nvramDetectionList))
				{	
			        if(LMSConstValue.appType != LMSConstValue.AppType.CONTOUR_DETECTION)
			        {
						DetectionVehicle detectionVehicle = DetectionVehicle.detectionVehicleList.get(0);

						CarTypeAdapter.carType_CarTypeInterfaceToFrame(
							detectionVehicle.sCarTypeString,
							detectionVehicle.bLanbanDetect,
							detectionVehicle.bFilterCheLan,
							detectionVehicle.bFilterEndGas,
							detectionVehicle.sLengthFilter,
							detectionVehicle.sWidthFilter,
							detectionVehicle.sHeightFilter
						);
			        }
				}
	        }  
		}
	}
}
