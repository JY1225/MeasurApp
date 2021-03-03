package layer.algorithmLayer;

public class ZPlanePoint {
	public float z;
	public int pointNum1; //条件1下的点数
	public int pointNum2; //条件2下的点数
	
	public ZPlanePoint(float _z, int _PointNum1)
	{ 
		pointNum1 = _PointNum1;
		z = _z;
	}
	
	public ZPlanePoint(float _z, int _PointNum1, int _PointNum2)
	{ 
		pointNum1 = _PointNum1;
		pointNum2 = _PointNum2;
		z = _z;
	}
	
	public Float getZ()
	{
		return z;
	}
}
