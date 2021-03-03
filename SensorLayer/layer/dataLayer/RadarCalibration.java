package layer.dataLayer;

import java.util.ArrayList;
import java.util.HashMap;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSToken;
import layer.algorithmLayer.ParseLMSAckCommand;

public class RadarCalibration
{
    private String DEBUG_TAG;
    protected boolean bGetBase;
    protected int iGetBaseNum;
    private static final int I_GET_BASE_POINT_NUM = 10;
    public static final int I_GET_BASE_TIME = 100;
    private int[][] tmpBaseX;
    private int[][] tmpBaseY;
    private int[][] baseX;
    private int[][] baseY;
    public static boolean[] bBiaoDing;
    public static int iGetMarkSensorID;
    public static int[] fsrl_width_x;
    public static int[] fsrl_width_y;
    public static int[] iGetFSRLBaseTime;
    public static int[] iGetFSRLCurrentTime;
    public static int[] iGetFSRLDistanceBetweenTime;
    public static int iGetFSRLLengthTime;
    public static int[] iGetFSRLWidthTime;
    public static LMSToken tokenStaticGetWidth;
    
    static {
        RadarCalibration.bBiaoDing = new boolean[10];
        RadarCalibration.iGetMarkSensorID = 0;
        RadarCalibration.fsrl_width_x = new int[10];
        RadarCalibration.fsrl_width_y = new int[10];
        RadarCalibration.iGetFSRLBaseTime = new int[10];
        RadarCalibration.iGetFSRLCurrentTime = new int[10];
        RadarCalibration.iGetFSRLDistanceBetweenTime = new int[10];
        RadarCalibration.iGetFSRLLengthTime = 0;
        RadarCalibration.iGetFSRLWidthTime = new int[2];
        RadarCalibration.tokenStaticGetWidth = new LMSToken();
    }
    
    public RadarCalibration() {
        this.DEBUG_TAG = "RadarCalibration";
        this.tmpBaseX = new int[100][721];
        this.tmpBaseY = new int[100][721];
        this.baseX = new int[10][721];
        this.baseY = new int[10][721];
    }
    
    public void baseFilter(final int sensorID) {
        final int[] sumBaseX = new int[721];
        final int[] sumBaseY = new int[721];
        final int[] tmpBaseMeanX = new int[721];
        final int[] tmpBaseMeanY = new int[721];
        for (int i = 0; i < 721; ++i) {
            int validBaseTime = 0;
            for (int j = 0; j < 100; ++j) {
                if (this.tmpBaseY[j][i] != 500000 && this.tmpBaseY[j][i] != 0) {
                    final int[] array = sumBaseX;
                    final int n = i;
                    array[n] += this.tmpBaseX[j][i];
                    final int[] array2 = sumBaseY;
                    final int n2 = i;
                    array2[n2] += this.tmpBaseY[j][i];
                    ++validBaseTime;
                }
            }
            if (validBaseTime > 0) {
                tmpBaseMeanX[i] = sumBaseX[i] / validBaseTime;
                tmpBaseMeanY[i] = sumBaseY[i] / validBaseTime;
            }
            else {
                tmpBaseMeanY[i] = (tmpBaseMeanX[i] = 0);
            }
        }
        for (int i = 0; i < 721; ++i) {
            sumBaseY[i] = (sumBaseX[i] = 0);
            int validBaseTime = 0;
            for (int j = 0; j < 100; ++j) {
                if (this.tmpBaseY[j][i] != 500000 && this.tmpBaseY[j][i] != 0 && Math.abs(tmpBaseMeanY[i] - this.tmpBaseY[j][i]) < 10 && Math.abs(tmpBaseMeanX[i] - this.tmpBaseX[j][i]) < 10) {
                    ++validBaseTime;
                    final int[] array3 = sumBaseX;
                    final int n3 = i;
                    array3[n3] += this.tmpBaseX[j][i];
                    final int[] array4 = sumBaseY;
                    final int n4 = i;
                    array4[n4] += this.tmpBaseY[j][i];
                }
            }
            if (validBaseTime > 0) {
                this.baseX[sensorID][i] = sumBaseX[i] / validBaseTime;
                this.baseY[sensorID][i] = sumBaseY[i] / validBaseTime;
            }
            else {
                this.baseX[sensorID][i] = 500000;
                this.baseY[sensorID][i] = 500000;
            }
        }
    }
    
