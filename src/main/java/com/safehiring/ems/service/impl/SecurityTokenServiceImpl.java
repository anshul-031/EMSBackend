package com.safehiring.ems.service.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.safehiring.ems.exception.InvalidTokenException;
import com.safehiring.ems.jpa.data.SecureToken;
import com.safehiring.ems.jpa.repository.SecureTokenRepository;
import com.safehiring.ems.service.SecurityTokenService;
import lombok.Data;


@Service
@Data
public class SecurityTokenServiceImpl implements SecurityTokenService {


    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom();
    private static final Charset US_ASCII = StandardCharsets.US_ASCII;
    private final SecureTokenRepository secureTokenRepository;
    @Value("${ems.secure.token.validity}")
    private int tokenValidityInSeconds;

    @Override
    public SecureToken createSecureToken() {
        final String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()));
        final SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveSecureToken(secureToken);
        return secureToken;
    }

    @Override
    public void saveSecureToken(final SecureToken token) {
        this.secureTokenRepository.save(token);
    }

    @Override
    public SecureToken findByToken(final String token) throws InvalidTokenException {
        return this.secureTokenRepository.findByToken(token).orElseThrow(() -> new InvalidTokenException(" Invalid token"));
    }

    @Override
    public void removeTokenByToken(final String token) {
        this.secureTokenRepository.removeByToken(token);
    }

    @Override
    public void removeToken(final SecureToken token) {
        this.secureTokenRepository.delete(token);
    }
}
