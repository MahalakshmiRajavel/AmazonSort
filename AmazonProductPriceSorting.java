package ContloPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;


import java.sql.Time;
import java.util.*;

public class AmazonProductPriceSorting {

    public static void main(String[] args) {
    	System.setProperty("webdriver.chrome.driver","C:\\webdriver\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open Amazon.in
        driver.get("https://www.amazon.in/");

        // Search for "lg soundbar"
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("lg soundbar");
        searchBox.submit();

        // Read product names and associated main price on 1st search result page
        int priceInt=0;
        String price=null;
        Map<String, Integer> productAndPriceMap = new HashMap<>();
        List<WebElement> productNames = driver.findElements(By.cssSelector("[class='a-size-medium a-color-base a-text-normal']"));
        for(int i=0;i<productNames.size();i++) { 
        	WebElement parentDiv = driver.findElement(RelativeLocator.with(By.cssSelector("[class='puisg-col-inner']")).near(productNames.get(i)));
    		try {
        		price=parentDiv.findElement(By.cssSelector("[class='a-price-whole']")).getText();
        		priceInt= Integer.parseInt(price.replaceAll("[^0-9]", ""));
        	}
        	catch(Exception NoSuchElementException) {
        		priceInt=0;
        	}
        	productAndPriceMap.put(productNames.get(i).getText(),priceInt );  
        }
            
        // Sort the products by price
        List<Map.Entry<String, Integer>> sortedProductPrices = new ArrayList<>(productAndPriceMap.entrySet());
        sortedProductPrices.sort(Map.Entry.comparingByValue());
        
        // Print the sorted products one by one
        for (Map.Entry<String,Integer> entry : sortedProductPrices) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }

        // Close the browser
        driver.quit();
    }
}
