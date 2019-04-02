package form.Middleware;

import shared.car.CarRequest;
import shared.gateway.Gateway;
import shared.car.CarReply;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class CarAppGateway extends Gateway {
    private MiddlewareController frame;

    public CarAppGateway(MiddlewareController frame) {
        super("carReplyQueue", "carRequestQueue");
        this.frame = frame;
    }

    public void carReply(CarReply reply, String corrolationId) {
        sender.send(sender.createTextMessage(reply, corrolationId));
    }

    @Override
    protected void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        CarRequest carRequest = new CarRequest();
        carRequest.fillFromCommaSeperatedValue(message.getText());
        frame.add(carRequest, CorrelationId);
    }
}
