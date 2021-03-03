package AppFrame.widgets;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import AppBase.appBase.AppConst;
import SensorBase.LMSConstValue;
import SensorBase.LMSPlatform;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class OperatorComboBox extends JComboBox{
	ComboBoxRenderer renderer = new ComboBoxRenderer();

	Operator operator[] = new Operator[AppConst.MAX_OPERATOR];

	public OperatorComboBox()
	{
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
		
		setRenderer(renderer);

		//===================================================================
		for(int i=0;i<AppConst.MAX_OPERATOR;i++)
		{
			operator[i] = new Operator();
			operator[i].index = i;
			operator[i].sOperatorName = LMSPlatform.getStringPorperty(LMSConstValue.USER_PROPERTY,Operator.nvramOperatorName+i, "");	
			operator[i].sOperatorID = LMSPlatform.getStringPorperty(LMSConstValue.USER_PROPERTY,Operator.nvramOperatorID+i, "");				
		}
		generateComboBox();
	}
	
	class ComboBoxRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) 
		{
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			Operator operator = (Operator) value;
			
			if(operator != null)
			{
				setText(operator.sOperatorName);
			}
			
			return this;
		}
	}

	public void generateComboBox()
	{
		removeAllItems();
		for(int i=0;i<AppConst.MAX_OPERATOR;i++)
		{			
			String sName = operator[i].sOperatorName;
			if(sName != null && !sName.equals(""))
			{
				addItem(operator[i]);  
			}
		}
	}
	
	public void generateComboBoxFromServer(String name)
	{
		removeAllItems();

		Operator operator = new Operator();
		operator.index = 0;
		operator.sOperatorName = name;	
		operator.sOperatorID = "";				

		addItem(operator);  
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    		
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
				
				if(nvram.equals(Operator.nvramOperatorName)||nvram.equals(Operator.nvramOperatorID))
				{
					int index = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
					String value = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_VALUE); 

					if(nvram.equals(Operator.nvramOperatorName))
					{								
						operator[index].sOperatorName = value;						
					}
					else if(nvram.equals(Operator.nvramOperatorID))
					{								
						operator[index].sOperatorID = value;						
					}
					
					generateComboBox();
				}
	        }
		}
	}
}
