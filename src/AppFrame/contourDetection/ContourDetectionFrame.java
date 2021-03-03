package AppFrame.contourDetection;

import http.WebService.WebService;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import CarDetect.CarDetectSetting;

import javax.swing.JTabbedPane;

import AppBase.appBase.AppInit;
import AppBase.appBase.CarTypeAdapter;
import AppFrame.carDetect.CarDetectFrameMainTab;
import AppFrame.debugerManager.SettingFrameBasicTab;
import AppFrame.debugerManager.SettingFrameCarOut;
import AppFrame.debugerManager.SettingFrameDebug;
import AppFrame.debugerManager.SettingFrameFsrlProtocol;
import AppFrame.debugerManager.SettingFrameHumanAdjust;
import AppFrame.debugerManager.SettingFrameLightCurtain;
import AppFrame.debugerManager.SettingFramePherial;
import AppFrame.debugerManager.SettingFrameThreeDTab;

import database.DataBaseSearch;
import database.DataBaseThread;
import database.DataBaseThread.DataBaseUserTypeEnum;

public class ContourDetectionFrame extends JFrame {
	private final static String DEBUG_TAG="ContourDetectionFrame";
    
	public static ContourDetectionTabPanelMain mainDetectPanel;
	public static JSplitPane mainDetectTab;
	public static ContourDetectionTabPanelMain mainQianYingDetectPanel;
	public static JSplitPane mainQianYingDetectTab;

	JTabbedPane tabbedPane;
	
