package com.boat.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boat.management.model.Boat;

public interface BoatRepository extends JpaRepository<Boat, Long> {

  List<Boat> findByNameContaining(String name);
}
