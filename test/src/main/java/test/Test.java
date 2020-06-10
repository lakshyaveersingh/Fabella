package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Test {

	public static void main(String kj[])
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		WebDriver driver = new  ChromeDriver(chromeOptions);
		
		String url = "https://www.falabella.com.co/falabella-co/product/4592077/Celular-Huawei-Nova-5T-128GB/4592077";
		url = "https://www.falabella.com.co/falabella-co/product/4975270/Celular-xiaomi-redmi-note-8-64-gb-azul/4975270";
		//driver.navigate().to("https://www.falabella.com.co/falabella-co/category/cat1660941/Celulares-y-Smartphones");
		driver.navigate().to(url);
		try{
			driver.findElement(By.xpath("//*[@id='showfb-modal-info-estado-p']/div/a/div/img")).click();
		}catch (Exception e) {
			e.printStackTrace();
		}
		driver.navigate().to(url);
		
		try {
			driver.findElement(By.xpath("//*[@id='acc-alert-accept']"));
		} catch (Exception e) {
			//e.printStackTrace();
		}
		getImagesURL(driver);
		
	}
	
	
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
	
}
