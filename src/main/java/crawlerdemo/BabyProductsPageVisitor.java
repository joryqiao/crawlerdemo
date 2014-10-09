package crawlerdemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import net.vidageek.crawler.Page;
import net.vidageek.crawler.PageVisitor;
import net.vidageek.crawler.Status;
import net.vidageek.crawler.Url;

public class BabyProductsPageVisitor implements PageVisitor {

	public void onError(Url url, Status status) {
		System.out.println("Error in: \n" + url + ";\n Status: " + status);
	}

	public void visit(Page page) {
		String content = page.getContent();
		//System.out.println("Content is: \n" + content);
		
		String file = "src/test/resources/pageContent3.html";
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			IOUtils.write(content, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean followUrl(Url url) {
		System.out.println("followUrl is: " + url );
		return false;
	}

}
