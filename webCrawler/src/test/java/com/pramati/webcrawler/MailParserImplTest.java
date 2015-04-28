package com.pramati.webcrawler;

import java.net.URL;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

public class MailParserImplTest {
	
	@Test
	public void getMailURLsTest()
		throws Exception {
		MailParserImpl fixture = new MailParserImpl();
		URL url = new URL("http://mail-archives.apache.org/mod_mbox/maven-users/");
		String year = "2014";

		ArrayList<String> result = fixture.getMailURLs(url, year);

		assertEquals(3244, result.size());
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MailParserImplTest.class);
	}
}