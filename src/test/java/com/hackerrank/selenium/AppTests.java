package com.hackerrank.selenium;

import com.hackerrank.selenium.db.Card;
import com.hackerrank.selenium.db.Database;
import com.hackerrank.selenium.db.Item;
import com.hackerrank.selenium.server.JettyServer;
import com.hackerrank.selenium.server.util.SeleniumUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTests {
  private static JettyServer server = null;
  private static WebDriver driver = null;
  private static List<String> products;
  private static Random random;
  public static final int TEST_PORT = 8080;
  private static final String itemsURL = "http://localhost:" + TEST_PORT;
  private static Item item = Item.empty();

  @BeforeAll
  public static void setup() {
    driver = SeleniumUtil.createDriver();

    server = new JettyServer(TEST_PORT);
    server.start();

    Database.init();

    random = new Random();
    products =
        new ArrayList<>(
            Arrays.asList(
                "Product1",
                "Product2",
                "Product3",
                "Product4",
                "Product5",
                "Product6",
                "Product7",
                "Product8",
                "Product9",
                "Product10",
                "Product11",
                "Product12"));
  }

  @AfterAll
  public static void tearDown() {
    driver.close();
    server.stop();
  }

  @Test
  @Order(1)
  public void testMakePurchase() {
    item = new Item(products.get(random.nextInt(10) + 2), "a.jpg", 22, "available");
    String expected = "available->carted->payment";

    PurchaseAutomation.makePurchase(itemsURL, item.name, driver);

    String actual = PurchaseAutomation.get(itemsURL + "/item", driver);

    System.out.println(" Actual: \n" + actual + "\n Expected: \n" + expected);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  @Order(2)
  public void testMakePayment() {
    Card expectedCard = new Card("CD123", "Valid");

    String expectedItem = "available->carted->payment->paid";
    String actualStatus =
        PurchaseAutomation.makePayment(itemsURL + "/payment.html", expectedCard, driver);
    String actualItem = PurchaseAutomation.get(itemsURL + "/item", driver);

    System.out.println(" Actual Item: \n" + actualItem + "\n Expected Item: " + expectedItem);
    System.out.println(
        " Actual Status: \n" + actualStatus + "\n Expected Status: " + expectedCard.status);

    Assertions.assertEquals(expectedItem, actualItem);
    Assertions.assertEquals(expectedCard.status, actualStatus);
  }

  @Test
  @Order(3)
  public void testDatabase() {
    Card card = new Card("CD" + new Random().nextInt(1000), "Valid");
    Database.insertCard(card);

    List<Card> cards = Database.fetchCards();

    Assertions.assertTrue(cards.size() == 1);
    Assertions.assertTrue(cards.get(0).toString().equals(card.toString()));
  }
}
