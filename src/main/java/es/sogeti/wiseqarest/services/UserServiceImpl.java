package es.sogeti.wiseqarest.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.sogeti.wiseqarest.dao.ProjectRepository;
import es.sogeti.wiseqarest.dao.UserRepository;
import es.sogeti.wiseqarest.managment.Project;
import es.sogeti.wiseqarest.managment.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Environment environment;
	
	@Override
	public User getUser(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	@Override
	public void saveUser(User user) {
		boolean isDevEnv = false;
		for (String profile : environment.getActiveProfiles())
			if(profile.trim().equals("development"))
				isDevEnv = true;

		// Password hashing process
		String password = 
				(user.getPassword() == null || user.getPassword().isEmpty()) ? 
						getUser(user.getUserName()).getPassword() : 
							(isDevEnv) ? user.getPassword() : 
								passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		userRepository.save(user);
	}	
	
	@Override
	public void deleteUser(String userName) {
		userRepository.delete(userName);
	}	
	
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}	

	@Override
	public List<Project> getProjectsAssociatedToUser(String userName) {
		User user = userRepository.findByUserName(userName);
		
		List<Project> projects = new LinkedList<>();
		for(String id: user.getProjects()) {
			Project project = projectRepository.findById(id);
			projects.add(project);
		}
		return projects;
	}
}
