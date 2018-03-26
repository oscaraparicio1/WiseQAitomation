package es.sogeti.wiseqarest.services;

import java.util.List;

import es.sogeti.wiseqarest.managment.Project;

public interface ProjectService {
	
	public Project getProject(String id);
	
	public Project getProjectByName(String name);

	public void saveProject(Project project);

	public void deleteProject(String id);
	
	public List<Project> getProjects();	
	
}
