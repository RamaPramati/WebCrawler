package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.crawling.MailParser;
import com.pramati.crawling.Storing;
public class Crawler 
{
	private MailParser checkCondition;
	private static URL url;
	private String year;
	Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Crawler(String url, String year, MailParser condition) throws FailingHttpStatusCodeException, MalformedURLException {
		LOGGER.info("I am in Crawler and received url and conditions as are"+url+"and"+condition);
		this.year = year;
		Crawler.url = new URL(url);
		checkCondition = condition;
	}

	public void crawlURL() throws MalformedURLException, IOException {
		LOGGER.info("I am on crawURL");
		ArrayList<String> storeURLs = checkCondition.getMailURLs(url, year);
		Iterator<?> ir=storeURLs.iterator();
		ExecutorService executor = Executors.newFixedThreadPool(3);
		while(ir.hasNext()) {
			String temp = ir.next().toString();
			LOGGER.info("I am on running thread for urlpage reading"+temp);
			URL storeURL = new URL(temp);
			Storing store = new FileSystem(storeURL);
			executor.execute((Runnable) store);
		}
		executor.shutdown();  
		while (!executor.isTerminated()) {   }  
	}

}