	public ContourDetectionFrame(LMSConstValue.AppType appType) {	
		int gridY = 0;
		int gridX = 0;
		
		String title = "IMI-BES 外廓检测仪";
		if(LMSConstValue.bDynamicDetect == true)
		{
			title += "(动态测量)";
		}
		else
		{
			title += "(静态测量)";				
		}
		setTitle(title);			
		
		LMSConstValue.appType = appType;

		//===================================================================
		CarDetectSetting.widthHeightDetectSetProductInfo();

        LMSPlatform.platformInit();

		//===================================================================
        AppInit.init();

		new Thread(new WebService().thread()).start();

		//====================================================================================
        DataBaseSearch.searchSingleResultInterface = new ContourDetectionSearchResultImplement();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				LMSLog.d(DEBUG_TAG,"EXIT_ON_CLOSE");
			}
		});
		
		//===================================================================
		setVisible(true);

		//===================================================================
		setBounds(100, 0, 1200, 750);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); //设置选项卡的布局方式。
				 
		//===================================================================
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals("SIMPLE"))
		{
			mainDetectPanel = new ContourDetectionTabPanelMain2(this,ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT,ContourDetectionDataBaseConst.MainPanelCarType.GUACHE);
			mainQianYingDetectPanel = new ContourDetectionTabPanelMain2(this,ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT,ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE);
		}
		else
		{
			mainDetectPanel = new ContourDetectionTabPanelMain1(this,ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT,ContourDetectionDataBaseConst.MainPanelCarType.GUACHE);
			mainQianYingDetectPanel = new ContourDetectionTabPanelMain1(this,ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT,ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE);
		}
		mainDetectTab = mainDetectPanel.createTab();
		mainQianYingDetectTab = mainQianYingDetectPanel.createTab();
		tabbedPane.addTab("检测界面(整车,挂车)", mainDetectTab);
		
		//=============================================================================
		if(!LMSConstValue.localStoreDatabaseEnumType.key.equals(LMSConstValue.DataBaseType.DATABASE_TYPE_NONE))
		{
			tabbedPane.addTab("查询界面", new ContourDetectionTabPanelSearch().createTab());
		}
		
        tabbedPane.addTab("用户设置", new ContourDetectionTabPanelSetting().createTab());
        tabbedPane.addTab("打印设置", new ContourDetectionTabPanelPrint().createTab());
        
		//===================================================================
		CarDetectFrameMainTab carDetectFrameMainTab = new CarDetectFrameMainTab();
        tabbedPane.addTab("检测仪主界面", carDetectFrameMainTab.createTab());

        SettingFrameCarOut settingFrameCarOut = new SettingFrameCarOut();
        tabbedPane.addTab("过车日志", settingFrameCarOut.createTab());

        SettingFrameDebug settingFrameDebug = new SettingFrameDebug();
        tabbedPane.addTab("调试设置", settingFrameDebug.createTab());
         
		if(
			LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC)
			||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HGJN)
		)
		{
			SettingFrameHumanAdjust settingFrameHumanAdjust = new SettingFrameHumanAdjust();
			tabbedPane.addTab("修改日志", settingFrameHumanAdjust.createTab());
		}
		
		SettingFrameBasicTab settingFrameBasicTab = new SettingFrameBasicTab();
        tabbedPane.addTab("基本设置", settingFrameBasicTab.createTab());

        SettingFrameThreeDTab settingFrameThreeDTab = new SettingFrameThreeDTab();
        tabbedPane.addTab("其他设置", settingFrameThreeDTab.createTab());

        SettingFrameLightCurtain settingFrameLightCurtain = new SettingFrameLightCurtain();
        tabbedPane.addTab("轴距设置", settingFrameLightCurtain.createTab());

        SettingFramePherial settingFramePherial = new SettingFramePherial();
        tabbedPane.addTab("外设设置", settingFramePherial.createTab());
        
    	SettingFrameFsrlProtocol settingFrameFsrlProtocol = new SettingFrameFsrlProtocol();
        tabbedPane.addTab("FSRL协议调试", settingFrameFsrlProtocol.createTab());   
        
        if(!LMSConstValue.isMyMachine())
        {
			int tabSize = tabbedPane.getTabCount();
			boolean bStart = false;
			for(int i=0;i<tabSize;i++)
			{
				if(tabbedPane.getTitleAt(i).equals("基本设置"))
				{
					bStart = true;
				}
				
				if(bStart == true)
				{
					tabbedPane.setEnabledAt(i, false);
				}
			}
        }
        
        resetTabPanel();
        resetQianYingTabPanel();

		if(
			LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HF_SRF)
			||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSYY)
			||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSXSS)
		)
		{
			new DataBaseThread(
				DataBaseUserTypeEnum.PROTOCOL_DATABASE,
				LMSConstValue.sNvramCustomerProtocol_database_name.sValue,LMSConstValue.sNvramCustomerProtocol_database_table.sValue,null,
				LMSConstValue.sNvramCustomerProtocol_database_ip.sValue, LMSConstValue.sNvramCustomerProtocol_database_port.sValue,
				LMSConstValue.sNvramCustomerProtocol_database_user.sValue,LMSConstValue.sNvramCustomerProtocol_database_password.sValue
			).start();
		}
		
		//===================================================================
        EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	}	
	
	void resetTabPanel()
	{		
		if(LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
			||LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.XZY_1600)
		)
		{
			int tabSize = tabbedPane.getTabCount();
			int iInsertIndex = 0;
			for(int i=0;i<tabSize;i++)
			{
				if(tabbedPane.getTitleAt(i).equals("检测界面(整车,挂车)"))
				{
					iInsertIndex = i;
				}
			}
			
			//--------------------------------------------------------------------
			boolean bHasQianYing = false;
			for(int i=0;i<tabSize;i++)
			{
				if(tabbedPane.getTitleAt(i).equals("检测界面(牵引车)"))
				{
					bHasQianYing = true;
					
					break;
				}
			}
			if(bHasQianYing == false)
			{
				tabbedPane.insertTab("检测界面(牵引车)", null, mainQianYingDetectTab, "hi", iInsertIndex+1);
			}
		}
		else
		{
			/*
			int tabSize = tabbedPane.getTabCount();
			for(int i=0;i<tabbedPane.getTabCount();i++)
			{
				if(tabbedPane.getTitleAt(i).equals("检测界面(整车,挂车)"))
				{
					tabbedPane.setTitleAt(i, "检测界面");
				}
			}
			*/
			
			//--------------------------------------------------------------
			for(int i=0;i<tabbedPane.getTabCount();i++)
			{
				if(tabbedPane.getTitleAt(i).equals("检测界面(牵引车)"))
				{
					tabbedPane.remove(i);
				}
			}
		}
	}
	
	void resetQianYingTabPanel()
	{
		int tabSize = tabbedPane.getTabCount();
		for(int i=0;i<tabSize;i++)
		{
			if(tabbedPane.getTitleAt(i).equals("检测界面(牵引车)"))
			{
				if(CarTypeAdapter.carEnumType.key.endsWith(CarTypeAdapter.CAR_TYPE_GUACHE))
				{
					tabbedPane.setEnabledAt(i, true);
				}
				else
				{
					tabbedPane.setEnabledAt(i, false);						
				}
			}	
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
				
				if(nvram.equals(LMSConstValue.nvramAuth)) 
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

					if(str.equals("13579")||str.equals(Md5.convertMD5(LMSConstValue.sNvramSettingPassword.sValue))||str.equals("63780zhi"))
					{
						int tabSize = tabbedPane.getTabCount();
						boolean bStart = false;
						for(int i=0;i<tabSize;i++)
						{
							if(tabbedPane.getTitleAt(i).equals("基本设置"))
							{
								bStart = true;
							}
							
							if(bStart == true)
							{
								tabbedPane.setEnabledAt(i, true);
							}
						}
					}
				}
				else if(nvram.equals(CarTypeAdapter.nvramCarEnumType))
				{
					resetQianYingTabPanel();
				}
				else if(sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START && nvram.equals(LMSConstValue.nvramSensorType))
				{
					resetTabPanel();
					resetQianYingTabPanel();
				}
				else if(
					nvram.equals(LMSConstValue.sNvramCustomerProtocol_database_ip.nvramStr)
					||nvram.equals(LMSConstValue.sNvramCustomerProtocol_database_port.nvramStr)
					||nvram.equals(LMSConstValue.sNvramCustomerProtocol_database_name.nvramStr)
					||nvram.equals(LMSConstValue.sNvramCustomerProtocol_database_user.nvramStr)
					||nvram.equals(LMSConstValue.sNvramCustomerProtocol_database_password.nvramStr)
					||nvram.equals(LMSConstValue.sNvramCustomer.nvramStr)
				)
				{
					if(
						LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HF_SRF)
						||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSYY)
						||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSXSS)
					)
					{
						new DataBaseThread(
							DataBaseUserTypeEnum.PROTOCOL_DATABASE,
							LMSConstValue.sNvramCustomerProtocol_database_name.sValue,LMSConstValue.sNvramCustomerProtocol_database_table.sValue,null,
							LMSConstValue.sNvramCustomerProtocol_database_ip.sValue, LMSConstValue.sNvramCustomerProtocol_database_port.sValue,
							LMSConstValue.sNvramCustomerProtocol_database_user.sValue,LMSConstValue.sNvramCustomerProtocol_database_password.sValue
						).start();
					}
				}
	        }  
		}
	}	
}
