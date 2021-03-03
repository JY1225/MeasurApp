package AppBase.appBase;

import java.util.HashMap;

import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppFrame.carDetect.CarDetectFrameMainTab;
import AppFrame.contourDetection.ContourDetectionFrame;
import CarDetect.CarDetectSetting;
import CustomerProtocol.CustomerProtocol_SocketServer_XML;
import CustomerProtocol.DetectionVehicle;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import SensorBase.NvramType;
import SensorBase.enumType;

public class CarTypeAdapter {
	private final static String DEBUG_TAG="CarTypeAdapter";
  	
	public static final Object[][] items = new Object[][] 
	{   				
		{"K33","С�ͽγ�"            }
		,{"B11","������ͨ��ҳ�"      }
		,{"B12","������ʽ��ҳ�"      }
		,{"B13","���͹�ʽ��ҳ�"      }
		,{"B14","����ƽ���ҳ�"      }
		,{"B15","���ͼ�װ���ҳ�"    }
		,{"B16","������ж��ҳ�"      }
		,{"B17","��������ṹ��ҳ�"  }
		,{"B18","���Ͳ�դʽ��ҳ�"    }
		,{"B19","�����þӰ�ҳ�"      }
		,{"B1A","����ר����ҵ��ҳ�"  }
		,{"B1B","���͵�ƽ���ҳ�"    }
		,{"B21","������ͨ��ҳ�"      }
		,{"B22","������ʽ��ҳ�"      }
		,{"B23","���͹�ʽ��ҳ�"      }
		,{"B24","����ƽ���ҳ�"      }
		,{"B25","���ͼ�װ���ҳ�"    }
		,{"B26","������ж��ҳ�"      }
		,{"B27","��������ṹ��ҳ�"  }
		,{"B28","���Ͳ�դʽ��ҳ�"    }
		,{"B29","�����þӰ�ҳ�"      }
		,{"B2A","����ר����ҵ��ҳ�"  }
		,{"B2B","���͵�ƽ���ҳ�"    }
		,{"B31","������ͨ��ҳ�"      }
		,{"B32","������ʽ��ҳ�"      }
		,{"B33","���͹�ʽ��ҳ�"      }
		,{"B34","����ƽ���ҳ�"      }
		,{"B35","������ж��ҳ�"      }
		,{"B36","���Ͳ�դʽ��ҳ�"    }
		,{"B37","�����þӰ�ҳ�"      }
		,{"B38","����ר����ҵ��ҳ�"  }
		,{"B39","���͵�ƽ���ҳ�"    }
		,{"D11","�޹�糵"            }
		,{"D12","�й�糵"            }
		,{"G11","������ͨȫ�ҳ�"      }
		,{"G12","������ʽȫ�ҳ�"      }
		,{"G13","���͹�ʽȫ�ҳ�"      }
		,{"G14","����ƽ��ȫ�ҳ�"      }
		,{"G15","���ͼ�װ��ȫ�ҳ�"    }
		,{"G16","������жȫ�ҳ�"      }
		,{"G17","���Ͳ�դʽȫ�ҳ�"    }
		,{"G18","�����þ�ȫ�ҳ�"      }
		,{"G19","����ר����ҵȫ�ҳ�"  }
		,{"G21","������ͨȫ�ҳ�"      }
		,{"G22","������ʽȫ�ҳ�"      }
		,{"G23","���͹�ʽȫ�ҳ�"      }
		,{"G24","����ƽ��ȫ�ҳ�"      }
		,{"G25","���ͼ�װ��ȫ�ҳ�"    }
		,{"G26","������жȫ�ҳ�"      }
		,{"G27","���Ͳ�դʽȫ�ҳ�"    }
		,{"G28","�����þ�ȫ�ҳ�"      }
		,{"G29","����ר����ҵȫ�ҳ�"  }
		,{"G31","������ͨȫ�ҳ�"      }
		,{"G32","������ʽȫ�ҳ�"      }
		,{"G33","���͹�ʽȫ�ҳ�"      }
		,{"G34","����ƽ��ȫ�ҳ�"      }
		,{"G35","������жȫ�ҳ�"      }
		,{"G36","���Ͳ�դʽȫ�ҳ�"    }
		,{"G37","�����þ�ȫ�ҳ�"      }
		,{"G38","����ר����ҵȫ�ҳ�"  }
		,{"H11","������ͨ����"        }
		,{"H12","������ʽ����"        }
		,{"H13","���ͷ�ջ���"        }
		,{"H14","���͹�ʽ����"        }
		,{"H15","����ƽ�����"        }
		,{"H16","���ͼ�װ����"        }
		,{"H17","������ж����"        }
		,{"H18","��������ṹ����"    }
		,{"H19","���Ͳ�դʽ����"      }
		,{"H21","������ͨ����"        }
		,{"H22","������ʽ����"        }
		,{"H23","���ͷ�ջ���"        }
		,{"H24","���͹�ʽ����"        }
		,{"H25","����ƽ�����"        }
		,{"H26","���ͼ�װ����"        }
		,{"H27","������ж����"        }
		,{"H28","��������ṹ����"    }
		,{"H29","���Ͳ�դʽ����"      }
		,{"H31","������ͨ����"        }
		,{"H32","������ʽ����"        }
		,{"H33","���ͷ�ջ���"        }
		,{"H34","���͹�ʽ����"        }
		,{"H35","����ƽ�����"        }
		,{"H36","���ͼ�װ����"        }
		,{"H37","������ж����"        }
		,{"H38","��������ṹ����"    }
		,{"H39","���դʽ����"        }
		,{"H41","΢����ͨ����"        }
		,{"H42","΢����ʽ����"        }
		,{"H43","΢�͹�ʽ����"        }
		,{"H44","΢�ͷ�ջ���"        }
		,{"H45","΢����ж����"        }
		,{"H46","΢������ṹ����"    }
		,{"H47","΢�Ͳ�դʽ����"      }
		,{"H51","��ͨ���ٻ���"        }
		,{"H52","��ʽ���ٻ���"        }
		,{"H53","��ʽ���ٻ���"        }
		,{"H54","��ж���ٻ���"        }
		,{"H55","��դʽ���ٻ���"      }
		,{"J11","��ʽװ�ػ�е"        }
		,{"J12","��ʽ�ھ��е"        }
		,{"J13","��ʽƽ�ػ�е"        }
		,{"K11","������ͨ�ͳ�"        }
		,{"K12","����˫��ͳ�"        }
		,{"K13","�������̿ͳ�"        }
		,{"K14","���ͽ½ӿͳ�"        }
		,{"K15","����ԽҰ�ͳ�"        }
		,{"K16","���ͽγ�"            }
		,{"K17","����ר�ÿͳ�"        }
		,{"K21","������ͨ�ͳ�"        }
		,{"K22","����˫��ͳ�"        }
		,{"K23","�������̿ͳ�"        }
		,{"K24","���ͽ½ӿͳ�"        }
		,{"K25","����ԽҰ�ͳ�"        }
		,{"K26","���ͽγ�"            }
		,{"K27","����ר�ÿͳ�"        }
		,{"K31","С����ͨ�ͳ�"        }
		,{"K32","С��ԽҰ�ͳ�"        }
		,{"K34","С��ר�ÿͳ�"        }
		,{"K39","С�������"          }
		,{"K41","΢����ͨ�ͳ�"        }
		,{"K42","΢��ԽҰ�ͳ�"        }
		,{"K43","΢�ͽγ�"            }
		,{"K49","΢�������"          }
		,{"N11","��������"            }
		,{"Q11","���Ͱ��ǣ����"      }
		,{"Q12","����ȫ��ǣ����"      }
		,{"Q21","���Ͱ��ǣ����"      }
		,{"Q22","����ȫ��ǣ����"      }
		,{"Q31","���Ͱ��ǣ����"      }
		,{"Q32","����ȫ��ǣ����"      }
		,{"T11","������ʽ������"      }
		,{"T21","С����ʽ������"      }
		,{"T22","�ַ�������"          }
		,{"T23","�ַ����������"      }
		,{"X99","����"                }
		,{"Z11","����ר����ҵ��"      }
		,{"Z21","����ר����ҵ��"      }
		,{"Z31","С��ר����ҵ��"      }
		,{"Z41","΢��ר����ҵ��"      }
		,{"Z51","����ר����ҵ��"      }
		,{"Z71","����ר����ҵ��"      }
	}; 
	
