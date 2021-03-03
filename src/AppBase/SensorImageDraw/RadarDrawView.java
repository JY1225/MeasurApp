package AppBase.SensorImageDraw;

import AppBase.ImplementPC.SensorDrawViewImplementPC;
import SensorBase.LMSConstValue;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;

public class RadarDrawView extends SensorDrawViewImplementPC{
	private final static String DEBUG_TAG="RadarDrawView";
	
	public static int[] lmsPosition = new int[LMSConstValue.RADAR_SENSOR_NUM];

	final int TEXT_HEIGHT = 15; 
	final int TEXT_OFFSET = 2; 
	public void lmsHWMeasureDataMonitor(
			int sensorID,
			int canvasWidth,int canvasHeight,
			int iLeftWindow,int iRightWindow,int iFrontWindow,
			int whPosition,
			int iValidStartAngle,int iValidEndAngle,
			int iGroundStartAngle,int iGroundEndAngle,
			int iFrontEdgeWindow,int yOffset,
			int iLREdgeWindow,int xOffset,
			int dataLength,
			int x_point[],int y_point[]) 
	{		
		lmsDrawCoordinate(
				sensorID,
				canvasWidth,canvasHeight,
				iLeftWindow,iRightWindow,iFrontEdgeWindow,
				whPosition,
				xOffset,yOffset,
				iLREdgeWindow);
		
		lmsHWDrawHintFrame(
				sensorID,
				canvasWidth,canvasHeight,
				iLeftWindow,iRightWindow,iFrontWindow,
				whPosition,
				LMSConstValue.yBaseValue[sensorID],
				iValidStartAngle,iValidEndAngle,
				iGroundStartAngle,iGroundEndAngle,
				iFrontEdgeWindow,yOffset,
				iLREdgeWindow,xOffset);
		
		lmsDrawPointWithCarDetect(
				sensorID,
				canvasWidth,canvasHeight,
				iLeftWindow,iRightWindow,iFrontWindow,
				iFrontEdgeWindow,yOffset,
				iLREdgeWindow,xOffset,
				dataLength,
				x_point,y_point);
	}

	public enum enumDrawType{
		WIDTH_HEIGHT_DETECT,
		LMS_SETTING,
		ANTI_COLLITION,
    } 	

	public void lmsDrawStop(int canvasWidth,int canvasHeight)
	{
		String str = "节省资源,关闭监控图";
		drawText(str, canvasWidth-200, canvasHeight-100);
	}
	
