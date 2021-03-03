package AppFrame.logisticsMachine;

import http.Jason.HttpJason;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import SensorBase.LMSConstValue.enumDetectType;
import ThreeD.ThreeDMainThread;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import lmsTransfer.lmsTransferRunnable.LMSTransferFSRLProtocolSocketServer;
import database.DataBaseSearch;
import CarDetect.CarDetectSetting;
import AppBase.appBase.AppInit;

public class LogisticsMachineFrame extends JFrame{
	private final static String DEBUG_TAG="LogisticsMachineFrame";

	LogisticsMachineTabPanelDetectSingle logisticsMachineSingleDetectPanel;
	LogisticsMachineTabPanelDetectList logisticsMachineTabPanelDetectList;
	LogisticsMachineTabPanelSearch logisticsMachineSearchPanel;
	LogisticsMachineTabPanelSetting logisticMachineTabPanelSetting;

	ThreeDMainThread threeDMainThread;

	public LogisticsMachineFrame(LMSConstValue.AppType appType) {	
		int gridY = 0;
		int gridX = 0;
				
		LMSConstValue.appType = appType;

		//===================================================================
    	LMSConstValue.VALID_THING_HEIGHT_WH = 30;
    	LMSConstValue.VALID_THING_HEIGHT_LONG = 20;

		CarDetectSetting.widthHeightDetectSetProductInfo();

        LMSPlatform.platformInit();
        
		//===================================================================
        logisticsMachineSingleDetectPanel = new LogisticsMachineTabPanelDetectSingle(this,LogisticsMachineDataBaseConst.MainPanelType.DETECT_RESULT);
        logisticsMachineTabPanelDetectList = new LogisticsMachineTabPanelDetectList();
        logisticsMachineSearchPanel = new LogisticsMachineTabPanelSearch();
        logisticMachineTabPanelSetting = new LogisticsMachineTabPanelSetting();
        
        DataBaseSearch.searchSingleResultInterface = new LogisticsMachineSearchResultImplement();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				LMSLog.d(DEBUG_TAG,"EXIT_ON_CLOSE");
			}
		});
		
		//===================================================================
		setVisible(true);
		
		//===================================================================
//		setBounds(100, 50, 1200, 660);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); //设置选项卡的布局方式。
				 
		//===================================================================
        tabbedPane.addTab("单次检测界面", logisticsMachineSingleDetectPanel.createTab());
        tabbedPane.addTab("列表检测界面", logisticsMachineTabPanelDetectList.createTab());
        tabbedPane.addTab("查询界面", logisticsMachineSearchPanel.createTab());
        tabbedPane.addTab("设置界面", logisticMachineTabPanelSetting.createTab());
        
        String title = null;
        if(LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.LM1)
        {        	
    		title = "物流机";

            AppInit.init();
	        
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
			}	
        }
        else
        {
    		title = "物流机(VMS)";
    		
			//===================================================================
			LMSTransferFSRLProtocolSocketServer.lmsTransferFSRLProtocolSocketServer = new LMSTransferFSRLProtocolSocketServer();;
			new Thread(LMSTransferFSRLProtocolSocketServer.lmsTransferFSRLProtocolSocketServer.thread).start();   
	        
			LogisticsMachineProtocolParse logisticsMachineProtocolParse = new LogisticsMachineProtocolParse();
			new Thread(logisticsMachineProtocolParse.thread(0)).start();

			new Thread(new HttpJason().thread()).start();
        }		
		setTitle(title);	
		
		//===================================================================
        EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
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
				
	        }  
		}
	}
}
