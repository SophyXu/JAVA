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
			localPath = "D:\\spider\\"+count+".jpg";  					//图片存储的路径
			File file =new File(localPath);								//新建文件
			picConnection = (HttpURLConnection) sourceURL.openConnection();	//打开连接
			picConnection.connect();										//进行连接
			is = picConnection.getInputStream();	
			os = new FileOutputStream(file);
			int f = is.read();
			while (f != -1)										//将图片信息读入
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
