package monitor.webview.entity;

import java.io.Serializable;

/**
 * 图片信息实体
 * 
 * <p>Title PictrueInfo</p>
 * <p>Description </p>
 * @author chwx</p>
 * @date 2017-3-29 </p>
 */
public class PictureInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pictrueid;
	private String picturename;
	private String picturepath;
	private String pictureurl;
	private String web;
	public int getPictrueid() {
		return pictrueid;
	}
	public void setPictrueid(int pictrueid) {
		this.pictrueid = pictrueid;
	}
	public String getPicturename() {
		return picturename;
	}
	public void setPicturename(String picturename) {
		this.picturename = picturename;
	}
	public String getPicturepath() {
		return picturepath;
	}
	public void setPicturepath(String picturepath) {
		this.picturepath = picturepath;
	}
	public String getPictureurl() {
		return pictureurl;
	}
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	
}
