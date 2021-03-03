package AppFrame.logisticsMachine;

import http.Jason.HttpJason;
import http.Jason.JdHttpJason;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class LogisticsMachineTabPanelDetectList {
	private final static String DEBUG_TAG="LogisticsMachineTabPanelDetectList";
	JPanel listPanel;
	
	public static EventListener eventListener;

	JLabel readBarCodePercentLabel; 
	JLabel httpStateLabel; 

	LogisticsMachineConveyorDrawPanel logisticsMachineConveyorDrawPanel;
	public JSplitPane createTab() {
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//======================================================
		JSplitPane tabPanle = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		//=====================================================================
		tabPanle.setLeftComponent(createDataPanel());

		/*
		JSplitPane mainSplitPanel2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainTab.setRightComponent(mainSplitPanel2);

		//=====================================================================
		mainSplitPanel2.setLeftComponent(createMainDetectImagePanel());

		mainSplitPanel2.setRightComponent(createMainDetectSettingPanel());
		*/
//		logisticsMachineConveyorDrawPanel = new LogisticsMachineConveyorDrawPanel();
//		tabPanle.setRightComponent(logisticsMachineConveyorDrawPanel);

		return tabPanle;		
	}
	
	JPanel createDataPanel()
	{
		JPanel panel = new JPanel();		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{80};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{0.0,1.0};
		panel.setLayout(gridBagLayout);
		
		//=====================================================
		int gridY = 0;
		int gridX = 0;
		
		readBarCodePercentLabel = new JLabel("100%",JLabel.CENTER);
		readBarCodePercentLabel.setFont(new Font("����",Font.BOLD, 60));
		GridBagConstraints gbc_readBarCodePercentLabel = new GridBagConstraints();
		gbc_readBarCodePercentLabel.fill = GridBagConstraints.BOTH;
		gbc_readBarCodePercentLabel.gridwidth = 1;
		gbc_readBarCodePercentLabel.insets = new Insets(0, 0, 5, 5);
		gbc_readBarCodePercentLabel.gridx = gridX;
		gbc_readBarCodePercentLabel.gridy = gridY;
		panel.add(readBarCodePercentLabel,gbc_readBarCodePercentLabel);		
		gridX++;		
		gridY++;

		//=====================================================
		JPanel searchResultTablePanel = createListPanel();		
		GridBagConstraints gbc_searchResultTablePanel = new GridBagConstraints();
		gbc_searchResultTablePanel.insets = new Insets(0, 0, 5, 0);
		gbc_searchResultTablePanel.fill = GridBagConstraints.BOTH;
		gbc_searchResultTablePanel.gridx = 0;
		gbc_searchResultTablePanel.gridy = gridY;
		panel.add(searchResultTablePanel, gbc_searchResultTablePanel);
		gridY++;
		
		JPanel searchResultDeletePanel = createClearPanel();		
		GridBagConstraints gbc_searchResultDeletePanel = new GridBagConstraints();
		gbc_searchResultDeletePanel.insets = new Insets(0, 0, 5, 0);
		gbc_searchResultDeletePanel.fill = GridBagConstraints.BOTH;
		gbc_searchResultDeletePanel.gridx = 0;
		gbc_searchResultDeletePanel.gridy = gridY;
		panel.add(searchResultDeletePanel, gbc_searchResultDeletePanel);
		gridY++;

		return panel;
	}
	
	JTable table = null;
    DefaultTableModel tableModel = null;
	JPanel createListPanel()
	{
		listPanel = new JPanel();
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		listPanel.setLayout(gridBagLayout);
		
		//==========================================================
//		String[] columnNames = {"����","ʱ��","����","��(cm)","��(cm)","��(cm)","��С�������(cm3)","��ʵ���(cm3)","��ת�Ƕ�"};   //����
		String[] columnNames = {"����","ʱ��","����","��(cm)","��(cm)","��(cm)","��С�������(cm3)","��ʵ���(cm3)"};   //����

		tableModel = new DefaultTableModel(null,columnNames);
        table = new JTable(tableModel);
        
		//==========================================================
        TableColumnModel tcm = table.getColumnModel();
        
        //����
        TableColumn tc0 = tcm.getColumn(0);
        tc0.setMaxWidth(50);

        //ʱ��
        TableColumn tc1 = tcm.getColumn(1);
        tc1.setMinWidth(80);

        //����
        TableColumn tc2 = tcm.getColumn(2);
        tc2.setMinWidth(120);

        //�����
        TableColumn tc3 = tcm.getColumn(3);
        tc3.setMaxWidth(70);

        //�����
        TableColumn tc4 = tcm.getColumn(4);
        tc4.setMaxWidth(70);

        //�����
        TableColumn tc5 = tcm.getColumn(5);
        tc5.setMaxWidth(70);

        //��С�������
        TableColumn tc6 = tcm.getColumn(6);
        tc6.setMinWidth(120);
        tc6.setMaxWidth(120);

        //��ʵ���
        TableColumn tc7 = tcm.getColumn(7);
        tc7.setMinWidth(120);
        tc7.setMaxWidth(120);

        //==========================================================
        JScrollPane scrollPanel = new JScrollPane(table);   //֧�ֹ���

		GridBagConstraints gbc_resultScrollPanel = new GridBagConstraints();
		gbc_resultScrollPanel.fill = GridBagConstraints.BOTH;
		gbc_resultScrollPanel.gridwidth = 1;
		gbc_resultScrollPanel.insets = new Insets(0, 0, 5, 5);
		gbc_resultScrollPanel.gridx = 0;
		gbc_resultScrollPanel.gridy = 0;
		listPanel.add(scrollPanel,gbc_resultScrollPanel);		

		return listPanel;
	}
	
	JPanel createClearPanel()
	{
		JPanel panel = new JPanel();
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0,0};
		gridBagLayout.rowHeights = new int[]{20};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		httpStateLabel = new JLabel("�����������״̬��");
		GridBagConstraints gbc_httpStateLabel = new GridBagConstraints();
		gbc_httpStateLabel.fill = GridBagConstraints.BOTH;
		gbc_httpStateLabel.gridwidth = 1;
		gbc_httpStateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_httpStateLabel.gridx = gridX;
		gbc_httpStateLabel.gridy = gridY;
		panel.add(httpStateLabel,gbc_httpStateLabel);		
		gridX++;		
		
		JButton deleteButton = new JButton("�����ʾ����"); 
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.fill = GridBagConstraints.BOTH;
		gbc_deleteButton.gridwidth = 1;
		gbc_deleteButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteButton.gridx = gridX;
		gbc_deleteButton.gridy = gridY;
		panel.add(deleteButton,gbc_deleteButton);		
		//Button�¼�
		deleteButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				iAllRead = 0;
				iValidBarCodeRead = 0;
				readBarCodePercentLabel.setText("100%");
				
				//===============================================================
				table.removeAll();
				
				((DefaultTableModel) table.getModel()).getDataVector().clear();
		        ((DefaultTableModel) table.getModel()).fireTableDataChanged();
		         table.updateUI();

		         LMSLog.d(DEBUG_TAG, "removeAll");
			}
		});
		gridX++;
		
		return panel;
	}
	
	void listUpdate(String nowTime,JPanel panel,int iWidth,int iHeight,int iLength,String sBarCode,int iBoxVolumn,int iRealVolumn,float fRotateAngle)
	{		
		String []rowValues = 
			{String.valueOf(table.getRowCount()+1),
			 nowTime,
			 sBarCode,
			 String.valueOf((float)iLength/10),
			 String.valueOf((float)iWidth/10),
			 String.valueOf((float)iHeight/10),
			 String.valueOf(iBoxVolumn),
			 String.valueOf(iRealVolumn),
			 String.valueOf(fRotateAngle),
			};
        tableModel.addRow(rowValues);  //���һ��

        //ʹ���������һ��
        int rowCount = table.getRowCount()-1;
        table.getSelectionModel().setSelectionInterval(rowCount, rowCount);
        Rectangle rect = table.getCellRect(rowCount, 0, true);
        table.updateUI();
        table.scrollRectToVisible(rect);
        
//		panel.validate();
	}
	
	int iAllRead = 0;
	int iValidBarCodeRead = 0;
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

	        if (event.getEventType() != null && eventType.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT)) 
	        {
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
				{
					if((Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.CAR_RESULT.ordinal())
					{					
						int carWidth=0,carHeight=0,carLength=0,boxVolumn=0,realVolumn=0;
						float rotateAngle=0;
						int rotateCenterX = 0;
						int rotateCenterY = 0;
						String sBarCode = null;
						
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH))
						{
							carWidth = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH); 							
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT))
						{
							carHeight = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH))
						{
							carLength = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_BAR_CODE))
						{
							sBarCode = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_BAR_CODE); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN))
						{
							boxVolumn = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN))
						{
							realVolumn = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_ANGLE))
						{
							rotateAngle = (Float) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_ANGLE); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_CENTER_X))
						{
							rotateCenterX = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_CENTER_X); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_CENTER_Y))
						{
							rotateCenterY = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_CENTER_Y); 
						}
						
				        Date date = new Date();//���ϵͳʱ��.               
				        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						long lTimeOfReceived = date.getTime();	

						if(logisticsMachineConveyorDrawPanel != null)
						{
							LogisticsMachineConveyerObj conveyerObj = new LogisticsMachineConveyerObj(
								lTimeOfReceived,
								carWidth,carLength,
								rotateAngle,
								rotateCenterX,rotateCenterY);
							LogisticsMachineConveyorDrawPanel.conveyerObjList.add(conveyerObj);
						}
						
						listUpdate(nowTime,listPanel,carWidth,carHeight,carLength,sBarCode,boxVolumn,realVolumn,rotateAngle);
						
						iAllRead++;
						
						if(!sBarCode.equals("NoRead"))
						{
							iValidBarCodeRead++;
							
							JdHttpJason jdHttpJason = new JdHttpJason(sBarCode,carLength,carWidth,carHeight,nowTime,lTimeOfReceived);
							HttpJason.jdHttpJasonQueue.offer(jdHttpJason);
						}
						
						float fPercent = (float)iValidBarCodeRead*100/iAllRead;
						DecimalFormat decimalFormat=new DecimalFormat(".0");//���췽�����ַ���ʽ�������С������2λ,����0����. 
						String p=decimalFormat.format(fPercent);//format ���ص����ַ��� 						
						readBarCodePercentLabel.setText(p+"%");
					}
				}
	        }
	        else if (event.getEventType() != null && eventType.equals(LMSConstValue.HTTP_STATE_STRING_INTENT)) 
	        {
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_HTTP_STATE))
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_HTTP_STATE); 
					
					httpStateLabel.setText(str);
				}
	        }
		}
	}
}
