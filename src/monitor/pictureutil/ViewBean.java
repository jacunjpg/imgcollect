package monitor.pictureutil;

import java.io.Serializable;

public class ViewBean implements Serializable{

	private String id;
	private String name;
	private String start;
	private String end;
	private String type;
	private String idkey;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdkey() {
		return idkey;
	}
	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}
}
