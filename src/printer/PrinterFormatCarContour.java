package printer;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import AppFrame.contourDetection.ContourDetectionTabPanelMain;
import AppFrame.contourDetection.ContourDetectionTabPanelSetting;
import AppFrame.widgets.Operator;
import SensorBase.LMSConstValue;
import SensorBase.LMSFunction;
import SensorBase.LMSLog;

public class PrinterFormatCarContour {
    ContourDetectionTabPanelMain printPanel;

    public final int MARGIN_X = 15;

    public void setPanel(ContourDetectionTabPanelMain _printPanel)
    {
    	printPanel = _printPanel;
    }
    
    int drawPrintTitle(Graphics2D g2d, double paperWidth, double paperHeight)
    {    	
    	//Graphics2D绘图方式
        Font font = new Font("Serif", Font.BOLD, 20);  //设计字体为加粗，20
        
        g2d.setFont(font);

    	String str = "机动车外廓检测报告";
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D rec = fm.getStringBounds(str, g2d);  
        
        int stringWidth = (int) rec.getWidth();
        int stringHeight = (int) rec.getHeight();
		
        g2d.drawString(str, (int) ((paperWidth-stringWidth)/2), stringHeight);//把STR里的内容在纸张长，顶高处显示  
        
        return stringHeight;
    }
    
    int drawPrintOtherMessage(Graphics2D g2d, int yStart, double paperWidth, double paperHeight)
    {    	
        Font font = new Font("Serif", Font.PLAIN, 14);  
        g2d.setFont(font);
        
        FontMetrics fm = g2d.getFontMetrics();
        String str;
        int stringWidth;
        int stringHeight;
        int y = yStart;
        int Y_OFFSET = 2;
        Rectangle2D rec;
        
        str = "检测机构名称:"+LMSConstValue.sNvramUserMsg.sValue;
    	rec = fm.getStringBounds(str, g2d);  
        stringWidth = (int) rec.getWidth();
        stringHeight = (int) rec.getHeight();
        g2d.drawString(str, MARGIN_X, y+stringHeight);  
        y += (stringHeight+Y_OFFSET);
        
        str = printPanel.serialNumLabelStr+":"+printPanel.serialNumLabelTextField.getTextFieldText();
    	rec = fm.getStringBounds(str, g2d);  
        stringWidth = (int) rec.getWidth();
        stringHeight = (int) rec.getHeight();
        g2d.drawString(str, MARGIN_X, y+stringHeight);
        
        str = "检测时间:";
        if(printPanel.nowTime != null)
        {
        	str += printPanel.nowTime;
        }
    	rec = fm.getStringBounds(str, g2d);  
        stringWidth = (int) rec.getWidth();
        stringHeight = (int) rec.getHeight();
        g2d.drawString(str, (int) (paperWidth/2), y+stringHeight);

        y += (stringHeight+Y_OFFSET);

        return y;
    }
    
	int yOffset = 5;
	
	int END_HEIGHT = 60;
	void drawPrintMainFrame(Graphics2D g2d, int yStart, double paperWidth, double paperHeight)
	{
	   	int y1 = yStart;
    	int y2 = (int) (paperHeight-END_HEIGHT);
    	int x1 = MARGIN_X;
    	int x2 = (int)(paperWidth-MARGIN_X);
    	
    	g2d.drawLine(x1, y1, x2, y1);
    	g2d.drawLine(x1, y1, x1, y2);
    	g2d.drawLine(x2, y1, x2, y2);
    	g2d.drawLine(x1, y2, x2, y2);
	}
	
