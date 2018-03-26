package es.sogeti.wiseqarest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.sogeti.wiseqarest.dao.MetricRepository;
import es.sogeti.wiseqarest.managment.Metric;

@Service
public class MetricServiceImpl implements MetricService {

	@Autowired
	public MetricRepository metricRepository;
	
	@Override
	public Metric getMetric(String id) {
		return metricRepository.findById(id);
	}
	
//	@Override
//	public Metric getMetricByStrName(String strName) {
//		return metricRepository.findByStrName(strName);
//	}
//	
	@Override
	public List<Metric> getMetricByStrName(String strName) {
		return metricRepository.findByStrName(strName);
	}

	@Override
	public Metric getMetricByTitle(String title) {
		return metricRepository.findByTitle(title);
	}

	@Override
	public void saveMetric(Metric metric) {
		metricRepository.save(metric);
	}

	@Override
	public void deleteMetric(String strName) {
		metricRepository.delete(strName);
	}

	@Override
	public List<Metric> getMetrics() {
		return metricRepository.findAll();
	}

}
