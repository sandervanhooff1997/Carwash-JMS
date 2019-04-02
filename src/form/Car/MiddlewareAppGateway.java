package form.Car;

import com.sun.istack.NotNull;
import shared.car.CarRequest;
import shared.gateway.Gateway;
import shared.car.CarReply;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

public class MiddlewareAppGateway extends Gateway {

    private CarController frame;
    private Map<String, CarRequest> menuRequests;
    @NotNull
    private String name = "clientName";
    @NotNull
    private int Id = 0;

    public MiddlewareAppGateway(CarController frame) {
        super("carRequestQueue", "carReplyQueue");
        this.frame = frame;
        menuRequests = new HashMap<>();
    }

    //order a menu
    public void orderAMenu(CarRequest request) {
        String corrolationId = getCorrolationId();
        menuRequests.put(corrolationId, request);
        sender.send(sender.createTextMessage(request, corrolationId));
    }

    @Override
    protected void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        CarReply carReply = new CarReply();
        carReply.fillFromCommaSeperatedValue(message.getText());
        CarRequest request = menuRequests.get(CorrelationId);
        System.out.println(CorrelationId);
        frame.addReply(request, carReply);
    }

    @NotNull
    private String getCorrolationId()
    {
        Id++;
        return name + Integer.toString(Id);
    }

}
