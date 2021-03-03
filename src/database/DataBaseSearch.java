package database;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import SensorBase.LMSLog;

public class DataBaseSearch {
	final static String DEBUG_TAG="DataBaseSearchResultPanel";
	
	public static SearchSingleResultInterface searchSingleResultInterface;
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public void displayResultSet(
		JPanel panel,
		ResultSet rs,
		String tableName
		) throws SQLException { 
		//��λ�����һ����¼
		boolean moreRecords = rs.next(); 
			
		//���û�м�¼������ʾһ����Ϣ
		if ( ! moreRecords ) { 
			LMSLog.errorDialog("�޼�¼", "���޼�¼"); 
	
			panel.removeAll();
			panel.updateUI();

			return;
		}
		
		Vector columnHeads = new Vector(); 
		Vector rows = new Vector(); 
		
		try { 
			//��ȡ�ֶε�����
			ResultSetMetaData rsmd = rs.getMetaData(); 	
			
			columnHeads.addElement("����");			   
			for ( int i = 1; i <= rsmd.getColumnCount(); ++i ) 
			{
				DatabaseMetaData dbmd = DataBaseConst.local_store_access_conn.getMetaData();  
				ResultSet rsColumn = dbmd.getColumns(DataBaseConst.local_store_access_conn.getCatalog(), null, tableName, rsmd.getColumnName(i));
				while(rsColumn.next()) {    
					if(rsColumn.getString("REMARKS") != null && !rsColumn.getString("REMARKS").equals(""))
						columnHeads.addElement(rsColumn.getString("REMARKS"));
					else
						columnHeads.addElement(rsmd.getColumnName(i));			   
				}
			}
			
			//��ȡ��¼��
			int i = 0;
			do {
				rows.addElement( getNextRow( ++i, rs, rsmd ) ); 
			} while ( rs.next() ); 
			
			
			//�ڱ������ʾ��ѯ���			
			JTable table = new JTable(rows, columnHeads) 
			{
				//���ɱ༭���������˫���¼�
				public boolean isCellEditable(int row, int column) {
					return false;   
				}; 
			};
			///*
			//����ID��
			TableColumnModel tcm = table.getColumnModel();  
			TableColumn tc = tcm.getColumn(1);
			tc.setMinWidth(0);
			tc.setMaxWidth(0);
			tc.setPreferredWidth(0);
			//*/

			JScrollPane scrollPanel = new JScrollPane(table); 

			panel.removeAll();
			GridBagConstraints gbc_resultScrollPanel = new GridBagConstraints();
			gbc_resultScrollPanel.fill = GridBagConstraints.BOTH;
			gbc_resultScrollPanel.gridwidth = 1;
			gbc_resultScrollPanel.insets = new Insets(0, 0, 5, 5);
			gbc_resultScrollPanel.gridx = 0;
			gbc_resultScrollPanel.gridy = 0;
			panel.add(scrollPanel,gbc_resultScrollPanel);		
			panel.validate();
					
			//ע��˫��ĳ�е���Ӧ�¼�
			table.addMouseListener(new MouseAdapter()
			{ 
				public void mouseClicked(MouseEvent e) 
				{
                    //ʵ��˫�� 
					if(e.getClickCount() == 2)
					{ 
						int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //�����λ�� 
//						int col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //�����λ�� 
						String idString =(String)((JTable)e.getSource()).getValueAt(row,1); //��õ����Ԫ������ 
						
						LMSLog.d(DEBUG_TAG,"row="+row);
						
						searchSingleResultInterface.CREATE_NEW_FRAME(Integer.valueOf(idString));
					} 
					else 
					{
						return; 
					} 
				}
			});
		}catch (SQLException e) { 
			LMSLog.exception(e);
		}
	}


	@SuppressWarnings({"rawtypes","unchecked"})
	private Vector getNextRow(int row, ResultSet rs, ResultSetMetaData rsmd )throws SQLException{
		Vector currentRow = new Vector(); 

		currentRow.addElement(row); 
		for ( int i = 1; i <= rsmd.getColumnCount(); ++i )
		{
			currentRow.addElement( rs.getString( i ) ); 
		}

		//����һ����¼
		return currentRow; 
	}
	
