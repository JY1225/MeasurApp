package CustomerProtocol;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;

/**
 * @author jacky.yang
 *	����-�����ļ�
 */
public class CustomerProtocol_FILE_JSHX extends CustomerProtocol_FILE
{

	String DEBUG_TAG = "CustomerProtocol_FILE_HX";

	public CustomerProtocol_FILE_JSHX()
	{
		String fieldPath = "";//LMSConstValue.sNvramFieldUrl.sValue;
		CustomerProtocol_FILE.readFilePath = fieldPath;//"D:\\���������ߴ�\\NetFace";
		CustomerProtocol_FILE.readFileName = "Wkcarinfo.ini";

	}

	@SuppressWarnings("resource")
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		boolean o = detectPanel.bDecision;
		String clwkccpd = "";//���������ߴ��ж�,�ϸ񣬲��ϸ�
		if (o == true)
		{
			clwkccpd = "�ϸ�";
		}
		else
		{
			clwkccpd = "���ϸ�";
		}
		boolean l = detectPanel.bSingleDecisionLength;
		String clwkl = "";
		if (l == true)
		{
			clwkl = "�ϸ�";
		}
		else
		{
			clwkl = "���ϸ�";
		}
		boolean w = detectPanel.bSingleDecisionWidth;
		String clwkw = "";
		if (w == true)
		{
			clwkw = "�ϸ�";
		}
		else
		{
			clwkw = "���ϸ�";
		}
		boolean h = detectPanel.bSingleDecisionHeight;
		String clwkh = "";
		if (h == true)
		{
			clwkh = "�ϸ�";
		}
		else
		{
			clwkh = "���ϸ�";
		}
		boolean lb = detectPanel.bSingleDecisionLanban;
		String clwklb = "";
		if (lb == true)
		{
			clwklb = "�ϸ�";
		}
		else
		{
			clwklb = "���ϸ�";
		}
		boolean zj = detectPanel.bSingleDecisionZJ;
		String clwkzj = "";
		if (zj == true)
		{
			clwkzj = "�ϸ�";
		}
		else
		{
			clwkzj = "���ϸ�";
		}

		StringBuilder strfile = new StringBuilder("[�������]\r\n");
		if (detectPanel.iNvramDetectLength.iValue == 0)
		{
			LMSLog.warningDialog("���������ϴ�", "���������ϴ�,������Ϊ��");

			return false;
		}
		if (detectPanel.iNvramDetectWidth.iValue == 0)
		{
			LMSLog.warningDialog("���������ϴ�", "���������ϴ�,����Ϊ��");

			return false;
		}
		if (detectPanel.iNvramDetectHeight.iValue == 0)
		{
			LMSLog.warningDialog("���������ϴ�", "���������ϴ�,�߲���Ϊ��");

			return false;
		}
		//������Ϣ
		//��ˮ��
		strfile.append("�����ˮ��=" + detectPanel.serialNumLabelTextField.getTextFieldText() + "\r\n");
		//��������
		strfile.append("��������=" + detectPanel.vehicleNumTypeLabelTextField.getTextFieldText() + "\r\n");
		//���ƺ���
		strfile.append("���ƺ���=" + detectPanel.vehicleNumLabelTextField.getTextFieldText() + "\r\n");
		//��
		strfile.append("ʵ�⳵������=" + detectPanel.iNvramDetectLength.iValue + "\r\n");
		//��
		strfile.append("ʵ�⳵������=" + detectPanel.iNvramDetectWidth.iValue + "\r\n");
		//��
		strfile.append("ʵ�⳵������=" + detectPanel.iNvramDetectHeight.iValue + "\r\n");
		//���
		strfile.append("ʵ�⳵�����=" + detectPanel.iNvramDetectZJ.iValue + "\r\n");
		//�����
		strfile.append("ʵ�⳵�����=" + detectPanel.iNvramDetectLanbanHeight.iValue + "\r\n");
		//��⿪ʼʱ��
		strfile.append("��⿪ʼʱ��=" + detectPanel.inTime + "\r\n");
		//������ʱ��
		strfile.append("������ʱ��=" + detectPanel.outTime + "\r\n\r\n");
		//����ж�
		strfile.append("[����ж�]\r\n");
		//���������ж�
		strfile.append("���������ж�=" + clwkl + "\r\n");
		//���������ж�
		strfile.append("���������ж�=" + clwkw + "\r\n");
		//���������ж�
		strfile.append("���������ж�=" + clwkh + "\r\n");
		//��������ж�
		strfile.append("��������ж�=" + clwkzj + "\r\n");
		//��������ж�
		strfile.append("��������ж�=" + clwklb + "\r\n");
		//�����ж�
		strfile.append("�����ж�=" + clwkccpd + "\r\n");

