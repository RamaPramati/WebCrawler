import java.io.FileOutputStream;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test{	
	  try{
		FileOutputStream data=new FileOutputStream("Crawler.txt");
		
		public static void main(String[] args) 
		{
			
			//db.runSql2("TRUNCATE Record;");
			processPage("http://mail-archives.apache.org/mod_mbox/maven-users/");
		}
	 
		public static void processPage(String URL) throws SQLException, IOException{
							
				data.write
	 
				//get useful information
				Document doc = Jsoup.connect("http://mail-archives.apache.org/mod_mbox/maven-users/").get();
	 
				Elements questions = doc.select("a[href]");
				for(Element link: questions){
					if(link.attr("href").co("2014**.mbox/browser"))
						processPage(link.attr("abs:href"));
				}
			}
		}
	


