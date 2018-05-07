package monitor.util;

import java.io.File;
import java.io.IOException;

import monitor.pictureutil.DirectoryUtil;
import monitor.pictureutil.ZipTest;

public class CreateZipFile {

	
	public static String createZip(String zipName){
		
		String downloadPath =  ContentUtils.MQ_FILE_PATH+zipName;
		File path = new File(downloadPath);
		if (!path.exists())
			path.mkdirs();
		
		File zip1 = new File(ContentUtils.MQ_FILE_PATH, zipName + ".zip");
		File files = new File(downloadPath);
		try {
			ZipTest.ZipFiles(zip1, "", files);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DirectoryUtil.deleteDir(new File(downloadPath));
		DirectoryUtil.doDeleteEmptyDir(downloadPath);
		
		return downloadPath+".zip";
	}
}
