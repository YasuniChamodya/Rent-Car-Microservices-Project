package lk.codelabs.rentcloud.rentservice.rentservice.model;

import lk.codelabs.rentcloud.model.rent.Rent;

public class SimpleResponse implements Response{
    Rent rent;

    public SimpleResponse(){}
    public SimpleResponse(Rent rent){
        this.rent = rent;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
}
