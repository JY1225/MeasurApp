package layer.physicLayer;

import java.text.*;
import SensorBase.*;
import CarDetect.*;
import lmsBase.*;
import java.io.*;
import java.net.*;
import lmsEvent.*;
import java.util.*;

public class PhysicLayerSocket extends PhysicLayerPacket
{
    private String DEBUG_TAG;
    private Socket socket;
    public final int LMS_PORT = 2111;
    protected int _sensorID;
    SensorRadarB sensorRadarB;
    SensorRadarFS sensorRadarFS;
    SensorLightCurtain sensorLightCurtain;
    SensorXZY sensorXZY;
    SensorLMS4XX sensorLMS4XX;
    SensorVMD5XX sensorVMD5XX;
    SensorVMD5XX_F sensorVMD5XX_F;
    SensorLMS_1XX_5XX sensorLMS_1XX_5XX;
    SensorPS_16I sensorPS_16I;
    SensorHS_LED sensorHS_LED;
    SimpleDateFormat df;
    private BufferedReader bufferedReader;
    private InputStreamReader inputStreamReader;
    private InputStream inputStream;
    private OutputStream outputStream;
    public LMSToken tokenSocket;
    
    public PhysicLayerSocket(final int sensorID) {
        this.DEBUG_TAG = "PhysicLayerSocket";
        this.df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        this.tokenSocket = new LMSToken();
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
        final EventListener eventListener = new EventListener();
        LMSEventManager.addListener((LMSEventListener)eventListener);
    }
    
