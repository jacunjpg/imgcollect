package monitor.pictureutil;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import monitor.util.JsonHelper;
import monitor.webview.entity.dbinfo;

public class apitest implements Runnable{
	private static Logger logger = Logger.getLogger(apitest.class);
	private dbinfo dbinfo;
	private File file;
	private int fwqStyle;
	
	public apitest(dbinfo dbinfo, File file,int fwqStyle)
    {
        this.dbinfo = dbinfo;
        this.file = file;
        this.fwqStyle = fwqStyle;
    }
	public void checkpictures(dbinfo dbinfo, File file,int fwqStyle) {
		// TODO Auto-generated method stub
		System.out.println(file.getPath());

			String result1=Pictureobj.qtpicupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "c1");
			String result=Pictureobj.qtpicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "c1");
			logger.info("the"+ConfigInfo.count+"times success");
			logger.info("the result1 ="+result1);
			logger.info("the result2 ="+result);
			ConfigInfo.count++;
	}
	
	 public void run()
	    {
		 checkpictures(dbinfo,file,fwqStyle);
	    }
}
