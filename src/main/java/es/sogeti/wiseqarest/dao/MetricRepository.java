package es.sogeti.wiseqarest.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.sogeti.wiseqarest.managment.Metric;

public interface MetricRepository extends MongoRepository<Metric, String> {
	
	public Metric findById(String id);
	
	public List<Metric> findByStrName(String strName);
	
	public Metric findByTitle(String title);
	
}