    void baseCalcAlgo(final int sensorID) {
        double[] fit = new double[2];
        final ArrayList<BaseLinePoint> baseLinePointList = new ArrayList<BaseLinePoint>();
        int iGroundStartIndex = LMSConstValue.angleToIndex(sensorID, (float)LMSConstValue.iGroundStartAngle[sensorID]);
        int iGroundEndIndex = LMSConstValue.angleToIndex(sensorID, (float)LMSConstValue.iGroundEndAngle[sensorID]);
        if (iGroundStartIndex < 0) {
            iGroundStartIndex = 0;
        }
        if (iGroundEndIndex > 721) {
            iGroundEndIndex = 721;
        }
        this.baseFilter(sensorID);
        for (int i = iGroundStartIndex; i < iGroundEndIndex; ++i) {
            if (this.baseY[sensorID][i] != 500000 && this.baseY[sensorID][i] != 0) {
                final BaseLinePoint BaseLinePoint = new BaseLinePoint(this.baseX[sensorID][i], this.baseY[sensorID][i], true);
                baseLinePointList.add(BaseLinePoint);
            }
        }
        LMSLog.d(this.DEBUG_TAG, "baseLinePointList.size=" + baseLinePointList.size());
        fit = this.LineFittingLiner(baseLinePointList);
        final double angle = Math.toDegrees(Math.atan(fit[0]));
        baseLinePointList.clear();
        LMSLog.d(this.DEBUG_TAG, "middle A=" + fit[0]);
        LMSLog.d(this.DEBUG_TAG, "middle B=" + fit[1]);
        LMSLog.d(this.DEBUG_TAG, "middle @=" + angle);
        final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
        eventExtra.put("SensorID", -1);
        eventExtra.put("NOTIFY_TITLE", "\u6eda\u8f6c\u89d2\u6d4b\u91cf");
        eventExtra.put("NOTIFY_MSG", "\u8bf7\u8bbe" + LMSConstValue.getSensorName(sensorID) + "\u6eda\u8f6c\u89d2\u4e3a\uff1a" + (LMSConstValue.fAngleLROffset[sensorID] / 10.0f + angle));
        LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra);
    }
    
    double[] LineFittingLiner(final ArrayList<BaseLinePoint> lineList) {
        int realSize = 0;
        for (int i = 0; i < lineList.size(); ++i) {
            if (lineList.get(i).bValid) {
                ++realSize;
            }
        }
        LMSLog.d(this.DEBUG_TAG, "realSize=" + realSize);
        double B = 0.0;
        double xmean = 0.0;
        double ymean = 0.0;
        for (int j = 0; j < lineList.size(); ++j) {
            final BaseLinePoint baseLinePoint = lineList.get(j);
            if (baseLinePoint.bValid) {
                xmean += baseLinePoint.x;
                ymean += baseLinePoint.y;
            }
        }
        xmean /= realSize;
        ymean /= realSize;
        double sumx2 = 0.0;
        double sumxy = 0.0;
        for (int k = 0; k < lineList.size(); ++k) {
            final BaseLinePoint baseLinePoint2 = lineList.get(k);
            if (baseLinePoint2.bValid) {
                sumx2 += (baseLinePoint2.x - xmean) * (baseLinePoint2.x - xmean);
                sumxy += (baseLinePoint2.y - ymean) * (baseLinePoint2.x - xmean);
            }
        }
        final double A = sumxy / sumx2;
        B = ymean - A * xmean;
        final double[] result = { A, B };
        return result;
    }
    
    public boolean widthHeightCalBaseData(final int sensorID, final int iGetBaseNum, final int dataLength) {
        final boolean bBaseValid = true;
        final int loopStart = LMSConstValue.filterStartPoint[sensorID];
        final int loopEnd = LMSConstValue.filterEndPoint[sensorID];
        this.baseFilter(sensorID);
        int baseYSum = 0;
        int baseNum = 0;
        int iGroundStartIndex = LMSConstValue.angleToIndex(sensorID, (float)LMSConstValue.iGroundStartAngle[sensorID]);
        int iGroundEndIndex = LMSConstValue.angleToIndex(sensorID, (float)LMSConstValue.iGroundEndAngle[sensorID]);
        LMSLog.i(String.valueOf(this.DEBUG_TAG) + sensorID, "iGroundStartAngle=" + LMSConstValue.iGroundStartAngle[sensorID] + " iGroundStartIndex=" + iGroundStartIndex + " iGroundEndAngle=" + LMSConstValue.iGroundEndAngle[sensorID] + " iGroundEndIndex=" + iGroundEndIndex);
        if (iGroundStartIndex < 0) {
            iGroundStartIndex = 0;
        }
        if (iGroundEndIndex > 721) {
            iGroundEndIndex = 721;
        }
        for (int i = iGroundStartIndex; i < iGroundEndIndex; ++i) {
            if (this.baseY[sensorID][i] == 500000 || this.baseY[sensorID][i] == 0) {
                LMSLog.i(String.valueOf(this.DEBUG_TAG) + sensorID, "i=" + i + " \u65e0\u53cd\u5c04");
                final String errorMsg = " \u89d2\u5ea6:" + LMSConstValue.indexToAngle(sensorID, i) + " \u65e0\u53cd\u5c04";
                final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
                eventExtra.put("SensorID", -1);
                eventExtra.put("NOTIFY_TITLE", String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u57fa\u51c6\u503c\u5efa\u7acb\u5931\u8d25");
                eventExtra.put("NOTIFY_MSG", errorMsg);
                LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra);
                break;
            }
        }
        for (int i = iGroundStartIndex; i < iGroundEndIndex; ++i) {
            if (this.baseY[sensorID][i] != 500000 && this.baseY[sensorID][i] != 0) {
                ++baseNum;
                baseYSum += this.baseY[sensorID][i];
            }
        }
        if (baseNum <= 10) {
            final HashMap<String, Comparable> eventExtra2 = new HashMap<String, Comparable>();
            eventExtra2.put("SensorID", -1);
            eventExtra2.put("NOTIFY_TITLE", String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u57fa\u51c6\u503c\u5efa\u7acb\u5931\u8d25");
            eventExtra2.put("NOTIFY_MSG", "\u57fa\u51c6\u503c\u70b9\u6570\u592a\u5c11:" + baseNum);
            LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra2);
            LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "GETBASE ERROR VALUE TOO LITTLE:" + baseNum);
            return false;
        }
        LMSConstValue.yBaseValue[sensorID] = baseYSum / baseNum;
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "yBaseValue=" + LMSConstValue.yBaseValue[sensorID]);
        if (LMSConstValue.fixMethod[sensorID].key.equals("UP_FIX") && baseYSum <= 0) {
            final HashMap<String, Comparable> eventExtra2 = new HashMap<String, Comparable>();
            eventExtra2.put("SensorID", -1);
            eventExtra2.put("NOTIFY_TITLE", String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u57fa\u51c6\u503c\u5efa\u7acb\u5931\u8d25");
            eventExtra2.put("NOTIFY_MSG", "\u60ac\u6302\u65b9\u5f0f,\u57fa\u51c6\u503c\u4e0d\u5141\u8bb8\u4e3a\u8d1f");
            LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra2);
            return false;
        }
        if (!LMSConstValue.fixMethod[sensorID].key.equals("UP_FIX") && baseYSum >= 0) {
            final HashMap<String, Comparable> eventExtra2 = new HashMap<String, Comparable>();
            eventExtra2.put("SensorID", -1);
            eventExtra2.put("NOTIFY_TITLE", String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u57fa\u51c6\u503c\u5efa\u7acb\u5931\u8d25");
            eventExtra2.put("NOTIFY_MSG", "\u5730\u9762\u5b89\u88c5,\u57fa\u51c6\u503c\u4e0d\u5141\u8bb8\u4e3a\u6b63");
            LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra2);
            return false;
        }
        for (int i = iGroundStartIndex; i < iGroundEndIndex; ++i) {
            if (this.baseY[sensorID][i] != 500000 && this.baseY[sensorID][i] != 0 && Math.abs(this.baseY[sensorID][i] - LMSConstValue.yBaseValue[sensorID]) > 150) {
                LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "GETBASE ERROR i=" + i);
                final String errorMsg = "\u5730\u9762\u8d77\u59cb\u89d2\u5ea6:" + (LMSConstValue.baseBeginIndex[sensorID] * 10 * ParseLMSAckCommand.angleResolution[sensorID] + LMSConstValue.fAngleLROffset[sensorID]) / 10.0 + " \u5730\u9762\u7ed3\u675f\u89d2\u5ea6:" + (LMSConstValue.baseEndIndex[sensorID] * 10 * ParseLMSAckCommand.angleResolution[sensorID] + LMSConstValue.fAngleLROffset[sensorID]) / 10.0 + " \u89d2\u5ea6:" + (i * 10 * ParseLMSAckCommand.angleResolution[sensorID] + LMSConstValue.fAngleLROffset[sensorID]) / 10.0 + " \u4e0d\u5e73:y=" + this.baseY[sensorID][i];
                final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
                eventExtra.put("SensorID", -1);
                eventExtra.put("NOTIFY_TITLE", String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u57fa\u51c6\u503c\u5efa\u7acb\u5931\u8d25");
                eventExtra.put("NOTIFY_MSG", errorMsg);
                LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra);
                return false;
            }
        }
        final int leftWindow = LMSConstValue.getLeftWindow(sensorID);
        final int rightWindow = LMSConstValue.getRightWindow(sensorID);
        final int frontWindow = LMSConstValue.getHeightWindow(sensorID);
        LMSConstValue.baseBeginIndex[sensorID] = -1;
        LMSConstValue.baseEndIndex[sensorID] = 0;
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "loopStart=" + loopStart + " loopEnd=" + loopEnd);
        for (int j = loopStart; j < loopEnd; ++j) {
            if (this.baseX[sensorID][j] > -rightWindow && this.baseX[sensorID][j] < leftWindow && this.baseY[sensorID][j] < frontWindow) {
                if (LMSConstValue.baseBeginIndex[sensorID] == -1) {
                    LMSConstValue.baseBeginIndex[sensorID] = j;
                }
                else {
                    LMSConstValue.baseEndIndex[sensorID] = j;
                }
            }
        }
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "baseBeginIndex=" + LMSConstValue.baseBeginIndex[sensorID] + " baseEndIndex=" + LMSConstValue.baseEndIndex[sensorID]);
        LMSLog.d(String.valueOf(this.DEBUG_TAG) + sensorID, "baseBeginAngle=" + (LMSConstValue.baseBeginIndex[sensorID] * 10 * ParseLMSAckCommand.angleResolution[sensorID] + LMSConstValue.fAngleLROffset[sensorID]) / 10.0 + " baseEndAngle=" + (LMSConstValue.baseEndIndex[sensorID] * 10 * ParseLMSAckCommand.angleResolution[sensorID] + LMSConstValue.fAngleLROffset[sensorID]) / 10.0);
        return bBaseValid;
    }
    
    public MarkValue getMarkBig90(final int sensorID, final int dataLength, final int leftWindow, final int rightWindow, final int loopStart, final int loopEnd) {
        int iValidLineLastIndex = 0;
        int iValidHeight = 0;
        int iValidX = 0;
        int maxX = -1;
        int maxHeight = 0;
        boolean bValidLine = false;
        for (int i = loopStart; i < loopEnd; ++i) {
            if (this.baseX[sensorID][i] > -rightWindow && this.baseX[sensorID][i] < leftWindow) {
                final int height = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][i]);
                if (height > LMSConstValue.VALID_THING_HEIGHT[sensorID]) {
                    LMSLog.d(this.DEBUG_TAG, "height[" + i + "]=" + height + " x=" + this.baseX[sensorID][i]);
                    if (!bValidLine || this.baseX[sensorID][i] > maxX) {
                        maxX = this.baseX[sensorID][i];
                    }
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    bValidLine = true;
                }
                else if (bValidLine) {
                    iValidLineLastIndex = i;
                    break;
                }
            }
        }
        LMSLog.d(this.DEBUG_TAG, "maxHeight=" + maxHeight + " maxX=" + maxX);
        if (bValidLine) {
            boolean bBegin = true;
            int iValidHeightBeginIndex = 0;
            int iValidHeightEndIndex = 0;
            bValidLine = false;
            for (int i = loopStart; i < loopEnd; ++i) {
                if (this.baseX[sensorID][i] > -rightWindow && this.baseX[sensorID][i] < leftWindow) {
                    final int height = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][i]);
                    if (height > maxHeight - 20) {
                        LMSLog.d(this.DEBUG_TAG, "iiiiiiiiiiiiiii=" + i);
                        if (bBegin) {
                            bBegin = false;
                            iValidHeightBeginIndex = i;
                            iValidHeightEndIndex = i;
                        }
                        else {
                            iValidHeightEndIndex = i;
                        }
                        bValidLine = true;
                    }
                    else if (bValidLine) {
                        break;
                    }
                }
            }
            final int iValidHeightIndexMiddle = (iValidHeightBeginIndex + iValidHeightEndIndex) / 2;
            iValidHeight = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][iValidHeightIndexMiddle]);
            LMSLog.d(this.DEBUG_TAG, "iValidHeightBeginIndex=" + iValidHeightBeginIndex + " iValidHeightEndIndex=" + iValidHeightEndIndex);
            LMSLog.d(this.DEBUG_TAG, "iValidHeightIndexMiddle[" + iValidHeightIndexMiddle + "]=" + iValidHeight);
            int i;
            for (i = iValidHeightIndexMiddle; i > loopStart; --i) {
                if (this.baseX[sensorID][i] > -rightWindow && this.baseX[sensorID][i] < leftWindow) {
                    final int height = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][i]);
                    if (height > LMSConstValue.VALID_THING_HEIGHT[sensorID] && height < maxHeight - 50) {
                        iValidX = this.baseX[sensorID][i];
                        break;
                    }
                }
            }
            LMSLog.d(this.DEBUG_TAG, "iValidX[" + i + "]=" + iValidX);
        }
        else {
            iValidX = 500000;
        }
        final MarkValue markValue = new MarkValue(iValidX, iValidHeight, iValidLineLastIndex);
        LMSLog.d(this.DEBUG_TAG, "getMarkBig90 x=" + markValue.markX + " y=" + markValue.markY);
        return markValue;
    }
    
    public MarkValue getMarkLess90(final int sensorID, final int dataLength, final int leftWindow, final int rightWindow, final int loopStart, final int loopEnd) {
        int iValidLineLastIndex = 0;
        int iValidHeight = 0;
        int iValidX = 0;
        int minX = -1;
        int maxHeight = 0;
        boolean bValidLine = false;
        for (int i = loopStart; i < loopEnd; ++i) {
            if (this.baseX[sensorID][i] > -rightWindow && this.baseX[sensorID][i] < leftWindow) {
                final int height = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][i]);
                if (height > LMSConstValue.VALID_THING_HEIGHT[sensorID]) {
                    LMSLog.d(this.DEBUG_TAG, "height[" + i + "]=" + height + " x=" + this.baseX[sensorID][i]);
                    if (!bValidLine || this.baseX[sensorID][i] < minX) {
                        minX = this.baseX[sensorID][i];
                    }
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    bValidLine = true;
                }
                else if (bValidLine) {
                    iValidLineLastIndex = i;
                    break;
                }
            }
        }
        LMSLog.d(this.DEBUG_TAG, "maxHeight=" + maxHeight + " minX=" + minX);
        if (bValidLine) {
            boolean bValidHeightBegin = true;
            int iValidHeightBeginIndex = 0;
            int iValidHeightEndIndex = 0;
            bValidLine = false;
            for (int i = loopStart; i < loopEnd; ++i) {
                if (this.baseX[sensorID][i] > -rightWindow && this.baseX[sensorID][i] < leftWindow) {
                    final int height = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][i]);
                    if (height > maxHeight - 20) {
                        if (bValidHeightBegin) {
                            bValidHeightBegin = false;
                            iValidHeightBeginIndex = i;
                            iValidHeightEndIndex = i;
                        }
                        else {
                            iValidHeightEndIndex = i;
                        }
                        bValidLine = true;
                    }
                    else if (bValidLine) {
                        break;
                    }
                }
            }
            final int iValidHeightIndexMiddle = (iValidHeightBeginIndex + iValidHeightEndIndex) / 2;
            iValidHeight = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][iValidHeightIndexMiddle]);
            LMSLog.d(this.DEBUG_TAG, "iValidHeightBeginIndex=" + iValidHeightBeginIndex + " iValidHeightEndIndex=" + iValidHeightEndIndex);
            LMSLog.d(this.DEBUG_TAG, "iValidHeightIndexMiddle[" + iValidHeightIndexMiddle + "]=" + iValidHeight);
            int i;
            for (i = iValidHeightIndexMiddle; i < loopEnd; ++i) {
                if (this.baseX[sensorID][i] > -rightWindow && this.baseX[sensorID][i] < leftWindow) {
                    final int height = LMSConstValue.getHeight(sensorID, this.baseY[sensorID][i]);
                    if (height > LMSConstValue.VALID_THING_HEIGHT[sensorID] && height < maxHeight - 50) {
                        iValidX = this.baseX[sensorID][i];
                        break;
                    }
                }
            }
            LMSLog.d(this.DEBUG_TAG, "iValidX[" + i + "]=" + iValidX);
        }
        else {
            iValidX = 500000;
        }
        final MarkValue markValue = new MarkValue(iValidX, iValidHeight, iValidLineLastIndex);
        LMSLog.d(this.DEBUG_TAG, "getMarkLess90 x=" + markValue.markX + " y=" + markValue.markY);
        return markValue;
    }
    
    public MarkValue getMark(final int sensorID, final int dataLength) {
        final int leftWindow = LMSConstValue.iLeftWindow[sensorID];
        final int rightWindow = LMSConstValue.iRightWindow[sensorID];
        final boolean positionLess90 = leftWindow <= rightWindow;
        LMSLog.d(this.DEBUG_TAG, "positionLess90=" + positionLess90);
        final int loopStart = LMSConstValue.filterStartPoint[sensorID];
        final int loopEnd = LMSConstValue.filterEndPoint[sensorID];
        if (positionLess90) {
            return this.getMarkLess90(sensorID, dataLength, leftWindow, rightWindow, loopStart, loopEnd);
        }
        return this.getMarkBig90(sensorID, dataLength, leftWindow, rightWindow, loopStart, loopEnd);
    }
    
    public int getTwoObjDistance(final int sensorID, final int dataLength) {
        final int leftWindow = LMSConstValue.iLeftWindow[sensorID];
        final int rightWindow = LMSConstValue.iRightWindow[sensorID];
        final boolean positionLess90 = leftWindow <= rightWindow;
        LMSLog.d(this.DEBUG_TAG, "getTowObjDistance positionLess90=" + positionLess90);
        int lengthDistance0;
        int lengthDistance2;
        if (positionLess90) {
            int loopStart = LMSConstValue.filterEndPoint[sensorID];
            final int loopEnd = LMSConstValue.filterStartPoint[sensorID];
            MarkValue markValue = this.getMarkLess90(sensorID, dataLength, leftWindow, rightWindow, loopStart, loopEnd);
            lengthDistance0 = markValue.markX;
            loopStart = markValue.lastIndex;
            markValue = this.getMarkLess90(sensorID, dataLength, leftWindow, rightWindow, loopStart, loopEnd);
            lengthDistance2 = markValue.markX;
        }
        else {
            int loopStart = LMSConstValue.filterStartPoint[sensorID];
            final int loopEnd = LMSConstValue.filterEndPoint[sensorID];
            MarkValue markValue = this.getMarkBig90(sensorID, dataLength, leftWindow, rightWindow, loopStart, loopEnd);
            lengthDistance0 = markValue.markX;
            loopStart = markValue.lastIndex;
            markValue = this.getMarkBig90(sensorID, dataLength, leftWindow, rightWindow, loopStart, loopEnd);
            lengthDistance2 = markValue.markX;
            this.getMarkBig90(sensorID, dataLength, leftWindow, rightWindow, loopStart, loopEnd);
        }
        return Math.abs(lengthDistance2 - lengthDistance0);
    }
    
    public boolean bGroundFlat(final int sensorID, final int iGetBaseNum, final int dataLength, final DataLayerDataParseLineRadar dataParseLineAlgorithm) {
        if (!LMSConstValue.bBaseValid[sensorID]) {
            final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
            eventExtra.put("SensorID", -1);
            final String msg = String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u672a\u53d6\u57fa\u51c6";
            eventExtra.put("NOTIFY_TITLE", msg);
            eventExtra.put("NOTIFY_MSG", msg);
            LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra);
            return false;
        }
        final int[] sumBaseX = new int[721];
        final int[] sumBaseY = new int[721];
        final int loopStart = LMSConstValue.filterStartPoint[sensorID];
        final int loopEnd = LMSConstValue.filterEndPoint[sensorID];
        for (int i = 0; i < 721; ++i) {
            for (int j = 0; j < iGetBaseNum; ++j) {
                final int[] array = sumBaseX;
                final int n = i;
                array[n] += this.tmpBaseX[j][i];
                final int[] array2 = sumBaseY;
                final int n2 = i;
                array2[n2] += this.tmpBaseY[j][i];
            }
            this.baseX[sensorID][i] = sumBaseX[i] / iGetBaseNum;
            this.baseY[sensorID][i] = sumBaseY[i] / iGetBaseNum;
        }
        final int leftWindow = LMSConstValue.getLeftWindow(sensorID);
        final int rightWindow = LMSConstValue.getRightWindow(sensorID);
        final int frontWindow = LMSConstValue.getHeightWindow(sensorID);
        for (int k = loopStart; k < loopEnd; ++k) {
            if (dataParseLineAlgorithm.distanceX[k] > -rightWindow && dataParseLineAlgorithm.distanceX[k] < leftWindow && dataParseLineAlgorithm.distanceY[k] < frontWindow && LMSConstValue.yBaseValue[sensorID] - Math.abs(this.baseY[sensorID][k]) > 150) {
                LMSLog.i(String.valueOf(this.DEBUG_TAG) + sensorID, "index=" + k + " \u5b58\u5728\u5e72\u6270");
                final String errorMsg = "\u89d2\u5ea6:" + LMSConstValue.indexToAngle(sensorID, k) + " \u5b58\u5728\u5e72\u6270";
                final HashMap<String, Comparable> eventExtra2 = new HashMap<String, Comparable>();
                eventExtra2.put("SensorID", -1);
                eventExtra2.put("NOTIFY_TITLE", String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u5730\u9762\u4e0d\u5e73");
                eventExtra2.put("NOTIFY_MSG", errorMsg);
                LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra2);
                return false;
            }
        }
        final HashMap<String, Comparable> eventExtra3 = new HashMap<String, Comparable>();
        eventExtra3.put("SensorID", -1);
        final String msg2 = String.valueOf(LMSConstValue.getSensorName(sensorID)) + "\u5730\u9762 OK!!!";
        eventExtra3.put("NOTIFY_TITLE", msg2);
        eventExtra3.put("NOTIFY_MSG", msg2);
        LMSConstValue.lmsEventManager.sendEvent("lmsApp.intent.action.SETTING_NOTIFY_INTENT", (HashMap)eventExtra3);
        return true;
    }
    
    private void widthHeightGetBaseData(final int sensorID, final int iGetBaseNum, final int dataLength, final DataLayerDataParseLineRadar dataParseLineAlgorithm) {
        for (int i = 0; i < 721; ++i) {
            this.tmpBaseX[iGetBaseNum][i] = dataParseLineAlgorithm.distanceX[i];
            this.tmpBaseY[iGetBaseNum][i] = dataParseLineAlgorithm.distanceY[i];
        }
    }
    
    public void getBaseDataParse(final int sensorID, final DataLayerDataParseLineRadar dataParseLineAlgorithm) {
        if (this.iGetBaseNum >= 100) {
            this.bGetBase = false;
            if (!LMSConstValue.bGetGroundFlat) {
                LMSConstValue.bBaseValid[sensorID] = this.widthHeightCalBaseData(sensorID, this.iGetBaseNum, ParseLMSAckCommand.measureData16bit_amount[sensorID]);
                this.baseCalcAlgo(sensorID);
                LMSConstValue.bInitial[sensorID] = false;
                for (int i = 0; i < LMSConstValue.iCarRoadNum; ++i) {
                    LMSConstValue.carState[i] = LMSConstValue.enumCarState.NOT_CAR_DETECT;
                }
                final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
                eventExtra.put("SensorID", sensorID);
                LMSConstValue.lmsEventManager.sendEvent("SERVER_SEND_BASE_DATA_INTENT", (HashMap)eventExtra);
            }
            else {
                this.bGroundFlat(sensorID, this.iGetBaseNum, ParseLMSAckCommand.measureData16bit_amount[sensorID], dataParseLineAlgorithm);
            }
            this.iGetBaseNum = 0;
        }
        else {
            this.widthHeightGetBaseData(sensorID, this.iGetBaseNum, ParseLMSAckCommand.measureData16bit_amount[sensorID], dataParseLineAlgorithm);
            ++this.iGetBaseNum;
        }
    }
    
    public void biaoDing(final int sensorID, final DataLayerDataParseLineRadar dataParseLineAlgorithm) {
        if (LMSConstValue.bBaseValid[sensorID]) {
            if (RadarCalibration.iGetFSRLBaseTime[sensorID] > 0 && RadarCalibration.iGetMarkSensorID == sensorID) {
                final int[] iGetFSRLBaseTime = RadarCalibration.iGetFSRLBaseTime;
                final int n = iGetFSRLBaseTime[sensorID] - 1;
                iGetFSRLBaseTime[sensorID] = n;
                final int index = n;
                this.widthHeightGetBaseData(sensorID, index, ParseLMSAckCommand.measureData16bit_amount[sensorID], dataParseLineAlgorithm);
                if (index == 0) {
                    RadarCalibration.bBiaoDing[sensorID] = false;
                    this.baseFilter(sensorID);
                    final MarkValue markValue = this.getMark(sensorID, ParseLMSAckCommand.measureData16bit_amount[sensorID]);
                    final String str = "<SetBasePoint><Sensor>" + sensorID + "</Sensor>" + "<Horizontal>" + markValue.markY + "</Horizontal>" + "<Virtical>" + markValue.markX + "</Virtical>" + "</SetBasePoint>";
                    final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
                    eventExtra.put("INTENT_EXTRA_SOCKET_MSG", str);
                    LMSConstValue.lmsEventManager.sendEvent("SOCKET_SEND_MSG_INTENT", (HashMap)eventExtra);
                }
            }
            else if (RadarCalibration.iGetFSRLCurrentTime[sensorID] > 0 && RadarCalibration.iGetMarkSensorID == sensorID) {
                final int[] iGetFSRLCurrentTime = RadarCalibration.iGetFSRLCurrentTime;
                final int n2 = iGetFSRLCurrentTime[sensorID] - 1;
                iGetFSRLCurrentTime[sensorID] = n2;
                final int index = n2;
                this.widthHeightGetBaseData(sensorID, index, ParseLMSAckCommand.measureData16bit_amount[sensorID], dataParseLineAlgorithm);
                if (index == 0) {
                    RadarCalibration.bBiaoDing[sensorID] = false;
                    this.baseFilter(sensorID);
                    final MarkValue markValue = this.getMark(sensorID, ParseLMSAckCommand.measureData16bit_amount[sensorID]);
                    final String str = "<RealValue><Sensor>" + sensorID + "</Sensor>" + "<Horizontal>" + markValue.markY + "</Horizontal>" + "<Virtical>" + markValue.markX + "</Virtical>" + "</RealValue>";
                    final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
                    eventExtra.put("INTENT_EXTRA_SOCKET_MSG", str);
                    LMSConstValue.lmsEventManager.sendEvent("SOCKET_SEND_MSG_INTENT", (HashMap)eventExtra);
                }
            }
            else if (RadarCalibration.iGetFSRLDistanceBetweenTime[sensorID] > 0 && RadarCalibration.iGetMarkSensorID == sensorID) {
                final int[] iGetFSRLDistanceBetweenTime = RadarCalibration.iGetFSRLDistanceBetweenTime;
                final int n3 = iGetFSRLDistanceBetweenTime[sensorID] - 1;
                iGetFSRLDistanceBetweenTime[sensorID] = n3;
                final int index = n3;
                this.widthHeightGetBaseData(sensorID, index, ParseLMSAckCommand.measureData16bit_amount[sensorID], dataParseLineAlgorithm);
                if (index == 0) {
                    RadarCalibration.bBiaoDing[sensorID] = false;
                    this.baseFilter(sensorID);
                    final int distance = this.getTwoObjDistance(sensorID, ParseLMSAckCommand.measureData16bit_amount[sensorID]);
                    final String str = "<ReadDistanceRsp><Sensor>" + sensorID + "</Sensor>" + "<Distance>" + distance + "</Distance>" + "</ReadDistanceRsp>";
                    final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
                    eventExtra.put("INTENT_EXTRA_SOCKET_MSG", str);
                    LMSConstValue.lmsEventManager.sendEvent("SOCKET_SEND_MSG_INTENT", (HashMap)eventExtra);
                }
            }
            else {
                if (sensorID < 2 && RadarCalibration.iGetFSRLWidthTime[sensorID] > 0) {
                    final int[] iGetFSRLWidthTime = RadarCalibration.iGetFSRLWidthTime;
                    final int n4 = iGetFSRLWidthTime[sensorID] - 1;
                    iGetFSRLWidthTime[sensorID] = n4;
                    final int index = n4;
                    this.widthHeightGetBaseData(sensorID, index, ParseLMSAckCommand.measureData16bit_amount[sensorID], dataParseLineAlgorithm);
                    if (index == 0) {
                        this.baseFilter(sensorID);
                        final MarkValue markValue = this.getMark(sensorID, ParseLMSAckCommand.measureData16bit_amount[sensorID]);
                        RadarCalibration.fsrl_width_x[sensorID] = markValue.markX;
                        RadarCalibration.fsrl_width_y[sensorID] = markValue.markY;
                    }
                    synchronized (RadarCalibration.tokenStaticGetWidth) {
                        if (RadarCalibration.iGetFSRLWidthTime[0] == 0 && RadarCalibration.iGetFSRLWidthTime[1] == 0) {
                            RadarCalibration.bBiaoDing[0] = false;
                            RadarCalibration.bBiaoDing[1] = false;
                            final int width = LMSConstValue.iNvramLRDistance.iValue - Math.abs(RadarCalibration.fsrl_width_x[0]) - Math.abs(RadarCalibration.fsrl_width_x[1]);
                            LMSLog.d(this.DEBUG_TAG, "iLRDistance=" + LMSConstValue.iNvramLRDistance.iValue + " fsrl_width_x[0]=" + RadarCalibration.fsrl_width_x[0] + " fsrl_width_x[1]=" + RadarCalibration.fsrl_width_x[1]);
                            final String str2 = "<ReadOneDataValue><Width>" + width + "</Width></ReadOneDataValue>";
                            final HashMap<String, Comparable> eventExtra2 = new HashMap<String, Comparable>();
                            eventExtra2.put("INTENT_EXTRA_SOCKET_MSG", str2);
                            LMSConstValue.lmsEventManager.sendEvent("SOCKET_SEND_MSG_INTENT", (HashMap)eventExtra2);
                        }
                        // monitorexit(RadarCalibration.tokenStaticGetWidth)
                        return;
                    }
                }
                if (sensorID == 2 && RadarCalibration.iGetFSRLLengthTime > 0) {
                    final int index = --RadarCalibration.iGetFSRLLengthTime;
                    this.widthHeightGetBaseData(sensorID, index, ParseLMSAckCommand.measureData16bit_amount[sensorID], dataParseLineAlgorithm);
                    if (index == 0) {
                        RadarCalibration.bBiaoDing[sensorID] = false;
                        this.baseFilter(sensorID);
                        final MarkValue markValue = this.getMark(sensorID, ParseLMSAckCommand.measureData16bit_amount[sensorID]);
                        final String str = "<ReadOneDataValue><Length>" + markValue.markX + "</Length>" + "</ReadOneDataValue>";
                        final HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
                        eventExtra.put("INTENT_EXTRA_SOCKET_MSG", str);
                        LMSConstValue.lmsEventManager.sendEvent("SOCKET_SEND_MSG_INTENT", (HashMap)eventExtra);
                    }
                }
            }
        }
        else {
            RadarCalibration.bBiaoDing[sensorID] = false;
            final String str3 = LMSConstValue.getSensorName(sensorID);
            LMSLog.errorDialog("\u6807\u5b9a\u5931\u8d25", "\u6807\u5b9a\u5931\u8d25," + str3 + "\u672a\u53d6\u57fa\u51c6");
        }
    }
    
    public static void biaodingParse(final String str) {
        if (str.contains("<AskCommand>") && str.contains("</AskCommand>")) {
            if (str.contains("SetBasePoint")) {
                final String result = new XMLParse().getXMLStrValue(str, "SetBasePoint");
                final int sensorID = Integer.valueOf(result);
                RadarCalibration.bBiaoDing[sensorID] = true;
                RadarCalibration.iGetFSRLBaseTime[sensorID] = 100;
                RadarCalibration.iGetMarkSensorID = sensorID;
            }
            else if (str.contains("ReadRealValue")) {
                final String result = new XMLParse().getXMLStrValue(str, "ReadRealValue");
                final int sensorID = Integer.valueOf(result);
                RadarCalibration.bBiaoDing[sensorID] = true;
                RadarCalibration.iGetFSRLCurrentTime[sensorID] = 100;
                RadarCalibration.iGetMarkSensorID = sensorID;
            }
            else if (str.contains("ReadDistanceReq")) {
                final String result = new XMLParse().getXMLStrValue(str, "ReadDistanceReq");
                final int sensorID = Integer.valueOf(result);
                RadarCalibration.bBiaoDing[sensorID] = true;
                RadarCalibration.iGetFSRLDistanceBetweenTime[sensorID] = 100;
                RadarCalibration.iGetMarkSensorID = sensorID;
            }
            else if (str.contains("ReadOneDataValue")) {
                final String strType = new XMLParse().getXMLStrValue(str, "ReadOneDataValue");
                if (strType.equals("Width")) {
                    RadarCalibration.iGetFSRLWidthTime[0] = 100;
                    RadarCalibration.iGetFSRLWidthTime[1] = 100;
                    RadarCalibration.bBiaoDing[0] = true;
                    RadarCalibration.bBiaoDing[1] = true;
                }
                else if (strType.equals("Length")) {
                    RadarCalibration.iGetFSRLLengthTime = 100;
                    RadarCalibration.bBiaoDing[2] = true;
                }
            }
        }
    }
    
    class BaseLinePoint
    {
        double x;
        double y;
        boolean bValid;
        
        public BaseLinePoint(final double _x, final double _y, final boolean _bValid) {
            this.x = _x;
            this.y = _y;
            this.bValid = _bValid;
        }
    }
    
    class MarkValue
    {
        int markX;
        int markY;
        int lastIndex;
        
        public MarkValue(final int x, final int y, final int _lastIndex) {
            this.markX = x;
            this.markY = y;
            this.lastIndex = _lastIndex;
        }
    }
}
