package ThreeD;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException; 
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import AppBase.appBase.CarTypeAdapter;
import Ben.CNN.CNN;
import Ben.CNN.CNN_PredictMain;
import Ben.Space3d;
import Ben.Algorithm.SpaceCloud;
import FileManager.FileManager;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import layer.algorithmLayer.Contour;
import layer.algorithmLayer.ThreeDPoint;
import SensorBase.LMSConstValue;
import SensorBase.LMSConstValue.enumDetectType;
import SensorBase.LMSLog;
import ThreeD.ThreeDBasic.ThreeDImageType;
import lmsEvent.LMSEvent;
import lmsEvent.LMSEventListener;
import lmsEvent.LMSEventManager;

public class ThreeDGIPC extends JFrame implements GLEventListener { 
	private final static String DEBUG_TAG="ThreeDGIPC";
	    
    float x1 = 0.0f;             //���͵�X���� 
    float y1 = 0.0f;             //���͵�y���� 
    long rsize = 50;               //Ϊ���Ϳ��Ԥ����λ�û���� 
    float xstep = 1.0f;            //��Xÿ��λ�Ƶľ���,��Ȼ���ԸĴ�Щ 
    float ystep = 1.0f;            //��Yÿ��λ�Ƶľ��� 
    float windowWidth;             //���ﲻ��ָ����߿�ĵĿ������Ӿ�ͶӰ���Ҷ� 
    float windowHeight;            //���ﲻ��ָ����߿�ĵĸߣ������Ӿ�ͶӰ�Ķ��� 

    GLProfile glp;
    GL2 gl;                         //OPENGL�����ӿ� 
    GLCanvas glcanvas;             //����java.awt.Canvas, GLCanvas��Ҫ������ʾ����OPENGL��Ч�� 
    GLCapabilities capabilities;   //ָ����һ��OpenGL�Ĺ��ܣ���Ⱦ���ݱ���֧�֣���ɫ����ȣ��Լ������Ƿ������á� 

	int iFilterTwoSideSum;
	int iFilterSingleSideSum[] = new int[LMSConstValue.LMS_WH_SENSOR_NUM];
	int iFilterTwoSideReal;
	int iFilterSingleSideReal[] = new int[LMSConstValue.LMS_WH_SENSOR_NUM];

    boolean bRegenate = false;
    public void setLayout()
    {
		//==============================================================================
    	int SETTING_WIDTH = 100;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{WIDTH,SETTING_WIDTH};
		gridBagLayout.rowHeights = new int[]{HEIGHT};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0,Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		boundWidth = WIDTH+SETTING_WIDTH;
		LMSLog.d(DEBUG_TAG,"boundWidth============================================="+boundWidth);
		setBounds(0, 100, boundWidth, HEIGHT);

		//==============================================================================
		int gridX,gridY;
		
		gridX = 0;
		gridY = 0;
		
		GridBagConstraints gbc_canvas = new GridBagConstraints();
		gbc_canvas.insets = new Insets(0, 0, 5, 5);
		gbc_canvas.fill = GridBagConstraints.BOTH;
		gbc_canvas.gridx = gridX;
		gbc_canvas.gridy = gridY;
		getContentPane().add(glcanvas, gbc_canvas);         //���������һ��Component:glcanvas 
		
		JButton buttonYEnlarge = new JButton("Y+");
		GridBagConstraints gbc_buttonYEnlarge = new GridBagConstraints();
		gbc_buttonYEnlarge.anchor = GridBagConstraints.NORTH;
		gbc_buttonYEnlarge.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonYEnlarge.insets = new Insets(0, 0, 5, 0);
		gbc_buttonYEnlarge.gridwidth = 1;
		gbc_buttonYEnlarge.gridx = 1;
		gbc_buttonYEnlarge.gridy = gridY;
		getContentPane().add(buttonYEnlarge, gbc_buttonYEnlarge);		
		buttonYEnlarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
    }
    
