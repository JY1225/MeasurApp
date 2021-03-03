package AppFrame.logisticsMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSToken;
import layer.algorithmLayer.ThreeDPoint;

public class LogisticsMachineProtocolParse {
	private final static String DEBUG_TAG="LogisticsMachineProtocolParse";

	private Socket socket;  
	    
	private final int PORT = 2116; //
     
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");//设置日期格式

    //该线程所处理的Socket所对应的输入流   
    private BufferedReader bufferedReader;  
    private OutputStream outputStream;
	
   	public LMSToken tokenSocket = new LMSToken();
   	
	ArrayList<String> barCodeList_vms_520 = new ArrayList<String>(); 
	
	class VMS_530_PROTOCOL{
		int length,width,height,boxVolumn,realVolumn;
		String sBarCode;		
		long lTimeOfReceived;
	}
	Queue<VMS_530_PROTOCOL> vms_530_obj_queue = new ConcurrentLinkedQueue<VMS_530_PROTOCOL>(); 
	Queue<VMS_530_PROTOCOL> vms_530_obj_noRead_queue = new ConcurrentLinkedQueue<VMS_530_PROTOCOL>(); 
	Queue<VMS_530_PROTOCOL> vms_530_obj_noVolumn_queue = new ConcurrentLinkedQueue<VMS_530_PROTOCOL>(); 

	
   	String sBufferValid;
    public Runnable thread(final int sensorID)
    {
		return new Runnable(){
    		@Override
    		public void run() {                  
                LMSLog.d(DEBUG_TAG+sensorID,"socketRunnable thread run:"+Thread.currentThread().getId());

                //==============================================================
                try{
                	while(true)
	                {
	     		        if(connectSocket(sensorID))
	    		        {
	    	                LMSLog.d(DEBUG_TAG+sensorID,"socket connected");
	 
	    	    			char[] buffer = new char[1024];  ;
		            		
		    		        try {
		    		        	int count = 0;
		    		        	
		    		    		while ((count = bufferedReader.read(buffer))>0)  
								{  		 			
		    		    			String readString = new String(buffer,0,count);  

		    		        		LMSLog.d(DEBUG_TAG,"read:"+readString);
		    		        		
									if(buffer[0] == '\02')
									{
										sBufferValid = readString;  
									}
									else
									{
										sBufferValid += readString;    
									}
	
									if(buffer[count-1]!='\03')
									{
	//		    	        			LMSLog.w(DEBUG_TAG+sensorID,"sensorID="+sensorID+" not one frame");				    								    				
									}
									else 
									{
			    		        		protocolParse(sBufferValid);
									}
								}
							} 
		    		        catch (IOException e) 
		    		        {
					    		LMSLog.exception(sensorID,e);
							}
			    			
			                buffer = null;
	    		        }
	    		        else
	    		        {
	    		        	LMSLog.d(DEBUG_TAG+sensorID,"socket eeeee");
	    		        }
	                                    
	                	//=======================================================
	     				LMSConstValue.sendPortConnected(sensorID,false);
	    			} 
                } catch (Exception e) {
    	       		LMSLog.exceptionDialog(null, e);
    			}
       	    }    
		};
    }
    
	private boolean connectSocket(int sensorID){
		try {
            LMSLog.d(DEBUG_TAG+sensorID,"try connect socket IP="+LMSConstValue.SENSOR_IP[sensorID]);

            //设断连接尝试的超时时间，加快重连
            socket = new Socket();
            SocketAddress remoteAddr = new InetSocketAddress(LMSConstValue.SENSOR_IP[sensorID],PORT); 
            socket.connect(remoteAddr,1000);//       
			//socket[sensorID] = new Socket(LMSConstValue.LMS_IP[sensorID],LMS_PORT);     					
			socket.setReceiveBufferSize(1024);
			
			LMSLog.d(DEBUG_TAG+sensorID,"getReceiveBufferSize="+socket.getReceiveBufferSize());
			outputStream = socket.getOutputStream();  
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()),1024);

			LMSConstValue.sendPortConnected(sensorID,true);
			
