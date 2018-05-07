package monitor.pictureutil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Pictureimpl {
	
	
	public static void main(String[] args){
		creatbuilder(null,null,null,null,null);
		System.out.println("OK");
	}
	/**
	 * 
	 * @param httpClient 	服务器的请求配置
	 * @param url		 	服务器的目标接口
	 * @param bulider		参数对象
	 * 
	 * @return 输出结果为字符串类型的json 如果发生异常则结果为"err";
	 */
	 
	public static String upload(CloseableHttpClient httpClient,String url, MultipartEntityBuilder builder)
	{
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
	
	/**构建builder 该操作只构建参数对象，不进行传输操作
	 * 
	 * @param type 			type代表操作的类型具体见ConfigInfo
	 * @param id   			id在上传检测时代表检测任务的编号，在对库操作时代表库的编号
	 * @param image 		删除操作时被删除的图片名称，删除全部时为all
	 * @param imagesetfile	上传的图片文件，应为。zip格式
	 * @param cbks			比对，人脸检测时用到的被检测拷贝库的编号
	 * @return 
	 */
	public static MultipartEntityBuilder creatbuilder(String type,String id,String image,File imagesetfile,String[] cbks)
	{
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
				HTTP.UTF_8);
		if(type!=null&&!type.equals(""))
		builder.addPart("type",new StringBody(type,contentType));
		if(id!=null&&!id.equals(""))
		builder.addPart("id",new StringBody(id,contentType));
		if(image!=null&&!image.equals(""))
		builder.addPart("image",new StringBody(image,contentType));
		if(imagesetfile!=null&&imagesetfile.exists())
		{
		ContentBody cbFile = new FileBody(imagesetfile);
		builder.addPart("imageset_file", cbFile);
		}
		if(cbks!=null&&cbks.length>0)
		{
			String cbklist="";
			for(int i=0;i<cbks.length;i++)
			{
				if(i==0)
				{
					cbklist=cbks[i];
				}
				else
				{
					cbklist=cbklist+"####"+cbks[i];
				}
			}
		builder.addPart("db_names", new StringBody(cbklist, contentType));
		}
		return builder;
	}
	
	/**设置请求,ip代表服务器ip,impl代表接口，time代表设置时间.
	 * 
	 * @param ip 代表服务器的ip地址
	 * @param impl 代表调用的接口名
	 * @param time 代表请求的等待时间(ms)
	 * @return
	 */
	public static CloseableHttpClient initHttpClient(String ip,String impl,int time) {
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				time).setConnectTimeout(time).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		HttpHost localhost = new HttpHost(ip+impl);
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
