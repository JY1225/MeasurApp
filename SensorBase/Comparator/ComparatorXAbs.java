package Comparator;

import java.util.Comparator;

import layer.algorithmLayer.ThreeDPoint;

public class ComparatorXAbs implements Comparator{
	int bX;
	
	public ComparatorXAbs(int _bX)
	{
		bX = _bX;
	}
	
	@Override
	public int compare(Object arg0, Object arg1) {
		ThreeDPoint threeDPoint0=(ThreeDPoint)arg0;
		ThreeDPoint threeDPoint1=(ThreeDPoint)arg1;

		Float v1 = 0f;
		Float v0 = 0f;

		v1 = Math.abs(bX - threeDPoint1.getX());
		v0 = Math.abs(bX - threeDPoint0.getX());
		
		return v0.compareTo(v1);
	}
}
