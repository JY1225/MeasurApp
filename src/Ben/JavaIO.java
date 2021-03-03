package Ben;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import SensorBase.LMSLog;

public class JavaIO
{
	// 写String 不覆盖
	public static void writerString(String path, String data)
	{
		BufferedWriter bufferedWtriter = null;
		try
		{
			File file = new File(path);
			bufferedWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			bufferedWtriter.append(data);
			bufferedWtriter.newLine();
			bufferedWtriter.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (bufferedWtriter != null)
			{
				try
				{
					bufferedWtriter.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	// 写点云 覆盖
	public static void writerPointCloud(String path, ArrayList<Space3d> pointList)
	{
		BufferedWriter bufferedWtriter = null;
		try
		{
			File file = new File(path);
			file.delete();
			file.createNewFile();
			bufferedWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			for (int i = 0; i < pointList.size(); i++)
			{
				Space3d point = new Space3d(pointList.get(i));
				bufferedWtriter.append(String.valueOf(point.x) + "\t" + String.valueOf(point.y) + "\t" + String.valueOf(point.z));
				bufferedWtriter.newLine();
				bufferedWtriter.flush();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (bufferedWtriter != null)
			{
				try
				{
					bufferedWtriter.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	// 写矩阵 不覆盖
	public static void writerMatrix(String path, double[][] matrix)
	{
		BufferedWriter bufferedWtriter = null;
		try
		{
			File file = new File(path);
			file.createNewFile();
			bufferedWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			String stringMatrix = "";
			for (int i = 0; i < matrix.length; i++)
			{
				for (int j = 0; j < matrix[0].length; j++)
				{
					stringMatrix = stringMatrix + String.valueOf(matrix[i][j]) + "\t";
				}
			}
			bufferedWtriter.append(stringMatrix);
			bufferedWtriter.newLine();
			bufferedWtriter.flush();
		}

		catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (bufferedWtriter != null)
			{
				try
				{
					bufferedWtriter.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	// 读点云
	public static ArrayList<Space3d> readSpace3d(String path)
	{
		BufferedReader bufferedReader = null;
		ArrayList<Space3d> pointList = new ArrayList<Space3d>();
		try
		{
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")); // 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码,
			String stringData = null;
			while ((stringData = bufferedReader.readLine()) != null)
			{
				String[] asData = stringData.split("\t");
				Space3d point = new Space3d(Double.valueOf(asData[0]), Double.valueOf(asData[1]), Double.valueOf(asData[2]));
				pointList.add(point);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				bufferedReader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return pointList;
	}

	// 读txt矩阵
	public static double[][] readMatrix(String path, int rows, int cols)
	{
		BufferedReader bufferedReader = null;
		double[][] matrix = new double[rows][cols];
		try
		{
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")); // 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码,
			for (int i = 0; i < rows; i++)
			{
				String[] rowData = bufferedReader.readLine().split("\t");
				for (int j = 0; j < cols; j++)
				{
					matrix[i][j] = Double.valueOf(rowData[j]);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			LMSLog.exceptionDialog("检测仪异常", e);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return matrix;
	}
}