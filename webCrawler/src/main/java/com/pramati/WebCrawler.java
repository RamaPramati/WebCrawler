package com.pramati;

import java.io.IOException;
import java.net.MalformedURLException;

public class WebCrawler {
	
	public static void main(String args[]) throws MalformedURLException, IOException {
	    Crawler crawler=new Crawler("http://mail-archives.apache.org/mod_mbox/maven-users/","2014");
	    crawler.getMails();
	}
}
