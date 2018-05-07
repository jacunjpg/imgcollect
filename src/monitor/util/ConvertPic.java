package monitor.util;

import java.io.File;

import java.util.List;
import monitor.util.PrintStream;
public class ConvertPic {

 public static boolean process(String oldfilepath,String newFilePath,String toolsPath) {    
 int type = checkContentType(oldfilepath);
    boolean status = false;
    if (type==0) {
      status = processJPG(oldfilepath,newFilePath,toolsPath);// ֱ�ӽ��ļ�תΪflv�ļ�
    } else if (type==1) {
      String avifilepath = processAVI(oldfilepath,toolsPath);
      if (avifilepath == null)
        return false;// avi�ļ�û�еõ�
      status = processJPG(oldfilepath,newFilePath,toolsPath);// ��aviתΪflv
    }
    return status;
  }
  public static int checkContentType(String oldfilepath) {
    String type = oldfilepath.substring(oldfilepath.lastIndexOf(".") + 1,
    		oldfilepath.length()).toLowerCase();
//ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�
    if (type.equals("avi")) {
      return 0;
    } else if (type.equals("mpg")) {
      return 0;
    } else if (type.equals("wmv")) {
      return 0;
    } else if (type.equals("3gp")) {
      return 0;
    } else if (type.equals("mov")) {
      return 0;
    } else if (type.equals("mp4")) {
      return 0;
    } else if (type.equals("asf")) {
      return 0;
    } else if (type.equals("asx")) {
      return 0;
    } else if (type.equals("flv")) {
      return 0;
    }
    //��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��), 
    //�������ñ�Ĺ��ߣ�mencoder��ת��Ϊavi(ffmpeg�ܽ�����)��ʽ.
    else if (type.equals("wmv9")) {
      return 1;
    } else if (type.equals("rm")) {
      return 1;
    } else if (type.equals("rmvb")) {
      return 1;
    }    
    return 9;
  }
  private static boolean checkfile(String path){
   File file=new File(path);
   if(!file.isFile()){
   return false;
   }
   return true;
  }
 //��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��), �������ñ�Ĺ��ߣ�mencoder��ת��Ϊavi(ffmpeg�ܽ�����)��ʽ.
  public static String processAVI(String oldfilepath,String toolsPath) {
    List<String> commend=new java.util.ArrayList<String>();
    String type = oldfilepath.substring(0,
    		oldfilepath.lastIndexOf(".")).toLowerCase();
    commend.add(toolsPath+"\\mencoder");
    commend.add(oldfilepath);
    commend.add("-oac");
    commend.add("lavc");
    commend.add("-lavcopts");
    commend.add("acodec=mp3:abitrate=64");
    commend.add("-ovc");
    commend.add("xvid");
    commend.add("-xvidencopts");
    commend.add("bitrate=600");
    commend.add("-of");
    commend.add("avi");
    commend.add("-o");
    commend.add(type+".avi");
    try{
     ProcessBuilder builder = new ProcessBuilder();
      builder.command(commend);
      builder.start();
      
      
      
      
      return "d:\\bk\\a.avi";
    }catch(Exception e){
     e.printStackTrace();
     return null;
    }
  }
 //ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�
 public  static boolean processJPG(String oldfilepath,String newFilePath,String toolsPath) {
   if(!checkfile(oldfilepath)){
     System.out.println(oldfilepath+" is not file");
     return false;
     }   
    List<String> commend=new java.util.ArrayList<String>();
    commend.add(toolsPath+"\\ffmpeg");
    commend.add("-i");
    commend.add(oldfilepath);
   
    
    commend.add("-y");        
    commend.add("-f");       
    commend.add("image2");   
    
    commend.add("-s"); // ��Ӳ�����-s�����ò���ָ����ȡ��ͼƬ��С       
    commend.add("256*256"); // ��ӽ�ȡ��ͼƬ��СΪ350*240     
    commend.add("-vf");  
    commend.add("fps=fps=1/10");  //ÿ30���һ��ͼ  
    commend.add(newFilePath+"\\%d.jpg");
    try {
//      ProcessBuilder builder = new ProcessBuilder();
//      builder.command(commend);
//      builder.start();
    	 // 方案1
    	
    	 Process videoProcess = new ProcessBuilder().command(commend).redirectErrorStream(true).start();
    	 new PrintStream(videoProcess.getInputStream()).start();
    	 videoProcess.waitFor();
    	 return true;           
    	 
    	  } catch (Exception e) {
    	         e.printStackTrace();
    	          return false;
    	   }  
    }
}