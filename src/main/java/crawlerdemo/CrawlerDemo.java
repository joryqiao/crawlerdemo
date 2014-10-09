package crawlerdemo;

import net.vidageek.crawler.PageCrawler;
import net.vidageek.crawler.config.CrawlerConfiguration;

public class CrawlerDemo {

	//"http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Dbaby-products";
	private static final String URL = "http://www.amazon.com/s/ref=nb_sb_noss_1?url=search-alias%3Daps&field-keywords=baby+products";
	
	public static void main(String[] args) {
		CrawlerConfiguration cfg = new CrawlerConfiguration(URL);
		PageCrawler crawler = new PageCrawler(cfg);

		crawler.crawl(new BabyProductsPageVisitor());	
	}

}
