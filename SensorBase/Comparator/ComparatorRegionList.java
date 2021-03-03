package Comparator;

import java.util.ArrayList;
import java.util.Comparator;

import layer.algorithmLayer.CoutourRegion;



public class ComparatorRegionList implements Comparator{
	@Override
	public int compare(Object arg0, Object arg1) {
		CoutourRegion regionList0=(CoutourRegion)arg0;
		CoutourRegion regionList1=(CoutourRegion)arg1;

		return regionList1.getSize().compareTo(regionList0.getSize());
	}

}
