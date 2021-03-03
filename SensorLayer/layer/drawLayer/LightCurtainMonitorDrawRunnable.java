package layer.drawLayer;

import layer.dataLayer.*;
import CarDetect.*;
import SensorBase.*;

public abstract class LightCurtainMonitorDrawRunnable
{
    String DEBUG_TAG;
    public DataLayerDataParseLineLightCurtain dataLayerLightCurtainParseLine;
    
    public abstract void lightCurtainMonitorDrawRunnableParse();
    
    public LightCurtainMonitorDrawRunnable() {
        this.DEBUG_TAG = "LightCurtainMonitorDrawRunnable";
        this.dataLayerLightCurtainParseLine = new DataLayerDataParseLineLightCurtain();
    }
    
    public Runnable thread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        LightCurtainMonitorDrawRunnable.this.dataLayerLightCurtainParseLine = (DataLayerDataParseLineLightCurtain) CarDetectSetting.carDetectSetting.dataLayerLightCurtainRunnable.dataParseLineDrawImageQueue_LightCurtain.take();
                        LightCurtainMonitorDrawRunnable.this.lightCurtainMonitorDrawRunnableParse();
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
