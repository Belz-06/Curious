package com.hackerrank.selenium.db;

public class Item {
  public String name;
  public String img;
  public Integer price;
  public String status;

  public Item(String name, String img, Integer price, String status) {
    this.name = name;
    this.img = img;
    this.price = price;
    this.status = status;
  }

  public static Item empty() {
    return new Item("N/A", "N/A", 0, "N/A");
  }

  @Override
  public String toString() {
    return name + "|" + img + "|" + price + "|" + status;
  }
}
