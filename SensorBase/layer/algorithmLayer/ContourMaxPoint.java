package layer.algorithmLayer;

public class ContourMaxPoint {
	public int maxValue = 0;
	public ThreeDPoint threeDPoint;
	
	public ContourMaxPoint(int _maxValue, ThreeDPoint _threeDPoint)
	{
		maxValue = _maxValue;
		if(_threeDPoint == null)
		{
			threeDPoint = new ThreeDPoint(0, 0, 100000, 100000, 100000); 
		}
		else
		{
			threeDPoint = _threeDPoint;
		}
	}
}
