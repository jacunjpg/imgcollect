package monitor.pictureutil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
public class BaiduPicScript {
	
  public static void main(String args[]){
	  try {
		BaiduPicScript.getUrlTrue("ippr_z2C$qAzdH3FAzdH3Fjgp_z&e3Bvg6_z&e3BvgAzdH3FstfpAzdH3Fda8candnAzdH3FWada8candnn98c9lndadl9_z&e3B3r2");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
	public static String getUrlTrue(String url) throws ScriptException, NoSuchMethodException, IOException{
//		HttpServletRequest request = CommonUtil.getRequest();
//		String path_local =request.getSession().getServletContext().getRealPath(""); 
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("javascript");
		//String jsFileName ="F:\\tomcat-6.0.26-myeclipse\\webapps\\pic\\pages\\js\\pic.js";
		String jsFileName =ConfigInfo.jsFileName;// 读取js文件  
		File a=new File(jsFileName);
		System.out.println(a.getAbsolutePath());
		Object result;
		FileReader reader = new FileReader(jsFileName); 
		// 执行指定脚本   
		engine.eval(reader); 
		if(engine instanceof Invocable) {    
			Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数    
			// c = merge(2, 3);    
			result = ((Invocable)engine).invokeFunction("hex_md5",url);
			System.out.println("result = " + result);   
			}else{
				result = new String("");
			}
			reader.close();  
		return (String)result;
	}
}
