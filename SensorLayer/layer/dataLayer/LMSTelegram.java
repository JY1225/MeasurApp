package layer.dataLayer;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSPlatform;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import layer.algorithmLayer.ParseLMSAckCommand;

public class LMSTelegram {
  private static final String DEBUG_TAG = "LMS_LMS_TELEGRAM";
  
  public static String[] serialNumber = new String[40];
  
  public static final String LMS_TELE_ETX = "\003";
  
  public static final String LMS_TELE_SOPAS_ERROR_ACK_HEAD = "\002sFA";
  
  public static final String LMS_TELE_LOGIN_MAIN = "\002sMN SetAccessMode 02 B21ACE26\003";
  
  public static final String LMS_TELE_LOGIN_CLIENT = "\002sMN SetAccessMode 03 F4724744\003";
  
  public static final String LMS_TELE_LOGIN_SERVICE = "\002sMN SetAccessMode 04 81BE23AA\003";
  
  public static final String LMS_TELE_LOGIN_ACK = "\002sAN SetAccessMode 1\003";
  
  public static final String LMS_TELE_LOGOUT = "\002sMN Run\003";
  
  public static final String LMS_TELE_SET_OUTPUT_ANGLE_0_180 = "\002sWN LMPoutputRange 1 1388 0 1B7740\003";
  
  public static final String LMS_TELE_SET_OUTPUT_ANGLE_0_90 = "\002sWN LMPoutputRange 1 1388 0 DBBA0\003";
  
  public static final String LMS_TELE_SET_OUTPUT_ANGLE_5_175 = "\002sWN LMPoutputRange 1 1388 C350 1AB3F0\003";
  
  public static final String LMS_TELE_SET_OUTPUT_ANGLE_10_170 = "\002sWN LMPoutputRange 1 1388 186A0 19F0A0\003";
  
  public static final String LMS_TELE_SET_OUTPUT_ANGLE_ACK = "\002sWA LMPoutputRange\003";
  
  public static final String LMS_TELE_GET_ACTUAL_OUTPUT_RANGE = "\002sRN LMPoutputRange\003";
  
  public static final String LMS_TELE_GET_ACTUAL_OUTPUT_RANGE_ACK_HEAD = "\002sRA LMPoutputRange";
  
  public static final String LMS_TELE_SET_FREQ_AND_ANGULAR_RESOLUTION_75HZ = "\002sMN mCLsetscancfglist 6\003";
  
  public static final String LMS_TELE_SET_FREQ_AND_ANGULAR_RESOLUTION_50HZ = "\002sMN mLMPsetscancfg 1388 1 1388 0 0\003";
  
  public static final String LMS_TELE_SET_FREQ_AND_ANGULAR_RESOLUTION_25HZ = "\002sMN mLMPsetscancfg 9C4 1 9C4 0 0\003";
  
  public static final String LMS_TELE_SET_FREQ_AND_ANGULAR_RESOLUTION_15HZ = "\002sMN mLMPsetscancfg 5DC 1 9C4 0 0\003";
  
  public static final String LMS_TELE_GET_FREQ_AND_ANGULAR_RESOLUTION = "\002sRN LMPscancfg\003";
  
  public static final String LMS_TELE_GET_FREQ_AND_ANGULAR_RESOLUTION_ACK_HEAD = "\002sRA LMPscancfg";
  
  public static final String LMS_TELE_SET_DATA_NORMAL = "\002sWN LCMscandatacfg 01 00 1 1 0 00 00 0 0 0 0 1\003";
  
  public static final String LMS_TELE_SET_DATA_RSSI = "\002sWN LCMscandatacfg 02 00 1 1 0 00 00 0 0 0 0 1\003";
  
  public static final String LMS_TELE_MEASUREMENT_START = "\002sMN LMCstartmeas\003";
  
  public static final String LMS_TELE_MEASUREMENT_STOP = "\002sMN LMCstopmeas\003";
  
  public static final String LMS_TELE_POLLING_ONE = "\002sRN LMDscandata\003";
  
