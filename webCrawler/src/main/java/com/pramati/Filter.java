package com.pramati;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Filter extends Client {
	java.util.List<?> targetURLs;
	java.util.List<String> storeURLs;
	HtmlPage currentPage;

	public java.util.List<String> isRequired(URL url) throws FailingHttpStatusCodeException, IOException {
    
	currentPage = webClient.getPage(url);
	targetURLs = currentPage.getByXPath("//a[@href]");
	Iterator<?> ir = targetURLs.iterator();
	while(ir.hasNext()) {
    String element = ir.next().toString();
	if(!(element.contains("ajax")) && (element.contains("2014")))
		storeURLs.add(element);
	else {
		url = new URL(element);
		isRequired(url);
	}
	}
	return storeURLs;
  }
}

