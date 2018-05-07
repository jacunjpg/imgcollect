package monitor.pictureseach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import monitor.activemq.SendMessageByMqresult;
import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.DirectoryUtil;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.ImgCompress;
import monitor.util.SpringUtil;
import monitor.util.TestFtp;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

/**
 * 关键字任务图片下载
 * @author chwx
 *
 */
public class KeyWordsTaskDownload implements Runnable {
	
	private static Logger logger = Logger.getLogger(KeyWordsTaskDownload.class);
	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	private  String scsc2 ;
	private  String downloadpath;
	private  dbinfo dbinfo;
	private  int startid;
	private static int count=0;
	private static int filecount=0;
	private  File[] files=new File[2];
	private  List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
	public KeyWordsTaskDownload(String downloadpath,dbinfo dbinfo,String scsc2,int startid)
	{
		this.downloadpath=downloadpath;
		this.dbinfo=dbinfo;
		this.scsc2=scsc2;
		this.startid=startid;
	}
	

	@Override
	public void run() {
		List<Map<String, String>> picList = getContentList(scsc2);
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String add=tomcatpath+"pages/savedpictures/mission/"+"xinlangweibo/"+dbinfo.getMissionid();
		/**处理piclist,下图,压缩，入库，发送；
		 * 
		 */
		logger.debug("进入下载线程=============================");
		piclistdownload(picList,add,dbinfo);	
		logger.debug("退出下载线程=============================");
		
	}
	
	private static List<Map<String, String>> getContentList(String result) {
		List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
        JSONObject jsonobj=JSONObject.fromObject(result);
        JSONArray jsonlist=JSONArray.fromObject(jsonobj.get("data"));
        for(int i=0;i<jsonlist.size();i++)
        {
        	JSONObject json = JSONObject.fromObject(jsonlist.get(i));
        	if(json.get("original_pic")!=null&&!json.get("original_pic").equals("")&&!json.get("original_pic").equals("null"))
        	{
        	Map<String, String> content = new HashMap<String, String>();
			content.put("bdUrl", json.get("original_pic").toString());
			content.put("ywUrl", (String) json.get("url"));
			try {
				contentList.add(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	}
        	if(json.get("retweeted_original_pic")!=null&&!json.get("retweeted_original_pic").toString().equals("")&&!json.get("retweeted_original_pic").toString().equals("null"))
        	{
//        	System.out.println(json.get("retweeted_original_pic"));
        	Map<String, String> content = new HashMap<String, String>();
			content.put("bdUrl", json.get("retweeted_original_pic").toString() );
			content.put("ywUrl", (String) json.get("url"));
			try {
				contentList.add(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	}
        	
        }
		
		return contentList;
	}
	
	/*
	 * 关键字任务图片下载
	 */
	private void piclistdownload(List<Map<String, String>> urllist,String downloadpath,dbinfo dbinfo) {
		downloadpath = downloadpath + "\\";
		File path = new File(downloadpath);
		if (!path.exists())
			path.mkdirs();
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		for (int i = 0; i < urllist.size(); i++) {
			String url = urllist.get(i).get("bdUrl");
			if (!url.equals("")) {
				String host = url.substring(url.indexOf("//") + 2);
				host = host.substring(0, host.indexOf("/"));
				String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
				try {
					URL u2 = new URL(url);
					HttpURLConnection c2 = (HttpURLConnection) u2.openConnection();
					c2.addRequestProperty(
									"User-Agent",
									"Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; HUAWEI MT7-TL10 Build/HuaweiMT7-TL10) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/6.5 Mobile Safari/537.36");
					c2.addRequestProperty("Accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					c2.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
					c2.addRequestProperty("Connection", "keep-alive");
					c2.addRequestProperty("Host", host);
					c2.addRequestProperty("Referer", url);
					c2.connect();
					InputStream in = c2.getInputStream();
					int t = imageName.lastIndexOf(".");
					if(t>0){
						imageName =imageName.substring(t);
					}else{
						imageName = ".jpg";
					}
					imageName =""+dbinfo.getMissionid() + "_" + DateUtil.getCurrentTimeMillis()
							+ "_" + (count+1) + imageName;
					count++;
					String file = downloadpath + imageName;
					Map<String, String> p = new HashMap<String, String>();
					p.put("picturepath", "pages/savedpictures/mission/"+"xinlangweibo/"+dbinfo.getMissionid()+"/"+imageName);
					p.put("newName", imageName);
					p.put("oldUrl", urllist.get(i).get("ywUrl"));
					
					ReadProperties readProperties = new ReadProperties();
					String ftpenabled = readProperties.getValueByKey("ftpenabled");
					if (!new File(file).exists()) {
						downloadlist.add(p);
						FileOutputStream fo = new FileOutputStream(new File(file));
						byte[] buf = new byte[1024];
						int length = 0;
						while ((length = in.read(buf, 0, buf.length)) != -1)
							fo.write(buf, 0, length);
						in.close();
						fo.close();
						
						ImgCompress a=new ImgCompress(file);
						if(a.getheight()>500&&a.getwidth()>500)
						{
				    	a.resizeFix(ConfigInfo.picturesize, ConfigInfo.picturesize,file);
						}
						
						//如果使用FTP方式上传图片,此方法执行
						if(ftpenabled.equals("true")){
							//上传到ftp服务器开始====
							TestFtp tf = new TestFtp(dbinfo.getMissionid()+"");
							File fileFtp = new File(file);
							try {
								tf.upload(fileFtp);
							} catch (Exception e) {
								e.printStackTrace();
							}
							//上传到ftp服务器结束====
						}
						
						files[filecount]=new File(file);
						filecount++;
						
						String psend = readProperties.getValueByKey("psend");
						int ps = Integer.parseInt(psend);
						if(filecount==ps)
						{
							filecount=0;
							SendMessageByMqresult.sendpicture(dbinfo,null,downloadlist);
							insertmissionpicture(dbinfo,downloadlist);
							downloadlist = new ArrayList<Map<String, String>>();
						}
						
						//如果使用FTP方式上传图片，则删除本地图片
						if(ftpenabled.equals("true")){
							DirectoryUtil.deleteDir(new File(file));
						}
						
					}
					
					
				} catch (FileNotFoundException e1) {
					System.out.println("无法下载图片！" + url);
				} catch (IOException e2) {
					System.out.println("发生IO异常！" + url);
				}
			}
		}
		insertmissionpicture(dbinfo,downloadlist);
		downloadlist = new ArrayList<Map<String, String>>();
	}
	
	private static void insertmissionpicture(dbinfo dbinfo,
			List<Map<String, String>> result) {
		// TODO Auto-generated method stub
		dbinfo picture=null;
		List<dbinfo> picturelist=new ArrayList<dbinfo>();
		for(int i=0;i<result.size();i++)
		{
			picture=new dbinfo();
			String oldUrl=result.get(i).get("oldUrl");
			oldUrl=oldUrl.substring(oldUrl.indexOf("//")+2);
			oldUrl=oldUrl.substring(0,oldUrl.indexOf("/"));
			picture.setPicturepath(result.get(i).get("picturepath"));
			picture.setPicturename(result.get(i).get("newName"));
			picture.setPictureurl(result.get(i).get("oldUrl"));
			picture.setWeb(oldUrl);
			picture.setMissionid(dbinfo.getMissionid());
			picturelist.add(picture);
		}
		Map<String, String> params = new HashMap<String, String>();
		
		if(picturelist!=null&&picturelist.size()!=0)
		{
		missionService.insertpicture(picturelist);
		}
	}

}
