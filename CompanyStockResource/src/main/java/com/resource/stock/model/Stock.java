package com.resource.stock.model;

import lombok.Data;

@Data
public class Stock {

  private int id;

  private float value;

  public Stock(int id, float value) {
    this.value = value;
    this.id = id;
  }
}
