package AppFrame.contourDetection;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContourDetectionAdjustData extends JFrame
{
	
	public ContourDetectionAdjustData ()
	{
	Container c = getContentPane();
	c.setLayout(new GridLayout(6, 2, 10, 10));
	JLabel JLDataadjustor =new JLabel("����������Ȩ��");
	JTextField  JTDataadjustor = new JTextField();
	JLabel JLcheckdata =new JLabel("�豸��������");
	JTextField  JTcheckdata = new JTextField();	
	JLabel JLadjustdata = new JLabel("�˹���������");
	JTextField  JTadjustdata = new JTextField();	
	JLabel JLreason = new JLabel("����ԭ��");
	JTextField  JTreason = new JTextField();
	JLabel  JLbeizhu =new JLabel("��ע");
	JTextArea JTbeizhu = new JTextArea();
	JButton JBsave = new JButton("����");
	JButton JBcancle = new JButton("ȡ��");
	//���������
	c.add(JLDataadjustor);
	c.add(JTDataadjustor);
	c.add(JLcheckdata);
	c.add(JTcheckdata);
	c.add(JLadjustdata );
	c.add(JTadjustdata);
	c.add(JLreason);
	c.add(JTreason);
	c.add( JLbeizhu );
	c.add(JTbeizhu);
	c.add(JBsave);
	c.add(JBcancle );
	
	}
	
	

}
