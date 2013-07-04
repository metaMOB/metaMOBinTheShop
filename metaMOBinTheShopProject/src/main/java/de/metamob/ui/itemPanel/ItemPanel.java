package de.metamob.ui.itemPanel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

import de.metamob.session.SessionUtil;
import de.metamob.session.UIUserConfiguration;
import de.metamob.ui.callbacks.IMainPageItemCallback;

import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

import org.dieschnittstelle.jee.esa.erp.entities.*;

public class ItemPanel extends Panel {
	
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;
	
	private IMainPageItemCallback iMainPageItemCallback;
	private List<IndividualisedProductItem> itemList = null;
		
	private String prevMarker="<<"; 
	private String nextMarker=">>"; 
	
	private String selectedSortBy;
	private String selectedItemsPerPage;

	public String getSelectedSortBy() {
		return selectedSortBy;
	}

	public void setSelectedSortBy(String selectedSortBy) {
		this.selectedSortBy = selectedSortBy;
	}

	public String getSelectedItemsPerPage() {
		return selectedItemsPerPage;
	}

	public void setSelectedItemsPerPage(String selectedItemsPerPage) {
		this.selectedItemsPerPage = selectedItemsPerPage;
	}
	
	public void setItemsPerPage(int count){
		UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
		uiuc.setItemsPerPage(count);
		SessionUtil.setUIUserConfiguration(uiuc);
	}

	public ItemPanel(String id) {
		super(id);
		SessionUtil.setCurrentPage(0);
		setItemsPerPage(2);
		// TODO Auto-generated constructor stub
	}

	public ItemPanel(String id, IMainPageItemCallback itemPanelCallback) {
		super(id);
		this.iMainPageItemCallback = itemPanelCallback;
		SessionUtil.setCurrentPage(0);
		
		setItemsPerPage(2);
		// TODO Auto-generated constructor stub
		//addItemPanelModule();
	}
	
	private List<StockItem> generateTestProducts(){
    	String [] names = {"Stulle", "Brotchen", "Kuchen", "Zimtstern", "Mohngebaeck"};
    	int [] ids = {991, 992, 993, 994, 995};
    	int [] pos = {101, 101, 102, 103, 103};
    	int [] price = {199, 99, 599, 250, 50};
    	ProductType [] type = {ProductType.BREAD, ProductType.ROLL, ProductType.PASTRY, ProductType.PASTRY, ProductType.ROLL};
    	
    	List<StockItem> myList = new ArrayList<StockItem>();
    	
    	for (int i=0; i<names.length; i++){
    		IndividualisedProductItem tempIndividualProduct = new IndividualisedProductItem(names[i], type[i], 10 ,10);
    		tempIndividualProduct.setId(ids[i]);
    		PointOfSale tempPointOfSale = new PointOfSale();
    		tempPointOfSale.setId(pos[i]);
    		
    		StockItem tempStockItem = new StockItem(tempIndividualProduct, tempPointOfSale, 10);
    		tempStockItem.setPrice(price[i]);
    		myList.add(tempStockItem);
    	}
    	return myList;
    }
	
	//private ListView<IndividualisedProductItem> items = null;
	
	private void refreshItemList(){
		UIUserConfiguration uuuc = SessionUtil.getUIUserConfiguration();
		//List<IndividualisedProductItem> myList = null;
		
		if(uuuc.getTouchpont()==null){//uuuc.getProductType()
			itemList = stockSystem.getAllProductsOnStock(uuuc.getProductType(),uuuc.getSortType());
		}else{
			itemList = stockSystem.getProductsOnStock(uuuc.getTouchpont().getErpPointOfSaleId(),uuuc.getProductType(),uuuc.getSortType());
		}	
	}
	
