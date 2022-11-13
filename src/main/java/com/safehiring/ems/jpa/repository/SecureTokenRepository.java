package com.safehiring.ems.jpa.repository;

import com.safehiring.ems.jpa.data.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> {
    Optional<SecureToken> findByToken(final String token);

    Long removeByToken(final String token);
}
