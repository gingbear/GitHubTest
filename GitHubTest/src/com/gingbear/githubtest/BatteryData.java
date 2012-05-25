package com.gingbear.githubtest;

abstract class BatteryData {

	protected  int status = 0;
	protected  int health = 0;
	protected  boolean present = false;
	protected  int level = 0;
	protected   int scale = 0;
	protected  int icon_small = 0;
	protected  int plugged = 0;
	protected  int voltage = 0;
	protected  int temperature = 0;
	protected  String technology = "";
	protected  String statusString = "";
	protected  String healthString = "";
	protected  String acString = "";

	private void Log(){
            CustomLog.v("status", statusString);
            CustomLog.v("health", healthString);
            CustomLog.v("present", String.valueOf(present));
            CustomLog.v("level", String.valueOf(level));
            CustomLog.v("scale", String.valueOf(scale));
            CustomLog.v("icon_small", String.valueOf(icon_small));
            CustomLog.v("plugged", acString);
            CustomLog.v("voltage", String.valueOf(voltage));
            CustomLog.v("temperature", String.valueOf(temperature));
            CustomLog.v("technology", technology);
	}
	
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
		StringBuilder sb = new StringBuilder();   
        sb.append("status:"+statusString+"\n");  
        sb.append("health:"+healthString+"\n");  
        sb.append("present:"+String.valueOf(present)+"\n");
        sb.append("level:"+String.valueOf(level)+"\n");    
        sb.append("scale:"+String.valueOf(scale)+"\n");  
        sb.append("icon_small:"+String.valueOf(icon_small)+"\n");  
        sb.append("plugged:"+acString+"\n");
        sb.append("voltage:"+String.valueOf(voltage)+"\n");
        sb.append("temperature:"+String.valueOf(temperature)+"\n");
        sb.append("technology:"+technology+"\n");
		return sb.toString();
	}
}
