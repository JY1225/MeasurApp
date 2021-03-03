package Ben.CNN;

import java.util.Arrays;

public class CNN
{
	public static double[][] sigmoid(double[][] matrix)
	{
		int rows = matrix.length;
		int cols = matrix[0].length;
		double[][] outputMatrix = new double[rows][cols];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				outputMatrix[i][j] = 1.0 / (1.0 + Math.exp(-matrix[i][j]));
			}
		}
		return outputMatrix;
	}

	public static double sigmoid(double x)
	{
		double y = 1.0 / (1.0 + Math.exp(-x));
		return y;
	}

	private static String carTypeNumToString(int carTypeNum)
	{
		String CarType;
		if (carTypeNum == 1)
		{
			CarType = "小车";
		}
		else if (carTypeNum == 2)
		{
			CarType = "客车";
		}
		else if (carTypeNum == 3)
		{
			CarType = "货车";
		}
		else if (carTypeNum == 4)
		{
			CarType = "挂车";
		}
		else
		{
			CarType = "未知类型";
		}
		return CarType;

	}

	public static String predict(double[][] theta1, double[][] theta2, double[][] x)
	{

		double[][] h1 = sigmoid(Matrix.multiply(Matrix.unrollMatrixAndAddOne(x), theta1));
		double[][] h2 = sigmoid(Matrix.multiply(Matrix.unrollMatrixAndAddOne(h1), theta2));
		System.out.println("概率分布：" + Arrays.toString(h2[0]));

		double temp = h2[0][0];
		int carTypeNum = 1;
		for (int i = 1; i < h2[0].length; i++)
		{
			if (h2[0][i] > temp)
			{
				temp = h2[0][i];
				carTypeNum = i + 1;
			}
		}
		if (h2[0][carTypeNum - 1] < 0.5)
		{
			carTypeNum =0;
		}

		return carTypeNumToString(carTypeNum);
	}

}
