package com.itcadre.projectdb.models;

import java.util.Date;

public class ProjectQuote implements ProjectModel {
	
	private int id;
	private ProjectSummary projectSummary;
	private String clientName;
	private Date date;
	private String organization;
	private String text;
	
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

	public ProjectSummary getProjectSummary() {
		return projectSummary;
	}

	public void setProjectSummary(ProjectSummary projectSummary) {
		this.projectSummary = projectSummary;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	//Set by the database
	public int getId() {
		return id;
	}
}
