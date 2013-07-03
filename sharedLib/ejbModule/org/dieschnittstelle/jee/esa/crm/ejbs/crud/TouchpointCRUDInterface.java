package org.dieschnittstelle.jee.esa.crm.ejbs.crud;

import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

public interface TouchpointCRUDInterface {
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint Touchpoint);
	public AbstractTouchpoint readTouchpoint(int id);
	public List<AbstractTouchpoint> readAllTouchpoints();
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint Touchpoint);	
	public boolean deleteTouchpoint(int id);
}
