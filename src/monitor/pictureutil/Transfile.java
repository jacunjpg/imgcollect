package monitor.pictureutil;

import java.io.File;
import java.io.IOException;

import monitor.util.DateUtil;

import org.springframework.web.multipart.MultipartFile;

public class Transfile {
	
	/**将multipartFile转为文件数组
	 * 
	 * @param file	multipartfile
	 * @param path	存储路径
	 * @return
	 */
	public static File[] transfile(MultipartFile file[],String path)
	{
		new File(path).mkdirs();
		File[]	refile =new File[file.length];
		for(int i=0;i<file.length;i++)
		{
			File targetfile=new File(path,file[i].getOriginalFilename());
			try {
				file[i].transferTo(targetfile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			refile[i]=targetfile;
		}
		return refile;
	}
	/**
	 *  存储excel表格
	 * @param file
	 * @param path
	 * @return
	 */
	public static File[] transexcel(MultipartFile excel[],String pathfile)
	{
		File[]	refile =new File[excel.length];
		for(int i=0;i<excel.length;i++)
		{
			new File(pathfile).mkdirs();
			File targetfile=new File(pathfile);
			try {
				excel[i].transferTo(targetfile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			refile[i]=targetfile;
		}
		return refile;
	}
	/**
	 * 为图片换名 换名方式为 cbkid(a##,b##)_系统时间戳_自增数字。后缀
	 * @param files
	 * @return
	 */
	public static File[] changename(File[] files,String cbkid){
		File[] returnfile=new File[files.length];
		for(int i=0;i<files.length;i++)
		{
			int t=files[i].getName().lastIndexOf(".");
			String name=files[i].getName().substring(t);
			
			returnfile[i]=new File(files[i].getParent(),cbkid+"_"+DateUtil.getCurrentTimeMillis()+"_"+(i+1)+name);
			files[i].renameTo(returnfile[i]);
		}
		return returnfile;
	}
	/**
	 * 普通换名方式 数字+后缀
	 * @param files
	 * @return
	 */
	public static File[] changename(File[] files){
		File[] returnfile=new File[files.length];
		for(int i=0;i<files.length;i++)
		{
			int t=files[i].getName().lastIndexOf(".");
			String name=files[i].getName().substring(t);
			File targetfile=new File(files[i].getParent()+"/"+DateUtil.getCurrentTimeMillis()+"_"+(i+1)+name);
			files[i].renameTo(targetfile);
			returnfile[i]=targetfile;
			//System.out.println(files[i].getParent());
		}
		return returnfile;
	}
}
