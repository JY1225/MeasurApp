package algorithmLayer;

import CarAppAlgorithm.LightCurtainAlgorithm;
import CarAppAlgorithm.WidthHeightDetectRunnable;
import Comparator.ComparatorRegionList;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import layer.algorithmLayer.BubbleLine;
import layer.algorithmLayer.Contour;
import layer.algorithmLayer.ContourFilter;
import layer.algorithmLayer.CoutourRegion;
import layer.algorithmLayer.LightCurtainCarInLine;
import layer.algorithmLayer.SingleBubble;
import layer.algorithmLayer.ThreeDPoint;
import layer.algorithmLayer.ZPlane;

public class ContourFilterAlgorithm {
  private final String DEBUG_TAG = "ContourFilterAlgorithm";
  
  class ConjunctRegion {
    ArrayList<Integer> conjunctRegionIndex = new ArrayList<>();
  }
  
  public void coutourLightCurtain(Contour contour) {
    for (int i = 0; i < LightCurtainAlgorithm.lightCurtainCarInLineList.size(); i++) {
      LightCurtainCarInLine lightCurtainCarInLine = LightCurtainAlgorithm.lightCurtainCarInLineList.get(i);
      for (int j = 0; j < LMSConstValue.iLPNum; j++) {
        ThreeDPoint threeDPoint = new ThreeDPoint(10, 1, 0.0F, lightCurtainCarInLine.y[j], lightCurtainCarInLine.length_CurveFitting);
        contour.contourFilter.threeDPointListLightCurtain.add(threeDPoint);
      } 
    } 
  }
  
  float[] zHead = new float[LMSConstValue.LMS_WH_SENSOR_NUM];
  
  float[] zEnd = new float[LMSConstValue.LMS_WH_SENSOR_NUM];
  
