package com.schemeai;
import org.springframework.boot.CommandLineRunner;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataSeeder {
 @Bean CommandLineRunner seed(SchemeRepository schemes,UserRepository users,PasswordEncoder encoder){
  return args->{
   if(schemes.count()==0){
    add(schemes,"National Scholarship Portal","Education","Explore scholarships from participating government departments. Verify the specific programme rules.",1,35,500000,"student","All","Benefits vary by scholarship","https://scholarships.gov.in/");
    add(schemes,"PM-KISAN","Agriculture","Income-support programme for eligible landholding farmer families, subject to scheme rules.",18,100,1000000,"farmer","All","Subject to current scheme rules","https://pmkisan.gov.in/");
    add(schemes,"Pradhan Mantri MUDRA Yojana","Business","Explore eligible small-business lending through participating lenders.",18,100,10000000,"entrepreneur","All","Loan terms depend on lender and category","https://www.mudra.org.in/");
    add(schemes,"Pradhan Mantri Awas Yojana","Housing","Housing support programmes for eligible households; criteria depend on the component.",18,100,500000,"any","All","Check current component criteria","https://pmay-urban.gov.in/");
    add(schemes,"Ayushman Bharat PM-JAY","Healthcare","Check official eligibility for publicly funded hospital care under the programme.",1,100,500000,"any","All","Coverage subject to programme rules","https://pmjay.gov.in/");
    add(schemes,"National Career Service","Employment","Explore job-search, career guidance and employment services.",18,100,10000000,"jobseeker","All","Career and employment services","https://www.ncs.gov.in/");
    add(schemes,"Stand-Up India","Business","Explore lending support for eligible women and SC/ST entrepreneurs.",18,100,10000000,"entrepreneur","All","Loan support subject to eligibility","https://www.standupmitra.in/");
   }
   String email=System.getenv().getOrDefault("ADMIN_EMAIL","admin@schemeai.local");
   if(!users.existsByEmail(email))users.save(new AppUser("Scheme Admin",email,encoder.encode(System.getenv().getOrDefault("ADMIN_PASSWORD","ChangeMe123!")),"ADMIN"));
  };
 }
 private void add(SchemeRepository repo,String name,String category,String description,int min,int max,double income,String occupation,String state,String benefit,String url){
  Scheme s=new Scheme();s.name=name;s.category=category;s.description=description;s.minAge=min;s.maxAge=max;s.maxAnnualIncome=income;s.occupation=occupation;s.state=state;s.benefit=benefit;s.officialUrl=url;s.active=true;repo.save(s);
 }
}