	void init()
	{
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
	}
	
	public static NvramType sNvramCarTypeString = new NvramType("nvram_sNvramCarTypeString",NvramType.Type.STRING_TYPE);

	public static CarTypeAdapter innerObj = new CarTypeAdapter();

	public static final String CAR_TYPE_UNKNOW = "CAR_TYPE_UNKNOW";
	public static final String CAR_TYPE_NORMAL = "CAR_TYPE_NORMAL";
	public static final String CAR_TYPE_LANBAN = "CAR_TYPE_LANBAN";
	public static final String CAR_TYPE_GUACHE = "CAR_TYPE_GUACHE";
	public static final String CAR_TYPE_LB_GC = "CAR_TYPE_LB_GC";
	public static final String CAR_TYPE_LANZI_GUACHE = "CAR_TYPE_LANZI_GUACHE";
	public static final String CAR_TYPE_LANZI_LB_GC = "CAR_TYPE_LANZI_LB_GC";
	public static final String CAR_TYPE_LINE_HEAD_GUACHE = "CAR_TYPE_LINE_HEAD_GUACHE";
	public static final String CAR_TYPE_LINE_HEAD_LB_GC = "CAR_TYPE_LINE_HEAD_LB_GC";
	public static final String CAR_TYPE_ZZZ_GC = "CAR_TYPE_ZZZ_GC";
	public static final String CAR_TYPE_ZZZ_QC_GC = "CAR_TYPE_ZZZ_Q_GC";
	public static final String CAR_TYPE_DIAOBI_CHE = "CAR_TYPE_DIAOBI_CHE";
	public static final String CAR_TYPE_AUTO = "CAR_TYPE_AUTO";
	public class CarType extends enumType{		
		public CarType() {
			map.put(CAR_TYPE_NORMAL,"��ͨ����");  
			map.put(CAR_TYPE_GUACHE,"�ҳ�");  
			map.put(CAR_TYPE_ZZZ_GC,"������ҳ�");  
			map.put(CAR_TYPE_DIAOBI_CHE,"���۳�");  
			map.put(CAR_TYPE_ZZZ_QC_GC,"�����ᳵ������ҳ�");  
		}	
	}
	public static CarType carEnumType = innerObj.new CarType(); 
	public static String nvramCarEnumType = "nvram_carType";
	
