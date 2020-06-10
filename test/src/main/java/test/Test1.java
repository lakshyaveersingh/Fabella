package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test1 {

	public static void main(String jk[])
	{
		WebDriver driver = new ChromeDriver();
		String url = "https://www.falabella.com.co/falabella-co/product/prod9730021/Combo-Moto-G8-Plus-64GB-+-Moto-E6-Plus-64GB/sku9070013";
		driver.navigate().to(url);
		try{
		driver.findElement(By.xpath("//*[@id='__next']/div/section/div[1]/div[2]/div/button")).click();
		driver.navigate().to(url);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		//String str= driver.findElement(By.xpath("//*[@id='productInfoContainer']/div[2]/section/div[2]/div")).getText();
		String str= driver.findElement(By.xpath("//*[@id='productInfoContainer']/div[2]/section/div[2]/div")).getAttribute("innerHTML");
		
		//System.out.println("Before removing HTML Tags: " + str);
	    str = str.replaceAll("\\<.*?\\>", "\n");
	    System.out.println("After removing HTML Tags: " + str);
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
		    System.out.println("After removing HTML Tags: " + str);
		    return str;
	}
}
