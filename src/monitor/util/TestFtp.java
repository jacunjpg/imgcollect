package monitor.util;

import java.io.File;
import java.io.FileInputStream;

import monitor.pictureutil.ReadProperties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class TestFtp {
	private FTPClient ftp;
	private String path;
	private String addr;
	private int port;
	private String userName;
	private String pwd;

	public TestFtp(String missionid) {
		ReadProperties readProperties = new ReadProperties();
		String ftppath = readProperties.getValueByKey("ftppath")+"/"+missionid;
		String ftpip = readProperties.getValueByKey("ftpip");
		String ftpport = readProperties.getValueByKey("ftpport");
		String ftpuser = readProperties.getValueByKey("ftpuser");
		String ftppassword = readProperties.getValueByKey("ftppassword");
		this.path = ftppath;
		this.addr = ftpip;
		this.port = Integer.parseInt(ftpport);
		this.userName = ftpuser;
		this.pwd = ftppassword;
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param path
	 *            上传到ftp服务器具体的路径下
	 * @param addr
	 *            地址
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public boolean connect() throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		ftp.login(userName, pwd);
		ftp.setBufferSize(1024 * 1024 * 2);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		ftp.changeWorkingDirectory(path);
		result = true;
		return result;
	}

	/**
	 * 
	 * @param file
	 *            上传的文件或文件夹
	 * @throws Exception
	 */
	public void upload(File file) throws Exception {
		ftp.makeDirectory(path);
		ftp.changeWorkingDirectory(path);
		if (file.isDirectory()) {
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(file.getPath() + "\\" + files[i]);
				if (file1.isDirectory()) {
					upload(file1);
					ftp.changeToParentDirectory();
				} else {
					File file2 = new File(file.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					ftp.storeFile(file2.getName(), input);
					input.close();
				}
			}
		} else {
			
			File file2 = new File(file.getPath());
			FileInputStream input = new FileInputStream(file2);
			ftp.storeFile(file2.getName(), input);
			input.close();
		}
		ftp.disconnect();
	}
	
	public static void main(String[] args) throws Exception {
		TestFtp tf = new TestFtp("61");
        File file = new File("D:/img_bak");
        tf.upload(file);
    }

}
