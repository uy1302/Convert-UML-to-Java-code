public class Car {
	private String entertainmentSystem;
	private int seatingCapacity;

	void playMusic(String songName){
		System.out.println("Playing " + songName + " on the " + entertainmentSystem);
	}
	void enableCruiseControl(double speed){
		System.out.println("Cruise control engaged at " + speed + " mph.");
	}
}
