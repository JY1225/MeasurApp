package AppFrame.contourDetection;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import SensorBase.LMSConstValue;
import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import AppBase.appBase.AppConst;
import AppFrame.widgets.AuthWidget;
import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JLabelComboBox;
import AppFrame.widgets.JSettingLabelTextField;
import AppFrame.widgets.OperatorEditor;
import CustomerProtocol.DetectionVehicle;

public class ContourDetectionTabPanelSetting {
	private final static String DEBUG_TAG="ContourDetectionTabPanelSetting";
	
	protected JSplitPane splitPanelUser;

	JLabelComboBox localStore_database_ComboBox;
	JSettingLabelTextField localStore_database_IP_LabelTextField;
	JSettingLabelTextField localStore_database_PORT_LabelTextField;
	JSettingLabelTextField localStore_database_user_LabelTextField;
	JSettingLabelTextField localStore_database_password_LabelTextField;

	
	JTextField userMsgTextField;
	JSettingLabelTextField webServiceLabelTextField;
	JButtonBoolean webServiceDebugDialogButton;
	JSettingLabelTextField customProtocol_field_url_LabelTextField;

	JSettingLabelTextField customProtocol_database_IP_LabelTextField;
	JSettingLabelTextField customProtocol_database_PORT_LabelTextField;
	JSettingLabelTextField customProtocol_database_name_LabelTextField;
	JSettingLabelTextField customProtocol_database_table_LabelTextField;
	JSettingLabelTextField customProtocol_database_user_LabelTextField;
	JSettingLabelTextField customProtocol_database_password_LabelTextField;

	public static JLabel operatorNameLabel = new JLabel("操作员姓名");
	public static  JLabel operatorIDLabel = new JLabel("操作员证件号");

	private Object gbc_userNameTitleLabel;
	
	public ContourDetectionTabPanelSetting()
	{
		//===================================================================
        EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	}
	
	public JSplitPane createTab() {
		JSplitPane mainTab = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		//=====================================================================
		JSplitPane splitPanel1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		mainTab.setLeftComponent(splitPanel1);

		splitPanel1.setLeftComponent(createStationSettingPanel());		
		splitPanel1.setRightComponent(createUserPanel());		

		//=====================================================================
		mainTab.setRightComponent(createSettingPanel());
		
				
		return mainTab;
	}
	
	JPanel createStationSettingPanel()
	{
		JPanel panel = new JPanel();

		//======================================================
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 120, };
		gridBagLayout.rowHeights = new int[]{20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
	
		int gridX = 0,gridY = 0;
		
		JSettingLabelTextField station = new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"工位号", "", true,
			false,
			true,
			LMSConstValue.sNvramStationID, -1, -1
		);
		station._propertyType = LMSConstValue.USER_PROPERTY;

