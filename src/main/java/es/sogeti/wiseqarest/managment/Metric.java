package es.sogeti.wiseqarest.managment;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "metrics")
public class Metric {

	@Id
	private String id;
	private String strName;
	private String title;
	private String description;
	private List<String> modelDependencies;
	
	public Metric() {
	}
	
	public Metric(String strName, String title, String description, List<String> modelDependencies) {
		this.strName = strName;
		this.title = title;
		this.description = description;
		this.modelDependencies = modelDependencies;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getModelDependencies() {
		return modelDependencies;
	}

	public void setModelDependencies(List<String> modelDependencies) {
		this.modelDependencies = modelDependencies;
	}
}
