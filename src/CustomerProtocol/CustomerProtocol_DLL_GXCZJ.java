package CustomerProtocol;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import ParserXml.ParserXML;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class CustomerProtocol_DLL_GXCZJ extends CustomerProtocol_DLL
{
	public static String jcbh;//流水号
	public static boolean isBeginDetection;//是否开始检测

	public CustomerProtocol_DLL_GXCZJ()
	{

		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		//写入数据
		try
		{
			InetAddress address = InetAddress.getLocalHost();
			String hostAddress = address.getHostAddress();
			//龙州"19.15.7.250" 天等"19.15.9.58"   崇左联杰"19.15.3.30"
			if (detectPanel.iNvramDetectLength.iValue == 0 || detectPanel.iNvramDetectWidth.iValue == 0 || detectPanel.iNvramDetectHeight.iValue == 0 || "".equals(detectPanel.serialNumLabelTextField.getTextFieldText()))
			{
				LMSLog.warningDialog("返回结果", "检测流水号，测量长宽高不能为空");
				return false;
			}
			else
			{
				boolean o = detectPanel.bDecision;
				String clwkccpd = "";//车辆外廓尺寸判定,0-未检，1-合格，2-不合格
				if (o == true)
				{
					clwkccpd = "1";
				}
				else
				{
					clwkccpd = "2";
				}
				byte[] rexml = new byte[1024];
				String UTF8XmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				    + "<root><vehispara>"
				    + "<ip>"
				    + hostAddress
				    + "</ip>"
				    + "<jcbh>"
				    + detectPanel.serialNumLabelTextField.getTextFieldText()
				    + "</jcbh>" //检测编号
				    + "<zxh>0</zxh>" //检测线代号,可空
				    + "<jyxm>M1</jyxm>" //检验项目,m1
				    + "<jycs>1</jycs>" //检验次数
				    + "<cwkc>"
				    + detectPanel.iNvramDetectLength.iValue
				    + "</cwkc>" //车外廓长
				    + "<cwkk>"
				    + detectPanel.iNvramDetectWidth.iValue
				    + "</cwkk>" //车外廓宽
				    + "<cwkg>"
				    + detectPanel.iNvramDetectHeight.iValue
				    + "</cwkg>" //车外廓高
				    + "<zj>"
				    + detectPanel.iNvramDetectZJ.iValue
				    + "</zj>" //轴距,可空
				    + "<clwkccpd>"
				    + clwkccpd
				    + "</clwkccpd>" //车辆外廓尺寸判定,0-未检，1-合格，2-不合格
				    + "<cbmc></cbmc>" //设备名称,可空
				    + "</vehispara>"
				    + "</root>";

				boolean bl = ZJDLL.instanceDll.init();

				int zj = ZJDLL.instanceDll.writeObjectOut("18", "18C81", UTF8XmlDoc, "9001", "M1", rexml);
				if (bl != true || zj != 0)
				{
					LMSLog.warningDialog("返回结果", "联网失败，调用不到服务器DLL,初始化为 " + bl + "，返回值为" + zj);
					return false;
				}
				String res = new String(rexml);

				Map map1 = ParserXML.ReadXML(res.replace("\r", "").replace("\n", "").split("</root>")[0] + "</root>");
				Map map = (Map) map1.get("head");
				String code = (String) map.get("code");
				String msg = (String) map.get("message");

				//发送结束信号
				byte[] rexml2 = new byte[1024];
				String UTF8XmlDoc2 = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				    + "<root><vehispara>"
				    + "<jcbh>"
				    + detectPanel.serialNumLabelTextField.getTextFieldText()
				    + "</jcbh>" //检测编号				
				    + "<zxh>0</zxh>" //检测线代号,可空
				    + "<jycs>1</jycs>" //检验次数
				    + "<cbh></cbh>" //设备名称,可空
				    + "<jyxm>M1</jyxm>" //检验项目,m1		
				    + "</vehispara>"
				    + "</root>";

				boolean b = ZJDLL.instanceDll.init();
				int z = ZJDLL.instanceDll.writeObjectOut("18", "18C58", UTF8XmlDoc2, "9001", "M1", rexml2);
				if (b != true || z != 0)
				{
					LMSLog.warningDialog("返回结果", "联网失败，调用不到服务器DLL,初始化为 " + b + "，返回值为" + z);
					return false;
				}
				String res2 = new String(rexml2);

				Map map12 = ParserXML.ReadXML(res2.replace("\r", "").replace("\n", "").split("</root>")[0] + "</root>");
				Map map2 = (Map) map12.get("head");
				String msgg = (String) map.get("message");
				String code2 = (String) map2.get("code");

				if (!code2.equals("1"))
				{
					LMSLog.warningDialog("返回结果", "结束信号上传失败," + msgg);
					return false;
				}
				if (code.equals("1"))
				{
					LMSLog.warningDialog("返回结果", "数据上传成功");
					DetectionVehicle.detectionVehicleList.clear();
					DetectionVehicle detectionVehicle = new DetectionVehicle();
					detectionVehicle.sVehicleNumType = "01";//号牌种类
					detectionVehicle.sVehicleNum = "桂";//号牌号码
					DetectionVehicle.detectionVehicleList.add(detectionVehicle);
					notifyDetectionList();
					return true;
				}
				else
				{
					LMSLog.warningDialog("返回结果", "数据上传失败");
					return false;
				}
			}
		}
		catch (Exception e)
		{
			LMSLog.exceptionDialog("检测仪异常", e);
			return false;
		}

	}

	@SuppressWarnings(
	{ "rawtypes", "unused" })
	@Override
	public void queryDetectionList(ContourDetectionTabPanelMain detectPanel)
	{
		try
		{

			//			String hphm1=detectPanel.vehicleNumLabelTextField.getTextFieldText();
			//			String hpzl=detectPanel.vehicleNumTypeLabelTextField.getTextFieldText();
			//			
			//			String hphm="桂KV1193";
			//			String hphm2 = URLEncoder.encode(hphm1, "utf-8");
			if ("".equals(detectPanel.vehicleNumLabelTextField.getTextFieldText()) || "".equals(detectPanel.vehicleNumTypeLabelTextField.getTextFieldText()))
			{
				LMSLog.warningDialog("返回结果", "号牌号码，号牌种类不能为空");
			}
			else
			{
				String UTF8XmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\"?>"
				    + "<root><QueryCondition>"
				    + "<hpzl>"
				    + detectPanel.vehicleNumTypeLabelTextField.getTextFieldText()
				    + "</hpzl>"//号牌种类
				    + "<hphm>"
				    + URLEncoder.encode(detectPanel.vehicleNumLabelTextField.getTextFieldText(), "utf-8")
				    + "</hphm>"//号牌号码
				    + "</QueryCondition></root>";
				byte[] test = new byte[1024];

				boolean o = ZJDLL.instanceDll.init();
				int z = ZJDLL.instanceDll.queryObjectOut("18", "18C91", UTF8XmlDoc, "9001", "M1", test);
				if (z != 0 || o != true)
				{
					LMSLog.warningDialog("返回结果", "联网失败，调用不到服务器DLL,初始化为 " + o + "，返回值为" + z);
				}
				else
				{
					String res = new String(test);

					Map map1 = ParserXML.ReadXML(res.replace("\r", "").replace("\n", "").split("</root>")[0] + "</root>");
					Map map = (Map) map1.get("head");
					String msg = (String) map.get("message");
					String code = (String) map.get("code");
					if (res != null && !"".equals(res) && code.equals("1"))
					{
						DetectionVehicle.detectionVehicleList.clear();
						DetectionVehicle detectionVehicle = new DetectionVehicle();
						jcbh = (String) map.get("jcbh");
						String jylb = (String) map.get("jylb"); //检验类别
						detectionVehicle.sSerialNum = (String) map.get("jcbh");//流水号
						detectionVehicle.sVehicleNumType = (String) map.get("hpzl");//号牌种类
						detectionVehicle.sVehicleNum = (String) map.get("hphm");//号牌号码
						detectionVehicle.sVehicleID = (String) map.get("clsbdh");//车辆识别代号
						String zs = (String) map.get("zs");//轴数
						detectionVehicle.sVehicleType = (String) map.get("cllx");//车辆类型
						String clxh = (String) map.get("clxh");//车辆型号
						detectionVehicle.sVehicleBrand = (String) map.get("clpp1");//中文品牌
						detectionVehicle.sOriginalCarLength = (String) map.get("cwkc");//车外廓长
						detectionVehicle.sOriginalCarWidth = (String) map.get("cwkk");//车外廓宽
						detectionVehicle.sOriginalCarHeight = (String) map.get("cwkg");//车外廓高
						String syr = (String) map.get("syr");//所有人
						String dlrq = (String) map.get("dlrq");//登录日期
						detectionVehicle.sOriginalZJ = (String) map.get("zj");//轴距

						DetectionVehicle.detectionVehicleList.add(detectionVehicle);

						notifyDetectionList();
					}
					else
					{
						LMSLog.warningDialog("返回结果", msg);
					}
				}
			}
		}
		catch (java.lang.UnsatisfiedLinkError e)
		{
			LMSLog.exceptionDialog("检测仪异常,缺少动态链接库", e);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			LMSLog.exceptionDialog("检测仪异常", e);
		}
	}

	class EventListener implements LMSEventListener
	{
		public void lmsTransferEvent(LMSEvent event)
		{

			try
			{
				String eventType = event.getEventType();
				HashMap eventExtra = event.getEventExtra();

				int sensorID = 0;

				if (eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_SENSOR_ID))
				{
					sensorID = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_SENSOR_ID);
				}

			}
			catch (Exception e)
			{
				LMSLog.exceptionDialog("检测仪异常", e);
			}
		}
	}

	@Override
	public void beginDetection(ContourDetectionTabPanelMain detectPanel, boolean bValue)
	{
		isBeginDetection = bValue;

	}

	@Override
	public void carInSignal(ContourDetectionTabPanelMain detectPanel, boolean bValue)
	{
		if (bValue == true && isBeginDetection == true)
		{
			String msg = null;
			String res = null;
			try
			{
				byte[] rexml = new byte[1024];
				String s = detectPanel.serialNumLabelTextField.getTextFieldText();

				String UTF8XmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				    + "<root><vehispara>"
				    + "<jcbh>"
				    + detectPanel.serialNumLabelTextField.getTextFieldText()
				    + "</jcbh>" //检测编号
				    + "<zxh>0</zxh>" //检测线代号,可空
				    + "<jycs>1</jycs>" //检验次数		   
				    + "<cbh></cbh>" //设备名称,可空
				    + "<jyxm>M1</jyxm>" //检验项目,m1
				    + "</vehispara>"
				    + "</root>";

				boolean o = ZJDLL.instanceDll.init();
				int z = ZJDLL.instanceDll.writeObjectOut("18", "18C55", UTF8XmlDoc, "9001", "M1", rexml);

				res = new String(rexml);

				Map map1 = ParserXML.ReadXML(res.replace("\r", "").replace("\n", "").split("</root>")[0] + "</root>");
				Map map = (Map) map1.get("head");
				String code = (String) map.get("code");
				msg = (String) map.get("message");
				if (o != true || z != 0)
				{
					LMSLog.warningDialog("返回结果", "开始信号失败，检测编号=" + detectPanel.serialNumLabelTextField.getTextFieldText() + ", 错误信息=" + msg + ", 流水号=" + s);
				}
				if (!code.equals("1"))
				{
					LMSLog.warningDialog("返回结果", "项目开始信号上传失败, 错误信息=" + msg + ", 流水号=" + s);
				}
			}
			catch (Exception e)
			{
				LMSLog.warningDialog("返回结果", "开始信号接口返回:" + res + ",异常：" + e);
			}
		}
		else
		{
			boolean o = false;
			int z =0;
			if (isBeginDetection == true)
			{
				String res = null;
				try{					
				//拍照				
				byte[] rexml = new byte[1024];
				String s = detectPanel.serialNumLabelTextField.getTextFieldText();
				String UTF8XmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				    + "<root><vehispara>"
				    + "<jcbh>"
				    + detectPanel.serialNumLabelTextField.getTextFieldText()
				    + "</jcbh>" //检测编号
				    + "<zxh>0</zxh>" //检测线代号,可空
				    + "<jyxm>M1</jyxm>" //检验项目,m1
				    + "</vehispara>"
				    + "</root>";

				o = ZJDLL.instanceDll.init();
				z = ZJDLL.instanceDll.writeObjectOut("18", "18C99", UTF8XmlDoc, "9001", "M1", rexml);

				res = new String(rexml);

				Map map1 = ParserXML.ReadXML(res.replace("\r", "").replace("\n", "").split("</root>")[0] + "</root>");
				Map map = (Map) map1.get("head");
				String code = (String) map.get("code");
				String msg = (String) map.get("message");
				if (o != true || z != 0)
				{
					LMSLog.warningDialog("返回结果", "车出监听拍照失败，" + "检测编号=" + detectPanel.serialNumLabelTextField.getTextFieldText() + ", 错误信息=" + msg);
				}
				if (!code.equals("1"))
				{
					LMSLog.warningDialog("返回结果", "车出监听拍照失败, 流水号=" + s);
				}
				
			}catch(Exception e){
				LMSLog.warningDialog("返回结果", "车出监听拍照失败,返回xml:" + res+"初始化："+o+",返回值："+z);
			}
			}
		}
	}
}
