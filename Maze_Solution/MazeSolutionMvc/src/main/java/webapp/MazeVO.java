package webapp;

import java.io.Serializable;

public class MazeVO implements Serializable{
	//implements Serializable
	public String result;
	public String timeInSec;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTimeInSec() {
		return timeInSec;
	}
	public void setTimeInSec(String timeInSec) {
		this.timeInSec = timeInSec;
	}
	@Override
	public String toString() {
		return "MazeVO [result=" + result + ", timeInSec=" + timeInSec + "]";
	}	
	
	
	

}
