package com.pramati.crawling;

import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public interface Checker {
	public abstract boolean isRequiredURL(String url) throws FailingHttpStatusCodeException, IOException;
	public abstract boolean isParsableAndRequiredURL(URL url, String relativeURL);
}
