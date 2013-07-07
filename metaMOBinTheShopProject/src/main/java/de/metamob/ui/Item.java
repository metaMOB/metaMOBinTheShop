package de.metamob.ui;

import java.text.DecimalFormat;

import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.util.io.IClusterable;

public class Item implements IClusterable {

	// ...

	/**
	 *
	 */
	private static final long	serialVersionUID	= -4663384356580649542L;

	private String description;

	private String id;

	private ContextRelativeResource imageFile;

	private String name;

	private float price;

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public Item(final String id, final String name, final String description, final float price, final String image) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imageFile = new ContextRelativeResource(image);
	}

	public String getDescription() {
		return this.description;
	}

	public String getId() {
		return this.id;
	}

	public ContextRelativeResource getImage() {
		return this.imageFile;
	}

	public String getName() {
		return this.name;
	}
	public String getPrice() {
		return new DecimalFormat("0.00").format(this.price);
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public void setImage(final String image) {
		this.imageFile = new ContextRelativeResource(image);
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPrice(final float price) {
		this.price = price;
	}
}
