package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.crawling.Checker;

public class MailChecker extends WebClientInitializer implements Checker{

	private static final Logger LOGGER = Logger.getLogger(MailChecker.class.getName());
	private String year;

	public MailChecker(String year) {
		super();
		this.year = year;	
	}

	public boolean isRequiredURL(String urlString) {

		try {
			if(WEBCLIENT.getPage(urlString).getWebResponse().getContentType().compareTo("text/plain") == 0 && WEBCLIENT.getPage(urlString).getWebResponse().getContentAsString().startsWith("From"))
				return true;
		} catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} catch (MalformedURLException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}		}
		catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}	
		}
		return false;
	}

	public boolean isParsableAndRequiredURL(URL url, String relativeURL) {
		return(!(relativeURL.startsWith("http://") || relativeURL.startsWith("https://")) && (url.toString().matches(".*?"+year+".*?") || relativeURL.matches(".*?"+year+".*?")));
	}
}
