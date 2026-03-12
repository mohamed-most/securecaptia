package com.mohamed.securecaptia.repository;

import com.mohamed.securecaptia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {

    // CRUD operations
    Optional<User> findByEmail(String email );

}
