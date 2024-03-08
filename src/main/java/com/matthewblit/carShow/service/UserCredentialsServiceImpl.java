package com.matthewblit.carShow.service;

import com.matthewblit.carShow.entity.UserCredentials;
import com.matthewblit.carShow.repository.UserCredentialsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{
    private final UserCredentialsRepository userCredentialsRepository;

    public UserCredentialsServiceImpl(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @Override
    public void createUser(UserCredentials userCredentials) {
        userCredentialsRepository.save(userCredentials);
    }

    @Override
    public void deleteUser(Long id) {
        userCredentialsRepository.deleteById(id);
    }

    @Override
    public List<UserCredentials> getAllUsers() {
        return (List<UserCredentials>) userCredentialsRepository.findAll();
    }

    @Override
    public UserCredentials getUserById(Long id) {
        return userCredentialsRepository.findById(id).orElseThrow();
    }

    @Override
    public Optional<UserCredentials> getUserByEmail(String email) {
        return userCredentialsRepository.findByEmail(email);
    }
}
