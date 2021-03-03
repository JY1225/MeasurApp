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
		{"K33","小型轿车"            }
		,{"B11","重型普通半挂车"      }
		,{"B12","重型厢式半挂车"      }
		,{"B13","重型罐式半挂车"      }
		,{"B14","重型平板半挂车"      }
		,{"B15","重型集装箱半挂车"    }
		,{"B16","重型自卸半挂车"      }
		,{"B17","重型特殊结构半挂车"  }
		,{"B18","重型仓栅式半挂车"    }
		,{"B19","重型旅居半挂车"      }
		,{"B1A","重型专项作业半挂车"  }
		,{"B1B","重型低平板半挂车"    }
		,{"B21","中型普通半挂车"      }
		,{"B22","中型厢式半挂车"      }
		,{"B23","中型罐式半挂车"      }
		,{"B24","中型平板半挂车"      }
		,{"B25","中型集装箱半挂车"    }
		,{"B26","中型自卸半挂车"      }
		,{"B27","中型特殊结构半挂车"  }
		,{"B28","中型仓栅式半挂车"    }
		,{"B29","中型旅居半挂车"      }
		,{"B2A","中型专项作业半挂车"  }
		,{"B2B","中型低平板半挂车"    }
		,{"B31","轻型普通半挂车"      }
		,{"B32","轻型厢式半挂车"      }
		,{"B33","轻型罐式半挂车"      }
		,{"B34","轻型平板半挂车"      }
		,{"B35","轻型自卸半挂车"      }
		,{"B36","轻型仓栅式半挂车"    }
		,{"B37","轻型旅居半挂车"      }
		,{"B38","轻型专项作业半挂车"  }
		,{"B39","轻型低平板半挂车"    }
		,{"D11","无轨电车"            }
		,{"D12","有轨电车"            }
		,{"G11","重型普通全挂车"      }
		,{"G12","重型厢式全挂车"      }
		,{"G13","重型罐式全挂车"      }
		,{"G14","重型平板全挂车"      }
		,{"G15","重型集装箱全挂车"    }
		,{"G16","重型自卸全挂车"      }
		,{"G17","重型仓栅式全挂车"    }
		,{"G18","重型旅居全挂车"      }
		,{"G19","重型专项作业全挂车"  }
		,{"G21","中型普通全挂车"      }
		,{"G22","中型厢式全挂车"      }
		,{"G23","中型罐式全挂车"      }
		,{"G24","中型平板全挂车"      }
		,{"G25","中型集装箱全挂车"    }
		,{"G26","中型自卸全挂车"      }
		,{"G27","中型仓栅式全挂车"    }
		,{"G28","中型旅居全挂车"      }
		,{"G29","中型专项作业全挂车"  }
		,{"G31","轻型普通全挂车"      }
		,{"G32","轻型厢式全挂车"      }
		,{"G33","轻型罐式全挂车"      }
		,{"G34","轻型平板全挂车"      }
		,{"G35","轻型自卸全挂车"      }
		,{"G36","轻型仓栅式全挂车"    }
		,{"G37","轻型旅居全挂车"      }
		,{"G38","轻型专项作业全挂车"  }
		,{"H11","重型普通货车"        }
		,{"H12","重型厢式货车"        }
		,{"H13","重型封闭货车"        }
		,{"H14","重型罐式货车"        }
		,{"H15","重型平板货车"        }
		,{"H16","重型集装货车"        }
		,{"H17","重型自卸货车"        }
		,{"H18","重型特殊结构货车"    }
		,{"H19","重型仓栅式货车"      }
		,{"H21","中型普通货车"        }
		,{"H22","中型厢式货车"        }
		,{"H23","中型封闭货车"        }
		,{"H24","中型罐式货车"        }
		,{"H25","中型平板货车"        }
		,{"H26","中型集装货车"        }
		,{"H27","中型自卸货车"        }
		,{"H28","中型特殊结构货车"    }
		,{"H29","中型仓栅式货车"      }
		,{"H31","轻型普通货车"        }
		,{"H32","轻型厢式货车"        }
		,{"H33","轻型封闭货车"        }
		,{"H34","轻型罐式货车"        }
		,{"H35","轻型平板货车"        }
		,{"H36","轻型集装货车"        }
		,{"H37","轻型自卸货车"        }
		,{"H38","轻型特殊结构货车"    }
		,{"H39","轻仓栅式货车"        }
		,{"H41","微型普通货车"        }
		,{"H42","微型厢式货车"        }
		,{"H43","微型罐式货车"        }
		,{"H44","微型封闭货车"        }
		,{"H45","微型自卸货车"        }
		,{"H46","微型特殊结构货车"    }
		,{"H47","微型仓栅式货车"      }
		,{"H51","普通低速货车"        }
		,{"H52","厢式低速货车"        }
		,{"H53","罐式低速货车"        }
		,{"H54","自卸低速货车"        }
		,{"H55","仓栅式低速货车"      }
		,{"J11","轮式装载机械"        }
		,{"J12","轮式挖掘机械"        }
		,{"J13","轮式平地机械"        }
		,{"K11","大型普通客车"        }
		,{"K12","大型双层客车"        }
		,{"K13","大型卧铺客车"        }
		,{"K14","大型铰接客车"        }
		,{"K15","大型越野客车"        }
		,{"K16","大型轿车"            }
		,{"K17","大型专用客车"        }
		,{"K21","中型普通客车"        }
		,{"K22","中型双层客车"        }
		,{"K23","中型卧铺客车"        }
		,{"K24","中型铰接客车"        }
		,{"K25","中型越野客车"        }
		,{"K26","中型轿车"            }
		,{"K27","中型专用客车"        }
		,{"K31","小型普通客车"        }
		,{"K32","小型越野客车"        }
		,{"K34","小型专用客车"        }
		,{"K39","小型面包车"          }
		,{"K41","微型普通客车"        }
		,{"K42","微型越野客车"        }
		,{"K43","微型轿车"            }
		,{"K49","微型面包车"          }
		,{"N11","三轮汽车"            }
		,{"Q11","重型半挂牵引车"      }
		,{"Q12","重型全挂牵引车"      }
		,{"Q21","中型半挂牵引车"      }
		,{"Q22","中型全挂牵引车"      }
		,{"Q31","轻型半挂牵引车"      }
		,{"Q32","轻型全挂牵引车"      }
		,{"T11","大型轮式拖拉机"      }
		,{"T21","小型轮式拖拉机"      }
		,{"T22","手扶拖拉机"          }
		,{"T23","手扶变形运输机"      }
		,{"X99","其它"                }
		,{"Z11","大型专项作业车"      }
		,{"Z21","中型专项作业车"      }
		,{"Z31","小型专项作业车"      }
		,{"Z41","微型专项作业车"      }
		,{"Z51","重型专项作业车"      }
		,{"Z71","轻型专项作业车"      }
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
			map.put(CAR_TYPE_NORMAL,"普通车型");  
			map.put(CAR_TYPE_GUACHE,"挂车");  
			map.put(CAR_TYPE_ZZZ_GC,"中置轴挂车");  
			map.put(CAR_TYPE_DIAOBI_CHE,"吊臂车");  
			map.put(CAR_TYPE_ZZZ_QC_GC,"中置轴车辆运输挂车");  
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
		//车型设置
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
		//栏板设置
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
		//车篮滤除设置
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
		//车型设置
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
			//暂停检测			
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
			//栏板设置			
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
			//车篮滤除设置
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
			//尾气滤除设置
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
			//长小物体剔除
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
			//宽小物体剔除
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
			//高小物体剔除
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
			CarDetectFrameMainTab.detectParameterLabel.setText("测量参数:"+sNvramCarTypeString.sValue);
		}
		if(
			ContourDetectionFrame.mainDetectPanel != null 
			&& ContourDetectionFrame.mainDetectPanel.detectParameterLabel != null
		)
		{
			ContourDetectionFrame.mainDetectPanel.detectParameterLabel.setText("测量参数:"+sNvramCarTypeString.sValue);
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
