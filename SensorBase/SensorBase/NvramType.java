package SensorBase;

public class NvramType {
	public enum Type {
		BOOLEAN_TYPE,
		INTEGER_TYPE, 
		FLOAT_TYPE, 
		STRING_TYPE,
	}

	public boolean isValid;

	public boolean bValue;
	public int iValue;
	public float fValue;
	public String sValue;

	public String nvramStr;
	public Type type;

	public NvramType(String _nvramStr, Type _type) {
		nvramStr = _nvramStr;
		type = _type;
	}

}
