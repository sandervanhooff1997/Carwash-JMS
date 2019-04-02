package form.Middleware;


import shared.car.CarRequest;
import shared.carwash.CarwashReply;
import shared.carwash.CarwashRequest;

class JListLine {

	private CarRequest carRequest;
	private CarwashRequest carwashRequest;
	private CarwashReply carwashReply;

	public JListLine(CarRequest carRequest) {
		this.setCarRequest(carRequest);
	}

	public CarRequest getCarRequest() {
		return carRequest;
	}

	public void setCarRequest(CarRequest carRequest) {
		this.carRequest = carRequest;
	}

	public CarwashRequest getCarwashRequest() {
		return carwashRequest;
	}

	public void setCarwashRequest(CarwashRequest carwashRequest) {
		this.carwashRequest = carwashRequest;
	}

	public CarwashReply getCarwashReply() {
		return carwashReply;
	}

	public void setCarwashReply(CarwashReply carwashReply) {
		this.carwashReply = carwashReply;
	}

	@Override
	public String toString() {
		return carRequest.toString() + " || " + ((carwashReply != null) ? carwashReply.toString() : "waiting for reply...");
	}

}
