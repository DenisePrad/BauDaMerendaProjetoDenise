package com.bau.merenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bau.merenda.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
