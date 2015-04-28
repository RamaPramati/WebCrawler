package com.pramati.webcrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class Client
{
    final static WebClient webClient=new WebClient(BrowserVersion.FIREFOX_24);;
	static Logger LOGGER = LoggerFactory.getLogger(Client.class);
	
	static {
		LOGGER.info("I am in Client and initializing the webClient for getting the URL page");
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScriptStartingBefore(10000);
		webClient.setJavaScriptTimeout(1000000);
		webClient.waitForBackgroundJavaScript(5000);
	}
	
}
