package FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class FileManager {
	private final static String DEBUG_TAG="FileManager";

	// 快速复制
	public static void fileCopy(String pathIn, String pathOut)
	{
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		FileChannel channelIn = null;
		FileChannel channelOut = null;
		try
		{
			fileInputStream = new FileInputStream(pathIn);
			fileOutputStream = new FileOutputStream(pathOut);
			channelIn = fileInputStream.getChannel();
			channelOut = fileOutputStream.getChannel();
			channelIn.transferTo(0, channelIn.size(), channelOut);
		}
		catch (IOException e)
		{
			e.printStackTrace();

		} finally
		{
			try
			{
				if(fileInputStream != null)
				{
					fileInputStream.close();
				}
				if(channelIn != null)
				{
					channelIn.close();
				}
				if(fileOutputStream != null)
				{
					fileOutputStream.close();
				}
				if(channelOut != null)
				{
					channelOut.close();
				}
			}
			catch (IOException e)
			{
				LMSLog.exceptionDialog("检测仪异常", e);
			}

		}
	}
	
	public static void fileCopyWithRealTime(String time,String destPath, String srcPath, String fileName)  
	{
		//===================================================================
		String realTimeFileName;
		String strPath;
		
		String strYear,strMonth,strDay;
		strYear = time.substring(0,4);
		strMonth = time.substring(4,6);
		strDay = time.substring(6,8);
		
		File file;

		//----------------------------------------------------
		strPath = (".//"+destPath);
 		file = new File(strPath);  //判断文件夹是否存在,如果不存在则创建文件夹  
 		if (!file.exists()) 
 		{   
 			file.mkdir();
 		}
 		
 		//----------------------------------------------------
		strPath += ("//"+strYear);
 		file = new File(strPath);  //判断文件夹是否存在,如果不存在则创建文件夹  
 		if (!file.exists()) 
 		{   
 			file.mkdir();
 		}
 		//----------------------------------------------------
 		strPath += ("//"+strMonth);
 		file = new File(strPath);  //判断文件夹是否存在,如果不存在则创建文件夹  
 		if (!file.exists()) 
 		{   
 			file.mkdir();
 		}

 		//----------------------------------------------------
 		strPath += ("//"+strDay);
 		file = new File(strPath);  //判断文件夹是否存在,如果不存在则创建文件夹  
 		if (!file.exists()) 
 		{   
 			file.mkdir();
 		}

 		//----------------------------------------------------
		realTimeFileName = strPath+"//"+time+"_"+fileName;
			
   		FileManager.fileCopy(srcPath+"//"+fileName,realTimeFileName);
	}
	
	/**
     * 获取目录下所有文件(按时间排序)
     * 
     * @param path
     * @return
     */
    public static List<File> getFileSort(String path) {
 
        List<File> list = getFiles(path, new ArrayList<File>());
 
        if (list != null && list.size() > 0) {
 
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() > newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }
 
                }
            });
        }
 
        return list;
    }
 
    /**
     * 
     * 获取目录下所有文件
     * 
     * @param realpath
     * @param files
     * @return
     */
    public static List<File> getFiles(String realpath, List<File> files) {
 
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
            	//含子路径
            	/*
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
                */
                files.add(file);
            }
        }
        return files;
    }

    public static List<File> getFileSortDirectory(String path) {
    	 
        List<File> list = getFilesDirectory(path, new ArrayList<File>());
 
        if (list != null && list.size() > 0) {
 
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() > newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }
 
                }
            });
        }
 
        return list;
    }

    public static List<File> getFilesDirectory(String realpath, List<File> files) 
    {	 
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                	getFilesDirectory(file.getAbsolutePath(), files);
                    files.add(file);
                } 
            }
        }
        return files;
    }
    
    public static List<File> getFilesDirectoryTime(String realpath, List<File> files,int depth) 
    {	 
    	depth++;
    	
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                	getFilesDirectoryTime(file.getAbsolutePath(), files,depth);
                    files.add(file);
                } 
            }
        }
        return files;
    }
    
    public static List<File> getFilesAll(String realpath, List<File> files) 
    {	 
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                	getFilesAll(file.getAbsolutePath(), files);
                } 
                else
                {
                    files.add(file);
                }
            }
        }
        return files;
    }
    
    public static void removeFolderFileByDate(int calendarType,int offset,String strFolder)
	{		
   		//整理三维图文件夹
		Date date = null;
 		SimpleDateFormat sdf = new SimpleDateFormat(LMSConstValue.SDF);
		
		Calendar cl = Calendar.getInstance();
		Date dateNow = new Date();
		cl.setTime(dateNow);
		cl.add(calendarType, offset);	
		date = cl.getTime();
		String strTime = sdf.format(date);
		long lTime = LMSConstValue.stringToLongTime(strTime,LMSConstValue.SDF);      			
		LMSLog.d(DEBUG_TAG,"WEEK_OF_YEAR strTime="+strTime+" lTime="+lTime);
				
		List<File> list = getFilesAll(strFolder, new ArrayList<File>());
        for (File fileL : list) {					               
        	if(fileL.lastModified() < lTime)
        	{            	
            	fileL.delete();
        	}
        }	
	}
    
    public static File checkFile(String strPath,String strFileName)
	{
    	File pFile = null;
    	
    	if(strPath != null && strFileName != null)
	    {
			//====================================================================================
			File fileCarType = new File(strPath);  //判断文件夹是否存在,如果不存在则创建文件夹  
	 		if (!fileCarType.exists()) 
	 		{   
	 			fileCarType.mkdir();
	 		}
	
	 		//====================================================================================
	 		pFile = new File(strPath+"//"+strFileName);  			
			try {		       
				if(!pFile.exists())
					if(pFile.getParent()!=null && !new File(pFile.getParent()).exists()){
					    new File(pFile.getParent()).mkdirs();
				}
				pFile.createNewFile();
			} catch (Exception e) {
	       		LMSLog.exceptionDialog(null, e);
			}			
	    }
    	
		return pFile;
	}
}
