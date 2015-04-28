package com.pramati.webcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.crawling.Storing;
import com.gargoylesoftware.htmlunit.TextPage;


public class FileSystem extends Client implements Storing, Runnable {

	private Thread getData;
	private String threadName;
	private URL url;
    private static int count=1; 
	static Logger LOGGER = LoggerFactory.getLogger(FileSystem.class);

    
	public FileSystem(URL url){
		LOGGER.info("I am in Filesystem and received url as {}{}", url);
		this.url=url;
		this.threadName=String.valueOf(count);
	}

	public void run() {
		File file = new File("mails/"+String.valueOf(count));
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file));
			TextPage rbc= webClient.getPage(url);
			output.write(rbc.getWebResponse().getContentAsString());
			count++;
			output.close();
		} 
		catch (IOException e) {
			LOGGER.error("I got exception",e);
			e.printStackTrace();
		}
	}

	public void start() {
		getData = new Thread (this, threadName);
		getData.start ();
	}
}
