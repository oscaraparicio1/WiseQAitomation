package es.sogeti.wiseqarest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import es.sogeti.wiseqarest.managment.Project;
import es.sogeti.wiseqarest.managment.User;

@Service
public interface UserService {
	
	public User getUser(String userName);	

	public void saveUser(User user);
	
	public void deleteUser(String userName);
	
	public List<User> getUsers();
	
	public List<Project> getProjectsAssociatedToUser(String userName);
}
