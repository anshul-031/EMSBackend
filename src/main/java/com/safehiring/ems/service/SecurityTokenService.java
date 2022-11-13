package com.safehiring.ems.service;

import com.safehiring.ems.exceptio.InvalidTokenException;
import com.safehiring.ems.jpa.data.SecureToken;

public interface SecurityTokenService {

    SecureToken createSecureToken();

    void saveSecureToken(SecureToken token);

    SecureToken findByToken(String token) throws InvalidTokenException;

    void removeToken(SecureToken token);

    void removeTokenByToken(String token);

}
