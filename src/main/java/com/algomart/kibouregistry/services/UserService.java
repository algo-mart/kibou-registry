package com.algomart.kibouregistry.services;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.models.request.UserRequest;
import com.algomart.kibouregistry.models.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse addUser(UserRequest users);
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserRequest users);
    void deleteUser(Long id);
    Page<UserResponse> getAllUsers(int pageSize, int pageNumber, Category category);
}