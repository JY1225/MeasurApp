package layer.algorithmLayer;

public class ThreeDPoint {
	public int sensorID;
	public int region;
	public float x;
	public float y;
	public float z;

	public int index;

	public ThreeDPoint(int _sensorID,int _region, float _x, float _y, float _z)
	{
		sensorID = _sensorID;
		region = _region;
		x = _x;
		y = _y;
		z = _z;
	}
	
	public Float getX()
	{
		return x;
	}
	
	public Float getY()
	{
		return y;
	}
	
	public Float getZ()
	{
		return z;
	}
}
