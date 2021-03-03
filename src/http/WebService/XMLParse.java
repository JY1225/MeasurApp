package http.WebService;

import java.util.HashMap;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import AppFrame.widgets.DetectionListComboBox;
import CustomerProtocol.CustomerProtocol;
import CustomerProtocol.CustomerProtocol_WS;
import CustomerProtocol.CustomerProtocol_WS_SDGC;
import CustomerProtocol.DetectionVehicle;
import CustomerProtocol.CustomerProtocol_WS.EnumWSType;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class XMLParse extends DefaultHandler {
	private StringBuffer buf;
	private String str;

	public XMLParse() {
		super();
	}

	public void startDocument() throws SAXException {
		buf = new StringBuffer();
		System.out.println("*******开始解析XML*******");
	}

	public void endDocument() throws SAXException {
		System.out.println("*******XML解析结束*******");
	}

	public void endElement(String namespaceURI, String localName,
			String fullName) throws SAXException {
		str = buf.toString();
		System.out.println("节点=" + fullName);
		System.out.println("value=" + buf);
		System.out.println("长度=" + buf.length());

		String xmlString = buf.toString();
				
		//====================================================================
		if(CustomerProtocol_WS.customerProtocol != null)
		{			
			CustomerProtocol_WS.customerProtocol.parseDetectionList_WS(xmlString);
			
			if(DetectionVehicle.detectionVehicleList.size() > 0 && CustomerProtocol_WS.enumWSType == EnumWSType.QUERY)
			{			
				CustomerProtocol.notifyDetectionList();	
			}	
		}
		
		buf.delete(0, buf.length());
	}

	public void characters(char[] chars, int start, int length)
			throws SAXException {
		// 将元素内容累加到StringBuffer中
		buf.append(chars, start, length);
	}
	
	public String getXMLStrValue(String inStr, String getStr)
	{
		String getStrBegin = "<"+getStr+">";
		String getStrEnd = "</"+getStr+">";
		
		try
		{
			if(inStr.contains(getStrBegin))
			{
				//==========================================================
				int beginIndex,endIndex;
				
				beginIndex = inStr.indexOf(getStrBegin)+getStrBegin.length();
				endIndex = inStr.indexOf(getStrEnd);
				return inStr.substring(beginIndex,endIndex);
			}
		}
		catch(Exception e)
		{
			LMSLog.exceptionDialog("异常", e);
		}
		
		return null;
	}
}