  public void coutourFilterRadar(Contour contour) {
    SingleBubble[] singleBubble = new SingleBubble[LMSConstValue.LMS_WH_SENSOR_NUM];
    int[] lineNum = new int[LMSConstValue.LMS_WH_SENSOR_NUM];
    for (int sensorID = 0; sensorID < LMSConstValue.LMS_WH_SENSOR_NUM; sensorID++) {
      contour.contourFilter.threeDPointListSide[sensorID].clear();
      contour.contourFilter.regionListSide[sensorID].clear();
      contour.contourFilter.zPlaneList[sensorID].clear();
      contour.contourFilter.zPlaneFilterResultList[sensorID].clear();
      singleBubble[sensorID] = contour.whBubble[sensorID];
      lineNum[sensorID] = (singleBubble[sensorID]).bubble.size();
    } 
    float[] zStartTmp = new float[LMSConstValue.LMS_WH_SENSOR_NUM];
    zStartTmp[0] = 0.0F;
    if (LMSConstValue.LMS_WH_SENSOR_NUM > 1)
      if (LMSConstValue.iNvramLWDistance2.iValue == 0) {
        int length_CurveFitting0, length_CurveFitting1;
        if (WidthHeightDetectRunnable.bCarBacking(contour)) {
          length_CurveFitting0 = ((BubbleLine)(singleBubble[0]).bubble.get(0)).length_CurveFitting;
          length_CurveFitting1 = ((BubbleLine)(singleBubble[1]).bubble.get(0)).length_CurveFitting;
        } else {
          length_CurveFitting0 = ((BubbleLine)(singleBubble[0]).bubble.get((singleBubble[0]).bubble.size() - 1)).length_CurveFitting;
          length_CurveFitting1 = ((BubbleLine)(singleBubble[1]).bubble.get((singleBubble[1]).bubble.size() - 1)).length_CurveFitting;
        } 
        LMSLog.d("ContourFilterAlgorithm", "0 length_CurveFitting=" + length_CurveFitting0 + " 1 length_CurveFitting=" + length_CurveFitting1);
        contour.zStart[0] = 0.0F;
        contour.zStart[1] = (length_CurveFitting0 - length_CurveFitting1);
        zStartTmp[1] = contour.zStart[1];
        LMSLog.d("ContourFilterAlgorithm", "coutourFilterAlgo contour.zStart[0]=" + contour.zStart[0]);
        LMSLog.d("ContourFilterAlgorithm", "coutourFilterAlgo contour.zStart[1]=" + contour.zStart[1]);
      } else {
        zStartTmp[1] = 0.0F;
      }  
    int i;
    for (i = 0; i < LMSConstValue.LMS_WH_SENSOR_NUM; i++) {
      int leftWindow = LMSConstValue.getLeftWindow(i);
      int rightWindow = LMSConstValue.getRightWindow(i);
      int frontWindow = LMSConstValue.getHeightWindow(i);
      int loopStart = LMSConstValue.filterStartPoint[i];
      int loopEnd = LMSConstValue.filterEndPoint[i];
      if (!WidthHeightDetectRunnable.bCarBacking(contour)) {
        this.zHead[i] = ((BubbleLine)(singleBubble[i]).bubble.get(0)).length_CurveFitting;
        this.zEnd[i] = ((BubbleLine)(singleBubble[i]).bubble.get((singleBubble[i]).bubble.size() - 1)).length_CurveFitting;
      } else {
        this.zHead[i] = ((BubbleLine)(singleBubble[i]).bubble.get((singleBubble[i]).bubble.size() - 1)).length_CurveFitting;
        this.zEnd[i] = ((BubbleLine)(singleBubble[i]).bubble.get(0)).length_CurveFitting;
      } 
      for (int bubbleLineNum = 0; bubbleLineNum < lineNum[i]; bubbleLineNum++) {
        BubbleLine bubbleLine = (singleBubble[i]).bubble.get(bubbleLineNum);
        if (bubbleLineNum < 10 || bubbleLineNum > lineNum[i] - 10 || 
          bLengthEnoughBig(i, contour, bubbleLine.length_CurveFitting)) {
          float z = bubbleLine.length_CurveFitting + zStartTmp[i];
          int zStartIndex = contour.contourFilter.threeDPointListSide[i].size();
          for (int j = loopStart; j < loopEnd; j++) {
            boolean bPointShow = false;
            if (bubbleLine.x[j] != 500000 && 
              bubbleLine.x[j] > -rightWindow && bubbleLine.x[j] < leftWindow && 
              bubbleLine.x[j] < frontWindow && 
              j >= bubbleLine.beginIndex && 
              j <= bubbleLine.endIndex && 
              bubbleLine.y[j] >= LMSConstValue.VALID_THING_HEIGHT[i])
              bPointShow = true; 
            if (bPointShow) {
              int x;
              if (LMSConstValue.LMS_WH_SENSOR_NUM == 1) {
                x = bubbleLine.x[j];
              } else if (i == 0) {
                if (!(LMSConstValue.fixMethod[i]).key.equals("UP_FIX")) {
                  contour.contourFilter.bigView = true;
                  x = -bubbleLine.x[j] + LMSConstValue.longWH0Distance;
                } else if ((singleBubble[i]).bLeft) {
                  x = -bubbleLine.x[j] + LMSConstValue.longWH0Distance;
                } else {
                  x = bubbleLine.x[j] + LMSConstValue.longWH0Distance;
                } 
              } else if (!(LMSConstValue.fixMethod[i]).key.equals("UP_FIX")) {
                contour.contourFilter.bigView = false;
                x = bubbleLine.x[j] - LMSConstValue.iNvramLRDistance.iValue - LMSConstValue.longWH0Distance;
              } else if ((singleBubble[i]).bLeft) {
                x = bubbleLine.x[j] - LMSConstValue.iNvramLRDistance.iValue - LMSConstValue.longWH0Distance;
              } else {
                x = -bubbleLine.x[j] - LMSConstValue.iNvramLRDistance.iValue - LMSConstValue.longWH0Distance;
              } 
              ThreeDPoint threeDPoint = new ThreeDPoint(i, -1, x, bubbleLine.y[j], z);
              contour.contourFilter.threeDPointListSide[i].add(threeDPoint);
            } 
          } 
          int zEndIndex = contour.contourFilter.threeDPointListSide[i].size() - 1;
          ZPlane zPlane = new ZPlane(bubbleLine.length_CurveFitting, z, zStartIndex, zEndIndex);
          contour.contourFilter.zPlaneList[i].add(zPlane);
        } 
      } 
      LMSLog.d("ContourFilterAlgorithm", 
          "lineNum[" + i + "]=" + lineNum[i] + 
          " zPlaneNum[" + i + "]=" + contour.contourFilter.zPlaneList[i].size() + 
          " carLength=" + contour.carLength);
    } 
    for (i = 0; i < LMSConstValue.LMS_WH_SENSOR_NUM; i++) {
      if (!ContourFilter.bCoutourFilter) {
        CoutourRegion region = new CoutourRegion();
        for (int j = 0; j < contour.contourFilter.threeDPointListSide[i].size(); j++) {
          ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[i].get(j);
          threeDPoint.region = 0;
          region.index.add(Integer.valueOf(j));
        } 
        contour.contourFilter.regionListSide[i].add(region);
      } else if (LMSConstValue.getSensorType(0).equals("RADAR_B") || 
        LMSConstValue.getSensorType(0).equals("RADAR_FS")) {
        LMSLog.d("ContourFilterAlgorithm" + i, "FRIEND FILTER BIG======");
        contour.contourFilter.getClass();
        contour.contourFilter.getClass();
        contour.contourFilter.getClass();
        contour.contourFilter.getClass();
        friendDistanceFilter(-2, i, contour, 100, 20000, 60, 30);
      } else {
        contour.contourFilter.getClass();
        contour.contourFilter.getClass();
        contour.contourFilter.getClass();
        contour.contourFilter.getClass();
        friendDistanceFilter(-2, i, contour, 100, 20000, 100, 3);
      } 
    } 
    for (i = 0; i < LMSConstValue.LMS_WH_SENSOR_NUM; i++) {
      CoutourRegion region0 = contour.contourFilter.regionListSide[i].get(0);
      for (Iterator<Integer> it = region0.index.iterator(); it.hasNext(); ) {
        int index = ((Integer)it.next()).intValue();
        ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[i].get(index);
        if (threeDPoint.region != 0)
          it.remove(); 
      } 
      contour.contourFilter.regionListSide[i].set(0, region0);
      for (int z = 0; z < contour.contourFilter.zPlaneList[i].size(); z++) {
        ZPlane zPlane = contour.contourFilter.zPlaneList[i].get(z);
        boolean bStart = true;
        int startIndex = 0, endIndex = 0;
        int widthMaxIndex = 0;
        float widthOffset = 0.0F;
        for (int j = zPlane.startIndex; j <= zPlane.endIndex; j++) {
          ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[i].get(j);
          if (threeDPoint.region == 0) {
            if (bStart) {
              bStart = false;
              startIndex = j;
              widthOffset = threeDPoint.x;
              widthMaxIndex = j;
            } else if (i == 0) {
              if (widthOffset < threeDPoint.x) {
                widthOffset = threeDPoint.x;
                widthMaxIndex = j;
              } 
            } else if (widthOffset > threeDPoint.x) {
              widthOffset = threeDPoint.x;
              widthMaxIndex = j;
            } 
            endIndex = j;
          } 
        } 
        if (!bStart) {
          ZPlane zPlaneFilterResult = new ZPlane(zPlane.length_CurveFitting, zPlane.z, startIndex, endIndex);
          zPlaneFilterResult.widthMaxIndex = widthMaxIndex;
          contour.contourFilter.zPlaneFilterResultList[i].add(zPlaneFilterResult);
        } 
      } 
      contour.contourFilter.threeDPointNumAll += contour.contourFilter.threeDPointListSide[i].size();
    } 
    LMSLog.d("ContourFilterAlgorithm", " threeDPointAll=" + contour.contourFilter.threeDPointNumAll);
    creatShengJingWangLuo(contour);
  }
  
