package com.resource.stock.repo;

import java.util.List;
import java.util.Optional;

import com.resource.stock.model.User;

public interface UserRepo {
  boolean save(User user);

  boolean deleteById(int id);

  boolean update(User user);

  List<User> findAll();

  Optional<User> findByEmail(String email);

  Optional<User> findById(int id);
}
