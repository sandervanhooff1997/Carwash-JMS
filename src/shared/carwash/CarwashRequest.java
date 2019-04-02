package shared.carwash;

import shared.IMessage;

public class CarwashRequest implements IMessage {

    private String carType; // the requested carType

    public CarwashRequest() {
        super();
        this.carType = "";
    }

    public CarwashRequest(String carType) {
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
        return " carType=" + carType;
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