  boolean bLengthEnoughBig(int sensorID, Contour contour, int length) {
    for (int i = 0; i < contour.contourFilter.zPlaneList[sensorID].size(); i++) {
      if (Math.abs(length - ((ZPlane)contour.contourFilter.zPlaneList[sensorID].get(i)).length_CurveFitting) < 15)
        return false; 
    } 
    return true;
  }
  
  int iMaxRegion = 0;
  
  int friendDistanceFilterZPlane(Contour contour, int sensorID, ThreeDPoint threeDPointOri, int jOriLoop, int zCmpLoopStart, int friend_distance, int friend_distance_square, int friend_distance_z) {
    boolean bZPlaneStart = true;
    int zPlaneStartIndex = 0;
    ConjunctRegion conjunctRegion = new ConjunctRegion();
    boolean bCmpFinish = false;
    for (int zCmpLoop = zCmpLoopStart; zCmpLoop < contour.contourFilter.zPlaneList[sensorID].size(); zCmpLoop++) {
      ZPlane zPlaneCmp = contour.contourFilter.zPlaneList[sensorID].get(zCmpLoop);
      if (Math.abs(threeDPointOri.z - zPlaneCmp.z) <= friend_distance_z) {
        if (bZPlaneStart) {
          zPlaneStartIndex = zCmpLoop;
          bZPlaneStart = false;
        } 
        float xi = threeDPointOri.x;
        float yi = threeDPointOri.y;
        float zi = threeDPointOri.z;
        if (bCmpFinish)
          break; 
        for (int j = zPlaneCmp.startIndex; j <= zPlaneCmp.endIndex; j++) {
          if (bCmpFinish)
            break; 
          ThreeDPoint threeDPointCmp = contour.contourFilter.threeDPointListSide[sensorID].get(j);
          if (threeDPointCmp.region == -1) {
            bCmpFinish = true;
          } else if (threeDPointCmp.region >= 0) {
            float xj = threeDPointCmp.x;
            float yj = threeDPointCmp.y;
            if (xi - xj <= friend_distance && xi - xj >= -friend_distance && yi - yj <= friend_distance && yi - yj >= -friend_distance) {
              float distSquare = (xi - xj) * (xi - xj) + (yi - yj) * (yi - yj);
              if (distSquare <= friend_distance_square) {
                if (conjunctRegion.conjunctRegionIndex.size() == 0) {
                  threeDPointOri.region = threeDPointCmp.region;
                  contour.contourFilter.threeDPointListSide[sensorID].set(jOriLoop, threeDPointOri);
                  conjunctRegion.conjunctRegionIndex.add(Integer.valueOf(threeDPointCmp.region));
                } 
                if (!bHasConjunctRegion(threeDPointCmp.region, conjunctRegion))
                  conjunctRegion.conjunctRegionIndex.add(Integer.valueOf(threeDPointCmp.region)); 
              } 
            } 
          } 
        } 
      } 
    } 
    if (conjunctRegion.conjunctRegionIndex.size() == 0) {
      threeDPointOri.region = this.iMaxRegion;
      contour.contourFilter.threeDPointListSide[sensorID].set(jOriLoop, threeDPointOri);
      this.iMaxRegion++;
    } else if (conjunctRegion.conjunctRegionIndex.size() > 1) {
      int iInitIndex = ((Integer)conjunctRegion.conjunctRegionIndex.get(0)).intValue();
      for (int c = 1; c < conjunctRegion.conjunctRegionIndex.size(); c++) {
        int iConjunctIndex = ((Integer)conjunctRegion.conjunctRegionIndex.get(c)).intValue();
        for (int j = 0; j < contour.contourFilter.threeDPointListSide[sensorID].size(); j++) {
          ThreeDPoint threeDPointTmp = contour.contourFilter.threeDPointListSide[sensorID].get(j);
          if (threeDPointTmp.region == -1)
            break; 
          if (threeDPointTmp.region == iConjunctIndex) {
            threeDPointTmp.region = iInitIndex;
            contour.contourFilter.threeDPointListSide[sensorID].set(j, threeDPointTmp);
          } 
        } 
      } 
    } 
    return zPlaneStartIndex;
  }
  
