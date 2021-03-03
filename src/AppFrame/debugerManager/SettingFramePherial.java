package AppFrame.debugerManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import SensorBase.LMSConstValue;

import AppFrame.widgets.CameraSelector;
import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JSettingLabelTextField;
import AppFrame.widgets.SensorIPWidget;


public class SettingFramePherial {
	private final static String DEBUG_TAG="SettingFramePherial";

	JPanel panel;
//	Realplay realplay = new Realplay();

	public JSplitPane createTab() {	
		JSplitPane mainTab = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		//=====================================================================
		mainTab.setLeftComponent(createFrontCameraSettingPanel());
	
		JSplitPane mainSplitPanel2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainTab.setRightComponent(mainSplitPanel2);

		//=====================================================================
		mainSplitPanel2.setLeftComponent(createEndCameraSettingPanel());
	
		JSplitPane mainSplitPanel3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainSplitPanel2.setRightComponent(mainSplitPanel3);

		//=====================================================================
		mainSplitPanel3.setLeftComponent(createLEDSettingPanel());
		mainSplitPanel3.setRightComponent(createNoneSettingPanel());

		return mainTab;
	}
	
	private JPanel createFrontCameraSettingPanel()
	{
		panel = new JPanel();
		
		//======================================================
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50};
		gridBagLayout.rowHeights = new int[]{80, 80};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
		
		//============================================================================
		new CameraSelector(
			panel,
			gridX,gridY,
			"车头摄像头:",LMSConstValue.FRONT_CAMERA_ID
		);
		gridY+=2;

		return panel;
	}	
	
	private JPanel createEndCameraSettingPanel()
	{
		panel = new JPanel();
		
		//======================================================
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50};
		gridBagLayout.rowHeights = new int[]{80, 80};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
		
		new CameraSelector(
			panel,
			gridX,gridY,
			"车尾摄像头:",LMSConstValue.BACK_CAMERA_ID
		);
		gridY+=2;

		return panel;
	}	
	
	private JPanel createLEDSettingPanel()
	{
		panel = new JPanel();
		
		//======================================================
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50};
		gridBagLayout.rowHeights = new int[]{80, 80};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
				
	    JLabel labelLED = new JLabel("LED屏:");
		GridBagConstraints gbc_labelLED = new GridBagConstraints();
		gbc_labelLED.insets = new Insets(0, 0, 5, 5);
		gbc_labelLED.gridx = gridX;
		gbc_labelLED.gridy = gridY;
		panel.add(labelLED, gbc_labelLED);
		gridX++;
		
		new SensorIPWidget(LMSConstValue.LED_ID_START,panel,gridX,gridY);
		gridX+=6;
		
		gridY++;
		gridX = 1;
		new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"默认字符串", "", false,
			false,
			true,
			LMSConstValue.sNvramLedDefaultMessage, -1, -1
		);
		gridX+=2;
		
		new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"测试字符串", "", false,
			false,
			true,
			LMSConstValue.sNvramLedCurrentMessage, -1, -1
		);
		gridX+=2;

		new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"本地控制LED显示",
			"服务器控制LED显示",
			LMSConstValue.bNvramLedLocalSetting,
			-1
		);
		
		return panel;
	}	
	
	private JPanel createNoneSettingPanel()
	{
		panel = new JPanel();

		return panel;
	}
}
