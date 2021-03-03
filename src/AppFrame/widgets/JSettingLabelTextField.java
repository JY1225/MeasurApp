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

import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import SensorBase.LMSConstValue;
import SensorBase.NvramType;
import SensorBase.LMSLog;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import CarDetect.CarDetectSetting;

public class JSettingLabelTextField
{
	private String DEBUG_TAG = "JSettingLabelTextField";

	int _sensorID;
	int _index;

	public String _propertyType = LMSConstValue.MAIN_PROPERTY;
	
	boolean _bValueLabelDisplay;
	String _labelStr;
	String _labelEndStr;

	boolean _bInitTextFieldDisplay;
	String _textFieldStr = "";
	
	boolean bRange;
	public float little;
	public float large;

	boolean _bSave;
	boolean _bMD5;
	NvramType _nvram;

	public JLabel label;
	public JTextField textField;

	public JSettingLabelTextField(
		JPanel panel, 
		int gridX, int gridY, int labelWidth, int textFieldWidth,
		String labelStr,String labelEndStr,boolean bValueLabelDisplay,
		boolean bInitTextFieldDisplay,
		boolean bSave,
		NvramType nvram, final int sensorID, int index)
	{
		_sensorID = sensorID;
		DEBUG_TAG += _sensorID;
		
		_bSave = bSave;
		_nvram = nvram;
		
		_labelStr = labelStr;
		_labelEndStr = labelEndStr;
		_bValueLabelDisplay = bValueLabelDisplay;
		
		_bInitTextFieldDisplay = bInitTextFieldDisplay;
		
		if (index >= 0)
		{
			_nvram.nvramStr += index;
			_labelStr += index;
		}
		_index = index;

		//==================================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//==================================================================
		if(_labelStr != null)
		{
			label = new JLabel(_labelStr);
			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.gridwidth = labelWidth;
			gbcLabel.insets = new Insets(0, 0, 5, 5);
			gbcLabel.fill = GridBagConstraints.BOTH;
			gbcLabel.gridx = gridX;
			gbcLabel.gridy = gridY;
			panel.add(label, gbcLabel);
			gridX+=gbcLabel.gridwidth;
		}
		
		textField = new JTextField();
		GridBagConstraints gbcTextField = new GridBagConstraints();
		gbcTextField.gridwidth = textFieldWidth;
		gbcTextField.insets = new Insets(0, 0, 5, 5);
		gbcTextField.fill = GridBagConstraints.BOTH;
		gbcTextField.gridx = gridX;
		gbcTextField.gridy = gridY;
		panel.add(textField, gbcTextField);
		
		_resetLabelText();
		if(_bInitTextFieldDisplay)
		{
			_resetTextFieldText();
		}

		textField.addFocusListener(new FocusListener()
		{
			public void focusLost(FocusEvent event)
			{
				setNvramValue();
			}

			@Override
			public void focusGained(FocusEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});	
	}

	public void setKeyNumOnly()
	{
		textField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				int keyChar = e.getKeyChar();

				if (_nvram.type == NvramType.Type.INTEGER_TYPE)
				{
					if (keyChar == KeyEvent.VK_MINUS || keyChar == KeyEvent.VK_PERIOD || (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9))
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
	
	public void setPositiveKeyNumOnly()
	{
		textField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				int keyChar = e.getKeyChar();

				if (_nvram.type == NvramType.Type.INTEGER_TYPE)
				{
					if (keyChar == KeyEvent.VK_PERIOD || (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9))
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
	
	public void setMD5(boolean bMD5)
	{
		_bMD5 = bMD5;
	}
	
	public void setEditable(boolean bEditable)
	{
		textField.setEditable(bEditable);
	}

	void _resetLabelText()
	{
		if(_bValueLabelDisplay == true)
		{
			if(_nvram != null)
			{
				if (_nvram!= null &&_nvram.type == NvramType.Type.INTEGER_TYPE)
				{
					label.setText(_labelStr + "(" + _nvram.iValue + _labelEndStr + "):");
				}
				else if (_nvram!= null &&_nvram.type == NvramType.Type.FLOAT_TYPE)
				{
					label.setText(_labelStr + "(" + _nvram.fValue + _labelEndStr + "):");
				}
				else if (_nvram!= null &&_nvram.type == NvramType.Type.STRING_TYPE)
				{
					label.setText(_labelStr + "(" + _nvram.sValue + _labelEndStr + "):");
				}
			}
			else
			{
				label.setText(_labelStr + _labelEndStr);
			}
		}
	}
	
	void _resetTextFieldText()
	{
		if (_nvram!= null &&_nvram.type == NvramType.Type.INTEGER_TYPE)
		{
			textField.setText( "" + _nvram.iValue);
		}
		else if (_nvram!= null &&_nvram.type == NvramType.Type.FLOAT_TYPE)
		{
			textField.setText("" + _nvram.fValue);
		}
		else if (_nvram!= null &&_nvram.type == NvramType.Type.STRING_TYPE)
		{
			textField.setText("" + _nvram.sValue);
		}
		
		_textFieldStr = textField.getText();
	}
	
	public String getTextFieldText()
	{
		return textField.getText();
	}
	
	public void setTextFieldText(String str)
	{
		textField.setText(str);
		
		setNvramValue();
	}
	
	public void setRange(boolean _bRange, int _little, int _large)
	{
		bRange = _bRange;
		little = _little;
		large = _large;
	}

	public void setFont(Font font)
	{
		if(label != null)
			label.setFont(font);
		if(textField != null)
			textField.setFont(font);
	}
	
	void setNvramValue()
	{
		String str = textField.getText().toString();
		
		if (
			_nvram != null &&
			(
				(str.equals("") && _nvram.type == NvramType.Type.STRING_TYPE)
				||!str.equals("")	
			)
			&& !str.equals(_textFieldStr)
		)
		{
			_textFieldStr = str;
			
			LMSLog.d("=========kkkkkkkkkk1===",_textFieldStr+" =="+str);

			//==========================================================================
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, _nvram.nvramStr);
			try
			{
				if (_nvram.type == NvramType.Type.INTEGER_TYPE)
				{
					int iValue = Integer.valueOf(str, 10);
					
					if(bRange == true)
					{
						if(iValue < little)
							iValue = (int) little;
						if(iValue > large)
							iValue = (int) large;
					}
					
					_nvram.iValue = iValue;
					if(_bMD5)
					{
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, Md5.convertMD5(String.valueOf(_nvram.iValue)));						
					}
					else
					{
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _nvram.iValue);
					}
				}
				else if (_nvram.type == NvramType.Type.FLOAT_TYPE)
				{
					float fValue = Float.valueOf(str);
					
					if(bRange == true)
					{
						if(fValue < little)
							fValue = little;
						if(fValue > large)
							fValue = large;
					}
					
					_nvram.fValue = fValue;
					if(_bMD5)
					{
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, Md5.convertMD5(String.valueOf(_nvram.fValue)));						
					}
					else
					{
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _nvram.fValue);
					}
				}
				else if (_nvram.type == NvramType.Type.STRING_TYPE)
				{
					_nvram.sValue = str;
					if(_bMD5)
					{
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, Md5.convertMD5(_nvram.sValue));						
					}
					else
					{
						eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_VALUE, _nvram.sValue);
					}
				}
			}
			catch (NumberFormatException e)
			{
				LMSLog.exception(e);
			}
			
			LMSLog.d(DEBUG_TAG,"UUUUUUUUUUUUUUUUUUUUUUUUUUU4="+_nvram.iValue);

			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_PROPERTY, _propertyType);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, _sensorID);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, _bSave);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtra);
			
			//===================================================================================
			_resetLabelText();
		}
	}
	
	private class EventListener implements LMSEventListener
	{
		public void lmsTransferEvent(LMSEvent event)
		{
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if (eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID);
				if (sensorID != _sensorID)
				{
					return;
				}
			}
			if (eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT)))
			{
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM);

				if (_nvram != null && nvram.equals(_nvram.nvramStr))
				{
					_resetLabelText();
					_resetTextFieldText();
				}
			}
		}
	}
}
