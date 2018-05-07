package monitor.webview.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.PictureInfo;
import monitor.webview.entity.WebSitePicture;
import monitor.webview.service.IFaceDetectService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("faceDetectService")
public class FaceDetectServiceImpl implements IFaceDetectService {

	private static Logger logger = Logger.getLogger(FaceDetectServiceImpl.class);
	
	@Resource(name="basicDaoSupport")
	private IBasicDaoSupport daoSupport;

	@Override
	public void addPictrueInfo(List<PictureInfo> params) {
		try {
			logger.info("批量插入图片库开始！");
//			daoSupport.findForObject("pictrueMapper.addPicture", params);
			daoSupport.findForObject("pictureMapper.insertPictureList", params);
			logger.info("批量插入图片库成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertWebSitePicture(List<WebSitePicture> webSiteList) {
		try {
			logger.info("批量插入网站图片库开始！");
			daoSupport.findForObject("webSitePicture.insertWebSite", webSiteList);
			logger.info("批量插入网站图片库成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
