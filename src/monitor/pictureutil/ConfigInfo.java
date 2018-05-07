package monitor.pictureutil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import monitor.util.Configur;

public class ConfigInfo {
	
	public static Configur config = new Configur("config/properties/sysInfo.properties");
	
	public static String SESSION_USER = "user";
	//public static String DEFAULT_URL = "pages/longriver/taskIssued/issued.jsp";
	//服务器具体接口
	public static String cbkimpl="11000/image_set_upload";			//比对，人脸库库操作接口
	public static String piccheckimpl="11000/image_search_upload";	//比对，人脸检测操作接口
	public static String qtcheckimpl="20003/terror_detect_upload";	//群体事件检测操作接口
	//深圳服务器ip 共3台 
	public static String shenzhenfwq[]={"http://114.119.10.195:","http://114.119.10.198:","http://114.119.10.199:"};
	//接口操作类型 type
	//比对 人脸库操作接口类型 
	public static String cbkupload="0";     //比对库上传
	public static String rlkupload="1";     //人脸库上传
	public static String cbkdelete="3";     //比对,人脸库删除
	public static String cbkdetail="4";     //比对，人脸库详细内容查看
	public static String cbklist="5";		//比对，人脸库详细内容查看
	//比对 人脸 检测操作接口类型
	public static String picupload="7"; 	//检测图片上传
	public static String copypicsearch="8";	//比对检测启动
	public static String facepicsearch="9";	//人脸检测启动
	//群体事件接口type类型
	public static String qtpicupload="7";	//群体事件上传图片参数
	public static String qtpicstart="8";    //群体事件检测启动参数
	
	
	public static String jsFileName ="c:\\pem\\pic.js";    //解析用脚本
	public static String keypem ="c:\\pem\\pkcs8_private_key.pem"; 
//	public static String jsFileName ="/data/pem/pic.js";    //解析用脚本
//	public static String keypem ="/data/pem/pkcs8_private_key.pem"; 
	
//	public static String tomcatpath=System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf("\\"))
//									+"\\webapps\\pictwoview\\";
	
	public static String tomcatpath=config.getProperty("tomcatpath");
	
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(500);
//	public static String tomcatpath="d:\\img"
//			+"\\webapps\\pictwoview\\"; //自用
	
	public static String downloadpath="pages\\downloadpath\\";
	
	public static String fakezipget="c://fake/fakezipget/";		
	
//	public static String mqip="123.57.31.85";	


//	public static String mqip="192.168.101.8";	//南昌


//	public static String ftpip	 = "192.168.101.11";
//	public static int 	 ftpport = 21;
//	public static String ftpuser = "ftpuser";
//	public static String ftppassword = "chwx@123456";
	

//	public static String ftpip	 = "192.168.101.11";
//	public static int 	 ftpport = 21;
//	public static String ftpuser = "ftpuser";
//	public static String ftppassword = "chwx@123456";

	
	public static String zipadd="c://fake/fakezip/";

	public static String descadd="c://fake/sendpicture/";
	
	public static String imgzip="c://fake/imgzip/";

	public static String fakeresultget="c://fake/resultget/"; 
	
	public static String resultpictures="c://fake/resultpictures/";
	
	public static String cbkpicture="c://fake/cbkpictures/";
	
	//分类检测（fljc）发送文件到activeMq的发送类型
	public static final String FLJC_MQ_SENDTYPE = "1";
	//分类检测（fljc）发送文件到activeMq的机器ip
	public static final String FLJC_MQ_IP = "192.168.20.111";
	//分类检测（fljc）发送文件到activeMq的名称
	public static final String FLJC_MQ_MODENAME = "fljcupload";
	//分类检测（fljc）activeMq接收是否有文件isNotFile  true有文件false没有
	public static final String FLJC_MQ_ISNOTFILE = "true";
	
	//接收端启动线程存贮
	public static final Map<String,Object> receiveSocket = new HashMap<String, Object>();
	
	public static int weibocount=10;
	
	public static int zdhapicount=20;

	public static int count = 0;
	
	public static int NUM = 8;		//抓取机器人数量
	
//	public static boolean ISTRUE = false; //是否启用微博下任务方法(生产环境改为true)

	public static int picturesize =500; //压缩图片的最短边上限
	
	//属地任务回溯时间（H）
	public static int BACKHOUR = -1;
	//属地任务每秒回溯的数据量间隔时间（M）
	public static int BACKMINUTE = -2;
	
	//回溯的数据每500条创建一个线程
	public static int CREATETHREADLIMIT = 500;
	
	//百度搜索图片页数
	public static String BDPAGENUM=config.getProperty("bdpagenum");
	
	//百度搜索图片页数
	public static String MAXTHREADPOOL=config.getProperty("maxthreadpool");
	
}
