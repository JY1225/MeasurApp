package layer.physicLayer;

import CarDetect.*;
import SensorBase.*;

public class LMSWorkflow
{
    private static final String DEBUG_TAG = "LMS_LMS_WORKFLOW";
    
    public static void lmsWorkflowSetFreqAndAngularResolution(final int sensorID, final int hz) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            if (hz == 0) {
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 1388 1 1388 0 0\u0003");
            }
            else if (hz == 1) {
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 9C4 1 9C4 0 0\u0003");
            }
            else {
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LMPoutputRange 1 1388 0 1B7740\u0003");
            }
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSet_25HZ_25ANGLE_0_180_OUTPUT(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 9C4 1 9C4 0 0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LMPoutputRange 1 1388 0 1B7740\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSet_50HZ_50ANGLE_0_180_OUTPUT(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 1388 1 1388 0 0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LMPoutputRange 1 1388 0 1B7740\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSet_25HZ_25ANGLE_5_175_OUTPUT(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 9C4 1 9C4 0 0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LMPoutputRange 1 1388 C350 1AB3F0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSet_25HZ_25ANGLE_10_170_OUTPUT(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 9C4 1 9C4 0 0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LMPoutputRange 1 1388 186A0 19F0A0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSet_50HZ_50ANGLE_5_175_OUTPUT(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 1388 1 1388 0 0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LMPoutputRange 1 1388 C350 1AB3F0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSet_75HZ_50ANGLE_0_180_OUTPUT(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mCLsetscancfglist 6\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSet_15HZ_100ANGLE_0_180_OUTPUT(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mLMPsetscancfg 5DC 1 9C4 0 0\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LMPoutputRange 1 1388 0 1B7740\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSendPermanent(final int sensorID, final boolean start) {
        int command = 0;
        try {
            if (start) {
                command = 1;
            }
            else {
                command = 0;
            }
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sEN LMDscandata " + command + "\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowStartMeasure(final int sensorID, final boolean start) {
        try {
            if (start) {
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN LMCstartmeas\u0003");
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
                lmsWorkflowSendPermanent(sensorID, true);
            }
            else {
                lmsWorkflowSendPermanent(sensorID, false);
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN LMCstopmeas\u0003");
                CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
            }
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSetEchofilter(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN FREchoFilter 1\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSetnto1filter(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LFPnto1filter 1\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN mEEwriteall\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowAskTimeStamp(final int sensorID) {
        CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sRN STlms\u0003");
    }
    
    public static void lmsWorkflowPollingOne(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sRN LMDscandata\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowAskForOutputRange(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sRN LMPoutputRange\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowGetFreqAndAngularResolution(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sRN LMPscancfg\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
    
    public static void lmsWorkflowSetRSSI(final int sensorID) {
        try {
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN SetAccessMode 03 F4724744\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sWN LCMscandatacfg 02 00 1 1 0 00 00 0 0 0 0 1\u0003");
            CarDetectSetting.carDetectSetting.physicLayerSocket[sensorID].socketSend(sensorID, "\u0002sMN Run\u0003");
        }
        catch (Exception e) {
            LMSLog.exception(sensorID, (Throwable)e);
        }
    }
}