  void friendDistanceFilterPoint(Contour contour, int sensorID, ThreeDPoint threeDPointOri, int jOriLoop, int friend_distance, int friend_distance_square, int friend_distance_z) {
    ConjunctRegion conjunctRegion = new ConjunctRegion();
    int iLastRegion = 500000;
    if (!WidthHeightDetectRunnable.bCarBacking(contour)) {
      for (int i = jOriLoop - 1; i >= 0; ) {
        ThreeDPoint threeDPointCmp = contour.contourFilter.threeDPointListSide[sensorID].get(i);
        if (threeDPointOri.z - threeDPointCmp.z <= friend_distance_z) {
          if (threeDPointCmp.region != iLastRegion)
            if ((threeDPointOri.x - threeDPointCmp.x) * (threeDPointOri.x - threeDPointCmp.x) + (threeDPointOri.y - threeDPointCmp.y) * (threeDPointOri.y - threeDPointCmp.y) <= 
              friend_distance_square)
              if (conjunctRegion.conjunctRegionIndex.size() == 0) {
                threeDPointOri.region = threeDPointCmp.region;
                iLastRegion = threeDPointOri.region;
                conjunctRegion.conjunctRegionIndex.add(Integer.valueOf(threeDPointCmp.region));
              } else if (!bHasConjunctRegion(threeDPointCmp.region, conjunctRegion)) {
                iLastRegion = threeDPointOri.region;
                conjunctRegion.conjunctRegionIndex.add(Integer.valueOf(threeDPointCmp.region));
              }   
          i--;
        } 
        break;
      } 
    } else {
      for (int i = jOriLoop - 1; i >= 0; ) {
        ThreeDPoint threeDPointCmp = contour.contourFilter.threeDPointListSide[sensorID].get(i);
        if (threeDPointOri.z - threeDPointCmp.z >= -friend_distance_z) {
          if (threeDPointCmp.region != iLastRegion)
            if ((threeDPointOri.x - threeDPointCmp.x) * (threeDPointOri.x - threeDPointCmp.x) + (threeDPointOri.y - threeDPointCmp.y) * (threeDPointOri.y - threeDPointCmp.y) <= 
              friend_distance_square)
              if (conjunctRegion.conjunctRegionIndex.size() == 0) {
                threeDPointOri.region = threeDPointCmp.region;
                iLastRegion = threeDPointOri.region;
                conjunctRegion.conjunctRegionIndex.add(Integer.valueOf(threeDPointCmp.region));
              } else if (!bHasConjunctRegion(threeDPointCmp.region, conjunctRegion)) {
                iLastRegion = threeDPointOri.region;
                conjunctRegion.conjunctRegionIndex.add(Integer.valueOf(threeDPointCmp.region));
              }   
          i--;
        } 
        break;
      } 
    } 
    if (conjunctRegion.conjunctRegionIndex.size() == 0) {
      threeDPointOri.region = this.iMaxRegion;
      this.iMaxRegion++;
    } else if (conjunctRegion.conjunctRegionIndex.size() > 1) {
      int iInitIndex = ((Integer)conjunctRegion.conjunctRegionIndex.get(0)).intValue();
      for (int c = 1; c < conjunctRegion.conjunctRegionIndex.size(); c++) {
        int iConjunctIndex = ((Integer)conjunctRegion.conjunctRegionIndex.get(c)).intValue();
        for (int j = 0; j < jOriLoop; j++) {
          ThreeDPoint threeDPointTmp = contour.contourFilter.threeDPointListSide[sensorID].get(j);
          if (threeDPointTmp.region == iConjunctIndex)
            threeDPointTmp.region = iInitIndex; 
        } 
      } 
    } 
  }
  
