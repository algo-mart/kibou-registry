package com.algomart.kibouregistry.controller;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.models.request.UserRequest;
import com.algomart.kibouregistry.models.response.UserResponse;
import com.algomart.kibouregistry.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> addUsers(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.addUser(userRequest),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam(defaultValue = "10") int pageSize,
                                                                 @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "INTERN") Category category) {

        Page<UserResponse> participants = userService.getAllUsers(pageSize,pageNumber,category);
        return new ResponseEntity<>(participants,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateEvents (@PathVariable Long id, @Valid @RequestBody UserRequest
            userRequest){
        return new ResponseEntity<>(userService.updateUser(id, userRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvents(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User: " + id +  " deleted successfully");
    }

}