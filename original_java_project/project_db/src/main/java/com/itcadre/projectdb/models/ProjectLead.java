package com.itcadre.projectdb.models;

public class ProjectLead implements ProjectModel {
	
	private Project project;
	private String leadName;
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

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

}
