package com.hackerrank.selenium;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hackerrank.selenium.db.Card;

public class PurchaseAutomation {

  public static String makePurchase(String itemsURL, String itemName, WebDriver driver) {
  
     // Browse items page
     driver.get(itemsURL);
    
     // Find all items on the page
     List<WebElement> items = driver.findElements(By.tagName("P"));
 
     WebElement item = null;
     for (WebElement element : items) {
       if (element.getText().equals(itemName)) {
         System.out.println("Item found: " + itemName);
         item = element;
         break;
       }
      }

      if (item == null) {
        System.out.println("Item not found: " + itemName);
        return null;
      }
  
      // Select item itemName
      item.click();
  
      // Add it to cart
      driver.findElement(By.xpath("/html/body/ul/li[12]/div/div/form/h4/input")).click();
  
      // Go to the cart
      driver.findElement(By.xpath("/html/body/div[1]/div/div/div/a/span[2]")).click();
  
      // Make a purchase with a click on purchase button
      driver.findElement(By.xpath("/html/body/div[1]/div/div/a[2]")).click();
  
      // Return the server response as a string
     
      return driver.getPageSource();
  }
  public static String makePayment(String paymentURL, Card card, WebDriver driver) {
    
    //Fill in the card details
    //Make payment by clicking Pay button
    //return the server response as a string

    driver.get(paymentURL);
    driver.findElement(By.xpath("/html/body/div[1]/div/div/form/input[1]")).sendKeys(card.toString());
    driver.findElement(By.xpath("/html/body/div[1]/div/div/form/input[2]")).click();
      return driver.getPageSource();
  }

  // do not modify
  public static String get(String url, WebDriver driver) {
    driver.get(url);
    
    return driver.getPageSource();
  }
}
