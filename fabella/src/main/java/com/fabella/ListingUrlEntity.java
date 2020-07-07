package com.fabella;

public class ListingUrlEntity {

	String listingPageUrl;
	String productLinkForSpecificImage;
	String imageLink;
	
	String productName;
	String brandName;
	
	
	
	public ListingUrlEntity(String listingPageUrl, String productLinkForSpecificImage, String imageLink,
			String productName, String brandName) {
		super();
		this.listingPageUrl = listingPageUrl;
		this.productLinkForSpecificImage = productLinkForSpecificImage;
		this.imageLink = imageLink;
		this.productName = productName;
		this.brandName = brandName;
	}
	public String getListingPageUrl() {
		return listingPageUrl;
	}
	public void setListingPageUrl(String listingPageUrl) {
		this.listingPageUrl = listingPageUrl;
	}
	public String getProductLinkForSpecificImage() {
		return productLinkForSpecificImage;
	}
	public void setProductLinkForSpecificImage(String productLinkForSpecificImage) {
		this.productLinkForSpecificImage = productLinkForSpecificImage;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
	
	
	
	
	
}
