package com.fabella;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Sleeper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import net.bytebuddy.dynamic.scaffold.MethodGraph.NodeList;

public class Utils {

	public static String getValueByNodeName(String xmlFilePath, String tagName)
	{
		String value="";
		try {
			File xmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			org.w3c.dom.NodeList nList = doc.getElementsByTagName("value");
			Node nNode = nList.item(0);
			if(nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) nNode;
				value = eElement.getElementsByTagName(tagName).item(0).getTextContent();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	public void getList(List<HashMap<String, String>> productsLinks, WebDriver driver) 
	{

		for (HashMap<String, String> hm : productsLinks) {
			getListData(hm, driver);
			// break;
		}

	}
	public static String getTextFromXpath(WebDriver driver, String xpath)
	{
		return driver.findElement(By.xpath(xpath)).getText().trim();
	}

	public void getListData(HashMap<String, String> hm, WebDriver driver) {
		String id = hm.get("id");
		String url = hm.get("url");

		driver.get(url);
		sleepMicroSec(1000);

		List<WebElement> productsList = driver.findElements(By.xpath(
				".//div[contains(@class,\"search-results--products\")]//div[contains(@class,\"search-results\")]"));

		System.out.println("productsList size = " + productsList.size());

		for (WebElement element : productsList) {
			System.out.println("====================================");
			String productkey = "NA";
			String productCategory = "NA";
			String podId = "NA";
			String productLink = "NA";
			String images = "NA";
			String podTitle = "NA";
			String podSubtitle = "NA";
			String sellerText = "NA";
			String podRatingRound = "NA";
			String ratingStar = "NA";
			String podDescriptions = "NA";
			String podBadges = "NA";
			String primaryHighPrice = "NA";
			String primaryPrice = "NA";
			String tertiaryPrice = "NA";

			List<WebElement> podDescListEle = null;

			productkey = element.findElement(By.xpath(".//div[contains(@data-pod,\"catalyst-pod\")]"))
					.getAttribute("data-key");
			productCategory = element.findElement(By.xpath(".//div[contains(@data-pod,\"catalyst-pod\")]"))
					.getAttribute("data-category");
			podId = element.findElement(By.xpath(".//div[contains(@data-pod,\"catalyst-pod\")]")).getAttribute("id");
			productLink = element.findElement(By.xpath(".//a[contains(@class,\"pod-link\")]")).getAttribute("href");
			List<WebElement> imagesEle = element.findElements(By.xpath(".//img"));
			podTitle = element.findElement(By.xpath(".//b[contains(@class,\"pod-title\")]"))
					.getAttribute("textContent");
			podSubtitle = element.findElement(By.xpath(".//b[contains(@class,\"pod-subTitle\")]"))
					.getAttribute("textContent");
			sellerText = element.findElement(By.xpath(".//b[contains(@class,\"pod-sellerText\")]"))
					.getAttribute("textContent");

			if (!isEmpty(element, ".//div[contains(@class,\"pod-rating\")]")) {
				podRatingRound = element.findElement(By.xpath(".//div[contains(@class,\"pod-rating\")]"))
						.getAttribute("textContent");
			}

			if (!isEmpty(element, ".//div[contains(@class,\"pod-rating\")]//div[contains(@data-rating,\"\")]")) {
				ratingStar = element
						.findElement(
								By.xpath(".//div[contains(@class,\"pod-rating\")]//div[contains(@data-rating,\"\")]"))
						.getAttribute("data-rating");
			}

			if (!isEmpty(element, ".//ul[contains(@class,\"pod-bottom-description\")]/li")) {
				podDescListEle = element
						.findElements(By.xpath(".//ul[contains(@class,\"pod-bottom-description\")]/li"));
			}

			if (!isEmpty(element, ".//div[contains(@class,\"pod-badges\")]")) {
				podBadges = element.findElement(By.xpath(".//div[contains(@class,\"pod-badges\")]"))
						.getAttribute("textContent");
			}

			List<WebElement> pricesEle = element.findElements(By.xpath(".//div[contains(@class,\"prices\")]//ol/li"));

			if (imagesEle != null) {
				images = "";
				for (WebElement imgEle : imagesEle) {
					images = images + imgEle.getAttribute("src") + " || ";
				}
				images = "|| " + images;
			}

			if (podDescListEle != null) {
				podDescriptions = "";
				for (WebElement descEle : podDescListEle) {
					podDescriptions = podDescriptions + descEle.getAttribute("textContent") + " || ";
				}
				podDescriptions = "|| " + podDescriptions;
			}

			if (pricesEle != null) {

				for (WebElement priceEle : pricesEle) {

					if (!isEmpty(priceEle, ".//span[contains(@class,\"primary\") and contains(@class,\"high\")]")) {
						primaryHighPrice = priceEle
								.findElement(
										By.xpath(".//span[contains(@class,\"primary\") and contains(@class,\"high\")]"))
								.getAttribute("textContent");
					} else if (!isEmpty(priceEle,
							".//span[contains(@class,\"primary\") and not(contains(@class,\"high\"))]")) {
						primaryPrice = priceEle
								.findElement(By
										.xpath(".//span[contains(@class,\"primary\") and not(contains(@class,\"high\"))]"))
								.getAttribute("textContent");
					} else if (!isEmpty(priceEle, ".//span[contains(@class,\"tertiary\")]")) {
						tertiaryPrice = priceEle.findElement(By.xpath(".//span[contains(@class,\"tertiary\")]"))
								.getAttribute("textContent");
					}
				}

			}

			System.out.println("productkey = " + productkey);
			System.out.println("productCategory = " + productCategory);
			System.out.println("podId = " + podId);
			System.out.println("productLink = " + productLink);
			System.out.println("podTitle = " + podTitle);
			System.out.println("podSubtitle = " + podSubtitle);
			System.out.println("sellerText = " + sellerText);
			System.out.println("podRatingRound = " + podRatingRound);
			System.out.println("ratingStar = " + ratingStar);
			System.out.println("podBadges = " + podBadges);
			System.out.println("images = " + images);
			System.out.println("podDescriptions = " + podDescriptions);
			System.out.println("primaryHighPrice = " + primaryHighPrice);
			System.out.println("primaryPrice = " + primaryPrice);
			System.out.println("tertiaryPrice = " + tertiaryPrice);
		}
	}

	public boolean isEmpty(WebElement element, String xpath) {
		try {
			element.findElement(By.xpath(xpath));
			return false;
		} catch (Exception e) {
			return true;

		}
	}

	public void doScroll(WebDriver driver) {
		System.out.println("Inside scrolling ");
		try {
			long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

			Thread.sleep(8000);
			System.out.println("last height " + lastHeight);

			while (true) {
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

				Thread.sleep(5000);

				long newHeight = (long) ((JavascriptExecutor) driver)
						.executeScript("return document.body.scrollHeight");
				if (newHeight == lastHeight) {
					break;
				}
				System.out.println("newHeight " + newHeight);
				lastHeight = newHeight;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sleepMicroSec(int timeInMicroSec) {

		String t = timeInMicroSec+"";
		try {
			Thread.sleep(Long.parseLong(t));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clickElement(WebDriver driver, String xpathExpression)
	{
		driver.findElement(By.xpath(xpathExpression)).click();
	}
	
	public static List<String> getUrlListOfSmartPhones(WebDriver driver, String url)
	{
		List<String> urls = new ArrayList<>();
		List<WebElement> listofPaginationInPage = driver.findElements(By.xpath("//div[1]/div[@class='content-box-points'][1]/div[@class='no-selected-number extreme-number'][1]"));
		String numberOfPageString = listofPaginationInPage.get(0).getText();
		//String numberOfPageString = driver.findElement(By.xpath("//div/div[@class='content-box-points']/div[@class='no-selected-number extreme-number']")).getText().trim();
		int numberOfPages = Integer.parseInt(numberOfPageString);
		for(int i=1; i<=numberOfPages;i++)
		{
			String dynamicProductUrl = url+i;
			driver.navigate().to(dynamicProductUrl);
			List<WebElement> urlelements = driver.findElements(By.xpath("//*[@id='all-pods']/div[@class='pod-item']/div[@class='pod-body']/a"));
			for(WebElement urlLoop: urlelements)
			{
				String href = urlLoop.getAttribute("href");
				System.out.println(href);
				urls.add(href);
			}
			
		}
		return urls;
	}


	public static void waitDriver(WebDriver driver, int seconds) throws InterruptedException {
		synchronized (driver) {
			Thread.sleep(2000);
		}
	}
	/*
	public static void insertUrlInDb(List<String> urls) throws SQLException
	{
		Connection con = null;
		try {
			Class.forName(CONSTANTS.DRIVER_CLASS);
			con=DriverManager.getConnection(CONSTANTS.CONNECTION_URL+"", CONSTANTS.DB_USERNAME, CONSTANTS.DB_PASSWORD);
			Statement st;
 			for(String url :urls)
			{
				st = con.createStatement();
				String query = "INSERT INTO "+CONSTANTS.DB_NAME+".product_links(product_links) VALUES('"+url+"');";
				st.execute(query);
				st.close();
			}
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			try{
				con.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	*/
	public static Set<String> findDuplicates(List<String> listContainingDuplicates) {
		 
		final Set<String> setToReturn = new HashSet<String>();
		final Set<String> set1 = new HashSet<String>();
 
		for (String yourInt : listContainingDuplicates) {
			if (!set1.add(yourInt)) {
				setToReturn.add(yourInt);
			}
		}
		return setToReturn;
	}
	public static List<String> getListingPagesURLs()
	{
		Connection con = null;
		List<String> sub_sub_category_links = null;
		try {
			Class.forName(CONSTANTS.DRIVER_CLASS);
			con=DriverManager.getConnection(CONSTANTS.CONNECTION_URL, CONSTANTS.DB_USERNAME, CONSTANTS.DB_PASSWORD);
			Statement st;
			st = con.createStatement();
			String query = "SELECT sub_sub_category_link from listing_pages WHERE scanned=0";
			ResultSet resultSet = st.executeQuery(query);
			sub_sub_category_links = new ArrayList<>();
			while(resultSet.next()){
				sub_sub_category_links.add(resultSet.getString("sub_sub_category_link"));
			}
			System.out.println(sub_sub_category_links);
			}
			
			
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		Set<String> duplicates = findDuplicates(sub_sub_category_links);
		System.out.println(duplicates);
		return sub_sub_category_links;
	}
	
	public static int getNumberOfPagesInProductPage(WebDriver driver)
	{
		int pages = 0;
		try{
			String xpathForSinglePageApplication = "//*[@id='testId-searchResults-actionBar']/div/div[2]/div/ol/li";
			String xpathForMultiplePageApplication = "//div[1]/div/div/div[contains(@class, 'pagination')][1]/ol/li";
			//System.out.println(driver.findElements(By.xpath(xpathFor1Pages)).size());
			
			if(driver.findElements(By.xpath(xpathForSinglePageApplication)).size()==1)
			{
				System.out.println("Single pagen app");
				pages = 1;
			}
			else if(driver.findElements(By.xpath(xpathForMultiplePageApplication)).size()>1)
			{
				System.out.println("Multiple page application");
				List<WebElement> pagesListWebElements = driver.findElements(By.xpath(xpathForMultiplePageApplication));
				String pagesInString = pagesListWebElements.get(pagesListWebElements.size()-1).getText();
				pages = Integer.parseInt(pagesInString);
				System.out.println(pages);
			}
			
			return pages;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pages;
	}
	

	public static void insertProductLinksInDb(WebDriver driver, String listingPageUrl) throws InterruptedException {
		driver.navigate().to(listingPageUrl);
		
		//listingPageUrl = "https://www.falabella.com.co/falabella-co/category/cat50670/Audifonos";
		driver.navigate().to(listingPageUrl);
		 
		String textForNumberOfPages="";
		
		int productsOnCurrentPage = 0;
		int products = 0;
		int productsCount = 0;
		List<String> urls;// = new  ArrayList<>();
		List<String> queries = new  ArrayList<>();
		//String StringForBatchInsert[];
		int numberOfProductPages = getNumberOfPagesInProductPage(driver);
		try
		{
			for(int i=1; i<=numberOfProductPages; i++)
			{
					urls = new  ArrayList<>();
					driver.navigate().to(listingPageUrl+"?page="+i);
					sleepMicroSec(400);
					textForNumberOfPages = driver.findElement(By.xpath("//div[@id='testId-searchResults-actionBar-bottom']/div/div/span/span")).getText().trim();
					//productsOnCurrentPage = Integer.parseInt(textForNumberOfPages.split("de")[0].split("- ")[1].trim());
					sleepMicroSec(1000);
					List<WebElement> urlsOfProductsWebElement = driver.findElements(By.xpath("//div[@id='testId-searchResults-products']/div"));//a"));
					List<String> urlOnCurrentPage = new ArrayList<>();
	
					for(int j=1; j<=urlsOfProductsWebElement.size(); j++)
					{
						String xpathForCurrentDiv = "//div[@id='testId-searchResults-products']/div["+j+"]";
						sleepMicroSec(400);
						String xpathForCurrentDivColorsVariantsList = xpathForCurrentDiv + "/div/div/ul/li";
						if(driver.findElements(By.xpath(xpathForCurrentDivColorsVariantsList)).size()>0)
						{
							List<WebElement> buttonsWebElements = driver.findElements(By.xpath(xpathForCurrentDivColorsVariantsList));
							for(int n=1; n<=buttonsWebElements.size(); n++)
							{
							
							
								String buttonXpath = xpathForCurrentDivColorsVariantsList + "["+n+"]";
								driver.findElement(By.xpath(buttonXpath)).click();
								sleepMicroSec(300);
								String xpathForCurrentDivTo_a_Tag = "//div[@id='testId-searchResults-products']/div["+j+"]/div/a";
								String link = driver.findElement(By.xpath(xpathForCurrentDivTo_a_Tag)).getAttribute("href").toString().trim();
								urlOnCurrentPage.add(link);
							}
						}
						else
						{
							String xpath = xpathForCurrentDiv + "/div/a";
							String link = driver.findElement(By.xpath(xpath)).getAttribute("href").trim();
							urlOnCurrentPage.add(link);
						}
						
					}
					productsCount = productsCount+urlsOfProductsWebElement.size();
					
					urls.addAll(urlOnCurrentPage);
					//System.out.println(urlOnCurrentPage);
				
				for(String url:urls)
				{
					queries.add("INSERT INTO "+CONSTANTS.DB_NAME+".product_links(product_link, sub_sub_category_link, created_at) VALUES('"+url+"','"+listingPageUrl+"', NOW())");
					
				}
				int a = queries.size();
				System.out.println("queries till page "+i+"="+a);
			}
			
			Connection con = null;
			try {
				Class.forName(CONSTANTS.DRIVER_CLASS);
				con=DriverManager.getConnection(CONSTANTS.CONNECTION_URL, CONSTANTS.DB_USERNAME, CONSTANTS.DB_PASSWORD);
				Statement st;
				st = con.createStatement();
				for(String query:queries){
					st.addBatch(query);
				
				}
				int[] numberOfInsertions = st.executeBatch();
				if(numberOfInsertions.length>0)
				{
					try{
						Connection connetion=DriverManager.getConnection(CONSTANTS.CONNECTION_URL, CONSTANTS.DB_USERNAME, CONSTANTS.DB_PASSWORD);
						Statement statementForUpdate = connetion.createStatement();
						String sql = "UPDATE "+CONSTANTS.DB_NAME+".listing_pages SET scanned="+1+
								", no_of_pages = "+numberOfProductPages+""+
								", count_automated = "+productsCount+""+
								", scanned = "+1+""+
								" WHERE sub_sub_category_link='"+listingPageUrl+"'";
						statementForUpdate.execute(sql);
						statementForUpdate.close();
						connetion.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println(numberOfInsertions);
				st.close();
				con.close();
	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	catch(Exception e)
	{
		e.printStackTrace();
	}

	}


	public static int getRowCountOfProductLinkToBeProcessed() {
		Connection con = null;
		int count=0;
		try {
			Class.forName(CONSTANTS.DRIVER_CLASS);
			con=DriverManager.getConnection(CONSTANTS.CONNECTION_URL, CONSTANTS.DB_USERNAME, CONSTANTS.DB_PASSWORD);
			Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM "+CONSTANTS.DB_NAME+".product_links");
			rs.next();
			count = rs.getInt("count(*)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/*
	 * Generates a string separated by delimiter "||"
	 */
	public static String getImagesURL(WebDriver driver)
	{
		String imagesURL = "";
		int flag =0;
		List<String> img = new ArrayList<String>();
		
		String dotsXpth = "//div[@class='jsx-64595424 indicator']/div";
		List<WebElement> dotsList = driver.findElements(By.xpath(dotsXpth));
		if(dotsList.size()>0) //if dots are present
		{
			for(WebElement e:dotsList)
			{
				e.click();
				String imagesXpath = "//div[@class='jsx-64595424 carousel']/div/img";
				List<WebElement> urlList = driver.findElements(By.xpath(imagesXpath));
				for(WebElement element: urlList)
				{
					String url = element.getAttribute("src");
					img.add(url);
					imagesURL = imagesURL+"||"+url;
					
				}
			}
		}
		List<String> imgUrlLink = new ArrayList<String>();
		
		//below process if dots are absent
		if(flag==0)
		{
			String multipleImagesXpath = "//div[@class='jsx-64595424 carousel']/div/img";
			
			List<WebElement> multipleImagesXpathElements = driver.findElements(By.xpath(multipleImagesXpath));
			
			if(multipleImagesXpathElements.size()>0)
			{
				
				for(WebElement e:multipleImagesXpathElements)
				{
					String url = e.getAttribute("src");
					imgUrlLink.add(url);
					imagesURL=imagesURL+"||"+url;
				}
				flag=1;
			}
		}
		//below is the process for only one image but there is no thumbnail
		if(flag==0)
		{
			String singleImageShowingXpath = "//div[@class='jsx-2134917503 headline-wrapper fa--image-gallery-item__desktop']/div";
			if(driver.findElements(By.xpath(singleImageShowingXpath)).size()>0)
			{
				List<WebElement> singleImageShowingXpathsListWithoutThumbnails = driver.findElements(By.xpath(singleImageShowingXpath));
				for(WebElement e : singleImageShowingXpathsListWithoutThumbnails)
				{
					String url = e.getAttribute("src");
					imgUrlLink.add(url);
					imagesURL=imagesURL+"||"+url;
				}
				flag=1;
			}
		}
		//System.out.println(img.size());
		//System.out.println(imagesURL);
		
		return imagesURL;
	}
	
	
	public static Map<Integer, Integer> getReviews(WebDriver driver)
	{

		Map<Integer, Integer> starReviewCount = new HashMap<>();
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='bv-inline-histogram-ratings-star-container bv-flex-container']"));
		for(WebElement element:list)
		{
			//String stars = element.findElement(By.xpath("div[1]"));
			WebElement e1 = element.findElement(By.xpath("div[1]"));
			String starText = e1.getAttribute("data-bv-histogram-rating-value");
			int star = Integer.parseInt(starText);
			WebElement e2 = element.findElement(By.xpath("div[3]/span"));
			String reviews = e2.getText().trim();
			int reviewCount = Integer.parseInt(reviews);
			starReviewCount.put(star, reviewCount);
		}
		return starReviewCount;
	}
	
	public static String getProductInformation(WebDriver driver)
	{
		try{
			//driver.findElement(By.xpath("//*[@id='__next']/div/section/div[1]/div[2]/div/button")).click();
			//driver.navigate().to(url);
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			//String str= driver.findElement(By.xpath("//*[@id='productInfoContainer']/div[2]/section/div[2]/div")).getText();
			String str= driver.findElement(By.xpath("//*[@id='productInfoContainer']/div[2]/section/div[2]/div")).getAttribute("innerHTML");
			
			//System.out.println("Before removing HTML Tags: " + str);
		    str = str.replaceAll("\\<.*?\\>", "\n");
		    str = str.replaceAll("^[\n]* *[\n]*", "");
		    str = str.replaceAll("('\\);)", "");
		    str = str.replaceAll("\\@import url\\('", "");
		    System.out.println("After removing HTML Tags: " + str);
		    return str;
	}
	
	
	public static void fetchProductDetails(WebDriver driver, int totalLinksInproduct_linkTable) {
		Connection con = null;
		try{
 			Class.forName(CONSTANTS.DRIVER_CLASS);
			con=DriverManager.getConnection(CONSTANTS.CONNECTION_URL, CONSTANTS.DB_USERNAME, CONSTANTS.DB_PASSWORD);
			Statement st;
			st = con.createStatement();
			String sql = "SELECT product_link FROM "+CONSTANTS.DB_NAME+".product_links WHERE scanned=0 LIMIT 1";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			String product_link = rs.getString(1);
			driver.navigate().to(product_link);
			
			try {
				Utils.clickElement(driver, "//*[@id='showfb-modal-info-estado-p']/div/a/div/img");
				driver.navigate().to(product_link);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String brandName="NA";
			String product_name="NA";
			String variant_id="NA";
			String pod_badges="NA";
			String primary_high_price="NA";
			String primary_price="NA";
			String tertiary_price="NA";
			String cmrpoint="NA";
			String warranty="NA";
			String additional_services="NA";
			String characteristics_features="NA";
			String delivery_type="NA";
			String helpline="NA";
			String return_policy="NA";
			String specifications="NA";
			String product_information="NA";
			String images = "NA";
			String color = "NA";
			
			float rating=0;
			int reviews_count=0;
			int reviews_with_rate_5=0;
			int reviews_with_rate_4=0;
			int reviews_with_rate_3=0;
			int reviews_with_rate_2=0;
			int reviews_with_rate_1=0;
			int scanned;
			String updated_date;
			String created_at;
			
			String brandNameXpath ="//*[@id='__next']/div/section/div[1]/div[1]/div[2]/section[2]/div[1]/div[1]/div[1]/a";
			String product_nameXpath ="//*[@id='__next']/div/section/div[1]/div[1]/div[2]/section[2]/div[1]/div[2]/h1/div";
			String variant_idXpath ="//span[@class='jsx-3408573263']";
			String pod_badgesXpath ="//div[@class='jsx-1231170568 pod-badges pod-badges-PDP']/span";
			String primary_high_priceXpath ="//div/span[@class='copy13 primary high jsx-185326735 normal   ']";
			String primary_priceXpath ="//div/span[@class='copy1 primary  jsx-185326735 normal   ']";
			String tertiary_priceXpath ="//div/span[@class='copy5 tertiary  jsx-185326735 normal   ']";
			String cmrpointXpath ="//*[@id='cmr-points']";
			//String warrantyXpath ="//span[@class='jsx-2855074637 info']";
			String additional_servicesXpath ="";
			//String characteristics_featuresXpath ="";
			String delivery_typeXpath ="//div[contains(@class,'availability')][1]/div[2]/div[2]/div/span";
			String helplineXpath ="//div/span[@class='jsx-2350626903 telephone-link-number']";
			String return_policyXpath ="//span[@class='jsx-2855074637 info']";
			//String specificationsXpath ="";
			//String product_informationXpath ="";
			String ratingXpath = "//*[@id='ratings-summary']/div[2]";
			String reviews_countXpath = "//a[@class='bv-rating-label bv-text-link bv-focusable']";
			String colorXpath = "//div[contains(@class, 'product-specifications')]/div/div[contains(@class, 'color-swatch-container')]/span[2]";
			
			try{
				clickElement(driver, "//button[@class='jsx-2462791491 swatchButton swatchButton-collapseButton']");
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			try{
				brandName= getTextFromXpath(driver, brandNameXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				product_name = getTextFromXpath(driver, product_nameXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				Thread.sleep(500);
				//variant_idXpath = "//span[@class='jsx-3408573263']";
				driver.findElement(By.xpath(variant_idXpath));
				variant_id = getTextFromXpath(driver, variant_idXpath).split(": ")[1].trim();
				System.out.println(variant_id);
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				pod_badges = getTextFromXpath(driver, pod_badgesXpath).trim();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				primary_high_price = getTextFromXpath(driver, primary_high_priceXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				primary_price = getTextFromXpath(driver, primary_priceXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				tertiary_price = getTextFromXpath(driver, tertiary_priceXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				cmrpoint = getTextFromXpath(driver, cmrpointXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}

			try{
				additional_services = getTextFromXpath(driver, additional_servicesXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}

			try{
				String headingXpath = "//div[@class='jsx-3624412160 specifications-list']/ul/li/strong";
				List<WebElement> headings = driver.findElements(By.xpath(headingXpath));
				String completepairXpath = "//div[@class='jsx-3624412160 specifications-list']/ul/li";
				List<WebElement> completePairWebElements = driver.findElements(By.xpath(completepairXpath));
				String finalString = "";
				for(WebElement element : completePairWebElements)
				{
					String text = element.getText().trim();
					String key = text.split(":")[0].trim();
					String value = text.split(":")[1].trim();
					String pair = key + CONSTANTS.DELEMITER_ARROW +value+CONSTANTS.DELEMITER;
					
					finalString = finalString+pair;
				}
				System.out.println(finalString);
				characteristics_features = finalString;
			}catch (Exception e) {
				e.printStackTrace();
			}

			try{
				delivery_type = getTextFromXpath(driver, delivery_typeXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}

			try{
				helpline = getTextFromXpath(driver, helplineXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}

			try{
				return_policy = getTextFromXpath(driver, return_policyXpath);
			}catch (Exception e) {
				e.printStackTrace();
			}

			try{
				//specifications = getTextFromXpath(driver, );
				List<WebElement> specificationRows = driver.findElements(By.xpath("//tbody[@class='jsx-428502957']/tr"));
				StringBuilder sb = new StringBuilder();
				for(WebElement row: specificationRows)
				{
					String propertyName = row.findElement(By.xpath("td[1]")).getText().trim();
					String propertyValue = row.findElement(By.xpath("td[2]")).getText().trim();
					String pair = propertyName+CONSTANTS.DELEMITER_ARROW+propertyValue;
					sb.append(pair);
					sb.append("||");
					if(propertyName.contains("Garantía del proveedor"))
					{
						warranty = propertyValue;
					}
				}
				specifications = sb.toString();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			try{
				rating = Float.parseFloat(driver.findElement(By.xpath("//span[@class='bv-rating']/span")).getText());
			}catch (Exception e) {
				System.out.println("Rating absent or it is an error");
			}
			try{
				String reviewCountText = driver.findElement(By.xpath(reviews_countXpath)).getText().split("comentarios")[0].trim();
				reviews_count = Integer.parseInt(reviewCountText);
			}catch (Exception e) {
				System.out.println("Review Count not available or it is an error");
			}
			
			Map<Integer, Integer> reviewCounts = getReviews(driver);
			try{
				reviews_with_rate_1 = reviewCounts.get(1);
				reviews_with_rate_2 = reviewCounts.get(2);
				reviews_with_rate_3 = reviewCounts.get(3);
				reviews_with_rate_4 = reviewCounts.get(4);
				reviews_with_rate_5 = reviewCounts.get(5);
				reviews_count = reviews_with_rate_1+reviews_with_rate_2+reviews_with_rate_3+reviews_with_rate_4+reviews_with_rate_5;
			}catch (Exception e) {
				System.out.println("Exception in review count or review count is absent");
				
			}
			try{
				clickElement(driver, "//*[@id='acc-alert-close']");
			}
			catch (Exception e) {
				System.out.println("Popup not found");
			}
			try{
				images = getImagesURL(driver);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			try{
				product_information = getProductInformation(driver);
				product_information = product_information.replaceAll("'", "''");
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				color = driver.findElement(By.xpath(colorXpath)).getText().trim();
			}catch (Exception e) {
				e.printStackTrace();
			}

			String sqlQuery = "UPDATE "+CONSTANTS.DB_NAME+".product_links "+
					" SET brand_name='"+brandName+"',"+
					"product_name ='"+product_name+"',"+
					"variant_id='"+variant_id+"',"+
					"pod_badges='"+pod_badges+"',"+
					"primary_high_price='"+primary_high_price+"',"+
					"primary_price='"+primary_price+"',"+
					"tertiary_price='"+tertiary_price+"',"+
					"cmrpoint='"+cmrpoint+"',"+
					"warranty='"+warranty+"',"+
					"additional_services='"+additional_services+"',"+
					"characteristics_features='"+characteristics_features+"', "+
					"delivery_type='"+delivery_type+"', "+
					"helpline='"+helpline+"', "+
					"return_policy='"+return_policy+"', "+
					"specifications='"+specifications+"', "+
					"product_information='"+product_information+"', "+
					"rating="+rating+", "+
					"reviews_count="+reviews_count+", "+
					"reviews_with_rate_5="+reviews_with_rate_5+", "+
					"reviews_with_rate_4="+reviews_with_rate_4+", "+
					"reviews_with_rate_3="+reviews_with_rate_3+", "+
					"reviews_with_rate_2="+reviews_with_rate_2+", "+
					"reviews_with_rate_1="+reviews_with_rate_1+","+
					"scanned="+1+", "+
					"updated_date='"+java.time.LocalDateTime.now()+"', "+
					"images='"+images+"', "+
					"color='"+color+"', "+
					"created_at='"+java.time.LocalDateTime.now()+"' "+
					"WHERE product_link='"+product_link+"';";
			System.out.println(sqlQuery);
			
			Connection connection = null;
			try {
				Class.forName(CONSTANTS.DRIVER_CLASS);
				connection=DriverManager.getConnection(CONSTANTS.CONNECTION_URL, CONSTANTS.DB_USERNAME, CONSTANTS.DB_PASSWORD);
				Statement statement;
				statement = connection.createStatement();
				int updatedRows = statement.executeUpdate(sqlQuery);
				System.out.println(updatedRows);
			}catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(sqlQuery);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
