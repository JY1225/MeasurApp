package layer.algorithmLayer;

import java.util.ArrayList;

import CarAppAlgorithm.WidthHeightDetectRunnable;
import SensorBase.LMSLog;

public class HeightMax
{
	public static ArrayList<HeightMax> middleHeightMaxList = new ArrayList();

	public boolean bValid;
	public int yMax;
	public int z;
	
	public HeightMax(int _yMax, int _z, boolean _bValid)
	{
		yMax = _yMax;
		z = _z;
		bValid = _bValid;
	}
	
	public static void generateMiddleLineHeight(
		Contour contour,int sensorID,
		int iStart,int iEnd,
		int widthMiddle,int widthOffSet)
	{		
		int zPlaneSize = contour.contourFilter.zPlaneFilterResultList[sensorID].size();
		middleHeightMaxList.clear();
		for(int zPlaneLoop=0;zPlaneLoop<zPlaneSize;zPlaneLoop++)
		{
			ZPlane zPlane = WidthHeightDetectRunnable.iGetZPlaneWithCarBacking(contour, sensorID, zPlaneSize, zPlaneLoop);
				
			if(zPlane.z >= iStart&&zPlane.z <= iEnd)
			{
				int iHeightMax = 0;
				for(int i=zPlane.startIndex;i<zPlane.endIndex;i++)
				{
					ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(i);
					
					if(threeDPoint.region == 0)
					{
						if(threeDPoint.x<(widthOffSet+widthMiddle)&&threeDPoint.x>(-widthOffSet+widthMiddle)
						)
						{
							if(iHeightMax < (int) threeDPoint.y)
							{
								iHeightMax = (int) threeDPoint.y;
							}
						}
					}
				}
				
				//------------------------------------------------
				HeightMax heightMax = new HeightMax(iHeightMax, (int) zPlane.z, true);
				middleHeightMaxList.add(heightMax);
			}
		}		
	}
}