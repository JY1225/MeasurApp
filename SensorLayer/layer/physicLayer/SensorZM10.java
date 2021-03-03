package layer.physicLayer;

import java.util.concurrent.*;

public class SensorZM10
{
    String DEBUG_TAG;
    protected int _sensorID;
    
    public SensorZM10(final int sensorID) {
        this.DEBUG_TAG = "SensorZM10";
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void physicLayetDataReceive(final String sTimeOfReceived, final String content, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        final int iLastByteEnd = content.indexOf(93, 0);
        final int iLastByteBegin = content.lastIndexOf(44) + 1;
        final String tmp = content.substring(0, iLastByteBegin - 1);
        final int iFirstByteEnd = tmp.length();
        final int iFirstByteBegin = tmp.lastIndexOf(44, iFirstByteEnd) + 1;
        final String firstByte = content.substring(iFirstByteBegin, iFirstByteEnd);
        final String lastByte = content.substring(iLastByteBegin, iLastByteEnd);
        final int iLPFirstValue = Integer.valueOf(firstByte, 16);
        final int iLPLastValue = Integer.valueOf(lastByte, 16);
        final byte[] bytePacket = { (byte)(iLPLastValue & 0xFF), (byte)(iLPFirstValue & 0xFF) };
        final boolean bFirstReceived = true;
        PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, null, bytePacket);
    }
}