	private void addItemPanelModule(){	
		
		List<IndividualisedProductItem> itemsReduced = new ArrayList<IndividualisedProductItem>(); 
		
		int itemsPerPage = SessionUtil.getUIUserConfiguration().getItemsPerPage();
		for (int i = SessionUtil.getCurrentPage()*itemsPerPage; i< Math.min(itemList.size(), (SessionUtil.getCurrentPage()+1)*itemsPerPage); i++){
			itemsReduced.add(itemList.get(i));
		}
		
		ListView<IndividualisedProductItem> items = new ListView<IndividualisedProductItem>("items", itemsReduced){
			@Override
			protected void populateItem(final ListItem<IndividualisedProductItem> entry) {
				// TODO Auto-generated method stub
				AjaxLink<Void> link = new AjaxLink<Void>("itemLink"){
					@Override
					public void onClick(AjaxRequestTarget target) {
		                System.out.println("ITEM: "+ entry.getModelObject().getName());
		            }
				};
				entry.add(link);
				entry.add(new Label("itemName", entry.getModelObject().getName()));
				entry.add(new Label("itemDescription", entry.getModelObject().getProductType()));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(entry.getModelObject().getPrice()/100.0)));
				entry.add(new Image("itemImage", new ContextRelativeResource("images/products/example.jpg")));
			}
        };
        
        if(items!=null){
			 remove(items);
	    }
        
        add(items);
	}
	
	private void addNavigationModule(){
			// TODO Auto-generated method stub
		    final int currentPage = SessionUtil.getCurrentPage();
			
			int numOfItems = itemList.size();
			int numOfItemsPerPage = SessionUtil.getUIUserConfiguration().getItemsPerPage();
			final int numOfPages = (int)Math.ceil(numOfItems / numOfItemsPerPage);
			
			
			List<String[]>  myList= new ArrayList();
			
			int startPage = 0;
			int endPage = numOfPages;
			
			if (numOfPages >= 5){
				startPage  = Math.min(numOfPages-4, Math.max(0, currentPage-2));
				endPage = Math.max(4, Math.min(numOfPages, currentPage+2));
			}
			
			
			for (int i=startPage; i<=endPage; i++){
				if (i==currentPage){
					myList.add(new String[]{String.valueOf((i+1)), "<div class=\"selectedPage\">#"+(i+1)+"</div>"});
				} else {
					myList.add(new String[]{String.valueOf(i+1), String.valueOf(i+1)});
				}				
			}
			
			if (numOfPages != 1) {
				if (currentPage==0){
					myList.add(0, new String[]{prevMarker, "<div class=\"nextprev\"><div class=\"nextprev_inactive\">"+prevMarker+"</div></div>"});
				} else {
					myList.add(0, new String[]{prevMarker, "<div class=\"nextprev\">"+prevMarker+"</div>"});
				}
				
				if (currentPage < numOfPages){
					myList.add(new String[]{nextMarker, "<div class=\"nextprev\">"+nextMarker+"</div>"});
				} else {				
					myList.add(new String[]{nextMarker, "<div class=\"nextprev\"><div class=\"nextprev_inactive\">"+nextMarker+"</div></div>"});
				}
			}
			
			//itemList.get(index)
			
			//currentPage;
				
			ListView<String[]> navigation = new ListView<String[]>("pageNavigation", myList){
				@Override
				protected void populateItem(final ListItem<String[]> entry) {
					// TODO Auto-generated method stub
					AjaxLink<Void> link = new AjaxLink<Void>("pageLink"){
						@Override
						public void onClick(AjaxRequestTarget target) {
			                System.out.println("PAGELINK: "+ entry.getModelObject());
			                if (entry.getModelObject()[0].equals(prevMarker)){
			                	if (currentPage > 0){
			                		SessionUtil.setCurrentPage(currentPage-1);
			                	}
			                } 
			                else if (entry.getModelObject()[0].equals(nextMarker)){
			                	if (currentPage < numOfPages){
			                		SessionUtil.setCurrentPage(currentPage+1);
			                	}
			                } else {
			                	SessionUtil.setCurrentPage(Integer.parseInt(entry.getModelObject()[0])-1);
			                }			                			                
							setResponsePage(getPage());				                	
			            }
					};
					Label label = new Label("pageLabel", entry.getModelObject()[1]);
					label.setEscapeModelStrings(false);
					link.add(label);
					entry.add(link);					
				}
	        };
	    if(navigation!=null){
	      	remove(navigation);
		}
		add(navigation);
	}
	
	private void addSelectOptions(){
		List sortValues = new ArrayList();
		sortValues.add(SortType.PRICEUP.toString());
		sortValues.add(SortType.PRICEDOWN.toString());
		sortValues.add(SortType.ASC.toString());
		sortValues.add(SortType.DESC.toString());
		
		final DropDownChoice<List> selectSortBy = new DropDownChoice<List>("selectSortyBy", new PropertyModel<List>(this, "selectedSortBy"), sortValues);
		selectSortBy.add(new AjaxFormComponentUpdatingBehavior("onchange") {
		      protected void onUpdate(AjaxRequestTarget target) {
		    	    setSelectedSortBy(selectedSortBy.toString());
		    	    System.out.println("selectedSortBy: " + selectedSortBy);
		    	  	UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
		    	  	uiuc.setSortType(SortType.valueOf(selectedSortBy));
					SessionUtil.setUIUserConfiguration(uiuc);
					setResponsePage(getPage());
		      }
		});
		
		List itemsPerPageValues = new ArrayList();
		itemsPerPageValues.add("2");
		itemsPerPageValues.add("5");
		itemsPerPageValues.add("10");
		itemsPerPageValues.add("25");
		
		final DropDownChoice<List> selectItemsPerPage = new DropDownChoice<List>("selectItemsPerPage", new PropertyModel<List>(this, "selectedItemsPerPage"), itemsPerPageValues);
		
		//final DropDownChoice<List> selectItemsPerPage = new DropDownChoice<List>("selectItemsPerPage", itemsPerPageValues, "selectedItemsPerPage");
		selectItemsPerPage.add(new AjaxFormComponentUpdatingBehavior("onchange") {
		      protected void onUpdate(AjaxRequestTarget target) {
		    	  setItemsPerPage(Integer.parseInt(selectedItemsPerPage));
		    	  SessionUtil.setCurrentPage(0);
		    	  System.out.println(selectedItemsPerPage);
		    	  setResponsePage(getPage());
		      }
		});
		
		
		if(selectSortBy!=null){
			 remove(selectSortBy);
	    }
		
		if(selectItemsPerPage!=null){
			 remove(selectItemsPerPage);
	    }
		
		add(selectSortBy);
		add(selectItemsPerPage);
	}
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		refreshItemList();
		addNavigationModule();
		addItemPanelModule();
		addSelectOptions();
	}
}
