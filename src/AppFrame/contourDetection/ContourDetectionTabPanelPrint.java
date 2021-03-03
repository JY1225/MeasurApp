package AppFrame.contourDetection;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import SensorBase.LMSConstValue;

import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JSettingLabelTextField;

public class ContourDetectionTabPanelPrint {
	private final static String DEBUG_TAG="ContourDetectionTabPanelPrint";

	JPanel panel;

	public JPanel createTab() {
        //=============================================================
		panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 60, 60, 60, 60, 60};
		gridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 20, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
				
		new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 10,
			"打印页脚行2:",null,false,
			true,
			true,
			LMSConstValue.sNvramPrintFootStringLine2, -1, -1
		);
		gridY++;
		
		new JSettingLabelTextField(
			panel, 
			gridX, gridY, 1, 10,
			"打印页脚行3:",null,false,
			true,
			true,
			LMSConstValue.sNvramPrintFootStringLine3, -1, -1
		);
		gridY++;
		
		//======================================================================================
		gridX = 0;		
		for(int i=0;i<LMSConstValue.PRINT_IMAGE_NUM;i++)
		{
			gridX = 0;
			
			JLabel imageNameLabel = new JLabel("打印图片"+(i+1)+":"); 
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
				LMSConstValue.sNvramPrintImageTitle[i], -1, -1
			);
			gridX+=2;

			new JSettingLabelTextField(
				panel, 
				gridX, gridY, 1, 1,
				"读取的图片名:",null,false,
				true,
				true,
				LMSConstValue.sNvramPrintImage[i], -1, -1
			);
			gridX+=2;

			gridY++;
		}

		//======================================================================================
		gridX = 0;
		new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"打印操作员证件号(打开)",
			"打印操作员证件号(关闭)",
			LMSConstValue.bNvramPrintOperatorID,
			-1
		);
		gridX++;
		
		//=====================================================
		return panel;
	}
}
