package com.algomart.kibouregistry.services.impl;
import com.algomart.kibouregistry.entity.User;
import com.algomart.kibouregistry.enums.Category;
import com.algomart.kibouregistry.enums.SearchOperation;
import com.algomart.kibouregistry.exceptions.*;
import com.algomart.kibouregistry.models.SearchCriteria;
import com.algomart.kibouregistry.models.request.UserRequest;
import com.algomart.kibouregistry.models.response.UserResponse;
import com.algomart.kibouregistry.repository.EventsRepo;
import com.algomart.kibouregistry.repository.UserRepo;
import com.algomart.kibouregistry.services.UserService;
import com.algomart.kibouregistry.util.GenericSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final EventsRepo eventsRepo;
    @Override
    public UserResponse addUser(UserRequest participant) {
        String email = participant.getContactInfo().getEmail();
        User existingParticipant = userRepo.findByContactInfoEmail(email);

        if (existingParticipant != null) {
            throw new EmailAlreadyExistsException();
        }

        User newParticipant = new User();
        newParticipant.setName(participant.getName());
        newParticipant.setCategory(participant.getCategory());
        newParticipant.setContactInfo(participant.getContactInfo());
        User savedParticipant = userRepo.save(newParticipant);
        return new UserResponse(savedParticipant);
    }
    @Override
    public Page<UserResponse> getAllUsers(int pageSize, int pageNumber, Category category) {
        GenericSpecification<User> spec = new GenericSpecification<>();
        if (category != null) {
            spec.add(new SearchCriteria("category", category, SearchOperation.EQUAL));
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepo.findAll(spec, pageable).map(UserResponse::new);
    }
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepo.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(user);
    }
    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User participant1 = userRepo.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
        participant1.setName(userRequest.getName());
        participant1.setCategory(userRequest.getCategory());
        participant1.setContactInfo(userRequest.getContactInfo());

        var newParticipant = userRepo.save(participant1);
        return new UserResponse(newParticipant);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException(id));
        userRepo.deleteById(id);
    }
}