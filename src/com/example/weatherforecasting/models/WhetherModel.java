package com.example.weatherforecasting.models;

public class WhetherModel  {

	private String description, icon, id, main;

	public WhetherModel(String description, String icon, String id, String main) {

		this.description = description;
		this.icon = icon;
		this.id = id ;
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

}