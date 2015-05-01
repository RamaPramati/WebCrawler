package com.pramati.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import static org.junit.Assert.*;

public class MailParserImplTest {
	static final Logger LOGGER = Logger.getLogger(FileSystem.class.getName());
	URL	url;
	@Test
	public void getMailURLsTest(){
		final MailParserImpl fixture = new MailParserImpl();
		try {
			url = new URL("http://mail-archives.apache.org/mod_mbox/maven-users/");
		} catch (MalformedURLException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe("I got exception"+e);
			}
		}
		try {
			assertEquals(3244, fixture.getMailURLs(url, "2014").size());
		} catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe("I got exception"+e);

			}
		} catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe("I got exception"+e);

			}
		}

	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MailParserImplTest.class);
	}
}