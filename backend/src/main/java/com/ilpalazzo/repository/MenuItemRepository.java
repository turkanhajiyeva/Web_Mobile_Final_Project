package com.ilpalazzo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ilpalazzo.model.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByName(String name);
    List<MenuItem> findByCategory(String category);
    Optional<MenuItem> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    MenuItem save(MenuItem item);
}
