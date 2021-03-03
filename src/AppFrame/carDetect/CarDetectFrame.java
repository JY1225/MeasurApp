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
		//ʹ��ϵͳ������ʾ
		SystemTray systemTray;      
		TrayIcon trayIcon = null;   
		if (SystemTray.isSupported()) { //��ǰƽ̨�Ƿ�֧��ϵͳ����   
			setType(java.awt.Window.Type.UTILITY); //ʹӦ�ó���ͼ�겻��ϵͳ״̬����ʾ

            //����һ���Ҽ������˵�   
            PopupMenu popupMenu = new PopupMenu();   
            MenuItem mi = new MenuItem("�������");   
            MenuItem exit = new MenuItem("�˳�");   
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
            //��������ͼ��   
            try {            	
				trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/car.jpg")), "�������������", popupMenu);
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
            //��ȡ���̲˵�   
            systemTray = SystemTray.getSystemTray();   
            //�������ͼ��   
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

		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); //����ѡ��Ĳ��ַ�ʽ��

		//===================================================================		
		CarDetectFrameMainTab carDetectFrameMainTab = new CarDetectFrameMainTab();
        tabbedPane.addTab("�����������", carDetectFrameMainTab.createTab());

        SettingFrameCarOut settingFrameCarOut = new SettingFrameCarOut();
        tabbedPane.addTab("������־", settingFrameCarOut.createTab());

        SettingFrameDebug settingFrameDebug = new SettingFrameDebug();
        tabbedPane.addTab("��������", settingFrameDebug.createTab());
        
		SettingFrameBasicTab settingFrameBasicTab = new SettingFrameBasicTab();
        tabbedPane.addTab("��������", settingFrameBasicTab.createTab());

        SettingFrameThreeDTab settingFrameThreeDTab = new SettingFrameThreeDTab();
        tabbedPane.addTab("��������", settingFrameThreeDTab.createTab());
        
        SettingFrameLightCurtain settingFrameLightCurtain = new SettingFrameLightCurtain();
        tabbedPane.addTab("�������", settingFrameLightCurtain.createTab());

        SettingFramePherial settingFramePherial = new SettingFramePherial();
        tabbedPane.addTab("��������", settingFramePherial.createTab());

    	SettingFrameFsrlProtocol settingFrameFsrlProtocol = new SettingFrameFsrlProtocol();
        tabbedPane.addTab("FSRLЭ�����", settingFrameFsrlProtocol.createTab());        
                
        if(!LMSConstValue.isMyMachine())
        {
			int tabSize = tabbedPane.getTabCount();
			boolean bStart = false;
			for(int i=0;i<tabSize;i++)
			{
				if(tabbedPane.getTitleAt(i).equals("��������"))
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
			setTitle("IMI-BES δ֪�������");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WIDTH_HEIGHT_1_DETECT
			||LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED)
		{
			setTitle("IMI-BES ��߼����");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
		{
			String title = "IMI-BES ���������";
			if(LMSConstValue.bDynamicDetect == true)
			{
				title += "(��̬����)";
			}
			else
			{
				title += "(��̬����)";				
			}
			title += " --- ";
			title += LMSConstValue.defaultDetectType;	
			setTitle(title);			
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			setTitle("IMI-BES ��ײ���ϵͳ");						
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
							if(tabbedPane.getTitleAt(i).equals("��������"))
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
