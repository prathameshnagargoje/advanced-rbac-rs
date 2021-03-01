package com.resource.stock.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resource.stock.model.Stock;
import com.resource.stock.model.User;
import com.resource.stock.repo.UserRepoImpl;

@Service
public class UserDao {

  @Autowired private UserRepoImpl repository;

  public boolean save(User user) {
    if (repository.save(user)) return true;
    return false;
  }

  public Optional<User> getByEmail(String email) {
    return repository.findByEmail(email);
  }

  public Optional<User> getById(int id) {
    return repository.findById(id);
  }

  public Iterable<User> getAll() {
    return repository.findAll();
  }

  public boolean removeStock(int user_id, int stock_id) {
    Optional<User> opuser = repository.findById(user_id);
    if (opuser.isPresent()) {
      User user = opuser.get();
      user.removeStock(stock_id);
      return true;
    }
    return false;
  }

  public boolean addStock(int id, Stock stock) {
    Optional<User> opuser = repository.findById(id);
    if (opuser.isPresent()) {
      User user = opuser.get();
      user.addStock(stock);
      repository.deleteById(id);
      repository.save(user);
      return true;
    }
    return false;
  }
}
