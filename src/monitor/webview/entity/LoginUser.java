package monitor.webview.entity;

import java.io.Serializable;

public class LoginUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private int index;
	private int id;
	private String name;
	private String password;
	private String realName;
	private String address;
	private String ctime;
	private String email;
	private String tel;
	private String ntegration;
	private String image;
	private String role_id;
	private String openId;
	private String num;// 最大任務數
	private int shaibi;
	private String role;
	private int renrenbi;
	private int points;
	private int utype; // 0试用 1正式
	private float money; // 总金额
	
	private double border1;
	private double border2;

	private String sinausername;
	private String sinapassword;
	private String sinaappid;
	private String sinaappkey;
	private String sinalocal;
	private String appid;
	private String appkey;
	
	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getShaibi() {
		return shaibi;
	}

	public void setShaibi(int shaibi) {
		this.shaibi = shaibi;
	}

	public int getRenrenbi() {
		return renrenbi;
	}

	public void setRenrenbi(int renrenbi) {
		this.renrenbi = renrenbi;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNtegration() {
		return ntegration;
	}

	public void setNtegration(String ntegration) {
		this.ntegration = ntegration;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public int getUtype() {
		return utype;
	}

	public void setUtype(int utype) {
		this.utype = utype;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public double getBorder1() {
		return border1;
	}

	public void setBorder1(double border1) {
		this.border1 = border1;
	}

	public double getBorder2() {
		return border2;
	}

	public void setBorder2(double border2) {
		this.border2 = border2;
	}

	public String getSinausername() {
		return sinausername;
	}

	public void setSinausername(String sinausername) {
		this.sinausername = sinausername;
	}

	public String getSinapassword() {
		return sinapassword;
	}

	public void setSinapassword(String sinapassword) {
		this.sinapassword = sinapassword;
	}


	public String getSinaappkey() {
		return sinaappkey;
	}

	public void setSinaappkey(String sinaappkey) {
		this.sinaappkey = sinaappkey;
	}

	public String getSinaappid() {
		return sinaappid;
	}

	public void setSinaappid(String sinaappid) {
		this.sinaappid = sinaappid;
	}

	public String getSinalocal() {
		return sinalocal;
	}

	public void setSinalocal(String sinalocal) {
		this.sinalocal = sinalocal;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
}
