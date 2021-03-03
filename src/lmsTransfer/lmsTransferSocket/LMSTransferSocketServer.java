package lmsTransfer.lmsTransferSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsTransfer.LMSTransferRunnable;

public abstract class LMSTransferSocketServer extends LMSTransferRunnable{
	final static String DEBUG_TAG="LMSTransferSocketServer";

	public static Socket socket = null;
	private int port;
	private String ip;
	protected String readString;

	public ServerSocket server = null;
	BufferedReader bufferedReader = null;
	ArrayList<Socket> socketList = new ArrayList<Socket>();  
	ArrayList<OutputStream> outputStreamList = new ArrayList<OutputStream>();  

	public abstract void clientConnected();
	public abstract void serverSocketSetTimeOut();
	public abstract void PHYSIC_RECEIVE(String str);
	public abstract boolean BUFFER_READ(BufferedReader bufferedReader);
	
	public LMSTransferSocketServer(String ip,int port)
	{
		this.port = port;
		this.ip = ip;
	}
	
	public void runnabbleRun() throws IOException{
		//ѭ������server
		while(server == null)
		{	        	
			try {
				LMSLog.d(DEBUG_TAG,"server="+ip+" port="+port);
				
				server = new ServerSocket(port, 0, InetAddress.getByName(ip));
				
				LMSLog.d(DEBUG_TAG,"socket server:"+server);

			} catch (UnknownHostException e) {
	    		LMSLog.exception(e);
			} catch (BindException e) {
				LMSLog.exceptionDialog("������쳣:�˿�"+port+"��ʧ��,�����Ƿ���˶������",e);
				
		        System.exit(0);   
			} 
			
			synchronized (this) {  
        		try {
					this.wait(1*1000);
				} catch (InterruptedException e) {
		    		LMSLog.exception(e);
				}
        	}
		}

		//server�����ɹ���ѭ������client������
		while(server != null)
		{
	        try {
				socket = server.accept();
				serverSocketSetTimeOut();
				
				new ServerThread(socket).start();
				LMSLog.d(DEBUG_TAG,"client connect port:"+port);
			} catch (IOException e) {
	    		LMSLog.exception(e);
			}
		}
		
		LMSLog.d(DEBUG_TAG,"socketRunnable thread end:"+Thread.currentThread().getId());

	}

	public class ServerThread extends Thread { 
	    Socket sock; 
	    OutputStream outputStream;  

	    public ServerThread(Socket s) 
	    { 
	        sock =s ; 
	    } 
	    
	    public void run() 
	    { 
	        try{ 
				bufferedReader = new BufferedReader(new InputStreamReader(sock.getInputStream(),"GBK"),200*1024);
				outputStream = sock.getOutputStream();  
				
				socketList.add(sock);
		        outputStreamList.add(outputStream);

	    		clientConnected();

	    		if(bufferedReader!=null)
	    		{	
		    		while (BUFFER_READ(bufferedReader))
		    		{  	
//		    			if(port == LMSConstValue.LMS_AP_USER_PORT)
//		    				LMSLog.d(DEBUG_TAG,"PHYSIC_RECEIVE="+readString);
	
		    			PHYSIC_RECEIVE(readString);
					}
	    		}
	        } 
	        catch(IOException e) 
	        { 
	    		LMSLog.exception(e);
	        } 
	        finally
	        {
	            clientDisconnect();
	        }
	    } 
	    
		public synchronized void clientDisconnect()
		{
			LMSLog.d(DEBUG_TAG,"clientDisconnect");

			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.STATE_SOCKET_CLIENT_DISCONNECT_INTENT);    		

			try {
	        	if(sock != null)
	        	{         		
	        		socketList.remove(sock);
	        		outputStreamList.remove(outputStream);
	        		
	        		if(!sock.isOutputShutdown())
	        			sock.shutdownOutput();	
	        		if(!sock.isInputShutdown())
	        			sock.shutdownInput();	

		        	if(outputStream != null)
		        	{
		        		outputStream.close();
		        		outputStream = null;
		        	}
		        	if(bufferedReader != null)
		        	{
		        		bufferedReader.close();
		        		bufferedReader = null;
		        	}

		        	sock.close();
		        	sock = null;
	        	}
			} catch (IOException e){
	    		LMSLog.exception(e);
			} catch(Exception e){
	    		LMSLog.exception(e);
			}
		}
	}
	
	public void socketSend(String str) {
		// TODO Auto-generated method stub
		for(int i=0;i<socketList.size();i++)
		{
			try {
				if(socketList.get(i) != null && outputStreamList.get(i) != null)
				{
//					LMSLog.d(DEBUG_TAG,"socketSend "+i+":"+str);
					OutputStream outputStream = outputStreamList.get(i);
					if(outputStream != null)
					{
						outputStream.write((str).getBytes("utf-8"));
					}
				}
			} catch (IOException e) {
	    		LMSLog.exception(e);
			}	

		}
	}
	
	public void socketSendRaw(byte[] str) {
		// TODO Auto-generated method stub
		for(int i=0;i<socketList.size();i++)
		{
			try {
				if(socketList.get(i) != null && outputStreamList.get(i) != null)
				{
					LMSLog.d(DEBUG_TAG,"socketSendRaw i="+i);
					
					outputStreamList.get(i).write(str);
				}
			} catch (IOException e) {
	    		LMSLog.exception(e);
			}	

		}
	}
}
