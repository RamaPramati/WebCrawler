package com.pramati.webcrawler;

import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class Client
{
    public final static WebClient WEBCLIENT=new WebClient(BrowserVersion.FIREFOX_24);;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	static {
		LOGGER.info("I am in Client and initializing the webClient for getting the URL page");
		WEBCLIENT.getOptions().setJavaScriptEnabled(true);
		WEBCLIENT.getOptions().setRedirectEnabled(true);
		WEBCLIENT.getOptions().setThrowExceptionOnFailingStatusCode(true);
		WEBCLIENT.getOptions().setThrowExceptionOnScriptError(true);
		WEBCLIENT.setAjaxController(new NicelyResynchronizingAjaxController());
		WEBCLIENT.waitForBackgroundJavaScriptStartingBefore(10000);
		WEBCLIENT.setJavaScriptTimeout(1000000);
		WEBCLIENT.waitForBackgroundJavaScript(5000);
	}
	
}
