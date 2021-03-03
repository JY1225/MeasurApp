package http.WebService;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.StringReader;
import java.io.UnsupportedEncodingException;  
import java.net.ConnectException;
import java.net.MalformedURLException;  
import java.net.URL;  
import java.net.URLConnection;  
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;   
import javax.xml.parsers.SAXParserFactory;   
import org.xml.sax.InputSource;   
import org.xml.sax.SAXException;   
import org.xml.sax.helpers.DefaultHandler;   
  
import AppBase.ImplementPC.LogImplementPC;
import SensorBase.LMSConstValue;
import SensorBase.LMSLog;
  
public class HttpPost {  	
	String urlStr;
	
    protected void post(String _urlStr) {  
        try {  
        	urlStr = _urlStr;
        	
            URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
              
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());    
                   
            if(LMSConstValue.bNvramWebServiceDebugDialog. bValue)
            {
            	new HttpDialog().run();
            }
            
            out.write(xmlParameter);  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));  
            String line = "";  
            StringBuffer buf = new StringBuffer();  
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                buf.append(new String(line.getBytes(),"utf-8"));  
            }  
            System.out.println(buf.toString());  
            parse(buf.toString());  
            

        } catch (MalformedURLException e) {  
			LMSLog.exceptionDialog("URL格式错误:"+urlStr, e);  
        } catch (ConnectException e) {  
            LMSLog.exceptionDialog("与服务器连接异常:"+urlStr,e);  
        } catch (IOException e) {  
            LMSLog.exceptionDialog("异常:"+urlStr,e,"xml=\""+xmlParameter+"\"");  
        }  
    }  
  
    private static String xmlParameter;
    public static void setXmlParameter(String str)
    {
    	xmlParameter = str;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {  
        String url = "http://localhost:8459/test.asmx/WS_GetGabSizeVehList";  
        new HttpPost().post(url);  
    } 
    
    
    public static void parse(String protocolXML) {   
        
        try {   
             SAXParserFactory saxfac = SAXParserFactory.newInstance();      
             SAXParser saxparser = saxfac.newSAXParser();   
             XMLParse tsax = new XMLParse();   
             saxparser.parse(new InputSource(new StringReader(protocolXML)),tsax);                
         } catch (Exception e) {   
             LMSLog.exception(e);   
         }   
     }   
    
	public class HttpDialog extends Thread
	{
		public void run()
		{
			JOptionPane op = new JOptionPane(xmlParameter,JOptionPane.INFORMATION_MESSAGE);
		       
			JDialog dialog;
			dialog = op.createDialog(urlStr);
			dialog.setBounds(400, 200, 1000, 400);  
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setAlwaysOnTop(false);
			dialog.setModal(false);
			dialog.setVisible(true);
			dialog.show();
		}
	}
}   
   