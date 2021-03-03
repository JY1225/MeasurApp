package CustomerProtocol;

import java.io.File;
import java.util.HashMap;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import AppFrame.widgets.DetectionListComboBox;
import SensorBase.LMSConstValue;

public abstract class CustomerProtocol
{
	public static CustomerProtocol customerProtocol;
	
	public static String xmlStr;

	//开始检测
	public abstract void beginDetection(ContourDetectionTabPanelMain detectPanel,boolean bValue);

	//进出车信号
	public abstract void carInSignal(ContourDetectionTabPanelMain detectPanel,boolean bValue);

	//查询待检队列
	public abstract void queryDetectionList(ContourDetectionTabPanelMain detectPanel);

	//上传检测结果
	public abstract boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel);

	//WEBSERVICE解析待检队列数据:XML格式
	public abstract void parseDetectionList_WS(String xmlStr);	

	public static void init()
	{
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SZAC))
		{
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = false;
			DetectionVehicle.bProtocolFile = true;

			DetectionVehicle.bRefreshAsyc = false;

			DetectionVehicle.bAutoRefresh = true;
			DetectionVehicle.bAutoUpdate = false;

			DetectionVehicle.bFileStrLine = true;
			DetectionVehicle.bFileStrXML = false;

			customerProtocol = new CustomerProtocol_FILE_SZAC();
		}
		/**
		 * jacky.yang
		 * 佛山南华
		 */
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_FSNH))
		{	
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = false;
			DetectionVehicle.bProtocolFile = true;

			DetectionVehicle.bRefreshAsyc = false;

			DetectionVehicle.bAutoRefresh = true;
			DetectionVehicle.bAutoUpdate = false;

			DetectionVehicle.bFileStrLine = true;
			DetectionVehicle.bFileStrXML = false;

			customerProtocol = new CustomerProtocol_FILE_FSNH();
		}
		/**
		 * jacky.yang
		 * 江苏华西
		 */
		else if (LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSHX))
		{
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = false;
			DetectionVehicle.bProtocolFile = true;

			DetectionVehicle.bRefreshAsyc = false;

			DetectionVehicle.bAutoRefresh = true;
			DetectionVehicle.bAutoUpdate = false;

			DetectionVehicle.bFileStrLine = true;
			DetectionVehicle.bFileStrXML = false;

			customerProtocol = new CustomerProtocol_FILE_JSHX();
		}
		/**
		 * jacky.yang
		 * 广西京中
		 */
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_GXCZJ))
		{
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = false;
			DetectionVehicle.bProtocolFile = false;
			DetectionVehicle.bProtocolDll = true;

			DetectionVehicle.bRefreshAsyc = true;

			DetectionVehicle.bAutoRefresh = false;
			DetectionVehicle.bAutoUpdate = false;

			DetectionVehicle.bFileStrLine = false;
			DetectionVehicle.bFileStrXML = false;
			
			customerProtocol = new CustomerProtocol_DLL_GXCZJ();
		}
		/**
		 * jacky.yang
		 * 江苏新士尚
		 */
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSXSS))
		{
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = true;
			DetectionVehicle.bProtocolFile = false;
		
			DetectionVehicle.bRefreshAsyc = false;

			DetectionVehicle.bAutoRefresh = true;
			DetectionVehicle.bAutoUpdate = true;

			DetectionVehicle.bFileStrLine = false;
			DetectionVehicle.bFileStrXML = false;

			customerProtocol = new CustomerProtocol_DB_JSXSS();
		}
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC))
		{
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = true;
			DetectionVehicle.bProtocolDataBase = false;
			DetectionVehicle.bProtocolFile = false;
		
			DetectionVehicle.bRefreshAsyc = true;

			DetectionVehicle.bAutoRefresh = false;
			DetectionVehicle.bAutoUpdate = false;
			
			DetectionVehicle.bFileStrLine = false;
			DetectionVehicle.bFileStrXML = false;

			customerProtocol = new CustomerProtocol_WS_SDGC();
		}
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HF_SRF))
		{
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = true;
			DetectionVehicle.bProtocolFile = false;
		
			DetectionVehicle.bRefreshAsyc = false;

			DetectionVehicle.bAutoRefresh = false;
			DetectionVehicle.bAutoUpdate = false;

			DetectionVehicle.bFileStrLine = false;
			DetectionVehicle.bFileStrXML = false;

			customerProtocol = new CustomerProtocol_DB_HF_SRF();
		}
		else if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_JSYY))
		{
			DetectionVehicle.bProtocolSocketServer = false;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = true;
			DetectionVehicle.bProtocolFile = false;
		
			DetectionVehicle.bRefreshAsyc = false;

			DetectionVehicle.bAutoRefresh = true;
			DetectionVehicle.bAutoUpdate = true;

			DetectionVehicle.bFileStrLine = false;
			DetectionVehicle.bFileStrXML = false;

			customerProtocol = new CustomerProtocol_DB_JSYY();
		}
		else
		{
			DetectionVehicle.bProtocolSocketServer = true;
			DetectionVehicle.bProtocolWS = false;
			DetectionVehicle.bProtocolDataBase = false;
			DetectionVehicle.bProtocolFile = true;
		
			DetectionVehicle.bRefreshAsyc = false;

			DetectionVehicle.bAutoRefresh = true;
			DetectionVehicle.bAutoUpdate = true;

			DetectionVehicle.bFileStrLine = false;
			DetectionVehicle.bFileStrXML = true;
			
			customerProtocol = new CustomerProtocol_FILE_XML();
		}
	}
	
	public static void notifyDetectionList()
	{
		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, DetectionListComboBox.nvramDetectionList);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
		eventExtra.put(LMSConstValue.INTENT_EXTRA_SENSOR_ID, -1);
		LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtra);
	}
}
