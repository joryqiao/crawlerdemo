package crawlerdemo;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class HtmlParserTest {

	private static final String ROOT_URL = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Dbaby-products";
	private String liHtml = "<li style=\"margin-left: 18px\"> "
			+ "<a href=\"/s?ie=UTF8&amp;page=1&amp;rh=i%3Ababy-products%2Ck%3Ababy%2Bproducts\"> "
			+ "<span class=\"boldRefinementLink\">Baby</span> <span class=\"srSprite rightArrow\">"
			+ "</span> </a> </li> ";
	//@Test
	public void testParseHtml(){
		String html = null;
		try {
			html = IOUtils.toString(new FileInputStream("src/test/resources/pageContent.html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(html);
		
		Document doc = Jsoup.parse(html, "UTF-8");
		
		/*String keyOfBeginning = "Show results for";
		Elements showResultsFor = doc.getElementsContainingText(keyOfBeginning);
		if(showResultsFor == null || showResultsFor.size() == 0 ){
			System.out.println("Can not find the \"showResultsFor\", so stop.");
		}
		if(showResultsFor.size() > 1 ){
			System.out.println("There are more than 1 \"showResultsFor\" elements. Please pay attention");
		}*/
		
		//div
		String cssClassName = "categoryRefinementsSection";
		Elements cssClassNameDivs = doc.getElementsByClass(cssClassName);
		if(cssClassNameDivs == null || cssClassNameDivs.size() == 0 ){
			System.out.println("Can not find the \"cssClassNameDivs\", so stop.");
		}
		if(cssClassNameDivs.size() > 1 ){
			System.out.println("There are more than 1 \"cssClassNameDivs\" elements. Please pay attention");
		}
		Element cssClassNameDiv = cssClassNameDivs.first();
		//System.out.println(cssClassNameDiv.html());
		assertNotNull(cssClassNameDiv.html());
		
		//ul
		Elements uls = cssClassNameDiv.getElementsByTag("ul");
		if(null ==uls || uls.size() == 0){
			System.out.println("There is no ul tag, so return.");
		}
		for(Element ulElement : uls){
			Elements lis = ulElement.getElementsByTag("li");
			for(Element ilElement : lis){
				
				//TODO: processLi(ilElement);
				Elements spans = ilElement.getElementsByTag("span");
				for(Element spanElement : spans){
					if(spanElement.hasClass("boldRefinementLink")){
						String text = spanElement.text();
						//System.out.println("text: " + text);
						//assertNotNull(text);
					}
				}
				Elements as = ilElement.getElementsByTag("a");
				String prefix = "www.amazon.com";
				for(Element aElement : as){
					String hrefValue = aElement.attr("href");
					hrefValue = prefix + hrefValue;
					//System.out.println("href: " + hrefValue);
					//assertNotNull(hrefValue);
				}
			}
		}
	}
	
	//@Test
	public void testParseLocalFile(){
		String html = null;
		try {
			html = IOUtils.toString(new FileInputStream("src/test/resources/pageContent.html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(html);
		
//		HtmlParser parser = new HtmlParser();
//		parser.parseContent(html);
//		List<String> categories = parser.categories;
//		for(String category : categories){
//			System.out.println(category);
//		}
//		assertEquals(31, categories.size());
	}

	@Test
	public void testParse(){
		HtmlParser parser = new HtmlParser();
		Category root = parser.parse();

		String result = CategoryUtil.toString(root);
		
		String file = "src/test/resources/result.txt";
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			IOUtils.write(result, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(result);
	}
	
}
