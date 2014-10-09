package crawlerdemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import net.vidageek.crawler.PageCrawler;
import net.vidageek.crawler.config.CrawlerConfiguration;

public class CrawlerDemo {

	private static final String URL = "http://www.amazon.com/s/ref=nb_sb_noss_1?url=search-alias%3Daps&field-keywords=baby+products";
	private static final String URL_INSTEAD = "http://www.amazon.com/s/ref=nb_sb_noss_1?url=search-alias%3Dbaby-products&field-keywords=baby+products";
	
	public static void main(String[] args) {
		HtmlParser parser = new HtmlParser();
		Category root = parser.parse();
		String result = CategoryUtil.toString(root);
		String file = "result.txt";
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			IOUtils.write(result, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		CrawlerConfiguration cfg = new CrawlerConfiguration(URL_INSTEAD);
		PageCrawler crawler = new PageCrawler(cfg);
		
		crawler.crawl(new BabyProductsPageVisitor());	*/
	}

}
