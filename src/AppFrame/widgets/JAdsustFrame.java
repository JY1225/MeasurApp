package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;


import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.jna.examples.win32.Kernel32.FILE_NOTIFY_INFORMATION;

import AppFrame.debugerManager.SettingFrameCarOut;
import AppFrame.debugerManager.SettingFrameHumanAdjust;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class JAdsustFrame extends JFrame
{
	  static String DEBUG_TAG = "JAdsustFrame";
	  public static Date now = new Date();
	  public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	  public static JLabel JLDataadjustor =new JLabel("数据修正授权人");
	  public static JTextField  JTDataadjustor = new JTextField();
	  public static JLabel JLcheckdata =new JLabel("设备测量数据");
	  public static JTextArea  JTcheckdata = new JTextArea();	
	  public static JLabel JLadjustdata = new JLabel("人工修正数据");
	  public static JTextArea  JTadjustdata = new JTextArea();	
	  public static JLabel JLreason = new JLabel("修正原因");
	  //public static JTextArea  JTreason = new JTextArea();
	  public static JComboBox<String> JTReason =new JComboBox<String>();//下拉列表
	  public static JLabel  JLbeizhu =new JLabel("备注");
	  public static JTextArea JTbeizhu = new JTextArea();
	  public static JButton JBsave = new JButton("保存");
	  public static ArrayList<String> adjustname	= new ArrayList<String>();
	  
	  
	  static void readCarOutInternel(String inFile)
		{
	     // RandomAccessFile fileRead = null;
	
	      //使用读模式
	      try {
	      	File fileread = new File(inFile);
	      	
	      	if(fileread.exists())
	      	{
					
			        long length = fileread.length();
			       
			        //如果是0，代表是空文件，直接返回空结果
			        if (length == 0L)
			        {
			        	return ;
			        }
			        else
			        {
			        	       	
			        	
		                
		                	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileread), "UTF-8"));
		                   
		                      
		                        String line ; 
		                        while ((line=bufferedReader.readLine())!=null)
		    	                {
		                        	
		                            LMSLog.w(DEBUG_TAG, line);               	
		    						adjustname.add(line);
		    						
		    						
		    						
		    	                }
		                	
		                }
		             }
	      	
			} catch (FileNotFoundException e) {
				LMSLog.exception(e);
			} catch (IOException e) {
				LMSLog.exception(e);
			}        
		}
		
		public static void readCarOut()
		{		
	  	    adjustname.clear();
		    String inFile = "log//adjustorname.txt";	
		    readCarOutInternel(inFile);
		}
	  
		
		
		
		
	 
		public JAdsustFrame()
		{setBounds(200, 100, 600, 600);
	
		//==============================================================================
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{829};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0,1.0};
		getContentPane().setLayout(gridBagLayout);
	   //
		setTitle("人工修正内容");
		getContentPane().add(createJPanel());
		
		}
		
		
	
	private JPanel createJPanel()
	{
		JPanel panel = new JPanel();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{0, 0, 0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		//===========================================================================
				int gridX = 0;
				int gridY = 0;
				
	    //=========================================================
				
		    	GridBagConstraints gbc_JLDataadjustor = new GridBagConstraints();
		    	gbc_JLDataadjustor.fill = GridBagConstraints.BOTH;
		    	gbc_JLDataadjustor.insets = new Insets(0, 0, 0, 5);
		    	gbc_JLDataadjustor.gridx = gridX;
		    	gbc_JLDataadjustor.gridy = gridY;
		    	panel.add(JLDataadjustor, gbc_JLDataadjustor);
		    	gridX++;
		//==========================================================
		
		    	GridBagConstraints gbc_JTDataadjustor = new GridBagConstraints();
		    	gbc_JTDataadjustor.insets = new Insets(0, 0, 0, 5);
		    	gbc_JTDataadjustor.fill = GridBagConstraints.BOTH;
		    	gbc_JTDataadjustor.gridx = gridX;
		    	gbc_JTDataadjustor.gridy = gridY;
		    	panel.add(JTDataadjustor,gbc_JTDataadjustor);
		    	gridY++;
		 	    gridX=0;
		//===================================================================
		    	
		    	GridBagConstraints gbc_JLcheckdata = new GridBagConstraints();
		    	gbc_JLcheckdata.insets = new Insets(0, 0, 0, 5);
		    	gbc_JLcheckdata.fill = GridBagConstraints.BOTH;
		    	gbc_JLcheckdata.gridx = gridX;
		    	gbc_JLcheckdata.gridy = gridY;
		    	panel.add(JLcheckdata,gbc_JLcheckdata);
		    	gridX++;
	    //===============================================================
		    	GridBagConstraints gbc_JTcheckdata = new GridBagConstraints();
		    	gbc_JTcheckdata.insets = new Insets(0, 0, 0, 5);
		    	gbc_JTcheckdata.fill = GridBagConstraints.BOTH;
		    	gbc_JTcheckdata.gridx = gridX;
		    	gbc_JTcheckdata.gridy = gridY;
		    	panel.add(JTcheckdata,gbc_JTcheckdata);
		    	JTcheckdata.setLineWrap(true); //激活自动换行功能 
		    	JTcheckdata.setWrapStyleWord(true);
		    	JTcheckdata.setBorder(BorderFactory.createLoweredBevelBorder());
		    	gridY+=gbc_JTcheckdata.gridheight;
				gridX=0;
				// 激活断行不断字功能
		//================================================================  	
		 	   GridBagConstraints gbc_JLadjustdata = new GridBagConstraints();
		    	gbc_JLadjustdata.insets = new Insets(0, 0, 0, 5);
		    	gbc_JLadjustdata.fill = GridBagConstraints.BOTH;
		    	gbc_JLadjustdata.gridx = gridX;
		    	gbc_JLadjustdata.gridy = gridY;
		    	panel.add(JLadjustdata,gbc_JLadjustdata);
		    	gridX++;
	   //===================================================================
		    	GridBagConstraints gbc_JTadjustdata = new GridBagConstraints();
		    	gbc_JTadjustdata.insets = new Insets(0, 0, 0, 5);
		    	gbc_JTadjustdata.fill = GridBagConstraints.BOTH;
		    	gbc_JTadjustdata.gridx = gridX;
		    	gbc_JTadjustdata.gridy = gridY;
		    	panel.add(JTadjustdata,gbc_JTadjustdata);
		    	
		    	JTadjustdata.setLineWrap(true);        //激活自动换行功能 
		    	JTadjustdata.setWrapStyleWord(true);
		    	JTadjustdata.setBorder(BorderFactory.createLoweredBevelBorder());
		    	gridY+=gbc_JTadjustdata.gridheight;
				gridX=0;
	  //====================================================================
		 	   GridBagConstraints gbc_JLreason = new GridBagConstraints();
		    	gbc_JLreason.insets = new Insets(0, 0, 0, 5);
		    	gbc_JLreason.fill = GridBagConstraints.BOTH;
		    	gbc_JLreason.gridx = gridX;
		    	gbc_JLreason.gridy = gridY;
		    	panel.add(JLreason,gbc_JLreason);
		    	gridX++;
	  //===================================================================
		    	
		    	
		    	GridBagConstraints gbc_JTreason = new GridBagConstraints();
		    	gbc_JTreason.insets = new Insets(0, 0, 0, 5);
		    	gbc_JTreason.fill = GridBagConstraints.BOTH;
		    	gbc_JTreason.gridx = gridX;
		    	gbc_JTreason.gridy = gridY;
		    	panel.add(JTReason,gbc_JTreason);	    	
		    	gridY+=gbc_JTreason.gridheight;
				gridX=0;
				int selectedItemIndex = 0;
				readCarOut();
				for(int i=0;i<adjustname.size();i++)
		    	{
		    		String str= adjustname.get(i);
		    		JTReason.addItem(str);
					
					selectedItemIndex = i;
					
		    	}
				LMSLog.d(DEBUG_TAG, "----AAAAAAAA"+selectedItemIndex);
				JTReason.setSelectedIndex(selectedItemIndex);//设置默认值
				
				
				
	 //========================================================================
		 	  
		    	
		    	
		 	  GridBagConstraints gbc_JLbeizhu = new GridBagConstraints();
		    	gbc_JLbeizhu.insets = new Insets(0, 0, 0, 5);
		    	gbc_JLbeizhu.fill = GridBagConstraints.BOTH;
		    	gbc_JLbeizhu.gridx = gridX;
		    	gbc_JLbeizhu.gridy = gridY;
		    	panel.add( JLbeizhu,gbc_JLbeizhu);
		    	gridX++;
	 //===================================================================	
		    	
		    	
		    	 GridBagConstraints gbc_JTbeizhu = new GridBagConstraints();
			    	gbc_JTbeizhu.insets = new Insets(0, 0, 0, 5);
			    	gbc_JTbeizhu.fill = GridBagConstraints.BOTH;
			    	gbc_JTbeizhu.gridx = gridX;
			    	gbc_JTbeizhu.gridy = gridY;
			    	panel.add( JTbeizhu,gbc_JTbeizhu);
			    	JTbeizhu.setLineWrap(true);        //激活自动换行功能 
			    	JTbeizhu.setWrapStyleWord(true);
			    	JTbeizhu.setBorder(BorderFactory.createLoweredBevelBorder());
			    	gridY+=gbc_JTbeizhu.gridheight;
					gridX=0;
		 
	//===================================================================	    	
			 	   
		    	
			 	  
					GridBagConstraints gbc_JBsave = new GridBagConstraints();
					gbc_JBsave.fill = GridBagConstraints.BOTH;
					gbc_JBsave.gridwidth = 1;
					gbc_JBsave.insets = new Insets(0, 0, 5, 5);
					gbc_JBsave.gridx = gridX;
					gbc_JBsave.gridy = gridY;
					panel.add(JBsave,gbc_JBsave);
		    	
					JBsave.addActionListener(new ActionListener() {
						public void actionPerformed(  ActionEvent actionEvent) {
							
							String strfile="";
							strfile +="日期:"+((df.format(now)).toString())+"\t";
							
							strfile +="数据修正授权人:"+JTDataadjustor.getText()+"\t";
							strfile +="设备测量数据:"+JTcheckdata.getText()+ "\t";
							strfile +="人工修正数据:"+JTadjustdata.getText()+"\t";
							
							int i = JTReason.getSelectedIndex();
							strfile +="修正原因:"+adjustname.get(i)+"\t";
							strfile +="备注:"+JTbeizhu.getText()+"\t";
							strfile +="\r\n";
							
							File pwritefile =new File("log");
							if (!pwritefile.exists())
							{
								pwritefile.mkdir();							
							}
							File writefile = new File("log//HumanAdjust.log");
							try {		       
								if(!writefile.exists())
									if(writefile.getParent()!=null && !new File(writefile.getParent()).exists()){
									    new File(writefile.getParent()).mkdirs();
								}
								writefile.createNewFile();
							} catch (Exception e) {
					       		LMSLog.exceptionDialog("文件不存在/文件创建失败", e);
							}
							
							try
							{
								
								BufferedWriter bufferedWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writefile, true), "UTF-8")); 
								
								
								bufferedWtriter.write(strfile);
								bufferedWtriter.flush();
								
								  LMSLog.warningDialog("保存", "文件保存成功");
							}
							catch (IOException e)
							{
								LMSLog.exceptionDialog("文件写入异常", e);
							}
							SettingFrameHumanAdjust carout =new SettingFrameHumanAdjust();
							//carout.readCarOut();
							//carout.generateCarResultTextArea();
							carout.readCarOut();
							carout.refreshadjustdata();
							setVisible(false);
						}
					});
					return panel;		
						
						
						
						
						
						
					
		    	
		    	
		    	
	}
}