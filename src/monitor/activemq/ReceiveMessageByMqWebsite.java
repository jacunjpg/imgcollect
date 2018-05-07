package monitor.activemq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
import monitor.util.DateUtil;
import monitor.util.DirectoryUtil;
import monitor.util.JsonHelper;
import monitor.webview.entity.dbinfo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.BlobMessage;

/**
 * 
 *  接收南京网站发送的图片和图片信息
 * Title ReceiveMessageByMqWebsite
 * Description
 * @author jacun
 * @date 2017-5-2上午10:53:04
 */
public class ReceiveMessageByMqWebsite {

	public static void main(String[] args) {
		String url = "";
		// String url = "D:/mqfile/84.zip";
		// File file = new File(url);// 发送的文件
		String receiveType = "1";// 接收的类型 1发布一对一p ；2订阅一对多
		String isNotFile = "";
		String ip = "192.168.20.108";// 接收ip
		String modeName = "websiteimage";// 模式名称
		try {
			receive(receiveType, ip, isNotFile, modeName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void stopReceivewebsite() throws JMSException{
		System.out.println("websiteimage开始停止方法");
		System.out.println(ConfigInfo.receiveSocket.get("connection"));
		Connection connection = (Connection) ConfigInfo.receiveSocket.get("connection");
		connection.close();
		System.out.println("websiteimage接收停止完成");
	}
	
	public static void receivewebsite()
	{
		System.out.println("websiteimage启动");
		String receiveType = "1";// 接收的类型 1发布一对一;2订阅一对多
		String isNotFile = "true";
		//读取配置文件
		ReadProperties readProperties = new ReadProperties();
		String ip = readProperties.getValueByKey("mqip");// 接收ip
//		String ip = ConfigInfo.mqip;// 接收ip
//		String ip = "192.168.20.108";// 接收ip
//		String ip = ConfigInfo.mqip;// 接收ip
//		String ip = "192.168.20.110";// 接收ip
		String modeName = "websiteimage";// 模式名称
		try {
			System.out.println("爬虫图片开始接收"+modeName);
			ReceiveMessageByMqWebsite.receive(receiveType, ip, isNotFile, modeName);
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
		System.out.println("websiteimage开始接收2");
		// 获取 ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://" + ip + ":61616");
		// 创建 Connection
		Connection connection = connectionFactory.createConnection();
		connection.start();
		ConfigInfo.receiveSocket.put("connection", connection);
		System.out.println("connectionmap======"+ConfigInfo.receiveSocket.get("connection"));
		// 创建 Session
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		// 创建 Destinatione
		// 判断是一对一还是一对多
		if ("2".equals(receiveType)) {
			// 一对多
			System.out.println("一对多接收数据3");
			receiveTopic(session, isNotFile, modeName);
		} else {
			// 一对一
			System.out.println("一对一接收数据3");
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
						System.out.println("有文件接收数据4");
						ReceiveMessageByMqWebsite.receiveFile(message);
					} else {
						System.out.println("无文件接收数据4");
						ReceiveMessageByMqWebsite.receiveData(message);

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
			// 创建 Consumer
			MessageConsumer consumer = session.createConsumer(destination);
			// 注册消息监听器，当消息到达时被触发并处理消息
			consumer.setMessageListener(new MessageListener() {
				// 监听器中处理消息

				public void onMessage(Message message) {
					if ("true".equals(isFile)) {
						System.out.println("有文件接收数据4");
						ReceiveMessageByMqWebsite.receiveFile(message);
					} else {
						System.out.println("无文件接收数据4");
						ReceiveMessageByMqWebsite.receiveData(message);
					}
				}

			});
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	protected static void receiveData(Message message) {
		String sendType = null;
		String jsonData = null;
		String website = null;
		try {
			TextMessage msg = (TextMessage) message;
			sendType = msg.getStringProperty("sendType");
			jsonData = msg.getStringProperty("jsonData");
			website = msg.getStringProperty("website");
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println("无文件接收成功5");
		System.out.println(sendType);
		try {
			stopReceivewebsite();
		} catch (JMSException e) {
			e.printStackTrace();
		}
//		if(website!=null){
//			SavePicMessage.saveMessage(jsonData,website);
//		}
		dbinfo dbinfo = new dbinfo();
		dbinfo=(dbinfo) JsonHelper.JSONToObj(jsonData, dbinfo.class);
		faketrans missiontrans =new faketrans();
		missiontrans.missiontrans(dbinfo);
	}

	private static void receiveFile(Message message) {
		System.out.println("有文件接收成功5");
		try {
			System.out.println(message.getJMSDestination().toString());
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sendType = null;
		String imageData = null;
		String website = null;
		String infoData = null;
		if (message instanceof BlobMessage) {
			BlobMessage blobMessage = (BlobMessage) message;
			try {
				System.out.println("有文件接收成功5");
				String path =ConfigInfo.fakezipget+DateUtil.getCurrentTimeMillis()+".zip";
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
				imageData = blobMessage.getStringProperty("imageData");
				infoData = blobMessage.getStringProperty("infoData");
				website = blobMessage.getStringProperty("website");
				int missionid = -1;
				if(imageData!=null){
					//检测南京网站发送的图片信息
					dbinfo dbinfo = new dbinfo();
					dbinfo=(dbinfo) JsonHelper.JSONToObj(infoData, dbinfo.class);
					missionid = dbinfo.getMissionid();
					System.out.println("开始发送数据------------------------------");
//					SendMessageByMq.sendpicture(dbinfo,new File(path));
					System.out.println("结束发送数据------------------------------");
				}
				//保存南京网站发送的图片信息
				if(imageData!=null&&website!=null&&missionid!=-1){
					SavePicMessage.saveWebsiteMessage(imageData,website,missionid);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
