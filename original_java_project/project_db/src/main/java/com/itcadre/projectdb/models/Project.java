package com.itcadre.projectdb.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project implements ProjectModel {
	private boolean persisted;
	
	private String projectName;
	private Date startDate;
	private Date endDate;
	private String genericVersion;
	private String projectSummaryName;
	
	private ClientInfo clientInfo;
	private ProjectLead projectLead;
	private ProjectProblem projectProblem;
	
	private List<ProjectResult> projectResults;
	private ProjectSummary projectSummary;
	
	public boolean isPersisted() {
		return persisted;
	}
	
	
	public Project(){
		projectResults = new ArrayList<ProjectResult>();
	}
	

	@Override
	public boolean destroy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fromJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getGenericVersion() {
		return genericVersion;
	}

	public void setGenericVersion(String genericVersion) {
		this.genericVersion = genericVersion;
	}

	public String getProjectSummaryName() {
		return projectSummaryName;
	}

	public void setProjectSummaryName(String projectSummaryName) {
		this.projectSummaryName = projectSummaryName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public ClientInfo getClientInfo() {
		return clientInfo;
	}


	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}


	public ProjectLead getProjectLead() {
		return projectLead;
	}


	public void setProjectLead(ProjectLead projectLead) {
		this.projectLead = projectLead;
	}


	public ProjectProblem getProjectProblem() {
		return projectProblem;
	}


	public void setProjectProblem(ProjectProblem projectProblem) {
		this.projectProblem = projectProblem;
	}


	public List<ProjectResult> getProjectResults() {
		return projectResults;
	}


	public void setProjectResults(List<ProjectResult> projectResults) {
		this.projectResults = projectResults;
	}


	public ProjectSummary getProjectSummary() {
		return projectSummary;
	}


	public void setProjectSummary(ProjectSummary projectSummary) {
		this.projectSummary = projectSummary;
	}

}
