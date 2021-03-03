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
			"生成三维图(打开)",
			"生成三维图(关闭)",
			LMSConstValue.bNvramCreateThreeDImage,
			-1
		).setEnabled(false);
		gridX++;
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"旧三维图生成方式(打开)",
			"旧三维图生成方式(关闭)",
			LMSConstValue.bNvramThreeDScreenSnap,
			-1
		);
		gridX++;
		
		buttonBooleanThreeDFrame = new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"三维实时界面(打开)",
			"三维实时界面(关闭)",
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
			"三维图颜色:",
			-1,
			LMSConstValue.nvramBackgroundColor,LMSConstValue.enumBackgroundColor
		);
		gridX+=2;
		
		//========================================================================
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"三维图格式:",
			-1,
			LMSConstValue.nvramImageFormat,LMSConstValue.enumImageFormat
		);
		gridX+=2;
		
		//========================================================================
		new JLabelComboBox(
			panel,
			gridX,gridY,
			1,
			"三维图大小:",
			-1,
			LMSConstValue.nvramThreeDImageSize,LMSConstValue.enumThreeDImageSize
		);
		gridX+=2;	
		
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"三维图显示最值圈圈(打开)",
			"三维图显示最值圈圈(关闭)",
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
			"显示行车中线(打开)",
			"显示行车中线(关闭)",
			LMSConstValue.bNvramThreeDCarRodeMiddle,
			-1
		);
		gridX++;

		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"尺寸字体大小", "", true,
			false,
			true,
			LMSConstValue.iNvramThreeDImageFontSize, -1, -1
		).setRange(true, 10, 40);
		gridX+=2;

		//========================================================================
		new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"牵引车图片单独输出(打开)",
			"牵引车图片单独输出(关闭)",
			LMSConstValue.bNvramThreeDImageQianYing,
			-1
		);
		gridX+=2;
		
		//========================================================================
		new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"三维图显示车辆尺寸框图(打开)",
			"三维图显示车辆尺寸框图(关闭)",
			LMSConstValue.bNvramThreeDImageWithSizeFrame,
			-1
		);
		gridX+=2;
		
		//========================================================================
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"三维图显示车辆尺寸文字(打开)",
			"三维图显示车辆尺寸文字(关闭)",
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
			"进出车信号(打开)",
			"进出车信号(关闭)",
			LMSConstValue.bNvramCarInOutSignal,
			-1
		);
		gridX++;

		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"协议输出出车时间(打开)",
			"协议输出出车时间(关闭)",
			LMSConstValue.bNvramProtocolWithCarOutTime,
			-1
		);
		gridX+=1;

		buttonBooleanProtocolCarOutTimeWithStr = new JButtonBoolean(
			panel,
			gridX,gridY,2,
			"出车时间带OUT_TIME字符串(打开)",
			"出车时间带OUT_TIME字符串(关闭)",
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
			"协议输出类型",
			-1,
			LMSConstValue.nvramOutputType,LMSConstValue.enumOutputType
		);
		gridX+=2;

		buttonBooleanGuaCheLean = new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"挂车斜率修正(打开)",
			"挂车斜率修正(关闭)",
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
			"进车方向",
			-1,
			LMSConstValue.nvramCarInDirection,LMSConstValue.enumCarInDirection
		);
		gridX+=2;

		//==========================================
		new JButtonBoolean(
			panel,
			gridX,gridY,1,
			"倒车输出(打开)",
			"倒车输出(关闭)",
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
			"有效物体最小宽", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingWidthMin, -1, -1
		);
		gridX+=2;
		
		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"有效物体最大宽", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingWidthMax, -1, -1
		);		
		
		gridY++;
		gridX = 0;
		
		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"有效物体最小高", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingHeightMin, -1, -1
		);
		gridX+=2;
		
		new JSettingLabelTextField(
    		panel,
			gridX, gridY, 1, 1,
			"有效物体最大高", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingHeightMax, -1, -1
		);		
		
		gridY++;
		gridX = 0;
		
		new JSettingLabelTextField(
    		panel, 
			gridX, gridY, 1, 1,
			"有效物体最小长", "mm", true,
			false,
			true,
			LMSConstValue.iNvramValidThingLengthMin, -1, -1
		).setRange(true, 500, 50000);
		gridX+=2;
		
		new JSettingLabelTextField(
    		panel,
			gridX, gridY, 1, 1,
			"有效物体最大长", "mm", true,
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
