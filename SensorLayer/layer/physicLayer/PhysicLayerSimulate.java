package layer.physicLayer;

import java.text.*;
import layer.algorithmLayer.*;
import CarDetect.*;
import java.util.concurrent.*;
import java.io.*;
import CarAppAlgorithm.*;
import SensorBase.*;
import lmsEvent.*;
import java.util.*;

public class PhysicLayerSimulate extends PhysicLayerPacket
{
    private String DEBUG_TAG;
    SensorPS_16I[] sensorPS_16I;
    SensorZM10[] sensorZM10;
    SensorRadarB[] sensorRadarB;
    SensorRadarFS[] sensorRadarFS;
    SensorLightCurtain[] sensorLightCurtain;
    SensorXZY[] sensorXZY;
    SensorLMS4XX[] sensorLMS4XX;
    SensorVMD5XX[] sensorVMD5XX;
    SensorVMD5XX_F[] sensorVMD5XX_F;
    SensorLMS_1XX_5XX[] sensorLMS_1XX_5XX;
    int sensorID;
    public static double[] SIMULATE_X_ANGLE_OFFSET;
    public static double[] SIMULATE_Y_ANGLE_OFFSET;
    public static double[] SIMULATE_Z_ANGLE_OFFSET;
    public static int SIMULATE_WH_POSITION;
    public static int SIMULATE_GROUND_HEIGHT;
    public static int SIMULATE_CAR_HEIGHT;
    public static int SIMULATE_CAR_LENGTH;
    public static int SIMULATE_CAR_WIDTH;
    public static int[] SIMULATE_CAR_WIDTH_START;
    public static int SIMULATE_CAR_LENGTH_START;
    public static int SIMULATE_CAR_SPEED;
    public static int simulateCarLengthStart;
    public static boolean bSimulateWHCarIn;
    boolean bSimulateLessToBig;
    SimpleDateFormat df;
    File file;
    BufferedReader bufferedReader;
    boolean bGetDataFile;
    public int iDataRecordReplayBeginLine;
    static long[] logTime;
    static long[] logTimeNext;
    public static LMSToken tokenLogtime;
    Date d;
    boolean bReset;
    String packet;
    String packetNext;
    static boolean bLineOK;
    
    static {
        PhysicLayerSimulate.SIMULATE_X_ANGLE_OFFSET = new double[] { 0.0, 0.0, 0.0 };
        PhysicLayerSimulate.SIMULATE_Y_ANGLE_OFFSET = new double[] { 0.0, 0.0, 0.0 };
        PhysicLayerSimulate.SIMULATE_Z_ANGLE_OFFSET = new double[] { 0.0, 0.0, 0.0 };
        PhysicLayerSimulate.SIMULATE_GROUND_HEIGHT = 5000;
        PhysicLayerSimulate.SIMULATE_CAR_HEIGHT = 1400;
        PhysicLayerSimulate.SIMULATE_CAR_LENGTH = 6000;
        PhysicLayerSimulate.SIMULATE_CAR_WIDTH = 1600;
        PhysicLayerSimulate.SIMULATE_CAR_WIDTH_START = new int[] { 500, -1000 };
        PhysicLayerSimulate.SIMULATE_CAR_LENGTH_START = 8000;
        PhysicLayerSimulate.SIMULATE_CAR_SPEED = 2000;
        PhysicLayerSimulate.bSimulateWHCarIn = true;
        PhysicLayerSimulate.logTime = new long[40];
        PhysicLayerSimulate.logTimeNext = new long[40];
        PhysicLayerSimulate.tokenLogtime = new LMSToken();
        PhysicLayerSimulate.bLineOK = false;
    }
    
    public PhysicLayerSimulate(final int _sensorID) {
        this.DEBUG_TAG = "PhysicLayerSimulate";
        this.sensorPS_16I = new SensorPS_16I[40];
        this.sensorZM10 = new SensorZM10[40];
        this.sensorRadarB = new SensorRadarB[40];
        this.sensorRadarFS = new SensorRadarFS[40];
        this.sensorLightCurtain = new SensorLightCurtain[40];
        this.sensorXZY = new SensorXZY[40];
        this.sensorLMS4XX = new SensorLMS4XX[40];
        this.sensorVMD5XX = new SensorVMD5XX[40];
        this.sensorVMD5XX_F = new SensorVMD5XX_F[40];
        this.sensorLMS_1XX_5XX = new SensorLMS_1XX_5XX[40];
        this.bSimulateLessToBig = false;
        this.df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        this.bGetDataFile = true;
        this.bReset = false;
        this.sensorID = _sensorID;
        LMSLog.d(this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this.sensorID, "_sensorID=" + _sensorID);
    }
    
