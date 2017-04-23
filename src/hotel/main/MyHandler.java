package hotel.main;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler{
	
	Map <String, String>dbConfig;
	
	boolean db;
	boolean ip;
	boolean port;
	boolean sid;
	boolean user;
	boolean password;
	
	public MyHandler() {
		
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("db")) {
			dbConfig=new HashMap<String, String>();
			db=true;
		}else if (qName.equalsIgnoreCase("ip")) {
			ip=true;
		}else if (qName.equalsIgnoreCase("port")) {
			port=true;
		}else if (qName.equalsIgnoreCase("sid")) {
			sid=true;
		}else if (qName.equalsIgnoreCase("user")) {
			user=true;
		}else if (qName.equalsIgnoreCase("password")) {
			password=true;
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (ip) {
			dbConfig.put("ip", new String(ch, start, length));			
			ip=false;
		}else if (port) {
			dbConfig.put("port", new String(ch, start, length));	
			port=false;
		}else if (sid) {
			dbConfig.put("sid", new String(ch, start, length));	
			sid=false;
		}else if (user) {
			dbConfig.put("user", new String(ch, start, length));	
			user=false;
		}else if (password) {
			dbConfig.put("password", new String(ch, start, length));	
			password=false;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (db) {
			db=false;
		}
	}
}
