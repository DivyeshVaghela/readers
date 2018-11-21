package com.learning.readers.util;

import javax.servlet.ServletRequest;

public class ServerUtil {

	public static String getHostname(ServletRequest servletRequest){
		return servletRequest.getLocalName();
		//return InetAddress.getLocalHost().getHostName();
	}
	
	public static int getPortnumber(ServletRequest servletRequest) {
		return servletRequest.getLocalPort();
	}
}
