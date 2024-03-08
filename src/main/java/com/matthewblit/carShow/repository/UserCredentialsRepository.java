package com.matthewblit.carShow.repository;

import com.matthewblit.carShow.entity.UserCredentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByEmail(String email);
}