	public void lmsDrawCoordinate(
			int sensorID,
			int canvasWidth,int canvasHeight,
			int lWidthDetect,int rWidthDetect,int iFrontEdgeWindow,
			int whPosition,
			int xOffset,int yOffset,
			int iLREdgeWindow)
	{
		int i;
		int mLine;
		int heightStep,widthStep;
		int lineY1,lineY2,textY;
		
		//画坐标图
		paintSetColorRED();

		int coorBase = lWidthDetect+rWidthDetect+2*iLREdgeWindow;
		lmsPosition[sensorID] = (lWidthDetect+iLREdgeWindow)*canvasWidth/coorBase;

		textY = canvasHeight+yOffset;
		if(LMSConstValue.bUpDownTurn[sensorID] == false)
		{
			textY = (canvasHeight - textY + TEXT_HEIGHT);

			drawText("0°", canvasWidth-15+xOffset, textY+12);   		
			drawText("180°", 0+TEXT_OFFSET+xOffset, textY+12); 

			drawText("右", canvasWidth-15+xOffset, textY+12*2);   		
			drawText("左", 0+TEXT_OFFSET+xOffset, textY+12*2); 
			if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
			{
				drawText("宽高龙门位置", lmsPosition[sensorID]-whPosition*canvasWidth/coorBase+TEXT_OFFSET+xOffset, textY+12*2);
			}
			
			drawText("270°", lmsPosition[sensorID]+TEXT_OFFSET+xOffset, textY-20); 
			drawText("90°", lmsPosition[sensorID]+TEXT_OFFSET+xOffset, textY+16); 
		}
		else
		{
			drawText("0°", canvasWidth-15+xOffset, textY-12);   		
			drawText("180°", 0+TEXT_OFFSET+xOffset, textY-12);   	

			drawText("右", canvasWidth-15+xOffset, textY-12*2);   		
			drawText("左", 0+TEXT_OFFSET+xOffset, textY-12*2);   	
			if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
			{
				drawText("宽高龙门位置", lmsPosition[sensorID]-whPosition*canvasWidth/coorBase+TEXT_OFFSET+xOffset, textY-12*2);
			}
			
			drawText("90°", lmsPosition[sensorID]+TEXT_OFFSET+xOffset, textY-20); 
			drawText("270°", lmsPosition[sensorID]+TEXT_OFFSET+xOffset, textY+16); 
		}
		
		drawText("RADAR", lmsPosition[sensorID]+TEXT_OFFSET+xOffset, textY-TEXT_OFFSET);

    	paintSetColorBLACK(); 
    	
    	//*-------------------------------------------------------------
    	if(coorBase<4000)
    		widthStep = 4000/10;
    	else if(coorBase<5000)
    		widthStep = 5000/10;
    	else if(coorBase<10000)
    		widthStep = 10000/10;
    	else if(coorBase<20000)
    		widthStep = 20000/10;
    	else if(coorBase<30000)
    		widthStep = 30000/10;
    	else if(coorBase<40000)
    		widthStep = 40000/10;
    	else if(coorBase<50000)
    		widthStep = 50000/10;
    	else if(coorBase<60000)
    		widthStep = 60000/10;
    	else if(coorBase<70000)
    		widthStep = 70000/10;
    	else if(coorBase<80000)
    		widthStep = 80000/10;
    	else 
    		widthStep = 90000/10;
	
    	//==================================================================================
    	//竖
    	textY = canvasHeight+yOffset;
		if(LMSConstValue.bUpDownTurn[sensorID] == false)
			textY = (canvasHeight - textY + TEXT_HEIGHT);
		lineY1 = 0;
		lineY2 = canvasHeight;
		if(LMSConstValue.bUpDownTurn[sensorID] == false)
		{
			lineY1 = (canvasHeight - lineY1);
			lineY2 = (canvasHeight - lineY2);
		}
    	for(i=-(rWidthDetect+iLREdgeWindow)*3/widthStep;i<=(lWidthDetect+iLREdgeWindow)*3/widthStep;i++) 
    	{	
    		mLine = lmsPosition[sensorID]-widthStep*i*canvasWidth/coorBase;
    		drawLine(mLine+xOffset, lineY1, mLine+xOffset, lineY2); 
    		if(i!=0)
    		{
    			drawText(((float)(i*widthStep)/1000)+"m", mLine+TEXT_OFFSET+xOffset, textY-TEXT_OFFSET);
    		}
    	}
    	
    	//==================================================================================
    	//横
 		heightStep = iFrontEdgeWindow/LMSConstValue.MOVE_STEP;
	   	for(i=-LMSConstValue.MOVE_STEP*2;i<=LMSConstValue.MOVE_STEP*2;i++) 
    	{	
    		mLine = canvasHeight-heightStep*i*canvasHeight/iFrontEdgeWindow;
    		
    		lineY1 = mLine+yOffset;
    		lineY2 = mLine+yOffset;
    		if(LMSConstValue.bUpDownTurn[sensorID] == false)
    		{
    			lineY1 = (canvasHeight - lineY1);
    			lineY2 = (canvasHeight - lineY2);
    		}
    		drawLine(0, lineY1, canvasWidth, lineY2);
        	
    		textY = mLine+yOffset;
    		if(LMSConstValue.bUpDownTurn[sensorID] == false)
    			textY = (canvasHeight - textY + TEXT_HEIGHT);
    		if(i!=0)
    		{
    			drawText(((float)(i*heightStep)/1000)+"m", lmsPosition[sensorID]+TEXT_OFFSET+xOffset, textY-TEXT_OFFSET);
    		}
    	}    
	}
	
	void drawCoorLine(
		int sensorID,
		int canvasWidth,int canvasHeight,
		int iFrontEdgeWindow,int xOffset,int yOffset,
		int coorBase,
		int x1,int y1,int x2,int y2)
	{
		int xCoor1,xCoor2;
		int yCoor1,yCoor2;

		xCoor1 = lmsPosition[sensorID]-x1*canvasWidth/coorBase+xOffset;
		xCoor2 = lmsPosition[sensorID]-x2*canvasWidth/coorBase+xOffset;
		yCoor1 = (int)(iFrontEdgeWindow-y1)*canvasHeight/iFrontEdgeWindow+yOffset;
		yCoor2 = (int)(iFrontEdgeWindow-y2)*canvasHeight/iFrontEdgeWindow+yOffset;
		if(LMSConstValue.bUpDownTurn[sensorID] == false)
		{
			yCoor1 = (canvasHeight - yCoor1);
			yCoor2 = (canvasHeight - yCoor2);
		}
		drawLine(xCoor1, yCoor1, xCoor2, yCoor2); 
	}
		
