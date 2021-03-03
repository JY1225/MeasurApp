package AppFrame.widgets;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

import Ben.JavaIO;
import FileManager.FileManager;
import NetworkCamera.SDK_Hikvision.Camera_Hikvision;
import NetworkCamera.NetworkCameraConst;
import NetworkCamera.SDK_DaHua.Camera_DaHua;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class CameraSelector
{
	private static boolean bFrontCapture = false;
	private static boolean bBackCapture = false;

	private int _sensorID;
	private EventListener eventListener;

	public CameraSelector(JPanel panel, int gridX, int gridY, String labelStr, int sensorID)
	{
		//===================================================================
		eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//===================================================================
		_sensorID = sensorID;

		cameraInit();

		//===================================================================
		gridX = 0;
		JLabel labelCamera = new JLabel(labelStr);
		GridBagConstraints gbc_labelCamera = new GridBagConstraints();
		gbc_labelCamera.insets = new Insets(0, 0, 5, 5);
		gbc_labelCamera.gridx = gridX;
		gbc_labelCamera.gridy = gridY;
		panel.add(labelCamera, gbc_labelCamera);
		gridX++;

		new SensorIPWidget(sensorID, panel, gridX, gridY);
		gridX += 6;

		//===================================================================
		gridY++;
		gridX = 1;

		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "用户名", "", true, true, true, LMSConstValue.sNvramCameraUserName[sensorID], sensorID, -1);
		gridX += 2;

		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "密码", "", true, true, true, LMSConstValue.sNvramCameraPassword[sensorID], sensorID, -1);
		gridX += 2;

		new JSettingLabelTextField(panel, gridX, gridY, 1, 1, "拍照延时", "秒", true, false, true, LMSConstValue.iNvramCameraCaptureDelaySecond[sensorID], sensorID, -1);
		gridX += 2;

		JButton buttonCapture = new JButton("抓图");
		GridBagConstraints gbc_buttonCapture = new GridBagConstraints();
		gbc_buttonCapture.insets = new Insets(0, 0, 5, 5);
		gbc_buttonCapture.gridx = gridX;
		gbc_buttonCapture.gridy = gridY;
		panel.add(buttonCapture, gbc_buttonCapture);
		buttonCapture.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					capturePics();
				}
				catch (UnsatisfiedLinkError e)
				{
					LMSLog.exceptionDialog("摄像头异常", e);
				}
				catch (NoClassDefFoundError e)
				{
					LMSLog.exceptionDialog("摄像头异常", e);
				}
				catch (Exception e)
				{
					LMSLog.exception(1, e);
				}
			}
		});
		gridY++;

		/**
		 * gridX = 0;
		 * GridBagConstraints gbc_realplay = new GridBagConstraints();
		 * gbc_realplay.insets = new Insets(0, 0, 5, 5);
		 * gbc_realplay.gridx = gridX;
		 * gbc_realplay.gridy = gridY;
		 * panel.add(realplay, gbc_realplay);
		 * gridY++;
		 */
	}

	private void cameraInit()
	{
		try
		{
			if (LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.HK_DS))
			{
				if (_sensorID == LMSConstValue.FRONT_CAMERA_ID)
					Camera_Hikvision.frontCameraBackgroundInit();
				else if (_sensorID == LMSConstValue.BACK_CAMERA_ID)
					Camera_Hikvision.backCameraBackgroundInit();
			}
			else if (LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.DH_IPC))
			{
				if (_sensorID == LMSConstValue.FRONT_CAMERA_ID)
					Camera_DaHua.frontCamera.frontCameraBackgroundInit();
				else if (_sensorID == LMSConstValue.BACK_CAMERA_ID)
					Camera_DaHua.backCamera.backCameraBackgroundInit();
			}
		}
		catch (UnsatisfiedLinkError e)
		{
			LMSLog.exceptionDialog("摄像头异常", e);
		}
		catch (NoClassDefFoundError e)
		{
			LMSLog.exceptionDialog("摄像头异常", e);
		}
		catch (Exception e)
		{
			LMSLog.exception(_sensorID, e);
		}
	}

	private void capturePics()
	{
		if (_sensorID == LMSConstValue.FRONT_CAMERA_ID)
		{
			LMSLog.d("CAPTURE", "FRONT_CAMERA_ID");
			if (LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.HK_DS))
			{
				bFrontCapture = Camera_Hikvision.capturePics(NetworkCameraConst.lUserIDCarIn, NetworkCameraConst.sImageTempSavePath, NetworkCameraConst.sFileNameCarIn);
			}
			else if (LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.DH_IPC))
			{
				bFrontCapture = Camera_DaHua.frontCamera.capturePics(NetworkCameraConst.lUserIDCarIn, NetworkCameraConst.sImageTempSavePath, NetworkCameraConst.sFileNameCarIn);
			}
		}
		else if (_sensorID == LMSConstValue.BACK_CAMERA_ID)
		{
			LMSLog.d("CAPTURE", "BACK_CAMERA_ID");
			if (LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.HK_DS))
			{
				bBackCapture = Camera_Hikvision.capturePics(NetworkCameraConst.lUserIDCarOut, NetworkCameraConst.sImageTempSavePath, NetworkCameraConst.sFileNameCarOut);
			}
			else if (LMSConstValue.sensorType[_sensorID].key.equals(LMSConstValue.SensorType.DH_IPC))
			{
				bBackCapture = Camera_DaHua.backCamera.capturePics(NetworkCameraConst.lUserIDCarOut, NetworkCameraConst.sImageTempSavePath, NetworkCameraConst.sFileNameCarOut);
			}
		}

		if (bFrontCapture && bBackCapture)
		{
			bFrontCapture = false;
			bBackCapture = false;
			new Timer().schedule(new TimerTask()
			{
				public void run()
				{
					String pathIn1 = NetworkCameraConst.sImageTempSavePath + NetworkCameraConst.sFileNameCarIn + ".jpg";
					String pathOut1 = NetworkCameraConst.sImageSavePath + NetworkCameraConst.sFileNameCarIn + ".jpg";
					String pathIn2 = NetworkCameraConst.sImageTempSavePath + NetworkCameraConst.sFileNameCarOut + ".jpg";
					String pathOut2 = NetworkCameraConst.sImageSavePath + NetworkCameraConst.sFileNameCarOut + ".jpg";
					FileManager.fileCopy(pathIn1, pathOut1);
					FileManager.fileCopy(pathIn2, pathOut2);

					//通知显示图片
					LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SHOW_PICS);
				}
			}, 2000);

		}

	}

	class EventListener implements LMSEventListener
	{
		public void lmsTransferEvent(LMSEvent event)
		{
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

			int sensorID = 0;

			if (eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
			{
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID);
			}

			if (eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT)))
			{
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM);

				if (sensorID == _sensorID && (nvram.equals(LMSConstValue.nvramSensorType) || nvram.equals(LMSConstValue.nvramSensorIP) || nvram.equals(LMSConstValue.nvramSensorPort)))
				{
					cameraInit();
				}
			}
			else if (eventType != null && eventType.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT))
			{
				if (eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
				{
					int carState = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE);

					try
					{
						if (_sensorID == LMSConstValue.FRONT_CAMERA_ID && carState == LMSConstValue.enumCarState.CAR_COMMING.ordinal())
						{
							int delay = LMSConstValue.iNvramCameraCaptureDelaySecond[_sensorID].iValue;
							if (delay == 0)
							{
								capturePics();
							}
							else
							{
								captureDelay(delay * 1000);
							}
						}
						else if (_sensorID == LMSConstValue.BACK_CAMERA_ID && carState == LMSConstValue.enumCarState.CAR_OUTING.ordinal())
						{
							int delay = LMSConstValue.iNvramCameraCaptureDelaySecond[_sensorID].iValue;
							if (delay == 0)
							{
								capturePics();
							}
							else
							{
								captureDelay(delay * 1000);
							}
						}

					}
					catch (UnsatisfiedLinkError e)
					{
						LMSLog.exceptionDialog(null, e);
					}
					catch (NoClassDefFoundError e)
					{
						LMSLog.exceptionDialog("摄像头异常", e);
					}
					catch (Exception e)
					{
						LMSLog.exception(sensorID, e);
					}
				}
			}
		}
	}

	private void captureDelay(long _lTrigTime)
	{
		new Timer().schedule(new TimerTask()
		{
			public void run()
			{
				capturePics();
			}
		}, _lTrigTime);
	}
}
