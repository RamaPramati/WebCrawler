package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;

public class ConditionChecker extends Client {

	private static final Logger LOGGER = Logger.getLogger(ConditionChecker.class.getName());

	public static boolean isMailURL(String url) {
	
		try {
			if(WEBCLIENT.getPage(url).getWebResponse().getContentType().compareTo("text/plain") == 0)
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

	public static boolean isRequiredURL(URL url, String temp) {
			if((!(temp.startsWith("http://") || temp.startsWith("https://"))) && (url.toString().matches(".*?2014.*?") || (temp.matches(".*?2014.*?"))))
				return true;
		return false;
	}

}