	LinePoint determineDownPoint(LinePoint pointCmp1,LinePoint pointCmp2,LinePoint pointCmp3)
	{
		LinePoint point,pointCmp;

		pointCmp = null;
		point = null;
		if(pointCmp1 != null)
			point = pointCmp1;	

		pointCmp = pointCmp2;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.y < pointCmp.y)
				point = pointCmp; 
		}

		pointCmp = pointCmp3;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.y < pointCmp.y)
				point = pointCmp; 
		}
			
		return point;
	}
	
	LinePoint determineUpPoint(LinePoint pointCmp1,LinePoint pointCmp2,LinePoint pointCmp3)
	{
		LinePoint point,pointCmp;

		pointCmp = null;
		point = null;
		if(pointCmp1 != null)
			point = pointCmp1;	

		pointCmp = pointCmp2;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.y > pointCmp.y)
				point = pointCmp; 
		}

		pointCmp = pointCmp3;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.y > pointCmp.y)
				point = pointCmp; 
		}
			
		return point;
	}
	
	LinePoint determineLeftPoint(LinePoint pointCmp1,LinePoint pointCmp2,LinePoint pointCmp3)
	{
		LinePoint point,pointCmp;

		pointCmp = null;
		point = null;
		if(pointCmp1 != null)
			point = pointCmp1;	
		pointCmp = pointCmp2;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.x > pointCmp.x)
				point = pointCmp; 
		}
		pointCmp = pointCmp3;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.x > pointCmp.x)
				point = pointCmp; 
		}
			
		return point;
	}
	
	LinePoint determineRightPoint(LinePoint pointCmp1,LinePoint pointCmp2,LinePoint pointCmp3)
	{
		LinePoint point,pointCmp;

		pointCmp = null;
		point = null;
		if(pointCmp1 != null)
			point = pointCmp1;	
		pointCmp = pointCmp2;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.x < pointCmp.x)
				point = pointCmp; 
		}
		pointCmp = pointCmp3;
		if(pointCmp != null)
		{
			if(point == null)
				point = pointCmp;	
			else if(point.x < pointCmp.x)
				point = pointCmp; 
		}
			
		return point;
	}
	
	class LinePoint 
	{ 
		double x; 	
		double y; 
		
		LinePoint(double x,double y)
		{
			this.x = x;
			this.y = y;
		}
	}; 

	LinePoint getinsectpoint(LinePoint a,LinePoint b,LinePoint c,LinePoint d) 
	{  
		double delta,r,u; 

		delta=(b.x-a.x)*(c.y-d.y)-(c.x-d.x)*(b.y-a.y); 
	 
		if(delta==0)  
		{ 
			return null;
		} 
		else 
		{ 
			LinePoint p = new LinePoint(0,0); 

			r=((c.x-a.x)*(c.y-d.y)-(c.x-d.x)*(c.y-a.y))/delta; 
			u=((b.x-a.x)*(c.y-a.y)-(c.x-a.x)*(b.y-a.y))/ delta; 
	 
			if((r>=0&&r<=1)&&(u>=0&&u<=1)) 
			{ 
				p.x=a.x+r*(b.x-a.x);  
				p.y=a.y+r*(b.y-a.y); 
			}  
			else 
			{ 
				return null;
			}
			
			return p; 
		}  	 
	}  
	
	public void lmsHWDrawHintFrame(
			int sensorID,
			int canvasWidth,int canvasHeight,
			int leftDetect,int rightDetect,int frontWindow,
			int whPosition,
			int baseValue,
			int iValidStartAngle,int iValidEndAngle,
			int iGroundStartAngle,int iGroundEndAngle,
			int iFrontEdgeWindow,int yOffset,
			int iLREdgeWindow,int xOffset)
	{		
		boolean bValidBaseValue;
		int frontValue;
		if(baseValue ==0)
		{
			bValidBaseValue = false;
			
			frontValue = 1000;
		}
		else
		{
			bValidBaseValue = true;
			
			frontValue = baseValue;
		}
		
		//==========================================================================
		int maxLevel=0;
		if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			for(int level=0;level<LMSConstValue.ANTI_LEVEL;level++)
			{
				if(LMSConstValue.iAntiLevel[sensorID][level] !=0)
				{
					maxLevel = level;
					frontValue = LMSConstValue.iAntiLevel[sensorID][level];					
				}
			}
		}

		//==========================================================================
		int lineY1,lineY2,textY;
