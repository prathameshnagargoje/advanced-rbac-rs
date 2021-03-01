package com.resource.stock.controller;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.resource.stock.dao.UserDao;
import com.resource.stock.model.Stock;
import com.resource.stock.model.User;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private UserDao service;
  private int user_cc = 0;

  @GetMapping
  public ResponseEntity<Object> getStock(@AuthenticationPrincipal Jwt jwt) {
    String email = jwt.getClaimAsString("email");
    Optional<User> user = service.getByEmail(email);
    if (user.isPresent()) return new ResponseEntity<Object>(user.get(), HttpStatus.OK);
    else {
      User newuser = new User(user_cc++, email, new ArrayList<Stock>());
      service.save(newuser);
      return new ResponseEntity<Object>(newuser, HttpStatus.CREATED);
    }
  }
}
