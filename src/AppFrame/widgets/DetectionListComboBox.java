package AppFrame.widgets;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppFrame.contourDetection.ContourDetectionDataBaseConst;
import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import CustomerProtocol.DetectionVehicle;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class DetectionListComboBox extends JComboBox{
	public static String nvramDetectionList = "nvram_DetectionList";

	ComboBoxRenderer renderer = new ComboBoxRenderer();
	ContourDetectionDataBaseConst.MainPanelCarType _carType;
	
	public DetectionListComboBox(ContourDetectionDataBaseConst.MainPanelCarType carType)
	{
		_carType = carType;

		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
		
		setRenderer(renderer);

		//===================================================================
		DetectionVehicle.detectionVehicleList.clear();
		generateComboBox();
	}
	
	void generateComboBox()
	{
		try { 				
			removeAllItems();
			
			 for (Iterator it = DetectionVehicle.detectionVehicleList.iterator(); it.hasNext();) 
			{
				 DetectionVehicle detectionVehicle = (DetectionVehicle) it.next(); 
	        	
				 addItem(detectionVehicle);  
			}
		}catch (Exception e) {
			LMSLog.exceptionDialog("检查仪异常", e);
		}
	}
	
	class ComboBoxRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) 
		{
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			try { 				
				DetectionVehicle detectionList = (DetectionVehicle) value;
				
				if(detectionList != null)
				{
					setText(detectionList.sVehicleNum);
				}
				else
				{
					setText("手动输入");					
				}
			}catch (Exception e) {
				LMSLog.exceptionDialog("检查仪异常", e);
			}
			return this;
		}
	}
			
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    		
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
				
				if(nvram.equals(nvramDetectionList)&&_carType.equals(ContourDetectionDataBaseConst.MainPanelCarType.GUACHE))
				{				
					generateComboBox();
					
					if(DetectionVehicle.bAutoRefresh == false)
					{
						LMSLog.warningDialog("待检队列刷新成功", "待检队列刷新成功,请在待检队列选择车辆");
					}
				}
	        }
		}
	}
}