			if(timer == null)
			{
				timer = new Timer();
				timerCheck();
			}
		} catch (ConnectException e) {
			//socket连接失败的地方，不用写那么多日志了，我们界面上有反映
			//LMSLog.exception(e);
			return false;
		} catch (SocketTimeoutException e) {
			//socket连接失败的地方，不用写那么多日志了，我们界面上有反映
			//LMSLog.exception(e);
			return false;
		} catch (UnknownHostException e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
	    		LMSLog.exception(sensorID,e1);
			}
			
			return false;
		}catch (IOException e) {
    		LMSLog.exception(sensorID,e);

			return false;
		}	

			return true;
	}

	public void disconnectSocket(int sensorID)
	{
		LMSLog.d(DEBUG_TAG+sensorID,"disconnectSocket");
		try {
        	if(socket != null && socket.isClosed() == false)
        	{
        		socket.shutdownInput();	//正常跳出read 阻塞
        		socket.shutdownOutput();	
        		socket.close();
        		
	        	if(outputStream != null)
	        		outputStream.close();
	        	if(bufferedReader != null)
	        		bufferedReader.close();
        	}
		} catch (IOException e) {
    		LMSLog.exception(sensorID,e);
		}					
	}
	
	public void broadcastParse(String eventType,HashMap eventExtra) 
	{
		int sensorID = 0;
		
		if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 

        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
        {	    	
			String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

        	if(nvram.equals(LMSConstValue.nvramSensorIP)) 
	        {	    			
    			disconnectSocket(sensorID);
    			
    			synchronized(tokenSocket)
    			{
    				tokenSocket.notify();
    			}
	        }
        }        
	}
	
	void protocolParse(String str)
	{
		int startIndex,endIndex,strlen;
		String result;

		strlen = str.length();
		while((endIndex = str.indexOf('\3',0))>0)
		{
			startIndex = str.indexOf('\2',0);

			String packet = str.substring(startIndex+1,endIndex);
    		LMSLog.d(DEBUG_TAG,"one frame:"+packet);
 //   		packetParse_vms_520(packet);
    		packetParse_vms_530(packet);
    		
			result = str.substring(endIndex+1);
			if(endIndex != strlen-1)
			{
				str = result;
				strlen -= endIndex;
			}
			else
			{
				break;
			}
		}
	}
	
	void packetParse_vms_520(String str)
	{
		int startIndex,endIndex,strlen;
		String result;

		strlen = str.length();
		
		barCodeList_vms_520.clear();
		while((endIndex = str.indexOf(';',0))>0)
		{
			startIndex = 0;

			String object = str.substring(startIndex,endIndex);
    		LMSLog.d(DEBUG_TAG,"one object:"+object);
    		objectParse_vms_520(object);
    		
			result = str.substring(endIndex+1);
			if(endIndex != strlen-1)
			{
				str = result;
				strlen -= endIndex;
			}
			else
			{
				break;
			}
		}
	}

	void packetParse_vms_530(String str)
	{
		try {
			objectParse_vms_530(str);
		} catch (java.lang.ArithmeticException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(-1,e);
		}

		
	}	
	
	void objectParse_vms_520(String str)
	{
		int startIndex,endIndex,strlen;
		String result;
		int paramIndex = 0;
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		
		boolean bAdded = true;

		strlen = str.length();
		while((endIndex = str.indexOf(',',0))>0)
		{
			startIndex = 0;

			String param = str.substring(startIndex,endIndex);
//    		LMSLog.d(DEBUG_TAG,"param:"+param);
    		
			result = str.substring(endIndex+1);

    		if(paramIndex == 0)
			{
    			for(int i=0;i<barCodeList_vms_520.size();i++)
    			{
    				if(barCodeList_vms_520.get(i).contains(param)||param.contains(barCodeList_vms_520.get(i)))
    				{
    					bAdded = false;
    					break;
    				}
    			}
    			
    			if(bAdded == true)
    			{
    				barCodeList_vms_520.add(param);
    			}
    			else
    			{
    				break;
    			}
			}
			
			//=====================================================
    		if(paramIndex == 0)
    		{
				int iValue = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH,iValue);
    		}
    		else if(paramIndex == 1)
    		{
				int iValue = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH,iValue);
    		}
    		else if(paramIndex == 2)
    		{
				int iValue = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT,iValue);
    		}
    		else if(paramIndex == 3)
    		{
				int iValue = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN,iValue);
    		}
    		else if(paramIndex == 4)
    		{
				int iValue = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN,iValue);
    		}
    		else if(paramIndex == 6)
    		{
				eventExtra.put(LMSConstValue.INTENT_EXTRA_BAR_CODE,param);
    		}
    		/*
    		else if(paramIndex == 6)
    		{    		
				//最后
    			float fValue = Float.valueOf(param)/10;
				eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_ANGLE,fValue);
    		}
    		*/
    		
    		paramIndex++;
    		
			if(endIndex != strlen-1)
			{
				str = result;
				strlen -= endIndex;
			}
			else
			{

				break;
			}
		}
		
		if(bAdded == true)
		{
			eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.CAR_RESULT.ordinal());
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_STATE_CHANGE_INTENT,eventExtra);
		}
	}
	
	int getCharCount(String str,char chr)
	{
		int startIndex,endIndex,strlen = 0;
		String result;
		int count = 0;
		
		while((endIndex = str.indexOf(chr,0))>0)
		{
			count++;
			
			result = str.substring(endIndex+1);
			
			if(endIndex != strlen-1)
			{
				str = result;
				strlen -= endIndex;
			}
			else
			{
				break;
			}
		}
		
		return count;
	}
	
	int VMS_530_BARCODE_START_INDEX = 6;
	void objectParse_vms_530(String str)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.SDF);  
		String time = df.format(new Date());
		long lTimeOfReceived = 0;
		try {
			Date d = sdf.parse(time);
			lTimeOfReceived = d.getTime();	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(-1,e);
		}

		//=====================================================
		int startIndex,endIndex,strlen;
		String result;
		int paramIndex = 0;
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		
		strlen = str.length();
		
		int iBarCodeNum = getCharCount(str, ',')-VMS_530_BARCODE_START_INDEX;
