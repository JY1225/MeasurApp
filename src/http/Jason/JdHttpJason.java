package http.Jason;

import java.util.concurrent.ArrayBlockingQueue;

import SensorBase.LMSConstValue;

public class JdHttpJason {
	String sBarCode;
	float length,width,height;
	String sTime;
	long lTime;
	
	public JdHttpJason(String _sBarCode,float _length,float _width,float _height,String _sTime,long _lTime)
	{
		sBarCode = _sBarCode;
		length = _length;
		width = _width;
		height = _height;
		sTime = _sTime;
		lTime = _lTime;
	}
}
