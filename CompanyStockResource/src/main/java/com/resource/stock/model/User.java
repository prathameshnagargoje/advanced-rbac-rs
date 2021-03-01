package com.resource.stock.model;

import java.util.List;
import lombok.Data;

@Data
public class User {

  private int id;
  private List<Stock> stock;
  private String email;

  public User(int id, String email, List<Stock> stock) {
    this.id = id;
    this.email = email;
    this.stock = stock;
  }

  public void addStock(Stock s) {
    this.stock.add(s);
  }

  public void removeStock(int id) {
    this.stock.remove(id);
  }
}
