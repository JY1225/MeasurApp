package AppFrame.contourDetection;

import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppBase.appBase.CarTypeAdapter;
import AppFrame.widgets.JLabelComboBox;
import AppFrame.widgets.JSettingLabelTextField;
import SensorBase.LMSConstValue;

public class ContourDetectionTabPanelMain2 extends ContourDetectionTabPanelMain{
	private final static String DEBUG_TAG="ContourDetectionTabPanelMain2";
	    
    private int ID;
            
    JButton deleteButton;
    JButton saveButton;
    
    public ContourDetectionTabPanelMain2(JFrame frame,ContourDetectionDataBaseConst.MainPanelType type,ContourDetectionDataBaseConst.MainPanelCarType _carType)
    {
		super(frame,type,_carType);
		
		//======================================================
		if(panelType == ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT)
		{
			EventListener eventListener = new EventListener();
			LMSEventManager.addListener(eventListener);
		}
    }
    
	public JSplitPane createTab() {
				
		//======================================================
		JSplitPane mainTab = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		//=====================================================================
		mainTab.setLeftComponent(createMainDetectValuePanel());

		JSplitPane mainSplitPanel2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainTab.setRightComponent(mainSplitPanel2);

		//=====================================================================
		mainSplitPanel2.setLeftComponent(createMainDetectImagePanel());

		mainSplitPanel2.setRightComponent(createMainDetectSettingPanel());

		return mainTab;
    }

	private JPanel createMainDetectValuePanel() {	
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{60,100,150,150,150,150,150,150};
		gridBagLayout.rowHeights = new int[]{50,50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;
		if(panelType == ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT)
		{	
			new JLabelComboBox(
				panel,
				gridX,gridY,
				1,
				"测量参数",
				-1,
				CarTypeAdapter.nvramCarEnumType,CarTypeAdapter.carEnumType
			);
			gridX+=2;
		}
		else
		{
					
		}

		//===============================================================
 		jSettingLabelTextFieldDetectLength = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"长","mm",false,
			false,
			false,
			iNvramDetectLength, -1, -1
		);
 		jSettingLabelTextFieldDetectLength.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
 		gridX+=2;
 		
 		jSettingLabelTextFieldDetectWidth = new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 1,
			"宽","mm",false,
			false,
			false,
			iNvramDetectWidth, -1, -1
		);
 		jSettingLabelTextFieldDetectWidth.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
 		gridX+=2;
 		
 		jSettingLabelTextFieldDetectHeight = new JSettingLabelTextField(
			panel,
			gridX, gridY, 1, 1,
			"高","mm",false,
			false,
			false,
			iNvramDetectHeight, -1, -1
		);
 		jSettingLabelTextFieldDetectHeight.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
 		gridX+=2;
 		
		//===============================================================
		gridX = 0;
		gridY++;

		return panel;
	}	
	
	public JPanel createMainDetectImagePanel() {	
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 280, 40, 280, 40, 280};
		gridBagLayout.rowHeights = new int[]{190, 190};
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
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
		
		}
	}

	@Override
	public JPanel createPanelTab() {
		// TODO Auto-generated method stub
		return null;
	}
}
