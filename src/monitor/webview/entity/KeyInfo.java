package monitor.webview.entity;

import java.io.Serializable;
import java.util.List;

public class KeyInfo implements Serializable {
	
	private static final long serialVersionUID = 6258011831559955304L;
	
	private Integer id;
	private Integer keywordId;
	private Integer plat;
	private String value;
	private String addTime;
	private String url;
	private Integer urlid;
	private String title;
	private Integer result;
	private String remurl;
	private String name;
	private String locpath;
	private List<KeyInfo> items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getUrlid() {
		return urlid;
	}
	public void setUrlid(Integer urlid) {
		this.urlid = urlid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getRemurl() {
		return remurl;
	}
	public void setRemurl(String remurl) {
		this.remurl = remurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPlat() {
		return plat;
	}
	public void setPlat(Integer plat) {
		this.plat = plat;
	}
	public String getLocpath() {
		return locpath;
	}
	public void setLocpath(String locpath) {
		this.locpath = locpath;
	}
	public List<KeyInfo> getItems() {
		return items;
	}
	public void setItems(List<KeyInfo> items) {
		this.items = items;
	}
}
