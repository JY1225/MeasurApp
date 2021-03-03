package layer.algorithmLayer;

public class ZPlane {
	public int length_CurveFitting;
	public float z;
	public int startIndex;
	public int endIndex;
	
	public int widthMaxIndex;
		
	public ZPlane(int _length_CurveFitting,float _z,int _startIndex,int _endIndex)
	{ 
		length_CurveFitting = _length_CurveFitting;
		z = _z;
		startIndex = _startIndex;
		endIndex = _endIndex;
	}
}
