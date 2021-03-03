package AppFrame.contourDetection;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import database.DataBaseSearch;
import database.DataBaseThread;
import database.DataBaseThread.DataBaseUserTypeEnum;

import AppBase.appBase.AppConst;
import SensorBase.LMSConstValue;

public class ContourDetectionTabPanelSearch {
	private final static String DEBUG_TAG="ContourDetectionTabPanelSearch";
	
	public static DataBaseSearch dataBaseSearch = new DataBaseSearch();
	public static JPanel searchResultPanel;

	private JButton dataTimeSubmitQuery;
	private JTextField vehicleNumTextField;
	
	private Object gbc_vehicleNumQueryButton; 
	
	@SuppressWarnings("deprecation")
	public ContourDetectionTabPanelSearch() {
		new DataBaseThread(
			DataBaseUserTypeEnum.LOCAL_DATABASE,					
			ContourDetectionDataBaseConst.DATA_BASE_NAME, ContourDetectionDataBaseConst.TABLE_NAME, ContourDetectionDataBaseConst.createTableSQL,
			LMSConstValue.sNvramLocalStoreDataBaseIP.sValue, LMSConstValue.sNvramLocalStoreDataBasePORT.sValue,
			LMSConstValue.sNvramLocalStoreDataBaseName.sValue,LMSConstValue.sNvramLocalStoreDataBasePassword.sValue
		).start();
	}

	public JSplitPane createTab() {
		JSplitPane mainTab = new JSplitPane(JSplitPane.VERTICAL_SPLIT,createSearchPanel(),createSearchResultPanel());

		return mainTab;
    }
	
