package com.pramati;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class Client
{
	final static WebClient webClient=new WebClient(BrowserVersion.FIREFOX_24);;
	
	static {
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScriptStartingBefore(10000);
		webClient.setJavaScriptTimeout(1000000);
		webClient.waitForBackgroundJavaScript(5000);
	}
	
}
	 
 
        
 

 



