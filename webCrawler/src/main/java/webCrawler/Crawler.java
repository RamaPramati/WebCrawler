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
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

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
		webClient.setRefreshHandler(new ThreadedRefreshHandler());
	}
	public static void main(String args[]) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
	
        HtmlPage currentPage=webClient.getPage("http://mail-archives.apache.org/mod_mbox/maven-users/");
		
		java.util.List<?> target_div = currentPage.getByXPath("//a[@href]");
		URL expandURL=new URL("http://mail-archives.apache.org/mod_mbox/maven-users/");
		Iterator ir=target_div.iterator();
        while(ir.hasNext())
        {
        String element=ir.next().toString();
	    char dst[]=new char[20];
		if(element.contains("2014")){
		element.getChars(20, 39, dst, 0);
		String tempString=new String(dst);
		saveToFile(WebClient.expandUrl(expandURL,tempString).toString());
		}
	}
	}	
	public static void saveToFile(String urlMonthly) throws IOException
	{
		
		File file = new File(urlMonthly.substring(53, 59)+".txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));	
       
        URL url=new URL(urlMonthly);
        HtmlPage currentPage=webClient.getPage(url);
		
      
        char dst1[]=new char[100];
        urlMonthly.getChars(0, url.toString().lastIndexOf('/')+4, dst1, 0);
        StringBuilder stringUrl=new StringBuilder().append(dst1);
       
        stringUrl.replace(stringUrl.lastIndexOf("/")+1, stringUrl.lastIndexOf("/")+4, "ajax/");
       
        URL xmlURL=new URL(stringUrl.toString());	
        java.util.List<?> target_div = currentPage.getByXPath("//a[@onclick and @href != 'browser']");
       
		Iterator ir=target_div.iterator();
        while(ir.hasNext())
        {
        String element=ir.next().toString();
	    char dst[]=new char[100];
		element.getChars(element.indexOf("%3C")+3, element.indexOf("%3E")+1, dst, 0);
		StringBuilder tempString=new StringBuilder().append('<').append(dst);
		tempString.setCharAt(tempString.lastIndexOf("%"), '>');
		XmlPage currentPage1=webClient.getPage(WebClient.expandUrl(xmlURL,tempString.toString()));
		output.write(currentPage1.asXml());
        }
        output.close();		
	}
}
	 
 
        
 

 