	public static NvramType bNvramPauseDetect = new NvramType("nvram_bNvramPauseDetect",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramLanBanDetect = new NvramType("nvram_bNvramLanBanDetect",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramFilterCheLan = new NvramType("nvram_bNvramFilterCheLan",NvramType.Type.BOOLEAN_TYPE);
	public static NvramType bNvramFilterEndGas = new NvramType("nvram_bNvramFilterEndGas",NvramType.Type.BOOLEAN_TYPE);

	//=============================================================
	public static boolean oldCarTypeAdapter(String result)
	{
		if(result.contains("VehicleList"))
		{
			new CustomerProtocol_SocketServer_XML().parseSingleCarInformation(result);

			return false;
		}
		else if(result.contains(CarTypeAdapter.CAR_TYPE_UNKNOW)
			||result.contains(CarTypeAdapter.CAR_TYPE_NORMAL)
			||result.contains(CarTypeAdapter.CAR_TYPE_LANBAN)
			||result.contains(CarTypeAdapter.CAR_TYPE_DIAOBI_CHE)
			||result.contains(CarTypeAdapter.CAR_TYPE_GUACHE)
			||result.contains(CarTypeAdapter.CAR_TYPE_LB_GC)
			||result.contains(CarTypeAdapter.CAR_TYPE_LANZI_GUACHE)
			||result.contains(CarTypeAdapter.CAR_TYPE_LANZI_LB_GC)
			||result.contains(CarTypeAdapter.CAR_TYPE_LINE_HEAD_GUACHE)
		    ||result.contains(CarTypeAdapter.CAR_TYPE_LINE_HEAD_LB_GC)
			||result.contains(CarTypeAdapter.CAR_TYPE_ZZZ_GC)			
			||result.contains(CarTypeAdapter.CAR_TYPE_ZZZ_QC_GC)			
			||result.contains(CarTypeAdapter.CAR_TYPE_AUTO)
		)
		{
			if(result.contains(CarTypeAdapter.CAR_TYPE_UNKNOW))
				result = CarTypeAdapter.CAR_TYPE_UNKNOW;
			if(result.contains(CarTypeAdapter.CAR_TYPE_NORMAL))
				result = CarTypeAdapter.CAR_TYPE_NORMAL;
			if(result.contains(CarTypeAdapter.CAR_TYPE_LANBAN))
				result = CarTypeAdapter.CAR_TYPE_LANBAN;
			if(result.contains(CarTypeAdapter.CAR_TYPE_DIAOBI_CHE))
				result = CarTypeAdapter.CAR_TYPE_DIAOBI_CHE;
			if(result.contains(CarTypeAdapter.CAR_TYPE_GUACHE))
				result = CarTypeAdapter.CAR_TYPE_GUACHE;
			if(result.contains(CarTypeAdapter.CAR_TYPE_LB_GC))
				result = CarTypeAdapter.CAR_TYPE_LB_GC;
			if(result.contains(CarTypeAdapter.CAR_TYPE_LANZI_GUACHE))
				result = CarTypeAdapter.CAR_TYPE_LANZI_GUACHE;
			if(result.contains(CarTypeAdapter.CAR_TYPE_LANZI_LB_GC))
				result = CarTypeAdapter.CAR_TYPE_LANZI_LB_GC;
			if(result.contains(CarTypeAdapter.CAR_TYPE_LINE_HEAD_GUACHE))
				result = CarTypeAdapter.CAR_TYPE_LINE_HEAD_GUACHE;
			if(result.contains(CarTypeAdapter.CAR_TYPE_LINE_HEAD_LB_GC))
				result = CarTypeAdapter.CAR_TYPE_LINE_HEAD_LB_GC;
			if(result.contains(CarTypeAdapter.CAR_TYPE_ZZZ_GC))			
				result = CarTypeAdapter.CAR_TYPE_ZZZ_GC;
			if(result.contains(CarTypeAdapter.CAR_TYPE_ZZZ_QC_GC))			
				result = CarTypeAdapter.CAR_TYPE_ZZZ_QC_GC;
			if(result.contains(CarTypeAdapter.CAR_TYPE_AUTO))
				result = CarTypeAdapter.CAR_TYPE_AUTO;
			
			CarDetectSetting.CarTypeChangeTrig(result);			
		}
		
		return true;
	}
	
	void carType_CarTypeStringToFrame()
	{
		LMSLog.d(DEBUG_TAG, "carType_CarTypeStringToFrame 0:"+sNvramCarTypeString.sValue);

		//---------------------------------------------------------------
		//��������
		String carTypeStr = null;
		if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_NORMAL)
			||CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LANBAN)
		)
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_NORMAL;
		}
		else if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_GUACHE)
			||CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LB_GC)
			||CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LANZI_GUACHE)
			||CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LANZI_LB_GC)
			||CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LINE_HEAD_GUACHE)
			||CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LINE_HEAD_LB_GC)
		)
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_GUACHE;
		}
		else if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_ZZZ_GC))
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_ZZZ_GC;
		}
		else if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_ZZZ_QC_GC))
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_ZZZ_QC_GC;
		}
		else if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_DIAOBI_CHE))
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_DIAOBI_CHE;
		}
		
		if(CarDetectFrameMainTab.carTypeRadioButtonGroup != null)
		{
			CarDetectFrameMainTab.carTypeRadioButtonGroup.autoSetRadioButton(carTypeStr,CarTypeAdapter.carEnumType);
		}
		if(
			ContourDetectionFrame.mainDetectPanel != null  
			&& ContourDetectionFrame.mainDetectPanel.carTypeRadioButtonGroup != null
		)
		{
			ContourDetectionFrame.mainDetectPanel.carTypeRadioButtonGroup.autoSetRadioButton(carTypeStr,CarTypeAdapter.carEnumType);
		}

		LMSLog.d(DEBUG_TAG, "carType_CarTypeStringToFrame 1:"+sNvramCarTypeString.sValue);

		//---------------------------------------------------------------
		//��������
		boolean bSetSelect = false;
		if(sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LANBAN)
			||sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LB_GC)
			||sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LANZI_LB_GC)
			||sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LINE_HEAD_LB_GC)
		)
		{
			bSetSelect = true;
		}
		else
		{
			bSetSelect = false;
		}
			
		if(CarDetectFrameMainTab.lanBanDetectCheckBox != null)
		{
			CarDetectFrameMainTab.lanBanDetectCheckBox.autoSelect(bSetSelect);
		}
		if(
			ContourDetectionFrame.mainDetectPanel != null 
			&& ContourDetectionFrame.mainDetectPanel.lanBanDetectCheckBox != null
		)
		{
			ContourDetectionFrame.mainDetectPanel.lanBanDetectCheckBox.autoSelect(bSetSelect);
		}
		
		//---------------------------------------------------------------
		//�����˳�����
		if(sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LANZI_GUACHE)
			||sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_LANZI_LB_GC)
		)
		{
			bSetSelect = true;
		}
		else
		{
			bSetSelect = false;
		}
		
		if(CarDetectFrameMainTab.filterCheLanCheckBox != null)
		{
			CarDetectFrameMainTab.filterCheLanCheckBox.autoSelect(bSetSelect);
		}
		if(
			ContourDetectionFrame.mainDetectPanel != null 
			&& ContourDetectionFrame.mainDetectPanel.filterCheLanCheckBox != null
		)
		{
			ContourDetectionFrame.mainDetectPanel.filterCheLanCheckBox.autoSelect(bSetSelect);
		}
		
		//===========================================================================
		carType_StringToParameterLabel();
	}
	
	public static void carType_CarTypeInterfaceToFrame(
		String sCarTypeString, 
		boolean bLanbanDetect, 
		boolean bFilterCheLan,
		boolean bFilterEndGas,
		String sLengthFilter,
		String sWidthFilter,
		String sHeightFilter
	)
	{
		LMSLog.d(DEBUG_TAG, "carType_CarTypeInterfaceToFrame 0:"+sNvramCarTypeString.sValue);

		if(sCarTypeString == null)
		{
			return;
		}
		CarTypeAdapter.bNvramPauseDetect.bValue = DetectionVehicle.bPauseDetect;
		CarTypeAdapter.bNvramLanBanDetect.bValue = bLanbanDetect;
		CarTypeAdapter.bNvramFilterCheLan.bValue = bFilterCheLan;
		CarTypeAdapter.bNvramFilterEndGas.bValue = bFilterEndGas;
		
		//---------------------------------------------------------------
		//��������
		String carTypeStr = null;
		if(sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_NORMAL)
			||sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_LANBAN)
		)
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_NORMAL;
		}
		else if(sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_GUACHE)
			||sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_LB_GC)
			||sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_LANZI_GUACHE)
			||sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_LANZI_LB_GC)
			||sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_LINE_HEAD_GUACHE)
			||sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_LINE_HEAD_LB_GC)
		)
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_GUACHE;
		}
		else if(sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_ZZZ_GC))
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_ZZZ_GC;
		}
		else if(sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_ZZZ_QC_GC))
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_ZZZ_QC_GC;
		}
		else if(sCarTypeString.equals(CarTypeAdapter.CAR_TYPE_DIAOBI_CHE))
		{
			carTypeStr = CarTypeAdapter.CAR_TYPE_DIAOBI_CHE;
		}
		
		if(carTypeStr != null)
		{
			CarTypeAdapter.sNvramCarTypeString.sValue = sCarTypeString;						

			if(CarDetectFrameMainTab.carTypeRadioButtonGroup != null)
			{
				CarDetectFrameMainTab.carTypeRadioButtonGroup.autoSetRadioButton(carTypeStr,CarTypeAdapter.carEnumType);
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null  
				&& ContourDetectionFrame.mainDetectPanel.carTypeRadioButtonGroup != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.carTypeRadioButtonGroup.autoSetRadioButton(carTypeStr,CarTypeAdapter.carEnumType);
			}
			
			LMSLog.d(DEBUG_TAG, "carType_CarTypeStringToFrame 1:"+sNvramCarTypeString.sValue);
	
			//---------------------------------------------------------------
			//��ͣ���			
			if(CarDetectFrameMainTab.beginDetectionButton != null)
			{
				CarDetectFrameMainTab.beginDetectionButton.setLabelText();
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null 
				&& ContourDetectionFrame.mainDetectPanel.beginDetectionButton != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.beginDetectionButton.setLabelText();
			}
			
			//---------------------------------------------------------------
			//��������			
			if(CarDetectFrameMainTab.lanBanDetectCheckBox != null)
			{
				CarDetectFrameMainTab.lanBanDetectCheckBox.autoSelect(CarTypeAdapter.bNvramLanBanDetect.bValue);
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null 
				&& ContourDetectionFrame.mainDetectPanel.lanBanDetectCheckBox != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.lanBanDetectCheckBox.autoSelect(CarTypeAdapter.bNvramLanBanDetect.bValue);
			}
			
			//---------------------------------------------------------------
			//�����˳�����
			if(CarDetectFrameMainTab.filterCheLanCheckBox != null)
			{
				CarDetectFrameMainTab.filterCheLanCheckBox.autoSelect(CarTypeAdapter.bNvramFilterCheLan.bValue );
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null 
				&& ContourDetectionFrame.mainDetectPanel.filterCheLanCheckBox != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.filterCheLanCheckBox.autoSelect(CarTypeAdapter.bNvramFilterCheLan.bValue );
			}
			
			//---------------------------------------------------------------
			//β���˳�����
			if(CarDetectFrameMainTab.filterEndGasCheckBox != null)
			{
				CarDetectFrameMainTab.filterEndGasCheckBox.autoSelect(CarTypeAdapter.bNvramFilterEndGas.bValue );
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null 
				&& ContourDetectionFrame.mainDetectPanel.filterEndGasCheckBox != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.filterEndGasCheckBox.autoSelect(CarTypeAdapter.bNvramFilterEndGas.bValue );
			}
			
			//---------------------------------------------------------------
			//��С�����޳�
			if(sLengthFilter == null)
				sLengthFilter = LMSConstValue.LENGTH_FILTER_NONE;
			LMSLog.d(DEBUG_TAG, "sLengthFilter="+sLengthFilter);
			if(CarDetectFrameMainTab.lengthFilterLabelComboBox != null)
			{
				CarDetectFrameMainTab.lengthFilterLabelComboBox.autoSetSelectedItem(sLengthFilter);
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null 
				&& ContourDetectionFrame.mainDetectPanel.lengthFilterLabelComboBox != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.lengthFilterLabelComboBox.autoSetSelectedItem(sLengthFilter);
			}
			
			//---------------------------------------------------------------
			//��С�����޳�
			if(sWidthFilter == null)
				sWidthFilter = LMSConstValue.WIDTH_FILTER_NONE;
			LMSLog.d(DEBUG_TAG, "sWidthFilter="+sWidthFilter);
			if(CarDetectFrameMainTab.widthFilterLabelComboBox != null)
			{
				CarDetectFrameMainTab.widthFilterLabelComboBox.autoSetSelectedItem(sWidthFilter);
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null 
				&& ContourDetectionFrame.mainDetectPanel.widthFilterLabelComboBox != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.widthFilterLabelComboBox.autoSetSelectedItem(sWidthFilter);
			}
			
			//---------------------------------------------------------------
			//��С�����޳�
			if(sHeightFilter == null)
				sHeightFilter = LMSConstValue.HEIGHT_FILTER_NONE;
			LMSLog.d(DEBUG_TAG, "sHeightFilter="+sHeightFilter);
			if(CarDetectFrameMainTab.heightFilterLabelComboBox != null)
			{
				CarDetectFrameMainTab.heightFilterLabelComboBox.autoSetSelectedItem(sHeightFilter);
			}
			if(
				ContourDetectionFrame.mainDetectPanel != null 
				&& ContourDetectionFrame.mainDetectPanel.heightFilterLabelComboBox != null
			)
			{
				ContourDetectionFrame.mainDetectPanel.heightFilterLabelComboBox.autoSetSelectedItem(sHeightFilter);
			}
			
			LMSLog.d(DEBUG_TAG, "carType_CarTypeInterfaceToFrame 1:"+sNvramCarTypeString.sValue);
			
			//===========================================================================
			carType_FrameToParameterLabel();
		}
	}
	
	static void carType_FrameToParameterLabel()
	{
		LMSLog.d(DEBUG_TAG, "carType_FrameToParameterLabel");

		String str = CarTypeAdapter.carEnumType.key;
		
		if(CarTypeAdapter.carEnumType.key.equals(CarTypeAdapter.CAR_TYPE_NORMAL))
		{
			if(bNvramLanBanDetect.bValue == true)
			{
				str = CarTypeAdapter.CAR_TYPE_LANBAN;
			}
		}
		else if(CarTypeAdapter.carEnumType.key.equals(CarTypeAdapter.CAR_TYPE_GUACHE))
		{
			if(bNvramLanBanDetect.bValue == true)
			{				
				if(bNvramFilterCheLan.bValue == false)
				{
					str = CarTypeAdapter.CAR_TYPE_LB_GC;
				}
				else
				{
					str = CarTypeAdapter.CAR_TYPE_LANZI_LB_GC;					
				}
			}
			else
			{
				if(bNvramFilterCheLan.bValue == false)
				{
					str = CarTypeAdapter.CAR_TYPE_GUACHE;
				}
				else
				{
					str = CarTypeAdapter.CAR_TYPE_LANZI_GUACHE;					
				}
			}
		}

		sNvramCarTypeString.sValue = str;
		
		//=======================================================
		carType_StringToParameterLabel();
	}

	static void carType_StringToParameterLabel()
	{
		if(CarDetectFrameMainTab.detectParameterLabel != null)
		{
			CarDetectFrameMainTab.detectParameterLabel.setText("��������:"+sNvramCarTypeString.sValue);
		}
		if(
			ContourDetectionFrame.mainDetectPanel != null 
			&& ContourDetectionFrame.mainDetectPanel.detectParameterLabel != null
		)
		{
			ContourDetectionFrame.mainDetectPanel.detectParameterLabel.setText("��������:"+sNvramCarTypeString.sValue);
		}
		
		for(int i=0;i<LMSConstValue.iRadarSensorNum;i++)
		{
			CarDetectSetting.resetSystemStateString("NULL",i);
		}
	}
	
	private class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();
			 
	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    	
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 

				LMSLog.d(DEBUG_TAG, "nvram=============="+nvram);
        		if(
        			nvram.equals(CarTypeAdapter.nvramCarEnumType)
    				||nvram.equals(bNvramPauseDetect.nvramStr)
    				||nvram.equals(bNvramLanBanDetect.nvramStr)
    				||nvram.equals(bNvramFilterCheLan.nvramStr)
    			)
        		{
    				carType_FrameToParameterLabel();
        		}
        		else if(nvram.equals(sNvramCarTypeString.nvramStr))
        		{
        			carType_CarTypeStringToFrame();
        		}
	        }
		}
	}
}
