package es.sogeti.wiseqarest.managment;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import es.sogeti.wiseqarest.authentication.Role;

@Document(collection = "users")
public class User{
	
	@Id
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String language;
	private String token;

	private Role role;

	private Set<String> projects;

	public User(){
		this.userName = "";
		this.password = "";
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.language = "";
		this.role = Role.USER;
		this.projects = new HashSet<>();
	}
	
	public User(String username, String password, String firstname, String lastname, String email, 
			String language){
		
		this.userName = username;
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.language = language;
		this.role = Role.USER;
		this.projects = new HashSet<>();
	}
	
	public User(String username, String password, String firstname, String lastname, String email, 
			String language, Role role){
		
		this.userName = username;
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.language = language;
		this.role = role;
		this.projects = new HashSet<>();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<String> getProjects() {
		return projects;
	}

	public void setProjects(Set<String> projects) {
		this.projects = projects;
	}
	
	public boolean addProject(String projectID){
		return this.projects.add(projectID);
	}
	
	public boolean removeProject(String projectID){
		return this.projects.remove(projectID);
	}

}
