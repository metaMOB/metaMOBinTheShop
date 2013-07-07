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
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;
import org.dieschnittstelle.jee.esa.erp.entities.SortType;

import de.metamob.data.shoppingCart.ShoppingItem;
import de.metamob.session.SessionUtil;
import de.metamob.session.UIUserConfiguration;
import de.metamob.ui.callbacks.IMainPageItemCallback;

public class ItemPanel extends Panel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 8166563553772523825L;

	private IMainPageItemCallback iMainPageItemCallback;

	private List<IndividualisedProductItem> itemList = null;
	private final String nextMarker=">";

	private final String prevMarker="<";
	private String selectedItemsPerPage;

	private String selectedSortBy;
	@EJB(name="StockSystem")
	private StockSystemLocal stockSystem;

	public ItemPanel(final String id) {
		super(id);
		SessionUtil.setCurrentPage(0);
		this.setItemsPerPage(2);
		// TODO Auto-generated constructor stub
	}

	public ItemPanel(final String id, final IMainPageItemCallback itemPanelCallback) {
		super(id);
		this.iMainPageItemCallback = itemPanelCallback;
		SessionUtil.setCurrentPage(0);

		this.setItemsPerPage(2);
		// TODO Auto-generated constructor stub
		//addItemPanelModule();
	}

	private void addItemPanelModule(){

		final List<IndividualisedProductItem> itemsReduced = new ArrayList<IndividualisedProductItem>();

		final int itemsPerPage = SessionUtil.getUIUserConfiguration().getItemsPerPage();
		for (int i = SessionUtil.getCurrentPage()*itemsPerPage; i< Math.min(this.itemList.size(), (SessionUtil.getCurrentPage()+1)*itemsPerPage); i++){
			itemsReduced.add(this.itemList.get(i));
		}

		final ListView<IndividualisedProductItem> items = new ListView<IndividualisedProductItem>("items", itemsReduced){
			@Override
			protected void populateItem(final ListItem<IndividualisedProductItem> entry) {
				// TODO Auto-generated method stub
				final AjaxLink<Void> link = new AjaxLink<Void>("itemLink"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
		                System.out.println("ITEM: "+ entry.getModelObject().getName());
		                SessionUtil.getShoppingCarts().getShoppingCard(SessionUtil.getUIUserConfiguration().getTouchpont()).add(new ShoppingItem(entry.getModelObject()));

		                ItemPanel.this.iMainPageItemCallback.itemPanelClicked();
					}
				};
				entry.add(link);
				entry.add(new Label("itemName", entry.getModelObject().getName()));
				entry.add(new Label("itemDescription", ProductType.toReadableString(entry.getModelObject().getProductType())));
				entry.add(new Label("itemPrice", new DecimalFormat("0.00").format(entry.getModelObject().getPrice()/100.0)));
				entry.add(new Image("itemImage", new ContextRelativeResource("images/products/example.jpg")));
			}
        };

        if(items!=null){
			 this.remove(items);
	    }

        this.add(items);
	}

	private void addNavigationModule(){
			// TODO Auto-generated method stub
		    final int currentPage = SessionUtil.getCurrentPage();

			final int numOfItems = this.itemList.size();
			final int numOfItemsPerPage = SessionUtil.getUIUserConfiguration().getItemsPerPage();
			final int numOfPages = (int)Math.ceil(numOfItems / numOfItemsPerPage);


			final List<String[]>  myList= new ArrayList();

			int startPage = 0;
			int endPage = numOfPages;

			if (numOfPages >= 5){
				startPage  = Math.min(numOfPages-4, Math.max(0, currentPage-2));
				endPage = Math.max(4, Math.min(numOfPages, currentPage+2));
			}


			for (int i=startPage; i<=endPage; i++){
				if (i==currentPage){
					myList.add(new String[]{String.valueOf((i+1)), "<div class=\"pageactive\">"+(i+1)+"</div>"});
				} else {
					myList.add(new String[]{String.valueOf(i+1), String.valueOf(i+1)});
				}
			}

			if (numOfPages != 1) {
				if (currentPage==0){
					myList.add(0, new String[]{this.prevMarker, "<div class=\"nextprev\"><div class=\"nextprev_inactive\">"+this.prevMarker+"</div></div>"});
				} else {
					myList.add(0, new String[]{this.prevMarker, "<div class=\"nextprev\">"+this.prevMarker+"</div>"});
				}

				if (currentPage < numOfPages){
					myList.add(new String[]{this.nextMarker, "<div class=\"nextprev\">"+this.nextMarker+"</div>"});
				} else {
					myList.add(new String[]{this.nextMarker, "<div class=\"nextprev\"><div class=\"nextprev_inactive\">"+this.nextMarker+"</div></div>"});
				}
			}

			
			final ListView<String[]> navigation = new ListView<String[]>("pageNavigation", myList){
				@Override
				protected void populateItem(final ListItem<String[]> entry) {
					// TODO Auto-generated method stub
					final AjaxLink<Void> link = new AjaxLink<Void>("pageLink"){
						@Override
						public void onClick(final AjaxRequestTarget target) {
			                System.out.println("PAGELINK: "+ entry.getModelObject());
			                if (entry.getModelObject()[0].equals(ItemPanel.this.prevMarker)){
			                	if (currentPage > 0){
			                		SessionUtil.setCurrentPage(currentPage-1);
			                	}
			                }
			                else if (entry.getModelObject()[0].equals(ItemPanel.this.nextMarker)){
			                	if (currentPage < numOfPages){
			                		SessionUtil.setCurrentPage(currentPage+1);
			                	}
			                } else {
			                	SessionUtil.setCurrentPage(Integer.parseInt(entry.getModelObject()[0])-1);
			                }
							this.setResponsePage(this.getPage());
			            }
					};
					final Label label = new Label("pageLabel", entry.getModelObject()[1]);
					label.setEscapeModelStrings(false);
					link.add(label);
					entry.add(link);
				}
	        };
	    if(navigation!=null){
	      	this.remove(navigation);
		}
		this.add(navigation);
	}

	private void addSelectOptions(){
		final List sortValues = new ArrayList();
		sortValues.add(SortType.PRICEUP.toString());
		sortValues.add(SortType.PRICEDOWN.toString());
		sortValues.add(SortType.ASC.toString());
		sortValues.add(SortType.DESC.toString());

		final DropDownChoice<List> selectSortBy = new DropDownChoice<List>("selectSortyBy", new PropertyModel<List>(this, "selectedSortBy"), sortValues);
		selectSortBy.add(new AjaxFormComponentUpdatingBehavior("onchange") {
		      @Override
			protected void onUpdate(final AjaxRequestTarget target) {
		    	    ItemPanel.this.setSelectedSortBy(ItemPanel.this.selectedSortBy.toString());
		    	    System.out.println("selectedSortBy: " + ItemPanel.this.selectedSortBy);
		    	  	final UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
		    	  	uiuc.setSortType(SortType.valueOf(ItemPanel.this.selectedSortBy));
					SessionUtil.setUIUserConfiguration(uiuc);
					ItemPanel.this.setResponsePage(ItemPanel.this.getPage());
		      }
		});

		final List itemsPerPageValues = new ArrayList();
		itemsPerPageValues.add("2");
		itemsPerPageValues.add("5");
		itemsPerPageValues.add("10");
		itemsPerPageValues.add("25");

		final DropDownChoice<List> selectItemsPerPage = new DropDownChoice<List>("selectItemsPerPage", new PropertyModel<List>(this, "selectedItemsPerPage"), itemsPerPageValues);

		selectItemsPerPage.add(new AjaxFormComponentUpdatingBehavior("onchange") {
		      @Override
			protected void onUpdate(final AjaxRequestTarget target) {
		    	  ItemPanel.this.setItemsPerPage(Integer.parseInt(ItemPanel.this.selectedItemsPerPage));
		    	  SessionUtil.setCurrentPage(0);
		    	  System.out.println(ItemPanel.this.selectedItemsPerPage);
		    	  ItemPanel.this.setResponsePage(ItemPanel.this.getPage());
		      }
		});


		if(selectSortBy!=null){
			 this.remove(selectSortBy);
	    }

		if(selectItemsPerPage!=null){
			 this.remove(selectItemsPerPage);
	    }

		this.add(selectSortBy);
		this.add(selectItemsPerPage);
	}

	public String getSelectedItemsPerPage() {
		return this.selectedItemsPerPage;
	}

	public String getSelectedSortBy() {
		return this.selectedSortBy;
	}

	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		this.refreshItemList();
		this.addNavigationModule();
		this.addItemPanelModule();
		this.addSelectOptions();
	}

	private void refreshItemList(){
		final UIUserConfiguration uuuc = SessionUtil.getUIUserConfiguration();

		if(uuuc.getTouchpont()==null){
			this.itemList = this.stockSystem.getAllProductsOnStock(uuuc.getProductType(),uuuc.getSortType());
		}else{
			this.itemList = this.stockSystem.getProductsOnStock(uuuc.getTouchpont().getErpPointOfSaleId(),uuuc.getProductType(),uuuc.getSortType(),1);
		}
	}

	public void setItemsPerPage(final int count){
		final UIUserConfiguration uiuc = SessionUtil.getUIUserConfiguration();
		uiuc.setItemsPerPage(count);
		SessionUtil.setUIUserConfiguration(uiuc);
	}

	public void setSelectedItemsPerPage(final String selectedItemsPerPage) {
		this.selectedItemsPerPage = selectedItemsPerPage;
	}

	public void setSelectedSortBy(final String selectedSortBy) {
		this.selectedSortBy = selectedSortBy;
	}
}
