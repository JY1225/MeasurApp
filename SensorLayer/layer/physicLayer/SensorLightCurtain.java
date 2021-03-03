package layer.physicLayer;

import java.io.*;
import java.util.concurrent.*;
import SensorBase.*;

public class SensorLightCurtain extends PhysicLayerBufferByte
{
    String DEBUG_TAG;
    protected int _sensorID;
    
    public SensorLightCurtain(final int sensorID) {
        this.DEBUG_TAG = "PhysicLayerLightCurtain";
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void socketReceive(final int sensorID, final InputStream inputStream) {
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "LightCurtain socket connected");
        this.physicLayerBufferByteSocketReceive(sensorID, inputStream);
    }
    
    int getPacketLength() {
        return 20;
    }
    
    public static final int evalCRC16(final byte[] data) {
        int CRC = 65535;
        for (int IX = 0; IX < data.length; ++IX) {
            CRC ^= (data[IX] & 0xFF);
            for (int IY = 0; IY <= 7; ++IY) {
                if ((CRC & 0x1) != 0x0) {
                    CRC = (CRC >> 1 ^ 0xA001);
                }
                else {
                    CRC >>= 1;
                }
            }
        }
        return CRC;
    }
    
    int parsePacket(final boolean bFirstReceived, final String sTimeOfReceived, final int packetLength, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        for (int i = 0; i <= this.readBufferSaveNum - packetLength; ++i) {
            if (this.readBufferSave[i + 0] == 3 && this.readBufferSave[i + 1] == packetLength) {
                final byte[] bytePacket = new byte[packetLength - 2];
                System.arraycopy(this.readBufferSave, i, bytePacket, 0, packetLength - 2);
                final int crc = evalCRC16(bytePacket);
                final int crcGet = ((this.readBufferSave[i + packetLength - 1] & 0xFF) << 8) + (this.readBufferSave[i + packetLength - 2] & 0xFF);
                if (crc == crcGet) {
                    if (PhysicLayerPacket.iPortHasValidData[this._sensorID] < 5) {
                        final int[] iPortHasValidData = PhysicLayerPacket.iPortHasValidData;
                        final int sensorID = this._sensorID;
                        ++iPortHasValidData[sensorID];
                    }
                    System.arraycopy(this.readBufferSave, i + LMSConstValue.LIGHT_CURTAIN_TIME_STAMP_START, bytePacket, 0, LMSConstValue.LIGHT_CURTAIN_TIME_STAMP_LENGTH + LMSConstValue.LIGHT_CURTAIN_DATA_LENGTH);
                    PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, null, bytePacket);
                    return i + packetLength;
                }
                LMSLog.d(this.DEBUG_TAG, "light curtain crc error! crc=" + crc + " crcGet=" + crcGet);
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
