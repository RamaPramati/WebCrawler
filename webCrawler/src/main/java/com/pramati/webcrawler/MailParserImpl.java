package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pramati.crawling.MailParser;

public class MailParserImpl extends Client implements MailParser {
	private ArrayList<String> storeURLs;
	private static Logger LOGGER = LoggerFactory.getLogger(MailParserImpl.class);

	public ArrayList<String> getMailURLs(URL url, String year) throws FailingHttpStatusCodeException, IOException
	{
		LOGGER.info("I am in MailParserImpl and received url and year as {}{}", url, year);
		HtmlPage currentPage=webClient.getPage("http://mail-archives.apache.org/mod_mbox/maven-users/");
		List<?> targerURLs = currentPage.getByXPath("//a[@href]");
		Iterator<?> ir=targerURLs.iterator();
		while(ir.hasNext())
		{
			String element=ir.next().toString();
			char dst[]=new char[20];
			if(element.contains(year)){
				element.getChars(20, 39, dst, 0);
				StringBuilder tempString=new StringBuilder().append(dst);
				parseForMailURLS(WebClient.expandUrl(url,tempString.toString()));
			}
		}
		return storeURLs;
	}

	private void parseForMailURLS(URL url){
		LOGGER.info("I am in parsingMailURLS and parsing the url {}", url);
		try {
			HtmlPage tempPage;
			tempPage = webClient.getPage(url);
			int noOfPages = tempPage.getByXPath("//a[@onclick and @href = 'browser']").size()-3;
			for(int i=0;i<noOfPages;i++){
				LOGGER.info("There are total {} pages in url {}", noOfPages, url );
				tempPage = webClient.getPage(url.toString().replace("browser", "thread?"+i));
				List<?> mailURLs = tempPage.getByXPath("//a[starts-with(@href, '%3c')]");
				Iterator<?> ir = mailURLs.iterator();
				while(ir.hasNext()) {
					String mailURLIterator = ir.next().toString();
					storeURLs.add(constructMailURL(url, mailURLIterator));
				}
			}
		} 
		catch (FailingHttpStatusCodeException e) {
			LOGGER.error("I got exception",e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("I got exception",e);
			e.printStackTrace();
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
			LOGGER.info("Found mail and the url is {}", WebClient.expandUrl(xmlURL,tempString.toString()).toString() );
			constructedMailURL = WebClient.expandUrl(xmlURL,tempString.toString()).toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return constructedMailURL;
	}
}
