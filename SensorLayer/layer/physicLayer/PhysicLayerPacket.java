package layer.physicLayer;

import java.util.*;
import java.util.concurrent.*;
import layer.algorithmLayer.*;
import SensorBase.*;

public class PhysicLayerPacket
{
    private static final String DEBUG_TAG = "PhysicLayerPacket";
    public int antiLevel;
    public boolean bFirstReceived;
    public String sTimeOfReceived;
    public String stringPacket;
    public byte[] bytePacket;
    public static final int I_PORT_HAS_DATA = 20;
    public static final int I_PORT_HAS_VALID_DATA = 5;
    public static int[] iPortHasData;
    public static int[] iPortHasValidData;
    private static Timer[] timer;
    private TimerTask[] timerTask;
    
    static {
        PhysicLayerPacket.iPortHasData = new int[40];
        PhysicLayerPacket.iPortHasValidData = new int[40];
        PhysicLayerPacket.timer = new Timer[40];
    }
    
    public PhysicLayerPacket() {
        this.timerTask = new TimerTask[40];
    }
    
    public static void packetQueuePut(final BlockingQueue queue, final boolean bFirstReceived, final String time, final String stringPacket, final byte[] bytePacket) {
        final PhysicLayerPacket packet = new PhysicLayerPacket();
        packet.bFirstReceived = bFirstReceived;
        packet.sTimeOfReceived = time;
        packet.stringPacket = stringPacket;
        packet.bytePacket = bytePacket;
        try {
            queue.put(packet);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    void heartBreak(final int sensorID) {
        if (PhysicLayerPacket.timer[sensorID] == null) {
            PhysicLayerPacket.timer[sensorID] = new Timer();
        }
        if (PhysicLayerPacket.timer[sensorID] != null) {
            if (this.timerTask[sensorID] != null) {
                this.timerTask[sensorID].cancel();
            }
            this.timerTask[sensorID] = new TimeOutTask(sensorID);
            PhysicLayerPacket.timer[sensorID].schedule(this.timerTask[sensorID], 2000L, 2000L);
        }
    }
    
    class TimeOutTask extends TimerTask
    {
        String DEBUG_TAG;
        int _sensorID;
        
        TimeOutTask(final int sensorID) {
            this.DEBUG_TAG = "PhysicLayerPacket_TimeOutTask";
            this._sensorID = sensorID;
            this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
        }
        
        @Override
        public void run() {
            try {
                if (LMSConstValue.bSensorPortConnected[this._sensorID]) {
                    if (PhysicLayerPacket.iPortHasData[this._sensorID] == 0) {
                        ParseLMSAckCommand.telegramCounterLast[this._sensorID] = -1;
                        ParseLMSAckCommand.telegramCounterLost[this._sensorID] = 0;
                        LMSConstValue.bPortHasData[this._sensorID] = false;
                        LMSConstValue.sendPortHasData(this._sensorID, LMSConstValue.bPortHasData[this._sensorID]);
                    }
                    else if (PhysicLayerPacket.iPortHasData[this._sensorID] == 20) {
                        LMSConstValue.bPortHasData[this._sensorID] = true;
                        LMSConstValue.sendPortHasData(this._sensorID, LMSConstValue.bPortHasData[this._sensorID]);
                    }
                    if (PhysicLayerPacket.iPortHasValidData[this._sensorID] == 0) {
                        LMSConstValue.bPortHasValidData[this._sensorID] = false;
                        LMSConstValue.sendPortHasValidData(this._sensorID, LMSConstValue.bPortHasValidData[this._sensorID]);
                    }
                    else if (PhysicLayerPacket.iPortHasValidData[this._sensorID] == 5) {
                        LMSConstValue.bPortHasValidData[this._sensorID] = true;
                        LMSConstValue.sendPortHasValidData(this._sensorID, LMSConstValue.bPortHasValidData[this._sensorID]);
                    }
                    if (!LMSConstValue.bFixLed[this._sensorID]) {
                        if (LMSConstValue.bPortHasData[this._sensorID]) {
                            if (PhysicLayerPacket.iPortHasValidData[this._sensorID] > 0) {
                                final int[] iPortHasValidData = PhysicLayerPacket.iPortHasValidData;
                                final int sensorID = this._sensorID;
                                --iPortHasValidData[sensorID];
                            }
                        }
                        else {
                            PhysicLayerPacket.iPortHasValidData[this._sensorID] = 2;
                        }
                        if (PhysicLayerPacket.iPortHasData[this._sensorID] > 0) {
                            final int[] iPortHasData = PhysicLayerPacket.iPortHasData;
                            final int sensorID2 = this._sensorID;
                            --iPortHasData[sensorID2];
                        }
                    }
                }
            }
            catch (OutOfMemoryError e) {
                LMSLog.outOfMemoryDialog((Throwable)e);
            }
        }
    }
}
