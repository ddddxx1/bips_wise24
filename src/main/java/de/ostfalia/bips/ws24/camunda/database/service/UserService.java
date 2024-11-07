package de.ostfalia.bips.ws24.camunda.database.service;

import de.ostfalia.bips.ws24.camunda.database.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
