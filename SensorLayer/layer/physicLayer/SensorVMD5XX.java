package layer.physicLayer;

import java.io.*;
import SensorBase.*;
import CarDetect.*;
import java.util.concurrent.*;
import layer.dataLayer.*;

public class SensorVMD5XX extends PhysicLayerBufferString
{
    private String DEBUG_TAG;
    int _sensorID;
    byte[] VMD5XX_TELE_HEAD_BYTE;
    public static final String VMD5XX_TELE_SERIAL_NUM_REQ = "sRI 3";
    byte[] readBufferSaveTmp;
    byte[][] beaSerialNum;
    String strReceive;
    final int LENGTH_0_125_ANLGE = 2840;
    public int readBufferSaveNum;
    boolean bSerialNumPacketReceived;
    
    public SensorVMD5XX(final int sensorID) {
        this.DEBUG_TAG = "SensorVMD5XX";
        this.VMD5XX_TELE_HEAD_BYTE = new byte[] { 2, 2, 2, 2 };
        this.readBufferSaveTmp = new byte[1024000];
        this.beaSerialNum = new byte[10][4];
        this.bSerialNumPacketReceived = false;
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void socketReceive(final int sensorID, final InputStreamReader inputStreamReader) {
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "VMD5XX socket connected");
        this.SerialNumberPool();
        this.SerialPermReq();
        this._socketReceive(sensorID, inputStreamReader);
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
        this.lmsSendProtocol("sMI 0 3 F4724744", (byte)32);
        this.lmsSendProtocol("sMI 61", (byte)80);
        this.lmsSendProtocol("sMI 2", (byte)101);
        this.lmsSendProtocol("sEI A 1", (byte)15);
        this.lmsSendProtocol("sRI C9", (byte)50);
    }
    
    public void physicLayerDataReceive(final String sTimeOfReceived, final String content, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        try {
            if (content.startsWith("\u0002\u0002\u0002\u0002")) {
                this.strReceive = content;
            }
            else {
                this.strReceive = String.valueOf(this.strReceive) + content;
            }
            if (!this.bSerialNumPacketReceived && content.length() == 31 && content.substring(8, 11).equals("sRA")) {
                this.bSerialNumPacketReceived = true;
                LMSTelegram.serialNumber[this._sensorID] = content.substring(22, 30);
            }
            final boolean bFirstReceived = true;
            if (this.strReceive.length() >= 2840 && this.strReceive.substring(8, 11).equals("sSI")) {
                final String stringPacket = this.strReceive.substring(40, 2839);
                PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, stringPacket, null);
            }
        }
        catch (Exception e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
    }
}
