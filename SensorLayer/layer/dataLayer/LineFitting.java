package layer.dataLayer;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import java.util.LinkedList;

public class LineFitting {
  private final String DEBUG_TAG = "LineFitting";
  
  float[] xIn = new float[] { 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F };
  
  float[] yIn = new float[] { 2.1F, 4.1F, 5.9F, 8.0F, 9.8F, 12.3F, 14.1F, 15.9F, 18.2F, 19.8F };
  
  void LineFittingAlgo(float[] x, float[] y) {
    float xmean = 0.0F;
    float ymean = 0.0F;
    int size = x.length;
    for (int i = 0; i < size; i++) {
      xmean += x[i];
      ymean += y[i];
    } 
    xmean /= size;
    ymean /= size;
    float sumx2 = 0.0F;
    float sumxy = 0.0F;
    for (int j = 0; j < size; j++) {
      sumx2 += (x[j] - xmean) * (x[j] - xmean);
      sumxy += (y[j] - ymean) * (x[j] - xmean);
    } 
    float A = sumxy / sumx2;
    float B = ymean - A * xmean;
    LMSLog.d("LineFitting", "A=" + A);
    LMSLog.d("LineFitting", "B=" + B);
  }
  
  public float[] LineFittingLinerCompensateInt(int[] x, int[] y, int size) {
    float A;
    int realSize = 0;
    for (int i = 0; i < size; i++) {
      if (x[i] != 0 && y[i] != 0 && x[i] != y[i])
        realSize++; 
    } 
    float B = 0.0F;
    float xmean = 0.0F;
    float ymean = 0.0F;
    for (int j = 0; j < realSize; j++) {
      if (x[j] != 0 && y[j] != 0 && x[j] != y[j]) {
        xmean += x[j];
        ymean += y[j];
      } 
    } 
    xmean /= realSize;
    ymean /= realSize;
    float sumx2 = 0.0F;
    float sumxy = 0.0F;
    if (realSize == 0) {
      A = 1.0F;
      B = 0.0F;
    } else if (realSize == 1) {
      A = 1.0F;
      for (int k = 0; k < realSize; k++) {
        if (x[k] != 0 && y[k] != 0)
          B = (y[k] - x[k]); 
      } 
    } else {
      for (int k = 0; k < realSize; k++) {
        if (x[k] != 0 && y[k] != 0 && x[k] != y[k]) {
          sumx2 += (x[k] - xmean) * (x[k] - xmean);
          sumxy += (y[k] - ymean) * (x[k] - xmean);
        } 
      } 
      A = sumxy / sumx2;
      B = ymean - A * xmean;
    } 
    float[] result = { A, B };
    return result;
  }
  
