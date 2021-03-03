package layer.physicLayer;

import SensorBase.*;
import CarDetect.*;
import com.serotonin.util.queue.*;
import java.text.*;
import java.util.concurrent.*;
import com.serotonin.modbus4j.*;
import com.serotonin.modbus4j.ip.*;
import com.serotonin.modbus4j.exception.*;
import com.serotonin.modbus4j.msg.*;
import lmsEvent.*;
import java.util.*;

public class PhysicLayerModbus extends PhysicLayerPacket
{
    private String DEBUG_TAG;
    protected int _sensorID;
    SensorZM10 sensorZM10;
    public LMSToken tokenModbus;
    final int ModbusPort = 502;
    boolean bModbusPool;
    static ModbusMaster tcpMaster;
    static ModbusRequest modbusRequest;
    
    static {
        PhysicLayerModbus.tcpMaster = null;
        PhysicLayerModbus.modbusRequest = null;
    }
    
    public PhysicLayerModbus(final int sensorID) {
        this.DEBUG_TAG = "PhysicLayerModbus";
        this.tokenModbus = new LMSToken();
        this.bModbusPool = true;
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
                        LMSLog.d(PhysicLayerModbus.this.DEBUG_TAG, "thread run:" + Thread.currentThread().getId());
                        if (LMSConstValue.isValidIP(LMSConstValue.SENSOR_IP[PhysicLayerModbus.this._sensorID]) && LMSConstValue.getSensorType(PhysicLayerModbus.this._sensorID).equals("ZM10")) {
                            synchronized (CarDetectSetting.tokenSensorID[PhysicLayerModbus.this._sensorID]) {
                                if (PhysicLayerModbus.this.connectModbus(0, 16)) {
                                    LMSLog.d(PhysicLayerModbus.this.DEBUG_TAG, "connectmodbus module OK!!!");
                                    while (PhysicLayerModbus.this.bModbusPool) {
                                        PhysicLayerModbus.this.modbusLoop();
                                    }
                                }
                                else {
                                    LMSConstValue.sendPortConnected(PhysicLayerModbus.this._sensorID, false);
                                }
                                // monitorexit(CarDetectSetting.tokenSensorID[this.this$0._sensorID])
                                continue;
                            }
                        }
                        LMSLog.d(PhysicLayerModbus.this.DEBUG_TAG, "tokenModbus.wait !!!!!!!!");
                        synchronized (PhysicLayerModbus.this.tokenModbus) {
                            PhysicLayerModbus.this.tokenModbus.wait();
                        }
                        // monitorexit(this.this$0.tokenModbus)
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
    
    public static byte[] intToByte(final int i) {
        final byte[] result = { (byte)(i >> 8 & 0xFF), (byte)(i & 0xFF) };
        return result;
    }
    
    public void modbusLoop() {
        ModbusResponse modbusResponse = null;
        try {
            modbusResponse = PhysicLayerModbus.tcpMaster.send(PhysicLayerModbus.modbusRequest);
            final ByteQueue byteQueue = new ByteQueue(12);
            modbusResponse.write(byteQueue);
            final String content = byteQueue.toString();
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            final String timeStr = df.format(new Date());
            PhysicLayer.dataRecord(this._sensorID, String.valueOf(timeStr) + " " + content);
            if (LMSConstValue.getSensorType(this._sensorID).equals("ZM10")) {
                this.sensorZM10.physicLayetDataReceive(timeStr, content, CarDetectSetting.packetQueue[this._sensorID]);
            }
        }
        catch (ModbusTransportException e) {
            this.bModbusPool = false;
            LMSLog.exception((Throwable)e);
            LMSConstValue.sendPortConnected(this._sensorID, false);
        }
    }
    
    public boolean connectModbus(final int start, final int readLenth) {
        final ModbusFactory modbusFactory = new ModbusFactory();
        LMSLog.d(this.DEBUG_TAG, "try connect modbus module IP=" + LMSConstValue.SENSOR_IP[this._sensorID]);
        final IpParameters params = new IpParameters();
        params.setHost(LMSConstValue.SENSOR_IP[this._sensorID]);
        params.setPort(502);
        PhysicLayerModbus.tcpMaster = modbusFactory.createTcpMaster(params, true);
        try {
            PhysicLayerModbus.tcpMaster.init();
        }
        catch (ModbusInitException e) {
            LMSLog.exception((Throwable)e);
            return false;
        }
        try {
            PhysicLayerModbus.modbusRequest = (ModbusRequest)new ReadDiscreteInputsRequest(1, start, readLenth);
        }
        catch (ModbusTransportException e2) {
            LMSLog.exception((Throwable)e2);
            return false;
        }
        this.sensorZM10 = new SensorZM10(this._sensorID);
        this.bModbusPool = true;
        LMSConstValue.sendPortConnected(this._sensorID, true);
        return true;
    }
    
    void disconnectModBus() {
        this.bModbusPool = false;
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
                if (sensorID == PhysicLayerModbus.this._sensorID && (nvram.equals(LMSConstValue.nvramSensorType) || nvram.equals(LMSConstValue.nvramSensorIP) || nvram.equals(LMSConstValue.nvramSensorPort))) {
                    LMSLog.d(PhysicLayerModbus.this.DEBUG_TAG, "\u7f51\u7edc\u76d1\u542c\u5173\u95ed\u4e86\uff01");
                    PhysicLayerModbus.this.disconnectModBus();
                    synchronized (PhysicLayerModbus.this.tokenModbus) {
                        PhysicLayerModbus.this.tokenModbus.notify();
                    }
                    // monitorexit(this.this$0.tokenModbus)
                }
            }
        }
    }
}
