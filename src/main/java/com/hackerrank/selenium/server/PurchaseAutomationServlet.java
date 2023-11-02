package com.hackerrank.selenium.server;

import com.hackerrank.selenium.db.Item;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;

public class PurchaseAutomationServlet extends HttpServlet {
  private static Item item = Item.empty();
  private static final String ROW =
      "<tr>\n"
          + "\t\t\t\t\t<td><img width=\"100\" src=\"assets/img/$img\" alt=\"\"></td>\n"
          + "\t\t\t\t\t<td>$name</td>\n"
          + "\t\t\t\t\t<td>$price</td>\n"
          + "\t\t\t\t</tr>";

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setHeader("Cache-Control", "private, no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setCharacterEncoding("UTF-8");

    if ("/addToCart".equals(request.getRequestURI())) {
      System.out.println("Server->Processing URL: " + request.getRequestURL());

      item =
          new Item(
              request.getParameter("name"),
              request.getParameter("img"),
              Integer.parseInt(request.getParameter("price")),
              "available");

      response.sendRedirect("/index.html");
    } else {
      response.getWriter().write("Unsupported!");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setHeader("Cache-Control", "private, no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setCharacterEncoding("UTF-8");

    if ("/item".equals(request.getRequestURI())) {
      System.out.println("Server->Processing URL: " + request.getRequestURL());
      response.getWriter().write(item.status);

    } else if ("/cart".equals(request.getRequestURI())) {
      System.out.println("Server->Processing URL: " + request.getRequestURL());
      if (item.status.endsWith("available")) {
        item.status = item.status + "->carted";
      }
      String row =
          ROW.replace("$name", item.name)
              .replace("$img", item.img)
              .replace("$price", "$" + item.price + ".00\n");
      response
          .getWriter()
          .write(
              String.join("\n", Files.readAllLines(java.nio.file.Paths.get("website/cart.html")))
                  .replace("$ROWS", row));
    } else if ("/payment".equals(request.getRequestURI())) {
      System.out.println("Server->Processing URL: " + request.getRequestURL());

      if (item.status.endsWith("carted")) {
        item.status = item.status + "->payment";
        response.sendRedirect("/payment.html");
      }
    } else if ("/pay".equals(request.getRequestURI())) {
      System.out.println("Server->Processing URL: " + request.getRequestURL());

      if (item.status.endsWith("payment")) {
        if (request.getParameter("number").startsWith("CD")) {
          item.status = item.status + "->paid";
          response.getWriter().write("Valid");
        } else {
          response.getWriter().write("Invalid");
        }
      }
    }
  }
}
