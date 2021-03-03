package layer.physicLayer;

import java.util.*;
import SensorBase.*;
import layer.algorithmLayer.*;

public class PhysicLayer
{
    private static final String DEBUG_TAG = "PhysicLayer";
    public static Queue<String>[] recordForSaveQueue;
    static String[] caroutRecordStr;
    static int[] caroutRecordGetCount;
    public static int[] CAROUT_RECORD_COUNT;
    
    static {
        PhysicLayer.recordForSaveQueue = (Queue<String>[])new LinkedList[40];
        PhysicLayer.caroutRecordStr = new String[40];
        PhysicLayer.caroutRecordGetCount = new int[40];
        PhysicLayer.CAROUT_RECORD_COUNT = new int[40];
    }
    /**
     * 过车录像
     * @param sensorID
     * @param str
     */
    public static void dataRecord(final int sensorID, final String str) {
        try {
            if (LMSConstValue.boardType == LMSConstValue.enumBoardType.SERVER_BOARD) {
                if (LMSConstValue.enumRecordType.key.equals("RECORD_TYPE_CAR_WHOLE_RECORD")) {
                    LMSLog.data(sensorID, str);
                }
                else if (LMSConstValue.enumRecordType.key.equals("RECORD_TYPE_CAR_IN_RECORD")) {
                    if (PhysicLayer.recordForSaveQueue[sensorID] == null) {
                        PhysicLayer.recordForSaveQueue[sensorID] = new LinkedList<String>();
                    }
                    if (PhysicLayer.recordForSaveQueue[sensorID].size() < PhysicLayer.CAROUT_RECORD_COUNT[sensorID]) {
                        PhysicLayer.recordForSaveQueue[sensorID].offer(str);
                    }
                    else {
                        PhysicLayer.caroutRecordStr[sensorID] = PhysicLayer.recordForSaveQueue[sensorID].poll();
                        if (Contour.bCarIn) {
                            PhysicLayer.caroutRecordGetCount[sensorID] = 0;
                            LMSLog.data(sensorID, PhysicLayer.caroutRecordStr[sensorID]);
                        }
                        else if (PhysicLayer.caroutRecordGetCount[sensorID] < PhysicLayer.CAROUT_RECORD_COUNT[sensorID] * 2) {
                            final int[] caroutRecordGetCount = PhysicLayer.caroutRecordGetCount;
                            ++caroutRecordGetCount[sensorID];
                            LMSLog.data(sensorID, PhysicLayer.caroutRecordStr[sensorID]);
                        }
                        PhysicLayer.recordForSaveQueue[sensorID].offer(str);
                    }
                }
            }
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
}
