package Ben.Algorithm;

import java.util.ArrayList;

import Ben.Space3d;

public class SpaceCloud
{

	// 点云边界计算
	public static Space3d[] maxMin(ArrayList<Space3d> pointList)
	{
		int length = pointList.size();
		Space3d point0 = pointList.get(0);
		Space3d max = new Space3d(point0);
		Space3d min = new Space3d(point0);
		for (int i = 0; i < length; i++)
		{
			Space3d point = pointList.get(i);
			if (max.x < point.x)
			{
				max.x = point.x;
			}
			if (min.x > point.x)
			{
				min.x = point.x;
			}
			if (max.y < point.y)
			{
				max.y = point.y;
			}
			if (min.y > point.y)
			{
				min.y = point.y;
			}
			if (max.z < point.z)
			{
				max.z = point.z;
			}
			if (min.z > point.z)
			{
				min.z = point.z;
			}
		}
		Space3d[] maxMin =
		{ max, min };
		return maxMin;
	}

	//点云的XY数据转01矩阵，不保留原始信息
	public static double[][] pointCloudXZToMatrix(ArrayList<Space3d> pointList, double resolution, int rows, int cols)
	{
		double[][] matrix = new double[rows][cols];
		Space3d[] maxMin = SpaceCloud.maxMin(pointList);

		double xMiddle = (maxMin[0].x + maxMin[1].x) / 2;
		double zMiddle = (maxMin[0].z + maxMin[1].z) / 2;
		for (int i = 0; i < pointList.size(); i++)
		{
			Space3d point = pointList.get(i);
			int x = (int) (Math.floor((point.x - xMiddle) / resolution) + cols / 2);
			int z = (int) (Math.floor((point.z - zMiddle) / resolution) + rows / 2);

			if (x >= 0 && x < cols && z >= 0 && z < rows)
			{
				matrix[z][x] = 1;
			}
		}

		return matrix;
	}

}
