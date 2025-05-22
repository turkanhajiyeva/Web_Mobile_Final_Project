package com.ilpalazzo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ilpalazzo.model.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository <MenuItem, Integer> {
    Optional<MenuItem> findByName(String name);
    List<MenuItem> findByCategory(String category);
    Optional<MenuItem> findById(int id);
    boolean existsById(int id);
    void deleteById(int id);
    MenuItem save(MenuItem item);
}
