import java.io.*;
import java.net.*;
import java.util.*;

public class ReadURL {
	public static void main(String args[])
	{
		try 
		{
			URL sourceURL = new URL("http://www.zju.edu.cn/");		//�򿪸���ҳ
			Set set = new HashSet();	
			set.clear();
			BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));//����ҳԴ���뵱�������ļ�
			int i, j;
			String u = "http://www.zju.edu.cn/";
			String buf, str;	// Buffer to store lines
			int count = 0;
			while(!(null == (buf=in.readLine())))
			{
				while ((j = buf.indexOf(".jpg\""))>=0)			//һ��buf�п����ж�� .jpg ����Ϣ��������while�ֱ��ȡ
				{	
					if ((i = buf.lastIndexOf("src=\"",j)) >=0)
					{
						str = u+buf.substring(i+5,j+4);					//��ȡͼƬ����ַ
						if (!set.contains(str))
						{						//�ü���ȥ�ظ�
							set.add(str);
							System.out.println(str);					
							new ReadJPG(str).getpicture();				//��ȡͼƬ
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
