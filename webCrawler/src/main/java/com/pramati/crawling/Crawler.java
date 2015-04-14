package com.pramati.crawling;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.crawling.Storable;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Crawler extends Client
{	
	java.util.List<?> targetURLs;
	static URL url;
	String year;
	HtmlPage currentPage;
	
	public Crawler(String url, String year) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
	    currentPage=webClient.getPage(url);
        this.year=year;
        Crawler.url=new URL(url);
	}

	public void getMails() throws MalformedURLException, IOException
	{
		targetURLs = currentPage.getByXPath("//a[@href]");
		Iterator<?> ir=targetURLs.iterator();
		while(ir.hasNext())
      {
        String element=ir.next().toString();
	    char dst[]=new char[20];
	    char month[]=new char[20];
		if(!(element.contains("ajax")))
		{
		url= new URL(element);
		getMails();
		}
		else
		{
		element.getChars(20, 31, dst, 0);
		StringBuilder tempString=new StringBuilder().append(dst);
		element.getChars(24, 26, month, 0);
		StringBuilder month1=new StringBuilder().append(month);
		Storable store=new FileSystem(WebClient.expandUrl(url,tempString.toString()),month1.toString());
		store.start();
		}
	  }
	}	
}
