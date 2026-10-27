package com.schemeai;
import org.springframework.http.*;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 record AuthRequest(String name,String email,String password){}
 record PublicUser(Long id,String name,String email,String role){}
 record AuthResponse(String token,PublicUser user){}
 @PostMapping("/register") public ResponseEntity<?> register(@RequestBody AuthRequest r){
  if(r.name()==null||r.name().isBlank()||r.email()==null||!r.email().contains("@")||r.password()==null||r.password().length()<8)return ResponseEntity.badRequest().body(Map.of("message","Enter a name, valid email, and password of at least 8 characters."));
  if(users.existsByEmail(r.email().toLowerCase()))return ResponseEntity.status(409).body(Map.of("message","An account with this email already exists."));
  AppUser u=users.save(new AppUser(r.name().trim(),r.email().trim().toLowerCase(),encoder.encode(r.password()),"USER"));return ResponseEntity.ok(response(u));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody AuthRequest r){
  if(r.email()==null||r.password()==null)return ResponseEntity.badRequest().body(Map.of("message","Email and password are required."));
  return users.findByEmail(r.email().trim().toLowerCase()).filter(u->encoder.matches(r.password(),u.passwordHash)).<ResponseEntity<?>>map(u->ResponseEntity.ok(response(u))).orElseGet(()->ResponseEntity.status(401).body(Map.of("message","Invalid email or password.")));
 }
 private AuthResponse response(AppUser u){return new AuthResponse(jwt.createToken(u),new PublicUser(u.id,u.name,u.email,u.role));}
}