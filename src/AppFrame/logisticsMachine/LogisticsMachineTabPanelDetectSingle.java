package AppFrame.logisticsMachine;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import database.DataBaseConst;
import database.ImageDetailFrame;

public class LogisticsMachineTabPanelDetectSingle {
	private final static String DEBUG_TAG="LogisticsMachineTabPanelMain";

	public static LMSingleDetectPanelEventListener lMSingleDetectPanelEventListener;

	private JFrame frame;
	LogisticsMachineDataBaseConst.MainPanelType panelType;
	
    private JLabel downViewImageLabel;
    private JLabel leftSideViewImageLabel;
    private JLabel rightSideViewImageLabel;
    private JLabel frontViewImageLabel;
    private JLabel rearViewImageLabel;
    private JLabel rotateAngleLabel;
    private JLabel httpStateLabel;
    
    private int ID;
    private JTextField barCodeTextField;
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JTextField lengthTextField;
    private JTextField boxVolumnTextField;
    private JTextField realVolumnTextField;
    private JTextField rotateAngleTextField;
        
    public String downImageFileName;
    public String leftSideImageFileName;
    public String rightSideImageFileName;
    public String frontImageFileName;
    public String rearImageFileName;

    JButton deleteButton;

    JPanel mainDetectSettingPanel = new JPanel();

    public LogisticsMachineTabPanelDetectSingle(JFrame frame,LogisticsMachineDataBaseConst.MainPanelType type)
    {    	
    	this.frame = frame;
		panelType = type;

		//======================================================
		if(panelType == LogisticsMachineDataBaseConst.MainPanelType.DETECT_RESULT)
		{
			lMSingleDetectPanelEventListener = new LMSingleDetectPanelEventListener();
			LMSEventManager.addListener(lMSingleDetectPanelEventListener);
		}
    }
    
	public JSplitPane createTab() {
				
		//======================================================
		JSplitPane mainTab = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		//=====================================================================
		mainTab.setLeftComponent(createMainDetectValuePanel());

		/*
		JSplitPane mainSplitPanel2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainTab.setRightComponent(mainSplitPanel2);

		//=====================================================================
		mainSplitPanel2.setLeftComponent(createMainDetectImagePanel());

		mainSplitPanel2.setRightComponent(createMainDetectSettingPanel());
		*/

//		mainTab.setRightComponent(new LogisticsMachineConveyorDrawPanel());
		
		return mainTab;
    }

