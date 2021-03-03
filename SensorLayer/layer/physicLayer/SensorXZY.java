package layer.physicLayer;

import java.io.*;
import SensorBase.*;
import java.util.concurrent.*;

public class SensorXZY extends PhysicLayerBufferByte
{
    String DEBUG_TAG;
    protected int _sensorID;
    
    public SensorXZY(final int sensorID) {
        this.DEBUG_TAG = "SensorXZY";
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void socketReceive(final int sensorID, final InputStream inputStream) {
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "XZY socket connected");
        this.physicLayerBufferByteSocketReceive(sensorID, inputStream);
    }
    
    int getPacketLength() {
        if (LMSConstValue.getSensorType(this._sensorID).equals("XZY_2") || LMSConstValue.getSensorType(this._sensorID).equals("XZY_840")) {
            return 10;
        }
        if (LMSConstValue.getSensorType(this._sensorID).equals("XZY_1600")) {
            return 18;
        }
        return 10;
    }
    
    int parsePacket(final boolean bFirstReceived, final String sTimeOfReceived, final int packetLength, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        for (int i = 0; i <= this.readBufferSaveNum - packetLength; ++i) {
            if (this.readBufferSave[i + 0] == -86 && this.readBufferSave[i + 1] == -86) {
                if (PhysicLayerPacket.iPortHasValidData[this._sensorID] < 5) {
                    final int[] iPortHasValidData = PhysicLayerPacket.iPortHasValidData;
                    final int sensorID = this._sensorID;
                    ++iPortHasValidData[sensorID];
                }
                final byte[] bytePacket = new byte[packetLength - 2];
                System.arraycopy(this.readBufferSave, i + 2, bytePacket, 0, packetLength - 2);
                PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, null, bytePacket);
                return i + packetLength;
            }
        }
        return 0;
    }
    
    @Override
    void physicLayerDataReceive(final String timeStr, final int numBytes, final byte[] readBuffer, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        if (PhysicLayerPacket.iPortHasData[this._sensorID] < 20) {
            final int[] iPortHasData = PhysicLayerPacket.iPortHasData;
            final int sensorID = this._sensorID;
            ++iPortHasData[sensorID];
        }
        System.arraycopy(readBuffer, 0, this.readBufferSave, this.readBufferSaveNum, numBytes);
        this.readBufferSaveNum += numBytes;
        final int packetLength = this.getPacketLength();
        boolean bFirstReceived = true;
        while (this.readBufferSaveNum >= packetLength) {
            final int iGetNum = this.parsePacket(bFirstReceived, timeStr, packetLength, packetQueue);
            bFirstReceived = false;
            if (iGetNum <= 0) {
                break;
            }
            this.readBufferSaveNum -= iGetNum;
            if (this.readBufferSaveNum <= 0) {
                continue;
            }
            System.arraycopy(this.readBufferSave, iGetNum, this.readBufferSaveTmp, 0, this.readBufferSaveNum);
            System.arraycopy(this.readBufferSaveTmp, 0, this.readBufferSave, 0, this.readBufferSaveNum);
        }
    }
}
