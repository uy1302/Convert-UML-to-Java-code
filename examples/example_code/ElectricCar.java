public class ElectricCar {
	private double batteryCapacity;
	private double currentCharge;
	private String chargingPortType;

	void chargeBattery(double hours){
		currentCharge += hours * 50; // Assuming 50 units of charge per hour.
		System.out.println("Battery charged for " + hours + " hours.");

	}
	void regeneratePower(double energy){
		currentCharge += energy;
		System.out.println("Regenerated " + energy + " units of power.");
	}
}
```
