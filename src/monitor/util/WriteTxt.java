package monitor.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class WriteTxt {
	public static boolean writeTxtFile(String content){
		String path = createDir();
		String time = DateUtil.getCurrentDate()+".txt";
		String file = path+"/"+time;
		createFile(path+"/"+time);
		RandomAccessFile mm=null;  
		boolean flag=false;  
		FileOutputStream o=null;
		content += "\n"; 
		try {  
			o = new FileOutputStream(file,true);  
			o.write(content.getBytes("utf-8"));
			o.close();  
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();  
		}finally{ 
			try {
				if(mm!=null){  
					mm.close();
				}  
			} catch (IOException e) {
				e.printStackTrace();
			} 
	  }  
	  return flag;  
	}
	public static void createFile(String filePath){
		File f = new File(filePath);  
        if (!f.exists()) {  
        	try {
        		f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	public static String createDir(){
		System.out.println(WriteTxt.class.getResource("/").getFile());
		String path = WriteTxt.class.getResource("/").getFile();
		path +="mylog";
		File file =new File(path);
		if(!file.exists() && !file.isDirectory()){
		    file.mkdir();  
		}
//		//F:/myeclipseworkspace/network/WebRoot/WEB-INF/classes/mylog
		System.out.println(path);
		return path;
	}
	public static void main(String[] args){
		try {
			writeTxtFile("123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
