package App;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.JOptionPane;

import SensorBase.LMSConstValue;

public class AppStartBase {
	public static void appStartCheck()
	{
		try{
			File file = new File(".lock");
			RandomAccessFile raf = new RandomAccessFile(file,"rw");
			FileChannel fc = raf.getChannel();
			FileLock lock = fc.tryLock(0, 1, false);
			if (lock == null)
			{	
				JOptionPane.showMessageDialog(null, "ֻ������һ��,��������Ƿ��Ѿ�����!", "��ʾ", JOptionPane.OK_OPTION);
				System.exit(0);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "�����쳣", "��ʾ", JOptionPane.OK_OPTION);
		}
		
		//======================================================================
		final long MB = 1024 * 1024;  
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();  
		MemoryUsage usage = memorymbean.getHeapMemoryUsage();  		
		long heapMax = usage.getMax()/MB;
		if(heapMax < 600)
		{
			JOptionPane.showMessageDialog(null, "���ڴ�̫С,��ʹ��������������!", "��ʾ", JOptionPane.OK_OPTION);		
			
			if(!LMSConstValue.isMyMachine())
				System.exit(0);
		}
	}
}
