package com.pramati.webcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pramati.crawling.Storer;
import com.gargoylesoftware.htmlunit.TextPage;

public class WritingToFile extends Client implements Storer, Runnable {

	private String threadName;
	private URL url;
	private static int count=1; 
	private static final Logger LOGGER = Logger.getLogger(WritingToFile.class.getName());

	public WritingToFile(String temp){

		super();
		try {
			this.url=new URL(temp);
			this.threadName=String.valueOf(count);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		final File file = new File("mails/"+count);
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file));
			final TextPage rbc= webClient.getPage(url);
			output.write(rbc.getWebResponse().getContentAsString());
			count++;
			output.close();
		} 
		catch (IOException e) {
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.severe("I got exception"+e);
			}
		}
	}

	public void start() {
		Thread getData = new Thread (this, threadName);
		getData.start ();
	}
}
