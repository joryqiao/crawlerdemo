package crawlerdemo;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtil {

	private static List<List<Category>> chainAll = new ArrayList<List<Category>>();
	
	public static void generateCurrentCate(Category cate){
		if(null == cate.getParent()){ //root
			List<Category> chain = new ArrayList<Category>();
			chain.add(cate);
			chainAll.add(chain);
		} else {
			//found
			Category parent = cate.getParent();
			List<Category> categoriesTarget = null;
			for(List<Category> categories : chainAll){
				int cateSize = categories.size();
				Category lastOne = categories.get(cateSize-1); //last object
				if(lastOne.equals(parent)){
					categoriesTarget = categories;
					break;
				}
			}
			
			//
			List<Category> meAndBrothers = cate.getParent().getChildren();
			if(meAndBrothers.size() == 1){
				categoriesTarget.add(cate);
			} else {
				//TODO: 
				List<Category> categories = new ArrayList<Category>(categoriesTarget);
				categories.add(cate);
				chainAll.add(categories);
			}
		}
		
		if(cate.hasChildren()){
			for(Category child : cate.getChildren()){
				generateCurrentCate(child);
			}
		}else{
			return;
		}
	}
	
	public static String toString(Category category){
		generateCurrentCate(category);
		StringBuffer output = new StringBuffer();
		for(List<Category> cates : chainAll){
			for(int i=0; i<cates.size(); i++){
				Category cate = cates.get(i);
				output.append(cate.getName());
				if(i != (cates.size()-1))
					output.append("->");
			}
			output.append("\n");
		}
		return output.toString();
	}
	
	public static List<List<Category>> getChainAll() {
		return chainAll;
	}
	
	/*public void generate(Category cate){
		if(null == cate.getParent()){ //root
			List<Category> chain = new ArrayList<Category>();
			chain.add(cate);
			chainAll.add(chain);
		}
		
		if(!cate.hasChildren()){
//			Category parent = cate.getParent();
//			List<Category> categoriesParent = null;
//			for(List<Category> categories : chainAll){
//				int cateSize = categories.size();
//				Category lastOne = categories.get(cateSize-1); //last object
//				if(lastOne.equals(parent)){
//					categoriesParent = categories;
//					break;
//				}
//			}
//			categoriesParent.add(cate);
			return;
		}else {
			
			 * 1. find exist list; 2. copy n-1 list; 3. add to list; 4. nested;
			 
			List<Category> categoriesParent = null;
			found:
			for(Category child : cate.getChildren()){
				for(List<Category> categories : chainAll){
					int cateSize = categories.size();
					Category lastOne = categories.get(cateSize-1); //last object
					if(lastOne.equals(child.getParent())){
						categoriesParent = categories;
						break found;
					}
				}
			}
			int childrenSize = cate.getChildren().size();
			if(childrenSize > 1){
				if(null != categoriesParent){ //found it. and copy it.
					for(Category child : cate.getChildren()){
						List<Category> categories = new ArrayList<Category>(categoriesParent);
						categories.add(child);
						chainAll.add(categories);
					}
					chainAll.remove(categoriesParent);
				}
			} else if(childrenSize == 1){
				categoriesParent.add(cate.getChildren().get(0));
			}
		}
	}*/

	
	
	
}