  void friendDistanceFilter(int filterLevel, int sensorID, Contour contour, int friend_distance, int friend_distance_square, int friend_distance_z, int friend_filter_point) {
    long lLastTime = LMSConstValue.getCurrentLTime();
    this.iMaxRegion = 0;
    for (int i = 0; i < contour.contourFilter.threeDPointListSide[sensorID].size(); i++) {
      ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(i);
      friendDistanceFilterPoint(
          contour, 
          sensorID, threeDPoint, i, 
          friend_distance, friend_distance_square, 
          friend_distance_z);
    } 
    long lCurrentTime = LMSConstValue.getCurrentLTime();
    LMSLog.d("ContourFilterAlgorithm", "friendDistanceFilter time dif=" + (lCurrentTime - lLastTime));
    for (int j = 0; j < contour.contourFilter.threeDPointListSide[sensorID].size(); j++) {
      int pointRegion = ((ThreeDPoint)contour.contourFilter.threeDPointListSide[sensorID].get(j)).region;
      if (pointRegion >= -1) {
        boolean bAdded = false;
        for (int m = 0; m < contour.contourFilter.regionListSide[sensorID].size(); m++) {
          CoutourRegion contourRegion = contour.contourFilter.regionListSide[sensorID].get(m);
          if (pointRegion == contourRegion.region) {
            bAdded = true;
            contourRegion.index.add(Integer.valueOf(j));
            contour.contourFilter.regionListSide[sensorID].set(m, contourRegion);
            break;
          } 
        } 
        if (!bAdded) {
          CoutourRegion coutourRegion = new CoutourRegion();
          coutourRegion.region = pointRegion;
          coutourRegion.index.add(Integer.valueOf(j));
          contour.contourFilter.regionListSide[sensorID].add(coutourRegion);
          if (contour.contourFilter.regionListSide[sensorID].size() > 1000) {
            LMSLog.d("ContourFilterAlgorithm", "regionListSide too much!!!");
            break;
          } 
        } 
      } 
    } 
    ComparatorRegionList comparator = new ComparatorRegionList();
    Collections.sort(contour.contourFilter.regionListSide[sensorID], (Comparator)comparator);
    CoutourRegion region0 = contour.contourFilter.regionListSide[sensorID].get(0);
    for (int loop = 0; loop < region0.index.size(); loop++) {
      int index = ((Integer)region0.index.get(loop)).intValue();
      ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(index);
      threeDPoint.region = 0;
    } 
    for (int k = 1; k < contour.contourFilter.regionListSide[sensorID].size(); k++) {
      CoutourRegion region = contour.contourFilter.regionListSide[sensorID].get(k);
      int size = region.index.size();
      if ((friend_filter_point >= 0 && size > friend_filter_point) || 
        bHeadRegion(sensorID, contour, region) || 
        bEndRegion(sensorID, contour, region)) {
        for (int m = 0; m < size; m++) {
          int index = ((Integer)region.index.get(m)).intValue();
          ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(index);
          threeDPoint.region = 0;
        } 
        region0.index.addAll(region.index);
      } else {
        for (int m = 0; m < size; m++) {
          int index = ((Integer)region.index.get(m)).intValue();
          ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(index);
          threeDPoint.region = filterLevel;
        } 
      } 
    } 
  }
  
