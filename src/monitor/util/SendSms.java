package monitor.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

//短信发送
public class SendSms { //查询剩余短信条数
	 public static String getSmsLeft(){
		 StringBuffer res = new StringBuffer("");  
		 try {
			String urlStr = SendSimParams.SMS_LEFT_URL + "?username="+SendSimParams.USERNAME + "&password=" + SendSimParams.PASSWORD;
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        conn.setDoOutput(true);  
	        conn.setRequestMethod("GET");  
	        //read response  
            InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"utf-8");  
            BufferedReader reader = new BufferedReader(isr);  
            
            String flag = "";  
            while ((flag = reader.readLine()) != null) {  
                res.append(flag);  
            }  
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 try {
			Integer.parseInt(res.toString());
		 } catch (NumberFormatException e) {
			res.append("未开通短信");
		 }
		return res.toString();
	 }
	 
	 /**
	  * 2015年8月28日 创蓝-中国 短信接口对接
	  * */
	 public static String send(String mobiles, String content){
		System.out.println(mobiles+"-==-"+content);
		String returnString = null;
		try {
			System.out.println(mobiles+"---"+content);
			returnString = batchSend(SendSimParams.URI, SendSimParams.ACCOUNT, SendSimParams.PSWD, mobiles, content, SendSimParams.NEEDSTATUS, SendSimParams.PRODUCT, SendSimParams.EXTNO);
			System.out.println("returnString---"+returnString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	 }
	 /**
		 * 
		 * @param url 应用地址，类似于http://ip:port/msg/
		 * @param account 账号
		 * @param pswd 密码
		 * @param mobile 手机号码，多个号码使用","分割
		 * @param msg 短信内容
		 * @param needstatus 是否需要状态报告，需要true，不需要false
		 * @return 返回值定义参见HTTP协议文档
		 * @throws Exception
		 */
		public static String batchSend(String url, String account, String pswd, String mobile, String msg,
				boolean needstatus, String product, String extno) throws Exception {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod();
			try {
				URI base = new URI(url, false);
				method.setURI(new URI(base, "HttpBatchSendSM", false));
				method.setQueryString(new NameValuePair[] { 
						new NameValuePair("account", account),
						new NameValuePair("pswd", pswd), 
						new NameValuePair("mobile", mobile),
						new NameValuePair("needstatus", String.valueOf(needstatus)), 
						new NameValuePair("msg", msg),
						new NameValuePair("product", product),
						new NameValuePair("extno", extno), 
					});
				int result = client.executeMethod(method);
				if (result == HttpStatus.SC_OK) {
					InputStream in = method.getResponseBodyAsStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					return URLDecoder.decode(baos.toString(), "UTF-8");
				} else {
					throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
				}
			} finally {
				method.releaseConnection();
			}

		}
	 
     public static void main(String[] args){  
         //String phone = "15811507241";  //15652240394
//    	 String phone = "15652240394";
//         String message = "你存在催办业务,业务编号：2015 02 00 11，现阶段剩余时间：1天，请抓紧时间办理!";
//         //String message = "你存在催办业务,业务编号：2015020011，受理时间：2015-02-06 15:36:40.0，现阶段剩余时间：1天，请抓紧时间办理!发送时间："+new Date().toLocaleString();
//         
//         SendSms.sendMessage(phone, message); 
    	 String mobiles = "15652240394";//手机号码，多个号码使用","分割
 		 String content = "亲爱的用户，您的验证码是888888，5分钟内有效。测试";//短信内容
 		System.out.println("-----"+send(mobiles, content));
//    	 System.out.println(getSmsLeft());
     }
}
