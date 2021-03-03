package layer.drawLayer;

import layer.dataLayer.*;
import CarDetect.*;
import SensorBase.*;

public abstract class RadarMonitorDrawRunnable
{
    String DEBUG_TAG;
    int _sensorID;
    public DataLayerDataParseLineRadar dataParseLineDrawImage;
    
    public abstract void radarMonitorDrawRunnableParse();
    
    public RadarMonitorDrawRunnable(final int sensorID) {
        this.DEBUG_TAG = "RadarMonitorDrawRunnable";
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
        this.dataParseLineDrawImage = new DataLayerDataParseLineRadar();
    }
    
    public Runnable thread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        RadarMonitorDrawRunnable.this.dataParseLineDrawImage = (DataLayerDataParseLineRadar) CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[RadarMonitorDrawRunnable.this._sensorID].dataParseLineDrawImageQueue.take();
                        RadarMonitorDrawRunnable.this.radarMonitorDrawRunnableParse();
                    }
                }
                catch (OutOfMemoryError e) {
                    LMSLog.outOfMemoryDialog((Throwable)e);
                }
                catch (Exception e2) {
                    LMSLog.exceptionDialog((String)null, (Throwable)e2);
                }
            }
        };
    }
}
