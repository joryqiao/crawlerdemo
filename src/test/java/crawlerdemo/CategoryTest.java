package crawlerdemo;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class CategoryTest {

	static Category a = new Category("A");
	static Category b = new Category("B");
	static Category c = new Category("C");
	static Category d = new Category("D");
	static Category e = new Category("E");
	static Category f = new Category("F");
	static Category g = new Category("G");
	
	@BeforeClass
	public static void beforeClass(){
		a.addChild(b);
		a.addChild(c);
		c.addChild(d);
		c.addChild(e);
		e.addChild(f);
		e.addChild(g);
	}
	
	
	@Test
	public void testGenerate(){
		System.out.println(CategoryUtil.toString(a));
	}
	
	
	
	
}














