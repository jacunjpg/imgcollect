package monitor.common.servlet;

import java.util.Properties;

import monitor.util.MD5;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyPlaceholderConfigurerInfo extends PropertyPlaceholderConfigurer{
	  @Override
	  protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		      String password = props.getProperty("jdbc.password");
	          //解密jdbc.password属性值，并重新设置
	          //props.setProperty("jdbc.password",MD5.md5JieMi(password));
	          super.processProperties(beanFactory, props);
	     }
	}
