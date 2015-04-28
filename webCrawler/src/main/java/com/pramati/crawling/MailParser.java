package com.pramati.crawling;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public interface MailParser {
	public abstract ArrayList<String> getMailURLs(URL url, String year) throws FailingHttpStatusCodeException, IOException;
}
