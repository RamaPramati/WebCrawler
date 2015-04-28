package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import com.pramati.crawling.MailParser;

public class WebCrawler {
	static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String args[]) throws MalformedURLException, IOException {
		MyLogger.setup();

		LOGGER.info("I am in WebCrawler and storing mails on url and year"+"http://mail-archives.apache.org/mod_mbox/maven-users/"+"2014");
		MailParser condtion=new MailParserImpl();
		Crawler crawler=new Crawler("http://mail-archives.apache.org/mod_mbox/maven-users/","2014",condtion);
		crawler.crawlURL();
	}
}
