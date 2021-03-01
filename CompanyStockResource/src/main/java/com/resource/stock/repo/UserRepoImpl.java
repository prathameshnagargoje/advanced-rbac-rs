package com.resource.stock.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.resource.stock.model.User;

@Repository
public class UserRepoImpl implements UserRepo {

  private Map<Integer, User> repository;

  public UserRepoImpl(Map<Integer, User> repository) {
    this.repository = repository;
  }

  @Override
  public boolean save(User user) {
    if (!repository.containsKey(user.getId())) {
      repository.put(user.getId(), user);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteById(int id) {
    if (repository.containsKey(id)) {
      repository.remove(id);
      return true;
    }
    return false;
  }

  @Override
  public boolean update(User user) {
    if (repository.containsKey(user.getId())) {
      repository.replace(user.getId(), user);
      return true;
    }
    return false;
  }

  @Override
  public List<User> findAll() {
    // TODO Auto-generated method stub
    return repository.values().stream().collect(Collectors.toList());
  }

  @Override
  public Optional<User> findByEmail(String email) {
    // TODO Auto-generated method stub
    return repository
        .values()
        .stream()
        .filter(m -> m.getEmail().equalsIgnoreCase(email))
        .findFirst();
  }

  @Override
  public Optional<User> findById(int id) {
    // TODO Auto-generated method stub
    return Optional.ofNullable(repository.get(id));
  }
}
