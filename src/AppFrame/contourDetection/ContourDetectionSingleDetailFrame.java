package AppFrame.contourDetection;

import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import database.DataBaseConst;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class ContourDetectionSingleDetailFrame extends JFrame {
	private final static String DEBUG_TAG="ContourDetectionSingleDetailFrame";

	ContourDetectionTabPanelMain contourMainPanel;
	
	static int DETAIL_FRAME_NUM = 3;
	static ContourDetectionSingleDetailFrame contourDetectionSingleDetailFrame[] = new ContourDetectionSingleDetailFrame[DETAIL_FRAME_NUM];

	@SuppressWarnings("deprecation")
	private ContourDetectionSingleDetailFrame() {
		//Form�ı���
		super("��ѯ�����¼"); 

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				LMSLog.d(DEBUG_TAG,"EXIT_ON_CLOSE...");
				
				for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
				{
					if(contourMainPanel.imageFileName[i] != null)
					{
						File fDownImage = new File(contourMainPanel.imageFileName[i]); // ����Ҫɾ�����ļ�λ��
						if(fDownImage.exists())
							fDownImage.delete(); 
					}
				}
			}
		});
		
		//===================================================================
		setBounds(150, 50, 1120, 700);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);		
		
		//===================================================================
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals("SIMPLE"))
		{
			contourMainPanel = new ContourDetectionTabPanelMain2(this,ContourDetectionDataBaseConst.MainPanelType.SEARCH_SINGLE_RESULT,ContourDetectionDataBaseConst.MainPanelCarType.GUACHE);
			getContentPane().add(contourMainPanel.createTab());
		}
		else
		{
			contourMainPanel = new ContourDetectionTabPanelMain1(this,ContourDetectionDataBaseConst.MainPanelType.SEARCH_SINGLE_RESULT,ContourDetectionDataBaseConst.MainPanelCarType.GUACHE);
			getContentPane().add(contourMainPanel.createTab());
		}		
	}
	
	private void getRowDetial(int ID){
		try { 
			show();
			setVisible(true);

			//==================================================================
			String query =
				"SELECT * FROM "+ContourDetectionDataBaseConst.TABLE_NAME
				+" where "+ContourDetectionDataBaseConst.TABLE_COLUMN_ID+" = '" + ID +"'"; 

			LMSLog.d(DEBUG_TAG,"getRowDetial="+query);
			
			//ִ��SQL���
			Statement statement = DataBaseConst.local_store_access_conn.createStatement(); 
			ResultSet resultSet = statement.executeQuery( query ); 
			
			//�ڱ������ʾ��ѯ���
			contourMainPanel.displayRowDetial(resultSet); 
		}catch (SQLException e) { 
			LMSLog.exception(e);
		}
	}	
	
	public static void showFrame(int ID)
	{
		boolean bShow = false;
		for(int i = 0;i<DETAIL_FRAME_NUM;i++)
		{
			if(contourDetectionSingleDetailFrame[i] == null)
			{
				contourDetectionSingleDetailFrame[i] = new ContourDetectionSingleDetailFrame();
			}
			
			if(!contourDetectionSingleDetailFrame[i].isVisible())
			{
				contourDetectionSingleDetailFrame[i].getRowDetial(ID);
				
				bShow = true;
				break;
			}
		}
		if(bShow == false)
		{
			JOptionPane.showMessageDialog(null, "�򿪲�ѯ����̫��,���ȹر�", "�򿪲�ѯ����̫��,���ȹر�", JOptionPane.WARNING_MESSAGE); 
		}
	}
}
