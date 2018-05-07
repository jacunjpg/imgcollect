package monitor.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
	public static void returnStr(HttpServletResponse response,String returnStr){
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(returnStr);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void returnJsonStr(HttpServletResponse response,String returnStr){
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(returnStr);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void CallBack(HttpServletResponse response,String value){
		response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out;
		try {
			out = response.getWriter();
			out.flush();
	        out.println("<script>");
	        out.println("alert('"+value+"');");
//	        out.println("history.back();");
	        out.println("</script>");
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