	int TEXT_LINE_HEIGHT = 27;
    int drawPrintMainMessage(Graphics2D g2d, int yStart, double paperWidth, double paperHeight)
    {          
    	String str;
    	int blockX;
    	int stringWidth;
    	int stringHeight;
    	int descent;
    	int singleWidth;
    	Rectangle2D rec;
    	FontMetrics fm;
        	    	
    	int x1 = MARGIN_X;
    	int x2 = (int)(paperWidth-MARGIN_X);
    	    	    	
    	//-----------------------------------------------------
    	int LINE_Y = yStart;
    	blockX = x1;
    	
		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC))
		{
	    	g2d.setFont(new Font("Serif", Font.BOLD, 16));
	    	str = "车辆信息";
	    	fm = g2d.getFontMetrics();
	     	rec = fm.getStringBounds(str, g2d);  
	     	stringWidth = (int) rec.getWidth();
	     	stringHeight = (int) rec.getHeight();
	      	descent = fm.getDescent();
	    	singleWidth = (int) paperWidth;
	     	g2d.drawString(str, (int)(singleWidth-MARGIN_X-stringWidth)/2, LINE_Y+TEXT_LINE_HEIGHT-(TEXT_LINE_HEIGHT-stringHeight)/2-descent);
	     	
	     	LINE_Y += TEXT_LINE_HEIGHT;
	    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
		}
		
    	//-----------------------------------------------------
       	g2d.setFont(new Font("Serif", Font.PLAIN, 14));
       	
    	str = printPanel.vehicleNumLabelStr; //号牌号码
       	singleWidth = (int) (paperWidth-2*MARGIN_X)/6;
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);

     	str = printPanel.vehicleNumLabelTextField.getTextFieldText();
       	singleWidth = (int) (paperWidth-2*MARGIN_X)/3;
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);

     	//-----------------------------------------------------
       	str = printPanel.vehicleNumTypeLabelStr; //号牌种类
       	singleWidth = (int) (paperWidth-2*MARGIN_X)/6;
     	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);
     	
     	str = printPanel.vehicleNumTypeLabelTextField.getTextFieldText();
       	singleWidth = (int) (paperWidth-2*MARGIN_X)/3;
     	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true);   
     	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	
    	//=====================================================
    	str = printPanel.vehicleTypeLabelStr; //车辆类型
       	singleWidth = (int) (paperWidth-2*MARGIN_X)/6;
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
       	
