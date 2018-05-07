package monitor.pictureseach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.webview.entity.dbinfo;

public class BackDataCreateThread implements Runnable {
	private static Logger logger = Logger.getLogger(BackDataCreateThread.class);
	private List<dbinfo> infoList;
	private int filecount = 0;
	private dbinfo info = null;

	public BackDataCreateThread(List<dbinfo> list,dbinfo info) {
		this.infoList = list;
		this.info = info;
	}

	@Override
	public void run() {
		logger.debug("backTask thread MQ start run========="
				+ DateUtil.getCurrentTimeMillis());
		// 判断发送url的来源：微博url还是服务器地址
		ReadProperties readProperties = new ReadProperties();
		String picpathflag = readProperties.getValueByKey("picpathflag");
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		logger.debug("backTask start loop start========="+DateUtil.getCurrentTimeMillis()+"++++++---"+infoList.size());
		// 遍历返回的结果
		for (dbinfo pinfo : infoList) {
			Map<String, String> picInfo = new HashMap<String, String>();
			// 如果使用weibo图片url,此方法执行
			if (picpathflag.equals("true")) {
				// picturepath存放微博图片的地址url
				picInfo.put("pictureurl", pinfo.getOriginalurl());
			} else {
				// picturepath存放服务器图片的地址
				picInfo.put("pictureurl", pinfo.getPicturepath());
			}
			picInfo.put("picturepath", pinfo.getPicturepath());
			picInfo.put("originalurl", pinfo.getOriginalurl());// 微博图片地址
			picInfo.put("picturetime", pinfo.getPicturetime());// 微博创建时间
			picInfo.put("tasktype", pinfo.getTasktype());// 图片所属的任务类型
			picInfo.put("picturename", pinfo.getPicturename());
			picInfo.put("newName", pinfo.getPicturename());
			picInfo.put("oldUrl", pinfo.getPictureurl());
			picInfo.put("content", pinfo.getContent());

			downloadlist.add(picInfo);

			// 满2张图片发送mq
			filecount++;
			String psend = readProperties.getValueByKey("psend");
			int ps = Integer.parseInt(psend);
			if (filecount == ps) {
				filecount = 0;
				logger.debug("backTask send MQ start double========="
						+ DateUtil.getCurrentTimeMillis());
				// 将信息发送到MQ
				SendPicToMQ sendPicToMQ = new SendPicToMQ();
				sendPicToMQ.backSendMessage(info, downloadlist);
				logger.debug("backTask send MQ end double========="
						+ DateUtil.getCurrentTimeMillis());
				downloadlist = new ArrayList<Map<String, String>>();
			}
		}
		// 不足两个的发送单个
		if (downloadlist.size() > 0) {
			logger.debug("backTask send MQ start single========="
					+ DateUtil.getCurrentTimeMillis());
			SendPicToMQ sendPicToMQ = new SendPicToMQ();
			sendPicToMQ.backSendMessage(info, downloadlist);
			logger.debug("backTask send MQ end single========="
					+ DateUtil.getCurrentTimeMillis());
		}
		logger.debug("backTask start loop end========="+DateUtil.getCurrentTimeMillis());
		logger.debug("backTask thread MQ end run========="
				+ DateUtil.getCurrentTimeMillis());
	}

}
