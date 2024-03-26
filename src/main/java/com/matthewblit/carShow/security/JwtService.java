package com.matthewblit.carShow.security;

import com.matthewblit.carShow.entity.UserCredentials;
import com.matthewblit.carShow.exception.ResourceAccessException;
import com.matthewblit.carShow.service.UserCredentialsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET = "51ce84faf2b1a5960314a924c0836a17f6c3e0307843f00dc6d8b46cfcda1594";
    private final long EXPIRATION = 7200000;
    private final UserCredentialsService userCredentialsService;

    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    public String generateToken(String email) {
        //For Login Purpose
        Optional<UserCredentials> userCredentials = userCredentialsService.getUserByEmail(email);
        if(userCredentials.isEmpty()) throw new ResourceAccessException("User not Found");
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userCredentials.get().getRole());
        return generateToken(claims, userCredentials.get());
    }

    public String generateToken(UserDetails userDetails) {
        UserCredentials userCredentials = userCredentialsService.getUserByEmail(userDetails.getUsername())
                .orElseThrow();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userCredentials.getRole());
        return generateToken(claims, userCredentials);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        logger.info(String.valueOf(System.currentTimeMillis() + EXPIRATION));
        return Jwts
                //Setups the JWT token to send to the front end or client application
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + EXPIRATION)))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey() {
        //Creates a key for use by the JWT Encryption
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
