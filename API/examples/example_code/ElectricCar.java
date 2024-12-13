public class ElectricCar {
	private double batteryCapacity;
	private double currentCharge;
	private String chargingPortType;

	void chargeBattery(double hours){
		currentCharge += hours * 50; //example charging rate
		if(currentCharge > batteryCapacity) currentCharge = batteryCapacity;

	}
	void regeneratePower(double energy){
		currentCharge += energy;
		if(currentCharge > batteryCapacity) currentCharge = batteryCapacity;
	}
}