	public boolean bQueryAll;
	private String querySubString;
	private String displayColumnString;
	private static String querySQL;
	public void queryColumnString(JPanel panel,String table,String displayColumn,String serarchColumn,String str)
	{
        bQueryAll = false;
		querySubString = " FROM "+table+" WHERE "+serarchColumn+" = '" + str +"'";
		displayColumnString = displayColumn;

		querySQL =	"SELECT "+displayColumnString+querySubString; 

		getTable(panel,table,querySQL);
	}
	
	private void queryTime(JPanel panel,String table,String displayColumn,String timeColumn,String time)
	{
	    bQueryAll = false;
		querySubString = " FROM "+table+" WHERE "+timeColumn+" >= "+"'"+time+"'";
		displayColumnString = displayColumn;
		
		querySQL ="SELECT "+displayColumnString+querySubString; 
	
		getTable(panel,table,querySQL);
	}
	
	public void queryToday(JPanel panel,String table,String displayColumn,String timeColumn)
	{
        Date date = new Date();//���ϵͳʱ��.               
        String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
        displayColumnString = displayColumn;

	    queryTime(panel,table,displayColumnString,timeColumn,today);
	}
	
	public void queryThisMonth(JPanel panel,String table,String displayColumn,String timeColumn)
	{
	    Date date = new Date();//���ϵͳʱ��.               
	    String thisMonth = new SimpleDateFormat("yyyy-MM").format(date);
	    displayColumnString = displayColumn;

	    queryTime(panel,table,displayColumnString,timeColumn,thisMonth);
	}

	public void queryAll(JPanel panel,String table,String displayColumn)
	{
        bQueryAll = true;
        
        querySQL ="SELECT "+displayColumn+" FROM "+table; 

		LMSLog.d(DEBUG_TAG,"queryAll="+querySQL); 

		getTable(panel,table,querySQL);
	}

	public void queryLast(JPanel panel,String table)
	{        
		LMSLog.d(DEBUG_TAG,"queryLast="+querySQL); 

		getTable(panel,table,querySQL);
	}
	
	public void deleteQueryResult(JPanel panel,String table)
	{
		if(bQueryAll == false &&querySubString != null)
		{
			if(DataBaseConst.local_store_access_conn != null)
			{
				try { 						
					Statement statement = DataBaseConst.local_store_access_conn.createStatement(); 
					String sql = "DELETE"+querySubString; 
					LMSLog.d(DEBUG_TAG,"deleteQueryResult="+sql); 
					
			    	statement.executeUpdate(sql);
			    	
			    	panel.removeAll();
			    	panel.updateUI();
				}catch (SQLException e1) { 
					LMSLog.exception(e1);
				}	
			}
		}
	}
	
	public void deleteQueryID(JPanel panel,int ID,String table)
	{
		jdbc_mysql_delete_id(ID,table);
			
		queryLast(panel,table);
	}
	
	private void jdbc_mysql_delete_id(int id,String table)
	{		
		if(DataBaseConst.local_store_access_conn != null)
		{
			//=========================================================================================
			//����
			// MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ 
			// ������������Ҫָ��useUnicode��characterEncoding 
			// ִ�����ݿ����֮ǰҪ�����ݿ����ϵͳ�ϴ���һ�����ݿ⣬�����Լ����� 
			// �������֮ǰ��Ҫ�ȴ���javademo���ݿ�       
			try { 		
				Statement stmt = DataBaseConst.local_store_access_conn.createStatement(); 
				String sql = "DELETE FROM "+table+" WHERE ID="+id; 
				
				LMSLog.d(DEBUG_TAG,"jdbc_mysql_delete_id="+sql); 
			    stmt.executeUpdate(sql); 
			} catch (SQLException e) { 
				LMSLog.exception(e);
			} catch (Exception e) { 
				LMSLog.exception(e);
			}	
		}
	}
	
	void getTable(JPanel panel,String table,String query){
		if(DataBaseConst.local_store_access_conn != null)
		{
			try { 
				LMSLog.d(DEBUG_TAG,"getTable="+query);
				
				//ִ��SQL���
				Statement statement = DataBaseConst.local_store_access_conn.createStatement(); 
				ResultSet resultSet = statement.executeQuery( query ); 
				
				//�ڱ������ʾ��ѯ���
				displayResultSet(
					panel,
					resultSet,
					table); 
			}catch (SQLException e) { 
				LMSLog.exceptionDialog("���ݿ��쳣", e);
			}
		}
		else
		{
			LMSLog.errorDialog("���ݿ��쳣", "DataBaseConst.local_store_access_conn null");
		}
	}
}