    public void logFileSync(final int sensorID, final String packet, final String packetNext) {
        synchronized (PhysicLayerSimulate.tokenLogtime) {
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
                if (packet != null && !packet.equals("")) {
                    this.d = sdf.parse(packet.substring(0, 23));
                    PhysicLayerSimulate.logTime[sensorID] = this.d.getTime();
                }
                if (packetNext != null && !packetNext.equals("")) {
                    this.d = sdf.parse(packetNext.substring(0, 23));
                    PhysicLayerSimulate.logTimeNext[sensorID] = this.d.getTime();
                }
            }
            catch (Exception e) {
                LMSLog.d(this.DEBUG_TAG, "INVALID PACKET=" + packet);
                LMSLog.exception(sensorID, (Throwable)e);
            }
            boolean bLoop = true;
            boolean bSleep = false;
            while (bLoop) {
                bLoop = false;
                for (int i = 0; i < 40; ++i) {
                    if (i != sensorID) {
                        if (i >= LMSConstValue.iRadarSensorNum) {
                            if (i != 10) {
                                continue;
                            }
                            if (!LMSConstValue.getSensorType(i).equals("LIGHT_CURTAIN") && !LMSConstValue.getSensorType(i).equals("XZY_2") && !LMSConstValue.getSensorType(i).equals("XZY_840")) {
                                if (!LMSConstValue.getSensorType(i).equals("XZY_1600")) {
                                    continue;
                                }
                            }
                        }
                        try {
                            if (PhysicLayerSimulate.logTime[sensorID] > PhysicLayerSimulate.logTime[i] || PhysicLayerSimulate.logTime[sensorID] > PhysicLayerSimulate.logTimeNext[i]) {
                                if (!bSleep) {
                                    bSleep = true;
                                    PhysicLayerSimulate.tokenLogtime.notifyAll();
                                }
                                if (this.bReset) {
                                    this.bReset = false;
                                    bLoop = false;
                                }
                                else {
                                    bLoop = true;
                                }
                                PhysicLayerSimulate.tokenLogtime.wait();
                            }
                        }
                        catch (InterruptedException e2) {
                            LMSLog.exception(sensorID, (Throwable)e2);
                        }
                    }
                }
            }
        }
        // monitorexit(PhysicLayerSimulate.tokenLogtime)
    }
    
    void getFromFile(final int sensorID) {
        if (LMSConstValue.bDataReplay) {
            try {
                if (this.bGetDataFile) {
                    this.bGetDataFile = false;
                    if (sensorID == 10) {
                        this.file = new File("lightCurtain0.data");
                    }
                    else {
                        this.file = new File(String.valueOf(sensorID) + ".data");
                        ParseLMSAckCommand.telegramCounterLast[sensorID] = -1;
                        ParseLMSAckCommand.telegramCounterLost[sensorID] = 0;
                        if (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID] != null) {
                            CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID].lTimeOfReceivedLast = 0L;
                        }
                    }
                    if (this.file.exists()) {
                        this.bufferedReader = new BufferedReader(new FileReader(this.file));
                    }
                    this.packet = null;
                    this.packetNext = null;
                }
                ++this.iDataRecordReplayBeginLine;
                this.packet = this.packetNext;
                if (this.bufferedReader != null) {
                    this.packetNext = this.bufferedReader.readLine();
                }
                this.logFileSync(sensorID, this.packet, this.packetNext);
                if (sensorID == 0 && this.iDataRecordReplayBeginLine >= LMSConstValue.iNvramReplayLine.iValue) {
                    PhysicLayerSimulate.bLineOK = true;
                    try {
                        if (sensorID == 0) {
                            Thread.sleep(LMSConstValue.iNvramSimulateInteval.iValue);
                        }
                    }
                    catch (InterruptedException e) {
                        LMSLog.exception(sensorID, (Throwable)e);
                    }
                }
                if (!PhysicLayerSimulate.bLineOK) {
                    return;
                }
                final PhysicLayerPacket physicLayerPacket = new PhysicLayerPacket();
                if (this.packet != null && !this.packet.equals("")) {
                    try {
                        physicLayerPacket.sTimeOfReceived = this.packet.substring(0, 23);
                        if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B")) {
                            if (LMSConstValue.bProtocolOld_radarB) {
                                physicLayerPacket.stringPacket = this.packet.substring(24);
                                PhysicLayerPacket.packetQueuePut(CarDetectSetting.packetQueue[sensorID], true, physicLayerPacket.sTimeOfReceived, physicLayerPacket.stringPacket, null);
                            }
                            else {
                                if (this.sensorRadarB[sensorID] == null) {
                                    this.sensorRadarB[sensorID] = new SensorRadarB(sensorID, null);
                                }
                                physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                                this.sensorRadarB[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.bytePacket.length, physicLayerPacket.bytePacket, CarDetectSetting.packetQueue[sensorID]);
                            }
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("RADAR_FS")) {
                            if (this.sensorRadarFS[sensorID] == null) {
                                this.sensorRadarFS[sensorID] = new SensorRadarFS(sensorID, null);
                            }
                            physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                            this.sensorRadarFS[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.bytePacket.length, physicLayerPacket.bytePacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("LMS400")) {
                            if (this.sensorLMS4XX[sensorID] == null) {
                                this.sensorLMS4XX[sensorID] = new SensorLMS4XX(sensorID);
                            }
                            physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                            this.sensorLMS4XX[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.bytePacket.length, physicLayerPacket.bytePacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("VMD500")) {
                            if (this.sensorVMD5XX[sensorID] == null) {
                                this.sensorVMD5XX[sensorID] = new SensorVMD5XX(sensorID);
                            }
                            physicLayerPacket.stringPacket = this.packet.substring(24);
                            this.sensorVMD5XX[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.stringPacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("VMD500_F")) {
                            if (this.sensorVMD5XX_F[sensorID] == null) {
                                this.sensorVMD5XX_F[sensorID] = new SensorVMD5XX_F(sensorID);
                            }
                            physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                            this.sensorVMD5XX_F[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.bytePacket.length, physicLayerPacket.bytePacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("LIGHT_CURTAIN")) {
                            if (this.sensorLightCurtain[sensorID] == null) {
                                this.sensorLightCurtain[sensorID] = new SensorLightCurtain(sensorID);
                            }
                            physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                            this.sensorLightCurtain[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.bytePacket.length, physicLayerPacket.bytePacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("XZY_2") || LMSConstValue.getSensorType(sensorID).equals("XZY_840") || LMSConstValue.getSensorType(sensorID).equals("XZY_1600")) {
                            if (this.sensorXZY[sensorID] == null) {
                                this.sensorXZY[sensorID] = new SensorXZY(sensorID);
                            }
                            physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                            this.sensorXZY[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.bytePacket.length, physicLayerPacket.bytePacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("ZM10")) {
                            if (LMSConstValue.bProtocolOld_ZM10) {
                                physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                                PhysicLayerPacket.packetQueuePut(CarDetectSetting.packetQueue[sensorID], true, physicLayerPacket.sTimeOfReceived, null, physicLayerPacket.bytePacket);
                            }
                            else {
                                if (this.sensorZM10[sensorID] == null) {
                                    this.sensorZM10[sensorID] = new SensorZM10(sensorID);
                                }
                                physicLayerPacket.stringPacket = this.packet.substring(24);
                                this.sensorZM10[sensorID].physicLayetDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.stringPacket, CarDetectSetting.packetQueue[sensorID]);
                            }
                        }
                        else if (LMSConstValue.getSensorType(sensorID).equals("PS_16I")) {
                            if (this.sensorPS_16I[sensorID] == null) {
                                this.sensorPS_16I[sensorID] = new SensorPS_16I(sensorID);
                            }
                            physicLayerPacket.bytePacket = hexStringToBytes(this.packet.substring(24));
                            this.sensorPS_16I[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.bytePacket.length, physicLayerPacket.bytePacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                        else {
                            if (this.sensorLMS_1XX_5XX[sensorID] == null) {
                                this.sensorLMS_1XX_5XX[sensorID] = new SensorLMS_1XX_5XX(sensorID);
                            }
                            physicLayerPacket.stringPacket = this.packet.substring(24);
                            this.sensorLMS_1XX_5XX[sensorID].physicLayerDataReceive(physicLayerPacket.sTimeOfReceived, physicLayerPacket.stringPacket, CarDetectSetting.packetQueue[sensorID]);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e2) {
                        LMSLog.exception(sensorID, (Throwable)e2);
                    }
                    return;
                }
                try {
                    Thread.sleep(LMSConstValue.iNvramSimulateInteval.iValue);
                }
                catch (InterruptedException e3) {
                    LMSLog.exception(sensorID, (Throwable)e3);
                }
                return;
            }
            catch (IOException e4) {
                LMSLog.exception(sensorID, (Throwable)e4);
                return;
            }
        }
        for (int i = 0; i < 40; ++i) {
            PhysicLayerSimulate.logTime[i] = 0L;
            PhysicLayerSimulate.logTimeNext[i] = 0L;
        }
        synchronized (PhysicLayerSimulate.tokenLogtime) {
            PhysicLayerSimulate.tokenLogtime.notifyAll();
        }
        // monitorexit(PhysicLayerSimulate.tokenLogtime)
        this.iDataRecordReplayBeginLine = 0;
        PhysicLayerSimulate.bLineOK = false;
        this.bGetDataFile = true;
        if (sensorID == 0 && CarDetectSetting.carDetectSetting.widthHeightDetectRunnable[sensorID] != null) {
            synchronized (WidthHeightDetectRunnable.tokenAlogrithm) {
                WidthHeightDetectRunnable.clear_WH2_data();
            }
            // monitorexit(WidthHeightDetectRunnable.tokenAlogrithm)
        }
        try {
            Thread.sleep(LMSConstValue.iNvramSimulateInteval.iValue);
        }
        catch (InterruptedException e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    private static byte charToByte(final char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
    
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        final int length = hexString.length() / 2;
        final char[] hexChars = hexString.toCharArray();
        final byte[] d = new byte[length];
        for (int i = 0; i < length; ++i) {
            final int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    
    public Runnable thread(final int sensorID) {
        return new Runnable() {
            @Override
            public void run() {
                LMSLog.d(PhysicLayerSimulate.this.DEBUG_TAG, "handlerRunnable thread run:" + Thread.currentThread().getId());
                try {
                    LMSConstValue.bProtocolOld_ZM10 = LMSPlatform.getBooleanPorperty(LMSConstValue.nvramProtocolOld_ZM10, false);
                    LMSConstValue.bProtocolOld_radarB = LMSPlatform.getBooleanPorperty(LMSConstValue.nvramProtocolOld_radarB, false);
                    final EventListener eventListener = new EventListener();
                    LMSEventManager.addListener((LMSEventListener)eventListener);
                    while (true) {
                        if (LMSConstValue.boardType == LMSConstValue.enumBoardType.SIMULATE_FILE_BOARD) {
                            PhysicLayerSimulate.this.getFromFile(sensorID);
                        }
                        else {
                            Thread.sleep(100L);
                        }
                    }
                }
                catch (OutOfMemoryError e) {
                    LMSLog.outOfMemoryDialog((Throwable)e);
                }
                catch (Exception e2) {
                    LMSLog.exceptionDialog((String)null, (Throwable)e2);
                }
            }
        };
    }
    
    private class EventListener implements LMSEventListener
    {
        public void lmsTransferEvent(final LMSEvent event) {
            if (event.getEventType() != null) {
                final String eventType = event.getEventType();
                final HashMap eventExtra = event.getEventExtra();
                if (eventType != null && eventType.equals("lmsApp.intent.action.SETTING_TRANSFER_INTENT")) {
                    final String nvram = (String) eventExtra.get("SETTING_NVRAM");
                    if (nvram.equals(LMSConstValue.nvramDataReplay)) {
                        PhysicLayerSimulate.this.bReset = true;
                        synchronized (PhysicLayerSimulate.tokenLogtime) {
                            PhysicLayerSimulate.tokenLogtime.notifyAll();
                        }
                        // monitorexit(PhysicLayerSimulate.tokenLogtime)
                    }
                }
            }
        }
    }
}
