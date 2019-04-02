package form.Middleware;

import com.sun.istack.NotNull;
import shared.gateway.MessageReceiverGateway;
import shared.gateway.MessageSenderGateway;
import shared.carwash.CarwashReply;
import shared.carwash.CarwashRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

public class CarwashAppGateway {

    private List<Aggregation> aggregations;
    private MessageReceiverGateway receiver;
    @NotNull
    private int id = 0;

    private MiddlewareController frame;
    private List<Rule> carwashRules = new ArrayList<Rule>(){{
        add(new Rule("Helmond",new ArrayList<String>(){{add("auto"); }} ));
        add(new Rule("Tilburg", new ArrayList<String>(){{add("vrachtwagen"); }} ));
        add(new Rule("All", new ArrayList<String>(){{add("auto"); add("vrachtwagen"); }}));
    }};

    public CarwashAppGateway(MiddlewareController frame) {
        this.frame = frame;
        aggregations = new ArrayList<>();

        for (Rule rule : carwashRules) {
            rule.setSender(new MessageSenderGateway(rule.getCarwashName() + "RequestQueue"));
        }
        receiver = new MessageReceiverGateway("carwashReplyQueue");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage)
                {
                    try {
                        processMessage((TextMessage)message, message.getJMSCorrelationID());
                    }
                    catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendCarwashRequest(CarwashRequest request, String corrolationId) {
        int aggregationId = getAggregationID();
        int expectedReplies = 0;
        List<Rule> rulesToSend = new ArrayList<>();
        for (Rule rule : carwashRules) {
            if (rule.check(request.getCarType())) {
                expectedReplies++;
                rulesToSend.add(rule);
            }
        }
        aggregations.add(new Aggregation(aggregationId, expectedReplies, corrolationId));
        for (Rule rule : rulesToSend) {
            try {
                MessageSenderGateway sender = rule.getSender();
                Message message = sender.createTextMessage(request, corrolationId);
                message.setIntProperty("aggregationId", aggregationId);
                sender.send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void processMessage(TextMessage message, String CorrelationId) throws JMSException {
        String body = message.getText();
        System.out.println(">>> CorrolationId: " + message.getJMSCorrelationID() + " Message: " + body);
        CarwashReply carwashReply = new CarwashReply();
        carwashReply.fillFromCommaSeperatedValue(body);
        Integer aggregationId = message.getIntProperty("aggregationId");
        if (aggregationId == null)
            return;
        Aggregation foundAggregation = null;
        for (Aggregation aggregation : aggregations) {
            if (aggregation.getId() == aggregationId) {
                foundAggregation = aggregation;
                break;
            }
        }
        if (foundAggregation != null) {
            foundAggregation.addCarwashReply(carwashReply);
            if (foundAggregation.repliesReceived()){
                processAggregation(foundAggregation);
            }
        }
    }

    private void processAggregation(Aggregation aggregation) {
        aggregations.remove(aggregation);
        frame.add(aggregation.getCorrolationId(), aggregation.getBestReply());
    }

    @NotNull
    private int getAggregationID()
    {
        id++;
        return id;
    }
}
