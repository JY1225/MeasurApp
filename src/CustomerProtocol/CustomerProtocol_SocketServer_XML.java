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
<DetectStatus>已检测(未检测)344</DetectStatus>
<SerialNum>检测流水号344</SerialNum>
<VehicleNum>粤A2222</VehicleNum>
<VehicleNumType>号牌种类</VehicleNumType>
<VehicleType>车辆类型344</VehicleType>
<VehicleBrand>品牌型号344</VehicleBrand>
<VehicleID>车辆识别代码344</VehicleID>
<MotorID>发动机号码344</MotorID>
<NewOrOld>在用车(注册车)344</NewOrOld>
<OperatorName>操作员姓名344</OperatorName>
<OperatorID>操作员ID344</OperatorID>
<OriginalCarLength>1233333334</OriginalCarLength>
<OriginalCarWidth>2556444</OriginalCarWidth>
<OriginalCarHeight>155556</OriginalCarHeight>
<OriginalLanbanHeight>566655</OriginalLanbanHeight>
<AxleNum>轴数344</AxleNum>
<AxleDistance1>轴距1344</AxleDistance1>
<AxleDistance2>轴距2344</AxleDistance2>
<AxleDistance3>轴距3344</AxleDistance3>
<AxleDistance4>轴距4344</AxleDistance4>
<AxleDistance5>轴距5344</AxleDistance5>
</VehicleList>
<VehicleList>
<DetectStatus>已检测(未检测)</DetectStatus >
<SerialNum>检测流水号</SerialNum>
<VehicleNum>粤A333</VehicleNum>
<VehicleNumType>号牌种类</VehicleNumType>
<VehicleType>车辆类型</VehicleType>
<VehicleBrand>品牌型号</VehicleBrand>
<VehicleID>车辆识别代码</VehicleID>
<MotorID>发动机号码</MotorID>
<NewOrOld>在用车(注册车)</NewOrOld>
<OperatorName>操作员姓名</OperatorName>
<OperatorID>操作员ID</OperatorID>
<OriginalCarLength>12334</OriginalCarLength>
<OriginalCarWidth>2556</OriginalCarWidth>
<OriginalCarHeight>1556</OriginalCarHeight>
<OriginalLanbanHeight>555</OriginalLanbanHeight>
<AxleNum>轴数</AxleNum>
<AxleDistance1>轴距1</AxleDistance1>
<AxleDistance2>轴距2</AxleDistance2>
<AxleDistance3>轴距3</AxleDistance3>
<AxleDistance4>轴距4</AxleDistance4>
<AxleDistance5>轴距5</AxleDistance5>
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
