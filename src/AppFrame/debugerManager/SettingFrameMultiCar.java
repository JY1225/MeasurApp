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
			setTitle("IMI-BES δ֪�������");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED)
		{	
			setTitle("IMI-BES ���ٶ೵�������ͷ��߼��������");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.LM1)
		{	
			setTitle("IMI-BES �����ͷ����������");
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WIDTH_HEIGHT_1_DETECT)
		{
			setTitle("IMI-BES ���ٵ����������ͷ��߼��������");			
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2)
		{
			setTitle("IMI-BES ���ٵ�����˫���ͷ��߼��������");				
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME)
		{
			setTitle("IMI-BES ���ٵ����������ͷ�������������(ͬ�㰲װ)");				
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF)
		{
			setTitle("IMI-BES ���ٵ����������ͷ�������������(��㰲װ)");				
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_SAME)
		{
			setTitle("IMI-BES ���ٵ�����һͷ���һͷ���������������(ͬ�㰲װ)");			
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH1_L1_DIF)
		{
			setTitle("IMI-BES ���ٵ�����һͷ���һͷ���������������(��㰲װ)");		
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L2_SAME)
		{
			setTitle("IMI-BES ���ٵ������ļ��ͷ�������������");					
		}
		else if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{	
			setTitle("IMI-BES ��ײ���ϵͳ����");
		}
		else
		{
			setTitle("IMI-BES ���������");						
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
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT); //����ѡ��Ĳ��ַ�ʽ��
				 
		//===================================================================
		SettingFrameBasicTab settingFrameBasicTab = new SettingFrameBasicTab();
        tabbedPane.addTab("��������", settingFrameBasicTab.createTab());

        SettingFrameThreeDTab settingFrameThreeDTab = new SettingFrameThreeDTab();
        tabbedPane.addTab("��������", settingFrameThreeDTab.createTab());

        SettingFrameLightCurtain settingFrameLightCurtain = new SettingFrameLightCurtain();
        tabbedPane.addTab("�������", settingFrameLightCurtain.createTab());

        SettingFrameDebug settingFrameDebug = new SettingFrameDebug();
        tabbedPane.addTab("��������", settingFrameDebug.createTab());
        
        SettingFrameCarOut settingFrameCarOut = new SettingFrameCarOut();
        tabbedPane.addTab("������־", settingFrameCarOut.createTab());
 
    	SettingFrameFsrlProtocol settingFrameFsrlProtocol = new SettingFrameFsrlProtocol();
        tabbedPane.addTab("FSRLЭ�����", settingFrameFsrlProtocol.createTab());        
	}
}