  public static final String LMS_TELE_POLLING_ONE_ACK_HEAD = "\002sRA LMDscandata";
  
  public static final String LMS_TELE_PERMANENT_SEND_HEAD = "\002sEN LMDscandata ";
  
  public static final String LMS_TELE_PERMANENT_SEND_START_ACK_HEAD = "\002sEA LMDscandata";
  
  public static final String LMS_TELE_SET_ECHO_FILTER = "\002sWN FREchoFilter 1\003";
  
  public static final String LMS_TELE_SET_NTO1_FILTER = "\002sWN LFPnto1filter 1\003";
  
  public static final String LMS_TELE_SAVE_PARAMETERS = "\002sMN mEEwriteall\003";
  
  public static final String LMS_TELE_SAVE_PARAMETERS_ACK = "\002sAN mEEwriteall 1\003";
  
  public static final String LMS_TELE_DATA_HEAD = "\002sSN LMDscandata";
  
  public static final String LMS_TELE_ASK_TIMESTAMP = "\002sRN STlms\003";
  
  public static final String LMS_TELE_ASK_TIMESTAMP_ACK_HEAD = "\002sRA STlms";
  
  public static int measdata16bit_startIndex;
  
  public enum enumLmsDataField {
    LMS_DATA_FIELD_VersionNumber, LMS_DATA_FIELD_DeviceNumber, LMS_DATA_FIELD_SerialNumber, LMS_DATA_FIELD_DeviceStatus0, LMS_DATA_FIELD_DeviceStatus1, LMS_DATA_FIELD_TelegramCounter, LMS_DATA_FIELD_ScanCounter, LMS_DATA_FIELD_TimeSSU, LMS_DATA_FIELD_TimeOfTrans, LMS_DATA_FIELD_StatusOfDigitalInputs0, LMS_DATA_FIELD_StatusOfDigitalInputs1, LMS_DATA_FIELD_StatusOfDigitalOutputs0, LMS_DATA_FIELD_StatusOfDigitalOutputs1, LMS_DATA_FIELD_Reserve, LMS_DATA_FIELD_ScanFreq, LMS_DATA_FIELD_MeasFreq, LMS_DATA_FIELD_Encoder_amount, LMS_DATA_FIELD_Encoder, LMS_DATA_FIELD_16bitChannels_amount, LMS_DATA_FIELD_16bitChannels, LMS_DATA_FIELD_8bitChannels_amount, LMS_DATA_FIELD_8bitChannels;
  }
  
  public enum enumLmsDataEncoderField {
    LMS_DATA_FIELD_Encoder_Pos, LMS_DATA_FIELD_Encoder_Speed;
  }
  
  public enum enumLmsData16bitChannelField {
    LMS_DATA_FIELD_16bit_content, LMS_DATA_FIELD_16bit_scaleFator, LMS_DATA_FIELD_16bit_scaleFatorOffset, LMS_DATA_FIELD_16bit_startAngle, LMS_DATA_FIELD_16bit_steps, LMS_DATA_FIELD_16bit_amountOfDatas, LMS_DATA_FIELD_16bit_datas;
  }
  