		return panel;
	}
	
	JPanel createUserPanel()
	{
		JPanel panel = new JPanel();
		
		//======================================================
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{60, 100, 140};
		gridBagLayout.rowHeights = new int[]{
			20,
			20, 20, 20, 20, 20,
			20, 20, 20, 20, 20,
			20, 20, 20, 20, 20,
			20, 20, 20, 20, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{
			1.0,
			1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0, 1.0
			};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		JLabel userSerialTitleLabel = new JLabel("操作员序号"); 
		GridBagConstraints gbc_userSerialTitleLabel = new GridBagConstraints();
		gbc_userSerialTitleLabel.fill = GridBagConstraints.BOTH;
		gbc_userSerialTitleLabel.gridwidth = 1;
		gbc_userSerialTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_userSerialTitleLabel.gridx = gridX;
		gbc_userSerialTitleLabel.gridy = gridY;
		panel.add(userSerialTitleLabel,gbc_userSerialTitleLabel);		
		gridX++;
		
		GridBagConstraints gbc_operatorNameLabel = new GridBagConstraints();
		gbc_operatorNameLabel.fill = GridBagConstraints.BOTH;
		gbc_operatorNameLabel.gridwidth = 1;
		gbc_operatorNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_operatorNameLabel.gridx = gridX;
		gbc_operatorNameLabel.gridy = gridY;
		panel.add(operatorNameLabel,gbc_operatorNameLabel);		
		gridX++;
		 
		GridBagConstraints gbc_operatorIDLabel = new GridBagConstraints();
		gbc_operatorIDLabel.fill = GridBagConstraints.BOTH;
		gbc_operatorIDLabel.gridwidth = 1;
		gbc_operatorIDLabel.insets = new Insets(0, 0, 5, 5);
		gbc_operatorIDLabel.gridx = gridX;
		gbc_operatorIDLabel.gridy = gridY;
		panel.add(operatorIDLabel,gbc_operatorIDLabel);		
		gridX++;
		
		//=========================================================
		gridY++;
		gridX = 0;
		for(int i=0;i<AppConst.MAX_OPERATOR;i++)
		{
			new OperatorEditor(i,panel,gridX, gridY);
			
			gridY++;
			gridX = 0;
		}
		
		return panel;
	}
	
	JPanel createSettingPanel()
	{
		JPanel panel = new JPanel();

		//======================================================
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{80, 80, 80, 120, 100, 120, 100};
		gridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;
	
		gridX = 0;
		localStore_database_ComboBox = new JLabelComboBox(
 			panel,
 			gridX,gridY,
 			2,
 			"本地查询数据库服务器:",
 			-1,
 			LMSConstValue.nvramLocalStoreDataBaseType,LMSConstValue.localStoreDatabaseEnumType
 		);
		localStore_database_ComboBox.comboBox.setEnabled(false);
		gridX+=3;
			
		//======================================================================================
		localStore_database_IP_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"IP:",null,false,
			true,
			true,
			LMSConstValue.sNvramLocalStoreDataBaseIP, -1, -1
		);
		localStore_database_IP_LabelTextField.setEditable(false);
		gridX+=2;

		localStore_database_PORT_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"端口:",null,false,
			true,
			true,
			LMSConstValue.sNvramLocalStoreDataBasePORT, -1, -1
		);
		localStore_database_PORT_LabelTextField.setEditable(false);
		gridX+=2;
		
		//======================================================================================
		gridY++;
		gridX = 3;
		localStore_database_user_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"数据库账号:",null,false,
			true,
			true,
			LMSConstValue.sNvramLocalStoreDataBaseName, -1, -1
		);
		localStore_database_user_LabelTextField.setEditable(false);
		gridX+=2;
		
		localStore_database_password_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"数据库密码:",null,false,
			true,
			true,
			LMSConstValue.sNvramLocalStoreDataBasePassword, -1, -1
		);
		localStore_database_password_LabelTextField.setEditable(false);
		gridX+=2;
				
		//======================================================================================
		gridY++;
		gridX = 0;

		new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 2,
			"检测界面显示信息:",null,false,
			true,
			true,
			LMSConstValue.sNvramUserMsg, -1, -1
		);
		gridX+=3;
		
		new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"字体大小","",true,
			false,
			true,
			LMSConstValue.iNvramUserMsgFontSize, -1, -1
		).setRange(true, LMSConstValue.UserMsgFontSize_MIN, LMSConstValue.UserMsgFontSize_MAX);;
		gridX+=2;

		//======================================================================================
		gridY++;
		gridX = 0;
		
		for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
		{
			gridX = 0;
			
			JLabel imageNameLabel = new JLabel("主界面图片"+(i+1)+":"); 
			GridBagConstraints gbc_imageNameLabel = new GridBagConstraints();
			gbc_imageNameLabel.fill = GridBagConstraints.BOTH;
			gbc_imageNameLabel.gridwidth = 1;
			gbc_imageNameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_imageNameLabel.gridx = gridX;
			gbc_imageNameLabel.gridy = gridY;
			panel.add(imageNameLabel,gbc_imageNameLabel);		
			gridX++;

			new JSettingLabelTextField(
				panel, 
				gridX, gridY, 1, 1,
				"显示的标题名:",null,false,
				true,
				true,
				LMSConstValue.sNvramMainImageTitle[i], -1, -1
			);
			gridX+=2;

			new JSettingLabelTextField(
				panel, 
				gridX, gridY, 1, 1,
				"读取的图片名:",null,false,
				true,
				true,
				LMSConstValue.sNvramMainImage[i], -1, -1
			);
			gridX+=2;

			gridY++;
		}
		
		//======================================================================================
		gridX = 0;
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"栏板判定(打开)",
			"栏板判定(关闭)",
			LMSConstValue.bNvramLanBanSingleDecision,
			-1
		);
		gridX+=1;
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"轴距判定(打开)",
			"轴距判定(关闭)",
			LMSConstValue.bNvramZJSingleDecision,
			-1
		);
		gridX+=1;
		
		//======================================================================================
		gridY++;
		gridX = 0;
    	
		webServiceLabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 2,
			"WebService URL:",null,false,
			true,
			true,
			LMSConstValue.sNvramWebServiceUrl, -1, -1
		);
		gridX+=3;

		webServiceDebugDialogButton = new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"WebSevice调试对话框(打开)",
			"WebSevice调试对话框(关闭)",
			LMSConstValue.bNvramWebServiceDebugDialog,
			-1
		);
		gridX+=2;
	
		if(!LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC))
		{
			webServiceLabelTextField.setEditable(false);
			webServiceDebugDialogButton.setEnabled(false);
		}
		gridX+=2;
		
		//===========================================================
		gridY++;
		gridX = 0;
		new JLabelComboBox(
 			panel,
 			gridX,gridY,
 			2,
 			"客户数据库服务器:",
 			-1,
 			LMSConstValue.nvramProtocolDataBaseType,LMSConstValue.protocolDatabaseEnumType
 		);
		gridX+=3;
		
		//配置文件路径=====================================================
