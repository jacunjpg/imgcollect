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

public class BDSTPic {
	/**百度识图图片下载
	 * 
	 * @param downloadPath		图片下载存储地址
	 * @param query				图片下载用的图片关键词，这个部分为图片在网络上能被看到的地址
	 * @param page				图片下载页数。
	 * @return					返回结果味list，每个list包括三部分 bdurl，ywurl，host 其中bdurl为搜索引擎上的地址，ywurl为原文链接的地址
	 */
	public static List<Map<String,String>> downloadPic(String downloadPath,String query,int page){
		//获取百度图片地址和源文件地址
		 List<Map<String,String>> urlList = new ArrayList<Map<String,String>>();
		 //新闻正文正则  
	        String regexPicUrl = "\"MiddleThumbnailImageUrl\":\"([\\d\\D]*?)\",";  
	        String regexFromUrl = "\"gpg\":\"([\\d\\D]*?)\",";  
	        String regexHost = "\"ThumbnailDomain\":\"([\\d\\D]*?)\",";  
	        
	        //获取网页源代码
	        String regexString = "\\{\"ThumbnailContentSign\"([\\d\\D]*?)\"dataFrom\":\"image\"\\}";
	        File path=new File(downloadPath);  
	        if(!path.exists())  
	            path.mkdirs();  
	        
	        String paramsSign;
	        String qureySign = "";
			try {
				paramsSign = "http://image.baidu.com/n/pc_search?"
						+ "queryImageUrl="+URLEncoder.encode(query, "utf-8")
						+ "&fm=result_camera"
						+ "&uptype=paste"
						+ "&drag=1";
				String htmlSign = openUrl(paramsSign,"utf-8");
		        
				 String regexSign= "'querySign': '([\\d\\D]*?)',";  
				  qureySign = getUrl(regexSign,htmlSign);

				 System.out.println(qureySign);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	        try {
	        	for(int i=0;i<page;i++){
	        		String params = "http://image.baidu.com/n/similar?"
	        				+ "queryImageUrl="+URLEncoder.encode(query, "utf-8")
	        				+ "&querySign="+qureySign
	        				+ "&word=&t=1479452407697"
	        				+ "&rn=30"
	        				+ "&sort="
	        				+ "&fr=pc"
	        				+ "&pn="+i*30;
					String html = openUrl(params,"utf-8");
			        //获取图片URL  
					List<String> listUrl  = getContent(regexString,html);  
					
					for(int j=0;j<listUrl.size();j++){
						System.out.println(listUrl.get(j));
						Map<String,String> t = new HashMap<String,String>();
						String bdUrl = getUrl(regexPicUrl,listUrl.get(j));
						String ywUrl = "";
						
							 ywUrl = getUrl(regexFromUrl,listUrl.get(j));
							 String host = getUrl(regexHost,listUrl.get(j));
							 bdUrl=bdUrl.replaceAll("\\\\/", "/");
							 ywUrl=ywUrl.replaceAll("\\\\/", "/"); 
						System.out.println("bdUrl====="+bdUrl);
						System.out.println("ywUrl====="+ywUrl);
						t.put("bdUrl", bdUrl);
						t.put("ywUrl", ywUrl);
						t.put("host", host);
						urlList.add(t);
					}
					
					download(urlList,downloadPath);  
	        	}
				
			} catch (Exception e) {
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
	        	url = url.replace("\\", "");
	            String imageName=url.substring(url.lastIndexOf("/")+1,url.length());  
	            try {  
	            	URL u2 = new URL(url);
	        		HttpURLConnection c2 = (HttpURLConnection) u2.openConnection();
	        		c2.addRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; HUAWEI MT7-TL10 Build/HuaweiMT7-TL10) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/6.5 Mobile Safari/537.36");
	        		c2.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	        		c2.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
	        		c2.addRequestProperty("Connection", "keep-alive");
	        		c2.addRequestProperty("Host",  urlMap.get("host"));
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
