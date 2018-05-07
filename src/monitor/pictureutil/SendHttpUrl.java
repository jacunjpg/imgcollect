package monitor.pictureutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import monitor.util.HttpDeal;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendHttpUrl {

	public static void main(String[] args) {
		// 配置fetchIP和端口
		String ipAndPort = "localhost:8080";
		// 查询关键字
		String query = "海洋";
		// yid
		int yid = 2;
		// 压缩包路径
		String zip = "D:\\sendzip";
		sendUrl(ipAndPort, query, yid);
		// 打包发送
		// String savePathDir=ConfigInfo.fileDir+yid+"/";
		// sendFile(zip,yid+"",savePathDir);
		// System.out.println("success");
	}

	public static void sendUrl(String ipAndPort, String query, int yid) {
		String url = "http://" + ipAndPort + "/fetch3.0/bdfetch/saveKey.do";
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("keyword", query);
		params1.put("yid", "" + yid);
		HttpDeal.post(url, params1);
	}

	public static void sendFile(String zip, String fileName, String savePathDir) {
		InputStream in;
		// savePathDir=ConfigInfo.fileDir+2+"/";
		savePathDir = "D:\\apache-tomcat-6.0.14\\webapps\\fetch3.0\\pages/images/download/2";
		File zip1 = new File(zip, fileName + ".zip");
		File files = new File(savePathDir);
		try {
			// ZipTest.ZipFiles(zip1, "", files);
			// String url = ConfigInfo.config.getProperty("sendUrl");
			String url = "http://localhost:8080/pic/rljc/api.do";
			System.out.println("url:" + url);
			System.out.println("savePathDir:" + savePathDir);
			in = new FileInputStream(zip + "/9.zip");
			HttpClient client = new HttpClient();
			PostMethod postMethod = new PostMethod(url);
			postMethod.setRequestBody(in);
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(50000);// 设置超时
			int status;
			status = client.executeMethod(postMethod);
			System.out.println("status:" + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
