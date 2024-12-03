from GenAI.AIgenerator import AIagent
import os 
import requests
import urllib.parse
import time 

agent = AIagent(api_key="AIzaSyD7UJTPn6veFbiKhgDydYTgrDR14D9Grf4")

descriptions = {
        "public class Vehicle": {
            # "start": "The Vehicle initializes its engine and prepares all systems for operation.",
            "start" : "",
            # "stop": "The Vehicle powers down its engine and deactivates non-essential systems.",
            "stop" : "",
            # "accelerate": "The Vehicle increases its speed by applying more power to its drivetrain.",
            "accelerate":"",
            "brake": "The Vehicle slows down or comes to a stop by applying its braking mechanism."
        },
        "public class Car": {
            "playMusic": "The Car's entertainment system plays music for the passengers.",
            "enableCruiseControl": "The Car maintains a steady speed without driver input through its cruise control system."
        },
        "public class ElectricCar": {
            "chargeBattery": "The ElectricCar connects to a charging station to replenish its battery.",
            "regeneratePower": "The ElectricCar converts kinetic energy into electrical energy during braking to recharge the battery."
        }
    }

classes = {
    "public class Vehicle": {
        "attributes": {
            "make": "private String make",
            "model": "private String model",
            "year": "private int year",
            "speed": "private double speed"
        },
        "methods": {
            "start": "void start()",
            "stop": "void stop()",
            "accelerate": "void accelerate(double increment)",
            "brake": "void brake(double decrement)"
        }
    },
    "public class Car": {
        "attributes": {
            "entertainmentSystem": "private String entertainmentSystem",
            "seatingCapacity": "private int seatingCapacity"
        },
        "methods": {
            "playMusic": "void playMusic(String songName)",
            "enableCruiseControl": "void enableCruiseControl(double speed)"
        }
    },
    "public class ElectricCar": {
        "attributes": {
            "batteryCapacity": "private double batteryCapacity",
            "currentCharge": "private double currentCharge",
            "chargingPortType": "private String chargingPortType"
        },
        "methods": {
            "chargeBattery": "void chargeBattery(double hours)",
            "regeneratePower": "void regeneratePower(double energy)"
        }
    }
}

url = "http://127.0.0.1:8000"
res = agent.generateCodeByDescriptions(descriptions, classes)
# print(res)
for file in res:
    file_name = file[0] + ".java"
    # print(file_name)
    file_contents = file[1]
    # print(file_contents)
    write_path = "examples/example_code" + f"/{file_name}"
    if os.path.exists(write_path):
        with open(write_path, "w") as f:
            f.write(file_contents)
    else:
        os.makedirs(os.path.dirname(write_path), exist_ok=True)
        with open(write_path, "x") as f:
            f.write(file_contents)
    # print(file[0])
    # print(file_contents)
    # print(url)
    response = requests.post(
        f"{url}/send_data", 
        json={"Content": file_contents}
    )
    print("Response:", response.text)


# response = requests.post(f"{url}/clear_data", json={})
# print("Response:", response.text)