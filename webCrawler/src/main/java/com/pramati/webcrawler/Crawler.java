package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.crawling.MailParser;
import com.pramati.crawling.Storing;
import org.slf4j.*;
public class Crawler 
{
	private MailParser checkCondition;
	private static URL url;
	private String year;
	static Logger LOGGER = LoggerFactory.getLogger(Crawler.class);
	
	public Crawler(String url, String year, MailParser condition) throws FailingHttpStatusCodeException, MalformedURLException {
		LOGGER.info("I am in Crawler and received url and conditions as {}{}", url,condition);
		this.year=year;
		Crawler.url=new URL(url);
		checkCondition=condition;
	}

	public void crawlURL() throws MalformedURLException, IOException {
		LOGGER.debug("I am on crawURL");
		ArrayList<String> storeURLs = checkCondition.getMailURLs(url, year);
		Iterator<?> ir=storeURLs.iterator();
		ExecutorService executor = Executors.newFixedThreadPool(8);
		while(ir.hasNext()) {
			LOGGER.debug("I am on running thread for urlpage reading {}", ir.next().toString());
			URL storeURL = new URL(ir.next().toString());
			Storing store = new FileSystem(storeURL);
			executor.execute((Runnable) store);//store.start();
		}
		executor.shutdown();  
		while (!executor.isTerminated()) {   }  
		System.out.println(storeURLs.size());
	}

}









