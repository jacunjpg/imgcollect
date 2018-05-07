package monitor.pictureseach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.pictureutil.ReadProperties;
import monitor.webview.entity.dbinfo;

public class BackDateSend_bak implements Runnable {

	private List<dbinfo> infoList = null;
	
	public BackDateSend_bak(List<dbinfo> infoList){
		this.infoList=infoList;
	}
	
	@Override
	public void run() {
		//判断发送url的来源：微博url还是服务器地址
		ReadProperties readProperties = new ReadProperties();
		String picpathflag = readProperties.getValueByKey("picpathflag");
		//遍历返回的结果
		for (dbinfo info : infoList) {
			List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
			Map<String, String> picInfo = new HashMap<String, String>();
			//如果使用weibo图片url,此方法执行
			if(picpathflag.equals("true")){
				//picturepath存放微博图片的地址url
				picInfo.put("pictureurl", info.getOriginalurl());
			}else{
				//picturepath存放服务器图片的地址
				picInfo.put("pictureurl", info.getPicturepath());
			}
			picInfo.put("picturepath", info.getPicturepath());
			String imageName = info.getPicturepath().substring(info.getPicturepath().lastIndexOf("/")+1,info.getPicturepath().length());
			picInfo.put("picturename", imageName);
			picInfo.put("newName", imageName);
			picInfo.put("oldUrl", info.getPictureurl());
			picInfo.put("content", info.getContent());
			
			downloadlist.add(picInfo);
			//将信息发送到MQ
			SendPicToMQ sendPicToMQ = new SendPicToMQ();
			sendPicToMQ.backSendMessage(info, downloadlist);
		}
		
	}

}
