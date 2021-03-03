package NetworkCamera.SDK_Hikvision;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JWindow;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.examples.win32.W32API.HWND;

public class Realplay extends Panel
{
	private static HCNetSDK hCNetSDK = (HCNetSDK) Native.loadLibrary("HCNetSDK", HCNetSDK.class);
	long tempUserID;

	public void realplay(long userID)
	{
		tempUserID = userID;
		HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
		HWND hwnd = new HWND(Native.getComponentPointer(this));
		m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
		m_strClientInfo.lChannel = new NativeLong(1);
		m_strClientInfo.hPlayWnd = hwnd;
		NativeLong lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(new NativeLong(tempUserID), m_strClientInfo, null,
		        null, true);
		long previewHandle = lPreviewHandle.longValue();
		if (previewHandle < 0)
		{
			System.out.println("预览失败，代号：" + hCNetSDK.NET_DVR_GetLastError());
			return;
		}

		addMouseListener(new java.awt.event.MouseAdapter()
		{
			public void mousePressed(java.awt.event.MouseEvent evt)
			{
				panelRealplayMousePressed(evt);
			}
		});
	}

	private void panelRealplayMousePressed(java.awt.event.MouseEvent evt)
	{
		if (evt.getClickCount() == 2)
		{
			final JWindow window = new JWindow();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			window.setSize(screenSize);
			window.setVisible(true);
			HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
			HWND hwnd = new HWND(Native.getComponentPointer(window));
			m_strClientInfo.hPlayWnd = hwnd;
			m_strClientInfo.lChannel = new NativeLong(1);
			final NativeLong lRealHandle = hCNetSDK.NET_DVR_RealPlay_V30(new NativeLong(tempUserID), m_strClientInfo, null,
			        null, true);

			window.addMouseListener(new java.awt.event.MouseAdapter()
			{
				public void mousePressed(java.awt.event.MouseEvent evt)
				{
					if (evt.getClickCount() == 2)
					{
						hCNetSDK.NET_DVR_StopRealPlay(lRealHandle);
						window.dispose();
					}
				}
			});

		}
	}

}
