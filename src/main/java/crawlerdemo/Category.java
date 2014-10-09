package crawlerdemo;

import java.util.ArrayList;
import java.util.List;

public class Category {

	private String name;
	private String href;
	private Category parent;
	private List<Category> children;
	
	public Category() {
	}
	
	public Category(String name) {
		super();
		this.name = name;
		this.children = new ArrayList<Category>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
		for(Category child : children){
			child.setParent(this);
		}
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	public void addChild(Category child){
		if(null == this.children){
			this.children = new ArrayList<Category>();
		}
		child.setParent(this);
		children.add(child);
	}
	public boolean hasChildren(){
		return null != this.children && this.children.size() > 0 ;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
