package AppFrame.logisticsMachine;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import SensorBase.LMSConstValue;
import database.DataBaseSearch;
import database.DataBaseThread;
import database.DataBaseThread.DataBaseUserTypeEnum;

public class LogisticsMachineTabPanelSearch{
	private final static String DEBUG_TAG="LogisticsMachineSearchPanel";

	public static DataBaseSearch dataBaseSearch = new DataBaseSearch();
	public static JPanel searchResultPanel;
		
	@SuppressWarnings("deprecation")
	public LogisticsMachineTabPanelSearch() {
		new DataBaseThread(
			DataBaseUserTypeEnum.LOCAL_DATABASE,
			LogisticsMachineDataBaseConst.DATA_BASE_NAME, LogisticsMachineDataBaseConst.TABLE_NAME, LogisticsMachineDataBaseConst.createTableSQL,
			LMSConstValue.sNvramLocalStoreDataBaseIP.sValue, LMSConstValue.sNvramLocalStoreDataBasePORT.sValue,
			LMSConstValue.sNvramLocalStoreDataBaseName.sValue,LMSConstValue.sNvramLocalStoreDataBasePassword.sValue
		).start();
	}

	public JSplitPane createTab() {
		return new JSplitPane(JSplitPane.VERTICAL_SPLIT,createSearchPanel(),createSearchResultPanel());
    }
	
	JPanel createSearchPanel()
	{
		JPanel panel = new JPanel();
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 120, 120};
		gridBagLayout.rowHeights = new int[]{20, 20};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;
	
		JButton allQueryButton = new JButton("��ѯ����"); 
		GridBagConstraints gbc_allQueryButton = new GridBagConstraints();
		gbc_allQueryButton.fill = GridBagConstraints.BOTH;
		gbc_allQueryButton.gridwidth = 1;
		gbc_allQueryButton.insets = new Insets(0, 0, 5, 5);
		gbc_allQueryButton.gridx = gridX;
		gbc_allQueryButton.gridy = gridY;
		panel.add(allQueryButton,gbc_allQueryButton);		
		//Button�¼�
		allQueryButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){ 
				dataBaseSearch.queryAll(
					searchResultPanel,
					LogisticsMachineDataBaseConst.TABLE_NAME,
					LogisticsMachineDataBaseConst.TABLE_DISPLAY_COLUMN);
			}
		});
		gridX++;

		JButton currentDayQueryButton = new JButton("��ѯ����"); 
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
					LogisticsMachineDataBaseConst.TABLE_NAME,
					LogisticsMachineDataBaseConst.TABLE_DISPLAY_COLUMN,LogisticsMachineDataBaseConst.TABLE_COLUMN_TIME);
			}
		});
		gridX++;

		JButton currentMonthQueryButton = new JButton("��ѯ����"); 
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
					LogisticsMachineDataBaseConst.TABLE_NAME,
					LogisticsMachineDataBaseConst.TABLE_DISPLAY_COLUMN,
					LogisticsMachineDataBaseConst.TABLE_COLUMN_TIME);
			}
		});
		
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
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		searchResultPanel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;
		
		return searchResultPanel;
	}
	
	JPanel createSearchResultDeletePanel()
	{
		JPanel panel = new JPanel();
		
		//���ò���
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{20};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		JButton deleteButton = new JButton("ɾ����ѯ�������"); 
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
				dataBaseSearch.deleteQueryResult(
					searchResultPanel,		
					LogisticsMachineDataBaseConst.TABLE_NAME);					
			}
		});
		gridX++;
		
		return panel;
	}
}
