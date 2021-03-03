package Ben;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import SensorBase.LMSLog;

public class SocketServer extends Thread
{
	private final static String DEBUG_TAG = "SocketServer";
	private PrintWriter printWriter;
	private int port;
	private ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(100);
	private Socket socket;
	private ServerSocket serverSocket;

	public SocketServer(int port)
	{
		this.port = port;
	}

	public void add(String message)
	{
		messageQueue.add(message);
	}

	private void listen()
	{
		try
		{
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void connect()
	{
		try
		{
			socket = serverSocket.accept();
			//serverSocket.close();
			printWriter = new PrintWriter(socket.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void sent(String meassage)
	{
		printWriter.println(meassage);
		printWriter.flush();
	}

	public void run()
	{
		listen();
		connect();
		while (true)
		{
			try
			{
				String message = messageQueue.take();
				if (!isConnected())
				{
					connect();
				}
				sent(message);
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected()
	{
		try
		{
			socket.sendUrgentData(0xFF);
			return true;
		}
		catch (Exception e)
		{
			LMSLog.d(DEBUG_TAG,"¿Í»§socket¶Ï¿ª");
			return false;
		}
	}
}
