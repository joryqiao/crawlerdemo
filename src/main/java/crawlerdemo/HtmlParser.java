package crawlerdemo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {
	
	private static final String ROOT_URL = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Dbaby-products";
	private static final String ROOT_URL2 = "http://www.amazon.com/s/ref=nb_sb_noss_1?url=search-alias%3Dbaby-products&field-keywords=baby+products";
	private static final String TARGET_DIV_CLASS = "categoryRefinementsSection"; //root div class name
	private static final String TARGET_DIV_CLASS_LEFT_NAV = "left_nav";
	private static final String URL_PREFIX = "http://www.amazon.com";
	
	public Category rootCategory;
	
	public HtmlParser() {
		super();
		this.rootCategory = new Category("root");
		this.rootCategory.setHref(ROOT_URL2);
	}
	
	public Category parse(){
		List<Category> categories = parsePage(rootCategory);
		for(Category category : categories){
			if(StringUtils.isNotEmpty(category.getHref())){
				parsePage(category);
			}else{
				return rootCategory;
			}
		}
		return rootCategory;
	}
	
	public List<Category> parsePage(Category category){
		Document doc = null;
		String url = category.getHref();
		try {
			doc = Jsoup.connect(url).timeout(3000).post();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Get the Document exception. Please check the url: " + url);
			return null;
		}
		System.out.println("The url: " + url);
		//System.out.println(doc.body());
		
		List<Category> categories = new ArrayList<Category>();
		//div
		Elements cssClassNameDivs = doc.getElementsByClass(TARGET_DIV_CLASS);
		if(cssClassNameDivs == null || cssClassNameDivs.size() == 0 ){
			System.out.println("Can not find the \"cssClassNameDivs\", to find left_nav.");
			cssClassNameDivs = doc.getElementsByClass(TARGET_DIV_CLASS_LEFT_NAV);
		}
		for(Element cssClassNameDiv : cssClassNameDivs){
//			System.out.println("div content:\n"+ cssClassNameDiv);
			categories.addAll(extractFromDiv(cssClassNameDiv));
		}
		category.setChildren(categories);
		return categories;
	}
	
	private List<Category> extractFromDiv(Element cssClassNameDiv) {
		List<Category> categories = new ArrayList<Category>();
		//ul
		Elements uls = cssClassNameDiv.getElementsByTag("ul");
		if(null ==uls || uls.size() == 0){
			System.out.println("There is no ul tag, so return.");
		}
		for(Element ulElement : uls){
			Elements lis = ulElement.getElementsByTag("li");
			for(Element ilElement : lis){
				Category category = extractCategoryInSingleLi(ilElement);
				if(StringUtils.isEmpty(category.getName())){
					System.out.println("There is no category in il tag: " + ilElement.toString());
					continue;
				}
				categories.add(category);
			}
		}
		
		return categories;
	}
	
	/**
	 * <li style="margin-left: 18px"> 
	 * 	<a href="/s?ie=UTF8&amp;page=1&amp;rh=i%3Ababy-products%2Ck%3Ababy%2Bproducts"> 
	 * 		<span class="boldRefinementLink">Baby</span> 
	 * 		<span class="srSprite rightArrow"></span> 
	 * </a> 
	 * </li>
	 * @param ilElement
	 */
	public Category extractCategoryInSingleLi(Element ilElement) {
		String categoryText = "";
		Category category = new Category();
		Elements spans = ilElement.getElementsByTag("span");
		for(Element spanElement : spans){
			if(spanElement.hasClass("boldRefinementLink") || spanElement.hasClass("refinementLink")){
				categoryText = spanElement.text();
				category.setName(categoryText);
				//System.out.println("text: " + category);
			}
		}
		Elements as = ilElement.getElementsByTag("a");
		
		for(Element aElement : as){
			String hrefValue = aElement.attr("href");
			hrefValue = URL_PREFIX + hrefValue;
			category.setHref(hrefValue);
			//System.out.println("href: " + hrefValue);
//			parse(hrefValue);
		}
		return category;
	}
	
	public Category getRootCategory() {
		return rootCategory;
	}

	public void parseContent(String html){
		Document doc = Jsoup.parse(html);
		//div
		Elements cssClassNameDivs = doc.getElementsByClass(TARGET_DIV_CLASS);
		if(cssClassNameDivs == null || cssClassNameDivs.size() == 0 ){
			System.out.println("Can not find the \"cssClassNameDivs\", to find left_nav.");
			cssClassNameDivs = doc.getElementsByClass(TARGET_DIV_CLASS_LEFT_NAV);
		}
		
		for(Element cssClassNameDiv : cssClassNameDivs){
			extractFromDiv(cssClassNameDiv);
		}
	}
}
