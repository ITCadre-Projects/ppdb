package com.itcadre.projectdb.models;

public class ProjectResult implements ProjectModel {
	
	private Project project;
	private String textField;
	private String exemplar;
	private int version;
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

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getExemplar() {
		return exemplar;
	}

	public void setExemplar(String exemplar) {
		this.exemplar = exemplar;
	}

	//Version is set by the database
	public int getVersion() {
		return version;
	}
}
