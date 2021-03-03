package Ben;

import java.net.*;

import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Date;

public class SocketClient
{
	private Object[] dataReceived;
	private BufferedReader bufferedReader;
	private Socket socket;
	private final String SDF = "yyyy-MM-dd HH:mm:ss,SSS";
	private SimpleDateFormat sdf = new SimpleDateFormat(SDF);

	public void connect(String IP, int port)
	{
		int connectTimes = 0;
		while (connectTimes < 10)
		{
			try
			{
				connectTimes++;
				Thread.sleep(5000);
				socket = new Socket(IP, port);
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println(sdf.format(new Date()) + " IP: " + IP + " 端口: " + port + " 已连接");
				return;
			}
			catch (Exception e)
			{
				System.out.println("IP: " + IP + " 端口: " + port + " 请求连接。。。");
				System.out.println(e.toString());
			}
		}
		System.out.println("IP: " + IP + " 端口: " + port + " 连接失败，请检查网络连接，并重新打开软件。");
	}

	public Object[] ReadLine()
	{
		try
		{
			dataReceived = new Object[2];
			dataReceived[1] = bufferedReader.readLine();
			dataReceived[0] = System.currentTimeMillis();
		}
		catch (Exception e)
		{
			System.out.println("Error: Socket lost.");
			System.out.println(e.toString());
			System.exit(1);
		}
		return dataReceived;
	}

	public Object[] Read()
	{
		char[] buffer = new char[1024];
		try
		{
			bufferedReader.read(buffer);
			dataReceived[1] = String.valueOf(buffer);
			dataReceived[0] = System.currentTimeMillis();
		}
		catch (Exception e)
		{
			System.out.println("Error: Socket lost.");
			System.out.println(e.toString());
			System.exit(1);
		}
		return dataReceived;
	}

}