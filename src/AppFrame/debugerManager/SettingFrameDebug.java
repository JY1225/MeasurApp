package AppFrame.debugerManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.OperatingSystemMXBean; 
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppBase.appBase.DateChooseJButton;
import AppFrame.widgets.JLabelComboBox;
import FileManager.FileManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class SettingFrameDebug {
	private final static String DEBUG_TAG="SettingFrameDebug";

	public static EventListener eventListener;
	JPanel panel;

	String fname[] = new String[LMSConstValue.SYSTEM_SENSOR_NUM];
	
	DateChooseJButton startTimeJButton,stopTimeJButton;
	JLabel startTimeLabel;
	JTextField startTimeTextField;
	String startTimeStr = "yyyy-MM-dd HH:mm:ss";
	JLabel stopTimeLabel;
	JTextField stopTimeTextField;
	String stopTimeStr = "yyyy-MM-dd HH:mm:ss";
	
  	SimpleDateFormat inSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	Date d;
	
	long selectdStartTime,selectdStopTime;
	JTextArea JTSystemInfo;
	
	public JPanel createTab() {
		//=============================================================================
		File fileLog = new File("log");  //判断文件夹是否存在,如果不存在则创建文件夹  
 		if (!fileLog.exists()) 
 		{   
 			fileLog.mkdir();
 		}

 		File file = new File("log//data");  //判断文件夹是否存在,如果不存在则创建文件夹  
 		if (!file.exists()) 
 		{   
 			file.mkdir();
 		}

        //=============================================================
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

        //=============================================================
		panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;

		//========================================================================
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"录像类型",
			-1,
			LMSConstValue.nvramRecordType,LMSConstValue.enumRecordType
		);
		gridX+=2;

		//============================================ 		
 		JButton lastFiveButton = new JButton("生成最后5组过车数据");
		GridBagConstraints gbc_lastFiveButton = new GridBagConstraints();
		gbc_lastFiveButton.insets = new Insets(0, 0, 5, 5);
		gbc_lastFiveButton.gridx = gridX;
		gbc_lastFiveButton.gridy = gridY;
		panel.add(lastFiveButton, gbc_lastFiveButton);
		lastFiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastRecord(5);
			}
		});
		gridX++;

 		JButton lastTenButton = new JButton("生成最后10组过车数据");
		GridBagConstraints gbc_lastTenButton = new GridBagConstraints();
		gbc_lastTenButton.insets = new Insets(0, 0, 5, 5);
		gbc_lastTenButton.gridx = gridX;
		gbc_lastTenButton.gridy = gridY;
		panel.add(lastTenButton, gbc_lastTenButton);
		lastTenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastRecord(10);
			}
		});
		gridX++;

		JButton last15Button = new JButton("生成最后15组过车数据");
		GridBagConstraints gbc_last15Button = new GridBagConstraints();
		gbc_last15Button.insets = new Insets(0, 0, 5, 5);
		gbc_last15Button.gridx = gridX;
		gbc_last15Button.gridy = gridY;
		panel.add(last15Button, gbc_last15Button);
		last15Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastRecord(15);
			}
		});
		gridX++;
		
		JButton last20Button = new JButton("生成最后20组过车数据");
		GridBagConstraints gbc_last20Button = new GridBagConstraints();
		gbc_last20Button.insets = new Insets(0, 0, 5, 5);
		gbc_last20Button.gridx = gridX;
		gbc_last20Button.gridy = gridY;
		panel.add(last20Button, gbc_last20Button);
		last20Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastRecord(20);
			}
		});
		gridX++;

		JButton last25Button = new JButton("生成最后25组过车数据");
		GridBagConstraints gbc_last25Button = new GridBagConstraints();
		gbc_last25Button.insets = new Insets(0, 0, 5, 5);
		gbc_last25Button.gridx = gridX;
		gbc_last25Button.gridy = gridY;
		panel.add(last25Button, gbc_last25Button);
		last25Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastRecord(25);
			}
		});
		gridX++;

		JButton last30Button = new JButton("生成最后30组过车数据");
		GridBagConstraints gbc_last30Button = new GridBagConstraints();
		gbc_last30Button.insets = new Insets(0, 0, 5, 5);
		gbc_last30Button.gridx = gridX;
		gbc_last30Button.gridy = gridY;
		panel.add(last30Button, gbc_last30Button);
		last30Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastRecord(30);
			}
		});
		gridX++;

		gridY++;
		
		//============================================ 	
		gridX = 2;
 		JButton lastFiveMinButton = new JButton("生成最后5分钟数据");
		GridBagConstraints gbc_lastFiveMinButton = new GridBagConstraints();
		gbc_lastFiveMinButton.insets = new Insets(0, 0, 5, 5);
		gbc_lastFiveMinButton.gridx = gridX;
		gbc_lastFiveMinButton.gridy = gridY;
		panel.add(lastFiveMinButton, gbc_lastFiveMinButton);
		lastFiveMinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastMinRecord(5);
			}
		});
		gridX++;
 		
 		JButton lastTenMinButton = new JButton("生成最后10分钟数据");
		GridBagConstraints gbc_lastTenMinButton = new GridBagConstraints();
		gbc_lastTenMinButton.insets = new Insets(0, 0, 5, 5);
		gbc_lastTenMinButton.gridx = gridX;
		gbc_lastTenMinButton.gridy = gridY;
		panel.add(lastTenMinButton, gbc_lastTenMinButton);
		lastTenMinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastMinRecord(10);
			}
		});
		gridX++;

		JButton last15MinButton = new JButton("生成最后15分钟数据");
		GridBagConstraints gbc_last15MinButton = new GridBagConstraints();
		gbc_last15MinButton.insets = new Insets(0, 0, 5, 5);
		gbc_last15MinButton.gridx = gridX;
		gbc_last15MinButton.gridy = gridY;
		panel.add(last15MinButton, gbc_last15MinButton);
		last15MinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastMinRecord(15);
			}
		});
		gridX++;
		
		JButton last20MinButton = new JButton("生成最后20分钟数据");
		GridBagConstraints gbc_last20MinButton = new GridBagConstraints();
		gbc_last20MinButton.insets = new Insets(0, 0, 5, 5);
		gbc_last20MinButton.gridx = gridX;
		gbc_last20MinButton.gridy = gridY;
		panel.add(last20MinButton, gbc_last20MinButton);
		last20MinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastMinRecord(20);
			}
		});
		gridX++;
		
		JButton last25MinButton = new JButton("生成最后25分钟数据");
		GridBagConstraints gbc_last25MinButton = new GridBagConstraints();
		gbc_last25MinButton.insets = new Insets(0, 0, 5, 5);
		gbc_last25MinButton.gridx = gridX;
		gbc_last25MinButton.gridy = gridY;
		panel.add(last25MinButton, gbc_last25MinButton);
		last25MinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastMinRecord(25);
			}
		});
		gridX++;
		
		JButton last30MinButton = new JButton("生成最后30分钟数据");
		GridBagConstraints gbc_last30MinButton = new GridBagConstraints();
		gbc_last30MinButton.insets = new Insets(0, 0, 5, 5);
		gbc_last30MinButton.gridx = gridX;
		gbc_last30MinButton.gridy = gridY;
		panel.add(last30MinButton, gbc_last30MinButton);
		last30MinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateLastMinRecord(30);
			}
		});
		gridX++;
		
		gridY++;
		
 		//============================================
 		for(int i=0;i<LMSConstValue.SYSTEM_SENSOR_NUM;i++)
 		{
 			fname[i] = new String();
 		}
 		
		//=====================================================
 		gridX = 0;
		JSplitPane timeSelectPanel = new JSplitPane();
		GridBagConstraints gbc_splitPanelTimeSelect = new GridBagConstraints();
		gbc_splitPanelTimeSelect.insets = new Insets(0, 0, 5, 0);
		gbc_splitPanelTimeSelect.fill = GridBagConstraints.BOTH;
		gbc_splitPanelTimeSelect.gridwidth = 6;
		gbc_splitPanelTimeSelect.gridx = gridX;
		gbc_splitPanelTimeSelect.gridy = gridY;
		panel.add(timeSelectPanel, gbc_splitPanelTimeSelect);
		createTimeSelectSplitPanel(timeSelectPanel);
		
		gridX+=gbc_splitPanelTimeSelect.gridwidth;

		//=====================================================
		/*
		JSplitPane fileSelectPanel = new JSplitPane();
		GridBagConstraints gbc_splitPanelFileSelect = new GridBagConstraints();
		gbc_splitPanelFileSelect.insets = new Insets(0, 0, 5, 0);
		gbc_splitPanelFileSelect.fill = GridBagConstraints.BOTH;
		gbc_splitPanelFileSelect.gridwidth = 5;
		gbc_splitPanelFileSelect.gridx = gridX;
		gbc_splitPanelFileSelect.gridy = gridY;
		panel.add(fileSelectPanel, gbc_splitPanelFileSelect);
		createFileSelectSplitPanel(fileSelectPanel);
		*/
		gridY++;
		gridX = 0;
		
		JButton systemInfoButton = new JButton("获取系统信息");
		GridBagConstraints gbc_systemInfoButton = new GridBagConstraints();
		gbc_systemInfoButton.insets = new Insets(0, 0, 5, 5);
		gbc_systemInfoButton.gridx = gridX;
		gbc_systemInfoButton.gridy = gridY;
		panel.add(systemInfoButton, gbc_systemInfoButton);
		systemInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final long MB = 1024 * 1024;  
				
				OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean(); 
				long totalPhysicalMemory = getLongFromOperatingSystem(system,"getTotalPhysicalMemorySize");  
	            long freePhysicalMemory = getLongFromOperatingSystem(system, "getFreePhysicalMemorySize");  
	            long usedPhysicalMemorySize =totalPhysicalMemory - freePhysicalMemory;  
	            
				MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();  
				MemoryUsage usage = memorymbean.getHeapMemoryUsage();  		
				
				RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();  
				List<String> inputArguments = runtime.getInputArguments();   
				
				String str = "";
				str += "总物理内存(M):"+totalPhysicalMemory/MB+";  ";
				str += "已用物理内存(M):"+usedPhysicalMemorySize/MB+";  ";
				str += "剩余物理内存(M):"+freePhysicalMemory/MB+";  ";
				str += "\r\n";
				str += "堆上限(M):"+ usage.getMax()/MB+";  ";
				str += "初始堆(M):"+ usage.getInit()/MB+";  ";
				str += "已使用(M): "+ usage.getUsed()/MB+"  ";
				str += "\r\n";
				str += "jvm版本:"+runtime.getVmVersion();
				str += "\r\n";
				str += "VM参数:"+inputArguments;
				JTSystemInfo.setText(str);
			}
		});
		gridX++;
		
		JTSystemInfo = new JTextArea();
		JTSystemInfo = new JTextArea();
		JTSystemInfo.setLineWrap(true);        //激活自动换行功能 
		JTSystemInfo.setWrapStyleWord(true);            // 激活断行不断字功能

		JScrollPane scrollPane = new JScrollPane(JTSystemInfo);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = gridX;
		gbc_scrollPane.gridy = gridY;
		gbc_scrollPane.gridwidth = 10;
		panel.add(scrollPane, gbc_scrollPane);
		gridY++;
		
		//=====================================================
		return panel;
	}
	
	private static long getLongFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {  
        try {  
            final Method method = operatingSystem.getClass().getMethod(methodName,  
                    (Class<?>[]) null);  
            method.setAccessible(true);  
            return (Long) method.invoke(operatingSystem, (Object[]) null);  
        } catch (final InvocationTargetException e) {  
            if (e.getCause() instanceof Error) {  
                throw (Error) e.getCause();  
            } else if (e.getCause() instanceof RuntimeException) {  
                throw (RuntimeException) e.getCause();  
            }  
            throw new IllegalStateException(e.getCause());  
        } catch (final NoSuchMethodException e) {  
            throw new IllegalArgumentException(e);  
        } catch (final IllegalAccessException e) {  
            throw new IllegalStateException(e);  
        }  
    }  
  
	void createTimeSelectSplitPanel(JSplitPane splitPanel)
	{
		int gridX=0,gridY=0;

		//--------------------------------------------------
		JLabel timeSelect = new JLabel("时间区域");
		splitPanel.setLeftComponent(timeSelect);

		//--------------------------------------------------
		JPanel timeSelectPanel = new JPanel();
		splitPanel.setRightComponent(timeSelectPanel);
		GridBagLayout gbc_timeSelectPanel = new GridBagLayout();
		gbc_timeSelectPanel.columnWidths = new int[]{400, 200,100};
		gbc_timeSelectPanel.rowHeights = new int[]{25, 25,};
		gbc_timeSelectPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0,Double.MIN_VALUE};
		gbc_timeSelectPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		timeSelectPanel.setLayout(gbc_timeSelectPanel);

		//--------------------------------------------------
		gridX = 0;
		startTimeLabel = new JLabel("起始时间(yyyy-MM-dd HH:mm:ss):");
		GridBagConstraints gbc_startTimeLabel = new GridBagConstraints();
		gbc_startTimeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startTimeLabel.gridx = gridX;
		gbc_startTimeLabel.gridy = gridY;
		timeSelectPanel.add(startTimeLabel, gbc_startTimeLabel);
		gridX++;
		
		startTimeTextField = new JTextField("yyyy-MM-dd HH:mm:ss");
		GridBagConstraints gbc_startTimeTextField = new GridBagConstraints();
		gbc_startTimeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_startTimeTextField.fill = GridBagConstraints.BOTH;
		gbc_startTimeTextField.gridwidth = 1;
		gbc_startTimeTextField.gridx = gridX;
		gbc_startTimeTextField.gridy = gridY;
		timeSelectPanel.add(startTimeTextField, gbc_startTimeTextField);
		startTimeTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = startTimeTextField.getText().toString();
				
				if(!str.equals("") && !str.equals("yyyy-MM-dd HH:mm:ss") && !str.equals(startTimeStr))
				{
					startTimeStr = str;
					
					startTimeLabel.setText("起始时间(yyyy-MM-dd HH:mm:ss):"+str);
					
					LMSLog.d(DEBUG_TAG,"selectdStartTime="+selectdStartTime);
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		startTimeTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
				
				if(keyChar == KeyEvent.VK_MINUS
					||keyChar == KeyEvent.VK_SPACE 
					||keyChar == 58 //分号
					||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
				)
				{
					
				}
				else{  
					e.consume(); //关键，屏蔽掉非法输入   
				}  
		     }  
		});
		gridX++;
		
		if(LMSConstValue.isMyMachine())
		{
			JButton buttonsort = new JButton("算法排序");
			GridBagConstraints gbc_buttonSort = new GridBagConstraints();
			gbc_buttonSort.insets = new Insets(0, 0, 5, 5);
			gbc_buttonSort.gridx = gridX;
			gbc_buttonSort.gridy = gridY;
			timeSelectPanel.add(buttonsort, gbc_buttonSort);
			buttonsort.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){	
					File fileLog = new File("sort");  //判断文件夹是否存在,如果不存在则创建文件夹  
			 		if (!fileLog.exists()) 
			 		{  
			 			fileLog.mkdir();			
			 		}

			 		File file = new File("sort//data");  //判断文件夹是否存在,如果不存在则创建文件夹  
			 		if (!file.exists()) 
			 		{   
			 			file.mkdir();			 			
			 		}
			 		SimpleDateFormat logSdf = new SimpleDateFormat(LMSConstValue.SDF);  
		 			Date d1 ;
		 			Date d2 ;
		 			int sizelength =0;
		 			long logtime1 = 0;
		 			long logtime2 = 0;
		 			String swap =null;
		 			String line =null;
					String path = "sort";
					String path2 ="sort//data";
					String fileName = null;
                    ArrayList<String> str = new ArrayList<>();
			 		LMSLog.d(DEBUG_TAG, "进入循环");
			 		for(int sensorID=0;sensorID<3;sensorID++)
					{
			 			LMSLog.d(DEBUG_TAG, "第"+sensorID+"进入循环");
			 			
						if(sensorID <3)
						{
							fileName = sensorID+".data";
						}
						FileReader fileReader;
						try
						{
							File filerd =new File(path+"//"+fileName);
							if (!filerd.exists())
							{
								LMSLog.d(DEBUG_TAG, "第"+sensorID+"个雷达不存在，出循环");
								continue;
							}
							fileReader = new FileReader(filerd);
							BufferedReader bufferedReader2 = new BufferedReader(fileReader);
							
							try
							  {
								str.clear();
								while((line=bufferedReader2.readLine())!=null)
									str.add(line);
								LMSLog.d(DEBUG_TAG, str.size()+"");
								sizelength = str.size();
							  }
							catch (IOException e1)
							  {
								LMSLog.exceptionDialog("文件操作异常", e1);
							  }
							
						}
						catch (FileNotFoundException e1)
						{
							LMSLog.warningDialog("文件存在", "请把需要排序的文件放入指定文件夹");
							break;
						}
						//开始排序，冒泡算法
						for(int i =0;i<sizelength-1;i++)
						{
							LMSLog.d(DEBUG_TAG, "进行第"+i+"排序");
							if(i<sizelength-300)
							{		
							for(int j = i;j<i+300;j++)
							{
								try
								{
									d1 =logSdf.parse(str.get(j).substring(0, 23));
									d2 = logSdf.parse(str.get(j+1).substring(0, 23));
								    logtime1 = d1.getTime();
								    logtime2 = d2.getTime();
								    if(logtime1>logtime2)
									{
								    	LMSLog.d(DEBUG_TAG, j+"交换数据");
									swap = str.get(j+1);
									str.set(j+1, str.get(j));
									str.set(j, swap);
									}
								}
								catch (ParseException e1)
								{
									// TODO Auto-generated catch block
									LMSLog.d(DEBUG_TAG, "时间格式转换出错。");
								}
							}
							}
							else
							{
								for(int j = i;j<sizelength-1;j++)
								{
									try
									{	
										d1 =logSdf.parse(str.get(j).substring(0, 23));
										d2 = logSdf.parse(str.get(j+1).substring(0, 23));
									    logtime1 = d1.getTime();
									    logtime2 = d2.getTime();
									    if(logtime1>logtime2)
										{
									    	LMSLog.d(DEBUG_TAG, j+"交换数据");
										swap = str.get(j+1);
										str.set(j+1, str.get(j));
										str.set(j, swap);
										}
									}
									catch (ParseException e1)
									{
										// TODO Auto-generated catch block
										LMSLog.d(DEBUG_TAG, "时间格式转换出错。");
									}
						    }
						
							}
							
						}
						try
						{
							LMSLog.d(DEBUG_TAG, "整理数据写入新文件夹");
							File filewr = new File(path2+"//"+fileName);
							if(filewr.exists())
								filewr.delete();
							filewr.createNewFile();
							FileWriter fileWriter = new FileWriter(filewr);
							
							BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
							
							for(int K=0;K<sizelength;K++)
							{
								bufferedWriter.write(str.get(K)+"\r\n");
							    bufferedWriter.flush();
							}
							LMSLog.d(DEBUG_TAG, "文件排序完成");
						}
						catch (IOException e1)
						{
							// TODO Auto-generated catch block
							LMSLog.d(DEBUG_TAG, "文件写入出错");
						}
				  }
				}
				}
			); 	
		}	
		/*
		startTimeJButton = new DateChooseJButton();
		GridBagConstraints gbc_startTimeJButton = new GridBagConstraints();
		gbc_startTimeJButton.insets = new Insets(0, 0, 5, 5);
		gbc_startTimeJButton.gridx = gridX;
		gbc_startTimeJButton.gridy = gridY;
		timeSelectPanel.add(startTimeJButton, gbc_startTimeJButton);
		*/
		gridY++;

		//--------------------------------------------------
		gridX = 0;
		stopTimeLabel = new JLabel("结束时间(yyyy-MM-dd HH:mm:ss):");
		GridBagConstraints gbc_stopTimeLabel = new GridBagConstraints();
		gbc_stopTimeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stopTimeLabel.gridx = gridX;
		gbc_stopTimeLabel.gridy = gridY;
		timeSelectPanel.add(stopTimeLabel, gbc_stopTimeLabel);
		gridX++;

		stopTimeTextField = new JTextField("yyyy-MM-dd HH:mm:ss");
		GridBagConstraints gbc_stopTimeTextField = new GridBagConstraints();
		gbc_stopTimeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_stopTimeTextField.fill = GridBagConstraints.BOTH;
		gbc_stopTimeTextField.gridwidth = 1;
		gbc_stopTimeTextField.gridx = gridX;
		gbc_stopTimeTextField.gridy = gridY;
		timeSelectPanel.add(stopTimeTextField, gbc_stopTimeTextField);
		stopTimeTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = stopTimeTextField.getText().toString();
	
				if(!str.equals("") && !str.equals("yyyy-MM-dd HH:mm:ss") && !str.equals(stopTimeStr))
				{
					stopTimeStr = str;
					
					stopTimeLabel.setText("结束时间(yyyy-MM-dd HH:mm:ss):"+stopTimeStr);
					
					LMSLog.d(DEBUG_TAG,"selectdStopTime="+selectdStopTime);
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
		stopTimeTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar == KeyEvent.VK_MINUS
					||keyChar == KeyEvent.VK_SPACE 
					||keyChar == 58 //分号
					||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
				)
				{  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
		       }  
		     }  
		});
		gridX++;

		//============================================================
		JButton buttonCreate = new JButton("按该时间段生成文件");
		GridBagConstraints gbc_buttonCreate = new GridBagConstraints();
		gbc_buttonCreate.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCreate.gridx = gridX;
		gbc_buttonCreate.gridy = gridY;
		timeSelectPanel.add(buttonCreate, gbc_buttonCreate);
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){			
				try {
					LMSLog.d(DEBUG_TAG,"startTimeStr:"+startTimeStr+" stopTimeStr:"+stopTimeStr);

					d = inSdf.parse(startTimeStr);
					selectdStartTime = d.getTime();

					d = inSdf.parse(stopTimeStr);
					selectdStopTime = d.getTime();

					(new GeneratingDialog()).start();				

					generateRecordFile(selectdStartTime,selectdStopTime);
					
					dialog.dispose();
					
					JOptionPane.showMessageDialog(null, "数据生成完毕,请将log/data文件夹压缩并发送", "数据生成完毕", JOptionPane.INFORMATION_MESSAGE); 
				} catch (ParseException exception) {
					// TODO Auto-generated catch block
					LMSLog.exceptionDialog("日期格式不正确", exception);
				}
			}
		}); 
		gridX++;
		
		gridY++;
	}
	
	/*
	void createFileSelectSplitPanel(JSplitPane splitPanel)
	{
		int gridX=0,gridY=0;

		//--------------------------------------------------
		JLabel fileSelect = new JLabel("文件选择");
		splitPanel.setLeftComponent(fileSelect);

		//--------------------------------------------------
		JPanel fileSelectPanel = new JPanel();
		splitPanel.setRightComponent(fileSelectPanel);
		GridBagLayout gbc_fileSelectPanel = new GridBagLayout();
		gbc_fileSelectPanel.columnWidths = new int[]{400, 0};
		gbc_fileSelectPanel.rowHeights = new int[]{20, 20, 20, 20};
		gbc_fileSelectPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0,Double.MIN_VALUE};
		gbc_fileSelectPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0,Double.MIN_VALUE};
		fileSelectPanel.setLayout(gbc_fileSelectPanel);

		//--------------------------------------------------
		gridX = 0;
		for(int i=0;i<4;i++)
 		{
 			JButton button = new JButton("雷达"+i);

			FileChooseActionListener fileChooseActionListener = new FileChooseActionListener(button,i);			
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridx = gridX;
			gbc_button.gridy = gridY;
			fileSelectPanel.add(button, gbc_button);
			button.addActionListener(fileChooseActionListener); 
			gridY++;
	 	}
		
		gridY++;
		
		//============================================================
		gridX = 0;
		JButton buttonCreate = new JButton("生成文件");
		GridBagConstraints gbc_buttonCreate = new GridBagConstraints();
		gbc_buttonCreate.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCreate.gridx = gridX;
		gbc_buttonCreate.gridy = gridY;
		fileSelectPanel.add(buttonCreate, gbc_buttonCreate);
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				LMSLog.d(DEBUG_TAG,"buttonCreate");
			
				generateRecordFile(selectdStartTime,selectdStopTime);
			}
		}); 
	}
	*/
	
	void generateLastRecord(int last)
	{
		long lStartTime,lStopTime;
		
		int iValidTime = 0;
		String strStartTimeTmp = null;
		String strStartTime = null;
		
		SettingFrameCarOut.readCarOut();
		
		for(int i=0;i<SettingFrameCarOut.carOutString.size()-1;i++)
		{
			strStartTimeTmp = SettingFrameCarOut.carOutString.get(i).substring(0,23);
			if(!strStartTimeTmp.equals(LMSConstValue.strSimulateTime))
			{
				LMSLog.d(DEBUG_TAG,"strStartTimeTmp="+strStartTimeTmp);

				iValidTime++;
				strStartTime = strStartTimeTmp;
				if(iValidTime >= last)
					break;
			}
		}		
		try {
			d = inSdf.parse(strStartTime);
		} catch (ParseException e) {
			LMSLog.exception(e);
		}
		lStartTime = d.getTime();
		
        //======================================================================
    	SimpleDateFormat df = new SimpleDateFormat(LMSConstValue.SDF);//设置日期格式
    	String StrStopTime = df.format(new Date());
		try {
			d = inSdf.parse(StrStopTime);
		} catch (ParseException e) {
			LMSLog.exception(e);
		}
		lStopTime = d.getTime();
		
		LMSLog.d(DEBUG_TAG,"generateLastRecord strStartTime="+strStartTime+" StrStopTime="+StrStopTime);

		(new GeneratingDialog()).start();
		
		generateRecordFile(lStartTime,lStopTime);		
		
		dialog.dispose();
		
		JOptionPane.showMessageDialog(null, "数据生成完毕,请将log/data文件夹压缩并发送", "数据生成完毕", JOptionPane.INFORMATION_MESSAGE); 
	}
	
	void generateLastMinRecord(int lastMin)
	{
		long startTime,stopTime;
		
        //======================================================================
    	SimpleDateFormat df = new SimpleDateFormat(LMSConstValue.SDF);//设置日期格式
    	String StrStopTime = df.format(new Date());
		try {
			d = inSdf.parse(StrStopTime);
		} catch (ParseException e) {
			LMSLog.exception(e);
		}
		stopTime = d.getTime();
		
		LMSLog.d(DEBUG_TAG,"generateLastRecord!!!"+(stopTime-lastMin*60*1000)+" "+stopTime);

		(new GeneratingDialog()).start();
		
		generateRecordFile(stopTime-lastMin*60*1000,stopTime);		
		
		dialog.dispose();
		
		JOptionPane.showMessageDialog(null, "数据生成完毕,请将log/data文件夹压缩并发送", "数据生成完毕", JOptionPane.INFORMATION_MESSAGE); 
	}
	
	JDialog dialog;
	public class GeneratingDialog extends Thread
	{
		public void run()
		{
			JOptionPane op = new JOptionPane("本对话框将在30秒后关闭",JOptionPane.INFORMATION_MESSAGE);
		        
			dialog = op.createDialog("数据生成中,请等候2分钟");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setAlwaysOnTop(true);
			dialog.setModal(false);
			dialog.setVisible(true);
			dialog.show();
		}
	}
	 
	void generateRecordFile(long startTime,long stopTime)
	{
		LMSLog.d(DEBUG_TAG,"generateLastRecord lStartTime="+startTime+" lStopTime="+stopTime);

		FileManager.fileCopy("CarDetect.ini","log//data//CarDetect.ini");
		FileManager.fileCopy("log//CarOut.log","log//data//CarOut.log");
		FileManager.fileCopy("log//CarOut.log.1","log//data//CarOut.log.1");
		for(int sensorID=0;sensorID<LMSConstValue.SYSTEM_SENSOR_NUM;sensorID++)
		{
			String path = "log";
			String fileName = null;

			if(sensorID < LMSConstValue.RADAR_SENSOR_NUM)
			{
				fileName = sensorID+".data";
			}
			else if(sensorID == LMSConstValue.LIGHT_CURTAIN_ID_START)
			{
				fileName = "lightCurtain0.data";				
			}
			
			if(fileName != null)
			{
				LMSLog.d(DEBUG_TAG+sensorID,"input file:"+fname[sensorID]+" fileName="+fileName);
				
				if(!fname[sensorID].equals(""))
				{		    						    				
					generateDataFile(fname[sensorID],fileName,false,startTime,stopTime);
				}
				else
				{					
			        List<File> list = FileManager.getFileSort(path);
			        
					boolean bFirst = true;
			        for (File file : list) {					            
			            if(file.getName().startsWith(fileName)){
				            LMSLog.d(DEBUG_TAG,path+"//"+file.getName() + " : " + file.lastModified());
				            
				            SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.SDF);
				            Date date= new Date(file.lastModified());
				            LMSLog.d(DEBUG_TAG,"lastModified TIME="+ sdf.format(date));
				            
				            if(bValidDataFile(path+"//"+file.getName(),startTime,stopTime))
				            {
					            boolean bAppend = true;
					            if(bFirst == true)
					            {
					            	bFirst = false;
					            	bAppend = false;
					            }
					            
					            generateDataFile(path+"//"+file.getName(),path+"//data//"+fileName,bAppend,startTime,stopTime);
				            }
			            }
			        }					        
				}
			}
		}
	}
	
	boolean bValidDataFile(String inFile,long startTime,long stopTime)
	{
	  	SimpleDateFormat logSdf = new SimpleDateFormat(LMSConstValue.SDF);  
		Date d;
		long logTime;
		
        //=====================================================
		// 使用随机读取
        RandomAccessFile fileRead = null;
        //使用读模式
        try {
			fileRead = new RandomAccessFile(inFile, "r");
			//读取文件长度
	        long length = fileRead.length();
	        //如果是0，代表是空文件，直接返回空结果
	        if (length == 0L)
	        {
	            return false;
	        }
	        else
	        {
	        	long pos;
	        	//从最后读起
                pos = length - 1;
                while (pos > 0)
                {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n')
                    {
                        //使用readLine获取当前行
                        String line = fileRead.readLine();
                        
    					try {	
	                        d = logSdf.parse(line.substring(0,23));
							logTime = d.getTime();
							//如果最后一行都比起始时间小,则本文件无效
							if(logTime<startTime)
							{
								return false;
							}
							else
							{
								break;
							}
    					} catch (ParseException e) {
    						// TODO Auto-generated catch block
    						LMSLog.exception(e);
    					} catch (StringIndexOutOfBoundsException e) {
    						// TODO Auto-generated catch block
    						LMSLog.exception(e);
    					}
                    }
                }
                if (pos == 0)
                {
                    return false;
                }
                
                //=====================================================
                //从开始读起
                pos = 0;
                while (pos < length - 1)
                {
                    pos++;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n')
                    {
                        //使用readLine获取当前行
                        String line = fileRead.readLine();
                        
    					try {	
	                        d = logSdf.parse(line.substring(0,23));
							logTime = d.getTime();
							//如果开始都比最后时间大,则本文件无效
							if(logTime>stopTime)
							{
								return false;
							}
							else
							{
								break;
							}
    					} catch (ParseException e) {
    						// TODO Auto-generated catch block
    						LMSLog.exception(e);
    					} catch (StringIndexOutOfBoundsException e) {
    						// TODO Auto-generated catch block
    						LMSLog.exception(e);
    					}
                    }
                }
                if (pos == 0)
                {
                    return false;
                }
	        }
		} catch (IOException e) {
			LMSLog.exception(e);
		}
        
        return true;
	}
	
	void generateDataFile(String inFile,String outFile,boolean bAppend,long startTime,long stopTime)
	{
	   	String packet;   	
	  	SimpleDateFormat logSdf = new SimpleDateFormat(LMSConstValue.SDF);  
		Date d;
		long logTime;
		
        //=====================================================
        LMSLog.d(DEBUG_TAG,"bAppend="+bAppend);

	    try {
			FileReader fileReader = new FileReader(inFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
    		try {
        		FileWriter fileWriter = new FileWriter(outFile,bAppend);
				while((packet = bufferedReader.readLine()) != null)
				{
					try {
						d = logSdf.parse(packet.substring(0,23));
						logTime = d.getTime();
						if(logTime>=startTime&&logTime<=stopTime)
						{
//		    				LMSLog.d(DEBUG_TAG,"packet:"+packet);
							fileWriter.write(packet+"\r\n");
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						LMSLog.exception(e);
					} catch (StringIndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						LMSLog.exception(e);
					}
				}
	    		fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LMSLog.exception(e);
			}
  		} catch (FileNotFoundException e) {
			LMSLog.exception(e);
		}
	}	
    
	public class FileChooseActionListener implements ActionListener { 
	    private JButton button; 
	    private int sensorID;
	    
	    public FileChooseActionListener(JButton button,int sensorID){ 
	        this.button = button; 
	        this.sensorID = sensorID; 
	    } 
	    
	    //实现接口中的抽象方法 
	    public void actionPerformed(ActionEvent e) {
	    	JFileChooser fDialog;
	    	JFrame frame = null;
						
		    fDialog=new JFileChooser(new File("..")); //文件选择器
		    int result=fDialog.showOpenDialog(frame);
		    if(result==JFileChooser.APPROVE_OPTION){
		    	File f=fDialog.getSelectedFile();
		    	fname[sensorID]=f.getPath();
//			    	String fname=fDialog.getName();
		    	button.setText(fname[sensorID]);
		    }
		} 
	} 
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    	
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

	        }
		}
	}
}
