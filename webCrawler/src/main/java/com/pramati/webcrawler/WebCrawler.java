package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.crawling.MailParser;

public class WebCrawler {
	static Logger LOGGER = LoggerFactory.getLogger(WebCrawler.class);

	public static void main(String args[]) throws MalformedURLException, IOException {
		LOGGER.info("I am in WebCrawler and storing mails on url and year {}{}", "http://mail-archives.apache.org/mod_mbox/maven-users/","2014");
		MailParser condtion=new MailParserImpl();
		Crawler crawler=new Crawler("http://mail-archives.apache.org/mod_mbox/maven-users/","2014",condtion);
		crawler.crawlURL();
	}
}
