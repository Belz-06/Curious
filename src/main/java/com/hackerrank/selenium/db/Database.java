package com.hackerrank.selenium.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {
  private static final String CREATE_TBL_SQL =
      "CREATE TABLE cards " + "(number TEXT NOT NULL," + " status TEXT NOT NULL)";

  // create connection to sqlite database
  public static Connection getConnection() {
    Connection connection;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:purchase.db");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return connection;
  }

  // clear database
  public static void init() {
    Connection connection = getConnection();
    Statement statement = null;
    try {
      statement = connection.createStatement();
      statement.executeUpdate("DROP TABLE IF EXISTS cards");
      statement.executeUpdate(CREATE_TBL_SQL);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if (statement != null) {
          statement.close();   }
        if (connection != null) {
          connection.close(); }
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
    

    }
  }

  // fetch all the cards from cards table
  public static List<Card> fetchCards() {
        return null;
  }

  // do not modify
  public static void insertCard(Card card) {
    String query = "INSERT INTO cards values('" + card.number + "','" + card.status + "')";
    Connection connection;
    Statement statement = null;
    try {
      connection = getConnection();
      statement = connection.createStatement();
      System.out.println("Executing Query: " + query);
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
      }
    }
  }
}
