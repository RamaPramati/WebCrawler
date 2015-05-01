package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.crawling.MailParser;
import com.pramati.crawling.Storing;
public class Crawler 
{
	private MailParser checkCondition;
	private static URL url;
	private String year;
	
	static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

	public static void main(String args[]) throws MalformedURLException, IOException {
		
		FileHandler fileTxt = new FileHandler("log/webCrawler.log");
		SimpleFormatter formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		LOGGER.addHandler(fileTxt);
		
		final MailParser condtion=new MailParserImpl();
		final Crawler crawler=new Crawler("http://mail-archives.apache.org/mod_mbox/maven-users/","2014",condtion);
		crawler.crawlURL();
		LOGGER.info("Crawling success");
	}
	
	public Crawler(String url, String year, MailParser condition) throws FailingHttpStatusCodeException, MalformedURLException {
		this.year = year;
		Crawler.url = new URL(url);
		checkCondition = condition;
	}

	public void crawlURL() throws MalformedURLException, IOException {
		ArrayList<String> storeURLs = checkCondition.getMailURLs(url, year);
		Iterator<?> ir=storeURLs.iterator();
		ExecutorService executor = Executors.newFixedThreadPool(8);
		while(ir.hasNext()) {
			String temp = ir.next().toString();
			URL storeURL = new URL(temp);
			Storing store = new FileSystem(storeURL);
			executor.execute((Runnable) store);
		}
		executor.shutdown();  
		while (!executor.isTerminated()) {   }  	
	}

}