	JPanel createSearchPanel()
	{
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 120, 120};
		gridBagLayout.rowHeights = new int[]{20, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;
	
		JButton allQueryButton = new JButton("查询所有"); 
		GridBagConstraints gbc_allQueryButton = new GridBagConstraints();
		gbc_allQueryButton.fill = GridBagConstraints.BOTH;
		gbc_allQueryButton.gridwidth = 1;
		gbc_allQueryButton.insets = new Insets(0, 0, 5, 5);
		gbc_allQueryButton.gridx = gridX;
		gbc_allQueryButton.gridy = gridY;
		panel.add(allQueryButton,gbc_allQueryButton);		
		//Button事件
		allQueryButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){ 
				dataBaseSearch.queryAll(
					searchResultPanel,
					ContourDetectionDataBaseConst.TABLE_NAME,
					ContourDetectionDataBaseConst.TABLE_DISPLAY_COLUMN);
			}
		});
		gridX++;

		JButton currentDayQueryButton = new JButton("查询当天"); 
		GridBagConstraints gbc_currentDayQueryButton = new GridBagConstraints();
		gbc_currentDayQueryButton.fill = GridBagConstraints.BOTH;
		gbc_currentDayQueryButton.gridwidth = 1;
		gbc_currentDayQueryButton.insets = new Insets(0, 0, 5, 5);
		gbc_currentDayQueryButton.gridx = gridX;
		gbc_currentDayQueryButton.gridy = gridY;
		panel.add(currentDayQueryButton,gbc_currentDayQueryButton);		
		currentDayQueryButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){ 
				dataBaseSearch.queryToday(
					searchResultPanel,
					ContourDetectionDataBaseConst.TABLE_NAME,
					ContourDetectionDataBaseConst.TABLE_DISPLAY_COLUMN,
					ContourDetectionDataBaseConst.TABLE_COLUMN_TIME);
			}
		});
		gridX++;

		JButton currentMonthQueryButton = new JButton("查询本月"); 
		GridBagConstraints gbc_currentMonthQueryButton = new GridBagConstraints();
		gbc_currentMonthQueryButton.fill = GridBagConstraints.BOTH;
		gbc_currentMonthQueryButton.gridwidth = 1;
		gbc_currentMonthQueryButton.insets = new Insets(0, 0, 5, 5);
		gbc_currentMonthQueryButton.gridx = gridX;
		gbc_currentMonthQueryButton.gridy = gridY;
		panel.add(currentMonthQueryButton,gbc_currentMonthQueryButton);		
		currentMonthQueryButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){ 
				dataBaseSearch.queryThisMonth(
					searchResultPanel,
					ContourDetectionDataBaseConst.TABLE_NAME,
					ContourDetectionDataBaseConst.TABLE_DISPLAY_COLUMN,
					ContourDetectionDataBaseConst.TABLE_COLUMN_TIME);
			}
		});
		
		gridX = 0;
		gridY++;
		
		//==========================================================
		if(AppConst.bShowVehicleNum == true)
		{
			JLabel vehicleNumLabel = new JLabel("车牌号"); 
			GridBagConstraints gbc_vehicleNumLabel = new GridBagConstraints();
			gbc_vehicleNumLabel.fill = GridBagConstraints.BOTH;
			gbc_vehicleNumLabel.gridwidth = 1;
			gbc_vehicleNumLabel.insets = new Insets(0, 0, 5, 5);
			gbc_vehicleNumLabel.gridx = gridX;
			gbc_vehicleNumLabel.gridy = gridY;
			panel.add(vehicleNumLabel,gbc_vehicleNumLabel);	
			gridX++;
	
			vehicleNumTextField = new JTextField(); 
			GridBagConstraints gbc_vehicleNumTextField = new GridBagConstraints();
			gbc_vehicleNumTextField.fill = GridBagConstraints.BOTH;
			gbc_vehicleNumTextField.gridwidth = 1;
			gbc_vehicleNumTextField.insets = new Insets(0, 0, 5, 5);
			gbc_vehicleNumTextField.gridx = gridX;
			gbc_vehicleNumTextField.gridy = gridY;
			panel.add(vehicleNumTextField,gbc_vehicleNumTextField);	
			gridX++;
	
			JButton vehicleNumQueryButton = new JButton("按车牌查询"); 
			GridBagConstraints gbc_vehicleNumQueryButton = new GridBagConstraints();
			gbc_vehicleNumQueryButton.fill = GridBagConstraints.BOTH;
			gbc_vehicleNumQueryButton.gridwidth = 1;
			gbc_vehicleNumQueryButton.insets = new Insets(0, 0, 5, 5);
			gbc_vehicleNumQueryButton.gridx = gridX;
			gbc_vehicleNumQueryButton.gridy = gridY;
			panel.add(vehicleNumQueryButton,gbc_vehicleNumQueryButton);		
			vehicleNumQueryButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ){
					String query;
					
					String str = vehicleNumTextField.getText();
					if(!str.equals(""))
					{
						dataBaseSearch.queryColumnString(
							searchResultPanel,
							ContourDetectionDataBaseConst.TABLE_NAME,
							ContourDetectionDataBaseConst.TABLE_DISPLAY_COLUMN,
							ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_NUM,str);					
					}
				}
			});
			gridX++;
		}

		return panel;
	}
		
	JPanel createSearchResultPanel()
	{
		JPanel panel = new JPanel();		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		panel.setLayout(gridBagLayout);
		
		//=====================================================
		int gridY = 0;
		
		JPanel searchResultTablePanel = createSearchResultTablePanel();		
		GridBagConstraints gbc_searchResultTablePanel = new GridBagConstraints();
		gbc_searchResultTablePanel.insets = new Insets(0, 0, 5, 0);
		gbc_searchResultTablePanel.fill = GridBagConstraints.BOTH;
		gbc_searchResultTablePanel.gridx = 0;
		gbc_searchResultTablePanel.gridy = gridY;
		panel.add(searchResultTablePanel, gbc_searchResultTablePanel);
		gridY++;
		
		JPanel searchResultDeletePanel = createSearchResultDeletePanel();		
		GridBagConstraints gbc_searchResultDeletePanel = new GridBagConstraints();
		gbc_searchResultDeletePanel.insets = new Insets(0, 0, 5, 0);
		gbc_searchResultDeletePanel.fill = GridBagConstraints.BOTH;
		gbc_searchResultDeletePanel.gridx = 0;
		gbc_searchResultDeletePanel.gridy = gridY;
		panel.add(searchResultDeletePanel, gbc_searchResultDeletePanel);
		gridY++;

		return panel;
	}
	
	JPanel createSearchResultTablePanel()
	{
		searchResultPanel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		searchResultPanel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		/*
		JScrollPane resultScrollPanel = new JScrollPane(); 
		GridBagConstraints gbc_resultScrollPanel = new GridBagConstraints();
		gbc_resultScrollPanel.fill = GridBagConstraints.BOTH;
		gbc_resultScrollPanel.gridwidth = 1;
		gbc_resultScrollPanel.insets = new Insets(0, 0, 5, 5);
		gbc_resultScrollPanel.gridx = gridX;
		gbc_resultScrollPanel.gridy = gridY;
		searchResultPanel.add(scroller,gbc_resultScrollPanel);		
		 */
		
		return searchResultPanel;
	}
	
	JPanel createSearchResultDeletePanel()
	{
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{20};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		JButton deleteButton = new JButton("删除查询结果数据"); 
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.fill = GridBagConstraints.BOTH;
		gbc_deleteButton.gridwidth = 1;
		gbc_deleteButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteButton.gridx = gridX;
		gbc_deleteButton.gridy = gridY;
		panel.add(deleteButton,gbc_deleteButton);		
		//Button事件
		deleteButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){ 
				dataBaseSearch.deleteQueryResult(
					searchResultPanel,
					ContourDetectionDataBaseConst.TABLE_NAME);					
			}
		});
		gridX++;
		
		return panel;
	}
}
 
