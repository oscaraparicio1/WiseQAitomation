package es.sogeti.wiseqarest.managment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projects")
public class Project {

	@Id
	private String id;
	private String name;
	private String customerName;
	private String description;
	private String specificationTeam;
	private String testingTeam;
	private String testingCompany;

	public Project() {
	}
	
	public Project(String name, String customerName, String description, 
			String specificationTeam, String testingTeam, String testingCompany){
		this.name = name;
		this.customerName = customerName;
		this.description = description;
		this.specificationTeam = specificationTeam;
		this.testingTeam = testingTeam;
		this.testingCompany = testingCompany;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecificationTeam() {
		return specificationTeam;
	}

	public void setSpecificationTeam(String specificationTeam) {
		this.specificationTeam = specificationTeam;
	}

	public String getTestingTeam() {
		return testingTeam;
	}

	public void setTestingTeam(String testingTeam) {
		this.testingTeam = testingTeam;
	}

	public String getTestingCompany() {
		return testingCompany;
	}

	public void setTestingCompany(String testingCompany) {
		this.testingCompany = testingCompany;
	}
}
