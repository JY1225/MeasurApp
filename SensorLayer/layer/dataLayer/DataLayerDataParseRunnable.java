package layer.dataLayer;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import layer.algorithmLayer.ParseLMSAckCommand;
import layer.physicLayer.PhysicLayer;
import layer.physicLayer.PhysicLayerBufferByte;
import layer.physicLayer.PhysicLayerPacket;
import lmsBase.LMSProductInfo;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class DataLayerDataParseRunnable {
  private String DEBUG_TAG = "DataLayerDataParseRunnable";
  
  int sensorID;
  
  public int iInvalidSerialNum;
  
  protected DataLayerDataParseLineRadar dataLayerDataParseLineRadar = new DataLayerDataParseLineRadar();
  
  public ArrayBlockingQueue dataParseLineAlgorithmQueue = new ArrayBlockingQueue(LMSConstValue.LMS_PACKET_NUM_MAX);
  
  public ArrayBlockingQueue dataParseLineDrawImageQueue = new ArrayBlockingQueue(LMSConstValue.LMS_PACKET_NUM_MAX);
  
  protected DataLayerDataParseLineLightCurtain dataLayerDataParseLineLightCurtain = new DataLayerDataParseLineLightCurtain();
  
  public ArrayBlockingQueue dataParseLineAlgorithmQueue_LightCurtain = new ArrayBlockingQueue(LMSConstValue.LMS_PACKET_NUM_MAX);
  
  public ArrayBlockingQueue dataParseLineDrawImageQueue_LightCurtain = new ArrayBlockingQueue(LMSConstValue.LMS_PACKET_NUM_MAX);
  
  RadarCalibration radarCalibration = new RadarCalibration();
  
  protected DataLayerDataParseLine dataLayerDataParseLine = new DataLayerDataParseLine();
  
  BaseTime baseTime = new BaseTime();
  
  LinkedList<DataLayerDataParseLine> timeList = new LinkedList<>();
  
  LineFitting lineFitting;
  
  public long lTimeOfReceivedLast;
  
  int beaPlaneNumber;
  
  public static final int LMS400_FORMAT_LENGTH = 2;
  
  public static final int LMS400_DISTANCE_SCALING_LENGTH = 2;
  
  public static final int LMS400_START_ANGLE_LENGTH = 4;
  
  public static final int LMS400_ANGLE_STEP_LENGTH = 2;
  
  public static final int LMS400_NUM_OF_POINT_LENGTH = 2;
  
  public static final int LMS400_FREQ_LENGTH = 2;
  
  public static final int LMS400_REMISSION_SCALING_LENGTH = 2;
  
  public static final int LMS400_REMISSION_START_VALUE_LENGTH = 2;
  
  public static final int LMS400_REMISSION_END_VALUE_LENGTH = 2;
  
  public static final int LMS400_DATA_START = 20;
  
  public static final int VMD500_DATA_START = 20;
  
  public static final int VMD500_F_DATA_START = 28;
  
  public DataLayerDataParseRunnable(int _sensorID) {
    this.lineFitting = new LineFitting();
    this.lTimeOfReceivedLast = 0L;
    this.beaPlaneNumber = 0;
    this.sensorID = _sensorID;
    this.DEBUG_TAG = String.valueOf(this.DEBUG_TAG) + this.sensorID;
    EventListener eventListener = new EventListener();
    LMSEventManager.addListener(eventListener);
  }
  
  public Runnable thread() {
    return new Runnable() {
        public void run() {
          try {
            while (true) {
              PhysicLayerPacket singlePacket = null;
              if (CarDetectSetting.packetQueue[DataLayerDataParseRunnable.this.sensorID] != null)
                singlePacket = CarDetectSetting.packetQueue[DataLayerDataParseRunnable.this.sensorID].take(); 
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
              long lTimeOfReceived = 0L;
              try {
                Date d = sdf.parse(singlePacket.sTimeOfReceived);
                lTimeOfReceived = d.getTime();
              } catch (ParseException e) {
                LMSLog.exception(DataLayerDataParseRunnable.this.sensorID, e);
              } 
              if (LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("LMS1XX") || 
                LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("LMS511") || 
                LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("LMS400") || 
                LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("VMD500") || 
                LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("VMD500_F") || 
                LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("RADAR_B") || 
                LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("RADAR_FS")) {
                if (DataLayerDataParseRunnable.this.lTimeOfReceivedLast == 0L) {
                  DataLayerDataParseRunnable.this.lTimeOfReceivedLast = lTimeOfReceived;
                } else {
                  DataLayerDataParseRunnable.this.lTimeOfReceivedLast = lTimeOfReceived;
                  if (DataLayerDataParseRunnable.this.radarCalibration.bGetBase) {
                    DataLayerDataParseRunnable.this.radarCalibration.getBaseDataParse(DataLayerDataParseRunnable.this.sensorID, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar);
                  } else if (RadarCalibration.bBiaoDing[DataLayerDataParseRunnable.this.sensorID]) {
                    DataLayerDataParseRunnable.this.radarCalibration.biaoDing(DataLayerDataParseRunnable.this.sensorID, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar);
                  } else if (DataLayerDataParseRunnable.this.beaPlaneNumber >= 0) {
                    if (LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID].booleanValue() && 
                      DataLayerDataParseRunnable.this.dataParseLineAlgorithmQueue != null) {
                      DataLayerDataParseRunnable.this.iInvalidSerialNum = 0;
                      LMSConstValue.lLastTimeOfReceived_fitting[DataLayerDataParseRunnable.this.sensorID] = DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.lTimeOfReceived_fitting;
                      LMSConstValue.sLastTimeOfReceived[DataLayerDataParseRunnable.this.sensorID] = DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.sTimeOfReceived;
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.dataLayerDataParseLineRadarQueuePut(
                          DataLayerDataParseRunnable.this.dataParseLineAlgorithmQueue, 
                          DataLayerDataParseRunnable.this.beaPlaneNumber, 
                          DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.iScanCounter, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.timeOfTrans, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.sTimeOfReceived, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.lTimeOfReceived_fitting, 
                          DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.distanceX, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.distanceY, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.distanceR);
                    } else if (!LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID].booleanValue()) {
                      if (DataLayerDataParseRunnable.this.iInvalidSerialNum < 5) {
                        DataLayerDataParseRunnable.this.iInvalidSerialNum++;
                      } else {
                        HashMap<String, Comparable> eventExtra = new HashMap<>();
                        eventExtra.put("SensorID", Integer.valueOf(DataLayerDataParseRunnable.this.sensorID));
                        eventExtra.put("deviceStateType", LMSConstValue.enumDeviceStateType.INVALID_SENSOR);
                        LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.DEVICE_STATE_INTENT", eventExtra);
                      } 
                    } 
                    if (DataLayerDataParseRunnable.this.dataParseLineDrawImageQueue != null && (
                      LMSConstValue.bRadarMonitor[DataLayerDataParseRunnable.this.sensorID] || LMSConstValue.bDetectorMonitor))
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.dataLayerDataParseLineRadarQueuePut(
                          DataLayerDataParseRunnable.this.dataParseLineDrawImageQueue, 
                          DataLayerDataParseRunnable.this.beaPlaneNumber, 
                          0, 0L, null, 0L, 
                          DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.distanceX, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.distanceY, DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.distanceR); 
                  } 
                } 
                if (LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("RADAR_B")) {
                  if (singlePacket.stringPacket != null) {
                    DataLayerDataParseRunnable.this.beaPlaneNumber = DataLayerDataParseRunnable.this.beaDataParse_String(DataLayerDataParseRunnable.this.sensorID, singlePacket.stringPacket);
                    if (DataLayerDataParseRunnable.this.beaPlaneNumber >= 0)
                      try {
                        DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.timeOfTrans = Integer.valueOf(singlePacket.stringPacket.substring(28, 32), 16).intValue();
                      } catch (NumberFormatException e) {
                        LMSLog.exception(-1, e);
                      }  
                  } else if (singlePacket.bytePacket != null) {
                    DataLayerDataParseRunnable.this.beaPlaneNumber = DataLayerDataParseRunnable.this.beaDataParse_byte(DataLayerDataParseRunnable.this.sensorID, singlePacket.bytePacket);
                    if (DataLayerDataParseRunnable.this.beaPlaneNumber >= 0)
                      try {
                        DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.timeOfTrans = (((
                          singlePacket.bytePacket[13] & 0xFF) << 8) + (
                          singlePacket.bytePacket[12] & 0xFF));
                      } catch (NumberFormatException e) {
                        LMSLog.exception(-1, e);
                      }  
                  } 
                } else if (LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("RADAR_FS")) {
                  if (singlePacket.stringPacket == null)
                    if (singlePacket.bytePacket != null) {
                      DataLayerDataParseRunnable.this.beaPlaneNumber = DataLayerDataParseRunnable.this.beaFS_DataParse_byte(DataLayerDataParseRunnable.this.sensorID, singlePacket.bytePacket);
                      if (DataLayerDataParseRunnable.this.beaPlaneNumber >= 0)
                        try {
                          DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar.timeOfTrans = (((
                            singlePacket.bytePacket[20] & 0xFF) << 8) + (
                            singlePacket.bytePacket[19] & 0xFF));
                        } catch (NumberFormatException e) {
                          LMSLog.exception(-1, e);
                        }  
                    }  
                } else if (LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("LMS400")) {
                  DataLayerDataParseRunnable.this.beaPlaneNumber = 0;
                  if (!LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID].booleanValue())
                    if (LMSConstValue.boardType == LMSConstValue.enumBoardType.SIMULATE_FILE_BOARD) {
                      LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID] = Boolean.valueOf(true);
                    } else {
                      DataLayerDataParseRunnable.this.parseIsValidSerial(DataLayerDataParseRunnable.this.sensorID);
                    }  
                  if (!LMSProductInfo.bSensorInit[DataLayerDataParseRunnable.this.sensorID].booleanValue())
                    (new RadarSensorInit()).init(DataLayerDataParseRunnable.this.sensorID); 
                  DataLayerDataParseRunnable.this.lms400DataParse(DataLayerDataParseRunnable.this.sensorID, singlePacket.bytePacket);
                } else if (LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("VMD500")) {
                  DataLayerDataParseRunnable.this.beaPlaneNumber = 0;
                  if (!LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID].booleanValue())
                    if (LMSConstValue.boardType == LMSConstValue.enumBoardType.SIMULATE_FILE_BOARD) {
                      LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID] = Boolean.valueOf(true);
                    } else {
                      DataLayerDataParseRunnable.this.parseIsValidSerial(DataLayerDataParseRunnable.this.sensorID);
                    }  
                  if (!LMSProductInfo.bSensorInit[DataLayerDataParseRunnable.this.sensorID].booleanValue())
                    (new RadarSensorInit()).init(DataLayerDataParseRunnable.this.sensorID); 
                  DataLayerDataParseRunnable.this.VMD500DataParse(DataLayerDataParseRunnable.this.sensorID, singlePacket.stringPacket);
                } else if (LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("VMD500_F")) {
                  DataLayerDataParseRunnable.this.beaPlaneNumber = 0;
                  if (!LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID].booleanValue())
                    if (LMSConstValue.boardType == LMSConstValue.enumBoardType.SIMULATE_FILE_BOARD) {
                      LMSProductInfo.bValidSerialNumber[DataLayerDataParseRunnable.this.sensorID] = Boolean.valueOf(true);
                    } else {
                      DataLayerDataParseRunnable.this.parseIsValidSerial(DataLayerDataParseRunnable.this.sensorID);
                    }  
                  if (!LMSProductInfo.bSensorInit[DataLayerDataParseRunnable.this.sensorID].booleanValue())
                    (new RadarSensorInit()).init(DataLayerDataParseRunnable.this.sensorID); 
                  DataLayerDataParseRunnable.this.VMD500_F_DataParse(DataLayerDataParseRunnable.this.sensorID, singlePacket.bytePacket);
                } else {
                  DataLayerDataParseRunnable.this.beaPlaneNumber = 0;
                  DataLayerDataParseRunnable.this.socketServiceMsgHandle(DataLayerDataParseRunnable.this.sensorID, singlePacket.stringPacket);
                } 
                DataLayerDataParseRunnable.this.dataLayerDataParseLine = (DataLayerDataParseLine)DataLayerDataParseRunnable.this.dataLayerDataParseLineRadar;
              } else if ((LMSConstValue.sensorType[DataLayerDataParseRunnable.this.sensorID]).key.equals("LIGHT_CURTAIN") || 
                (LMSConstValue.sensorType[DataLayerDataParseRunnable.this.sensorID]).key.equals("XZY_2") || 
                (LMSConstValue.sensorType[DataLayerDataParseRunnable.this.sensorID]).key.equals("XZY_840") || 
                (LMSConstValue.sensorType[DataLayerDataParseRunnable.this.sensorID]).key.equals("XZY_1600") || 
                (LMSConstValue.sensorType[DataLayerDataParseRunnable.this.sensorID]).key.equals("ZM10") || 
                (LMSConstValue.sensorType[DataLayerDataParseRunnable.this.sensorID]).key.equals("PS_16I")) {
                if (DataLayerDataParseRunnable.this.dataParseLineAlgorithmQueue_LightCurtain != null) {
                  LMSConstValue.lLastTimeOfReceived_fitting[DataLayerDataParseRunnable.this.sensorID] = DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.lTimeOfReceived_fitting;
                  LMSConstValue.sLastTimeOfReceived[DataLayerDataParseRunnable.this.sensorID] = DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.sTimeOfReceived;
                  DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.dataLayerLightCurtainParseLineQueuePut(
                      DataLayerDataParseRunnable.this.dataParseLineAlgorithmQueue_LightCurtain, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.iScanCounter, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.timeOfTrans, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.sTimeOfReceived, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.lTimeOfReceived_fitting, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.bLightCurtainLightStatus);
                } 
                if (DataLayerDataParseRunnable.this.dataParseLineDrawImageQueue_LightCurtain != null && 
                  LMSConstValue.bDetectorMonitor)
                  DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.dataLayerLightCurtainParseLineQueuePut(
                      DataLayerDataParseRunnable.this.dataParseLineDrawImageQueue_LightCurtain, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.iScanCounter, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.timeOfTrans, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.sTimeOfReceived, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.lTimeOfReceived_fitting, 
                      DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.bLightCurtainLightStatus); 
                DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.sTimeOfReceived = singlePacket.sTimeOfReceived;
                DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain.lTimeOfReceived_fitting = lTimeOfReceived;
                if (!LMSProductInfo.bSensorInit[DataLayerDataParseRunnable.this.sensorID].booleanValue())
                  DataLayerDataParseRunnable.this.lightCurtainSensorInit(DataLayerDataParseRunnable.this.sensorID); 
                DataLayerDataParseRunnable.this.lightCurtainDataParse(DataLayerDataParseRunnable.this.sensorID, singlePacket.bytePacket);
                DataLayerDataParseRunnable.this.dataLayerDataParseLine = (DataLayerDataParseLine)DataLayerDataParseRunnable.this.dataLayerDataParseLineLightCurtain;
              } 
              DataLayerDataParseRunnable.this.dataLayerDataParseLine.iPlaneNum = DataLayerDataParseRunnable.this.beaPlaneNumber;
              DataLayerDataParseRunnable.this.dataLayerDataParseLine.sTimeOfReceived = singlePacket.sTimeOfReceived;
              DataLayerDataParseRunnable.this.dataLayerDataParseLine.lTimeOfReceived = lTimeOfReceived;
              if (singlePacket.bFirstReceived)
                DataLayerDataParseRunnable.this.dataLayerDataParseLine.dataLayerDataParseLineAdd(DataLayerDataParseRunnable.this.timeList, DataLayerDataParseRunnable.this.dataLayerDataParseLine); 
              if (DataLayerDataParseRunnable.this.timeList.size() >= DataLayerDataParseRunnable.this.lineFitting.getTimeListSize(DataLayerDataParseRunnable.this.sensorID)) {
                DataLayerDataParseRunnable.this.timeList.removeFirst();
                DataLayerDataParseRunnable.this.lineFitting.adjustBaseTime(DataLayerDataParseRunnable.this.sensorID, DataLayerDataParseRunnable.this.baseTime, DataLayerDataParseRunnable.this.timeList, false);
              } 
              if (LMSConstValue.getSensorType(DataLayerDataParseRunnable.this.sensorID).equals("ZM10")) {
                DataLayerDataParseRunnable.this.dataLayerDataParseLine.lTimeOfReceived_fitting = DataLayerDataParseRunnable.this.dataLayerDataParseLine.lTimeOfReceived;
                continue;
              } 
              DataLayerDataParseRunnable.this.dataLayerDataParseLine.lTimeOfReceived_fitting = 
                DataLayerDataParseRunnable.this.lineFitting.getLTimeOfReceived_LineFitting(
                  DataLayerDataParseRunnable.this.sensorID, 
                  DataLayerDataParseRunnable.this.dataLayerDataParseLine.iScanCounter, 
                  DataLayerDataParseRunnable.this.baseTime.iScanCounter, 
                  DataLayerDataParseRunnable.this.baseTime.B, 
                  DataLayerDataParseRunnable.this.baseTime.A);
            } 
          } catch (OutOfMemoryError e) {
            LMSLog.outOfMemoryDialog(e);
          } catch (Exception e) {
            LMSLog.exceptionDialog(null, e);
          } 
        }
      };
  }
  
  int getCharIndexWithNum(String str, char c, int NUM) {
    int validIndex = -1;
    int startIndex = 0;
    int num = 0;
    int index;
    while ((index = str.indexOf(c, startIndex)) >= 0) {
      startIndex = index + 1;
      num++;
      if (num == NUM) {
        validIndex = index;
        break;
      } 
    } 
    return validIndex;
  }
  
  private boolean beaDataChk(int sensorID, String packet) {
    int chkFirstIndex = getCharIndexWithNum(packet, ' ', 2) + 1;
    int chkLastIndex = packet.lastIndexOf(' ');
    int CHK = 21930;
    int sum = 0;
    try {
      CHK = Integer.valueOf(packet.substring(chkLastIndex + 1), 16).intValue();
      String message = packet.substring(chkFirstIndex, chkLastIndex + 1);
      int startIndex = 0;
      int endIndex;
      while ((endIndex = message.indexOf(' ', startIndex)) >= 0) {
        String strValue = message.substring(startIndex, endIndex);
        startIndex = endIndex + 1;
        for (int i = 0; i < strValue.length() / 2; i++)
          sum += Integer.valueOf(strValue.substring(i * 2, i * 2 + 2), 16).intValue(); 
      } 
    } catch (NumberFormatException e) {
      LMSLog.exception(sensorID, e);
    } 
    if ((sum & 0xFFFF) != CHK) {
      LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "packet=" + packet);
      LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "CHK=" + CHK + " sum=" + sum);
      return false;
    } 
    return true;
  }
  
  private int beaDataParse_String(int sensorID, String packet) {
    if (!beaDataChk(sensorID, packet))
      return -1; 
    if (!LMSProductInfo.bValidSerialNumber[sensorID].booleanValue()) {
      LMSTelegram.serialNumber[sensorID] = packet.substring(19, 27);
      parseIsValidSerial(sensorID);
    } 
    if (!LMSProductInfo.bSensorInit[sensorID].booleanValue())
      (new RadarSensorInit()).init(sensorID); 
    (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter = 
      LMSPlatform.hexStringToInt(packet.substring(28, 32));
    LMSTelegram.checkTelegramCounterLost(sensorID);
    int dataLength = 274;
    LMSTelegram.getSubFieldAndToFloat(sensorID, packet.substring(36), dataLength);
    if (LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.ANTI_COLLITION) {
      int antiLevel = -1;
      int leftWindow = LMSConstValue.iLeftWindow[sensorID];
      int rightWindow = LMSConstValue.iRightWindow[sensorID];
      int loopStart = LMSConstValue.filterStartPoint[sensorID];
      int loopEnd = LMSConstValue.filterEndPoint[sensorID];
      for (int i = loopStart + 1; i < loopEnd - 1; i++) {
        for (int planeNum = 0; planeNum < 4; planeNum++) {
          for (int levelLoop = 0; levelLoop < LMSConstValue.ANTI_LEVEL; levelLoop++) {
            if ((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] > -rightWindow && 
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] < leftWindow)
              if ((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] < LMSConstValue.iAntiLevel[sensorID][levelLoop]) {
                if (antiLevel == -1 || levelLoop < antiLevel)
                  antiLevel = levelLoop; 
                break;
              }  
          } 
        } 
      } 
      (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel = antiLevel;
      LMSLog.d(this.DEBUG_TAG, "antiLevel=" + (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel);
      if ((CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel != (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevelLast) {
        LMSLog.d(this.DEBUG_TAG, "COLLITION !!!!!!" + (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel);
        HashMap<String, Comparable> eventExtra = new HashMap<>();
        eventExtra.put("carState", Integer.valueOf(LMSConstValue.enumCarState.ANTI_LEVEL_CHANGE.ordinal()));
        eventExtra.put("errorCode", Integer.valueOf((CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).errorCode));
        eventExtra.put("SensorID", Integer.valueOf(sensorID));
        eventExtra.put("antiLevel", Integer.valueOf((CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel + 1));
        LMSConstValue.lmsEventManager.sendEvent("CAR_STATE_CHANGE_INTENT", eventExtra);
      } 
      (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevelLast = (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel;
    } 
    if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B"))
      return Integer.valueOf(packet.substring(33, 35), 16).intValue(); 
    return 4;
  }
  
  private int beaDataParse_byte(int sensorID, byte[] packet) {
    int iPlaneIndex = -1;
    iPlaneIndex = packet[14] & 0xFF;
    if (!LMSProductInfo.bValidSerialNumber[sensorID].booleanValue()) {
      String hex3 = Integer.toHexString(packet[11] & 0xFF);
      if (hex3.length() == 1)
        hex3 = String.valueOf('0') + hex3; 
      String hex2 = Integer.toHexString(packet[10] & 0xFF);
      if (hex2.length() == 1)
        hex2 = String.valueOf('0') + hex2; 
      String hex1 = Integer.toHexString(packet[9] & 0xFF);
      if (hex1.length() == 1)
        hex1 = String.valueOf('0') + hex1; 
      String hex0 = Integer.toHexString(packet[8] & 0xFF);
      if (hex0.length() == 1)
        hex0 = String.valueOf('0') + hex0; 
      LMSTelegram.serialNumber[sensorID] = String.valueOf(hex3.toUpperCase()) + hex2.toUpperCase() + hex1.toUpperCase() + hex0.toUpperCase();
      parseIsValidSerial(sensorID);
    } 
    if (!LMSProductInfo.bSensorInit[sensorID].booleanValue())
      (new RadarSensorInit()).init(sensorID); 
    (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter = (
      0xFF & packet[12]) + ((0xFF & packet[13]) << 8);
    LMSTelegram.checkTelegramCounterLost(sensorID);
    for (int i = 0; i < 274; i++) {
      int loopStart = LMSConstValue.physicStartPoint[sensorID] + i;
      int iValue = (0xFF & packet[15 + (i << 1)]) + ((0xFF & packet[15 + (i << 1) + 1]) << 8);
      if (iValue > 61440) {
        iValue = 500000;
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] = 500000;
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[loopStart] = 500000;
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[loopStart] = 500000;
      } else {
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] = LMSConstValue.rFBOffsetAdjust(sensorID, loopStart, iValue);
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getCosLR(sensorID, loopStart));
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getSinLR(sensorID, loopStart));
      } 
    } 
    if (LMSConstValue.defaultDetectType == LMSConstValue.enumDetectType.ANTI_COLLITION) {
      int antiLevel = -1;
      int leftWindow = LMSConstValue.iLeftWindow[sensorID];
      int rightWindow = LMSConstValue.iRightWindow[sensorID];
      int loopStart = LMSConstValue.filterStartPoint[sensorID];
      int loopEnd = LMSConstValue.filterEndPoint[sensorID];
      for (int j = loopStart + 1; j < loopEnd - 1; j++) {
        for (int planeNum = 0; planeNum < 4; planeNum++) {
          for (int levelLoop = 0; levelLoop < LMSConstValue.ANTI_LEVEL; levelLoop++) {
            if ((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[j] > -rightWindow && 
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[j] < leftWindow)
              if ((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[j] < LMSConstValue.iAntiLevel[sensorID][levelLoop]) {
                if (antiLevel == -1 || levelLoop < antiLevel)
                  antiLevel = levelLoop; 
                break;
              }  
          } 
        } 
      } 
      (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel = antiLevel;
      LMSLog.d(this.DEBUG_TAG, "antiLevel=" + (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel);
      if ((CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel != (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevelLast) {
        LMSLog.d(this.DEBUG_TAG, "COLLITION !!!!!!" + (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel);
        HashMap<String, Comparable> eventExtra = new HashMap<>();
        eventExtra.put("carState", Integer.valueOf(LMSConstValue.enumCarState.ANTI_LEVEL_CHANGE.ordinal()));
        eventExtra.put("errorCode", Integer.valueOf((CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).errorCode));
        eventExtra.put("SensorID", Integer.valueOf(sensorID));
        eventExtra.put("antiLevel", Integer.valueOf((CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel + 1));
        LMSConstValue.lmsEventManager.sendEvent("CAR_STATE_CHANGE_INTENT", eventExtra);
      } 
      (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevelLast = (CarDetectSetting.carDetectSetting.physicLayerSerialPort[sensorID]).antiLevel;
    } 
    return iPlaneIndex;
  }
  
  short canopen_gilgen_CRC16(byte[] data) {
    short crc = 0;
    for (short i = 0; i < data.length - 2; i = (short)(i + 1)) {
      crc = (short)(crc ^ (short)(data[i] << 8) & 0xFFFF);
      for (short j = 0; j < 8; j = (short)(j + 1)) {
        if ((crc & 0x8000) != 0) {
          crc = (short)(crc << 1 & 0xFFFF ^ 0x90D9);
        } else {
          crc = (short)(crc << 1 & 0xFFFF);
        } 
      } 
    } 
    return crc;
  }
  
  private boolean beaFS_DataChk_byte(int sensorID, byte[] packet) {
    byte[] pcrc = new byte[2];
    int crc = canopen_gilgen_CRC16(packet);
    int crcGet = (packet[358] & 0xFF) + ((packet[359] & 0xFF) << 8);
    if ((crc & 0xFFFF) != crcGet) {
      LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "packet=" + PhysicLayerBufferByte.bytesToHexString(packet));
      LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "crc=" + crc + " crcGet=" + crcGet);
      return false;
    } 
    return true;
  }
  
  private int beaFS_DataParse_byte(int sensorID, byte[] packet) {
    if (!beaFS_DataChk_byte(sensorID, packet))
      return 0; 
    if (!LMSProductInfo.bValidSerialNumber[sensorID].booleanValue()) {
      String hex3 = Integer.toHexString(packet[16] & 0xFF);
      if (hex3.length() == 1)
        hex3 = String.valueOf('0') + hex3; 
      String hex2 = Integer.toHexString(packet[15] & 0xFF);
      if (hex2.length() == 1)
        hex2 = String.valueOf('0') + hex2; 
      String hex1 = Integer.toHexString(packet[14] & 0xFF);
      if (hex1.length() == 1)
        hex1 = String.valueOf('0') + hex1; 
      String hex0 = Integer.toHexString(packet[13] & 0xFF);
      if (hex0.length() == 1)
        hex0 = String.valueOf('0') + hex0; 
      LMSTelegram.serialNumber[sensorID] = String.valueOf(hex3.toUpperCase()) + hex2.toUpperCase() + hex1.toUpperCase() + hex0.toUpperCase();
      parseIsValidSerial(sensorID);
    } 
    if (!LMSProductInfo.bSensorInit[sensorID].booleanValue())
      (new RadarSensorInit()).init(sensorID); 
    (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter = (
      0xFF & packet[19]) + ((0xFF & packet[20]) << 8);
    LMSTelegram.checkTelegramCounterLost(sensorID);
    for (int i = 0; i < 168; i++) {
      int loopStart = LMSConstValue.physicStartPoint[sensorID] + i;
      int iValue = (0xFF & packet[22 + (i << 1)]) + ((0xFF & packet[22 + (i << 1) + 1]) << 8);
      if (iValue > 61440) {
        iValue = 500000;
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] = 500000;
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[loopStart] = 500000;
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[loopStart] = 500000;
      } else {
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] = LMSConstValue.rFBOffsetAdjust(sensorID, loopStart, iValue);
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getCosLR(sensorID, loopStart));
        (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getSinLR(sensorID, loopStart));
      } 
    } 
    return 1;
  }
  
  private void lms400DataParse(int sensorID, byte[] bytePacket) {
    for (int i = 0; i < ParseLMSAckCommand.measureData16bit_amount[sensorID]; i++) {
      int loopStart = LMSConstValue.physicStartPoint[sensorID] + i;
      int iValue = (0xFF & bytePacket[20 + (i << 1)]) + ((0xFF & bytePacket[20 + (i << 1) + 1]) << 8);
      (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] = LMSConstValue.rFBOffsetAdjust(sensorID, loopStart, iValue);
      (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getCosLR(sensorID, loopStart));
      (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getSinLR(sensorID, loopStart));
    } 
  }
  
  private void VMD500DataParse(int sensorID, String str) {
    LMSTelegram.getSubFieldAndToFloat(sensorID, str, ParseLMSAckCommand.measureData16bit_amount[sensorID] - 1);
  }
  
  private void VMD500_F_DataParse(int sensorID, byte[] bytePacket) {
    for (int i = 0; i < ParseLMSAckCommand.measureData16bit_amount[sensorID]; i++) {
      int loopStart = LMSConstValue.physicStartPoint[sensorID] + i;
      int iValue = (((0xFF & bytePacket[28 + i * 3 + 1]) << 8) + (0xFF & bytePacket[28 + i * 3])) / 10;
      (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] = LMSConstValue.rFBOffsetAdjust(sensorID, loopStart, iValue);
      (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getCosLR(sensorID, loopStart));
      (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[loopStart] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[loopStart] * LMSConstValue.getSinLR(sensorID, loopStart));
    } 
  }
  
  private void lightCurtainDataParse(int sensorID, byte[] bytePacket) {
    if (LMSConstValue.getSensorType(sensorID).equals("ZM10") || 
      LMSConstValue.getSensorType(sensorID).equals("PS_16I")) {
      int count = 0;
      for (int loop = 0; loop < 2; loop++) {
        byte dataByte = bytePacket[loop];
        for (int bitLoop = 0; bitLoop < 8; bitLoop++) {
          boolean bLightStatus = false;
          if ((dataByte << bitLoop & 0x80) == 128)
            bLightStatus = true; 
          this.dataLayerDataParseLineLightCurtain.bLightCurtainLightStatus[count] = bLightStatus;
          count++;
        } 
      } 
      if (LMSConstValue.getSensorType(sensorID).equals("PS_16I")) {
        this.dataLayerDataParseLineLightCurtain.iScanCounter = ((
          bytePacket[2] & 0xFF) << 8) + (
          bytePacket[3] & 0xFF);
        this.dataLayerDataParseLineLightCurtain.timeOfTrans = this.dataLayerDataParseLineLightCurtain.iScanCounter;
      } 
    } else if (LMSConstValue.getSensorType(sensorID).equals("XZY_2") || 
      LMSConstValue.getSensorType(sensorID).equals("XZY_840") || 
      LMSConstValue.getSensorType(sensorID).equals("XZY_1600")) {
      this.dataLayerDataParseLineLightCurtain.iScanCounter = ((
        bytePacket[3] & 0xFF) << 32) + ((
        bytePacket[2] & 0xFF) << 16) + ((
        bytePacket[1] & 0xFF) << 8) + (
        bytePacket[0] & 0xFF);
      this.dataLayerDataParseLineLightCurtain.timeOfTrans = this.dataLayerDataParseLineLightCurtain.iScanCounter;
      int count = 0;
      int iLightCurtainDataStart = 4;
      int LOOP_NUM = (int)Math.ceil((Float.valueOf(LMSConstValue.iLPNum).floatValue() / 8.0F));
      for (int loop = 0; loop < LOOP_NUM; loop++) {
        byte dataByte = bytePacket[iLightCurtainDataStart + loop];
        for (int bitLoop = 7; bitLoop >= 0; bitLoop--) {
          boolean bLightStatus = false;
          if ((dataByte << bitLoop & 0x80) == 128)
            bLightStatus = true; 
          this.dataLayerDataParseLineLightCurtain.bLightCurtainLightStatus[count] = bLightStatus;
          count++;
        } 
      } 
    } else {
      this.dataLayerDataParseLineLightCurtain.iScanCounter = ((
        bytePacket[0] & 0xFF) << 32) + ((
        bytePacket[1] & 0xFF) << 16) + ((
        bytePacket[2] & 0xFF) << 8) + (
        bytePacket[3] & 0xFF);
      this.dataLayerDataParseLineLightCurtain.timeOfTrans = this.dataLayerDataParseLineLightCurtain.iScanCounter;
      int count = 0;
      int iLightCurtainDataStart = 4;
      for (int loop = 0; loop < LMSConstValue.iLPNum / 8; loop++) {
        byte dataByte = bytePacket[iLightCurtainDataStart + loop];
        for (int bitLoop = 0; bitLoop < 8; bitLoop++) {
          boolean bLightStatus = false;
          if ((dataByte << bitLoop & 0x80) == 128)
            bLightStatus = true; 
          this.dataLayerDataParseLineLightCurtain.bLightCurtainLightStatus[count] = bLightStatus;
          count++;
        } 
      } 
    } 
  }
  
  public void lightCurtainSensorInit(int sensorID) {
    LMSProductInfo.bSensorInit[sensorID] = Boolean.valueOf(true);
    if (LMSConstValue.getSensorType(sensorID).equals("LIGHT_CURTAIN")) {
      ParseLMSAckCommand.scanFreq[sensorID] = 100;
      PhysicLayer.CAROUT_RECORD_COUNT[sensorID] = ParseLMSAckCommand.scanFreq[sensorID] * 10 / 50;
    } else if (LMSConstValue.getSensorType(sensorID).equals("XZY_2")) {
      ParseLMSAckCommand.scanFreq[sensorID] = 286;
      PhysicLayer.CAROUT_RECORD_COUNT[sensorID] = ParseLMSAckCommand.scanFreq[sensorID] * 10 / 100;
    } else if (LMSConstValue.getSensorType(sensorID).equals("XZY_840")) {
      ParseLMSAckCommand.scanFreq[sensorID] = 100;
      PhysicLayer.CAROUT_RECORD_COUNT[sensorID] = ParseLMSAckCommand.scanFreq[sensorID] * 10 / 50;
    } else if (LMSConstValue.getSensorType(sensorID).equals("XZY_1600")) {
      ParseLMSAckCommand.scanFreq[sensorID] = 100;
      PhysicLayer.CAROUT_RECORD_COUNT[sensorID] = ParseLMSAckCommand.scanFreq[sensorID] * 10 / 50;
    } else if (LMSConstValue.getSensorType(sensorID).equals("ZM10") || 
      LMSConstValue.getSensorType(sensorID).equals("PS_16I")) {
      ParseLMSAckCommand.scanFreq[sensorID] = 100;
      PhysicLayer.CAROUT_RECORD_COUNT[sensorID] = ParseLMSAckCommand.scanFreq[sensorID] * 10;
    } 
  }
  
  public void parseIsValidSerial(int sensorID) {
    LMSLog.d(this.DEBUG_TAG, "LMSTelegram.serialNumber[" + sensorID + "]=" + LMSTelegram.serialNumber[sensorID]);
    if (LMSProductInfo.cmpSerialNum(sensorID)) {
      LMSProductInfo.bValidSerialNumber[sensorID] = Boolean.valueOf(true);
      return;
    } 
  }
  
  private void socketServiceMsgHandle(int sensorID, String str) {
    LMSTelegram.parseLMSAckCommandType(sensorID, str);
    switch (ParseLMSAckCommand.commandType[sensorID]) {
      case 1107:
        if (!LMSProductInfo.bValidSerialNumber[sensorID].booleanValue()) {
          LMSTelegram.serialNumber[sensorID] = LMSTelegram.parseLMSDataFrame(sensorID, ParseLMSAckCommand.resultStr[sensorID], LMSTelegram.enumLmsDataField.LMS_DATA_FIELD_SerialNumber);
          parseIsValidSerial(sensorID);
        } 
        if (!LMSProductInfo.bSensorInit[sensorID].booleanValue())
          (new RadarSensorInit()).init(sensorID); 
        LMSTelegram.parseLMSDataFrame(sensorID, ParseLMSAckCommand.resultStr[sensorID], LMSTelegram.enumLmsDataField.LMS_DATA_FIELD_16bitChannels);
        break;
      case 1102:
        LMSLog.d(this.DEBUG_TAG, "LMS_WORKFLOW_POLLING_ONE_ACK:" + ParseLMSAckCommand.resultStr[sensorID]);
        if (!LMSProductInfo.bValidSerialNumber[sensorID].booleanValue()) {
          LMSTelegram.serialNumber[sensorID] = LMSTelegram.parseLMSDataFrame(sensorID, ParseLMSAckCommand.resultStr[sensorID], LMSTelegram.enumLmsDataField.LMS_DATA_FIELD_SerialNumber);
          parseIsValidSerial(sensorID);
        } 
        if (!LMSProductInfo.bSensorInit[sensorID].booleanValue())
          (new RadarSensorInit()).init(sensorID); 
        LMSTelegram.parseLMSDataFrame(sensorID, ParseLMSAckCommand.resultStr[sensorID], LMSTelegram.enumLmsDataField.LMS_DATA_FIELD_16bitChannels);
        break;
      case 1103:
        LMSTelegram.parseLMSAckPermantSend(sensorID);
        break;
      case 1104:
        LMSTelegram.parseLMSAckOutputRange(sensorID);
        break;
      case 1105:
        LMSTelegram.parseLMSAckFreqAndAngularResolution(sensorID);
        break;
      case 1109:
        LMSTelegram.parseLMSAckTimeStamp(sensorID);
        break;
    } 
  }
  
  private class EventListener implements LMSEventListener {
    private EventListener() {}
    
    public void lmsTransferEvent(LMSEvent event) {
      String eventType = event.getEventType();
      HashMap eventExtra = event.getEventExtra();
      int _sensorID = 0;
      if (eventExtra.containsKey("SensorID"))
        _sensorID = ((Integer)eventExtra.get("SensorID")).intValue(); 
      if (eventType.equals("TRIG_GET_BASE_DATA_INTENT")) {
        if (DataLayerDataParseRunnable.this.sensorID == _sensorID) {
          LMSLog.d(DataLayerDataParseRunnable.this.DEBUG_TAG, "TRIG_GET_BASE_DATA_INTENT---- iGetBaseNum=" + 
              DataLayerDataParseRunnable.this.radarCalibration.iGetBaseNum);
          DataLayerDataParseRunnable.this.radarCalibration.bGetBase = true;
          DataLayerDataParseRunnable.this.radarCalibration.iGetBaseNum = 0;
        } 
      } else if (eventType.equals("SOCKET_SEND_MSG_INTENT")) {
        if (eventExtra.containsKey("INTENT_EXTRA_SOCKET_MSG")) {
          String str = (String)eventExtra.get("INTENT_EXTRA_SOCKET_MSG");
          RadarCalibration.biaodingParse(str);
        } 
      } 
    }
  }
}
