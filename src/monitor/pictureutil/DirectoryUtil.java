package monitor.pictureutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.google.common.io.Files;

public class DirectoryUtil {

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
				try {
					Files.copy(f, new File(des+"\\"+f.getName()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				(f.getPath(), des + "\\" + f.getName()); // 调用文件拷贝的方法
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
	/**
	 * 测试
	 */
	// public static void main(String[] args) {
	// doDeleteEmptyDir("new_dir1");
	// String newDir2 = "new_dir2";
	// boolean success = deleteDir(new File(newDir2));
	// if (success) {
	// System.out.println("Successfully deleted populated directory: " +
	// newDir2);
	// } else {
	// System.out.println("Failed to delete populated directory: " + newDir2);
	// }
	// }
}
