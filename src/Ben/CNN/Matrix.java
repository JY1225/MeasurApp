package Ben.CNN;

import java.util.ArrayList;

public class Matrix
{
	//矩阵乘法运算
	public static double[][] multiply(double[][] x, double[][] y)
	{
		int rows = x.length;
		int cols = y[0].length;
		int length = y.length;
		double[][] z = new double[rows][cols];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				double temp = 0;
				for (int k = 0; k < length; k++)
				{
					temp = temp + x[i][k] * y[k][j];
				}
				z[i][j] = temp;
			}
		}

		return z;
	}

	//展开矩阵并在前面加1
	public static double[][] unrollMatrixAndAddOne(double[][] x)
	{
		int rows = x.length;
		int cols = x[0].length;
		int yLength = rows * cols + 1;
		double[][] y = new double[1][yLength];
		y[0][0] = 1;

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				y[0][i*cols+j+1]=x[i][j];
			}
		}

		return y;
	}



}
