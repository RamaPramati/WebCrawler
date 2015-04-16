package com.pramati;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.crawling.Storing;
import com.gargoylesoftware.htmlunit.UnexpectedPage;


public class FileSystem extends Client implements Storing, Runnable {
	
	private Thread getData;
	private String threadName;
	URL urlMonthly;
	
	public FileSystem(URL url, String month) throws MalformedURLException {
		urlMonthly=url;
		this.threadName=month;
	}

	public void run() {
		File file = new File(urlMonthly.toString().substring(57, 59)+".txt");
        BufferedWriter output;
		try {
		output = new BufferedWriter(new FileWriter(file));
        UnexpectedPage rbc= webClient.getPage(urlMonthly);
        output.write(rbc.getWebResponse().getContentAsString());
        output.close();
		} 
		catch (IOException e) {
		e.printStackTrace();
		}
	}

	public void start() {
		if (getData == null) {
			getData = new Thread (this, threadName);
			getData.start ();
	      }
	}
}