    public ThreeDGIPC() throws HeadlessException { 
		LMSLog.d(DEBUG_TAG, "ThreeDGIPC");

		EventListener eventListener = new EventListener();
		LMSEventManager.addListener(eventListener);

		//======================================================
    	try{
    		glp = GLProfile.getMaxFixedFunc(true);
        	capabilities = new GLCapabilities(glp);        //ʵ����capabilities 
    		
        	glcanvas = new GLCanvas(capabilities);      //ʵ����glcanvas 
        	glcanvas.addGLEventListener(this);          //��glcanvas���GL�¼����� 
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

    	this.addWindowListener(new WindowAdapter() {     //��������ӹر��¼� 
    		public void windowClosing(WindowEvent e) { 
    			System.exit(0); 
    		} 
    	}); 

    	if(LMSConstValue.bNvramThreeDFrame.bValue == false)
    	{
    		setType(java.awt.Window.Type.UTILITY); //ʹӦ�ó���ͼ�겻��ϵͳ״̬����ʾ
    	}
    	
    	setTitle("��������"); 
    	if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE9))
    	{
    		WIDTH = WIDTH*9/10;
    		HEIGHT = HEIGHT*9/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE8))
    	{
    		WIDTH = WIDTH*8/10;
    		HEIGHT = HEIGHT*8/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE7))
    	{
    		WIDTH = WIDTH*7/10;
    		HEIGHT = HEIGHT*7/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE6))
    	{
    		WIDTH = WIDTH*6/10;
    		HEIGHT = HEIGHT*6/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE5))
    	{
    		WIDTH = WIDTH*5/10;
    		HEIGHT = HEIGHT*5/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE4))
    	{
    		WIDTH = WIDTH*4/10;
    		HEIGHT = HEIGHT*4/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE3))
    	{
    		WIDTH = WIDTH*3/10;
    		HEIGHT = HEIGHT*3/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE2))
    	{
    		WIDTH = WIDTH*2/10;
    		HEIGHT = HEIGHT*2/10;	    		
    	}
    	else if(LMSConstValue.enumThreeDImageSize.key.equals(LMSConstValue.enumThreeDImageSize.ThreeDImageSIZE1))
    	{
    		WIDTH = WIDTH*1/10;
    		HEIGHT = HEIGHT*1/10;	    		
    	}
    		
		//==============================================================================
    	setLayout();
    	
		//==============================================================================
		Dimension d = getSize();
 		LMSLog.d(DEBUG_TAG,"threeD prepare gl "+d.width+" "+d.height);

//		setSize(WIDTH,HEIGHT); 
		setVisible(true); 	
		setResizable(false);