	final int W_H_L_VALUE_FONT = 40;
	final int IMAGE_FONT = 16;
	private JPanel createMainDetectValuePanel() {	
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{120,250,120,250};
		gridBagLayout.rowHeights = new int[]{50,50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		//===============================================================
		JLabel barCodeLabel = new JLabel("条码");
		barCodeLabel.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_barCodeLabel = new GridBagConstraints();
		gbc_barCodeLabel.fill = GridBagConstraints.BOTH;
		gbc_barCodeLabel.gridwidth = 1;
		gbc_barCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_barCodeLabel.gridx = gridX;
		gbc_barCodeLabel.gridy = gridY;
		panel.add(barCodeLabel,gbc_barCodeLabel);
		gridX++;
		
		barCodeTextField = new JTextField("0");
		barCodeTextField.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_barCodeTextField = new GridBagConstraints();
		gbc_barCodeTextField.fill = GridBagConstraints.BOTH;
		gbc_barCodeTextField.gridwidth = 3;
		gbc_barCodeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_barCodeTextField.gridx = gridX;
		gbc_barCodeTextField.gridy = gridY;
		panel.add(barCodeTextField,gbc_barCodeTextField);
		barCodeTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) { 
				/*
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		       */
		     }  
		 });		

		//===============================================================
		gridY++;
		gridX = 0;
		JLabel realVolumnLabel = new JLabel("真实体积(cm3)");
		realVolumnLabel.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_realVolumnLabel = new GridBagConstraints();
		gbc_realVolumnLabel.fill = GridBagConstraints.BOTH;
		gbc_realVolumnLabel.gridwidth = 2;
		gbc_realVolumnLabel.insets = new Insets(0, 0, 5, 5);
		gbc_realVolumnLabel.gridx = gridX;
		gbc_realVolumnLabel.gridy = gridY;
		panel.add(realVolumnLabel,gbc_realVolumnLabel);
		gridX += gbc_realVolumnLabel.gridwidth;
		
		realVolumnTextField = new JTextField("0");
		realVolumnTextField.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_realVolumnTextField = new GridBagConstraints();
		gbc_realVolumnTextField.fill = GridBagConstraints.BOTH;
		gbc_realVolumnTextField.gridwidth = 2;
		gbc_realVolumnTextField.insets = new Insets(0, 0, 5, 5);
		gbc_realVolumnTextField.gridx = gridX;
		gbc_realVolumnTextField.gridy = gridY;
		panel.add(realVolumnTextField,gbc_realVolumnTextField);
		realVolumnTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		});
		gridX++;
		//===============================================================
		
		//===============================================================
		gridY++;
		gridX = 0;
		JLabel boxVolumnLabel = new JLabel("最小盒子体积(cm3)");
		boxVolumnLabel.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_boxVolumnLabel = new GridBagConstraints();
		gbc_boxVolumnLabel.fill = GridBagConstraints.BOTH;
		gbc_boxVolumnLabel.gridwidth = 2;
		gbc_boxVolumnLabel.insets = new Insets(0, 0, 5, 5);
		gbc_boxVolumnLabel.gridx = gridX;
		gbc_boxVolumnLabel.gridy = gridY;
		panel.add(boxVolumnLabel,gbc_boxVolumnLabel);
		gridX += gbc_boxVolumnLabel.gridwidth;
		
		boxVolumnTextField = new JTextField("0");
		boxVolumnTextField.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_boxVolumnTextField = new GridBagConstraints();
		gbc_boxVolumnTextField.fill = GridBagConstraints.BOTH;
		gbc_boxVolumnTextField.gridwidth = 2;
		gbc_boxVolumnTextField.insets = new Insets(0, 0, 5, 5);
		gbc_boxVolumnTextField.gridx = gridX;
		gbc_boxVolumnTextField.gridy = gridY;
		panel.add(boxVolumnTextField,gbc_boxVolumnTextField);
		boxVolumnTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		});
				
		//===============================================================
		gridX = 0;
		gridY++;
		
		JLabel lengthLabel = new JLabel("长(cm)");
		lengthLabel.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_lengthLabel = new GridBagConstraints();
		gbc_lengthLabel.fill = GridBagConstraints.BOTH;
		gbc_lengthLabel.gridwidth = 1;
		gbc_lengthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lengthLabel.gridx = gridX;
		gbc_lengthLabel.gridy = gridY;
		panel.add(lengthLabel,gbc_lengthLabel);
		gridX++;
		
		lengthTextField = new JTextField("0");
		lengthTextField.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_lengthTextField = new GridBagConstraints();
		gbc_lengthTextField.fill = GridBagConstraints.BOTH;
		gbc_lengthTextField.gridwidth = 3;
		gbc_lengthTextField.insets = new Insets(0, 0, 5, 5);
		gbc_lengthTextField.gridx = gridX;
		gbc_lengthTextField.gridy = gridY;
		panel.add(lengthTextField,gbc_lengthTextField);
		lengthTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		});
		gridX++;

		gridY++;
		gridX = 0;
		
		JLabel widthLabel = new JLabel("宽(cm)");
		widthLabel.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_widthLabel = new GridBagConstraints();
		gbc_widthLabel.fill = GridBagConstraints.BOTH;
		gbc_widthLabel.gridwidth = 1;
		gbc_widthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_widthLabel.gridx = gridX;
		gbc_widthLabel.gridy = gridY;
		panel.add(widthLabel,gbc_widthLabel);
		gridX++;
		
		widthTextField = new JTextField("0");
		widthTextField.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_widthTextField = new GridBagConstraints();
		gbc_widthTextField.fill = GridBagConstraints.BOTH;
		gbc_widthTextField.gridwidth = 3;
		gbc_widthTextField.insets = new Insets(0, 0, 5, 5);
		gbc_widthTextField.gridx = gridX;
		gbc_widthTextField.gridy = gridY;
		panel.add(widthTextField,gbc_widthTextField);
		widthTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		 });		
		gridX++;

		//===============================================================
		gridX = 0;
		gridY++;
		JLabel heightLabel = new JLabel("高(cm)");
		heightLabel.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_heightLabel = new GridBagConstraints();
		gbc_heightLabel.fill = GridBagConstraints.BOTH;
		gbc_heightLabel.gridwidth = 1;
		gbc_heightLabel.insets = new Insets(0, 0, 5, 5);
		gbc_heightLabel.gridx = gridX;
		gbc_heightLabel.gridy = gridY;
		panel.add(heightLabel,gbc_heightLabel);
		gridX++;
		
		heightTextField = new JTextField("0");
		heightTextField.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_heightTextField = new GridBagConstraints();
		gbc_heightTextField.fill = GridBagConstraints.BOTH;
		gbc_heightTextField.gridwidth = 3;
		gbc_heightTextField.insets = new Insets(0, 0, 5, 5);
		gbc_heightTextField.gridx = gridX;
		gbc_heightTextField.gridy = gridY;
		panel.add(heightTextField,gbc_heightTextField);
		heightTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		});
		gridX++;

		//===============================================================
		/*
		gridY++;
		gridX = 0;

		rotateAngleLabel = new JLabel("旋转角度");
		rotateAngleLabel.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_rotateAngle = new GridBagConstraints();
		gbc_rotateAngle.fill = GridBagConstraints.BOTH;
		gbc_rotateAngle.gridwidth = 2;
		gbc_rotateAngle.insets = new Insets(0, 0, 5, 5);
		gbc_rotateAngle.gridx = gridX;
		gbc_rotateAngle.gridy = gridY;
		panel.add(rotateAngleLabel,gbc_rotateAngle);
		gridX++;
		
		rotateAngleTextField = new JTextField("0");
		rotateAngleTextField.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
		GridBagConstraints gbc_rotateAngleTextField = new GridBagConstraints();
		gbc_rotateAngleTextField.fill = GridBagConstraints.BOTH;
		gbc_rotateAngleTextField.gridwidth = 1;
		gbc_rotateAngleTextField.insets = new Insets(0, 0, 5, 5);
		gbc_rotateAngleTextField.gridx = gridX;
		gbc_rotateAngleTextField.gridy = gridY;
		panel.add(rotateAngleTextField,gbc_rotateAngleTextField);
		rotateAngleTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //关键，屏蔽掉非法输入   
					
		       }  
		     }  
		});
		*/
		
		//===============================================================
		gridY++;
		gridX = 0;
		
		httpStateLabel = new JLabel("与服务器连接状态：");
		GridBagConstraints gbc_httpStateLabel = new GridBagConstraints();
		gbc_httpStateLabel.fill = GridBagConstraints.BOTH;
		gbc_httpStateLabel.gridwidth = 1;
		gbc_httpStateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_httpStateLabel.gridx = gridX;
		gbc_httpStateLabel.gridy = gridY;
		panel.add(httpStateLabel,gbc_httpStateLabel);		

		return panel;
	}	
	
	private JPanel createMainDetectImagePanel() {	
		JPanel panel = new JPanel();
		
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 300, 300};
		gridBagLayout.rowHeights = new int[]{20,180,20,180};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;

		JLabel downViewLabel = new JLabel("俯视图");
		GridBagConstraints gbc_downViewLabel = new GridBagConstraints();
		gbc_downViewLabel.fill = GridBagConstraints.BOTH;
		gbc_downViewLabel.gridwidth = 1;
		gbc_downViewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_downViewLabel.gridx = gridX;
		gbc_downViewLabel.gridy = gridY;
		panel.add(downViewLabel,gbc_downViewLabel);
		gridX++;
	
		JLabel leftSideViewLabel = new JLabel("左侧视图");
		GridBagConstraints gbc_leftSideViewLabel = new GridBagConstraints();
		gbc_leftSideViewLabel.fill = GridBagConstraints.BOTH;
		gbc_leftSideViewLabel.gridwidth = 1;
		gbc_leftSideViewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_leftSideViewLabel.gridx = gridX;
		gbc_leftSideViewLabel.gridy = gridY;
		panel.add(leftSideViewLabel,gbc_leftSideViewLabel);
		gridX++;
		
		JLabel rightSideViewLabel = new JLabel("右侧视图");
		GridBagConstraints gbc_rightSideViewLabel = new GridBagConstraints();
		gbc_rightSideViewLabel.fill = GridBagConstraints.BOTH;
		gbc_rightSideViewLabel.gridwidth = 1;
		gbc_rightSideViewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rightSideViewLabel.gridx = gridX;
		gbc_rightSideViewLabel.gridy = gridY;
		panel.add(rightSideViewLabel,gbc_rightSideViewLabel);
		gridX++;

		gridX = 0;
		//================================================================
		gridY++;

		downViewImageLabel = new JLabel();
		GridBagConstraints gbc_downViewImageLabel = new GridBagConstraints();
		gbc_downViewImageLabel.fill = GridBagConstraints.BOTH;
		gbc_downViewImageLabel.gridwidth = 1;
		gbc_downViewImageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_downViewImageLabel.gridx = gridX;
		gbc_downViewImageLabel.gridy = gridY;
		downViewImageLabel.addMouseListener(new labelMouseListener(downViewImageLabel)); 
		panel.add(downViewImageLabel,gbc_downViewImageLabel);

		gridX++;
	
		leftSideViewImageLabel = new JLabel();
		GridBagConstraints gbc_leftSideViewImageLabel = new GridBagConstraints();
		gbc_leftSideViewImageLabel.fill = GridBagConstraints.BOTH;
		gbc_leftSideViewImageLabel.gridwidth = 1;
		gbc_leftSideViewImageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_leftSideViewImageLabel.gridx = gridX;
		gbc_leftSideViewImageLabel.gridy = gridY;
		leftSideViewImageLabel.addMouseListener(new labelMouseListener(leftSideViewImageLabel)); 
		panel.add(leftSideViewImageLabel,gbc_leftSideViewImageLabel);
		gridX++;

		rightSideViewImageLabel = new JLabel();
		GridBagConstraints gbc_rightSideViewImageLabel = new GridBagConstraints();
		gbc_rightSideViewImageLabel.fill = GridBagConstraints.BOTH;
		gbc_rightSideViewImageLabel.gridwidth = 1;
		gbc_rightSideViewImageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rightSideViewImageLabel.gridx = gridX;
		gbc_rightSideViewImageLabel.gridy = gridY;
		rightSideViewImageLabel.addMouseListener(new labelMouseListener(rightSideViewImageLabel)); 
		panel.add(rightSideViewImageLabel,gbc_rightSideViewImageLabel);
		gridX++;

		gridY++;
		//===================================================================
		gridX = 0;
		
		JLabel frontViewLabel = new JLabel("前视图");
		GridBagConstraints gbc_frontViewLabel = new GridBagConstraints();
		gbc_frontViewLabel.fill = GridBagConstraints.BOTH;
		gbc_frontViewLabel.gridwidth = 1;
		gbc_frontViewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_frontViewLabel.gridx = gridX;
		gbc_frontViewLabel.gridy = gridY;
		panel.add(frontViewLabel,gbc_frontViewLabel);
		gridX++;
		
		JLabel rearViewLabel = new JLabel("后视图");
		GridBagConstraints gbc_rearViewLabel = new GridBagConstraints();
		gbc_rearViewLabel.fill = GridBagConstraints.BOTH;
		gbc_rearViewLabel.gridwidth = 1;
		gbc_rearViewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rearViewLabel.gridx = gridX;
		gbc_rearViewLabel.gridy = gridY;
		panel.add(rearViewLabel,gbc_rearViewLabel);
		gridX++;
		
		gridY++;
		//===================================================================
		gridX = 0;

		frontViewImageLabel = new JLabel();
		GridBagConstraints gbc_frontViewImageLabel = new GridBagConstraints();
		gbc_frontViewImageLabel.fill = GridBagConstraints.BOTH;
		gbc_frontViewImageLabel.gridwidth = 1;
		gbc_frontViewImageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_frontViewImageLabel.gridx = gridX;
		gbc_frontViewImageLabel.gridy = gridY;
		frontViewImageLabel.addMouseListener(new labelMouseListener(frontViewImageLabel)); 
		panel.add(frontViewImageLabel,gbc_frontViewImageLabel);
		gridX++;
		
		rearViewImageLabel = new JLabel();
		GridBagConstraints gbc_rearViewImageLabel = new GridBagConstraints();
		gbc_rearViewImageLabel.fill = GridBagConstraints.BOTH;
		gbc_rearViewImageLabel.gridwidth = 1;
		gbc_rearViewImageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rearViewImageLabel.gridx = gridX;
		gbc_rearViewImageLabel.gridy = gridY;
		rearViewImageLabel.addMouseListener(new labelMouseListener(rearViewImageLabel)); 
		panel.add(rearViewImageLabel,gbc_rearViewImageLabel);

		return panel;
	}
		
	private class LMSingleDetectPanelEventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

	        if (event.getEventType() != null && eventType.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT)) 
	        {
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
				{
					if((Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.CAR_RESULT.ordinal())
					{						
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH))
						{
							int carWidth = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_WIDTH); 
							
							widthTextField.setText(String.valueOf((float)carWidth/10));
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT))
						{
							int carHeight = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT); 
							heightTextField.setText(String.valueOf((float)carHeight/10));
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH))
						{
							int carLength = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_LENGTH); 
							lengthTextField.setText(String.valueOf((float)carLength/10));
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_BAR_CODE))
						{
							String sBarCode = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_BAR_CODE); 
							barCodeTextField.setText(sBarCode);
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN))
						{
							int boxVolumn = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_BOX_VOLUMN); 
							boxVolumnTextField.setText(String.valueOf(boxVolumn));
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN))
						{
							int realVolumn = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN); 
							realVolumnTextField.setText(String.valueOf(realVolumn));
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ROTATE_ANGLE))
						{
							float rotateAngle = (Float) eventExtra.get(LMSConstValue.INTENT_EXTRA_ROTATE_ANGLE); 
							rotateAngleTextField.setText(String.valueOf(rotateAngle));
						}
						
						/*
						downImageFileName = "image//down.png";
						setScaledIconImage(downImageFileName,downViewImageLabel);
		
						leftSideImageFileName = "image//left.png";
						setScaledIconImage(leftSideImageFileName,leftSideViewImageLabel);

						rightSideImageFileName = "image//right.png";
						setScaledIconImage(rightSideImageFileName,rightSideViewImageLabel);

						frontImageFileName = "image//front.png";
						setScaledIconImage(frontImageFileName,frontViewImageLabel);

						rearImageFileName = "image//rear.png";
						setScaledIconImage(rearImageFileName,rearViewImageLabel);
						*/
														
