package com.zufangbao.sun.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * @author zjm
 *
 */
public class XMLFormatUtil {

	public static String formatXML(String inputXML) {

		String requestXML = null;
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(inputXML));
			XMLWriter writer = null;
			if (document != null) {
				try {
					StringWriter stringWriter = new StringWriter();
					OutputFormat format = new OutputFormat("        ", true);
					writer = new XMLWriter(stringWriter, format);
					writer.write(document);
					writer.flush();
					requestXML = stringWriter.getBuffer().toString();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestXML;
	}

}
