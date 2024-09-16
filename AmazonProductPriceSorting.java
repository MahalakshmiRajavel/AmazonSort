package ContloPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        List<WebElement> parentElement = driver.findElements(By.cssSelector("[class='a-section a-spacing-small a-spacing-top-small']"));
        System.out.println(parentElement.size());
        int priceInt=0;
        String price=null;
        Map<String, Integer> productAndPriceMap = new HashMap<>();
        
        for (int i=0;i<parentElement.size();i++) {
            try {
            	price = parentElement.get(i).findElement(By.cssSelector("[class='a-price-whole']")).getText();
            	priceInt = Integer.parseInt(price.replaceAll("[^0-9]", ""));
            } 
            catch (Exception NoSuchElementException) {	
            	priceInt = 0;
            }
            String product=parentElement.get(i).findElement(By.cssSelector("[class='a-size-medium a-color-base a-text-normal']")).getText();
            System.out.println(product);
            //productAndPriceMap.put(product,priceInt );     
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
