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

	// ��¼����	
	private String m_strIp = "192.168.1.108";
	private Integer m_nPort = new Integer("37777");
	private String m_strUser = "admin";
	private String m_strPassword = "admin";
	// �豸��Ϣ
	private NetSDKLib.NET_DEVICEINFO m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO();
	private NativeLong m_hLoginHandle = new NativeLong(0); // ��¼���
	private NativeLong m_hPlayHandle = new NativeLong(0); // Ԥ�����
	private HWND m_hwnd; // ���Ŵ��ھ��

	public CameraGUI()
	{
		sdkEnv = new SDKEnvironment();
		sdkEnv.init();

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainFrame = new JFrame("�� ���������");
				mainFrame.setSize(897, 721);
				mainFrame.setLayout(new BorderLayout());
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				mainFrame.setVisible(true);

				loginJPanel = new LoginPanel(); // ��¼��� 
				realPlayPanel = new RealPlayPanel(); // ʵʱ����

				mainFrame.add(loginJPanel, BorderLayout.NORTH);
				mainFrame.add(realPlayPanel, BorderLayout.CENTER);

				WindowAdapter closeWindowAdapter = new WindowAdapter()
		        {
			        public void windowClosing(WindowEvent e)
			        {
				        System.out.println("Window Closing");
				        LoginOutButtonPerformed(null); // �ǳ�
				        sdkEnv.cleanup();
				        mainFrame.dispose();
			        }
		        };
				mainFrame.addWindowListener(closeWindowAdapter);
			}
		});
	}

	/**
	 * NetSDK ���ʼ��
	 */
	private class SDKEnvironment
	{

		private boolean bInit = false;
		private boolean bLogopen = false;

		private DisConnect disConnect = new DisConnect(); // �豸����֪ͨ�ص�
		private HaveReConnect haveReConnect = new HaveReConnect(); // �������ӻָ�

		// ��ʼ��
		public boolean init()
		{

			// SDK ���ʼ��, �����ö��߻ص�
			bInit = NetSdk.CLIENT_Init(disConnect, new NativeLong(0));
			if (!bInit)
			{
				System.err.println("Initialize SDK failed");
				return false;
			}

			// ����־����ѡ
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

			// ��ȡ�汾, ��ѡ����
			System.out.printf("NetSDK Version [%d]\n", NetSdk.CLIENT_GetSDKVersion());

			// ���ö��������ص��ӿڣ����ù����������ɹ��ص������󣬵��豸���ֶ��������SDK�ڲ����Զ�������������
			// �˲���Ϊ��ѡ�������������û���������
			NetSdk.CLIENT_SetAutoReconnect(haveReConnect, new NativeLong(0));

			// ���õ�¼��ʱʱ��ͳ��Դ��� , �˲���Ϊ��ѡ����	   
			int waitTime = 5000; // ��¼������Ӧ��ʱʱ������Ϊ 5s
			int tryTimes = 3; // ��¼ʱ���Խ�������3��
			NetSdk.CLIENT_SetConnectTime(waitTime, tryTimes);

			// ���ø������������NET_PARAM��nWaittime��nConnectTryNum��Ա��CLIENT_SetConnectTime 
			// �ӿ����õĵ�¼�豸��ʱʱ��ͳ��Դ���������ͬ
			// �˲���Ϊ��ѡ����
			NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
			netParam.nConnectTime = 10000; // ��¼ʱ���Խ������ӵĳ�ʱʱ��
			NetSdk.CLIENT_SetNetworkParam(netParam);

			return true;
		}

		// �������
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

		// �豸���߻ص�: ͨ�� CLIENT_Init ���øûص����������豸���ֶ���ʱ��SDK����øú���
		public class DisConnect implements NetSDKLib.fDisConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("Device[%s] Port[%d] Disconnect!\n", pchDVRIP, nDVRPort);
			}
		}

		// �������ӻָ����豸�����ɹ��ص�
		// ͨ�� CLIENT_SetAutoReconnect ���øûص����������Ѷ��ߵ��豸�����ɹ�ʱ��SDK����øú���
		public class HaveReConnect implements NetSDKLib.fHaveReConnect
		{
			public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser)
			{
				System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
			}
		}
	}

	/**
	 * ��¼��ť
	 */
	private void LoginButtonPerformed(ActionEvent e)
	{
		m_strIp = ipTextArea.getText();
		m_nPort = Integer.parseInt(portTextArea.getText());
		m_strUser = nameTextArea.getText();
		m_strPassword = new String(passwordTextArea.getPassword());

		System.out.println("�豸��ַ��" + m_strIp + "\n�˿ںţ�" + m_nPort + "\n�û�����" + m_strUser + "\n���룺" + m_strPassword);

		// ��¼�豸
		int nError[] =
		{ 0 };
		m_hLoginHandle = NetSdk.CLIENT_LoginEx(m_strIp, (short) m_nPort.intValue(), m_strUser, m_strPassword, 0, null, m_stDeviceInfo, nError);
		if (m_hLoginHandle.longValue() == 0)
		{
			System.err.printf("Login Device[%s] Port[%d]Failed. Last Error[%x]\n", m_strIp, m_nPort, NetSdk.CLIENT_GetLastError());
			JOptionPane.showMessageDialog(mainFrame, "��¼ʧ��");
		}
		else
		{
			System.out.println("Login Success [ " + m_strIp + " ]");
			JOptionPane.showMessageDialog(mainFrame, "��¼�ɹ�");
			logoutBtn.setEnabled(true);
			loginBtn.setEnabled(false);

		}
	}

	/**
	 * �ǳ���ť
	 */
	private void LoginOutButtonPerformed(ActionEvent e)
	{
		if (m_hLoginHandle.longValue() != 0)
		{
			System.out.println("LogOut Button Action");

			// ȷ���رռ���
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
	 * ��ʼʵʱ���Ӱ�ť�¼�
	 */
	private void startRealPlay()
	{
		if (m_hLoginHandle.longValue() == 0)
		{
			System.err.println("Please login first");
			JOptionPane.showMessageDialog(mainFrame, "���ȵ�¼");
			return;
		}

		int channel = 0; // Ԥ��ͨ����
		int playType = NetSDKLib.NET_RealPlayType.NET_RType_Realplay; // ʵʱԤ��

		m_hwnd = new HWND(Native.getComponentPointer(realplayWindow));
		m_hPlayHandle = NetSdk.CLIENT_RealPlayEx(m_hLoginHandle, channel, m_hwnd, playType);
		if (m_hPlayHandle.longValue() == 0)
		{
			int error = NetSdk.CLIENT_GetLastError();
			JOptionPane.showMessageDialog(mainFrame, "��ʼʵʱ����ʧ�ܣ������룺" + String.format("[0x%x]", error));
		}
		else
		{
			startPlayButton.setEnabled(false);
			stopPlayButton.setEnabled(true);
		}
	}

	/**
	 * ����ʵʱԤ����ť�¼�
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
	 * �ֶ�ץͼ��ť�¼�
	 */
	private void snapPicPeformed()
	{
		if (m_hLoginHandle.longValue() == 0)
		{
			System.err.println("Plese Login First");
			JOptionPane.showMessageDialog(mainFrame, "���ȵ�¼");
			return;
		}

		//Ben
		SnapRev OnSnapRevMessage = new SnapRev();
		NetSdk.CLIENT_SetSnapRevCallBack(OnSnapRevMessage, m_hLoginHandle);

		NetSDKLib.SNAP_PARAMS stParam = new NetSDKLib.SNAP_PARAMS();
		boolean bSnap = NetSdk.CLIENT_SnapPictureEx(m_hLoginHandle, stParam, stParam.Reserved);

		if (bSnap)
		{
			JOptionPane.showMessageDialog(mainFrame, "ץͼ�ɹ�");
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
	 * ���ñ߿�
	 */
	private void setBorderEx(JComponent object, String title, int width)
	{
		Border innerBorder = BorderFactory.createTitledBorder(title);
		Border outerBorder = BorderFactory.createEmptyBorder(width, width, width, width);
		object.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
	}

	/**
	 * ��¼���
	 */
	private class LoginPanel extends JPanel
	{
		public LoginPanel()
		{
			loginBtn = new JButton("����");
			logoutBtn = new JButton("�ǳ�");
			nameLabel = new JLabel("�û���");
			passwordLabel = new JLabel("����");
			nameTextArea = new JTextField("admin", 8);
			passwordTextArea = new JPasswordField("admin", 8);
			ipLabel = new JLabel("�豸��ַ");
			portLabel = new JLabel("�˿ں�");
			ipTextArea = new JTextField("192.168.1.108", 15);
			portTextArea = new JTextField("37777", 6);

			setLayout(new FlowLayout());
			setBorderEx(this, "��¼", 2);

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

			// ��¼��ť. �����¼�
			loginBtn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					LoginButtonPerformed(e);
				}
			});

			// �ǳ���ť. �����¼�
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
	 * Ԥ�����
	 */
	private class RealPlayPanel extends JPanel
	{
		public RealPlayPanel()
		{
			setBorderEx(this, "ʵʱԤ��", 2);
			setLayout(new BorderLayout());
			Dimension dim = getPreferredSize();
			dim.height = 367;
			dim.width = 374;
			setPreferredSize(dim);

			realplayWindow = new Panel();
			startPlayButton = new JButton("����");
			stopPlayButton = new JButton("ֹͣ����");
			snapButton = new JButton("�ֶ�ץͼ");

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

			//ץͼ��ť��������
			snapButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					snapPicPeformed();
				}
			});
			// ���Ӱ�ť��������
			startPlayButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					startRealPlay();
				}
			});

			// ֹͣ���Ӱ�ť����
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
	 * ���������
	 */
	private JFrame mainFrame;

	/**
	 * ��¼�����
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
	 * ʵʱԤ�����
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
