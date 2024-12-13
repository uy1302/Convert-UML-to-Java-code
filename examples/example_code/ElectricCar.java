public class ElectricCar {
	private double batteryCapacity;
	private double currentCharge;
	private String chargingPortType;

	void chargeBattery(double hours){
		currentCharge += hours * 50; //Assuming a charge rate of 50 units per hour.
		if (currentCharge > batteryCapacity) currentCharge = batteryCapacity;
	}
	void regeneratePower(double energy){
		currentCharge += energy;
		if (currentCharge > batteryCapacity) currentCharge = batteryCapacity;
	}
}

