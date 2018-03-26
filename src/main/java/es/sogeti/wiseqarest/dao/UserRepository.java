package es.sogeti.wiseqarest.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.sogeti.wiseqarest.managment.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	public User findByUserName(String userName);

}
