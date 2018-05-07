package monitor.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import monitor.util.ImgCompress;
import monitor.util.ConvertPic;

public class ReadFile {
        public ReadFile() {
        }
        /**
         * 读取某个文件夹下的所有文件
         */
        public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
                try {

                        File file = new File(filepath);
                        if (!file.isDirectory()) {
                                System.out.println("文件");
                                System.out.println("path=" + file.getPath());
                                System.out.println("absolutepath=" + file.getAbsolutePath());
                                System.out.println("name=" + file.getName());

                        } else if (file.isDirectory()) {
                                System.out.println("文件夹");
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                        File readfile = new File(filepath + "\\" + filelist[i]);
                                        if (!readfile.isDirectory()) {
                                                System.out.println("path=" + readfile.getPath());
                                                System.out.println("absolutepath="
                                                                + readfile.getAbsolutePath());
                                                System.out.println("name=" + readfile.getName());

                                        } else if (readfile.isDirectory()) {
                                                readfile(filepath + "\\" + filelist[i]);
                                        }
                                }

                        }

                } catch (FileNotFoundException e) {
                        System.out.println("readfile()   Exception:" + e.getMessage());
                }
                return true;
        }

        public static List<String> readfileVideo(String filepath,String toolsPath) throws FileNotFoundException, IOException {
            File file = new File(filepath);
           
            List<String> picList = new ArrayList<String>();
            
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                    	 boolean flag = false;
                         String fileNew = readfile.getName().substring(0, readfile.getName().indexOf("."));
                         String newFilePath = filepath+"\\"+fileNew;
                         File fileCreate = new File(newFilePath);  
                         if(!fileCreate.exists()){
                        	 makeDir(fileCreate);  
                        	  flag =ConvertPic.process(readfile.getPath(),newFilePath,toolsPath);
                        	 
                         }
                        	 picList.add(fileNew);
                        	 if(flag){
                        		 continue;
                        	 }

                    } 
            }

            return picList;
        }
        
        
        public static List<String> readfilePic(String filepath) throws FileNotFoundException, IOException {
            File file = new File(filepath);
            List<String> picList = new ArrayList<String>();
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                    	ImgCompress imgCom = new ImgCompress(filepath + "\\" + filelist[i]);  
                        imgCom.resizeFix(400, 400,filepath + "\\" + filelist[i]);  
            			picList.add(readfile.getName());
                    } 
            }
			
            return picList;
        }
       
        public static void makeDir(File dir) {  
            if(! dir.getParentFile().exists()) {  
                makeDir(dir.getParentFile());  
            }  
            dir.mkdir();  
        } 
      
        
        public static void main(String[] args) {
                try {
                	readfilePic("D:\2\51\01.jpg");
                        // deletefile("D:/file");
                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }
                System.out.println("ok");
        }

}