package CustomerProtocol;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import http.WebService.WebService;
import http.WebService.WebServiceLine;
import http.WebService.XMLParse;
import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import AppFrame.widgets.DetectionListComboBox;
import AppFrame.widgets.Operator;
import ParserXml.ParserXML;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class CustomerProtocol_WS_SDGC extends CustomerProtocol_WS{
	public static boolean isBeginDetection;//是否开始检测
	@Override
	public void queryDetectionList(ContourDetectionTabPanelMain detectPanel)
	{		
		enumWSType = EnumWSType.QUERY;
        try {
        	String xmlStr = "PlateID=";
    		String url = LMSConstValue.sNvramWebServiceUrl.sValue+"WS_GetGabSizeVehList";  
    		
    		WebServiceLine webServiceLine = new WebServiceLine(url,xmlStr);
        	WebService.webServiceQueue.put(webServiceLine);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			LMSLog.exceptionDialog("WebService异常", e);
		}
	}

	@Override
	public void parseDetectionList_WS(String xmlStr)
	{		
		if(xmlStr != null && xmlStr.contains("VehCount"))
		{
			DetectionVehicle.detectionVehicleList.clear();
			
			int vehCount = Integer.valueOf(new XMLParse().getXMLStrValue(xmlStr,"VehCount"));
			for(int i=0; i<vehCount; i++)
			{
				String vehStr = new XMLParse().getXMLStrValue(xmlStr,"Veh_"+(i+1));
	
				DetectionVehicle detectionVehicle = new DetectionVehicle();
				detectionVehicle.index = i+1;
				
				detectionVehicle.sSerialNum = new XMLParse().getXMLStrValue(vehStr,"InspectionID");
				detectionVehicle.sVehicleNum = new XMLParse().getXMLStrValue(vehStr,"PlateID");
				detectionVehicle.sVehicleNumType = new XMLParse().getXMLStrValue(vehStr,"PlateType");
				detectionVehicle.sVehicleType = new XMLParse().getXMLStrValue(vehStr,"Model");
				
				detectionVehicle.sOriginalCarLength = new XMLParse().getXMLStrValue(vehStr,"SizeL");
				detectionVehicle.sOriginalCarWidth = new XMLParse().getXMLStrValue(vehStr,"SizeW");
				detectionVehicle.sOriginalCarHeight = new XMLParse().getXMLStrValue(vehStr,"SizeH");
				
				detectionVehicle.sOriginalLanBanHeight = new XMLParse().getXMLStrValue(vehStr,"SizePH");
				
				DetectionVehicle.detectionVehicleList.add(detectionVehicle);
			}						
		}
	}
	
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{		
		enumWSType = EnumWSType.UPLOAD;

		String xmlStr = "";

		if(detectPanel.serialNumLabelTextField.getTextFieldText().equals(""))
		{
			LMSLog.warningDialog("不允许结果上传", "不允许结果上传,检测流水号不能为空");
			
			return false;
		}
		else
		{
			xmlStr += "InspectionID="+detectPanel.serialNumLabelTextField.getTextFieldText();
		}

		if(LMSConstValue.sNvramStationID.sValue.equals(""))
		{
			LMSLog.warningDialog("不允许结果上传", "不允许结果上传,工位号不能为空");
			
			return false;
		}
		else
		{
	        xmlStr += ("&DevID="+LMSConstValue.sNvramStationID.sValue);
		}
        
        if(detectPanel.iNvramDetectLength.iValue == 0)
		{
			LMSLog.warningDialog("不允许结果上传", "不允许结果上传,长不能为空");
			
			return false;
		}
		else
		{
			xmlStr += ("&SizeL="+ detectPanel.iNvramDetectLength.iValue);
		}
        if(detectPanel.iNvramDetectWidth.iValue == 0)
		{
			LMSLog.warningDialog("不允许结果上传", "不允许结果上传,宽不能为空");
			
			return false;
		}
		else
		{
			xmlStr += ("&SizeW="+ detectPanel.iNvramDetectWidth.iValue);
		}
        if(detectPanel.iNvramDetectHeight.iValue == 0)
		{
			LMSLog.warningDialog("不允许结果上传", "不允许结果上传,高不能为空");
			
			return false;
		}
		else
		{
			xmlStr += ("&SizeH="+ detectPanel.iNvramDetectHeight.iValue);
		}
        xmlStr += ("&SizePH="+ detectPanel.iNvramDetectLanbanHeight.iValue);

        Operator operator = (Operator)detectPanel.operatorComboBox.getSelectedItem();
        if(operator == null)
        {
	        xmlStr += ("&InspectorName=");
	        xmlStr += ("&InspectorPID=");
        }
        else
        {
	        xmlStr += ("&InspectorName="+ operator.sOperatorName);
	        xmlStr += ("&InspectorPID="+ operator.sOperatorID);			        	
        }
        xmlStr += ("&InspectorAdvice="+ "");

        LMSLog.d("xmlStr=",xmlStr);
        
        try {
    		String url = LMSConstValue.sNvramWebServiceUrl.sValue+"WS_UploadGabSizeResult"; 
    		
    		WebServiceLine webServiceLine = new WebServiceLine(url,xmlStr);
        	WebService.webServiceQueue.put(webServiceLine);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			LMSLog.exceptionDialog("WebService异常", e);
		}
		
		return true;
	}

	@Override
	public void beginDetection(ContourDetectionTabPanelMain detectPanel,boolean bValue) {
		CustomerProtocol_WS.enumWSType = EnumWSType.STATE;
		isBeginDetection = bValue;
		String xmlStr = "";
		if(detectPanel.serialNumLabelTextField.getTextFieldText().equals(""))
		{
			LMSLog.warningDialog("不允许触发\"正在检测中\"", "不允许触发\"正在检测中\",检测流水号不能为空");
			
			return;
		}
		else
		{
			xmlStr += "InspectionID="+detectPanel.serialNumLabelTextField.getTextFieldText();
		}
		
        if(LMSConstValue.sNvramStationID.sValue.equals(""))
        {
			LMSLog.warningDialog("不允许触发\"正在检测中\"", "不允许触发\"正在检测中\",工位号不能为空");

			return;
        }
		else
		{
			xmlStr += ("&DevID="+LMSConstValue.sNvramStationID.sValue);
		}
        
        try {
			String url = LMSConstValue.sNvramWebServiceUrl.sValue+"WS_BeginGabSizeItem"; 
    		
    		WebServiceLine webServiceLine = new WebServiceLine(url,xmlStr);
        	WebService.webServiceQueue.put(webServiceLine);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			LMSLog.exceptionDialog("WebService异常", e);
		}
	}

	@Override
	public void carInSignal(ContourDetectionTabPanelMain detectPanel,
			boolean bValue) {
		
		if (bValue == true && isBeginDetection == true)
		{
			try
			{	
			String xmlStrPt ="";
			xmlStrPt += "InspectionID="+detectPanel.serialNumLabelTextField.getTextFieldText();
			xmlStrPt += ("&DevID="+LMSConstValue.sNvramStationID.sValue);
			xmlStrPt += ("&PhotoType=1");
				String url = LMSConstValue.sNvramWebServiceUrl.sValue+"WS_TakeGabSizePhoto"; 
	    		
	    		WebServiceLine webServiceLine = new WebServiceLine(url,xmlStrPt);
	        	WebService.webServiceQueue.put(webServiceLine);
			}
			catch (Exception e)
			{
				LMSLog.exceptionDialog("WebService异常", e);
			}
		}
		else
		{
			
			if (isBeginDetection == true)
			{
				try
				{	
					String xmlStrPt ="";
					xmlStrPt += "InspectionID="+detectPanel.serialNumLabelTextField.getTextFieldText();
					xmlStrPt += ("&DevID="+LMSConstValue.sNvramStationID.sValue);
					xmlStrPt += ("&PhotoType=2");
					String url = LMSConstValue.sNvramWebServiceUrl.sValue+"WS_TakeGabSizePhoto"; 
			    		
			    	WebServiceLine webServiceLine = new WebServiceLine(url,xmlStrPt);
			        WebService.webServiceQueue.put(webServiceLine);
			}catch(Exception e){
				LMSLog.exceptionDialog("WebService异常", e);
			}
			}
		}
	
	}
}
