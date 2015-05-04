package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pramati.crawling.MailParser;

public class MailParserImpl extends Client implements MailParser {
	
	private ArrayList<String> storeURLs = new ArrayList<String>();
	private static final Logger LOGGER = Logger.getLogger(MailParserImpl.class.getName());
	
	public ArrayList<String> getMailURLs(URL url, String year) throws FailingHttpStatusCodeException, IOException
	{
		HtmlPage currentPage=WEBCLIENT.getPage(url);
		List<?> targerURLs = currentPage.getByXPath("//a[@href]");
		Iterator<?> targerURLsIterator=targerURLs.iterator();
		StringBuilder tempString=new StringBuilder();
		char temp[]=new char[20];
		while(targerURLsIterator.hasNext())
		{
			String element=targerURLsIterator.next().toString();
			if(element.contains(year)){
				element.getChars(20, 39, temp, 0);
				tempString=tempString.append(temp);
				parseForMailURLS(WebClient.expandUrl(url,tempString.toString()));
			}
			tempString.setLength(0);
		}
		return storeURLs;
	}

	private void parseForMailURLS(URL url){
		if (LOGGER.isLoggable(Level.INFO)){
			LOGGER.info("I am in parsingMailURLS and parsing the url "+url);				
		}
		try {
			HtmlPage tempPage;
			tempPage = WEBCLIENT.getPage(url);
			int noOfPages = tempPage.getByXPath("//a[@onclick and @href = 'browser']").size()-3;
			for(int i=0;i<noOfPages;i++){
				if (LOGGER.isLoggable(Level.INFO)){
					LOGGER.info("The total nuber of pages in"+url+" are"+noOfPages);				
				}
				tempPage = WEBCLIENT.getPage(url.toString().replace("browser", "thread?"+i));
				List<?> mailURLs = tempPage.getByXPath("//a[starts-with(@href, '%3c')]");
				Iterator<?> mailURLsIterator = mailURLs.iterator();
				while(mailURLsIterator.hasNext()) {
					String mailURLIterator = mailURLsIterator.next().toString();
					storeURLs.add(constructMailURL(url, mailURLIterator));
				}
			}
		} 
		catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
	}

	private String constructMailURL(URL url, String mailURLIterator){
		String constructedMailURL = null;
		try {
			char dst1[]=new char[100];
			url.toString().getChars(0, url.toString().lastIndexOf('/')+4, dst1, 0);
			StringBuilder stringUrl=new StringBuilder().append(dst1);
			stringUrl.replace(stringUrl.lastIndexOf("/")+1, stringUrl.lastIndexOf("/")+4, "raw/");
			URL xmlURL;
			xmlURL = new URL(stringUrl.toString());
			char dst[]=new char[100];
			mailURLIterator.getChars(mailURLIterator.indexOf("%3c"), mailURLIterator.indexOf("%3e")+3, dst, 0);
			StringBuilder tempString=new StringBuilder().append(dst);
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.info("Found mailurl as "+WebClient.expandUrl(xmlURL,tempString.toString()).toString());
			}
			constructedMailURL = WebClient.expandUrl(xmlURL,tempString.toString()).toString();
		} catch (MalformedURLException e){
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
		return constructedMailURL;
	}
}