    public Runnable thread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        LMSLog.d(PhysicLayerSocket.this.DEBUG_TAG, "thread run:" + Thread.currentThread().getId());
                        if (LMSConstValue.isValidIP(LMSConstValue.SENSOR_IP[PhysicLayerSocket.this._sensorID]) && (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("RADAR_B") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("RADAR_F") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("RADAR_FS") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("LIGHT_CURTAIN") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("XZY_2") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("XZY_840") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("XZY_1600") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("LMS1XX") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("LMS511") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("TIM551") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("LMS400") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("VMD500") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("VMD500_F") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("PS_16I") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("HS_LED"))) {
                            LMSLog.d(PhysicLayerSocket.this.DEBUG_TAG, "----------------" + LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID));
                            synchronized (CarDetectSetting.tokenSensorID[PhysicLayerSocket.this._sensorID]) {
                                //连接lidar connectSocket------------------------
                            	if (PhysicLayerSocket.this.connectSocket()) {
                                    if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("LMS400")) {
                                        PhysicLayerSocket.this.sensorLMS4XX.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStream);
                                    }
                                    else if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("VMD500")) {
                                        PhysicLayerSocket.this.sensorVMD5XX.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStreamReader);
                                    }
                                    else if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("VMD500_F")) {
                                        PhysicLayerSocket.this.sensorVMD5XX_F.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStream);
                                    }
                                    else if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("RADAR_B")) {
                                        PhysicLayerSocket.this.sensorRadarB.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStream);
                                    }
                                    else if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("RADAR_FS")) {
                                        PhysicLayerSocket.this.sensorRadarFS.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStream);
                                    }
                                    else if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("LIGHT_CURTAIN")) {
                                        PhysicLayerSocket.this.sensorLightCurtain.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStream);
                                    }
                                    else if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("XZY_2") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("XZY_840") || LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("XZY_1600")) {
                                        PhysicLayerSocket.this.sensorXZY.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStream);
                                    }
                                    else if (LMSConstValue.getSensorType(PhysicLayerSocket.this._sensorID).equals("PS_16I")) {
                                        PhysicLayerSocket.this.sensorPS_16I.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStream);
                                    }
                                    else {
                                        PhysicLayerSocket.this.sensorLMS_1XX_5XX.socketReceive(PhysicLayerSocket.this._sensorID, PhysicLayerSocket.this.inputStreamReader);
                                    }
                                }
                                else {
                                    LMSLog.d(PhysicLayerSocket.this.DEBUG_TAG, "socket eeeee");
                                    LMSProductInfo.bValidSerialNumber[PhysicLayerSocket.this._sensorID] = false;
                                    LMSProductInfo.bSensorInit[PhysicLayerSocket.this._sensorID] = false;
                                }
                                LMSConstValue.sendPortConnected(PhysicLayerSocket.this._sensorID, false);
                                // monitorexit(CarDetectSetting.tokenSensorID[this.this$0._sensorID])
                                continue;
                            }
                        }
                        LMSLog.d(PhysicLayerSocket.this.DEBUG_TAG, "socket tokenSocket.wait !!!!!!!!");
                        synchronized (PhysicLayerSocket.this.tokenSocket) {
                            PhysicLayerSocket.this.tokenSocket.wait();
                        }
                        // monitorexit(this.this$0.tokenSocket)
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
    
    public void socketSend(final int sensorID, final String str) {
        try {
            this.outputStream.write(str.getBytes("utf-8"));
        }
        catch (IOException e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public void socketSendRaw(final byte[] str) {
        try {
            this.outputStream.write(str);
        }
        catch (IOException e) {
            LMSLog.exception((Throwable)e);
        }
    }
    
    private boolean connectSocket() {
        try {
            this.socket = new Socket();
            int port = 2111;
            if (LMSConstValue.sensorType[this._sensorID].key.equals("LMS1XX") || LMSConstValue.sensorType[this._sensorID].key.equals("LMS511") || LMSConstValue.sensorType[this._sensorID].key.equals("LMS511")) {
                port = 2111;
            }
            else if (LMSConstValue.getSensorType(this._sensorID).equals("PS_16I")) {
                port = 505;
            }
            else {
                port = LMSConstValue.SENSOR_PORT[this._sensorID];
            }
            LMSLog.d(this.DEBUG_TAG, "try connect socket IP=" + LMSConstValue.SENSOR_IP[this._sensorID] + " port=" + port);
            final SocketAddress remoteAddr = new InetSocketAddress(LMSConstValue.SENSOR_IP[this._sensorID], port);
            this.socket.connect(remoteAddr, 5000);
            if (LMSConstValue.sensorType[this._sensorID].key.equals("LMS1XX") || LMSConstValue.sensorType[this._sensorID].key.equals("LMS511") || LMSConstValue.sensorType[this._sensorID].key.equals("TIM551") || LMSConstValue.sensorType[this._sensorID].key.equals("LMS400") || LMSConstValue.sensorType[this._sensorID].key.equals("VMD500") || LMSConstValue.sensorType[this._sensorID].key.equals("VMD500_F") || LMSConstValue.sensorType[this._sensorID].key.equals("VMS530") || LMSConstValue.sensorType[this._sensorID].key.equals("VMS530")) {
                this.socket.setSoTimeout(30000);
            }
            this.socket.setReceiveBufferSize(512000);
            LMSLog.d(this.DEBUG_TAG, "getReceiveBufferSize=" + this.socket.getReceiveBufferSize());
            this.outputStream = this.socket.getOutputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()), 204800);
            this.inputStream = this.socket.getInputStream();
            this.inputStreamReader = new InputStreamReader(this.inputStream);
            this.sensorRadarB = new SensorRadarB(this._sensorID, this.outputStream);
            this.sensorRadarFS = new SensorRadarFS(this._sensorID, this.outputStream);
            this.sensorLightCurtain = new SensorLightCurtain(this._sensorID);
            this.sensorXZY = new SensorXZY(this._sensorID);
            this.sensorPS_16I = new SensorPS_16I(this._sensorID);
            this.sensorHS_LED = new SensorHS_LED(this._sensorID, this.outputStream);
            this.sensorLMS4XX = new SensorLMS4XX(this._sensorID);
            this.sensorVMD5XX = new SensorVMD5XX(this._sensorID);
            this.sensorVMD5XX_F = new SensorVMD5XX_F(this._sensorID);
            this.sensorLMS_1XX_5XX = new SensorLMS_1XX_5XX(this._sensorID);
            if (!LMSConstValue.getSensorType(this._sensorID).equals("LMS1XX") && !LMSConstValue.getSensorType(this._sensorID).equals("TIM551") && !LMSConstValue.getSensorType(this._sensorID).equals("LMS511") && !LMSConstValue.getSensorType(this._sensorID).equals("LMS400") && !LMSConstValue.getSensorType(this._sensorID).equals("VMD500") && !LMSConstValue.getSensorType(this._sensorID).equals("VMD500_F")) {
                this.heartBreak(this._sensorID);
            }
            LMSConstValue.sendPortConnected(this._sensorID, true);
        }
        catch (ConnectException e) {
            LMSLog.exception((Throwable)e);
            return false;
        }
        catch (SocketTimeoutException e2) {
            LMSLog.exception((Throwable)e2);
            return false;
        }
        catch (UnknownHostException e3) {
            try {
                Thread.sleep(2000L);
                LMSLog.exception((Throwable)e3);
            }
            catch (InterruptedException e4) {
                LMSLog.exception(this._sensorID, (Throwable)e4);
            }
            return false;
        }
        catch (IOException e5) {
            LMSLog.exception(this._sensorID, (Throwable)e5);
            return false;
        }
        return true;
    }
    
    public void disconnectSocket() {
        LMSLog.d(this.DEBUG_TAG, "disconnectSocket");
        try {
            if (this.socket != null && !this.socket.isClosed()) {
                this.socket.shutdownInput();
                this.socket.shutdownOutput();
                this.socket.close();
                if (this.outputStream != null) {
                    this.outputStream.close();
                }
                if (this.bufferedReader != null) {
                    this.bufferedReader.close();
                }
            }
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
    }
    
    private class EventListener implements LMSEventListener
    {
        public void lmsTransferEvent(final LMSEvent event) {
            final String eventType = event.getEventType();
            final HashMap eventExtra = event.getEventExtra();
            int sensorID = 0;
            if (eventExtra.containsKey("SensorID")) {
                sensorID = (int) eventExtra.get("SensorID");
            }
            if (eventType != null && eventType.equals("lmsApp.intent.action.SETTING_TRANSFER_INTENT")) {
                final String nvram = (String) eventExtra.get("SETTING_NVRAM");
                if (sensorID == PhysicLayerSocket.this._sensorID && (nvram.equals(LMSConstValue.nvramSensorType) || nvram.equals(LMSConstValue.nvramSensorIP) || nvram.equals(LMSConstValue.nvramSensorPort))) {
                    LMSLog.d(PhysicLayerSocket.this.DEBUG_TAG, "\u7f51\u7edc\u76d1\u542c\u5173\u95ed\u4e86\uff01");
                    PhysicLayerSocket.this.disconnectSocket();
                    synchronized (PhysicLayerSocket.this.tokenSocket) {
                        PhysicLayerSocket.this.tokenSocket.notify();
                        // monitorexit(this.this$0.tokenSocket)
                        return;
                    }
                }
                if (sensorID == PhysicLayerSocket.this._sensorID && nvram.equals(LMSConstValue.nvramFixLed) && PhysicLayerSocket.this.sensorRadarB != null) {
                    if (LMSConstValue.bFixLed[sensorID]) {
                        PhysicLayerSocket.this.sensorRadarB.beaEnterSettingModeTrig();
                    }
                    else {
                        PhysicLayerSocket.this.sensorRadarB.bFixLedMode = false;
                        PhysicLayerSocket.this.sensorRadarB.beaCloseFixLed();
                        try {
                            Thread.sleep(200L);
                        }
                        catch (InterruptedException e) {
                            LMSLog.exception((Throwable)e);
                        }
                        PhysicLayerSocket.this.sensorRadarB.beaEnterMeasureMode();
                    }
                }
            }
        }
    }
}
