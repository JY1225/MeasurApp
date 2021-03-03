package CustomerProtocol;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import AppFrame.contourDetection.ContourDetectionFrame;
import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

/**
 * @author jacky.yang
 *	�ϻ�-�����ļ�
 */
public class CustomerProtocol_FILE_FSNH extends CustomerProtocol_FILE
{

	String DEBUG_TAG = "CustomerProtocol_FILE_FSNH";
	static String TrailerMeasure;
	static String IsDetBoxHeight;//����-�Ƿ�������
	static String IsDetTrailerBoxHeight;//�ҳ�-�Ƿ�������

	public CustomerProtocol_FILE_FSNH()
	{
		String fieldPath = "";//LMSConstValue.sNvramFieldUrl.sValue;
		CustomerProtocol_FILE.readFilePath = fieldPath;//"D:\\Share";
		CustomerProtocol_FILE.readFileName = "BegCTDet.ini";

	}

	public static String CarType = "";

	@SuppressWarnings("resource")
	@Override
	public boolean updateDetectionResult(ContourDetectionTabPanelMain detectPanel)
	{
		StringBuilder strfile = new StringBuilder("[DetResult]\r\n");//�����
		String clwkl = "";
		String clwkw = "";
		String clwkh = "";
		String clwklb = "";
		//����܇
		String qclwkl = "";
		String qclwkw = "";
		String qclwkh = "";
		String qclwklb = "";
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

		//�������ȱ�׼ֵ
		//        strfile.append("VehLengthStandard = "+Integer.valueOf(
		//        		detectPanel.jSettingLabelTextFieldOriginalLength.getTextFieldText())+"\r\n");
		if (TrailerMeasure.equals("0"))//��ͨ��
		{
			if (detectPanel.bSingleDecisionLength)
			{
				clwkl = "0";
			}
			else
			{
				clwkl = "1";
			}
			if (detectPanel.bSingleDecisionWidth)
			{
				clwkw = "0";
			}
			else
			{
				clwkw = "1";
			}
			if (detectPanel.bSingleDecisionHeight)
			{
				clwkh = "0";
			}
			else
			{
				clwkh = "1";
			}
			if (IsDetBoxHeight.equals("1"))
			{
				if (detectPanel.bSingleDecisionLanban)
				{
					clwklb = "0";
				}
				else
				{
					clwklb = "1";
				}
			}
		}
		if (TrailerMeasure.equals("1"))//ǣ��������+�ҳ�
		{
			//�ҳ��ж�
			if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionLength)
			{
				clwkl = "0";
			}
			else
			{
				clwkl = "1";
			}
			if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionWidth)
			{
				clwkw = "0";
			}
			else
			{
				clwkw = "1";
			}
			if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionHeight)
			{
				clwkh = "0";
			}
			else
			{
				clwkh = "1";
			}
			if (IsDetTrailerBoxHeight.equals("1"))
			{
				if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionLanban)
				{
					clwklb = "0";
				}
				else
				{
					clwklb = "1";
				}
			}
			//ǣ�����ж�
			if (ContourDetectionFrame.mainQianYingDetectPanel.bSingleDecisionLength)
			{
				qclwkl = "0";
			}
			else
			{
				qclwkl = "1";
			}
			if (ContourDetectionFrame.mainQianYingDetectPanel.bSingleDecisionWidth)
			{
				qclwkw = "0";
			}
			else
			{
				qclwkw = "1";
			}
			if (ContourDetectionFrame.mainQianYingDetectPanel.bSingleDecisionHeight)
			{
				qclwkh = "0";
			}
			else
			{
				qclwkh = "1";
			}
			
		}
		if (TrailerMeasure.equals("2"))//ǣ����
		{
			if (ContourDetectionFrame.mainQianYingDetectPanel.bSingleDecisionLength)
			{
				qclwkl = "0";
			}
			else
			{
				qclwkl = "1";
			}
			if (ContourDetectionFrame.mainQianYingDetectPanel.bSingleDecisionWidth)
			{
				qclwkw = "0";
			}
			else
			{
				qclwkw = "1";
			}
			if (ContourDetectionFrame.mainQianYingDetectPanel.bSingleDecisionHeight)
			{
				qclwkh = "0";
			}
			else
			{
				qclwkh = "1";
			}
			
		}
		if (TrailerMeasure.equals("3"))//�ҳ�
		{
			if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionLength)
			{
				clwkl = "0";
			}
			else
			{
				clwkl = "1";
			}
			if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionWidth)
			{
				clwkw = "0";
			}
			else
			{
				clwkw = "1";
			}
			if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionHeight)
			{
				clwkh = "0";
			}
			else
			{
				clwkh = "1";
			}
			if (IsDetTrailerBoxHeight.equals("1"))
			{
				if (ContourDetectionFrame.mainDetectPanel.bSingleDecisionLanban)
				{
					clwklb = "0";
				}
				else
				{
					clwklb = "1";
				}
			}
		}
		
		//����������
		strfile.append("DetFinishType = F\r\n");
		//��⿪ʼʱ��
		strfile.append("DetBegTime = " + detectPanel.inTime + "\r\n");
		//������ʱ��
		strfile.append("DetEndTime = " + detectPanel.outTime + "\r\n");

		if (TrailerMeasure.equals("0"))//��ͨ��
		{
			//��������
			strfile.append("VehLength = " + detectPanel.iNvramDetectLength.iValue + "\r\n");
			//���������ж�
			strfile.append("VehLengthJudge = " + clwkl + "\r\n");
			//�������
			strfile.append("VehWidth = " + detectPanel.iNvramDetectWidth.iValue + "\r\n");
			//��������ж�
			strfile.append("VehWidthJudge = " + clwkw + "\r\n");
			//�����߶�
			strfile.append("VehHeight = " + detectPanel.iNvramDetectHeight.iValue + "\r\n");
			//�����߶��ж�
			strfile.append("VehHeightJudge = " + clwkh + "\r\n");
			if (IsDetBoxHeight.equals("1")){
			//����߶�
			strfile.append("BoxHeight = " + detectPanel.iNvramDetectLanbanHeight.iValue + "\r\n");
			//����߶��ж�
			strfile.append("BoxHeightJudge = " + clwklb + "\r\n");
			}
//			//���᳤��
//			strfile.append("CompartmentLength = \r\n");
//			//���᳤���ж�
//			strfile.append("CompartmentLengthJudge = \r\n");

			//			//�������
			//			strfile.append("[WheelbaseDetResult]\r\n");
			//			//����
			//			strfile.append("WheelbaseNumber="+detectPanel.jSettingLabelTextFieldDetectXZJ.getTextFieldText()+"\r\n");
			//			//�����
			//			strfile.append("Pinbase="+detectPanel.jSettingLabelTextFieldDetectZ.length+"\r\n");
			//			for(int i=0;i<detectPanel.jSettingLabelTextFieldDetectZ.length;i++){
			//			//�˾�1
			//			strfile.append("Wheelbase"+(i+1)+"="+detectPanel.jSettingLabelTextFieldDetectZ[i]+"\r\n");
			//			}
		}
		if (TrailerMeasure.equals("1"))//ǣ��������+�ҳ�
		{
			//��������
			strfile.append("VehLength = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectLength.iValue + "\r\n");
			//���������ж�
			strfile.append("VehLengthJudge = " + qclwkl + "\r\n");
			//�������
			strfile.append("VehWidth = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectWidth.iValue + "\r\n");
			//��������ж�
			strfile.append("VehWidthJudge = " + qclwkw + "\r\n");
			//�����߶�
			strfile.append("VehHeight = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectHeight.iValue + "\r\n");
			//�����߶��ж� 
			strfile.append("VehHeightJudge = " + qclwkh + "\r\n");
			if (IsDetBoxHeight.equals("1")){
				//����߶�
				strfile.append("BoxHeight = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectLanbanHeight.iValue + "\r\n");
				//����߶��ж�
				strfile.append("BoxHeightJudge = " + qclwklb + "\r\n");
			}
			//���᳤��
			//			strfile.append("CompartmentLength = \r\n");
			//			//���᳤���ж�
			//			strfile.append("CompartmentLengthJudge = \r\n");

			//�ҳ�����
			strfile.append("TrailerLength = " + ContourDetectionFrame.mainDetectPanel.iNvramDetectLength.iValue + "\r\n");
			//�ҳ������ж�
			strfile.append("TrailerLengthJudge = " + clwkl + "\r\n");
			//�ҳ����
			strfile.append("TrailerWidth = " + ContourDetectionFrame.mainDetectPanel.iNvramDetectWidth.iValue + "\r\n");
			//�ҳ�����ж�
			strfile.append("TrailerWidthJudge = " + clwkw + "\r\n");
			//�ҳ��߶�
			strfile.append("TrailerHeight = " + detectPanel.iNvramDetectHeight.iValue + "\r\n");
			//�ҳ��߶��ж�
			strfile.append("TrailerHeightJudge = " + clwkh + "\r\n");
			if (IsDetTrailerBoxHeight.equals("1"))
			{
			//�ҳ�����߶�
			strfile.append("TrailerBoxHeight = " + ContourDetectionFrame.mainDetectPanel.iNvramDetectLanbanHeight.iValue + "\r\n");
			//�ҳ�����߶��ж�
			strfile.append("TrailerBoxHeightJudge = " + clwklb + "\r\n\r\n");
			}
		}
		if (TrailerMeasure.equals("2"))//ǣ��������
		{
			//��������
			strfile.append("VehLength = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectLength.iValue + "\r\n");
			//���������ж�
			strfile.append("VehLengthJudge = " + qclwkl + "\r\n");
			//�������
			strfile.append("VehWidth = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectWidth.iValue + "\r\n");
			//��������ж�
			strfile.append("VehWidthJudge = " + qclwkw + "\r\n");
			//�����߶�
			strfile.append("VehHeight = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectHeight.iValue + "\r\n");
			//�����߶��ж� 
			strfile.append("VehHeightJudge = " + qclwkh + "\r\n");
			//����߶�
			if (IsDetBoxHeight.equals("1")){
				//����߶�
				strfile.append("BoxHeight = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectLanbanHeight.iValue + "\r\n");
				//����߶��ж�
				strfile.append("BoxHeightJudge = " + qclwklb + "\r\n");
			}
//			strfile.append("BoxHeight = " + ContourDetectionFrame.mainQianYingDetectPanel.iNvramDetectLanbanHeight.iValue + "\r\n");
//			//����߶��ж�
//			strfile.append("BoxHeightJudge = " + qclwklb + "\r\n");
//			//���᳤��
//			strfile.append("CompartmentLength = \r\n");
//			//���᳤���ж�
//			strfile.append("CompartmentLengthJudge = \r\n");

			//			//�������
			//			strfile.append("[WheelbaseDetResult]\r\n");
			//			//����
			//			strfile.append("Pinbase="+ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldDetectZ.length+"\r\n");
			//			for(int i=0;i<ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldDetectZ.length;i++){
			//			//�˾�1
			//			strfile.append("Wheelbase"+(i+1)+"="+ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldDetectZ[i]+"\r\n");
			//			}
		}
		if (TrailerMeasure.equals("3"))//�ҳ�
		{
			//�ҳ�����
			strfile.append("TrailerLength = " + ContourDetectionFrame.mainDetectPanel.iNvramDetectLength.iValue + "\r\n");
			//�ҳ������ж�
			strfile.append("TrailerLengthJudge = " + clwkl + "\r\n");
			//�ҳ����
			strfile.append("TrailerWidth = " + ContourDetectionFrame.mainDetectPanel.iNvramDetectWidth.iValue + "\r\n");
			//�ҳ�����ж�
			strfile.append("TrailerWidthJudge = " + clwkw + "\r\n");
			//�ҳ��߶�
			strfile.append("TrailerHeight = " + ContourDetectionFrame.mainDetectPanel.iNvramDetectHeight.iValue + "\r\n");
			//�ҳ��߶��ж�
			if (IsDetTrailerBoxHeight.equals("1"))
			{
			strfile.append("TrailerHeightJudge = " + clwkh + "\r\n");
			//�ҳ�����߶�
			strfile.append("TrailerBoxHeight = " + ContourDetectionFrame.mainDetectPanel.iNvramDetectLanbanHeight.iValue + "\r\n");
			//�ҳ�����߶��ж�
			strfile.append("TrailerBoxHeightJudge = " + clwklb + "\r\n\r\n");
			}
			//			//�������
			//			strfile.append("[WheelbaseDetResult]\r\n");
			//			//����
			//			strfile.append("Pinbase="+ContourDetectionFrame.mainDetectPanel.jSettingLabelTextFieldDetectZ.length+"\r\n");
			//			for(int i=0;i<ContourDetectionFrame.mainDetectPanel.jSettingLabelTextFieldDetectZ.length;i++){
			//			//�˾�1
			//			strfile.append("Wheelbase"+(i+1)+"="+ContourDetectionFrame.mainDetectPanel.jSettingLabelTextFieldDetectZ[i]+"\r\n");
			//			}
		}

		File wfile = new File("/EndCTDet.ini");
		File rfile = new File("/BegCTDet.ini");
		if (rfile.exists())
		{
			rfile.delete();
		}
		if (wfile.exists())
		{
			wfile.delete();
			try
			{
				wfile.createNewFile();
			}
			catch (IOException e)
			{

				LMSLog.exceptionDialog("�ļ��쳣", e);
			}
		}
		else
		{
			try
			{
				wfile.createNewFile();
//				LMSLog.warningDialog("�ļ�������", "�������ļ�");
			}
			catch (IOException e)
			{

				LMSLog.exceptionDialog("�ļ��쳣", e);
			}
		}

		FileWriter filewriter;
		try
		{
			filewriter = new FileWriter(wfile);
			BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
			bufferedWriter.write(strfile.toString());
			bufferedWriter.flush();
			bufferedWriter.close();
			if(TrailerMeasure.equals("0")||TrailerMeasure.equals("2"))
			{
			BufferedImage down = ImageIO.read(new File("./image/down.png"));
			ImageIO.write(down, "bmp", new File("/VehRoof.bmp"));
			BufferedImage right = ImageIO.read(new File("./image/right.png"));
			ImageIO.write(right, "bmp", new File("/VehBody.bmp"));
			}
			if(TrailerMeasure.equals("1"))
			{
			BufferedImage down = ImageIO.read(new File("./image/down.png"));
			ImageIO.write(down, "bmp", new File("/VehRoof_T.bmp"));
			ImageIO.write(down, "bmp", new File("/VehRoof.bmp"));
			BufferedImage right = ImageIO.read(new File("./image/right.png"));
			ImageIO.write(right, "bmp", new File("/VehBody_T.bmp"));
			ImageIO.write(right, "bmp", new File("/VehBody.bmp"));
			}
			if(TrailerMeasure.equals("3"))
			{
			BufferedImage down = ImageIO.read(new File("./image/down.png"));
			ImageIO.write(down, "bmp", new File("/VehRoof_T.bmp"));
			BufferedImage right = ImageIO.read(new File("./image/right.png"));
			ImageIO.write(right, "bmp", new File("/VehBody_T.bmp"));
			}
			
		}
		catch (IOException e)
		{

			LMSLog.exceptionDialog("�ļ��쳣", e);
			return false;
		}
		return true;
	}

	@Override
	public void parseDetectionList(ArrayList<String> lineList)
	{
		DetectionVehicle.bAutoRefresh = true;
		DetectionVehicle.detectionVehicleList.clear();
		DetectionVehicle detectionVehicle = new DetectionVehicle();
		int k=0;
		while (k<lineList.size())
		{
			String str = lineList.get(k);

			int index = str.indexOf("=");
			if (str.contains("TrailerMeasure"))
			{
				TrailerMeasure = str.substring(index + 1).trim();
				break;
			}
			k++;
		}
		if("0".equals(TrailerMeasure)){
		for (int i = 0; i < lineList.size(); i++)
		{
			String str = lineList.get(i);

			int index = str.indexOf("=");
			
			if (str.contains("PlateNumber"))
			{
				
					detectionVehicle.sVehicleNum = str.substring(index + 1).trim();				
					//ContourDetectionFrame.mainQianYingDetectPanel.vehicleNumLabelTextField.setTextFieldText(str.substring(index + 1).trim());
				
				LMSLog.d(DEBUG_TAG, "���ƺ���=" + detectionVehicle.sVehicleNum);

				if (!"".equals(detectionVehicle.sVehicleNum))
					detectionVehicle.sNewOrOld = "���ó�";
				else
					detectionVehicle.sNewOrOld = "ע�ᳵ";
				LMSLog.d(DEBUG_TAG, "�Ƿ��³�=" + CarType);
			}

			if (str.contains("BoxHeightStandard"))
			{
				detectionVehicle.sOriginalLanBanHeight = str.substring(index + 1).trim();

				LMSLog.d(DEBUG_TAG, "����߶ȱ�׼ֵ=" + detectionVehicle.sOriginalLanBanHeight);

			}
			if (str.contains("VehType"))
			{
				
				detectionVehicle.sVehicleType = str.substring(index + 1).trim();
				
				LMSLog.d(DEBUG_TAG, "��������=" + detectionVehicle.sVehicleType);

			}

			if (str.contains("VehLengthStandard"))
			{
				detectionVehicle.sOriginalCarLength = str.substring(index + 1).trim();
								
				LMSLog.d(DEBUG_TAG, "�������ȱ�׼ֵ=" + detectionVehicle.sOriginalCarLength);

			}
			if (str.contains("VehWidthStandard"))
			{
				
				detectionVehicle.sOriginalCarWidth = str.substring(index + 1).trim();
								
				LMSLog.d(DEBUG_TAG, "������ȱ�׼ֵ=" + detectionVehicle.sOriginalCarWidth);

			}
			if (str.contains("VehHeightStandard"))
			{				
				detectionVehicle.sOriginalCarHeight = str.substring(index + 1).trim();
								
				LMSLog.d(DEBUG_TAG, "�����߶ȱ�׼ֵ=" + detectionVehicle.sOriginalCarHeight);

			}
			if (str.contains("IsDetTrailerBoxHeight"))
			{
				//�Ƿ���ҳ�����
				IsDetTrailerBoxHeight = str.substring(index + 1).trim();				

			}
			if (str.contains("IsDetBoxHeight"))
			{
				//�Ƿ�����������
				IsDetBoxHeight = str.substring(index + 1).trim();				

			}
		}
		}
		//ǣ����+�ҳ�
		if("1".equals(TrailerMeasure)||"2".equals(TrailerMeasure)||"3".equals(TrailerMeasure)){
			for (int i = 0; i < lineList.size(); i++)
			{
				String str = lineList.get(i);

				int index = str.indexOf("=");
				
				if (str.contains("PlateNumber"))
				{
					
						detectionVehicle.sVehicleNum = str.substring(index + 1).trim();				
						ContourDetectionFrame.mainQianYingDetectPanel.vehicleNumLabelTextField.setTextFieldText(str.substring(index + 1).trim());
					
					LMSLog.d(DEBUG_TAG, "���ƺ���=" + str.substring(index + 1).trim());

					if (!"".equals(detectionVehicle.sVehicleNum))
						detectionVehicle.sNewOrOld = "���ó�";
					else
						detectionVehicle.sNewOrOld = "ע�ᳵ";
					LMSLog.d(DEBUG_TAG, "�Ƿ��³�=" + detectionVehicle.sNewOrOld);
				}

				if (str.contains("BoxHeightStandard")&&!str.contains("TrailerBoxHeightStandard"))
				{
					//detectionVehicle.sOriginalLanBanHeight = str.substring(index + 1).trim();
					ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalLanbanHeight.setTextFieldText(str.substring(index + 1).trim());
					LMSLog.d(DEBUG_TAG, "ǣ��������߶ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("TrailerBoxHeightStandard"))
				{
					detectionVehicle.sOriginalLanBanHeight = str.substring(index + 1).trim();
					//ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalLanbanHeight.setTextFieldText(str.substring(index + 1).trim());
					LMSLog.d(DEBUG_TAG, "�ҳ�����߶ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("VehType"))
				{
					
					detectionVehicle.sVehicleType = str.substring(index + 1).trim();
					ContourDetectionFrame.mainQianYingDetectPanel.vehicleTypeLabelTextField.setTextFieldText(str.substring(index + 1).trim());
					LMSLog.d(DEBUG_TAG, "��������=" + str.substring(index + 1).trim());

				}

				if (str.contains("VehLengthStandard"))
				{
					//detectionVehicle.sOriginalCarLength = str.substring(index + 1).trim();
					ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalLength.setTextFieldText(str.substring(index + 1).trim());				
					LMSLog.d(DEBUG_TAG, "ǣ�������ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("TrailerLengthStandard"))
				{
					detectionVehicle.sOriginalCarLength = str.substring(index + 1).trim();
					//ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalLength.setTextFieldText(str.substring(index + 1).trim());				
					LMSLog.d(DEBUG_TAG, "�ҳ����ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("VehWidthStandard"))
				{
					
					//detectionVehicle.sOriginalCarWidth = str.substring(index + 1).trim();
					ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalWidth.setTextFieldText(str.substring(index + 1).trim());								
					LMSLog.d(DEBUG_TAG, "ǣ������ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("TrailerWidthStandard"))
				{
					
					detectionVehicle.sOriginalCarWidth = str.substring(index + 1).trim();
					//ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalWidth.setTextFieldText(str.substring(index + 1).trim());								
					LMSLog.d(DEBUG_TAG, "�ҳ���ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("VehHeightStandard"))
				{				
					//detectionVehicle.sOriginalCarHeight = str.substring(index + 1).trim();
					ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalHeight.setTextFieldText(str.substring(index + 1).trim());				
					LMSLog.d(DEBUG_TAG, "ǣ�����߶ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("TrailerHeightStandard"))
				{				
					detectionVehicle.sOriginalCarHeight = str.substring(index + 1).trim();
					//ContourDetectionFrame.mainQianYingDetectPanel.jSettingLabelTextFieldOriginalHeight.setTextFieldText(str.substring(index + 1).trim());				
					LMSLog.d(DEBUG_TAG, "�ҳ��߶ȱ�׼ֵ=" + str.substring(index + 1).trim());

				}
				if (str.contains("IsDetTrailerBoxHeight"))
				{
					//�Ƿ���ҳ�����
					IsDetTrailerBoxHeight = str.substring(index + 1).trim();				

				}
				if (str.contains("IsDetBoxHeight"))
				{
					//�Ƿ�����������
					IsDetBoxHeight = str.substring(index + 1).trim();				

				}
			}
			}
				
		DetectionVehicle.detectionVehicleList.add(detectionVehicle);
	}

	@Override
	public void parseDetectionList(String xmlStr)
	{

	}

	@Override
	public void beginDetection(ContourDetectionTabPanelMain detectPanel, boolean bValue)
	{
		if (bValue == true)
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			detectPanel.inTime = df.format(new Date());
		}
	}

	@Override
	public void carInSignal(ContourDetectionTabPanelMain detectPanel, boolean bValue)
	{
		if (bValue == false)
		{

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			detectPanel.outTime = df.format(new Date());

		}
	}

}
