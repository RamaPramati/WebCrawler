package com.pramati.crawling;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public interface MailParser {
	 List<String> getMailURLs(URL url, String year) throws FailingHttpStatusCodeException, IOException;
}
