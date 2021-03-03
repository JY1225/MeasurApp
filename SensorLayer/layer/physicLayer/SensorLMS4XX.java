package layer.physicLayer;

import java.io.*;
import SensorBase.*;
import CarDetect.*;
import java.util.concurrent.*;
import layer.dataLayer.*;

public class SensorLMS4XX extends PhysicLayerBufferByte
{
    private String DEBUG_TAG;
    int _sensorID;
    byte[] LMS400_TELE_HEAD_BYTE;
    public static final String LMS400_TELE_SERIAL_NUM_REQ = "sRI 3";
    public static final String LMS400_TELE_SEND_PERM_REQ = "sMN mLRreqdata 0021";
    byte[] byteSerialNumberPoolAck;
    final int LMS4XX_SERIAL_NUMBER_POOL_ACK_LENGTH = 22;
    final int LMS4XX_SERIAL_NUMBER_POOL_ACK_CMD_LENGTH = 14;
    byte[] readBufferSaveTmp;
    byte[][] beaSerialNum;
    final int LMS4XX_PROTOCOL_MIN_LENGTH = 10;
    final int LMS4XX_DATA_LENGTH = 1438;
    
    public SensorLMS4XX(final int sensorID) {
        this.DEBUG_TAG = "SensorLMS4XX";
        this.LMS400_TELE_HEAD_BYTE = new byte[] { 2, 2, 2, 2 };
        this.byteSerialNumberPoolAck = new byte[] { 115, 82, 65, 32, 48, 48, 48, 51, 32, 48, 48, 48, 56, 32 };
        this.readBufferSaveTmp = new byte[1024000];
        this.beaSerialNum = new byte[10][4];
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void socketReceive(final int sensorID, final InputStream inputStream) {
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "SensorLMS4XX socket connected");
        this.SerialNumberPool();
        this.SerialPermReq();
        this.physicLayerBufferByteSocketReceive(sensorID, inputStream);
    }
    
    public static byte[] intToByteArray1(final int i) {
        final byte[] result = { (byte)(i >> 24 & 0xFF), (byte)(i >> 16 & 0xFF), (byte)(i >> 8 & 0xFF), (byte)(i & 0xFF) };
        return result;
    }
    
    void lmsSendProtocol(final String cmd) {
        final int iByteBufferLength = this.LMS400_TELE_HEAD_BYTE.length + 4 + cmd.length() + 1;
        final byte[] byteBuffer = new byte[iByteBufferLength];
        System.arraycopy(this.LMS400_TELE_HEAD_BYTE, 0, byteBuffer, 0, 4);
        System.arraycopy(intToByteArray1(cmd.length()), 0, byteBuffer, 4, 4);
        System.arraycopy(cmd.getBytes(), 0, byteBuffer, 8, cmd.length());
        CarDetectSetting.carDetectSetting.physicLayerSocket[this._sensorID].socketSendRaw(byteBuffer);
    }
    
    void SerialNumberPool() {
        this.lmsSendProtocol("sRI 3");
    }
    
    void SerialPermReq() {
        this.lmsSendProtocol("sMN mLRreqdata 0021");
    }
    
    int getPacketLength() {
        return 10;
    }
    
    @Override
    void physicLayerDataReceive(final String timeStr, final int numBytes, final byte[] readBuffer, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        System.arraycopy(readBuffer, 0, this.readBufferSave, this.readBufferSaveNum, numBytes);
        this.readBufferSaveNum += numBytes;
        boolean bFirstReceived = true;
        final int packetLength = this.getPacketLength();
        while (this.readBufferSaveNum >= packetLength) {
            final int iGetNum = this.parsePacket(bFirstReceived, timeStr, packetQueue);
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
    
    int parsePacket(final boolean bFirstReceived, final String sTimeOfReceived, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        int endIndex = 0;
        for (int i = 0; i <= this.readBufferSaveNum - 10; ++i) {
            if (this.readBufferSave[i + 0] == 2 && this.readBufferSave[i + 1] == 2 && this.readBufferSave[i + 2] == 2 && this.readBufferSave[i + 3] == 2) {
                final int packetLength = this.fourByte2int(this.readBufferSave, i + 4);
                if (this.readBufferSaveNum - 8 - 1 < packetLength) {
                    LMSLog.d(this.DEBUG_TAG, "not a frame" + sTimeOfReceived);
                    return -1;
                }
                final int frameStart = i + 8;
                final byte checkXOR = this.readBufferSave[frameStart + packetLength];
                int xor = this.readBufferSave[frameStart];
                for (int c = frameStart + 1; c < frameStart + packetLength; ++c) {
                    xor ^= this.readBufferSave[c];
                }
                if (xor == checkXOR) {
                    if (packetLength == 22) {
                        boolean bSerialNumberPoolAck = true;
                        for (int c2 = 0; c2 < 14; ++c2) {
                            if ((this.readBufferSave[c2 + frameStart] & 0xFF) != (this.byteSerialNumberPoolAck[c2] & 0xFF)) {
                                bSerialNumberPoolAck = false;
                                break;
                            }
                        }
                        if (bSerialNumberPoolAck) {
                            LMSTelegram.serialNumber[this._sensorID] = String.valueOf(String.valueOf(this.readBufferSave[frameStart + 14 + 0] - 48)) + String.valueOf(this.readBufferSave[frameStart + 14 + 1] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 2] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 3] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 4] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 5] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 6] - 48) + String.valueOf(this.readBufferSave[frameStart + 14 + 7] - 48);
                            LMSLog.d(this.DEBUG_TAG, "sSerialNum=" + LMSTelegram.serialNumber[this._sensorID]);
                        }
                    }
                    else if (packetLength == 1438) {
                        final byte[] bytePacket = new byte[1438];
                        System.arraycopy(this.readBufferSave, i, bytePacket, 0, packetLength);
                        PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, null, bytePacket);
                    }
                    endIndex += packetLength + 8 + 1;
                    return endIndex;
                }
                LMSLog.d(this.DEBUG_TAG, "XOR ERROR");
            }
        }
        return endIndex;
    }
}
