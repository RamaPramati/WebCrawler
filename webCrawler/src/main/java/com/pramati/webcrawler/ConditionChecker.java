package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class ConditionChecker extends Client {

	private static final Logger LOGGER = Logger.getLogger(ConditionChecker.class.getName());

	public static boolean isMailURL(String url) {
	
		try {
			if((webClient.getPage(url).getWebResponse().getContentType().compareTo("application/xml") == 0) || ((webClient.getPage(url).getWebResponse().getContentType().compareTo("text/plain") == 0)))
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

}
