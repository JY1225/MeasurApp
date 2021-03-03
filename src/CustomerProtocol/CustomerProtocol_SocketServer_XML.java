package CustomerProtocol;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import layer.physicLayer.PhysicLayerPacket;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import AppBase.appBase.CarTypeAdapter;
import AppFrame.widgets.DetectionListComboBox;
import http.WebService.XMLParse;

public class CustomerProtocol_SocketServer_XML {
	private final static String DEBUG_TAG="CustomerProtocol_XML";

	/*
<VehicleList>
<DetectStatus>�Ѽ��(δ���)344</DetectStatus>
<SerialNum>�����ˮ��344</SerialNum>
<VehicleNum>��A2222</VehicleNum>
<VehicleNumType>��������</VehicleNumType>
<VehicleType>��������344</VehicleType>
<VehicleBrand>Ʒ���ͺ�344</VehicleBrand>
<VehicleID>����ʶ�����344</VehicleID>
<MotorID>����������344</MotorID>
<NewOrOld>���ó�(ע�ᳵ)344</NewOrOld>
<OperatorName>����Ա����344</OperatorName>
<OperatorID>����ԱID344</OperatorID>
<OriginalCarLength>1233333334</OriginalCarLength>
<OriginalCarWidth>2556444</OriginalCarWidth>
<OriginalCarHeight>155556</OriginalCarHeight>
<OriginalLanbanHeight>566655</OriginalLanbanHeight>
<AxleNum>����344</AxleNum>
<AxleDistance1>���1344</AxleDistance1>
<AxleDistance2>���2344</AxleDistance2>
<AxleDistance3>���3344</AxleDistance3>
<AxleDistance4>���4344</AxleDistance4>
<AxleDistance5>���5344</AxleDistance5>
</VehicleList>
<VehicleList>
<DetectStatus>�Ѽ��(δ���)</DetectStatus >
<SerialNum>�����ˮ��</SerialNum>
<VehicleNum>��A333</VehicleNum>
<VehicleNumType>��������</VehicleNumType>
<VehicleType>��������</VehicleType>
<VehicleBrand>Ʒ���ͺ�</VehicleBrand>
<VehicleID>����ʶ�����</VehicleID>
<MotorID>����������</MotorID>
<NewOrOld>���ó�(ע�ᳵ)</NewOrOld>
<OperatorName>����Ա����</OperatorName>
<OperatorID>����ԱID</OperatorID>
<OriginalCarLength>12334</OriginalCarLength>
<OriginalCarWidth>2556</OriginalCarWidth>
<OriginalCarHeight>1556</OriginalCarHeight>
<OriginalLanbanHeight>555</OriginalLanbanHeight>
<AxleNum>����</AxleNum>
<AxleDistance1>���1</AxleDistance1>
<AxleDistance2>���2</AxleDistance2>
<AxleDistance3>���3</AxleDistance3>
<AxleDistance4>���4</AxleDistance4>
<AxleDistance5>���5</AxleDistance5>
</VehicleList>
	 */	
	String STR_BEGIN = "<VehicleList>";
	String STR_END = "</VehicleList>";
	public void parseSingleCarInformation(String str)
	{		
		DetectionVehicle.bAutoRefresh = true;
		DetectionVehicle.detectionVehicleList.clear();
		
		int i = 0;
		while(str.indexOf("</VehicleList>") != -1)
		{
			String resultSingleCar = new XMLParse().getXMLStrValue(str,"VehicleList");		
			if(resultSingleCar != null)
			{
				DetectionVehicle detectionVehicle = new DetectionVehicle();

				detectionVehicle.index = ++i;

				CustomerProtocol_XML_MINE.XML_parse(resultSingleCar,detectionVehicle);
				
				LMSLog.d(DEBUG_TAG,"SSSS 0 ="+detectionVehicle.index);
				DetectionVehicle.detectionVehicleList.add(detectionVehicle);
			}			
			
			str = str.substring(str.indexOf("</VehicleList>")+1);
		}
		
		//=================================================================================
		if(DetectionVehicle.detectionVehicleList.size() > 0)
		{			
			CustomerProtocol.notifyDetectionList();	
		}		
	}
}
