package org.example.scheduleservice.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
        jwtTokenProvider = new JwtTokenProvider(publicKey);
    }

    private String generateTestToken(String username, String role, Long doctorId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("doctorId", doctorId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        String token = generateTestToken("dr_house", "DOCTOR", 100L);
        assertEquals("dr_house", jwtTokenProvider.extractUsername(token));
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {
        String token = generateTestToken("dr_house", "DOCTOR", 100L);
        assertEquals("DOCTOR", jwtTokenProvider.extractRole(token));
    }

    @Test
    void extractDoctorId_shouldReturnCorrectDoctorId() {
        String token = generateTestToken("dr_house", "DOCTOR", 100L);
        assertEquals(100L, jwtTokenProvider.extractDoctorId(token));
    }

    @Test
    void validateToken_shouldReturnTrueForValidToken() {
        String token = generateTestToken("dr_house", "DOCTOR", 100L);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_shouldReturnFalseForExpiredToken() {
        String token = Jwts.builder()
                .setSubject("dr_house")
                .claim("role", "DOCTOR")
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200000))
                .setExpiration(new Date(System.currentTimeMillis() - 3600000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_shouldReturnFalseForMalformedToken() {
        assertFalse(jwtTokenProvider.validateToken("invalid.token.string"));
    }
}