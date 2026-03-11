package com.mohamed.securecaptia.repository;

import com.mohamed.securecaptia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,UUID> {

    // CRUD operations


}
