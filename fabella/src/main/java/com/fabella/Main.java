package com.fabella;

import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

	public static void main(String kj[]) throws InterruptedException, SQLException
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		WebDriver driver = new  ChromeDriver(chromeOptions);
		
		driver.manage().window().maximize();
		
		String url = "https://www.falabella.com.co/falabella-co/category/cat1660941/Celulares-y-Smartphones?page=";
		driver.navigate().to(url);
		Utils.clickElement(driver, "//*[@id='showfb-modal-info-estado-p']/div/a/div/img");
		Utils.waitDriver(driver, 1);
		driver.navigate().to(url+1);
		Utils.waitDriver(driver, 1);
		
		try {
			Utils.clickElement(driver, "//*[@id='acc-alert-accept']");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//List<String> urls = Utils.getUrlListOfSmartPhones(driver, url);
		//Utils.insertUrlInDb(urls);
		
		String insert_into_listing_pages_table = Utils.getValueByNodeName("config.xml", "insert_into_listing_pages_table"); 
		String insertInto_product_links_table = Utils.getValueByNodeName("config.xml", "insert_into_product_links_table");
		
		if(insert_into_listing_pages_table.equalsIgnoreCase("true"))
		{
			List<String> listing_pages_table_Urls = Utils.getListingPagesURLs();
			for(String listingPageUrl: listing_pages_table_Urls)
			{
				driver.navigate().to(listingPageUrl);
				
				try {
					Utils.insertProductLinksInDb(driver, listingPageUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		//----------Above was the process to insert the links of products and below is to insert a particular product detail---------
		
		if(insertInto_product_links_table.equalsIgnoreCase("true"))
		{
			int totalLinksInproduct_linkTable = Utils.getRowCountOfProductLinkToBeProcessed();
			System.out.println(totalLinksInproduct_linkTable);
			for(int i=0; i<totalLinksInproduct_linkTable;i++){
				Utils.fetchProductDetails(driver, totalLinksInproduct_linkTable);
			}
		}
	}
}
