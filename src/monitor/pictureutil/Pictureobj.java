package monitor.pictureutil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import monitor.util.HttpDeal;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

public class Pictureobj {
	
	public static void main(String[] args){
		File zip=new File("E:\fakezip\1.zip");
//		HttpPost httpPost = new HttpPost("localhost:8088/fetch0/pic?links=http://www.xici.net");
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("links","http://www.xici.net" );
//		HttpDeal.post("http://localhost:8088/fetch0/pic", params);
		qtpicupload(zip, ConfigInfo.shenzhenfwq[1], "b1");
		qtpicsearch(ConfigInfo.shenzhenfwq[1],"b1");
		getcbklist(ConfigInfo.shenzhenfwq[1]);
		System.out.println("OK");
	}
	/**用于上传比对或人脸库
	 * 
	 * @param zipfile 	被上传的图片压缩zip包 其中只包含图片(不包含文件夹)
	 * @param type		上传的检测库类型 0为比对库 1为人脸库
	 * @param cbkid		上传的比对库或人脸库的编号 一般以字母+数字的组合
	 * @param fwqip		上传的服务器ip
	 * @return
	 */
	public static String uploadcbk(File zipfile,String type,String cbkid,String fwqip){
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(type, cbkid, null, zipfile, null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	/**用于上传比对或人脸库
	 * 
	 * @param zipfilepath	被上传的图片压缩zip包的路径
	 * @param type			上传的检测库类型 0为比对库 1为人脸库
	 * @param cbkid			上传的比对库或人脸库的编号 一般以字母+数字的组合
	 * @param fwqip			上传的服务器ip
	 * @return
	 */
	public static String uploadcbk(String zipfilepath,String type,String cbkid,String fwqip){
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(type, cbkid, null, new File(zipfilepath), null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	/**用于上传比对或人脸库
	 * 
	 * @param pictures    	被上传的图片
	 * @param zipfilepath	zip包路径
	 * @param type			上传的检测库类型 0为比对库 1为人脸库
	 * @param cbkid			上传的比对库或人脸库的编号 一般以字母+数字的组合
	 * @param fwqip			上传的服务器ip
	 * @return
	 */
	public static String uploadcbk(File[] pictures,String zipfilepath,String type,String cbkid,String fwqip){
		File zip =new File(zipfilepath);
		try {
			ZipTest.ZipFiles(zip, "", pictures);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(type, cbkid, null, zip, null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	
	/**用于上传比对或人脸库 默认压缩与d://zip.zip
	 * 
	 * @param pictures    	被上传的图片
	 * @param type			上传的检测库类型 0为比对库 1为人脸库
	 * @param cbkid			上传的比对库或人脸库的编号 一般以字母+数字的组合
	 * @param fwqip			上传的服务器ip
	 * @return
	 */
	public static String uploadcbk(File[] pictures,String type,String cbkid,String fwqip){
		File zip =new File("d://zip.zip");
		try {
			ZipTest.ZipFiles(zip, "", pictures);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(type, cbkid, null, zip, null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	/**获取检测库的列表
	 * 
	 * @param fwqip		服务器的地址
	 * @return
	 */
	public static String getcbklist(String fwqip)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.cbklist, "1",null, null, null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	/**获取检测库的详细图片信息
	 * 
	 * @param fwqip		服务器的地址
	 * @param cbkid		检测库编号
	 * @return
	 */
	public static String getcbkdetail(String fwqip,String cbkid)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.cbkdetail, cbkid,null, null, null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	
	/**
	 * 
	 * @param fwqip		服务器的地址
	 * @param cbkid		删除的比对库编号
	 * @return
	 */
	public static String deletecbk(String fwqip,String cbkid)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.cbkdelete, cbkid,"all", null, null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	
	/**删除检测库图片
	 * 
	 * @param fwqip		服务器地址
	 * @param cbkid		检测库id
	 * @param image		被删除的图片名
	 * @return
	 */
	public static String deletepicture(String fwqip,String cbkid,String image)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.cbkimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.cbkdelete, cbkid,image, null, null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.cbkimpl, builder);
		return result;
	}
	/**	群体事件检测图片上传
	 * 
	 * @param zipfile		上传的待检图片zip包
	 * @param fwqip			服务器ip	
	 * @param id			任务的编号
	 * @return
	 */
	public static String qtpicupload(File zipfile,String fwqip,String id)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.qtcheckimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.qtpicupload, id , null, zipfile , null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.qtcheckimpl, builder);
		return result;
	}
	/**启动群体事件检测
	 * 
	 * @param fwqip			服务器ip
	 * @param id			任务编号
	 * @return
	 */
	public static String qtpicsearch(String fwqip,String id)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.qtcheckimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.qtpicstart, id , null, null , null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.qtcheckimpl, builder);
		return result;
	}
	/**	检测库检测图片上传
	 * 
	 * @param zipfile		上传的待检图片zip包
	 * @param fwqip			服务器ip	
	 * @param id			任务的编号
	 * @return
	 */
	public static String picupload(File zipfile,String fwqip,String id)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.piccheckimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.picupload, id , null, zipfile , null);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.piccheckimpl, builder);
		return result;
	}
	/**	比对检测启动
	 * 
	 * @param fwqip			服务器ip	
	 * @param id			任务的编号
	 * @param cbk			比对库识别代码,用于检测哪几个比对库，多个比对库用####相隔 例:a1####a2####a3 或 a1####a2
	 * @return
	 */
	public static String copypicsearch(String fwqip,String id,String[] cbk)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.piccheckimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.copypicsearch, id , null, null , cbk);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.piccheckimpl, builder);
		return result;
	}
	/**	人脸检测启动
	 * 
	 * @param fwqip			服务器ip	
	 * @param id			任务的编号
	 * @param cbk			人脸库识别代码,用于检测哪几个比对库，多个人脸库用####相隔 例:a1####a2####a3 或 a1####a2
	 * @return
	 */
	public static String facepicsearch(String fwqip,String id,String[] cbk)
	{
		CloseableHttpClient httpClient	= 	Pictureimpl.initHttpClient(fwqip,ConfigInfo.piccheckimpl,50000);//库操作接口
		MultipartEntityBuilder builder	= 	Pictureimpl.creatbuilder(ConfigInfo.facepicsearch, id , null, null , cbk);
		String result					=	Pictureimpl.upload(httpClient, fwqip+ConfigInfo.piccheckimpl, builder);
		return result;
	}
	

}
