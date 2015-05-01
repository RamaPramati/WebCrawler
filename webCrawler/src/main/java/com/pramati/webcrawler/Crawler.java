package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.crawling.MailParser;
import com.pramati.crawling.Storing;
public class Crawler 
{
	private MailParser checkCondition;
	private static URL url;
	private final String year;
	
	private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

	public static void main(String args[]){
		try{
			try {
				FileHandler fileTxt;
				fileTxt = new FileHandler("log/webCrawler.log");
				SimpleFormatter formatterTxt = new SimpleFormatter();
				fileTxt.setFormatter(formatterTxt);
				LOGGER.addHandler(fileTxt);
			} catch (SecurityException e) {
				if (LOGGER.isLoggable(Level.INFO)){
					LOGGER.severe(e.toString());
				}
			} 		
			try {
				MailParser condtion=new MailParserImpl();
				url = new URL("http://mail-archives.apache.org/mod_mbox/maven-users/"); 
				Crawler crawler = new Crawler(url,"2014",condtion);
				crawler.crawlURL();
			} catch (FailingHttpStatusCodeException e) {
				if (LOGGER.isLoggable(Level.INFO)){
					LOGGER.severe(e.toString());
				}
			} catch (MalformedURLException e) {
				if (LOGGER.isLoggable(Level.INFO)){
					LOGGER.severe(e.toString());
				}
			}
		}
		catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
		LOGGER.info("Crawling success");
	}
	
	public Crawler(URL url, String year, MailParser condition) throws FailingHttpStatusCodeException, MalformedURLException {
		this.year = year;
		checkCondition = condition;
	}

	public void crawlURL() throws MalformedURLException, IOException {
		ArrayList<String> storeURLs = checkCondition.getMailURLs(url, year);
		Iterator<String> storeURLsIterator = storeURLs.iterator();
		ExecutorService executor = Executors.newFixedThreadPool(8);
		int executorShutdownFlag = 0;
		while(storeURLsIterator.hasNext() || !executor.isTerminated()){
			if(storeURLsIterator.hasNext()) {
				String temp = storeURLsIterator.next().toString();
				URL storeURL = new URL(temp);
				Storing store = new FileSystem(storeURL);
				executor.execute((Runnable) store);
			}
			else if(executorShutdownFlag == 0) {
				executorShutdownFlag = 1;
				executor.shutdown();
			}
		}
		} 	
	}











