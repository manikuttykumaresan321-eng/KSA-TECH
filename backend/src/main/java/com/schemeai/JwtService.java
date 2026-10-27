package com.schemeai;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class JwtService {
 private final SecretKey key;
 public JwtService(){String secret=System.getenv().getOrDefault("JWT_SECRET","dev-only-change-this-secret-key-before-deployment-123456789");key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public String createToken(AppUser u){return Jwts.builder().subject(u.email).claim("role",u.role).claim("uid",u.id).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+86400000L)).signWith(key).compact();}
 public io.jsonwebtoken.Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
}