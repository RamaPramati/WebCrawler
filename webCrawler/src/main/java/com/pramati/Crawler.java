package com.pramati;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.crawling.Storable;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class Crawler 
{	
	static URL url;
	String year;
	
	public Crawler(String url, String year) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
        this.year=year;
        Crawler.url=new URL(url);
	}

	public void getMails() throws MalformedURLException, IOException
	{
		Filter checkCondition= new Filter();
		java.util.List<String> storeURLs=checkCondition.isRequired(url);
		Iterator<?> ir=storeURLs.iterator();
		while(ir.hasNext())
      {
        String element=ir.next().toString();
	    char dst[]=new char[20];
	    char month[]=new char[20];
		element.getChars(20, 31, dst, 0);
		StringBuilder tempString=new StringBuilder().append(dst);
		element.getChars(24, 26, month, 0);
		StringBuilder month1=new StringBuilder().append(month);
		URL storeURL=new URL(element);
		Storable store=new FileSystem(storeURL,month1.toString());
		store.start();
	  }
	}
}	

