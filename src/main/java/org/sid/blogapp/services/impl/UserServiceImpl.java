package org.sid.blogapp.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sid.blogapp.domain.entities.User;
import org.sid.blogapp.repositories.UserRepository;
import org.sid.blogapp.services.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
       return     userRepository.findById(id).orElseThrow(
               ()-> new EntityNotFoundException("User not found with id " + id)
       );
    }
}
