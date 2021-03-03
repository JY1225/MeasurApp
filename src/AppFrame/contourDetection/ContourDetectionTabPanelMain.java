package AppFrame.contourDetection;

import http.WebService.WebService;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.PacketTooBigException;

import printer.PrintUIComponent;

import AppBase.appBase.AppConst;
import AppBase.appBase.CarTypeAdapter;
import AppFrame.contourDetection.ContourDetectionDataBaseConst.MainPanelCarType;
import AppFrame.widgets.DetectionListComboBox;
import AppFrame.widgets.JButtonBoolean;
import AppFrame.widgets.JCheckBoxNvram;
import AppFrame.widgets.JLabelComboBox;
import AppFrame.widgets.JLabelRadioButtonGroup;
import AppFrame.widgets.JSettingLabelTextField;
import AppFrame.widgets.OperatorComboBox;
import CustomerProtocol.CustomerProtocol;
import CustomerProtocol.CustomerProtocol_DB_HF_SRF;
import CustomerProtocol.CustomerProtocol_DB_JSYY;
import CustomerProtocol.CustomerProtocol_WS_SDGC;
import CustomerProtocol.DetectionVehicle;
import CustomerProtocol.ZJDLL;
import ParserXml.ParserXML;
import database.DataBaseConst;
import database.ImageUtil;

