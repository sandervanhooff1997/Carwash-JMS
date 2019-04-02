package shared.car;

import shared.IMessage;

public class CarReply implements IMessage {

    private int nrCars; // the nrCars of carwash
    private String carwashID; // the unique kitchen identification

    public CarReply() {
        super();
        this.nrCars = 0;
        this.carwashID = "";
    }
    public CarReply(int nrCars, String carwashID) {
        super();
        this.nrCars = nrCars;
        this.carwashID = carwashID;
    }

    public int getNrCars() {
        return nrCars;
    }

    public void setNrCars(int nrCars) {
        this.nrCars = nrCars;
    }

    public String getCarwashID() {
        return carwashID;
    }

    public void setCarwashID(String carwashID) {
        this.carwashID = carwashID;
    }

    @Override
    public String toString(){
        return " nrCars="+String.valueOf(nrCars) + " carwashID="+String.valueOf(carwashID);
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
