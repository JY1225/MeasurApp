package ParserXml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParserXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	public static Map ReadXML(String xml){
    	List ResultList = new ArrayList();
    	StringReader SRxml = new StringReader(xml);
		SAXReader reader = new SAXReader();
		Document doc;
		Map<String,String> map = new HashMap<String,String>();
		Map<String,Map<String,String>> resultMap = new HashMap<String, Map<String,String>>();
		String vehisparaId = null;
		Map<String,String> tempMap = null;
		try{
			doc = reader.read(SRxml);
			Element root = doc.getRootElement();
			int count = root.nodeCount();//子节点数�?
			for(Iterator<Element> i = root.elementIterator(); i.hasNext(); ){//循环root的子节点
				Element rootElement = (Element)i.next();
				if("head".equals(rootElement.getName())){//取head子节点里的数�?
					for(Iterator<Element> headI = rootElement.elementIterator(); headI.hasNext();){//循环head子节�?
						Element headElement = (Element)headI.next();
						map.put(headElement.getName(), headElement.getText());//存入map中�??
					}
					resultMap.put("head", map);//不论是否是多行数据，将head的信息存入mutiRowMap�?
				}
				if("body".equals(rootElement.getName())){
					if(map.get("code").equals("1") &&  Integer.parseInt(map.get("rownum")) == 1){//返回�?条数据的处理方法�?
						for(Iterator<Element> bodyI = rootElement.elementIterator(); bodyI.hasNext();){
							Element bodyElement = (Element)bodyI.next();
							if("vehispara".equals(bodyElement.getName())){
								for(Iterator<Element> VehI = bodyElement.elementIterator(); VehI.hasNext();){
									Element vehisparaElement = (Element)VehI.next();
									if("row".equals(vehisparaElement.getName())){
										for(Iterator<Element> RowI = vehisparaElement.elementIterator();RowI.hasNext();){
											Element RowElement = (Element)RowI.next();
											map.put(RowElement.getName(), RowElement.getText());
										}
									}
									else{//如果没有row标签,直接赋�??
											//Element vehisparaElement = (Element)VehI.next();
											map.put(vehisparaElement.getName(), vehisparaElement.getText());
										}
									}
								}
							}
						}
					else if(map.get("code").equals("1") &&  Integer.parseInt(map.get("rownum")) != 1){//多条返回数据的处理方法并没有完成rownum > 1;
						for(Iterator<Element> bodyI = rootElement.elementIterator(); bodyI.hasNext();){//循环遍历body内的节点
							Element bodyElement = (Element)bodyI.next();
							if("vehispara".equals(bodyElement.getName())){
								vehisparaId = bodyElement.attribute("id").getValue();
								tempMap = new HashMap<String, String>();
								for(Iterator<Element> VehI = bodyElement.elementIterator();VehI.hasNext();){//循环遍历vehispara内的节点
									Element vehisparaElement = (Element)VehI.next();
									tempMap.put(vehisparaElement.getName(), vehisparaElement.getText());
									
								}
								resultMap.put("row_"+vehisparaId, tempMap);
							}
						}
					}
					else{//如果没有返回数据 code=0
						//do nothing
					}
					}
				}
		}catch(DocumentException e){
			e.printStackTrace();
			map.put("exception", e.getMessage());
		}
		if(map.get("rownum") != null && Integer.parseInt(map.get("rownum")) == 1){
			resultMap.put("data", map);//如果是一个数据，resultMap中有两个map,�?个的key是head.另一个key是data.但其实两个key值对应同�?个map�?
			return resultMap;
		}
		else{
			return resultMap;//如果是多个数据，resultMap中有rownum+1个map,�?个的key是head.另外maps的key�?'row_'+rowId.其中也包括了没有数据的情�?:这时候resultMap中只有一个key值为head的map
		}
		
    }
}
