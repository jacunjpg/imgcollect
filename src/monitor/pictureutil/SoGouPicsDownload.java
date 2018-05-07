package monitor.pictureutil;
import java.io.BufferedReader;  
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;  
import java.net.URLConnection;  
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;  
import java.util.Map;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  











import javax.script.ScriptException;

import net.sf.json.JSONObject;  
public class SoGouPicsDownload {
	  
	/**
	 *  搜狗图片下载
	 * @param query		图像下载关键词
	 * @param add		图片的存储路径，实际存储路径为add/nameid/图片名
	 * @param page		下载图片的页数
	 * @param nameid	图片存储路径
	 * @return			返回的结果为list list中包含两项，bdurl与ywurl，bdurl为图片在搜狗图片中的链接，ywurl为图片原文链接
	 */
	 public List<Map<String,String>> downloadPic(String query,String add,int page,String nameid){
		//获取百度图片地址和源文件地址
		 List<Map<String,String>> urlList = new ArrayList<Map<String,String>>();
		 //新闻正文正则  
	        String regex1 = "\"thumbUrl\":\"([\\d\\D]*?)\",";  
	        String regex2 = "\"page_url\":\"([\\d\\D]*?)\",";  
	        //获取网页源代码
	        String regexString = "\\{\"type\"([\\d\\D]*?)\\}";
	        String downloadPath=add+nameid+"\\";  
	        File path=new File(downloadPath);  
	        if(!path.exists())  
	            path.mkdirs();  
	        try {
	        	for(int i=0;i<page;i++){
	        		
	        		
	        		String params = "http://pic.sogou.com/pics?"
	        				+ "query="+URLEncoder.encode(query, "utf-8")
	        				+ "&mood=0"
	        				+ "&picformat=0"
	        				+ "&mode=1&di=2"
	        				+ "&start="+i*36
	        				+ "&reqType=ajax"
	        				+ "&tn=0"
	        				+ "&reqFrom=result";
	   //http://pic.sogou.com/pics?query=%BD%AD%D4%F3%C3%F1&mood=0&picformat=0&mode=1&di=2&start=144&reqType=ajax&tn=0&reqFrom=result

					String html = openUrl(params,"utf-8");
					System.out.println(html);
			        //获取图片URL  
					List<String> listUrl  = getContent(regexString,html);  
					
					for(int j=0;j<listUrl.size();j++){
						System.out.println(listUrl.get(j));
						Map<String,String> t = new HashMap<String,String>();
						String bdUrl = getUrl(regex1,listUrl.get(j));
						String ywUrl = "";
						try {
							 ywUrl = getUrl(regex2,listUrl.get(j));
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("bdUrl====="+bdUrl);
						System.out.println("ywUrl====="+ywUrl);
						t.put("bdUrl", bdUrl);
						t.put("ywUrl", ywUrl);
						urlList.add(t);
					}
					
					download(urlList,downloadPath);  
	        	}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return urlList;  
	 }
	 
	    /** 
	     * 访问url返回url的html代码 
	     */  
	    public static String openUrl(String currentUrl,String charset) {  
	        InputStream is = null;  
	        BufferedReader br = null;  
	        URL url;  
	        StringBuffer html = new StringBuffer();  
	        try {  
	            url = new URL(currentUrl);  
	            URLConnection conn = url.openConnection();  
	            conn.setReadTimeout(5000);  
	            conn.connect();  
	            is = conn.getInputStream();  
	            br = new BufferedReader(new InputStreamReader(is,charset));  
	            String str;  
	            while (null != (str = br.readLine())) {  
	                html.append(str).append("\n");  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (br != null) {  
	                try {  
	                    br.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	            if (is != null) {  
	                try {  
	                    is.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	  
	        }  
	        return html.toString();  
	    }  
	      
	    private static List<String> getContent(String regex,String text) {  
	    	List<String> a = new ArrayList<String>(); 
	    	String content = "";
	        Pattern pattern = Pattern.compile(regex);  
	        Matcher matcher = pattern.matcher(text);  
	        while(matcher.find()) {  
	            content = matcher.group(1).toString();  
	            try {
	            	//BaiduPicScript.getUrlTrue(content)
	            	a.add(content);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	        }  
	       
	        return a;  
	    }  
	    private static String getUrl(String regex,String text) {  
	    	String picUrl = "";
	        Pattern pattern = Pattern.compile(regex);  
	        Matcher matcher = pattern.matcher(text);  
	        while(matcher.find()) {  
	        	picUrl = matcher.group(1).toString();  
	            
	        }  
	       
	        return picUrl;  
	    }
	    public static void download(List<Map<String,String>> listUrl,String downloadPath)  
	    {         
	        for(Map<String,String>  urlMap:listUrl)  
	        {  
	        	String url = urlMap.get("bdUrl");
	            String imageName=url.substring(url.lastIndexOf("/")+1,url.length());  
	            try {  
	            	URL u2 = new URL(url);
	        		HttpURLConnection c2 = (HttpURLConnection) u2.openConnection();
	        		c2.addRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; HUAWEI MT7-TL10 Build/HuaweiMT7-TL10) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/6.5 Mobile Safari/537.36");
	        		c2.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	        		c2.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
	        		c2.addRequestProperty("Connection", "keep-alive");
	        		c2.addRequestProperty("Referer", url);
	        		c2.connect();
	        		InputStream in = c2.getInputStream();                
	                String file=downloadPath+imageName;  
	                FileOutputStream fo=new FileOutputStream(new File(file));  
	                byte[] buf=new byte[1024];  
	                int length=0;  
	                while((length=in.read(buf,0,buf.length))!=-1)  
	                    fo.write(buf, 0, length);  
	               // System.out.println(Thread.currentThread().getName()+url+"下载完成！");  
	                in.close();  
	                fo.close();  
	            } catch (FileNotFoundException e1) {                  
	                System.out.println("无法下载图片！"+url);  
	            } catch(IOException e2) {  
	                System.out.println("发生IO异常！"+url);  
	            }  
	        }  
	    }  
}
