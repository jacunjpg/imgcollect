package monitor.activemq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.PicProcessByBaidu;
import monitor.pictureutil.PicProcessByQihu;
import monitor.pictureutil.PicProcessBySougou;
import monitor.pictureutil.ViewSliveUtil;
import monitor.pictureutil.zidonghuaapi2;
import monitor.util.DateUtil;

public class test implements Runnable{
	private static Logger logger = Logger.getLogger(test.class);
	private String filename;
	private HttpClient client;
	private PostMethod postMethod;
	public test(String filename, HttpClient client, PostMethod postMethod)
    {
        this.filename = filename;
        this.client = client;
        this.postMethod =postMethod;
    }
	
	public static void main(String[] sadfasd) throws HttpException, IOException, InterruptedException{  
//		String url= "http://api.bit3.cn/api/img-comp" ;  
//		List<Map<String,String>> result=ViewSliveUtil.getTaskByTaskName("mission54", "", "E:\\sendpicture\\", ""+1, "E:\\sendpicture\\2\\");	
         
 }  
	
	public static Part[] creatbuilder(String userid, File filedown1, File filedown2, String check)
	{
		try { Part[] builder={
					new FilePart("image",filedown1)
				   , new FilePart("image",filedown2) , 
		       new StringPart("userid", userid, "UTF-8")
				   , new StringPart("comptype", check, "UTF-8") };
				return builder;
		} catch (FileNotFoundException e){// TODO Auto-generated catch block
			e.printStackTrace();}
		return	null	;
		
		
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016718_22.jpg", contentType));
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016713_15.jpg", contentType));
//		return null;
	}
	public static String upload(HttpClient httpClient,String url,Part[] parts, PostMethod postMethod)
	{
		String type=DateUtil.getCurrentTimeMillis();
		HttpPost httpPost = new HttpPost(url);
		try {
			
			RequestEntity entity =new MultipartRequestEntity(parts, new HttpMethodParams());
			
			postMethod.setRequestEntity(entity);
			// 生成 HTTP POST 实体
			
			httpClient.setConnectionTimeout(500);
//			httpClient.setTimeout(500);
//			httpPost.setEntity(entity);// 设置请求参数
			logger.info("**************client start send  ****************");
			httpClient.executeMethod(postMethod);// 设置请求参数
			logger.info("================ client end send ==================");
			String result =postMethod.getResponseBodyAsString();
			// System.out.println(image_file.get(0));
			postMethod.releaseConnection();
			return result;
		} catch (Exception e) {
			System.out.println(e.toString());
			return "err";
		} finally {
			httpPost.releaseConnection();
			httpPost.abort();
			postMethod.releaseConnection();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Part[] builder =creatbuilder("f91a7f0a8457904fchgx",new File(filename),new File(filename),"face");
		String result=upload(client,"http://api.bit3.cn/api/img-comp",builder,postMethod);
		System.out.println(result);
	}
}
