package AppFrame.debugerManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class SettingFrameHumanAdjust {
	private final static String DEBUG_TAG="SettingFrameHumanAdjust";

	public static EventListener eventListener;

	JPanel panel;
	static JTextArea jTextArea;
 
	public static ArrayList<String> stringList = new ArrayList<String>();  
    public static ArrayList<String> restringlist =new ArrayList<String>();
	final static int MAX_CAR_OUT_LINE_NUM = 100;
	public static int carOutLineNum = 0;
	static void readCarOutInternel(String inFile)
	{
       // RandomAccessFile fileRead = null;

        //ʹ�ö�ģʽ
        try {
        	File fileread = new File(inFile);
        	
        	if(fileread.exists())
        	{
		        long length = fileread.length();
		       
		        //�����0�������ǿ��ļ���ֱ�ӷ��ؿս��
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
		        		// LMSLog.w(DEBUG_TAG, line);               	
		        		stringList.add(line);
		        		LMSLog.w(DEBUG_TAG, "�����Ѿ���������");
	    			}
		        	carOutLineNum = stringList.size();
		        }
        	}
		} catch (FileNotFoundException e) {
			LMSLog.exception(e);
		} catch (IOException e) {
			LMSLog.exception(e);
		}        
	}
	
	public void readCarOut()
	{	 	
    	stringList.clear();
		//carOutLineNum = 0;
		
	    String inFile = "log//HumanAdjust.log";	
	    readCarOutInternel(inFile);
	}
	
	public void generateCarResultTextArea()
	{
	    //===========================================================================
		jTextArea.setText("�޸���־\r\n");
	    for(int i= 0;i<stringList.size();i++)//��Ҫ��ֹ����Խ��
	    {
	    	System.out.println("hello");
	    	String str = stringList.get(i);
	    	
	    //	LMSLog.d(DEBUG_TAG,"carOutString="+str);
	    	LMSLog.w(DEBUG_TAG, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	    	jTextArea.append(str+"\n");              	     
	    }
	}
	
	public void refreshadjustdata(){
		 for(int i= carOutLineNum-1;i<stringList.size();i++)//��Ҫ��ֹ����Խ��
		 {   	
			 String str = stringList.get(i);
		    	
			 LMSLog.d(DEBUG_TAG,"carOutString="+str+"hello");
		    	
			 jTextArea.append(str+"\n");   
			 LMSLog.d(DEBUG_TAG,"carOutString=wwwwwwwwwww");
		 }
	}
	
	
	public JPanel createTab() {
		//=============================================================================
		File fileLog = new File("log");  //�ж��ļ����Ƿ����,����������򴴽��ļ���  
 		if (!fileLog.exists()) 
 		{   
 			fileLog.mkdir();
 		}

 		File file = new File("log//data");  //�ж��ļ����Ƿ����,����������򴴽��ļ���  
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
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
		jTextArea = new JTextArea("�޸���־\r\n");
		jTextArea.setLineWrap(true);        //�����Զ����й��� 
		jTextArea.setWrapStyleWord(true);            // ������в����ֹ���
		
		readCarOut();
		generateCarResultTextArea();
		
		JScrollPane scrollPane = new JScrollPane(jTextArea);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = gridX;
		gbc_scrollPane.gridy = gridY;
		panel.add(scrollPane, gbc_scrollPane);

		gridY+=gbc_scrollPane.gridheight;
		
		//=====================================================
		return panel;
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 

	        if(eventType != null && eventType.equals(LMSConstValue.CAR_OUT_LOG_INTENT))
			{	      
	        	String str = null;
	        	
	        	if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_OUT_LOG_STR))
	        		str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_OUT_LOG_STR); 
			}
		}
	}}
