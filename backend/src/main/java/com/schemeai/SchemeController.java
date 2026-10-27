package com.schemeai;
import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/schemes")
public class SchemeController {
 private final SchemeRepository repo;public SchemeController(SchemeRepository repo){this.repo=repo;}
 @GetMapping public List<Scheme> all(){return repo.findByActiveTrueOrderByNameAsc();}
 @GetMapping("/{id}") public Scheme one(@PathVariable Long id){return repo.findById(id).filter(s->s.active).orElseThrow(()->new NotFoundException("Scheme not found."));}
}