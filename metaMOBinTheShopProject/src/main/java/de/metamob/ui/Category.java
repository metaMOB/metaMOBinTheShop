package de.metamob.ui;

import org.apache.wicket.util.io.IClusterable;

public class Category implements IClusterable {

	// *** ..

	/**
	 *
	 */
	private static final long	serialVersionUID	= 4146496982724147662L;
	private String categoryName;

	public Category() {
	}

	public Category(final String text) {
		// TODO Auto-generated constructor stub
		this.categoryName = text;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(final String text) {
		this.categoryName = text;
	}

	@Override
	public String toString(){
		//return this.categoryName;
		return this.categoryName;
	}
}

