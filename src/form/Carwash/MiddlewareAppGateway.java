package form.Carwash;

import shared.gateway.Gateway;
import shared.carwash.CarwashReply;
import shared.carwash.CarwashRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

public class MiddlewareAppGateway extends Gateway {

    private List<Request> requests;
    private ICarwashController frame;

    public MiddlewareAppGateway(ICarwashController frame, String carwashName) {
        super("carwashReplyQueue", carwashName + "RequestQueue");
        this.frame = frame;
        requests = new ArrayList<>();
    }

    public void sendKitchenMenuReply(CarwashRequest carwashRequest, CarwashReply reply) {
        for (Request request : requests) {
            if (request.getCarwashRequest() == carwashRequest) {
                try {
                    Message message = sender.createTextMessage(reply, request.getCorrolationId());
                    message.setIntProperty("aggregationId", request.getAggregationId());
                    sender.send(message);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    protected void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        Integer aggregationId = message.getIntProperty("aggregationId");

        CarwashRequest carwashRequest = new CarwashRequest();
        carwashRequest.fillFromCommaSeperatedValue(message.getText());
        requests.add(new Request(message.getJMSCorrelationID(), carwashRequest, aggregationId));
        frame.add(carwashRequest);
    }


}
