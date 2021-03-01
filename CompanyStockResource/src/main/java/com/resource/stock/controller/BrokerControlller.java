package com.resource.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resource.stock.dao.UserDao;
import com.resource.stock.model.Stock;
import com.resource.stock.model.User;

@RestController
@RequestMapping("/broker")
public class BrokerControlller {

  @Autowired private UserDao userService;
  private int stock_cc = 0;

  @GetMapping
  public Iterable<User> getAllUsers() {
    return userService.getAll();
  }

  @PostMapping
  public ResponseEntity<Object> addStock(
      @RequestParam("user-id") int user_id, @RequestParam("stock") float stock) {
    if (userService.addStock(user_id, new Stock(stock_cc++, stock)))
      return new ResponseEntity<Object>(HttpStatus.OK);
    return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping
  public ResponseEntity<Object> removeStock(
      @RequestParam("user-id") int user_id, @RequestParam("stock") int stock_id) {
    if (userService.removeStock(user_id, stock_id))
      return new ResponseEntity<Object>(HttpStatus.OK);
    return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
  }
}