import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.NvramType;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public abstract class ContourDetectionTabPanelMain{
	private final static String DEBUG_TAG="ContourMainPanel";

	public abstract JSplitPane createTab();
	public abstract JPanel createPanelTab();
	
	public JFrame frame;
	ContourDetectionDataBaseConst.MainPanelType panelType;
	public ContourDetectionDataBaseConst.MainPanelCarType carType;
	ContourDetectionTabPanelMain panelMain = this;
		
	public JButtonBoolean beginDetectionButton;
	public JLabel detectParameterLabel;
	public JLabelRadioButtonGroup carTypeRadioButtonGroup;
	public JCheckBoxNvram lanBanDetectCheckBox;
	public JCheckBoxNvram filterCheLanCheckBox;
	public JCheckBoxNvram filterEndGasCheckBox;
	public JLabelComboBox lengthFilterLabelComboBox;
	public JLabelComboBox widthFilterLabelComboBox;
	public JLabelComboBox heightFilterLabelComboBox;
	
	DetectionListComboBox detectionListComboBox;
	
	public String serialNumLabelStr = "检测流水号";
	public JSettingLabelTextField serialNumLabelTextField;
	public String vehicleNumLabelStr = "号牌号码";
	public JSettingLabelTextField vehicleNumLabelTextField;
	public String vehicleNumTypeLabelStr = "号牌种类";
	public JSettingLabelTextField vehicleNumTypeLabelTextField;
	public String vehicleTypeLabelStr = "车辆类型";
	public JSettingLabelTextField vehicleTypeLabelTextField;
//	public JAutoCompleteComboBox vehicleTypeComboBox;
	public String vehicleBrandLabelStr = "品牌型号";
	public JSettingLabelTextField vehicleBrandLabelTextField;
	public String vehicleIDLabelStr = "车辆识别代码";
	public JSettingLabelTextField vehicleIDLabelTextField;
	public String vehicleMotorIDLabelStr = "发动机号码";
	public JSettingLabelTextField vehicleMotorIDLabelTextField;

	public OperatorComboBox operatorComboBox;
	public String nowTime;
	
    //=================================================================================

    public ContourImageLabel viewImageLabel[] = new ContourImageLabel[5];
    
    private int ID;
    
    public final int MAX_Z = 5;
	
    //=================================================================================
    //1:注册车(判定标准1%,或±50mm)
    //2:在用车(判定标准2%,或±100mm)
    JButtonBoolean newCarButton;
    public String carstandard="判定标准";
	public NvramType bNvramNewCar = new NvramType("nvram_bNewCar",NvramType.Type.BOOLEAN_TYPE);
	public float decisionStandard = 1;  
	public float compareValueWHL = 1.0f;
	public int compareAbsValueWHL = 50;
	public float compareValueLanban = 1.0f;
	public int compareAbsValueLanban = 50;
	public float compareValueZhouJu = 1.0f;
	public int compareAbsValueZhouJu = 50;

	public JSettingLabelTextField detectVarLabelTextField;

    //=================================================================================
	public String originalCarStr = "原车数据";
	public String detectCarStr = "测量数据";

	public String absoluteCarStr = "绝对误差";
	public int absoluteCarLength = 0;
	public int absoluteCarWidth = 0;
	public int absoluteCarHeight = 0;
	public int absoluteCarLanbanHeight = 0;
	int absoluteCarXZJ = 0;
	int absoluteCarZ[] = new int[MAX_Z];
	public int absoluteCarZJ;

	public String compareCarStr = "相对误差";
	float compareCarLength = 0;
	float compareCarWidth = 0;
	float compareCarHeight = 0;
	float compareCarLanbanHeight = 0;
	float compareCarXZJ = 0;
	float compareCarZ[] = new float[MAX_Z];
	float compareCarZJ;

	public String carLengthStr = "整车长";
	public String carWidthStr = "整车宽";
	public String carHeightStr = "整车高";
	public String carLanbanStr = "栏板高";
	public String carXZJStr = "銷轴距";
	public String carZJStr = "轴距";
//	public static String carLanbanStr = "栏板高";
//	public static String carLanbanStr = "栏板高";
	
	public JTextField zNumTextField = new JTextField();
    
	public JSettingLabelTextField jSettingLabelTextFieldOriginalLength;
	public JSettingLabelTextField jSettingLabelTextFieldOriginalWidth;
	public JSettingLabelTextField jSettingLabelTextFieldOriginalHeight;
	public JSettingLabelTextField jSettingLabelTextFieldOriginalLanbanHeight;
	public JSettingLabelTextField jSettingLabelTextFieldOriginalXZJ;
	public JSettingLabelTextField jSettingLabelTextFieldOriginalZ[] = new JSettingLabelTextField[MAX_Z];
	public JSettingLabelTextField jSettingLabelTextFieldOriginalZJ;

	//==============================================================================
	protected NvramType iNvramOriginalLength = new NvramType("nvram_OriginalLength",NvramType.Type.INTEGER_TYPE);
	protected NvramType iNvramOriginalWidth = new NvramType("nvram_OriginalWidth",NvramType.Type.INTEGER_TYPE);
	protected NvramType iNvramOriginalHeight = new NvramType("nvram_OriginalHeight",NvramType.Type.INTEGER_TYPE);
	protected NvramType iNvramOriginalLanbanHeight = new NvramType("nvram_OriginalLanbanHeight",NvramType.Type.INTEGER_TYPE);
	protected NvramType iNvramOriginalXZJ = new NvramType("nvram_OriginalXZJ",NvramType.Type.INTEGER_TYPE);
	protected NvramType iNvramOriginalZ[] = new NvramType[MAX_Z];//("nvram_DetectLength",NvramType.Type.INTEGER_TYPE);
	protected NvramType iNvramOriginalZJ = new NvramType("nvram_OriginalZJ",NvramType.Type.INTEGER_TYPE);

	//==============================================================================
	int zNum = 0;
	public NvramType iNvramDetectLength = new NvramType("nvram_DetectLength",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectWidth = new NvramType("nvram_DetectWidth",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectHeight = new NvramType("nvram_DetectHeight",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectLanbanHeight = new NvramType("nvram_DetectLanbanHeight",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectXZJ = new NvramType("nvram_DetectXZJ",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectZ[] = new NvramType[MAX_Z];//("nvram_DetectLength",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectZJ = new NvramType("nvram_DetectZJ",NvramType.Type.INTEGER_TYPE);

	//==============================================================================
	int iSaveDetectLength = 0;
	int iSaveDetectWidth = 0;
	int iSaveDetectHeight = 0;
	int iSaveDetectLanbanHeight = 0;
	int iSaveDetectZJ = 0;

	JButtonBoolean humanAdjustButtonBoolean;
	NvramType bNvramHumanAdjust = new NvramType("nvram_bNvramHumanAdjust",NvramType.Type.BOOLEAN_TYPE);
	NvramType sNvramHumanAdjustReason = new NvramType("nvram_sNvramHumanAdjustReason",NvramType.Type.STRING_TYPE);

	//==============================================================================
	public NvramType iNvramDetectLength_RearViewMirror = new NvramType("nvram_DetectLengthWithRearViewMirror",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectWidth_RearViewMirror = new NvramType("nvram_DetectWidthWithRearViewMirror",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectHeight_RearViewMirror = new NvramType("nvram_DetectHeightWithRearViewMirror",NvramType.Type.INTEGER_TYPE);

	public NvramType iNvramDetectLength_FrontMirror = new NvramType("nvram_DetectLengthWithFrontMirror",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectWidth_FrontMirror = new NvramType("nvram_DetectWithWithFrontMirror",NvramType.Type.INTEGER_TYPE);
	public NvramType iNvramDetectHeight_FrontMirror = new NvramType("nvram_DetectHeightWithFrontMirror",NvramType.Type.INTEGER_TYPE);

	//==============================================================================
	public JLabel detectLengthLabel;
	public JLabel detectWidthLabel;
	public JLabel detectHeightLabel;
	public JLabel detectLanbanHeightLabel;
	public JLabel detectXZJLabel;
	public JLabel detectZLabel[] = new JLabel[MAX_Z];
	public JLabel detectZJLabel;

	public JSettingLabelTextField jSettingLabelTextFieldDetectLength;
	public JSettingLabelTextField jSettingLabelTextFieldDetectWidth;
	public JSettingLabelTextField jSettingLabelTextFieldDetectHeight;
	public JSettingLabelTextField jSettingLabelTextFieldDetectLanbanHeight;
	public JSettingLabelTextField jSettingLabelTextFieldDetectXZJ;
	public JSettingLabelTextField jSettingLabelTextFieldDetectZ[] = new JSettingLabelTextField[MAX_Z];
	public JSettingLabelTextField jSettingLabelTextFieldDetectZJ;
	public JTextArea beiZhuLabel = new JTextArea();
	public JTextArea beiZhuTextArea = new JTextArea();

	//==============================================================================    
	public JSettingLabelTextField absoluteDifLengthTextField;
	public JSettingLabelTextField absoluteDifWidthTextField;
	public JSettingLabelTextField absoluteDifHeightTextField;
	public JSettingLabelTextField absoluteDifLanbanHeightTextField;
	public JSettingLabelTextField absoluteDifXZJTextField;
	public JSettingLabelTextField absoluteDifZTextField[] = new JSettingLabelTextField[MAX_Z];
	public JSettingLabelTextField absoluteDifZJTextField;

	public JSettingLabelTextField compareDifLengthTextField;
	public JSettingLabelTextField compareDifWidthTextField;
	public JSettingLabelTextField compareDifHeightTextField;
	public JSettingLabelTextField compareDifLanbanHeightTextField;
	public JSettingLabelTextField compareDifXZJTextField;
	public JSettingLabelTextField compareDifZTextField[] = new JSettingLabelTextField[MAX_Z];
	public JSettingLabelTextField compareDifZJTextField;

	public String singleDecisionStr = "单项判定";
	public JSettingLabelTextField singleDecisionLengthTextField;
	public JSettingLabelTextField singleDecisionWidthTextField;
	public JSettingLabelTextField singleDecisionHeightTextField;
	public JSettingLabelTextField singleDecisionLanbanHeightLabelTextField;
//    JTextField singleDecisionZTextField[] = new JTextField[MAX_Z];
	public JSettingLabelTextField singleDecisionZJLabelTextField;;


	public boolean bSingleDecisionLength;
	public boolean bSingleDecisionWidth;
	public boolean bSingleDecisionHeight;
	public boolean bSingleDecisionLanban;
	public boolean bSingleDecisionZJ;
	public boolean bDecisionWHL;
	public boolean bDecision;
	
	public JLabel decisionLabel;
	public JSettingLabelTextField decisionTextField;
	
	public String inTime;//进车时间
	public String outTime;//出车时间
	
    //=================================================================================
    public String imageFileName[] = new String[LMSConstValue.MAIN_IMAGE_NUM];
    JButton deleteButton;
    JButton saveButton;

    JPanel mainDetectSettingPanel = new JPanel();
    public JLabel companyLable;
    
    public ContourDetectionTabPanelMain(JFrame frame,ContourDetectionDataBaseConst.MainPanelType type,MainPanelCarType _carType)
    {
    	this.frame = frame;
		panelType = type;
		carType = _carType;
		
		//======================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		for(int i=0;i<MAX_Z;i++)
		{
			iNvramOriginalZ[i] = new NvramType("nvram_OriginalZ"+i,NvramType.Type.INTEGER_TYPE);
			iNvramDetectZ[i] = new NvramType("nvram_DetectZ"+i,NvramType.Type.INTEGER_TYPE);
		}		
    }
    
	final int W_H_L_VALUE_FONT = 30;
	final int IMAGE_FONT = 16;
	
	public PrintUIComponent print = new PrintUIComponent(panelMain);

	public JPanel createMainDetectSettingPanel() {	
		//设置布局
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100,100,800};
		gridBagLayout.rowHeights = new int[]{200};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0,1.0,1.0};
		mainDetectSettingPanel.setLayout(gridBagLayout);
		
		//==========================================================
		int gridX = 0,gridY = 0;
		
		GridBagConstraints gbc_printPreviewButton = new GridBagConstraints();
		gbc_printPreviewButton.fill = GridBagConstraints.BOTH;
		gbc_printPreviewButton.gridwidth = 1;
		gbc_printPreviewButton.insets = new Insets(0, 0, 5, 5);
		gbc_printPreviewButton.gridx = gridX;
		gbc_printPreviewButton.gridy = gridY;
		mainDetectSettingPanel.add(print.printPreviewButton,gbc_printPreviewButton);
		gridX++;
		
		GridBagConstraints gbc_printButton = new GridBagConstraints();
		gbc_printButton.fill = GridBagConstraints.BOTH;
		gbc_printButton.gridwidth = 1;
		gbc_printButton.insets = new Insets(0, 0, 5, 5);
		gbc_printButton.gridx = gridX;
		gbc_printButton.gridy = gridY;
		mainDetectSettingPanel.add(print.printButton,gbc_printButton);
		gridX++;
		
		if(panelType == ContourDetectionDataBaseConst.MainPanelType.SEARCH_SINGLE_RESULT)
		{
			deleteButton = new JButton("删除");
			deleteButton.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
			GridBagConstraints gbc_deleteButton = new GridBagConstraints();
			gbc_deleteButton.fill = GridBagConstraints.BOTH;
			gbc_deleteButton.gridwidth = 1;
			gbc_deleteButton.insets = new Insets(0, 0, 5, 5);
			gbc_deleteButton.gridx = gridX;
			gbc_deleteButton.gridy = gridY;
			mainDetectSettingPanel.add(deleteButton,gbc_deleteButton);
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
					
					ContourDetectionTabPanelSearch.dataBaseSearch.deleteQueryID(
						ContourDetectionTabPanelSearch.searchResultPanel,
						ID,ContourDetectionDataBaseConst.TABLE_NAME);					
				}
			});
			gridX++;
		}
		else
		{
			if(LMSConstValue.bDynamicDetect == false)
			{
				JButton getLengthButton = new JButton("取长");
				getLengthButton.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
				GridBagConstraints gbc_getLengthButton = new GridBagConstraints();
				gbc_getLengthButton.fill = GridBagConstraints.BOTH;
				gbc_getLengthButton.gridwidth = 1;
				gbc_getLengthButton.insets = new Insets(0, 0, 5, 5);
				gbc_getLengthButton.gridx = gridX;
				gbc_getLengthButton.gridy = gridY;
				mainDetectSettingPanel.add(getLengthButton,gbc_getLengthButton);
				getLengthButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
	
					}
				});
				gridX++;
			}
		
			if(AppConst.companyName.equals("KTS"))
			{
				companyLable = new JLabel("中山市卡特世检测设备科技有限公司，专业生产汽车、摩托车检测设备、尾气环保.www.carte.com.cn");
				companyLable.setFont(new Font("宋体",Font.BOLD, 18));
				GridBagConstraints gbc_companyLable = new GridBagConstraints();
				gbc_companyLable.fill = GridBagConstraints.BOTH;
				gbc_companyLable.gridwidth = 1;
				gbc_companyLable.insets = new Insets(0, 0, 5, 5);
				gbc_companyLable.gridx = gridX;
				gbc_companyLable.gridy = gridY;
				mainDetectSettingPanel.add(companyLable,gbc_companyLable);
			}
			else if(AppConst.companyName.equals("LHKJ"))
			{
			    JLabel companyImageLabel = new JLabel();
				GridBagConstraints gbc_companyImageLabel = new GridBagConstraints();
				gbc_companyImageLabel.fill = GridBagConstraints.BOTH;
				gbc_companyImageLabel.gridwidth = 1;
				gbc_companyImageLabel.insets = new Insets(0, 0, 5, 5);
				gbc_companyImageLabel.gridx = gridX;
				gbc_companyImageLabel.gridy = gridY;
				mainDetectSettingPanel.add(companyImageLabel,gbc_companyImageLabel);
				gridX++;
				
				
			    ImageIcon iconImage = new ImageIcon(getClass().getResource("/LHKJ.jpg"));
			    iconImage.setImage(iconImage.getImage().getScaledInstance(300,100,Image.SCALE_AREA_AVERAGING)); 
			    companyImageLabel.setIcon(iconImage); 
				
				companyLable = new JLabel("黑龙江利华科技开发有限公司");
				companyLable.setFont(new Font("宋体",Font.BOLD, 22));
				GridBagConstraints gbc_companyLable = new GridBagConstraints();
				gbc_companyLable.fill = GridBagConstraints.BOTH;
				gbc_companyLable.gridwidth = 1;
				gbc_companyLable.insets = new Insets(0, 0, 5, 5);
				gbc_companyLable.gridx = gridX;
				gbc_companyLable.gridy = gridY;
				mainDetectSettingPanel.add(companyLable,gbc_companyLable);
			}
			if(AppConst.companyName.equals("NJXSS"))
			{
				companyLable = new JLabel("南京新士尚:"+LMSConstValue.sNvramUserMsg.sValue, JLabel.CENTER);
				companyLable.setFont(new Font("宋体",Font.BOLD, 18));
				GridBagConstraints gbc_companyLable = new GridBagConstraints();
				gbc_companyLable.fill = GridBagConstraints.BOTH;
				gbc_companyLable.gridwidth = 1;
				gbc_companyLable.insets = new Insets(0, 0, 5, 5);
				gbc_companyLable.gridx = gridX;
				gbc_companyLable.gridy = gridY;
				mainDetectSettingPanel.add(companyLable,gbc_companyLable);
			}
			else
			{
				companyLable = new JLabel(LMSConstValue.sNvramUserMsg.sValue, JLabel.CENTER);
				companyLable.setFont(new Font("宋体",Font.BOLD, LMSConstValue.iNvramUserMsgFontSize.iValue));
				GridBagConstraints gbc_companyLable = new GridBagConstraints();
				gbc_companyLable.fill = GridBagConstraints.BOTH;
				gbc_companyLable.gridwidth = 1;
				gbc_companyLable.insets = new Insets(0, 0, 5, 5);
				gbc_companyLable.gridx = gridX;
				gbc_companyLable.gridy = gridY;
				mainDetectSettingPanel.add(companyLable,gbc_companyLable);
				
				/*
				saveButton = new JButton("保存");
				saveButton.setFont(new Font("宋体",Font.BOLD, W_H_L_VALUE_FONT));
				GridBagConstraints gbc_saveButton = new GridBagConstraints();
				gbc_saveButton.fill = GridBagConstraints.BOTH;
				gbc_saveButton.gridwidth = 1;
				gbc_saveButton.insets = new Insets(0, 0, 5, 5);
				gbc_saveButton.gridx = gridX;
				gbc_saveButton.gridy = gridY;
				mainDetectSettingPanel.add(saveButton,gbc_saveButton);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(panelType == ContourDetectionDataBaseConst.MainPanelType.SEARCH_SINGLE_RESULT)
						{
							jdbc_mysql_update_id(ID);
							
							frame.dispose();
		
							ContourDetectionTabPanelSearch.dataBaseSearch.queryLast(
								ContourDetectionTabPanelSearch.searchResultPanel,
								ContourDetectionDataBaseConst.TABLE_NAME);;
						}
						else
						{
							jdbc_mysql_add();		
							
					        saveButton.setText("保存成功");
						}				
					}
				});
				gridX++;
				*/
			}
		}

		return mainDetectSettingPanel;
	};
	
	void setLEDMessage()
	{
		if(!LMSConstValue.getSensorType(LMSConstValue.LED_ID_START).equals(LMSConstValue.SensorType.UNKNOW)
			&&LMSConstValue.bNvramLedLocalSetting.bValue == true)
		{
			LMSLog.d(DEBUG_TAG,"setLEDMessage----------------");
			
			LMSConstValue.sNvramLedCurrentMessage.sValue = "";	
			if(bQianYing == true)
			{
				if(ContourDetectionFrame.mainQianYingDetectPanel != null 
					&& ContourDetectionFrame.mainQianYingDetectPanel.vehicleNumLabelTextField != null
					&& !ContourDetectionFrame.mainQianYingDetectPanel.vehicleNumLabelTextField.getTextFieldText().equals(""))
				{
					LMSConstValue.sNvramLedCurrentMessage.sValue = ("牵引车:"+vehicleNumLabelTextField.getTextFieldText());
				}
				
				if(ContourDetectionFrame.mainQianYingDetectPanel.bDecision == true)
					LMSConstValue.sNvramLedCurrentMessage.sValue += " 判定:合格";
				else
					LMSConstValue.sNvramLedCurrentMessage.sValue += " 判定:不合格";
	
				String strDecisionLength = "×";//"×：不合格";		
				if(bSingleDecisionLength)
					strDecisionLength = "Ｏ";//"Ｏ：合格";
				String strDecisionWidth = "×";//"×：不合格";		
				if(bSingleDecisionWidth)
					strDecisionWidth = "Ｏ";//"Ｏ：合格";
				String strDecisionHeight = "×";//"×：不合格";		
				if(bSingleDecisionHeight)
					strDecisionHeight = "Ｏ";//"Ｏ：合格";
	
				LMSConstValue.sNvramLedCurrentMessage.sValue += 
					" 牵引车长:"+iNvramDetectLength.iValue+" "+strDecisionLength
					+" 牵引车宽:"+iNvramDetectWidth.iValue+" "+strDecisionWidth
					+" 牵引车高:"+iNvramDetectHeight.iValue+" "+strDecisionHeight;			
			}
	
			if(bGuaChe == false)
			{
				if(ContourDetectionFrame.mainDetectPanel != null 
					&& ContourDetectionFrame.mainDetectPanel.vehicleNumLabelTextField != null
					&& !ContourDetectionFrame.mainDetectPanel.vehicleNumLabelTextField.getTextFieldText().equals(""))
				{
					LMSConstValue.sNvramLedCurrentMessage.sValue += ContourDetectionFrame.mainDetectPanel.vehicleNumLabelTextField.getTextFieldText();
				}
				
				if(bDecision == true)
					LMSConstValue.sNvramLedCurrentMessage.sValue += " 判定:合格";
				else
					LMSConstValue.sNvramLedCurrentMessage.sValue += " 判定:不合格";
	
				String strDecisionLength = "×";//"×：不合格";		
				if(bSingleDecisionLength)
					strDecisionLength = "Ｏ";//"Ｏ：合格";
				String strDecisionWidth = "×";//"×：不合格";		
				if(bSingleDecisionWidth)
					strDecisionWidth = "Ｏ";//"Ｏ：合格";
				String strDecisionHeight = "×";//"×：不合格";		
				if(bSingleDecisionHeight)
					strDecisionHeight = "Ｏ";//"Ｏ：合格";
	
				LMSConstValue.sNvramLedCurrentMessage.sValue += 
					" 长:"+iNvramDetectLength.iValue+" "+strDecisionLength
					+" 宽:"+iNvramDetectWidth.iValue+" "+strDecisionWidth
					+" 高:"+iNvramDetectHeight.iValue+" "+strDecisionHeight;
			}
			else
			{
				if(ContourDetectionFrame.mainDetectPanel != null 
					&& ContourDetectionFrame.mainDetectPanel.vehicleNumLabelTextField != null
					&& !ContourDetectionFrame.mainDetectPanel.vehicleNumLabelTextField.getTextFieldText().equals(""))
				{
					LMSConstValue.sNvramLedCurrentMessage.sValue += (" 挂车:"+(ContourDetectionFrame.mainDetectPanel.vehicleNumLabelTextField.getTextFieldText()));
				}
				
				if(bDecision == true)
					LMSConstValue.sNvramLedCurrentMessage.sValue += " 判定:合格";
				else
					LMSConstValue.sNvramLedCurrentMessage.sValue += " 判定:不合格";
	
				String strDecisionLength = "×";//"×：不合格";		
				if(bSingleDecisionLength)
					strDecisionLength = "Ｏ";//"Ｏ：合格";
				String strDecisionWidth = "×";//"×：不合格";		
				if(bSingleDecisionWidth)
					strDecisionWidth = "Ｏ";//"Ｏ：合格";
				String strDecisionHeight = "×";//"×：不合格";		
				if(bSingleDecisionHeight)
					strDecisionHeight = "Ｏ";//"Ｏ：合格";
				
				LMSConstValue.sNvramLedCurrentMessage.sValue += 
					" 挂车长:"+iNvramDetectLength.iValue+" "+strDecisionLength
					+" 挂车宽:"+iNvramDetectWidth.iValue+" "+strDecisionWidth
					+" 挂车高:"+iNvramDetectHeight.iValue+" "+strDecisionHeight;
			}
			HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM, LMSConstValue.sNvramLedCurrentMessage.nvramStr);
			eventExtra.put(LMSConstValue.INTENT_EXTRA_SETTING_SAVE, false);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.SETTING_TRANSFER_INTENT, eventExtra);
		}
	}
	
	void calcCarDecision()
	{
		if(bNvramNewCar.bValue == true)
		{
			decisionStandard = 1;

			compareValueWHL = 1.0f;
			compareAbsValueWHL = 50;
			
			compareValueLanban = 1.0f;
			compareAbsValueLanban = 50;
		}
		else
		{
			decisionStandard = 2;

			compareValueWHL = 2.0f;
			compareAbsValueWHL = 100;
			
			compareValueLanban = 2.0f;
			compareAbsValueLanban = 50;
		}

		if(iNvramOriginalLength.iValue != 0 && iNvramDetectLength.iValue != 0)
		{
			absoluteCarLength = iNvramDetectLength.iValue - iNvramOriginalLength.iValue;
			compareCarLength = (float)Math.round(100*100*(iNvramDetectLength.iValue - iNvramOriginalLength.iValue)/iNvramOriginalLength.iValue)/100;
			absoluteDifLengthTextField.setTextFieldText(String.valueOf(absoluteCarLength));
			compareDifLengthTextField.setTextFieldText(String.valueOf(compareCarLength));

			if(Math.abs(compareCarLength) <= compareValueWHL||Math.abs(absoluteCarLength) <= compareAbsValueWHL)
			{
				bSingleDecisionLength = true;
				if(singleDecisionLengthTextField != null)
					singleDecisionLengthTextField.setTextFieldText("合格");
			}
			else
			{
				bSingleDecisionLength = false;
				if(singleDecisionLengthTextField != null)
					singleDecisionLengthTextField.setTextFieldText("不合格");
			}
		}
		
		//------------------------------------------------------------------------
		if(iNvramOriginalWidth.iValue != 0 &&iNvramDetectWidth.iValue != 0 && !LMSConstValue.bNvramCarWideMaxBoolean.bValue)
		{
			absoluteCarWidth = iNvramDetectWidth.iValue - iNvramOriginalWidth.iValue;
			compareCarWidth = (float)Math.round(100*100*(iNvramDetectWidth.iValue - iNvramOriginalWidth.iValue)/iNvramOriginalWidth.iValue)/100;
			absoluteDifWidthTextField.setTextFieldText(String.valueOf(absoluteCarWidth));
			compareDifWidthTextField.setTextFieldText(String.valueOf(compareCarWidth));
			
			if(Math.abs(compareCarWidth) <= compareValueWHL||Math.abs(absoluteCarWidth) <= compareAbsValueWHL)
			{
				bSingleDecisionWidth = true;
				if(singleDecisionWidthTextField != null)
					singleDecisionWidthTextField.setTextFieldText("合格");
			}
			else
			{
				bSingleDecisionWidth = false;
				if(singleDecisionWidthTextField != null)
					singleDecisionWidthTextField.setTextFieldText("不合格");
			}
		}
		//
		if(iNvramOriginalWidth.iValue != 0 &&iNvramDetectWidth.iValue != 0 && LMSConstValue.bNvramCarWideMaxBoolean.bValue)
		{
			absoluteCarWidth = iNvramDetectWidth.iValue - iNvramOriginalWidth.iValue;
			compareCarWidth = (float)Math.round(100*100*(iNvramDetectWidth.iValue - iNvramOriginalWidth.iValue)/iNvramOriginalWidth.iValue)/100;
			absoluteDifWidthTextField.setTextFieldText(String.valueOf(absoluteCarWidth));
			compareDifWidthTextField.setTextFieldText(String.valueOf(compareCarWidth));
			if((Math.abs(compareCarWidth) <= compareValueWHL||Math.abs(absoluteCarWidth) <= compareAbsValueWHL) && iNvramDetectWidth.iValue <= 2550)
			{
				bSingleDecisionWidth = true;
				if(singleDecisionWidthTextField != null)
					singleDecisionWidthTextField.setTextFieldText("合格");
			}
			else
			{
				bSingleDecisionWidth = false;
				if(singleDecisionWidthTextField != null)
					singleDecisionWidthTextField.setTextFieldText("不合格");
			}
		}
		
		//------------------------------------------------------------------------
		if(iNvramOriginalHeight.iValue != 0&&iNvramDetectHeight.iValue != 0)
		{
			absoluteCarHeight = iNvramDetectHeight.iValue - iNvramOriginalHeight.iValue;
			compareCarHeight = (float)Math.round(100*100*(iNvramDetectHeight.iValue - iNvramOriginalHeight.iValue)/iNvramOriginalHeight.iValue)/100;
			absoluteDifHeightTextField.setTextFieldText(String.valueOf(absoluteCarHeight));
			compareDifHeightTextField.setTextFieldText(String.valueOf(compareCarHeight));
			
			if(Math.abs(compareCarHeight) <= compareValueWHL||Math.abs(absoluteCarHeight) <= compareAbsValueWHL)
			{
				bSingleDecisionHeight = true;
				if(singleDecisionHeightTextField != null)
					singleDecisionHeightTextField.setTextFieldText("合格");
			}
			else
			{
				bSingleDecisionHeight = false;
				if(singleDecisionHeightTextField != null)
					singleDecisionHeightTextField.setTextFieldText("不合格");
			}
		}
		
		//------------------------------------------------------------------------
		if(LMSConstValue.bNvramLanBanSingleDecision.bValue == true)
		{
			if(iNvramOriginalLanbanHeight.iValue != 0&&iNvramDetectLanbanHeight.iValue != 0)
			{
				singleDecisionLanbanHeightLabelTextField.textField.setHorizontalAlignment(SwingConstants.LEFT);

				absoluteCarLanbanHeight = iNvramDetectLanbanHeight.iValue - iNvramOriginalLanbanHeight.iValue;
				compareCarLanbanHeight = (float)Math.round(100*100*(iNvramDetectLanbanHeight.iValue - iNvramOriginalLanbanHeight.iValue)/iNvramOriginalLanbanHeight.iValue)/100;
				absoluteDifLanbanHeightTextField.setTextFieldText(String.valueOf(absoluteCarLanbanHeight));
				compareDifLanbanHeightTextField.setTextFieldText(String.valueOf(compareCarLanbanHeight));
				
				if(Math.abs(compareCarLanbanHeight) <= compareValueLanban||Math.abs(absoluteCarLanbanHeight) <= compareAbsValueLanban)
				{
					bSingleDecisionLanban = true;
					if(singleDecisionLanbanHeightLabelTextField != null)
						singleDecisionLanbanHeightLabelTextField.setTextFieldText("合格");
				}
				else
				{
					bSingleDecisionLanban = false;
					if(singleDecisionLanbanHeightLabelTextField != null)
						singleDecisionLanbanHeightLabelTextField.setTextFieldText("不合格");
				}
			}
			else
			{
				bSingleDecisionLanban = true;
				if(singleDecisionLanbanHeightLabelTextField != null)
				{
					singleDecisionLanbanHeightLabelTextField.textField.setHorizontalAlignment(SwingConstants.CENTER);
					singleDecisionLanbanHeightLabelTextField.setTextFieldText("/");
				}
			}
		}
		
		if(LMSConstValue.bNvramZJSingleDecision.bValue == true)
		{
			if(iNvramOriginalZJ.iValue != 0&&iNvramDetectZJ.iValue != 0)
			{
				absoluteCarZJ = iNvramDetectZJ.iValue - iNvramOriginalZJ.iValue;
				compareCarZJ = (float)Math.round(100*100*(iNvramDetectZJ.iValue - iNvramOriginalZJ.iValue)/iNvramOriginalZJ.iValue)/100;
				absoluteDifZJTextField.setTextFieldText(String.valueOf(absoluteCarZJ));
				compareDifZJTextField.setTextFieldText(String.valueOf(compareCarZJ));
				
	 			singleDecisionZJLabelTextField.textField.setHorizontalAlignment(SwingConstants.LEFT);
				if(Math.abs(compareCarZJ) <= compareValueZhouJu||Math.abs(absoluteCarZJ) <= compareAbsValueZhouJu)
				{
					bSingleDecisionZJ = true;
					if(singleDecisionZJLabelTextField != null)
						singleDecisionZJLabelTextField.setTextFieldText("合格");
				}
				else
				{
					bSingleDecisionZJ = false;
					if(singleDecisionZJLabelTextField != null)
						singleDecisionZJLabelTextField.setTextFieldText("不合格");
				}
			}
			else
			{
				bSingleDecisionZJ = true;
				if(singleDecisionZJLabelTextField != null)
				{
		 			singleDecisionZJLabelTextField.textField.setHorizontalAlignment(SwingConstants.CENTER);
					singleDecisionZJLabelTextField.setTextFieldText("/");
				}
			}
		}
		//------------------------------------------------------------------------
		if(bSingleDecisionLength&&bSingleDecisionWidth&&bSingleDecisionHeight)
		{
			bDecisionWHL = true;
		}
		else
		{
			bDecisionWHL = false;			
		}
		
		if(bDecisionWHL
			&&(LMSConstValue.bNvramLanBanSingleDecision.bValue == false||bSingleDecisionLanban == true)
			&&(LMSConstValue.bNvramZJSingleDecision.bValue == false||bSingleDecisionZJ == true)		
		)
		{
			bDecision = true;
			if(decisionTextField != null)
				decisionTextField.setTextFieldText("合格");
		}
		else
		{
			bDecision = false;
			if(decisionTextField != null)
				decisionTextField.setTextFieldText("不合格");
		}
		
		//------------------------------------------------------------------------
		if(iNvramOriginalLanbanHeight.iValue != 0 &&iNvramDetectLanbanHeight.iValue != 0)
		{
			absoluteCarLanbanHeight = iNvramDetectLanbanHeight.iValue - iNvramOriginalLanbanHeight.iValue;
			compareCarLanbanHeight = (float)Math.round(100*100*(iNvramDetectLanbanHeight.iValue - iNvramOriginalLanbanHeight.iValue)/iNvramOriginalLanbanHeight.iValue)/100;
			absoluteDifLanbanHeightTextField.setTextFieldText(String.valueOf(absoluteCarLanbanHeight));
			compareDifLanbanHeightTextField.setTextFieldText(String.valueOf(compareCarLanbanHeight));
		}
		else
		{
			absoluteDifLanbanHeightTextField.setTextFieldText("");
			compareDifLanbanHeightTextField.setTextFieldText("");			
		}
		
		//------------------------------------------------------------------------
		if(iNvramOriginalXZJ.iValue != 0&&iNvramDetectXZJ.iValue != 0)
		{
			absoluteCarXZJ = iNvramDetectXZJ.iValue - iNvramOriginalXZJ.iValue;
			compareCarXZJ = (float)Math.round(100*100*(iNvramDetectXZJ.iValue - iNvramOriginalXZJ.iValue)/iNvramOriginalXZJ.iValue)/100;
			absoluteDifXZJTextField.setTextFieldText(String.valueOf(absoluteCarXZJ));
			compareDifXZJTextField.setTextFieldText(String.valueOf(compareCarXZJ));
		}
		
		//------------------------------------------------------------------------
		for(int i=0; i<MAX_Z; i++)
		{
			if(iNvramOriginalZ[i].iValue != 0&&iNvramDetectZ[i].iValue != 0)
			{
				absoluteCarZ[i] = iNvramDetectZ[i].iValue - iNvramOriginalZ[i].iValue;
				compareCarZ[i] = (float)Math.round(100*100*(iNvramDetectZ[i].iValue - iNvramOriginalZ[i].iValue)/iNvramOriginalZ[i].iValue)/100;
				absoluteDifZTextField[i].setTextFieldText(String.valueOf(absoluteCarZ[i]));
				compareDifZTextField[i].setTextFieldText(String.valueOf(compareCarZ[i]));
			}
		}
		
		if(iNvramOriginalZJ.iValue != 0&&iNvramDetectZJ.iValue != 0)
		{
			absoluteCarZJ = iNvramDetectZJ.iValue - iNvramOriginalZJ.iValue;
			compareCarZJ = (float)Math.round(100*100*(iNvramDetectZJ.iValue - iNvramOriginalZJ.iValue)/iNvramOriginalZJ.iValue)/100;
			absoluteDifZJTextField.setTextFieldText(String.valueOf(absoluteCarZJ));
			compareDifZJTextField.setTextFieldText(String.valueOf(compareCarZJ));
		}		
		//------------------------------------------------------------
		setLEDMessage();
	}
	
	boolean bGuaChe = false;
	boolean bQianYing = false;
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

	        if (event.getEventType() != null && eventType.equals(LMSConstValue.CAR_STATE_CHANGE_INTENT)
	        	&&panelType == ContourDetectionDataBaseConst.MainPanelType.DETECT_RESULT) //限定查询界面不显示当次检测结果
	        {
				if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_STATE))
				{
					int carState = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE);
					//进车出监听
					if (carType.equals(ContourDetectionDataBaseConst.MainPanelCarType.GUACHE))
					{
						if (carState == LMSConstValue.enumCarState.CAR_COMMING.ordinal())
						{
							if(CustomerProtocol.customerProtocol != null)
							{
								CustomerProtocol.customerProtocol.carInSignal(panelMain, true);						
							}
						}
						else if (carState == LMSConstValue.enumCarState.CAR_OUTING.ordinal())
						{
							if(CustomerProtocol.customerProtocol != null)
							{
								CustomerProtocol.customerProtocol.carInSignal(panelMain, false);						
							}
						}
					}
										
					//-----------------------------------------
					if(
						(Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.CAR_RESULT.ordinal()
						||(Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.CAR_REGENATE_RESULT.ordinal()
					)
					{
						if(carType == ContourDetectionDataBaseConst.MainPanelCarType.GUACHE && eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH))
						{						
							int carGLength = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH); 
							
							if(carGLength == 0)
								bGuaChe = false;
							else
								bGuaChe = true;
						}
						
						if(carType == ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE && eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH))
						{						
							int carQLength = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH); 
							
							if(carQLength == 0)
								return;
							else
								bQianYing = true;
						}
						
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_REAR_VIEW_MIRROR))
						{
							iNvramDetectWidth_RearViewMirror.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_REAR_VIEW_MIRROR); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_REAR_VIEW_MIRROR))
						{
							iNvramDetectHeight_RearViewMirror.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_REAR_VIEW_MIRROR); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_REAR_VIEW_MIRROR))
						{
							iNvramDetectLength_RearViewMirror.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_REAR_VIEW_MIRROR); 
						}
						
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_FRONT_MIRROR))
						{
							iNvramDetectWidth_FrontMirror.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_WIDTH_WITH_FRONT_MIRROR); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_FRONT_MIRROR))
						{
							iNvramDetectHeight_FrontMirror.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT_WITH_FRONT_MIRROR); 
						}
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_FRONT_MIRROR))
						{
							iNvramDetectLength_FrontMirror.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LENGTH_WITH_FRONT_MIRROR); 
						}
						
						if(bGuaChe == true)
						{
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH))
							{
								iNvramDetectLength.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_LENGTH);
								if(jSettingLabelTextFieldDetectLength != null)
									jSettingLabelTextFieldDetectLength.setTextFieldText(String.valueOf(iNvramDetectLength.iValue));
								if(detectLengthLabel != null)
									detectLengthLabel.setText(String.valueOf(iNvramDetectLength.iValue));
							}
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_WIDTH))
							{
								iNvramDetectWidth.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_WIDTH); 
								if(jSettingLabelTextFieldDetectWidth != null)
									jSettingLabelTextFieldDetectWidth.setTextFieldText(String.valueOf(iNvramDetectWidth.iValue));
								if(detectWidthLabel != null)
									detectWidthLabel.setText(String.valueOf(iNvramDetectWidth.iValue));
							}
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT))
							{
								iNvramDetectHeight.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_HEIGHT); 
								if(jSettingLabelTextFieldDetectHeight != null)
									jSettingLabelTextFieldDetectHeight.setTextFieldText(String.valueOf(iNvramDetectHeight.iValue));
								if(detectHeightLabel != null)
									detectHeightLabel.setText(String.valueOf(iNvramDetectHeight.iValue));
							}
						}
						else if(bQianYing == true)
						{
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH))
							{
								iNvramDetectLength.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_LENGTH);
								if(jSettingLabelTextFieldDetectLength != null)
									jSettingLabelTextFieldDetectLength.setTextFieldText(String.valueOf(iNvramDetectLength.iValue));
								if(detectLengthLabel != null)
									detectLengthLabel.setText(String.valueOf(iNvramDetectLength.iValue));
							}
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_WIDTH))
							{
								iNvramDetectWidth.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_WIDTH); 
								if(jSettingLabelTextFieldDetectWidth != null)
									jSettingLabelTextFieldDetectWidth.setTextFieldText(String.valueOf(iNvramDetectWidth.iValue));
								if(detectWidthLabel != null)
									detectWidthLabel.setText(String.valueOf(iNvramDetectWidth.iValue));
							}
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_HEIGHT))
							{
								iNvramDetectHeight.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_HEIGHT); 
								if(jSettingLabelTextFieldDetectHeight != null)
									jSettingLabelTextFieldDetectHeight.setTextFieldText(String.valueOf(iNvramDetectHeight.iValue));
								if(detectHeightLabel != null)
									detectHeightLabel.setText(String.valueOf(iNvramDetectHeight.iValue));
							}
						}
						else
						{
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LENGTH))
							{
								iNvramDetectLength.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LENGTH); 
								LMSLog.d(DEBUG_TAG,"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="+iNvramDetectLength.iValue);
								if(jSettingLabelTextFieldDetectLength != null)
								{
									LMSLog.d(DEBUG_TAG,"BBBBBBBBBBBBBBBBBB="+iNvramDetectLength.iValue);

									LMSLog.d(DEBUG_TAG,"CCCCCCCCCCCCCCCCC="+jSettingLabelTextFieldDetectLength.getTextFieldText());
									
									jSettingLabelTextFieldDetectLength.setTextFieldText(String.valueOf(iNvramDetectLength.iValue));
									
									LMSLog.d(DEBUG_TAG,"DDDDDDDDDDDDDDDDD="+jSettingLabelTextFieldDetectLength.getTextFieldText());
								}
								if(detectLengthLabel != null)
									detectLengthLabel.setText(String.valueOf(iNvramDetectLength.iValue));
							}
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_WIDTH))
							{
								iNvramDetectWidth.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_WIDTH); 
								if(jSettingLabelTextFieldDetectWidth != null)
									jSettingLabelTextFieldDetectWidth.setTextFieldText(String.valueOf(iNvramDetectWidth.iValue));
								if(detectWidthLabel != null)
									detectWidthLabel.setText(String.valueOf(iNvramDetectWidth.iValue));
							}
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT))
							{
								iNvramDetectHeight.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_HEIGHT); 
								if(jSettingLabelTextFieldDetectHeight != null)
									jSettingLabelTextFieldDetectHeight.setTextFieldText(String.valueOf(iNvramDetectHeight.iValue));
								if(detectHeightLabel != null)
									detectHeightLabel.setText(String.valueOf(iNvramDetectHeight.iValue));
							}
						}
												
						//------------------------------------------------------------------------
						iNvramOriginalLength.iValue = 0;
						if(jSettingLabelTextFieldOriginalLength != null && !jSettingLabelTextFieldOriginalLength.getTextFieldText().equals(""))
						{
							try {
								iNvramOriginalLength.iValue = Integer.valueOf(jSettingLabelTextFieldOriginalLength.getTextFieldText());
							}
							catch (NumberFormatException e)
							{
								LMSLog.exceptionDialog("异常", e); 
							}
						}
						
						//------------------------------------------------------------------------
						iNvramOriginalWidth.iValue = 0;
						if(jSettingLabelTextFieldOriginalWidth != null && !jSettingLabelTextFieldOriginalWidth.getTextFieldText().equals(""))
						{
							try {
								iNvramOriginalWidth.iValue = Integer.valueOf(jSettingLabelTextFieldOriginalWidth.getTextFieldText());
							}
							catch (NumberFormatException e)
							{
								LMSLog.exceptionDialog("异常", e); 
							}
						}
						
						//------------------------------------------------------------------------
						iNvramOriginalHeight.iValue = 0;
						if(jSettingLabelTextFieldOriginalHeight != null && !jSettingLabelTextFieldOriginalHeight.getTextFieldText().equals(""))
						{
							try {
								iNvramOriginalHeight.iValue = Integer.valueOf(jSettingLabelTextFieldOriginalHeight.getTextFieldText());
							}
							catch (NumberFormatException e)
							{
								LMSLog.exceptionDialog("异常", e); 
							}
						}
						
						//------------------------------------------------------------------------
						if(jSettingLabelTextFieldDetectLanbanHeight != null)
							jSettingLabelTextFieldDetectLanbanHeight.setTextFieldText("");	
						if(detectLanbanHeightLabel != null)
							detectLanbanHeightLabel.setText("");	
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_LB_HEIGHT))
						{
							//牵引车,无栏板
							if(carType != ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE)
							{
								iNvramDetectLanbanHeight.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_LB_HEIGHT); 
								if(jSettingLabelTextFieldDetectLanbanHeight != null && iNvramDetectLanbanHeight.iValue !=0)
									jSettingLabelTextFieldDetectLanbanHeight.setTextFieldText(String.valueOf(iNvramDetectLanbanHeight.iValue));
								if(detectLanbanHeightLabel != null && iNvramDetectLanbanHeight.iValue !=0)
									detectLanbanHeightLabel.setText(String.valueOf(iNvramDetectLanbanHeight.iValue));
							}
						}
						iNvramOriginalLanbanHeight.iValue = 0;
						if(jSettingLabelTextFieldOriginalLanbanHeight != null && !jSettingLabelTextFieldOriginalLanbanHeight.getTextFieldText().equals(""))
						{
							try {
								iNvramOriginalLanbanHeight.iValue = Integer.valueOf(jSettingLabelTextFieldOriginalLanbanHeight.getTextFieldText());
							}
							catch (NumberFormatException e)
							{
								LMSLog.exceptionDialog("异常", e); 
							}
						}

						//------------------------------------------------------------------------
						if(jSettingLabelTextFieldDetectXZJ != null)
							jSettingLabelTextFieldDetectXZJ.setTextFieldText("");	
						if(detectXZJLabel != null)
							detectXZJLabel.setText("");	
						if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_XZJ))
						{
							iNvramDetectXZJ.iValue = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_XZJ); 
							if(jSettingLabelTextFieldDetectXZJ != null && iNvramDetectXZJ.iValue != 0)
								jSettingLabelTextFieldDetectXZJ.setTextFieldText(String.valueOf(iNvramDetectXZJ.iValue));
							if(detectXZJLabel != null && iNvramDetectXZJ.iValue != 0)
								detectXZJLabel.setText(String.valueOf(iNvramDetectXZJ.iValue));
						}
						iNvramOriginalXZJ.iValue = 0;
						if(jSettingLabelTextFieldOriginalXZJ != null && !jSettingLabelTextFieldOriginalXZJ.getTextFieldText().equals(""))
						{
							try {
								iNvramOriginalXZJ.iValue = Integer.valueOf(jSettingLabelTextFieldOriginalXZJ.getTextFieldText());
							}
							catch (NumberFormatException e)
							{
								LMSLog.exceptionDialog("异常", e); 
							}
						}
						
						//------------------------------------------------------------------------
						String CAR_ZD = null;
						String CAR_ZJ = null;
						if(bGuaChe)
						{
					 		if(LMSConstValue.bNvramZJ_GuaChe_XIAO.bValue == true
					 			&&(
					 				LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
					 				||LMSConstValue.getSensorType(LMSConstValue.LIGHT_CURTAIN_ID_START).equals(LMSConstValue.SensorType.XZY_1600)
					 			)
							)
					 		{
								if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_XZJ))
								{
									CAR_ZJ = LMSConstValue.INTENT_EXTRA_CAR_XZJ;
								}
					 		}
					 		else
					 		{
								if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_G_Z_NUM))
								{
									zNum = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_G_Z_NUM); 
									CAR_ZD = LMSConstValue.INTENT_EXTRA_CAR_G_ZD;
									CAR_ZJ = LMSConstValue.INTENT_EXTRA_CAR_G_ZJ;
								}
					 		}
						}
						else if(bQianYing)
						{
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Q_Z_NUM))
							{
								zNum = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Q_Z_NUM); 
								CAR_ZD = LMSConstValue.INTENT_EXTRA_CAR_Q_ZD;
								CAR_ZJ = LMSConstValue.INTENT_EXTRA_CAR_Q_ZJ;
							}
						}
						else
						{
							if(eventExtra.containsKey(LMSConstValue.INTENT_EXTRA_CAR_Z_NUM))
							{
								zNum = (Integer) eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_Z_NUM); 
								CAR_ZD = LMSConstValue.INTENT_EXTRA_CAR_ZD;
								CAR_ZJ = LMSConstValue.INTENT_EXTRA_CAR_ZJ;
							}
						}
						/*
						zNumTextField.setText(String.valueOf(zNum));
						for(int i=0; i<MAX_Z; i++)
						{
							if(eventExtra.containsKey(CAR_ZD+i))
							{
								iNvramDetectZ[i].iValue = (Integer) eventExtra.get(CAR_ZD+i); 
								
								if(jSettingLabelTextFieldDetectZ[i] != null)
									jSettingLabelTextFieldDetectZ[i].setTextFieldText(String.valueOf(iNvramDetectZ[i].iValue));
								if(detectZLabel[i] != null)
									detectZLabel[i].setText(String.valueOf(iNvramDetectZ[i].iValue));
							}
							else
							{
								if(jSettingLabelTextFieldDetectZ[i] != null)
									jSettingLabelTextFieldDetectZ[i] .setTextFieldText("");	
								if(detectZLabel[i] != null)
									detectZLabel[i] .setText("");	
							}
							iNvramOriginalZ[i].iValue = 0;
							if(jSettingLabelTextFieldOriginalZ[i] != null && !jSettingLabelTextFieldOriginalZ[i].getTextFieldText().equals(""))
							{
								try {
									iNvramOriginalZ[i].iValue = Integer.valueOf(jSettingLabelTextFieldOriginalZ[i].getTextFieldText());
								}
								catch (NumberFormatException e)
								{
									LMSLog.exceptionDialog("异常", e); 
								}
							}
						}
						*/
						if(eventExtra.containsKey(CAR_ZJ))
						{
							iNvramDetectZJ.iValue = (Integer) eventExtra.get(CAR_ZJ); 
							
							if(jSettingLabelTextFieldDetectZJ != null)
								jSettingLabelTextFieldDetectZJ.setTextFieldText(String.valueOf(iNvramDetectZJ.iValue));
							if(detectZJLabel != null)
								detectZJLabel.setText(String.valueOf(iNvramDetectZJ.iValue));
						}
						else
						{
							if(jSettingLabelTextFieldDetectZJ != null)
								jSettingLabelTextFieldDetectZJ .setTextFieldText("");	
							if(detectZJLabel != null)
								detectZJLabel .setText("");	
						}
						iNvramOriginalZJ.iValue = 0;
						if(jSettingLabelTextFieldOriginalZJ != null && !jSettingLabelTextFieldOriginalZJ.getTextFieldText().equals(""))
						{
							try {
								iNvramOriginalZJ.iValue = Integer.valueOf(jSettingLabelTextFieldOriginalZJ.getTextFieldText());
							}
							catch (NumberFormatException e)
							{
								LMSLog.exceptionDialog("异常", e); 
							}
						}
						
						//===========================================================================
						for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
						{
							genImage(i);
						}
						
						//===========================================================================
						if(saveButton != null)
						{
							saveButton.setText("保存");
						}

						//===========================================================================
						calcCarDecision();

						//===========================================================================						
						iSaveDetectLength = iNvramDetectLength.iValue;
						iSaveDetectWidth = iNvramDetectWidth.iValue;
						iSaveDetectHeight = iNvramDetectHeight.iValue;
						iSaveDetectLanbanHeight = iNvramDetectLanbanHeight.iValue;
						iSaveDetectZJ = iNvramDetectZJ.iValue;
						
						if(humanAdjustButtonBoolean != null && bNvramHumanAdjust.bValue == true)
							humanAdjustButtonBoolean.doClick();

						//===========================================================================
						if((Integer)eventExtra.get(LMSConstValue.INTENT_EXTRA_CAR_STATE) == LMSConstValue.enumCarState.CAR_RESULT.ordinal())
						{												
							if(DetectionVehicle.bAutoUpdate == true)
							{
								//入库
								jdbc_mysql_add();

								if(CustomerProtocol.customerProtocol != null)
								{
									CustomerProtocol.customerProtocol.updateDetectionResult(panelMain);
								}
							}
						}
						
						mainDetectSettingPanel.repaint();
					}
				}
	        }
	        else if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    		
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
				
				if(nvram.equals(LMSConstValue.sNvramUserMsg.nvramStr))
				{					
					companyLable.setText(LMSConstValue.sNvramUserMsg.sValue);
				}
				else if(nvram.equals(LMSConstValue.iNvramUserMsgFontSize.nvramStr))
				{					
					companyLable.setFont(new Font("宋体",Font.BOLD, LMSConstValue.iNvramUserMsgFontSize.iValue));
				}
				else if(
					nvram.equals(iNvramOriginalLength.nvramStr)
					||nvram.equals(iNvramOriginalWidth.nvramStr)
					||nvram.equals(iNvramOriginalHeight.nvramStr)
					||nvram.equals(iNvramOriginalLanbanHeight.nvramStr)
					||nvram.equals(iNvramOriginalXZJ.nvramStr)
					||nvram.equals(iNvramOriginalZ[0].nvramStr)
					||nvram.equals(iNvramOriginalZ[1].nvramStr)
					||nvram.equals(iNvramOriginalZ[2].nvramStr)
					||nvram.equals(iNvramOriginalZ[3].nvramStr)
					||nvram.equals(iNvramOriginalZ[4].nvramStr)
					||nvram.equals(iNvramOriginalZJ.nvramStr)
					||nvram.equals(iNvramDetectLength.nvramStr)
					||nvram.equals(iNvramDetectWidth.nvramStr)
					||nvram.equals(iNvramDetectHeight.nvramStr)
					||nvram.equals(iNvramDetectLanbanHeight.nvramStr)
					||nvram.equals(iNvramDetectXZJ.nvramStr)
					||nvram.equals(iNvramDetectZ[0].nvramStr)
					||nvram.equals(iNvramDetectZ[1].nvramStr)
					||nvram.equals(iNvramDetectZ[2].nvramStr)
					||nvram.equals(iNvramDetectZ[3].nvramStr)
					||nvram.equals(iNvramDetectZ[4].nvramStr)
					||nvram.equals(iNvramDetectZJ.nvramStr)
					||nvram.equals(bNvramNewCar.nvramStr)
					||nvram.equals(LMSConstValue.bNvramCarWideMaxBoolean.nvramStr)
				)
				{			
					calcCarDecision();
				}
				else 
				{
					for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
					{
						if(nvram.equals(LMSConstValue.sNvramMainImageTitle[i].nvramStr))
						{
							viewImageLabel[i].setLabel(LMSConstValue.sNvramMainImageTitle[i].sValue);
						}
						else if(nvram.equals(LMSConstValue.sNvramMainImage[i].nvramStr))
						{
							genImage(i);
						}
					}
				}
			}
	        else if(eventType != null && (eventType.equals(LMSConstValue.SHOW_PICS))) 
	        {
	        	for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
	    		{
	    			genImage(i);
	    		}
			}  
		}
	}
	
	public String regenImageName(String strIn, ContourDetectionDataBaseConst.MainPanelCarType carType)
	{
		String str;
		if(strIn.equals("car_in"))
		{
			str = imagePath+"car_in.jpg";
		}
		else if(strIn.equals("car_out"))
		{
			str = imagePath+"car_out.jpg";
		}
		else
		{
			String endStr = "jpg";
			if(!imagePath.contains("tmp"))
			{
				endStr = LMSConstValue.enumImageFormat.key;
			}
			if(carType == ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE
	        	&&LMSConstValue.bNvramThreeDImageQianYing.bValue == true)
	        {
				str = imagePath+"qianYing_"+strIn+"."+endStr;
	        }
			else
			{
				str = imagePath+strIn+"."+endStr;				
			}
		}
		
		return str;
	}
	
	String imagePath = "image//";
	void genImage(int index)
	{				
		imageFileName[index] = regenImageName(LMSConstValue.sNvramMainImage[index].sValue,carType);

		if(viewImageLabel[index] != null)
			viewImageLabel[index].setScaledIconImage(imageFileName[index]);
	}
	
	void readDatabaseImage(String columnName,int iColumn,ResultSet rs,ResultSetMetaData rsmd) throws SQLException
	{
		try { 	
			String fileName = "image//tmp//"+ID+"//"+columnName+".jpg";
			LMSLog.d(DEBUG_TAG,"fileName="+fileName);
			InputStream inputStream = rs.getBinaryStream(rsmd.getColumnName(iColumn));
	        ImageUtil.readBlob(inputStream, fileName);
		}
		catch (NullPointerException e)
		{
			LMSLog.exception(e);
		}
	}
	
	public void displayRowDetial( ResultSet rs ) throws SQLException { 
		//定位到达第一条记录
		boolean moreRecords = rs.next(); 
			
		//如果没有记录，则提示一条消息
		if ( ! moreRecords ) { 
			LMSLog.errorDialog("无记录", "查无记录"); 
	
			return;
		}
		 			
		//获取字段的名称
		ResultSetMetaData rsmd = rs.getMetaData(); 	
		for ( int i = 1; i <= rsmd.getColumnCount(); ++i )
		{
			if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.TABLE_COLUMN_ID))
			{
				ID = Integer.valueOf(rs.getString(i));
				
				File file = new File("image//tmp//"+ID);  //判断文件夹是否存在,如果不存在则创建文件夹  
				imagePath = "image//tmp//"+ID+"//";
		 		if (!file.exists()) 
		 		{   
		 			file.mkdir();
		 		}
			}
			if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.TABLE_COLUMN_TIME))
			{
				nowTime = rs.getString(i).substring(0,19);
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_SERIAL_NUM))
			{
				if(serialNumLabelTextField != null)
					serialNumLabelTextField.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_NUM))
			{
				if(vehicleNumLabelTextField != null)
					vehicleNumLabelTextField.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_NUM_TYPE))
			{
				if(vehicleNumTypeLabelTextField != null)
					vehicleNumTypeLabelTextField.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_TYPE))
			{
				/*
				if(vehicleTypeComboBox != null)
					vehicleTypeComboBox.addItem(rs.getString(i));
				*/
				if(vehicleTypeLabelTextField != null)
					vehicleTypeLabelTextField.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_BRAND))
			{
				if(vehicleBrandLabelTextField != null)
					vehicleBrandLabelTextField.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_ID))
			{
				if(vehicleIDLabelTextField != null)
					vehicleIDLabelTextField.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_MOTOR_ID))
			{
				if(vehicleMotorIDLabelTextField != null)
					vehicleMotorIDLabelTextField.setTextFieldText(rs.getString(i));
			}
			//-------------------------------------------------------------------------------------------
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_DECISION_STANDARD))
			{
				int iValue = Integer.valueOf(rs.getString(i));
				if(iValue == 1)
				{
					bNvramNewCar.bValue = true;
					newCarButton.setText("注册车(判定标准1%,或±50mm)");
				}
				else if(iValue == 2)
				{
					bNvramNewCar.bValue = false;
					newCarButton.setText("在用车(判定标准2%,或±100mm)");
				}
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_DETECT_VAR))
			{
				if(detectVarLabelTextField != null)
					detectVarLabelTextField.setTextFieldText(rs.getString(i));
			}
			//-------------------------------------------------------------------------------------------
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_WIDTH))
			{
				if(jSettingLabelTextFieldDetectWidth != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectWidth.setTextFieldText(rs.getString(i));
				if(detectWidthLabel != null)
					detectWidthLabel.setText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HEIGHT))
			{
				if(jSettingLabelTextFieldDetectHeight != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectHeight.setTextFieldText(rs.getString(i));
				if(detectHeightLabel != null)
					detectHeightLabel.setText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LENGTH))
			{
				if(jSettingLabelTextFieldDetectLength != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectLength.setTextFieldText(rs.getString(i));
				if(detectLengthLabel != null)
					detectLengthLabel.setText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LANBAN_HEIGHT))
			{
				if(jSettingLabelTextFieldDetectLanbanHeight != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectLanbanHeight.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_XZJ))
			{
				if(jSettingLabelTextFieldDetectXZJ != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectXZJ.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_ZJ))
			{
				if(jSettingLabelTextFieldDetectZJ != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectZJ.setTextFieldText(rs.getString(i));
			}
			//-------------------------------------------------------------------------------------------
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z_NUM))
			{	
				if(zNumTextField != null&&Integer.valueOf(rs.getString(i)) != 0)
					zNumTextField.setText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z0))
			{
				if(jSettingLabelTextFieldDetectZ[0] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectZ[0].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z1))
			{
				if(jSettingLabelTextFieldDetectZ[1] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectZ[1].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z2))
			{
				if(jSettingLabelTextFieldDetectZ[2] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectZ[2].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z3))
			{
				if(jSettingLabelTextFieldDetectZ[3] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectZ[3].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z4))
			{
				if(jSettingLabelTextFieldDetectZ[4] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldDetectZ[4].setTextFieldText(rs.getString(i));
			}
			//-------------------------------------------------------------------------------------------
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_WIDTH_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalWidth != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalWidth.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HEIGHT_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalHeight != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalHeight.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LENGTH_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalLength != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalLength.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LANBAN_HEIGHT_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalLanbanHeight != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalLanbanHeight.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_XZJ_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalXZJ != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalXZJ.setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_ZJ_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalZJ != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalZJ.setTextFieldText(rs.getString(i));
			}
			//-------------------------------------------------------------------------------------------
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z_NUM_ORIGINAL))
			{	
//				if(zNumTextField != null&&Integer.valueOf(rs.getString(i)) != 0)
//					zNumTextField.setText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z0_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalZ[0] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalZ[0].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z1_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalZ[1] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalZ[1].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z2_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalZ[2] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalZ[2].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z3_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalZ[3] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalZ[3].setTextFieldText(rs.getString(i));
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z4_ORIGINAL))
			{
				if(jSettingLabelTextFieldOriginalZ[4] != null&&Integer.valueOf(rs.getString(i)) != 0)
					jSettingLabelTextFieldOriginalZ[4].setTextFieldText(rs.getString(i));
			}
			//-------------------------------------------------------------------------------------------
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_DOWN_VIEW))
			{
				readDatabaseImage("down",i,rs,rsmd);
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LEFT_SIDE_VIEW))
			{
				readDatabaseImage("left",i,rs,rsmd);
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_RIGHT_SIDE_VIEW))
			{
				readDatabaseImage("right",i,rs,rsmd);
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_FRONT_VIEW))
			{
				readDatabaseImage("front",i,rs,rsmd);
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_REAR_VIEW))
			{
				readDatabaseImage("rear",i,rs,rsmd);
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_CAR_IN))
			{
				readDatabaseImage("car_in",i,rs,rsmd);
			}
			else if(rsmd.getColumnName(i).equals(ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_CAR_OUT))
			{
				readDatabaseImage("car_out",i,rs,rsmd);
			}