//		int mFrontWindowLine = 0;
				
		int coorBase = leftDetect+rightDetect+2*iLREdgeWindow;
		
 	    //================================================================================
 	   	int xStartAngle = (int) -((99999)*Math.cos((float)(iValidStartAngle/10)*Math.PI/180));
 	    int yStartAngle = (int) ((99999)*Math.sin((float)(iValidStartAngle/10)*Math.PI/180));
		LinePoint pStartAngle1 = new LinePoint(0, 0);
		LinePoint pStartAngle2 = new LinePoint(xStartAngle, yStartAngle);

 	   	int xEndAngle = (int) -((99999)*Math.cos((float)(iValidEndAngle/10)*Math.PI/180));
 	    int yEndAngle = (int) ((99999)*Math.sin((float)(iValidEndAngle/10)*Math.PI/180));
		LinePoint pEndAngle1 = new LinePoint(0, 0);
		LinePoint pEndAngle2 = new LinePoint(xEndAngle, yEndAngle);

		LinePoint pLeftWindow1 = new LinePoint(leftDetect, -99999);
		LinePoint pLeftWindow2 = new LinePoint(leftDetect, 99999);

		LinePoint pRightWindow1 = new LinePoint(-rightDetect, -99999);
		LinePoint pRightWindow2 = new LinePoint(-rightDetect, 99999);

		LinePoint pGround1 = new LinePoint(99999, frontValue);
		LinePoint pGround2 = new LinePoint(-99999, frontValue);
		
		LinePoint pFrontWindow1 = new LinePoint(99999, frontWindow);
		LinePoint pFrontWindow2 = new LinePoint(-99999, frontWindow);
		

 	    //================================================================================
		LinePoint point_startAngle_leftWindow = getinsectpoint(pStartAngle1, pStartAngle2, pLeftWindow1, pLeftWindow2);
		LinePoint point_startAngle_rightWindow = getinsectpoint(pStartAngle1, pStartAngle2, pRightWindow1, pRightWindow2);
		LinePoint point_startAngle_frontWindow = getinsectpoint(pStartAngle1, pStartAngle2, pFrontWindow1, pFrontWindow2);
		LinePoint point_startAngle_ground = getinsectpoint(pStartAngle1, pStartAngle2, pGround1, pGround2);
		LinePoint point_endAngle_leftWindow = getinsectpoint(pEndAngle1, pEndAngle2, pLeftWindow1, pLeftWindow2);
		LinePoint point_endAngle_rightWindow = getinsectpoint(pEndAngle1, pEndAngle2, pRightWindow1, pRightWindow2);
		LinePoint point_endAngle_frontWindow = getinsectpoint(pEndAngle1, pEndAngle2, pFrontWindow1, pFrontWindow2);
		LinePoint point_endAngle_ground = getinsectpoint(pEndAngle1, pEndAngle2, pGround1, pGround2);
		LinePoint point_ground_leftWindow = getinsectpoint(pGround1, pGround2, pLeftWindow1, pLeftWindow2);
		LinePoint point_ground_rightWindow = getinsectpoint(pGround1, pGround2, pRightWindow1, pRightWindow2);
		LinePoint point_frontWindow_leftWindow = getinsectpoint(pFrontWindow1, pFrontWindow2, pLeftWindow1, pLeftWindow2);
		LinePoint point_frontWindow_rightWindow = getinsectpoint(pFrontWindow1, pFrontWindow2, pRightWindow1, pRightWindow2);
	
 	    //================================================================================
		LinePoint pAntiLevelGround1[] = new LinePoint[LMSConstValue.ANTI_LEVEL];
		LinePoint pAntiLevelGround2[] = new LinePoint[LMSConstValue.ANTI_LEVEL];

		LinePoint point_startAngle_AntiLevelground[] = new LinePoint[LMSConstValue.ANTI_LEVEL];
		LinePoint point_endAngle_AntiLevelground[] = new LinePoint[LMSConstValue.ANTI_LEVEL];
		
		if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			for(int level=0;level<LMSConstValue.ANTI_LEVEL;level++)
			{
				if(LMSConstValue.iAntiLevel[sensorID][level] !=0)
				{
					pAntiLevelGround1[level] = new LinePoint(99999, LMSConstValue.iAntiLevel[sensorID][level]);
					pAntiLevelGround2[level] = new LinePoint(-99999, LMSConstValue.iAntiLevel[sensorID][level]);
					
					point_startAngle_AntiLevelground[level] = getinsectpoint(pStartAngle1, pStartAngle2, pAntiLevelGround1[level], pAntiLevelGround2[level]);
					point_endAngle_AntiLevelground[level] = getinsectpoint(pEndAngle1, pEndAngle2, pAntiLevelGround1[level], pAntiLevelGround2[level]);
				}
			}
		}
		
 	    //================================================================================
		int x1,y1,x2,y2;
		LinePoint pointZero = new LinePoint(0,0);
		
		//画左窗口线
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX)
			||LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			paintSetColorGREEN();
			x1 = (int) pLeftWindow1.x;
			y1 = (int) pLeftWindow1.y;
			x2 = (int) pLeftWindow2.x;
			y2 = (int) pLeftWindow2.y;
			if(point_endAngle_leftWindow != null && y1 < point_endAngle_leftWindow.y)
				y1 = (int) point_endAngle_leftWindow.y;
			if(point_startAngle_leftWindow != null 
				&& !(point_startAngle_leftWindow.x ==0 && point_startAngle_leftWindow.y ==0) 
				&& y2 > point_startAngle_leftWindow.y
			)
			{
				y2 = (int) point_startAngle_leftWindow.y;
			}
			if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
			{
				if(point_ground_leftWindow != null && y2 > point_ground_leftWindow.y)
				{
					y2 = (int) point_ground_leftWindow.y;
				}
			}
			else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
			{
				if(point_ground_leftWindow != null && y1 < point_ground_leftWindow.y)
				{
					y1 = (int) point_ground_leftWindow.y;
				}
				if(point_frontWindow_leftWindow != null && y2 > point_frontWindow_leftWindow.y)
				{
					y2 = (int) point_frontWindow_leftWindow.y;
				}
			}
			if(y1 < y2)
			{
				drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
					(int) x1,(int) y1,(int) x2,(int) y2);
			}
		}
		
		//画右窗口线
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX)
			||LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			paintSetColorGREEN();
			x1 = (int) pRightWindow1.x;
			y1 = (int) pRightWindow1.y;
			x2 = (int) pRightWindow2.x;
			y2 = (int) pRightWindow2.y;
			if(point_startAngle_rightWindow != null && y1 < point_startAngle_rightWindow.y)
				y1 = (int) point_startAngle_rightWindow.y;
			if(point_endAngle_rightWindow != null 
				&& !(point_endAngle_rightWindow.x == 0 && point_endAngle_rightWindow.y == 0)	
				&& y2 > point_endAngle_rightWindow.y
			)
			{
				y2 = (int) point_endAngle_rightWindow.y;
			}
			if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
			{
				if(point_ground_rightWindow != null && y2 > point_ground_rightWindow.y)
				{
					y2 = (int) point_ground_rightWindow.y;
				}
			}
			else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
			{
				if(point_ground_rightWindow != null && y1 < point_ground_rightWindow.y)
				{
					y1 = (int) point_ground_rightWindow.y;
				}
				if(point_frontWindow_rightWindow != null && y2 > point_frontWindow_rightWindow.y)
				{
					y2 = (int) point_frontWindow_rightWindow.y;
				}
			}
			if(y1 < y2)
			{
				drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
					(int) x1,(int) y1,(int) x2,(int) y2);
			}
		}

		//画起始角度
		paintSetColorGREEN();
		x1 = (int) pStartAngle1.x;
		y1 = (int) pStartAngle1.y;
		x2 = (int) pStartAngle2.x;
		y2 = (int) pStartAngle2.y;
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
		{
			if(point_startAngle_ground != null && y2 > point_startAngle_ground.y)
			{
				x2 = (int) point_startAngle_ground.x;
				y2 = (int) point_startAngle_ground.y;
			}
			if(point_startAngle_leftWindow != null && x2 > point_startAngle_leftWindow.x)
			{
				x2 = (int) point_startAngle_leftWindow.x;
				y2 = (int) point_startAngle_leftWindow.y;
			}
			if(point_startAngle_rightWindow != null && x2 < point_startAngle_rightWindow.x)
			{
				x2 = (int) point_startAngle_rightWindow.x;
				y2 = (int) point_startAngle_rightWindow.y;
			}
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			if(point_startAngle_ground != null && y2 < point_startAngle_ground.y)
			{
				x2 = (int) point_startAngle_ground.x;
				y2 = (int) point_startAngle_ground.y;
			}
			if(point_startAngle_leftWindow != null && x2 > point_startAngle_leftWindow.x)
			{
				x2 = (int) point_startAngle_leftWindow.x;
				y2 = (int) point_startAngle_leftWindow.y;
			}
			if(point_startAngle_frontWindow != null && y2 > point_startAngle_frontWindow.y)
			{
				x2 = (int) point_startAngle_frontWindow.x;
				y2 = (int) point_startAngle_frontWindow.y;
			}			
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			if(point_startAngle_ground != null && y2 < point_startAngle_ground.y)
			{
				x2 = (int) point_startAngle_ground.x;
				y2 = (int) point_startAngle_ground.y;
			}
			if(point_startAngle_frontWindow != null && y2 > point_startAngle_frontWindow.x)
			{
				x2 = (int) point_startAngle_frontWindow.x;
				y2 = (int) point_startAngle_frontWindow.y;
			}
			if(point_startAngle_rightWindow != null && x2 < point_startAngle_rightWindow.x)
			{
				x2 = (int) point_startAngle_rightWindow.x;
				y2 = (int) point_startAngle_rightWindow.y;
			}
		}
		drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
			(int) x1,(int) y1,(int) x2,(int) y2);

		//画终止角度
		paintSetColorGREEN();
		x1 = (int) pEndAngle1.x;
		y1 = (int) pEndAngle1.y;
		x2 = (int) pEndAngle2.x;
		y2 = (int) pEndAngle2.y;
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
		{
			if(point_endAngle_ground != null && y2 > point_endAngle_ground.y)
			{
				x2 = (int) point_endAngle_ground.x;
				y2 = (int) point_endAngle_ground.y;
			}		
			if(point_endAngle_leftWindow != null && x2 > point_endAngle_leftWindow.x)
			{
				x2 = (int) point_endAngle_leftWindow.x;
				y2 = (int) point_endAngle_leftWindow.y;
			}
			if(point_endAngle_rightWindow != null && x2 < point_endAngle_rightWindow.x)
			{
				x2 = (int) point_endAngle_rightWindow.x;
				y2 = (int) point_endAngle_rightWindow.y;
			}
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			if(point_endAngle_ground != null && y2 < point_endAngle_ground.y)
			{
				x2 = (int) point_endAngle_ground.x;
				y2 = (int) point_endAngle_ground.y;
			}		
			if(point_endAngle_leftWindow != null && x2 > point_endAngle_leftWindow.x)
			{
				x2 = (int) point_endAngle_leftWindow.x;
				y2 = (int) point_endAngle_leftWindow.y;
			}
			if(point_endAngle_frontWindow != null && y2 > point_endAngle_frontWindow.y)
			{
				x2 = (int) point_endAngle_frontWindow.x;
				y2 = (int) point_endAngle_frontWindow.y;
			}
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			if(point_endAngle_ground != null && y2 < point_endAngle_ground.y)
			{
				x2 = (int) point_endAngle_ground.x;
				y2 = (int) point_endAngle_ground.y;
			}		
			if(point_endAngle_frontWindow != null && x2 > point_endAngle_frontWindow.x)
			{
				x2 = (int) point_endAngle_frontWindow.x;
				y2 = (int) point_endAngle_frontWindow.y;
			}
			if(point_endAngle_rightWindow != null && x2 < point_endAngle_rightWindow.x)
			{
				x2 = (int) point_endAngle_rightWindow.x;
				y2 = (int) point_endAngle_rightWindow.y;
			}
		}
		drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
			(int) x1,(int) y1,(int) x2,(int) y2);

		//画地线
		paintSetColorYELLOW();
		x1 = (int) pGround1.x;
		y1 = (int) pGround1.y;
		x2 = (int) pGround2.x;
		y2 = (int) pGround2.y;
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.UP_FIX))
		{
			if(x1 > pLeftWindow1.x)
				x1 = (int) pLeftWindow1.x;
			if(point_endAngle_ground != null && x1 > point_endAngle_ground.x)
				x1 = (int) point_endAngle_ground.x;
			if(x2 < pRightWindow1.x)
				x2 = (int) pRightWindow1.x;
			if(point_startAngle_ground != null && x2 < point_startAngle_ground.x)
				x2 = (int) point_startAngle_ground.x;
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			if(x1 > pLeftWindow1.x)
				x1 = (int) pLeftWindow1.x;
			if(point_endAngle_ground != null && x2 < point_endAngle_ground.x)
				x2 = (int) point_endAngle_ground.x;
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			if(x2 < pRightWindow1.x)
				x2 = (int) pRightWindow1.x;
			if(point_startAngle_ground != null && x2 > point_startAngle_ground.x)
				x2 = (int) point_startAngle_ground.x;
		}
		if(x1 > x2)
		{
			drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
				(int) x1,(int) y1,(int) x2,(int) y2);
		}
		
		//画前窗口线
		paintSetColorGREEN();
		if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_LEFT_WINDOW))
		{
			x1 = (int) pFrontWindow1.x;
			y1 = (int) pFrontWindow1.y;
			x2 = (int) pFrontWindow2.x;
			y2 = (int) pFrontWindow2.y;
			if(x1 > pLeftWindow1.x)
				x1 = (int) pLeftWindow1.x;
			if(point_endAngle_frontWindow != null && x1 > point_endAngle_frontWindow.x)
				x1 = (int) point_endAngle_frontWindow.x;
			if(point_startAngle_frontWindow != null && x2 < point_startAngle_frontWindow.x)
				x2 = (int) point_startAngle_frontWindow.x;
			if(x1 > x2)
			{
				drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
					(int) x1,(int) y1,(int) x2,(int) y2);
			}
		}
		else if(LMSConstValue.fixMethod[sensorID].key.equals(LMSConstValue.EnumFixMethod.GROUND_FIX_RIGHT_WINDOW))
		{
			x1 = (int) pFrontWindow1.x;
			y1 = (int) pFrontWindow1.y;
			x2 = (int) pFrontWindow2.x;
			y2 = (int) pFrontWindow2.y;
			if(x2 < pRightWindow1.x)
				x2 = (int) pRightWindow1.x;
			if(point_endAngle_frontWindow != null && x1 > point_endAngle_frontWindow.x)
				x1 = (int) point_endAngle_frontWindow.x;
			if(point_startAngle_frontWindow != null && x2 < point_startAngle_frontWindow.x)
				x2 = (int) point_startAngle_frontWindow.x;
			if(x1 > x2)
			{
				drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
					(int) x1,(int) y1,(int) x2,(int) y2);
			}
		}
		
		//画宽高龙门位置线
		if(LMSConstValue.radarFunctionType[sensorID] == LMSConstValue.enumRadarFunctionType.LONG_SENSOR_ID)
		{
			LinePoint pWHPosition1 = new LinePoint(whPosition, -99999);
			LinePoint pWHPosition2 = new LinePoint(whPosition, 99999);

			paintSetColorRED();
			x1 = (int) pWHPosition1.x;
			y1 = (int) pWHPosition1.y;
			x2 = (int) pWHPosition2.x;
			y2 = (int) pWHPosition2.y;
			drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
				(int) x1,(int) y1,(int) x2,(int) y2);			
		}
		
		//画防撞窗口线
		paintSetColorGREEN();
		if(LMSConstValue.defaultDetectType == enumDetectType.ANTI_COLLITION)
		{
			for(int level=0;level<LMSConstValue.ANTI_LEVEL;level++)
			{
				if(LMSConstValue.iAntiLevel[sensorID][level] !=0)
				{
					x1 = (int) pAntiLevelGround1[level].x;
					y1 = (int) pAntiLevelGround1[level].y;
					x2 = (int) pAntiLevelGround2[level].x;
					y2 = (int) pAntiLevelGround2[level].y;
					if(x1 > pLeftWindow1.x)
						x1 = (int) pLeftWindow1.x;
					if(point_endAngle_AntiLevelground[level] != null && x1 > point_endAngle_AntiLevelground[level].x)
						x1 = (int) point_endAngle_AntiLevelground[level].x;
					if(x2 < pRightWindow1.x)
						x2 = (int) pRightWindow1.x;
					if(point_startAngle_AntiLevelground[level] != null && x2 < point_startAngle_AntiLevelground[level].x)
						x2 = (int) point_startAngle_AntiLevelground[level].x;
					if(x1 > x2)
					{
						drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,
							(int) x1,(int) y1,(int) x2,(int) y2);
					}
				}
			}
		}
		//=============================================================================
	   	int xAngleDisplay = (int) -((99999)*Math.cos((float)(LMSConstValue.iAngleDisplay[sensorID]/10)*Math.PI/180));
 	    int yAngleDisplay = (int) ((99999)*Math.sin((float)(LMSConstValue.iAngleDisplay[sensorID]/10)*Math.PI/180));
		if(LMSConstValue.bAngleDisplay[sensorID] == true)
		{
			paintSetColorPINK();

			//画角度显示线
			x1 = 0;
			x2 = xAngleDisplay;
			y1 = 0;
			y2 = yAngleDisplay;
			drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,x1,y1,x2,y2);
		}
		
		//画地面起始,终止角
		//=============================================================================
	   	int xGroundStartAngle = (int) -((99999)*Math.cos((float)(iGroundStartAngle/10)*Math.PI/180));
 	    int yGroundStartAngle = (int) ((99999)*Math.sin((float)(iGroundStartAngle/10)*Math.PI/180));
		
 	   paintSetColorMAGENTA();
		x1 = 0;
		x2 = xGroundStartAngle;
		y1 = 0;
		y2 = yGroundStartAngle;
		drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,x1,y1,x2,y2);
		
		//-------------------------------------------------------------------------------------
		int xGroundEndAngle = (int) -((99999)*Math.cos((float)(iGroundEndAngle/10)*Math.PI/180));
 	    int yGroundEndAngle = (int) ((99999)*Math.sin((float)(iGroundEndAngle/10)*Math.PI/180));
		
 	   paintSetColorMAGENTA();
		x1 = 0;
		x2 = xGroundEndAngle;
		y1 = 0;
		y2 = yGroundEndAngle;
		drawCoorLine(sensorID,canvasWidth,canvasHeight,iFrontEdgeWindow,xOffset,yOffset,coorBase,x1,y1,x2,y2);
		
		//=============================================================================
     	//车道
     	if(LMSConstValue.defaultDetectType == enumDetectType.WH_1_HIGH_SPEED
   			||LMSConstValue.defaultDetectType == enumDetectType.LM1)
     	{	
     		int iCarRoadWidthSum = 0;
     		int mCarRoadLine;
     		int carRoadCoor;
     		
     		setStrokeWidth(3);
     		paintSetColorYELLOW();	

			for(int i=0;i<LMSConstValue.iCarRoadNum-1;i++)
	        {
				iCarRoadWidthSum += LMSConstValue.iCarRoadWidth[i];
	
    			carRoadCoor = rightDetect - iCarRoadWidthSum;
     			mCarRoadLine = lmsPosition[sensorID]+carRoadCoor*canvasWidth/coorBase;	
		   		lineY1 = 0;
	    		lineY2 = canvasHeight;
	    		if(LMSConstValue.bUpDownTurn[sensorID] == false)
	    		{
	    			lineY1 = (canvasHeight - lineY1);
	    			lineY2 = (canvasHeight - lineY2);
	    		}
	    		drawLine(mCarRoadLine, lineY1, mCarRoadLine, lineY2); 

		   		textY = canvasHeight-TEXT_HEIGHT;
	    		if(LMSConstValue.bUpDownTurn[sensorID] == false)
	    			textY = (canvasHeight - textY + TEXT_HEIGHT);
				if(LMSConstValue.bCarRoadOutputTurn == false)
				{
					drawText("车道"+i, mCarRoadLine+TEXT_OFFSET, textY-TEXT_OFFSET);
				}
				else
				{
					drawText("车道"+(LMSConstValue.iCarRoadNum-1-i), mCarRoadLine+TEXT_OFFSET, textY-TEXT_OFFSET);					
				}
	        }	    		    	
     	}
	}

	public void lmsDrawPointWithCarDetect(int sensorID,int canvasWidth,int canvasHeight,
			int leftDetect,int rightDetect,int frontDetect,
			int iFrontEdgeWindow,int yOffset,
			int iLREdgeWindow,int xOffset,
			int dataLength,
			int x_point[],int y_point[])
	{
		int xDraw = 0,yDraw=0;	
		 /*
		  * 1.2个为一组，X,Y
		  * 2.只显示widthDetect范围
		  */
		
		int PlaneNumber = 1;
		if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.RADAR_F))	//LMS511
		{
			PlaneNumber = 4;
		}
		
