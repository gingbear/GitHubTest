package com.gingbear.githubtest;

abstract class BatteryData {

	int status = 0;

	int health = 0;
	boolean present = false;
    int level = 0;
    int scale = 0;
    int icon_small = 0;
    int plugged = 0;
    int voltage = 0;
    int temperature = 0;
	protected  String technology = "";
	protected  String statusString = "";
	protected  String healthString = "";
	protected  String acString = "";
	protected  StringBuilder sb = new StringBuilder();   
	public int getStatus() {
		return status;
	}
    public int getHealth() {
		return health;
	}


	public boolean isPresent() {
		return present;
	}


	public int getLevel() {
		return level;
	}


	public int getScale() {
		return scale;
	}


	public int getIcon_small() {
		return icon_small;
	}


	public int getPlugged() {
		return plugged;
	}


	public int getVoltage() {
		return voltage;
	}


	public int getTemperature() {
		return temperature;
	}


	public String getTechnology() {
		return technology;
	}


	public String getStatusString() {
		return statusString;
	}


	public String getHealthString() {
		return healthString;
	}


	public String getAcString() {
		return acString;
	}


	public String getSb() {
		return sb.toString();
	}
}
