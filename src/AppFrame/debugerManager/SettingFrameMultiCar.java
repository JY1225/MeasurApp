package AppFrame.debugerManager;

import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import lmsBase.LMSProductInfo;

import CarDetect.CarDetectSetting;
import lmsEvent.LMSEventManager;

import SensorBase.LMSConstValue;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;

import java.io.IOException;


public class SettingFrameMultiCar extends JFrame{
	private final static String DEBUG_TAG="SettingFrameMultiCar";
	
	public SettingFrameMultiCar() {	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				LMSLog.d(DEBUG_TAG,"EXIT_ON_CLOSE");
				
	    		LMSEventManager.removeListener(SettingFrameBasicTab.eventListener);
	    		LMSEventManager.removeListener(SettingFrameThreeDTab.settingFrameThreeDTabEventListener);
			}
		});
		
		if(LMSConstValue.defaultDetectType == enumDetectType.UNKNOW_DETECT_TYPE)
		{
			setTitle("IMI-BES 未知检测类型");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED)
		{	
			setTitle("IMI-BES 高速多车道单检测头宽高检测仪设置");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.LM1)
		{	
			setTitle("IMI-BES 单检测头物流机设置");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WIDTH_HEIGHT_1_DETECT)
		{
			setTitle("IMI-BES 低速单车道单检测头宽高检测仪设置");			
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2)
		{
			setTitle("IMI-BES 低速单车道双检测头宽高检测仪设置");				
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME)
		{
			setTitle("IMI-BES 低速单车道三检测头外廓检测仪设置(同点安装)");				
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF)
		{
			setTitle("IMI-BES 低速单车道三检测头外廓检测仪设置(异点安装)");				
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME)
		{
			setTitle("IMI-BES 低速单车道一头宽高一头长外廓检测仪设置(同点安装)");			
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF)
		{
			setTitle("IMI-BES 低速单车道一头宽高一头长外廓检测仪设置(异点安装)");		
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
		{
			setTitle("IMI-BES 低速单车道四检测头外廓检测仪设置");					
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{	
			setTitle("IMI-BES 防撞检测系统设置");
		}
		else
		{
			setTitle("IMI-BES 检测仪设置");						
		}
		
		try {
			setIconImage(ImageIO.read(getClass().getResource("/car.jpg")));
		} catch (IOException e) {
    		LMSLog.exception(e);
		}
		
		if(LMSConstValue.defaultDetectType == enumDetectType.UNKNOW_DETECT_TYPE)
		{
			setBounds(100, 0, 1100, 300);
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			setBounds(100, 0, 1100, 400);
		}
		else
		{
			setBounds(100, 0, 1200, 750);
		}
		
		//=====================================================================	
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
		SettingFrameBasicTab settingFrameBasicTab = new SettingFrameBasicTab();
        tabbedPane.addTab("基本设置", settingFrameBasicTab.createTab());

        SettingFrameThreeDTab settingFrameThreeDTab = new SettingFrameThreeDTab();
        tabbedPane.addTab("其他设置", settingFrameThreeDTab.createTab());

        SettingFrameLightCurtain settingFrameLightCurtain = new SettingFrameLightCurtain();
        tabbedPane.addTab("轴距设置", settingFrameLightCurtain.createTab());

        SettingFrameDebug settingFrameDebug = new SettingFrameDebug();
        tabbedPane.addTab("调试设置", settingFrameDebug.createTab());
        
        SettingFrameCarOut settingFrameCarOut = new SettingFrameCarOut();
        tabbedPane.addTab("过车日志", settingFrameCarOut.createTab());
 
    	SettingFrameFsrlProtocol settingFrameFsrlProtocol = new SettingFrameFsrlProtocol();
        tabbedPane.addTab("FSRL协议调试", settingFrameFsrlProtocol.createTab());        
	}
}
