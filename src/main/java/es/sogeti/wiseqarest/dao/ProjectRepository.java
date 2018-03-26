package es.sogeti.wiseqarest.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.sogeti.wiseqarest.managment.Project;

public interface ProjectRepository extends MongoRepository<Project, String>{

	public Project findById(String id);
	
	public Project findByName(String name);
}
