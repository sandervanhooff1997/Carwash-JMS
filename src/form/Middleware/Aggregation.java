package form.Middleware;

import shared.carwash.CarwashReply;

import java.util.ArrayList;
import java.util.List;

public class Aggregation {
    private int id;
    private int expectedReplies;
    private String corrolationId;
    private List<CarwashReply> replies;

    public Aggregation(int id, int expectedReplies, String corrolationId) {
        this.expectedReplies = expectedReplies;
        this.id = id;
        this.corrolationId = corrolationId;
        replies = new ArrayList<>();
    }

    public int getExpectedReplies() {
        return expectedReplies;
    }

    public int getId() {
        return id;
    }

    public String getCorrolationId() {
        return corrolationId;
    }

    public int getReceivedReplies() {
        return replies.size();
    }

    public void addCarwashReply(CarwashReply reply) {
        replies.add(reply);
    }

    public boolean repliesReceived() {
        if (replies.size() == expectedReplies) {
            return true;
        }
        return false;
    }

    public CarwashReply getBestReply() {
        CarwashReply bestReply = null;
        for (CarwashReply reply : replies) {
            if (bestReply == null || bestReply.getNrCars() > reply.getNrCars()) {
                bestReply = reply;
            }
        }
        return bestReply;
    }
}
