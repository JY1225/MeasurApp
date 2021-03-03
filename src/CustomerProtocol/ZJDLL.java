package CustomerProtocol;

import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface ZJDLL extends Library {
	ZJDLL instanceDll  = (ZJDLL)Native.loadLibrary("ZJDLL",ZJDLL.class);
	
	//C:\\Users\\Administrator.PC-201609131709\\Desktop\\�ۼ춯̬��\\
	public boolean init();
	
	//��ѯxtlb=18��jkid=18C91��yhdh=9001��jkxh=9001
	//jkid Ϊ18C91��xm Ϊ M1��UTF8XmlDoc Ϊ�̶�XML��ʽ����
	public int queryObjectOut(String xtlb,String jkid,String UTF8XmlDoc,
			String yhdh,String xm,byte[]  rexml);
	
	//д��xtlb=18��jkid=18C81��yhdh=9001��jkxh=9001
	//jkid Ϊ18C55 18C81 18C58��xm Ϊ M1��UTF8XmlDoc Ϊ�̶�XML��ʽ����
	public int writeObjectOut (String xtlb,String jkid,
			String UTF8XmlDoc,String yhdh,String xm,byte[]  rexml);
}
