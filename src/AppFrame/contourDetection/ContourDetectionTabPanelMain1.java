package AppFrame.contourDetection;

import http.WebService.WebService;
import http.WebService.WebServiceLine;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import AppBase.appBase.CarTypeAdapter;
import AppFrame.contourDetection.ContourDetectionDataBaseConst.MainPanelCarType;
import AppFrame.widgets.DetectionListComboBox;
import AppFrame.widgets.JAdsustFrame;
import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JCheckBoxNvram;
import AppFrame.widgets.JLabelComboBox;
import AppFrame.widgets.JLabelRadioButtonGroup;
import AppFrame.widgets.JSettingLabelTextField;
import AppFrame.widgets.OperatorComboBox;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import CarDetect.CarDetectSetting;
import CustomerProtocol.CustomerProtocol;
import CustomerProtocol.CustomerProtocol_DB_HF_SRF;
import CustomerProtocol.CustomerProtocol_DB_JSYY;
import CustomerProtocol.CustomerProtocol_WS;
import CustomerProtocol.CustomerProtocol_WS_SDGC;
import CustomerProtocol.DetectionVehicle;
import CustomerProtocol.CustomerProtocol_WS.EnumWSType;

public class ContourDetectionTabPanelMain1 extends ContourDetectionTabPanelMain{
	private final static String DEBUG_TAG="ContourDetectionTabPanelMain1";
  	
	JAdsustFrame         aujustFrame	;
	JButton flashDetectionButton;
	GridBagConstraints gbc_flashDetectionButton;
	JButton upLoadButton;
	GridBagConstraints gbc_upLoadButton;
	JButtonBoolean onlyShowNotDetectedButton;
	
	ContourDetectionDataBaseConst.MainPanelCarType _carType;
	
    public ContourDetectionTabPanelMain1(JFrame frame,ContourDetectionDataBaseConst.MainPanelType type,MainPanelCarType carType)
    {
    	super(frame, type, carType);
    	
    	_carType = carType;
    	
		//======================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
    }

	public JSplitPane createTab() 
	{		
		//======================================================
		JSplitPane mainTab = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		JSplitPane mainSplitPanel2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainTab.setLeftComponent(createMessagePanel());		
		mainTab.setRightComponent(mainSplitPanel2);
		
		//=====================================================================		
		JSplitPane mainSplitPanel3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainSplitPanel2.setLeftComponent(mainSplitPanel3);
		mainSplitPanel2.setRightComponent(createMainDetectSettingPanel());

		//=====================================================================		
		JSplitPane mainSplitPanel4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainSplitPanel3.setLeftComponent(mainSplitPanel4);
		mainSplitPanel3.setRightComponent(createDetectParameterPanel());
		
		//=====================================================================
		mainSplitPanel4.setLeftComponent(createMainDetectValuePanel());
		mainSplitPanel4.setRightComponent(createMainDetectImagePanel());

		return mainTab;
	}
	
