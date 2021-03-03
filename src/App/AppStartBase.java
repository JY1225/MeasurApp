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
				JOptionPane.showMessageDialog(null, "只能启动一次,请检查程序是否已经启动!", "提示", JOptionPane.OK_OPTION);
				System.exit(0);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "启动异常", "提示", JOptionPane.OK_OPTION);
		}
		
		//======================================================================
		final long MB = 1024 * 1024;  
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();  
		MemoryUsage usage = memorymbean.getHeapMemoryUsage();  		
		long heapMax = usage.getMax()/MB;
		if(heapMax < 600)
		{
			JOptionPane.showMessageDialog(null, "堆内存太小,请使用启动器来启动!", "提示", JOptionPane.OK_OPTION);		
			
			if(!LMSConstValue.isMyMachine())
				System.exit(0);
		}
	}
}
