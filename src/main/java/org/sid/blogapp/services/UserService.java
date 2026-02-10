package org.sid.blogapp.services;

import org.sid.blogapp.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
}