//     	str = printPanel.vehicleTypeComboBox.getText();
     	str = printPanel.vehicleTypeLabelTextField.getTextFieldText();
       	singleWidth = (int) (paperWidth-2*MARGIN_X)/3;
     	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
     	
     	//-----------------------------------------------------
    	if(!LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC))
    	{	
	       	str = printPanel.vehicleBrandLabelStr; //品牌型号
	       	singleWidth = (int) (paperWidth-2*MARGIN_X)/6;
	       	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		false); 
         		
	     	str = printPanel.vehicleBrandLabelTextField.getTextFieldText();
	       	singleWidth = (int) (paperWidth-2*MARGIN_X)/3;
	       	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		true); 
         }
    	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	
    	//=====================================================
    	if(!LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC))
    	{
    		//=====================================================
        	str = printPanel.vehicleIDLabelStr; //车辆识别代码
           	singleWidth = (int) (paperWidth-2*MARGIN_X)/6;
        	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		false); 

         	//-----------------------------------------------------
         	str = printPanel.vehicleIDLabelTextField.getTextFieldText();
           	singleWidth = (int) (paperWidth-2*MARGIN_X)/3;
        	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		false); 
        	
        	//=====================================================
        	str = printPanel.vehicleMotorIDLabelStr; //发动机号码
           	singleWidth = (int) (paperWidth-2*MARGIN_X)/6;
        	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		false); 

         	//-----------------------------------------------------
         	str = printPanel.vehicleMotorIDLabelTextField.getTextFieldText();
           	singleWidth = (int) (paperWidth-2*MARGIN_X)/3;
        	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		true); 

        	//-----------------------------------------------------
         	LINE_Y += TEXT_LINE_HEIGHT;
        	blockX = x1;
        	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	}
    	
    	//=====================================================
       	g2d.setFont(new Font("Serif", Font.BOLD, 11));
    	singleWidth = (int) (paperWidth-2*MARGIN_X)/7;

    	str = "数据项目"; //数据项目
      	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 	
      	
    	//-----------------------------------------------------
    	str = printPanel.originalCarStr; //原车数据
      	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 	
      	 	
    	//-----------------------------------------------------
    	str = printPanel.detectCarStr; //测量数据
      	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 	
      	 	 	
       	//-----------------------------------------------------
    	str = printPanel.absoluteCarStr; //绝对误差
     	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 	
      	 	 	
      	//-----------------------------------------------------
    	str = printPanel.compareCarStr; //相对误差
     	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 	
      	//-----------------------------------------------------
     	str = printPanel.carstandard; //判定标准
     	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 	
     	//-----------------------------------------------------
    	str = printPanel.singleDecisionStr; //单项判定
     	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true); 	
      	 	 	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	
    	//=====================================================
      	g2d.setFont(new Font("Serif", Font.PLAIN, 11));
    	singleWidth = (int) (paperWidth-2*MARGIN_X)/7;
      	 
    	str = printPanel.carLengthStr; //整车长
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 	

    	//-----------------------------------------------------
    	str = printPanel.jSettingLabelTextFieldOriginalLength.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	if(!printPanel.jSettingLabelTextFieldDetectLength.getTextFieldText().equals(""))
    		str = String.valueOf(printPanel.jSettingLabelTextFieldDetectLength.getTextFieldText())+" mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	str = printPanel.absoluteDifLengthTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	str = printPanel.compareDifLengthTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " %";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	//-----------------------------------------------------
    	if( printPanel.bNvramNewCar.bValue==true)
    	{
    		str ="1%或±50mm";
    	}
    	else
    	{
    		str ="2%或±100mm";
    	}
    	
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	//-----------------------------------------------------
    	str = printPanel.singleDecisionLengthTextField.getTextFieldText(); 
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true); 
    	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	
    	//=====================================================
    	str = printPanel.carWidthStr; //整车宽
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);  
    	
    	//-----------------------------------------------------
    	str = printPanel.jSettingLabelTextFieldOriginalWidth.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
	
       	//-----------------------------------------------------
    	if(!printPanel.jSettingLabelTextFieldDetectWidth.getTextFieldText().equals(""))
    		str = String.valueOf(printPanel.jSettingLabelTextFieldDetectWidth.getTextFieldText())+" mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	str = printPanel.absoluteDifWidthTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
	
       	//-----------------------------------------------------
    	str = printPanel.compareDifWidthTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " %";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
	//-------------------------------------------------------
    	if( printPanel.bNvramNewCar.bValue==true)
    	{
    		str ="1%或±50mm";
    	}
    	else
    	{
    		str ="2%或±100mm";
    	}
    	
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
     	//-----------------------------------------------------
    	str = printPanel.singleDecisionWidthTextField.getTextFieldText(); 
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true); 
    	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	
    	//=====================================================
    	str = printPanel.carHeightStr; //整车高
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);     	

    	//-----------------------------------------------------
    	str = printPanel.jSettingLabelTextFieldOriginalHeight.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	if(!printPanel.jSettingLabelTextFieldDetectHeight.getTextFieldText().equals(""))
    		str = String.valueOf(printPanel.jSettingLabelTextFieldDetectHeight.getTextFieldText())+" mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
	
       	//-----------------------------------------------------
    	str = printPanel.absoluteDifHeightTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	str = printPanel.compareDifHeightTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " %";
    	else
    		str = "/"; 
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
     	//-----------------------------------------------------
    	if( printPanel.bNvramNewCar.bValue==true)
    	{
    		str ="1%或±50mm";
    	}
    	else
    	{
    		str ="2%或±100mm";
    	}
    	
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	//-----------------------------------------------------
    	str = printPanel.singleDecisionHeightTextField.getTextFieldText(); 
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true); 
    	    	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	
    	//=====================================================
    	str = printPanel.carLanbanStr; //栏板高
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	str = printPanel.jSettingLabelTextFieldOriginalLanbanHeight.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";   
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	if(!printPanel.jSettingLabelTextFieldDetectLanbanHeight.getTextFieldText().equals(""))
    		str = String.valueOf(printPanel.jSettingLabelTextFieldDetectLanbanHeight.getTextFieldText())+" mm";
    	else
    		str = "/";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	str = printPanel.absoluteDifLanbanHeightTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
       	//-----------------------------------------------------
    	str = printPanel.compareDifLanbanHeightTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " %";
    	else
    		str = "/";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
       	//------------------------------------------------------
       	if( printPanel.bNvramNewCar.bValue==true)
    	{
    		str ="1%或±50mm";
    	}
    	else
    	{
    		str ="2%或±50mm";
    	}
    	
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	
     	//-----------------------------------------------------
    	str = printPanel.singleDecisionLanbanHeightLabelTextField.getTextFieldText(); 
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true); 
    	    	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);

    	//=====================================================
    	str = printPanel.carZJStr; 
       	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		false); 
        		
       	//-----------------------------------------------------
    	str = printPanel.jSettingLabelTextFieldOriginalZJ.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";  
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    		
       	//-----------------------------------------------------
    	if(!printPanel.jSettingLabelTextFieldDetectZJ.getTextFieldText().equals(""))
    		str = String.valueOf(printPanel.jSettingLabelTextFieldDetectZJ.getTextFieldText())+" mm";
    	else
    		str = "/";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    		
       	//-----------------------------------------------------
    	str = printPanel.absoluteDifZJTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " mm";
    	else
    		str = "/";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    		
       	//-----------------------------------------------------
    	str = printPanel.compareDifZJTextField.getTextFieldText();
    	if(str != null && !str.equals("") && !str.equals("0"))
    		str += " %";
    	else
    		str = "/"; 
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    		
       	//----------------------------------------------------
       	if( printPanel.bNvramNewCar.bValue==true)
    	{
    		str ="1%或±50mm";
    	}
    	else
    	{
    		str ="1%或±50mm";
    	}
    	
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
     	//-----------------------------------------------------
    	str = printPanel.singleDecisionZJLabelTextField.getTextFieldText(); 
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    		    	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	    	
    	//=====================================================
    	/*
    	str = "轴数";
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	    	
    	//-----------------------------------------------------
    	str = "轴距1";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	    	
    	//-----------------------------------------------------
    	str = "轴距2";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	   	
    	//-----------------------------------------------------
    	str = "轴距3";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	    	
    	//-----------------------------------------------------
    	str = "轴距4";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	    	
    	//-----------------------------------------------------
    	str = "轴距5";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true); 
    	    	
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	
    	//=====================================================
    	int zNum = 0;
    	for(int i = 0; i < printPanel.MAX_Z;i++)
    	{
        	if(!printPanel.jSettingLabelTextFieldDetectZ[i].getTextFieldText().equals(""))
        	{
        		if(zNum == 0)
        			zNum = 2;
        		else
        			zNum++;
        	}
    	}
    	str = String.valueOf(zNum);
    	//有时候用户在界面修改了轴距为0,导致轴数变小
//    	str = ContourDetectionTabPanelMain.zNumTextField.getText();
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false); 
    	    	
    	//-----------------------------------------------------
    	boolean bLast = false;
    	for(int i = 0; i < printPanel.MAX_Z;i++)
    	{
        	if(!printPanel.jSettingLabelTextFieldDetectZ[i].getTextFieldText().equals(""))
        		str = String.valueOf(printPanel.jSettingLabelTextFieldDetectZ[i].getTextFieldText())+" mm";
	    	else
	    		str = "/"; 
        	
        	if(i == (printPanel.MAX_Z-1))
        		bLast = true;
        	
           	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		bLast);         	
    	}
   
    	//-----------------------------------------------------
     	LINE_Y += TEXT_LINE_HEIGHT;
    	blockX = x1;
    	g2d.drawLine(x1, LINE_Y, x2, LINE_Y);
    	*/
    	
    	return LINE_Y;
    }
    
    int drawSingleBlock(
    	Graphics2D g2d,String str, 
    	int blockX, int singleWidth, boolean bMiddle,
    	int LINE_Y, int singleHeight, 
    	boolean bLastBlock)
    {
    	if(str != null)
    	{
	    	FontMetrics fm = g2d.getFontMetrics();
	    	Rectangle2D rec = fm.getStringBounds(str, g2d);  
	    	int stringWidth = (int) rec.getWidth();
	    	int stringHeight = (int) rec.getHeight();
	    	int descent = fm.getDescent();
	    	int iStrX = blockX;
	    	if(bMiddle)
	    	{
	    		iStrX += (int)(singleWidth-stringWidth)/2;
	    	}
	     	g2d.drawString(str, iStrX, LINE_Y+singleHeight-(singleHeight-stringHeight)/2-descent);
	     	
	 		blockX += singleWidth;
	     	if(!bLastBlock)
	     	{
		    	g2d.drawLine(blockX, LINE_Y, blockX, LINE_Y+singleHeight); //竖分隔线
	     	}

	     	return blockX;
    	}
    	else    		
    		return 0;
    }
    
    void drawPrintImage(Graphics2D g2d, int LINE_Y, double paperWidth, int height)
    {
    	int INTERNEL_MARGIN = 2;
    	int singleWidth = (int)(paperWidth-(MARGIN_X+INTERNEL_MARGIN)*2)/2;;
    	int blockX;
    	String str;
    	String strPrintFileName;
    	int STR_HEIGHT = 20;
    	int imageHeight = height/2 - STR_HEIGHT;
    	
     	g2d.setFont(new Font("Serif", Font.PLAIN, 11));
     	 
    	//=====================================================
     	strPrintFileName = printPanel.regenImageName(LMSConstValue.sNvramPrintImage[0].sValue,printPanel.carType);;
     	blockX = MARGIN_X+INTERNEL_MARGIN;
    	drawPrintImageSingle(g2d, strPrintFileName, blockX, LINE_Y, singleWidth, imageHeight);
    	
    	strPrintFileName = printPanel.regenImageName(LMSConstValue.sNvramPrintImage[1].sValue,printPanel.carType);;
    	blockX += (singleWidth+INTERNEL_MARGIN);
    	drawPrintImageSingle(g2d, strPrintFileName, blockX, LINE_Y, singleWidth, imageHeight);

       	//=====================================================
    	LINE_Y += imageHeight;
    	blockX = MARGIN_X;
    	
    	str = LMSConstValue.sNvramPrintImageTitle[0].sValue; 
       	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, STR_HEIGHT, 
           		true); 
        	
    	str = LMSConstValue.sNvramPrintImageTitle[1].sValue; 
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, STR_HEIGHT, 
       		true);
    	
    	//====================================================================
    	LINE_Y += STR_HEIGHT;
    	
    	strPrintFileName = printPanel.regenImageName(LMSConstValue.sNvramPrintImage[2].sValue,printPanel.carType);;
    	blockX = MARGIN_X+INTERNEL_MARGIN;
    	drawPrintImageSingle(g2d, strPrintFileName, blockX, LINE_Y, singleWidth, imageHeight);
    	
    	strPrintFileName = printPanel.regenImageName(LMSConstValue.sNvramPrintImage[3].sValue,printPanel.carType);;
    	blockX += (singleWidth+INTERNEL_MARGIN);
    	drawPrintImageSingle(g2d, strPrintFileName, blockX, LINE_Y, singleWidth, imageHeight);
    	
    	//=====================================================
    	LINE_Y += imageHeight;
    	blockX = MARGIN_X;
    	
    	str = LMSConstValue.sNvramPrintImageTitle[2].sValue; 
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, STR_HEIGHT, 
       		true);

    	str = LMSConstValue.sNvramPrintImageTitle[3].sValue; 
    	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, STR_HEIGHT, 
       		true);
    }
    
    void JlabelSetText(Graphics2D g2d,
    	int iStrX, int LINE_Y, int width,int singleHeight,
    	String longString) throws InterruptedException 
    {
        StringBuilder builder = new StringBuilder();
        char[] chars = longString.toCharArray();

        FontMetrics fontMetrics = g2d.getFontMetrics();
    	int descent = fontMetrics.getDescent();
    	Rectangle2D rec = fontMetrics.getStringBounds("AA", g2d);  
    	int stringWidth = (int) rec.getWidth();
    	int stringHeight = (int) rec.getHeight();

        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len) 
                        > width) {
                    break;
                }
            }
            builder.append(chars, start, len-1);
         	g2d.drawString(builder.toString().substring(start, start+len-1), iStrX, LINE_Y+singleHeight-(singleHeight-stringHeight)/2-descent);
         	LINE_Y+=stringHeight;
         	
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
     	g2d.drawString(builder.toString().substring(start, start+longString.length()-start), iStrX, LINE_Y+singleHeight-(singleHeight-stringHeight)/2-descent);
    }

    int drawOperator(Graphics2D g2d, int yStart, double paperWidth,double paperHeight)
    {    	
      	int x1 = MARGIN_X;
    	int x2 = (int)(paperWidth-MARGIN_X);
    	g2d.drawLine(x1, yStart, x2, yStart);

    	//-----------------------------------------------------------------------------       
        String str = null;
    	int LINE_Y = yStart;
        int singleWidth;
        int blockX = MARGIN_X;

        Font font = new Font("Serif", Font.PLAIN, 10);  
        g2d.setFont(font);
        singleWidth = (int) (paperWidth-2*MARGIN_X)*2/3;

		if(LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_SDGC)
			||LMSConstValue.sNvramCustomer.sValue.toUpperCase().equals(LMSConstValue.CUSTOMER_HGJN)	
		)
		{
			str = printPanel.beiZhuLabel.getText()+printPanel.beiZhuTextArea.getText();
		}
		else
		{
			str = "备注:";
		}
		try {
			JlabelSetText(
				g2d,
				blockX, LINE_Y, singleWidth,TEXT_LINE_HEIGHT,
			    str);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		str = "";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, false,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);
		
    	//-----------------------------------------------------------------------------       
        blockX = (int)(MARGIN_X+(4*(paperWidth-2*MARGIN_X)/6));
        
        font = new Font("Serif", Font.PLAIN, 14);  
        g2d.setFont(font);

    	//-----------------------------------------------------------------------------
        Operator operator = (Operator)printPanel.operatorComboBox.getSelectedItem();
        
        str = ContourDetectionTabPanelSetting.operatorNameLabel.getText();
        singleWidth = (int) (paperWidth-2*MARGIN_X)/6;
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);
       	
       	if(operator != null)
        {
        	str = operator.sOperatorName;
        }                
       	else
       	{
       		str = "";
       	}
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true);
       	
    	//----------------------------------------------------------------------------- 
       	/*
       	if(LMSConstValue.bNvramPrintOperatorID.bValue == true)
       	{
	        str = ContourDetectionTabPanelSetting.operatorIDLabel.getText();
	       	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		false);
           	
	        if(operator != null)
	        {
	        	str = operator.sOperatorID;
	        }
	        else
	        {
	        	str = "";
	        }
	        singleWidth = (int) (paperWidth-2*MARGIN_X)/2;
	       	blockX = drawSingleBlock(
           		g2d, str, 
           		blockX, singleWidth, true,
           		LINE_Y, TEXT_LINE_HEIGHT, 
           		true);
       	}
       	*/
    	LINE_Y += TEXT_LINE_HEIGHT;
    	
        return LINE_Y;
    }
    
    int drawDesicion(Graphics2D g2d, int yStart, double paperWidth,double paperHeight)
    {    	
      	int x1 = MARGIN_X + (int)(paperWidth-2*MARGIN_X)*2/3;
    	int x2 = (int)(paperWidth-MARGIN_X);
    	g2d.drawLine(x1, yStart, x2, yStart);

    	//-----------------------------------------------------------------------------       
        String str;
    	int LINE_Y = yStart;
        int singleWidth;
        int blockX = MARGIN_X;

        Font font = new Font("Serif", Font.PLAIN, 10);  
        g2d.setFont(font);

        singleWidth = (int) (paperWidth-2*MARGIN_X)*2/3;
		str = "";
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, false,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);

       	//-----------------------------------------------------------------------------      
        font = new Font("Serif", Font.PLAIN, 14);  
        g2d.setFont(font);

        singleWidth = (int) (paperWidth-2*MARGIN_X)/6;

        str = printPanel.decisionLabel.getText();
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		false);
       	
        str = printPanel.decisionTextField.getTextFieldText();
       	blockX = drawSingleBlock(
       		g2d, str, 
       		blockX, singleWidth, true,
       		LINE_Y, TEXT_LINE_HEIGHT, 
       		true);
   	
    	LINE_Y += TEXT_LINE_HEIGHT;
    	
        return LINE_Y;
    }
    
    int drawPrintEnd(Graphics2D g2d, int yStart, double paperWidth, double paperHeight)
    {    	
      	int x1 = MARGIN_X;
    	int x2 = (int)(paperWidth-MARGIN_X);
    	g2d.drawLine(x1, yStart, x2, yStart);

    	//-----------------------------------------------------------------------------
        //
    	Font font = new Font("Serif", Font.PLAIN, 10);  
        g2d.setFont(font);
        
        FontMetrics fm = g2d.getFontMetrics();
        String str;
        int stringHeight;
        int y = yStart;
        int Y_OFFSET = 2;
        Rectangle2D rec;
                
        str = "注:";
        /*
          if(printPanel.bNvramNewCar.bValue == true)
         
        {
        	str += "注册车测量差值超出1%,或±50mm,的为不合格,";
        }
        else
        {
        	str += "在用车测量差值超出2%,或±100mm,的为不合格,";
        }
        str += "\"/\"代表无";
    	rec = fm.getStringBounds(str, g2d);  
        stringHeight = (int) rec.getHeight();
        g2d.drawString(str, x1, y+stringHeight);
        
        y += (stringHeight+Y_OFFSET);*/

        //=================================================================
        if(!LMSConstValue.sNvramPrintFootStringLine2.sValue.equals(""))
        {
	        str = LMSConstValue.sNvramPrintFootStringLine2.sValue;
	    	rec = fm.getStringBounds(str, g2d);  
	        stringHeight = (int) rec.getHeight();
	        g2d.drawString(str, x1, y+stringHeight);
	
	        y += (stringHeight+Y_OFFSET);
        }
        
        if(!LMSConstValue.sNvramPrintFootStringLine3.sValue.equals(""))
        {
	        str = LMSConstValue.sNvramPrintFootStringLine3.sValue;
	    	rec = fm.getStringBounds(str, g2d);  
	        stringHeight = (int) rec.getHeight();
	        g2d.drawString(str, x1, y+stringHeight);
	
	        y += (stringHeight+Y_OFFSET);
        }
        
        return y;
    }
    
    void drawPrintImageSingle(Graphics2D g2d, String fileName, int xStart, int yStart, int width, int height)
    {    	
    	ImageIcon image = new LMSFunction().getScaledIconImage(fileName,width,height); 
    	if(image != null)
    	{
    		g2d.drawImage(image.getImage(), xStart, yStart, null);
    	}
    }
    
    void drawPrint(Graphics2D g2d, double paperWidth, double paperHeight)
    {
    	int yStart;
    	
    	yStart =drawPrintTitle(g2d, paperWidth, paperHeight);
    	
    	yStart += (2*yOffset);
    	yStart = drawPrintOtherMessage(g2d, yStart, paperWidth, paperHeight);
    	
    	yStart += yOffset;
    	drawPrintMainFrame(g2d, yStart, paperWidth, paperHeight);
    	
    	yStart = drawPrintMainMessage(g2d, yStart, paperWidth, paperHeight);
    	
    	yStart += yOffset;
    	int yEnd = (int) (paperHeight - END_HEIGHT - TEXT_LINE_HEIGHT*2);
    	drawPrintImage(g2d, yStart, paperWidth, yEnd-yStart);
    	
    	yStart = drawOperator(g2d, yEnd, paperWidth, paperHeight);
    	
    	yStart = drawDesicion(g2d, yStart, paperWidth, paperHeight);
    	
    	yStart = (int) (paperHeight - END_HEIGHT);
    	drawPrintEnd(g2d, yStart, paperWidth, paperHeight);
    }
}
