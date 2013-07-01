package com.mkyong.hello;

import org.apache.wicket.util.io.IClusterable;
import org.apache.*;

public class Category implements IClusterable {

	// *** .. ...
	
	private String categoryName;
	
	public Category() {
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String text) {
		this.categoryName = text;
	}
	
	public Category(String text) {
		// TODO Auto-generated constructor stub
		this.categoryName = text;
	}
	
	public String toString(){
		//return this.categoryName;
		return categoryName;
	}
}

