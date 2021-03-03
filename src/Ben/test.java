package Ben;

import java.util.ArrayList;

public class test
{

	public static void main(String[] args)
	{
		ArrayList<Space3f> singleFramePoints = new ArrayList<Space3f>();
		Space3f point = new Space3f(1,1,1);
		singleFramePoints.add(point);
		singleFramePoints.add(point);
		singleFramePoints.add(point);
		singleFramePoints.set(0, null);
		
	}

}