	JPanel detectionListPanel;
	void createAutoGetDetectionList(JPanel panel,int gridX,int gridY)
	{
		detectionListPanel = panel;
		
		JLabel detectionListLabel = new JLabel("待检队列:");  
		GridBagConstraints gbc_detectionListLabel = new GridBagConstraints();
		gbc_detectionListLabel.fill = GridBagConstraints.BOTH;
		gbc_detectionListLabel.gridwidth = 1;
		gbc_detectionListLabel.insets = new Insets(0, 0, 5, 5);
		gbc_detectionListLabel.gridx = gridX;
		gbc_detectionListLabel.gridy = gridY;
        panel.add(detectionListLabel,gbc_detectionListLabel);  
        gridX++;
		
        detectionListComboBox = new DetectionListComboBox(_carType);  
		GridBagConstraints gbc_detectionListComboBox = new GridBagConstraints();
		gbc_detectionListComboBox.fill = GridBagConstraints.BOTH;
		gbc_detectionListComboBox.gridwidth = 1;
		gbc_detectionListComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_detectionListComboBox.gridx = gridX;
		gbc_detectionListComboBox.gridy = gridY;
        panel.add(detectionListComboBox,gbc_detectionListComboBox);  
        gridX++;
        detectionListComboBox.addItemListener(new ItemListener() {  
            @Override  
            public void itemStateChanged(ItemEvent e) {  
                // TODO Auto-generated method stub  
                String item = e.getItem().toString();  
                int stateChange = e.getStateChange();  
                if (stateChange == ItemEvent.SELECTED) {  
					DetectionVehicle detectionVehicle = (DetectionVehicle)detectionListComboBox.getSelectedItem();
   					
					serialNumLabelTextField.setTextFieldText(detectionVehicle.sSerialNum);
					vehicleNumLabelTextField.setTextFieldText(detectionVehicle.sVehicleNum);
					vehicleNumTypeLabelTextField.setTextFieldText(detectionVehicle.sVehicleNumType);
					
//					vehicleTypeComboBox.addItem(detectionVehicle.sVehicleType);
					vehicleTypeLabelTextField.setTextFieldText(detectionVehicle.sVehicleType);
					
					vehicleBrandLabelTextField.setTextFieldText(detectionVehicle.sVehicleBrand);
					vehicleIDLabelTextField.setTextFieldText(detectionVehicle.sVehicleID);
					vehicleMotorIDLabelTextField.setTextFieldText(detectionVehicle.sMotorID);
					if(detectionVehicle.sNewOrOld != null && detectionVehicle.sNewOrOld.endsWith("注册车"))
					{
						bNvramNewCar.bValue = true;
					}
					else
					{
						bNvramNewCar.bValue = false;						
					}
					HashMap<String, Comparable> eventExtraNewCar = new HashMap<String, Comparable>();
					eventExtraNewCar.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, bNvramNewCar.nvramStr);
					eventExtraNewCar.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtraNewCar);

					if(detectionVehicle.sOperatorName != null && !detectionVehicle.sOperatorName.equals(""))
					{
						operatorComboBox.generateComboBoxFromServer(detectionVehicle.sOperatorName);
					}
					else
					{
						operatorComboBox.generateComboBox();						
					}
					
					jSettingLabelTextFieldOriginalLength.setTextFieldText(detectionVehicle.sOriginalCarLength);
					jSettingLabelTextFieldOriginalWidth.setTextFieldText(detectionVehicle.sOriginalCarWidth);
					jSettingLabelTextFieldOriginalHeight.setTextFieldText(detectionVehicle.sOriginalCarHeight);
					if(detectionVehicle.sOriginalLanBanHeight != null && !detectionVehicle.sOriginalLanBanHeight.equals(""))
					{
						jSettingLabelTextFieldOriginalLanbanHeight.setTextFieldText(detectionVehicle.sOriginalLanBanHeight);
					}
					if(detectionVehicle.sOriginalZJ != null && !detectionVehicle.sOriginalZJ.equals(""))
					{
						jSettingLabelTextFieldOriginalZJ.setTextFieldText(detectionVehicle.sOriginalZJ);
					}

					//=================================================================================
					CarTypeAdapter.carType_CarTypeInterfaceToFrame(
						detectionVehicle.sCarTypeString,
						detectionVehicle.bLanbanDetect,
						detectionVehicle.bFilterCheLan,
						detectionVehicle.bFilterEndGas,
						detectionVehicle.sLengthFilter,
						detectionVehicle.sWidthFilter,
						detectionVehicle.sHeightFilter
					);
                }else if (stateChange == ItemEvent.DESELECTED) {  

                }else {  

                }  
            }  
        });  
        
		flashDetectionButton = new JButton("刷新待检队列");
	    gbc_flashDetectionButton = new GridBagConstraints();
	    gbc_flashDetectionButton.fill = GridBagConstraints.BOTH;
	    gbc_flashDetectionButton.gridwidth = 2;
	    gbc_flashDetectionButton.insets = new Insets(0, 0, 5, 5);
	    gbc_flashDetectionButton.gridx = gridX;
	    gbc_flashDetectionButton.gridy = gridY;
	    flashDetectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				if(CustomerProtocol.customerProtocol != null)
				{
					CustomerProtocol.customerProtocol.queryDetectionList(panelMain);
					
					if(DetectionVehicle.bRefreshAsyc == false && DetectionVehicle.detectionVehicleList.size() > 0)
					{			
						CustomerProtocol.notifyDetectionList();	
					}	
				}
			}
		});
		gridX += gbc_flashDetectionButton.gridwidth;
				
		//---------------------------------------------------------------------------
		upLoadButton = new JButton("结果上传");
	    gbc_upLoadButton = new GridBagConstraints();
	    gbc_upLoadButton.fill = GridBagConstraints.BOTH;
	    gbc_upLoadButton.gridwidth = 1;
	    gbc_upLoadButton.insets = new Insets(0, 0, 5, 5);
	    gbc_upLoadButton.gridx = gridX;
	    gbc_upLoadButton.gridy = gridY;
	    upLoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jdbc_mysql_add();

				HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.bNvramResultResend.nvramStr);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtra);
				
				if(CustomerProtocol.customerProtocol != null)
				{
					boolean bResult = CustomerProtocol.customerProtocol.updateDetectionResult(panelMain);
					
					if(bResult == true)
						LMSLog.warningDialog("结果上传", "结果上传完成");
//					else
//						LMSLog.warningDialog("结果上传", "结果上传失败");						
				}			
			}
		});
		gridX++;
	
		onlyShowNotDetectedButton = new JButtonBoolean(
			detectionListPanel,
			gridX, gridY, 2,
			"只显示未检测车辆(打开)",
			"只显示未检测车辆(关闭)",
			LMSConstValue.bNvramOnlyShowNotDetectdCar,
			-1
		);
 		gridX+=2;

        reflashDetectListPanel();
	}

	void reflashDetectListPanel()
	{
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC))
		{
			detectionListPanel.add(flashDetectionButton,gbc_flashDetectionButton);
			detectionListPanel.add(upLoadButton);
			detectionListPanel.remove(onlyShowNotDetectedButton);
		}
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_GXCZJ))
		{
			detectionListPanel.add(flashDetectionButton,gbc_flashDetectionButton);
			detectionListPanel.add(upLoadButton);
			detectionListPanel.remove(onlyShowNotDetectedButton);
		}
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HF_SRF))
		{
			detectionListPanel.add(flashDetectionButton,gbc_flashDetectionButton);
			detectionListPanel.add(upLoadButton);
			detectionListPanel.add(onlyShowNotDetectedButton,onlyShowNotDetectedButton.getGBC());
		}
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSYY))
		{
			detectionListPanel.add(flashDetectionButton,gbc_flashDetectionButton);
			detectionListPanel.add(upLoadButton);
			detectionListPanel.add(onlyShowNotDetectedButton,onlyShowNotDetectedButton.getGBC());
		}
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSXSS))
		{
			detectionListPanel.add(flashDetectionButton,gbc_flashDetectionButton);
			detectionListPanel.add(upLoadButton);
			detectionListPanel.add(onlyShowNotDetectedButton,onlyShowNotDetectedButton.getGBC());
		}
		else
		{
			detectionListPanel.remove(flashDetectionButton);
			detectionListPanel.add(upLoadButton);
			detectionListPanel.remove(onlyShowNotDetectedButton);
		}		
	}
		
	JPanel messagePanel;
	JPanel createMessagePanel()
	{
		messagePanel = new JPanel();
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_FSNH)){
			File rfile = new File("D://Share/BegCTDet.ini");
			if (rfile.exists())
			{
				rfile.delete();
			}			
		}
		//设置布局
		//==========================================================
		int gridX = 0,gridY = 0;
		
		if(panelType != ContourDetectionDataBaseConst.MainPanelType.SEARCH_SINGLE_RESULT)
		{
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{40, 120, 40, 120, 40, 120, 40, 80, 40, 150};
			gridBagLayout.rowHeights = new int[]{25,25,25};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
			messagePanel.setLayout(gridBagLayout);
			
			createAutoGetDetectionList(messagePanel,gridX,gridY);
			
			gridX = 0;
			gridY++;
		}
		else
		{
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{40, 120, 40, 120, 40, 120, 40, 80, 40, 150};
			gridBagLayout.rowHeights = new int[]{25, 25};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			messagePanel.setLayout(gridBagLayout);
		}
		
		//=====================================================================
		serialNumLabelTextField = new JSettingLabelTextField(
			messagePanel,
			gridX, gridY, 1, 1,
			serialNumLabelStr,":",false,
			false,
			false,
			null, -1, -1
		);
		gridX+=2;
		
		vehicleNumLabelTextField = new JSettingLabelTextField(
			messagePanel,
			gridX, gridY, 1, 1,
			vehicleNumLabelStr,":",false,
			false,
			false,
			null, -1, -1
		);
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_GXCZJ)){
			vehicleNumLabelTextField.setTextFieldText("桂");			
		}
		gridX+=2;
		
		vehicleNumTypeLabelTextField = new JSettingLabelTextField(
			messagePanel,
			gridX, gridY, 1, 1,
			vehicleNumTypeLabelStr,":",false,
			false,
			false,
			null, -1, -1
		);
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_GXCZJ)){
			vehicleNumTypeLabelTextField.setTextFieldText("01");			
		}
		gridX+=2;
		
		//============================================================================
		newCarButton =  new JButtonBoolean(
			messagePanel,
			gridX,gridY,2,
			"注册车(判定标准1%,或±50mm)",
			"在用车(判定标准2%,或±100mm)",
			bNvramNewCar,
			-1
		);
 		gridX+=2;

		//---------------------------------------------------------------------------
		if(panelType != ContourDetectionDataBaseConst.MainPanelType.SEARCH_SINGLE_RESULT)
		{
			beginDetectionButton = new JButtonBoolean(
				detectionListPanel,
				gridX, gridY, 2,
				LMSConstValue.strPauseDetect,
				LMSConstValue.strDetecting,
				CarTypeAdapter.bNvramPauseDetect,
				-1
			);
			beginDetectionButton.resetTextBackGroundColor(Color.RED, Color.GREEN);
			gridX++;
		}
		
		//=====================================================================
		gridX = 0;
		gridY++;
				
		vehicleBrandLabelTextField = new JSettingLabelTextField(
			messagePanel,
			gridX, gridY, 1, 1,
			vehicleBrandLabelStr,":",false,
			false,
			false,
			null, -1, -1	
		);
		gridX+=2;
		
		//---------------------------------------------------------		
		vehicleIDLabelTextField = new JSettingLabelTextField(
			messagePanel,
			gridX, gridY, 1, 1,
			vehicleIDLabelStr,":",false,
			false,
			false,
			null, -1, -1	
		);
		gridX+=2;
		
		//---------------------------------------------------------		
		vehicleMotorIDLabelTextField = new JSettingLabelTextField(
			messagePanel,
			gridX, gridY, 1, 1,
			vehicleMotorIDLabelStr,":",false,
			false,
			false,
			null, -1, -1	
		);
		gridX+=2;
		
		//---------------------------------------------------------
		JLabel detectorNameLabel = new JLabel(ContourDetectionTabPanelSetting.operatorNameLabel.getText()+":");  
		GridBagConstraints gbc_detectorNameLabel = new GridBagConstraints();
		gbc_detectorNameLabel.fill = GridBagConstraints.BOTH;
		gbc_detectorNameLabel.gridwidth = 1;
		gbc_detectorNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_detectorNameLabel.gridx = gridX;
		gbc_detectorNameLabel.gridy = gridY;
		messagePanel.add(detectorNameLabel,gbc_detectorNameLabel);  
        gridX++;
		
		operatorComboBox = new OperatorComboBox();  
		GridBagConstraints gbc_detectorComboBox = new GridBagConstraints();
		gbc_detectorComboBox.fill = GridBagConstraints.BOTH;
		gbc_detectorComboBox.gridwidth = 1;
		gbc_detectorComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_detectorComboBox.gridx = gridX;
		gbc_detectorComboBox.gridy = gridY;
		messagePanel.add(operatorComboBox,gbc_detectorComboBox);  
        gridX++;
        
 		//=================================================================
        /*
		JLabel vehicleTypeLabel = new JLabel(vehicleTypeLabelStr);  
		GridBagConstraints gbc_vehicleTypeLabel = new GridBagConstraints();
		gbc_vehicleTypeLabel.fill = GridBagConstraints.BOTH;
		gbc_vehicleTypeLabel.gridwidth = 1;
		gbc_vehicleTypeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_vehicleTypeLabel.gridx = gridX;
		gbc_vehicleTypeLabel.gridy = gridY;
		messagePanel.add(vehicleTypeLabel,gbc_vehicleTypeLabel);  
		gridX++;
		
		if(carType == ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE)
		{
			DefaultComboBoxModel   model   =   new   DefaultComboBoxModel(); 
			vehicleTypeComboBox = new JAutoCompleteComboBox(model); 
			for(int i= 0;i<CarType.items.length;i++)
			{
			    model.addElement(CarType.items[i][1]);         	
			}
		}
		else
		{
			vehicleTypeComboBox = CarDetectFrameMainTab.vehicleTypeComboBox;
		}
		GridBagConstraints gbc_vehicleTypeComboBox = new GridBagConstraints();
		gbc_vehicleTypeComboBox.fill = GridBagConstraints.BOTH;
		gbc_vehicleTypeComboBox.gridwidth = 1;
		gbc_vehicleTypeComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_vehicleTypeComboBox.gridx = gridX;
		gbc_vehicleTypeComboBox.gridy = gridY;
		messagePanel.add(vehicleTypeComboBox,gbc_vehicleTypeComboBox);  
		*/
        vehicleTypeLabelTextField = new JSettingLabelTextField(
			messagePanel,
			gridX, gridY, 1, 1,
			vehicleTypeLabelStr,":",false,
			false,
			false,
			null, -1, -1	
		);
		gridX+=2;
        
        gridX++;           
		return messagePanel;
	}
	
	private JPanel createDetectParameterPanel() {	
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 80};
		gridBagLayout.rowHeights = new int[]{25,25,25,25,25,25,25,25,25,25,25,25};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//============================================================================
		int gridX = 0;
		int gridY = 0;
				
		if(
			LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC)
			||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HGJN)
		)
		{
			humanAdjustButtonBoolean = new JButtonBoolean(
				panel,
				gridX, gridY, 2,
				"人工修正(打开)",
				"人工修正(关闭)",
				bNvramHumanAdjust,
				-1
			);
	 		gridY++;
	 		gridX=0;
	 		/*		
	 		beiZhuLabel = new JTextArea("备注:");
			GridBagConstraints gbc_beiZhuLabelTextArea = new GridBagConstraints();
			gbc_beiZhuLabelTextArea.fill = GridBagConstraints.BOTH;
			gbc_beiZhuLabelTextArea.gridwidth = 2;
			gbc_beiZhuLabelTextArea.gridheight = 1;
			gbc_beiZhuLabelTextArea.gridx = gridX;
			gbc_beiZhuLabelTextArea.gridy = gridY;
			panel.add(beiZhuLabel, gbc_beiZhuLabelTextArea);
			beiZhuLabel.setEditable(false);
			beiZhuLabel.setLineWrap(true);        //激活自动换行功能 
			beiZhuLabel.setWrapStyleWord(true);            // 激活断行不断字功能
			beiZhuLabel.setOpaque(false);
			gridY+=gbc_beiZhuLabelTextArea.gridheight;
			gridX=0;
			
			beiZhuTextArea = new JTextArea();
			GridBagConstraints gbc_beiZhuTextArea = new GridBagConstraints();
			gbc_beiZhuTextArea.fill = GridBagConstraints.BOTH;
			gbc_beiZhuTextArea.insets = new Insets(0, 0, 5, 5);
			gbc_beiZhuTextArea.gridwidth = 2;
			gbc_beiZhuTextArea.gridheight = 4;
			gbc_beiZhuTextArea.gridx = gridX;
			gbc_beiZhuTextArea.gridy = gridY;
			panel.add(beiZhuTextArea, gbc_beiZhuTextArea);
			beiZhuTextArea.setLineWrap(true);        //激活自动换行功能 
			beiZhuTextArea.setWrapStyleWord(true);  */          // 激活断行不断字功能
			beiZhuTextArea.addFocusListener(new FocusListener(){			
				public void focusLost(FocusEvent e) {
				}
		
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
							
				}			
			});
			//gridY+=gbc_beiZhuTextArea.gridheight;
			//gridX=0;
		}

 		//===============================================================
		if(panelType == ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT
			&&carType == ContourDetectionDataBaseConst.MainPanelCarType.GUACHE)
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
        
		return panel;
	}
	
	void resetCarTypeLocalSetting()
	{
		if(carTypeRadioButtonGroup != null)
		{
			carTypeRadioButtonGroup.setEnabled(LMSConstValue.bNvramCarTypeLocalSetting.bValue);
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
	
	private JPanel createMainDetectValuePanel() {	
		JPanel panel = new JPanel();
	
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
//		gridBagLayout.columnWidths = new int[]{85, 85, 85, 85, 85, 85, 85, 85, 85, 85, 85};
		gridBagLayout.columnWidths = new int[]{150, 150, 150, 150, 150, 150};
		gridBagLayout.rowHeights = new int[]{25,25,25,25,25};
//		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		gridX = 1;
		JLabel longLabel = new JLabel(carLengthStr);  
		GridBagConstraints gbc_longLabel = new GridBagConstraints();
		gbc_longLabel.fill = GridBagConstraints.BOTH;
		gbc_longLabel.gridwidth = 1;
		gbc_longLabel.insets = new Insets(0, 0, 5, 5);
		gbc_longLabel.gridx = gridX;
		gbc_longLabel.gridy = gridY;
        panel.add(longLabel,gbc_longLabel);  
        gridX++;
        
        JLabel widthLabel = new JLabel(carWidthStr);  
		GridBagConstraints gbc_widthLabel = new GridBagConstraints();
		gbc_widthLabel.fill = GridBagConstraints.BOTH;
		gbc_widthLabel.gridwidth = 1;
		gbc_widthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_widthLabel.gridx = gridX;
		gbc_widthLabel.gridy = gridY;
        panel.add(widthLabel,gbc_widthLabel);  
        gridX++;
        
        JLabel heightLabel = new JLabel(carHeightStr);  
 		GridBagConstraints gbc_heightLabel = new GridBagConstraints();
 		gbc_heightLabel.fill = GridBagConstraints.BOTH;
 		gbc_heightLabel.gridwidth = 1;
 		gbc_heightLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_heightLabel.gridx = gridX;
 		gbc_heightLabel.gridy = gridY;
 		panel.add(heightLabel,gbc_heightLabel);  
 		gridX++;
 		
 		JLabel lanbanLabel = new JLabel(carLanbanStr);  
 		GridBagConstraints gbc_lanbanLabel = new GridBagConstraints();
 		gbc_lanbanLabel.fill = GridBagConstraints.BOTH;
 		gbc_lanbanLabel.gridwidth = 1;
 		gbc_lanbanLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_lanbanLabel.gridx = gridX;
 		gbc_lanbanLabel.gridy = gridY;
 		panel.add(lanbanLabel,gbc_lanbanLabel);  
 		gridX++;
 		
 		/*
 		JLabel xzjLabel = new JLabel(carXZJStr);  
 		GridBagConstraints gbc_xzjLabel = new GridBagConstraints();
 		gbc_xzjLabel.fill = GridBagConstraints.BOTH;
 		gbc_xzjLabel.gridwidth = 1;
 		gbc_xzjLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_xzjLabel.gridx = gridX;
 		gbc_xzjLabel.gridy = gridY;
 		panel.add(xzjLabel,gbc_xzjLabel);  
 		gridX++;
 		
 		for(int i=0;i<MAX_Z;i++)
 		{
	 		JLabel zLabel = new JLabel("轴距"+(i+1));  
	 		GridBagConstraints gbc_zLabel = new GridBagConstraints();
	 		gbc_zLabel.fill = GridBagConstraints.BOTH;
	 		gbc_zLabel.gridwidth = 1;
	 		gbc_zLabel.insets = new Insets(0, 0, 5, 5);
	 		gbc_zLabel.gridx = gridX;
	 		gbc_zLabel.gridy = gridY;
	 		panel.add(zLabel,gbc_zLabel);  
	 		gridX++;
 		}
 		*/
 		JLabel zJLabel = new JLabel("轴距");  
 		GridBagConstraints gbc_zJLabel = new GridBagConstraints();
 		gbc_zJLabel.fill = GridBagConstraints.BOTH;
 		gbc_zJLabel.gridwidth = 1;
 		gbc_zJLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_zJLabel.gridx = gridX;
 		gbc_zJLabel.gridy = gridY;
 		panel.add(zJLabel,gbc_zJLabel);  
 		gridX++;
 		
 		//=====================================================================
 		gridY++;
 		gridX = 0;
 		
 		JLabel originalLabel = new JLabel(originalCarStr+"(mm)");
 		GridBagConstraints gbc_originalLabel = new GridBagConstraints();
 		gbc_originalLabel.fill = GridBagConstraints.BOTH;
 		gbc_originalLabel.gridwidth = 1;
 		gbc_originalLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_originalLabel.gridx = gridX;
 		gbc_originalLabel.gridy = gridY;
 		panel.add(originalLabel,gbc_originalLabel);  
 		gridX++;
 		
 		jSettingLabelTextFieldOriginalLength = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramOriginalLength, -1, -1
		);
 		jSettingLabelTextFieldOriginalLength.setKeyNumOnly();
		gridX++;
 		
 		jSettingLabelTextFieldOriginalWidth = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramOriginalWidth, -1, -1
		);
 		jSettingLabelTextFieldOriginalWidth.setKeyNumOnly();
		gridX++;
 		
		jSettingLabelTextFieldOriginalHeight = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramOriginalHeight, -1, -1
		);
		jSettingLabelTextFieldOriginalHeight.setKeyNumOnly();
		gridX++;

		jSettingLabelTextFieldOriginalLanbanHeight = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramOriginalLanbanHeight, -1, -1
		);
		jSettingLabelTextFieldOriginalLanbanHeight.setKeyNumOnly();
		gridX++;

		/*
		jSettingLabelTextFieldOriginalXZJ = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramOriginalXZJ, -1, -1
		);
		jSettingLabelTextFieldOriginalXZJ.setKeyNumOnly();
		gridX++;
 		
 		for(int i=0;i<MAX_Z;i++)
 		{
 			jSettingLabelTextFieldOriginalZ[i] = new JSettingLabelTextField(
				panel,
				gridX, gridY, 1, 1,
				null,null,false,
				false,
				false,
				iNvramOriginalZ[i], -1, -1
			);
			jSettingLabelTextFieldOriginalZ[i].setKeyNumOnly();
			gridX++;
 		}
 		*/
		jSettingLabelTextFieldOriginalZJ = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramOriginalZJ, -1, -1
		);
		jSettingLabelTextFieldOriginalZJ.setKeyNumOnly();
		gridX++;
		
 		//=====================================================================
 		gridY++;
 		gridX = 0;
 		
 		JLabel detectLabel = new JLabel(detectCarStr+"(mm)");
 		GridBagConstraints gbc_detectLabel = new GridBagConstraints();
 		gbc_detectLabel.fill = GridBagConstraints.BOTH;
 		gbc_detectLabel.gridwidth = 1;
 		gbc_detectLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_detectLabel.gridx = gridX;
 		gbc_detectLabel.gridy = gridY;
 		panel.add(detectLabel,gbc_detectLabel);  
 		gridX++;
 		
 		jSettingLabelTextFieldDetectLength = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramDetectLength, -1, -1
		);
 		jSettingLabelTextFieldDetectLength.setKeyNumOnly();
 		jSettingLabelTextFieldDetectLength.setEditable(false);
		gridX++;
		
		jSettingLabelTextFieldDetectWidth = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramDetectWidth,-1, -1
		);
		jSettingLabelTextFieldDetectWidth.setKeyNumOnly();
		jSettingLabelTextFieldDetectWidth.setEditable(false);
		gridX++;
 	
		jSettingLabelTextFieldDetectHeight = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramDetectHeight, -1, -1
		);
		jSettingLabelTextFieldDetectHeight.setKeyNumOnly();
		jSettingLabelTextFieldDetectHeight.setEditable(false);
		gridX++;
 	
		jSettingLabelTextFieldDetectLanbanHeight = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramDetectLanbanHeight, -1, -1
		);
		jSettingLabelTextFieldDetectLanbanHeight.setKeyNumOnly();
		jSettingLabelTextFieldDetectLanbanHeight.setEditable(false);
		gridX++;
 	
		/*
		jSettingLabelTextFieldDetectXZJ = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramDetectXZJ, -1, -1
		);
		jSettingLabelTextFieldDetectXZJ.setKeyNumOnly();
//		jSettingLabelTextFieldDetectXZJ.setEditable(false);
		gridX++;

 		for(int i=0;i<MAX_Z;i++)
 		{
 			jSettingLabelTextFieldDetectZ[i] = new JSettingLabelTextField(
				panel,
				gridX, gridY, 1, 1,
				null,null,false,
				false,
				false,
				iNvramDetectZ[i], -1, -1
			);
			jSettingLabelTextFieldDetectZ[i].setKeyNumOnly();
 //			jSettingLabelTextFieldDetectZ[i].setEditable(false);
			gridX++;
 		}
 		*/
		jSettingLabelTextFieldDetectZJ = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			iNvramDetectZJ, -1, -1
		);
		jSettingLabelTextFieldDetectZJ.setKeyNumOnly();
 		jSettingLabelTextFieldDetectZJ.setEditable(false);
		gridX++;
		
 		//=====================================================================
 		gridY++;
 		gridX = 0;
 		
 		JLabel absoultDifLabel = new JLabel(absoluteCarStr+"(mm)");
 		GridBagConstraints gbc_absoultDifLabel = new GridBagConstraints();
 		gbc_absoultDifLabel.fill = GridBagConstraints.BOTH;
 		gbc_absoultDifLabel.gridwidth = 1;
 		gbc_absoultDifLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_absoultDifLabel.gridx = gridX;
 		gbc_absoultDifLabel.gridy = gridY;
 		panel.add(absoultDifLabel,gbc_absoultDifLabel);  
 		gridX++;
 		
 		absoluteDifLengthTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		absoluteDifLengthTextField.setEditable(false);
 		gridX++;

 		absoluteDifWidthTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		absoluteDifWidthTextField.setEditable(false);
 		gridX++;

 		absoluteDifHeightTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		absoluteDifHeightTextField.setEditable(false); 		
 		gridX++;
 		
 		absoluteDifLanbanHeightTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		absoluteDifLanbanHeightTextField.setEditable(false);
 		gridX++;
 		
 		/*
 		absoluteDifXZJTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		absoluteDifXZJTextField.setEditable(false);
 		gridX++;
 		
 		for(int i=0;i<MAX_Z;i++)
 		{
 			absoluteDifZTextField[i] = new JSettingLabelTextField(
				panel,
				gridX, gridY, 1, 1,
				null,null,false,
				false,
				false,
				null, -1, -1
			);
 			absoluteDifZTextField[i].setEditable(false);
	 		gridX++;
 		}
 		*/
 		absoluteDifZJTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		absoluteDifZJTextField.setEditable(false);
 		gridX++;
 		
		//=====================================================================
 		gridY++;
 		gridX = 0;
 		
 		JLabel compareDifLabel = new JLabel(compareCarStr+"(%)");
 		GridBagConstraints gbc_compareDifLabel = new GridBagConstraints();
 		gbc_compareDifLabel.fill = GridBagConstraints.BOTH;
 		gbc_compareDifLabel.gridwidth = 1;
 		gbc_compareDifLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_compareDifLabel.gridx = gridX;
 		gbc_compareDifLabel.gridy = gridY;
 		panel.add(compareDifLabel,gbc_compareDifLabel);  
 		gridX++;
 		
 		compareDifLengthTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		compareDifLengthTextField.setEditable(false);
 		gridX++;
 		
 		compareDifWidthTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		compareDifWidthTextField.setEditable(false);
 		gridX++;
 		
 		compareDifHeightTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		compareDifHeightTextField.setEditable(false);
 		gridX++;
 		
 		compareDifLanbanHeightTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		compareDifLanbanHeightTextField.setEditable(false);
 		gridX++;
 	
 		/*
 		compareDifXZJTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		compareDifXZJTextField.setEditable(false);
 		gridX++;
 		
 		for(int i=0;i<MAX_Z;i++)
 		{
 			compareDifZTextField[i] = new JSettingLabelTextField(
				panel,
				gridX, gridY, 1, 1,
				null,null,false,
				false,
				false,
				null, -1, -1
			);
 			compareDifZTextField[i].setEditable(false);
	 		gridX++;	 		
 		}
 		*/
 		compareDifZJTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		compareDifZJTextField.setEditable(false);
 		gridX++;
 		
		//=====================================================================
 		gridY++;
 		gridX = 0;
 		
 		JLabel singleDecisionLabel = new JLabel(singleDecisionStr);
 		GridBagConstraints gbc_singleDecisionLabel = new GridBagConstraints();
 		gbc_singleDecisionLabel.fill = GridBagConstraints.BOTH;
 		gbc_singleDecisionLabel.gridwidth = 1;
 		gbc_singleDecisionLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_singleDecisionLabel.gridx = gridX;
 		gbc_singleDecisionLabel.gridy = gridY;
 		panel.add(singleDecisionLabel,gbc_singleDecisionLabel);  
 		gridX++;

 		singleDecisionLengthTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		singleDecisionLengthTextField.setEditable(false);
 		gridX++;
 		
 		singleDecisionWidthTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		singleDecisionWidthTextField.setEditable(false);
 		gridX++;
 		
 		singleDecisionHeightTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		singleDecisionHeightTextField.setEditable(false);
 		gridX++;
 		
 		singleDecisionLanbanHeightLabelTextField = new JSettingLabelTextField(
 			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		); 
 		singleDecisionLanbanHeightLabelTextField.setEditable(false);
 		if(LMSConstValue.bNvramLanBanSingleDecision.bValue == false)
 		{
 			singleDecisionLanbanHeightLabelTextField.textField.setHorizontalAlignment(SwingConstants.CENTER);
 			singleDecisionLanbanHeightLabelTextField.setTextFieldText("/");
 		}
 		gridX++;
 		
 		/*
 		for(int i=0;i<MAX_Z;i++)
 		{
 			singleDecisionZTextField[i] = new JTextField();  
	 		GridBagConstraints gbc_singleDecisionZ = new GridBagConstraints();
	 		gbc_singleDecisionZ.fill = GridBagConstraints.BOTH;
	 		gbc_singleDecisionZ.gridwidth = 1;
	 		gbc_singleDecisionZ.insets = new Insets(0, 0, 5, 5);
	 		gbc_singleDecisionZ.gridx = gridX;
	 		gbc_singleDecisionZ.gridy = gridY;
	 		panel.add(singleDecisionZTextField[i],gbc_singleDecisionZ);  
	 		gridX++;
 		}
 		*/
 		
 		singleDecisionZJLabelTextField = new JSettingLabelTextField(
 			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		); 
 		singleDecisionZJLabelTextField.setEditable(false);
 		if(LMSConstValue.bNvramZJSingleDecision.bValue == false)
 		{
 			singleDecisionZJLabelTextField.textField.setHorizontalAlignment(SwingConstants.CENTER);
 			singleDecisionZJLabelTextField.setTextFieldText("/");
 		}
 		gridX++;
 		
		//=====================================================================
 		gridY++;
 		gridX = 0;
 		
 		decisionLabel = new JLabel("检测结果");
 		GridBagConstraints gbc_DecisionLabel = new GridBagConstraints();
 		gbc_DecisionLabel.fill = GridBagConstraints.BOTH;
 		gbc_DecisionLabel.gridwidth = 1;
 		gbc_DecisionLabel.insets = new Insets(0, 0, 5, 5);
 		gbc_DecisionLabel.gridx = gridX;
 		gbc_DecisionLabel.gridy = gridY;
 		panel.add(decisionLabel,gbc_DecisionLabel);  
 		gridX++;
 		
 		decisionTextField = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			null,null,false,
			false,
			false,
			null, -1, -1
		);
 		decisionTextField.setEditable(false);
 		gridX++;
 		
 		JButtonBoolean wideMaxButtonBoolean = new JButtonBoolean(
				panel,
				gridX,gridY,1,				
				"最宽2550mm(打开)",
				"最宽2550mm(关闭)",
				LMSConstValue.bNvramCarWideMaxBoolean,
				-1
			);
 		
 		gridX++;
 		
		if(carType == ContourDetectionDataBaseConst.MainPanelCarType.GUACHE
			&&panelType == ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT)
		{
	 		gridX = 5;
	 		JButtonBoolean zjGuaCheButtonBoolean = new JButtonBoolean(
				panel,
				gridX,gridY,1,
				"挂车轴距(销轴距)",
				"挂车轴距(自身轴距)",
				LMSConstValue.bNvramZJ_GuaChe_XIAO,
				-1
			);
	 		if(
	 			!(LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
	 				||LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.XZY_1600)
	 			)
			)
	 		{
	 			zjGuaCheButtonBoolean.setEnabled(false);
	 		}
		}

		gridX+=1;
		
		return panel;
	}
	
	private JPanel createMainDetectImagePanel() {	
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 280, 40, 280, 40, 280};
		gridBagLayout.rowHeights = new int[]{180, 180};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;
		
		//================================================================
		for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
		{
			viewImageLabel[i] = new ContourImageLabel(panel,gridX,gridY,LMSConstValue.sNvramMainImageTitle[i].sValue,imageFileName[i],1,1);
			gridX+=2;
			
			if(i == 2)
			{
				gridY++;
				gridX = 0;				
			}
		}
		
		return panel;
	}

	@Override
	public JPanel createPanelTab() {
		// TODO Auto-generated method stub
		return null;
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
				if(nvram.equals(LMSConstValue.sNvramCustomer.nvramStr)) 
				{
			        reflashDetectListPanel();
				}
				else if(nvram.equals(LMSConstValue.nvramAuth)) 
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

					if(str.equals("63780zhi"))
					{
				 		
					}
				}
				else if(nvram.equals(bNvramHumanAdjust.nvramStr)) 
				{
					if(bNvramHumanAdjust.bValue)
					{
					
						if(aujustFrame == null)
						{
				 			aujustFrame = new JAdsustFrame();
						}
						
						if(aujustFrame.isVisible())
							aujustFrame.setVisible(false);
						else
							aujustFrame.setVisible(true);
						jSettingLabelTextFieldDetectLength.setEditable(true);						
				 		jSettingLabelTextFieldDetectWidth.setEditable(true);						
				 		jSettingLabelTextFieldDetectHeight.setEditable(true);						
				 		jSettingLabelTextFieldDetectLanbanHeight.setEditable(true);		
				 		/*
				 		jSettingLabelTextFieldDetectXZJ.setEditable(true);	
						for(int i=0; i<MAX_Z; i++)
						{
							jSettingLabelTextFieldDetectZ[i].setEditable(true);
						}
						*/
				 		jSettingLabelTextFieldDetectZJ.setEditable(true);
				 		
					}
					else
					{
						jSettingLabelTextFieldDetectLength.setEditable(false);						
				 		jSettingLabelTextFieldDetectWidth.setEditable(false);						
				 		jSettingLabelTextFieldDetectHeight.setEditable(false);						
				 		jSettingLabelTextFieldDetectLanbanHeight.setEditable(false);		
				 		/*
				 		jSettingLabelTextFieldDetectXZJ.setEditable(false);	
						for(int i=0; i<MAX_Z; i++)
						{
							jSettingLabelTextFieldDetectZ[i].setEditable(false);
						}
						*/
				 		jSettingLabelTextFieldDetectZJ.setEditable(false);		
				 		
				 		//===========================================================================
						beiZhuLabel.setText("备注:");
					}
				}
				else if(
					nvram.equals(iNvramDetectLength.nvramStr)
					||nvram.equals(iNvramDetectWidth.nvramStr)
					||nvram.equals(iNvramDetectHeight.nvramStr)
					||nvram.equals(iNvramDetectLanbanHeight.nvramStr)
					||nvram.equals(iNvramDetectZJ.nvramStr)
				)
				{
					if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC)
						||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HGJN)
					)
					{
						//===========================================================================
						String strBeiZhu = "";
                        String Strhumanadjust ="";
                        //===========================================================================
			    	/*	int iWidth = 0;
			    		boolean bHas = true;
			    		try {
			    			iWidth = Integer.valueOf(jSettingLabelTextFieldDetectWidth.getTextFieldText());
			    		} catch (NumberFormatException e) {
			    			LMSLog.exception(e);
			    		}
			    		if(iWidth != 0)
			    		{
			        		if(iNvramDetectWidth_RearViewMirror.iValue != iWidth)
			        		{
			        			if(bHas == true)
			        				strBeiZhu += ";";
			        			
			        			strBeiZhu += ("测量宽:"+iNvramDetectWidth_RearViewMirror.iValue);
			        			
			        			bHas = true;
			        		}
			    		}
			    		
			    		//-----------------------------------------------------------------------------
			    		int iLength = 0;
			    		try {
			    			iLength = Integer.valueOf(jSettingLabelTextFieldDetectLength.getTextFieldText());
			    		} catch (NumberFormatException e) {
			    			LMSLog.exception(e);
			    		}
			    		if(iLength != 0)
			    		{
			        		if(iNvramDetectLength_FrontMirror.iValue != iLength)
			        		{
			        			if(bHas == true)
			        				strBeiZhu += ";";
			        			
			        			strBeiZhu += ("测量长:"+iNvramDetectLength_FrontMirror.iValue);
			        			
			            		bHas = true;
			        		}
			    		}
			    		//=======================================================================
			    		int ihight = 0;
			    		try {
	    			ihight = Integer.valueOf(jSettingLabelTextFieldDetectHeight.getTextFieldText());
			    		} catch (NumberFormatException e) {
			    			LMSLog.exception(e);
			    		}
			    		if(ihight != 0)
			    		{
			        		if(iNvramDetectWidth_RearViewMirror.iValue != ihight)
			        		{
			        			if(bHas == true)
			        				strBeiZhu += ";";
			        			
			        			strBeiZhu += ("测量高:"+iNvramDetectHeight_RearViewMirror.iValue);
	        			
			            		bHas = true;
			        		}
			    		}
			    		*/
//===============================================================================================
						//----------------------------------------------------------------------
						if(iSaveDetectLength != 0 && iNvramDetectLength.iValue != iSaveDetectLength)
						{
							strBeiZhu += ("原测量长"+iSaveDetectLength+";");
							Strhumanadjust+=("修改长:"+Integer.valueOf(jSettingLabelTextFieldDetectLength.getTextFieldText())+";");
						}
							if(iSaveDetectWidth != 0 && iNvramDetectWidth.iValue != iSaveDetectWidth)
							{
								strBeiZhu += ("原测量宽"+iSaveDetectWidth+";");
								Strhumanadjust+=("修改宽:"+Integer.valueOf(jSettingLabelTextFieldDetectWidth.getTextFieldText())+";");
							}
						if(iSaveDetectHeight != 0 && iNvramDetectHeight.iValue != iSaveDetectHeight)
						{
							strBeiZhu += ("原测量高"+iSaveDetectHeight+";");
							Strhumanadjust+=("修改高:"+Integer.valueOf(jSettingLabelTextFieldDetectHeight.getTextFieldText())+";");
						}
						if(iSaveDetectLanbanHeight != 0 && iNvramDetectLanbanHeight.iValue != iSaveDetectLanbanHeight)
						{
							strBeiZhu += ("原测量栏板高"+iSaveDetectLanbanHeight+";");
							Strhumanadjust+=("修改栏板高:"+Integer.valueOf(jSettingLabelTextFieldDetectLanbanHeight.getTextFieldText())+";");
						}
						if(iSaveDetectZJ != 0 && iNvramDetectZJ.iValue != iSaveDetectZJ)
							{
							strBeiZhu += ("原测量轴距"+iSaveDetectZJ+";");
							Strhumanadjust+=("修改轴距:"+Integer.valueOf(jSettingLabelTextFieldDetectZJ.getTextFieldText())+";");
							}
					
						JAdsustFrame.JTcheckdata.setText(strBeiZhu);
						JAdsustFrame.JTadjustdata.setText(Strhumanadjust);
					}
				}
				else if(nvram.equals(LMSConstValue.bNvramLanBanSingleDecision.nvramStr)) 
				{
					if(LMSConstValue.bNvramLanBanSingleDecision.bValue == false)
					{
			 			singleDecisionLanbanHeightLabelTextField.textField.setHorizontalAlignment(SwingConstants.CENTER);
						singleDecisionLanbanHeightLabelTextField.setTextFieldText("/");
					}
					else
					{
			 			singleDecisionLanbanHeightLabelTextField.textField.setHorizontalAlignment(SwingConstants.LEFT);
						singleDecisionLanbanHeightLabelTextField.setTextFieldText("");
					}
				}
				else if(nvram.equals(LMSConstValue.bNvramZJSingleDecision.nvramStr)) 
				{
					if(LMSConstValue.bNvramZJSingleDecision.bValue == false)
					{
			 			singleDecisionZJLabelTextField.textField.setHorizontalAlignment(SwingConstants.CENTER);
						singleDecisionZJLabelTextField.setTextFieldText("/");
					}
					else
					{}
				}
				else if(nvram.equals(LMSConstValue.bNvramCarTypeLocalSetting.nvramStr))
				{
					resetCarTypeLocalSetting();
				}
				else if(nvram.equals(CarTypeAdapter.bNvramPauseDetect.nvramStr))
				{
					//协议处理
					if(CustomerProtocol.customerProtocol != null)
					{
						CustomerProtocol.customerProtocol.beginDetection(panelMain, CarTypeAdapter.bNvramPauseDetect.bValue);						
					}
					
					//LED灯牌处理
					//灯牌显示
					if(!LMSConstValue.getSensorType(LMSConstValue.LED_ID_START).equals(LMSConstValue.SensorType.UNKNOW)
						&&LMSConstValue.bNvramLedLocalSetting.bValue == true)
					{
						if(CarTypeAdapter.bNvramPauseDetect.bValue == true)
						{
							LMSConstValue.sNvramLedCurrentMessage.sValue = "停止检测";
						}
						else
						{
							LMSConstValue.sNvramLedCurrentMessage.sValue = vehicleNumLabelTextField.getTextFieldText()+"开始检测，3-5km/h直线行驶";
						}
							
						HashMap<String, Comparable> eventExtraLED = new HashMap<String, Comparable>();
						eventExtraLED.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.sNvramLedCurrentMessage.nvramStr);
						eventExtraLED.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
						LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtraLED);
					}
				}
	        }  
		}
	}	
}
