package layer.dataLayer;

import java.util.HashMap;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
