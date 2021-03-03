package ThreeD;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.HashMap;

import layer.algorithmLayer.Contour;

import AppBase.appBase.CarTypeAdapter;
import AppFrame.carDetect.CarDetectFrameMainTab;
import AppFrame.contourDetection.ContourDetectionFrame;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class ThreeDBasic {
	private final static String DEBUG_TAG="ThreeDBasic";

	static float downPosition;
	static float upPosition;
	static float leftPosition = 0;
	static float rightPosition = 0;

	static float qianYingUpPosition = 0;
	static float qianYingDownPosition = 0;
	static float qianYingStartPosition = 0;
	static float qianYingEndPosition = 0;

	static float nearOrth;
	static float farOrth;
	static float leftOrth;
	static float rightOrth;
	static float topOrth;
	static float bottomOrth;
	
	public enum ThreeDImageType{
		FRONT,
		REAR,
		LEFT,
		RIGHT,
		DOWN,
		QIANYING_FRONT,
		QIANYING_REAR,
		QIANYING_LEFT,
		QIANYING_RIGHT,
		QIANYING_DOWN,
		GUACHE
	}

	public void genThreeDImage(
		Contour contour,
		GL2 gl,
		ThreeDImageType threeDImageType,
		int arrayNum,FloatBuffer floatBuffer,
		int screenWidth, int screenHeight)
	{
		float orthBase = contour.carLength*2/5;
    	
    	nearOrth = -50000.0f;
    	farOrth = 50000.0f;
    	leftOrth = (float)-orthBase;
    	rightOrth = (float)orthBase;
    	topOrth = (float)orthBase;
    	bottomOrth = (float)-orthBase;
		
		leftOrth *= (float)screenWidth/(float)screenHeight;
		rightOrth *= (float)screenWidth/(float)screenHeight;			

		gl.glOrtho(
			leftOrth, 
			rightOrth,
			bottomOrth,
			topOrth,
			nearOrth,
			farOrth);    
			
		//========================================================================
		//前视
    	if(threeDImageType == ThreeDImageType.REAR||threeDImageType == ThreeDImageType.QIANYING_REAR)
    	{			
	      	if(LMSConstValue.isMyMachine())
	      	{
	 	       	gl.glRotatef(0f,1.0f,0.0f,0.0f);
			    gl.glRotatef(0f,0.0f,1.0f,0.0f);
			    gl.glRotatef(0f,0.0f,0.0f,1.0f);	
	      	}
	      	else
	      	{
		       	gl.glRotatef(20f,1.0f,0.0f,0.0f);
	   			if(contour.contourFilter.bigView == true)
	   				gl.glRotatef(210f,0.0f,1.0f,0.0f);
	   			else
	   				gl.glRotatef(30f,0.0f,1.0f,0.0f);
			    gl.glRotatef(0f,0.0f,0.0f,1.0f);	
	      	}
	      	
	    	//移到中心
			gl.glTranslatef(0.0f, -(contour.carHeight/2), -(contour.carLength/2));	

			gl.glInterleavedArrays(GL2.GL_C3F_V3F,0,floatBuffer);
	       	gl.glDrawArrays(GL.GL_POINTS, 0, arrayNum);
	      	gl.glFlush();
    	}
    	else if(threeDImageType == ThreeDImageType.FRONT||threeDImageType == ThreeDImageType.QIANYING_FRONT)
    	{
	      	if(LMSConstValue.isMyMachine())
	      	{
				gl.glRotatef(0f,1.0f,0.0f,0.0f);
			    gl.glRotatef(180f,0.0f,1.0f,0.0f);
			    gl.glRotatef(0f,0.0f,0.0f,1.0f);	
	      	}
	      	else
	      	{
		       	gl.glRotatef(20f,1.0f,0.0f,0.0f);
	   			if(contour.contourFilter.bigView == true)
	   				gl.glRotatef(340f,0.0f,1.0f,0.0f);
	   			else
	   				gl.glRotatef(160f,0.0f,1.0f,0.0f);	   				
			    gl.glRotatef(0f,0.0f,0.0f,1.0f);	
	      	}	
	      	
	    	//移到中心
			gl.glTranslatef(0.0f, -(contour.carHeight/2), -(contour.carLength/2));	

			gl.glInterleavedArrays(GL2.GL_C3F_V3F,0,floatBuffer);
	       	gl.glDrawArrays(GL.GL_POINTS, 0, arrayNum);
	      	gl.glFlush(); 	
       	}
    	else if(threeDImageType == ThreeDImageType.LEFT||threeDImageType == ThreeDImageType.QIANYING_LEFT)
    	{
			gl.glRotatef(0f,1.0f,0.0f,0.0f);
		    gl.glRotatef(90f,0.0f,1.0f,0.0f);
		    gl.glRotatef(0f,0.0f,0.0f,1.0f);		    

	    	//移到中心
			gl.glTranslatef(0.0f, -(contour.carHeight/2), -(contour.carLength/2));	

			gl.glInterleavedArrays(GL2.GL_C3F_V3F,0,floatBuffer);
	       	gl.glDrawArrays(GL.GL_POINTS, 0, arrayNum);
	      	gl.glFlush(); 
    	}
    	else if(threeDImageType == ThreeDImageType.RIGHT||threeDImageType == ThreeDImageType.QIANYING_RIGHT)
    	{
	       	gl.glRotatef(0f,1.0f,0.0f,0.0f);
		    gl.glRotatef(90f,0.0f,1.0f,0.0f);
		    gl.glRotatef(0f,0.0f,0.0f,1.0f);    

	    	//移到中心
			gl.glTranslatef(0.0f, -(contour.carHeight/2), -(contour.carLength/2));	

			gl.glInterleavedArrays(GL2.GL_C3F_V3F,0,floatBuffer);
	       	gl.glDrawArrays(GL.GL_POINTS, 0, arrayNum);
	      	gl.glFlush(); 
    	}
    	else if(threeDImageType == ThreeDImageType.DOWN||threeDImageType == ThreeDImageType.QIANYING_DOWN)
    	{		
	       	gl.glRotatef(90f,1.0f,0.0f,0.0f);
		    gl.glRotatef(90f,0.0f,1.0f,0.0f);
		    gl.glRotatef(0f,0.0f,0.0f,1.0f);

	    	//移到中心
			gl.glTranslatef(0.0f, -(contour.carHeight/2), -(contour.carLength/2));	

			gl.glInterleavedArrays(GL2.GL_C3F_V3F,0,floatBuffer);
	       	gl.glDrawArrays(GL.GL_POINTS, 0, arrayNum);
	      	gl.glFlush(); 
    	}
    	else if(threeDImageType == ThreeDImageType.GUACHE)
    	{
			gl.glRotatef(0f,1.0f,0.0f,0.0f);
		    gl.glRotatef(90f,0.0f,1.0f,0.0f);
		    gl.glRotatef(0f,0.0f,0.0f,1.0f);		    

	    	//移到中心
			gl.glTranslatef(0.0f, -(contour.carHeight/2), -(contour.carLength/2));	

			gl.glInterleavedArrays(GL2.GL_C3F_V3F,0,floatBuffer);
	       	gl.glDrawArrays(GL.GL_POINTS, 0, arrayNum);
	      	gl.glFlush(); 
    	}
   		LMSLog.d(DEBUG_TAG,"threeD prepare buffer:"+arrayNum);
	}
	
	public ThreeDImageType genThreeDImageType(String viewString)
	{
   		ThreeDImageType threeDImageType = null;
		if(viewString.equals("front"))
    	{
    		threeDImageType = ThreeDImageType.FRONT;
    	}
    	else if(viewString.equals("rear"))
    	{
    		threeDImageType = ThreeDImageType.REAR;	    		
    	}
    	else if(viewString.equals("left"))
    	{
    		threeDImageType = ThreeDImageType.LEFT;	    			    		
    	}
    	else if(viewString.equals("right"))
    	{
    		threeDImageType = ThreeDImageType.RIGHT;	    			    			    		
    	}
    	else if(viewString.equals("down"))
    	{
    		threeDImageType = ThreeDImageType.DOWN;	    			    			    			    		
    	}
    	else if(viewString.equals("qianYing_front"))
    	{
    		threeDImageType = ThreeDImageType.QIANYING_FRONT;
    	}
    	else if(viewString.equals("qianYing_rear"))
    	{
    		threeDImageType = ThreeDImageType.QIANYING_REAR;	    		
    	}
    	else if(viewString.equals("qianYing_left"))
    	{
    		threeDImageType = ThreeDImageType.QIANYING_LEFT;	    			    		
    	}
    	else if(viewString.equals("qianYing_right"))
    	{
    		threeDImageType = ThreeDImageType.QIANYING_RIGHT;	    			    			    		
    	}
    	else if(viewString.equals("qianYing_down"))
    	{
    		threeDImageType = ThreeDImageType.QIANYING_DOWN;	    			    			    			    		
    	}
    	else if(viewString.equals("guaChe"))
    	{
    		threeDImageType = ThreeDImageType.GUACHE;	    			    			    			    			    		
    	}
		
		return threeDImageType;
	}
	
	void drawLine(
		Contour contour,
		GL2 gl,
		ThreeDImageType threeDImageType
	)
	{
		float lineColorRed_R = 1.0f,lineColorRed_G = 0.0f,lineColorRed_B = 0.0f;
		float lineColorBLUE_R = 0.5f,lineColorBLUE_G = 0.5f,lineColorBLUE_B = 1.0f;

		if(threeDImageType == ThreeDImageType.LEFT||threeDImageType == ThreeDImageType.QIANYING_LEFT)
		{
			if(contour.bGuaChe == true)
			{
				upPosition = ((-(contour.carHeight/2)+contour.carGuaCheHeightLeanOriginal-LMSConstValue.iNvramHeightOutputCompensate.iValue))/topOrth;
				downPosition = -(contour.carHeight/2)/topOrth;

				leftPosition = (float)-(contour.carGuaCheZ)/leftOrth;
				rightPosition = ((float)contour.contourFilter.iZEnd)/rightOrth;		

				//===========================================================================
				/*
				qianYingStartPosition = (float)-(contour.carQianYingZStart)/leftOrth;
				qianYingEndPosition = (float)-(contour.carQianYingZEnd)/leftOrth;						
				qianYingStartPosition += (contour.carLength/2)/leftOrth;
				qianYingEndPosition += (contour.carLength/2)/leftOrth;
				qianYingStartPosition += (contour.iLightCurtainRadarDaistance0-LMSConstValue.iNvramLightCurtianRadarDistance0.iValue)/leftOrth;
				qianYingEndPosition += (contour.iLightCurtainRadarDaistance0-LMSConstValue.iNvramLightCurtianRadarDistance0.iValue)/leftOrth;
				*/
				if(contour.bQianYing == true)
				{
					qianYingUpPosition = ((-(contour.carHeight/2)+contour.carQianYingHeight-LMSConstValue.iNvramHeightOutputCompensate.iValue))/topOrth;
					qianYingDownPosition = -(contour.carHeight/2)/topOrth;
					qianYingStartPosition = (float)-(contour.contourFilter.iZStart)/leftOrth;
					qianYingEndPosition = (float)-(contour.contourFilter.iZStart+contour.carQianYingLength)/leftOrth;						
					qianYingStartPosition += (contour.carLength/2)/leftOrth;
					qianYingEndPosition += (contour.carLength/2)/leftOrth;
				}
			}
			else
			{
				upPosition = (contour.carHeight/2-LMSConstValue.iNvramHeightOutputCompensate.iValue)/topOrth;
				downPosition = -(contour.carHeight/2)/topOrth;

				leftPosition = (float)-(contour.contourFilter.iZStart)/leftOrth;
				rightPosition = ((float)contour.contourFilter.iZEnd)/rightOrth;
			}	
			
			leftPosition += (contour.carLength/2)/leftOrth;
			rightPosition -= (contour.carLength/2)/rightOrth;

			if(threeDImageType == ThreeDImageType.LEFT)
			{	
		      	//长线
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B); 
			    gl.glVertex3f(leftPosition,(contour.carHeight*3/4)/topOrth,0);
			    gl.glVertex3f(leftPosition,contour.carHeight/2/bottomOrth,0);
			    gl.glVertex3f(rightPosition,(contour.carHeight*3/4)/topOrth,0);
			    gl.glVertex3f(rightPosition,contour.carHeight/2/bottomOrth,0);
			    gl.glEnd(); 
	
			    //高线
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
			    gl.glVertex3f((leftPosition-0.1f),upPosition,0);
			    gl.glVertex3f((rightPosition+0.1f),upPosition,0);
			    gl.glEnd(); 
			    
				if(					
		    		LMSConstValue.isMyMachine()
		    		&&(
			    		LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
			    		||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_1600)
			    	)
		    	)
				{
					float xPosition;
					int iZNum = 0;
					int iZMiddle[] = new int[LMSConstValue.MAX_Z_NUM];
					if(contour.bGuaChe == true)
					{
						iZNum = contour.iCarGuaCheZNum;
						iZMiddle = contour.iCarGuaCheZMiddle;
					}
					else
					{
						iZNum = contour.iCarZNum;
						iZMiddle = contour.iCarZMiddle;
					}

					for(int i=0;i<iZNum;i++)
					{		
						xPosition = (float)-(iZMiddle[i])/leftOrth;
						xPosition += (contour.carLength/2)/leftOrth;
						xPosition += (contour.iLightCurtainRadarDaistance0-LMSConstValue.iNvramLightCurtianLongDistance.iValue)/leftOrth;
						
					    gl.glLoadIdentity();	//重置投影矩阵
					    gl.glBegin(GL.GL_LINES);   
					    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B); 
					    gl.glVertex3f(xPosition, contour.carHeight/4/bottomOrth, 0);
					    gl.glVertex3f(xPosition, contour.carHeight/2/bottomOrth, 0);
					    gl.glEnd(); 
					}
				}
				
			    if(contour.bLanBanChe == true)
			    {
			    	{
				    	float lanbanCheLeftUp = contour.iLanbanCheValidYBegin;
				    	float lanbanCheRightUp = contour.iLanbanCheValidYEnd;
	
				    	float lanbanCheLeftUpPosition = ((-(contour.carHeight/2)+lanbanCheLeftUp))/topOrth;
				    	float lanbanCheRightUpPosition = ((-(contour.carHeight/2)+lanbanCheRightUp))/topOrth;
				    	float lanbanCheleftPosition = (float)-contour.dLanbanCheValidXBegin/leftOrth;
				    	float lanbanCherightPosition = (float)contour.dLanbanCheValidXEnd/rightOrth;
				    	lanbanCheleftPosition += (contour.carLength/2)/leftOrth;
				    	lanbanCherightPosition -= (contour.carLength/2)/rightOrth;
	
						gl.glLoadIdentity();	//重置投影矩阵
					    gl.glBegin(GL.GL_LINES);   
					    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
					    gl.glVertex3f(lanbanCheleftPosition,lanbanCheLeftUpPosition,0);
					    gl.glVertex3f(lanbanCherightPosition,lanbanCheRightUpPosition,0);
					    gl.glEnd(); 
			    	}

			    	//=============================================================================
			    	{
					    float lanbanPanelLeftUp = contour.iLanbanPanelValidYBegin;
				    	float lanbanPanelRightUp = contour.iLanbanPanelValidYEnd;
	
				    	float lanbanPanelLeftUpPosition = ((-(contour.carHeight/2)+lanbanPanelLeftUp))/topOrth;
				    	float lanbanPanelRightUpPosition = ((-(contour.carHeight/2)+lanbanPanelRightUp))/topOrth;
				    	float lanbanPanelleftPosition = (float)-contour.dLanbanPanelValidXBegin/leftOrth;
				    	float lanbanPanelrightPosition = (float)contour.dLanbanPanelValidXEnd/rightOrth;
				    	lanbanPanelleftPosition += (contour.carLength/2)/leftOrth;
				    	lanbanPanelrightPosition -= (contour.carLength/2)/rightOrth;
	
				    	gl.glLoadIdentity();	//重置投影矩阵
					    gl.glBegin(GL.GL_LINES);   
					    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
					    gl.glVertex3f(lanbanPanelleftPosition,lanbanPanelLeftUpPosition,0);
					    gl.glVertex3f(lanbanPanelrightPosition,lanbanPanelRightUpPosition,0);
					    gl.glEnd(); 
			    	}
			    }
			}
			
		    if(contour.carQianYingHeight != 0
		    	&&
		    		(threeDImageType == ThreeDImageType.LEFT
		    		&&LMSConstValue.bNvramThreeDImageQianYing.bValue == false)
		    	||threeDImageType == ThreeDImageType.QIANYING_LEFT
			)
		    {
			    //牵引车长线
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorBLUE_R, lineColorBLUE_G, lineColorBLUE_B);
			    gl.glVertex3f(qianYingStartPosition,(contour.carHeight*3/4)/topOrth,0);
			    gl.glVertex3f(qianYingStartPosition,contour.carHeight/2/bottomOrth,0);
			    gl.glVertex3f(qianYingEndPosition,(contour.carHeight*3/4)/topOrth,0);
			    gl.glVertex3f(qianYingEndPosition,contour.carHeight/2/bottomOrth,0);
			    gl.glEnd(); 
			    
			    //牵引车高线
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorBLUE_R, lineColorBLUE_G, lineColorBLUE_B);
			    gl.glVertex3f((qianYingStartPosition-0.1f),qianYingUpPosition,0);
			    gl.glVertex3f((qianYingEndPosition+0.1f),qianYingUpPosition,0);
			    gl.glEnd(); 
			    
				if(					
		    		LMSConstValue.isMyMachine()
		    		&&(
			    		LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.LIGHT_CURTAIN)
			    		||LMSConstValue.sensorType[LMSConstValue.LIGHT_CURTAIN_ID_START].key.equals(LMSConstValue.SensorType.XZY_1600)
			    	)
		    	)
				{
					float xPosition;
					int iZNum = 0;
					int iZMiddle[] = new int[LMSConstValue.MAX_Z_NUM];

					iZNum = contour.iCarQianYingZNum;
					iZMiddle = contour.iCarQianYingZMiddle;

					for(int i=0;i<iZNum;i++)
					{		
						xPosition = (float)-(iZMiddle[i])/leftOrth;
						xPosition += (contour.carLength/2)/leftOrth;
						xPosition += (contour.iLightCurtainRadarDaistance0-LMSConstValue.iNvramLightCurtianLongDistance.iValue)/leftOrth;
						
					    gl.glLoadIdentity();	//重置投影矩阵
					    gl.glBegin(GL.GL_LINES);   
					    gl.glColor3f(lineColorBLUE_R, lineColorBLUE_G, lineColorBLUE_B); 
					    gl.glVertex3f(xPosition, contour.carHeight/4/bottomOrth, 0);
					    gl.glVertex3f(xPosition, contour.carHeight/2/bottomOrth, 0);
					    gl.glEnd(); 
					}
				}
				
		    }
		    
		    if(LMSConstValue.isMyMachine())
			{
				float xPosition;

				if(contour.carXZJ >0)
				{
					xPosition = (float)-(contour.iXiao_length_CurveFitting)/leftOrth;
					xPosition += (contour.carLength/2)/leftOrth;
										    
				    gl.glLoadIdentity();	//重置投影矩阵
				    gl.glBegin(GL.GL_LINES);   
				    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
				    gl.glVertex3f(xPosition,(LMSConstValue.yXiaoStart/2)/topOrth,0);
				    gl.glVertex3f(xPosition,(LMSConstValue.yXiaoEnd/2)/bottomOrth,0);
				    gl.glEnd();
				}
			}
		    
		    //=====================================================================
		    if(LMSConstValue.bNvramThreeDDisplayPointMax.bValue == true)
		    {
			    //最高值标圆圈
			    int n = 30;  
			    float R = 0.05f;  
			    float Pi = 3.1415926536f; 
			    float xMax = 0;

				if(contour.bGuaChe == true)
				{
				    gl.glLoadIdentity();	//重置投影矩阵
				    gl.glBegin(GL.GL_LINE_LOOP);
				    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
				    				   	
					xMax = -(contour.heightMaxPointGuaChe.threeDPoint.z)/leftOrth;
					xMax += (contour.carLength/2)/leftOrth;
				    for(int i=0; i<n; ++i)
				    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), (float)(upPosition + R*Math.sin(2*Pi/n*i)));
				    gl.glEnd(); //搜索 
				    
					if(contour.bQianYing == true && contour.heightMaxPointQianYing != null)
					{
					    gl.glLoadIdentity();	//重置投影矩阵
					    gl.glBegin(GL.GL_LINE_LOOP);
					    gl.glColor3f(lineColorBLUE_R, lineColorBLUE_G, lineColorBLUE_B);
						xMax = -(contour.heightMaxPointQianYing.threeDPoint.z)/leftOrth;
						xMax += (contour.carLength/2)/leftOrth;
					    for(int i=0; i<n; ++i)
					    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), (float)(qianYingUpPosition + R*Math.sin(2*Pi/n*i)));
					    gl.glEnd(); //搜索 
					}
				}
				else
				{
				    gl.glLoadIdentity();	//重置投影矩阵
				    gl.glBegin(GL.GL_LINE_LOOP);
				    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
					xMax = -(contour.heightMaxPoint.threeDPoint.z)/leftOrth;
					xMax += (contour.carLength/2)/leftOrth;
				    for(int i=0; i<n; ++i)
				    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), (float)(upPosition + R*Math.sin(2*Pi/n*i)));
				    gl.glEnd(); //搜索 		    
				}
		    }
		}
		else if(threeDImageType == ThreeDImageType.DOWN||threeDImageType == ThreeDImageType.QIANYING_DOWN)
		{
	      	if(LMSConstValue.bNvramThreeDCarRodeMiddle.bValue == true)
	      	{
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glColor4f(1.0f, 1.0f, 0f, 0f); //黄色
			    //横线
			    gl.glBegin(GL.GL_LINES);   
			    gl.glVertex3f(-1.0f,0,0);
			    gl.glVertex3f(1.0f,0,0);
			    gl.glEnd();   		    
       		}

			if(contour.bGuaChe == true && contour.widthMaxPointGuaChe[0]!= null && contour.widthMaxPointGuaChe[1]!= null)
			{
				downPosition = (contour.widthMaxPointGuaChe[0].maxValue)/bottomOrth;
				upPosition = (contour.widthMaxPointGuaChe[1].maxValue)/topOrth;

				leftPosition = (float)-(contour.carGuaCheZ)/leftOrth;
				rightPosition = ((float)contour.contourFilter.iZEnd)/rightOrth;		
				
				/*
				qianYingStartPosition = (float)-(contour.carQianYingZStart)/leftOrth;
				qianYingEndPosition = (float)-(contour.carQianYingZEnd)/leftOrth;						
				qianYingStartPosition += (contour.carLength/2)/leftOrth;
				qianYingEndPosition += (contour.carLength/2)/leftOrth;
				qianYingStartPosition += (contour.iLightCurtainRadarDaistance0-LMSConstValue.iNvramLightCurtianRadarDistance0.iValue)/leftOrth;
				qianYingEndPosition += (contour.iLightCurtainRadarDaistance0-LMSConstValue.iNvramLightCurtianRadarDistance0.iValue)/leftOrth;
				*/
				if(contour.bQianYing == true && contour.widthMaxPointQianYing[0] != null && contour.widthMaxPointQianYing[1] != null)
				{
					qianYingDownPosition = (contour.widthMaxPointQianYing[0].maxValue)/bottomOrth;
					qianYingUpPosition = (contour.widthMaxPointQianYing[1].maxValue)/topOrth;
					qianYingStartPosition = (float)-(contour.contourFilter.iZStart)/leftOrth;
					qianYingEndPosition = (float)-(contour.contourFilter.iZStart+contour.carQianYingLength)/leftOrth;						
					qianYingStartPosition += (contour.carLength/2)/leftOrth;
					qianYingEndPosition += (contour.carLength/2)/leftOrth;
				}
			}
			else if(contour.widthMaxPoint[0] != null && contour.widthMaxPoint[1] != null)
			{
				downPosition = (contour.widthMaxPoint[0].maxValue)/bottomOrth;
				upPosition = (contour.widthMaxPoint[1].maxValue)/topOrth;
				leftPosition = (float)-(contour.contourFilter.iZStart)/leftOrth;
				rightPosition = ((float)contour.contourFilter.iZEnd)/rightOrth;
			}	
			
			leftPosition += (contour.carLength/2)/leftOrth;
			rightPosition -= (contour.carLength/2)/rightOrth;

			if(threeDImageType == ThreeDImageType.DOWN)
			{	
		      	//长=
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B); 
			    gl.glVertex3f(leftPosition,-(downPosition-0.2f),0);
			    gl.glVertex3f(leftPosition,(upPosition-0.2f),0);
			    gl.glVertex3f(rightPosition,-(downPosition-0.2f),0);
			    gl.glVertex3f(rightPosition,(upPosition-0.2f),0);
			    gl.glEnd(); 
	
			    //宽线
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B); 
			    gl.glVertex3f((leftPosition-0.1f),-downPosition,0);
			    gl.glVertex3f((rightPosition+0.1f),-downPosition,0);
			    gl.glVertex3f((leftPosition-0.1f),upPosition,0);
			    gl.glVertex3f((rightPosition+0.1f),upPosition,0);
			    gl.glEnd(); 
			}

		    if(contour.carQianYingHeight != 0
		    	&&
		    		(threeDImageType == ThreeDImageType.DOWN
		    		&&LMSConstValue.bNvramThreeDImageQianYing.bValue == false)
		    	||threeDImageType == ThreeDImageType.QIANYING_DOWN
			)
		    {				    
			    //牵引车长线
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorBLUE_R, lineColorBLUE_G, lineColorBLUE_B);
			    gl.glVertex3f(qianYingStartPosition,(contour.carHeight*3/4)/topOrth,0);
			    gl.glVertex3f(qianYingStartPosition,contour.carHeight/2/bottomOrth,0);
			    gl.glVertex3f(qianYingEndPosition,(contour.carHeight*3/4)/topOrth,0);
			    gl.glVertex3f(qianYingEndPosition,contour.carHeight/2/bottomOrth,0);
			    gl.glEnd(); 
			    
			    //牵引车宽线
				gl.glLoadIdentity();	//重置投影矩阵
			    gl.glBegin(GL.GL_LINES);   
			    gl.glColor3f(lineColorBLUE_R, lineColorBLUE_G, lineColorBLUE_B);
			    gl.glVertex3f((qianYingStartPosition-0.1f),-qianYingDownPosition,0);
			    gl.glVertex3f((qianYingEndPosition+0.1f),-qianYingDownPosition,0);
			    gl.glVertex3f((qianYingStartPosition-0.1f),qianYingUpPosition,0);
			    gl.glVertex3f((qianYingEndPosition+0.1f),qianYingUpPosition,0);
			    gl.glEnd(); 
		    }	
		    
		    //=====================================================================
		    if(LMSConstValue.bNvramThreeDDisplayPointMax.bValue == true)
		    {
			    //最宽值标圆圈
			    int n = 30;  
			    float R = 0.05f;  
			    float Pi = 3.1415926536f; 
			    float xMax = 0;
	
				if(contour.bGuaChe == true)
				{
					gl.glLoadIdentity();	//重置投影矩阵
				    gl.glBegin(GL.GL_LINE_LOOP);
				    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);			
				    xMax = -(contour.widthMaxPointGuaChe[0].threeDPoint.z)/leftOrth;
					xMax += (contour.carLength/2)/leftOrth;
				    for(int i=0; i<n; ++i)
				    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), -(float)((contour.widthMaxPointGuaChe[0].threeDPoint.x)/bottomOrth + R*Math.sin(2*Pi/n*i)));
				    gl.glEnd(); //搜索 
		
				    gl.glLoadIdentity();	//重置投影矩阵
				    gl.glBegin(GL.GL_LINE_LOOP);
				    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
				    xMax = -(contour.widthMaxPointGuaChe[1].threeDPoint.z)/leftOrth;
					xMax += (contour.carLength/2)/leftOrth;
				    for(int i=0; i<n; ++i)
				    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), (float)((contour.widthMaxPointGuaChe[1].threeDPoint.x)/topOrth + R*Math.sin(2*Pi/n*i)));
				    gl.glEnd(); //搜索 				    
				    
					if(contour.bQianYing == true && contour.widthMaxPointQianYing[0] != null && contour.widthMaxPointQianYing[1] != null)
					{
						gl.glLoadIdentity();	//重置投影矩阵
					    gl.glBegin(GL.GL_LINE_LOOP);
					    gl.glColor3f(lineColorBLUE_R, lineColorBLUE_G, lineColorBLUE_B);			
					    xMax = -(contour.widthMaxPointQianYing[0].threeDPoint.z)/leftOrth;
						xMax += (contour.carLength/2)/leftOrth;
					    for(int i=0; i<n; ++i)
					    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), -(float)((contour.widthMaxPointQianYing[0].threeDPoint.x)/bottomOrth + R*Math.sin(2*Pi/n*i)));
					    gl.glEnd(); //搜索 
			
					    gl.glLoadIdentity();	//重置投影矩阵
					    gl.glBegin(GL.GL_LINE_LOOP);
					    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
					    xMax = -(contour.widthMaxPointQianYing[1].threeDPoint.z)/leftOrth;
						xMax += (contour.carLength/2)/leftOrth;
					    for(int i=0; i<n; ++i)
					    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), (float)((contour.widthMaxPointQianYing[1].threeDPoint.x)/topOrth + R*Math.sin(2*Pi/n*i)));
					    gl.glEnd(); //搜索 				    
					    
					}
				}
				else
				{
					gl.glLoadIdentity();	//重置投影矩阵
				    gl.glBegin(GL.GL_LINE_LOOP);
				    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);			
				    xMax = -(contour.widthMaxPoint[0].threeDPoint.z)/leftOrth;
					xMax += (contour.carLength/2)/leftOrth;
				    for(int i=0; i<n; ++i)
				    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), -(float)((contour.widthMaxPoint[0].threeDPoint.x)/bottomOrth + R*Math.sin(2*Pi/n*i)));
				    gl.glEnd(); //搜索 
		
				    gl.glLoadIdentity();	//重置投影矩阵
				    gl.glBegin(GL.GL_LINE_LOOP);
				    gl.glColor3f(lineColorRed_R, lineColorRed_G, lineColorRed_B);
				    xMax = -(contour.widthMaxPoint[1].threeDPoint.z)/leftOrth;
					xMax += (contour.carLength/2)/leftOrth;
				    for(int i=0; i<n; ++i)
				    	gl.glVertex2f((float)(xMax + R*Math.cos(2*Pi/n*i)), (float)((contour.widthMaxPoint[1].threeDPoint.x)/topOrth + R*Math.sin(2*Pi/n*i)));
				    gl.glEnd(); //搜索 				    
				}
		    }
		}
	}
	
	void drawSize(
		Contour contour,
		ThreeDImageType threeDImageType, BufferedImage image,
		int screenWidth, int screenHeight
	)
	{
		Graphics g = image.getGraphics();
		g.setColor(Color.RED);
		
		Font fontOriginal = g.getFont();
		Font font = new Font(fontOriginal.getFontName(),Font.PLAIN,LMSConstValue.iNvramThreeDImageFontSize.iValue);		
		g.setFont(font);
		int fontHeight = g.getFontMetrics().getHeight();

		String str;
		FontMetrics fm;
		Rectangle2D rec;
		int stringWidth;
		int stringHeight;
		int xCoordinate;
		int yCoordinate;
		
		int canvasWidth = screenWidth;
		int canvasHeight = screenHeight;

		if(LMSConstValue.bNvramThreeDImageWithSize.bValue&&
			(threeDImageType == ThreeDImageType.LEFT||threeDImageType == ThreeDImageType.QIANYING_LEFT)
		)
		{		    			
			if(threeDImageType == ThreeDImageType.LEFT)
			{
				if(contour.carGuaCheLength != 0)
				{
					//左上角显示
					str = "挂车宽:"+contour.carGuaCheWidth;
					
					g.setColor(Color.RED);
					fm = g.getFontMetrics();
					   
					stringWidth = fm.stringWidth(str);
					stringHeight = fm.getAscent();

					xCoordinate = (int) 20;
					yCoordinate = (int) 40;
			    			
					g.drawString(str, xCoordinate, yCoordinate);
					
					//============================================================
					str = "挂车高:"+contour.carGuaCheHeight;
					
					fm = g.getFontMetrics();
					rec=fm.getStringBounds(str, g);  
		
					stringWidth = (int) rec.getWidth();
					stringHeight = (int) rec.getHeight();
					        
					if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
					{							
						xCoordinate = (int)((1+rightPosition)*canvasWidth/2+2);
					}
					else
					{
						xCoordinate = (int)((1-rightPosition)*canvasWidth/2-stringWidth-2);					
					}
					yCoordinate = (int) ((1-(upPosition+downPosition)/2)*canvasHeight/2);
						
					g.drawString(str, xCoordinate, yCoordinate);

					//------------------------------------------------------------
					if(LMSConstValue.bNvramGuaCheLean.bValue == true)
					{
						str = "(原倾斜高:"+contour.carGuaCheHeightLeanOriginal+")";
						
						fm = g.getFontMetrics();
						rec=fm.getStringBounds(str, g);  
			
						stringWidth = (int) rec.getWidth();
						stringHeight = (int) rec.getHeight();
						        
						if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
						{							
							xCoordinate = (int)((1+rightPosition)*canvasWidth/2+2);
						}
						else
						{
							xCoordinate = (int)((1-rightPosition)*canvasWidth/2-stringWidth-2);					
						}
						yCoordinate += (stringHeight+5);
							
						g.drawString(str, xCoordinate, yCoordinate);
						
						//------------------------------------------------------------
						str = "(倾角:"+String.format("%.2f", contour.dGuacheLeanAngle)+"度)";
						
						fm = g.getFontMetrics();
						rec=fm.getStringBounds(str, g);  
			
						stringWidth = (int) rec.getWidth();
						stringHeight = (int) rec.getHeight();
						        
						if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
						{							
							xCoordinate = (int)((1+rightPosition)*canvasWidth/2+2);
						}
						else
						{
							xCoordinate = (int)((1-rightPosition)*canvasWidth/2-stringWidth-2);					
						}
						yCoordinate += (stringHeight+5);
							
						g.drawString(str, xCoordinate, yCoordinate);
					}
				}
				else
				{
					//左上角显示
					str = "宽:"+contour.carWidth;
					
					g.setColor(Color.RED);
					fm = g.getFontMetrics();
					   
					stringWidth = fm.stringWidth(str);
					stringHeight = fm.getAscent();

					xCoordinate = (int) 20;
					yCoordinate = (int) 40;
			    			
					g.drawString(str, xCoordinate, yCoordinate);
					
					//============================================================
					str = "高:"+contour.carHeight;							
					
					fm = g.getFontMetrics();
					rec=fm.getStringBounds(str, g);  
		
					stringWidth = (int) rec.getWidth();
					stringHeight = (int) rec.getHeight();
					        
					if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
					{							
						xCoordinate = (int)((1+rightPosition)*canvasWidth/2+2);
					}
					else
					{
						xCoordinate = (int)((1-rightPosition)*canvasWidth/2-stringWidth-2);					
					}
					yCoordinate = (int) ((1-(upPosition+downPosition)/2)*canvasHeight/2);
						
					g.drawString(str, xCoordinate, yCoordinate);
				}
				
	    		//=======================================================
				if(contour.carGuaCheLength != 0)
				{
					str = "挂车长:"+contour.carGuaCheLength;
				}
				else
				{
					str = "长:"+contour.carLength;							
				}
				fm = g.getFontMetrics();
	
				stringWidth = fm.stringWidth(str);
				stringHeight = fm.getAscent();
	    			        
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)(((1+(leftPosition+rightPosition)/2)*canvasWidth-stringWidth)/2);
				}
				else
				{
					xCoordinate = (int)(((1-(leftPosition+rightPosition)/2)*canvasWidth-stringWidth)/2);					
				}
				yCoordinate = (int) (((1-upPosition)*canvasHeight/2)-10);
	
				g.drawString(str, xCoordinate, yCoordinate);
			}
			
		    if(contour.bLanBanChe == true && threeDImageType == ThreeDImageType.LEFT)
		    {
		    	str = "栏板高:\n"+contour.carLanBanHeight;
    			fm = g.getFontMetrics();
				
				stringWidth = fm.stringWidth(str);
				stringHeight = fm.getAscent();
    			
    			//===========================
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)(((1+(leftPosition+rightPosition)/2)*canvasWidth-stringWidth)/2);
				}
				else
				{
					xCoordinate = (int)(((1-(leftPosition+rightPosition)/2)*canvasWidth-stringWidth)/2);					
				}
				float lanbanStrPosition = (contour.carHeight/2)/topOrth;
				yCoordinate = (int) (((1+lanbanStrPosition)*canvasHeight/2)+20);
	
				g.drawString(str, xCoordinate, yCoordinate);
		    }
		    
			//------------------------------------------
		    if(contour.carQianYingHeight != 0
		    	&&
		    		(threeDImageType == ThreeDImageType.LEFT
		    		&&LMSConstValue.bNvramThreeDImageQianYing.bValue == false)
		    	||threeDImageType == ThreeDImageType.QIANYING_LEFT
			)
		    {				
		    	str = "牵引车高:\n"+contour.carQianYingHeight;
		    	g.setColor(Color.BLUE);
    			fm = g.getFontMetrics();
    			rec=fm.getStringBounds(str, g);  

    			stringWidth = (int) rec.getWidth();
    			stringHeight = (int) rec.getHeight();
    			        
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)((1+qianYingStartPosition)*canvasWidth/2-stringWidth-2);
				}
				else
				{
					xCoordinate = (int)((1-qianYingStartPosition)*canvasWidth/2+2);					
				}
				yCoordinate = (int) ((1-(qianYingUpPosition+qianYingDownPosition)/2)*canvasHeight/2);

				g.drawString(str, xCoordinate, yCoordinate);
    			
	    		//------------------------------------------
				str = "牵引车长:"+contour.carQianYingLength;
				
				g.setColor(Color.BLUE);
				fm = g.getFontMetrics();
				   
				stringWidth = fm.stringWidth(str);
				stringHeight = fm.getAscent();
	    			     
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)(((1+(qianYingStartPosition+qianYingEndPosition)/2)*canvasWidth-stringWidth)/2);
				}
				else
				{
					xCoordinate = (int)(((1-(qianYingStartPosition+qianYingEndPosition)/2)*canvasWidth-stringWidth)/2);					
				}
				yCoordinate = (int) (((1-qianYingUpPosition)*canvasHeight/2)-10);
	    			
				g.drawString(str, xCoordinate, yCoordinate);
		    }
		    			
			//=======================================================
	   		LMSLog.d(DEBUG_TAG,"leftPosition="+leftPosition);
	   		LMSLog.d(DEBUG_TAG,"rightPosition="+rightPosition);
	   		LMSLog.d(DEBUG_TAG,"upPosition="+upPosition);
	   		LMSLog.d(DEBUG_TAG,"downPosition="+downPosition);
		}
		else if(LMSConstValue.bNvramThreeDImageWithSize.bValue&&
			(threeDImageType == ThreeDImageType.DOWN||threeDImageType == ThreeDImageType.QIANYING_DOWN)	  
		)
		{		    
			float offset;
			if(threeDImageType == ThreeDImageType.DOWN)
			{
				//左上角显示
				if(contour.carGuaCheLength != 0)
				{
					str = "挂车高:"+contour.carGuaCheHeight;
				}
				else
				{
					str = "高:"+contour.carHeight;							
				}
				
				g.setColor(Color.RED);
				fm = g.getFontMetrics();
				   
				stringWidth = fm.stringWidth(str);
				stringHeight = fm.getAscent();

				xCoordinate = (int) 20;
				yCoordinate = (int) 40;
		    			
				g.drawString(str, xCoordinate, yCoordinate);

				//==========================================================================
				if(contour.carGuaCheLength != 0)
				{
					str = "挂车宽:"+contour.carGuaCheWidth;
				}
				else
				{
					str = "宽:"+contour.carWidth;							
				}
				fm = g.getFontMetrics();
				rec=fm.getStringBounds(str, g);  
	
				stringWidth = (int) rec.getWidth();
				stringHeight = (int) rec.getHeight();
				        
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)((1+rightPosition)*canvasWidth/2+2);
				}
				else
				{
					xCoordinate = (int)((1-rightPosition)*canvasWidth/2-stringWidth-2);					
				}
				offset = Math.abs(upPosition-downPosition)/2;
				if(upPosition>downPosition)
					yCoordinate = (int) (((1-offset)*canvasHeight-stringHeight)/2);
				else
					yCoordinate = (int) (((1+offset)*canvasHeight-stringHeight)/2);
					
				g.drawString(str, xCoordinate, yCoordinate);
				
	    		//=======================================================
				if(contour.carGuaCheLength != 0)
				{
					str = "挂车长:"+contour.carGuaCheLength;
				}
				else
				{
					str = "长:"+contour.carLength;							
				}
				fm = g.getFontMetrics();
	
				stringWidth = fm.stringWidth(str);
				stringHeight = fm.getAscent();
	    			        
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)(((1+(leftPosition+rightPosition)/2)*canvasWidth-stringWidth)/2);
				}
				else
				{
					xCoordinate = (int)(((1-(leftPosition+rightPosition)/2)*canvasWidth-stringWidth)/2);					
				}
				yCoordinate = (int) (((downPosition+1)*canvasHeight-stringHeight-10)/2);
	
				g.drawString(str, xCoordinate, yCoordinate);	
			}
			
			//==========================================================
			if(contour.carQianYingHeight != 0
		    	&&
		    		(threeDImageType == ThreeDImageType.DOWN
		    		&&LMSConstValue.bNvramThreeDImageQianYing.bValue == false)
		    	||threeDImageType == ThreeDImageType.QIANYING_DOWN
			)
			{				
				str = "牵引车宽:"+contour.carQianYingWidth;
				g.setColor(Color.BLUE);
    			fm = g.getFontMetrics();
    			rec=fm.getStringBounds(str, g);  

    			stringWidth = (int) rec.getWidth();
    			stringHeight = (int) rec.getHeight();
    			        
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)((1+qianYingStartPosition)*canvasWidth/2-stringWidth-2);
				}
				else
				{
					xCoordinate = (int)((1-qianYingStartPosition)*canvasWidth/2+2);					
				}
				offset = Math.abs(qianYingUpPosition-qianYingDownPosition)/2;
    			if(qianYingUpPosition>qianYingDownPosition)
    				yCoordinate = (int) (((1-offset)*canvasHeight-stringHeight)/2);
    			else
    				yCoordinate = (int) (((1+offset)*canvasHeight-stringHeight)/2);
    				
    			g.drawString(str, xCoordinate, yCoordinate);
    			
	    		//=======================================================
    			str = "牵引车长:"+contour.carQianYingLength;
    			g.setColor(Color.BLUE);
				fm = g.getFontMetrics();

				stringWidth = fm.stringWidth(str);
				stringHeight = fm.getAscent();
	    			    
				if(LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
				{							
					xCoordinate = (int)(((1+(qianYingStartPosition+qianYingEndPosition)/2)*canvasWidth-stringWidth)/2);
				}
				else
				{
					xCoordinate = (int)(((1-(qianYingStartPosition+qianYingEndPosition)/2)*canvasWidth-stringWidth)/2);					
				}
				yCoordinate = (int) (((qianYingDownPosition+1)*canvasHeight-stringHeight-2)/2-10);

				g.drawString(str, xCoordinate, yCoordinate);	
			}			
    	}	
		else if(threeDImageType == ThreeDImageType.RIGHT||threeDImageType == ThreeDImageType.QIANYING_RIGHT)
		{
			str = LMSConstValue.softwareVersion+":"+contour.carTypeString;
			if(CarTypeAdapter.bNvramFilterEndGas.bValue == true)
				str += ";尾气滤除";
				
			g.setColor(Color.RED);
			fm = g.getFontMetrics();
			   
			stringWidth = fm.stringWidth(str);
			stringHeight = fm.getAscent();

			xCoordinate = (int) 20;
			yCoordinate = (int) 40;
	    			
			g.drawString(str, xCoordinate, yCoordinate);
		}
		
		g.dispose();
	}
}
