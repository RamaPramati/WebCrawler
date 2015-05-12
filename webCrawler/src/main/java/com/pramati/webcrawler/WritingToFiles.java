package com.pramati.webcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.TextPage;
import com.pramati.crawling.Storer;


public class WritingToFiles extends Client implements Storer, Runnable {

	private String threadName;
	private static int count=1; 
	private static final Logger LOGGER = Logger.getLogger(WritingToFiles.class.getName());
	URL url;

	public WritingToFiles(URL url) throws MalformedURLException {
		super();
		this.url = url;
		this.threadName=String.valueOf(count);
	}

	public void run() {
		File file = new File("mails/mail "+count);
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file));
			TextPage rbc= WEBCLIENT.getPage(url);
			output.write(rbc.getWebResponse().getContentAsString());
			output.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		count++;
	}

	public void start() {
		Thread getData = new Thread (this, threadName);
		getData.start ();
	}
}