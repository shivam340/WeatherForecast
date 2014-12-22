package com.example.weatherforecasting.models;

public class TemperatureModel  {

	private double day , night, morn, eve, min , max;

	public TemperatureModel (double day, double night, double morn , double eve, double min, double max) {

		this.day = day;
		this.night = night;
		this.morn = morn;
		this.eve = eve;
		this.min = min;
		this.max = max;
	}


	public double getDay() {
		return day;
	}


	public void setDay(double day) {
		this.day = day;
	}


	public double getNight() {
		return night;
	}


	public void setNight(double night) {
		this.night = night;
	}


	public double getMorn() {
		return morn;
	}


	public void setMorn(double morn) {
		this.morn = morn;
	}


	public double getEve() {
		return eve;
	}


	public void setEve(double eve) {
		this.eve = eve;
	}


	public double getMin() {
		return min;
	}


	public void setMin(double min) {
		this.min = min;
	}


	public double getMax() {
		return max;
	}


	public void setMax(double max) {
		this.max = max;
	}

}