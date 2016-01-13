package com.itcadre.projectdb.models;

public interface ProjectModel {
	
	public boolean destroy();
	public boolean save();
	public String toJSON();
	public String fromJSON();
}
