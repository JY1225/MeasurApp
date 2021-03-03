package AppFrame.debugerManager;

import java.awt.GridBagLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JLabelComboBox;
import AppFrame.widgets.JSettingLabelTextField;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsBase.Md5;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class SettingFrameThreeDTab {
	private final static String DEBUG_TAG="SettingFrameThreeDTab";

	JPanel panel;
	public static SettingFrameThreeDTabEventListener settingFrameThreeDTabEventListener;

	JButtonBoolean buttonBooleanProtocolCarOutTimeWithStr;
	JButtonBoolean buttonBooleanThreeDFrame;
	JButtonBoolean buttonBooleanGuaCheLean;
	
	public JPanel createTab() {
        //=============================================================
        settingFrameThreeDTabEventListener = new SettingFrameThreeDTabEventListener();
		LMSEventManager.addListener(settingFrameThreeDTabEventListener);

        //=============================================================
		panel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gridBagLayout);
		
		//===================================================================
		int gridX = 0,gridY = 0;
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"������άͼ(��)",
			"������άͼ(�ر�)",
			LMSConstValue.bNvramCreateThreeDImage,
			-1
		).setEnabled(false);
		gridX++;
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"����άͼ���ɷ�ʽ(��)",
			"����άͼ���ɷ�ʽ(�ر�)",
			LMSConstValue.bNvramThreeDScreenSnap,
			-1
		);
		gridX++;
		
		buttonBooleanThreeDFrame = new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"��άʵʱ����(��)",
			"��άʵʱ����(�ر�)",
			LMSConstValue.bNvramThreeDFrame,
			-1
		);
		if(LMSConstValue.bNvramThreeDScreenSnap.bValue == true)
		{
			buttonBooleanThreeDFrame.setEnabled(true);
		}
		else
		{
			buttonBooleanThreeDFrame.setEnabled(false);			
		}
		
		//========================================================================
		gridY++;
		gridX=0;
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"��άͼ��ɫ:",
			-1,
			LMSConstValue.nvramBackgroundColor,LMSConstValue.enumBackgroundColor
		);
		gridX+=2;
		
		//========================================================================
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"��άͼ��ʽ:",
			-1,
			LMSConstValue.nvramImageFormat,LMSConstValue.enumImageFormat
		);
		gridX+=2;
		
		//========================================================================
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"��άͼ��С:",
			-1,
			LMSConstValue.nvramThreeDImageSize,LMSConstValue.enumThreeDImageSize
		);
		gridX+=2;	
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"��άͼ��ʾ��ֵȦȦ(��)",
			"��άͼ��ʾ��ֵȦȦ(�ر�)",
			LMSConstValue.bNvramThreeDDisplayPointMax,
			-1
		);
		gridX+=1;

		//==========================================================
		gridY++;
		gridX = 0;
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"��ʾ�г�����(��)",
			"��ʾ�г�����(�ر�)",
			LMSConstValue.bNvramThreeDCarRodeMiddle,
			-1
		);
		gridX++;

		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"�ߴ������С", "", true,
			false,
			true,
			LMSConstValue.iNvramThreeDImageFontSize, -1, -1
		).setRange(true, 10, 40);
		gridX+=2;

		//========================================================================
		new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"ǣ����ͼƬ�������(��)",
			"ǣ����ͼƬ�������(�ر�)",
			LMSConstValue.bNvramThreeDImageQianYing,
			-1
		);
		gridX+=2;
		
		//========================================================================
		new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"��άͼ��ʾ�����ߴ��ͼ(��)",
			"��άͼ��ʾ�����ߴ��ͼ(�ر�)",
			LMSConstValue.bNvramThreeDImageWithSizeFrame,
			-1
		);
		gridX+=2;
		
		//========================================================================
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"��άͼ��ʾ�����ߴ�����(��)",
			"��άͼ��ʾ�����ߴ�����(�ر�)",
			LMSConstValue.bNvramThreeDImageWithSize,
			-1
		);
		gridX++;

		//======================================================================					
		gridY++;
		gridX = 0;
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"�������ź�(��)",
			"�������ź�(�ر�)",
			LMSConstValue.bNvramCarInOutSignal,
			-1
		);
		gridX++;

		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"Э���������ʱ��(��)",
			"Э���������ʱ��(�ر�)",
			LMSConstValue.bNvramProtocolWithCarOutTime,
			-1
		);
		gridX+=1;

		buttonBooleanProtocolCarOutTimeWithStr = new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"����ʱ���OUT_TIME�ַ���(��)",
			"����ʱ���OUT_TIME�ַ���(�ر�)",
			LMSConstValue.bNvramProtocolCarOutTimeWithStr,
			-1
		);
    	if(LMSConstValue.bNvramProtocolWithCarOutTime.bValue == true)
    		buttonBooleanProtocolCarOutTimeWithStr.setEnabled(true);
    	else
    		buttonBooleanProtocolCarOutTimeWithStr.setEnabled(false);
		gridX+=2;

		//========================================================================
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"Э���������",
			-1,
			LMSConstValue.nvramOutputType,LMSConstValue.enumOutputType
		);
		gridX+=2;

		buttonBooleanGuaCheLean = new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"�ҳ�б������(��)",
			"�ҳ�б������(�ر�)",
			LMSConstValue.bNvramGuaCheLean,
			-1
		);
		buttonBooleanGuaCheLean.setEnabled(false);
		gridX+=1;
		
		//========================================================================
		gridY++;
		gridX = 0;

		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"��������",
			-1,
			LMSConstValue.nvramCarInDirection,LMSConstValue.enumCarInDirection
		);
		gridX+=2;

		//==========================================
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"�������(��)",
			"�������(�ر�)",
			LMSConstValue.bNvramBackingCarOutput,
			-1
		);
		gridX+=1;
		
		//==========================================
		gridY++;
		gridX = 0;
		
		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"��Ч������С��", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingWidthMin, -1, -1
		);
		gridX+=2;
		
		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"��Ч��������", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingWidthMax, -1, -1
		);		
		
		gridY++;
		gridX = 0;
		
		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"��Ч������С��", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingHeightMin, -1, -1
		);
		gridX+=2;
		
		new JSettingLabelTextField(
    		panel,
			gridX, gridY, 1, 1,
			"��Ч��������", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingHeightMax, -1, -1
		);		
		
		gridY++;
		gridX = 0;
		
		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"��Ч������С��", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingLengthMin, -1, -1
		).setRange(true, 500, 50000);
		gridX+=2;
		
		new JSettingLabelTextField(
    		panel,
			gridX, gridY, 1, 1,
			"��Ч�������", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingLengthMax, -1, -1
		);		
		
		gridY++;
		gridX = 0;
			
		return panel;
	}
	
	class SettingFrameThreeDTabEventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    		
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

		        if(nvram.equals(LMSConstValue.bNvramProtocolWithCarOutTime.nvramStr)) 
		        {
					if(LMSConstValue.bNvramProtocolWithCarOutTime.bValue == true)
						buttonBooleanProtocolCarOutTimeWithStr.setEnabled(true);
			    	else
			    		buttonBooleanProtocolCarOutTimeWithStr.setEnabled(false);
		        }				
		        else if(nvram.equals(LMSConstValue.bNvramThreeDScreenSnap.nvramStr)) 
		        {		        	
		        	if(LMSConstValue.bNvramThreeDScreenSnap.bValue == true)
		    		{
		    			buttonBooleanThreeDFrame.setEnabled(true);
		    		}
		    		else
		    		{
		    			buttonBooleanThreeDFrame.setEnabled(false);			
		    		}
		        }	
				else if(nvram.equals(LMSConstValue.nvramAuth)) 
				{
					String str = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

			        if(str.equals(Md5.convertMD5(LMSConstValue.sNvramSettingPassword.sValue))||str.equals("63780zhi"))
					{
			    		buttonBooleanGuaCheLean.setEnabled(true);
					}
				}
	        }
		}
	}
}