//		init(glcanvas);
//		reshape(glcanvas,0,0,700,500);
		
    	if(LMSConstValue.bNvramThreeDFrame.bValue == false||LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
    	{
    		setLocation(-boundWidth,100);
    	}
     } 

    static int WIDTH = 1004;
    static int HEIGHT = 606;
	int boundWidth;
     
	String time;
	float colorR,colorG,colorB;
	final float RED_R = 1.0f,RED_G = 0.0f,RED_B = 0.0f; //��ɫ
	final float BLUE_R = 0.5f,BLUE_G = 0.5f,BLUE_B = 1.0f; //��ɫ
	final float BLACK_R = 0.0f,BLACK_G = 0.0f,BLACK_B = 0.0f; //��ɫ
	final float WHITE_R = 1.0f,WHITE_G = 1.0f,WHITE_B = 1.0f; //��ɫ
	final int CAR_IN_LINE = 10;

	Contour contour = null;
		
	void draw(GLAutoDrawable glAutoDrawable)
    {   
		LMSLog.d(DEBUG_TAG, "threeD draw..................");
		
		if(contour == null)
		{
			return;
		}
				
      	//=================================================
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");//�������ڸ�ʽ
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
		if(LMSConstValue.bNvramThreeDDisplayPointMax.bValue == true)
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
			if(LMSConstValue.bNvramThreeDDisplayPointMax.bValue == true)
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
			LMSLog.d(DEBUG_TAG+sensorID,"ASDF="+contour.widthMaxPoint[sensorID].threeDPoint.x+" "+contour.widthMaxPoint[sensorID].threeDPoint.y+" "+contour.widthMaxPoint[sensorID].threeDPoint.z);
			
			for(int i=0;i<contour.contourFilter.threeDPointListSide[sensorID].size();i++)
			{				
	     		ThreeDPoint threeDPoint = contour.contourFilter.threeDPointListSide[sensorID].get(i);
					
				//------------------------------------------------------------------------
	    		if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_AUTO))
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
				if(threeDPoint.region == 0)
				{
					iFilterSingleSideReal[threeDPoint.sensorID]++;
					iFilterTwoSideReal++;
					
					ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(colorR);
					ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(colorG);
					ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(colorB);						
					ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(threeDPoint.x);
					ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(threeDPoint.y);
					ThreeDMainThread.floatBufferSingleSide[threeDPoint.sensorID].put(threeDPoint.z);
					
					ThreeDMainThread.floatBufferTwoSide.put(colorR);
					ThreeDMainThread.floatBufferTwoSide.put(colorG);
					ThreeDMainThread.floatBufferTwoSide.put(colorB);						
					ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.x);
					ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.y);
					ThreeDMainThread.floatBufferTwoSide.put(threeDPoint.z);	
				}
			}			
    	}
		
		//----------------------------------------------------------------
		if(CarTypeAdapter.sNvramCarTypeString.sValue.equals(CarTypeAdapter.CAR_TYPE_AUTO))
		{
			//String result = CNN.predict(CNN_PredictMain.theta1, CNN_PredictMain.theta2, SpaceCloud.pointCloudXZToMatrix(pointList, 100, 40, 240));
			//LMSLog.d(DEBUG_TAG,"�������ͣ�"+result);
		}
		
		//========================================================================================
		ThreeDMainThread.floatBufferTwoSide.clear();  
		for(int sensorID=0;sensorID<LMSConstValue.LMS_WH_SENSOR_NUM;sensorID++)
		{
			ThreeDMainThread.floatBufferSingleSide[sensorID].clear();  
		}
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
    }
    	
	float downPosition;
	float upPosition;
	float leftPosition = 0;
	float rightPosition = 0;

	float downPositionLB;
	float upPositionLB;
	float leftPositionLB = 0;
	float rightPositionLB = 0;

	void showAndSaveToFile(GLAutoDrawable glAutoDrawable,int arrayNum,FloatBuffer floatBuffer,String viewString)
    {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT); //�����Ļ����Ȼ���
    	gl.glLoadIdentity();	//���õ�ǰ��ģ�͹۲����
    	
		//===============================================================
      	gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);	//ȡ�����ö���

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
       		//ˢ������һ����,���ã�1.��ʱ���Կ�����ˢ�� 2.�ó�ʱ��������̵߳���
       		Thread.sleep(50);
       		
       		LMSLog.d(DEBUG_TAG,"threeD prepare buffer after");
       		
            BufferedImage image; 

            //=========================================================================
	      	//����ͼƬ
	        ByteBuffer fb = ByteBuffer.allocateDirect(screenWidth * screenHeight * 3);//.order(ByteOrder.nativeOrder());
	//http://www.cnblogs.com/sunnyjones/articles/798237.html
	//http://blog.csdn.net/u012270174/article/details/17652049        
	//        gl.glPixelStorei(gl.GL_UNPACK_ALIGNMENT, 1);
	        gl.glReadPixels(0, 0, screenWidth, screenHeight, gl.GL_RGB, gl.GL_UNSIGNED_BYTE, fb);
	
	        image = new BufferedImage(screenWidth, screenHeight,BufferedImage.TYPE_INT_RGB);
	        
		    int[] pixels = new int[screenWidth * screenHeight];
	        int bindex;
	        // convert RGB data in ByteBuffer to integer array
	        for (int i=0; i < pixels.length; i++) {
	        	//����ת
	        	//��ת180��
	        	bindex = (pixels.length-i-1) * 3;
				pixels[i] =
				((fb.get(bindex) << 16)) +
				((fb.get(bindex+1) << 8)) +
				((fb.get(bindex+2) << 0));
	        }
	        
			image.setRGB(0, 0, screenWidth, screenHeight, pixels, 0 , screenWidth);	

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
      	gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);	//ȡ�����ö���
      	
   		LMSLog.d(DEBUG_TAG,"threeD write finish");   		
    }
    
	public void display(GLAutoDrawable drawable)  
	{ 		
		resetBackColor();
		
		draw(drawable);
		
		contour = null;		
	}
     
     public void displayChanged(GLAutoDrawable drawable, boolean arg1, 
              boolean arg2) { 
     } 

     public void init(GLAutoDrawable drawable) { 
		LMSLog.d(DEBUG_TAG, "init.......................");
		
		//============================================================
		gl = drawable.getGL().getGL2(); 
		
	   	gl.glShadeModel(GL2.GL_SMOOTH);	//������Ӱƽ��
	   	gl.glClearColor(0.0f,0.0f,1.0f,1f);	//����ɫ
	   	gl.glClearDepth(1.0f);		//������Ȼ���
	   	gl.glEnable(GL.GL_DEPTH_TEST);	//������Ȳ���
	   	gl.glDepthFunc(GL.GL_LEQUAL);		//������Ȳ��Ե�����
	   	gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_FASTEST);	//����ϵͳ��͸�ӽ�������
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
		
		gl.glClearColor(backgroundColorR, backgroundColorG, backgroundColorB,0f);	//����ɫ			
 	}
 	
 	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    	 
 		//��OpenGL �A�O, ��o glDrawPixels �ĈD�n�Y��, ����ÿһ�� row �Ĵ�С ( �� byte ���� ), Ҳ�ǿ��Խo 4 ������.
		screenWidth = (width/4)*4;
		screenHeight = height;
						 
		resetBackColor();
				
		gl.glViewport(0, 0, screenWidth, screenHeight);	//���ó�����С 
		
		gl.glMatrixMode(GL2.GL_PROJECTION);	    	//����ͶӰ���󣬸���Ϊ��������͸��
		gl.glLoadIdentity();	//����ͶӰ����
							
//		gl.glRotatef(180,0.0f,0.0f,1.0f);
//		gl.glTranslatef(0.0f, -(topOrth-bottomOrth)/2, 0.0f);	//��������������	
		//     	GLU.gluLookAt(gl, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f);
		gl.glMatrixMode(GL2.GL_MODELVIEW);	//ѡ��ģ�͹۲����
		gl.glLoadIdentity();	//���ù۲����
	} 

 	public void threeDOneContour(Contour _contour,boolean _bRegenate) {         
		contour = _contour;
		bRegenate = _bRegenate;
		
		glcanvas.display();                  //���̲߳�ͣ����display() 	
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

				if(nvram.equals(LMSConstValue.bNvramThreeDFrame.nvramStr)||nvram.equals(LMSConstValue.bNvramThreeDScreenSnap.nvramStr))
				{
		    		if(LMSConstValue.bNvramThreeDFrame.bValue == false||LMSConstValue.bNvramThreeDScreenSnap.bValue == false)
			    		setLocation(-boundWidth,100);
		    		else
			    		setLocation(10,100);
				}
	        }
		}
	}
} 

