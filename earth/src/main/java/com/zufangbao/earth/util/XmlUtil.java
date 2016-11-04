package com.zufangbao.earth.util;

import com.thoughtworks.xstream.XStream;

public class XmlUtil {
	
	public static final String XML_Head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	public static XStream xStream(){
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.ignoreUnknownElements();
		return xStream;
	}
}
