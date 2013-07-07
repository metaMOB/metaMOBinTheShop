package org.dieschnittstelle.jee.esa.erp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
// @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Campaign extends AbstractProduct implements Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 4407600000386810001L;
	
	@OneToMany
	private List<ProductBundle>	bundles;
	
	public Campaign() {
		this.bundles = new ArrayList<ProductBundle>();
	}
	
	public Campaign(final String name) {
		super(name);
		this.bundles = new ArrayList<ProductBundle>();
	}
	
	public void addBundle(final ProductBundle bundle) {
		this.bundles.add(bundle);
	}
	
	public Collection<ProductBundle> getBundles() {
		return this.bundles;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, new String[] { "bundles" });
	}
	
	public void setBundles(final List<ProductBundle> bundles) {
		this.bundles = bundles;
	}
	
	// public boolean equals(Object other) {
	// return EqualsBuilder.reflectionEquals(this, other,
	// new String[] { "bundles" })
	// && LangUtils
	// .setequals(this.bundles, ((Campaign) other).bundles);
	// }
	
	@Override
	public String toString() {
		return "{Campaign " + this.getId() + ", " + this.getName() + ", " + this.bundles + "}";
	}
	
}
