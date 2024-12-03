public class ElectricCar {
	private double batteryCapacity;
	private double currentCharge;
	private String chargingPortType;

	void chargeBattery(double hours){
		currentCharge += hours * 10; //Example charging rate
		if(currentCharge > batteryCapacity) currentCharge = batteryCapacity;
	}
	void regeneratePower(double energy){
		currentCharge += energy * 0.8; //Example regeneration efficiency
		if(currentCharge > batteryCapacity) currentCharge = batteryCapacity;
	}
}
