package com.pramati.webcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.pramati.crawling.Storer;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.SgmlPage;

public class WritingToFile extends Client implements Storer, Runnable {

	private String threadName;
	private String content;
	private static int count=1; 
	private static final Logger LOGGER = Logger.getLogger(WritingToFile.class.getName());

	public WritingToFile(Page page){

		super();
			this.content = ((SgmlPage) page).asXml();
			this.threadName=String.valueOf(count);
	}

	public void run() {
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(content));
		org.w3c.dom.Document doc;
		
			db = dbf.newDocumentBuilder();
			doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("mail");
		

		final File file = new File("mails/"+count);
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
			
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);


			NodeList name = element.getElementsByTagName("from");
			Element line = (Element) name.item(0);

			output.write("Name  :- " + line.getTextContent() + "\n");

			NodeList subject = element.getElementsByTagName("subject");

			line = (Element) subject.item(0);

			output.write("Subject  :-" + line.getTextContent() + "\n");

			NodeList date = element.getElementsByTagName("date");

			line = (Element) date.item(0);

			output.write("Date  :-" + line.getTextContent() + "\n");

			NodeList msg = element.getElementsByTagName("contents");

			line = (Element) msg.item(0);

			output.write("contents:- " + line.getTextContent() + "\n\n");
			output.write("================\n\n");

		}
		output.close();
		}catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	public void start() {
		Thread getData = new Thread (this, threadName);
		getData.start ();
	}
}
