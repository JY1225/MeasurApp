package layer.physicLayer;

import java.util.concurrent.*;
import java.text.*;
import java.util.*;
import CarDetect.*;
import SensorBase.*;
import java.io.*;

public abstract class PhysicLayerBufferByte
{
    private static final String DEBUG_TAG = "PhysicLayerBufferByte";
    public byte[] readBufferSave;
    public byte[] readBufferSaveTmp;
    public int readBufferSaveNum;
    
    public PhysicLayerBufferByte() {
        this.readBufferSave = new byte[1024000];
        this.readBufferSaveTmp = new byte[1024000];
    }
    
    abstract void physicLayerDataReceive(final String p0, final int p1, final byte[] p2, final BlockingQueue<PhysicLayerPacket> p3);
    
    public int fourByte2int(final byte[] res, final int offset) {
        final int targets = (res[offset + 3] & 0xFF) | (res[offset + 2] << 8 & 0xFF00) | res[offset + 1] << 24 >>> 8 | res[offset + 0] << 24;
        return targets;
    }
    
    public static final String bytesToHexString(final byte[] bArray) {
        final StringBuffer sb = new StringBuffer(bArray.length);
        for (int i = 0; i < bArray.length; ++i) {
            final String sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp);
        }
        return sb.toString();
    }
    
    public static final String nBytesToHexString(final int num, final byte[] bArray) {
        final StringBuffer sb = new StringBuffer(bArray.length);
        for (int i = 0; i < num; ++i) {
            final String sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp);
        }
        return sb.toString();
    }
    /**
     * 接收数据 readBuffer
    
     */
    @SuppressWarnings("unused")
	void physicLayerBufferByteSocketReceive(final int sensorID, final InputStream inputStream) {
        char[] buffer = new char[204800];
        final int count = 0;
        try {
            final byte[] readBuffer = new byte[204800];
            int numBytes = 0;
            while ((numBytes = inputStream.read(readBuffer)) > 0) {
                final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
                final String timeStr = df.format(new Date());
                //过车录像
                PhysicLayer.dataRecord(sensorID, String.valueOf(timeStr) + " " + nBytesToHexString(numBytes, readBuffer));
                //接收数据 readBuffer
                this.physicLayerDataReceive(timeStr, numBytes, readBuffer, CarDetectSetting.packetQueue[sensorID]);
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
