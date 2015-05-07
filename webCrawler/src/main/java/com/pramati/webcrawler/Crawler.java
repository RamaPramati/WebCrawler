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
public class Crawler extends Client
{
	private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());
	private static ArrayList<String> storeURLs = new ArrayList<String>(); 
	private static ArrayList<String> parsedURLs = new ArrayList<String>();
	public static ArrayList<String> urlsToBeParsed = new ArrayList<String>();

	public static void main(String args[]){

		try{
			FileHandler fileTxt;
			fileTxt = new FileHandler("log/webCrawler.log");
			SimpleFormatter formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			LOGGER.addHandler(fileTxt);

			Crawler.crawlURL("http://mail-archives.apache.org/mod_mbox/maven-users/");
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
		finally{
			Iterator<String> storeURLsIterator = storeURLs.iterator();
			while(storeURLsIterator.hasNext())
			{
				System.out.println(storeURLsIterator.next());
			}
		}
		LOGGER.info("Crawling success");
	}

	public static void crawlURL(String urlString) {

		if(ConditionChecker.isMailURL(urlString)){
			if(urlString.contains("2014"))
				storeURLs.add(urlString);
		}
		else{
			try {
				URL url = new URL(urlString);
				Parser.getURLs(url);
				Iterator<String> urlsIterator = urlsToBeParsed.iterator();
				while(urlsIterator.hasNext()){
					String temp = urlsIterator.next();
					if(!(parsedURLs.contains(temp)))
					{
						parsedURLs.add(temp);
						if(webClient.getPage(url).getWebResponse().getContentType().compareTo("text/html") == 0)
							crawlURL(temp);
					}
					//System.out.println(temp);
				}
			}
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (FailingHttpStatusCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 	

	public static void writeURLsToFile() {

		Iterator<String> storeURLsIterator = storeURLs.iterator();
		ExecutorService executor = Executors.newFixedThreadPool(8);
		int executorShutdownFlag = 0;
		while(storeURLsIterator.hasNext() || !executor.isTerminated()){
			if(storeURLsIterator.hasNext()) {
				String temp = storeURLsIterator.next().toString();
				Storer store = new WritingToFile(temp);
				executor.execute((Runnable) store);
			}
			else if(executorShutdownFlag == 0) {
				executorShutdownFlag = 1;
				executor.shutdown();
			}
		}
	}
}










