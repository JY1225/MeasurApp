package CustomerProtocol;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public abstract class CustomerProtocol_WS extends CustomerProtocol{
	public enum EnumWSType{
		QUERY, //刷新
		UPLOAD,	//上传	
		STATE,	//开始上传
    } 	
	public static EnumWSType enumWSType;
	

	//解析待检队列数据:XML格式
	public abstract void parseDetectionList_WS(String xmlStr);	
}
