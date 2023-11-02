package com.hackerrank.selenium;

import com.hackerrank.selenium.db.Card;
import org.openqa.selenium.WebDriver;

public class PurchaseAutomation {

  public static void makePurchase(String itemsURL, String itemName, WebDriver driver) {
    // TODO
  }

  public static String makePayment(String paymentURL, Card card, WebDriver driver) {
    // TODO
    return null;
  }

  // do not modify
  public static String get(String url, WebDriver driver) {
    driver.get(url);
    return driver.getPageSource();
  }
}
