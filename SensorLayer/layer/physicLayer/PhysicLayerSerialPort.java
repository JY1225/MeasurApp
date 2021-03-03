package layer.physicLayer;

import SensorBase.*;
import java.io.*;
import gnu.io.*;
import java.text.*;
import java.util.*;
import CarDetect.*;
import java.util.concurrent.*;
import lmsEvent.*;

public class PhysicLayerSerialPort extends PhysicLayerPacket implements SerialPortEventListener
{
    private String DEBUG_TAG;
    private String appName;
    public int antiLevelLast;
    public int errorCode;
    private int timeout;
    private int threadTime;
    SensorRadarB sensorRadarB;
    SensorRadarFS sensorRadarFS;
    SensorHS_LED sensorHS_LED;
    protected CommPortIdentifier commPort;
    protected SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    public static LMSToken tokenRXTX;
    public LMSToken tokenSerialPort;
    protected int _sensorID;
    byte[][] beaSerialNum;
    
    static {
        PhysicLayerSerialPort.tokenRXTX = new LMSToken();
    }
    
    public Runnable thread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        LMSLog.d(PhysicLayerSerialPort.this.DEBUG_TAG, "serial thread run:" + Thread.currentThread().getId());
                        boolean bPortOk = false;
                        if (LMSConstValue.isValidIP(LMSConstValue.SENSOR_IP[PhysicLayerSerialPort.this._sensorID]) || LMSConstValue.getSensorType(PhysicLayerSerialPort.this._sensorID).equals("UNKNOW")) {
                            synchronized (PhysicLayerSerialPort.this.tokenSerialPort) {
                                LMSLog.d(PhysicLayerSerialPort.this.DEBUG_TAG, "tokenSerialPort.wait !!!!!!!!");
                                PhysicLayerSerialPort.this.tokenSerialPort.wait();
                                // monitorexit(this.this$0.tokenSerialPort)
                                continue;
                            }
                        }
                        if (LMSConstValue.isValidCOM(LMSConstValue.SENSOR_IP[PhysicLayerSerialPort.this._sensorID])) {
                            if (PhysicLayerSerialPort.this._sensorID < 10 && !LMSConstValue.isMyMachine()) {
                                final HashMap<String, Comparable> eventExtraParam = new HashMap<String, Comparable>();
                                eventExtraParam.put("SensorID", PhysicLayerSerialPort.this._sensorID);
                                eventExtraParam.put("lms_parameter", "\u4e0d\u652f\u6301\u4e32\u53e3\u6a21\u5f0f");
                                LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.LMS_PARAMETER_STRING_INTENT", (HashMap)eventExtraParam);
                                LMSConstValue.sendPortConnected(PhysicLayerSerialPort.this._sensorID, false);
                                LMSLog.d(PhysicLayerSerialPort.this.DEBUG_TAG, "not support serial");
                                Thread.sleep(2000L);
                            }
                            else {
                                bPortOk = PhysicLayerSerialPort.this.selectPort(LMSConstValue.SENSOR_IP[PhysicLayerSerialPort.this._sensorID].toUpperCase());
                                if (bPortOk) {
                                    synchronized (PhysicLayerSerialPort.this.tokenSerialPort) {
                                        LMSLog.d(PhysicLayerSerialPort.this.DEBUG_TAG, "tokenSerialPort.wait !!!!!!!!");
                                        PhysicLayerSerialPort.this.tokenSerialPort.wait();
                                        // monitorexit(this.this$0.tokenSerialPort)
                                        continue;
                                    }
                                }
                                LMSLog.d(PhysicLayerSerialPort.this.DEBUG_TAG, "Thread sleep for reconnect");
                                Thread.sleep(2000L);
                            }
                        }
                        else {
                            final HashMap<String, Comparable> eventExtraParam = new HashMap<String, Comparable>();
                            eventExtraParam.put("SensorID", PhysicLayerSerialPort.this._sensorID);
                            eventExtraParam.put("lms_parameter", "\u5730\u5740\u65e0\u6548");
                            LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.LMS_PARAMETER_STRING_INTENT", (HashMap)eventExtraParam);
                            LMSConstValue.sendPortConnected(PhysicLayerSerialPort.this._sensorID, false);
                            LMSLog.d(PhysicLayerSerialPort.this.DEBUG_TAG, "");
                            Thread.sleep(2000L);
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
    
    public PhysicLayerSerialPort(final int sensorID) {
        this.DEBUG_TAG = "PhysicLayerSerialPort";
        this.appName = "sensorManager";
        this.timeout = 1000;
        this.threadTime = 0;
        this.tokenSerialPort = new LMSToken();
        this.beaSerialNum = new byte[10][4];
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
        if (LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.ANTI_COLLITION) {
            this.antiLevelLast = -1;
            this.antiLevel = -1;
        }
        final EventListener eventListener = new EventListener();
        LMSEventManager.addListener((LMSEventListener)eventListener);
    }
    
    public boolean selectPort(final String portName) {
        try {
            final HashMap<String, Comparable> eventExtraParam = new HashMap<String, Comparable>();
            eventExtraParam.put("SensorID", this._sensorID);
            eventExtraParam.put("lms_parameter", "\u4e32\u53e3\u521d\u59cb\u5316\u4e2d(\u5982\u957f\u65f6\u95f4\u672a\u80fd\u521d\u59cb\u5316\u6210\u529f,\u8bf7\u68c0\u67e5\u4e32\u53e3\u670d\u52a1\u5668\u662f\u5426\u6b63\u5e38)");
            LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.LMS_PARAMETER_STRING_INTENT", (HashMap)eventExtraParam);
            LMSConstValue.sendPortConnected(this._sensorID, false);
            LMSLog.d(this.DEBUG_TAG, "selectPort 0000:" + portName);
            final Enumeration en = CommPortIdentifier.getPortIdentifiers();
            while (en.hasMoreElements()) {
                final CommPortIdentifier cpid = (CommPortIdentifier) en.nextElement();
                if (cpid.getPortType() == 1 && cpid.getName().equals(portName)) {
                    this.commPort = cpid;
                    break;
                }
            }
            synchronized (PhysicLayerSerialPort.tokenRXTX) {
                this.openPort();
            }
            // monitorexit(PhysicLayerSerialPort.tokenRXTX)
            if (this.serialPort != null) {
                LMSLog.d(this.DEBUG_TAG, "startRead------------" + this.serialPort.getName());
                this.startRead(0);
                return true;
            }
            if (LMSConstValue.isValidCOM(LMSConstValue.SENSOR_IP[this._sensorID])) {
                LMSLog.d(this.DEBUG_TAG, "\u7aef\u53e3" + portName + "\u9009\u62e9\u5931\u8d25");
                final HashMap<String, Comparable> eventExtraParam2 = new HashMap<String, Comparable>();
                eventExtraParam2.put("SensorID", this._sensorID);
                eventExtraParam2.put("lms_parameter", "\u4e32\u53e3\u6253\u5f00\u5931\u8d25(\u4e0d\u5b58\u5728\u8be5\u7aef\u53e3)");
                LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.LMS_PARAMETER_STRING_INTENT", (HashMap)eventExtraParam2);
                LMSConstValue.sendPortConnected(this._sensorID, false);
            }
        }
        catch (UnsatisfiedLinkError e) {
            LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38:UnsatisfiedLinkError", (Throwable)e);
            System.exit(0);
        }
        return false;
    }
    
    private void openPort() {
        if (this.commPort != null) {
            LMSLog.d(this.DEBUG_TAG, "\u7aef\u53e3" + this.commPort.getName() + "\u9009\u62e9\u6210\u529f,\u73b0\u5728\u5b9e\u4f8b\u5316 SerialPort");
            try {
                this.serialPort = (SerialPort)this.commPort.open(this.appName, this.timeout);
                LMSConstValue.sendPortConnected(this._sensorID, true);
                this.errorCode = 0;
                this.heartBreak(this._sensorID);
                LMSLog.d(this.DEBUG_TAG, "\u5b9e\u4f8b SerialPort \u6210\u529f\uff01");
            }
            catch (PortInUseException e) {
                LMSLog.exception(this._sensorID, (Throwable)e);
                final HashMap<String, Comparable> eventExtraParam = new HashMap<String, Comparable>();
                eventExtraParam.put("SensorID", this._sensorID);
                eventExtraParam.put("lms_parameter", "\u4e32\u53e3\u6253\u5f00\u5931\u8d25(\u4e32\u53e3\u88ab\u5360\u7528)");
                LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.LMS_PARAMETER_STRING_INTENT", (HashMap)eventExtraParam);
                LMSConstValue.sendPortConnected(this._sensorID, false);
            }
            try {
                if (this.serialPort != null) {
                    int baudRate = 0;
                    int parity = 0;
                    if (LMSConstValue.getSensorType(this._sensorID).equals("RADAR_B")) {
                        baudRate = 460800;
                        parity = 0;
                    }
                    else if (LMSConstValue.getSensorType(this._sensorID).equals("RADAR_FS")) {
                        baudRate = 921600;
                        parity = 0;
                    }
                    else if (LMSConstValue.getSensorType(this._sensorID).equals("LIGHT_CURTAIN")) {
                        baudRate = 115200;
                        parity = 0;
                    }
                    else if (LMSConstValue.getSensorType(this._sensorID).equals("XZY_2") || LMSConstValue.getSensorType(this._sensorID).equals("XZY_840") || LMSConstValue.getSensorType(this._sensorID).equals("XZY_1600")) {
                        baudRate = 115200;
                        parity = 1;
                    }
                    else if (LMSConstValue.getSensorType(this._sensorID).equals("HS_LED")) {
                        baudRate = 9600;
                        parity = 0;
                    }
                    this.serialPort.setSerialPortParams(baudRate, 8, 1, parity);
                }
            }
            catch (UnsupportedCommOperationException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    private void checkPort() {
        if (this.commPort == null) {
            this.errorCode = 1;
            LMSLog.d(this.DEBUG_TAG, "\u6ca1\u6709\u9009\u62e9\u7aef\u53e3\uff0c\u8bf7\u4f7f\u7528 selectPort(String portName) \u65b9\u6cd5\u9009\u62e9\u7aef\u53e3");
        }
        if (this.serialPort == null) {
            this.errorCode = 1;
            LMSLog.d(this.DEBUG_TAG, "SerialPort \u5bf9\u8c61\u65e0\u6548\uff01");
        }
    }
    
    public void write(final String message) {
        this.checkPort();
        try {
            if (this.outputStream != null) {
                this.outputStream.write(message.getBytes());
            }
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
            try {
                this.outputStream.close();
            }
            catch (Exception e2) {
                LMSLog.exception(this._sensorID, (Throwable)e2);
            }
            return;
        }
        finally {
            try {
                this.outputStream.close();
            }
            catch (Exception e2) {
                LMSLog.exception(this._sensorID, (Throwable)e2);
            }
        }
        try {
            this.outputStream.close();
        }
        catch (Exception e2) {
            LMSLog.exception(this._sensorID, (Throwable)e2);
        }
    }
    
    public void write(final byte[] message) {
        this.checkPort();
        try {
            if (this.outputStream != null) {
                this.outputStream.write(message);
            }
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
            try {
                this.outputStream.close();
            }
            catch (Exception e2) {
                LMSLog.exception(this._sensorID, (Throwable)e2);
            }
            return;
        }
        finally {
            try {
                this.outputStream.close();
            }
            catch (Exception e2) {
                LMSLog.exception(this._sensorID, (Throwable)e2);
            }
        }
        try {
            this.outputStream.close();
        }
        catch (Exception e2) {
            LMSLog.exception(this._sensorID, (Throwable)e2);
        }
    }
    
    public void startRead(final int time) {
        this.checkPort();
        try {
            if (this.serialPort != null) {
                this.inputStream = new BufferedInputStream(this.serialPort.getInputStream());
                this.outputStream = this.serialPort.getOutputStream();
                this.sensorRadarB = new SensorRadarB(this._sensorID, this.outputStream);
                this.sensorRadarFS = new SensorRadarFS(this._sensorID, this.outputStream);
                this.sensorHS_LED = new SensorHS_LED(this._sensorID, this.outputStream);
            }
        }
        catch (IOException e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
        try {
            if (this.serialPort != null) {
                this.serialPort.addEventListener((SerialPortEventListener)this);
            }
        }
        catch (TooManyListenersException e2) {
            LMSLog.exception(this._sensorID, (Throwable)e2);
        }
        if (this.serialPort != null) {
            this.serialPort.notifyOnDataAvailable(true);
        }
    }
    
    public void close() {
        if (this.serialPort != null) {
            this.serialPort.close();
            this.serialPort = null;
            this.commPort = null;
        }
    }
    
    public void serialEvent(final SerialPortEvent arg0) {
        switch (arg0.getEventType()) {
            case 1: {
                final byte[] readBuffer = new byte[102400];
                int numBytes = 0;
                try {
                    while (this.inputStream.available() > 0) {
                        numBytes += this.inputStream.read(readBuffer);
                    }
                }
                catch (Exception e) {
                    LMSLog.exception(this._sensorID, (Throwable)e);
                }
                final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
                final String timeStr = df.format(new Date());
                PhysicLayer.dataRecord(this._sensorID, String.valueOf(timeStr) + " " + PhysicLayerBufferByte.nBytesToHexString(numBytes, readBuffer));
                if (LMSConstValue.getSensorType(this._sensorID).equals("RADAR_B")) {
                    this.sensorRadarB.physicLayerDataReceive(timeStr, numBytes, readBuffer, CarDetectSetting.packetQueue[this._sensorID]);
                    break;
                }
                if (LMSConstValue.getSensorType(this._sensorID).equals("RADAR_FS")) {
                    this.sensorRadarFS.physicLayerDataReceive(timeStr, numBytes, readBuffer, CarDetectSetting.packetQueue[this._sensorID]);
                    break;
                }
                break;
            }
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
                if (sensorID == PhysicLayerSerialPort.this._sensorID && (nvram.equals(LMSConstValue.nvramSensorType) || nvram.equals(LMSConstValue.nvramSensorIP) || nvram.equals(LMSConstValue.nvramSensorPort))) {
                    LMSLog.d(PhysicLayerSerialPort.this.DEBUG_TAG, "com\u53e3\u76d1\u542c\u5173\u95ed\u4e86\uff01");
                    PhysicLayerSerialPort.this.close();
                    synchronized (PhysicLayerSerialPort.this.tokenSerialPort) {
                        PhysicLayerSerialPort.this.tokenSerialPort.notify();
                        // monitorexit(this.this$0.tokenSerialPort)
                        return;
                    }
                }
                if (sensorID == PhysicLayerSerialPort.this._sensorID && nvram.equals(LMSConstValue.nvramFixLed) && PhysicLayerSerialPort.this.sensorRadarB != null) {
                    if (LMSConstValue.bFixLed[sensorID]) {
                        PhysicLayerSerialPort.this.sensorRadarB.beaEnterSettingModeTrig();
                    }
                    else {
                        PhysicLayerSerialPort.this.sensorRadarB.bFixLedMode = false;
                        PhysicLayerSerialPort.this.sensorRadarB.beaCloseFixLed();
                        try {
                            Thread.sleep(200L);
                        }
                        catch (InterruptedException e) {
                            LMSLog.exception((Throwable)e);
                        }
                        PhysicLayerSerialPort.this.sensorRadarB.beaEnterMeasureMode();
                    }
                }
            }
        }
    }
}
