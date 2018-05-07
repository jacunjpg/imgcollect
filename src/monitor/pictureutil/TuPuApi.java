package monitor.pictureutil;

import java.io.File;
import java.util.ArrayList;
 
public class TuPuApi {
   private static String secretId = "56e0ebb01e52c4f856425bbd";

	public static void main(String[] args) {
		// secret id
		tupuapi(new File("e:\\path").listFiles());
	}
	
	public static String tupuapi(File[] filelist) {
		// secret id
		String secretId = "56e0ebb01e52c4f856425bbd";
		// private KEY path
		String privateKey = ConfigInfo.keypem;
		// request Url
		String requestUrl = "http://api.open.tuputech.com/v3/recognition/";
		// fileList imageFile or url
		ArrayList<String> fileList = new ArrayList<String>();
		// tags
		String tags[] = { "tag1", "tag2" };
		for(int i=0;i<filelist.length;i++)
		{
			if(filelist[i]!=null)
			fileList.add(filelist[i].getPath());
		}
	/**
   	 * @param secretId
   	 *            用户secretId
   	 * @param pkPath
   	 *            用户私钥路径
	 * @param requestUrl
	 *            请求接口地址
 	 */
//		Api api = new Api(secretId, privateKey,requestUrl);
     /**
   	 * @param fileType
   	 *            传入的数据类型，ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE为本地文件
   	 *            ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE 为图片 Url
   	 * @param fileLists
   	 *            文件集合(本地文件路径或者 Url)
   	 * @param tags
   	 *            [可选] 用于给图片附加额外信息（比如：直播客户可能传房间号，或者主播ID信息）。方便后续根据tag搜索到相关的图片
   	 * @return
   	 */
//		JSONObject result = api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileList,tags);
//		JSONObject json=JSONObject.fromObject(result.get("5808841f5e1778ef49219a99").toString());
//		String jsonlist=json.getString("fileList");
		
		return null;
	}
}

