package monitor.pictureutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import monitor.util.DateUtil;
import monitor.util.ImgCompress;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

public class zidonghuaapi2 {
     
    private static Logger logger = Logger.getLogger(zidonghuaapi.class);
	public static void main(String[] args){
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	    connectionManager.setMaxTotalConnections (100);//总的连接数  
	    connectionManager.setMaxConnectionsPerHost (50);//每个host的最大连接数
	    
		File file1=new File("e:/jiance/55631.jpg");
		File file2=new File("e:/jiance/55631.jpg");
		HttpClient httpClient=zidonghuaapi2.initHttpClient("http://api.bit3.cn/api/img-comp",5000,connectionManager);
		Part[] builder = zidonghuaapi2.creatbuilder("f91a7f0a8457904fchgx",file1,file2,"face");
		String result=zidonghuaapi2.upload(httpClient,"http://api.bit3.cn/api/img-comp",builder);
		System.out.println(result);
	}
	public static Part[] creatbuilder(String userid, File filedown1, File filedown2, String check)
	{
		try 
		{				
			Part[] builder={ new FilePart("image",filedown1)
						   , new FilePart("image",filedown2)
						   , new StringPart("userid", userid, "UTF-8")
						   , new StringPart("comptype", check, "UTF-8") };
			return builder;
		}
		catch (FileNotFoundException e){// TODO Auto-generated catch block
		e.printStackTrace();}
		
		
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016718_22.jpg", contentType));
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016713_15.jpg", contentType));
		return null;
		
//
//		Part[] builder={new StringPart("userid", userid, "UTF-8")
//		   , new StringPart("comptype", check, "UTF-8") };
//		return builder;
	}
	
	public static MultipartEntityBuilder createseqingbuilder(String userid, File[] pics) throws IOException
	{
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
				HTTP.UTF_8);
		builder.addPart("userid", new StringBody(userid, contentType));
		for(int i=0;i<10&&i<pics.length;i++)
		{
			if(pics[i]!=null)
			{
			ImgCompress a=new ImgCompress(pics[i].getPath());
	    	a.resizeFix(256, 256,pics[i].getPath());
	    	builder.addPart("image", new FileBody(pics[i]));
			}
		}
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016718_22.jpg", contentType));
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016713_15.jpg", contentType));
		return builder;
	}
	
	public static String upload(HttpClient httpClient,String url,Part[] parts)
	{
		String type=DateUtil.getCurrentTimeMillis();
		HttpPost httpPost = new HttpPost(url);
		try {
			PostMethod postMethod = new PostMethod(url);
			RequestEntity entity =new MultipartRequestEntity(parts, new HttpMethodParams());
			postMethod.setRequestEntity(entity);
			// 生成 HTTP POST 实体
//			httpPost.setEntity(entity);// 设置请求参数
			//超时设置
			httpClient.executeMethod(postMethod);// 设置请求参数
			String result =postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			// System.out.println(image_file.get(0));
			return result;
		} catch (Exception e) {
			System.out.println(e.toString());
			return "err";
		} finally {
			httpPost.releaseConnection();
			httpPost.abort();
		}
	}
	
	public static HttpClient initHttpClient(String Url,int time, MultiThreadedHttpConnectionManager connectionManager) {
		// 设置请求和传输超时时间
		connectionManager.setMaxTotalConnections(100);
		connectionManager.setMaxConnectionsPerHost(50);
		HttpClient httpClient = new HttpClient();  
        httpClient.setHttpConnectionManager( connectionManager );  
        httpClient.setConnectionTimeout(time);
        httpClient.setTimeout(time);
		return httpClient;
	}
}
