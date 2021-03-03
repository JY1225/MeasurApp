package AppFrame.logisticsMachine;

import java.awt.GridBagLayout;

import javax.swing.JFrame;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;

import SensorBase.LMSLog;

public class LogisticsMachineSingleDetailFrame extends JFrame {
	private final static String DEBUG_TAG="LogisticsMachineSingleDetailFrame";
	
	public LogisticsMachineSingleDetailFrame(int ID) {
		//Form的标题
		super("查询结果记录");
		
		LMSLog.d(DEBUG_TAG,"LogisticsMachineSingleDetailFrame="+ID);

		//===================================================================
		setBounds(150, 50, 900, 660);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		show();
	}
}
