package com.example.demo;

// CRUD
// Create Read Update Delete

import com.example.demo.models.Pizza;
import org.springframework.data.repository.CrudRepository;

public interface PizzaAction extends CrudRepository<Pizza,Integer> {
}
