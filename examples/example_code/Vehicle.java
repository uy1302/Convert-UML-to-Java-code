public class Vehicle {
	private String make;
	private String model;
	private int year;
	private double speed;

	void start(){
		speed = 0;
	}
	void stop(){
		speed = 0;
	}
	void accelerate(double increment){
		speed += increment;
	}
	void brake(double decrement){
		speed -= decrement;
		if(speed < 0) speed = 0;
	}
}
