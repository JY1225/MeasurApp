package lmsTransfer.lmsTransferType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.LMSConstValue.enumDetectType;
import lmsBase.LMSProductInfo;

public abstract class LMSTransferTypeResult extends LMSTransferType{
	private final static String DEBUG_TAG="LMSTransferTypeResult";

	public abstract void PHYSIC_SEND(String str);

	String ip;
	public LMSTransferTypeResult(String ip)
	{
		this.ip = ip;
	}
	
	@Override
	public void eventToCommand(String event,HashMap eventExtra) 
	{		
		if (event.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT))
		{
			int sensorID = 0;
			int carNum = 0;
			int carState = 0;
			int carWidth = 0;
			int carHeight = 0;
			int carLength = 0;
			int carWidth_rearViewMirror = 0;
			int carHeight_rearViewMirror = 0;
			int carLength_rearViewMirror = 0;
			int carWidth_frontMirror = 0;
			int carHeight_frontMirror = 0;
			int carLength_frontMirror = 0;
			int carLBHeight = 0;
			int carZNum = 0;
			int carZD[] = new int[LMSConstValue.MAX_Z_NUM-1];
			int carZJ = 0;
			int carTime = 0;
			//---------------------------
			int carGLength = 0;
			int carGWidth = 0;
			int carGHeight = 0;
			int carGHeightLeanOri = 0;
			int carGZNum = 0;
			int carGZD[] = new int[LMSConstValue.MAX_Z_NUM-1];
			int carGZJ = 0;
			//---------------------------
			int carQLength = 0;
			int carQWidth = 0;
			int carQHeight = 0;
			int carQZNum = 0;
			int carQZD[] = new int[LMSConstValue.MAX_Z_NUM-1];
			int carQZJ = 0;
			//---------------------------
			int carXZJ = 0;
			//---------------------------			
			String sCarOutTime = null;
			int antiLevel = 0;
			int errorCode = 0;
			int realVolumn = 0;
		
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_NUM))
				carNum = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_NUM); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
				carState = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_WIDTH))
				carWidth = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_WIDTH); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT))
				carHeight = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LENGTH))
				carLength = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LENGTH); 
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_REAR_VIEW_MIRROR))
				carWidth_rearViewMirror = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_REAR_VIEW_MIRROR); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_REAR_VIEW_MIRROR))
				carHeight_rearViewMirror = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_REAR_VIEW_MIRROR); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_REAR_VIEW_MIRROR))
				carLength_rearViewMirror = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_REAR_VIEW_MIRROR); 
			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_FRONT_MIRROR))
				carWidth_frontMirror = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_FRONT_MIRROR); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_FRONT_MIRROR))
				carHeight_frontMirror = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_FRONT_MIRROR); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_FRONT_MIRROR))
				carLength_frontMirror = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_FRONT_MIRROR); 

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_TIME))
				carTime = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_TIME); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_OUT_TIME))
				sCarOutTime = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_OUT_TIME); 

			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LB_HEIGHT))
				carLBHeight = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LB_HEIGHT); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_XZJ))
				carXZJ = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_XZJ); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Z_NUM))
				carZNum = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Z_NUM); 
			if(carZNum>1)
			{
				for(int i=0;i<carZNum-1;i++)
				{
					if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_ZD+i))
						carZD[i] = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_ZD+i); 
				}
			}			
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_ZJ))
				carZJ = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_ZJ); 
			//----------------------------------------------------------------------------
			//挂车
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH))
				carGLength = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_WIDTH))
				carGWidth = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_WIDTH); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT))
				carGHeight = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT_LEAN_ORI))
				carGHeightLeanOri = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT_LEAN_ORI); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_Z_NUM))
				carGZNum = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_Z_NUM); 
			if(carGZNum>1)
			{
				for(int i=0;i<carGZNum-1;i++)
				{
					if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_ZD+i))
						carGZD[i] = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_ZD+i); 
				}
			}
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_ZJ))
				carGZJ = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_ZJ); 
			//----------------------------------------------------------------------------
			//牵引车
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH))
				carQLength = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_WIDTH))
				carQWidth = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_WIDTH); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_HEIGHT))
				carQHeight = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_HEIGHT); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_Z_NUM))
				carQZNum = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_Z_NUM); 
			if(carQZNum>1)
			{
				for(int i=0;i<carQZNum-1;i++)
				{
					if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_ZD+i))
						carQZD[i] = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_ZD+i); 
				}
			}
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_ZJ))
				carQZJ = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_ZJ); 
			
			//=============================================================================
			//防撞检测
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ANTI_LEVEL))
				antiLevel = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ANTI_LEVEL); 
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_ERROR_CODE))
				errorCode = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_ERROR_CODE); 
			
			//=============================================================================
			//体积测量
			if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN))
				realVolumn = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_REAL_VOLUMN); 

			if(carState == LMSConstValue.enumCarState.CAR_RESULT.ordinal())
			{	
//				LMSLog.d(DEBUG_TAG,"carNum="+carNum+" carWidth="+carWidth+" carHeight="+carHeight+" carLength="+carLength+" time="+carTime);

				LMSConstValue.last_width = carWidth;
				LMSConstValue.last_height = carHeight;
				LMSConstValue.last_length = carLength;
				LMSConstValue.last_guache_width = carGWidth;
				LMSConstValue.last_guache_height = carGHeight;
				LMSConstValue.last_guache_length = carGLength;
				LMSConstValue.last_carTime = carTime;
				LMSConstValue.last_carOutTime = sCarOutTime;

				String str = setDetectResult(
					sensorID,
					sCarOutTime,
					carWidth,carHeight,carLength,
					carZNum,carZD,carZJ,
					carWidth_rearViewMirror,carHeight_rearViewMirror,carLength_rearViewMirror,
					carWidth_frontMirror,carHeight_frontMirror,carLength_frontMirror,
					carNum,carTime,
					//-------------------------------------------------
					carGLength,carGWidth,carGHeight,carGHeightLeanOri,carLBHeight,
					carGZNum,carGZD,carGZJ,
					//-------------------------------------------------
					carQLength,carQWidth,carQHeight,
					carQZNum,carQZD,carQZJ,
					//-------------------------------------------------
					carXZJ
					);
				
				PHYSIC_SEND(str);
			}
			else if(LMSConstValue.bNvramCarInOutSignal.bValue&&carState == LMSConstValue.enumCarState.CAR_COMMING.ordinal())
			{	
				String str = ("\2IO I C "+carNum+"\3"+'\n');
				PHYSIC_SEND(str);
			}
			else if(LMSConstValue.bNvramCarInOutSignal.bValue&&carState == LMSConstValue.enumCarState.CAR_OUTING.ordinal())
			{	
				String str = ("\2IO O C "+carNum+"\3"+'\n');
				PHYSIC_SEND(str);
			}
			else if(carState == LMSConstValue.enumCarState.ANTI_LEVEL_CHANGE.ordinal())
			{
				String str = ("\2S "+sensorID+" A "+antiLevel+" E "+errorCode+"\3"+'\n');
				PHYSIC_SEND(str);
			}
			else if(carState == LMSConstValue.enumCarState.VOLUMN_RESULT.ordinal())
			{
				String str = "\2";

				if(LMSConstValue.bNvramProtocolWithCarOutTime.bValue == true)
				{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS ");//设置日期格式
					String sendTime = df.format(new Date()); 
					str += sendTime;
				}

				str += ("VOLUMN "+realVolumn+"\3"+'\n');
				PHYSIC_SEND(str);
			}
		}
	}
			
	public void sendVerno() {
		PHYSIC_SEND("\2V "+LMSConstValue.softwareVersion+"\3");
	}  

	public static String outTimeParse(String content)
	{
		String resultStr = null;
		int startIndex,endIndex;
		String searchProtocol = "OUT_TIME ";
		startIndex = content.indexOf(searchProtocol,0);
		if(startIndex>=0)
		{
			startIndex+=searchProtocol.length();
			endIndex = startIndex+23;
			resultStr = content.substring(startIndex,endIndex);
		}
		
		return resultStr;
	}
	
	public static String resultParse(String content,String searchProtocol)
	{
		String resultStr = null;
		int startIndex,endIndex;

		startIndex = content.indexOf(searchProtocol,0);
		if(startIndex>=0)
		{
			startIndex+=searchProtocol.length();
			endIndex = content.indexOf(' ',startIndex);
			if(endIndex>0)
				resultStr = content.substring(startIndex,endIndex);
			else
				resultStr = content.substring(startIndex);
		}
		
		return resultStr;
	}
	
	public void physic_receive_server(String str)
	{
		LMSLog.d(DEBUG_TAG,"physic_receive str="+str);
		
		String result = null;
		if(str.startsWith("\02"))
		{
			if(str.endsWith("\03"))
				result = str.substring(1,str.length()-1);
			else if(str.endsWith("\03\n"))
				result = str.substring(1,str.length()-2);
		}
		else
		{
			result = str;
		}
		
		String outStr = LMSTrasferCarTypeParse.carTypeParse(result);
		
		if(result.contains("GET_CAR_TYPE"))
		{
			PHYSIC_SEND(outStr);
		}
	}
	
	public String setDetectResult(int sensorID,
		String sCarOutTime,
		int w,int h,int l,
		int zNum,int[] zD,int zJ,
		int w_RearViewMirror,int h_RearViewMirror,int l_RearViewMirror,
		int w_frontMirror,int h_frontMirror,int l_frontMirror,
		int carNum,int time,
		//-----------------------------------------------------
		int gLength,int gWidth,int gHeight,int gHeightLeanOri,int lbHeight,
		int gZNum,int[] gZD,int gZJ,
		//-----------------------------------------------------
		int qianYingLength,int qianYingWidth,int qianYingHeight,
		int qZNum,int[] qZD,int qZJ,
		//-----------------------------------------------------
		int carXZJ
	)
	{
		String str = "";
		String strCarOut = "";
		String strLog = "";
		
    	if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HGJN))
    	{
    		str += "V "+ LMSConstValue.softwareVersionNum;
    		str += " W "+w +" H " +h +" L "+l+" C "+carNum+" T "+time
				+" B "+lbHeight;
			str += (" ZN "+zNum);
			if(zNum>1)
			{
				for(int i=0;i<zNum-1;i++)
				{
					str+=(" Z"+i+" "+zD[i]);
				}
			}
			str += (" ZJ "+zJ);
			//-----------------------------------------------------
			str	+= " QL "+qianYingLength+" QW "+qianYingWidth+" QH "+qianYingHeight;
			str += (" QZN "+qZNum);
			if(qZNum>1)
			{
				for(int i=0;i<qZNum-1;i++)
				{
					str+=(" QZ"+i+" "+qZD[i]);
				}
			};
			str += (" QZJ "+qZJ);
			//-----------------------------------------------------
			str += " GL "+gLength+" GW "+gWidth+" GH "+gHeight+" GOH "+gHeightLeanOri;
			str += (" GZN "+gZNum);
			if(gZNum>1)
			{
				for(int i=0;i<gZNum-1;i++)
				{
					str+=(" GZ"+i+" "+gZD[i]);
				}
			};
			str += (" GZJ "+gZJ);
			//-----------------------------------------------------
			str += " XZJ "+carXZJ;
			//-----------------------------------------------------
			str	+= " WRM "+w_RearViewMirror +" HRM " +h_RearViewMirror +" LRM "+l_RearViewMirror
				+" WFM "+w_frontMirror +" HFM " +h_frontMirror +" LFM "+l_frontMirror;
    	}
    	else if(LMSConstValue.enumOutputType.key != null 
			&&LMSConstValue.enumOutputType.key.equals(LMSConstValue.EnumOutputType.OUTPUT_WH))
		{
			str += "W "+w +" H " +h;
		}
		else if(LMSConstValue.enumOutputType.key != null 
			&&LMSConstValue.enumOutputType.key.equals(LMSConstValue.EnumOutputType.OUTPUT_WHL))
		{
			str += "W "+w +" H " +h +" L "+l+" C "+carNum+" T "+time;
		}
		else if(LMSConstValue.enumOutputType.key != null 
			&&LMSConstValue.enumOutputType.key.equals(LMSConstValue.EnumOutputType.OUTPUT_FULL))	
		{
    		str += "V "+ LMSConstValue.softwareVersionNum;
			str += " W "+w +" H " +h +" L "+l+" C "+carNum+" T "+time
				+" B "+lbHeight;
			str += (" ZN "+zNum);
			if(zNum>1)
			{
				for(int i=0;i<zNum-1;i++)
				{
					str+=(" Z"+i+" "+zD[i]);
				}
			}
			//-----------------------------------------------------
			str	+= " QL "+qianYingLength+" QW "+qianYingWidth+" QH "+qianYingHeight;
			str += (" QZN "+qZNum);
			if(qZNum>1)
			{
				for(int i=0;i<qZNum-1;i++)
				{
					str+=(" QZ"+i+" "+qZD[i]);
				}
			};
			//-----------------------------------------------------
			str += " GL "+gLength+" GW "+gWidth+" GH "+gHeight+" GOH "+gHeightLeanOri;;
			str += (" GZN "+gZNum);
			if(gZNum>1)
			{
				for(int i=0;i<gZNum-1;i++)
				{
					str+=(" GZ"+i+" "+gZD[i]);
				}
			};
			//-----------------------------------------------------
			str += " XZJ "+carXZJ;
		}
		
		//===============================================
		if(LMSConstValue.bNvramProtocolWithCarOutTime.bValue == true)
		{
			strCarOut = sCarOutTime+" "+str;
			
			if(LMSConstValue.bNvramProtocolCarOutTimeWithStr.bValue == true)
			{
				strCarOut = "OUT_TIME "+strCarOut;
			}
		}
		else
		{
			strCarOut = str;
		}
		
		//================================================================
		if(ip.endsWith(LMSConstValue.LOCAL_IP))
		{
			carOutFile(strCarOut);

			//================================================================
			strLog = sCarOutTime+" "+str;
			LMSLog.carOut(strLog);

			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_CAR_OUT_LOG_STR,strLog);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_OUT_LOG_INTENT,eventExtra);	    						    		
		}
				
		strCarOut = "\2"+strCarOut+"\3";
		strCarOut += '\n';

		//===============================================
		return strCarOut;
	}
	
	private static void carOutFile(String str)
	{
		String carNum = LMSTransferTypeResult.resultParse(str,"C ");
		if(carNum != null)
		{
			int iCarNum = Integer.valueOf(carNum);
			String FILE_NAME = "result//result"+iCarNum+".data";
			
			try {
				File test= new File(FILE_NAME); 
				long fileLength = test.length(); 
				if(fileLength<1024*1024)
				{								
					FileWriter fileWriter = null;
					if(LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED
						||LMSConstValue.defaultDetectType == enumDetectType.LM1)
					{
						fileWriter = new FileWriter(FILE_NAME,true);
					}
					else
					{
						fileWriter = new FileWriter(FILE_NAME,false);					
					}
					if(fileWriter != null)
					{
						fileWriter.write(str+"\r\n");						
						
						fileWriter.close();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}										
		}
	}

}
