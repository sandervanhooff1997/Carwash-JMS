package form.Carwash;


import shared.carwash.CarwashRequest;

public class Request {
    private String corrolationId;
    private CarwashRequest carwashRequest;
    private int aggregationId;

    public Request(String corrolationId, CarwashRequest carwashRequest, int aggregationId) {
        this.corrolationId = corrolationId;
        this.carwashRequest = carwashRequest;
        this.aggregationId = aggregationId;
    }

    public String getCorrolationId() {
        return corrolationId;
    }

    public CarwashRequest getCarwashRequest() {
        return carwashRequest;
    }

    public int getAggregationId() {
        return aggregationId;
    }
}
