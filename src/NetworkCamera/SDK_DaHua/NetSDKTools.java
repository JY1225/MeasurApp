package NetworkCamera.SDK_DaHua;

import NetworkCamera.SDK_DaHua.NetSDKTools;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class NetSDKTools
{

	public static void GetPointerData(Pointer pNativeData, Structure pJavaStu)
	{
		GetPointerDataToStruct(pNativeData, 0, pJavaStu);
	}

	public static void GetPointerDataToStruct(Pointer pNativeData, long OffsetOfpNativeData, Structure pJavaStu)
	{
		pJavaStu.write();
		Pointer pJavaMem = pJavaStu.getPointer();
		pJavaMem.write(0, pNativeData.getByteArray(OffsetOfpNativeData, pJavaStu.size()), 0, pJavaStu.size());
		pJavaStu.read();
	}

	public static void GetPointerDataToStructArr(Pointer pNativeData, Structure[] pJavaStuArr)
	{
		long offset = 0;
		for (int i = 0; i < pJavaStuArr.length; ++i)
		{
			GetPointerDataToStruct(pNativeData, offset, pJavaStuArr[i]);
			offset += pJavaStuArr[i].size();
		}
	}

	/**
	 * 将结构体数组拷贝到内存
	 * @param pNativeData 
	 * @param pJavaStuArr
	 */
	public static void SetStructArrToPointerData(Structure[] pJavaStuArr, Pointer pNativeData)
	{
		long offset = 0;
		for (int i = 0; i < pJavaStuArr.length; ++i)
		{
			SetStructDataToPointer(pJavaStuArr[i], pNativeData, offset);
			offset += pJavaStuArr[i].size();
		}
	}

	public static void SetStructDataToPointer(Structure pJavaStu, Pointer pNativeData, long OffsetOfpNativeData)
	{
		pJavaStu.write();
		Pointer pJavaMem = pJavaStu.getPointer();
		pNativeData.write(OffsetOfpNativeData, pJavaMem.getByteArray(0, pJavaStu.size()), 0, pJavaStu.size());
	}

	public static void ByteArrToStructure(byte[] pNativeData, Structure pJavaStu)
	{
		pJavaStu.write();
		Pointer pJavaMem = pJavaStu.getPointer();
		pJavaMem.write(0, pNativeData, 0, pJavaStu.size());
		pJavaStu.read();
	}

	public static void ByteArrZero(byte[] dst)
	{
		// 清零
		for (int i = 0; i < dst.length; ++i)
		{
			dst[i] = 0;
		}
	}

	public static void StringToByteArr(String src, byte[] dst)
	{
		try
		{
			byte[] GBKBytes = src.getBytes("GBK");
			for (int i = 0; i < GBKBytes.length; i++)
			{
				dst[i] = (byte) GBKBytes[i];
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	public static long GetFileSize(String filePath)
	{
		File f = new File(filePath);
		if (f.exists() && f.isFile())
		{
			return f.length();
		}
		else
		{
			return 0;
		}
	}

	public static boolean ReadAllFileToMemory(String file, Memory mem)
	{
		if (mem != Memory.NULL)
		{
			long fileLen = GetFileSize(file);
			if (fileLen <= 0)
			{
				return false;
			}

			try
			{
				File infile = new File(file);
				if (infile.canRead())
				{
					FileInputStream in = new FileInputStream(infile);
					int buffLen = 1024;
					byte[] buffer = new byte[buffLen];
					long currFileLen = 0;
					int readLen = 0;
					while (currFileLen < fileLen)
					{
						readLen = in.read(buffer);
						mem.write(currFileLen, buffer, 0, readLen);
						currFileLen += readLen;
					}

					in.close();
					return true;
				}
				else
				{
					System.out.println("Failed to open file %s for read!!!\n");
					return false;
				}
			}
			catch (Exception e)
			{
				System.out.println("Failed to open file %s for read!!!\n");
				System.out.println(e);
				e.printStackTrace();
			}
		}

		return false;
	}

	public static void savePicture(Pointer pBuf, int dwBufSize, String sDstFile)
	{
		try
		{
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(sDstFile)));
			out.write(pBuf.getByteArray(0, dwBufSize), 0, dwBufSize);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void savePicture(Pointer pBuf, int dwBufOffset, int dwBufSize, String sDstFile)
	{
		try
		{
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(sDstFile)));
			out.write(pBuf.getByteArray(dwBufOffset, dwBufSize), 0, dwBufSize);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
