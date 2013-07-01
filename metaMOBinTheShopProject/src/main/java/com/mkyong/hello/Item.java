package com.mkyong.hello;

import java.text.DecimalFormat;

import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class Item implements IClusterable {
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return new DecimalFormat("0.00").format(price);
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public ContextRelativeResource getImage() {
		return imageFile;
	}

	public void setImage(String image) {
		this.image = image;
		this.imageFile = new ContextRelativeResource(image);
	}	

	private String id;
	private String name;
	private String description;
	private float price;
	private String image;
	private ContextRelativeResource imageFile;
	
	public Item() {
		// TODO Auto-generated constructor stub
	}
	
	public Item(String id, String name, String description, float price, String image) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.image = image;
		this.imageFile = new ContextRelativeResource(image);
	}
}
