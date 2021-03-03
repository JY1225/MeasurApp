package http.Jason;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import CarDetect.CarDetectSetting;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
import net.sf.json.JSONObject;

public class HttpJason {
	private final static String DEBUG_TAG="httpJason";

	static int postObjNum = 0;
	
	public static ArrayBlockingQueue jdHttpJasonQueue = new ArrayBlockingQueue(LMSConstValue.JD_HTTP_JASON_PACKET_MAX); 
	
		
	public Runnable thread()
    {
		return new Runnable(){
    		@Override
    		public void run() {                  
                LMSLog.d(DEBUG_TAG,"thread run:"+Thread.currentThread().getId());

                //==============================================================
                try{
	    			while(true)
	    			{	
	    				JdHttpJason jdHttpJason = (JdHttpJason) jdHttpJasonQueue.take();
	    				
	    				JDHttpPost(jdHttpJason);
				
	    			}
                } catch (Exception e) {
    	       		LMSLog.exceptionDialog(null, e);
    			}
       	    }    
		};
    }
    
	public static void JDHttpPost(JdHttpJason jdHttpJason)
	{		
		String url = "http://dms.etms.jd.com/services/task/saveTask";
		LMSLog.d(DEBUG_TAG, "url 0="+url);
		//*
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Authorization","9A9AD19D216315D78E76F653D861CA25");
		httpPost.addHeader("RegisterNo","25");
		httpPost.addHeader("Date",jdHttpJason.sTime);
				
		JSONObject json = new JSONObject();  
		json.put("barCode", jdHttpJason.sBarCode);
		json.put("scannerTime", jdHttpJason.lTime);
		json.put("length", jdHttpJason.length/10);
		json.put("width", jdHttpJason.width/10);
		json.put("height", jdHttpJason.height/10);
		
		StringEntity s = new StringEntity(json.toString(),"utf-8"); 
		s.setContentEncoding("HTTP.UTF_8");  
        //s.setContentType("application/json");     
        s.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));   

		httpPost.setEntity(s);
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;

		try {
			httpResponse = httpClient.execute(httpPost);

			int statusCode = httpResponse.getStatusLine().getStatusCode();  //返回状态码 ，用来进行识别或者判断访问结果

			LMSLog.d(DEBUG_TAG, "statusCode=================="+statusCode);
			if(statusCode == 200){
				InputStream inStream = httpResponse.getEntity().getContent();  //要处理该数据流是否为GZIP流
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));    
                StringBuilder strber = new StringBuilder();    
  
                String line = null;    
                while ((line = reader.readLine()) != null)     
                    strber.append(line);    
                inStream.close();    

                LMSLog.d(DEBUG_TAG, "strber=============="+strber);
                
                //==================================================================
                if(strber.toString().contains("\"code\":200"))
                {
	                postObjNum++;
	            	String str = "成功上传个数："+postObjNum;
	    	   		
	            	HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
	    	   		eventExtra.put(LMSConstValue.INTENT_EXTRA_HTTP_STATE, str);
	    			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.HTTP_STATE_STRING_INTENT,eventExtra);			
                }
                else
                {
	            	String str = "上传失败："+strber;
	    	   	
	            	HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
	    	   		eventExtra.put(LMSConstValue.INTENT_EXTRA_HTTP_STATE, str);
	    			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.HTTP_STATE_STRING_INTENT,eventExtra);			                	
                }
			}
			else
			{
	        	String str = "上传失败，状态码:"+statusCode;

	        	HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
		   		eventExtra.put(LMSConstValue.INTENT_EXTRA_HTTP_STATE, str);
				LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.HTTP_STATE_STRING_INTENT,eventExtra);			
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			LMSLog.exception(e);;
		} catch (java.net.UnknownHostException|java.net.ConnectException e) {
        	String str = "服务器连接失败";
	   		HashMap<String, Comparable> eventExtra = new HashMap<String, Comparable>();
	   		eventExtra.put(LMSConstValue.INTENT_EXTRA_HTTP_STATE, str);
			LMSConstValue.lmsEventManager.sendEvent(LMSConstValue.HTTP_STATE_STRING_INTENT,eventExtra);			

			LMSLog.exception(e);;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LMSLog.exception(e);;
		}
	}
}
