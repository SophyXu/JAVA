package webspider_TXT;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;

public class FileDownLoader {
	static int count = 0;
	static String done = "";
	static byte[] response;
	private void saveToLocal(byte[] data,String filePath)
	{
		try {
			DataOutputStream out=new DataOutputStream(new FileOutputStream(new File(filePath)));
			for(int i=0;i<data.length;i++)
			out.write(data[i]);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getPlainText(String str){
		try{
			Parser parser = new Parser();
			parser.setInputHTML(str);
			
			StringBean sb = new StringBean();
			sb.setLinks(false);
			sb.setReplaceNonBreakingSpaces(true);
			sb.setCollapse(true);
			parser.visitAllNodesWith(sb);
			str = sb.getStrings();
		} catch (Exception e){
			e.printStackTrace();
		}
		return str;
	}

	public String  downloadFile(String url)
	{
		  String filePath=null;
		  HttpClient httpClient=new HttpClient();
		  	  httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);	  
		  GetMethod getMethod=new GetMethod(url);	 
		  getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
		  getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			new DefaultHttpMethodRetryHandler());		  
		  try{ 
			  int statusCode = httpClient.executeMethod(getMethod);
			  if (statusCode != HttpStatus.SC_OK) 
			  {
				  System.err.println("Method failed: "+ getMethod.getStatusLine());
				  filePath=null;
			  }		  
			  byte[] responseBody = getMethod.getResponseBody();//读取为字节数组
			  String convert = new String(responseBody,"ISO-8859-1");
			  done += getPlainText(convert);
			  response = done.getBytes("ISO-8859-1");
		  } catch (HttpException e) {
			  System.out.println("Please check your provided http address!");
			  e.printStackTrace();
		  } catch (IOException e) {
			  e.printStackTrace();
			  System.out.println("出错了！");
		  } finally {		  
			  filePath="d:\\spider\\" + "《天龙八部》" + ".txt";
			  saveToLocal(response,filePath);
			  getMethod.releaseConnection();	
		  }
		  return filePath;
	}
}