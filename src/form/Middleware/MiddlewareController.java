package form.Middleware;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import shared.car.CarReply;
import shared.car.CarRequest;
import shared.carwash.CarwashReply;
import shared.carwash.CarwashRequest;

import java.util.HashMap;
import java.util.Map;

public class MiddlewareController {

    @FXML
    private ListView lvRequest;

    private CarAppGateway carAppGateway;
    private CarwashAppGateway carwashAppGateway;
    private Map<String, CarRequest> carwashRequests;

    private ObservableList<JListLine> listModel;
    //private JList<JListLine> list = new JList<>(listModel);

    public void initialize(){
        listModel = FXCollections.observableArrayList();
        carAppGateway = new CarAppGateway(this);
        carwashAppGateway = new CarwashAppGateway(this);
        carwashRequests = new HashMap<>();

    }

    public void add(final CarRequest carRequest, String correlationId) {
        carwashRequests.put(correlationId, carRequest);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                listModel.add(new JListLine(carRequest));
            }
        });
        CarwashRequest carwashRequest = new CarwashRequest(carRequest.getCarType());
        carwashAppGateway.sendCarwashRequest(carwashRequest, correlationId);
        this.add(carRequest, carwashRequest);
    }

    public void add(String corrolationId, CarwashReply carwashReply){
        JListLine rr = getLine(carwashRequests.get(corrolationId));
        if (rr!= null && carwashReply != null){
            rr.setCarwashReply(carwashReply);
            lvRequest.setItems(listModel);
            lvRequest.refresh();
        }
        carAppGateway.carReply(new CarReply(carwashReply.getNrCars(), carwashReply.getCarwashID()), corrolationId);
    }

    public void add(CarRequest carRequest, CarwashRequest carwashRequest){
        JListLine rr = getLine(carRequest);
        if (rr!= null && carwashRequest != null){
            rr.setCarwashRequest(carwashRequest);
            lvRequest.setItems(listModel);
            lvRequest.refresh();
        }
    }

    private JListLine getLine(CarRequest request){
        for (int i = 0; i < listModel.size(); i++){
            JListLine rr =listModel.get(i);
            if (rr.getCarRequest() == request){
                return rr;
            }
        }
        return null;
    }

}
