package layer.physicLayer;

import java.io.*;
import SensorBase.*;
import java.util.concurrent.*;

public class SensorPS_16I extends PhysicLayerBufferByte
{
    String DEBUG_TAG;
    protected int _sensorID;
    
    public SensorPS_16I(final int sensorID) {
        this.DEBUG_TAG = "SensorZm10_socket";
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void socketReceive(final int sensorID, final InputStream inputStream) {
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "PS_16I socket connected");
        this.physicLayerBufferByteSocketReceive(sensorID, inputStream);
    }
    
    int getPacketLength() {
        return 13;
    }
    
    int parsePacket(final boolean bFirstReceived, final String sTimeOfReceived, final int packetLength, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        for (int i = 0; i <= this.readBufferSaveNum - packetLength; ++i) {
            if (this.readBufferSave[i + 4] == 0 && this.readBufferSave[i + 5] == 7 && this.readBufferSave[i + 6] == 1 && this.readBufferSave[i + 7] == 2 && this.readBufferSave[i + 8] == 4) {
                if (PhysicLayerPacket.iPortHasValidData[this._sensorID] < 5) {
                    final int[] iPortHasValidData = PhysicLayerPacket.iPortHasValidData;
                    final int sensorID = this._sensorID;
                    ++iPortHasValidData[sensorID];
                }
                final byte[] bytePacket = { (byte)(this.readBufferSave[10] & 0xFF), (byte)(this.readBufferSave[9] & 0xFF), (byte)(this.readBufferSave[11] & 0xFF), (byte)(this.readBufferSave[12] & 0xFF) };
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
