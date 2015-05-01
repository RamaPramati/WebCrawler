package com.pramati.webcrawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class Client
{
	protected static final WebClient WEBCLIENT=new WebClient(BrowserVersion.FIREFOX_24);
	
	public Client() {
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
