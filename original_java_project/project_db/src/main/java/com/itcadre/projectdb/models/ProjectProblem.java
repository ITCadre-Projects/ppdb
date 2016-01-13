package com.itcadre.projectdb.models;

public class ProjectProblem implements ProjectModel {
	private Project project;
	private String projectProblemDescription;

	private boolean persisted;
	
	public boolean isPersisted() {
		return persisted;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectProblemDescription() {
		return projectProblemDescription;
	}

	public void setProjectProblemDescription(String projectProblemDescription) {
		this.projectProblemDescription = projectProblemDescription;
	}
}
