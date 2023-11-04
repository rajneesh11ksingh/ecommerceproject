package com.electronic.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronic.store.entities.Category;

public interface CategoryRepo extends JpaRepository<Category ,String> {
 
}
