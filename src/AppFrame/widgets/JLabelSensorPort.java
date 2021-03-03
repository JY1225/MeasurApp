package AppFrame.widgets;

import javax.swing.JLabel;

import SensorBase.LMSConstValue;

public class JLabelSensorPort extends JLabel{
	private String DEBUG_TAG="JLabelSensorPort";

	private int sensorID;
	
	public JLabelSensorPort(int _sensorID)
	{
		sensorID = _sensorID;
		
		DEBUG_TAG += sensorID;
		
		resetText();
	}
	
	void resetText()
	{
		if(LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.UNKNOW))
		{
    		setText("ÍøÂç¶Ë¿Ú(ÎÞ)");	  
		}
		else if(LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.LMS1XX)
    		||LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.LMS511)
    		||LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.LMS511))
    	{
    		setText("ÍøÂç¶Ë¿Ú(2111)");	  
    	}
    	else if(LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.ZM10))
    	{
    		setText("ÍøÂç¶Ë¿Ú(502)");	  
    	}
    	else if(LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.PS_16I))
    	{
    		setText("ÍøÂç¶Ë¿Ú(505)");	  
    	}
    	else if(LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.HK_DS))
    	{
    		setText("ÍøÂç¶Ë¿Ú(8000)");	  
    	}
    	else if(LMSConstValue.sensorType[sensorID].key.equals(LMSConstValue.SensorType.DH_IPC))
    	{
    		setText("ÍøÂç¶Ë¿Ú(37777)");	  
    	}
    	else
    	{
        	setText("ÍøÂç¶Ë¿Ú("+LMSConstValue.SENSOR_PORT[sensorID]+")");	        		
    	}
	}
}
