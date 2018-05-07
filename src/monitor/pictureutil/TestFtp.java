package monitor.pictureutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.ftp.FtpClient;

public class TestFtp {

	public static void main(String[] args) {
		File a=new TestFtp().downFile("192.168.20.47", // FTP服务器hostname
				21,// FTP服务器端口
				"admin", // FTP登录账号
				"123456", // FTP登录密码
				"webapps/pictwoview/pages/savedpictures/mission/xinlangweibo/15",// FTP服务器上的相对路径
				"15_20170711085655562_653.jpg",// 要下载的文件名
				"c:/test/"// 下载后保存到本地的路径

		);
//		try {
//			new FileInputStream(a.getPath());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
////		}
//		System.out.println(a.getPath());
//		
//		uploadFile(a,
//				"admin",
//				"123456",
//				"192.168.20.47",
//				21,
//				a.getName(),
//				"webapps/pictwoview");
//		deleteFtpFile("192.168.20.47",
//				21,
//				"admin",
//				"123456",
//				"webapps/pictwoview/test",
//				"");
		
		// deleteFtpFile("192.168.20.113", 21, "CHWX", "123",
		// "D:/download/jsondownload", "1490798716159_image_1432.json");
		// downAndDEleteFiles("192.168.20.113", // FTP服务器hostname
		// 21,// FTP服务器端口
		// "CHWX", // FTP登录账号
		// "123", // FTP登录密码
		// "d:/download/jsondownload/",// FTP服务器上的相对路径
		// "1490798716131_image_2490.json",// 要下载的文件名
		// "c:/test/"// 下载后保存到本地的路径
		//
		// );
	}
	
    public boolean uploadFile(
            File file, //要写入的内容
            String username, //ftp登录用户名
            String password, //ftp登录密码
            String hostname, //主机名
            int	   port,     //端口号
            String filename, //创建的文件名
            String path      //指定写入目录
            ){
            boolean flag = false;
            FTPClient ftpClient = new FTPClient();
    
            try{
//                InputStream is = null;
                int reply;
                //1.输入流
                //2.连接服务器,采用默认端口21

                ftpClient.connect(hostname,port);
                //3.登录ftp
                ftpClient.login(username , password);
                reply = ftpClient.getReplyCode();
                if(!FTPReply.isPositiveCompletion(reply)){
                	ftpClient.disconnect();
                    return flag;
                }
                //4.指定写入的目录
                ftpClient.changeWorkingDirectory(path);
                String[] dirs = path.split("/");
    			for (String dir : dirs) {
    				// 2.创建并进入不存在的目录
    				if (!ftpClient.changeWorkingDirectory(dir)) {
    					ftpClient.mkd(dir);
    					ftpClient.changeWorkingDirectory(dir);
    					// System.out.println("进入目录成功:" + dir);
    				}
    			}
                //5.写操作
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//                System.out.println(new FileInputStream(file));
                ftpClient.storeFile(file.getName(),new FileInputStream(file));
//                is.close();
                //退出
                ftpClient.logout();
                flag = true;
            }catch(Exception e)
            {
                e.printStackTrace();
            }finally{
                if(ftpClient.isConnected()){
                    try{
                        ftpClient.disconnect();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            return flag;
        }

    

	public boolean deleteFtpFile(String url, int port, String username,
			String password, String remotePath, String fileName) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;

			// 连接FTP服务器
			if (port > -1) {
				ftp.connect(url, port);
			} else {
				ftp.connect(url);
			}

			// 登录
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}

			// 转移到FTP服务器目录
			ftp.changeWorkingDirectory(remotePath);
			System.out.println(ftp.listFiles().length);
//			 File f=new File(remotePath + "/" + fileName);
//			 if(f.exists()){
//			 f.delete();
//			 }
			 if(!fileName.equals(""))
			 {
			 success = ftp.deleteFile("/"+remotePath + "/" + fileName);
			 }
			 else
			 {
			 FTPFile[] files = ftp.listFiles();
			 for(int k=0;k<files.length;k++)
			 {
				 ftp.deleteFile("/"+remotePath + "/" + files[k].getName());
			 }
		     ftp.removeDirectory("/"+remotePath + "/");
			 }
			 ftp.logout();
			System.out.println("删除成功！");
		} catch (IOException e) {
			// logger.error(EXCEPTION_NAME, e);
			success = false;
			System.out.println("删除失败！");
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					// logger.error(EXCEPTION_NAME, e);
				}
			}
		}
		return success;
	}
	
	public ByteArrayOutputStream downFile2(String url, // FTP服务器hostname
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String remotePath,// FTP服务器上的相对路径
			String fileName,// 要下载的文件名
			String localPath// 下载后保存到本地的路径
	) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		ByteArrayOutputStream bis=new ByteArrayOutputStream();
		try {
			
			List<String> filelist = new ArrayList<String>();
			int num = 0;
			int reply;
			ftp.connect(url, port);
			
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return null;
			}
			// System.out.println("aaa");
			// String ftpPath = new String(remotePath.getBytes("GBK"),
			// "iso-8859-1"); // 关键是这行代码
