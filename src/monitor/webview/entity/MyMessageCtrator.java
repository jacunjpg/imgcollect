package monitor.webview.entity;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;
public class MyMessageCtrator implements MessageCreator,Serializable{
	
	private static final long serialVersionUID = 1L;
	private String json;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public MyMessageCtrator(String json){
		this.json = json;
	}
	@Override
	public Message createMessage(Session session) throws JMSException {
		// TODO Auto-generated method stub
		return session.createTextMessage(this.json);
	}
	
}
