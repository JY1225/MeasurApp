package Ben.CNN;

import java.util.ArrayList;
import Ben.CNN.CNN;
import Ben.Space3d;
import Ben.Algorithm.SpaceCloud;
import Ben.JavaIO;

public class CNN_PredictMain
{
	public static void main(String[] args)
	{
		double[][] theta1 = JavaIO.readMatrix("CNN_Parameter/theta1.txt",9601, 100);

		double[][] theta2 = JavaIO.readMatrix("CNN_Parameter/theta2.txt",101, 4);

		ArrayList<Space3d> pointList = JavaIO.readSpace3d("CNN_Parameter/CarData.txt");

		String result = CNN.predict(theta1, theta2, SpaceCloud.pointCloudXZToMatrix(pointList, 100, 40, 240));
			
		System.out.println("≥µ¡æ¿‡–Õ£∫"+result);
	}

}
