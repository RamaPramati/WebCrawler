package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pramati.crawling.Checker;


public class Parser extends WebClientInitializer{

	private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

	public static List<String> getURLs(URL url, Checker checker)
	{

		ArrayList<String> urlsToBeParsed = new ArrayList<String>();

		try {
			HtmlPage currentPage = WEBCLIENT.getPage(url);
			List<HtmlAnchor> relativeURLs = (List<HtmlAnchor>) currentPage.getByXPath("//a[@href]");
			Iterator<HtmlAnchor> relativeURLsIterator = relativeURLs.iterator();
			String urlString;
			while(relativeURLsIterator.hasNext())
			{
				urlString = relativeURLsIterator.next().getHrefAttribute();
				if(checker.isParsableAndRequiredURL(url, urlString))
					urlsToBeParsed.add(WebClient.expandUrl(url, urlString.toString()).toString());
			}
		}catch (MalformedURLException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}
		return urlsToBeParsed;
	}
}
