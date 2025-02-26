package com.algomart.kibouregistry.repository;
import com.algomart.kibouregistry.entity.User;
import com.algomart.kibouregistry.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    User findByContactInfoEmail(String email);

    List<User> findByCategory(Category category);
}
