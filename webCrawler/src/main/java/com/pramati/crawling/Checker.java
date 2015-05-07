package com.pramati.crawling;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public interface Checker {
	 boolean isRequired(String url) throws FailingHttpStatusCodeException, IOException;
}
