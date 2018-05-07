package monitor.activemq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JFileChooser;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.ReadProperties;
import monitor.robot.service.BaiduSearchRobot;
import monitor.robot.service.QihuSearchRobot;
import monitor.robot.service.SinaLocalBackRobot;
import monitor.robot.service.SougouSearchRobot;
import monitor.robot.service.sinalocalsearchRobot;
import monitor.robot.service.sinasearchRobot;
import monitor.util.DirectoryUtil;
import monitor.util.ExpireRobot;
import monitor.util.InitSystemServlet;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.BackTask;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.GrabTask;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.RobotManage;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.BlobMessage;
import org.apache.log4j.Logger;

public class ReceiveMessageByMqmission {
	
	private static Logger logger = Logger.getLogger(ReceiveMessageByMqmission.class);
	public static void main(String[] args) {
		// String url = "D:/mqfile/84.zip";
		// File file = new File(url);// 发送的文件
		String receiveType = "1";// 接收的类型 1发布一对一 ；2订阅一对多
		String isNotFile = "";
		String ip = "192.168.20.118";// 接收ip
		String modeName = "mission";// 模式名称
		try {
			receive(receiveType, ip, isNotFile, modeName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void receivemission()
	{
		logger.debug("receivemission启动");
		String receiveType = "1";// 接收的类型 1发布一对一 ；2订阅一对多
		String isNotFile = "flase";
		//读取配置文件
		ReadProperties readProperties = new ReadProperties();
		String ip = readProperties.getValueByKey("mqip");// 接收ip
//		String ip = ConfigInfo.mqip;// 接收ip
		String modeName = "mission";// 模式名称
		try {
			ReceiveMessageByMqmission.receive(receiveType, ip, isNotFile, modeName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * Title void Description
	 * 
	 * @author jacun
	 * @param modeName
	 * @param ip
	 * @param receiveType
	 * @date 2017-4-11上午10:43:10
	 * @throws JMSException
	 */
	public static void receive(String receiveType, String ip, String isNotFile,
			String modeName) throws JMSException {
		logger.debug("开始接收2");
		// 获取 ConnectionFactory
		//不支持自动连接
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
//				"tcp://" + ip + ":61616");
		//"failover:("tcp://:61616")?initialReconnectDelay=1000&maxReconnectDelay=30000"
		ReadProperties readProperties = new ReadProperties();
		String mqusername = readProperties.getValueByKey("mqusername");
		String mqpassword = readProperties.getValueByKey("mqpassword");
		//支持自动连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(mqusername, mqpassword,
				"failover:(tcp://"+ip+":61616)?initialReconnectDelay=1000&maxReconnectDelay=30000");
//		 创建 Connection
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// 创建 Session
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		// 创建 Destinatione
		// 判断是一对一还是一对多
		if ("2".equals(receiveType)) {
			// 一对多
			logger.debug("一对多接收数据3");
			receiveTopic(session, isNotFile, modeName);
		} else {
			// 一对一
			logger.debug("一对一接收数据3");
			receiveQueue(session, isNotFile, modeName);
		}

	}

	private static void receiveTopic(Session session, String isNotFile,
			String modeName) {
		try {
			final String isFile = isNotFile;
			Destination destination = session.createTopic(modeName);
			
			// 创建 Consumer
			MessageConsumer consumer = session.createConsumer(destination);
			// 注册消息监听器，当消息到达时被触发并处理消息
			consumer.setMessageListener(new MessageListener() {
				// 监听器中处理消息
				public void onMessage(Message message) {
					if ("true".equals(isFile)) {
						logger.debug("有文件接收数据4");
						ReceiveMessageByMqmission.receiveFile(message);
					} else {
						logger.debug("无文件接收数据4");
//						ReceiveMessageByMqmission.receiveData(message);

					}

				}

			});
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	private static void receiveQueue(Session session, String isNotFile,
			String modeName) {
		try {
			final String isFile = isNotFile;
			Destination destination = session.createQueue(modeName);
//			Queue queue =new ActiveMQQueue(modeName+"?consumer.prefetchSize=10");  
//			MessageConsumer consumer = session.createConsumer(queue);
			// 创建 Consumer
			MessageConsumer consumer = session.createConsumer(destination);
			// 注册消息监听器，当消息到达时被触发并处理消息
			consumer.setMessageListener(new MessageListener() {
				// 监听器中处理消息

				public void onMessage(Message message) {
					if ("true".equals(isFile)) {
						logger.debug("有文件接收数据4");
						ReceiveMessageByMqmission.receiveFile(message);
					} else {
						logger.debug("无文件接收数据4");
//						ReceiveMessageByMqmission.receiveData(message,robotManage);
						//================任务接收，纳入机器人管理====================================
						RobotManage robotManage = new RobotManage();
						if(robotManage.getNum()>0){
							logger.debug("开始run");
							//执行
							ReceiveMessageByMqmission.receiveData(message,robotManage);
						}
						//当机器人数没有空闲时候，一直睡眠
						while(robotManage.getNum()<=0){
							logger.debug("sleep.....");
							try {
								Thread.sleep(1000*2);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						//====================================================

					}

				}

			});
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	protected static void receiveData(Message message,RobotManage robotManage) {
//		String sendType = null;
		String jsonData = null;
//		String website = null;
		try {
			TextMessage msg = (TextMessage) message;
//			sendType = msg.getStringProperty("sendType");
			jsonData = msg.getStringProperty("jsonData");
//			website = msg.getStringProperty("website");
		} catch (JMSException e) {
			e.printStackTrace();
		}
		logger.debug("无文件接收成功5");
		//获取开始时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = sdf.format(new Date());
		//引入service
		IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
		
		//添加任务id
		GrabTask grabTask = new GrabTask();
		
		dbinfo dbinfo = new dbinfo();
		dbinfo=(dbinfo) JsonHelper.JSONToObj(jsonData, dbinfo.class);
		grabTask.setTaskid(dbinfo.getMissionid());
		
		//修改任务状态
		faketrans missiontrans =new faketrans();
		missiontrans.updateMissionStatus(dbinfo.getMissionid());
		
		//初始化一个机器人的任务id
		String robotid = "";
		//备注是否是属地任务
		String remark = "";
		//百度|搜狗|360
		//baidu
		if(dbinfo.getSearchmode().contains("baidu"))
		{
			//创建机器人数量减一
			robotManage.createRobot();
			robotid = robotManage.saveSearchRobot(dbinfo,dbinfo.getSearchmode(),"0");
			Collecttask robotTask = robotManage.getCollecttaskById(robotid);
			BaiduSearchRobot robot = new BaiduSearchRobot(robotTask);
			robot.startrobot();
			grabTask.setSinasearchRobot(robot);
			InitSystemServlet.grabTaskList.add(grabTask);
		}
		//sougou
		if(dbinfo.getSearchmode().contains("sougou"))
		{
			//创建机器人数量减一
			robotManage.createRobot();
			robotid = robotManage.saveSearchRobot(dbinfo,dbinfo.getSearchmode(),"0");
			Collecttask robotTask = robotManage.getCollecttaskById(robotid);
			SougouSearchRobot robot = new SougouSearchRobot(robotTask);
			robot.startrobot();
			grabTask.setSinasearchRobot(robot);
			InitSystemServlet.grabTaskList.add(grabTask);
		}
		//qihu
		if(dbinfo.getSearchmode().contains("qihu"))
		{
			//创建机器人数量减一
			robotManage.createRobot();
			robotid = robotManage.saveSearchRobot(dbinfo,dbinfo.getSearchmode(),"0");
			Collecttask robotTask = robotManage.getCollecttaskById(robotid);
			QihuSearchRobot robot = new QihuSearchRobot(robotTask);
			robot.startrobot();
			grabTask.setSinasearchRobot(robot);
			InitSystemServlet.grabTaskList.add(grabTask);
		}
		//========================
		//
		if(dbinfo.getSearchmode().contains("www.sinaweibo.com"))
		{
			//创建机器人数量减一
			robotManage.createRobot();
			robotid = robotManage.saveRobot(dbinfo,dbinfo.getSearchmode(),"0");
			Collecttask robotTask = robotManage.getCollecttaskById(robotid);
			sinasearchRobot robot = new sinasearchRobot(robotTask);
			robot.startrobot();
			grabTask.setSinasearchRobot(robot);
			InitSystemServlet.grabTaskList.add(grabTask);
		}
		if(dbinfo.getSearchmode().contains("www.sinalocalmission"))
		{
			
			//获取本机ip
			String addr = "";
			try {
				addr = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			remark="L";
			boolean robot_start_flag=true;
			//20170727新增控制属地任务的创建数量
			/**
			 * appid 和 appkey  当前登录人的配置信息
			 * 下载任务后判断
			 * 正在执行的任务对应的用户的appid_r和appkey_r
			 * appid.equals(appid_r)&&appkey.equals(appkey_r)是否为true
			 * true：有正在执行的属地化任务，不需要创建新的机器人，新增任务与机器人关系即可
			 * false：创建新的机器人，新增任务与机器人关系
			 */
			//查询属地化任务的创建用户所对应的信息
			IAuthorityService authorityService=(IAuthorityService) SpringUtil.getObject("authorityService");
			Map<String,String> params = new HashMap<String,String>();
			params.put("name", dbinfo.getUser());
			LoginUser userinfo=authorityService.getAuthorityByName(params);
			String appid = userinfo.getAppid();
			String appkey = userinfo.getAppkey();
			
			//获取机器人列表中是否有正在执行的属地任务
			List<Collecttask> collecttask = missionService.getRunningTaskList();
			for (Collecttask task : collecttask) {
				String missionid = task.getMissionid();
				String addrt = task.getAddr();
				//判断是否是本台采集机器
				if(addr.equals(addrt)){
					
					LoginUser info=authorityService.getInfoByMissionid(missionid);
					String appid_r=info.getAppid();
					String appkey_r = info.getAppkey();
					//如果为true，说明已经有一个同类型的属地化任务正在采集
					if(appid.equals(appid_r)&&appkey.equals(appkey_r)){
						//判断是否启动了，如果没有启动机器人，那么启动该机器人
						if(task.getStatus().equals("4")){
							//遍历本地静态变量中的机器任务
							for (int i = 0; i < InitSystemServlet.grabTaskList.size(); i++) {
								GrabTask grabTasks = InitSystemServlet.grabTaskList.get(i);
								String grabtaskid = InitSystemServlet.grabTaskList.get(i).getTaskid()+"";
								//如果相同，认为是同一个任务
								if(grabtaskid.trim().equals(missionid)){
									robotid = task.getTaskid();
									robot_start_flag = false;
									ConfigInfo.NUM--;
									//启动
									grabTasks.getSinasearchRobot().startrobot();
									missionService.updateTaskForQBymissionid(missionid);
								}
							}
						}else if(task.getStatus().equals("2")){
							robotid = task.getTaskid();
							robot_start_flag = false;
						}
						ExpireRobot expireRobot = new ExpireRobot();
						expireRobot.updateTimeByMissionid(dbinfo.getEndtime(),missionid);
					}
				}
			}
			
			//创建机器人并启动，将机器人信息存入静态变量中
			if(robot_start_flag){
				//创建机器人
				robotManage.createRobot();
				robotid = robotManage.saveRobot(dbinfo,dbinfo.getSearchmode(),"1");
				Collecttask robotTask = robotManage.getCollecttaskById(robotid);
				sinalocalsearchRobot robot = new sinalocalsearchRobot(robotTask);
				robot.startrobot();
				grabTask.setSinasearchRobot(robot);
				grabTask.setAppid(userinfo.getAppid());
				grabTask.setAppkey(userinfo.getAppkey());
				InitSystemServlet.grabTaskList.add(grabTask);
			}
			
			//创建新任务的回溯任务,只是暂时存放在这个字段中，并无实际含义
			BackTask backTask = new BackTask();
			dbinfo.setRemark(startTime);
			SinaLocalBackRobot backRobot = new SinaLocalBackRobot(dbinfo);
			backRobot.startrobot();
			backTask.setTaskid(dbinfo.getMissionid());
			backTask.setSinasearchRobot(backRobot);
			backTask.setAppid(userinfo.getAppid());
			backTask.setAppkey(userinfo.getAppkey());
			backTask.setStatus("2");
			InitSystemServlet.backTaskList.add(backTask);
		}
		
		//保存新任务与机器人的关系+
		if(robotid!=""){
			missionService.saveMissionRobot(dbinfo.getMissionid(),robotid,remark);
			
		}
		
		//抓取
//		missiontrans.missiontrans(dbinfo);

	}

	private static void receiveFile(Message message) {
		try {
			System.out.println(message.getJMSDestination().toString());
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
		String sendType = null;
		String jsonData = null;
//		String website = null;
		if (message instanceof BlobMessage) {
			BlobMessage blobMessage = (BlobMessage) message;
			try {
				logger.debug("有文件接收成功5");
				String path =ConfigInfo.fakezipget+"zip.zip";
				DirectoryUtil.createFile(path);
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("请指定文件保存位置");
				fileChooser.setSelectedFile(new File(path));
				File file = fileChooser.getSelectedFile();
				OutputStream os = new FileOutputStream(file);
				InputStream inputStream = blobMessage.getInputStream();
				// 写文件，你也可以使用其他方式
				byte[] buff = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buff)) > 0) {
					os.write(buff, 0, len);
				}
				os.close();
				
				sendType = blobMessage.getStringProperty("sendType");
				jsonData = blobMessage.getStringProperty("jsonData");
//				website = blobMessage.getStringProperty("website");
				System.out.println(sendType);
				System.out.println(jsonData);
//				if(jsonData!=null&&website!=null){
//					SavePicMessage.saveMessage(jsonData,website);
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
