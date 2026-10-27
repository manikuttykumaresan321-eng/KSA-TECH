package com.schemeai;
import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/saved")
public class SavedController {
 private final UserRepository users;private final SavedSchemeRepository saved;private final SchemeRepository schemes;
 public SavedController(UserRepository users,SavedSchemeRepository saved,SchemeRepository schemes){this.users=users;this.saved=saved;this.schemes=schemes;}
 private AppUser user(){String email=SecurityContextHolder.getContext().getAuthentication().getName();return users.findByEmail(email).orElseThrow(()->new NotFoundException("User not found."));}
 @GetMapping public List<Scheme> list(){AppUser u=user();return saved.findByUserId(u.id).stream().map(x->schemes.findById(x.schemeId).orElse(null)).filter(Objects::nonNull).toList();}
 @PostMapping("/{schemeId}") public Map<String,String> add(@PathVariable Long schemeId){AppUser u=user();if(!schemes.existsById(schemeId))throw new NotFoundException("Scheme not found.");if(saved.findByUserIdAndSchemeId(u.id,schemeId).isEmpty())saved.save(new SavedScheme(u.id,schemeId));return Map.of("message","Scheme saved.");}
 @DeleteMapping("/{schemeId}") public Map<String,String> remove(@PathVariable Long schemeId){saved.deleteByUserIdAndSchemeId(user().id,schemeId);return Map.of("message","Saved scheme removed.");}
}