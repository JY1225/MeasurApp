package CustomerProtocol;

import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface ZJDLL extends Library {
	ZJDLL instanceDll  = (ZJDLL)Native.loadLibrary("ZJDLL",ZJDLL.class);
	
	//C:\\Users\\Administrator.PC-201609131709\\Desktop\\综检动态库\\
	public boolean init();
	
	//查询xtlb=18，jkid=18C91，yhdh=9001，jkxh=9001
	//jkid 为18C91，xm 为 M1，UTF8XmlDoc 为固定XML格式参数
	public int queryObjectOut(String xtlb,String jkid,String UTF8XmlDoc,
			String yhdh,String xm,byte[]  rexml);
	
	//写入xtlb=18，jkid=18C81，yhdh=9001，jkxh=9001
	//jkid 为18C55 18C81 18C58，xm 为 M1，UTF8XmlDoc 为固定XML格式参数
	public int writeObjectOut (String xtlb,String jkid,
			String UTF8XmlDoc,String yhdh,String xm,byte[]  rexml);
}
