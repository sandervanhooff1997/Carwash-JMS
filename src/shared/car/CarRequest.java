package shared.car;

import shared.IMessage;

public class CarRequest implements IMessage {
    private String carType; // unique client number.

    public CarRequest() {
        super();
        this.carType = "";
    }

    public CarRequest(String carType) {
        super();
        this.carType = carType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }


    @Override
    public String toString() {
        return "Car Type=" + carType;
    }

    @Override
    public String getCommaSeperatedValue() {
        return carType;
    }

    @Override
    public void fillFromCommaSeperatedValue(String value) {
        String[] array = value.split(",");

        carType = array[0];
    }
}
