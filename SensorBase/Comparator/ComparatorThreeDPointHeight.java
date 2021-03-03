package Comparator;

import java.util.Comparator;

import layer.algorithmLayer.ThreeDPoint;

public class ComparatorThreeDPointHeight implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		ThreeDPoint threeDPoint0=(ThreeDPoint)arg0;
		ThreeDPoint threeDPoint1=(ThreeDPoint)arg1;

		return threeDPoint1.getY().compareTo(threeDPoint0.getY());
	}

}
