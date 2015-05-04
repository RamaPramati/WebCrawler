package com.pramati.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import static org.junit.Assert.*;

public class MailParserImplTest {
	
	private static final Logger LOGGER = Logger.getLogger(FileSystem.class.getName());

	@Test
	public void getMailURLsTest(){
		final MailParserImpl fixture = new MailParserImpl();
		try {
			assertEquals(3244, fixture.getMailURLs(new URL("http://mail-archives.apache.org/mod_mbox/maven-users/"), "2014").size());
		} catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		}

	}

	public static void main(String args[]) {
		new org.junit.runner.JUnitCore().run(MailParserImplTest.class);
	}
}