		//������������ļ�
		File wfile = new File("/" + detectPanel.serialNumLabelTextField.getTextFieldText() + "rst.dat");
		FileWriter filewriter;
		try
		{
			filewriter = new FileWriter(wfile);
			BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
			bufferedWriter.write(strfile.toString());
			bufferedWriter.flush();
			//���������ɱ�־�ļ�
			new File("/Dataready.dat").createNewFile();
		}
		catch (IOException e)
		{
			LMSLog.exceptionDialog("�ļ��쳣", e);
		}
		return true;
	}

	@Override
	public void parseDetectionList(ArrayList<String> lineList)
	{
		DetectionVehicle.bAutoRefresh = true;
		DetectionVehicle.detectionVehicleList.clear();
		DetectionVehicle detectionVehicle = new DetectionVehicle();

		for (int i = 0; i < lineList.size(); i++)
		{
			String str = lineList.get(i);

			int index = str.indexOf("=");
			if (str.contains("������ˮ��"))
			{
				detectionVehicle.sSerialNum = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "�����ˮ��=" + detectionVehicle.sSerialNum);

			}

			if (str.contains("��������"))
			{
				detectionVehicle.sVehicleNumType = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "��������=" + detectionVehicle.sVehicleNumType);

			}
			if (str.contains("���ƺ���"))
			{
				detectionVehicle.sVehicleNum = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "���ƺ���=" + detectionVehicle.sVehicleNum);
				if (!"".equals(detectionVehicle.sVehicleNum))
				{
					detectionVehicle.sNewOrOld = "���ó�";
				}
				else
				{
					detectionVehicle.sNewOrOld = "ע�ᳵ";
				}

			}

			if (str.contains("Ʒ���ͺ�"))
			{

				detectionVehicle.sVehicleBrand = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "Ʒ���ͺ�=" + detectionVehicle.sVehicleBrand);

			}
			if (str.contains("��������"))
			{

				detectionVehicle.sVehicleType = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "��������=" + detectionVehicle.sVehicleType);

			}
			if (str.contains("VIN"))
			{

				detectionVehicle.sVehicleID = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "����ʶ����=" + detectionVehicle.sVehicleID);

			}
			if (str.contains("��������"))
			{

				detectionVehicle.sMotorID = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "����������=" + detectionVehicle.sMotorID);

			}
			if (str.contains("��������"))
			{

				detectionVehicle.sOriginalCarLength = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "��������=" + detectionVehicle.sOriginalCarLength);

			}
			if (str.contains("��������"))
			{

				detectionVehicle.sOriginalCarWidth = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "��������=" + detectionVehicle.sOriginalCarWidth);

			}
			if (str.contains("��������"))
			{

				detectionVehicle.sOriginalCarHeight = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "��������=" + detectionVehicle.sOriginalCarHeight);

			}
			if (str.contains("�������"))
			{

				detectionVehicle.sOriginalLanBanHeight = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "�������=" + detectionVehicle.sOriginalLanBanHeight);

			}
			if (str.contains("�������"))
			{

				detectionVehicle.sOriginalZJ = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "�������=" + detectionVehicle.sOriginalZJ);

			}
		}
		File rfile = new File("/Wkcarinfo.ini");
		if (rfile.exists())
		{
			rfile.delete();
		}
		DetectionVehicle.detectionVehicleList.add(detectionVehicle);
	}

	@Override
	public void parseDetectionList(String xmlStr)
	{
		// TODO Auto-generated method stub

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
	public void beginDetection(ContourDetectionTabPanelMain detectPanel,boolean bValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void carInSignal(ContourDetectionTabPanelMain detectPanel, boolean bValue) 
	{
		if(bValue == true)
		{
			try
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				detectPanel.inTime = df.format(new Date());
				new File("/TakePhotoIn.cmd").createNewFile();
			}
			catch (IOException e)
			{
				LMSLog.exceptionDialog("������쳣", e);
			}
		}
		else
		{
			try
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				detectPanel.outTime = df.format(new Date());
				new File("/TakePhotoOut.cmd").createNewFile();
			}
			catch (IOException e)
			{
				LMSLog.exceptionDialog("������쳣", e);
			}
		}
	}
}
