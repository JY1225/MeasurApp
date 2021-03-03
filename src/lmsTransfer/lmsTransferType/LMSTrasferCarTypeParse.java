package lmsTransfer.lmsTransferType;

import AppBase.appBase.CarTypeAdapter;
import AppFrame.debugerManager.SettingFrameFsrlProtocol;
import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

public class LMSTrasferCarTypeParse {
	private final static String DEBUG_TAG="LMSTrasferCarTypeParse";

	public static String carTypeParse(String result)
	{
		if(result != null)
		{			
			CarTypeAdapter.oldCarTypeAdapter(result);
			
			if(result.contains("GET_CAR_TYPE"))
			{		
				String outStr = null;
				if(result.equals("<STX>"))
					outStr = "<STX>"+CarTypeAdapter.sNvramCarTypeString.sValue+"<ETX>";
				else
					outStr = "\2"+CarTypeAdapter.sNvramCarTypeString.sValue+"\3";
					
				return outStr;
			}
	
			
			if(result.contains("DTBC_SET_"))
			{
				int beginIndex,endIndex;

				try {
					if(result.contains(LMSConstValue.sDTBC_Set_W))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_W)+LMSConstValue.sDTBC_Set_W.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_W = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_W+LMSConstValue.iDTBC_W);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_H))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_H)+LMSConstValue.sDTBC_Set_H.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_H = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_H+LMSConstValue.iDTBC_H);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_L))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_L)+LMSConstValue.sDTBC_Set_L.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_L = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_L+LMSConstValue.iDTBC_L);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_GW))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_GW)+LMSConstValue.sDTBC_Set_GW.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_GW = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_GW+LMSConstValue.iDTBC_GW);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_GH))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_GH)+LMSConstValue.sDTBC_Set_GH.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_GH = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_GH+LMSConstValue.iDTBC_GH);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_GL))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_GL)+LMSConstValue.sDTBC_Set_GL.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_GL = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_GL+LMSConstValue.iDTBC_GL);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_QW))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_QW)+LMSConstValue.sDTBC_Set_QW.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_QW = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_QW+LMSConstValue.iDTBC_QW);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_QH))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_QH)+LMSConstValue.sDTBC_Set_QH.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_QH = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_QH+LMSConstValue.iDTBC_QH);
					}
					if(result.contains(LMSConstValue.sDTBC_Set_QL))
					{									
						beginIndex = result.indexOf(LMSConstValue.sDTBC_Set_QL)+LMSConstValue.sDTBC_Set_QL.length();
						endIndex = result.indexOf("<ETX>",beginIndex);
						LMSConstValue.iDTBC_QL = Integer.valueOf(result.substring(beginIndex,endIndex));
						
						LMSLog.d(DEBUG_TAG,LMSConstValue.sDTBC_Set_QL+LMSConstValue.iDTBC_QL);
					}
				}
				catch (java.lang.NumberFormatException e)
				{
					LMSLog.exceptionDialog("“Ï≥£", e);
				}
			}
		}
		
		return "";
	}
}
