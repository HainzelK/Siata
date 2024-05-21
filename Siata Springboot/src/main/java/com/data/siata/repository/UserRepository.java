package com.data.siata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.data.siata.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
}
