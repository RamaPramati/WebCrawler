package java.com.pramati.webcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.webcrawler.MailChecker;

import static org.junit.Assert.*;

public class MailCheckerTest {
	
	private static final Logger LOGGER = Logger.getLogger(MailCheckerTest.class.getName());

	@Test
	public void isRequiredURLTestPositive(){
		
		final MailChecker fixture = new MailChecker("2014");
		try {
			assertTrue(fixture.isRequiredURL("http://mail-archives.apache.org/mod_mbox/maven-users/201409.mbox/raw/%3c03A9E824-BBD3-4616-B325-CB0AFCFFA834@takari.io%3e"));
		} catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} 
	}
	
	@Test
	public void isRequiredURLTestNegative(){
		
		final MailChecker fixture = new MailChecker("2014");
		try {
			assertTrue(fixture.isRequiredURL("http://mail-archives.apache.org/mod_mbox/maven-users/"));
		} catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} 
	}
	
	@Test
	public void isParsableAndRequiredURLTestNegative(){
		
		final MailChecker fixture = new MailChecker("2014");
		try {
			assertFalse(fixture.isParsableAndRequiredURL(new URL("http://mail-archives.apache.org/mod_mbox/maven-users/"),"http://mail-archives.apache.org/mod_mbox/maven-users/"));
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
	
public void isParsableAndRequiredURLTestPositive(){
		
		final MailChecker fixture = new MailChecker("2014");
		try {
			assertTrue(fixture.isParsableAndRequiredURL(new URL("http://mail-archives.apache.org/mod_mbox/maven-users/"),"201409.mbox/raw/%3c03A9E824-BBD3-4616-B325-CB0AFCFFA834@takari.io%3e"));
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
		new org.junit.runner.JUnitCore().run(MailCheckerTest.class);
	}
}