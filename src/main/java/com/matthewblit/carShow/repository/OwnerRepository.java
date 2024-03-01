package com.matthewblit.carShow.repository;

import com.matthewblit.carShow.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    //SELECT * FROM owners WHERE name == name;
    Optional<Owner> findByName(String name);
}
