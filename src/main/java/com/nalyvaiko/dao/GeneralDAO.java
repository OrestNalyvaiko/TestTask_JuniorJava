package com.nalyvaiko.dao;

import java.util.List;
import java.util.Optional;

public interface GeneralDAO<T, K> {

  Optional<T> get(K id);

  void add(T entity);

  void delete(T entity);

  T update(T entity);

  List<T> getAll();
}
