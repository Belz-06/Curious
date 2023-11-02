package com.hackerrank.selenium.db;

public class Card {
  public String number;
  public String status;

  public Card(String number, String status) {
    this.number = number;
    this.status = status;
  }

  @Override
  public String toString() {
    return "Product{" + "number='" + number + '\'' + '\'' + ", status='" + status + '\'' + '}';
  }
}