//						if(LMSConstValue.isMyMachine())	
						{
							jdbc_mysql_add();
						}
					}
				}
	        }
	        else if (event.getEventType() != null && eventType.equals(LMSConstValue.HTTP_STATE_STRING_INTENT)) 
	        {
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_HTTP_STATE))
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_HTTP_STATE); 
					
					httpStateLabel.setText(str);
				}
	        }
		}
	}
	
	void setScaledIconImage(String fileName,JLabel viewImageLabel)
	{
	    ImageIcon iconImage = new ImageIcon(fileName);
	    
	    float labelWidth = viewImageLabel.getWidth();
	    float labelHeight = viewImageLabel.getHeight();
	    float iconWidth = iconImage.getIconWidth();
	    float iconHeight = iconImage.getIconHeight();
	    int width,height;
	    
	    if(labelWidth/labelHeight < iconWidth/iconHeight)
	    {
	    	width = (int) labelWidth;
	    	height = (int) (width*iconHeight/iconWidth);
	    }
	    else
	    {
	    	height = (int) labelHeight;
	    	width = (int) (height*iconWidth/iconHeight);
	    	
	    }
	    	
	    iconImage.setImage(iconImage.getImage().getScaledInstance(width,height,Image.SCALE_AREA_AVERAGING)); 
	    viewImageLabel.setIcon(iconImage); 
	}
	
	void jdbc_mysql_add()
	{			
		if(DataBaseConst.local_store_access_conn != null)
		{
			//=========================================================================================
			//连接
			// MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值 
			// 避免中文乱码要指定useUnicode和characterEncoding 
			// 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定， 
			// 下面语句之前就要先创建javademo数据库       
			try { 
		        Date date = new Date();//获得系统时间.               
		        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	
		        String sBarCode;
		        float width,height,length;
		        int boxVolumn,realVolumn;
		        float rotateAngle;
		        if(barCodeTextField.getText().equals(""))
		        	sBarCode = null;
		        else
		        	sBarCode = barCodeTextField.getText();
		        if(widthTextField.getText().equals(""))
		        	width = 0;
		        else
		        	width = Float.valueOf(widthTextField.getText());
		        if(heightTextField.getText().equals(""))
		        	height = 0;
		        else
		        	height = Float.valueOf(heightTextField.getText());
		        if(lengthTextField.getText().equals(""))
		        	length = 0;
		        else
		        	length = Float.valueOf(lengthTextField.getText());
		        if(boxVolumnTextField == null || boxVolumnTextField.getText().equals(""))
		        	boxVolumn = 0;
		        else
		        	boxVolumn = Integer.valueOf(boxVolumnTextField.getText());
		        if(realVolumnTextField == null || realVolumnTextField.getText().equals(""))
		        	realVolumn = 0;
		        else
		        	realVolumn = Integer.valueOf(realVolumnTextField.getText());
		        if(rotateAngleTextField == null|| rotateAngleTextField.getText().equals(""))
		        	rotateAngle = 0;
		        else
		        	rotateAngle = Float.valueOf(rotateAngleTextField.getText());
		        
		        String sql = "insert into "+LogisticsMachineDataBaseConst.TABLE_NAME+"("
					+LogisticsMachineDataBaseConst.TABLE_COLUMN_TIME+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_BAR_CODE+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_WIDTH+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_HEIGHT+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_LENGTH+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_BOX_VOLUMN+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_REAL_VOLUMN+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_ROTATE_ANGLE
		        	+") values(" 
		        	+"'"+nowTime+"'"+','
		        	+"'"+sBarCode+"'"+','
		        	+"'"+width+"'"+','
		        	+"'"+height+"'"+','
		        	+"'"+length+"'"+','
		        	+"'"+boxVolumn+"'"+','
		        	+"'"+realVolumn+"'"+','
		        	+"'"+rotateAngle+"'"
		        	+")";
				LMSLog.d(DEBUG_TAG,"jdbc_mysql_add="+sql); 
	
				PreparedStatement ps = DataBaseConst.local_store_access_conn.prepareStatement(sql);
	
				/*
				InputStream inputStream = null;
				String imageStr;
				int index = 0;
				
				imageStr = "image//left.png";
				inputStream = ImageUtil.getImageByte(imageStr);
				if(inputStream != null)
				{
					index++;
					ps.setBinaryStream(index, inputStream ,inputStream.available());
				}
				else
				{
					LMSLog.d(DEBUG_TAG, "no image:"+imageStr);
				}
				
				imageStr = "image//right.png";
				inputStream = ImageUtil.getImageByte(imageStr);
				if(inputStream != null)
				{
					index++;
					ps.setBinaryStream(index, inputStream ,inputStream.available());
				}
				else
				{
					LMSLog.d(DEBUG_TAG, "no image:"+imageStr);
				}
				
				imageStr = "image//down.png";
		        inputStream = ImageUtil.getImageByte(imageStr);
				if(inputStream != null)
				{
					index++;
					ps.setBinaryStream(index, inputStream ,inputStream.available());
				}
				else
				{
					LMSLog.d(DEBUG_TAG, "no image:"+imageStr);
				}

				imageStr = "image//front.png";
				inputStream = ImageUtil.getImageByte(imageStr);
				if(inputStream != null)
				{
					index++;
					ps.setBinaryStream(index, inputStream ,inputStream.available());
				}
				else
				{
					LMSLog.d(DEBUG_TAG, "no image:"+imageStr);
				}

				imageStr = "image//rear.png";
				inputStream = ImageUtil.getImageByte(imageStr);
				if(inputStream != null)
				{
					index++;
					ps.setBinaryStream(index, inputStream ,inputStream.available());
				}
				else
				{
					LMSLog.d(DEBUG_TAG, "no image:"+imageStr);
				}
				if(index > 0)
				 */
				{
					ps.execute();
				}
			} catch (SQLException e) { 
				LMSLog.exception(e);
			} catch (Exception e) { 
				LMSLog.exception(e);
			} 
		}
	}
	
	void jdbc_mysql_update_id(int id)
	{
		if(DataBaseConst.local_store_access_conn != null)
		{
			//=========================================================================================
			//连接
			// MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值 
			// 避免中文乱码要指定useUnicode和characterEncoding 
			// 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定， 
			// 下面语句之前就要先创建javademo数据库       
			try { 
				Statement stmt = DataBaseConst.local_store_access_conn.createStatement(); 
				String sql = "update "+LogisticsMachineDataBaseConst.TABLE_NAME+" set "
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_BAR_CODE+'='+"'"+barCodeTextField.getText()+"'"+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_WIDTH+'='+"'"+widthTextField.getText()+"'"+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_HEIGHT+'='+"'"+heightTextField.getText()+"'"+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_LENGTH+'='+"'"+lengthTextField.getText()+"'"+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_BOX_VOLUMN+'='+"'"+boxVolumnTextField.getText()+"'"+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_REAL_VOLUMN+'='+"'"+realVolumnTextField.getText()+"'"+','
		        	+LogisticsMachineDataBaseConst.TABLE_COLUMN_LENGTH+'='+"'"+rotateAngleTextField.getText()+"'"
		        	+" where "+LogisticsMachineDataBaseConst.TABLE_COLUMN_ID+" = "+ "'" + id + "'"; 
				LMSLog.d(DEBUG_TAG,"jdbc_mysql_update_id="+sql); 
	//			sql = "insert into student(NO,name) values('2012002','周小俊')"; 
		        stmt.executeUpdate(sql); 
			} catch (SQLException e) { 
				LMSLog.exception(e);
			} catch (Exception e) { 
				LMSLog.exception(e);
			}
		}
	}
	
	public class labelMouseListener implements MouseListener{
		JLabel _label;
		
		public labelMouseListener(JLabel label){
			_label = label;
		}

		public void mouseEntered(MouseEvent e) {
			/**鼠标移到组件上方法时事件处理方法.**/
		}

		public void mouseExited(MouseEvent e) {
		/**鼠标移开组件时事件处理方法.**/}

		public void mousePressed(MouseEvent e) {
		/**鼠标在组件上按下(但没弹起)时事件处理方法.**/}

		public void mouseReleased(MouseEvent e) {
		/**鼠标在组件上弹起事件处理方法.**/}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			String fileName = null;

			if(_label == downViewImageLabel)
				fileName = downImageFileName;
			else if(_label == leftSideViewImageLabel)
				fileName = leftSideImageFileName;
			else if(_label == rightSideViewImageLabel)
				fileName = rightSideImageFileName;
			else if(_label == frontViewImageLabel)
				fileName = frontImageFileName;
			else if(_label == rearViewImageLabel)
				fileName = rearImageFileName;
				
			if(fileName != null)
			{				
				ImageDetailFrame.showImage(fileName);
			}
		}
	}
}
