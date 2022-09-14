package lk.codelabs.rentcloud.vehicleservice.vehicleservice.service;

import lk.codelabs.rentcloud.model.vehicle.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle save(Vehicle vehicle);
    Vehicle findById(int id);
    List<Vehicle> findAll();
}
