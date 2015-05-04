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
import com.pramati.crawling.Checker;
import com.pramati.crawling.Storer;
public class Crawler 
{
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
				Crawler.crawlURL("http://mail-archives.apache.org/mod_mbox/maven-users/");
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
	
	public static void crawlURL(String urlString) throws MalformedURLException, IOException {
		
		URL url = new URL(urlString);
		ArrayList<String> urls = Parser.getURLs(url);
		ArrayList<String> storeURLs = new ArrayList<String>(); 
		Iterator<String> urlsIterator = urls.iterator();
		while(urlsIterator.hasNext()){
		if(ConditionChecker.isRequired(urlsIterator.next())){
			storeURLs.add(urlsIterator.next());
		}
		else
			crawlURL(urlsIterator.next());
		}
		
		Iterator<String> storeURLsIterator = storeURLs.iterator();
		ExecutorService executor = Executors.newFixedThreadPool(8);
		int executorShutdownFlag = 0;
		while(storeURLsIterator.hasNext() || !executor.isTerminated()){
			if(storeURLsIterator.hasNext()) {
				String temp = storeURLsIterator.next().toString();
				URL storeURL = new URL(temp);
				Storer store = new WritingToFile(storeURL);
				executor.execute((Runnable) store);
			}
			else if(executorShutdownFlag == 0) {
				executorShutdownFlag = 1;
				executor.shutdown();
			}
		}
		} 	
	}










