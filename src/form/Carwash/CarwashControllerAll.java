package form.Carwash;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import shared.carwash.CarwashReply;
import shared.carwash.CarwashRequest;
import shared.request.RequestReply;

public class CarwashControllerAll implements ICarwashController {

    @FXML
    private TextField tfNrCars;
    @FXML
    private ListView listCarwash;

    private String carwashName = "All";
    private MiddlewareAppGateway gateway;
    private ObservableList<RequestReply<CarwashRequest, CarwashReply>> listModel;
    private ObservableList<RequestReply<CarwashRequest, CarwashReply>> list;

    public void initialize(){
        gateway = new MiddlewareAppGateway(this, carwashName);
        listModel = FXCollections.observableArrayList();
    }

    public void btn1Action(ActionEvent actionEvent) {
        RequestReply<CarwashRequest, CarwashReply> rr = (RequestReply<CarwashRequest, CarwashReply>) listCarwash.getSelectionModel().getSelectedItem();
        int nrCars = Integer.parseInt((tfNrCars.getText()));
        CarwashReply reply = new CarwashReply(carwashName, nrCars);
        if (rr!= null && reply != null){
            rr.setReply(reply);

            listCarwash.setItems(listModel);
            gateway.sendKitchenMenuReply(rr.getRequest(), reply);
            listCarwash.refresh();
        }
    }

    @Override
    public void add(CarwashRequest carwashRequest) {
        final RequestReply<CarwashRequest, CarwashReply> rr = new RequestReply<>(carwashRequest, (CarwashReply) null);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                listModel.add(rr);
            }
        });
        listCarwash.setItems(listModel);
        listCarwash.refresh();
    }
}
