package SensorBase;

public interface LMSLogInterface {
	void LMS_DEBUG_D(String debugTag,String DebugString);
	void LMS_DEBUG_I(String debugTag,String DebugString);
	void LMS_DEBUG_W(String debugTag,String DebugString);
	void LMS_DEBUG_E(String debugTag,String DebugString);
	void LMS_LITTLE(String debugTag,String DebugString);
	void LMS_EXCEPTION(int sensorID,Throwable t);
	void LMS_EXCEPTION(Throwable t);
	void LMS_OUT_OF_MEMORY_DIALOG(Throwable t);
	void LMS_EXCEPTION_DIALOG(String title,Throwable t);
	void LMS_EXCEPTION_DIALOG(String title,Throwable t, String headStr);
	void LMS_WARNING_DIALOG(String title,String msg);
	void LMS_ERROR_DIALOG(String title,String msg);
	void LMS_DATA_LOG(int sensorID,String DebugString);
	void LMS_CAR_OUT(String str);
	void LMS_DEBUG_OUT_ERROR(String DebugString);
}
