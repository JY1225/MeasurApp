package CustomerProtocol;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public abstract class CustomerProtocol_WS extends CustomerProtocol{
	public enum EnumWSType{
		QUERY, //ˢ��
		UPLOAD,	//�ϴ�	
		STATE,	//��ʼ�ϴ�
    } 	
	public static EnumWSType enumWSType;
	

	//���������������:XML��ʽ
	public abstract void parseDetectionList_WS(String xmlStr);	
}
