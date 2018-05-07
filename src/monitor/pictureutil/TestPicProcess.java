package monitor.pictureutil;

import java.util.List;
import java.util.Map;

import monitor.util.JsonUtil;
import monitor.webview.entity.dbinfo;

public class TestPicProcess {

	/**
	 * 
	 * <p>
	 * Title void
	 * </p>
	 * <p>
	 * Description
	 * </p>
	 * 
	 * @author chwx</p>
	 * @date 2017-3-29 </p>
	 * @param args
	 */
	public static void main(String[] args) {
		// 百度识图
		// String query =
		// "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492061820116&di=58acabe7463aec842381db739b01451b&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01445b56f1ef176ac7257d207ce87d.jpg%40900w_1l_2o_100sh.jpg";
		// // String query = "图片";
		// String add = "D:\\bdst/";
		// int page = 1;
		// String nameid = "007";
		// String zip = "D:\\007zip";
		// 百度图片处理
		// List<Map<String, String>> downloadPic =
		// PicProcessByBaidu.downloadPic(query, add, page, nameid,zip);
		// 搜狗图片处理
		// List<Map<String, String>> downloadPic =
		// PicProcessBySougou.downloadPic(query, add, page, nameid,zip);
		// 360图片处理
		// List<Map<String, String>> downloadPic =
		// PicProcessByQihu.downloadPic(query, add, page, nameid,zip);

		// 百度识图
		// List<Map<String, String>> downloadPic = BDSTPic.downloadPic(add,
		// query,
		// page);

		// 百度识图改进
		// List<Map<String, String>> downloadPic =
		// PicProcessByBdst.downloadPic(query, add, page, nameid,zip);
		// System.out.println(downloadPic);

		// ==========json解析部分======================================
		// 解析json
		// 拷贝库上传返回结果
		// String json =
		// "{code:0,message:Copy library add success,number:10,id:0001}";
		// System.out.println("需要解析的json数据========="+json);
		// dbinfo info = JsonUtil.copyLibResultConvertObject(json);
		// System.out.println("code=="+info.getId());
		// System.out.println("id=="+info.getCbkname());
		// System.out.println("number=="+info.getCount());
		// System.out.println("message=="+info.getQuery());

		// 过检图片上传返回结果
		// String json =
		// "{code:0,message:\'success or error\',id:\"0001\",name:\"pic.jpg\"}";
		// System.out.println("需要解析的json数据========="+json);
		// dbinfo info = JsonUtil.ChackImgResultConvertObject(json);
		// System.out.println("code=="+info.getId());
		// System.out.println("name=="+info.getPicturename());
		// System.out.println("message=="+info.getQuery());
		// System.out.println("id=="+info.getCbkname());

		// 过检图片拷贝检测或者人脸识别返回结果
		String json = "{\"message\": \"Search success\", \"code\": 0, \"id\": \"c6\", \"relatives\": [{\"a_20170308162812_48.jpg\": [{\"path\": \"b10_20170308162812_48.jpg\", \"score\": 0.0}, {\"path\": \"b10_20170308162812_47.jpg\", \"score\": 0.0}, {\"path\": \"b10_20170308162812_95.jpg\", \"score\": 1.0177748306200787}]}, {\"a_20170308162812_60.jpg\": [{\"path\": \"b10_20170308162812_41.jpg\", \"score\": 1.0728000458676885}, {\"path\": \"b10_20170308162812_50.jpg\", \"score\": 1.0748636180341375}]}, {\"a_20170308162812_24.png\": [{\"path\": \"b10_20170308162812_24.png\", \"score\": 0.0}, {\"path\": \"b10_20170308162812_73.png\", \"score\": 1.06609132398776}, {\"path\": \"b10_20170308162812_74.jpg\", \"score\": 1.0685365362492578}]}], \"name\": []}";
		System.out.println("需要解析的json数据=========" + json);
		List<dbinfo> listInfo = JsonUtil
				.ChackImgCopyDiscernResultConvertList(json);
		for (dbinfo dbinfo : listInfo) {
			System.out.println("name==="+dbinfo.getPicturename());
			System.out.println("sorce==="+dbinfo.getCbkname());
			System.out.println("path==="+dbinfo.getPicturepath());
		}

		// 拷贝库删除（可以使用:拷贝库上传解析方法）

		// 查看指定库中的文件返回结果
		// String json =
		// "{\"code\":0,\"message\":\"success\",\"id\":0001,\"fileList\":[{\"path\":\"http://xxxx/xxxx/xxxx.jpg\"},{\"path\":\"http://xxxx/xxxx/xxxx.jpg\"}]}";
		// List<dbinfo> listInfo = JsonUtil.viewLibFile(json);
		// for (dbinfo dbinfo : listInfo) {
		// System.out.println(dbinfo.getPicturepath());
		// }

		// 查看所有拷贝库id
		// String json =
		// "{code:0,message:\"success\",faceDatabase:[{name:\"XXf\"},{name:\"YYf\"}],\"objDatabase\":[{name:\"XXo\"},{name:\"YYo\"}]}";
		// List<Map<String, String>> listMap = JsonUtil.viewCopyLibIdList(json);
		// for (Map<String, String> map : listMap) {
		// System.out.println(map.get("name"));
		// }

		// 人脸识别库上传(可以使用:拷贝库上传解析方法)

		// 上传待检测图片文件(可以使用:拷贝库上传解析方法)

		// 对图像进行检测识别，将多张图片的检测结果(群体事件返回结果)
//		 String json =
//		 "{\"prob\": [{\"14_20170414095047793_29.jpg\": 0.0},{\"14_20170414095047789_20.jpg\": 0.0}, {\"14_20170414095047789_18.jpg\": 0.0}, {\"14_20170414095047783_3.jpg\": 0.0}, {\"14_20170414095047790_21.jpg\": 0.0}, {\"14_20170414095047783_2.jpg\": 0.0}, {\"14_20170414095047784_5.jpg\": 0.0}], \"message\": \"Do QT_event detection success\", \"code\": 0, \"id\": \"c8\", \"name\": []}";
//		 List<dbinfo> listInfo = JsonUtil.imgTestResultConvertList(json);
//		 for (dbinfo info : listInfo) {
//		 System.out.println("image_name==="+info.getPicturename()+"  image_value==="+info.getPicturepath());
//		 }
		 
		 
		 
//		 JSONObject data = jsonObject.getJSONObject("relatives");
//		 net.sf.json.JSONArray jsonArray =
//		 jsonObject.getJSONArray("relatives");
//		 JSONObject jo = JSONObject.parseObject(json);
//		 System.out.println(jo);
//		 System.out.println("code=="+info.getId());
//		 System.out.println("name=="+info.getPicturename());
//		 System.out.println("message=="+info.getQuery());
//		 System.out.println("id=="+info.getCbkname());

	}

}
