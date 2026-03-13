package com.IslanaCarvalho.UserHub.infrastructure.repository;

import com.IslanaCarvalho.UserHub.infrastructure.entitys.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
