import java.net.URL;
import java.io.File;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.io.FileOutputStream;

public class ReadJPG {
	String url;
	String localPath = null;
	InputStream is = null;
	OutputStream os = null;
	HttpURLConnection picConnection = null;
	static int count = 0;
	ReadJPG (String u)
	{
		url = u;
	}
	public String getpicture()
	{
		String str = "";
		try{
			URL sourceURL = new URL(url);
			count ++;
			localPath = "D:\\spider\\"+count+".jpg";  					//ͼƬ�洢��·��
			File file =new File(localPath);								//�½��ļ�
			picConnection = (HttpURLConnection) sourceURL.openConnection();	//������
			picConnection.connect();										//��������
			is = picConnection.getInputStream();	
			os = new FileOutputStream(file);
			int f = is.read();
			while (f != -1)										//��ͼƬ��Ϣ����
			{
				os.write(f);
				f = is.read();
			}
			System.out.println(count+".jpg is saved!");
			is.close();
			os.close();
		} catch(Exception e) {
			System.out.println("File error:\n" + e);
		}
		return str;
	}
}
