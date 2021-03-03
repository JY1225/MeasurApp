package ThreeD;

import java.awt.HeadlessException; 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.BufferOverflowException;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLOffscreenAutoDrawable;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.AWTGLReadBufferUtil;

import layer.algorithmLayer.Contour;
import layer.algorithmLayer.ThreeDPoint;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;
import AppBase.appBase.CarTypeAdapter;
import Ben.CNN.CNN;
import Ben.CNN.CNN_PredictMain;
import Ben.Space3d;
import Ben.Algorithm.SpaceCloud;
import CarAppAlgorithm.WidthHeightDetectRunnable;
import FileManager.FileManager;
import SensorBase.LMSConstValue;
import SensorBase.LMSToken;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;
import ThreeD.ThreeDBasic.ThreeDImageType;

public class ThreeDGIPCSingle extends JPanel implements GLEventListener { 
	private String DEBUG_TAG="ThreeDGIPCSingle";
	    
	float fEnlarge = 1.0f;
    float x1 = 0.0f;             //矩型的X坐标 
    float y1 = 0.0f;             //矩型的y坐标 
    long rsize = 50;               //为矩型宽度预留的位置或距离 
    float windowWidth;             //这里不是指窗体边框的的宽，而是视觉投影的右端 
    float windowHeight;            //这里不是指窗体边框的的高，而是视觉投影的顶端 

    GL2 gl;                         //OPENGL的主接口 
//    public GLCanvas glcanvas;             //类似java.awt.Canvas, GLCanvas主要用来显示各种OPENGL的效果 
    public GLJPanel glcanvas; 
	GLProfile glp;
	GLCapabilities capabilities;
	GLDrawableFactory factory;
	GLOffscreenAutoDrawable drawable;
	
    boolean bOffScreen;
     
    FPSAnimator animator = null;  
    
    public ThreeDGIPCSingle(boolean _bOffScreen) throws HeadlessException { 
		LMSLog.d(DEBUG_TAG, "ThreeDGIPCSingle");
		
		bOffScreen = _bOffScreen;

		//===============================================================
		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);
		
