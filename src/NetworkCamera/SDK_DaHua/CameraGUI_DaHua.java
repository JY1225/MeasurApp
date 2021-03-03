package NetworkCamera.SDK_DaHua;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.W32API.HWND;

class CameraGUI
{
	static NetSDKLib NetSdk = NetSDKLib.COMMON_INSTANCE;
	static NetSDKLib ConfigSdk = NetSDKLib.CONFIG_INSTANCE;

	private SDKEnvironment sdkEnv;

	// 登录参数	
	private String m_strIp = "192.168.1.108";
	private Integer m_nPort = new Integer("37777");
	private String m_strUser = "admin";
	private String m_strPassword = "admin";
	// 设备信息
	private NetSDKLib.NET_DEVICEINFO m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO();
	private NativeLong m_hLoginHandle = new NativeLong(0); // 登录句柄
	private NativeLong m_hPlayHandle = new NativeLong(0); // 预览句柄
	private HWND m_hwnd; // 播放窗口句柄

	public CameraGUI()
	{
		sdkEnv = new SDKEnvironment();
		sdkEnv.init();

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainFrame = new JFrame("大华 网络摄像机");
				mainFrame.setSize(897, 721);
				mainFrame.setLayout(new BorderLayout());
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				mainFrame.setVisible(true);

				loginJPanel = new LoginPanel(); // 登录面板 
				realPlayPanel = new RealPlayPanel(); // 实时监视

				mainFrame.add(loginJPanel, BorderLayout.NORTH);
				mainFrame.add(realPlayPanel, BorderLayout.CENTER);

				WindowAdapter closeWindowAdapter = new WindowAdapter()
		        {
			        public void windowClosing(WindowEvent e)
			        {
				        System.out.println("Window Closing");
				        LoginOutButtonPerformed(null); // 登出
				        sdkEnv.cleanup();
				        mainFrame.dispose();
			        }
		        };
				mainFrame.addWindowListener(closeWindowAdapter);
			}
		});
	}

	/**
	 * NetSDK 库初始化
	 */
	private class SDKEnvironment
	{

		private boolean bInit = false;
		private boolean bLogopen = false;

		private DisConnect disConnect = new DisConnect(); // 设备断线通知回调
		private HaveReConnect haveReConnect = new HaveReConnect(); // 网络连接恢复

		// 初始化
		public boolean init()
		{

			// SDK 库初始化, 并设置断线回调
			bInit = NetSdk.CLIENT_Init(disConnect, new NativeLong(0));
			if (!bInit)
			{
				System.err.println("Initialize SDK failed");
				return false;
			}

			// 打开日志，可选
			NetSDKLib.LOG_SET_PRINT_INFO setLog = new NetSDKLib.LOG_SET_PRINT_INFO();

			File path = new File(".");
			String logPath = path.getAbsoluteFile().getParent() + "\\sdk_log\\ITSEventMsg_" + System.currentTimeMillis() + ".log";

			setLog.bSetFilePath = 1;
			System.arraycopy(logPath.getBytes(), 0, setLog.szLogFilePath, 0, logPath.getBytes().length);

			setLog.bSetPrintStrategy = 1;
			setLog.nPrintStrategy = 0;
			bLogopen = NetSdk.CLIENT_LogOpen(setLog);
			if (!bLogopen)
			{
				System.err.println("Failed to open NetSDK log !!!");
			}

			// 获取版本, 可选操作
			System.out.printf("NetSDK Version [%d]\n", NetSdk.CLIENT_GetSDKVersion());

			// 设置断线重连回调接口，设置过断线重连成功回调函数后，当设备出现断线情况，SDK内部会自动进行重连操作
			// 此操作为可选操作，但建议用户进行设置
			NetSdk.CLIENT_SetAutoReconnect(haveReConnect, new NativeLong(0));

			// 设置登录超时时间和尝试次数 , 此操作为可选操作	   
			int waitTime = 5000; // 登录请求响应超时时间设置为 5s
			int tryTimes = 3; // 登录时尝试建立链接3次
			NetSdk.CLIENT_SetConnectTime(waitTime, tryTimes);

			// 设置更多网络参数，NET_PARAM的nWaittime，nConnectTryNum成员与CLIENT_SetConnectTime 
			// 接口设置的登录设备超时时间和尝试次数意义相同
			// 此操作为可选操作
			NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
			netParam.nConnectTime = 10000; // 登录时尝试建立链接的超时时间
			NetSdk.CLIENT_SetNetworkParam(netParam);

			return true;
		}

		// 清除环境
		public void cleanup()
		{
			if (bLogopen)
			{
				NetSdk.CLIENT_LogClose();
			}

			if (bInit)
			{
				NetSdk.CLIENT_Cleanup();
			}
		}

		// 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
		public class DisConnect implements NetSDKLib.fDisConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("Device[%s] Port[%d] Disconnect!\n", pchDVRIP, nDVRPort);
			}
		}

		// 网络连接恢复，设备重连成功回调
		// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
		public class HaveReConnect implements NetSDKLib.fHaveReConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
			}
		}
	}

	/**
	 * 登录按钮
	 */
	private void LoginButtonPerformed(ActionEvent e)
	{
		m_strIp = ipTextArea.getText();
		m_nPort = Integer.parseInt(portTextArea.getText());
		m_strUser = nameTextArea.getText();
		m_strPassword = new String(passwordTextArea.getPassword());

		System.out.println("设备地址：" + m_strIp + "\n端口号：" + m_nPort + "\n用户名：" + m_strUser + "\n密码：" + m_strPassword);

		// 登录设备
		int nError[] =
		{ 0 };
		m_hLoginHandle = NetSdk.CLIENT_LoginEx(m_strIp, (short) m_nPort.intValue(), m_strUser, m_strPassword, 0, null, m_stDeviceInfo, nError);
		if (m_hLoginHandle.longValue() == 0)
		{
			System.err.printf("Login Device[%s] Port[%d]Failed. Last Error[%x]\n", m_strIp, m_nPort, NetSdk.CLIENT_GetLastError());
			JOptionPane.showMessageDialog(mainFrame, "登录失败");
		}
		else
		{
			System.out.println("Login Success [ " + m_strIp + " ]");
			JOptionPane.showMessageDialog(mainFrame, "登录成功");
			logoutBtn.setEnabled(true);
			loginBtn.setEnabled(false);

		}
	}

	/**
	 * 登出按钮
	 */
	private void LoginOutButtonPerformed(ActionEvent e)
	{
		if (m_hLoginHandle.longValue() != 0)
		{
			System.out.println("LogOut Button Action");

			// 确保关闭监视
			stopRealPlay();

			if (NetSdk.CLIENT_Logout(m_hLoginHandle))
			{
				System.out.println("Logout Success [ " + m_strIp + " ]");
				m_hLoginHandle.setValue(0);
				logoutBtn.setEnabled(false);
				loginBtn.setEnabled(true);
			}
		}
	}

	/**
	 * 开始实时监视按钮事件
	 */
	private void startRealPlay()
	{
		if (m_hLoginHandle.longValue() == 0)
		{
			System.err.println("Please login first");
			JOptionPane.showMessageDialog(mainFrame, "请先登录");
			return;
		}

		int channel = 0; // 预览通道号
		int playType = NetSDKLib.NET_RealPlayType.NET_RType_Realplay; // 实时预览

		m_hwnd = new HWND(Native.getComponentPointer(realplayWindow));
		m_hPlayHandle = NetSdk.CLIENT_RealPlayEx(m_hLoginHandle, channel, m_hwnd, playType);
		if (m_hPlayHandle.longValue() == 0)
		{
			int error = NetSdk.CLIENT_GetLastError();
			JOptionPane.showMessageDialog(mainFrame, "开始实时监视失败，错误码：" + String.format("[0x%x]", error));
		}
		else
		{
			startPlayButton.setEnabled(false);
			stopPlayButton.setEnabled(true);
		}
	}

	/**
	 * 结束实时预览按钮事件
	 */
	private void stopRealPlay()
	{
		if (m_hPlayHandle.longValue() == 0)
		{
			System.out.println("Make sure the realplay Handle is valid");
			return;
		}

		if (NetSdk.CLIENT_StopRealPlayEx(m_hPlayHandle))
		{
			System.out.println("Success to stop realplay");
			startPlayButton.setEnabled(true);
			stopPlayButton.setEnabled(false);
			m_hPlayHandle.setValue(0);
			realplayWindow.repaint();
		}
	}

	/**
	 * 手动抓图按钮事件
	 */
	private void snapPicPeformed()
	{
		if (m_hLoginHandle.longValue() == 0)
		{
			System.err.println("Plese Login First");
			JOptionPane.showMessageDialog(mainFrame, "请先登录");
			return;
		}

		//Ben
		SnapRev OnSnapRevMessage = new SnapRev();
		NetSdk.CLIENT_SetSnapRevCallBack(OnSnapRevMessage, m_hLoginHandle);

		NetSDKLib.SNAP_PARAMS stParam = new NetSDKLib.SNAP_PARAMS();
		boolean bSnap = NetSdk.CLIENT_SnapPictureEx(m_hLoginHandle, stParam, stParam.Reserved);

		if (bSnap)
		{
			JOptionPane.showMessageDialog(mainFrame, "抓图成功");
		}

	}

	public class SnapRev implements NetSDKLib.fSnapRev
	{
		public void invoke(NativeLong lLoginID, Pointer pBuf, int RevLen, int EncodeType, NativeLong CmdSerial, NativeLong dwUser)
		{
			String m_imagePath = "./image/";
			String bigPicture = m_imagePath + "test" + ".jpg";
			NetSDKTools.savePicture(pBuf, RevLen, bigPicture);
		}
	}

	/**
	 * 设置边框
	 */
	private void setBorderEx(JComponent object, String title, int width)
	{
		Border innerBorder = BorderFactory.createTitledBorder(title);
		Border outerBorder = BorderFactory.createEmptyBorder(width, width, width, width);
		object.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
	}

	/**
	 * 登录面板
	 */
	private class LoginPanel extends JPanel
	{
		public LoginPanel()
		{
			loginBtn = new JButton("登入");
			logoutBtn = new JButton("登出");
			nameLabel = new JLabel("用户名");
			passwordLabel = new JLabel("密码");
			nameTextArea = new JTextField("admin", 8);
			passwordTextArea = new JPasswordField("admin", 8);
			ipLabel = new JLabel("设备地址");
			portLabel = new JLabel("端口号");
			ipTextArea = new JTextField("192.168.1.108", 15);
			portTextArea = new JTextField("37777", 6);

			setLayout(new FlowLayout());
			setBorderEx(this, "登录", 2);

			add(ipLabel);
			add(ipTextArea);
			add(portLabel);
			add(portTextArea);
			add(nameLabel);
			add(nameTextArea);
			add(passwordLabel);
			add(passwordTextArea);
			add(loginBtn);
			add(logoutBtn);

			logoutBtn.setEnabled(false);

			// 登录按钮. 监听事件
			loginBtn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					LoginButtonPerformed(e);
				}
			});

			// 登出按钮. 监听事件
			logoutBtn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					LoginOutButtonPerformed(e);
				}
			});

		}
	}

	/**
	 * 预览面板
	 */
	private class RealPlayPanel extends JPanel
	{
		public RealPlayPanel()
		{
			setBorderEx(this, "实时预览", 2);
			setLayout(new BorderLayout());
			Dimension dim = getPreferredSize();
			dim.height = 367;
			dim.width = 374;
			setPreferredSize(dim);

			realplayWindow = new Panel();
			startPlayButton = new JButton("监视");
			stopPlayButton = new JButton("停止监视");
			snapButton = new JButton("手动抓图");

			realplayWindow.setBackground(new java.awt.Color(153, 240, 255));
			realplayWindow.setForeground(new java.awt.Color(0, 0, 0));
			realplayWindow.setBounds(5, 5, 350, 290);
			realplayWindow.setSize(358, 294);

			JPanel btnPanel = new JPanel();
			btnPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			btnPanel.setLayout(new GridLayout(0, 3, 5, 5));
			btnPanel.add(startPlayButton);
			btnPanel.add(stopPlayButton);
			btnPanel.add(snapButton);

			add(realplayWindow, BorderLayout.CENTER);
			add(btnPanel, BorderLayout.SOUTH);

			//抓图按钮动作监听
			snapButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					snapPicPeformed();
				}
			});
			// 监视按钮动作监听
			startPlayButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					startRealPlay();
				}
			});

			// 停止监视按钮监听
			stopPlayButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					stopRealPlay();
				}
			});
		}
	}

	/**
	 * 主界面组件
	 */
	private JFrame mainFrame;

	/**
	 * 登录条组件
	 */
	private JButton loginBtn;
	private JButton logoutBtn;
	private JLabel nameLabel;
	private JLabel passwordLabel;
	private JTextField nameTextArea;
	private JPasswordField passwordTextArea;
	private JLabel ipLabel;
	private JLabel portLabel;
	private JTextField ipTextArea;
	private JTextField portTextArea;
	private LoginPanel loginJPanel;

	/**
	 * 实时预览组件
	 */
	private RealPlayPanel realPlayPanel;
	private java.awt.Panel realplayWindow;
	private JButton startPlayButton;
	private JButton stopPlayButton;
	private JButton snapButton;
}

public class CameraGUI_DaHua
{
	public static void main(String[] args)
	{
		CameraGUI cameraGUI = new CameraGUI();
	}
}
