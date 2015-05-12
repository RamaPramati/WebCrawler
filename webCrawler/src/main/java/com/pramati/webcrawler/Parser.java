package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class Parser extends Client{

	private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

	public static ArrayList<String> getURLs(URL url)
	{

		ArrayList<String> urlsToBeParsed = new ArrayList<String>();

		try {
			HtmlPage currentPage = WEBCLIENT.getPage(url);
			List<HtmlAnchor> targerURLs = (List<HtmlAnchor>) currentPage.getByXPath("//a[@href]");
			Iterator<HtmlAnchor> targerURLsIterator = targerURLs.iterator();
			String temp;
			while(targerURLsIterator.hasNext())
			{
				temp = targerURLsIterator.next().getHrefAttribute();
				if(ConditionChecker.isRequiredURL(url, temp))
					urlsToBeParsed.add(WebClient.expandUrl(url, temp.toString()).toString());
			}
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlsToBeParsed;
	}
}
