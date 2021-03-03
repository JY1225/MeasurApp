package AppFrame.debugerManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class SettingFrameCarOut {
	private final static String DEBUG_TAG="SettingFrameCarOut";

	public static EventListener eventListener;

	JPanel panel;
	JTextArea carResultTextArea;

	public static ArrayList<String> carOutString = new ArrayList<String>();  

	final static int MAX_CAR_OUT_LINE_NUM = 100;
	static int carOutLineNum = 0;
	static void readCarOutInternel(String inFile)
	{
        RandomAccessFile fileRead = null;

        //ʹ�ö�ģʽ
        try {
        	if(new File(inFile).exists())
        	{
				fileRead = new RandomAccessFile(inFile, "r");
				//��ȡ�ļ�����
		        long length = fileRead.length();
		       
		        //�����0�������ǿ��ļ���ֱ�ӷ��ؿս��
		        if (length == 0L)
		        {
		        	return ;
		        }
		        else
		        {
		        	long pos;
		        	//��������
	                pos = length - 1;
	                while (pos > 0)
	                {
	                    pos--;
	                    //��ʼ��ȡ
	                    fileRead.seek(pos);
	                    //�����ȡ��\n�����Ƕ�ȡ��һ��
	                    if (pos == 0||fileRead.readByte() == '\n')
	                    {
	                        //ʹ��readLine��ȡ��ǰ��
	                        String line = fileRead.readLine();
	                		
	    					try {	
	                    		carOutString.add(line.substring(42));
	    					} catch (StringIndexOutOfBoundsException e) {
	    						// TODO Auto-generated catch block
	    						LMSLog.exception(e);
	    					}
	    					
	                		carOutLineNum++;
	                		if(carOutLineNum > MAX_CAR_OUT_LINE_NUM)
	                			break;
	                    }
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
    	LMSLog.d(DEBUG_TAG,"readCarOut");

		carOutString.clear();
		carOutLineNum = 0;
		
	    String inFile = "log//CarOut.log";	
	    readCarOutInternel(inFile);
        
	    if(carOutLineNum < MAX_CAR_OUT_LINE_NUM)
	    {
		    inFile = "log//CarOut.log.1";	
		    readCarOutInternel(inFile);	    	
	    }
	}
	
	void generateCarResultTextArea()
	{
	    //===========================================================================
	    carResultTextArea.setText("������־\r\n");
	    for(int i=carOutString.size()-1;i>=0;i--)
	    {
	    	String str = carOutString.get(i);
	    	
//	    	LMSLog.d(DEBUG_TAG,"carOutString="+str);
	    	
    		carResultTextArea.append(str+"\n");              	     
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
		carResultTextArea = new JTextArea("������־\r\n");
		carResultTextArea.setLineWrap(true);        //�����Զ����й��� 
		carResultTextArea.setWrapStyleWord(true);            // ������в����ֹ���
		readCarOut();
		generateCarResultTextArea();
		
		JScrollPane scrollPane = new JScrollPane(carResultTextArea);
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
	        	
				if(str != null)
				{			
					int lineNum = carResultTextArea.getLineCount();
					    
				    if(lineNum>100)
				    {
			    		String tmp = carResultTextArea.getText();
			    		int index = tmp.indexOf("\n");
			    		try {
			    			carResultTextArea.getDocument().remove(0, index+1);
			    		} catch (BadLocationException e) {
			    			LMSLog.exception(e);
			    		}
				    }
					    
            		carResultTextArea.append(str+"\n");              	     
				}
			}
		}
	}
}
