package NetworkCamera.SDK_Hikvision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import SensorBase.LMSConstValue;

public class CapturePicsGUI extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static long userID1 = -1;
	private static long userID2 = -1;

	private JButton buttonRegist1;
	private JButton buttonRegist2;
	private JButton buttonCapture1;
	private JButton buttonCapture2;
	private JButton buttonRealPlay1;
	private JButton buttonRealPlay2;
	java.awt.Panel panelRealplay1;
	java.awt.Panel panelRealplay2;
	Realplay RL1 = new Realplay();
	Realplay RL2 = new Realplay();

	public static void main(String[] args)
	{
		JFrame window = new JFrame("IMI-BES");
		CapturePicsGUI content = new CapturePicsGUI();
		window.setContentPane(content);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
		window.setLocation((screenSize.width - window.getWidth()) / 2, (screenSize.height - window.getHeight()) / 2);
		window.setVisible(true);
	}

	public CapturePicsGUI()
	{
		buttonRegist1 = new JButton("注册1");
		buttonRegist1.addActionListener(this);
		buttonRegist2 = new JButton("注册2");
		buttonRegist2.addActionListener(this);
		buttonRealPlay1 = new JButton("预览1");
		buttonRealPlay1.addActionListener(this);
		buttonRealPlay2 = new JButton("预览2");
		buttonRealPlay2.addActionListener(this);
		buttonCapture1 = new JButton("抓图1");
		buttonCapture1.addActionListener(this);
		buttonCapture2 = new JButton("抓图2");
		buttonCapture2.addActionListener(this);

		JPanel panelButton = new JPanel();
		panelButton.setLayout(new GridLayout(3, 2, 1, 1));
		panelButton.add(buttonRegist1);
		panelButton.add(buttonRegist2);
		panelButton.add(buttonRealPlay1);
		panelButton.add(buttonRealPlay2);
		panelButton.add(buttonCapture1);
		panelButton.add(buttonCapture2);

		JPanel panelOfTwoRealplay = new JPanel();
		panelOfTwoRealplay.setLayout(new GridLayout(1, 2, 1, 1));
		panelOfTwoRealplay.add(RL1);
		panelOfTwoRealplay.add(RL2);

		setBackground(Color.gray);
		setLayout(new BorderLayout(2, 2));
		setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		add(panelButton, BorderLayout.SOUTH);
		add(panelOfTwoRealplay, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent evt)
	{
		Object source = evt.getSource();
		if (source == buttonRegist1)
		{
			userID1 = Camera_Hikvision.initAndRegist("192.168.0.31",LMSConstValue.FRONT_CAMERA_ID);

//			userID1 = CapturePics.initAndRegist("192.168.0.31");
		}
		else if (source == buttonRegist2)
		{
			userID2 = Camera_Hikvision.initAndRegist("192.168.0.32",LMSConstValue.BACK_CAMERA_ID);

//			userID2 = CapturePics.initAndRegist("192.168.0.32");
		}
		else if (source == buttonRealPlay1)
		{
			if (userID1 == -1)
			{
				System.out.println("未注�?");
				return;
			}
			
			RL1.realplay(userID1);
		}

		else if (source == buttonRealPlay2)
		{
			if (userID2 == -1)
			{
				System.out.println("未注�?");
				return;
			}
			RL2.realplay(userID2);
		}
		else if (source == buttonCapture1)
		{
			Camera_Hikvision.capturePics(userID1, "C:/Users/Ben/Desktop/Pics/", "A");
		}
		else if (source == buttonCapture2)
		{
			Camera_Hikvision.capturePics(userID2, "C:/Users/Ben/Desktop/Pics/", "B");
		}
	}

}
