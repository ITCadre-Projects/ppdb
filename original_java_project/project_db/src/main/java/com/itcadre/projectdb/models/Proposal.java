package com.itcadre.projectdb.models;

import java.util.Date;

public class Proposal implements ProjectModel {
	
	private boolean persisted;
	private ProjectSummary projectSummary;
	private String name;
	private Date dateSubmitted;
	private String organizationSubmitted;
	private int version;
	private String text;
	
	public boolean save(){
		
		return true;
	}

	@Override
	public boolean destroy() {
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

	public boolean isPersisted() {
		return persisted;
	}

	public ProjectSummary getProjectSummary() {
		return projectSummary;
	}

	public void setProjectSummary(ProjectSummary projectSummary) {
		this.projectSummary = projectSummary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getOrganizationSubmitted() {
		return organizationSubmitted;
	}

	public void setOrganizationSubmitted(String organizationSubmitted) {
		this.organizationSubmitted = organizationSubmitted;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	//Set by the database handler.
	public int getVersion() {
		return version;
	}
}
