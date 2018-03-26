package es.sogeti.wiseqarest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sogeti.wiseqarest.dao.ProjectRepository;
import es.sogeti.wiseqarest.managment.Project;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Project getProject(String id) {
		return projectRepository.findById(id);
	}
	
	@Override
	public Project getProjectByName(String name) {
		return projectRepository.findByName(name);
	}
		
	
	@Override
	public void saveProject(Project project) {
		projectRepository.save(project);
	}
	
	@Override
	public void deleteProject(String id) {
		projectRepository.delete(id);
	}	
	
	@Override
	public List<Project> getProjects() {
		return projectRepository.findAll();
	}
}













