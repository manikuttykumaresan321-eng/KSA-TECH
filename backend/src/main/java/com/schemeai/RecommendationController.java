package com.schemeai;
import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/recommendations")
public class RecommendationController {
 private final SchemeRepository schemes;public RecommendationController(SchemeRepository schemes){this.schemes=schemes;}
 public record Profile(int age,double annualIncome,String state,String occupation,String need){}
 public record Match(Scheme scheme,int score,List<String> reasons){}
 @PostMapping public List<Match> recommend(@RequestBody Profile p){
  if(p.age()<0||p.age()>120||p.annualIncome()<0)throw new IllegalArgumentException("Enter a valid age and annual income.");
  String occupation=norm(p.occupation()),state=norm(p.state()),need=norm(p.need());
  List<Match> out=new ArrayList<>();
  for(Scheme s:schemes.findByActiveTrueOrderByNameAsc()){
   int score=0;List<String> reasons=new ArrayList<>();
   if(p.age()>=s.minAge&&p.age()<=s.maxAge){score+=35;reasons.add("Age range matches");}
   if(p.annualIncome()<=s.maxAnnualIncome){score+=25;reasons.add("Income is within the listed guide range");}
   if(s.occupation==null||s.occupation.isBlank()||norm(s.occupation).equals("any")||norm(s.occupation).contains(occupation)){score+=25;reasons.add("Occupation appears relevant");}
   if(s.state==null||s.state.isBlank()||norm(s.state).equals("all")||norm(s.state).equals(state)){score+=10;reasons.add("State preference matches");}
   if(need.isBlank()||need.equals("all")||norm(s.category).equals(need)){score+=5;reasons.add("Category matches your selected need");}
   if(score>=45)out.add(new Match(s,score,reasons));
  }
  out.sort(Comparator.comparingInt(Match::score).reversed());return out.stream().limit(10).toList();
 }
 private String norm(String s){return s==null?"":s.trim().toLowerCase(Locale.ROOT);}
}