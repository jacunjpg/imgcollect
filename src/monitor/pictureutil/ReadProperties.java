package monitor.pictureutil;

import java.util.ResourceBundle;

public class ReadProperties {
//	public static void main(String[] args) {
//		getValueByKey("systemip");
//	}

	public String getValueByKey(String keyName){
		ResourceBundle bundle = ResourceBundle.getBundle("config.properties.sysInfo");
		String value = bundle.getString(keyName);
		return value;
	}
}