  static int getSubFieldAndToFloat(int sensorID, String msg, int dataLength) {
    int j = 0;
    int iValue = 0;
    String result = "";
    int startIndex = 0;
    int endIndex = -1;
    try {
      char[] buffer = msg.toCharArray();
      int length = msg.length();
      if (LMSConstValue.getSensorType(sensorID).equals("LMS511")) {
        int loopStart = LMSConstValue.physicStartPoint[sensorID];
        int loopEnd = dataLength + loopStart;
        for (int i = loopStart; i < loopEnd; i++) {
          iValue = 0;
          for (j = startIndex; (buffer[j] >= '0' && buffer[j] <= '9') || (buffer[j] >= 'A' && buffer[j] <= 'Z'); j++) {
            char s = buffer[j];
            if (j < length) {
              if (s > '9') {
                iValue = (iValue << 4) + 10 + s - 65;
              } else {
                iValue = (iValue << 4) + s - 48;
              } 
            } else {
              iValue = 500000;
              LMSLog.w("LMS_LMS_TELEGRAM", "getSubFieldAndToFloat i:" + i + "j:" + j);
              break;
            } 
          } 
          startIndex = j + 1;
          if (iValue < 10) {
            iValue = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = 500000;
          } else {
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = LMSConstValue.rFBOffsetAdjust(sensorID, i, iValue);
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getCosLR(sensorID, i));
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getSinLR(sensorID, i));
          } 
        } 
      } else if (LMSConstValue.getSensorType(sensorID).equals("LMS1XX") || 
        LMSConstValue.getSensorType(sensorID).equals("TIM551")) {
        int loopStart = LMSConstValue.physicStartPoint[sensorID];
        int loopEnd = dataLength + loopStart;
        for (int i = loopStart; i < loopEnd; i++) {
          iValue = 0;
          for (j = startIndex; (buffer[j] >= '0' && buffer[j] <= '9') || (buffer[j] >= 'A' && buffer[j] <= 'Z'); j++) {
            char s = buffer[j];
            if (j < length) {
              if (s > '9') {
                iValue = (iValue << 4) + 10 + s - 65;
              } else {
                iValue = (iValue << 4) + s - 48;
              } 
            } else {
              iValue = 500000;
              LMSLog.w("LMS_LMS_TELEGRAM", "getSubFieldAndToFloat i:" + i + "j:" + j);
              break;
            } 
          } 
          startIndex = j + 1;
          if (iValue < 10) {
            iValue = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = 500000;
          } else {
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = LMSConstValue.rFBOffsetAdjust(sensorID, i, iValue);
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getCosLR(sensorID, i));
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getSinLR(sensorID, i));
          } 
        } 
      } else if (LMSConstValue.getSensorType(sensorID).equals("VMD500")) {
        int loopStart = 0;
        int loopEnd = dataLength + loopStart;
        for (int i = loopStart; i < loopEnd; i++) {
          iValue = 0;
          for (j = startIndex; (buffer[j] >= '0' && buffer[j] <= '9') || (buffer[j] >= 'A' && buffer[j] <= 'Z'); j++) {
            char s = buffer[j];
            if (j < length) {
              if (s > '9') {
                iValue = (iValue << 4) + 10 + s - 65;
              } else {
                iValue = (iValue << 4) + s - 48;
              } 
            } else {
              iValue = 500000;
              LMSLog.w("LMS_LMS_TELEGRAM", "getSubFieldAndToFloat i:" + i + "j:" + j);
              break;
            } 
          } 
          startIndex = j + 1;
          if (iValue < 10) {
            iValue = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = 500000;
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = 500000;
          } else {
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = LMSConstValue.rFBOffsetAdjust(sensorID, i, iValue);
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getCosLR(sensorID, i));
            (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getSinLR(sensorID, i));
          } 
        } 
      } else if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B") || 
        LMSConstValue.getSensorType(sensorID).equals("RADAR_F")) {
        int PlaneNumber;
        if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B")) {
          PlaneNumber = 1;
        } else {
          PlaneNumber = 4;
        } 
        int loopStart = LMSConstValue.physicStartPoint[sensorID];
        int loopEnd = dataLength + loopStart;
        for (int planeNum = 0; planeNum < PlaneNumber; planeNum++) {
          for (int i = loopStart; i < loopEnd; i++) {
            iValue = 0;
            for (j = startIndex; (buffer[j] >= '0' && buffer[j] <= '9') || (buffer[j] >= 'A' && buffer[j] <= 'Z'); j++) {
              char s = buffer[j];
              if (j < length) {
                if (s > '9') {
                  iValue = (iValue << 4) + 10 + s - 65;
                } else {
                  iValue = (iValue << 4) + s - 48;
                } 
              } else {
                iValue = 500000;
                LMSLog.w("LMS_LMS_TELEGRAM", "getSubFieldAndToFloat i:" + i + "j:" + j);
                break;
              } 
            } 
            startIndex = j + 1;
            if (iValue > 61440) {
              iValue = 500000;
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = 500000;
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = 500000;
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = 500000;
            } else {
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] = LMSConstValue.rFBOffsetAdjust(sensorID, i, iValue);
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceX[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getCosLR(sensorID, i));
              (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceY[i] = (int)((CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.distanceR[i] * LMSConstValue.getSinLR(sensorID, i));
            } 
          } 
        } 
      } 
      endIndex = startIndex;
    } catch (Exception e) {
      LMSLog.exception(sensorID, e);
    } 
    return endIndex;
  }
  
  public static void parseLMSAckPermantSend(int sensorID) {
    String result = "";
    String msg = ParseLMSAckCommand.resultStr[sensorID];
    LMSLog.d("LMS_LMS_TELEGRAM", "parseLMSAckPermantSend:" + msg);
    int startIndex = 0;
    int endIndex = -1;
    try {
      startIndex = endIndex + 1;
      endIndex = msg.indexOf('\003', startIndex);
      result = msg.substring(startIndex, endIndex);
      if (result.equals("0")) {
        ParseLMSAckCommand.permentSend[sensorID] = false;
      } else {
        ParseLMSAckCommand.permentSend[sensorID] = true;
      } 
    } catch (Exception e) {
      LMSLog.exception(sensorID, e);
    } 
  }
  
  public static void parseLMSAckOutputRange(int sensorID) {
    String result = "";
    String msg = ParseLMSAckCommand.resultStr[sensorID];
    int startIndex = 0;
    int endIndex = -1;
    try {
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      result = msg.substring(startIndex, endIndex);
      ParseLMSAckCommand.startAngle[sensorID] = (LMSPlatform.hexStringToInt(result) / 10000);
      startIndex = endIndex + 1;
      endIndex = msg.indexOf('\003', startIndex);
      result = msg.substring(startIndex, endIndex);
      ParseLMSAckCommand.stopAngle[sensorID] = (LMSPlatform.hexStringToInt(result) / 10000);
    } catch (Exception e) {
      LMSLog.exception(sensorID, e);
    } 
  }
  
  public static void parseLMSAckFreqAndAngularResolution(int sensorID) {
    String result = "";
    String msg = ParseLMSAckCommand.resultStr[sensorID];
    LMSLog.d("LMS_LMS_TELEGRAM", "parseLMSAckFreqAndAngularResolution:" + msg);
    int startIndex = 0;
    int endIndex = -1;
    try {
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      result = msg.substring(startIndex, endIndex);
      ParseLMSAckCommand.scanFreq[sensorID] = LMSPlatform.hexStringToInt(result) / 100;
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      result = msg.substring(startIndex, endIndex);
      ParseLMSAckCommand.angleResolution[sensorID] = (LMSPlatform.hexStringToInt(result) / 10000.0F);
    } catch (Exception e) {
      LMSLog.exception(sensorID, e);
    } 
  }
  
  public static void parseLMSAckTimeStamp(int sensorID) {
    String result = "";
    String msg = ParseLMSAckCommand.resultStr[sensorID];
    LMSLog.d("LMS_LMS_TELEGRAM", "parseLMSAckTimeStamp:" + msg);
    int startIndex = 0;
    int endIndex = -1;
    try {
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      result = msg.substring(startIndex, endIndex);
      ParseLMSAckCommand.statusCode[sensorID] = LMSPlatform.hexStringToInt(result);
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
    } catch (Exception e) {
      LMSLog.exception(sensorID, e);
    } 
  }
  
  public static int getScanCounterOffset(int sensorID, int firstCounter, int secondCounter) {
    int offset = 0;
    if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B") || 
      LMSConstValue.getSensorType(sensorID).equals("RADAR_FS")) {
      if (firstCounter < secondCounter + 1) {
        offset = firstCounter + 65000 - secondCounter;
      } else {
        offset = firstCounter - secondCounter;
      } 
    } else if (firstCounter < secondCounter + 1) {
      offset = firstCounter + 65535 - secondCounter;
    } else {
      offset = firstCounter - secondCounter;
    } 
    return offset;
  }
  
  public static void checkTelegramCounterLost(int sensorID) {
    if (ParseLMSAckCommand.telegramCounterLast[sensorID] != -1 && 
      ParseLMSAckCommand.telegramCounterLast[sensorID] != (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter && 
      ParseLMSAckCommand.telegramCounterLast[sensorID] + 1 != (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter) {
      LMSLog.d("LMS_LMS_TELEGRAM[" + sensorID + "]", "LLLLLLLLLLLLLLLLLLLL=" + ParseLMSAckCommand.telegramCounterLost[sensorID] + 
          " last=" + ParseLMSAckCommand.telegramCounterLast[sensorID] + " current=" + (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter);
      int offset = getScanCounterOffset(
          sensorID, 
          (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter, 
          ParseLMSAckCommand.telegramCounterLast[sensorID]);
      if (offset > 1) {
        ParseLMSAckCommand.telegramCounterLost[sensorID] = ParseLMSAckCommand.telegramCounterLost[sensorID] + offset - 1;
        String msg = CarDetectSetting.getSensorParameter(sensorID);
        HashMap<String, Comparable> eventExtra = new HashMap<>();
        eventExtra.put("SensorID", Integer.valueOf(sensorID));
        eventExtra.put("lms_parameter", msg);
        LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.LMS_PARAMETER_STRING_INTENT", eventExtra);
        LMSLog.d("LMS_LMS_TELEGRAM[" + sensorID + "]", "telegramCounterLost:" + ParseLMSAckCommand.telegramCounterLost[sensorID]);
      } 
    } 
    ParseLMSAckCommand.telegramCounterLast[sensorID] = (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter;
  }
  
  public static String parseLMSDataFrame(int sensorID, String msg, enumLmsDataField lmsDataFieldType) {
    String result = null;
    int encoderAmount = 0;
    int startIndex = 0;
    int endIndex = -1;
    try {
      for (int i = 0; i <= lmsDataFieldType.ordinal(); i++) {
        if (i == enumLmsDataField.LMS_DATA_FIELD_TelegramCounter.ordinal()) {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
          result = msg.substring(startIndex, endIndex);
          (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.iScanCounter = LMSPlatform.hexStringToInt(result);
          checkTelegramCounterLost(sensorID);
        } else if (i == enumLmsDataField.LMS_DATA_FIELD_TimeOfTrans.ordinal()) {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
          result = msg.substring(startIndex, endIndex);
          (CarDetectSetting.carDetectSetting.dataLayerDataParseRunnable[sensorID]).dataLayerDataParseLineRadar.timeOfTrans = LMSPlatform.hexStringToLong(result);
        } else if (i == enumLmsDataField.LMS_DATA_FIELD_ScanFreq.ordinal()) {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
          result = msg.substring(startIndex, endIndex);
          ParseLMSAckCommand.scanFreq[sensorID] = LMSPlatform.hexStringToInt(result) / 100;
        } else if (i == enumLmsDataField.LMS_DATA_FIELD_MeasFreq.ordinal()) {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
          result = msg.substring(startIndex, endIndex);
          ParseLMSAckCommand.measureFreq[sensorID] = LMSPlatform.hexStringToInt(result) * 100;
          ParseLMSAckCommand.angleResolution[sensorID] = (1.0F / (ParseLMSAckCommand.measureFreq[sensorID] / ParseLMSAckCommand.scanFreq[sensorID] / 360));
        } else if (i == enumLmsDataField.LMS_DATA_FIELD_Encoder_amount.ordinal()) {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
          if (endIndex == -1) {
            Exception e = new Exception();
            throw e;
          } 
          result = msg.substring(startIndex, endIndex);
          encoderAmount = LMSPlatform.hexStringToInt(result);
        } else if (i != enumLmsDataField.LMS_DATA_FIELD_Encoder.ordinal()) {
          if (i == enumLmsDataField.LMS_DATA_FIELD_16bitChannels_amount.ordinal()) {
            startIndex = endIndex + 1;
            endIndex = msg.indexOf(' ', startIndex);
            result = msg.substring(startIndex, endIndex);
            ParseLMSAckCommand.amountOf16bitChannels[sensorID] = LMSPlatform.hexStringToInt(result);
          } else if (i == enumLmsDataField.LMS_DATA_FIELD_16bitChannels.ordinal()) {
            startIndex = endIndex + 1;
            measdata16bit_startIndex = startIndex;
            endIndex = parseLMS16bitDataChannels(
                sensorID, 
                msg, 
                startIndex, 
                ParseLMSAckCommand.amountOf16bitChannels[sensorID], 
                enumLmsData16bitChannelField.LMS_DATA_FIELD_16bit_datas.ordinal() + 1);
          } else {
            startIndex = endIndex + 1;
            endIndex = msg.indexOf(' ', startIndex);
          } 
        } 
      } 
      if (endIndex == -1) {
        Exception e = new Exception();
        throw e;
      } 
      result = msg.substring(startIndex, endIndex);
    } catch (Exception e) {
      LMSLog.exception(sensorID, e);
    } 
    return result;
  }
  
  static int parseLMS16bitDataChannels(int sensorID, String msg, int inputStartIndex, int amount, int nSubField) throws Exception {
    int startIndex = inputStartIndex;
    int endIndex = inputStartIndex;
    for (int i = 0; i < amount; i++) {
      for (int k = 0; k < nSubField; k++) {
        if (k == enumLmsData16bitChannelField.LMS_DATA_FIELD_16bit_startAngle.ordinal()) {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
          if (endIndex == -1) {
            Exception e = new Exception();
            throw e;
          } 
          String result = msg.substring(startIndex, endIndex);
          ParseLMSAckCommand.startAngle[sensorID] = (LMSPlatform.hexStringToInt(result) / 10000);
        } else if (k == enumLmsData16bitChannelField.LMS_DATA_FIELD_16bit_amountOfDatas.ordinal()) {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
          if (endIndex == -1) {
            Exception e = new Exception();
            throw e;
          } 
          String result = msg.substring(startIndex, endIndex);
          ParseLMSAckCommand.measureData16bit_amount[sensorID] = LMSPlatform.hexStringToInt(result);
        } else if (k == enumLmsData16bitChannelField.LMS_DATA_FIELD_16bit_datas.ordinal()) {
          startIndex = endIndex + 1;
          String result = msg.substring(startIndex);
          int measDataIndexLength = getSubFieldAndToFloat(sensorID, result, ParseLMSAckCommand.measureData16bit_amount[sensorID]);
          if (measDataIndexLength != -1) {
            endIndex = startIndex + measDataIndexLength;
          } else {
            LMSLog.w("LMS_LMS_TELEGRAM", "parseLMS16bitDataChannels[" + sensorID + "]:msg=" + msg);
            return -1;
          } 
        } else {
          startIndex = endIndex + 1;
          endIndex = msg.indexOf(' ', startIndex);
        } 
      } 
    } 
    return endIndex;
  }
  
  public static int parseLMSAckCommandNum(String msg) {
    int endIndex = -1;
    endIndex = msg.indexOf('\003', 0);
    return endIndex;
  }
  
  public static void parseLMSAckCommandType(int sensorID, String msg) {
    try {
      if (msg.equals("\002sWA LMPoutputRange\003")) {
        LMSLog.d("LMS_LMS_TELEGRAM", "parseLMSAckCommandType" + msg);
        ParseLMSAckCommand.commandType[sensorID] = 1108;
        return;
      } 
      if (msg.equals("\002sAN SetAccessMode 1\003")) {
        LMSLog.d("LMS_LMS_TELEGRAM", "LOGIN ACK,SENSOR RESTART");
        return;
      } 
      int startIndex = 0;
      int endIndex = msg.indexOf(' ', startIndex);
      if (endIndex == -1) {
        Exception e = new Exception();
        throw e;
      } 
      String lmsAckCommandType = msg.substring(startIndex, endIndex);
      if (lmsAckCommandType.equals("\002sFA")) {
        LMSLog.d("LMS_LMS_TELEGRAM", "parseLMSAckCommandType 1" + msg);
        ParseLMSAckCommand.commandType[sensorID] = 1100;
        ParseLMSAckCommand.resultStr[sensorID] = msg.substring(endIndex + 1);
        return;
      } 
      startIndex = endIndex + 1;
      endIndex = msg.indexOf(' ', startIndex);
      if (endIndex == -1) {
        LMSLog.d("LMS_LMS_TELEGRAM", "unParse msg:" + msg);
      } else {
        String lmsAckCommand = msg.substring(startIndex, endIndex);
        if ((String.valueOf(lmsAckCommandType) + ' ' + lmsAckCommand).equals("\002sSN LMDscandata")) {
          ParseLMSAckCommand.commandType[sensorID] = 1107;
          ParseLMSAckCommand.resultStr[sensorID] = msg.substring(endIndex + 1);
          return;
        } 
        if ((String.valueOf(lmsAckCommandType) + ' ' + lmsAckCommand).equals("\002sRA LMDscandata")) {
          ParseLMSAckCommand.commandType[sensorID] = 1102;
          ParseLMSAckCommand.resultStr[sensorID] = msg.substring(endIndex + 1);
          return;
        } 
        if ((String.valueOf(lmsAckCommandType) + ' ' + lmsAckCommand).equals("\002sEA LMDscandata")) {
          ParseLMSAckCommand.commandType[sensorID] = 1103;
          ParseLMSAckCommand.resultStr[sensorID] = msg.substring(endIndex + 1);
          return;
        } 
        if ((String.valueOf(lmsAckCommandType) + ' ' + lmsAckCommand).equals("\002sRA LMPoutputRange")) {
          ParseLMSAckCommand.commandType[sensorID] = 1104;
          ParseLMSAckCommand.resultStr[sensorID] = msg.substring(endIndex + 1);
          return;
        } 
        if ((String.valueOf(lmsAckCommandType) + ' ' + lmsAckCommand).equals("\002sRA LMPscancfg")) {
          ParseLMSAckCommand.commandType[sensorID] = 1105;
          ParseLMSAckCommand.resultStr[sensorID] = msg.substring(endIndex + 1);
          return;
        } 
        if ((String.valueOf(lmsAckCommandType) + ' ' + lmsAckCommand).equals("\002sRA STlms")) {
          ParseLMSAckCommand.commandType[sensorID] = 1109;
          ParseLMSAckCommand.resultStr[sensorID] = msg.substring(endIndex + 1);
          return;
        } 
      } 
    } catch (Exception e) {
      LMSLog.exception(sensorID, e);
    } 
    ParseLMSAckCommand.commandType[sensorID] = 1100;
  }
  
  public static String getLocalIpAddress() {
    String ipaddress = "";
    int j = 0;
    try {
      Enumeration<NetworkInterface> en = 
        NetworkInterface.getNetworkInterfaces();
      while (en.hasMoreElements()) {
        int i = 0;
        NetworkInterface intf = en.nextElement();
        Enumeration<InetAddress> enumIpAddr = intf
          .getInetAddresses();
        while (enumIpAddr.hasMoreElements()) {
          i++;
          InetAddress inetAddress = enumIpAddr.nextElement();
          LMSLog.d("LMS_LMS_TELEGRAM", "Ip ..." + inetAddress.getHostAddress().toString());
          if (!inetAddress.isLoopbackAddress()) {
            ipaddress = inetAddress.getHostAddress().toString();
            LMSLog.d("LMS_LMS_TELEGRAM", "Ip" + ipaddress);
          } 
        } 
      } 
    } catch (SocketException e) {
      LMSLog.exception(e);
    } 
    return ipaddress;
  }
}
