package monitor.webview.service;

import java.util.List;
import java.util.Map;

import monitor.webview.entity.PictureInfo;
import monitor.webview.entity.WebSitePicture;

public interface IFaceDetectService {

	//添加图片
	void addPictrueInfo(List<PictureInfo> params);
	//添加网站图片
	void insertWebSitePicture(List<WebSitePicture> webSiteList);

}
