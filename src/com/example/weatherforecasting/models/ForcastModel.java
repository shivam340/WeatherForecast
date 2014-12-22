package com.example.weatherforecasting.models;

import java.util.ArrayList;

public class ForcastModel  {

	private String name;
	private ArrayList<DataModel> dataModels = new ArrayList<DataModel>();
	
	
	public ForcastModel(String name , ArrayList<DataModel> dataModels) {
		this.name = name;
		this.dataModels.addAll(dataModels);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<DataModel> getDataModels() {
		return dataModels;
	}


	public void setDataModels(ArrayList<DataModel> dataModels) {
		this.dataModels = dataModels;
	}

}