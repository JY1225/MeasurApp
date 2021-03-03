package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import AppBase.appBase.CarTypeAdapter;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import layer.dataLayer.LMSTelegram;
import lmsBase.LMSProductInfo;
import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class JAuthFrame extends JFrame{
	String DEBUG_TAG="JAuthFrame";

	JPasswordField textFieldOriginalAuth;
	JPasswordField textFieldNewAuth;
	JPasswordField textFieldNewAuthConfirm;
	JPasswordField superPasswordField;
	
	String originalMD5Str = "";
	String newMD5Str = "";
	String newMD5ConfirmStr = "";
	String tmpMD5Str = "";
	
	JTextField genPasswordTextField;
	JLabel labelEnterKey;
	JLabel labelPasswordGen;

	JTextField superUserGenPasswordTextField;
	JLabel superUserPasswordLabel;
	
	public JAuthFrame()
	{		
		//==============================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==============================================================================		
		setBounds(200, 100, 600, 340);

		//==============================================================================
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{829};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0,1.0};
		getContentPane().setLayout(gridBagLayout);

		//========================================================
		setTitle("��������");
		
		//========================================================
		getContentPane().add(createJPanel());
	}
	
	private JPanel createJPanel()
	{
		JPanel panel = new JPanel();
	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 100, 100, 100,};
		gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{0, 0, 0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===========================================================================
		int gridX = 0;
		int gridY = 0;
		
		JLabel labelOriginalString = new JLabel();
    	GridBagConstraints gbc_labelOriginalString = new GridBagConstraints();
    	gbc_labelOriginalString.fill = GridBagConstraints.BOTH;
    	gbc_labelOriginalString.insets = new Insets(0, 0, 0, 5);
    	gbc_labelOriginalString.gridx = gridX;
    	gbc_labelOriginalString.gridy = gridY;
    	panel.add(labelOriginalString, gbc_labelOriginalString);
    	if(LMSConstValue.sNvramSettingPassword.sValue.equals(""))
    	{
    		labelOriginalString.setText("ԭ����(δ��)");
    	}
    	else
    	{
    		labelOriginalString.setText("ԭ����(����)");    		
    	}
    	gridX++;
    	
    	textFieldOriginalAuth = new JPasswordField();
	    GridBagConstraints gbc_textFieldOriginalAuth = new GridBagConstraints();
	    gbc_textFieldOriginalAuth.insets = new Insets(0, 0, 0, 5);
	    gbc_textFieldOriginalAuth.fill = GridBagConstraints.BOTH;
	    gbc_textFieldOriginalAuth.gridx = gridX;
	    gbc_textFieldOriginalAuth.gridy = gridY;
	    panel.add(textFieldOriginalAuth,gbc_textFieldOriginalAuth);
	    textFieldOriginalAuth.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = textFieldOriginalAuth.getText();

				if(!str.equals("")){					
					originalMD5Str = Md5.convertMD5(str);  
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
	    textFieldOriginalAuth.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
				{  
			
				}else{  
					e.consume(); //�ؼ������ε��Ƿ�����   
				}  
			}  
		});
	    
        gridX++;
	    new JCheckBoxNvram(
			panel,
			gridX,gridY,1,
			"����ɼ�",
			LMSConstValue.bNvramOriginalPasswordView,
			-1,
			false
		);
	    
	    gridY++;
	    gridX=0;
	    
		//===========================================================================
	    JLabel labelNewString = new JLabel("����������");
    	GridBagConstraints gbc_labelNewString = new GridBagConstraints();
    	gbc_labelNewString.fill = GridBagConstraints.BOTH;
    	gbc_labelNewString.insets = new Insets(0, 0, 0, 5);
    	gbc_labelNewString.gridx = gridX;
    	gbc_labelNewString.gridy = gridY;
    	panel.add(labelNewString, gbc_labelNewString);
    	gridX++;
    	
    	textFieldNewAuth = new JPasswordField();
	    GridBagConstraints gbc_textFieldNewAuth = new GridBagConstraints();
	    gbc_textFieldNewAuth.insets = new Insets(0, 0, 0, 5);
	    gbc_textFieldNewAuth.fill = GridBagConstraints.BOTH;
	    gbc_textFieldNewAuth.gridx = gridX;
	    gbc_textFieldNewAuth.gridy = gridY;
	    panel.add(textFieldNewAuth,gbc_textFieldNewAuth);
	    textFieldNewAuth.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = textFieldNewAuth.getText();

				if(!str.equals("")){					
					newMD5Str = Md5.convertMD5(str);  					
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
	    textFieldOriginalAuth.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
				{  
			
				}else{  
					e.consume(); //�ؼ������ε��Ƿ�����   
				}  
			}  
		});
	    
	    gridX++;
	    new JCheckBoxNvram(
			panel,
			gridX,gridY,1,
			"����ɼ�",
			LMSConstValue.bNvramNewPasswordView,
			-1,
			false
		);
	    
	    gridY++;
	    gridX=0;

	    //===========================================================================
	    JLabel labelNewConfirmString = new JLabel("������ȷ��");
    	GridBagConstraints gbc_labelNewConfirmString = new GridBagConstraints();
    	gbc_labelNewConfirmString.fill = GridBagConstraints.BOTH;
    	gbc_labelNewConfirmString.insets = new Insets(0, 0, 0, 5);
    	gbc_labelNewConfirmString.gridx = gridX;
    	gbc_labelNewConfirmString.gridy = gridY;
    	panel.add(labelNewConfirmString, gbc_labelNewConfirmString);
    	gridX++;
    	
    	textFieldNewAuthConfirm = new JPasswordField();
	    GridBagConstraints gbc_textFieldNewAuthConfirm = new GridBagConstraints();
	    gbc_textFieldNewAuthConfirm.insets = new Insets(0, 0, 0, 5);
	    gbc_textFieldNewAuthConfirm.fill = GridBagConstraints.BOTH;
	    gbc_textFieldNewAuthConfirm.gridx = gridX;
	    gbc_textFieldNewAuthConfirm.gridy = gridY;
	    panel.add(textFieldNewAuthConfirm,gbc_textFieldNewAuthConfirm);
	    textFieldNewAuthConfirm.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = textFieldNewAuthConfirm.getText();

				if(!str.equals("")){					
					newMD5ConfirmStr = Md5.convertMD5(str);  					
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
	    textFieldNewAuthConfirm.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
				{  
			
				}else{  
					e.consume(); //�ؼ������ε��Ƿ�����   
				}  
			}  
		});
	    
	    gridY++;
	    gridX=0;
		//===========================================================================

	    JButton saveButton = new JButton("��������");
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.fill = GridBagConstraints.BOTH;
		gbc_saveButton.gridwidth = 1;
		gbc_saveButton.insets = new Insets(0, 0, 5, 5);
		gbc_saveButton.gridx = gridX;
		gbc_saveButton.gridy = gridY;
		panel.add(saveButton,gbc_saveButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!newMD5ConfirmStr.equals(newMD5Str))
				{
					LMSLog.warningDialog("��������", "��������ʧ��,�������������ò�һ��");
				}
				else if(newMD5Str.equals(""))
				{
					LMSLog.warningDialog("��������", "��������ʧ��,������Ϊ��");
				}
				else
				{ 
					if(originalMD5Str.equals(LMSConstValue.sPasswordTmpAllMD5Str)
						||originalMD5Str.equals(LMSConstValue.sNvramSettingPassword.sValue)
					)
					{
						LMSConstValue.sNvramSettingPassword.sValue = newMD5Str;
						
						HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.sNvramSettingPassword.nvramStr);
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.sNvramSettingPassword.sValue);
						LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtra);
	
						LMSLog.warningDialog("��������", "�����������");
					}
					else
					{
						if(LMSProductInfo.customerCode0 == null)
						{
							LMSLog.warningDialog("��������", "��������ʧ��,���������к�Ϊ��");
						}
						else 
						{
							for(int i=0;i<LMSProductInfo.customerList.size();i++)
							{
								String[] customer = LMSProductInfo.customerList.get(i);

								//-----------------------------------------------------------
								int iPasswordTmpCustomer = iGenTmpPasswordCustomer(LMSConstValue.iPasswordKey,customer[1]);
							    String sPasswordTmpCustomer = String.valueOf(iPasswordTmpCustomer);
							    String sPasswordTmpCustomerMD5Str = Md5.convertMD5(sPasswordTmpCustomer);  	

								if(originalMD5Str.equals(sPasswordTmpCustomerMD5Str))
								{
									if(customer[1].equals(LMSProductInfo.customerCode0[1]))
									{
										LMSConstValue.sNvramSettingPassword.sValue = newMD5Str;
										
										HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
										eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
										eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.sNvramSettingPassword.nvramStr);
										eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, LMSConstValue.sNvramSettingPassword.sValue);
										LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtra);
					
										LMSLog.warningDialog("��������", "�����������");

										return;
									}
									else
									{
										LMSLog.warningDialog("��������", "��������ʧ��,�������ͻ���������");
										
										return;
									}									
								}
							}
	
							LMSLog.warningDialog("��������", "��������ʧ��,ԭ�������");
						}
					}
				}
			}
		});		
		gridX++;
		
		JLabel labelKeyString = new JLabel("��Կ:"+LMSConstValue.iPasswordKey);
    	GridBagConstraints gbc_labelKeyString = new GridBagConstraints();
    	gbc_labelKeyString.fill = GridBagConstraints.BOTH;
    	gbc_labelKeyString.insets = new Insets(0, 0, 0, 5);
    	gbc_labelKeyString.gridx = gridX;
    	gbc_labelKeyString.gridy = gridY;
    	panel.add(labelKeyString, gbc_labelKeyString);
    	gridX++;

		//===========================================================================
		gridY++;
		gridX=0;
			    	
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"ѡ��ͻ�",
			-1,
			LMSProductInfo.nvramCustomerCode,LMSProductInfo.enumForComboBoxCustomerCode
		);
		gridX+=2;
		
		//-----------------------------------------------------------------------------
		JLabel labelSuperPassword = new JLabel("��������:");
    	GridBagConstraints gbc_labelSuperPassword = new GridBagConstraints();
    	gbc_labelSuperPassword.fill = GridBagConstraints.BOTH;
    	gbc_labelSuperPassword.insets = new Insets(0, 0, 0, 5);
    	gbc_labelSuperPassword.gridx = gridX;
    	gbc_labelSuperPassword.gridy = gridY;
    	panel.add(labelSuperPassword, gbc_labelSuperPassword);
    	gridX++;
    	
		superPasswordField = new JPasswordField();
	    GridBagConstraints gbc_superPasswordField = new GridBagConstraints();
	    gbc_superPasswordField.insets = new Insets(0, 0, 0, 5);
	    gbc_superPasswordField.fill = GridBagConstraints.BOTH;
	    gbc_superPasswordField.gridx = gridX;
	    gbc_superPasswordField.gridy = gridY;
	    panel.add(superPasswordField,gbc_superPasswordField);
	    superPasswordField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {				
				for(int i=0;i<LMSProductInfo.customerList.size();i++)
				{
					String[] customer = LMSProductInfo.customerList.get(i);
					if(customer[0].equals(LMSProductInfo.enumForComboBoxCustomerCode.key)
						&&customer[1].equals(superPasswordField.getText()))
					{
						CustomerCode = customer[1];
						genPasswordTextField.setEditable(true);							
					}
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});
	    superPasswordField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
				{  
			
				}else{  
					e.consume(); //�ؼ������ε��Ƿ�����   
				}  
			}  
		});
	    
	    //========================================================================
	    gridY++;
	    gridX = 0;
	    
	    labelEnterKey = new JLabel("������Կ");
    	GridBagConstraints gbc_labelEnterKey = new GridBagConstraints();
    	gbc_labelEnterKey.fill = GridBagConstraints.BOTH;
    	gbc_labelEnterKey.insets = new Insets(0, 0, 0, 5);
    	gbc_labelEnterKey.gridx = gridX;
    	gbc_labelEnterKey.gridy = gridY;
    	panel.add(labelEnterKey, gbc_labelEnterKey);
    	gridX++;

    	genPasswordTextField = new JTextField();
		GridBagConstraints gbc_genPasswordTextField = new GridBagConstraints();
		gbc_genPasswordTextField.fill = GridBagConstraints.BOTH;
		gbc_genPasswordTextField.gridwidth = 1;
		gbc_genPasswordTextField.insets = new Insets(0, 0, 5, 5);
		gbc_genPasswordTextField.gridx = gridX;
		gbc_genPasswordTextField.gridy = gridY;
		panel.add(genPasswordTextField,gbc_genPasswordTextField);
		genPasswordTextField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
					
				}else{  
					e.consume(); //�ؼ������ε��Ƿ�����   
		       }  
		     }  
		});
		genPasswordTextField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent e) {
				String str = genPasswordTextField.getText();

				if(!str.equals("")){	
					labelPasswordGen.setText(""+iGenTmpPasswordCustomer(Integer.valueOf(str), CustomerCode));
				}
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		genPasswordTextField.setEditable(false);
		gridX++;
		
		JLabel labelPassword = new JLabel("���ɵ�ԭʼ����:");
    	GridBagConstraints gbc_labelPassword = new GridBagConstraints();
    	gbc_labelPassword.fill = GridBagConstraints.BOTH;
    	gbc_labelPassword.insets = new Insets(0, 0, 0, 5);
    	gbc_labelPassword.gridx = gridX;
    	gbc_labelPassword.gridy = gridY;
    	panel.add(labelPassword, gbc_labelPassword);
    	gridX++;
    	
    	labelPasswordGen = new JLabel("_________");
    	GridBagConstraints gbc_labelPasswordGen = new GridBagConstraints();
    	gbc_labelPasswordGen.fill = GridBagConstraints.BOTH;
    	gbc_labelPasswordGen.insets = new Insets(0, 0, 0, 5);
    	gbc_labelPasswordGen.gridx = gridX;
    	gbc_labelPasswordGen.gridy = gridY;
    	panel.add(labelPasswordGen, gbc_labelPasswordGen);
    	gridX++;
	
    	//===============================================================================
    	if(LMSConstValue.isMyMachine())
    	{
    		//========================================================================
		    gridY++;
		    gridX = 0;
		    
		    superUserGenPasswordTextField = new JTextField();
    		GridBagConstraints gbc_superUserGenPasswordTextField = new GridBagConstraints();
    		gbc_superUserGenPasswordTextField.fill = GridBagConstraints.BOTH;
    		gbc_superUserGenPasswordTextField.gridwidth = 1;
    		gbc_superUserGenPasswordTextField.insets = new Insets(0, 0, 5, 5);
    		gbc_superUserGenPasswordTextField.gridx = gridX;
    		gbc_superUserGenPasswordTextField.gridy = gridY;
    		panel.add(superUserGenPasswordTextField,gbc_superUserGenPasswordTextField);
    		superUserGenPasswordTextField.addKeyListener(new KeyAdapter(){  
    			public void keyTyped(KeyEvent e) {  
    				int keyChar = e.getKeyChar();                 
    			
    				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
    					
    				}else{  
    					e.consume(); //�ؼ������ε��Ƿ�����   
    		       }  
    		     }  
    		});
    		superUserGenPasswordTextField.addFocusListener(new FocusListener(){			
    			public void focusLost(FocusEvent e) {
    				String str = superUserGenPasswordTextField.getText();

    				if(!str.equals("")){	
    					superUserPasswordLabel.setText(""+LMSConstValue.iGenTmpPasswordAll(Integer.valueOf(str)));
    				}
    			}
    			@Override
    			public void focusGained(FocusEvent arg0) {
    				// TODO Auto-generated method stub
    				
    			}
    		});
    		gridX++;
    		
    		superUserPasswordLabel = new JLabel("�����û���ʱ����");
        	GridBagConstraints gbc_superUserPasswordLabel = new GridBagConstraints();
        	gbc_superUserPasswordLabel.fill = GridBagConstraints.BOTH;
        	gbc_superUserPasswordLabel.insets = new Insets(0, 0, 0, 5);
        	gbc_superUserPasswordLabel.gridx = gridX;
        	gbc_superUserPasswordLabel.gridy = gridY;
        	panel.add(superUserPasswordLabel, gbc_superUserPasswordLabel);
        	gridX++;
		}
    	
		return panel;
	}
	
	String CustomerCode;
    public static int iGenTmpPasswordCustomer(int _iPasswordKey,String customerCode)
	{
		return 3*((_iPasswordKey/3)+Integer.valueOf(customerCode));
	}	
    
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
				
				if(nvram.equals(LMSConstValue.bNvramOriginalPasswordView.nvramStr))
				{
					if(LMSConstValue.bNvramOriginalPasswordView.bValue == true)
						textFieldOriginalAuth.setEchoChar((char) 0);
					else
						textFieldOriginalAuth.setEchoChar('*');
				}
				else if(nvram.equals(LMSConstValue.bNvramNewPasswordView.nvramStr))
				{
					if(LMSConstValue.bNvramNewPasswordView.bValue == true)
					{
						textFieldNewAuth.setEchoChar((char) 0);
						textFieldNewAuthConfirm.setEchoChar((char) 0);
					}
					else
					{
						textFieldNewAuth.setEchoChar('*');
						textFieldNewAuthConfirm.setEchoChar('*');
					}
				}
	        }
		}
	}
}
