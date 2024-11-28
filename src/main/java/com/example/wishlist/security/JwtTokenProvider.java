package com.example.wishlist.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Компонент для работы с JWT токенами
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        // Ensure the key is at least 256 bits (32 bytes) long by padding if necessary
        if (keyBytes.length < 32) {
            byte[] newKey = new byte[32];
            System.arraycopy(keyBytes, 0, newKey, 0, Math.min(keyBytes.length, 32));
            // Pad remaining bytes with a secure pattern
            for (int i = keyBytes.length; i < 32; i++) {
                newKey[i] = (byte) ((i * 7 + 13) % 256);
            }
            keyBytes = newKey;
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Генерация JWT токена
     * @param authentication данные аутентификации
     * @return JWT токен
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Получение имени пользователя из токена
     * @param token JWT токен
     * @return имя пользователя
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Валидация JWT токена
     * @param authToken JWT токен
     * @return true, если токен валиден
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
}
