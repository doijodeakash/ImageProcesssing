/* 
 * Copyright (C) 2020 10Xofy. All rights reserved.
 */
package xsemble.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class PageUtil {
	
	// TODO Change this url as needed. It is used by all Page tests
	public static String getTestSystemBaseUrl() {
		return "http://localhost:8080/imagepro/";
	}

    /** Gets xml from object */
	public static String getXml(Object obj) {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		XMLEncoder xe = new XMLEncoder(outstream);
		xe.writeObject(obj);
		xe.close();
		String xmlStr = new String(outstream.toByteArray(),StandardCharsets.UTF_8);
		return xmlStr;
	}
	
	/** Gets object from xml */
    public static Object getObject(String xmlStr) {
        XMLDecoder xd = null;
        try {
            xd = new XMLDecoder(new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8)));
            return xd.readObject();
        }
        finally {
            if (xd != null) xd.close();
        }
    }
}
