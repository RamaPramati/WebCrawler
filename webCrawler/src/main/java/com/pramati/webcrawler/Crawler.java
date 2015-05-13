package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.crawling.Checker;
import com.pramati.crawling.Storer;

public class Crawler extends WebClientInitializer
{
	private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());
	private static List<String> requiredURLs = new ArrayList<String>(); 
	private static List<String> parsedURLs = new ArrayList<String>();
	private static String year = "2014";
	private static String url = "http://mail-archives.apache.org/mod_mbox/maven-users/";

	public static void main(String args[]){

		try{
			FileHandler fileTxt;
			fileTxt = new FileHandler("log/webCrawler.log");
			SimpleFormatter formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			LOGGER.addHandler(fileTxt);

			Checker checker = new MailChecker(year); 
			Crawler.crawlURL(url, checker);
			Crawler.writeURLsToFile();
		}
		catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
		catch (SecurityException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} 		
		catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
		LOGGER.info("Crawling success");
	}

	public static void crawlURL(String urlString, Checker checker) {

		ArrayList<String> urlsToBeParsed = new ArrayList<String>();
		try {
			if(checker.isRequiredURL(urlString)){
				requiredURLs.add(urlString);
			}
			else{
				URL url = new URL(urlString);
				if(WEBCLIENT.getPage(new URL(urlString)).getWebResponse().getContentType().compareTo("text/html") == 0)
					urlsToBeParsed.addAll(Parser.getURLs(url, checker));
				Iterator<String> urlsIterator = urlsToBeParsed.iterator();
				while(urlsIterator.hasNext()){
					String temp = urlsIterator.next();
					if(!(parsedURLs.contains(temp)))
					{
						LOGGER.info("Crawling url"+temp);
						parsedURLs.add(temp);
						crawlURL(temp, checker);
					}
				}
			}
		}catch (IllegalArgumentException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
		catch (ConcurrentModificationException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
		catch (MalformedURLException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
	} 	

	public static void writeURLsToFile() {

		try {
			Iterator<String> requiredURLsIterator = requiredURLs.iterator();
			ExecutorService executor = Executors.newFixedThreadPool(8);
			int executorShutdownFlag = 0;
			while(requiredURLsIterator.hasNext() || !executor.isTerminated()){
				if(requiredURLsIterator.hasNext()) {
					String temp = requiredURLsIterator.next().toString();
					LOGGER.info("Writing url:"+temp);
					Storer store = new WritingToFiles(new URL(temp));
					executor.execute((Runnable) store);
				}
				else if(executorShutdownFlag == 0) {
					executorShutdownFlag = 1;
					executor.shutdown();
				}
			}
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
}










