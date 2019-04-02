package form.Middleware;

import shared.gateway.MessageSenderGateway;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String carwashName;
    private List<String> cars;

    private MessageSenderGateway sender;

    public Rule(String carwashName, List<String> cars) {
        this.carwashName = carwashName;
        this.cars = new ArrayList<>(cars);
    }

    public String getCarwashName() {
        return carwashName;
    }

    public MessageSenderGateway getSender() {
        return sender;
    }

    public void setSender(MessageSenderGateway sender) {
        this.sender = sender;
    }

     public boolean check(String car) {
        if (cars.contains(car)) {
            return true;
        }
        return false;
     }
}
