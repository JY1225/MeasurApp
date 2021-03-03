package Comparator;

import java.util.Comparator;

import layer.algorithmLayer.ThreeDPoint;

public class ComparatorThreeDPointSingle implements Comparator{
	boolean bMinSide;
	public ComparatorThreeDPointSingle(boolean _bMinSide) {
		bMinSide = _bMinSide;
	}
	
	@Override
	public int compare(Object arg0, Object arg1) {
		ThreeDPoint threeDPoint0=(ThreeDPoint)arg0;
		ThreeDPoint threeDPoint1=(ThreeDPoint)arg1;
		
		if(bMinSide == true)
			return threeDPoint0.getX().compareTo(threeDPoint1.getX());			
		else
			return threeDPoint1.getX().compareTo(threeDPoint0.getX());
	}
}
