package shared.carwash;

import shared.IMessage;

public class CarwashReply implements IMessage {

    private String carwashID; // the unique kitchen Id
    private int nrCars;

    public CarwashReply() {
        this.carwashID = "";
        this.nrCars = 0;
    }

    public CarwashReply(String carwashID, int nrCars) {
        this.carwashID = carwashID;
        this.nrCars = nrCars;
    }

    public String getCarwashID() {
        return carwashID;
    }

    public void setCarwashID(String carwashID) {
        this.carwashID = carwashID;
    }

    public void setNrCars(int nrCars) {
        this.nrCars = nrCars;
    }

    public int getNrCars() {
        return nrCars;
    }

    public String toString() {
        return "carwash=" + this.carwashID + " nrCars=" + this.nrCars;
    }

    @Override
    public String getCommaSeperatedValue() {
        return carwashID + "," + nrCars;
    }

    @Override
    public void fillFromCommaSeperatedValue(String value) {
        String[] array = value.split(",", 2);

        carwashID = array[0];
        nrCars = Integer.parseInt(array[1]);
    }


}