  boolean bHeadRegion(int sensorID, Contour contour, CoutourRegion region) {
    int size = region.index.size();
    for (int loop = 0; loop < size; loop++) {
      int index = ((Integer)region.index.get(loop)).intValue();
      ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(index);
      contour.contourFilter.getClass();
      if (threeDPoint.z > this.zHead[sensorID] + 300.0F)
        return false; 
    } 
    return true;
  }
  
  boolean bEndRegion(int sensorID, Contour contour, CoutourRegion region) {
    int size = region.index.size();
    for (int loop = 0; loop < size; loop++) {
      int index = ((Integer)region.index.get(loop)).intValue();
      ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(index);
      contour.contourFilter.getClass();
      if (threeDPoint.z < this.zEnd[sensorID] - 300.0F)
        return false; 
    } 
    return true;
  }
  
  void creatShengJingWangLuo(Contour contour) {
    boolean bShengJingWangLuo = false;
    if (bShengJingWangLuo) {
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
      String time = df.format(new Date());
      FileWriter fileWriter = null;
      try {
        fileWriter = new FileWriter(String.valueOf(time) + "_carData.txt");
      } catch (IOException e) {
        e.printStackTrace();
      } 
      for (int sensorID = 0; sensorID < LMSConstValue.LMS_WH_SENSOR_NUM; sensorID++) {
        CoutourRegion region0 = contour.contourFilter.regionListSide[sensorID].get(0);
        int size = region0.index.size();
        for (int j = 0; j < size; j++) {
          int index = ((Integer)region0.index.get(j)).intValue();
          ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(index);
          try {
            fileWriter.write(String.valueOf(threeDPoint.x) + " " + threeDPoint.y + " " + threeDPoint.z + "\n");
          } catch (IOException e) {
            e.printStackTrace();
          } 
        } 
      } 
    } 
  }
  