//		Log.d(DEBUG_TAG, "lmsDrawPointWithCarDetect dataLength="+dataLength);
		for(int planeNum=0;planeNum<PlaneNumber;planeNum++)
		{
			try{
				setStrokeWidth(2);			
				int loopStart = LMSConstValue.physicStartPoint[sensorID];
				int loopEnd = dataLength+loopStart;
				
				float[] redPoints = new float[loopEnd*2];
				float[] bluePoints = new float[loopEnd*2];
				if(LMSConstValue.getSensorType(sensorID).equals(LMSConstValue.SensorType.RADAR_F))	
				{
					for(int i=loopStart;i<loopEnd;i++)
					{			
						//x_float --- 是以LMS为中心点的距离
						yDraw = (int)(iFrontEdgeWindow-y_point[i])*canvasHeight/iFrontEdgeWindow;
						yDraw += yOffset;
			    		if(LMSConstValue.bUpDownTurn[sensorID] == false)
			    		{
			    			yDraw = (canvasHeight - yDraw);
			    		}

						xDraw = xOffset +
							(int)(lmsPosition[sensorID]-x_point[i]*canvasWidth/(leftDetect+rightDetect+iLREdgeWindow*2));
										
						bluePoints[i*2] = xDraw;
						bluePoints[i*2+1] = yDraw;					
					}
					
					if(planeNum == 0)
						paintSetColorRED();	
					else if(planeNum == 1)
						paintSetColorBLUE();	
					else if(planeNum == 2)
						paintSetColorYELLOW();
					else
						paintSetColorMAGENTA();
					drawPoints(bluePoints);
				}
				else
				{
					for(int i=loopStart;i<loopEnd;i++)
					{			
						//x_float --- 是以LMS为中心点的距离
						yDraw = (int)(iFrontEdgeWindow-y_point[i])*canvasHeight/iFrontEdgeWindow;
						yDraw += yOffset;
			    		if(LMSConstValue.bUpDownTurn[sensorID] == false)
			    		{
			    			yDraw = (canvasHeight - yDraw);
			    		}
			    		
						xDraw = xOffset +
							(int)(lmsPosition[sensorID]-x_point[i]*canvasWidth/(leftDetect+rightDetect+iLREdgeWindow*2));
										
						if((LMSConstValue.yBaseValue[sensorID]-y_point[i])> LMSConstValue.JUMP_POINT_Y
							||(y_point[i]-LMSConstValue.yBaseValue[sensorID])> LMSConstValue.JUMP_POINT_Y) //跳变稍大的 值都用蓝色标注
						{
							bluePoints[i*2] = xDraw;
							bluePoints[i*2+1] = yDraw;
						}
						else
						{
							redPoints[i*2] = xDraw;
							redPoints[i*2+1] = yDraw;
						}
					}
					paintSetColorRED();	
					drawPoints(redPoints);
					paintSetColorBLUE();	
					drawPoints(bluePoints);
				}
			}catch (Exception e){
	    		LMSLog.exception(sensorID,e);
			}
		}
	}
}