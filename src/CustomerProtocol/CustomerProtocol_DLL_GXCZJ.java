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
	public static String jcbh;//��ˮ��
	public static boolean isBeginDetection;//�Ƿ�ʼ���

	public CustomerProtocol_DLL_GXCZJ()
	{

		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		//д������
		try
		{
			InetAddress address = InetAddress.getLocalHost();
			String hostAddress = address.getHostAddress();
			//����"19.15.7.250" ���"19.15.9.58"   ��������"19.15.3.30"
			if (detectPanel.iNvramDetectLength.iValue == 0 || detectPanel.iNvramDetectWidth.iValue == 0 || detectPanel.iNvramDetectHeight.iValue == 0 || "".equals(detectPanel.serialNumLabelTextField.getTextFieldText()))
			{
				LMSLog.warningDialog("���ؽ��", "�����ˮ�ţ���������߲���Ϊ��");
				return false;
			}
			else
			{
				boolean o = detectPanel.bDecision;
				String clwkccpd = "";//���������ߴ��ж�,0-δ�죬1-�ϸ�2-���ϸ�
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
				    + "</jcbh>" //�����
				    + "<zxh>0</zxh>" //����ߴ���,�ɿ�
				    + "<jyxm>M1</jyxm>" //������Ŀ,m1
				    + "<jycs>1</jycs>" //�������
				    + "<cwkc>"
				    + detectPanel.iNvramDetectLength.iValue
				    + "</cwkc>" //��������
				    + "<cwkk>"
				    + detectPanel.iNvramDetectWidth.iValue
				    + "</cwkk>" //��������
				    + "<cwkg>"
				    + detectPanel.iNvramDetectHeight.iValue
				    + "</cwkg>" //��������
				    + "<zj>"
				    + detectPanel.iNvramDetectZJ.iValue
				    + "</zj>" //���,�ɿ�
				    + "<clwkccpd>"
				    + clwkccpd
				    + "</clwkccpd>" //���������ߴ��ж�,0-δ�죬1-�ϸ�2-���ϸ�
				    + "<cbmc></cbmc>" //�豸����,�ɿ�
				    + "</vehispara>"
				    + "</root>";

				boolean bl = ZJDLL.instanceDll.init();

				int zj = ZJDLL.instanceDll.writeObjectOut("18", "18C81", UTF8XmlDoc, "9001", "M1", rexml);
				if (bl != true || zj != 0)
				{
					LMSLog.warningDialog("���ؽ��", "����ʧ�ܣ����ò���������DLL,��ʼ��Ϊ " + bl + "������ֵΪ" + zj);
					return false;
				}
				String res = new String(rexml);

				Map map1 = ParserXML.ReadXML(res.replace("\r", "").replace("\n", "").split("</root>")[0] + "</root>");
				Map map = (Map) map1.get("head");
				String code = (String) map.get("code");
				String msg = (String) map.get("message");

				//���ͽ����ź�
				byte[] rexml2 = new byte[1024];
				String UTF8XmlDoc2 = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				    + "<root><vehispara>"
				    + "<jcbh>"
				    + detectPanel.serialNumLabelTextField.getTextFieldText()
				    + "</jcbh>" //�����				
				    + "<zxh>0</zxh>" //����ߴ���,�ɿ�
				    + "<jycs>1</jycs>" //�������
				    + "<cbh></cbh>" //�豸����,�ɿ�
				    + "<jyxm>M1</jyxm>" //������Ŀ,m1		
				    + "</vehispara>"
				    + "</root>";

				boolean b = ZJDLL.instanceDll.init();
				int z = ZJDLL.instanceDll.writeObjectOut("18", "18C58", UTF8XmlDoc2, "9001", "M1", rexml2);
				if (b != true || z != 0)
				{
					LMSLog.warningDialog("���ؽ��", "����ʧ�ܣ����ò���������DLL,��ʼ��Ϊ " + b + "������ֵΪ" + z);
					return false;
				}
				String res2 = new String(rexml2);

				Map map12 = ParserXML.ReadXML(res2.replace("\r", "").replace("\n", "").split("</root>")[0] + "</root>");
				Map map2 = (Map) map12.get("head");
				String msgg = (String) map.get("message");
				String code2 = (String) map2.get("code");

				if (!code2.equals("1"))
				{
					LMSLog.warningDialog("���ؽ��", "�����ź��ϴ�ʧ��," + msgg);
					return false;
				}
				if (code.equals("1"))
				{
					LMSLog.warningDialog("���ؽ��", "�����ϴ��ɹ�");
					DetectionVehicle.detectionVehicleList.clear();
					DetectionVehicle detectionVehicle = new DetectionVehicle();
					detectionVehicle.sVehicleNumType = "01";//��������
					detectionVehicle.sVehicleNum = "��";//���ƺ���
					DetectionVehicle.detectionVehicleList.add(detectionVehicle);
					notifyDetectionList();
					return true;
				}
				else
				{
					LMSLog.warningDialog("���ؽ��", "�����ϴ�ʧ��");
					return false;
				}
			}
		}
		catch (Exception e)
		{
			LMSLog.exceptionDialog("������쳣", e);
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
			//			String hphm="��KV1193";
			//			String hphm2 = URLEncoder.encode(hphm1, "utf-8");
			if ("".equals(detectPanel.vehicleNumLabelTextField.getTextFieldText()) || "".equals(detectPanel.vehicleNumTypeLabelTextField.getTextFieldText()))
			{
				LMSLog.warningDialog("���ؽ��", "���ƺ��룬�������಻��Ϊ��");
			}
			else
			{
				String UTF8XmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\"?>"
				    + "<root><QueryCondition>"
				    + "<hpzl>"
				    + detectPanel.vehicleNumTypeLabelTextField.getTextFieldText()
				    + "</hpzl>"//��������
				    + "<hphm>"
				    + URLEncoder.encode(detectPanel.vehicleNumLabelTextField.getTextFieldText(), "utf-8")
				    + "</hphm>"//���ƺ���
				    + "</QueryCondition></root>";
				byte[] test = new byte[1024];

				boolean o = ZJDLL.instanceDll.init();
				int z = ZJDLL.instanceDll.queryObjectOut("18", "18C91", UTF8XmlDoc, "9001", "M1", test);
				if (z != 0 || o != true)
				{
					LMSLog.warningDialog("���ؽ��", "����ʧ�ܣ����ò���������DLL,��ʼ��Ϊ " + o + "������ֵΪ" + z);
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
						String jylb = (String) map.get("jylb"); //�������
						detectionVehicle.sSerialNum = (String) map.get("jcbh");//��ˮ��
						detectionVehicle.sVehicleNumType = (String) map.get("hpzl");//��������
						detectionVehicle.sVehicleNum = (String) map.get("hphm");//���ƺ���
						detectionVehicle.sVehicleID = (String) map.get("clsbdh");//����ʶ�����
						String zs = (String) map.get("zs");//����
						detectionVehicle.sVehicleType = (String) map.get("cllx");//��������
						String clxh = (String) map.get("clxh");//�����ͺ�
						detectionVehicle.sVehicleBrand = (String) map.get("clpp1");//����Ʒ��
						detectionVehicle.sOriginalCarLength = (String) map.get("cwkc");//��������
						detectionVehicle.sOriginalCarWidth = (String) map.get("cwkk");//��������
						detectionVehicle.sOriginalCarHeight = (String) map.get("cwkg");//��������
						String syr = (String) map.get("syr");//������
						String dlrq = (String) map.get("dlrq");//��¼����
						detectionVehicle.sOriginalZJ = (String) map.get("zj");//���

						DetectionVehicle.detectionVehicleList.add(detectionVehicle);

						notifyDetectionList();
					}
					else
					{
						LMSLog.warningDialog("���ؽ��", msg);
					}
				}
			}
		}
		catch (java.lang.UnsatisfiedLinkError e)
		{
			LMSLog.exceptionDialog("������쳣,ȱ�ٶ�̬���ӿ�", e);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			LMSLog.exceptionDialog("������쳣", e);
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
				LMSLog.exceptionDialog("������쳣", e);
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
				    + "</jcbh>" //�����
				    + "<zxh>0</zxh>" //����ߴ���,�ɿ�
				    + "<jycs>1</jycs>" //�������		   
				    + "<cbh></cbh>" //�豸����,�ɿ�
				    + "<jyxm>M1</jyxm>" //������Ŀ,m1
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
					LMSLog.warningDialog("���ؽ��", "��ʼ�ź�ʧ�ܣ������=" + detectPanel.serialNumLabelTextField.getTextFieldText() + ", ������Ϣ=" + msg + ", ��ˮ��=" + s);
				}
				if (!code.equals("1"))
				{
					LMSLog.warningDialog("���ؽ��", "��Ŀ��ʼ�ź��ϴ�ʧ��, ������Ϣ=" + msg + ", ��ˮ��=" + s);
				}
			}
			catch (Exception e)
			{
				LMSLog.warningDialog("���ؽ��", "��ʼ�źŽӿڷ���:" + res + ",�쳣��" + e);
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
				//����				
				byte[] rexml = new byte[1024];
				String s = detectPanel.serialNumLabelTextField.getTextFieldText();
				String UTF8XmlDoc = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				    + "<root><vehispara>"
				    + "<jcbh>"
				    + detectPanel.serialNumLabelTextField.getTextFieldText()
				    + "</jcbh>" //�����
				    + "<zxh>0</zxh>" //����ߴ���,�ɿ�
				    + "<jyxm>M1</jyxm>" //������Ŀ,m1
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
					LMSLog.warningDialog("���ؽ��", "������������ʧ�ܣ�" + "�����=" + detectPanel.serialNumLabelTextField.getTextFieldText() + ", ������Ϣ=" + msg);
				}
				if (!code.equals("1"))
				{
					LMSLog.warningDialog("���ؽ��", "������������ʧ��, ��ˮ��=" + s);
				}
				
			}catch(Exception e){
				LMSLog.warningDialog("���ؽ��", "������������ʧ��,����xml:" + res+"��ʼ����"+o+",����ֵ��"+z);
			}
			}
		}
	}
}
