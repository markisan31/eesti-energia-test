package com.eestienergia.testassignment.repository;

import com.eestienergia.testassignment.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findAllByOrderByName();
    Optional<Item> findByName(String name);
}
