package es.sogeti.wiseqarest.services;

import java.util.List;

import es.sogeti.wiseqarest.managment.Metric;

public interface MetricService {

	public Metric getMetric(String id);
	
	public List<Metric> getMetricByStrName(String strName);
	
	public Metric getMetricByTitle(String title);
	
	public void saveMetric(Metric metric);
	
	public void deleteMetric(String strName);
	
	public List<Metric> getMetrics();
	
}