//		LMSLog.d(DEBUG_TAG,"iBarCodeNum:"+iBarCodeNum);
		
		int length = 0,width = 0,height = 0;
		while((endIndex = str.indexOf(',',0))>0)
		{
			startIndex = 0;

			String param = str.substring(startIndex,endIndex);
//    		LMSLog.d(DEBUG_TAG,"param:"+param);
    		
			result = str.substring(endIndex+1);
			
			//=====================================================
    		if(paramIndex == 0)
    		{
    			length = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH,length/iBarCodeNum);
    		}
    		else if(paramIndex == 1)
    		{
    			width = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH,width);
    		}
    		else if(paramIndex == 2)
    		{
    			height = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT,height);
    		}
    		else if(paramIndex == 3)
    		{
				int iValue = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN,iValue/iBarCodeNum);
    		}
    		else if(paramIndex == 4)
    		{
				int iValue = Integer.valueOf(param);
				eventExtra.put(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN,iValue/iBarCodeNum);
    		}
    		else if(paramIndex >= VMS_530_BARCODE_START_INDEX)
    		{
				eventExtra.put(LMSConstValue.INTENT_EXTRA_BAR_CODE,param);

				//====================================================================
				VMS_530_PROTOCOL vms_530_PROTOCOL = new VMS_530_PROTOCOL();
				vms_530_PROTOCOL.sBarCode = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_BAR_CODE);
				vms_530_PROTOCOL.length = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH);
				vms_530_PROTOCOL.width = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH);
				vms_530_PROTOCOL.height = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT);
				vms_530_PROTOCOL.boxVolumn = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN);
				vms_530_PROTOCOL.realVolumn = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN);
				vms_530_PROTOCOL.lTimeOfReceived = lTimeOfReceived;

				//====================================================================
				if(param.equals("NoRead")&&(width==0)&&(height==0)&&(length==0))
				{
    				LMSLog.d(DEBUG_TAG,"NoRead + NoVolumn:"+vms_530_PROTOCOL.sBarCode
    					+" length="+vms_530_PROTOCOL.length
    					+" width="+vms_530_PROTOCOL.width
    					+" height="+vms_530_PROTOCOL.height
   					);		

					//NoRead + 无体积,不输出
				}
				else if(((!param.equals("NoRead"))&&(width>0)))
				{					
					//有条码，有体积正常输出
    				LMSLog.d(DEBUG_TAG,"normal queue:"+vms_530_PROTOCOL.sBarCode
    					+" length="+vms_530_PROTOCOL.length
    					+" width="+vms_530_PROTOCOL.width
    					+" height="+vms_530_PROTOCOL.height
   					);		

					eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.CAR_RESULT.ordinal());
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_STATE_CHANGE_INTENT,eventExtra);
				}
				else 
				{
					//=================================================================
					if(param.equals("NoRead")&&(width>=LMSConstValue.iMinPacketWidth))
					{						
	    				LMSLog.d(DEBUG_TAG,"NoRead queue:"+vms_530_PROTOCOL.sBarCode
        					+" length="+vms_530_PROTOCOL.length
        					+" width="+vms_530_PROTOCOL.width
        					+" height="+vms_530_PROTOCOL.height
       					);		

						vms_530_obj_noRead_queue.offer(vms_530_PROTOCOL);
					}
					else if(!param.equals("NoRead")&&(width==0)&&(height==0)&&(length==0))
					{
						if(!bBufferHasBarcode(param,vms_530_obj_queue))
						{
		    				LMSLog.d(DEBUG_TAG,"NoVolumn queue:"+vms_530_PROTOCOL.sBarCode
	        					+" length="+vms_530_PROTOCOL.length
	        					+" width="+vms_530_PROTOCOL.width
	        					+" height="+vms_530_PROTOCOL.height
	       					);		

							vms_530_obj_noVolumn_queue.offer(vms_530_PROTOCOL);						
						}
						else
						{
		    				LMSLog.d(DEBUG_TAG,"NoVolumn but has buffer:"+vms_530_PROTOCOL.sBarCode
	        					+" length="+vms_530_PROTOCOL.length
	        					+" width="+vms_530_PROTOCOL.width
	        					+" height="+vms_530_PROTOCOL.height
	       					);		
						}
					}
					
					noRead_noVolumn_maching();
				}
				
				//先进行缓冲匹配，最后才更新缓冲
				vms_530_obj_queue.offer(vms_530_PROTOCOL);
    		}
    		/*
    		else if(paramIndex == 6)
    		{    		
				//最后
    			float fValue = Float.valueOf(param)/10;
				eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_ANGLE,fValue);
    		}
    		*/
    		
    		paramIndex++;
    		
			if(endIndex != strlen-1)
			{
				str = result;
				strlen -= endIndex;
			}
			else
			{
				break;
			}
		}		
	}
	
	void queueTimeReset(boolean bDiscard,Queue<VMS_530_PROTOCOL> queue,int timeInteval)
	{
		//时间判断
		SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.SDF);  
		String time = df.format(new Date());
		long lTimeOfReceived = 0;
		try {
			Date d = sdf.parse(time);
			lTimeOfReceived = d.getTime();	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(-1,e);
		}
				
		for (Iterator it = queue.iterator(); it.hasNext();) 
        { 
        	VMS_530_PROTOCOL vms_530_PROTOCOL = (VMS_530_PROTOCOL) it.next();
        	
     		if(lTimeOfReceived - vms_530_PROTOCOL.lTimeOfReceived > timeInteval)
			{     			
    			if(!bDiscard)
    			{
    				String queueName = null;
    				if(queue == vms_530_obj_queue)
    					queueName = "vms_530_obj_queue";
    				else if(queue == vms_530_obj_noRead_queue)
    					queueName = "vms_530_obj_noRead_queue";
    				else if(queue == vms_530_obj_noVolumn_queue)
    					queueName = "vms_530_obj_noVolumn_queue";
    				
    				LMSLog.d(DEBUG_TAG,"queue:"+queueName+" too long time pop:"+vms_530_PROTOCOL.sBarCode
    					+" length="+vms_530_PROTOCOL.length
    					+" width="+vms_530_PROTOCOL.width
    					+" height="+vms_530_PROTOCOL.height
    					);		

	    			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
	
	    			eventExtra.put(LMSConstValue.INTENT_EXTRA_BAR_CODE,vms_530_PROTOCOL.sBarCode);
	    			eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH,vms_530_PROTOCOL.length);
	    			eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH,vms_530_PROTOCOL.width);
	    			eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT,vms_530_PROTOCOL.height);
	    			eventExtra.put(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN,vms_530_PROTOCOL.boxVolumn);
	    			eventExtra.put(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN,vms_530_PROTOCOL.realVolumn);
	
	    			eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.CAR_RESULT.ordinal());
	    			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_STATE_CHANGE_INTENT,eventExtra);
    			}
    			else
    			{
    				String queueName = null;
    				if(queue == vms_530_obj_queue)
    					queueName = "vms_530_obj_queue";
    				else if(queue == vms_530_obj_noRead_queue)
    					queueName = "vms_530_obj_noRead_queue";
    				else if(queue == vms_530_obj_noVolumn_queue)
    					queueName = "vms_530_obj_noVolumn_queue";
    				
    				LMSLog.d(DEBUG_TAG,"queue:"+queueName+" too long time remove:"+vms_530_PROTOCOL.sBarCode
    					+" length="+vms_530_PROTOCOL.length
    					+" width="+vms_530_PROTOCOL.width
    					+" height="+vms_530_PROTOCOL.height
    					);		
    			}
    			
    			//===========================================================
     			it.remove();
			}
    	} 
	}
	
	boolean bBufferHasBarcode(String sBarcode,Queue<VMS_530_PROTOCOL> queue)
	{		
        for (Iterator it = queue.iterator(); it.hasNext();) 
        { 
        	VMS_530_PROTOCOL vms_530_PROTOCOL = (VMS_530_PROTOCOL) it.next();
        	
     		if(sBarcode.equals(vms_530_PROTOCOL.sBarCode))
			{
     			return true;
			}
    	} 
        
        return false;
	}
	
	void noRead_noVolumn_maching()
	{
		if(vms_530_obj_noVolumn_queue.size()>0&&vms_530_obj_noRead_queue.size()>0)
		{
			VMS_530_PROTOCOL noRead = vms_530_obj_noRead_queue.poll();
			VMS_530_PROTOCOL noVolumn = vms_530_obj_noVolumn_queue.poll();
			
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();

			eventExtra.put(LMSConstValue.INTENT_EXTRA_BAR_CODE,noVolumn.sBarCode);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH,noRead.length);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH,noRead.width);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT,noRead.height);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN,noRead.boxVolumn);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN,noRead.realVolumn);

			eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.CAR_RESULT.ordinal());
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_STATE_CHANGE_INTENT,eventExtra);
		}
	}
	
    private Timer timer;
    private TimerTask timerTask;
	class TimeOutTask extends TimerTask{         
        // TimerTask 是个抽象类,实现的是Runable类   
        @Override  
        public void run() {
        	try {
	        	queueTimeReset(false,vms_530_obj_noRead_queue,LMSConstValue.iNoReadNoVolumnMachingTimeInteval);
	        	queueTimeReset(true,vms_530_obj_noVolumn_queue,LMSConstValue.iNoReadNoVolumnMachingTimeInteval);
	        	queueTimeReset(true,vms_530_obj_queue,LMSConstValue.iNoReadNoVolumnMachingTimeInteval+15000);
        	} catch (Exception e) {
	       		LMSLog.exceptionDialog(null, e);
			}
        }
    };
    
	void timerCheck()
    {
    	if(timer != null)
    	{	
	    	if(timerTask != null)
	    	{	
		    	timerTask.cancel();
	    	}
	    	timerTask = new TimeOutTask();
            timer.schedule(timerTask,1000,1000);   	    	
    	}
    }
}
