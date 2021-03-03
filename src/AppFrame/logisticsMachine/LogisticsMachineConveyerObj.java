package AppFrame.logisticsMachine;

public class LogisticsMachineConveyerObj {
	long lReceiveTime;
	
  	int width;
	int length;
	
	float rotateAngle;
	int rotateCenterX;
	int rotateCenterY;
	
	public LogisticsMachineConveyerObj(
		long _lReceiveTime,
		int _width,int _length,
		float _rotateAngle,
		int _rotateCenterX,int _rotateCenterY)
	{
		lReceiveTime = _lReceiveTime;
		width = _width;
		length = _length;
		rotateAngle = _rotateAngle;
		rotateCenterX = _rotateCenterX;
		rotateCenterY = _rotateCenterY;
	}
}
