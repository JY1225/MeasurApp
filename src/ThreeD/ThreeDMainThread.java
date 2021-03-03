package ThreeD;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.jogamp.common.nio.Buffers;

import layer.algorithmLayer.Contour;
import layer.dataLayer.RadarCalibration;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppBase.appBase.CarTypeAdapter;
import AppFrame.carDetect.CarDetectFrameMainTab;
import AppFrame.debugerManager.SettingFrameDebug.GeneratingDialog;
import CarDetect.CarDetectSetting;
import FileManager.FileManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import ThreeD.ThreeDGIPCSingle.EventListener;

public class ThreeDMainThread implements Runnable{
	private final static String DEBUG_TAG="ThreeDMainThread";

	Thread myThread = new Thread(this); 

	public ThreeDGIPCSingle threeDAnimator = new ThreeDGIPCSingle(false);

	ThreeDGIPC threeDGIPC = new ThreeDGIPC();

	Contour contour;

	public static FloatBuffer floatBufferTwoSide;
	public static FloatBuffer floatBufferSingleSide[] = new FloatBuffer[LMSConstValue.LMS_WH_SENSOR_NUM];	

    final int I_SINGLE_SIDE_NUM_MAX = 100*10000;
	void threeDBufferInit()
	{
		//======================================================
		//nio不会进行垃圾回收
		if(floatBufferTwoSide == null)
			floatBufferTwoSide = Buffers.newDirectFloatBuffer(2*I_SINGLE_SIDE_NUM_MAX*3*2);
		for(int sensorID=0;sensorID<LMSConstValue.LMS_WH_SENSOR_NUM;sensorID++)
		{
			if(floatBufferSingleSide[sensorID] == null)
				floatBufferSingleSide[sensorID] = Buffers.newDirectFloatBuffer(I_SINGLE_SIDE_NUM_MAX*3*2);			
		}		
	}
	
	public void start() { 
		FileManager.removeFolderFileByDate(Calendar.DAY_OF_YEAR, -14, "image"); //2周前

		threeDBufferInit();

        myThread.start();                                //主线程开始         
        
		//===============================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	} 

	void threeDCarOutRegenate()
	{
		new ThreeDCarAlgorithm().threeDCarAlgorithm(contour);
		new ThreeDCarAlgorithm().threeDCarOutCompensate(contour);		
	}
	
	void threeDRegenerate()
	{
		if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
		{							
			new ThreeDGIPCSingle(true).threeDOneContour(contour,false);
			
			if(LMSConstValue.isMyMachine())
			{
				threeDAnimator.threeDOneContour(contour,false);
			}
		}
		else
		{							
			threeDGIPC.threeDOneContour(contour,false);
		}						
	}
	
	@Override
	public void run() {
        while (true) { 		
			LMSLog.d(DEBUG_TAG, "ThreeDMainThread.......................");
	
			//============================================================
			try {
				contour = CarDetectSetting.carDetectSetting.contourList.take();
				
				LMSLog.d(DEBUG_TAG, "contourList.take()");

				threeDCarOutRegenate();
				threeDRegenerate();
				new ThreeDCarAlgorithm().threeDFinishSendCar(contour,false);
			} catch (Exception e) {
	       		LMSLog.exceptionDialog(null, e);
			}
        }
	}	
	
	JDialog dialog;
	public class GeneratingDialog extends Thread
	{
		public GeneratingDialog()
		{
			JOptionPane op = new JOptionPane("测量数据重新计算中",JOptionPane.INFORMATION_MESSAGE);
	        
			dialog = op.createDialog("测量数据重新计算中");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setAlwaysOnTop(true);
			dialog.setModal(false);
			dialog.setVisible(true);
			dialog.show();
		}
		
		public void run()
		{
		}
	}
	
	class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

				if(contour != null)
				{
					if(nvram.equals(LMSConstValue.iNvramLightCurtianLongDistance.nvramStr))
					{					
						if(LMSConstValue.bNvramCreateThreeDImage.bValue)
						{			
							if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
							{
								new ThreeDGIPCSingle(true).threeDOneContour(contour,true);
							}
							else
							{
								threeDGIPC.threeDOneContour(contour,true);
							}
						}
					}
					else if(nvram.equals(LMSConstValue.bNvramThreeDDisplayRadar0.nvramStr)
						||nvram.equals(LMSConstValue.bNvramThreeDDisplayRadar1.nvramStr)
						||nvram.equals(LMSConstValue.bNvramThreeDDisplayLightCurtain.nvramStr)
						||nvram.equals(LMSConstValue.bNvramThreeDDisplayFilterIn.nvramStr)
						||nvram.equals(LMSConstValue.bNvramThreeDDisplayFilterOut1.nvramStr)
						||nvram.equals(LMSConstValue.bNvramThreeDDisplayFilterOut2.nvramStr)
						||nvram.equals(LMSConstValue.iNvramXRangeMin.nvramStr)
						||nvram.equals(LMSConstValue.iNvramXRangeMax.nvramStr)
						||nvram.equals(LMSConstValue.iNvramYRangeMin.nvramStr)
						||nvram.equals(LMSConstValue.iNvramYRangeMax.nvramStr)
						||nvram.equals(LMSConstValue.iNvramZRangeMin.nvramStr)
						||nvram.equals(LMSConstValue.iNvramZRangeMax.nvramStr)
						||nvram.equals(LMSConstValue.bNvramThreeDDisplayMiddle.nvramStr)
						||nvram.equals(LMSConstValue.iNvramLWDistance2.nvramStr)
						||nvram.equals(LMSConstValue.iNvramLightCurtianLongDistance.nvramStr)
						||nvram.equals(LMSConstValue.nvramEnumThreeDMaxPointType)
					)
					{
						threeDRegenerate();
					}
					else if(
						nvram.equals(CarTypeAdapter.nvramCarEnumType)
						||nvram.equals(CarTypeAdapter.bNvramLanBanDetect.nvramStr)
						||nvram.equals(CarTypeAdapter.bNvramFilterCheLan.nvramStr)
						||nvram.equals(CarTypeAdapter.bNvramFilterEndGas.nvramStr)
						||nvram.equals(LMSConstValue.nvramEnumLengthFilterType)
						||nvram.equals(LMSConstValue.nvramEnumWidthFilterType)
						||nvram.equals(LMSConstValue.nvramEnumHeightFilterType)
					)
					{
				        if(LMSConstValue.appType == LMSConstValue.AppType.CONTOUR_DETECTION)
				        {
							(new GeneratingDialog()).start();				
	
							threeDCarOutRegenate();
							threeDRegenerate();
							new ThreeDCarAlgorithm().threeDFinishSendCar(contour,true);
							
							dialog.dispose();
				        }
					}
					else if(nvram.equals(LMSConstValue.bNvramResultResend.nvramStr))
					{
						new ThreeDCarAlgorithm().threeDFinishSendCar(contour,false);
					}
				}
	        }
		}
	}
}
