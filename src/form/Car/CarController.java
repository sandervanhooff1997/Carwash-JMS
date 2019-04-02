package form.Car;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import shared.car.CarReply;
import shared.car.CarRequest;
import shared.request.RequestReply;

public class CarController {

    @FXML
    private TextField tfCarType;
    @FXML
    private ListView lvCars;

    private MiddlewareAppGateway gateway;

    private ObservableList<RequestReply<CarRequest, CarReply>> listModel;
    //private JList<RequestReply<CarRequest,CarReply>> requestReplyList;

    @FXML
    public void initialize(){
        gateway = new MiddlewareAppGateway(this);
        listModel = FXCollections.observableArrayList();
        //requestReplyList = new JList<RequestReply<CarRequest,CarReply>>(listModel);
    }


    public void btnSubmitAction(ActionEvent actionEvent) {
        String carType = tfCarType.getText();

        CarRequest request = new CarRequest(carType);
        RequestReply rr = new RequestReply<CarRequest, CarReply>(request, null);
        listModel.add(rr);
        gateway.orderAMenu(request);
        lvCars.setItems(listModel);

    }

    public void addReply(CarRequest request, CarReply carReply) {
        for (int i = 0; i < listModel.size(); i++){
            RequestReply<CarRequest, CarReply> rr = listModel.get(i);
            if (rr.getRequest() == request){
                rr.setReply(carReply);
                lvCars.setItems(listModel);
                lvCars.refresh();
                break;
            }
        }

    }
}
