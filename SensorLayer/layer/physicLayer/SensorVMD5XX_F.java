package layer.physicLayer;

import java.io.*;
import SensorBase.*;
import CarDetect.*;
import java.util.concurrent.*;
import layer.dataLayer.*;

public class SensorVMD5XX_F extends PhysicLayerBufferByte
{
    private String DEBUG_TAG;
    int _sensorID;
    byte[] VMD5XX_TELE_HEAD_BYTE;
    public static final String VMD5XX_TELE_SERIAL_NUM_REQ = "sRI 3";
    byte[] readBufferSaveTmp;
    byte[][] beaSerialNum;
    String strReceive;
    public int readBufferSaveNum;
    final int SERIAL_NUMBER_POOL_ACK_LENGTH = 22;
    final int SERIAL_NUMBER_POOL_ACK_CMD_LENGTH = 14;
    boolean bSerialNumPacketReceived;
    
    public SensorVMD5XX_F(final int sensorID) {
        this.DEBUG_TAG = "SensorVMD5XX";
        this.VMD5XX_TELE_HEAD_BYTE = new byte[] { 2, 2, 2, 2 };
        this.readBufferSaveTmp = new byte[1024000];
        this.beaSerialNum = new byte[10][4];
        this.bSerialNumPacketReceived = false;
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void socketReceive(final int sensorID, final InputStream inputStream) {
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "socket connected");
        this.SerialNumberPool();
        this.SerialPermReq();
        this.physicLayerBufferByteSocketReceive(sensorID, inputStream);
    }
    
    public static byte[] intToByteArray1(final int i) {
        final byte[] result = { (byte)(i >> 24 & 0xFF), (byte)(i >> 16 & 0xFF), (byte)(i >> 8 & 0xFF), (byte)(i & 0xFF) };
        return result;
    }
    
    void lmsSendProtocol(final String cmd, final byte crc) {
        final int iByteBufferLength = this.VMD5XX_TELE_HEAD_BYTE.length + 4 + cmd.length() + 1;
        final byte[] byteBuffer = new byte[iByteBufferLength];
        System.arraycopy(this.VMD5XX_TELE_HEAD_BYTE, 0, byteBuffer, 0, 4);
        System.arraycopy(intToByteArray1(cmd.length()), 0, byteBuffer, 4, 4);
        System.arraycopy(cmd.getBytes(), 0, byteBuffer, 8, cmd.length());
        byteBuffer[iByteBufferLength - 1] = crc;
        CarDetectSetting.carDetectSetting.physicLayerSocket[this._sensorID].socketSendRaw(byteBuffer);
    }
    
    void SerialNumberPool() {
        this.lmsSendProtocol("sRI 3", (byte)0);
    }
    
    void SerialPermReq() {
        this.lmsSendProtocol("sMN mLRreqdata 1020", (byte)118);
    }
    
    @Override
    void physicLayerDataReceive(final String timeStr, final int numBytes, final byte[] readBuffer, final BlockingQueue<PhysicLayerPacket> packetQueue) {
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
    
    int getPacketLength() {
        return 1759;
    }
    
    int parsePacket(final boolean bFirstReceived, final String sTimeOfReceived, final int packetLength, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        for (int i = 0; i <= this.readBufferSaveNum - packetLength; ++i) {
            if (this.readBufferSave[i + 0] == 2 && this.readBufferSave[i + 1] == 2 && this.readBufferSave[i + 2] == 2 && this.readBufferSave[i + 3] == 2) {
                final int frameStart = i + 8;
                if (!this.bSerialNumPacketReceived && packetLength == 22 && this.readBufferSave[i + 8] == 115 && this.readBufferSave[i + 9] == 82 && this.readBufferSave[i + 10] == 65) {
                    this.bSerialNumPacketReceived = true;
                    LMSTelegram.serialNumber[this._sensorID] = String.valueOf(String.valueOf(this.readBufferSave[frameStart + 14 + 0] - 48)) + String.valueOf(this.readBufferSave[frameStart + 14 + 1] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 2] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 3] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 4] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 5] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 6] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 7] - 48);
                    LMSLog.d(this.DEBUG_TAG, "sSerialNum=" + LMSTelegram.serialNumber[this._sensorID]);
                }
                if (this.readBufferSaveNum < packetLength) {
                    LMSLog.d(this.DEBUG_TAG, "not a frame" + sTimeOfReceived);
                    return -1;
                }
                final int dataLength = this.fourByte2int(this.readBufferSave, i + 4);
                final byte checkXOR = this.readBufferSave[frameStart + dataLength];
                int xor = this.readBufferSave[frameStart];
                for (int c = frameStart + 1; c < frameStart + dataLength; ++c) {
                    xor ^= this.readBufferSave[c];
                }
                if (xor == checkXOR) {
                    final byte[] bytePacket = new byte[packetLength];
                    System.arraycopy(this.readBufferSave, i, bytePacket, 0, packetLength);
                    PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, null, bytePacket);
                    return i + packetLength;
                }
                LMSLog.d(this.DEBUG_TAG, "error xor!!!");
            }
        }
        return 0;
    }
}