  boolean bHasConjunctRegion(int region, ConjunctRegion conjunctRegion) {
    for (int i = 0; i < conjunctRegion.conjunctRegionIndex.size(); i++) {
      if (region == ((Integer)conjunctRegion.conjunctRegionIndex.get(i)).intValue())
        return true; 
    } 
    return false;
  }
  
  public void volumnAlgo(Contour contour) {
    float volumn = 0.0F;
    float volumnZPlane = 0.0F;
    for (int sensorID = 0; sensorID < LMSConstValue.LMS_WH_SENSOR_NUM; sensorID++) {
      int zPlaneSize = contour.contourFilter.zPlaneFilterResultList[sensorID].size();
      for (int zPlaneLoop = 0; zPlaneLoop < zPlaneSize; zPlaneLoop++) {
        int iAdjustIndex = WidthHeightDetectRunnable.iGetLoopIndexWithCarBacking(contour, zPlaneSize, zPlaneLoop);
        ZPlane zPlane = contour.contourFilter.zPlaneFilterResultList[sensorID].get(iAdjustIndex);
        if (iAdjustIndex > 0) {
          ZPlane zPlaneOld = contour.contourFilter.zPlaneFilterResultList[sensorID].get(iAdjustIndex - 1);
          volumn += volumnZPlane * Math.abs(zPlane.z - zPlaneOld.z);
        } 
        if (zPlane.endIndex - zPlane.startIndex > 0) {
          ThreeDPoint threeDPoint0 = contour.contourFilter.threeDPointListSide[sensorID].get(zPlane.startIndex);
          for (int index = zPlane.startIndex + 1; index <= zPlane.endIndex; index++) {
            ThreeDPoint threeDPoint1 = contour.contourFilter.threeDPointListSide[sensorID].get(index);
            volumnZPlane += (threeDPoint0.y + threeDPoint1.y) * Math.abs(threeDPoint1.x - threeDPoint0.x) / 2.0F;
            threeDPoint0 = threeDPoint1;
          } 
        } 
      } 
    } 
    volumn /= 2.0F;
    LMSLog.d("ContourFilterAlgorithm", "volumn=" + volumn);
  }
}
