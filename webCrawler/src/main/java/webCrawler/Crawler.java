package webCrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Crawler
{
	
	final static WebClient webClient;
	
	static
	{
		webClient= new WebClient(BrowserVersion.FIREFOX_24);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
		webClient.getOptions().setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScriptStartingBefore(10000);
		webClient.setJavaScriptTimeout(1000000);
		webClient.waitForBackgroundJavaScript(5000);
	}
	public static void main(String args[]) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
	
        HtmlPage currentPage=webClient.getPage("http://mail-archives.apache.org/mod_mbox/maven-users/");
		
		java.util.List<?> targerURLs = currentPage.getByXPath("//a[@href]");
		URL expandURL=new URL("http://mail-archives.apache.org/mod_mbox/maven-users/");
		Iterator ir=targerURLs.iterator();
        while(ir.hasNext())
        {
        String element=ir.next().toString();
	    char dst[]=new char[20];
		if(element.contains("2014")){
		element.getChars(20, 31, dst, 0);
		StringBuilder tempString=new StringBuilder().append(dst);
		saveToFile(WebClient.expandUrl(expandURL,tempString.toString()).toString());
		}
	}
	}	
	public static void saveToFile(String urlMonthly) throws IOException
	{
		
		File file = new File(urlMonthly.substring(53, 59)+".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        
        URL url=new URL(urlMonthly);
              
        UnexpectedPage rbc= webClient.getPage(url);
        
        output.write(rbc.getWebResponse().getContentAsString());
        
        output.close();		
	}
}