//			LMSLog.d(DEBUG_TAG,rsmd.getColumnName(i)+"="+rs.getString( i ) ); 
		}
		
		
		for(int i=0;i<LMSConstValue.MAIN_IMAGE_NUM;i++)
		{
			genImage(i);
		}

		//================================================================
	}
	
	void jdbc_mysql_add()
	{			
        Date date = new Date();//获得系统时间.               
        nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        String sDetectType;
        if(carType == ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE)
        {
        	sDetectType = "牵引车";
        }
        else
        {
        	sDetectType = CarTypeAdapter.carEnumType.getValueFromKey(CarTypeAdapter.carEnumType.key);
        }
        
		if(DataBaseConst.local_store_access_conn != null)
		{
			//=========================================================================================
			//连接
			// MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值 
			// 避免中文乱码要指定useUnicode和characterEncoding 
			// 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定， 
			// 下面语句之前就要先创建javademo数据库       
			try { 	
		        String sql = "insert into "+ContourDetectionDataBaseConst.TABLE_NAME+"("
					+ContourDetectionDataBaseConst.TABLE_COLUMN_TIME+','
		        	//-------------------------------------------------------------
					+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_SERIAL_NUM+','
					+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_NUM+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_NUM_TYPE+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_TYPE+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_BRAND+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_ID+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_MOTOR_ID+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_DECISION_STANDARD+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_DETECT_VAR+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_WIDTH+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HEIGHT+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LENGTH+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LANBAN_HEIGHT+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_XZJ+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z_NUM+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z0+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z1+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z2+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z3+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z4+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_WIDTH_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HEIGHT_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LENGTH_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LANBAN_HEIGHT_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_XZJ_ORIGINAL+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z_NUM_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z0_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z1_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z2_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z3_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_Z4_ORIGINAL+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_ZJ_ORIGINAL+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_ZJ+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_DETECT_NUM_OF_TIME+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_OPERATOR_NAME+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_OPERATOR_ID+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_YINCHEYUAN_NAME+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_YINCHEYUAN_ID+','
		        	//-------------------------------------------------------------
//		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LENGTH_PRDADJUST+','
//		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_WIDTH_PRDADJUST+','
//		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HEIGHT_PRDADJUST+','
//		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LANBAN_PRDADJUST+','
//		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_ZJ_PRDADJUST+','
//		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HUMAN_ADJUST_COMMENT_PARAMETER+','
//		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HUMAN_ADJUST_COMMENT_VALUE+','
		        	//-------------------------------------------------------------
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LEFT_SIDE_VIEW+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_RIGHT_SIDE_VIEW+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_DOWN_VIEW+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_FRONT_VIEW+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_REAR_VIEW+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_CAR_IN+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_CAR_OUT
		        	+") values(" 
		        	//========================================================================
		        	+"'"+nowTime+"'"+','
		        	//-------------------------------------------------------------
		        	+"'"+serialNumLabelTextField.getTextFieldText()+"'"+','
		        	+"'"+vehicleNumLabelTextField.getTextFieldText()+"'"+','
		        	+"'"+vehicleNumTypeLabelTextField.getTextFieldText()+"'"+','
//		        	+"'"+vehicleTypeComboBox.getText()+"'"+','
					+"'"+vehicleTypeLabelTextField.getTextFieldText()+"'"+','
		        	+"'"+vehicleBrandLabelTextField.getTextFieldText()+"'"+','
		        	+"'"+vehicleIDLabelTextField.getTextFieldText()+"'"+','
		        	+"'"+vehicleMotorIDLabelTextField.getTextFieldText()+"'"+','
		        	//-------------------------------------------------------------
		        	+"'"+decisionStandard+"'"+','
		        	+"'"+sDetectType+"'"+','
		        	//-------------------------------------------------------------
		        	+"'"+iNvramDetectWidth.iValue+"'"+','
		        	+"'"+iNvramDetectHeight.iValue+"'"+','
		        	+"'"+iNvramDetectLength.iValue+"'"+','
		        	+"'"+iNvramDetectLanbanHeight.iValue+"'"+','
		        	+"'"+iNvramDetectXZJ.iValue+"'"+','
		        	//-------------------------------------------------------------
					+"'"+zNum+"'"+','
					+"'"+iNvramDetectZ[0].iValue+"'"+','
					+"'"+iNvramDetectZ[1].iValue+"'"+','
					+"'"+iNvramDetectZ[2].iValue+"'"+','
					+"'"+iNvramDetectZ[3].iValue+"'"+','
					+"'"+iNvramDetectZ[4].iValue+"'"+','
					//-------------------------------------------------------------
		        	+"'"+iNvramOriginalWidth.iValue+"'"+','
		        	+"'"+iNvramOriginalHeight.iValue+"'"+','
		        	+"'"+iNvramOriginalLength.iValue+"'"+','
		        	+"'"+iNvramOriginalLanbanHeight.iValue+"'"+','
		        	+"'"+iNvramOriginalXZJ.iValue+"'"+','
		        	//-------------------------------------------------------------
					+"'"+0+"'"+','
					+"'"+iNvramOriginalZ[0].iValue+"'"+','
					+"'"+iNvramOriginalZ[1].iValue+"'"+','
					+"'"+iNvramOriginalZ[2].iValue+"'"+','
					+"'"+iNvramOriginalZ[3].iValue+"'"+','
					+"'"+iNvramOriginalZ[4].iValue+"'"+','
		        	//-------------------------------------------------------------
					+"'"+iNvramOriginalZJ.iValue+"'"+','
					+"'"+iNvramDetectZJ.iValue+"'"+','
		        	//-------------------------------------------------------------
					//+"'"+iNvramDetectZJ.iValue+"'"+','
					+"'"+1+"'"+','
		        	//-------------------------------------------------------------
					+"'"+""+"'"+','
					+"'"+""+"'"+','
		        	//-------------------------------------------------------------
					+"'"+""+"'"+','
					+"'"+""+"'"+','
		        	//-------------------------------------------------------------
	//				+"'"+iSaveDetectLength+"'"+','
	//				+"'"+iSaveDetectWidth+"'"+','
	//				+"'"+iSaveDetectHeight+"'"+','
	//				+"'"+iSaveDetectLanbanHeight+"'"+','
	//				+"'"+iSaveDetectZJ+"'"+','
	//				+"'"+beiZhuLabel.getText()+"'"+','
	//				+"'"+beiZhuTextArea.getText()+"'"+','
		        	//-------------------------------------------------------------
		        	+"?,?,?,?,?,?,?"
		        	+")";
				LMSLog.d(DEBUG_TAG,"jdbc_mysql_add="+sql); 
	
				PreparedStatement ps = DataBaseConst.local_store_access_conn.prepareStatement(sql);
	
				InputStream inputStream = null;
				String imageStr;
				int index = 0;
				
				try {			
					for(int i=0;i<7;i++)
					{
						String imageNameStr = null;
						
						if(i < 5)
						{
							if(i == 0)
							{
								imageNameStr = "left.";
							}
							else if(i == 1)
							{
								imageNameStr = "right.";
							}
							else if(i == 2)
							{
								imageNameStr = "down.";
							}
							else if(i == 3)
							{
								imageNameStr = "front.";
							}
							else if(i == 4)
							{
								imageNameStr = "rear.";
							}
	
					        if(carType == ContourDetectionDataBaseConst.MainPanelCarType.QIAN_YING_CHE
						       	&&LMSConstValue.bNvramThreeDImageQianYing.bValue == true)
					        {
					        	imageStr = "image//qianYing_"+imageNameStr+LMSConstValue.enumImageFormat.key;			        	
					        }
					        else
					        {
					        	imageStr = "image//"+imageNameStr+LMSConstValue.enumImageFormat.key;			        	
					        }
						}
						else
						{
							if(i == 5)
							{
								imageNameStr = "car_in.jpg";
							}
							else if(i == 6)
							{
								imageNameStr = "car_out.jpg";
							}
				        	imageStr = "image//"+imageNameStr;			        	
						}
						
						inputStream = ImageUtil.getImageByte(imageStr);
						index++;
						if(inputStream != null)
						{
							ps.setBinaryStream(index, inputStream ,inputStream.available());
						}
						else
						{
							ps.setBinaryStream(index,null);					
						}
					}
				}
				catch (java.io.FileNotFoundException e)
				{
					LMSLog.exceptionDialog("数据库异常", e);
				}
				if(index > 0)
				{
					ps.execute();
				}
			} catch (AbstractMethodError e) { 
				LMSLog.exceptionDialog("数据库异常", e);
			} catch (PacketTooBigException e) { 
				LMSLog.exceptionDialog("数据库异常", e);
			} catch (SQLException e) { 
				LMSLog.exceptionDialog("数据库异常", e);
			} catch (Exception e) { 
				LMSLog.exceptionDialog("数据库异常", e);
			} 
		}
	}
	
	void jdbc_mysql_update_id(int id)
	{
		if(DataBaseConst.local_store_access_conn != null)
		{
			//=========================================================================================
			//连接
			// MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值 
			// 避免中文乱码要指定useUnicode和characterEncoding 
			// 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定， 
			// 下面语句之前就要先创建javademo数据库       
			try { 
				Statement stmt = DataBaseConst.local_store_access_conn.createStatement(); 
				String sql = "update "+ContourDetectionDataBaseConst.TABLE_NAME+" set "
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_VEHICLE_NUM+'='+"'"+vehicleNumLabelTextField.getTextFieldText()+"'"+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_WIDTH+'='+"'"+iNvramDetectWidth.iValue+"'"+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_HEIGHT+'='+"'"+iNvramDetectHeight.iValue+"'"+','
		        	+ContourDetectionDataBaseConst.CAR_TABLE_COLUMN_LENGTH+'='+"'"+iNvramDetectLength.iValue+"'"
		        	+" where "+ContourDetectionDataBaseConst.TABLE_COLUMN_ID+" = "+ "'" + id + "'"; 
				LMSLog.d(DEBUG_TAG,"jdbc_mysql_update_id="+sql); 
	//			sql = "insert into student(NO,name) values('2012002','周小俊')"; 
		        stmt.executeUpdate(sql); 
			} catch (SQLException e) { 
				LMSLog.exception(e);
			} catch (Exception e) { 
				LMSLog.exception(e);
			}
		}
	}
}
