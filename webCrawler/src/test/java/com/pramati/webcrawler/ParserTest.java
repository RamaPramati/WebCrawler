package java.com.pramati.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.pramati.crawling.Checker;
import com.pramati.webcrawler.MailChecker;
import com.pramati.webcrawler.Parser;

import static org.junit.Assert.*;

public class ParserTest {
	
	private static final Logger LOGGER = Logger.getLogger(ParserTest.class.getName());

	@Test
	public void getURLsTest(){
		
		Checker checker = new MailChecker("2014");
		try {
			assertEquals(545,Parser.getURLs(new URL("http://mail-archives.apache.org/mod_mbox/maven-users/"),checker).size());
		} catch (FailingHttpStatusCodeException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} catch (MalformedURLException e) {
			if (LOGGER.isLoggable(Level.INFO)){
				LOGGER.severe(e.toString());
			}
		} 
	}
	
	public static void main(String args[]) {
		new org.junit.runner.JUnitCore().run(ParserTest.class);
	}
}