		if(bOffScreen)
		{
	    	try{
	    		//耗时的操作
	    		glp = GLProfile.getDefault();
	    	    capabilities = new GLCapabilities(glp); 
	    	}
			catch (UnsatisfiedLinkError e)
			{
				LMSLog.exceptionDialog(null,e);
				System.exit(0);
			}
			catch (GLException e)
			{
				LMSLog.exceptionDialog(null,e);
				System.exit(0);
			}
		}
		else 
    	{
			try{
	    		glp = GLProfile.getMaxFixedFunc(true);
			    capabilities = new GLCapabilities(glp);        //实例化capabilities 
			}
			catch (UnsatisfiedLinkError e)
			{
				LMSLog.exceptionDialog(null,e);
				System.exit(0);
			}
			catch (GLException e)
			{
				LMSLog.exceptionDialog(null,e);
				System.exit(0);
			}
			
//	    	glcanvas = new GLCanvas(capabilities);      //实例化glcanvas 
		    glcanvas = new GLJPanel(capabilities);
		    glcanvas.addGLEventListener(this);          //给glcanvas添加GL事件处理 
		    
		    //===================================================
		    glcanvas.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent evt) {
	 
	                canvasMousePressed(evt);
	            }
		    });
		    glcanvas.addMouseMotionListener(new MouseMotionAdapter() {
	            public void mouseDragged(MouseEvent evt) {
	                canvasMouseDragged(evt);
	            } 
		    });
		    glcanvas.addMouseWheelListener(new MouseWheelListener() {
	            public void mouseWheelMoved(MouseWheelEvent evt) {	 
	                canvasMouseWheelMoved(evt);
	            }
		    });
	        //===================================================
		    animator=new FPSAnimator(glcanvas,10,true); 
			animator.start();
    	}
    } 
    
    float moveX,moveY;
    float rquadX,rquadY;
    int mouseX, mouseY;
    int mouseEventType = MouseEvent.BUTTON1;
    private void canvasMouseDragged(MouseEvent evt) {
    	//GEN-FIRST:event_canvasMouseDragged
        // TODO add your handling code here:
    	if(mouseEventType == MouseEvent.BUTTON1&&LMSConstValue.enumThreeDMouseType.key.equals(LMSConstValue.EnumThreeDMouseType.MOUSE_HAND_ROTATE))
    	{
	        rquadX += ((float) (evt.getX() - mouseX)) / 4;
	        rquadY -= ((float) (evt.getY() - mouseY)) / 4;
	        mouseX = evt.getX();
	        mouseY = evt.getY();
	     
			if(rquadX >= 360)
				rquadX -= 360;
			else if(rquadX < 0)
				rquadX += 360; 
			if(rquadY >= 360)
				rquadY -= 360;
			else if(rquadY < 360)
				rquadY += 360;
			
	        LMSLog.d(DEBUG_TAG,"rquadX="+rquadX+" rquadY="+rquadY);
    	}
    	else if(mouseEventType == MouseEvent.BUTTON3)
    	{
    		moveX += ((float) (evt.getX() - mouseX)) * 4;
    		moveY -= ((float) (evt.getY() - mouseY)) * 4;
    		mouseX = evt.getX();
    		mouseY = evt.getY();
    	}
    	
	}//GEN-LAST:event_canvasMouseDragged
    	 
    private void canvasMousePressed(MouseEvent evt) {
    	mouseEventType = evt.getButton();

    	//GEN-FIRST:event_canvasMousePressed
        // TODO add your handling code here:
    	mouseX = evt.getX();
    	mouseY = evt.getY();
   
	}//GEN-LAST:event_canvasMousePressed
    	 
    private void canvasMouseWheelMoved(MouseWheelEvent evt) {
    	float MAX = 10.0f;
    	float MIN = 0.5f;
    	
    	fEnlarge += 0.2*evt.getWheelRotation();

    	if(fEnlarge > 10.0)
			fEnlarge = 10;
		if(fEnlarge < 0.2)
			fEnlarge = 0.2f;		
	}//GEN-LAST:event_canvasMouseWheelMoved 
    	    
   	public static LMSToken offScreenToken = new LMSToken();
    void offScreenDisplay()
	{
		synchronized (offScreenToken) 
		{
			LMSLog.d(DEBUG_TAG, "offScreenDisplay");
	
			screenWidth = 992;
			screenHeight = 578;
	
			capabilities.setOnscreen(false); 
			if(drawable != null)
			{
				drawable.getContext().release();
				drawable.destroy();
			}
			//if(factory == null)
			{
			    factory = GLDrawableFactory.getFactory(glp); 
			}
			//if(drawable == null)
			{
			    drawable = factory.createOffscreenAutoDrawable(null,capabilities,null,screenWidth,screenHeight); 
			}
	        drawable.display(); 
	        drawable.getContext().makeCurrent(); 
		    gl = drawable.getGL().getGL2(); 
			
			resetBackColor();
			staticShowAndSave(drawable);
			contour = null;
			
			drawable.getContext().release();
			drawable.destroy();
		}
	}
    
	public void threeDOneContour(Contour _contour,boolean _bRegenate) {         
		contour = _contour;
		bRegenate = _bRegenate;
		
		try{
			generateRadarThreeDData();
			if(LMSConstValue.isMyMachine() == true)
			{
				if(LMSConstValue.bNvramThreeDDisplayLightCurtain.bValue == true)
				{
					generateLightCurtainThreeDData();
				}
			}
			resetThreeDDataPosition();
		}
		catch (BufferOverflowException e) {
			LMSLog.exceptionDialog(null, e);
		}
		catch (OutOfMemoryError e) {
        	LMSLog.outOfMemoryDialog(e); 
		}
		
		if(bOffScreen)
			offScreenDisplay();
		else
			glcanvas.display();                  //主线程不停调用display() 	
	} 
     
	String time;
	float colorR,colorG,colorB;
	final float RED_R = 1.0f,RED_G = 0.0f,RED_B = 0.0f; //红色
	final float BLUE_R = 0.5f,BLUE_G = 0.5f,BLUE_B = 1.0f; //蓝色
	final float BLACK_R = 0.0f,BLACK_G = 0.0f,BLACK_B = 0.0f; //红色
	final float WHITE_R = 1.0f,WHITE_G = 1.0f,WHITE_B = 1.0f; //蓝色
	final int CAR_IN_LINE = 10;

	Contour contour = null;
	boolean bRegenate = false;
		
	int iFilterTwoSideSum;
	int iFilterSingleSideSum[] = new int[LMSConstValue.LMS_WH_SENSOR_NUM];
	int iFilterTwoSideReal;
	int iFilterSingleSideReal[] = new int[LMSConstValue.LMS_WH_SENSOR_NUM];
	
	boolean getDisplay(ThreeDPoint threeDPoint)
	{
		boolean bDisplay = false;
		
		if(bOffScreen == true)
		{
			if(threeDPoint.region == 0)
			{
				bDisplay = true;
			}
		}
		else 
		{
			boolean bSubDisplay = false;
			if(
				(
					LMSConstValue.bNvramThreeDDisplayMiddle.bValue == false
					&&threeDPoint.x>LMSConstValue.iNvramXRangeMin.iValue&&threeDPoint.x<LMSConstValue.iNvramXRangeMax.iValue
					&&threeDPoint.y>LMSConstValue.iNvramYRangeMin.iValue&&threeDPoint.y<LMSConstValue.iNvramYRangeMax.iValue
					&&threeDPoint.z>LMSConstValue.iNvramZRangeMin.iValue&&threeDPoint.z<LMSConstValue.iNvramZRangeMax.iValue
				)
				||(LMSConstValue.bNvramThreeDDisplayMiddle.bValue == true && Math.abs(threeDPoint.x-contour.carGuaCheWidthMiddle) <= 100)
			)
			{
				bSubDisplay = true;
			}
			
			if(bSubDisplay == true)
			{
				if(
					(threeDPoint.region == 0&&LMSConstValue.bNvramThreeDDisplayFilterIn.bValue == true)
					||(threeDPoint.region == -2&&LMSConstValue.bNvramThreeDDisplayFilterOut1.bValue == true)
					||(threeDPoint.region == -3&&LMSConstValue.bNvramThreeDDisplayFilterOut2.bValue == true)
				)
				{
					bDisplay = true;
				}
			}
		}

		return bDisplay;
	}
	
	void generateRadarThreeDData()
	{
		LMSLog.d(DEBUG_TAG, "generateThreeDData..................");
				
      	//=================================================
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");//设置日期格式
		time = df.format(new Date()); 

      	//=================================================        
		iFilterTwoSideSum = 0;
		for(int sensorID=0;sensorID<LMSConstValue.LMS_WH_SENSOR_NUM;sensorID++)
		{
			for(int region = 0;region < contour.contourFilter.regionListSide[sensorID].size();region++)
			{
				iFilterSingleSideSum[sensorID] += contour.contourFilter.regionListSide[sensorID].get(region).index.size();
			}
			iFilterTwoSideSum+=iFilterSingleSideSum[sensorID];
			
			iFilterSingleSideReal[sensorID] = 0;
			iFilterTwoSideReal = 0;
		}
						
      	//=================================================        
		boolean bHeightMaxPoint = false;
		boolean bHeightMaxPointGuaChe = false;
		boolean bHeightMaxPointQianYing = false;
		if(bRegenate == true)
		{
			if(!LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.NO_CAR_MAX_POINT))
			{
				if(
					LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.WHOLE_CAR_MAX_POINT)
					&&contour.heightMaxPoint != null
				)
				{
					bHeightMaxPoint = true;
				}
				else if(LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.GUA_CHE_MAX_POINT)
					&&contour.heightMaxPointGuaChe != null
				)
				{
					bHeightMaxPointGuaChe = true;
				}
				else if(LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.QIAN_YING_MAX_POINT)
					&&contour.heightMaxPointQianYing != null						
				)
				{
					bHeightMaxPointQianYing = true;
				}
			}
		}
		else if(LMSConstValue.bNvramThreeDDisplayPointMax.bValue == true)
		{
			if(contour.heightMaxPoint != null)
			{
				bHeightMaxPoint = true;
			}
			if(contour.heightMaxPointGuaChe != null)
			{
				bHeightMaxPointGuaChe = true;
			}
			if(contour.heightMaxPointQianYing != null)
			{
				bHeightMaxPointQianYing = true;
			}
		}
		
     	//=================================================  
		ArrayList<Space3d> pointList = new ArrayList<Space3d>();
		for(int sensorID=0;sensorID<LMSConstValue.LMS_WH_SENSOR_NUM;sensorID++)
		{
			boolean bWidthMaxPoint = false;
			boolean bWidthMaxPointGuaChe = false;
			boolean bWidthMaxPointQianYing = false;
			if(bRegenate == true)
			{
				if(!LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.NO_CAR_MAX_POINT))
				{
					if(LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.WHOLE_CAR_MAX_POINT)
						&&contour.widthMaxPoint[sensorID] != null
					)
					{
						bWidthMaxPoint = true;
					}
					else if(LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.GUA_CHE_MAX_POINT)
						&&contour.widthMaxPointGuaChe[sensorID] != null
					)
					{
						bWidthMaxPointGuaChe = true;
					}
					else if(LMSConstValue.enumThreeDMaxPointType.key.equals(LMSConstValue.EnumThreeDMaxPointType.QIAN_YING_MAX_POINT)
						&&contour.widthMaxPointQianYing[sensorID] != null
					)
					{
						bWidthMaxPointQianYing = true;
					}
				}
			}
			else if(LMSConstValue.bNvramThreeDDisplayPointMax.bValue == true)
			{
				if(contour.widthMaxPoint[sensorID] != null)
				{
					bWidthMaxPoint = true;
				}
				if(contour.widthMaxPointGuaChe[sensorID] != null)
				{
					bWidthMaxPointGuaChe = true;
				}
				if(contour.widthMaxPointQianYing[sensorID] != null)
				{
					bWidthMaxPointQianYing = true;
				}
			}
			
			if(contour.widthMaxPoint[sensorID] != null)
			{
				LMSLog.d(DEBUG_TAG+sensorID,
					"bWidthMaxPoint="+bWidthMaxPoint
					+" "+contour.widthMaxPoint[sensorID].threeDPoint.x
					+" "+contour.widthMaxPoint[sensorID].threeDPoint.y
					+" "+contour.widthMaxPoint[sensorID].threeDPoint.z
				);
			}
			if(contour.widthMaxPointGuaChe[sensorID] != null)
			{
				LMSLog.d(DEBUG_TAG+sensorID,
					"bWidthMaxPointGuaChe="+bWidthMaxPointGuaChe
					+" "+contour.widthMaxPointGuaChe[sensorID].threeDPoint.x
					+" "+contour.widthMaxPointGuaChe[sensorID].threeDPoint.y
					+" "+contour.widthMaxPointGuaChe[sensorID].threeDPoint.z
				);
			}
			if(contour.widthMaxPointQianYing[sensorID] != null)
			{
				LMSLog.d(DEBUG_TAG+sensorID,
					"bWidthMaxPointQianYing="+bWidthMaxPointQianYing
					+" "+contour.widthMaxPointQianYing[sensorID].threeDPoint.x
					+" "+contour.widthMaxPointQianYing[sensorID].threeDPoint.y
					+" "+contour.widthMaxPointQianYing[sensorID].threeDPoint.z
				);
			}
			
			if(bOffScreen == true
				||(sensorID == 0&&LMSConstValue.bNvramThreeDDisplayRadar0.bValue == true)
				||(sensorID == 1&&LMSConstValue.bNvramThreeDDisplayRadar1.bValue == true)
			)
			{			
				int iMove = 0;
				if(LMSConstValue.iNvramLWDistance2.iValue != 0 && sensorID == 1) //静态重组
				{
					iMove = (int) ((LMSConstValue.iNvramLWDistance2.iValue-LMSConstValue.iNvramLWDistance.iValue)-contour.zStart[1]);					
				}

				for(int i=0;i<contour.contourFilter.threeDPointListSide[sensorID].size();i++)
				{				
		     		ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(i);
						
					//------------------------------------------------------------------------
		     		if(
	     				bOffScreen == true
	     				&&(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_AUTO))
	     			)
		     		{
			     		Space3d point = new Space3d(threeDPoint.z, threeDPoint.x, threeDPoint.y);
						pointList.add(point);
		     		}
		     		
					//------------------------------------------------------------------------
					if(
						(
							bWidthMaxPoint == true
							&&contour.widthMaxPoint[sensorID].maxValue != LMSConstValue.INVALID_X
							&&Math.abs(threeDPoint.x -  contour.widthMaxPoint[sensorID].threeDPoint.x)<100
							&&Math.abs(threeDPoint.y -  contour.widthMaxPoint[sensorID].threeDPoint.y)<100
							&&Math.abs(threeDPoint.z -  contour.widthMaxPoint[sensorID].threeDPoint.z)<100
						)
						||
						(
							bWidthMaxPointGuaChe == true
							&&contour.widthMaxPointGuaChe[sensorID].maxValue != LMSConstValue.INVALID_X
							&&Math.abs(threeDPoint.x -  contour.widthMaxPointGuaChe[sensorID].threeDPoint.x)<100
							&&Math.abs(threeDPoint.y -  contour.widthMaxPointGuaChe[sensorID].threeDPoint.y)<100
							&&Math.abs(threeDPoint.z -  contour.widthMaxPointGuaChe[sensorID].threeDPoint.z)<100
						)
						||
						(
							bWidthMaxPointQianYing == true
							&&contour.widthMaxPointQianYing[sensorID].maxValue != LMSConstValue.INVALID_X
							&&Math.abs(threeDPoint.x -  contour.widthMaxPointQianYing[sensorID].threeDPoint.x)<100
							&&Math.abs(threeDPoint.y -  contour.widthMaxPointQianYing[sensorID].threeDPoint.y)<100
							&&Math.abs(threeDPoint.z -  contour.widthMaxPointQianYing[sensorID].threeDPoint.z)<100
						)
					)
					{
						colorR = RED_R;                                                                                              
						colorG = RED_G;                                                                                              
						colorB = RED_B;  
					}
					else if(
						(
							bHeightMaxPoint == true
							&&contour.heightMaxPoint.maxValue != LMSConstValue.INVALID_X
							&&Math.abs(threeDPoint.x -  contour.heightMaxPoint.threeDPoint.x)<100
							&&Math.abs(threeDPoint.y -  contour.heightMaxPoint.threeDPoint.y)<100
							&&Math.abs(threeDPoint.z -  contour.heightMaxPoint.threeDPoint.z)<100
						)
						||
						(
							bHeightMaxPointGuaChe == true
							&&contour.heightMaxPointGuaChe.maxValue != LMSConstValue.INVALID_X
							&&Math.abs(threeDPoint.x -  contour.heightMaxPointGuaChe.threeDPoint.x)<100
							&&Math.abs(threeDPoint.y -  contour.heightMaxPointGuaChe.threeDPoint.y)<100
							&&Math.abs(threeDPoint.z -  contour.heightMaxPointGuaChe.threeDPoint.z)<100
						)
						||
						(
							bHeightMaxPointQianYing == true
							&&contour.heightMaxPointQianYing.maxValue != LMSConstValue.INVALID_X
							&&Math.abs(threeDPoint.x -  contour.heightMaxPointQianYing.threeDPoint.x)<100
							&&Math.abs(threeDPoint.y -  contour.heightMaxPointQianYing.threeDPoint.y)<100
							&&Math.abs(threeDPoint.z -  contour.heightMaxPointQianYing.threeDPoint.z)<100
						)
					)
					{
						colorR = BLUE_R;                                                                                              
						colorG = BLUE_G;                                                                                              
						colorB = BLUE_B;  
					}
					else
					{
						if(LMSConstValue.enumBackgroundColor.key.equals(LMSConstValue.EnumBackgroundColor.BLACK_BACKGROUND_WHITH_FONT))
						{
							colorR = WHITE_R;                                                                                              
							colorG = WHITE_G;                                                                                              
							colorB = WHITE_B;   
						}
						else
						{
							colorR = BLACK_R;                                                                                              
							colorG = BLACK_G;                                                                                              
							colorB = BLACK_B;   							
						}
					}

					//------------------------------------------------------------------------
					if(getDisplay(threeDPoint))
					{
						iFilterSingleSideReal[threeDPoint.sensorID]++;
						iFilterTwoSideReal++;
						
						ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(colorR);
						ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(colorG);
						ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(colorB);						
						ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(threeDPoint.x);
						ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(threeDPoint.y);
						ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(threeDPoint.z+iMove);
						
						ThreeDMainThread.floatBufferTwoSide.put(colorR);
						ThreeDMainThread.floatBufferTwoSide.put(colorG);
						ThreeDMainThread.floatBufferTwoSide.put(colorB);						
						ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.x);
						ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.y);
						ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.z+iMove);	
					}
				}
			}			
    	}

		//----------------------------------------------------------------
		if(
			bOffScreen == true
			&&(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_AUTO))
		)
 		{
			//String result = CNN.predict(CNN_PredictMain.theta1, CNN_PredictMain.theta2, SpaceCloud.pointCloudXZToMatrix(pointList, 100, 40, 240));
			//LMSLog.d(DEBUG_TAG,"车辆类型："+result);
 		}
	}
	
	void generateLightCurtainThreeDData()
	{
		int iMove = 0;
		if(LMSConstValue.iNvramLightCurtianLongDistance.iValue != 0) //静态重组
		{
			iMove = (int) ((LMSConstValue.iNvramLightCurtianLongDistance.iValue-LMSConstValue.iNvramLWDistance.iValue)-contour.zLightCurtainStart);					
			
			LMSLog.d(DEBUG_TAG, "generateLightCurtainThreeDData="+iMove);
		}

		for(int i=0;i<contour.contourFilter.threeDPointListLightCurtain.size();i++)
		{
			ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListLightCurtain.get(i);

			iFilterSingleSideReal[0]++;
			ThreeDMainThread.floatBufferSingleSide[0].put(colorR);
			ThreeDMainThread.floatBufferSingleSide[0].put(colorG);
			ThreeDMainThread.floatBufferSingleSide[0].put(colorB);						
			ThreeDMainThread.floatBufferSingleSide[0].put(threeDPoint.x);
			ThreeDMainThread.floatBufferSingleSide[0].put(threeDPoint.y);
			ThreeDMainThread.floatBufferSingleSide[0].put(threeDPoint.z+iMove);
			
			iFilterTwoSideReal++;
			ThreeDMainThread.floatBufferTwoSide.put(colorR);
			ThreeDMainThread.floatBufferTwoSide.put(colorG);
			ThreeDMainThread.floatBufferTwoSide.put(colorB);						
			ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.x);
			ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.y);
			ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.z+iMove);	
		}
	}
	
	void resetThreeDDataPosition()
	{
		ThreeDMainThread.floatBufferTwoSide.clear();
		for(int sensorID=0;sensorID<LMSConstValue.LMS_WH_SENSOR_NUM;sensorID++)
		{
			ThreeDMainThread.floatBufferSingleSide[sensorID].clear();  
		}	
	}
	
	void staticShowAndSave(GLAutoDrawable glAutoDrawable)
    {   	
		LMSLog.d(DEBUG_TAG, "staticShowAndSave");

		showAndSaveToFile(glAutoDrawable,iFilterSingleSideReal[0],(FloatBuffer) ThreeDMainThread.floatBufferSingleSide[0].position(0),"left");
		if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
			||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME)
		{
			showAndSaveToFile(glAutoDrawable,iFilterSingleSideReal[1],(FloatBuffer) ThreeDMainThread.floatBufferSingleSide[1].position(0),"right");
		}
	  	showAndSaveToFile(glAutoDrawable,iFilterTwoSideReal,(FloatBuffer) ThreeDMainThread.floatBufferTwoSide.position(0),"down");
      	showAndSaveToFile(glAutoDrawable,iFilterTwoSideReal,(FloatBuffer) ThreeDMainThread.floatBufferTwoSide.position(0),"front");
      	showAndSaveToFile(glAutoDrawable,iFilterTwoSideReal,(FloatBuffer) ThreeDMainThread.floatBufferTwoSide.position(0),"rear");
      	
		if(LMSConstValue.bNvramThreeDImageQianYing.bValue == true)
		{
			showAndSaveToFile(glAutoDrawable,iFilterSingleSideReal[0],(FloatBuffer) ThreeDMainThread.floatBufferSingleSide[0].position(0),"qianYing_left");
			if(LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_DIF
				||LMSConstValue.defaultDetectType == enumDetectType.WH2_L1_SAME)
			{
				showAndSaveToFile(glAutoDrawable,iFilterSingleSideReal[1],(FloatBuffer) ThreeDMainThread.floatBufferSingleSide[1].position(0),"qianYing_right");
			}
		  	showAndSaveToFile(glAutoDrawable,iFilterTwoSideReal,(FloatBuffer) ThreeDMainThread.floatBufferTwoSide.position(0),"qianYing_down");
	      	showAndSaveToFile(glAutoDrawable,iFilterTwoSideReal,(FloatBuffer) ThreeDMainThread.floatBufferTwoSide.position(0),"qianYing_front");
	      	showAndSaveToFile(glAutoDrawable,iFilterTwoSideReal,(FloatBuffer) ThreeDMainThread.floatBufferTwoSide.position(0),"qianYing_rear");
		}
		
      	//=================================================
      	if(bRegenate == true)
      	{
			HashMap<String, Comparable> eventExtraResult = new HashMap<String, Comparable>();
			eventExtraResult.put(LMSConstValue.INTENT_EXTRA_CAR_STATE, LMSConstValue.enumCarState.LIGHTCURTAIN_REGENATE.ordinal());
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.CAR_STATE_CHANGE_INTENT,eventExtraResult);
      	}
    }
			
	float angleX = 0;
	float angleY = 0;
	float angleZ = 0;
	void dynamicShow(GLAutoDrawable glAutoDrawable,int arrayNum,FloatBuffer floatBuffer)
	{		
//		LMSLog.d(DEBUG_TAG, "show X="+angleX+" Y="+angleY+" Z="+angleZ);

		gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT); //清除屏幕和深度缓存

		if(iFilterTwoSideReal <= 0)
			return;

		gl.glLoadIdentity();	//重置当前的模型观察矩阵
    	
    	float orthBase = 0;
    	    	    	
    	float nearOrth = -50000.0f;
    	float farOrth = 50000.0f;

		gl.glLoadIdentity();	//重置投影矩阵

		//===============================================================
      	gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);	//取消设置顶点

      	orthBase = contour.carLength*2/5;
	    //车道宽作为左右视窗；车高度作为上下视窗		
		float leftOrth = (float)-orthBase;
		float rightOrth = (float)orthBase;
		float topOrth = (float)orthBase;
		float bottomOrth = (float)-orthBase;
		
		if (screenWidth <= screenHeight)
		{
			bottomOrth *= (float)screenHeight/(float)screenWidth;
			topOrth *= (float)screenHeight/(float)screenWidth;
		}
		else
		{
			leftOrth *= (float)screenWidth/(float)screenHeight;
			rightOrth *= (float)screenWidth/(float)screenHeight;			
		} 
		
		gl.glOrtho(
			leftOrth, 
			rightOrth,
			bottomOrth,
			topOrth,
			nearOrth,
			farOrth);    
		
		gl.glScalef(fEnlarge, fEnlarge, fEnlarge);	 

    	//移到中心
		gl.glTranslatef(moveX, moveY -(contour.carHeight/2), -(contour.carLength/2));	
		
		//旋转前先回归原位坐标
		gl.glTranslatef(0, (contour.carHeight/2), (contour.carLength/2));	
       	gl.glRotatef(angleX,1.0f,0.0f,0.0f);
	    gl.glRotatef(angleY,0.0f,1.0f,0.0f);
	    gl.glRotatef(angleZ,0.0f,0.0f,1.0f);	
		gl.glTranslatef(0, -(contour.carHeight/2), -(contour.carLength/2));	

		gl.glInterleavedArrays(GL2.GL_C3F_V3F,0,floatBuffer);
       	gl.glDrawArrays(GL.GL_POINTS, 0, arrayNum);
      	gl.glFlush(); 
      	
		//===============================================================
      	gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);	//取消设置顶点
	}
	
	void showAndSaveToFile(GLAutoDrawable glAutoDrawable,int arrayNum,FloatBuffer floatBuffer,String viewString)
    {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT); //清除屏幕和深度缓存
    	gl.glLoadIdentity();	//重置当前的模型观察矩阵
    	    	    	
		//===============================================================
      	gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);	//取消设置顶点

   		LMSLog.d(DEBUG_TAG,"threeD prepare gl "+viewString);

    	try{
       		ThreeDImageType threeDImageType = new ThreeDBasic().genThreeDImageType(viewString);
	    	
	    	//========================================================================
   			new ThreeDBasic().genThreeDImage(
   				contour,
   				gl,
   				threeDImageType,
   				arrayNum,floatBuffer,
   				screenWidth,screenHeight);

       		LMSLog.d(DEBUG_TAG,"threeD prepare buffer:"+arrayNum);

     		//==========================================================
	      	if(LMSConstValue.bNvramThreeDImageWithSizeFrame.bValue)
	      	{	
	      		new ThreeDBasic().drawLine(contour,gl,threeDImageType);
	      	}

     		//==========================================================
       		//刷屏缓冲一下先,作用：1.给时间显卡真正刷屏 2.让出时间给其他线程调度
       		Thread.sleep(50);
       		
       		LMSLog.d(DEBUG_TAG,"threeD prepare buffer after");
       		
            //=========================================================================
       		BufferedImage image = new AWTGLReadBufferUtil(glAutoDrawable.getGLProfile(), false).readPixelsToBufferedImage(glAutoDrawable.getGL(), 0, 0, screenWidth, screenHeight, true); 
            
        	String path = "image";
			String fileName = viewString+"."+LMSConstValue.enumImageFormat.key;
			
  			//===================================================================
       		LMSLog.d(DEBUG_TAG,"threeD write "+fileName);
       			
			File file = new File(path+"//"+fileName);
			if(file != null)
			{
				file.delete();
				
	      		new ThreeDBasic().drawSize(contour, threeDImageType, image, screenWidth, screenHeight);

				ImageIO.write(image, LMSConstValue.enumImageFormat.key, file);
			}
       		FileManager.fileCopyWithRealTime(time, path, path, fileName);
		}
		catch(Exception e)
		{
    		LMSLog.exceptionDialog(null, e);
		}
    	
		//===============================================================
      	gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);	//取消设置顶点
      	
   		LMSLog.d(DEBUG_TAG,"threeD write finish");   		
    }
    
    public BufferedImage rotate90DX(BufferedImage bi) 
    {
    	int width = bi.getWidth();     
    	int height = bi.getHeight();           
    	BufferedImage biFlip = new BufferedImage(height, width, bi.getType());           
    	for(int i=0; i<width; i++)         
    		for(int j=0; j<height; j++)             
    			biFlip.setRGB(height-1-j, width-1-i, bi.getRGB(i, j));           
    	return biFlip; 
    }    
    
    public BufferedImage rotate90SX(BufferedImage bi) {
    	int width = bi.getWidth();     
    	int height = bi.getHeight();           
    	BufferedImage biFlip = new BufferedImage(height, width, bi.getType());           
    	for(int i=0; i<width; i++)         
    		for(int j=0; j<height; j++)             
    			biFlip.setRGB(j, i, bi.getRGB(i, j));           
    	return biFlip; 
    } 
    
    public BufferedImage filp180DX(BufferedImage bi) 
    {
    	int width = bi.getWidth();     
    	int height = bi.getHeight();           
    	BufferedImage biFlip = new BufferedImage(width, height, bi.getType());           
    	for(int i=0; i<width; i++)         
    		for(int j=0; j<height; j++)             
    			biFlip.setRGB(width-1-i, j, bi.getRGB(i, j));           
    	return biFlip; 
    }    
    
    public BufferedImage filp180SX(BufferedImage bi) {
    	int width = bi.getWidth();     
    	int height = bi.getHeight();           
    	BufferedImage biFlip = new BufferedImage(width, height, bi.getType());           
    	for(int i=0; i<width; i++)         
    		for(int j=0; j<height; j++)             
    			biFlip.setRGB(i, height-1-j, bi.getRGB(i, j));           
    	return biFlip; 
    } 
    
    float fStep = 1;
    void setRotateAngleAndStep()
    {
    	if(LMSConstValue.enumThreeDMouseType.key.equals(LMSConstValue.EnumThreeDMouseType.MOUSE_AUTO_ROTATE))
    	{	    	
			if(angleX < 360)
				angleX += fStep;
	
			if(angleY < 360)
				angleY += fStep;
	
			if(angleZ < 360)
				angleZ += fStep;
    	}
    	else if(LMSConstValue.enumThreeDMouseType.key.equals(LMSConstValue.EnumThreeDMouseType.MOUSE_HAND_ROTATE))
    	{
//    		angleX = rquadX;
    		angleY = rquadY;
    		angleZ = rquadX;
       	}    	
    }
	public void display(GLAutoDrawable drawable)  
	{ 		
		if(contour == null)
			return;
		
//		LMSLog.d(DEBUG_TAG, "display.......................");
		
		resetBackColor();
		setRotateAngleAndStep();

		dynamicShow(drawable,iFilterTwoSideReal,(FloatBuffer) ThreeDMainThread.floatBufferTwoSide.position(0));
	}
	
     public void displayChanged(GLAutoDrawable drawable, boolean arg1, 
              boolean arg2) { 
     } 

     public void init(GLAutoDrawable drawable) { 
		LMSLog.d(DEBUG_TAG, "init.......................");
				
		setSize(500,300); 
		
		//============================================================
		gl = drawable.getGL().getGL2(); 
		
	   	gl.glShadeModel(GL2.GL_SMOOTH);	//启用阴影平滑
	   	gl.glClearColor(0.0f,0.0f,1.0f,1f);	//背景色
	   	gl.glClearDepth(1.0f);		//启用深度缓存
	   	gl.glEnable(GL.GL_DEPTH_TEST);	//启用深度测试
	   	gl.glDepthFunc(GL.GL_LEQUAL);		//所做深度测试的类型
	   	gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_FASTEST);	//告诉系统对透视进行修正
     } 

 	int screenWidth;
 	int screenHeight;

 	float backgroundColorR;
 	float backgroundColorG;
 	float backgroundColorB;
 	void resetBackColor()
 	{ 			
		if(LMSConstValue.enumBackgroundColor.key.equals(LMSConstValue.EnumBackgroundColor.BLACK_BACKGROUND_WHITH_FONT))
		{
			backgroundColorR = 0.0f;
			backgroundColorG = 0.0f;
			backgroundColorB = 0.0f;
		}
		else
		{
			backgroundColorR = 1.0f;
			backgroundColorG = 1.0f;
			backgroundColorB = 1.0f;
		}
		
		gl.glClearColor(backgroundColorR, backgroundColorG, backgroundColorB,0f);	//背景色			
 	}
 	
 	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { 
		LMSLog.d(DEBUG_TAG, "reshape x="+x+" y="+y+" width="+width+" height="+height);

 		//因OpenGL 預設, 你給 glDrawPixels 的圖檔資料, 它的每一個 row 的大小 ( 以 byte 來算 ), 也是可以給 4 整除的.
		screenWidth = (width/4)*4;
		screenHeight = height;
						 
		resetBackColor();
				
		gl.glViewport(0, 0, screenWidth, screenHeight);	//设置场景大小 
		
		gl.glMatrixMode(GL2.GL_PROJECTION);	    	//设置投影矩阵，负责为场景增加透视
		gl.glLoadIdentity();	//重置投影矩阵
							
//		gl.glRotatef(180,0.0f,0.0f,1.0f);
//		gl.glTranslatef(0.0f, -(topOrth-bottomOrth)/2, 0.0f);	//重新上移至中心	
		//     	GLU.gluLookAt(gl, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f);
		gl.glMatrixMode(GL2.GL_MODELVIEW);	//选择模型观察矩阵
		gl.glLoadIdentity();	//重置观察矩阵
	} 

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	} 
	
	class EventListener implements LMSEventListener {
		public void lmsTransferEvent(LMSEvent event) {
			String eventType = event.getEventType();
			HashMap eventExtra = event.getEventExtra();

	        if(eventType != null && (eventType.equals(LMSConstValue.SETTING_TRANSFER_INTENT))) 
	        {	    
				String nvram = (String) eventExtra.get(LMSConstValue.INTENT_EXTRA_SETTING_NVRAM); 
	        }
		}
	}
} 