//		gridY++;
//		gridX = 0;
//		customProtocol_field_url_LabelTextField = new JSettingLabelTextField(
//				panel, 
//				gridX, gridY, 1, 1,
//				"共享文件路径:",null,false,
//				true,
//				true,
//				LMSConstValue.sNvramFieldUrl, -1, -1
//			);
//		gridX+=3;
//		if(!DetectionVehicle.bProtocolFile){
//			customProtocol_field_url_LabelTextField.setEditable(false);
//		}
//		
//		gridY--;
		customProtocol_database_IP_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"IP:",null,false,
			true,
			true,
			LMSConstValue.sNvramCustomerProtocol_database_ip, -1, -1
		);
		gridX+=2;

		customProtocol_database_PORT_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"端口:",null,false,
			true,
			true,
			LMSConstValue.sNvramCustomerProtocol_database_port, -1, -1
		);
		gridX+=2;

		//----------------------------------------------
		gridY++;
		gridX = 3;
		customProtocol_database_name_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"数据库名:",null,false,
			true,
			true,
			LMSConstValue.sNvramCustomerProtocol_database_name, -1, -1
		);
		gridX+=2;

		customProtocol_database_table_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"表名:",null,false,
			true,
			true,
			LMSConstValue.sNvramCustomerProtocol_database_table, -1, -1
		);
		gridX+=2;
		
		//----------------------------------------------
		gridY++;
		gridX = 3;
		customProtocol_database_user_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"用户名:",null,false,
			true,
			true,
			LMSConstValue.sNvramCustomerProtocol_database_user, -1, -1
		);
		gridX+=2;

		customProtocol_database_password_LabelTextField = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"密码:",null,false,
			true,
			true,
			LMSConstValue.sNvramCustomerProtocol_database_password, -1, -1
		);
		gridX+=2;

		boolean bEditable = false;
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HF_SRF)
			||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSYY)
			||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSXSS))
		{
			bEditable = true;
		}
		else
		{
			bEditable = false;
		}
		customProtocol_database_IP_LabelTextField.setEditable(bEditable);
		customProtocol_database_PORT_LabelTextField.setEditable(bEditable);
		customProtocol_database_name_LabelTextField.setEditable(bEditable);
		customProtocol_database_table_LabelTextField.setEditable(bEditable);
		customProtocol_database_user_LabelTextField.setEditable(bEditable);
		customProtocol_database_password_LabelTextField.setEditable(bEditable);

		return panel;
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
				
				if(nvram.equals(LMSConstValue.nvramAuth)) 
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

					if(str.equals("13579")||str.equals(Md5.convertMD5(LMSConstValue.sNvramSettingPassword.sValue))||str.equals("63780zhi"))
					{
						localStore_database_ComboBox.comboBox.setEnabled(true);
						localStore_database_IP_LabelTextField.setEditable(true);
						localStore_database_PORT_LabelTextField.setEditable(true);
						localStore_database_user_LabelTextField.setEditable(true);
						localStore_database_password_LabelTextField.setEditable(true);
					}
				}
				else if(nvram.equals(LMSConstValue.sNvramCustomer.nvramStr)) 
				{
					boolean bEditable = false;
					if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC))
					{
						bEditable = true;
					}
					else
					{
						bEditable = false;
					}
					webServiceLabelTextField.setEditable(bEditable);
					webServiceDebugDialogButton.setEnabled(bEditable);
					//设置共享文件路径监听
//					if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSHX)
//							||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_FSNH)
//							)
//					{
//						customProtocol_field_url_LabelTextField.setEditable(true);	
//					}
//					else
//					{
//						customProtocol_field_url_LabelTextField.setEditable(false);
//					}
					//--------------------------------------------------------
					if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HF_SRF)
						||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSYY)
						||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSXSS))
					{
						bEditable = true;
					}
					else
					{
						bEditable = false;
					}
					customProtocol_database_IP_LabelTextField.setEditable(bEditable);
					customProtocol_database_PORT_LabelTextField.setEditable(bEditable);
					customProtocol_database_name_LabelTextField.setEditable(bEditable);
					customProtocol_database_table_LabelTextField.setEditable(bEditable);
					customProtocol_database_user_LabelTextField.setEditable(bEditable);
					customProtocol_database_password_LabelTextField.setEditable(bEditable);
				}
	        }  
		}
	}	
}
