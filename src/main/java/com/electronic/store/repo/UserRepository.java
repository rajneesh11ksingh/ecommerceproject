package com.electronic.store.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.store.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User,String> {
	
     Optional<User> findByEmail(String email);
     
     Optional<User> findByEmailAndPassword(String email,String Password);
     
     List<User> findByNameContaining(String keyword);
     
}
