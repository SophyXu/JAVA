import java.io.*;
import java.net.*;
import java.util.*;

public class ReadURL {
	public static void main(String args[])
	{
		try 
		{
			URL sourceURL = new URL("http://www.zju.edu.cn/");		//打开该网页
			Set set = new HashSet();	
			set.clear();
			BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));//将网页源代码当做输入文件
			int i, j;
			String u = "http://www.zju.edu.cn/";
			String buf, str;	// Buffer to store lines
			int count = 0;
			while(!(null == (buf=in.readLine())))
			{
				while ((j = buf.indexOf(".jpg\""))>=0)			//一个buf中可能有多个 .jpg 的信息，所以用while分别获取
				{	
					if ((i = buf.lastIndexOf("src=\"",j)) >=0)
					{
						str = u+buf.substring(i+5,j+4);					//获取图片的网址
						if (!set.contains(str))
						{						//用集除去重复
							set.add(str);
							System.out.println(str);					
							new ReadJPG(str).getpicture();				//获取图片
							count++;
						}
					}
					buf = buf.substring(j+5,buf.length());	
				}
				if (count > 20) break;
			  }
			  System.out.println(count);
			  in.close();		// Close the input stream	  
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
