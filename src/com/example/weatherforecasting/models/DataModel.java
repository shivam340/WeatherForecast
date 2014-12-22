package com.example.weatherforecasting.models;

public class DataModel {

	private TemperatureModel temperatureModel;
	private WhetherModel whetherModel;
	private double humidity, pressure, speed, deg, clouds;
	
	public DataModel( TemperatureModel temperatureModel, WhetherModel whetherModel, 
			double humidity, double pressure, double speed , double deg, double clouds){
		
		this.temperatureModel= temperatureModel;
		this.whetherModel = whetherModel;

		this.humidity = humidity;
		this.pressure = pressure;
		this.speed = speed;
		this.deg = deg;
		this.clouds = clouds;
	}

	

	public TemperatureModel getTemperatureModel() {
		return temperatureModel;
	}


	public void setTemperatureModel(TemperatureModel temperatureModel) {
		this.temperatureModel = temperatureModel;
	}


	public WhetherModel getWhetherModel() {
		return whetherModel;
	}


	public void setWhetherModel(WhetherModel whetherModel) {
		this.whetherModel = whetherModel;
	}


	public double getHumidity() {
		return humidity;
	}


	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}


	public double getPressure() {
		return pressure;
	}


	public void setPressure(double pressure) {
		this.pressure = pressure;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public double getDeg() {
		return deg;
	}


	public void setDeg(double deg) {
		this.deg = deg;
	}


	public double getClouds() {
		return clouds;
	}


	public void setClouds(double clouds) {
		this.clouds = clouds;
	}

}