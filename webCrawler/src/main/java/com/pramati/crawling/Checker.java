package com.pramati.crawling;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public interface Checker {
	 boolean isRequired(String url) throws FailingHttpStatusCodeException, IOException;
}