  private int scanCounterFitting(int sensorID, int iScanCounterStart, int iScanCounter) {
    int max = 0;
    if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B") || 
      LMSConstValue.getSensorType(sensorID).equals("RADAR_FS")) {
      max = 65000;
    } else if (LMSConstValue.getSensorType(sensorID).equals("LIGHT_CURTAIN") || 
      LMSConstValue.getSensorType(sensorID).equals("XZY_2") || 
      LMSConstValue.getSensorType(sensorID).equals("XZY_840") || 
      LMSConstValue.getSensorType(sensorID).equals("XZY_1600")) {
      max = -1;
    } else if (LMSConstValue.getSensorType(sensorID).equals("PS_16I")) {
      max = 65535;
    } else {
      max = 65535;
    } 
    if (iScanCounter >= iScanCounterStart)
      return iScanCounter; 
    return iScanCounter + max;
  }
  
  public boolean bScanCounterContinued_Get(int sensorID, int iScanCounter, int iNextScanCounter) {
    int iScanCounter_Fitting = scanCounterFitting(sensorID, iScanCounter, iNextScanCounter);
    if (LMSConstValue.getSensorType(sensorID).equals("LIGHT_CURTAIN")) {
      if (iScanCounter_Fitting - iScanCounter < 20 && iScanCounter_Fitting - iScanCounter > 0)
        return true; 
      return false;
    } 
    if (LMSConstValue.getSensorType(sensorID).equals("ZM10"))
      return true; 
    if (iScanCounter_Fitting - iScanCounter == 1)
      return true; 
    return false;
  }
  
  private int getTimeMaxOffset(int sensorID) {
    if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B") || 
      LMSConstValue.getSensorType(sensorID).equals("RADAR_FS")) {
      if (LMSConstValue.bProtocolOld_radarB)
        return 150; 
      return 180;
    } 
    if (LMSConstValue.getSensorType(sensorID).equals("LIGHT_CURTAIN"))
      return 700; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_2"))
      return 500; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_840"))
      return 500; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_1600"))
      return 500; 
    if (LMSConstValue.getSensorType(sensorID).equals("PS_16I"))
      return 30; 
    if (LMSConstValue.getSensorType(sensorID).equals("ZM10"))
      return 300; 
    if (LMSConstValue.getSensorType(sensorID).equals("LMS1XX")) {
      if (sensorID == LMSConstValue.getLongSensorID())
        return 100; 
      return 50;
    } 
    return 2;
  }
  
  private int getLineScanCounterOffset(int sensorID) {
    if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B") || 
      LMSConstValue.getSensorType(sensorID).equals("RADAR_FS"))
      return 3; 
    if (LMSConstValue.getSensorType(sensorID).equals("LIGHT_CURTAIN"))
      return 500; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_2"))
      return 105; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_840"))
      return 105; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_1600"))
      return 60; 
    if (LMSConstValue.getSensorType(sensorID).equals("PS_16I"))
      return 3; 
    if (LMSConstValue.getSensorType(sensorID).equals("ZM10"))
      return 3; 
    if (LMSConstValue.getSensorType(sensorID).equals("LMS1XX"))
      return 3; 
    return 3;
  }
  
  protected int getTimeListSize(int sensorID) {
    if (LMSConstValue.getSensorType(sensorID).equals("RADAR_B") || 
      LMSConstValue.getSensorType(sensorID).equals("RADAR_FS"))
      return 180; 
    if (LMSConstValue.getSensorType(sensorID).equals("LIGHT_CURTAIN"))
      return 20; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_2"))
      return 20; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_840"))
      return 20; 
    if (LMSConstValue.getSensorType(sensorID).equals("XZY_1600"))
      return 20; 
    if (LMSConstValue.getSensorType(sensorID).equals("PS_16I"))
      return 100; 
    if (LMSConstValue.getSensorType(sensorID).equals("ZM10"))
      return 50; 
    if (LMSConstValue.getSensorType(sensorID).equals("LMS1XX")) {
      if (sensorID == LMSConstValue.getLongSensorID())
        return 40; 
      return 80;
    } 
    return 20;
  }
  
  public double[] fittingTime(int sensorID, LinkedList<DataLayerDataParseLine> timeList, boolean bLog) {
    double A = 0.0D;
    double B = 0.0D;
    double xmean = 0.0D;
    double ymean = 0.0D;
    int validSize = 0;
    int loopSize = timeList.size();
    DataLayerDataParseLine timeLine0 = timeList.get(0);
    for (int i = 0; i < loopSize; i++) {
      DataLayerDataParseLine timeLine = timeList.get(i);
      if (timeLine.bTimeValid) {
        int iScanCounter_Fitting = scanCounterFitting(sensorID, timeLine0.iScanCounter, timeLine.iScanCounter);
        long x = iScanCounter_Fitting;
        long y = timeLine.lTimeOfReceived;
        xmean += x;
        ymean += y;
        validSize++;
      } 
    } 
    if (validSize > 0) {
      xmean /= validSize;
      ymean /= validSize;
    } 
    double sumx2 = 0.0D;
    double sumxy = 0.0D;
    for (int j = 0; j < loopSize; j++) {
      DataLayerDataParseLine timeLine = timeList.get(j);
      if (timeLine.bTimeValid) {
        int iScanCounter_Fitting = scanCounterFitting(sensorID, timeLine0.iScanCounter, timeLine.iScanCounter);
        long x = iScanCounter_Fitting;
        long y = timeLine.lTimeOfReceived;
        sumx2 += (x - xmean) * (x - xmean);
        sumxy += (y - ymean) * (x - xmean);
      } 
    } 
    if (sumx2 > 0.0D) {
      A = sumxy / sumx2;
      B = ymean - A * xmean;
    } 
    if (bLog && sensorID == 2) {
      LMSLog.d("LineFitting" + sensorID, "LineFitting size=" + timeList.size());
      LMSLog.d("LineFitting" + sensorID, "LineFitting timeOfReceived0=" + ((DataLayerDataParseLine)timeList.get(0)).sTimeOfReceived);
      LMSLog.d("LineFitting" + sensorID, "LineFitting timeOfReceived1=" + ((DataLayerDataParseLine)timeList.get(timeList.size() - 1)).sTimeOfReceived);
      LMSLog.d("LineFitting" + sensorID, "LineFitting A=" + A);
      LMSLog.d("LineFitting" + sensorID, "LineFitting B=" + B);
    } 
    double[] lineFittingParam = { A, B };
    return lineFittingParam;
  }
  
  public void adjustBaseTime(int sensorID, BaseTime baseTime, LinkedList<DataLayerDataParseLine> timeList, boolean bLog) {
    for (int i = 0; i < timeList.size() - 1; i++) {
      DataLayerDataParseLine timeLineNext = timeList.get(i + 1);
      DataLayerDataParseLine timeLine = timeList.get(i);
      int iScanCounter_Fitting = scanCounterFitting(sensorID, timeLine.iScanCounter, timeLineNext.iScanCounter);
      if (Math.abs(timeLineNext.lTimeOfReceived - timeLine.lTimeOfReceived) > getTimeMaxOffset(sensorID) || 
        iScanCounter_Fitting - timeLine.iScanCounter > getLineScanCounterOffset(sensorID)) {
        timeLine.bTimeValid = false;
        if (bLog && sensorID == 2)
          LMSLog.d("LineFitting" + sensorID, "inValid: timeLineNext=" + 
              timeLineNext.sTimeOfReceived + 
              " timeLine=" + timeLine.sTimeOfReceived + 
              " iScanCounter_Fitting=" + iScanCounter_Fitting + 
              " timeLine.iScanCounter=" + timeLine.iScanCounter + 
              " dif=" + (timeLineNext.lTimeOfReceived - timeLine.lTimeOfReceived)); 
      } else {
        timeLine.bTimeValid = true;
      } 
    } 
    if (bLog && sensorID == 2)
      LMSLog.d("LineFitting" + sensorID, "valid-------------"); 
    double[] lineFittingParam = fittingTime(sensorID, timeList, bLog);
    DataLayerDataParseLine timeLine0 = timeList.get(0);
    baseTime.iScanCounter = timeLine0.iScanCounter;
    baseTime.A = lineFittingParam[0];
    baseTime.B = lineFittingParam[1];
  }
  
  public long getLTimeOfReceived_LineFitting(int sensorID, int iScanCounter, int iScanCounter_start, double B, double A) {
    long lTimeOfReceived_LineFitting = 0L;
    if (A > 0.0D) {
      int iScanCounter_Fitting = scanCounterFitting(sensorID, iScanCounter_start, iScanCounter);
      lTimeOfReceived_LineFitting = (long)(B + iScanCounter_Fitting * A);
      return lTimeOfReceived_LineFitting;
    } 
    return 0L;
  }
}
