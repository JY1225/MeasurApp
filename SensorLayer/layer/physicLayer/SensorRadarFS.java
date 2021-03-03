package layer.physicLayer;

import java.util.*;
import SensorBase.*;
import java.util.concurrent.*;
import java.io.*;

public class SensorRadarFS extends PhysicLayerBufferByte
{
    private static final String DEBUG_TAG = "SensorRadarFS";
    protected int _sensorID;
    OutputStream _outputStream;
    private static Timer beaEnterSettingModeTimer;
    private TimerTask beaEnterSettingModeTimerTask;
    int PLANE_NUM;
    byte[] byteBeaEnterSettingMode;
    byte[] byteBeaEnterSettingModeRsp;
    byte[] byteBeaEnterMeasureMode;
    byte[] byteBeaClosefixLed;
    byte[] byteBeaOpenfixLed;
    boolean bFixLedMode;
    
    static {
        SensorRadarFS.beaEnterSettingModeTimer = new Timer();
    }
    
    public SensorRadarFS(final int sensorID, final OutputStream outputStream) {
        this.PLANE_NUM = 1;
        this.byteBeaEnterSettingMode = new byte[] { -91 };
        this.byteBeaEnterSettingModeRsp = new byte[] { -4, -3, -2, -1, 3, 0, 82, -61, 2, 23, 1 };
        this.byteBeaEnterMeasureMode = new byte[] { -4, -3, -2, -1, 3, 0, 81, -61, 1, 21, 1 };
        this.byteBeaClosefixLed = new byte[] { -4, -3, -2, -1, 3, 0, 89, -61, 0, 28, 1 };
        this.byteBeaOpenfixLed = new byte[] { -4, -3, -2, -1, 3, 0, 89, -61, 1, 29, 1 };
        this._sensorID = sensorID;
        this._outputStream = outputStream;
    }
    
    void socketReceive(final int sensorID, final InputStream inputStream) {
        LMSLog.d("SensorRadarFS" + sensorID, "RadarB socket connected");
        this.physicLayerBufferByteSocketReceive(sensorID, inputStream);
    }
    
    int getPacketLength() {
        int packetLength = 0;
        if (LMSConstValue.bFixLed[this._sensorID]) {
            packetLength = 11;
        }
        else {
            packetLength = 360;
            this.PLANE_NUM = 1;
        }
        return packetLength;
    }
    
    int parsePacket(final boolean bFirstReceived, final String timeStr, final int packetLength, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        int endIndex;
        if (LMSConstValue.bFixLed[this._sensorID]) {
            endIndex = this.parseBeaSetting(packetLength);
        }
        else {
            endIndex = this.parseBeaFS_Mdi_byte(bFirstReceived, timeStr, packetLength, this.PLANE_NUM, packetQueue);
        }
        return endIndex;
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
    
    void beaEnterSettingModeTrig() {
        if (SensorRadarFS.beaEnterSettingModeTimer != null) {
            if (this.beaEnterSettingModeTimerTask != null) {
                this.beaEnterSettingModeTimerTask.cancel();
            }
            this.beaEnterSettingModeTimerTask = new beaEnterSettingModeTimerOutTask();
            SensorRadarFS.beaEnterSettingModeTimer.schedule(this.beaEnterSettingModeTimerTask, 100L, 100L);
        }
    }
    
    void beaEnterSettingMode() {
        try {
            this._outputStream.write(this.byteBeaEnterSettingMode);
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
    }
    
    void beaEnterMeasureMode() {
        try {
            this._outputStream.write(this.byteBeaEnterMeasureMode);
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
    }
    
    void beaCloseFixLed() {
        try {
            this._outputStream.write(this.byteBeaClosefixLed);
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
    }
    
    void beaOpenFixLed() {
        LMSLog.d("SensorRadarFS", "beaOpenFixLed");
        try {
            this._outputStream.write(this.byteBeaOpenfixLed);
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
    }
    
    int parseBeaSetting(final int packetLength) {
        boolean bFixLedModeTmp = false;
        int endIndex = packetLength;
        for (int i = 0; i <= this.readBufferSaveNum - packetLength; ++i) {
            endIndex = i + packetLength;
            bFixLedModeTmp = true;
            for (int j = 0; j < packetLength; ++j) {
                if (this.readBufferSave[i + j] != this.byteBeaEnterSettingModeRsp[j]) {
                    bFixLedModeTmp = false;
                    break;
                }
            }
            if (bFixLedModeTmp) {
                break;
            }
        }
        LMSLog.d("SensorRadarFS", "bFixLedModeTmp=" + bFixLedModeTmp);
        if (!bFixLedModeTmp && !this.bFixLedMode) {
            this.bFixLedMode = false;
        }
        else {
            this.bFixLedMode = true;
        }
        LMSLog.d("SensorRadarFS", "bFixLedMode=" + this.bFixLedMode);
        return endIndex;
    }
    
    int parseBeaFS_Mdi_byte(final boolean bFirstReceived, final String sTimeOfReceived, final int packetLength, final int PLANE_NUM, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        for (int i = 0; i <= this.readBufferSaveNum - packetLength; ++i) {
            if (this.readBufferSave[i + 0] == -66 && this.readBufferSave[i + 1] == -96 && this.readBufferSave[i + 2] == 18 && this.readBufferSave[i + 3] == 52) {
                if (PhysicLayerPacket.iPortHasValidData[this._sensorID] < 5) {
                    final int[] iPortHasValidData = PhysicLayerPacket.iPortHasValidData;
                    final int sensorID = this._sensorID;
                    ++iPortHasValidData[sensorID];
                }
                final byte[] bytePacket = new byte[360];
                System.arraycopy(this.readBufferSave, i, bytePacket, 0, packetLength);
                PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, null, bytePacket);
                return i + packetLength;
            }
        }
        return 0;
    }
    
    class beaEnterSettingModeTimerOutTask extends TimerTask
    {
        @Override
        public void run() {
            if (LMSConstValue.bFixLed[SensorRadarFS.this._sensorID]) {
                if (!SensorRadarFS.this.bFixLedMode) {
                    SensorRadarFS.this.beaEnterSettingMode();
                }
                else {
                    SensorRadarFS.this.beaOpenFixLed();
                }
            }
            else {
                SensorRadarFS.this.beaEnterSettingModeTimerTask.cancel();
            }
        }
    }
}
