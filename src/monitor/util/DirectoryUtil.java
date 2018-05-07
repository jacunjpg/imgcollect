package monitor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DirectoryUtil {

	/**
	 * 
	 * 
	 * Title void Description 创建文件以及文件夹
	 * 
	 * @author jacun
	 * @date 2017-4-27下午3:00:27
	 * @param src
	 */
	public static void createFile(String src) {

		// path表示你所创建文件的路径
		String path = src.substring(0, src.lastIndexOf("/"));
		// fileName表示你创建的文件名
		String fileName = src.substring(src.lastIndexOf("/") + 1, src.length());
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		File file = new File(f, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 文件拷贝
	 */
	public static void copy(String src, String des) {
		System.out.println(src + "----->" + des);
		File file1 = new File(src);
		File[] fs = file1.listFiles();
		File file2 = new File(des);
		if (!file2.exists()) {
			file2.mkdirs();
		}
		for (File f : fs) {
			if (f.isFile()) {
				fileCopy(f.getPath(), des + "\\" + f.getName()); // 调用文件拷贝的方法
			} else if (f.isDirectory()) {
				copy(f.getPath(), des + "\\" + f.getName());
			}
		}
	}

	/**
	 * 文件拷贝的方法
	 */
	private static void fileCopy(String src, String des) {

		BufferedReader br = null;
		PrintStream ps = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					src)));
			ps = new PrintStream(new FileOutputStream(des));
			String s = null;
			while ((s = br.readLine()) != null) {
				ps.println(s);
				ps.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (ps != null)
					ps.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除空目录
	 * 
	 * @param dir
	 *            将要删除的目录路径
	 */
	public static void doDeleteEmptyDir(String dir) {
		boolean success = (new File(dir)).delete();
		if (success) {
			System.out.println("Successfully deleted empty directory: " + dir);
		} else {
			System.out.println("Failed to delete empty directory: " + dir);
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public String plusHour(int num) {
		Date d = new Date();
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.HOUR, num);// num为增加的小时，可以改变的
		d = ca.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String enddate = format.format(d);
		System.out.println("结束时间：" + enddate);
		return enddate;
	}
	
	public String plusMinute(String starttime, int num) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date date = new Date();
		try {
			date = sdf.parse(starttime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, num);// num为增加的分钟数，可以改变的
		Date d  = ca.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String enddate = format.format(d);
		System.out.println("结束时间：" + enddate);
		return enddate;
	}
	
	public String plusMinuteAtStartTime(int num) {
		
		Date d = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.add(Calendar.MINUTE, num);// num为增加的分钟数，可以改变的
		Date time  = ca.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String enddate = format.format(time);
		System.out.println("结束时间：" + enddate);
		return enddate;
	}

	/**
	 * 测试
	 */
//	public static void main(String[] args) {
//		System.out.println(plusMinuteAtStartTime(-5));
//		doDeleteEmptyDir("new_dir1");
//		String newDir2 = "new_dir2";
//		boolean success = deleteDir(new File(newDir2));
//		if (success) {
//			System.out.println("Successfully deleted populated directory: "
//					+ newDir2);
//		} else {
//			System.out.println("Failed to delete populated directory: "
//					+ newDir2);
//		}
//	}

}
