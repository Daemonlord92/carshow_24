package com.matthewblit.carShow.service;

import com.matthewblit.carShow.entity.UserCredentials;

import java.util.List;
import java.util.Optional;

public interface UserCredentialsService {
    void createUser(UserCredentials userCredentials);
    void deleteUser(Long id);
    List<UserCredentials> getAllUsers();
    UserCredentials getUserById(Long id);

    Optional<UserCredentials> getUserByEmail(String email);
}