//			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			ftp.changeWorkingDirectory(remotePath);
//			String[] dirs = remotePath.split("/");
//			for (String dir : dirs) {
//				// 2.创建并进入不存在的目录
//				if (!ftp.changeWorkingDirectory(dir)) {
//					ftp.mkd(dir);
//					ftp.changeWorkingDirectory(dir);
//					// System.out.println("进入目录成功:" + dir);
//				}
//			}
			// System.out.println(ftp.getLocalAddress());
			FTPFile[] fs = ftp.listFiles();
//			System.out.println(fs.length);
			for (FTPFile ff : fs) {
				// System.out.println("bb" + fs);
//				num++;
//				System.out.println(ff.getName());
//				if (num <= 7) {
					 if (ff.getName().equals(fileName)) {
					// System.out.println("dd");
//					File localFile = new File(localPath + "/" + ff.getName());
					
//					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), bis);
					
					// System.out.println("ccc" + ff.getName() + fileName);
//					is.close();
					// }
//					filelist.add(ff.getName());
//					
//					File f = new File(remotePath + ff.getName());
//					if (f.exists()) {
//						f.delete();
//					}
				}
			}
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return bis;
	}

	/**
	 * 
	 * author:liyunlong
	 * date  :2017-7-11
	 * @param url  FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 */
	public File downFile(String url, // FTP服务器hostname
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String remotePath,// FTP服务器上的相对路径
			String fileName,// 要下载的文件名
			String localPath// 下载后保存到本地的路径
	) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
	       
			List<String> filelist = new ArrayList<String>();
			int num = 0;
			int reply;
			ftp.connect(url, port);
			
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return null;
			}
			// System.out.println("aaa");
			// String ftpPath = new String(remotePath.getBytes("GBK"),
			// "iso-8859-1"); // 关键是这行代码
//			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			ftp.changeWorkingDirectory(remotePath);
//			String[] dirs = remotePath.split("/");
//			for (String dir : dirs) {
//				// 2.创建并进入不存在的目录
//				if (!ftp.changeWorkingDirectory(dir)) {
//					ftp.mkd(dir);
//					ftp.changeWorkingDirectory(dir);
//					// System.out.println("进入目录成功:" + dir);
//				}
//			}
			// System.out.println(ftp.getLocalAddress());
			FTPFile[] fs = ftp.listFiles();
//			System.out.println(fs.length);
			for (FTPFile ff : fs) {
				// System.out.println("bb" + fs);
//				num++;
//				System.out.println(ff.getName());
//				if (num <= 7) {
					 if (ff.getName().equals(fileName)) {
					// System.out.println("dd");
					File localFile = new File(localPath + "/" + ff.getName());
					
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					
					// System.out.println("ccc" + ff.getName() + fileName);
					is.close();
					// }
					filelist.add(ff.getName());
					
					File f = new File(remotePath + ff.getName());
					if (f.exists()) {
						f.delete();
					}
				}
			}
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return new File(localPath + "/" + fileName);
	}

	public List<String> downAndDEleteFiles(String url, // FTP服务器hostname
			int port,// FTP服务器端口
			String username, // FTP登录账号
			String password, // FTP登录密码
			String remotePath,// FTP服务器上的相对路径
			String fileName,// 要下载的文件名
			String localPath// 下载后保存到本地的路径
	) {
		int num = 0;
		List<String> filelist = new ArrayList<String>();
		// boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return filelist = new ArrayList<String>();
			}
			// System.out.println("aaa");
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			// System.out.println(ftp.getLocalAddress());
			FTPFile[] fs = ftp.listFiles();

			for (FTPFile ff : fs) {
				// System.out.println("bb" + fs);
				num++;
				if (num <= 7) {
					// if (ff.getName().equals(fileName)) {
					// System.out.println("dd");
					File localFile = new File(remotePath + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					// System.out.println("ccc" + ff.getName() + fileName);
					is.close();
					// }
					filelist.add(ff.getName());
					ftp.deleteFile(remotePath + ff.getName());
					// File f = new File(remotePath + ff.getName());
					// if (f.exists()) {
					// f.delete();
					// }
				}
			}
			ftp.logout();
			// 删除服务器 json文件
			try {
				// String str = "smb://root:runwxb@192.168.10.111"//
				// smb://CHWX:123@192.168.20.21
				// + "/data/imgdownload/json/" + ff.getName();

				// SmbFile file = new SmbFile(str);
				// if (file.exists()) {
				// file.delete();
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
			// success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return filelist;
	}
}
