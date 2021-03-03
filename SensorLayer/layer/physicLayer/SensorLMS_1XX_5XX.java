package layer.physicLayer;

import java.io.*;
import java.util.concurrent.*;
import SensorBase.*;

public class SensorLMS_1XX_5XX extends PhysicLayerBufferString
{
    private String DEBUG_TAG;
    int _sensorID;
    String strReceive;
    
    public SensorLMS_1XX_5XX(final int sensorID) {
        this.DEBUG_TAG = "SensorLMS_1XX_5XX";
        this.strReceive = "";
        this._sensorID = sensorID;
        this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this._sensorID;
    }
    
    void socketReceive(final int sensorID, final InputStreamReader inputStreamReader) {
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "lms1xx_5xx socket connected");
        LMSWorkflow.lmsWorkflowSetnto1filter(sensorID);
        LMSWorkflow.lmsWorkflowSendPermanent(sensorID, true);
        this._socketReceive(sensorID, inputStreamReader);
    }
    
    public void physicLayerDataReceive(final String sTimeOfReceived, final String content, final BlockingQueue<PhysicLayerPacket> packetQueue) {
        try {
            if (content.startsWith("\u0002")) {
                this.strReceive = content;
            }
            else {
                this.strReceive = String.valueOf(this.strReceive) + content;
            }
            int i = 0;
            int strlen = this.strReceive.length();
            boolean bFirstReceived = true;
            int endIndex;
            while ((endIndex = this.strReceive.indexOf(3, 0)) > 0) {
                final int startIndex = this.strReceive.indexOf(2, 0);
                if (startIndex >= 0) {
                    final String stringPacket = this.strReceive.substring(startIndex, endIndex + 1);
                    final String result = this.strReceive.substring(endIndex + 1);
                    PhysicLayerPacket.packetQueuePut(packetQueue, bFirstReceived, sTimeOfReceived, stringPacket, null);
                    bFirstReceived = false;
                    if (endIndex == strlen - 1) {
                        break;
                    }
                    this.strReceive = result;
                    strlen -= endIndex;
                    if (++i > LMSConstValue.LMS_PACKET_NUM_MAX_SOCKET) {
                        break;
                    }
                    continue;
                }
                else {
                    this.strReceive = this.strReceive.substring(endIndex + 1);
                }
            }
        }
        catch (Exception e) {
            LMSLog.exception(this._sensorID, (Throwable)e);
        }
    }
}
