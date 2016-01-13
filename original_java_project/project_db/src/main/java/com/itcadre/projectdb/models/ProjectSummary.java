package com.itcadre.projectdb.models;

public class ProjectSummary implements ProjectModel {
	
	private String projectSummaryName;
	private String projectName;
	private String task;
	private String goals;
	
	private Project project;
	
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

	public String getProjectSummaryName() {
		return projectSummaryName;
	}

	public void setProjectSummaryName(String projectSummaryName) {
		this.projectSummaryName = projectSummaryName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	

}
