package layer.physicLayer;

import java.util.concurrent.*;
import java.text.*;
import java.util.*;
import CarDetect.*;
import SensorBase.*;
import java.io.*;

public abstract class PhysicLayerBufferString
{
    private static final String DEBUG_TAG = "PhysicLayerBufferString";
    public byte[] readBufferSave;
    public byte[] readBufferSaveTmp;
    public int readBufferSaveNum;
    
    public PhysicLayerBufferString() {
        this.readBufferSave = new byte[1024000];
        this.readBufferSaveTmp = new byte[1024000];
    }
    
    abstract void physicLayerDataReceive(final String p0, final String p1, final BlockingQueue<PhysicLayerPacket> p2);
    
    void _socketReceive(final int sensorID, final InputStreamReader inputStreamReader) {
        char[] buffer = new char[204800];
        int count = 0;
        try {
            while ((count = inputStreamReader.read(buffer)) > 0) {
                final String content = new String(buffer, 0, count);
                final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
                final String timeStr = df.format(new Date());
                PhysicLayer.dataRecord(sensorID, String.valueOf(timeStr) + " " + content);
                this.physicLayerDataReceive(timeStr, content, CarDetectSetting.packetQueue[sensorID]);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
        catch (IOException e2) {
            LMSLog.exception(sensorID, (Throwable)e2);
        }
        buffer = null;
    }
}
