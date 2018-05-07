package monitor.pictureutil;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import monitor.util.DateUtil;
import monitor.util.ImgCompress;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class zidonghuaapi {
	private static Logger logger = Logger.getLogger(zidonghuaapi.class);
	public static void main(String[] args){
		String time="2017-05-21 18:51:16.0";
		time=time.substring(0,time.length()-2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			Date time1  =sdf.parse(time);
			System.out.println(sdf.format(time1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static MultipartEntityBuilder creatbuilder(String userid, File filedown1, File filedown2, String check)
	{
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
				HTTP.UTF_8);
		builder.addPart("userid", new StringBody(userid, contentType));
		builder.addPart("comptype", new StringBody(check, contentType));
        builder.addPart("image", new FileBody(filedown1));
        builder.addPart("image", new FileBody(filedown2));
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016718_22.jpg", contentType));
//		builder.addPart("image", new StringBody("http://123.57.31.85/pictwoview/pages/savedpictures/mission/8/8_20170508110016713_15.jpg", contentType));
		return builder;
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
	
	public static String upload(CloseableHttpClient httpClient,String url, MultipartEntityBuilder builder)
	{
		String type=DateUtil.getCurrentTimeMillis();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpEntity entity = builder.build();// 生成 HTTP POST 实体
			httpPost.setEntity(entity);// 设置请求参数
			CloseableHttpResponse response = httpClient.execute(httpPost);// 设置请求参数
			String result = EntityUtils.toString(response.getEntity(),
					HTTP.UTF_8);
			response.close();
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
	
	public static CloseableHttpClient initHttpClient(String Url,int time) {
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				time).setConnectTimeout(time).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		HttpHost localhost = new HttpHost(Url);
		// 将最大连接数增加到200
		cm.setMaxTotal(200);
		// 将每个路由基础的连接增加到20
		cm.setDefaultMaxPerRoute(20);
		// 将目标主机的最大连接数增加到50
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(cm)
				.setDefaultRequestConfig(requestConfig).build();
		return httpClient;
	}
}
