package es.sogeti.wiseqarest.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.sogeti.wiseqarest.dao.UserRepository;
import es.sogeti.wiseqarest.managment.User;

public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private UserRepository ur;
	@Autowired
	private Environment environment;
	/*
	@Autowired
	private ProjectRepository pr;
	*/
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * Spring security check with username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*
		Project p = new Project("Project 1", "a", "a", "a", "a", "a");
		pr.save(p);
		Project p2 = new Project("Project 2", "a", "a", "a", "a", "a");
		pr.save(p2);
		*/
		if(ur.findAll().isEmpty()) {
			boolean isDevEnv = false;
			for (String profile : environment.getActiveProfiles())
				if(profile.trim().equals("development"))
					isDevEnv = true;
			
			String password = "realadmin";
			if(!isDevEnv)
				password = passwordEncoder.encode(password);
			
			User admin = new User("admin", password, "a", "a", "a", "en", Role.ADMIN);
			ur.save(admin);
		}
		/*
		User userdb = new User("cristian", passwordEncoder.encode("asdasd"), "a", "a", "a", "en", Role.USER);
		userdb.addProject(p.getId());
		userdb.addProject(p2.getId());
		ur.save(userdb);
		*/
		
		User user = getUserDetail(username);
		return new org.springframework.security.core.userdetails.User(
					user.getUserName(),
					user.getPassword(),
					true,
					true,
					true,
					true,
					getAuthorities(user.getRole())
			);
	}
	
	/**
	 * Generates a list of authorities based on the role passed as a parameter
	 * 
	 * @param role 
	 * @return A list of the authorities granted to a determined role
	 */
	public static List<GrantedAuthority> getAuthorities(Role role) {
		 List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		 if (Role.USER.equals(role)) {
			 authList.add(new SimpleGrantedAuthority(Role.USER.toString()));			 
		 } else if (Role.ADMIN.equals(role)) {
			 authList.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		 }
		 return authList;
	}
	
	/**
	 * Get user from database based on the username
	 * 
	 * @param username Username to search
	 * @return kcycle user
	 */
	public User getUserDetail(String username){
		 MongoOperations mongoOperation = (MongoOperations) mongoTemplate;
		 User user = mongoOperation.findOne(
				 new Query(Criteria.where("userName").is(username)), User.class
			);
		 return user;
	}	
}