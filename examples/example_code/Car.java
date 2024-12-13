public class Car {
	private String entertainmentSystem;
	private int seatingCapacity;

	void playMusic(String songName){
		System.out.println("Playing: " + songName);
	}
	void enableCruiseControl(double speed){
		System.out.println("Cruise control enabled at " + speed + " km/h");
	}
}
