package com.schemeai;
import org.springframework.web.bind.annotation.*;import jakarta.validation.Valid;import java.util.Map;
@RestController @RequestMapping("/api/admin")
public class AdminController {
 private final SchemeRepository repo;public AdminController(SchemeRepository repo){this.repo=repo;}
 public record SchemeInput(String name,String category,String description,int minAge,int maxAge,double maxAnnualIncome,String occupation,String state,String benefit,String officialUrl){}
 @PostMapping("/schemes") public Scheme create(@RequestBody SchemeInput r){
  if(r.name()==null||r.name().isBlank()||r.category()==null||r.category().isBlank())throw new IllegalArgumentException("Scheme name and category are required.");
  if(r.minAge()<0||r.maxAge()<r.minAge()||r.maxAnnualIncome()<0)throw new IllegalArgumentException("Invalid eligibility limits.");
  Scheme s=new Scheme();s.name=r.name().trim();s.category=r.category().trim();s.description=r.description();s.minAge=r.minAge();s.maxAge=r.maxAge();s.maxAnnualIncome=r.maxAnnualIncome();s.occupation=r.occupation()==null||r.occupation().isBlank()?"any":r.occupation().trim();s.state=r.state()==null||r.state().isBlank()?"All":r.state().trim();s.benefit=r.benefit()==null?"Check official portal":r.benefit();s.officialUrl=r.officialUrl();return repo.save(s);
 }
 @DeleteMapping("/schemes/{id}") public Map<String,String> delete(@PathVariable Long id){Scheme s=repo.findById(id).orElseThrow(()->new NotFoundException("Scheme not found."));s.active=false;repo.save(s);return Map.of("message","Scheme deactivated.");}
}