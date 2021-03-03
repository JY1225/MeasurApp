package layer.physicLayer;

import java.io.*;
import lmsEvent.*;
import SensorBase.*;
import java.util.*;

public class SensorHS_LED
{
    private static final String DEBUG_TAG = "SensorHS_LED";
    protected int _sensorID;
    OutputStream _outputStream;
    
    public SensorHS_LED(final int sensorID, final OutputStream outputStream) {
        this._sensorID = sensorID;
        this._outputStream = outputStream;
        final EventListener eventListener = new EventListener();
        LMSEventManager.addListener((LMSEventListener)eventListener);
    }
    
    public static byte[] set(final String message) {
        byte[] messageOut = { 0, 48, 64, 49, 50 };
        try {
            final byte[] bCommand1 = { 0, 49, 49, 50 };
            final int L1 = bCommand1.length;
            final byte[] bLEDScreenMessage = message.getBytes("gbk");
            final int L2 = bLEDScreenMessage.length;
            final byte[] bCommand2 = { 13 };
            messageOut = new byte[L1 + L2 + 1];
            System.arraycopy(bCommand1, 0, messageOut, 0, L1);
            System.arraycopy(bLEDScreenMessage, 0, messageOut, L1, L2);
            System.arraycopy(bCommand2, 0, messageOut, L1 + L2, 1);
        }
        catch (UnsupportedEncodingException e) {
            LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e);
        }
        catch (IOException e2) {
            LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e2);
        }
        catch (Exception e3) {
            LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e3);
        }
        return messageOut;
    }
    
    public static byte[] defaultSet(final String defaultMessage) {
        byte[] messageOut = { 0, 48, 64, 49, 50 };
        try {
            final byte[] bCommand1 = { 0, 48, 64, 49, 50 };
            final int L1 = bCommand1.length;
            final byte[] bDefaultMessage = defaultMessage.getBytes("gbk");
            final int L2 = bDefaultMessage.length;
            final byte[] bCommand2 = { 13 };
            messageOut = new byte[L1 + L2 + 1];
            System.arraycopy(bCommand1, 0, messageOut, 0, L1);
            System.arraycopy(bDefaultMessage, 0, messageOut, L1, L2);
            System.arraycopy(bCommand2, 0, messageOut, L1 + L2, 1);
        }
        catch (UnsupportedEncodingException e) {
            LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e);
        }
        catch (IOException e2) {
            LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e2);
        }
        catch (Exception e3) {
            LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e3);
        }
        return messageOut;
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
                if (nvram.equals(LMSConstValue.sNvramLedDefaultMessage.nvramStr)) {
                    try {
                        SensorHS_LED.this._outputStream.write(SensorHS_LED.defaultSet(LMSConstValue.sNvramLedDefaultMessage.sValue));
                    }
                    catch (UnsupportedEncodingException e) {
                        LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e);
                    }
                    catch (IOException e2) {
                        LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e2);
                    }
                    catch (Exception e3) {
                        LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e3);
                    }
                }
                else if (nvram.equals(LMSConstValue.sNvramLedCurrentMessage.nvramStr)) {
                    LMSLog.d("SensorHS_LED", "LED MESSAGE=" + LMSConstValue.sNvramLedCurrentMessage.sValue);
                    try {
                        LMSLog.d("SensorHS_LED", "LED MESSAGE=" + LMSConstValue.sNvramLedCurrentMessage.sValue);
                        final String strWrite = LMSConstValue.sNvramLedCurrentMessage.sValue.replace("\u8c6b", "\u6cb3\u5357");
                        LMSLog.d("SensorHS_LED", "strWrite=" + strWrite);
                        SensorHS_LED.this._outputStream.write(SensorHS_LED.set(strWrite));
                    }
                    catch (UnsupportedEncodingException e) {
                        LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e);
                    }
                    catch (IOException e2) {
                        LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e2);
                    }
                    catch (Exception e3) {
                        LMSLog.exceptionDialog("\u68c0\u6d4b\u4eea\u5f02\u5e38", (Throwable)e3);
                    }
                }
            }
        }
    }
}
