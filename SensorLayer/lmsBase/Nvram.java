package lmsBase;

import lmsEvent.*;
import SensorBase.*;
import java.util.*;

public class Nvram
{
    private String DEBUG_TAG;
    public EventListener eventListener;
    
    public Nvram() {
        this.DEBUG_TAG = "Nvram";
        this.eventListener = new EventListener();
    }
    
    private class EventListener implements LMSEventListener
    {
        public void lmsTransferEvent(final LMSEvent event) {
            if (event.getEventType() != null) {
                final String eventType = event.getEventType();
                final HashMap eventExtra = event.getEventExtra();
                int sensorID = 0;
                if (eventExtra.containsKey("SensorID")) {
                    sensorID = (int) eventExtra.get("SensorID");
                }
                String strProperty = null;
                if (eventExtra.containsKey("SETTING_PROPERTY")) {
                    strProperty = (String) eventExtra.get("SETTING_PROPERTY");
                }
                if (eventType.equals("lmsApp.intent.action.SETTING_TRANSFER_INTENT")) {
                    final String nvram = (String) eventExtra.get("SETTING_NVRAM");
                    final String value = String.valueOf(eventExtra.get("SETTING_VALUE"));
                    boolean bSave = true;
                    if (eventExtra.containsKey("SETTING_SAVE")) {
                        bSave = (boolean) eventExtra.get("SETTING_SAVE");
                    }
                    if (bSave) {
                        if (sensorID == -1) {
                            LMSPlatform.savePorperty(strProperty, nvram, value);
                        }
                        else {
                            LMSPlatform.savePorperty(strProperty, String.valueOf(nvram) + sensorID, value);
                        }
                    }
                }
                else if (eventType.equals("SERVER_SEND_BASE_DATA_INTENT")) {
                    LMSLog.d(String.valueOf(Nvram.this.DEBUG_TAG) + sensorID, "bBaseValid[" + sensorID + "]=" + LMSConstValue.bBaseValid[sensorID]);
                    LMSPlatform.savePorperty(strProperty, String.valueOf(LMSConstValue.nvramYBaseValue) + sensorID, String.valueOf(LMSConstValue.yBaseValue[sensorID]));
                    LMSPlatform.savePorperty(strProperty, String.valueOf(LMSConstValue.nvramBaseBeginIndex) + sensorID, String.valueOf(LMSConstValue.baseBeginIndex[sensorID]));
                    LMSPlatform.savePorperty(strProperty, String.valueOf(LMSConstValue.nvramBaseEndIndex) + sensorID, String.valueOf(LMSConstValue.baseEndIndex[sensorID]));
                    LMSPlatform.savePorperty(strProperty, "nvram_bBaseValid" + sensorID, String.valueOf(LMSConstValue.bBaseValid[sensorID]));
                }
                else if (eventType.equals("lmsApp.intent.action.ANTI_LEVEL_INTENT")) {
                    final int level = (int) eventExtra.get("antiLevel");
                    LMSPlatform.savePorperty(strProperty, "nvram_antiLevel" + sensorID + level, String.valueOf(LMSConstValue.iAntiLevel[sensorID][level]));
                }
                else if (eventType.equals("lmsApp.intent.action.CAR_ROAD_WIDHT_CHANGE_INTENT")) {
                    for (int i = 0; i < 3; ++i) {
                        LMSPlatform.savePorperty(strProperty, "nvram_carRoadWidth" + i, String.valueOf(LMSConstValue.iCarRoadWidth[i]));
                    }
                }
                else if (eventType.equals("lmsApp.intent.action.CAR_ROAD_OUTPUT_TURN_INTENT")) {
                    LMSPlatform.savePorperty(strProperty, "nvram_bCarRoadOutputTurn", String.valueOf(LMSConstValue.bCarRoadOutputTurn));
                }
                else if (eventType.equals("lmsApp.intent.action.BOARD_TYPE_INTENT")) {
                    LMSPlatform.savePorperty(strProperty, "nvram_boardType", String.valueOf(LMSConstValue.boardType));
                }
            }
        }
    }
}
