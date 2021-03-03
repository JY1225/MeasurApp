package AppFrame.widgets;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SensorBase.NvramType;

public class JLabelTextField {
	private String DEBUG_TAG="JLabelTextField";

	String _labelStr;

	NvramType.Type _varType;

	JLabel label;
	JTextField textField;
	
	public JLabelTextField(
		JPanel panel,
		int gridX,int gridY,
		int labelWidth,int textFieldWidth,
		String labelStr,
		NvramType.Type varType
	)
	{
		_varType = varType;
		_labelStr = labelStr;

		//==================================================================
		label = new JLabel(_labelStr);
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.insets = new Insets(0, 0, 5, 5);
		gbcLabel.fill = GridBagConstraints.BOTH;
		gbcLabel.gridwidth = labelWidth;
		gbcLabel.gridx = gridX;
		gbcLabel.gridy = gridY;
		panel.add(label, gbcLabel);
		gridX++;

		textField = new JTextField();
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.insets = new Insets(0, 0, 5, 5);
		gbcTextField.fill = GridBagConstraints.BOTH;
		gbcTextField.gridwidth = textFieldWidth;
		gbcTextField.gridx = gridX;
		gbcTextField.gridy = gridY;
		panel.add(textField,gbcTextField);
		textField.addFocusListener(new FocusListener(){			
			public void focusLost(FocusEvent event) {
				
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}		
		});
		textField.addKeyListener(new KeyAdapter(){  
			public void keyTyped(KeyEvent e) {  
				int keyChar = e.getKeyChar();                 
			
				if(_varType == NvramType.Type.INTEGER_TYPE)
				{
					if(keyChar == KeyEvent.VK_MINUS||keyChar == KeyEvent.VK_PERIOD||(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9))
					{  									

					}
					else
					{  
						e.consume(); //关键，屏蔽掉非法输入   
						
					}  
				}
		     }  
		 });  
	}
	
	public void setFont(Font font)
	{
		label.setFont(font);
		textField.setFont(font);
	}
	
	public void setTextFieldText(String str)
	{
		textField.setText(str);
	}
	
	public String getTextFieldText()
	{
		return textField.getText();
	}
}
