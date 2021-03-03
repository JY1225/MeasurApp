package Ben.CNN;

import java.io.*;
import java.util.ArrayList;

import Ben.Space3d;
import Ben.JavaIO;
import Ben.Algorithm.SpaceCloud;

public class MatrixSaveMain
{
	private static int appType = 1; // 1 ¥¥Ω®Õº∆¨ £ª2 –¥æÿ’Û
	private static int carTypes = 1;
	private static int cars = 2;

	private static int rows;
	private static int cols;
	private static int resolution;

	public static void main(String[] args) throws IOException
	{
		if (appType == 1)
		{
			rows = 400;
			cols = 2400;
			resolution = 10;

			for (int i = 1; i <= carTypes; i++)
			{

				for (int j = 1; j <= cars; j++)
				{
					String path = "F:/2 Data/PointCloudData/temp/" + i + " " + j + ".txt";
					File file = new File(path);
					if (file.exists())
					{
						ArrayList<Space3d> pointList = JavaIO.readSpace3d(path);
						double[][] imageMatrix = SpaceCloud.pointCloudXZToMatrix(pointList, resolution, rows, cols);

						//¥¥Ω®Õº∆¨
						MatrixView showMatrix = new MatrixView(rows, cols);
						showMatrix.MatrixToImage(imageMatrix);
						showMatrix.createImage("F:/2 Data/PointCloudData/temp/" + i + " " + j + ".jpg");
					}
				}
			}
		}
		else if (appType == 2)
		{
			rows = 40;
			cols = 240;
			resolution = 100;
			String path1 = "F:/2 Data/PointCloudData/temp/XMatrix.txt";
			String path2 ="F:/2 Data/PointCloudData/temp/YMatrix.txt";


			for (int i = 1; i <= carTypes; i++)
			{
				for (int j = 1; j <= cars; j++)
				{
					String path = "F:/2 Data/PointCloudData/temp/" + i + " " + j + ".txt";
					File file = new File(path);
					if (file.exists())
					{
						ArrayList<Space3d> pointList = JavaIO.readSpace3d(path);
						double[][] imageMatrix = SpaceCloud.pointCloudXZToMatrix(pointList, resolution, rows, cols);

						//–¥æÿ’Û
						JavaIO.writerMatrix(path1,imageMatrix);
						JavaIO.writerString(path2,String.valueOf(i));
					}
				}
			}
		}
	}
}