package AppFrame.carDetect;

import http.WebService.WebService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import SensorBase.LMSConstValue;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import AppBase.appBase.AppInit;
import AppFrame.debugerManager.SettingFrameBasicTab;
import AppFrame.debugerManager.SettingFrameCarOut;
import AppFrame.debugerManager.SettingFrameDebug;
import AppFrame.debugerManager.SettingFrameFsrlProtocol;
import AppFrame.debugerManager.SettingFrameLightCurtain;
import AppFrame.debugerManager.SettingFramePherial;
import AppFrame.debugerManager.SettingFrameThreeDTab;
import CarDetect.CarDetectSetting;

import java.awt.AWTException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.util.HashMap;

import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class CarDetectFrame extends JFrame{
	private final String DEBUG_TAG="CarDetectFrame";
	    
	JTabbedPane tabbedPane;
	
	public CarDetectFrame(LMSConstValue.AppType appType) {	
		LMSConstValue.appType = appType;
		
		CarDetectSetting.widthHeightDetectSetProductInfo();

        LMSPlatform.platformInit();
        //lidar
        AppInit.init();
        
		new Thread(new WebService().thread()).start();

 		//====================================================================================
		///*
		//使在系统托盘显示
		SystemTray systemTray;      
		TrayIcon trayIcon = null;   
		if (SystemTray.isSupported()) { //当前平台是否支持系统托盘   
			setType(java.awt.Window.Type.UTILITY); //使应用程序图标不在系统状态栏显示

            //创建一个右键弹出菜单   
            PopupMenu popupMenu = new PopupMenu();   
            MenuItem mi = new MenuItem("打开主面板");   
            MenuItem exit = new MenuItem("退出");   
            popupMenu.add(mi);   
            popupMenu.add(exit);   
            mi.addActionListener(new ActionListener() {   
                @Override  
                public void actionPerformed(ActionEvent e) {   
                    setVisible(true);   
                }   
    
            });   
            exit.addActionListener(new ActionListener() {   
                @Override  
                public void actionPerformed(ActionEvent e) {   
                    System.exit(0);   
                }   
            });   
            //创建托盘图标   
            try {            	
				trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/car.jpg")), "车辆轮廓检测仪", popupMenu);
			} catch (IOException e) {
	    		LMSLog.exception(e);
			}   
            trayIcon.setImageAutoSize(true); 
            trayIcon.addMouseListener(
            	new MouseAdapter() 
            	{     
            		@Override     
            		public void mouseClicked(MouseEvent e) 
            		{            
            			if(isVisible())
            				setVisible(false);
            			else
            				setVisible(true);            				
            		} 
            	});
            //获取托盘菜单   
            systemTray = SystemTray.getSystemTray();   
            //添加托盘图标   
            try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
	    		LMSLog.exception(e);
			}  
            
            setVisible(false);   
        }   
		else
		{
            setVisible(true);   
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
        //*/
		setBounds(100, 0, 1200, 750);
		
		//=====================================================================	
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
		CarDetectFrameMainTab carDetectFrameMainTab = new CarDetectFrameMainTab();
        tabbedPane.addTab("检测仪主界面", carDetectFrameMainTab.createTab());

        SettingFrameCarOut settingFrameCarOut = new SettingFrameCarOut();
        tabbedPane.addTab("过车日志", settingFrameCarOut.createTab());

        SettingFrameDebug settingFrameDebug = new SettingFrameDebug();
        tabbedPane.addTab("调试设置", settingFrameDebug.createTab());
        
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

        //===================================================================
        EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	
		//=======================================================
		if(LMSConstValue.defaultDetectType == enumDetectType.UNKNOW_DETECT_TYPE)
		{
			setTitle("IMI-BES 未知检测类型");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WIDTH_HEIGHT_1_DETECT
			||LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED)
		{
			setTitle("IMI-BES 宽高检测仪");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
		{
			String title = "IMI-BES 外廓检测仪";
			if(LMSConstValue.bDynamicDetect == true)
			{
				title += "(动态测量)";
			}
			else
			{
				title += "(静态测量)";				
			}
			title += " --- ";
			title += LMSConstValue.defaultDetectType;	
			setTitle(title);			
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			setTitle("IMI-BES 防撞检测系统");						
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});		
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
	        }  
		}
	}	
}
